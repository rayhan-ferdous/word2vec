import   gnu  .  getopt  .  Getopt  ; 
import   gnu  .  getopt  .  LongOpt  ; 
import   java  .  io  .  BufferedInputStream  ; 
import   java  .  io  .  EOFException  ; 
import   java  .  io  .  File  ; 
import   java  .  io  .  FileInputStream  ; 
import   java  .  io  .  IOException  ; 
import   java  .  io  .  InputStream  ; 
import   java  .  io  .  OutputStream  ; 
import   java  .  io  .  StringWriter  ; 
import   java  .  net  .  Socket  ; 
import   java  .  security  .  GeneralSecurityException  ; 
import   java  .  text  .  MessageFormat  ; 
import   java  .  util  .  Enumeration  ; 
import   java  .  util  .  Iterator  ; 
import   java  .  util  .  LinkedHashMap  ; 
import   java  .  util  .  LinkedList  ; 
import   java  .  util  .  List  ; 
import   java  .  util  .  Locale  ; 
import   java  .  util  .  Random  ; 
import   java  .  util  .  ResourceBundle  ; 
import   java  .  util  .  SortedMap  ; 
import   java  .  util  .  StringTokenizer  ; 
import   java  .  util  .  TreeMap  ; 
import   java  .  util  .  Vector  ; 
import   org  .  apache  .  log4j  .  Logger  ; 
import   org  .  dcm4che  .  data  .  Command  ; 
import   org  .  dcm4che  .  data  .  Dataset  ; 
import   org  .  dcm4che  .  data  .  DcmDecodeParam  ; 
import   org  .  dcm4che  .  data  .  DcmEncodeParam  ; 
import   org  .  dcm4che  .  data  .  DcmObjectFactory  ; 
import   org  .  dcm4che  .  data  .  DcmParseException  ; 
import   org  .  dcm4che  .  data  .  DcmParser  ; 
import   org  .  dcm4che  .  data  .  DcmParserFactory  ; 
import   org  .  dcm4che  .  data  .  FileFormat  ; 
import   org  .  dcm4che  .  dict  .  DictionaryFactory  ; 
import   org  .  dcm4che  .  dict  .  Tags  ; 
import   org  .  dcm4che  .  dict  .  UIDDictionary  ; 
import   org  .  dcm4che  .  dict  .  UIDs  ; 
import   org  .  dcm4che  .  dict  .  VRs  ; 
import   org  .  dcm4che  .  net  .  AAssociateAC  ; 
import   org  .  dcm4che  .  net  .  AAssociateRQ  ; 
import   org  .  dcm4che  .  net  .  ActiveAssociation  ; 
import   org  .  dcm4che  .  net  .  Association  ; 
import   org  .  dcm4che  .  net  .  AssociationFactory  ; 
import   org  .  dcm4che  .  net  .  DataSource  ; 
import   org  .  dcm4che  .  net  .  PDU  ; 
import   org  .  dcm4che  .  net  .  PresContext  ; 
import   org  .  dcm4che  .  util  .  DcmURL  ; 
import   org  .  dcm4che  .  util  .  SSLContextAdapter  ; 
import   org  .  dcm4che  .  util  .  UIDGenerator  ; 





public   class   DcmGen  { 

private   static   final   String  [  ]  DEF_TS  =  {  UIDs  .  ImplicitVRLittleEndian  }  ; 

private   static   final   int   PCID_ECHO  =  1  ; 

static   final   Logger   log  =  Logger  .  getLogger  (  "DcmGen"  )  ; 

private   static   ResourceBundle   messages  =  ResourceBundle  .  getBundle  (  "DcmGen"  ,  Locale  .  getDefault  (  )  )  ; 

private   static   final   UIDDictionary   uidDict  =  DictionaryFactory  .  getInstance  (  )  .  getDefaultUIDDictionary  (  )  ; 

private   static   final   AssociationFactory   aFact  =  AssociationFactory  .  getInstance  (  )  ; 

private   static   final   DcmObjectFactory   oFact  =  DcmObjectFactory  .  getInstance  (  )  ; 

private   static   final   DcmParserFactory   pFact  =  DcmParserFactory  .  getInstance  (  )  ; 

private   static   final   int   ECHO  =  0  ; 

private   static   final   int   SEND  =  1  ; 

private   final   int   mode  ; 

private   DcmURL   url  =  null  ; 

private   int   repeatSingle  =  1  ; 

private   int   repeatWhole  =  1  ; 

private   int   priority  =  Command  .  MEDIUM  ; 

private   int   acTimeout  =  5000  ; 

private   int   dimseTimeout  =  0  ; 

private   int   soCloseDelay  =  500  ; 

private   String   uidSuffix  =  null  ; 

private   AAssociateRQ   assocRQ  =  aFact  .  newAAssociateRQ  (  )  ; 

private   boolean   packPDVs  =  false  ; 

private   boolean   truncPostPixelData  =  false  ; 

private   int   bufferSize  =  2048  ; 

private   byte  [  ]  buffer  =  null  ; 

private   SSLContextAdapter   tls  =  null  ; 

private   String  [  ]  cipherSuites  =  null  ; 

private   ActiveAssociation   activeAssociation  =  null  ; 

private   int   sentCount  =  0  ; 

private   long   sentBytes  =  0L  ; 

private   boolean   excludePrivate  =  false  ; 

private   boolean   random  =  false  ; 

private   boolean   complete  =  false  ; 

private   UIDGenerator   uidGen  =  UIDGenerator  .  getInstance  (  )  ; 

private   Random   rnd  =  new   Random  (  )  ; 

private   Dataset  [  ]  datasets  =  null  ; 

private   Dataset   overwrite  =  DcmObjectFactory  .  getInstance  (  )  .  newDataset  (  )  ; 

private   static   final   LongOpt  [  ]  LONG_OPTS  =  new   LongOpt  [  ]  {  new   LongOpt  (  "ac-timeout"  ,  LongOpt  .  REQUIRED_ARGUMENT  ,  null  ,  2  )  ,  new   LongOpt  (  "dimse-timeout"  ,  LongOpt  .  REQUIRED_ARGUMENT  ,  null  ,  2  )  ,  new   LongOpt  (  "so-close-delay"  ,  LongOpt  .  REQUIRED_ARGUMENT  ,  null  ,  2  )  ,  new   LongOpt  (  "prior-high"  ,  LongOpt  .  NO_ARGUMENT  ,  null  ,  'P'  )  ,  new   LongOpt  (  "prior-low"  ,  LongOpt  .  NO_ARGUMENT  ,  null  ,  'p'  )  ,  new   LongOpt  (  "max-pdu-len"  ,  LongOpt  .  REQUIRED_ARGUMENT  ,  null  ,  2  )  ,  new   LongOpt  (  "max-op-invoked"  ,  LongOpt  .  REQUIRED_ARGUMENT  ,  null  ,  2  )  ,  new   LongOpt  (  "pack-pdvs"  ,  LongOpt  .  NO_ARGUMENT  ,  null  ,  'k'  )  ,  new   LongOpt  (  "trunc-post-pixeldata"  ,  LongOpt  .  NO_ARGUMENT  ,  null  ,  't'  )  ,  new   LongOpt  (  "exclude-private"  ,  LongOpt  .  NO_ARGUMENT  ,  null  ,  'x'  )  ,  new   LongOpt  (  "buf-len"  ,  LongOpt  .  REQUIRED_ARGUMENT  ,  null  ,  2  )  ,  new   LongOpt  (  "set"  ,  LongOpt  .  REQUIRED_ARGUMENT  ,  null  ,  's'  )  ,  new   LongOpt  (  "tls-key"  ,  LongOpt  .  REQUIRED_ARGUMENT  ,  null  ,  2  )  ,  new   LongOpt  (  "tls-key-passwd"  ,  LongOpt  .  REQUIRED_ARGUMENT  ,  null  ,  2  )  ,  new   LongOpt  (  "tls-cacerts"  ,  LongOpt  .  REQUIRED_ARGUMENT  ,  null  ,  2  )  ,  new   LongOpt  (  "tls-cacerts-passwd"  ,  LongOpt  .  REQUIRED_ARGUMENT  ,  null  ,  2  )  ,  new   LongOpt  (  "repeat-dimse"  ,  LongOpt  .  REQUIRED_ARGUMENT  ,  null  ,  2  )  ,  new   LongOpt  (  "repeat-assoc"  ,  LongOpt  .  REQUIRED_ARGUMENT  ,  null  ,  2  )  ,  new   LongOpt  (  "uid-suffix"  ,  LongOpt  .  REQUIRED_ARGUMENT  ,  null  ,  2  )  ,  new   LongOpt  (  "help"  ,  LongOpt  .  NO_ARGUMENT  ,  null  ,  'h'  )  ,  new   LongOpt  (  "version"  ,  LongOpt  .  NO_ARGUMENT  ,  null  ,  'v'  )  ,  new   LongOpt  (  "set-random"  ,  LongOpt  .  NO_ARGUMENT  ,  null  ,  'r'  )  ,  new   LongOpt  (  "set-complete"  ,  LongOpt  .  NO_ARGUMENT  ,  null  ,  'c'  )  }  ; 

private   static   void   set  (  Configuration   cfg  ,  String   s  )  { 
int   pos  =  s  .  indexOf  (  ':'  )  ; 
if  (  !  Character  .  isDigit  (  s  .  charAt  (  4  )  )  )  { 
if  (  pos  ==  -  1  )  cfg  .  put  (  "set.0."  +  s  ,  ""  )  ;  else   cfg  .  put  (  "set.0."  +  s  .  substring  (  0  ,  pos  )  ,  s  .  substring  (  pos  +  1  )  )  ; 
}  else  { 
if  (  pos  ==  -  1  )  cfg  .  put  (  "set."  +  s  ,  ""  )  ;  else   cfg  .  put  (  "set."  +  s  .  substring  (  0  ,  pos  )  ,  s  .  substring  (  pos  +  1  )  )  ; 
} 
} 

public   static   void   main  (  String   args  [  ]  )  throws   Exception  { 
Getopt   g  =  new   Getopt  (  "DcmGen"  ,  args  ,  ""  ,  LONG_OPTS  )  ; 
Configuration   cfg  =  new   Configuration  (  DcmGen  .  class  .  getResource  (  "dcmgen.cfg"  )  )  ; 
int   c  ; 
while  (  (  c  =  g  .  getopt  (  )  )  !=  -  1  )  { 
switch  (  c  )  { 
case   2  : 
cfg  .  put  (  LONG_OPTS  [  g  .  getLongind  (  )  ]  .  getName  (  )  ,  g  .  getOptarg  (  )  )  ; 
break  ; 
case  'P'  : 
cfg  .  put  (  "prior"  ,  "1"  )  ; 
break  ; 
case  'p'  : 
cfg  .  put  (  "prior"  ,  "2"  )  ; 
break  ; 
case  'k'  : 
cfg  .  put  (  "pack-pdvs"  ,  "true"  )  ; 
break  ; 
case  't'  : 
cfg  .  put  (  "trunc-post-pixeldata"  ,  "true"  )  ; 
break  ; 
case  'x'  : 
cfg  .  put  (  "exclude-private"  ,  "true"  )  ; 
break  ; 
case  's'  : 
set  (  cfg  ,  g  .  getOptarg  (  )  )  ; 
break  ; 
case  'r'  : 
cfg  .  put  (  "set-random"  ,  "true"  )  ; 
break  ; 
case  'c'  : 
cfg  .  put  (  "set-complete"  ,  "true"  )  ; 
break  ; 
case  'v'  : 
exit  (  messages  .  getString  (  "version"  )  ,  false  )  ; 
case  'h'  : 
exit  (  messages  .  getString  (  "usage"  )  ,  false  )  ; 
case  '?'  : 
exit  (  null  ,  true  )  ; 
break  ; 
} 
} 
int   optind  =  g  .  getOptind  (  )  ; 
int   argc  =  args  .  length  -  optind  ; 
if  (  argc  ==  0  )  { 
exit  (  messages  .  getString  (  "missing"  )  ,  true  )  ; 
} 
try  { 
DcmGen   DcmGen  =  new   DcmGen  (  cfg  ,  new   DcmURL  (  args  [  optind  ]  )  ,  argc  )  ; 
DcmGen  .  execute  (  args  ,  optind  +  1  )  ; 
}  catch  (  IllegalArgumentException   e  )  { 
exit  (  e  .  getMessage  (  )  ,  true  )  ; 
} 
} 

DcmGen  (  Configuration   cfg  ,  DcmURL   url  ,  int   argc  )  { 
this  .  url  =  url  ; 
this  .  priority  =  Integer  .  parseInt  (  cfg  .  getProperty  (  "prior"  ,  "0"  )  )  ; 
this  .  packPDVs  =  "true"  .  equalsIgnoreCase  (  cfg  .  getProperty  (  "pack-pdvs"  ,  "false"  )  )  ; 
this  .  truncPostPixelData  =  "true"  .  equalsIgnoreCase  (  cfg  .  getProperty  (  "trunc-post-pixeldata"  ,  "false"  )  )  ; 
this  .  excludePrivate  =  "true"  .  equalsIgnoreCase  (  cfg  .  getProperty  (  "exclude-private"  ,  "false"  )  )  ; 
this  .  bufferSize  =  Integer  .  parseInt  (  cfg  .  getProperty  (  "buf-len"  ,  "2048"  )  )  &  0xfffffffe  ; 
this  .  repeatWhole  =  Integer  .  parseInt  (  cfg  .  getProperty  (  "repeat-assoc"  ,  "1"  )  )  ; 
this  .  repeatSingle  =  Integer  .  parseInt  (  cfg  .  getProperty  (  "repeat-dimse"  ,  "1"  )  )  ; 
this  .  uidSuffix  =  cfg  .  getProperty  (  "uid-suffix"  )  ; 
this  .  mode  =  argc  >  1  ?  SEND  :  ECHO  ; 
this  .  random  =  "true"  .  equalsIgnoreCase  (  cfg  .  getProperty  (  "set-random"  ,  "false"  )  )  ; 
this  .  complete  =  "true"  .  equalsIgnoreCase  (  cfg  .  getProperty  (  "set-complete"  ,  "false"  )  )  ; 
initAssocParam  (  cfg  ,  url  ,  mode  ==  ECHO  )  ; 
initTLS  (  cfg  )  ; 
initOverwrite  (  cfg  )  ; 
} 

public   void   execute  (  String  [  ]  args  ,  int   offset  )  throws   InterruptedException  ,  IOException  ,  GeneralSecurityException  { 
switch  (  mode  )  { 
case   ECHO  : 
echo  (  )  ; 
break  ; 
case   SEND  : 
send  (  args  ,  offset  )  ; 
break  ; 
default  : 
throw   new   RuntimeException  (  "Illegal mode: "  +  mode  )  ; 
} 
} 

private   ActiveAssociation   openAssoc  (  )  throws   IOException  ,  GeneralSecurityException  { 
Association   assoc  =  aFact  .  newRequestor  (  newSocket  (  url  .  getHost  (  )  ,  url  .  getPort  (  )  )  )  ; 
assoc  .  setAcTimeout  (  acTimeout  )  ; 
assoc  .  setDimseTimeout  (  dimseTimeout  )  ; 
assoc  .  setSoCloseDelay  (  soCloseDelay  )  ; 
assoc  .  setPackPDVs  (  packPDVs  )  ; 
PDU   assocAC  =  assoc  .  connect  (  assocRQ  )  ; 
if  (  !  (  assocAC   instanceof   AAssociateAC  )  )  { 
return   null  ; 
} 
ActiveAssociation   retval  =  aFact  .  newActiveAssociation  (  assoc  ,  null  )  ; 
retval  .  start  (  )  ; 
return   retval  ; 
} 

public   void   echo  (  )  throws   InterruptedException  ,  IOException  ,  GeneralSecurityException  { 
long   t1  =  System  .  currentTimeMillis  (  )  ; 
int   count  =  0  ; 
for  (  int   i  =  0  ;  i  <  repeatWhole  ;  ++  i  )  { 
ActiveAssociation   active  =  openAssoc  (  )  ; 
if  (  active  !=  null  )  { 
if  (  active  .  getAssociation  (  )  .  getAcceptedTransferSyntaxUID  (  PCID_ECHO  )  ==  null  )  { 
log  .  error  (  messages  .  getString  (  "noPCEcho"  )  )  ; 
}  else   for  (  int   j  =  0  ;  j  <  repeatSingle  ;  ++  j  ,  ++  count  )  { 
active  .  invoke  (  aFact  .  newDimse  (  PCID_ECHO  ,  oFact  .  newCommand  (  )  .  initCEchoRQ  (  j  )  )  ,  null  )  ; 
} 
active  .  release  (  true  )  ; 
} 
} 
long   dt  =  System  .  currentTimeMillis  (  )  -  t1  ; 
log  .  info  (  MessageFormat  .  format  (  messages  .  getString  (  "echoDone"  )  ,  new   Object  [  ]  {  new   Integer  (  count  )  ,  new   Long  (  dt  )  }  )  )  ; 
} 

public   void   send  (  String  [  ]  args  ,  int   offset  )  throws   InterruptedException  ,  IOException  ,  GeneralSecurityException  { 
if  (  bufferSize  >  0  )  { 
buffer  =  new   byte  [  bufferSize  ]  ; 
} 
long   t1  =  System  .  currentTimeMillis  (  )  ; 
for  (  int   i  =  0  ;  i  <  repeatWhole  ;  ++  i  )  { 
ActiveAssociation   active  =  openAssoc  (  )  ; 
if  (  active  !=  null  )  { 
for  (  int   k  =  offset  ;  k  <  args  .  length  ;  ++  k  )  { 
Dataset   ds  =  null  ; 
if  (  random  )  { 
ds  =  datasets  [  rnd  .  nextInt  (  datasets  .  length  )  ]  ; 
if  (  ds  ==  null  )  ds  =  overwrite  ; 
ds  .  putUI  (  Tags  .  StudyInstanceUID  ,  uidGen  .  createUID  (  )  )  ; 
ds  .  putUI  (  Tags  .  SeriesInstanceUID  ,  uidGen  .  createUID  (  )  )  ; 
}  else   if  (  complete  )  { 
ds  =  datasets  [  (  k  -  offset  )  %  datasets  .  length  ]  ; 
if  (  ds  ==  null  )  ds  =  overwrite  ; 
}  else   if  (  datasets  .  length  >  (  k  -  offset  )  &&  datasets  [  (  k  -  offset  )  ]  !=  null  )  { 
ds  =  datasets  [  (  k  -  offset  )  ]  ; 
} 
if  (  ds  !=  null  )  { 
log  .  info  (  "Used dataset for FILE/DIR "  +  args  [  k  ]  +  " :"  )  ; 
logDataset  (  ds  )  ; 
} 
send  (  active  ,  new   File  (  args  [  k  ]  )  ,  ds  )  ; 
} 
active  .  release  (  true  )  ; 
} 
} 
long   dt  =  System  .  currentTimeMillis  (  )  -  t1  ; 
log  .  info  (  MessageFormat  .  format  (  messages  .  getString  (  "sendDone"  )  ,  new   Object  [  ]  {  new   Integer  (  sentCount  )  ,  new   Long  (  sentBytes  )  ,  new   Long  (  dt  )  ,  new   Float  (  sentBytes  /  (  1.024f  *  dt  )  )  }  )  )  ; 
} 

private   void   send  (  ActiveAssociation   active  ,  File   file  ,  Dataset   ds  )  throws   InterruptedException  ,  IOException  { 
if  (  !  file  .  isDirectory  (  )  )  { 
for  (  int   i  =  0  ;  i  <  repeatSingle  ;  ++  i  )  { 
if  (  ds  !=  null  &&  random  )  ds  .  putUI  (  Tags  .  SOPInstanceUID  ,  uidGen  .  createUID  (  )  )  ; 
sendFile  (  active  ,  file  ,  ds  )  ; 
} 
return  ; 
} 
File  [  ]  list  =  file  .  listFiles  (  )  ; 
for  (  int   i  =  0  ;  i  <  list  .  length  ;  ++  i  )  { 
send  (  active  ,  list  [  i  ]  ,  ds  )  ; 
} 
} 

private   boolean   sendFile  (  ActiveAssociation   active  ,  File   file  ,  Dataset   mergeDataset  )  throws   InterruptedException  ,  IOException  { 
InputStream   in  =  null  ; 
DcmParser   parser  =  null  ; 
Dataset   ds  =  null  ; 
try  { 
try  { 
in  =  new   BufferedInputStream  (  new   FileInputStream  (  file  )  )  ; 
parser  =  pFact  .  newDcmParser  (  in  )  ; 
try  { 
FileFormat   format  =  parser  .  detectFileFormat  (  )  ; 
ds  =  oFact  .  newDataset  (  )  ; 
parser  .  setDcmHandler  (  ds  .  getDcmHandler  (  )  )  ; 
parser  .  parseDcmFile  (  format  ,  Tags  .  PixelData  )  ; 
if  (  parser  .  getReadTag  (  )  ==  Tags  .  PixelData  )  { 
if  (  parser  .  getStreamPosition  (  )  +  parser  .  getReadLength  (  )  >  file  .  length  (  )  )  { 
throw   new   EOFException  (  "Pixel Data Length: "  +  parser  .  getReadLength  (  )  +  " exceeds file length: "  +  file  .  length  (  )  )  ; 
} 
} 
log  .  info  (  MessageFormat  .  format  (  messages  .  getString  (  "readDone"  )  ,  new   Object  [  ]  {  file  }  )  )  ; 
}  catch  (  DcmParseException   e  )  { 
log  .  error  (  MessageFormat  .  format  (  messages  .  getString  (  "failformat"  )  ,  new   Object  [  ]  {  file  }  )  )  ; 
return   false  ; 
} 
}  catch  (  IOException   e  )  { 
log  .  error  (  MessageFormat  .  format  (  messages  .  getString  (  "failread"  )  ,  new   Object  [  ]  {  file  ,  e  }  )  )  ; 
return   false  ; 
} 
sendDataset  (  active  ,  file  ,  parser  ,  merge  (  ds  ,  mergeDataset  )  )  ; 
return   true  ; 
}  finally  { 
if  (  in  !=  null  )  { 
try  { 
in  .  close  (  )  ; 
}  catch  (  IOException   ignore  )  { 
} 
; 
} 
} 
} 

private   boolean   sendDataset  (  ActiveAssociation   active  ,  File   file  ,  DcmParser   parser  ,  Dataset   ds  )  throws   InterruptedException  ,  IOException  { 
if  (  uidSuffix  !=  null  &&  uidSuffix  .  length  (  )  >  0  )  { 
applyUIDSuffix  (  ds  )  ; 
} 
String   sopInstUID  =  ds  .  getString  (  Tags  .  SOPInstanceUID  )  ; 
if  (  sopInstUID  ==  null  )  { 
log  .  error  (  MessageFormat  .  format  (  messages  .  getString  (  "noSOPinst"  )  ,  new   Object  [  ]  {  file  }  )  )  ; 
return   false  ; 
} 
String   sopClassUID  =  ds  .  getString  (  Tags  .  SOPClassUID  )  ; 
if  (  sopClassUID  ==  null  )  { 
log  .  error  (  MessageFormat  .  format  (  messages  .  getString  (  "noSOPclass"  )  ,  new   Object  [  ]  {  file  }  )  )  ; 
return   false  ; 
} 
PresContext   pc  =  null  ; 
Association   assoc  =  active  .  getAssociation  (  )  ; 
if  (  parser  .  getDcmDecodeParam  (  )  .  encapsulated  )  { 
String   tsuid  =  ds  .  getFileMetaInfo  (  )  .  getTransferSyntaxUID  (  )  ; 
if  (  (  pc  =  assoc  .  getAcceptedPresContext  (  sopClassUID  ,  tsuid  )  )  ==  null  )  { 
log  .  error  (  MessageFormat  .  format  (  messages  .  getString  (  "noPCStore3"  )  ,  new   Object  [  ]  {  uidDict  .  lookup  (  sopClassUID  )  ,  uidDict  .  lookup  (  tsuid  )  ,  file  }  )  )  ; 
return   false  ; 
} 
}  else   if  (  (  pc  =  assoc  .  getAcceptedPresContext  (  sopClassUID  ,  UIDs  .  ImplicitVRLittleEndian  )  )  ==  null  &&  (  pc  =  assoc  .  getAcceptedPresContext  (  sopClassUID  ,  UIDs  .  ExplicitVRLittleEndian  )  )  ==  null  &&  (  pc  =  assoc  .  getAcceptedPresContext  (  sopClassUID  ,  UIDs  .  ExplicitVRBigEndian  )  )  ==  null  )  { 
log  .  error  (  MessageFormat  .  format  (  messages  .  getString  (  "noPCStore2"  )  ,  new   Object  [  ]  {  uidDict  .  lookup  (  sopClassUID  )  ,  file  }  )  )  ; 
return   false  ; 
} 
active  .  invoke  (  aFact  .  newDimse  (  pc  .  pcid  (  )  ,  oFact  .  newCommand  (  )  .  initCStoreRQ  (  assoc  .  nextMsgID  (  )  ,  sopClassUID  ,  sopInstUID  ,  priority  )  ,  new   MyDataSource  (  parser  ,  ds  ,  buffer  )  )  ,  null  )  ; 
sentBytes  +=  parser  .  getStreamPosition  (  )  ; 
++  sentCount  ; 
return   true  ; 
} 

private   void   applyUIDSuffix  (  Dataset   ds  )  { 
ds  .  putUI  (  Tags  .  StudyInstanceUID  ,  ds  .  getString  (  Tags  .  StudyInstanceUID  ,  ""  )  +  uidSuffix  )  ; 
ds  .  putUI  (  Tags  .  SeriesInstanceUID  ,  ds  .  getString  (  Tags  .  SeriesInstanceUID  ,  ""  )  +  uidSuffix  )  ; 
ds  .  putUI  (  Tags  .  SOPInstanceUID  ,  ds  .  getString  (  Tags  .  SOPInstanceUID  ,  ""  )  +  uidSuffix  )  ; 
} 

private   Dataset   merge  (  Dataset   ds  ,  Dataset   merge  )  { 
if  (  !  complete  &&  !  random  &&  datasets  .  length  >  0  )  merge  =  datasets  [  0  ]  ; 
if  (  ds  ==  null  )  { 
return   merge  ; 
} 
if  (  merge  ==  null  )  { 
if  (  datasets  .  length  ==  1  )  merge  =  datasets  [  0  ]  ;  else   return   ds  ; 
} 
ds  .  putAll  (  merge  ,  Dataset  .  MERGE_ITEMS  )  ; 
return   ds  ; 
} 

private   final   class   MyDataSource   implements   DataSource  { 

final   DcmParser   parser  ; 

final   Dataset   ds  ; 

final   byte  [  ]  buffer  ; 

MyDataSource  (  DcmParser   parser  ,  Dataset   ds  ,  byte  [  ]  buffer  )  { 
this  .  parser  =  parser  ; 
this  .  ds  =  ds  ; 
this  .  buffer  =  buffer  ; 
} 

public   void   writeTo  (  OutputStream   out  ,  String   tsUID  )  throws   IOException  { 
DcmEncodeParam   netParam  =  (  DcmEncodeParam  )  DcmDecodeParam  .  valueOf  (  tsUID  )  ; 
if  (  excludePrivate  )  ds  .  excludePrivate  (  )  .  writeDataset  (  out  ,  netParam  )  ;  else   ds  .  writeDataset  (  out  ,  netParam  )  ; 
if  (  parser  .  getReadTag  (  )  ==  Tags  .  PixelData  )  { 
DcmDecodeParam   fileParam  =  parser  .  getDcmDecodeParam  (  )  ; 
ds  .  writeHeader  (  out  ,  netParam  ,  parser  .  getReadTag  (  )  ,  parser  .  getReadVR  (  )  ,  parser  .  getReadLength  (  )  )  ; 
if  (  netParam  .  encapsulated  )  { 
parser  .  parseHeader  (  )  ; 
while  (  parser  .  getReadTag  (  )  ==  Tags  .  Item  )  { 
ds  .  writeHeader  (  out  ,  netParam  ,  parser  .  getReadTag  (  )  ,  parser  .  getReadVR  (  )  ,  parser  .  getReadLength  (  )  )  ; 
writeValueTo  (  out  ,  false  )  ; 
parser  .  parseHeader  (  )  ; 
} 
if  (  parser  .  getReadTag  (  )  !=  Tags  .  SeqDelimitationItem  )  { 
throw   new   DcmParseException  (  "Unexpected Tag:"  +  Tags  .  toString  (  parser  .  getReadTag  (  )  )  )  ; 
} 
if  (  parser  .  getReadLength  (  )  !=  0  )  { 
throw   new   DcmParseException  (  "(fffe,e0dd), Length:"  +  parser  .  getReadLength  (  )  )  ; 
} 
ds  .  writeHeader  (  out  ,  netParam  ,  Tags  .  SeqDelimitationItem  ,  VRs  .  NONE  ,  0  )  ; 
}  else  { 
boolean   swap  =  fileParam  .  byteOrder  !=  netParam  .  byteOrder  &&  parser  .  getReadVR  (  )  ==  VRs  .  OW  ; 
writeValueTo  (  out  ,  swap  )  ; 
} 
if  (  truncPostPixelData  )  { 
return  ; 
} 
ds  .  clear  (  )  ; 
try  { 
parser  .  parseDataset  (  fileParam  ,  -  1  )  ; 
}  catch  (  IOException   e  )  { 
log  .  warn  (  "Error reading post-pixeldata attributes:"  ,  e  )  ; 
} 
if  (  excludePrivate  )  ds  .  excludePrivate  (  )  .  writeDataset  (  out  ,  netParam  )  ;  else   ds  .  writeDataset  (  out  ,  netParam  )  ; 
} 
} 

private   void   writeValueTo  (  OutputStream   out  ,  boolean   swap  )  throws   IOException  { 
InputStream   in  =  parser  .  getInputStream  (  )  ; 
int   len  =  parser  .  getReadLength  (  )  ; 
if  (  swap  &&  (  len  &  1  )  !=  0  )  { 
throw   new   DcmParseException  (  "Illegal length of OW Pixel Data: "  +  len  )  ; 
} 
if  (  buffer  ==  null  )  { 
if  (  swap  )  { 
int   tmp  ; 
for  (  int   i  =  0  ;  i  <  len  ;  ++  i  ,  ++  i  )  { 
tmp  =  in  .  read  (  )  ; 
out  .  write  (  in  .  read  (  )  )  ; 
out  .  write  (  tmp  )  ; 
} 
}  else  { 
for  (  int   i  =  0  ;  i  <  len  ;  ++  i  )  { 
out  .  write  (  in  .  read  (  )  )  ; 
} 
} 
}  else  { 
byte   tmp  ; 
int   c  ,  remain  =  len  ; 
while  (  remain  >  0  )  { 
c  =  in  .  read  (  buffer  ,  0  ,  Math  .  min  (  buffer  .  length  ,  remain  )  )  ; 
if  (  c  ==  -  1  )  { 
throw   new   EOFException  (  "EOF during read of pixel data"  )  ; 
} 
if  (  swap  )  { 
if  (  (  c  &  1  )  !=  0  )  { 
buffer  [  c  ++  ]  =  (  byte  )  in  .  read  (  )  ; 
} 
for  (  int   i  =  0  ;  i  <  c  ;  ++  i  ,  ++  i  )  { 
tmp  =  buffer  [  i  ]  ; 
buffer  [  i  ]  =  buffer  [  i  +  1  ]  ; 
buffer  [  i  +  1  ]  =  tmp  ; 
} 
} 
out  .  write  (  buffer  ,  0  ,  c  )  ; 
remain  -=  c  ; 
} 
} 
parser  .  setStreamPosition  (  parser  .  getStreamPosition  (  )  +  len  )  ; 
} 
} 

private   Socket   newSocket  (  String   host  ,  int   port  )  throws   IOException  ,  GeneralSecurityException  { 
if  (  cipherSuites  !=  null  )  { 
return   tls  .  getSocketFactory  (  cipherSuites  )  .  createSocket  (  host  ,  port  )  ; 
}  else  { 
return   new   Socket  (  host  ,  port  )  ; 
} 
} 

private   static   void   exit  (  String   prompt  ,  boolean   error  )  { 
if  (  prompt  !=  null  )  System  .  err  .  println  (  prompt  )  ; 
if  (  error  )  System  .  err  .  println  (  messages  .  getString  (  "try"  )  )  ; 
System  .  exit  (  1  )  ; 
} 

private   static   String   maskNull  (  String   aet  )  { 
return   aet  !=  null  ?  aet  :  "DCMGEN"  ; 
} 

private   final   void   initAssocParam  (  Configuration   cfg  ,  DcmURL   url  ,  boolean   echo  )  { 
acTimeout  =  Integer  .  parseInt  (  cfg  .  getProperty  (  "ac-timeout"  ,  "5000"  )  )  ; 
dimseTimeout  =  Integer  .  parseInt  (  cfg  .  getProperty  (  "dimse-timeout"  ,  "0"  )  )  ; 
soCloseDelay  =  Integer  .  parseInt  (  cfg  .  getProperty  (  "so-close-delay"  ,  "500"  )  )  ; 
assocRQ  .  setCalledAET  (  url  .  getCalledAET  (  )  )  ; 
assocRQ  .  setCallingAET  (  maskNull  (  url  .  getCallingAET  (  )  )  )  ; 
assocRQ  .  setMaxPDULength  (  Integer  .  parseInt  (  cfg  .  getProperty  (  "max-pdu-len"  ,  "16352"  )  )  )  ; 
assocRQ  .  setAsyncOpsWindow  (  aFact  .  newAsyncOpsWindow  (  Integer  .  parseInt  (  cfg  .  getProperty  (  "max-op-invoked"  ,  "0"  )  )  ,  1  )  )  ; 
if  (  echo  )  { 
assocRQ  .  addPresContext  (  aFact  .  newPresContext  (  PCID_ECHO  ,  UIDs  .  Verification  ,  DEF_TS  )  )  ; 
return  ; 
} 
for  (  Enumeration   it  =  cfg  .  keys  (  )  ;  it  .  hasMoreElements  (  )  ;  )  { 
String   key  =  (  String  )  it  .  nextElement  (  )  ; 
if  (  key  .  startsWith  (  "pc."  )  )  { 
initPresContext  (  Integer  .  parseInt  (  key  .  substring  (  3  )  )  ,  cfg  .  tokenize  (  cfg  .  getProperty  (  key  )  ,  new   LinkedList  (  )  )  )  ; 
} 
} 
} 

private   final   void   initPresContext  (  int   pcid  ,  List   val  )  { 
Iterator   it  =  val  .  iterator  (  )  ; 
String   as  =  UIDs  .  forName  (  (  String  )  it  .  next  (  )  )  ; 
String  [  ]  tsUIDs  =  new   String  [  val  .  size  (  )  -  1  ]  ; 
for  (  int   i  =  0  ;  i  <  tsUIDs  .  length  ;  ++  i  )  { 
tsUIDs  [  i  ]  =  UIDs  .  forName  (  (  String  )  it  .  next  (  )  )  ; 
} 
assocRQ  .  addPresContext  (  aFact  .  newPresContext  (  pcid  ,  as  ,  tsUIDs  )  )  ; 
} 

private   void   initOverwrite  (  Configuration   config  )  { 
SortedMap   cfg  =  new   TreeMap  (  config  )  ; 
LinkedHashMap   list  =  new   LinkedHashMap  (  )  ; 
for  (  Iterator   it  =  cfg  .  keySet  (  )  .  iterator  (  )  ;  it  .  hasNext  (  )  ;  )  { 
String   key  =  (  String  )  it  .  next  (  )  ; 
if  (  key  .  startsWith  (  "set."  )  )  { 
String  [  ]  split  =  key  .  split  (  "[.]"  )  ; 
int   subKey  =  Integer  .  parseInt  (  split  [  1  ]  )  ; 
int   tag  =  Tags  .  forName  (  split  [  2  ]  )  ; 
if  (  subKey  ==  0  )  { 
overwrite  .  putXX  (  tag  ,  (  String  )  cfg  .  get  (  key  )  )  ; 
continue  ; 
} 
Dataset   ds  =  (  Dataset  )  list  .  get  (  ""  +  (  subKey  -  1  )  )  ; 
if  (  ds  ==  null  )  { 
ds  =  DcmObjectFactory  .  getInstance  (  )  .  newDataset  (  )  ; 
list  .  put  (  ""  +  (  subKey  -  1  )  ,  ds  )  ; 
} 
try  { 
ds  .  putXX  (  tag  ,  (  String  )  cfg  .  get  (  key  )  )  ; 
}  catch  (  Exception   e  )  { 
throw   new   IllegalArgumentException  (  "Illegal entry in DcmGen.cfg - "  +  key  +  "="  +  cfg  .  get  (  key  )  )  ; 
} 
} 
} 
datasets  =  (  Dataset  [  ]  )  list  .  values  (  )  .  toArray  (  new   Dataset  [  0  ]  )  ; 
} 

private   void   initTLS  (  Configuration   cfg  )  { 
try  { 
cipherSuites  =  url  .  getCipherSuites  (  )  ; 
if  (  cipherSuites  ==  null  )  { 
return  ; 
} 
tls  =  SSLContextAdapter  .  getInstance  (  )  ; 
char  [  ]  keypasswd  =  cfg  .  getProperty  (  "tls-key-passwd"  ,  "secret"  )  .  toCharArray  (  )  ; 
tls  .  setKey  (  tls  .  loadKeyStore  (  DcmGen  .  class  .  getResource  (  cfg  .  getProperty  (  "tls-key"  ,  "identity.p12"  )  )  ,  keypasswd  )  ,  keypasswd  )  ; 
tls  .  setTrust  (  tls  .  loadKeyStore  (  DcmGen  .  class  .  getResource  (  cfg  .  getProperty  (  "tls-cacerts"  ,  "cacerts.jks"  )  )  ,  cfg  .  getProperty  (  "tls-cacerts-passwd"  ,  "secret"  )  .  toCharArray  (  )  )  )  ; 
tls  .  init  (  )  ; 
}  catch  (  Exception   ex  )  { 
throw   new   RuntimeException  (  "Could not initalize TLS configuration: "  ,  ex  )  ; 
} 
} 

private   void   logDataset  (  Dataset   ds  )  { 
try  { 
StringWriter   w  =  new   StringWriter  (  )  ; 
ds  .  dumpDataset  (  w  ,  null  )  ; 
log  .  info  (  w  .  toString  (  )  )  ; 
}  catch  (  Exception   e  )  { 
log  .  warn  (  "Failed to dump dataset"  ,  e  )  ; 
} 
} 
} 

