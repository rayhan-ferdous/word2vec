package   org  .  jsecurity  .  io  ; 

import   org  .  apache  .  commons  .  logging  .  Log  ; 
import   org  .  apache  .  commons  .  logging  .  LogFactory  ; 
import   org  .  jsecurity  .  util  .  ClassUtils  ; 
import   java  .  io  .  FileInputStream  ; 
import   java  .  io  .  IOException  ; 
import   java  .  io  .  InputStream  ; 
import   java  .  net  .  URL  ; 







public   class   ResourceUtils  { 




public   static   final   String   CLASSPATH_PREFIX  =  "classpath:"  ; 




public   static   final   String   URL_PREFIX  =  "url:"  ; 




public   static   final   String   FILE_PREFIX  =  "file:"  ; 


private   static   final   Log   log  =  LogFactory  .  getLog  (  ResourceUtils  .  class  )  ; 




private   ResourceUtils  (  )  { 
} 











public   static   boolean   hasResourcePrefix  (  String   resourcePath  )  { 
return   resourcePath  !=  null  &&  (  resourcePath  .  startsWith  (  CLASSPATH_PREFIX  )  ||  resourcePath  .  startsWith  (  URL_PREFIX  )  ||  resourcePath  .  startsWith  (  FILE_PREFIX  )  )  ; 
} 







public   static   boolean   resourceExists  (  String   resourcePath  )  { 
InputStream   stream  =  null  ; 
boolean   exists  =  false  ; 
try  { 
stream  =  getInputStreamForPath  (  resourcePath  )  ; 
exists  =  true  ; 
}  catch  (  IOException   e  )  { 
stream  =  null  ; 
}  finally  { 
if  (  stream  !=  null  )  { 
try  { 
stream  .  close  (  )  ; 
}  catch  (  IOException   ignored  )  { 
} 
} 
} 
return   exists  ; 
} 










public   static   InputStream   getInputStreamForPath  (  String   resourcePath  )  throws   IOException  { 
InputStream   is  ; 
if  (  resourcePath  .  startsWith  (  CLASSPATH_PREFIX  )  )  { 
is  =  loadFromClassPath  (  stripPrefix  (  resourcePath  )  )  ; 
}  else   if  (  resourcePath  .  startsWith  (  URL_PREFIX  )  )  { 
is  =  loadFromUrl  (  stripPrefix  (  resourcePath  )  )  ; 
}  else   if  (  resourcePath  .  startsWith  (  FILE_PREFIX  )  )  { 
is  =  loadFromFile  (  stripPrefix  (  resourcePath  )  )  ; 
}  else  { 
is  =  loadFromFile  (  resourcePath  )  ; 
} 
if  (  is  ==  null  )  { 
throw   new   IOException  (  "Resource ["  +  resourcePath  +  "] could not be found."  )  ; 
} 
return   is  ; 
} 

private   static   InputStream   loadFromFile  (  String   path  )  throws   IOException  { 
if  (  log  .  isDebugEnabled  (  )  )  { 
log  .  debug  (  "Opening file ["  +  path  +  "]..."  )  ; 
} 
return   new   FileInputStream  (  path  )  ; 
} 

private   static   InputStream   loadFromUrl  (  String   urlPath  )  throws   IOException  { 
if  (  log  .  isDebugEnabled  (  )  )  { 
log  .  debug  (  "Opening url ["  +  urlPath  +  "]..."  )  ; 
} 
URL   url  =  new   URL  (  urlPath  )  ; 
return   url  .  openStream  (  )  ; 
} 

private   static   InputStream   loadFromClassPath  (  String   path  )  { 
if  (  log  .  isDebugEnabled  (  )  )  { 
log  .  debug  (  "Opening resource from class path ["  +  path  +  "]..."  )  ; 
} 
return   ClassUtils  .  getResourceAsStream  (  path  )  ; 
} 

private   static   String   stripPrefix  (  String   resourcePath  )  { 
return   resourcePath  .  substring  (  resourcePath  .  indexOf  (  ":"  )  +  1  )  ; 
} 

public   static   void   close  (  InputStream   is  )  { 
if  (  is  !=  null  )  { 
try  { 
is  .  close  (  )  ; 
}  catch  (  IOException   e  )  { 
log  .  warn  (  "Error closing input stream."  ,  e  )  ; 
} 
} 
} 
} 

