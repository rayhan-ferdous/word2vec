package   lazyj  ; 

import   java  .  awt  .  Component  ; 
import   java  .  awt  .  Graphics2D  ; 
import   java  .  awt  .  Image  ; 
import   java  .  awt  .  MediaTracker  ; 
import   java  .  awt  .  image  .  BufferedImage  ; 
import   java  .  io  .  BufferedReader  ; 
import   java  .  io  .  ByteArrayInputStream  ; 
import   java  .  io  .  ByteArrayOutputStream  ; 
import   java  .  io  .  File  ; 
import   java  .  io  .  FileInputStream  ; 
import   java  .  io  .  FileOutputStream  ; 
import   java  .  io  .  FileReader  ; 
import   java  .  io  .  IOException  ; 
import   java  .  io  .  InputStream  ; 
import   java  .  io  .  OutputStream  ; 
import   java  .  io  .  UnsupportedEncodingException  ; 
import   java  .  net  .  InetAddress  ; 
import   java  .  net  .  URL  ; 
import   java  .  net  .  URLConnection  ; 
import   java  .  text  .  Normalizer  ; 
import   java  .  util  .  HashMap  ; 
import   java  .  util  .  Iterator  ; 
import   java  .  util  .  LinkedList  ; 
import   java  .  util  .  List  ; 
import   java  .  util  .  Locale  ; 
import   java  .  util  .  Map  ; 
import   java  .  util  .  prefs  .  AbstractPreferences  ; 
import   java  .  util  .  prefs  .  BackingStoreException  ; 
import   java  .  util  .  regex  .  Matcher  ; 
import   java  .  util  .  regex  .  Pattern  ; 
import   java  .  util  .  zip  .  GZIPInputStream  ; 
import   java  .  util  .  zip  .  GZIPOutputStream  ; 
import   java  .  util  .  zip  .  ZipEntry  ; 
import   java  .  util  .  zip  .  ZipOutputStream  ; 
import   javax  .  crypto  .  Cipher  ; 
import   javax  .  crypto  .  CipherOutputStream  ; 
import   javax  .  crypto  .  spec  .  SecretKeySpec  ; 
import   javax  .  imageio  .  IIOImage  ; 
import   javax  .  imageio  .  ImageIO  ; 
import   javax  .  imageio  .  ImageWriteParam  ; 
import   javax  .  imageio  .  ImageWriter  ; 
import   javax  .  imageio  .  plugins  .  jpeg  .  JPEGImageWriteParam  ; 
import   javax  .  imageio  .  stream  .  FileImageOutputStream  ; 
import   javax  .  imageio  .  stream  .  ImageOutputStream  ; 
import   lazyj  .  cache  .  ExpirationCache  ; 
import   lazyj  .  page  .  BasePage  ; 





@  SuppressWarnings  (  "nls"  ) 
public   final   class   Utils  { 




private   static   boolean   bLazyjConfigFolderDetermined  =  false  ; 




private   static   String   sLazyjConfigFolder  =  null  ; 




















public   static   String   getLazyjConfigFolder  (  )  { 
if  (  bLazyjConfigFolderDetermined  )  return   sLazyjConfigFolder  ; 
sLazyjConfigFolder  =  System  .  getProperty  (  "lazyj.config.folder"  )  ; 
if  (  sLazyjConfigFolder  !=  null  )  { 
final   File   f  =  new   File  (  sLazyjConfigFolder  )  ; 
if  (  f  .  isDirectory  (  )  &&  f  .  canRead  (  )  )  { 
bLazyjConfigFolderDetermined  =  true  ; 
return   sLazyjConfigFolder  ; 
} 
} 
sLazyjConfigFolder  =  getConfigFolder  (  "lazyj"  ,  new   String  [  ]  {  "logging.properties"  ,  "dbsessions.properties"  ,  "basepage.properties"  ,  "modules.properties"  }  )  ; 
bLazyjConfigFolderDetermined  =  true  ; 
return   sLazyjConfigFolder  ; 
} 








public   static   String   getConfigFolder  (  final   String   sAppName  ,  final   String  [  ]  filesToSearch  )  { 
final   List  <  String  >  folders  =  new   LinkedList  <  String  >  (  )  ; 
try  { 
File   fDir  =  new   File  (  "."  )  ; 
if  (  fDir  .  isDirectory  (  )  &&  fDir  .  canRead  (  )  )  { 
String   sPath  =  fDir  .  getCanonicalPath  (  )  ; 
folders  .  add  (  sPath  +  File  .  separator  +  "config"  +  File  .  separator  +  sAppName  )  ; 
folders  .  add  (  sPath  +  File  .  separator  +  "config"  )  ; 
folders  .  add  (  sPath  )  ; 
} 
fDir  =  new   File  (  ".."  )  ; 
if  (  fDir  .  isDirectory  (  )  &&  fDir  .  canRead  (  )  )  { 
String   sPath  =  fDir  .  getCanonicalPath  (  )  ; 
folders  .  add  (  sPath  +  File  .  separator  +  "config"  +  File  .  separator  +  sAppName  )  ; 
folders  .  add  (  sPath  +  File  .  separator  +  "config"  )  ; 
folders  .  add  (  sPath  )  ; 
} 
}  catch  (  IOException   _  )  { 
} 
folders  .  add  (  System  .  getProperty  (  "user.home"  )  +  File  .  separator  +  '.'  +  sAppName  )  ; 
folders  .  add  (  "/etc/"  +  sAppName  )  ; 
for  (  String   sPath  :  folders  )  { 
File   f  =  new   File  (  sPath  )  ; 
if  (  !  f  .  isDirectory  (  )  ||  !  f  .  canRead  (  )  )  continue  ; 
for  (  String   sFile  :  filesToSearch  )  { 
f  =  new   File  (  sPath  ,  sFile  )  ; 
if  (  f  .  isFile  (  )  &&  f  .  canRead  (  )  )  return   sPath  ; 
} 
} 
return   null  ; 
} 


























public   static   final   boolean   resize  (  final   String   sSource  ,  final   String   sDest  ,  final   int   width  ,  final   int   height  ,  final   float   quality  ,  final   boolean   bDeleteOriginalFile  )  { 
final   BufferedImage   orig  ; 
try  { 
orig  =  javax  .  imageio  .  ImageIO  .  read  (  new   FileInputStream  (  sSource  )  )  ; 
}  catch  (  Exception   e  )  { 
Log  .  log  (  Log  .  ERROR  ,  "lazyj.Utils"  ,  "image resize: exception decoding a compressed format from file '"  +  sSource  +  "'"  ,  e  )  ; 
return   false  ; 
} 
final   int   w  =  orig  .  getWidth  (  )  ; 
final   int   h  =  orig  .  getHeight  (  )  ; 
BufferedImage   dest  ; 
if  (  w  <=  width  &&  h  <=  height  )  { 
dest  =  orig  ; 
}  else  { 
final   double   ratio  =  (  double  )  w  /  (  double  )  h  ; 
int   destWidth  =  width  ; 
int   destHeight  =  height  ; 
if  (  w  *  height  >  h  *  width  )  { 
destHeight  =  (  int  )  (  destWidth  /  ratio  )  ; 
}  else  { 
destWidth  =  (  int  )  (  destHeight  *  ratio  )  ; 
} 
Component   comp  =  new   Component  (  )  { 

private   static   final   long   serialVersionUID  =  1L  ; 
}  ; 
Image   i2  =  orig  .  getScaledInstance  (  destWidth  ,  destHeight  ,  Image  .  SCALE_SMOOTH  )  ; 
MediaTracker   tracker  =  new   MediaTracker  (  comp  )  ; 
tracker  .  addImage  (  i2  ,  0  )  ; 
try  { 
tracker  .  waitForID  (  0  )  ; 
}  catch  (  InterruptedException   e  )  { 
e  .  printStackTrace  (  )  ; 
} 
dest  =  new   BufferedImage  (  destWidth  ,  destHeight  ,  BufferedImage  .  TYPE_INT_RGB  )  ; 
Graphics2D   big  =  dest  .  createGraphics  (  )  ; 
big  .  drawImage  (  i2  ,  0  ,  0  ,  comp  )  ; 
} 
try  { 
final   Iterator  <  ImageWriter  >  it  =  ImageIO  .  getImageWritersByFormatName  (  "jpeg"  )  ; 
if  (  it  .  hasNext  (  )  )  { 
final   ImageWriter   writer  =  it  .  next  (  )  ; 
final   ImageOutputStream   ios  =  new   FileImageOutputStream  (  new   File  (  sDest  )  )  ; 
writer  .  setOutput  (  ios  )  ; 
final   JPEGImageWriteParam   iwParam  =  new   JPEGImageWriteParam  (  Locale  .  getDefault  (  )  )  ; 
iwParam  .  setCompressionMode  (  ImageWriteParam  .  MODE_EXPLICIT  )  ; 
iwParam  .  setCompressionQuality  (  quality  )  ; 
writer  .  write  (  null  ,  new   IIOImage  (  dest  ,  null  ,  null  )  ,  iwParam  )  ; 
ios  .  flush  (  )  ; 
ios  .  close  (  )  ; 
}  else  { 
return   false  ; 
} 
}  catch  (  Throwable   t  )  { 
Log  .  log  (  Log  .  ERROR  ,  "lazyj.Utils"  ,  "resize: cannot write to destination file: "  +  sDest  ,  t  )  ; 
return   false  ; 
} 
if  (  bDeleteOriginalFile  )  try  { 
if  (  !  (  new   File  (  sSource  )  )  .  delete  (  )  )  Log  .  log  (  Log  .  WARNING  ,  "lazyj.Utils"  ,  "resize: could not delete original file ("  +  sSource  +  ")"  )  ; 
}  catch  (  SecurityException   se  )  { 
Log  .  log  (  Log  .  ERROR  ,  "lazyj.Utils"  ,  "resize: security constraints prevents file deletion"  )  ; 
} 
return   true  ; 
} 















public   static   final   boolean   compress  (  final   String   sSource  ,  final   String   sDest  ,  final   boolean   bDeleteSourceOnSuccess  )  { 
InputStream   is  =  null  ; 
OutputStream   os  =  null  ; 
try  { 
os  =  new   GZIPOutputStream  (  new   FileOutputStream  (  sDest  )  )  ; 
is  =  new   FileInputStream  (  sSource  )  ; 
final   byte  [  ]  buff  =  new   byte  [  1024  ]  ; 
int   r  ; 
do  { 
r  =  is  .  read  (  buff  )  ; 
if  (  r  >  0  )  os  .  write  (  buff  ,  0  ,  r  )  ; 
}  while  (  r  >  0  )  ; 
is  .  close  (  )  ; 
os  .  flush  (  )  ; 
os  .  close  (  )  ; 
}  catch  (  Throwable   e  )  { 
Log  .  log  (  Log  .  WARNING  ,  "lazyj.Utils"  ,  "compress : cannot compress '"  +  sSource  +  "' to '"  +  sDest  +  "'"  ,  e  )  ; 
return   false  ; 
}  finally  { 
if  (  is  !=  null  )  { 
try  { 
is  .  close  (  )  ; 
}  catch  (  IOException   ioe  )  { 
} 
} 
if  (  os  !=  null  )  { 
try  { 
os  .  close  (  )  ; 
}  catch  (  IOException   ioe  )  { 
} 
} 
} 
if  (  bDeleteSourceOnSuccess  )  try  { 
if  (  !  (  new   File  (  sSource  )  )  .  delete  (  )  )  Log  .  log  (  Log  .  WARNING  ,  "lazyj.Utils"  ,  "compress: could not delete original file ("  +  sSource  +  ")"  )  ; 
}  catch  (  SecurityException   se  )  { 
Log  .  log  (  Log  .  ERROR  ,  "lazyj.Utils"  ,  "compress: security constraints prevents file deletion"  )  ; 
} 
return   true  ; 
} 













public   static   final   boolean   compressToZip  (  final   String   sSource  ,  final   String   sDest  ,  final   boolean   bDeleteSourceOnSuccess  )  { 
ZipOutputStream   os  =  null  ; 
InputStream   is  =  null  ; 
try  { 
os  =  new   ZipOutputStream  (  new   FileOutputStream  (  sDest  )  )  ; 
is  =  new   FileInputStream  (  sSource  )  ; 
final   byte  [  ]  buff  =  new   byte  [  1024  ]  ; 
int   r  ; 
String   sFileName  =  sSource  ; 
if  (  sFileName  .  indexOf  (  '/'  )  >=  0  )  sFileName  =  sFileName  .  substring  (  sFileName  .  lastIndexOf  (  '/'  )  +  1  )  ; 
os  .  putNextEntry  (  new   ZipEntry  (  sFileName  )  )  ; 
while  (  (  r  =  is  .  read  (  buff  )  )  >  0  )  os  .  write  (  buff  ,  0  ,  r  )  ; 
is  .  close  (  )  ; 
os  .  flush  (  )  ; 
os  .  closeEntry  (  )  ; 
os  .  close  (  )  ; 
}  catch  (  Throwable   e  )  { 
Log  .  log  (  Log  .  WARNING  ,  "lazyj.Utils"  ,  "compressToZip : cannot compress '"  +  sSource  +  "' to '"  +  sDest  +  "' because"  ,  e  )  ; 
return   false  ; 
}  finally  { 
if  (  is  !=  null  )  { 
try  { 
is  .  close  (  )  ; 
}  catch  (  IOException   ioe  )  { 
} 
} 
if  (  os  !=  null  )  { 
try  { 
os  .  close  (  )  ; 
}  catch  (  IOException   ioe  )  { 
} 
} 
} 
if  (  bDeleteSourceOnSuccess  )  try  { 
if  (  !  (  new   File  (  sSource  )  )  .  delete  (  )  )  Log  .  log  (  Log  .  WARNING  ,  "lazyj.Utils"  ,  "compressToZip: could not delete original file ("  +  sSource  +  ")"  )  ; 
}  catch  (  SecurityException   se  )  { 
Log  .  log  (  Log  .  ERROR  ,  "lazyj.Utils"  ,  "compressToZip: security constraints prevents file deletion"  )  ; 
} 
return   true  ; 
} 








public   static   final   String   readFile  (  final   String   sFileName  )  { 
return   readFile  (  sFileName  ,  "UTF-8"  )  ; 
} 








public   static   final   String   readFile  (  final   String   sFileName  ,  final   String   charSet  )  { 
final   File   f  =  new   File  (  sFileName  )  ; 
if  (  !  f  .  exists  (  )  ||  !  f  .  canRead  (  )  ||  !  f  .  isFile  (  )  )  { 
Log  .  log  (  Log  .  WARNING  ,  "lazyj.Utils"  ,  "could not read '"  +  sFileName  +  "' because : exists="  +  f  .  exists  (  )  +  ", canread="  +  f  .  canRead  (  )  +  ", isfile="  +  f  .  isFile  (  )  )  ; 
return   null  ; 
} 
FileInputStream   fis  =  null  ; 
try  { 
final   long   len  =  f  .  length  (  )  ; 
final   byte   b  [  ]  =  new   byte  [  (  int  )  len  ]  ; 
fis  =  new   FileInputStream  (  f  )  ; 
int   readLen  =  fis  .  read  (  b  )  ; 
if  (  len  !=  readLen  )  return   null  ; 
return   new   String  (  b  ,  charSet  )  ; 
}  catch  (  IOException   ioe  )  { 
Log  .  log  (  Log  .  WARNING  ,  "lazyj.Utils"  ,  "exception reading from '"  +  sFileName  +  "'"  ,  ioe  )  ; 
return   null  ; 
}  finally  { 
if  (  fis  !=  null  )  try  { 
fis  .  close  (  )  ; 
}  catch  (  IOException   e  )  { 
} 
} 
} 




private   static   final   HashMap  <  String  ,  String  >  HTML_CHAR_MAP  =  new   HashMap  <  String  ,  String  >  (  64  ,  0.95f  )  ; 

static  { 
HTML_CHAR_MAP  .  put  (  "Aacute"  ,  "A"  )  ; 
HTML_CHAR_MAP  .  put  (  "Agrave"  ,  "A"  )  ; 
HTML_CHAR_MAP  .  put  (  "Acirc"  ,  "A"  )  ; 
HTML_CHAR_MAP  .  put  (  "Atilde"  ,  "A"  )  ; 
HTML_CHAR_MAP  .  put  (  "Aring"  ,  "A"  )  ; 
HTML_CHAR_MAP  .  put  (  "Auml"  ,  "A"  )  ; 
HTML_CHAR_MAP  .  put  (  "AElig"  ,  "AE"  )  ; 
HTML_CHAR_MAP  .  put  (  "Ccedil"  ,  "C"  )  ; 
HTML_CHAR_MAP  .  put  (  "Eacute"  ,  "E"  )  ; 
HTML_CHAR_MAP  .  put  (  "Egrave"  ,  "E"  )  ; 
HTML_CHAR_MAP  .  put  (  "Ecirc"  ,  "E"  )  ; 
HTML_CHAR_MAP  .  put  (  "Euml"  ,  "E"  )  ; 
HTML_CHAR_MAP  .  put  (  "Iacute"  ,  "I"  )  ; 
HTML_CHAR_MAP  .  put  (  "Igrave"  ,  "I"  )  ; 
HTML_CHAR_MAP  .  put  (  "Icirc"  ,  "I"  )  ; 
HTML_CHAR_MAP  .  put  (  "Iuml"  ,  "I"  )  ; 
HTML_CHAR_MAP  .  put  (  "Ntilde"  ,  "N"  )  ; 
HTML_CHAR_MAP  .  put  (  "Oacute"  ,  "O"  )  ; 
HTML_CHAR_MAP  .  put  (  "Ograve"  ,  "O"  )  ; 
HTML_CHAR_MAP  .  put  (  "Ocirc"  ,  "O"  )  ; 
HTML_CHAR_MAP  .  put  (  "Otilde"  ,  "O"  )  ; 
HTML_CHAR_MAP  .  put  (  "Ouml"  ,  "O"  )  ; 
HTML_CHAR_MAP  .  put  (  "Oslash"  ,  "O"  )  ; 
HTML_CHAR_MAP  .  put  (  "Uacute"  ,  "U"  )  ; 
HTML_CHAR_MAP  .  put  (  "Ugrave"  ,  "U"  )  ; 
HTML_CHAR_MAP  .  put  (  "Ucirc"  ,  "U"  )  ; 
HTML_CHAR_MAP  .  put  (  "Uuml"  ,  "U"  )  ; 
HTML_CHAR_MAP  .  put  (  "Yacute"  ,  "Y"  )  ; 
HTML_CHAR_MAP  .  put  (  "aacute"  ,  "a"  )  ; 
HTML_CHAR_MAP  .  put  (  "agrave"  ,  "a"  )  ; 
HTML_CHAR_MAP  .  put  (  "acirc"  ,  "a"  )  ; 
HTML_CHAR_MAP  .  put  (  "atilde"  ,  "a"  )  ; 
HTML_CHAR_MAP  .  put  (  "auml"  ,  "a"  )  ; 
HTML_CHAR_MAP  .  put  (  "aelig"  ,  "ae"  )  ; 
HTML_CHAR_MAP  .  put  (  "ccedil"  ,  "c"  )  ; 
HTML_CHAR_MAP  .  put  (  "eacute"  ,  "e"  )  ; 
HTML_CHAR_MAP  .  put  (  "egrave"  ,  "e"  )  ; 
HTML_CHAR_MAP  .  put  (  "ecirc"  ,  "e"  )  ; 
HTML_CHAR_MAP  .  put  (  "euml"  ,  "e"  )  ; 
HTML_CHAR_MAP  .  put  (  "iacute"  ,  "i"  )  ; 
HTML_CHAR_MAP  .  put  (  "igrave"  ,  "i"  )  ; 
HTML_CHAR_MAP  .  put  (  "icirc"  ,  "i"  )  ; 
HTML_CHAR_MAP  .  put  (  "iuml"  ,  "i"  )  ; 
HTML_CHAR_MAP  .  put  (  "ntilde"  ,  "n"  )  ; 
HTML_CHAR_MAP  .  put  (  "oacute"  ,  "o"  )  ; 
HTML_CHAR_MAP  .  put  (  "ograve"  ,  "o"  )  ; 
HTML_CHAR_MAP  .  put  (  "ocirc"  ,  "o"  )  ; 
HTML_CHAR_MAP  .  put  (  "otilde"  ,  "o"  )  ; 
HTML_CHAR_MAP  .  put  (  "ouml"  ,  "o"  )  ; 
HTML_CHAR_MAP  .  put  (  "oslash"  ,  "o"  )  ; 
HTML_CHAR_MAP  .  put  (  "uacute"  ,  "u"  )  ; 
HTML_CHAR_MAP  .  put  (  "ugrave"  ,  "u"  )  ; 
HTML_CHAR_MAP  .  put  (  "ucirc"  ,  "u"  )  ; 
HTML_CHAR_MAP  .  put  (  "uuml"  ,  "u"  )  ; 
HTML_CHAR_MAP  .  put  (  "yacute"  ,  "y"  )  ; 
HTML_CHAR_MAP  .  put  (  "yuml"  ,  "y"  )  ; 
} 








public   static   final   String   toBaseCharacters  (  final   String   sText  )  { 
if  (  sText  ==  null  ||  sText  .  length  (  )  ==  0  )  return   sText  ; 
final   char  [  ]  chars  =  sText  .  toCharArray  (  )  ; 
final   int   iSize  =  chars  .  length  ; 
final   StringBuilder   sb  =  new   StringBuilder  (  iSize  )  ; 
for  (  int   i  =  0  ;  i  <  iSize  ;  i  ++  )  { 
final   char   c  =  chars  [  i  ]  ; 
if  (  c  ==  '&'  &&  i  <  iSize  -  1  &&  (  (  chars  [  i  +  1  ]  >=  'a'  &&  chars  [  i  +  1  ]  <=  'z'  )  ||  (  chars  [  i  +  1  ]  >=  'A'  &&  chars  [  i  +  1  ]  <=  'Z'  )  )  )  { 
final   int   idx  =  sText  .  indexOf  (  ';'  ,  i  +  1  )  ; 
if  (  idx  >  0  &&  idx  -  i  <  9  )  { 
final   String   s  =  sText  .  substring  (  i  +  1  ,  idx  )  ; 
final   String   v  =  HTML_CHAR_MAP  .  get  (  s  )  ; 
if  (  v  !=  null  )  { 
sb  .  append  (  v  )  ; 
i  +=  s  .  length  (  )  +  1  ; 
continue  ; 
} 
} 
} 
if  (  c  ==  '&'  &&  i  <  iSize  -  2  &&  chars  [  i  +  1  ]  ==  '#'  &&  (  chars  [  i  +  2  ]  >=  '0'  &&  chars  [  i  +  2  ]  <=  '9'  )  )  { 
final   int   idx  =  sText  .  indexOf  (  ';'  ,  i  +  1  )  ; 
if  (  idx  >  0  &&  idx  -  i  <  9  )  { 
final   String   s  =  sText  .  substring  (  i  +  2  ,  idx  )  ; 
try  { 
final   char   ch  =  (  char  )  Integer  .  parseInt  (  s  )  ; 
sb  .  append  (  ch  )  ; 
}  catch  (  NumberFormatException   nfe  )  { 
sb  .  append  (  "&#"  )  .  append  (  s  )  .  append  (  ';'  )  ; 
} 
} 
} 
switch  (  c  )  { 
case   0xA9  : 
sb  .  append  (  "(C)"  )  ; 
continue  ; 
case  '—'  : 
sb  .  append  (  '-'  )  ; 
continue  ; 
case  '’'  : 
sb  .  append  (  '\''  )  ; 
continue  ; 
case  '“'  : 
case  '”'  : 
sb  .  append  (  '"'  )  ; 
continue  ; 
case  '…'  : 
sb  .  append  (  "..."  )  ; 
continue  ; 
} 
String   sLetter  =  new   String  (  new   char  [  ]  {  c  }  )  ; 
sLetter  =  Normalizer  .  normalize  (  sLetter  ,  Normalizer  .  Form  .  NFD  )  ; 
try  { 
byte  [  ]  bLetter  =  sLetter  .  getBytes  (  "UTF-8"  )  ; 
sb  .  append  (  (  char  )  bLetter  [  0  ]  )  ; 
}  catch  (  UnsupportedEncodingException   e  )  { 
} 
} 
return   sb  .  toString  (  )  ; 
} 








public   static   final   boolean   copyFile  (  final   String   sSource  ,  final   String   sDest  )  { 
InputStream   is  =  null  ; 
OutputStream   os  =  null  ; 
try  { 
is  =  new   FileInputStream  (  sSource  )  ; 
os  =  new   FileOutputStream  (  sDest  )  ; 
final   byte  [  ]  buff  =  new   byte  [  16  *  1024  ]  ; 
int   len  ; 
while  (  (  len  =  is  .  read  (  buff  )  )  >  0  )  { 
os  .  write  (  buff  ,  0  ,  len  )  ; 
} 
os  .  flush  (  )  ; 
os  .  close  (  )  ; 
os  =  null  ; 
is  .  close  (  )  ; 
is  =  null  ; 
return   true  ; 
}  catch  (  Throwable   t  )  { 
return   false  ; 
}  finally  { 
if  (  is  !=  null  )  { 
try  { 
is  .  close  (  )  ; 
}  catch  (  Exception   e  )  { 
} 
} 
if  (  os  !=  null  )  { 
try  { 
os  .  close  (  )  ; 
}  catch  (  Exception   e  )  { 
} 
} 
} 
} 








public   static   byte  [  ]  compress  (  final   byte  [  ]  buffer  )  { 
try  { 
final   ByteArrayOutputStream   baos  =  new   ByteArrayOutputStream  (  )  ; 
final   GZIPOutputStream   gzipos  =  new   GZIPOutputStream  (  baos  )  ; 
gzipos  .  write  (  buffer  )  ; 
gzipos  .  flush  (  )  ; 
gzipos  .  close  (  )  ; 
return   baos  .  toByteArray  (  )  ; 
}  catch  (  IOException   ioe  )  { 
return   null  ; 
} 
} 








public   static   byte  [  ]  uncompress  (  final   byte  [  ]  buffer  )  { 
try  { 
final   GZIPInputStream   gzipis  =  new   GZIPInputStream  (  new   ByteArrayInputStream  (  buffer  )  )  ; 
final   ByteArrayOutputStream   baos  =  new   ByteArrayOutputStream  (  )  ; 
final   byte  [  ]  tmp  =  new   byte  [  1024  ]  ; 
int   len  ; 
while  (  (  len  =  gzipis  .  read  (  tmp  )  )  >  0  )  { 
baos  .  write  (  tmp  ,  0  ,  len  )  ; 
} 
baos  .  flush  (  )  ; 
return   baos  .  toByteArray  (  )  ; 
}  catch  (  IOException   ioe  )  { 
return   null  ; 
} 
} 




private   static   final   ExpirationCache  <  String  ,  String  >  ipCache  =  new   ExpirationCache  <  String  ,  String  >  (  1024  )  ; 




public   static   void   clearCaches  (  )  { 
PageCache  .  clear  (  )  ; 
BasePage  .  clear  (  )  ; 
FrameworkStats  .  clear  (  )  ; 
DBSession  .  clear  (  )  ; 
} 







public   static   String   getHostName  (  final   String   ip  )  { 
if  (  ip  ==  null  ||  ip  .  length  (  )  <=  0  )  return   ip  ; 
final   String   sIP  =  ip  .  toLowerCase  (  Locale  .  getDefault  (  )  )  ; 
final   String   sName  =  ipCache  .  get  (  sIP  )  ; 
if  (  sName  !=  null  )  return   sName  ; 
final   InetAddress   addr  ; 
try  { 
addr  =  InetAddress  .  getByName  (  sIP  )  ; 
}  catch  (  Exception   e  )  { 
ipCache  .  put  (  sIP  ,  sIP  ,  1000  *  60  *  10  )  ; 
return   sIP  ; 
} 
try  { 
final   String   sTemp  =  addr  .  getCanonicalHostName  (  )  .  toLowerCase  (  Locale  .  getDefault  (  )  )  ; 
if  (  sTemp  !=  null  &&  !  sTemp  .  equals  (  sIP  )  )  { 
ipCache  .  put  (  sIP  ,  sTemp  ,  1000  *  60  *  120  )  ; 
return   sTemp  ; 
} 
}  catch  (  Throwable   t  )  { 
} 
try  { 
final   String   sTemp  =  addr  .  getHostName  (  )  .  toLowerCase  (  Locale  .  getDefault  (  )  )  ; 
if  (  sTemp  !=  null  &&  !  sTemp  .  equals  (  sIP  )  )  { 
ipCache  .  put  (  sIP  ,  sTemp  ,  1000  *  60  *  120  )  ; 
return   sTemp  ; 
} 
}  catch  (  Throwable   e  )  { 
} 
ipCache  .  put  (  sIP  ,  sIP  ,  1000  *  60  *  10  )  ; 
return   sIP  ; 
} 




private   static   final   Pattern   PATTERN_HTML_COMMENT  =  Pattern  .  compile  (  "<!--.*?-->"  ,  Pattern  .  DOTALL  )  ; 




private   static   final   Pattern   PATTERN_HTML_SCRIPT  =  Pattern  .  compile  (  "<script.*?</script.*?>"  ,  Pattern  .  DOTALL  |  Pattern  .  CASE_INSENSITIVE  )  ; 




private   static   final   Pattern   PATTERN_HTML_STYLE  =  Pattern  .  compile  (  "<style.*?</style.*?>"  ,  Pattern  .  DOTALL  |  Pattern  .  CASE_INSENSITIVE  )  ; 




private   static   final   Pattern   PATTERN_HTML_HEAD  =  Pattern  .  compile  (  "<head.*?</head.*?>"  ,  Pattern  .  DOTALL  |  Pattern  .  CASE_INSENSITIVE  )  ; 




private   static   final   Pattern   PATTERN_HTML_SPACES  =  Pattern  .  compile  (  "\\s+"  ,  Pattern  .  DOTALL  )  ; 




private   static   final   Pattern   PATTERN_HTML_BR  =  Pattern  .  compile  (  "<(ul|br|/li|/option|/div|table|/tr).*?>"  ,  Pattern  .  DOTALL  |  Pattern  .  CASE_INSENSITIVE  )  ; 




private   static   final   Pattern   PATTERN_HTML_TAG  =  Pattern  .  compile  (  "<.*?>"  ,  Pattern  .  DOTALL  )  ; 




private   static   final   Pattern   PATTERN_HTML_BULL  =  Pattern  .  compile  (  "<(li|option).*?>"  ,  Pattern  .  DOTALL  |  Pattern  .  CASE_INSENSITIVE  )  ; 




private   static   final   Pattern   PATTERN_HTML_SPECIAL  =  Pattern  .  compile  (  "&[a-zA-Z]{2,6};"  ,  Pattern  .  DOTALL  |  Pattern  .  CASE_INSENSITIVE  )  ; 




private   static   final   Pattern   PATTERN_HTML_LINES  =  Pattern  .  compile  (  "\\s*\n\\s*"  )  ; 




private   static   final   Pattern   PATTERN_HTML_TRIM  =  Pattern  .  compile  (  "(^\\s+)|(\\s+$)"  )  ; 









public   static   String   htmlToText  (  final   String   sHTML  )  { 
return   htmlToText  (  sHTML  ,  0  )  ; 
} 




public   static   final   int   HTML_OPTION_KEEP_SPACES  =  1  ; 




public   static   final   int   HTML_OPTION_KEEP_NEWLINES  =  2  ; 









public   static   String   htmlToText  (  final   String   sHTML  ,  final   int   options  )  { 
Matcher   m  =  PATTERN_HTML_COMMENT  .  matcher  (  sHTML  )  ; 
String   s  =  m  .  replaceAll  (  ""  )  ; 
m  =  PATTERN_HTML_SCRIPT  .  matcher  (  s  )  ; 
s  =  m  .  replaceAll  (  ""  )  ; 
m  =  PATTERN_HTML_STYLE  .  matcher  (  s  )  ; 
s  =  m  .  replaceAll  (  ""  )  ; 
m  =  PATTERN_HTML_HEAD  .  matcher  (  s  )  ; 
s  =  m  .  replaceAll  (  ""  )  ; 
if  (  (  options  &  HTML_OPTION_KEEP_SPACES  )  ==  0  )  { 
m  =  PATTERN_HTML_SPACES  .  matcher  (  s  )  ; 
s  =  m  .  replaceAll  (  " "  )  ; 
} 
m  =  PATTERN_HTML_BR  .  matcher  (  s  )  ; 
s  =  m  .  replaceAll  (  "\n"  )  ; 
m  =  PATTERN_HTML_BULL  .  matcher  (  s  )  ; 
s  =  m  .  replaceAll  (  "- "  )  ; 
m  =  PATTERN_HTML_TAG  .  matcher  (  s  )  ; 
s  =  m  .  replaceAll  (  ""  )  ; 
if  (  s  .  indexOf  (  "&"  )  >=  0  )  { 
s  =  s  .  replace  (  "&lt;"  ,  "<"  )  ; 
s  =  s  .  replace  (  "&gt;"  ,  ">"  )  ; 
s  =  s  .  replace  (  "&raquo;"  ,  ">>"  )  ; 
s  =  s  .  replace  (  "&laquo;"  ,  "<<"  )  ; 
s  =  s  .  replace  (  "&copy;"  ,  "(c)"  )  ; 
s  =  s  .  replace  (  "&amp;"  ,  "&"  )  ; 
s  =  s  .  replace  (  "&ndash;"  ,  "-"  )  ; 
s  =  s  .  replace  (  "&mdash;"  ,  "-"  )  ; 
s  =  s  .  replace  (  "&quot;"  ,  "\""  )  ; 
s  =  s  .  replace  (  "&hellip;"  ,  "..."  )  ; 
s  =  s  .  replace  (  "&rsquo;"  ,  "'"  )  ; 
s  =  s  .  replace  (  "&lsquo;"  ,  "`"  )  ; 
s  =  s  .  replace  (  "&rdquo;"  ,  "\""  )  ; 
s  =  s  .  replace  (  "&ldquo;"  ,  "\""  )  ; 
if  (  s  .  indexOf  (  "&"  )  >=  0  )  { 
for  (  Map  .  Entry  <  String  ,  String  >  me  :  HTML_CHAR_MAP  .  entrySet  (  )  )  { 
s  =  s  .  replace  (  '&'  +  me  .  getKey  (  )  +  ';'  ,  me  .  getValue  (  )  )  ; 
} 
} 
} 
int   idxStart  =  s  .  indexOf  (  "&#"  )  ; 
while  (  idxStart  >  0  )  { 
final   int   idx  =  s  .  indexOf  (  ';'  ,  idxStart  +  2  )  ; 
if  (  idx  >  0  &&  idx  -  idxStart  <  9  )  { 
final   String   v  =  s  .  substring  (  idxStart  +  2  ,  idx  )  ; 
try  { 
final   char   c  =  (  char  )  Integer  .  parseInt  (  v  )  ; 
s  =  Format  .  replace  (  s  ,  "&#"  +  v  +  ";"  ,  String  .  valueOf  (  c  )  )  ; 
idxStart  =  s  .  indexOf  (  "&#"  )  ; 
continue  ; 
}  catch  (  NumberFormatException   nfe  )  { 
} 
} 
idxStart  =  s  .  indexOf  (  "&#"  ,  idxStart  +  2  )  ; 
} 
if  (  (  options  &  HTML_OPTION_KEEP_NEWLINES  )  ==  0  )  { 
m  =  PATTERN_HTML_LINES  .  matcher  (  s  )  ; 
s  =  m  .  replaceAll  (  "\n"  )  ; 
} 
m  =  PATTERN_HTML_TRIM  .  matcher  (  s  )  ; 
s  =  m  .  replaceAll  (  ""  )  ; 
s  =  s  .  replace  (  "&nbsp;"  ,  " "  )  ; 
m  =  PATTERN_HTML_SPECIAL  .  matcher  (  s  )  ; 
s  =  m  .  replaceAll  (  ""  )  ; 
return   s  ; 
} 











public   static   String   download  (  final   String   sURL  ,  final   String   sFilename  )  throws   IOException  { 
final   URL   u  =  new   URL  (  sURL  )  ; 
final   URLConnection   conn  =  u  .  openConnection  (  )  ; 
final   InputStream   is  =  conn  .  getInputStream  (  )  ; 
final   byte  [  ]  buff  =  new   byte  [  10240  ]  ; 
int   read  ; 
final   OutputStream   os  ; 
String   actualFileName  =  sFilename  ; 
if  (  sFilename  !=  null  )  { 
File   f  =  new   File  (  sFilename  )  ; 
if  (  f  .  exists  (  )  &&  f  .  isDirectory  (  )  )  { 
String   sLastPart  =  sURL  ; 
int   idx  =  sLastPart  .  indexOf  (  '?'  )  ; 
if  (  idx  >=  0  )  sLastPart  =  sLastPart  .  substring  (  0  ,  idx  )  ; 
idx  =  sLastPart  .  lastIndexOf  (  '/'  )  ; 
if  (  idx  >=  0  )  sLastPart  =  sLastPart  .  substring  (  idx  +  1  )  ; 
f  =  new   File  (  f  ,  sLastPart  )  ; 
actualFileName  =  f  .  getCanonicalPath  (  )  ; 
} 
os  =  new   FileOutputStream  (  f  )  ; 
}  else  { 
os  =  new   ByteArrayOutputStream  (  )  ; 
} 
try  { 
while  (  (  read  =  is  .  read  (  buff  )  )  >  0  )  { 
os  .  write  (  buff  ,  0  ,  read  )  ; 
} 
}  finally  { 
os  .  close  (  )  ; 
} 
is  .  close  (  )  ; 
if  (  os   instanceof   ByteArrayOutputStream  )  { 
String   encoding  =  conn  .  getContentEncoding  (  )  ; 
if  (  encoding  ==  null  )  encoding  =  "UTF-8"  ; 
final   byte  [  ]  content  =  (  (  ByteArrayOutputStream  )  os  )  .  toByteArray  (  )  ; 
try  { 
return   new   String  (  content  ,  encoding  )  ; 
}  catch  (  final   UnsupportedEncodingException   uee  )  { 
return   new   String  (  content  )  ; 
} 
} 
return   actualFileName  ; 
} 









public   static   boolean   stringToBool  (  final   String   s  ,  final   boolean   bDefault  )  { 
if  (  s  !=  null  &&  s  .  length  (  )  >  0  )  { 
final   char   c  =  s  .  charAt  (  0  )  ; 
if  (  c  ==  't'  ||  c  ==  'T'  ||  c  ==  'y'  ||  c  ==  'Y'  ||  c  ==  '1'  )  return   true  ; 
if  (  c  ==  'f'  ||  c  ==  'F'  ||  c  ==  'n'  ||  c  ==  'N'  ||  c  ==  '0'  )  return   false  ; 
} 
return   bDefault  ; 
} 










private   static   byte  [  ]  blowfishCrypt  (  final   byte  [  ]  input  ,  final   byte  [  ]  key  ,  final   int   mode  )  throws   Exception  { 
final   SecretKeySpec   skeySpec  =  new   SecretKeySpec  (  key  ,  "Blowfish"  )  ; 
final   Cipher   cipher  =  Cipher  .  getInstance  (  "Blowfish/ECB/PKCS5Padding"  )  ; 
cipher  .  init  (  mode  ,  skeySpec  )  ; 
final   ByteArrayOutputStream   bos  =  new   ByteArrayOutputStream  (  )  ; 
final   ByteArrayInputStream   bis  =  new   ByteArrayInputStream  (  input  )  ; 
final   CipherOutputStream   cos  =  new   CipherOutputStream  (  bos  ,  cipher  )  ; 
int   length  =  0  ; 
final   byte  [  ]  buffer  =  new   byte  [  1024  ]  ; 
while  (  (  length  =  bis  .  read  (  buffer  )  )  !=  -  1  )  { 
cos  .  write  (  buffer  ,  0  ,  length  )  ; 
} 
bis  .  close  (  )  ; 
cos  .  close  (  )  ; 
return   bos  .  toByteArray  (  )  ; 
} 








public   static   byte  [  ]  blowfishCrypt  (  final   byte  [  ]  input  ,  final   byte  [  ]  key  )  { 
try  { 
return   blowfishCrypt  (  input  ,  key  ,  Cipher  .  ENCRYPT_MODE  )  ; 
}  catch  (  Exception   e  )  { 
return   null  ; 
} 
} 








public   static   String   blowfishCrypt  (  final   String   input  ,  final   String   key  )  { 
try  { 
return   base64Encode  (  blowfishCrypt  (  input  .  getBytes  (  )  ,  key  .  getBytes  (  )  )  )  ; 
}  catch  (  Exception   e  )  { 
return   null  ; 
} 
} 








public   static   byte  [  ]  blowfishDecrypt  (  final   byte  [  ]  input  ,  final   byte  [  ]  key  )  { 
try  { 
return   blowfishCrypt  (  input  ,  key  ,  Cipher  .  DECRYPT_MODE  )  ; 
}  catch  (  Exception   e  )  { 
return   null  ; 
} 
} 








public   static   String   blowfishDecrypt  (  final   String   input  ,  final   String   key  )  { 
try  { 
return   new   String  (  blowfishDecrypt  (  base64Decode  (  input  )  ,  key  .  getBytes  (  )  )  )  ; 
}  catch  (  Exception   e  )  { 
return   null  ; 
} 
} 




public   static   void   main  (  String  [  ]  args  )  { 
String   orig  =  "something longer to make more sense and make sure the line doesn't end at the base64 boundaries"  ; 
String   key  =  "blabla"  ; 
byte  [  ]  input  =  orig  .  getBytes  (  )  ; 
byte  [  ]  k  =  key  .  getBytes  (  )  ; 
byte  [  ]  enc  =  blowfishCrypt  (  input  ,  k  )  ; 
System  .  err  .  println  (  "Original: "  +  input  .  length  +  ", enc: "  +  enc  .  length  )  ; 
System  .  err  .  println  (  blowfishDecrypt  (  blowfishCrypt  (  orig  ,  key  )  ,  key  )  )  ; 
String   base64  =  base64Encode  (  enc  )  ; 
System  .  err  .  println  (  "Encoded : "  +  base64  )  ; 
} 





public   static   class   Base64Coder   extends   AbstractPreferences  { 




private   String   store  ; 




private   static   Base64Coder   instance  =  new   Base64Coder  (  )  ; 




private   Base64Coder  (  )  { 
super  (  null  ,  ""  )  ; 
} 







public   static   synchronized   String   encode  (  final   byte  [  ]  b  )  { 
instance  .  putByteArray  (  null  ,  b  )  ; 
return   instance  .  get  (  null  ,  null  )  ; 
} 







public   static   synchronized   byte  [  ]  decode  (  final   String   base64String  )  { 
instance  .  put  (  null  ,  base64String  )  ; 
return   instance  .  getByteArray  (  null  ,  null  )  ; 
} 

@  Override 
public   String   get  (  final   String   key  ,  final   String   def  )  { 
return   this  .  store  ; 
} 

@  Override 
public   void   put  (  String   key  ,  String   value  )  { 
this  .  store  =  value  ; 
} 

@  Override 
protected   AbstractPreferences   childSpi  (  String   name  )  { 
return   null  ; 
} 

@  Override 
protected   void   putSpi  (  String   key  ,  String   value  )  { 
} 

@  Override 
protected   String   getSpi  (  String   key  )  { 
return   null  ; 
} 

@  Override 
protected   void   removeSpi  (  String   key  )  { 
} 

@  Override 
protected   String  [  ]  keysSpi  (  )  throws   BackingStoreException  { 
return   null  ; 
} 

@  Override 
protected   String  [  ]  childrenNamesSpi  (  )  throws   BackingStoreException  { 
return   null  ; 
} 

@  Override 
protected   void   syncSpi  (  )  throws   BackingStoreException  { 
} 

@  Override 
protected   void   removeNodeSpi  (  )  throws   BackingStoreException  { 
} 

@  Override 
protected   void   flushSpi  (  )  throws   BackingStoreException  { 
} 
} 








public   static   String   base64Encode  (  final   byte  [  ]  b  )  { 
final   String   encoded  =  Base64Coder  .  encode  (  b  )  ; 
if  (  encoded  .  indexOf  (  '\n'  )  >=  0  ||  encoded  .  indexOf  (  '\r'  )  >=  0  )  { 
final   char  [  ]  chars  =  encoded  .  toCharArray  (  )  ; 
final   StringBuilder   sb  =  new   StringBuilder  (  chars  .  length  )  ; 
for  (  final   char   c  :  chars  )  { 
if  (  c  !=  '\r'  &&  c  !=  '\n'  )  sb  .  append  (  c  )  ; 
} 
return   sb  .  toString  (  )  ; 
} 
return   encoded  ; 
} 







public   static   byte  [  ]  base64Decode  (  final   String   data  )  { 
return   Base64Coder  .  decode  (  data  )  ; 
} 










public   static   Iterable  <  String  >  getFileLines  (  final   String   file  )  throws   IOException  { 
return   new   FileLinesIterable  (  new   BufferedReader  (  new   FileReader  (  file  )  )  )  ; 
} 
} 

