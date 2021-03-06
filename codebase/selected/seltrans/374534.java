package   eu  .  planets_project  .  ifr  .  core  .  services  .  validation  .  impl  ; 

import   java  .  io  .  File  ; 
import   java  .  io  .  Serializable  ; 
import   java  .  net  .  URI  ; 
import   java  .  util  .  Arrays  ; 
import   java  .  util  .  List  ; 
import   javax  .  ejb  .  Stateless  ; 
import   javax  .  jws  .  WebMethod  ; 
import   javax  .  jws  .  WebService  ; 
import   javax  .  xml  .  ws  .  BindingType  ; 
import   org  .  apache  .  commons  .  logging  .  Log  ; 
import   org  .  apache  .  commons  .  logging  .  LogFactory  ; 
import   com  .  sun  .  xml  .  ws  .  developer  .  StreamingAttachment  ; 
import   eu  .  planets_project  .  services  .  PlanetsServices  ; 
import   eu  .  planets_project  .  services  .  datatypes  .  DigitalObject  ; 
import   eu  .  planets_project  .  services  .  datatypes  .  Parameter  ; 
import   eu  .  planets_project  .  services  .  datatypes  .  ServiceDescription  ; 
import   eu  .  planets_project  .  services  .  datatypes  .  ServiceReport  ; 
import   eu  .  planets_project  .  services  .  datatypes  .  Tool  ; 
import   eu  .  planets_project  .  services  .  datatypes  .  ServiceReport  .  Status  ; 
import   eu  .  planets_project  .  services  .  datatypes  .  ServiceReport  .  Type  ; 
import   eu  .  planets_project  .  services  .  utils  .  FileUtils  ; 
import   eu  .  planets_project  .  services  .  utils  .  ProcessRunner  ; 
import   eu  .  planets_project  .  services  .  validate  .  Validate  ; 
import   eu  .  planets_project  .  services  .  validate  .  ValidateResult  ; 





@  WebService  (  name  =  PngCheck  .  NAME  ,  serviceName  =  Validate  .  NAME  ,  targetNamespace  =  PlanetsServices  .  NS  ,  endpointInterface  =  "eu.planets_project.services.validate.Validate"  ) 
@  StreamingAttachment  (  parseEagerly  =  true  ) 
@  Stateless 
@  BindingType  (  value  =  "http://schemas.xmlsoap.org/wsdl/soap/http?mtom=true"  ) 
public   final   class   PngCheck   implements   Validate  ,  Serializable  { 


private   static   final   long   serialVersionUID  =  -  596706737946485163L  ; 


static   final   String   NAME  =  "PngCheck"  ; 





private   static   final   List  <  URI  >  PNG_PRONOM  =  Arrays  .  asList  (  URI  .  create  (  "info:pronom/fmt/11"  )  ,  URI  .  create  (  "info:pronom/fmt/12"  )  ,  URI  .  create  (  "info:pronom/fmt/13"  )  )  ; 


private   static   final   Log   LOG  =  LogFactory  .  getLog  (  PngCheck  .  class  )  ; 









private   boolean   basicValidateOneBinary  (  final   File   tempFile  ,  final   URI   fmt  )  { 
if  (  fmt  !=  null  &&  !  PNG_PRONOM  .  contains  (  fmt  )  )  { 
throw   new   IllegalArgumentException  (  "PngCheck can only validate PNG ("  +  PNG_PRONOM  +  ") files, not "  +  fmt  .  toString  (  )  )  ; 
} 
List  <  String  >  commands  =  Arrays  .  asList  (  "pngcheck"  ,  tempFile  .  getAbsolutePath  (  )  )  ; 
ProcessRunner   pr  =  new   ProcessRunner  (  commands  )  ; 
LOG  .  debug  (  "Executing: "  +  commands  )  ; 
pr  .  run  (  )  ; 
String   output  =  pr  .  getProcessOutputAsString  (  )  ; 
LOG  .  debug  (  "PngCheck call output: "  +  output  )  ; 
LOG  .  debug  (  "PngCheck call error: "  +  pr  .  getProcessErrorAsString  (  )  )  ; 
return   output  .  contains  (  "OK:"  )  ; 
} 










@  WebMethod 
public   boolean   basicValidateOneBinary  (  final   String   fileName  )  { 
return   basicValidateOneBinary  (  new   File  (  fileName  )  ,  null  )  ; 
} 





public   ServiceDescription   describe  (  )  { 
ServiceDescription  .  Builder   sd  =  new   ServiceDescription  .  Builder  (  NAME  ,  Validate  .  class  .  getCanonicalName  (  )  )  ; 
sd  .  classname  (  this  .  getClass  (  )  .  getCanonicalName  (  )  )  ; 
sd  .  description  (  "Validation service based on PngCheck."  )  ; 
sd  .  author  (  "Fabian Steeg"  )  ; 
sd  .  inputFormats  (  PNG_PRONOM  .  toArray  (  new   URI  [  ]  {  }  )  )  ; 
sd  .  tool  (  Tool  .  create  (  null  ,  "PngCheck"  ,  null  ,  null  ,  "http://www.libpng.org/pub/png/apps/pngcheck.html"  )  )  ; 
sd  .  serviceProvider  (  "The Planets Consortium"  )  ; 
return   sd  .  build  (  )  ; 
} 




public   ValidateResult   validate  (  final   DigitalObject   digitalObject  ,  final   URI   format  ,  final   List  <  Parameter  >  parameters  )  { 
File   file  =  FileUtils  .  writeInputStreamToTmpFile  (  digitalObject  .  getContent  (  )  .  read  (  )  ,  "pngcheck-temp"  ,  "bin"  )  ; 
boolean   valid  =  basicValidateOneBinary  (  file  ,  format  )  ; 
ValidateResult   result  =  new   ValidateResult  .  Builder  (  format  ,  new   ServiceReport  (  Type  .  INFO  ,  Status  .  SUCCESS  ,  "OK"  )  )  .  ofThisFormat  (  valid  )  .  validInRegardToThisFormat  (  valid  )  .  build  (  )  ; 
return   result  ; 
} 
} 

