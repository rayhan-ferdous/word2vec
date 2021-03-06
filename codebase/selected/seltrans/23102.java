package   org  .  jumpmind  .  symmetric  .  transport  .  http  ; 

import   java  .  io  .  BufferedReader  ; 
import   java  .  io  .  BufferedWriter  ; 
import   java  .  io  .  IOException  ; 
import   java  .  io  .  OutputStream  ; 
import   java  .  io  .  OutputStreamWriter  ; 
import   java  .  net  .  HttpURLConnection  ; 
import   java  .  net  .  URL  ; 
import   java  .  util  .  zip  .  GZIPOutputStream  ; 
import   org  .  apache  .  commons  .  io  .  IOUtils  ; 
import   org  .  jumpmind  .  exception  .  IoException  ; 
import   org  .  jumpmind  .  symmetric  .  common  .  Constants  ; 
import   org  .  jumpmind  .  symmetric  .  model  .  ChannelMap  ; 
import   org  .  jumpmind  .  symmetric  .  service  .  IConfigurationService  ; 
import   org  .  jumpmind  .  symmetric  .  service  .  RegistrationRequiredException  ; 
import   org  .  jumpmind  .  symmetric  .  transport  .  AuthenticationException  ; 
import   org  .  jumpmind  .  symmetric  .  transport  .  ConnectionRejectedException  ; 
import   org  .  jumpmind  .  symmetric  .  transport  .  IOutgoingWithResponseTransport  ; 
import   org  .  jumpmind  .  symmetric  .  transport  .  SyncDisabledException  ; 
import   org  .  jumpmind  .  symmetric  .  web  .  WebConstants  ; 

public   class   HttpOutgoingTransport   implements   IOutgoingWithResponseTransport  { 

private   URL   url  ; 

private   BufferedWriter   writer  ; 

private   BufferedReader   reader  ; 

private   HttpURLConnection   connection  ; 

private   int   httpTimeout  ; 

private   boolean   useCompression  ; 

private   int   compressionStrategy  ; 

private   int   compressionLevel  ; 

private   String   basicAuthUsername  ; 

private   String   basicAuthPassword  ; 

private   boolean   streamOutputEnabled  =  false  ; 

private   int   streamOutputChunkSize  =  30720  ; 

public   HttpOutgoingTransport  (  URL   url  ,  int   httpTimeout  ,  boolean   useCompression  ,  int   compressionStrategy  ,  int   compressionLevel  ,  String   basicAuthUsername  ,  String   basicAuthPassword  ,  boolean   streamOutputEnabled  ,  int   streamOutputSize  )  { 
this  .  url  =  url  ; 
this  .  httpTimeout  =  httpTimeout  ; 
this  .  useCompression  =  useCompression  ; 
this  .  compressionLevel  =  compressionLevel  ; 
this  .  compressionStrategy  =  compressionStrategy  ; 
this  .  basicAuthUsername  =  basicAuthUsername  ; 
this  .  basicAuthPassword  =  basicAuthPassword  ; 
this  .  streamOutputChunkSize  =  streamOutputSize  ; 
this  .  streamOutputEnabled  =  streamOutputEnabled  ; 
} 

public   void   close  (  )  { 
closeWriter  (  true  )  ; 
closeReader  (  )  ; 
if  (  connection  !=  null  )  { 
connection  .  disconnect  (  )  ; 
connection  =  null  ; 
} 
} 

private   void   closeReader  (  )  { 
if  (  reader  !=  null  )  { 
IOUtils  .  closeQuietly  (  reader  )  ; 
reader  =  null  ; 
} 
} 

private   void   closeWriter  (  boolean   closeQuietly  )  { 
if  (  writer  !=  null  )  { 
try  { 
writer  .  flush  (  )  ; 
}  catch  (  IOException   ex  )  { 
throw   new   IoException  (  ex  )  ; 
}  finally  { 
if  (  closeQuietly  )  { 
IOUtils  .  closeQuietly  (  writer  )  ; 
}  else  { 
try  { 
writer  .  close  (  )  ; 
}  catch  (  IOException   ex  )  { 
throw   new   IoException  (  ex  )  ; 
} 
} 
writer  =  null  ; 
} 
} 
} 










private   HttpURLConnection   requestReservation  (  )  { 
try  { 
connection  =  HttpTransportManager  .  openConnection  (  url  ,  basicAuthUsername  ,  basicAuthPassword  )  ; 
connection  .  setUseCaches  (  false  )  ; 
connection  .  setConnectTimeout  (  httpTimeout  )  ; 
connection  .  setReadTimeout  (  httpTimeout  )  ; 
connection  .  setRequestMethod  (  "HEAD"  )  ; 
analyzeResponseCode  (  connection  .  getResponseCode  (  )  )  ; 
}  catch  (  IOException   ex  )  { 
throw   new   IoException  (  ex  )  ; 
} 
return   connection  ; 
} 

public   BufferedWriter   open  (  )  { 
try  { 
connection  =  HttpTransportManager  .  openConnection  (  url  ,  basicAuthUsername  ,  basicAuthPassword  )  ; 
if  (  streamOutputEnabled  )  { 
connection  .  setChunkedStreamingMode  (  streamOutputChunkSize  )  ; 
} 
connection  .  setDoInput  (  true  )  ; 
connection  .  setDoOutput  (  true  )  ; 
connection  .  setUseCaches  (  false  )  ; 
connection  .  setConnectTimeout  (  httpTimeout  )  ; 
connection  .  setReadTimeout  (  httpTimeout  )  ; 
connection  .  setRequestMethod  (  "PUT"  )  ; 
connection  .  setRequestProperty  (  "accept-encoding"  ,  "gzip"  )  ; 
if  (  useCompression  )  { 
connection  .  addRequestProperty  (  "Content-Type"  ,  "gzip"  )  ; 
} 
OutputStream   out  =  connection  .  getOutputStream  (  )  ; 
if  (  useCompression  )  { 
out  =  new   GZIPOutputStream  (  out  )  { 

{ 
this  .  def  .  setLevel  (  compressionLevel  )  ; 
this  .  def  .  setStrategy  (  compressionStrategy  )  ; 
} 
}  ; 
} 
OutputStreamWriter   wout  =  new   OutputStreamWriter  (  out  ,  Constants  .  ENCODING  )  ; 
writer  =  new   BufferedWriter  (  wout  )  ; 
return   writer  ; 
}  catch  (  IOException   ex  )  { 
throw   new   IoException  (  ex  )  ; 
} 
} 





private   void   analyzeResponseCode  (  int   code  )  throws   IOException  { 
if  (  WebConstants  .  SC_SERVICE_UNAVAILABLE  ==  code  )  { 
throw   new   ConnectionRejectedException  (  )  ; 
}  else   if  (  WebConstants  .  SC_FORBIDDEN  ==  code  )  { 
throw   new   AuthenticationException  (  )  ; 
}  else   if  (  WebConstants  .  SYNC_DISABLED  ==  code  )  { 
throw   new   SyncDisabledException  (  )  ; 
}  else   if  (  WebConstants  .  REGISTRATION_REQUIRED  ==  code  )  { 
throw   new   RegistrationRequiredException  (  )  ; 
} 
} 

public   BufferedReader   readResponse  (  )  throws   IOException  { 
closeWriter  (  false  )  ; 
analyzeResponseCode  (  connection  .  getResponseCode  (  )  )  ; 
this  .  reader  =  HttpTransportManager  .  getReaderFrom  (  connection  )  ; 
return   this  .  reader  ; 
} 

public   boolean   isOpen  (  )  { 
return   connection  !=  null  ; 
} 

public   ChannelMap   getSuspendIgnoreChannelLists  (  IConfigurationService   configurationService  )  { 
HttpURLConnection   connection  =  requestReservation  (  )  ; 
ChannelMap   suspendIgnoreChannelsList  =  new   ChannelMap  (  )  ; 
String   suspends  =  connection  .  getHeaderField  (  WebConstants  .  SUSPENDED_CHANNELS  )  ; 
String   ignores  =  connection  .  getHeaderField  (  WebConstants  .  IGNORED_CHANNELS  )  ; 
suspendIgnoreChannelsList  .  addSuspendChannels  (  suspends  )  ; 
suspendIgnoreChannelsList  .  addIgnoreChannels  (  ignores  )  ; 
return   suspendIgnoreChannelsList  ; 
} 
} 

