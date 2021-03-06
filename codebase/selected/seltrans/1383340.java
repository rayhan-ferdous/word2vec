package   net  .  sf  .  chineseutils  ; 

import   java  .  io  .  BufferedReader  ; 
import   java  .  io  .  IOException  ; 
import   java  .  io  .  Reader  ; 
import   java  .  io  .  StringReader  ; 
import   java  .  io  .  StringWriter  ; 
import   java  .  io  .  Writer  ; 
import   net  .  sf  .  chineseutils  .  mapping  .  STCodeMapping  ; 
import   net  .  sf  .  chineseutils  .  mapping  .  STLexemicMapping  ; 
import   net  .  sf  .  chineseutils  .  mapping  .  TSCodeMapping  ; 
import   net  .  sf  .  chineseutils  .  mapping  .  TSLexemicMapping  ; 
import   org  .  apache  .  commons  .  logging  .  Log  ; 
import   org  .  apache  .  commons  .  logging  .  LogFactory  ; 







public   class   ChineseUtils  { 




private   static   final   Log   logger  =  LogFactory  .  getLog  (  ChineseUtils  .  class  )  ; 

private   static   boolean   INITED  =  false  ; 

public   static   int   READ_AHEAD_LIMIT  =  1024  ; 







private   static   final   char   CJK_UNIFIED_IDEOGRAPHS_START  =  '一'  ; 

static  { 
init  (  )  ; 
} 




public   ChineseUtils  (  )  { 
} 






private   static   boolean   init  (  )  { 
if  (  !  INITED  )  { 
if  (  !  (  new   STCodeMapping  (  )  )  .  init  (  )  ||  !  (  new   TSCodeMapping  (  )  )  .  init  (  )  )  { 
return   false  ; 
} 
if  (  !  (  new   STLexemicMapping  (  )  )  .  init  (  )  ||  !  (  new   TSLexemicMapping  (  )  )  .  init  (  )  )  { 
return   false  ; 
} 
} 
return   true  ; 
} 













public   static   void   simpToTrad  (  Reader   reader  ,  Writer   writer  ,  boolean   lexicalMapping  )  throws   IOException  { 
Reader   in  =  null  ; 
if  (  !  reader  .  markSupported  (  )  )  { 
in  =  new   BufferedReader  (  reader  )  ; 
}  else  { 
in  =  reader  ; 
} 
int   c  ; 
continueConvert  :  while  (  (  c  =  in  .  read  (  )  )  !=  -  1  )  { 
char   ch  =  (  char  )  c  ; 
if  (  lexicalMapping  )  { 
int   p  =  STLexemicMapping  .  findBlock  (  ch  )  ; 
if  (  p  !=  -  1  )  { 
int   start  =  STLexemicMapping  .  blockStart  (  p  )  ; 
int   end  =  STLexemicMapping  .  blockEnd  (  p  )  ; 
for  (  int   i  =  start  ;  i  <  end  ;  i  ++  )  { 
String   glossary  =  STLexemicMapping  .  SC_TO_TC_LEXEME  [  i  ]  ; 
int   l  =  glossary  .  length  (  )  ; 
in  .  mark  (  READ_AHEAD_LIMIT  )  ; 
boolean   match  =  true  ; 
for  (  int   j  =  1  ;  j  <  l  ;  j  ++  )  { 
if  (  in  .  read  (  )  !=  glossary  .  charAt  (  j  )  )  { 
match  =  false  ; 
break  ; 
} 
} 
in  .  reset  (  )  ; 
if  (  match  )  { 
writer  .  write  (  STLexemicMapping  .  SC_TO_TC_LEXEME_MAP  [  i  ]  )  ; 
in  .  skip  (  l  -  1  )  ; 
continue   continueConvert  ; 
} 
} 
} 
} 
if  (  Character  .  UnicodeBlock  .  CJK_UNIFIED_IDEOGRAPHS  .  equals  (  Character  .  UnicodeBlock  .  of  (  ch  )  )  )  { 
writer  .  write  (  STCodeMapping  .  SC_TO_TC_CODE_MAP  [  ch  -  CJK_UNIFIED_IDEOGRAPHS_START  ]  )  ; 
}  else  { 
writer  .  write  (  ch  )  ; 
} 
} 
} 













public   static   void   tradToSimp  (  Reader   reader  ,  Writer   writer  ,  boolean   lexicalMapping  )  throws   IOException  { 
Reader   in  =  null  ; 
if  (  !  reader  .  markSupported  (  )  )  { 
in  =  new   BufferedReader  (  reader  )  ; 
}  else  { 
in  =  reader  ; 
} 
int   c  ; 
continueConvert  :  while  (  (  c  =  in  .  read  (  )  )  !=  -  1  )  { 
char   ch  =  (  char  )  c  ; 
if  (  lexicalMapping  )  { 
int   p  =  TSLexemicMapping  .  findBlock  (  ch  )  ; 
if  (  p  !=  -  1  )  { 
int   start  =  TSLexemicMapping  .  blockStart  (  p  )  ; 
int   end  =  TSLexemicMapping  .  blockEnd  (  p  )  ; 
for  (  int   i  =  start  ;  i  <  end  ;  i  ++  )  { 
String   glossary  =  TSLexemicMapping  .  TC_TO_SC_LEXEME  [  i  ]  ; 
int   l  =  glossary  .  length  (  )  ; 
in  .  mark  (  READ_AHEAD_LIMIT  )  ; 
boolean   match  =  true  ; 
for  (  int   j  =  1  ;  j  <  l  ;  j  ++  )  { 
if  (  in  .  read  (  )  !=  glossary  .  charAt  (  j  )  )  { 
match  =  false  ; 
break  ; 
} 
} 
in  .  reset  (  )  ; 
if  (  match  )  { 
writer  .  write  (  TSLexemicMapping  .  TC_TO_SC_LEXEME_MAP  [  i  ]  )  ; 
in  .  skip  (  l  -  1  )  ; 
continue   continueConvert  ; 
} 
} 
} 
} 
if  (  Character  .  UnicodeBlock  .  CJK_UNIFIED_IDEOGRAPHS  .  equals  (  Character  .  UnicodeBlock  .  of  (  ch  )  )  )  { 
writer  .  write  (  TSCodeMapping  .  TC_TO_SC_CODE_MAP  [  ch  -  CJK_UNIFIED_IDEOGRAPHS_START  ]  )  ; 
}  else  { 
writer  .  write  (  ch  )  ; 
} 
} 
} 











public   static   void   simpToTrad  (  Reader   in  ,  Writer   out  )  throws   IOException  { 
simpToTrad  (  in  ,  out  ,  true  )  ; 
} 











public   static   void   tradToSimp  (  Reader   in  ,  Writer   out  )  throws   IOException  { 
tradToSimp  (  in  ,  out  ,  true  )  ; 
} 










public   static   String   simpToTrad  (  String   str  ,  boolean   lexicalMapping  )  { 
String   ret  =  str  ; 
Reader   in  =  new   StringReader  (  str  )  ; 
Writer   out  =  new   StringWriter  (  )  ; 
try  { 
simpToTrad  (  in  ,  out  ,  lexicalMapping  )  ; 
}  catch  (  IOException   e  )  { 
if  (  logger  .  isDebugEnabled  (  )  )  { 
e  .  printStackTrace  (  )  ; 
} 
} 
ret  =  out  .  toString  (  )  ; 
if  (  logger  .  isDebugEnabled  (  )  )  { 
logger  .  debug  (  str  +  " [SC]->[TC] "  +  ret  )  ; 
} 
return   ret  ; 
} 










public   static   String   tradToSimp  (  String   str  ,  boolean   lexicalMapping  )  { 
String   ret  =  str  ; 
Reader   in  =  new   StringReader  (  str  )  ; 
Writer   out  =  new   StringWriter  (  )  ; 
try  { 
tradToSimp  (  in  ,  out  ,  lexicalMapping  )  ; 
}  catch  (  IOException   e  )  { 
if  (  logger  .  isDebugEnabled  (  )  )  { 
e  .  printStackTrace  (  )  ; 
} 
} 
ret  =  out  .  toString  (  )  ; 
if  (  logger  .  isDebugEnabled  (  )  )  { 
logger  .  debug  (  str  +  " [TC]->[SC] "  +  ret  )  ; 
} 
return   ret  ; 
} 








public   static   String   simpToTrad  (  String   str  )  { 
return   simpToTrad  (  str  ,  true  )  ; 
} 








public   static   String   tradToSimp  (  String   str  )  { 
return   tradToSimp  (  str  ,  true  )  ; 
} 

public   static   String   convert  (  String   str  ,  boolean   inverse  )  { 
if  (  inverse  )  { 
return   tradToSimp  (  str  )  ; 
}  else  { 
return   simpToTrad  (  str  )  ; 
} 
} 

public   static   void   convert  (  Reader   in  ,  Writer   out  ,  boolean   inverse  )  throws   IOException  { 
if  (  inverse  )  { 
tradToSimp  (  in  ,  out  )  ; 
}  else  { 
simpToTrad  (  in  ,  out  )  ; 
} 
} 
} 

