package   guineu  .  modules  .  mylly  .  filter  .  GroupIdentification  ; 

import   guineu  .  data  .  Dataset  ; 
import   guineu  .  data  .  DatasetType  ; 
import   guineu  .  data  .  PeakListRow  ; 
import   guineu  .  data  .  impl  .  datasets  .  SimpleGCGCDataset  ; 
import   guineu  .  data  .  impl  .  peaklists  .  SimplePeakListRowGCGC  ; 
import   guineu  .  taskcontrol  .  AbstractTask  ; 
import   guineu  .  taskcontrol  .  TaskStatus  ; 
import   java  .  io  .  BufferedReader  ; 
import   java  .  io  .  ByteArrayInputStream  ; 
import   java  .  io  .  ByteArrayOutputStream  ; 
import   java  .  io  .  File  ; 
import   java  .  io  .  FileNotFoundException  ; 
import   java  .  io  .  IOException  ; 
import   java  .  io  .  InputStream  ; 
import   java  .  io  .  InputStreamReader  ; 
import   java  .  io  .  OutputStream  ; 
import   java  .  io  .  StringWriter  ; 
import   java  .  io  .  Writer  ; 
import   java  .  net  .  HttpURLConnection  ; 
import   java  .  net  .  URL  ; 
import   java  .  net  .  URLConnection  ; 
import   java  .  util  .  ArrayList  ; 
import   java  .  util  .  List  ; 
import   java  .  util  .  logging  .  Level  ; 
import   java  .  util  .  logging  .  Logger  ; 
import   org  .  jfree  .  xml  .  writer  .  AttributeList  ; 
import   org  .  jfree  .  xml  .  writer  .  XMLWriter  ; 





public   class   GroupIdentificationTask   extends   AbstractTask  { 

private   Dataset   dataset  ; 

private   double   progress  =  0.0  ; 

public   GroupIdentificationTask  (  Dataset   dataset  )  { 
this  .  dataset  =  dataset  ; 
} 

public   String   getTaskDescription  (  )  { 
return  "Filtering files with Group Identifiacion Filter... "  ; 
} 

public   double   getFinishedPercentage  (  )  { 
return   progress  ; 
} 

public   void   cancel  (  )  { 
setStatus  (  TaskStatus  .  CANCELED  )  ; 
} 

public   void   run  (  )  { 
setStatus  (  TaskStatus  .  PROCESSING  )  ; 
if  (  dataset  .  getType  (  )  !=  DatasetType  .  GCGCTOF  )  { 
setStatus  (  TaskStatus  .  ERROR  )  ; 
errorMessage  =  "Wrong data set type. This module is for the group identification in GCxGC-MS data"  ; 
return  ; 
}  else  { 
try  { 
actualMap  (  (  SimpleGCGCDataset  )  dataset  )  ; 
setStatus  (  TaskStatus  .  FINISHED  )  ; 
}  catch  (  Exception   ex  )  { 
Logger  .  getLogger  (  GroupIdentificationTask  .  class  .  getName  (  )  )  .  log  (  Level  .  SEVERE  ,  null  ,  ex  )  ; 
setStatus  (  TaskStatus  .  ERROR  )  ; 
} 
} 
} 

private   String   PredictManyXMLFile  (  SimplePeakListRowGCGC   newRow  )  throws   FileNotFoundException  ,  IOException  { 
Writer   w  =  new   StringWriter  (  )  ; 
XMLWriter   xmlW  =  new   XMLWriter  (  w  )  ; 
xmlW  .  writeXmlDeclaration  (  )  ; 
xmlW  .  allowLineBreak  (  )  ; 
AttributeList   attributes  =  new   AttributeList  (  )  ; 
attributes  .  setAttribute  (  "xmlns:xsi"  ,  "http://www.w3.org/2001/XMLSchema-instance"  )  ; 
attributes  .  setAttribute  (  "xmlns:xsd"  ,  "http://www.w3.org/2001/XMLSchema"  )  ; 
attributes  .  setAttribute  (  "xmlns:soap"  ,  "http://schemas.xmlsoap.org/soap/envelope/"  )  ; 
xmlW  .  writeTag  (  "soap:Envelope"  ,  attributes  ,  false  )  ; 
xmlW  .  startBlock  (  )  ; 
xmlW  .  writeTag  (  "soap:Body"  ,  false  )  ; 
xmlW  .  startBlock  (  )  ; 
attributes  =  new   AttributeList  (  )  ; 
attributes  .  setAttribute  (  "xmlns"  ,  "http://gmd.mpimp-golm.mpg.de/webservices/wsPrediction.asmx/"  )  ; 
xmlW  .  writeTag  (  "PredictAll4AlkaneRiColumnTypeComposition"  ,  attributes  ,  false  )  ; 
xmlW  .  startBlock  (  )  ; 
xmlW  .  writeTag  (  "Ri"  ,  false  )  ; 
String   RTI  =  String  .  valueOf  (  newRow  .  getRTI  (  )  )  ; 
xmlW  .  writeText  (  RTI  )  ; 
xmlW  .  writeText  (  "</Ri>"  )  ; 
xmlW  .  endBlock  (  )  ; 
xmlW  .  writeTag  (  "Spectrum"  ,  false  )  ; 
String   spectrum  =  newRow  .  getSpectrumString  (  )  ; 
spectrum  =  spectrum  .  replace  (  ":"  ,  " "  )  ; 
spectrum  =  spectrum  .  replace  (  ", "  ,  ""  )  ; 
spectrum  =  spectrum  .  replace  (  "["  ,  ""  )  ; 
spectrum  =  spectrum  .  replace  (  "]"  ,  ""  )  ; 
xmlW  .  writeText  (  spectrum  )  ; 
xmlW  .  writeText  (  "</Spectrum>"  )  ; 
xmlW  .  endBlock  (  )  ; 
xmlW  .  writeTag  (  "ColumnType"  ,  false  )  ; 
xmlW  .  writeText  (  "MDN35"  )  ; 
xmlW  .  writeText  (  "</ColumnType>"  )  ; 
xmlW  .  endBlock  (  )  ; 
xmlW  .  endBlock  (  )  ; 
xmlW  .  writeCloseTag  (  "PredictAll4AlkaneRiColumnTypeComposition"  )  ; 
xmlW  .  endBlock  (  )  ; 
xmlW  .  writeCloseTag  (  "soap:Body"  )  ; 
xmlW  .  endBlock  (  )  ; 
xmlW  .  writeCloseTag  (  "soap:Envelope"  )  ; 
xmlW  .  close  (  )  ; 
return   w  .  toString  (  )  ; 
} 

private   List  <  String  >  getAnswer  (  String   xmlFile2Send  ,  HttpURLConnection   httpConn  )  { 
try  { 
InputStream   fin  =  new   ByteArrayInputStream  (  xmlFile2Send  .  getBytes  (  "UTF-8"  )  )  ; 
ByteArrayOutputStream   bout  =  new   ByteArrayOutputStream  (  )  ; 
copy  (  fin  ,  bout  )  ; 
fin  .  close  (  )  ; 
byte  [  ]  b  =  bout  .  toByteArray  (  )  ; 
httpConn  .  setRequestProperty  (  "Content-Length"  ,  String  .  valueOf  (  b  .  length  )  )  ; 
httpConn  .  setRequestProperty  (  "Content-Type"  ,  "text/xml; charset=utf-8"  )  ; 
httpConn  .  setRequestProperty  (  "SOAPAction"  ,  "http://gmd.mpimp-golm.mpg.de/webservices/wsPrediction.asmx/PredictAll4AlkaneRiColumnTypeComposition"  )  ; 
httpConn  .  setRequestMethod  (  "POST"  )  ; 
httpConn  .  setDoOutput  (  true  )  ; 
httpConn  .  setDoInput  (  true  )  ; 
OutputStream   out  =  httpConn  .  getOutputStream  (  )  ; 
out  .  write  (  b  )  ; 
out  .  close  (  )  ; 
InputStreamReader   isr  =  new   InputStreamReader  (  httpConn  .  getInputStream  (  )  )  ; 
BufferedReader   in  =  new   BufferedReader  (  isr  )  ; 
String   inputLine  ; 
List  <  String  >  group  =  new   ArrayList  <  String  >  (  )  ; 
String   name  =  ""  ; 
while  (  (  inputLine  =  in  .  readLine  (  )  )  !=  null  )  { 
while  (  inputLine  .  contains  (  "<FunctionalGroup>"  )  )  { 
name  =  inputLine  .  substring  (  inputLine  .  indexOf  (  "<FunctionalGroup>"  )  +  17  ,  inputLine  .  indexOf  (  "</FunctionalGroup>"  )  )  ; 
String   temporalInputLine  =  inputLine  .  substring  (  inputLine  .  indexOf  (  "</Results>"  )  +  10  )  ; 
if  (  temporalInputLine  .  contains  (  "<PredictionGroupIsPresent>true</PredictionGroupIsPresent>"  )  &&  !  group  .  contains  (  name  )  )  { 
group  .  add  (  name  )  ; 
} 
name  =  ""  ; 
inputLine  =  inputLine  .  substring  (  inputLine  .  indexOf  (  "</Results>"  )  +  10  )  ; 
} 
} 
in  .  close  (  )  ; 
fin  .  close  (  )  ; 
httpConn  .  disconnect  (  )  ; 
return   group  ; 
}  catch  (  Exception   ex  )  { 
ex  .  printStackTrace  (  )  ; 
return   null  ; 
} 
} 

public   static   void   copy  (  InputStream   in  ,  OutputStream   out  )  throws   IOException  { 
synchronized  (  in  )  { 
synchronized  (  out  )  { 
byte  [  ]  buffer  =  new   byte  [  256  ]  ; 
while  (  true  )  { 
int   bytesRead  =  in  .  read  (  buffer  )  ; 
if  (  bytesRead  ==  -  1  )  { 
break  ; 
} 
out  .  write  (  buffer  ,  0  ,  bytesRead  )  ; 
} 
} 
} 
} 

protected   void   actualMap  (  SimpleGCGCDataset   input  )  throws   Exception  { 
int   numRows  =  input  .  getNumberRows  (  )  ; 
int   count  =  0  ; 
for  (  PeakListRow   row  :  input  .  getAlignment  (  )  )  { 
if  (  getStatus  (  )  ==  TaskStatus  .  CANCELED  )  { 
break  ; 
} 
URL   url  =  new   URL  (  "http://gmd.mpimp-golm.mpg.de/webservices/wsPrediction.asmx"  )  ; 
URLConnection   connection  =  url  .  openConnection  (  )  ; 
HttpURLConnection   httpConn  =  (  HttpURLConnection  )  connection  ; 
String   xmlFile  =  this  .  PredictManyXMLFile  (  (  SimplePeakListRowGCGC  )  row  )  ; 
List  <  String  >  group  =  this  .  getAnswer  (  xmlFile  ,  httpConn  )  ; 
if  (  group  !=  null  )  { 
String   finalGroup  =  ""  ; 
for  (  String   name  :  group  )  { 
finalGroup  +=  name  +  ","  ; 
} 
(  (  SimplePeakListRowGCGC  )  row  )  .  setGolmGroup  (  finalGroup  )  ; 
count  ++  ; 
progress  =  (  double  )  count  /  numRows  ; 
} 
} 
File   file  =  new   File  (  "temporalFile.xml"  )  ; 
file  .  delete  (  )  ; 
} 

public   String   getName  (  )  { 
return  "Filter Group Identification"  ; 
} 
} 

