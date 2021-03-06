package   net  .  sf  .  wikiinajar  .  xrays  ; 

import   java  .  io  .  *  ; 
import   java  .  util  .  *  ; 
import   java  .  net  .  *  ; 







































public   class   NanoHTTPD  { 

public   final   Thread   thread  ; 












public   Response   serve  (  String   uri  ,  String   method  ,  Properties   header  ,  Properties   parms  )  { 
System  .  out  .  println  (  method  +  " '"  +  uri  +  "' "  )  ; 
Enumeration   e  =  header  .  propertyNames  (  )  ; 
while  (  e  .  hasMoreElements  (  )  )  { 
String   value  =  (  String  )  e  .  nextElement  (  )  ; 
System  .  out  .  println  (  "  HDR: '"  +  value  +  "' = '"  +  header  .  getProperty  (  value  )  +  "'"  )  ; 
} 
e  =  parms  .  propertyNames  (  )  ; 
while  (  e  .  hasMoreElements  (  )  )  { 
String   value  =  (  String  )  e  .  nextElement  (  )  ; 
System  .  out  .  println  (  "  PRM: '"  +  value  +  "' = '"  +  parms  .  getProperty  (  value  )  +  "'"  )  ; 
} 
return   serveFile  (  uri  ,  header  ,  new   File  (  "."  )  ,  true  )  ; 
} 








public   static   void   logError  (  String   msg  ,  Throwable   t  ,  String   file  )  { 
try  { 
PrintWriter   p  =  new   PrintWriter  (  new   FileWriter  (  file  )  )  ; 
p  .  println  (  msg  )  ; 
t  .  printStackTrace  (  p  )  ; 
p  .  close  (  )  ; 
if  (  p  .  checkError  (  )  )  { 
t  .  printStackTrace  (  )  ; 
} 
}  catch  (  Throwable   t2  )  { 
System  .  err  .  println  (  msg  )  ; 
t  .  printStackTrace  (  )  ; 
} 
} 





public   class   Response  { 




public   Response  (  )  { 
this  .  status  =  HTTP_OK  ; 
} 




public   Response  (  String   status  ,  String   mimeType  ,  InputStream   data  )  { 
this  .  status  =  status  ; 
this  .  mimeType  =  mimeType  ; 
this  .  data  =  data  ; 
} 





public   Response  (  String   status  ,  String   mimeType  ,  String   txt  )  { 
this  .  status  =  status  ; 
this  .  mimeType  =  mimeType  ; 
try  { 
this  .  data  =  new   ByteArrayInputStream  (  txt  .  getBytes  (  "UTF-8"  )  )  ; 
}  catch  (  UnsupportedEncodingException   e  )  { 
this  .  data  =  new   ByteArrayInputStream  (  txt  .  getBytes  (  )  )  ; 
} 
} 




public   void   addHeader  (  String   name  ,  String   value  )  { 
header  .  put  (  name  ,  value  )  ; 
} 




public   String   status  ; 




public   String   mimeType  ; 




public   InputStream   data  ; 





public   Properties   header  =  new   Properties  (  )  ; 
} 




public   static   final   String   HTTP_OK  =  "200 OK"  ,  HTTP_REDIRECT  =  "301 Moved Permanently"  ,  HTTP_REDIRECT_FOUND  =  "302 Found"  ,  HTTP_FORBIDDEN  =  "403 Forbidden"  ,  HTTP_NOTFOUND  =  "404 Not Found"  ,  HTTP_BADREQUEST  =  "400 Bad Request"  ,  HTTP_INTERNALERROR  =  "500 Internal Server Error"  ,  HTTP_NOTIMPLEMENTED  =  "501 Not Implemented"  ; 




public   static   final   String   MIME_PLAINTEXT  =  "text/plain"  ,  MIME_HTML  =  "text/html"  ,  MIME_DEFAULT_BINARY  =  "application/octet-stream"  ; 





public   NanoHTTPD  (  int   port  ,  final   IServerSettings   settings  )  throws   IOException  { 
myTcpPort  =  port  ; 
final   ServerSocket   ss  =  new   ServerSocket  (  myTcpPort  )  ; 
thread  =  new   Thread  (  new   Runnable  (  )  { 

public   void   run  (  )  { 
try  { 
while  (  true  )  { 
Socket   client  =  ss  .  accept  (  )  ; 
if  (  settings  .  allowOnlyLocalConnections  (  )  &&  !  client  .  getInetAddress  (  )  .  isLoopbackAddress  (  )  )  { 
client  .  close  (  )  ; 
}  else  { 
new   HTTPSession  (  client  )  ; 
} 
} 
}  catch  (  IOException   ioe  )  { 
} 
} 
}  )  ; 
thread  .  setDaemon  (  true  )  ; 
thread  .  start  (  )  ; 
} 

protected   static   String   decodePercentHelper  (  String   str  )  throws   UnsupportedEncodingException  { 
return   URLDecoder  .  decode  (  str  ,  "UTF-8"  )  ; 
} 




public   static   void   main  (  String  [  ]  args  )  { 
System  .  out  .  println  (  "NanoHTTPD 1.1 (C) 2001,2005-2007 Jarno Elonen\n"  +  "(Command line options: [port] [--licence])\n"  )  ; 
int   lopt  =  -  1  ; 
for  (  int   i  =  0  ;  i  <  args  .  length  ;  ++  i  )  if  (  args  [  i  ]  .  toLowerCase  (  )  .  endsWith  (  "licence"  )  )  { 
lopt  =  i  ; 
System  .  out  .  println  (  LICENCE  +  "\n"  )  ; 
} 
int   port  =  80  ; 
if  (  args  .  length  >  0  &&  lopt  !=  0  )  port  =  Integer  .  parseInt  (  args  [  0  ]  )  ; 
if  (  args  .  length  >  1  &&  args  [  1  ]  .  toLowerCase  (  )  .  endsWith  (  "licence"  )  )  System  .  out  .  println  (  LICENCE  +  "\n"  )  ; 
NanoHTTPD   nh  =  null  ; 
try  { 
nh  =  new   NanoHTTPD  (  port  ,  new   IServerSettings  (  )  { 

public   boolean   allowOnlyLocalConnections  (  )  { 
return   true  ; 
} 

; 
}  )  ; 
}  catch  (  IOException   ioe  )  { 
System  .  err  .  println  (  "Couldn't start server:\n"  +  ioe  )  ; 
System  .  exit  (  -  1  )  ; 
} 
nh  .  myFileDir  =  new   File  (  ""  )  ; 
System  .  out  .  println  (  "Now serving files in port "  +  port  +  " from \""  +  new   File  (  ""  )  .  getAbsolutePath  (  )  +  "\""  )  ; 
System  .  out  .  println  (  "Hit Enter to stop.\n"  )  ; 
try  { 
System  .  in  .  read  (  )  ; 
}  catch  (  Throwable   t  )  { 
} 
; 
} 





protected   class   HTTPSession   implements   Runnable  { 

private   static   final   String   ERROR_LOG  =  "error.log"  ; 

protected   HTTPSession  (  )  { 
} 

public   HTTPSession  (  Socket   s  )  { 
mySocket  =  s  ; 
Thread   t  =  new   Thread  (  this  )  ; 
t  .  setDaemon  (  true  )  ; 
t  .  start  (  )  ; 
} 

public   void   run  (  )  { 
try  { 
InputStream   is  =  mySocket  .  getInputStream  (  )  ; 
if  (  is  ==  null  )  return  ; 
BufferedReader   in  =  new   BufferedReader  (  new   InputStreamReader  (  is  )  )  ; 
StringTokenizer   st  =  new   StringTokenizer  (  in  .  readLine  (  )  )  ; 
if  (  !  st  .  hasMoreTokens  (  )  )  sendError  (  HTTP_BADREQUEST  ,  "BAD REQUEST: Syntax error. Usage: GET /example/file.html"  )  ; 
String   method  =  st  .  nextToken  (  )  ; 
if  (  !  st  .  hasMoreTokens  (  )  )  sendError  (  HTTP_BADREQUEST  ,  "BAD REQUEST: Missing URI. Usage: GET /example/file.html"  )  ; 
String   uri  =  decodePercent  (  st  .  nextToken  (  )  )  ; 
Properties   parms  =  new   Properties  (  )  ; 
int   qmi  =  uri  .  indexOf  (  '?'  )  ; 
if  (  qmi  >=  0  )  { 
decodeParms  (  uri  .  substring  (  qmi  +  1  )  ,  parms  )  ; 
uri  =  decodePercent  (  uri  .  substring  (  0  ,  qmi  )  )  ; 
} 
Properties   header  =  new   Properties  (  )  ; 
if  (  st  .  hasMoreTokens  (  )  )  { 
String   line  =  in  .  readLine  (  )  ; 
while  (  line  .  trim  (  )  .  length  (  )  >  0  )  { 
int   p  =  line  .  indexOf  (  ':'  )  ; 
header  .  put  (  line  .  substring  (  0  ,  p  )  .  trim  (  )  .  toLowerCase  (  )  ,  line  .  substring  (  p  +  1  )  .  trim  (  )  )  ; 
line  =  in  .  readLine  (  )  ; 
} 
} 
if  (  method  .  equalsIgnoreCase  (  "POST"  )  )  { 
long   size  =  0x7FFFFFFFFFFFFFFFl  ; 
String   contentLength  =  header  .  getProperty  (  "content-length"  )  ; 
if  (  contentLength  !=  null  )  { 
try  { 
size  =  Integer  .  parseInt  (  contentLength  )  ; 
}  catch  (  NumberFormatException   ex  )  { 
} 
} 
String   postLine  =  ""  ; 
char   buf  [  ]  =  new   char  [  512  ]  ; 
int   read  =  in  .  read  (  buf  )  ; 
while  (  read  >=  0  &&  size  >  0  &&  !  postLine  .  endsWith  (  "\r\n"  )  )  { 
size  -=  read  ; 
postLine  +=  String  .  valueOf  (  buf  ,  0  ,  read  )  ; 
if  (  size  >  0  )  read  =  in  .  read  (  buf  )  ; 
} 
postLine  =  postLine  .  trim  (  )  ; 
decodeParms  (  postLine  ,  parms  )  ; 
} 
Response   r  =  serve  (  uri  ,  method  ,  header  ,  parms  )  ; 
if  (  r  ==  null  )  sendError  (  HTTP_INTERNALERROR  ,  "SERVER INTERNAL ERROR: Serve() returned a null response."  )  ;  else   sendResponse  (  r  .  status  ,  r  .  mimeType  ,  r  .  header  ,  r  .  data  )  ; 
in  .  close  (  )  ; 
}  catch  (  IOException   ioe  )  { 
try  { 
sendError  (  HTTP_INTERNALERROR  ,  "SERVER INTERNAL ERROR: IOException: "  +  ioe  .  getMessage  (  )  )  ; 
}  catch  (  Throwable   t  )  { 
} 
}  catch  (  InterruptedException   ie  )  { 
}  catch  (  Throwable   t  )  { 
String   msg  =  "An error was encountered at: "  +  new   Date  (  )  +  "\nServer is shutting down. Error details:\n"  ; 
logError  (  msg  ,  t  ,  ERROR_LOG  )  ; 
System  .  exit  (  1  )  ; 
} 
} 





protected   String   decodePercent  (  String   str  )  throws   InterruptedException  { 
try  { 
return   decodePercentHelper  (  str  )  ; 
}  catch  (  Exception   e  )  { 
sendError  (  HTTP_BADREQUEST  ,  "BAD REQUEST: Bad percent-encoding."  )  ; 
return   null  ; 
} 
} 






private   void   decodeParms  (  String   parms  ,  Properties   p  )  throws   InterruptedException  { 
if  (  parms  ==  null  )  return  ; 
StringTokenizer   st  =  new   StringTokenizer  (  parms  ,  "&"  )  ; 
while  (  st  .  hasMoreTokens  (  )  )  { 
String   e  =  st  .  nextToken  (  )  ; 
int   sep  =  e  .  indexOf  (  '='  )  ; 
if  (  sep  >=  0  )  p  .  put  (  decodePercent  (  e  .  substring  (  0  ,  sep  )  )  .  trim  (  )  ,  decodePercent  (  e  .  substring  (  sep  +  1  )  )  )  ; 
} 
} 





private   void   sendError  (  String   status  ,  String   msg  )  throws   InterruptedException  { 
sendResponse  (  status  ,  MIME_PLAINTEXT  ,  null  ,  new   ByteArrayInputStream  (  msg  .  getBytes  (  )  )  )  ; 
throw   new   InterruptedException  (  )  ; 
} 




private   void   sendResponse  (  String   status  ,  String   mime  ,  Properties   header  ,  InputStream   data  )  { 
try  { 
if  (  status  ==  null  )  throw   new   Error  (  "sendResponse(): Status can't be null."  )  ; 
OutputStream   out  =  mySocket  .  getOutputStream  (  )  ; 
PrintWriter   pw  =  new   PrintWriter  (  out  )  ; 
pw  .  print  (  "HTTP/1.0 "  +  status  +  " \r\n"  )  ; 
if  (  mime  !=  null  )  pw  .  print  (  "Content-Type: "  +  mime  +  "\r\n"  )  ; 
if  (  header  ==  null  ||  header  .  getProperty  (  "Date"  )  ==  null  )  pw  .  print  (  "Date: "  +  gmtFrmt  .  format  (  new   Date  (  )  )  +  "\r\n"  )  ; 
if  (  header  !=  null  )  { 
Enumeration   e  =  header  .  keys  (  )  ; 
while  (  e  .  hasMoreElements  (  )  )  { 
String   key  =  (  String  )  e  .  nextElement  (  )  ; 
String   value  =  header  .  getProperty  (  key  )  ; 
pw  .  print  (  key  +  ": "  +  value  +  "\r\n"  )  ; 
} 
} 
pw  .  print  (  "\r\n"  )  ; 
pw  .  flush  (  )  ; 
if  (  data  !=  null  )  { 
byte  [  ]  buff  =  new   byte  [  2048  ]  ; 
while  (  true  )  { 
int   read  =  data  .  read  (  buff  ,  0  ,  2048  )  ; 
if  (  read  <=  0  )  break  ; 
out  .  write  (  buff  ,  0  ,  read  )  ; 
} 
} 
out  .  flush  (  )  ; 
out  .  close  (  )  ; 
if  (  data  !=  null  )  data  .  close  (  )  ; 
}  catch  (  IOException   ioe  )  { 
try  { 
mySocket  .  close  (  )  ; 
}  catch  (  Throwable   t  )  { 
} 
} 
} 

private   Socket   mySocket  ; 
} 

; 





protected   String   encodeUri  (  String   uri  )  { 
String   newUri  =  ""  ; 
StringTokenizer   st  =  new   StringTokenizer  (  uri  ,  "/ "  ,  true  )  ; 
while  (  st  .  hasMoreTokens  (  )  )  { 
String   tok  =  st  .  nextToken  (  )  ; 
if  (  tok  .  equals  (  "/"  )  )  newUri  +=  "/"  ;  else   if  (  tok  .  equals  (  " "  )  )  newUri  +=  "%20"  ;  else  { 
try  { 
newUri  +=  URLEncoder  .  encode  (  tok  ,  "UTF-8"  )  ; 
}  catch  (  UnsupportedEncodingException   uee  )  { 
} 
} 
} 
return   newUri  ; 
} 

private   int   myTcpPort  ; 

File   myFileDir  ; 





public   Response   serveFile  (  String   uri  ,  Properties   header  ,  File   homeDir  ,  boolean   allowDirectoryListing  )  { 
if  (  !  homeDir  .  isDirectory  (  )  )  return   new   Response  (  HTTP_INTERNALERROR  ,  MIME_PLAINTEXT  ,  "INTERNAL ERRROR: serveFile(): given homeDir is not a directory."  )  ; 
uri  =  uri  .  trim  (  )  .  replace  (  File  .  separatorChar  ,  '/'  )  ; 
if  (  uri  .  indexOf  (  '?'  )  >=  0  )  uri  =  uri  .  substring  (  0  ,  uri  .  indexOf  (  '?'  )  )  ; 
if  (  uri  .  startsWith  (  ".."  )  ||  uri  .  endsWith  (  ".."  )  ||  uri  .  indexOf  (  "../"  )  >=  0  )  return   new   Response  (  HTTP_FORBIDDEN  ,  MIME_PLAINTEXT  ,  "FORBIDDEN: Won't serve ../ for security reasons."  )  ; 
File   f  =  new   File  (  homeDir  ,  uri  )  ; 
if  (  !  f  .  exists  (  )  )  return   new   Response  (  HTTP_NOTFOUND  ,  MIME_PLAINTEXT  ,  "Error 404, file not found."  )  ; 
if  (  f  .  isDirectory  (  )  )  { 
if  (  !  uri  .  endsWith  (  "/"  )  )  { 
uri  +=  "/"  ; 
Response   r  =  new   Response  (  HTTP_REDIRECT  ,  MIME_HTML  ,  "<html><body>Redirected: <a href=\""  +  uri  +  "\">"  +  uri  +  "</a></body></html>"  )  ; 
r  .  addHeader  (  "Location"  ,  uri  )  ; 
return   r  ; 
} 
if  (  new   File  (  f  ,  "index.html"  )  .  exists  (  )  )  f  =  new   File  (  homeDir  ,  uri  +  "/index.html"  )  ;  else   if  (  new   File  (  f  ,  "index.htm"  )  .  exists  (  )  )  f  =  new   File  (  homeDir  ,  uri  +  "/index.htm"  )  ;  else   if  (  allowDirectoryListing  )  { 
String  [  ]  files  =  f  .  list  (  )  ; 
String   msg  =  "<html><body><h1>Directory "  +  uri  +  "</h1><br/>"  ; 
if  (  uri  .  length  (  )  >  1  )  { 
String   u  =  uri  .  substring  (  0  ,  uri  .  length  (  )  -  1  )  ; 
int   slash  =  u  .  lastIndexOf  (  '/'  )  ; 
if  (  slash  >=  0  &&  slash  <  u  .  length  (  )  )  msg  +=  "<b><a href=\""  +  uri  .  substring  (  0  ,  slash  +  1  )  +  "\">..</a></b><br/>"  ; 
} 
for  (  int   i  =  0  ;  i  <  files  .  length  ;  ++  i  )  { 
File   curFile  =  new   File  (  f  ,  files  [  i  ]  )  ; 
boolean   dir  =  curFile  .  isDirectory  (  )  ; 
if  (  dir  )  { 
msg  +=  "<b>"  ; 
files  [  i  ]  +=  "/"  ; 
} 
msg  +=  "<a href=\""  +  encodeUri  (  uri  +  files  [  i  ]  )  +  "\">"  +  files  [  i  ]  +  "</a>"  ; 
if  (  curFile  .  isFile  (  )  )  { 
long   len  =  curFile  .  length  (  )  ; 
msg  +=  " &nbsp;<font size=2>("  ; 
if  (  len  <  1024  )  msg  +=  curFile  .  length  (  )  +  " bytes"  ;  else   if  (  len  <  1024  *  1024  )  msg  +=  curFile  .  length  (  )  /  1024  +  "."  +  (  curFile  .  length  (  )  %  1024  /  10  %  100  )  +  " KB"  ;  else   msg  +=  curFile  .  length  (  )  /  (  1024  *  1024  )  +  "."  +  curFile  .  length  (  )  %  (  1024  *  1024  )  /  10  %  100  +  " MB"  ; 
msg  +=  ")</font>"  ; 
} 
msg  +=  "<br/>"  ; 
if  (  dir  )  msg  +=  "</b>"  ; 
} 
return   new   Response  (  HTTP_OK  ,  MIME_HTML  ,  msg  )  ; 
}  else  { 
return   new   Response  (  HTTP_FORBIDDEN  ,  MIME_PLAINTEXT  ,  "FORBIDDEN: No directory listing."  )  ; 
} 
} 
try  { 
String   mime  =  null  ; 
int   dot  =  f  .  getCanonicalPath  (  )  .  lastIndexOf  (  '.'  )  ; 
if  (  dot  >=  0  )  mime  =  (  String  )  theMimeTypes  .  get  (  f  .  getCanonicalPath  (  )  .  substring  (  dot  +  1  )  .  toLowerCase  (  )  )  ; 
if  (  mime  ==  null  )  mime  =  MIME_DEFAULT_BINARY  ; 
long   startFrom  =  0  ; 
String   range  =  header  .  getProperty  (  "Range"  )  ; 
if  (  range  !=  null  )  { 
if  (  range  .  startsWith  (  "bytes="  )  )  { 
range  =  range  .  substring  (  "bytes="  .  length  (  )  )  ; 
int   minus  =  range  .  indexOf  (  '-'  )  ; 
if  (  minus  >  0  )  range  =  range  .  substring  (  0  ,  minus  )  ; 
try  { 
startFrom  =  Long  .  parseLong  (  range  )  ; 
}  catch  (  NumberFormatException   nfe  )  { 
} 
} 
} 
FileInputStream   fis  =  new   FileInputStream  (  f  )  ; 
fis  .  skip  (  startFrom  )  ; 
Response   r  =  new   Response  (  HTTP_OK  ,  mime  ,  fis  )  ; 
r  .  addHeader  (  "Content-length"  ,  ""  +  (  f  .  length  (  )  -  startFrom  )  )  ; 
r  .  addHeader  (  "Content-range"  ,  ""  +  startFrom  +  "-"  +  (  f  .  length  (  )  -  1  )  +  "/"  +  f  .  length  (  )  )  ; 
return   r  ; 
}  catch  (  IOException   ioe  )  { 
return   new   Response  (  HTTP_FORBIDDEN  ,  MIME_PLAINTEXT  ,  "FORBIDDEN: Reading file failed."  )  ; 
} 
} 




private   static   Hashtable   theMimeTypes  =  new   Hashtable  (  )  ; 

static  { 
StringTokenizer   st  =  new   StringTokenizer  (  "htm		text/html "  +  "html		text/html "  +  "txt		text/plain "  +  "asc		text/plain "  +  "gif		image/gif "  +  "jpg		image/jpeg "  +  "jpeg		image/jpeg "  +  "png		image/png "  +  "mp3		audio/mpeg "  +  "m3u		audio/mpeg-url "  +  "pdf		application/pdf "  +  "doc		application/msword "  +  "ogg		application/x-ogg "  +  "zip		application/octet-stream "  +  "exe		application/octet-stream "  +  "class		application/octet-stream "  )  ; 
while  (  st  .  hasMoreTokens  (  )  )  theMimeTypes  .  put  (  st  .  nextToken  (  )  ,  st  .  nextToken  (  )  )  ; 
} 




private   static   java  .  text  .  SimpleDateFormat   gmtFrmt  ; 

static  { 
try  { 
gmtFrmt  =  new   java  .  text  .  SimpleDateFormat  (  "E, d MMM yyyy HH:mm:ss "  ,  Locale  .  US  )  ; 
gmtFrmt  .  setTimeZone  (  TimeZone  .  getTimeZone  (  "GMT"  )  )  ; 
}  catch  (  Exception   e  )  { 
} 
} 




private   static   final   String   LICENCE  =  "Copyright (C) 2001,2005 by Jarno Elonen <elonen@iki.fi>\n"  +  "\n"  +  "Redistribution and use in source and binary forms, with or without\n"  +  "modification, are permitted provided that the following conditions\n"  +  "are met:\n"  +  "\n"  +  "Redistributions of source code must retain the above copyright notice,\n"  +  "this list of conditions and the following disclaimer. Redistributions in\n"  +  "binary form must reproduce the above copyright notice, this list of\n"  +  "conditions and the following disclaimer in the documentation and/or other\n"  +  "materials provided with the distribution. The name of the author may not\n"  +  "be used to endorse or promote products derived from this software without\n"  +  "specific prior written permission. \n"  +  " \n"  +  "THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR\n"  +  "IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES\n"  +  "OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.\n"  +  "IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,\n"  +  "INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT\n"  +  "NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,\n"  +  "DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY\n"  +  "THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT\n"  +  "(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE\n"  +  "OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE."  ; 
} 

