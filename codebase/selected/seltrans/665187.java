package   net  .  iharder  .  xmlizable  ; 
























public   class   Base64  { 


public   static   final   boolean   ENCODE  =  true  ; 


public   static   final   boolean   DECODE  =  false  ; 


private   static   final   int   MAX_LINE_LENGTH  =  76  ; 


private   static   final   byte   EQUALS_SIGN  =  (  byte  )  '='  ; 


private   static   final   byte   NEW_LINE  =  (  byte  )  '\n'  ; 


private   static   final   byte  [  ]  ALPHABET  =  {  (  byte  )  'A'  ,  (  byte  )  'B'  ,  (  byte  )  'C'  ,  (  byte  )  'D'  ,  (  byte  )  'E'  ,  (  byte  )  'F'  ,  (  byte  )  'G'  ,  (  byte  )  'H'  ,  (  byte  )  'I'  ,  (  byte  )  'J'  ,  (  byte  )  'K'  ,  (  byte  )  'L'  ,  (  byte  )  'M'  ,  (  byte  )  'N'  ,  (  byte  )  'O'  ,  (  byte  )  'P'  ,  (  byte  )  'Q'  ,  (  byte  )  'R'  ,  (  byte  )  'S'  ,  (  byte  )  'T'  ,  (  byte  )  'U'  ,  (  byte  )  'V'  ,  (  byte  )  'W'  ,  (  byte  )  'X'  ,  (  byte  )  'Y'  ,  (  byte  )  'Z'  ,  (  byte  )  'a'  ,  (  byte  )  'b'  ,  (  byte  )  'c'  ,  (  byte  )  'd'  ,  (  byte  )  'e'  ,  (  byte  )  'f'  ,  (  byte  )  'g'  ,  (  byte  )  'h'  ,  (  byte  )  'i'  ,  (  byte  )  'j'  ,  (  byte  )  'k'  ,  (  byte  )  'l'  ,  (  byte  )  'm'  ,  (  byte  )  'n'  ,  (  byte  )  'o'  ,  (  byte  )  'p'  ,  (  byte  )  'q'  ,  (  byte  )  'r'  ,  (  byte  )  's'  ,  (  byte  )  't'  ,  (  byte  )  'u'  ,  (  byte  )  'v'  ,  (  byte  )  'w'  ,  (  byte  )  'x'  ,  (  byte  )  'y'  ,  (  byte  )  'z'  ,  (  byte  )  '0'  ,  (  byte  )  '1'  ,  (  byte  )  '2'  ,  (  byte  )  '3'  ,  (  byte  )  '4'  ,  (  byte  )  '5'  ,  (  byte  )  '6'  ,  (  byte  )  '7'  ,  (  byte  )  '8'  ,  (  byte  )  '9'  ,  (  byte  )  '+'  ,  (  byte  )  '/'  }  ; 





private   static   final   byte  [  ]  DECODABET  =  {  -  9  ,  -  9  ,  -  9  ,  -  9  ,  -  9  ,  -  9  ,  -  9  ,  -  9  ,  -  9  ,  -  5  ,  -  5  ,  -  9  ,  -  9  ,  -  5  ,  -  9  ,  -  9  ,  -  9  ,  -  9  ,  -  9  ,  -  9  ,  -  9  ,  -  9  ,  -  9  ,  -  9  ,  -  9  ,  -  9  ,  -  9  ,  -  9  ,  -  9  ,  -  9  ,  -  9  ,  -  9  ,  -  5  ,  -  9  ,  -  9  ,  -  9  ,  -  9  ,  -  9  ,  -  9  ,  -  9  ,  -  9  ,  -  9  ,  -  9  ,  62  ,  -  9  ,  -  9  ,  -  9  ,  63  ,  52  ,  53  ,  54  ,  55  ,  56  ,  57  ,  58  ,  59  ,  60  ,  61  ,  -  9  ,  -  9  ,  -  9  ,  -  1  ,  -  9  ,  -  9  ,  -  9  ,  0  ,  1  ,  2  ,  3  ,  4  ,  5  ,  6  ,  7  ,  8  ,  9  ,  10  ,  11  ,  12  ,  13  ,  14  ,  15  ,  16  ,  17  ,  18  ,  19  ,  20  ,  21  ,  22  ,  23  ,  24  ,  25  ,  -  9  ,  -  9  ,  -  9  ,  -  9  ,  -  9  ,  -  9  ,  26  ,  27  ,  28  ,  29  ,  30  ,  31  ,  32  ,  33  ,  34  ,  35  ,  36  ,  37  ,  38  ,  39  ,  40  ,  41  ,  42  ,  43  ,  44  ,  45  ,  46  ,  47  ,  48  ,  49  ,  50  ,  51  ,  -  9  ,  -  9  ,  -  9  ,  -  9  }  ; 

private   static   final   byte   BAD_ENCODING  =  -  9  ; 

private   static   final   byte   WHITE_SPACE_ENC  =  -  5  ; 

private   static   final   byte   EQUALS_SIGN_ENC  =  -  1  ; 


private   Base64  (  )  { 
} 





public   static   void   main  (  String  [  ]  args  )  { 
try  { 
{ 
byte  [  ]  bytes1  =  {  (  byte  )  2  ,  (  byte  )  2  ,  (  byte  )  3  ,  (  byte  )  0  ,  (  byte  )  9  }  ; 
byte  [  ]  bytes2  =  {  (  byte  )  99  ,  (  byte  )  2  ,  (  byte  )  2  ,  (  byte  )  3  ,  (  byte  )  0  ,  (  byte  )  9  }  ; 
System  .  out  .  println  (  "Bytes 2,2,3,0,9 as Base64: "  +  encodeBytes  (  bytes1  )  )  ; 
System  .  out  .  println  (  "Bytes 2,2,3,0,9 w/ offset: "  +  encodeBytes  (  bytes2  ,  1  ,  bytes2  .  length  -  1  )  )  ; 
byte  [  ]  dbytes  =  decode  (  encodeBytes  (  bytes1  )  )  ; 
System  .  out  .  print  (  encodeBytes  (  bytes1  )  +  " decoded: "  )  ; 
for  (  int   i  =  0  ;  i  <  dbytes  .  length  ;  i  ++  )  System  .  out  .  print  (  dbytes  [  i  ]  +  (  i  <  dbytes  .  length  -  1  ?  ","  :  "\n"  )  )  ; 
} 
{ 
java  .  io  .  FileInputStream   fis  =  new   java  .  io  .  FileInputStream  (  "test.gif.b64"  )  ; 
Base64  .  InputStream   b64is  =  new   Base64  .  InputStream  (  fis  ,  DECODE  )  ; 
byte  [  ]  bytes  =  new   byte  [  0  ]  ; 
int   b  =  -  1  ; 
while  (  (  b  =  b64is  .  read  (  )  )  >=  0  )  { 
byte  [  ]  temp  =  new   byte  [  bytes  .  length  +  1  ]  ; 
System  .  arraycopy  (  bytes  ,  0  ,  temp  ,  0  ,  bytes  .  length  )  ; 
temp  [  bytes  .  length  ]  =  (  byte  )  b  ; 
bytes  =  temp  ; 
} 
b64is  .  close  (  )  ; 
javax  .  swing  .  ImageIcon   iicon  =  new   javax  .  swing  .  ImageIcon  (  bytes  )  ; 
javax  .  swing  .  JLabel   jlabel  =  new   javax  .  swing  .  JLabel  (  "Read from test.gif.b64"  ,  iicon  ,  0  )  ; 
javax  .  swing  .  JFrame   jframe  =  new   javax  .  swing  .  JFrame  (  )  ; 
jframe  .  getContentPane  (  )  .  add  (  jlabel  )  ; 
jframe  .  pack  (  )  ; 
jframe  .  show  (  )  ; 
java  .  io  .  FileOutputStream   fos  =  new   java  .  io  .  FileOutputStream  (  "test.gif_out"  )  ; 
fos  .  write  (  bytes  )  ; 
fos  .  close  (  )  ; 
fis  =  new   java  .  io  .  FileInputStream  (  "test.gif_out"  )  ; 
b64is  =  new   Base64  .  InputStream  (  fis  ,  ENCODE  )  ; 
byte  [  ]  ebytes  =  new   byte  [  0  ]  ; 
b  =  -  1  ; 
while  (  (  b  =  b64is  .  read  (  )  )  >=  0  )  { 
byte  [  ]  temp  =  new   byte  [  ebytes  .  length  +  1  ]  ; 
System  .  arraycopy  (  ebytes  ,  0  ,  temp  ,  0  ,  ebytes  .  length  )  ; 
temp  [  ebytes  .  length  ]  =  (  byte  )  b  ; 
ebytes  =  temp  ; 
} 
b64is  .  close  (  )  ; 
String   s  =  new   String  (  ebytes  )  ; 
javax  .  swing  .  JTextArea   jta  =  new   javax  .  swing  .  JTextArea  (  s  )  ; 
javax  .  swing  .  JScrollPane   jsp  =  new   javax  .  swing  .  JScrollPane  (  jta  )  ; 
jframe  =  new   javax  .  swing  .  JFrame  (  )  ; 
jframe  .  setTitle  (  "Read from test.gif_out"  )  ; 
jframe  .  getContentPane  (  )  .  add  (  jsp  )  ; 
jframe  .  pack  (  )  ; 
jframe  .  show  (  )  ; 
fos  =  new   java  .  io  .  FileOutputStream  (  "test.gif.b64_out"  )  ; 
fos  .  write  (  ebytes  )  ; 
fis  =  new   java  .  io  .  FileInputStream  (  "test.gif.b64_out"  )  ; 
b64is  =  new   Base64  .  InputStream  (  fis  ,  DECODE  )  ; 
byte  [  ]  edbytes  =  new   byte  [  0  ]  ; 
b  =  -  1  ; 
while  (  (  b  =  b64is  .  read  (  )  )  >=  0  )  { 
byte  [  ]  temp  =  new   byte  [  edbytes  .  length  +  1  ]  ; 
System  .  arraycopy  (  edbytes  ,  0  ,  temp  ,  0  ,  edbytes  .  length  )  ; 
temp  [  edbytes  .  length  ]  =  (  byte  )  b  ; 
edbytes  =  temp  ; 
} 
b64is  .  close  (  )  ; 
iicon  =  new   javax  .  swing  .  ImageIcon  (  edbytes  )  ; 
jlabel  =  new   javax  .  swing  .  JLabel  (  "Read from test.gif.b64_out"  ,  iicon  ,  0  )  ; 
jframe  =  new   javax  .  swing  .  JFrame  (  )  ; 
jframe  .  getContentPane  (  )  .  add  (  jlabel  )  ; 
jframe  .  pack  (  )  ; 
jframe  .  show  (  )  ; 
} 
{ 
java  .  io  .  FileInputStream   fis  =  new   java  .  io  .  FileInputStream  (  "test.gif_out"  )  ; 
byte  [  ]  rbytes  =  new   byte  [  0  ]  ; 
int   b  =  -  1  ; 
while  (  (  b  =  fis  .  read  (  )  )  >=  0  )  { 
byte  [  ]  temp  =  new   byte  [  rbytes  .  length  +  1  ]  ; 
System  .  arraycopy  (  rbytes  ,  0  ,  temp  ,  0  ,  rbytes  .  length  )  ; 
temp  [  rbytes  .  length  ]  =  (  byte  )  b  ; 
rbytes  =  temp  ; 
} 
fis  .  close  (  )  ; 
java  .  io  .  FileOutputStream   fos  =  new   java  .  io  .  FileOutputStream  (  "test.gif.b64_out2"  )  ; 
Base64  .  OutputStream   b64os  =  new   Base64  .  OutputStream  (  fos  ,  ENCODE  )  ; 
b64os  .  write  (  rbytes  )  ; 
b64os  .  close  (  )  ; 
fis  =  new   java  .  io  .  FileInputStream  (  "test.gif.b64_out2"  )  ; 
byte  [  ]  rebytes  =  new   byte  [  0  ]  ; 
b  =  -  1  ; 
while  (  (  b  =  fis  .  read  (  )  )  >=  0  )  { 
byte  [  ]  temp  =  new   byte  [  rebytes  .  length  +  1  ]  ; 
System  .  arraycopy  (  rebytes  ,  0  ,  temp  ,  0  ,  rebytes  .  length  )  ; 
temp  [  rebytes  .  length  ]  =  (  byte  )  b  ; 
rebytes  =  temp  ; 
} 
fis  .  close  (  )  ; 
String   s  =  new   String  (  rebytes  )  ; 
javax  .  swing  .  JTextArea   jta  =  new   javax  .  swing  .  JTextArea  (  s  )  ; 
javax  .  swing  .  JScrollPane   jsp  =  new   javax  .  swing  .  JScrollPane  (  jta  )  ; 
javax  .  swing  .  JFrame   jframe  =  new   javax  .  swing  .  JFrame  (  )  ; 
jframe  .  setTitle  (  "Read from test.gif.b64_out2"  )  ; 
jframe  .  getContentPane  (  )  .  add  (  jsp  )  ; 
jframe  .  pack  (  )  ; 
jframe  .  show  (  )  ; 
fos  =  new   java  .  io  .  FileOutputStream  (  "test.gif_out2"  )  ; 
b64os  =  new   Base64  .  OutputStream  (  fos  ,  DECODE  )  ; 
b64os  .  write  (  rebytes  )  ; 
b64os  .  close  (  )  ; 
javax  .  swing  .  ImageIcon   iicon  =  new   javax  .  swing  .  ImageIcon  (  "test.gif_out2"  )  ; 
javax  .  swing  .  JLabel   jlabel  =  new   javax  .  swing  .  JLabel  (  "Read from test.gif_out2"  ,  iicon  ,  0  )  ; 
jframe  =  new   javax  .  swing  .  JFrame  (  )  ; 
jframe  .  getContentPane  (  )  .  add  (  jlabel  )  ; 
jframe  .  pack  (  )  ; 
jframe  .  show  (  )  ; 
} 
{ 
java  .  io  .  FileInputStream   fis  =  new   java  .  io  .  FileInputStream  (  "D:\\temp\\testencoding.txt"  )  ; 
Base64  .  InputStream   b64is  =  new   Base64  .  InputStream  (  fis  ,  DECODE  )  ; 
java  .  io  .  FileOutputStream   fos  =  new   java  .  io  .  FileOutputStream  (  "D:\\temp\\file.zip"  )  ; 
int   b  ; 
while  (  (  b  =  b64is  .  read  (  )  )  >=  0  )  fos  .  write  (  b  )  ; 
fos  .  close  (  )  ; 
b64is  .  close  (  )  ; 
} 
}  catch  (  Exception   e  )  { 
e  .  printStackTrace  (  )  ; 
} 
} 









private   static   byte  [  ]  encode3to4  (  byte  [  ]  threeBytes  )  { 
return   encode3to4  (  threeBytes  ,  3  )  ; 
} 














private   static   byte  [  ]  encode3to4  (  byte  [  ]  threeBytes  ,  int   numSigBytes  )  { 
byte  [  ]  dest  =  new   byte  [  4  ]  ; 
encode3to4  (  threeBytes  ,  0  ,  numSigBytes  ,  dest  ,  0  )  ; 
return   dest  ; 
} 






















private   static   byte  [  ]  encode3to4  (  byte  [  ]  source  ,  int   srcOffset  ,  int   numSigBytes  ,  byte  [  ]  destination  ,  int   destOffset  )  { 
int   inBuff  =  (  numSigBytes  >  0  ?  (  (  source  [  srcOffset  ]  <<  24  )  >  >  >  8  )  :  0  )  |  (  numSigBytes  >  1  ?  (  (  source  [  srcOffset  +  1  ]  <<  24  )  >  >  >  16  )  :  0  )  |  (  numSigBytes  >  2  ?  (  (  source  [  srcOffset  +  2  ]  <<  24  )  >  >  >  24  )  :  0  )  ; 
switch  (  numSigBytes  )  { 
case   3  : 
destination  [  destOffset  ]  =  ALPHABET  [  (  inBuff  >  >  >  18  )  ]  ; 
destination  [  destOffset  +  1  ]  =  ALPHABET  [  (  inBuff  >  >  >  12  )  &  0x3f  ]  ; 
destination  [  destOffset  +  2  ]  =  ALPHABET  [  (  inBuff  >  >  >  6  )  &  0x3f  ]  ; 
destination  [  destOffset  +  3  ]  =  ALPHABET  [  (  inBuff  )  &  0x3f  ]  ; 
return   destination  ; 
case   2  : 
destination  [  destOffset  ]  =  ALPHABET  [  (  inBuff  >  >  >  18  )  ]  ; 
destination  [  destOffset  +  1  ]  =  ALPHABET  [  (  inBuff  >  >  >  12  )  &  0x3f  ]  ; 
destination  [  destOffset  +  2  ]  =  ALPHABET  [  (  inBuff  >  >  >  6  )  &  0x3f  ]  ; 
destination  [  destOffset  +  3  ]  =  EQUALS_SIGN  ; 
return   destination  ; 
case   1  : 
destination  [  destOffset  ]  =  ALPHABET  [  (  inBuff  >  >  >  18  )  ]  ; 
destination  [  destOffset  +  1  ]  =  ALPHABET  [  (  inBuff  >  >  >  12  )  &  0x3f  ]  ; 
destination  [  destOffset  +  2  ]  =  EQUALS_SIGN  ; 
destination  [  destOffset  +  3  ]  =  EQUALS_SIGN  ; 
return   destination  ; 
default  : 
return   destination  ; 
} 
} 











public   static   String   encodeObject  (  java  .  io  .  Serializable   serializableObject  )  { 
java  .  io  .  ByteArrayOutputStream   baos  =  null  ; 
java  .  io  .  OutputStream   b64os  =  null  ; 
java  .  io  .  ObjectOutputStream   oos  =  null  ; 
try  { 
baos  =  new   java  .  io  .  ByteArrayOutputStream  (  )  ; 
b64os  =  new   Base64  .  OutputStream  (  baos  ,  Base64  .  ENCODE  )  ; 
oos  =  new   java  .  io  .  ObjectOutputStream  (  b64os  )  ; 
oos  .  writeObject  (  serializableObject  )  ; 
}  catch  (  java  .  io  .  IOException   e  )  { 
e  .  printStackTrace  (  )  ; 
return   null  ; 
}  finally  { 
try  { 
oos  .  close  (  )  ; 
}  catch  (  Exception   e  )  { 
} 
try  { 
b64os  .  close  (  )  ; 
}  catch  (  Exception   e  )  { 
} 
try  { 
baos  .  close  (  )  ; 
}  catch  (  Exception   e  )  { 
} 
} 
return   new   String  (  baos  .  toByteArray  (  )  )  ; 
} 









public   static   String   encodeBytes  (  byte  [  ]  source  )  { 
return   encodeBytes  (  source  ,  0  ,  source  .  length  )  ; 
} 









public   static   String   encodeBytes  (  byte  [  ]  source  ,  int   off  ,  int   len  )  { 
int   len43  =  len  *  4  /  3  ; 
byte  [  ]  outBuff  =  new   byte  [  (  len43  )  +  (  (  len  %  3  )  >  0  ?  4  :  0  )  +  (  len43  /  MAX_LINE_LENGTH  )  ]  ; 
int   d  =  0  ; 
int   e  =  0  ; 
int   len2  =  len  -  2  ; 
int   lineLength  =  0  ; 
for  (  ;  d  <  len2  ;  d  +=  3  ,  e  +=  4  )  { 
encode3to4  (  source  ,  d  +  off  ,  3  ,  outBuff  ,  e  )  ; 
lineLength  +=  4  ; 
if  (  lineLength  ==  MAX_LINE_LENGTH  )  { 
outBuff  [  e  +  4  ]  =  NEW_LINE  ; 
e  ++  ; 
lineLength  =  0  ; 
} 
} 
if  (  d  <  len  )  { 
encode3to4  (  source  ,  d  +  off  ,  len  -  d  ,  outBuff  ,  e  )  ; 
e  +=  4  ; 
} 
return   new   String  (  outBuff  ,  0  ,  e  )  ; 
} 









public   static   String   encodeString  (  String   s  )  { 
return   encodeBytes  (  s  .  getBytes  (  )  )  ; 
} 










private   static   byte  [  ]  decode4to3  (  byte  [  ]  fourBytes  )  { 
byte  [  ]  outBuff1  =  new   byte  [  3  ]  ; 
int   count  =  decode4to3  (  fourBytes  ,  0  ,  outBuff1  ,  0  )  ; 
byte  [  ]  outBuff2  =  new   byte  [  count  ]  ; 
for  (  int   i  =  0  ;  i  <  count  ;  i  ++  )  outBuff2  [  i  ]  =  outBuff1  [  i  ]  ; 
return   outBuff2  ; 
} 























private   static   int   decode4to3  (  byte  [  ]  source  ,  int   srcOffset  ,  byte  [  ]  destination  ,  int   destOffset  )  { 
if  (  source  [  srcOffset  +  2  ]  ==  EQUALS_SIGN  )  { 
int   outBuff  =  (  (  DECODABET  [  source  [  srcOffset  ]  ]  &  0xFF  )  <<  18  )  |  (  (  DECODABET  [  source  [  srcOffset  +  1  ]  ]  &  0xFF  )  <<  12  )  ; 
destination  [  destOffset  ]  =  (  byte  )  (  outBuff  >  >  >  16  )  ; 
return   1  ; 
}  else   if  (  source  [  srcOffset  +  3  ]  ==  EQUALS_SIGN  )  { 
int   outBuff  =  (  (  DECODABET  [  source  [  srcOffset  ]  ]  &  0xFF  )  <<  18  )  |  (  (  DECODABET  [  source  [  srcOffset  +  1  ]  ]  &  0xFF  )  <<  12  )  |  (  (  DECODABET  [  source  [  srcOffset  +  2  ]  ]  &  0xFF  )  <<  6  )  ; 
destination  [  destOffset  ]  =  (  byte  )  (  outBuff  >  >  >  16  )  ; 
destination  [  destOffset  +  1  ]  =  (  byte  )  (  outBuff  >  >  >  8  )  ; 
return   2  ; 
}  else  { 
try  { 
int   outBuff  =  (  (  DECODABET  [  source  [  srcOffset  ]  ]  &  0xFF  )  <<  18  )  |  (  (  DECODABET  [  source  [  srcOffset  +  1  ]  ]  &  0xFF  )  <<  12  )  |  (  (  DECODABET  [  source  [  srcOffset  +  2  ]  ]  &  0xFF  )  <<  6  )  |  (  (  DECODABET  [  source  [  srcOffset  +  3  ]  ]  &  0xFF  )  )  ; 
destination  [  destOffset  ]  =  (  byte  )  (  outBuff  >  >  16  )  ; 
destination  [  destOffset  +  1  ]  =  (  byte  )  (  outBuff  >  >  8  )  ; 
destination  [  destOffset  +  2  ]  =  (  byte  )  (  outBuff  )  ; 
return   3  ; 
}  catch  (  Exception   e  )  { 
System  .  out  .  println  (  ""  +  source  [  srcOffset  ]  +  ": "  +  (  DECODABET  [  source  [  srcOffset  ]  ]  )  )  ; 
System  .  out  .  println  (  ""  +  source  [  srcOffset  +  1  ]  +  ": "  +  (  DECODABET  [  source  [  srcOffset  +  1  ]  ]  )  )  ; 
System  .  out  .  println  (  ""  +  source  [  srcOffset  +  2  ]  +  ": "  +  (  DECODABET  [  source  [  srcOffset  +  2  ]  ]  )  )  ; 
System  .  out  .  println  (  ""  +  source  [  srcOffset  +  3  ]  +  ": "  +  (  DECODABET  [  source  [  srcOffset  +  3  ]  ]  )  )  ; 
return  -  1  ; 
} 
} 
} 








public   static   byte  [  ]  decode  (  String   s  )  { 
byte  [  ]  bytes  =  s  .  getBytes  (  )  ; 
return   decode  (  bytes  ,  0  ,  bytes  .  length  )  ; 
} 











public   static   String   decodeToString  (  String   s  )  { 
return   new   String  (  decode  (  s  )  )  ; 
} 









public   static   Object   decodeToObject  (  String   encodedObject  )  { 
byte  [  ]  objBytes  =  decode  (  encodedObject  )  ; 
java  .  io  .  ByteArrayInputStream   bais  =  null  ; 
java  .  io  .  ObjectInputStream   ois  =  null  ; 
try  { 
bais  =  new   java  .  io  .  ByteArrayInputStream  (  objBytes  )  ; 
ois  =  new   java  .  io  .  ObjectInputStream  (  bais  )  ; 
return   ois  .  readObject  (  )  ; 
}  catch  (  java  .  io  .  IOException   e  )  { 
e  .  printStackTrace  (  )  ; 
return   null  ; 
}  catch  (  java  .  lang  .  ClassNotFoundException   e  )  { 
e  .  printStackTrace  (  )  ; 
return   null  ; 
}  finally  { 
try  { 
bais  .  close  (  )  ; 
}  catch  (  Exception   e  )  { 
} 
try  { 
ois  .  close  (  )  ; 
}  catch  (  Exception   e  )  { 
} 
} 
} 











public   static   byte  [  ]  decode  (  byte  [  ]  source  ,  int   off  ,  int   len  )  { 
int   len34  =  len  *  3  /  4  ; 
byte  [  ]  outBuff  =  new   byte  [  len34  ]  ; 
int   outBuffPosn  =  0  ; 
byte  [  ]  b4  =  new   byte  [  4  ]  ; 
int   b4Posn  =  0  ; 
int   i  =  0  ; 
byte   sbiCrop  =  0  ; 
byte   sbiDecode  =  0  ; 
for  (  i  =  0  ;  i  <  len  ;  i  ++  )  { 
sbiCrop  =  (  byte  )  (  source  [  i  ]  &  0x7f  )  ; 
sbiDecode  =  DECODABET  [  sbiCrop  ]  ; 
if  (  sbiDecode  >=  WHITE_SPACE_ENC  )  { 
if  (  sbiDecode  >=  EQUALS_SIGN_ENC  )  { 
b4  [  b4Posn  ++  ]  =  sbiCrop  ; 
if  (  b4Posn  >  3  )  { 
outBuffPosn  +=  decode4to3  (  b4  ,  0  ,  outBuff  ,  outBuffPosn  )  ; 
b4Posn  =  0  ; 
if  (  sbiCrop  ==  EQUALS_SIGN  )  break  ; 
} 
} 
}  else  { 
System  .  err  .  println  (  "Bad Base64 input character at "  +  i  +  ": "  +  source  [  i  ]  +  "(decimal)"  )  ; 
return   null  ; 
} 
} 
byte  [  ]  out  =  new   byte  [  outBuffPosn  ]  ; 
System  .  arraycopy  (  outBuff  ,  0  ,  out  ,  0  ,  outBuffPosn  )  ; 
return   out  ; 
} 










public   static   class   InputStream   extends   java  .  io  .  FilterInputStream  { 

private   boolean   encode  ; 

private   int   position  ; 

private   byte  [  ]  buffer  ; 

private   int   bufferLength  ; 

private   int   numSigBytes  ; 

private   int   lineLength  ; 







public   InputStream  (  java  .  io  .  InputStream   in  )  { 
this  (  in  ,  Base64  .  DECODE  )  ; 
} 











public   InputStream  (  java  .  io  .  InputStream   in  ,  boolean   encode  )  { 
super  (  in  )  ; 
this  .  encode  =  encode  ; 
this  .  bufferLength  =  encode  ?  4  :  3  ; 
this  .  buffer  =  new   byte  [  bufferLength  ]  ; 
this  .  position  =  -  1  ; 
this  .  lineLength  =  0  ; 
} 








public   int   read  (  )  throws   java  .  io  .  IOException  { 
if  (  position  <  0  )  { 
if  (  encode  )  { 
byte  [  ]  b3  =  new   byte  [  3  ]  ; 
int   numBinaryBytes  =  0  ; 
for  (  int   i  =  0  ;  i  <  3  ;  i  ++  )  { 
try  { 
int   b  =  in  .  read  (  )  ; 
if  (  b  >=  0  )  { 
b3  [  i  ]  =  (  byte  )  b  ; 
numBinaryBytes  ++  ; 
} 
}  catch  (  java  .  io  .  IOException   e  )  { 
if  (  i  ==  0  )  throw   e  ; 
} 
} 
if  (  numBinaryBytes  >  0  )  { 
encode3to4  (  b3  ,  0  ,  numBinaryBytes  ,  buffer  ,  0  )  ; 
position  =  0  ; 
numSigBytes  =  4  ; 
}  else  { 
return  -  1  ; 
} 
}  else  { 
byte  [  ]  b4  =  new   byte  [  4  ]  ; 
int   i  =  0  ; 
for  (  i  =  0  ;  i  <  4  ;  i  ++  )  { 
int   b  =  0  ; 
do  { 
b  =  in  .  read  (  )  ; 
}  while  (  b  >=  0  &&  DECODABET  [  b  &  0x7f  ]  <=  WHITE_SPACE_ENC  )  ; 
if  (  b  <  0  )  break  ; 
b4  [  i  ]  =  (  byte  )  b  ; 
} 
if  (  i  ==  4  )  { 
numSigBytes  =  decode4to3  (  b4  ,  0  ,  buffer  ,  0  )  ; 
position  =  0  ; 
}  else   if  (  i  ==  0  )  { 
return  -  1  ; 
}  else  { 
throw   new   java  .  io  .  IOException  (  "Improperly padded Base64 input."  )  ; 
} 
} 
} 
if  (  position  >=  0  )  { 
if  (  position  >=  numSigBytes  )  return  -  1  ; 
if  (  encode  &&  lineLength  >=  MAX_LINE_LENGTH  )  { 
lineLength  =  0  ; 
return  '\n'  ; 
}  else  { 
lineLength  ++  ; 
int   b  =  buffer  [  position  ++  ]  ; 
if  (  position  >=  bufferLength  )  position  =  -  1  ; 
return   b  &  0xFF  ; 
} 
}  else  { 
throw   new   java  .  io  .  IOException  (  "Error in Base64 code reading stream."  )  ; 
} 
} 













public   int   read  (  byte  [  ]  dest  ,  int   off  ,  int   len  )  throws   java  .  io  .  IOException  { 
int   i  ; 
int   b  ; 
for  (  i  =  0  ;  i  <  len  ;  i  ++  )  { 
b  =  read  (  )  ; 
if  (  b  <  0  )  return  -  1  ; 
dest  [  off  +  i  ]  =  (  byte  )  b  ; 
} 
return   i  ; 
} 
} 










public   static   class   OutputStream   extends   java  .  io  .  FilterOutputStream  { 

private   boolean   encode  ; 

private   int   position  ; 

private   byte  [  ]  buffer  ; 

private   int   bufferLength  ; 

private   int   lineLength  ; 







public   OutputStream  (  java  .  io  .  OutputStream   out  )  { 
this  (  out  ,  Base64  .  ENCODE  )  ; 
} 











public   OutputStream  (  java  .  io  .  OutputStream   out  ,  boolean   encode  )  { 
super  (  out  )  ; 
this  .  encode  =  encode  ; 
this  .  bufferLength  =  encode  ?  3  :  4  ; 
this  .  buffer  =  new   byte  [  bufferLength  ]  ; 
this  .  position  =  0  ; 
this  .  lineLength  =  0  ; 
} 













public   void   write  (  int   theByte  )  throws   java  .  io  .  IOException  { 
if  (  encode  )  { 
buffer  [  position  ++  ]  =  (  byte  )  theByte  ; 
if  (  position  >=  bufferLength  )  { 
out  .  write  (  Base64  .  encode3to4  (  buffer  ,  bufferLength  )  )  ; 
lineLength  +=  4  ; 
if  (  lineLength  >=  MAX_LINE_LENGTH  )  { 
out  .  write  (  NEW_LINE  )  ; 
lineLength  =  0  ; 
} 
position  =  0  ; 
} 
}  else  { 
if  (  DECODABET  [  theByte  &  0x7f  ]  >  WHITE_SPACE_ENC  )  { 
buffer  [  position  ++  ]  =  (  byte  )  theByte  ; 
if  (  position  >=  bufferLength  )  { 
out  .  write  (  Base64  .  decode4to3  (  buffer  )  )  ; 
position  =  0  ; 
} 
}  else   if  (  DECODABET  [  theByte  &  0x7f  ]  !=  WHITE_SPACE_ENC  )  { 
throw   new   java  .  io  .  IOException  (  "Invalid character in Base64 data."  )  ; 
} 
} 
} 










public   void   write  (  byte  [  ]  theBytes  ,  int   off  ,  int   len  )  throws   java  .  io  .  IOException  { 
for  (  int   i  =  0  ;  i  <  len  ;  i  ++  )  { 
write  (  theBytes  [  off  +  i  ]  )  ; 
} 
} 








public   void   flush  (  )  throws   java  .  io  .  IOException  { 
super  .  flush  (  )  ; 
if  (  position  >  0  )  { 
if  (  encode  )  { 
out  .  write  (  Base64  .  encode3to4  (  buffer  ,  position  )  )  ; 
}  else  { 
throw   new   java  .  io  .  IOException  (  "Base64 input not properly padded."  )  ; 
} 
} 
out  .  flush  (  )  ; 
} 






public   void   close  (  )  throws   java  .  io  .  IOException  { 
super  .  close  (  )  ; 
out  .  close  (  )  ; 
buffer  =  null  ; 
out  =  null  ; 
} 
} 
} 

