import   org  .  dcm4che  .  data  .  Command  ; 
import   org  .  dcm4che  .  data  .  Dataset  ; 
import   org  .  dcm4che  .  data  .  FileMetaInfo  ; 
import   org  .  dcm4che  .  data  .  DcmDecodeParam  ; 
import   org  .  dcm4che  .  data  .  DcmElement  ; 
import   org  .  dcm4che  .  data  .  DcmEncodeParam  ; 
import   org  .  dcm4che  .  data  .  DcmObjectFactory  ; 
import   org  .  dcm4che  .  data  .  DcmParser  ; 
import   org  .  dcm4che  .  data  .  DcmParseException  ; 
import   org  .  dcm4che  .  data  .  DcmParserFactory  ; 
import   org  .  dcm4che  .  data  .  FileFormat  ; 
import   org  .  dcm4che  .  dict  .  DictionaryFactory  ; 
import   org  .  dcm4che  .  dict  .  Tags  ; 
import   org  .  dcm4che  .  dict  .  UIDDictionary  ; 
import   org  .  dcm4che  .  dict  .  UIDs  ; 
import   org  .  dcm4che  .  dict  .  VRs  ; 
import   org  .  dcm4che  .  net  .  ActiveAssociation  ; 
import   org  .  dcm4che  .  net  .  Association  ; 
import   org  .  dcm4che  .  net  .  AAssociateAC  ; 
import   org  .  dcm4che  .  net  .  AAssociateRQ  ; 
import   org  .  dcm4che  .  net  .  AssociationFactory  ; 
import   org  .  dcm4che  .  net  .  DataSource  ; 
import   org  .  dcm4che  .  net  .  Dimse  ; 
import   org  .  dcm4che  .  net  .  PDU  ; 
import   org  .  dcm4che  .  net  .  PresContext  ; 
import   org  .  dcm4che  .  server  .  PollDirSrv  ; 
import   org  .  dcm4che  .  server  .  PollDirSrvFactory  ; 
import   org  .  dcm4che  .  util  .  SSLContextAdapter  ; 
import   org  .  dcm4che  .  util  .  DcmURL  ; 
import   java  .  io  .  File  ; 
import   java  .  io  .  InputStream  ; 
import   java  .  io  .  IOException  ; 
import   java  .  io  .  OutputStream  ; 
import   java  .  io  .  BufferedInputStream  ; 
import   java  .  io  .  FileInputStream  ; 
import   java  .  nio  .  ByteOrder  ; 
import   java  .  net  .  Socket  ; 
import   java  .  text  .  MessageFormat  ; 
import   java  .  util  .  Enumeration  ; 
import   java  .  util  .  Iterator  ; 
import   java  .  util  .  LinkedList  ; 
import   java  .  util  .  List  ; 
import   java  .  util  .  Locale  ; 
import   java  .  util  .  ResourceBundle  ; 
import   java  .  security  .  GeneralSecurityException  ; 
import   gnu  .  getopt  .  Getopt  ; 
import   gnu  .  getopt  .  LongOpt  ; 
import   org  .  apache  .  log4j  .  Logger  ; 





public   class   DcmSnd   implements   PollDirSrv  .  Handler  { 

private   static   final   String  [  ]  DEF_TS  =  {  UIDs  .  ImplicitVRLittleEndian  }  ; 

private   static   final   int   PCID_ECHO  =  1  ; 

static   final   Logger   log  =  Logger  .  getLogger  (  "DcmSnd"  )  ; 

private   static   ResourceBundle   messages  =  ResourceBundle  .  getBundle  (  "DcmSnd"  ,  Locale  .  getDefault  (  )  )  ; 

private   static   final   UIDDictionary   uidDict  =  DictionaryFactory  .  getInstance  (  )  .  getDefaultUIDDictionary  (  )  ; 

private   static   final   AssociationFactory   aFact  =  AssociationFactory  .  getInstance  (  )  ; 

private   static   final   DcmObjectFactory   oFact  =  DcmObjectFactory  .  getInstance  (  )  ; 

private   static   final   DcmParserFactory   pFact  =  DcmParserFactory  .  getInstance  (  )  ; 

private   static   final   int   ECHO  =  0  ; 

private   static   final   int   SEND  =  1  ; 

private   static   final   int   POLL  =  2  ; 

private   final   int   mode  ; 

private   DcmURL   url  =  null  ; 

private   int   repeatSingle  =  1  ; 

private   int   repeatWhole  =  1  ; 

private   int   priority  =  Command  .  MEDIUM  ; 

private   int   assocTO  =  0  ; 

private   int   dimseTO  =  0  ; 

private   int   releaseTO  =  0  ; 

private   AAssociateRQ   assocRQ  =  aFact  .  newAAssociateRQ  (  )  ; 

private   int   bufferSize  =  2048  ; 

private   byte  [  ]  buffer  =  null  ; 

private   SSLContextAdapter   tls  =  null  ; 

private   String  [  ]  cipherSuites  =  null  ; 

private   Dataset   overwrite  =  oFact  .  newDataset  (  )  ; 

private   PollDirSrv   pollDirSrv  =  null  ; 

private   File   pollDir  =  null  ; 

private   long   pollPeriod  =  5000L  ; 

private   ActiveAssociation   activeAssociation  =  null  ; 

private   int   sentCount  =  0  ; 

private   long   sentBytes  =  0L  ; 

private   static   final   LongOpt  [  ]  LONG_OPTS  =  new   LongOpt  [  ]  {  new   LongOpt  (  "prior-high"  ,  LongOpt  .  NO_ARGUMENT  ,  null  ,  'P'  )  ,  new   LongOpt  (  "prior-low"  ,  LongOpt  .  NO_ARGUMENT  ,  null  ,  'p'  )  ,  new   LongOpt  (  "max-pdu-len"  ,  LongOpt  .  REQUIRED_ARGUMENT  ,  null  ,  2  )  ,  new   LongOpt  (  "max-op-invoked"  ,  LongOpt  .  REQUIRED_ARGUMENT  ,  null  ,  2  )  ,  new   LongOpt  (  "buf-len"  ,  LongOpt  .  REQUIRED_ARGUMENT  ,  null  ,  2  )  ,  new   LongOpt  (  "set"  ,  LongOpt  .  REQUIRED_ARGUMENT  ,  null  ,  's'  )  ,  new   LongOpt  (  "tls-key"  ,  LongOpt  .  REQUIRED_ARGUMENT  ,  null  ,  2  )  ,  new   LongOpt  (  "tls-key-passwd"  ,  LongOpt  .  REQUIRED_ARGUMENT  ,  null  ,  2  )  ,  new   LongOpt  (  "tls-cacerts"  ,  LongOpt  .  REQUIRED_ARGUMENT  ,  null  ,  2  )  ,  new   LongOpt  (  "tls-cacerts-passwd"  ,  LongOpt  .  REQUIRED_ARGUMENT  ,  null  ,  2  )  ,  new   LongOpt  (  "poll-dir"  ,  LongOpt  .  REQUIRED_ARGUMENT  ,  null  ,  2  )  ,  new   LongOpt  (  "poll-period"  ,  LongOpt  .  REQUIRED_ARGUMENT  ,  null  ,  2  )  ,  new   LongOpt  (  "poll-retry-open"  ,  LongOpt  .  REQUIRED_ARGUMENT  ,  null  ,  2  )  ,  new   LongOpt  (  "poll-delta-last-modified"  ,  LongOpt  .  REQUIRED_ARGUMENT  ,  null  ,  2  )  ,  new   LongOpt  (  "poll-done-dir"  ,  LongOpt  .  REQUIRED_ARGUMENT  ,  null  ,  2  )  ,  new   LongOpt  (  "repeat-dimse"  ,  LongOpt  .  REQUIRED_ARGUMENT  ,  null  ,  2  )  ,  new   LongOpt  (  "repeat-assoc"  ,  LongOpt  .  REQUIRED_ARGUMENT  ,  null  ,  2  )  ,  new   LongOpt  (  "help"  ,  LongOpt  .  NO_ARGUMENT  ,  null  ,  'h'  )  ,  new   LongOpt  (  "version"  ,  LongOpt  .  NO_ARGUMENT  ,  null  ,  'v'  )  }  ; 

private   static   void   set  (  Configuration   cfg  ,  String   s  )  { 
int   pos  =  s  .  indexOf  (  ':'  )  ; 
if  (  pos  ==  -  1  )  { 
cfg  .  put  (  "set."  +  s  ,  ""  )  ; 
}  else  { 
cfg  .  put  (  "set."  +  s  .  substring  (  0  ,  pos  )  ,  s  .  substring  (  pos  +  1  )  )  ; 
} 
} 

public   static   void   main  (  String   args  [  ]  )  throws   Exception  { 
Getopt   g  =  new   Getopt  (  "dcmsnd"  ,  args  ,  ""  ,  LONG_OPTS  )  ; 
Configuration   cfg  =  new   Configuration  (  DcmSnd  .  class  .  getResource  (  "dcmsnd.cfg"  )  )  ; 
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
case  's'  : 
set  (  cfg  ,  g  .  getOptarg  (  )  )  ; 
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
DcmSnd   dcmsnd  =  new   DcmSnd  (  cfg  ,  new   DcmURL  (  args  [  optind  ]  )  ,  argc  )  ; 
dcmsnd  .  execute  (  args  ,  optind  +  1  )  ; 
}  catch  (  IllegalArgumentException   e  )  { 
exit  (  e  .  getMessage  (  )  ,  true  )  ; 
} 
} 

DcmSnd  (  Configuration   cfg  ,  DcmURL   url  ,  int   argc  )  { 
this  .  url  =  url  ; 
this  .  priority  =  Integer  .  parseInt  (  cfg  .  getProperty  (  "prior"  ,  "0"  )  )  ; 
this  .  bufferSize  =  Integer  .  parseInt  (  cfg  .  getProperty  (  "buf-len"  ,  "2048"  )  )  &  0xfffffffe  ; 
this  .  repeatWhole  =  Integer  .  parseInt  (  cfg  .  getProperty  (  "repeat-assoc"  ,  "1"  )  )  ; 
this  .  repeatSingle  =  Integer  .  parseInt  (  cfg  .  getProperty  (  "repeat-dimse"  ,  "1"  )  )  ; 
this  .  mode  =  argc  >  1  ?  SEND  :  initPollDirSrv  (  cfg  )  ?  POLL  :  ECHO  ; 
initAssocRQ  (  cfg  ,  url  ,  mode  ==  ECHO  )  ; 
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
case   POLL  : 
poll  (  )  ; 
break  ; 
default  : 
throw   new   RuntimeException  (  "Illegal mode: "  +  mode  )  ; 
} 
} 

private   ActiveAssociation   openAssoc  (  )  throws   IOException  ,  GeneralSecurityException  { 
Association   assoc  =  aFact  .  newRequestor  (  newSocket  (  url  .  getHost  (  )  ,  url  .  getPort  (  )  )  )  ; 
PDU   assocAC  =  assoc  .  connect  (  assocRQ  ,  assocTO  )  ; 
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
send  (  active  ,  new   File  (  args  [  k  ]  )  )  ; 
} 
active  .  release  (  true  )  ; 
} 
} 
long   dt  =  System  .  currentTimeMillis  (  )  -  t1  ; 
log  .  info  (  MessageFormat  .  format  (  messages  .  getString  (  "sendDone"  )  ,  new   Object  [  ]  {  new   Integer  (  sentCount  )  ,  new   Long  (  sentBytes  )  ,  new   Long  (  dt  )  ,  new   Float  (  sentBytes  /  (  1.024f  *  dt  )  )  }  )  )  ; 
} 

public   void   poll  (  )  { 
pollDirSrv  .  start  (  pollDir  ,  pollPeriod  )  ; 
} 

public   void   openSession  (  )  throws   Exception  { 
activeAssociation  =  openAssoc  (  )  ; 
if  (  activeAssociation  ==  null  )  { 
throw   new   IOException  (  "Could not open association"  )  ; 
} 
} 

public   boolean   process  (  File   file  )  throws   Exception  { 
return   sendFile  (  activeAssociation  ,  file  )  ; 
} 

public   void   closeSession  (  )  { 
if  (  activeAssociation  !=  null  )  { 
try  { 
activeAssociation  .  release  (  true  )  ; 
}  catch  (  Exception   e  )  { 
log  .  warn  (  "release association throws:"  ,  e  )  ; 
} 
activeAssociation  =  null  ; 
} 
} 

private   void   send  (  ActiveAssociation   active  ,  File   file  )  throws   InterruptedException  ,  IOException  { 
if  (  !  file  .  isDirectory  (  )  )  { 
for  (  int   i  =  0  ;  i  <  repeatSingle  ;  ++  i  )  { 
sendFile  (  active  ,  file  )  ; 
} 
return  ; 
} 
File  [  ]  list  =  file  .  listFiles  (  )  ; 
for  (  int   i  =  0  ;  i  <  list  .  length  ;  ++  i  )  { 
send  (  active  ,  list  [  i  ]  )  ; 
} 
} 

private   boolean   sendFile  (  ActiveAssociation   active  ,  File   file  )  throws   InterruptedException  ,  IOException  { 
InputStream   in  =  null  ; 
DcmParser   parser  =  null  ; 
Dataset   ds  =  null  ; 
try  { 
try  { 
in  =  new   BufferedInputStream  (  new   FileInputStream  (  file  )  )  ; 
parser  =  pFact  .  newDcmParser  (  in  )  ; 
FileFormat   format  =  parser  .  detectFileFormat  (  )  ; 
if  (  format  !=  null  )  { 
ds  =  oFact  .  newDataset  (  )  ; 
parser  .  setDcmHandler  (  ds  .  getDcmHandler  (  )  )  ; 
parser  .  parseDcmFile  (  format  ,  Tags  .  PixelData  )  ; 
log  .  info  (  MessageFormat  .  format  (  messages  .  getString  (  "readDone"  )  ,  new   Object  [  ]  {  file  }  )  )  ; 
}  else  { 
log  .  error  (  MessageFormat  .  format  (  messages  .  getString  (  "failformat"  )  ,  new   Object  [  ]  {  file  }  )  )  ; 
return   false  ; 
} 
}  catch  (  IOException   e  )  { 
log  .  error  (  MessageFormat  .  format  (  messages  .  getString  (  "failread"  )  ,  new   Object  [  ]  {  file  ,  e  }  )  )  ; 
return   false  ; 
} 
sendDataset  (  active  ,  file  ,  parser  ,  ds  )  ; 
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
doOverwrite  (  ds  )  ; 
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

private   void   doOverwrite  (  Dataset   ds  )  { 
for  (  Iterator   it  =  overwrite  .  iterator  (  )  ;  it  .  hasNext  (  )  ;  )  { 
DcmElement   el  =  (  DcmElement  )  it  .  next  (  )  ; 
ds  .  putXX  (  el  .  tag  (  )  ,  el  .  vr  (  )  ,  el  .  getByteBuffer  (  )  )  ; 
} 
} 

private   static   final   class   MyDataSource   implements   DataSource  { 

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
ds  .  writeDataset  (  out  ,  netParam  )  ; 
if  (  parser  .  getReadTag  (  )  ==  Tags  .  PixelData  )  { 
DcmDecodeParam   fileParam  =  parser  .  getDcmDecodeParam  (  )  ; 
ds  .  writeHeader  (  out  ,  netParam  ,  parser  .  getReadTag  (  )  ,  parser  .  getReadVR  (  )  ,  parser  .  getReadLength  (  )  )  ; 
if  (  netParam  .  encapsulated  )  { 
parser  .  parseHeader  (  )  ; 
while  (  parser  .  getReadTag  (  )  ==  Tags  .  Item  )  { 
ds  .  writeHeader  (  out  ,  netParam  ,  parser  .  getReadTag  (  )  ,  parser  .  getReadVR  (  )  ,  parser  .  getReadLength  (  )  )  ; 
copy  (  parser  .  getInputStream  (  )  ,  out  ,  parser  .  getReadLength  (  )  ,  false  ,  buffer  )  ; 
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
copy  (  parser  .  getInputStream  (  )  ,  out  ,  parser  .  getReadLength  (  )  ,  swap  ,  buffer  )  ; 
} 
ds  .  clear  (  )  ; 
parser  .  parseDataset  (  fileParam  ,  -  1  )  ; 
ds  .  writeDataset  (  out  ,  netParam  )  ; 
} 
} 
} 

private   static   void   copy  (  InputStream   in  ,  OutputStream   out  ,  int   len  ,  boolean   swap  ,  byte  [  ]  buffer  )  throws   IOException  { 
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

private   final   void   initAssocRQ  (  Configuration   cfg  ,  DcmURL   url  ,  boolean   echo  )  { 
assocRQ  .  setCalledAET  (  url  .  getCalledAET  (  )  )  ; 
assocRQ  .  setCallingAET  (  url  .  getCallingAET  (  )  )  ; 
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

private   void   initOverwrite  (  Configuration   cfg  )  { 
for  (  Enumeration   it  =  cfg  .  keys  (  )  ;  it  .  hasMoreElements  (  )  ;  )  { 
String   key  =  (  String  )  it  .  nextElement  (  )  ; 
if  (  key  .  startsWith  (  "set."  )  )  { 
try  { 
overwrite  .  putXX  (  Tags  .  forName  (  key  .  substring  (  4  )  )  ,  cfg  .  getProperty  (  key  )  )  ; 
}  catch  (  Exception   e  )  { 
throw   new   IllegalArgumentException  (  "Illegal entry in dcmsnd.cfg - "  +  key  +  "="  +  cfg  .  getProperty  (  key  )  )  ; 
} 
} 
} 
} 

private   boolean   initPollDirSrv  (  Configuration   cfg  )  { 
String   pollDirName  =  cfg  .  getProperty  (  "poll-dir"  ,  ""  ,  "<none>"  ,  ""  )  ; 
if  (  pollDirName  .  length  (  )  ==  0  )  { 
return   false  ; 
} 
pollDir  =  new   File  (  pollDirName  )  ; 
if  (  !  pollDir  .  isDirectory  (  )  )  { 
throw   new   IllegalArgumentException  (  "Not a directory - "  +  pollDirName  )  ; 
} 
pollPeriod  =  1000L  *  Integer  .  parseInt  (  cfg  .  getProperty  (  "poll-period"  ,  "5"  )  )  ; 
pollDirSrv  =  PollDirSrvFactory  .  getInstance  (  )  .  newPollDirSrv  (  this  )  ; 
pollDirSrv  .  setOpenRetryPeriod  (  1000L  *  Integer  .  parseInt  (  cfg  .  getProperty  (  "poll-retry-open"  ,  "60"  )  )  *  1000L  )  ; 
pollDirSrv  .  setDeltaLastModified  (  1000L  *  Integer  .  parseInt  (  cfg  .  getProperty  (  "poll-delta-last-modified"  ,  "3"  )  )  )  ; 
String   doneDirName  =  cfg  .  getProperty  (  "poll-done-dir"  ,  ""  ,  "<none>"  ,  ""  )  ; 
if  (  doneDirName  .  length  (  )  !=  0  )  { 
File   doneDir  =  new   File  (  doneDirName  )  ; 
if  (  !  doneDir  .  isDirectory  (  )  )  { 
throw   new   IllegalArgumentException  (  "Not a directory - "  +  doneDirName  )  ; 
} 
pollDirSrv  .  setDoneDir  (  doneDir  )  ; 
} 
return   true  ; 
} 

private   void   initTLS  (  Configuration   cfg  )  { 
try  { 
cipherSuites  =  url  .  getCipherSuites  (  )  ; 
if  (  cipherSuites  ==  null  )  { 
return  ; 
} 
tls  =  SSLContextAdapter  .  getInstance  (  )  ; 
char  [  ]  keypasswd  =  cfg  .  getProperty  (  "key-passwd"  ,  "dcm4che"  )  .  toCharArray  (  )  ; 
tls  .  setKey  (  tls  .  loadKeyStore  (  DcmSnd  .  class  .  getResource  (  cfg  .  getProperty  (  "tls-key"  ,  "dcmsnd.key"  )  )  ,  keypasswd  )  ,  keypasswd  )  ; 
tls  .  setTrust  (  tls  .  loadKeyStore  (  DcmSnd  .  class  .  getResource  (  cfg  .  getProperty  (  "tls-cacerts"  ,  "cacerts"  )  )  ,  cfg  .  getProperty  (  "tls-cacerts-passwd"  ,  "dcm4che"  )  .  toCharArray  (  )  )  )  ; 
tls  .  init  (  )  ; 
}  catch  (  Exception   ex  )  { 
throw   new   RuntimeException  (  "Could not initalize TLS configuration - "  +  ex  .  getMessage  (  )  )  ; 
} 
} 
} 

