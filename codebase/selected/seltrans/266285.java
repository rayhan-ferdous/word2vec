package   com  .  google  .  typography  .  font  .  sfntly  .  testutils  ; 

import   com  .  google  .  typography  .  font  .  sfntly  .  Font  ; 
import   com  .  google  .  typography  .  font  .  sfntly  .  Font  .  Builder  ; 
import   com  .  google  .  typography  .  font  .  sfntly  .  FontFactory  ; 
import   com  .  google  .  typography  .  font  .  sfntly  .  Tag  ; 
import   com  .  google  .  typography  .  font  .  sfntly  .  table  .  FontDataTable  ; 
import   com  .  google  .  typography  .  font  .  sfntly  .  table  .  Header  ; 
import   com  .  google  .  typography  .  font  .  sfntly  .  table  .  Table  ; 
import   com  .  google  .  typography  .  font  .  sfntly  .  table  .  core  .  CMap  ; 
import   com  .  google  .  typography  .  font  .  sfntly  .  table  .  core  .  CMapTable  ; 
import   com  .  google  .  typography  .  font  .  sfntly  .  table  .  core  .  FontHeaderTable  ; 
import   com  .  google  .  typography  .  font  .  sfntly  .  table  .  core  .  MaximumProfileTable  ; 
import   com  .  google  .  typography  .  font  .  sfntly  .  table  .  core  .  NameTable  ; 
import   com  .  google  .  typography  .  font  .  sfntly  .  table  .  core  .  NameTable  .  NameEntry  ; 
import   java  .  io  .  File  ; 
import   java  .  io  .  FileInputStream  ; 
import   java  .  io  .  IOException  ; 
import   java  .  io  .  InputStream  ; 
import   java  .  io  .  OutputStream  ; 
import   java  .  util  .  Comparator  ; 
import   java  .  util  .  Set  ; 
import   java  .  util  .  TreeSet  ; 
import   java  .  util  .  logging  .  Logger  ; 




public   class   TestFontUtils  { 

private   static   final   Logger   logger  =  Logger  .  getLogger  (  Font  .  class  .  getCanonicalName  (  )  )  ; 

private   TestFontUtils  (  )  { 
} 








public   static   Font  [  ]  loadFont  (  File   file  )  throws   IOException  { 
return   loadFont  (  file  ,  true  )  ; 
} 









public   static   Font  [  ]  loadFont  (  File   file  ,  boolean   fingerprint  )  throws   IOException  { 
FontFactory   fontFactory  =  FontFactory  .  getInstance  (  )  ; 
fontFactory  .  fingerprintFont  (  fingerprint  )  ; 
FileInputStream   is  =  null  ; 
try  { 
is  =  new   FileInputStream  (  file  )  ; 
return   fontFactory  .  loadFonts  (  is  )  ; 
}  finally  { 
if  (  is  !=  null  )  { 
is  .  close  (  )  ; 
} 
} 
} 









public   static   Font  [  ]  loadFont  (  File   file  ,  byte  [  ]  ...  expectedFingerprints  )  throws   IOException  { 
Font  [  ]  fonts  =  loadFont  (  file  ,  true  )  ; 
for  (  int   i  =  0  ;  i  <  Math  .  min  (  fonts  .  length  ,  expectedFingerprints  .  length  )  ;  i  ++  )  { 
if  (  expectedFingerprints  [  i  ]  !=  fonts  [  i  ]  .  digest  (  )  )  { 
throw   new   IOException  (  "Did not get the expected fingerprint for font#"  +  i  +  " in "  +  file  .  getPath  (  )  +  " Has the file changed?"  )  ; 
} 
} 
return   fonts  ; 
} 








public   static   Font  [  ]  loadFontUsingByteArray  (  File   file  )  throws   IOException  { 
return   loadFontUsingByteArray  (  file  ,  true  )  ; 
} 









public   static   Font  [  ]  loadFontUsingByteArray  (  File   file  ,  boolean   fingerprint  )  throws   IOException  { 
byte  [  ]  b  =  TestUtils  .  readFile  (  file  )  ; 
FontFactory   fontFactory  =  FontFactory  .  getInstance  (  )  ; 
fontFactory  .  fingerprintFont  (  fingerprint  )  ; 
return   fontFactory  .  loadFonts  (  b  )  ; 
} 

private   static   final   Comparator  <  Table  >  TABLE_COMPARATOR_BY_OFFSET  =  new   Comparator  <  Table  >  (  )  { 

@  Override 
public   int   compare  (  Table   o1  ,  Table   o2  )  { 
return   o1  .  header  (  )  .  offset  (  )  -  o2  .  header  (  )  .  offset  (  )  ; 
} 
}  ; 

public   static   Font  [  ]  buildAndCheckFont  (  FontFactory   fontFactory  ,  InputStream   is  ,  int   debug  )  throws   IOException  { 
Font  [  ]  fontArray  =  fontFactory  .  loadFonts  (  is  )  ; 
for  (  Font   font  :  fontArray  )  { 
if  (  debug  >=  1  )  { 
logger  .  info  (  font  .  toString  (  )  )  ; 
} 
FontHeaderTable   head  =  font  .  getTable  (  Tag  .  head  )  ; 
if  (  head  !=  null  )  { 
long   magicNumber  =  head  .  magicNumber  (  )  ; 
if  (  0x5F0F3CF5  !=  magicNumber  )  { 
throw   new   RuntimeException  (  "Magic number is incorrect."  )  ; 
} 
if  (  debug  >=  2  )  { 
logger  .  fine  (  "magic number = "  +  Long  .  toHexString  (  magicNumber  )  )  ; 
MaximumProfileTable   maxp  =  font  .  getTable  (  Tag  .  maxp  )  ; 
logger  .  fine  (  "Number of glyphs = "  +  maxp  .  numGlyphs  (  )  )  ; 
logger  .  fine  (  "\n------ Tables by File Location"  )  ; 
} 
}  else  { 
FontDataTable   bhed  =  font  .  getTable  (  Tag  .  intValue  (  "bhed"  )  )  ; 
if  (  bhed  ==  null  )  { 
throw   new   RuntimeException  (  "No head table or bhed"  )  ; 
} 
} 
Set  <  Table  >  tables  =  new   TreeSet  <  Table  >  (  TABLE_COMPARATOR_BY_OFFSET  )  ; 
tables  .  addAll  (  font  .  tableMap  (  )  .  values  (  )  )  ; 
for  (  Table   table  :  tables  )  { 
if  (  debug  >=  2  )  { 
System  .  out  .  println  (  table  )  ; 
} 
Header   header  =  table  .  header  (  )  ; 
boolean   csValid  =  table  .  calculatedChecksum  (  )  ==  header  .  checksum  (  )  ; 
if  (  !  csValid  )  { 
logger  .  severe  (  "\t**** Checksum not valid!"  )  ; 
logger  .  severe  (  "\tcalculated = 0x"  +  Long  .  toHexString  (  table  .  calculatedChecksum  (  )  )  )  ; 
logger  .  severe  (  "\tread       = 0x"  +  Long  .  toHexString  (  header  .  checksum  (  )  )  )  ; 
throw   new   RuntimeException  (  "Checksum for table "  +  Tag  .  stringValue  (  header  .  tag  (  )  )  )  ; 
} 
} 
if  (  debug  >=  2  )  { 
logger  .  fine  (  "\n------ CMap Tables"  )  ; 
CMapTable   cmap  =  (  CMapTable  )  font  .  getTable  (  Tag  .  cmap  )  ; 
for  (  CMap   cmt  :  cmap  )  { 
logger  .  fine  (  cmt  .  toString  (  )  )  ; 
for  (  int   c  =  0  ;  c  <  256  ;  c  ++  )  { 
logger  .  finer  (  c  +  " = "  +  cmt  .  glyphId  (  c  )  )  ; 
} 
} 
logger  .  fine  (  "\n----- Name Tables"  )  ; 
NameTable   name  =  (  NameTable  )  font  .  getTable  (  Tag  .  name  )  ; 
for  (  NameEntry   entry  :  name  )  { 
logger  .  finer  (  entry  .  toString  (  )  )  ; 
} 
} 
} 
return   fontArray  ; 
} 

public   static   File   serializeFont  (  File   fontFile  )  throws   Exception  { 
Font  [  ]  fonts  =  TestFontUtils  .  loadFont  (  fontFile  )  ; 
if  (  fonts  ==  null  ||  fonts  .  length  !=  1  )  { 
return   null  ; 
} 
String   extension  =  TestUtils  .  extension  (  fontFile  )  ; 
return   serializeFont  (  fonts  [  0  ]  ,  extension  )  ; 
} 

public   static   File   serializeFont  (  Font   font  ,  String   extension  )  throws   Exception  { 
File   serializedFontFile  =  File  .  createTempFile  (  "serializedFont_"  ,  extension  )  ; 
return   TestFontUtils  .  serializeFont  (  font  ,  serializedFontFile  )  ; 
} 

public   static   File   serializeFont  (  Font   font  ,  File   serializedFontFile  )  throws   Exception  { 
serializedFontFile  .  mkdirs  (  )  ; 
serializedFontFile  .  createNewFile  (  )  ; 
OutputStream   os  =  null  ; 
try  { 
os  =  TestUtils  .  createOutputStream  (  serializedFontFile  )  ; 
FontFactory   factory  =  FontFactory  .  getInstance  (  )  ; 
factory  .  serializeFont  (  font  ,  os  )  ; 
}  finally  { 
if  (  os  !=  null  )  { 
os  .  close  (  )  ; 
} 
} 
return   serializedFontFile  ; 
} 

public   static   Builder   builderForFontFile  (  File   fontFile  )  throws   IOException  { 
return   builderForFontFile  (  fontFile  ,  true  )  ; 
} 

public   static   Builder   builderForFontFile  (  File   fontFile  ,  boolean   fingerprint  )  throws   IOException  { 
FileInputStream   is  =  new   FileInputStream  (  fontFile  )  ; 
FontFactory   otfFactory  =  FontFactory  .  getInstance  (  )  ; 
otfFactory  .  fingerprintFont  (  fingerprint  )  ; 
Builder  [  ]  builder  =  otfFactory  .  loadFontsForBuilding  (  is  )  ; 
return   builder  [  0  ]  ; 
} 
} 

