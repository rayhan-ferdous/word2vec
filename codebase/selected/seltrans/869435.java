package   org  .  jdesktop  .  swingx  .  ws  .  yahoo  .  search  .  videosearch  ; 

import   java  .  io  .  InputStream  ; 
import   java  .  net  .  URL  ; 
import   java  .  util  .  ArrayList  ; 
import   java  .  util  .  Date  ; 
import   java  .  util  .  HashMap  ; 
import   java  .  util  .  HashSet  ; 
import   java  .  util  .  List  ; 
import   java  .  util  .  Map  ; 
import   java  .  util  .  Set  ; 
import   javax  .  xml  .  parsers  .  ParserConfigurationException  ; 
import   javax  .  xml  .  parsers  .  SAXParser  ; 
import   javax  .  xml  .  parsers  .  SAXParserFactory  ; 
import   org  .  jdesktop  .  swingx  .  ws  .  yahoo  .  search  .  Format  ; 
import   org  .  jdesktop  .  swingx  .  ws  .  yahoo  .  search  .  ResultsArrayList  ; 
import   org  .  jdesktop  .  swingx  .  ws  .  yahoo  .  search  .  ResultsList  ; 
import   org  .  jdesktop  .  swingx  .  ws  .  yahoo  .  search  .  Type  ; 
import   org  .  jdesktop  .  swingx  .  ws  .  yahoo  .  search  .  YahooSearch  ; 
import   org  .  xml  .  sax  .  Attributes  ; 
import   org  .  xml  .  sax  .  SAXException  ; 
import   org  .  xml  .  sax  .  helpers  .  DefaultHandler  ; 








public   final   class   YahooVideoSearch   extends   YahooSearch  { 

private   String   appId  ; 

private   String   query  ; 

private   Type   type  ; 

private   FileFormat   format  ; 

private   boolean   adultOk  ; 

private   Set  <  String  >  sites  =  new   HashSet  <  String  >  (  )  ; 


public   YahooVideoSearch  (  )  { 
} 




protected   final   String   getVersionNumber  (  )  { 
return  "V1"  ; 
} 




protected   final   String   getServiceName  (  )  { 
return  "VideoSearchService"  ; 
} 




protected   final   String   getMethod  (  )  { 
return  "videoSearch"  ; 
} 




protected   final   Map   getParameters  (  )  { 
Map   params  =  new   HashMap  (  )  ; 
params  .  put  (  "appid"  ,  appId  )  ; 
params  .  put  (  "query"  ,  query  )  ; 
if  (  type  !=  null  )  { 
params  .  put  (  "type"  ,  type  .  getCode  (  )  )  ; 
} 
if  (  format  !=  null  )  { 
params  .  put  (  "format"  ,  format  .  getCode  (  )  )  ; 
} 
params  .  put  (  "adult_ok"  ,  adultOk  ?  1  :  0  )  ; 
if  (  sites  .  size  (  )  >  0  )  { 
StringBuilder   buffer  =  new   StringBuilder  (  )  ; 
for  (  String   site  :  sites  )  { 
if  (  buffer  .  length  (  )  >  0  )  { 
buffer  .  append  (  "&"  )  ; 
buffer  .  append  (  "site="  )  ; 
buffer  .  append  (  site  )  ; 
}  else  { 
buffer  .  append  (  site  )  ; 
} 
} 
params  .  put  (  "site"  ,  buffer  .  toString  (  )  )  ; 
} 
return   params  ; 
} 




protected   ResultsList  <  Result  >  readResults  (  InputStream   in  )  throws   Exception  { 
SAXParserFactory   factory  =  SAXParserFactory  .  newInstance  (  )  ; 
SAXParser   p  ; 
p  =  factory  .  newSAXParser  (  )  ; 
Parser   pp  =  new   Parser  (  )  ; 
p  .  parse  (  in  ,  pp  )  ; 
return   pp  .  results  ; 
} 




public   String   getAppId  (  )  { 
return   appId  ; 
} 








public   void   setAppId  (  String   appId  )  { 
String   old  =  getAppId  (  )  ; 
this  .  appId  =  appId  ; 
firePropertyChange  (  "appId"  ,  old  ,  getAppId  (  )  )  ; 
} 




public   String   getQuery  (  )  { 
return   query  ; 
} 





public   void   setQuery  (  String   query  )  { 
String   old  =  getQuery  (  )  ; 
this  .  query  =  query  ; 
firePropertyChange  (  "query"  ,  old  ,  getQuery  (  )  )  ; 
} 

public   Type   getType  (  )  { 
return   type  ; 
} 





public   void   setType  (  Type   type  )  { 
Type   old  =  getType  (  )  ; 
this  .  type  =  type  ; 
firePropertyChange  (  "type"  ,  old  ,  getType  (  )  )  ; 
} 




public   FileFormat   getFileFormat  (  )  { 
return   format  ; 
} 





public   void   setFileFormat  (  FileFormat   format  )  { 
FileFormat   old  =  getFileFormat  (  )  ; 
this  .  format  =  format  ; 
firePropertyChange  (  "fileFormat"  ,  old  ,  getFileFormat  (  )  )  ; 
} 




public   boolean   isAdultOk  (  )  { 
return   adultOk  ; 
} 





public   void   setAdultOk  (  boolean   adultOk  )  { 
boolean   old  =  isAdultOk  (  )  ; 
this  .  adultOk  =  adultOk  ; 
firePropertyChange  (  "adultOk"  ,  old  ,  isAdultOk  (  )  )  ; 
} 





public   static   final   class   Result  { 

private   String   title  ; 

private   String   summary  ; 

private   URL   url  ; 

private   URL   clickUrl  ; 

private   URL   refererUrl  ; 

private   int   fileSize  ; 

private   FileFormat   fileFormat  ; 

private   int   height  ; 

private   int   width  ; 

private   int   duration  ; 

private   int   channels  ; 

private   boolean   streaming  ; 

private   URL   thumbnail  ; 

private   String   publisher  ; 

private   String   copyright  ; 

public   String   getTitle  (  )  { 
return   title  ; 
} 

public   String   getSummary  (  )  { 
return   summary  ; 
} 

public   URL   getUrl  (  )  { 
return   url  ; 
} 

public   URL   getClickUrl  (  )  { 
return   clickUrl  ; 
} 

public   URL   getRefererUrl  (  )  { 
return   refererUrl  ; 
} 

public   int   getFileSize  (  )  { 
return   fileSize  ; 
} 

public   FileFormat   getFileFormat  (  )  { 
return   fileFormat  ; 
} 

public   int   getHeight  (  )  { 
return   height  ; 
} 

public   int   getWidth  (  )  { 
return   width  ; 
} 

public   int   getDuration  (  )  { 
return   duration  ; 
} 

public   int   getChannels  (  )  { 
return   channels  ; 
} 

public   boolean   isStreaming  (  )  { 
return   streaming  ; 
} 

public   URL   getThumbnail  (  )  { 
return   thumbnail  ; 
} 

public   String   getPublisher  (  )  { 
return   publisher  ; 
} 

public   String   getCopyright  (  )  { 
return   copyright  ; 
} 
} 

private   final   class   Parser   extends   DefaultHandler  { 

private   StringBuilder   buffer  ; 

private   ResultsList  <  Result  >  results  ; 

private   Result   result  ; 

public   void   startElement  (  String   uri  ,  String   localName  ,  String   qName  ,  Attributes   attributes  )  throws   SAXException  { 
if  (  "ResultSet"  .  equals  (  qName  )  )  { 
results  =  new   ResultsArrayList  <  Result  >  (  YahooVideoSearch  .  this  ,  Integer  .  parseInt  (  attributes  .  getValue  (  "totalResultsAvailable"  )  )  ,  Integer  .  parseInt  (  attributes  .  getValue  (  "firstResultPosition"  )  )  )  ; 
}  else   if  (  "Result"  .  equals  (  qName  )  )  { 
result  =  new   Result  (  )  ; 
results  .  add  (  result  )  ; 
}  else  { 
buffer  =  new   StringBuilder  (  )  ; 
} 
} 

public   void   endElement  (  String   uri  ,  String   localName  ,  String   qName  )  throws   SAXException  { 
try  { 
if  (  "Title"  .  equals  (  qName  )  )  { 
result  .  title  =  buffer  .  toString  (  )  ; 
}  else   if  (  "Summary"  .  equals  (  qName  )  )  { 
result  .  summary  =  buffer  .  toString  (  )  ; 
}  else   if  (  "Url"  .  equals  (  qName  )  )  { 
result  .  url  =  new   URL  (  buffer  .  toString  (  )  )  ; 
}  else   if  (  "ClickUrl"  .  equals  (  qName  )  )  { 
result  .  clickUrl  =  new   URL  (  buffer  .  toString  (  )  )  ; 
}  else   if  (  "RefererUrl"  .  equals  (  qName  )  )  { 
result  .  refererUrl  =  new   URL  (  buffer  .  toString  (  )  )  ; 
}  else   if  (  "FileSize"  .  equals  (  qName  )  )  { 
result  .  fileSize  =  Integer  .  parseInt  (  buffer  .  toString  (  )  )  ; 
}  else   if  (  "FileFormat"  .  equals  (  qName  )  )  { 
String   format  =  buffer  .  toString  (  )  ; 
if  (  "avi"  .  equals  (  format  )  )  { 
result  .  fileFormat  =  FileFormat  .  AVI  ; 
}  else   if  (  "flash"  .  equals  (  format  )  )  { 
result  .  fileFormat  =  FileFormat  .  FLASH  ; 
}  else   if  (  "mpeg"  .  equals  (  format  )  )  { 
result  .  fileFormat  =  FileFormat  .  MPEG  ; 
}  else   if  (  "msmedia"  .  equals  (  format  )  )  { 
result  .  fileFormat  =  FileFormat  .  MSMEDIA  ; 
}  else   if  (  "quicktime"  .  equals  (  format  )  )  { 
result  .  fileFormat  =  FileFormat  .  QUICKTIME  ; 
}  else   if  (  "realmedia"  .  equals  (  format  )  )  { 
result  .  fileFormat  =  FileFormat  .  REALMEDIA  ; 
} 
}  else   if  (  "Height"  .  equals  (  qName  )  )  { 
result  .  height  =  Integer  .  parseInt  (  buffer  .  toString  (  )  )  ; 
}  else   if  (  "Width"  .  equals  (  qName  )  )  { 
result  .  width  =  Integer  .  parseInt  (  buffer  .  toString  (  )  )  ; 
}  else   if  (  "Duration"  .  equals  (  qName  )  )  { 
}  else   if  (  "Channels"  .  equals  (  qName  )  )  { 
}  else   if  (  "Streaming"  .  equals  (  qName  )  )  { 
result  .  streaming  =  Boolean  .  parseBoolean  (  buffer  .  toString  (  )  )  ; 
}  else   if  (  "Thumbnail"  .  equals  (  qName  )  )  { 
}  else   if  (  "Publisher"  .  equals  (  qName  )  )  { 
result  .  publisher  =  buffer  .  toString  (  )  ; 
}  else   if  (  "Restrictions"  .  equals  (  qName  )  )  { 
System  .  out  .  println  (  buffer  .  toString  (  )  )  ; 
}  else   if  (  "Copyright"  .  equals  (  qName  )  )  { 
result  .  copyright  =  buffer  .  toString  (  )  ; 
} 
}  catch  (  Exception   e  )  { 
e  .  printStackTrace  (  )  ; 
} 
} 

public   void   characters  (  char  [  ]  chars  ,  int   start  ,  int   offset  )  throws   SAXException  { 
if  (  buffer  !=  null  )  { 
buffer  .  append  (  chars  ,  start  ,  offset  )  ; 
}  else  { 
} 
} 
} 
} 

