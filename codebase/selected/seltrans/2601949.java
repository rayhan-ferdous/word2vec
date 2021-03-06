package   jazzlib  ; 

import   java  .  io  .  IOException  ; 
import   java  .  io  .  OutputStream  ; 
import   java  .  io  .  UnsupportedEncodingException  ; 
import   java  .  util  .  Enumeration  ; 
import   java  .  util  .  Vector  ; 













public   class   ZipOutputStream   extends   DeflaterOutputStream   implements   ZipConstants  { 

private   Vector   entries  =  new   Vector  (  )  ; 

private   CRC32   crc  =  new   CRC32  (  )  ; 

private   ZipEntry   curEntry  =  null  ; 

private   int   curMethod  ; 

private   int   size  ; 

private   int   offset  =  0  ; 

private   byte  [  ]  zipComment  =  new   byte  [  0  ]  ; 

private   int   defaultMethod  =  DEFLATED  ; 




private   static   final   int   ZIP_STORED_VERSION  =  10  ; 

private   static   final   int   ZIP_DEFLATED_VERSION  =  20  ; 




public   static   final   int   STORED  =  0  ; 




public   static   final   int   DEFLATED  =  8  ; 





public   ZipOutputStream  (  OutputStream   out  )  { 
super  (  out  ,  new   Deflater  (  Deflater  .  DEFAULT_COMPRESSION  ,  true  )  )  ; 
} 







public   void   setComment  (  String   comment  )  { 
byte  [  ]  commentBytes  ; 
try  { 
commentBytes  =  comment  .  getBytes  (  "UTF-8"  )  ; 
}  catch  (  UnsupportedEncodingException   uee  )  { 
throw   new   AssertionError  (  uee  )  ; 
} 
if  (  commentBytes  .  length  >  0xffff  )  throw   new   IllegalArgumentException  (  "Comment too long."  )  ; 
zipComment  =  commentBytes  ; 
} 









public   void   setMethod  (  int   method  )  { 
if  (  method  !=  STORED  &&  method  !=  DEFLATED  )  throw   new   IllegalArgumentException  (  "Method not supported."  )  ; 
defaultMethod  =  method  ; 
} 







public   void   setLevel  (  int   level  )  { 
def  .  setLevel  (  level  )  ; 
} 




private   void   writeLeShort  (  int   value  )  throws   IOException  { 
out  .  write  (  value  &  0xff  )  ; 
out  .  write  (  (  value  >  >  8  )  &  0xff  )  ; 
} 




private   void   writeLeInt  (  int   value  )  throws   IOException  { 
writeLeShort  (  value  )  ; 
writeLeShort  (  value  >  >  16  )  ; 
} 






private   void   writeLeInt  (  long   value  )  throws   IOException  { 
writeLeInt  (  (  int  )  value  )  ; 
} 











public   void   putNextEntry  (  ZipEntry   entry  )  throws   IOException  { 
if  (  entries  ==  null  )  throw   new   ZipException  (  "ZipOutputStream was finished"  )  ; 
int   method  =  entry  .  getMethod  (  )  ; 
int   flags  =  0  ; 
if  (  method  ==  -  1  )  method  =  defaultMethod  ; 
if  (  method  ==  STORED  )  { 
if  (  entry  .  getCompressedSize  (  )  >=  0  )  { 
if  (  entry  .  getSize  (  )  <  0  )  entry  .  setSize  (  entry  .  getCompressedSize  (  )  )  ;  else   if  (  entry  .  getSize  (  )  !=  entry  .  getCompressedSize  (  )  )  throw   new   ZipException  (  "Method STORED, but compressed size != size"  )  ; 
}  else   entry  .  setCompressedSize  (  entry  .  getSize  (  )  )  ; 
if  (  entry  .  getSize  (  )  <  0  )  throw   new   ZipException  (  "Method STORED, but size not set"  )  ; 
if  (  entry  .  getCrc  (  )  <  0  )  throw   new   ZipException  (  "Method STORED, but crc not set"  )  ; 
}  else   if  (  method  ==  DEFLATED  )  { 
if  (  entry  .  getCompressedSize  (  )  <  0  ||  entry  .  getSize  (  )  <  0  ||  entry  .  getCrc  (  )  <  0  )  flags  |=  8  ; 
} 
if  (  curEntry  !=  null  )  closeEntry  (  )  ; 
if  (  entry  .  getTime  (  )  <  0  )  entry  .  setTime  (  System  .  currentTimeMillis  (  )  )  ; 
entry  .  flags  =  flags  ; 
entry  .  offset  =  offset  ; 
entry  .  setMethod  (  method  )  ; 
curMethod  =  method  ; 
writeLeInt  (  LOCSIG  )  ; 
writeLeShort  (  method  ==  STORED  ?  ZIP_STORED_VERSION  :  ZIP_DEFLATED_VERSION  )  ; 
writeLeShort  (  flags  )  ; 
writeLeShort  (  method  )  ; 
writeLeInt  (  entry  .  getDOSTime  (  )  )  ; 
if  (  (  flags  &  8  )  ==  0  )  { 
writeLeInt  (  (  int  )  entry  .  getCrc  (  )  )  ; 
writeLeInt  (  (  int  )  entry  .  getCompressedSize  (  )  )  ; 
writeLeInt  (  (  int  )  entry  .  getSize  (  )  )  ; 
}  else  { 
writeLeInt  (  0  )  ; 
writeLeInt  (  0  )  ; 
writeLeInt  (  0  )  ; 
} 
byte  [  ]  name  ; 
try  { 
name  =  entry  .  getName  (  )  .  getBytes  (  "UTF-8"  )  ; 
}  catch  (  UnsupportedEncodingException   uee  )  { 
throw   new   AssertionError  (  uee  )  ; 
} 
if  (  name  .  length  >  0xffff  )  throw   new   ZipException  (  "Name too long."  )  ; 
byte  [  ]  extra  =  entry  .  getExtra  (  )  ; 
if  (  extra  ==  null  )  extra  =  new   byte  [  0  ]  ; 
writeLeShort  (  name  .  length  )  ; 
writeLeShort  (  extra  .  length  )  ; 
out  .  write  (  name  )  ; 
out  .  write  (  extra  )  ; 
offset  +=  LOCHDR  +  name  .  length  +  extra  .  length  ; 
curEntry  =  entry  ; 
crc  .  reset  (  )  ; 
if  (  method  ==  DEFLATED  )  def  .  reset  (  )  ; 
size  =  0  ; 
} 






public   void   closeEntry  (  )  throws   IOException  { 
if  (  curEntry  ==  null  )  throw   new   ZipException  (  "No open entry"  )  ; 
if  (  curMethod  ==  DEFLATED  )  super  .  finish  (  )  ; 
int   csize  =  curMethod  ==  DEFLATED  ?  def  .  getTotalOut  (  )  :  size  ; 
if  (  curEntry  .  getSize  (  )  <  0  )  curEntry  .  setSize  (  size  )  ;  else   if  (  curEntry  .  getSize  (  )  !=  size  )  throw   new   ZipException  (  "size was "  +  size  +  ", but I expected "  +  curEntry  .  getSize  (  )  )  ; 
if  (  curEntry  .  getCompressedSize  (  )  <  0  )  curEntry  .  setCompressedSize  (  csize  )  ;  else   if  (  curEntry  .  getCompressedSize  (  )  !=  csize  )  throw   new   ZipException  (  "compressed size was "  +  csize  +  ", but I expected "  +  curEntry  .  getSize  (  )  )  ; 
if  (  curEntry  .  getCrc  (  )  <  0  )  curEntry  .  setCrc  (  crc  .  getValue  (  )  )  ;  else   if  (  curEntry  .  getCrc  (  )  !=  crc  .  getValue  (  )  )  throw   new   ZipException  (  "crc was "  +  Long  .  toHexString  (  crc  .  getValue  (  )  )  +  ", but I expected "  +  Long  .  toHexString  (  curEntry  .  getCrc  (  )  )  )  ; 
offset  +=  csize  ; 
if  (  curMethod  ==  DEFLATED  &&  (  curEntry  .  flags  &  8  )  !=  0  )  { 
writeLeInt  (  EXTSIG  )  ; 
writeLeInt  (  (  int  )  curEntry  .  getCrc  (  )  )  ; 
writeLeInt  (  (  int  )  curEntry  .  getCompressedSize  (  )  )  ; 
writeLeInt  (  (  int  )  curEntry  .  getSize  (  )  )  ; 
offset  +=  EXTHDR  ; 
} 
entries  .  addElement  (  curEntry  )  ; 
curEntry  =  null  ; 
} 






public   void   write  (  byte  [  ]  b  ,  int   off  ,  int   len  )  throws   IOException  { 
if  (  curEntry  ==  null  )  throw   new   ZipException  (  "No open entry."  )  ; 
switch  (  curMethod  )  { 
case   DEFLATED  : 
super  .  write  (  b  ,  off  ,  len  )  ; 
break  ; 
case   STORED  : 
out  .  write  (  b  ,  off  ,  len  )  ; 
break  ; 
} 
crc  .  update  (  b  ,  off  ,  len  )  ; 
size  +=  len  ; 
} 






public   void   finish  (  )  throws   IOException  { 
if  (  entries  ==  null  )  return  ; 
if  (  curEntry  !=  null  )  closeEntry  (  )  ; 
int   numEntries  =  0  ; 
int   sizeEntries  =  0  ; 
Enumeration   e  =  entries  .  elements  (  )  ; 
while  (  e  .  hasMoreElements  (  )  )  { 
ZipEntry   entry  =  (  ZipEntry  )  e  .  nextElement  (  )  ; 
int   method  =  entry  .  getMethod  (  )  ; 
writeLeInt  (  CENSIG  )  ; 
writeLeShort  (  method  ==  STORED  ?  ZIP_STORED_VERSION  :  ZIP_DEFLATED_VERSION  )  ; 
writeLeShort  (  method  ==  STORED  ?  ZIP_STORED_VERSION  :  ZIP_DEFLATED_VERSION  )  ; 
writeLeShort  (  entry  .  flags  )  ; 
writeLeShort  (  method  )  ; 
writeLeInt  (  entry  .  getDOSTime  (  )  )  ; 
writeLeInt  (  (  int  )  entry  .  getCrc  (  )  )  ; 
writeLeInt  (  (  int  )  entry  .  getCompressedSize  (  )  )  ; 
writeLeInt  (  (  int  )  entry  .  getSize  (  )  )  ; 
byte  [  ]  name  ; 
try  { 
name  =  entry  .  getName  (  )  .  getBytes  (  "UTF-8"  )  ; 
}  catch  (  UnsupportedEncodingException   uee  )  { 
throw   new   AssertionError  (  uee  )  ; 
} 
if  (  name  .  length  >  0xffff  )  throw   new   ZipException  (  "Name too long."  )  ; 
byte  [  ]  extra  =  entry  .  getExtra  (  )  ; 
if  (  extra  ==  null  )  extra  =  new   byte  [  0  ]  ; 
String   str  =  entry  .  getComment  (  )  ; 
byte  [  ]  comment  ; 
try  { 
comment  =  str  !=  null  ?  str  .  getBytes  (  "UTF-8"  )  :  new   byte  [  0  ]  ; 
}  catch  (  UnsupportedEncodingException   uee  )  { 
throw   new   AssertionError  (  uee  )  ; 
} 
if  (  comment  .  length  >  0xffff  )  throw   new   ZipException  (  "Comment too long."  )  ; 
writeLeShort  (  name  .  length  )  ; 
writeLeShort  (  extra  .  length  )  ; 
writeLeShort  (  comment  .  length  )  ; 
writeLeShort  (  0  )  ; 
writeLeShort  (  0  )  ; 
writeLeInt  (  0  )  ; 
writeLeInt  (  entry  .  offset  )  ; 
out  .  write  (  name  )  ; 
out  .  write  (  extra  )  ; 
out  .  write  (  comment  )  ; 
numEntries  ++  ; 
sizeEntries  +=  CENHDR  +  name  .  length  +  extra  .  length  +  comment  .  length  ; 
} 
writeLeInt  (  ENDSIG  )  ; 
writeLeShort  (  0  )  ; 
writeLeShort  (  0  )  ; 
writeLeShort  (  numEntries  )  ; 
writeLeShort  (  numEntries  )  ; 
writeLeInt  (  sizeEntries  )  ; 
writeLeInt  (  offset  )  ; 
writeLeShort  (  zipComment  .  length  )  ; 
out  .  write  (  zipComment  )  ; 
out  .  flush  (  )  ; 
entries  =  null  ; 
} 
} 

