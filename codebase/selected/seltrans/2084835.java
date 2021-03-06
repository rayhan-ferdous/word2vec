package   org  .  ccnx  .  ccn  .  protocol  ; 

import   java  .  util  .  Arrays  ; 
import   java  .  util  .  HashMap  ; 
import   java  .  util  .  Map  .  Entry  ; 
import   org  .  ccnx  .  ccn  .  impl  .  encoding  .  CCNProtocolDTags  ; 
import   org  .  ccnx  .  ccn  .  impl  .  encoding  .  GenericXMLEncodable  ; 
import   org  .  ccnx  .  ccn  .  impl  .  encoding  .  XMLDecoder  ; 
import   org  .  ccnx  .  ccn  .  impl  .  encoding  .  XMLEncodable  ; 
import   org  .  ccnx  .  ccn  .  impl  .  encoding  .  XMLEncoder  ; 
import   org  .  ccnx  .  ccn  .  impl  .  support  .  DataUtils  ; 
import   org  .  ccnx  .  ccn  .  impl  .  support  .  Log  ; 
import   org  .  ccnx  .  ccn  .  io  .  content  .  ContentDecodingException  ; 
import   org  .  ccnx  .  ccn  .  io  .  content  .  ContentEncodingException  ; 





















public   class   SignedInfo   extends   GenericXMLEncodable   implements   XMLEncodable  { 

public   enum   ContentType  { 

DATA  ,  ENCR  ,  GONE  ,  KEY  ,  LINK  ,  NACK 
} 

; 

public   static   final   byte  [  ]  DATA_VAL  =  new   byte  [  ]  {  (  byte  )  0x0c  ,  (  byte  )  0x04  ,  (  byte  )  0xc0  }  ; 

public   static   final   byte  [  ]  ENCR_VAL  =  new   byte  [  ]  {  (  byte  )  0x10  ,  (  byte  )  0xd0  ,  (  byte  )  0x91  }  ; 

public   static   final   byte  [  ]  GONE_VAL  =  new   byte  [  ]  {  (  byte  )  0x18  ,  (  byte  )  0xe3  ,  (  byte  )  0x44  }  ; 

public   static   final   byte  [  ]  KEY_VAL  =  new   byte  [  ]  {  (  byte  )  0x28  ,  (  byte  )  0x46  ,  (  byte  )  0x3f  }  ; 

public   static   final   byte  [  ]  LINK_VAL  =  new   byte  [  ]  {  (  byte  )  0x2c  ,  (  byte  )  0x83  ,  (  byte  )  0x4a  }  ; 

public   static   final   byte  [  ]  NACK_VAL  =  new   byte  [  ]  {  (  byte  )  0x34  ,  (  byte  )  0x00  ,  (  byte  )  0x8a  }  ; 

protected   static   final   HashMap  <  ContentType  ,  String  >  ContentTypeNames  =  new   HashMap  <  ContentType  ,  String  >  (  )  ; 

protected   static   final   HashMap  <  String  ,  ContentType  >  ContentNameTypes  =  new   HashMap  <  String  ,  ContentType  >  (  )  ; 

protected   static   final   HashMap  <  ContentType  ,  byte  [  ]  >  ContentTypeValues  =  new   HashMap  <  ContentType  ,  byte  [  ]  >  (  )  ; 

protected   static   final   HashMap  <  byte  [  ]  ,  ContentType  >  ContentValueTypes  =  new   HashMap  <  byte  [  ]  ,  ContentType  >  (  )  ; 

static  { 
ContentTypeNames  .  put  (  ContentType  .  DATA  ,  "DATA"  )  ; 
ContentTypeNames  .  put  (  ContentType  .  ENCR  ,  "ENCR"  )  ; 
ContentTypeNames  .  put  (  ContentType  .  GONE  ,  "GONE"  )  ; 
ContentTypeNames  .  put  (  ContentType  .  KEY  ,  "KEY/"  )  ; 
ContentTypeNames  .  put  (  ContentType  .  LINK  ,  "LINK"  )  ; 
ContentTypeNames  .  put  (  ContentType  .  NACK  ,  "NACK"  )  ; 
ContentNameTypes  .  put  (  "DATA"  ,  ContentType  .  DATA  )  ; 
ContentNameTypes  .  put  (  "ENCR"  ,  ContentType  .  ENCR  )  ; 
ContentNameTypes  .  put  (  "GONE"  ,  ContentType  .  GONE  )  ; 
ContentNameTypes  .  put  (  "KEY/"  ,  ContentType  .  KEY  )  ; 
ContentNameTypes  .  put  (  "LINK"  ,  ContentType  .  LINK  )  ; 
ContentNameTypes  .  put  (  "NACK"  ,  ContentType  .  NACK  )  ; 
ContentTypeValues  .  put  (  ContentType  .  DATA  ,  DATA_VAL  )  ; 
ContentTypeValues  .  put  (  ContentType  .  ENCR  ,  ENCR_VAL  )  ; 
ContentTypeValues  .  put  (  ContentType  .  GONE  ,  GONE_VAL  )  ; 
ContentTypeValues  .  put  (  ContentType  .  KEY  ,  KEY_VAL  )  ; 
ContentTypeValues  .  put  (  ContentType  .  LINK  ,  LINK_VAL  )  ; 
ContentTypeValues  .  put  (  ContentType  .  NACK  ,  NACK_VAL  )  ; 
ContentValueTypes  .  put  (  DATA_VAL  ,  ContentType  .  DATA  )  ; 
ContentValueTypes  .  put  (  ENCR_VAL  ,  ContentType  .  ENCR  )  ; 
ContentValueTypes  .  put  (  GONE_VAL  ,  ContentType  .  GONE  )  ; 
ContentValueTypes  .  put  (  KEY_VAL  ,  ContentType  .  KEY  )  ; 
ContentValueTypes  .  put  (  LINK_VAL  ,  ContentType  .  LINK  )  ; 
ContentValueTypes  .  put  (  NACK_VAL  ,  ContentType  .  NACK  )  ; 
} 

protected   PublisherPublicKeyDigest   _publisher  ; 

protected   CCNTime   _timestamp  ; 

protected   ContentType   _type  ; 

protected   KeyLocator   _locator  ; 

protected   Integer   _freshnessSeconds  ; 

protected   byte  [  ]  _finalBlockID  ; 






public   SignedInfo  (  PublisherPublicKeyDigest   publisher  ,  KeyLocator   locator  )  { 
this  (  publisher  ,  null  ,  null  ,  locator  )  ; 
} 







public   SignedInfo  (  PublisherPublicKeyDigest   publisher  ,  ContentType   type  ,  KeyLocator   locator  )  { 
this  (  publisher  ,  null  ,  type  ,  locator  )  ; 
} 








public   SignedInfo  (  PublisherPublicKeyDigest   publisher  ,  CCNTime   timestamp  ,  ContentType   type  ,  KeyLocator   locator  )  { 
this  (  publisher  ,  timestamp  ,  type  ,  locator  ,  null  ,  null  )  ; 
} 









public   SignedInfo  (  PublisherPublicKeyDigest   publisher  ,  ContentType   type  ,  KeyLocator   locator  ,  Integer   freshnessSeconds  ,  byte  [  ]  finalBlockID  )  { 
this  (  publisher  ,  null  ,  type  ,  locator  ,  freshnessSeconds  ,  finalBlockID  )  ; 
} 










public   SignedInfo  (  PublisherPublicKeyDigest   publisher  ,  CCNTime   timestamp  ,  ContentType   type  ,  KeyLocator   locator  ,  Integer   freshnessSeconds  ,  byte  [  ]  finalBlockID  )  { 
super  (  )  ; 
this  .  _publisher  =  publisher  ; 
if  (  null  ==  timestamp  )  { 
this  .  _timestamp  =  CCNTime  .  now  (  )  ; 
}  else  { 
this  .  _timestamp  =  timestamp  ; 
} 
this  .  _type  =  (  null  ==  type  )  ?  ContentType  .  DATA  :  type  ; 
this  .  _locator  =  locator  ; 
this  .  _freshnessSeconds  =  freshnessSeconds  ; 
this  .  _finalBlockID  =  finalBlockID  ; 
} 





public   SignedInfo  (  SignedInfo   other  )  { 
this  (  other  .  getPublisherKeyID  (  )  ,  other  .  getTimestamp  (  )  ,  other  .  getType  (  )  ,  other  .  getKeyLocator  (  )  ,  other  .  getFreshnessSeconds  (  )  ,  other  .  getFinalBlockID  (  )  )  ; 
} 




public   SignedInfo  (  )  { 
} 




public   SignedInfo   clone  (  )  { 
KeyLocator   kl  =  getKeyLocator  (  )  ; 
return   new   SignedInfo  (  getPublisherKeyID  (  )  ,  getTimestamp  (  )  ,  getType  (  )  ,  null  ==  kl  ?  null  :  kl  .  clone  (  )  ,  getFreshnessSeconds  (  )  ,  getFinalBlockID  (  )  )  ; 
} 





public   boolean   emptyPublisher  (  )  { 
if  (  (  null  !=  getPublisherKeyID  (  )  )  &&  (  0  !=  getPublisher  (  )  .  length  )  )  return   false  ; 
return   true  ; 
} 





public   boolean   defaultContentType  (  )  { 
return  (  (  null  ==  _type  )  ||  (  ContentType  .  DATA  ==  _type  )  )  ; 
} 





public   boolean   emptyTimestamp  (  )  { 
return  (  null  ==  _timestamp  )  ; 
} 





public   boolean   emptyKeyLocator  (  )  { 
return  (  null  ==  _locator  )  ; 
} 





public   final   byte  [  ]  getPublisher  (  )  { 
return   _publisher  .  digest  (  )  ; 
} 

public   final   PublisherPublicKeyDigest   getPublisherKeyID  (  )  { 
return   _publisher  ; 
} 





public   final   CCNTime   getTimestamp  (  )  { 
return   _timestamp  ; 
} 





public   final   KeyLocator   getKeyLocator  (  )  { 
return   _locator  ; 
} 





public   final   int   getFreshnessSeconds  (  )  { 
return   _freshnessSeconds  ; 
} 





public   boolean   emptyFreshnessSeconds  (  )  { 
return  (  null  ==  _freshnessSeconds  )  ; 
} 





public   final   byte  [  ]  getFinalBlockID  (  )  { 
return   _finalBlockID  ; 
} 





public   boolean   emptyFinalBlockID  (  )  { 
return  (  null  ==  _finalBlockID  )  ; 
} 





public   void   setFinalBlockID  (  byte  [  ]  finalBlockID  )  { 
_finalBlockID  =  finalBlockID  ; 
} 





public   void   setType  (  ContentType   type  )  { 
if  (  null  ==  type  )  { 
_type  =  ContentType  .  DATA  ; 
}  else  { 
_type  =  type  ; 
} 
} 





public   final   ContentType   getType  (  )  { 
if  (  null  ==  _type  )  return   ContentType  .  DATA  ; 
return   _type  ; 
} 





public   String   getTypeName  (  )  { 
return   typeToName  (  getType  (  )  )  ; 
} 






public   static   final   String   typeToName  (  ContentType   type  )  { 
if  (  ContentTypeNames  .  get  (  type  )  ==  null  )  { 
Log  .  warning  (  "Cannot find name for type: "  +  type  )  ; 
} 
return   ContentTypeNames  .  get  (  type  )  ; 
} 






public   static   final   ContentType   nameToType  (  String   name  )  { 
return   ContentNameTypes  .  get  (  name  )  ; 
} 





public   byte  [  ]  getTypeValue  (  )  { 
return   typeToValue  (  getType  (  )  )  ; 
} 









public   static   final   byte  [  ]  typeToValue  (  ContentType   type  )  { 
if  (  ContentTypeValues  .  get  (  type  )  ==  null  )  { 
Log  .  warning  (  "Cannot find name for type: "  +  type  )  ; 
} 
return   ContentTypeValues  .  get  (  type  )  ; 
} 









public   static   final   ContentType   valueToType  (  byte  [  ]  value  )  { 
for  (  Entry  <  byte  [  ]  ,  ContentType  >  entry  :  ContentValueTypes  .  entrySet  (  )  )  { 
if  (  Arrays  .  equals  (  value  ,  entry  .  getKey  (  )  )  )  return   entry  .  getValue  (  )  ; 
} 
return   null  ; 
} 

@  Override 
public   void   decode  (  XMLDecoder   decoder  )  throws   ContentDecodingException  { 
decoder  .  readStartElement  (  getElementLabel  (  )  )  ; 
if  (  decoder  .  peekStartElement  (  CCNProtocolDTags  .  PublisherPublicKeyDigest  )  )  { 
_publisher  =  new   PublisherPublicKeyDigest  (  )  ; 
_publisher  .  decode  (  decoder  )  ; 
} 
if  (  decoder  .  peekStartElement  (  CCNProtocolDTags  .  Timestamp  )  )  { 
_timestamp  =  decoder  .  readDateTime  (  CCNProtocolDTags  .  Timestamp  )  ; 
} 
if  (  decoder  .  peekStartElement  (  CCNProtocolDTags  .  Type  )  )  { 
byte  [  ]  binType  =  decoder  .  readBinaryElement  (  CCNProtocolDTags  .  Type  )  ; 
_type  =  valueToType  (  binType  )  ; 
if  (  null  ==  _type  )  { 
throw   new   ContentDecodingException  (  "Cannot parse signedInfo type: "  +  DataUtils  .  printHexBytes  (  binType  )  +  " "  +  binType  .  length  +  " bytes."  )  ; 
} 
}  else  { 
_type  =  ContentType  .  DATA  ; 
} 
if  (  decoder  .  peekStartElement  (  CCNProtocolDTags  .  FreshnessSeconds  )  )  { 
_freshnessSeconds  =  decoder  .  readIntegerElement  (  CCNProtocolDTags  .  FreshnessSeconds  )  ; 
} 
if  (  decoder  .  peekStartElement  (  CCNProtocolDTags  .  FinalBlockID  )  )  { 
_finalBlockID  =  decoder  .  readBinaryElement  (  CCNProtocolDTags  .  FinalBlockID  )  ; 
} 
if  (  decoder  .  peekStartElement  (  CCNProtocolDTags  .  KeyLocator  )  )  { 
_locator  =  new   KeyLocator  (  )  ; 
_locator  .  decode  (  decoder  )  ; 
} 
decoder  .  readEndElement  (  )  ; 
} 

@  Override 
public   void   encode  (  XMLEncoder   encoder  )  throws   ContentEncodingException  { 
if  (  !  validate  (  )  )  { 
throw   new   ContentEncodingException  (  "Cannot encode "  +  this  .  getClass  (  )  .  getName  (  )  +  ": field values missing."  )  ; 
} 
encoder  .  writeStartElement  (  getElementLabel  (  )  )  ; 
if  (  !  emptyPublisher  (  )  )  { 
getPublisherKeyID  (  )  .  encode  (  encoder  )  ; 
} 
if  (  !  emptyTimestamp  (  )  )  { 
encoder  .  writeDateTime  (  CCNProtocolDTags  .  Timestamp  ,  getTimestamp  (  )  )  ; 
} 
if  (  !  defaultContentType  (  )  )  { 
encoder  .  writeElement  (  CCNProtocolDTags  .  Type  ,  getTypeValue  (  )  )  ; 
} 
if  (  !  emptyFreshnessSeconds  (  )  )  { 
encoder  .  writeElement  (  CCNProtocolDTags  .  FreshnessSeconds  ,  getFreshnessSeconds  (  )  )  ; 
} 
if  (  !  emptyFinalBlockID  (  )  )  { 
encoder  .  writeElement  (  CCNProtocolDTags  .  FinalBlockID  ,  getFinalBlockID  (  )  )  ; 
} 
if  (  !  emptyKeyLocator  (  )  )  { 
getKeyLocator  (  )  .  encode  (  encoder  )  ; 
} 
encoder  .  writeEndElement  (  )  ; 
} 

@  Override 
public   long   getElementLabel  (  )  { 
return   CCNProtocolDTags  .  SignedInfo  ; 
} 

@  Override 
public   boolean   validate  (  )  { 
if  (  emptyPublisher  (  )  ||  emptyTimestamp  (  )  ||  emptyKeyLocator  (  )  )  return   false  ; 
return   true  ; 
} 

@  Override 
public   int   hashCode  (  )  { 
final   int   prime  =  31  ; 
int   result  =  1  ; 
result  =  prime  *  result  +  Arrays  .  hashCode  (  _finalBlockID  )  ; 
result  =  prime  *  result  +  (  (  _freshnessSeconds  ==  null  )  ?  0  :  _freshnessSeconds  .  hashCode  (  )  )  ; 
result  =  prime  *  result  +  (  (  _locator  ==  null  )  ?  0  :  _locator  .  hashCode  (  )  )  ; 
result  =  prime  *  result  +  (  (  _publisher  ==  null  )  ?  0  :  _publisher  .  hashCode  (  )  )  ; 
result  =  prime  *  result  +  (  (  _timestamp  ==  null  )  ?  0  :  _timestamp  .  hashCode  (  )  )  ; 
result  =  prime  *  result  +  (  (  _type  ==  null  )  ?  0  :  _type  .  hashCode  (  )  )  ; 
return   result  ; 
} 

@  Override 
public   boolean   equals  (  Object   obj  )  { 
if  (  this  ==  obj  )  return   true  ; 
if  (  obj  ==  null  )  return   false  ; 
if  (  getClass  (  )  !=  obj  .  getClass  (  )  )  return   false  ; 
SignedInfo   other  =  (  SignedInfo  )  obj  ; 
if  (  !  Arrays  .  equals  (  _finalBlockID  ,  other  .  _finalBlockID  )  )  return   false  ; 
if  (  _freshnessSeconds  ==  null  )  { 
if  (  other  .  _freshnessSeconds  !=  null  )  return   false  ; 
}  else   if  (  !  _freshnessSeconds  .  equals  (  other  .  _freshnessSeconds  )  )  return   false  ; 
if  (  _locator  ==  null  )  { 
if  (  other  .  _locator  !=  null  )  return   false  ; 
}  else   if  (  !  _locator  .  equals  (  other  .  _locator  )  )  return   false  ; 
if  (  _publisher  ==  null  )  { 
if  (  other  .  _publisher  !=  null  )  return   false  ; 
}  else   if  (  !  _publisher  .  equals  (  other  .  _publisher  )  )  return   false  ; 
if  (  _timestamp  ==  null  )  { 
if  (  other  .  _timestamp  !=  null  )  return   false  ; 
}  else   if  (  !  _timestamp  .  equals  (  other  .  _timestamp  )  )  return   false  ; 
if  (  _type  ==  null  )  { 
if  (  other  .  _type  !=  null  )  return   false  ; 
}  else   if  (  !  _type  .  equals  (  other  .  _type  )  )  return   false  ; 
return   true  ; 
} 

public   String   toString  (  )  { 
StringBuffer   s  =  new   StringBuffer  (  )  ; 
if  (  _type  !=  null  )  s  .  append  (  String  .  format  (  "si: type=%s"  ,  typeToName  (  _type  )  )  )  ; 
s  .  append  (  String  .  format  (  "si: timestamp=%s"  ,  _timestamp  )  )  ; 
s  .  append  (  String  .  format  (  "si: pub=%s"  ,  _publisher  )  )  ; 
if  (  _locator  !=  null  )  s  .  append  (  String  .  format  (  "si: loc=%s"  ,  _locator  )  )  ; 
if  (  _freshnessSeconds  !=  null  )  s  .  append  (  String  .  format  (  "si: type=%s"  ,  _freshnessSeconds  )  )  ; 
return   s  .  toString  (  )  ; 
} 
} 

