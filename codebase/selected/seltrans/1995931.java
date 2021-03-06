package   library  .  corba  ; 







public   class   _CORBALibraryServerLibrarianStub   extends   org  .  omg  .  CORBA  .  portable  .  ObjectImpl   implements   library  .  corba  .  CORBALibraryServerLibrarian  { 

public   boolean   validateReader  (  int   readerID  )  { 
org  .  omg  .  CORBA  .  portable  .  InputStream   $in  =  null  ; 
try  { 
org  .  omg  .  CORBA  .  portable  .  OutputStream   $out  =  _request  (  "validateReader"  ,  true  )  ; 
$out  .  write_ulong  (  readerID  )  ; 
$in  =  _invoke  (  $out  )  ; 
boolean   $result  =  $in  .  read_boolean  (  )  ; 
return   $result  ; 
}  catch  (  org  .  omg  .  CORBA  .  portable  .  ApplicationException   $ex  )  { 
$in  =  $ex  .  getInputStream  (  )  ; 
String   _id  =  $ex  .  getId  (  )  ; 
throw   new   org  .  omg  .  CORBA  .  MARSHAL  (  _id  )  ; 
}  catch  (  org  .  omg  .  CORBA  .  portable  .  RemarshalException   $rm  )  { 
return   validateReader  (  readerID  )  ; 
}  finally  { 
_releaseReply  (  $in  )  ; 
} 
} 

public   library  .  corba  .  BookedItemShort  [  ]  getBookedItems  (  )  { 
org  .  omg  .  CORBA  .  portable  .  InputStream   $in  =  null  ; 
try  { 
org  .  omg  .  CORBA  .  portable  .  OutputStream   $out  =  _request  (  "getBookedItems"  ,  true  )  ; 
$in  =  _invoke  (  $out  )  ; 
library  .  corba  .  BookedItemShort   $result  [  ]  =  library  .  corba  .  BookedItemsShortHelper  .  read  (  $in  )  ; 
return   $result  ; 
}  catch  (  org  .  omg  .  CORBA  .  portable  .  ApplicationException   $ex  )  { 
$in  =  $ex  .  getInputStream  (  )  ; 
String   _id  =  $ex  .  getId  (  )  ; 
throw   new   org  .  omg  .  CORBA  .  MARSHAL  (  _id  )  ; 
}  catch  (  org  .  omg  .  CORBA  .  portable  .  RemarshalException   $rm  )  { 
return   getBookedItems  (  )  ; 
}  finally  { 
_releaseReply  (  $in  )  ; 
} 
} 

public   int  [  ]  confirmLoan  (  int  [  ]  itemIDs  )  { 
org  .  omg  .  CORBA  .  portable  .  InputStream   $in  =  null  ; 
try  { 
org  .  omg  .  CORBA  .  portable  .  OutputStream   $out  =  _request  (  "confirmLoan"  ,  true  )  ; 
library  .  corba  .  integersHelper  .  write  (  $out  ,  itemIDs  )  ; 
$in  =  _invoke  (  $out  )  ; 
int   $result  [  ]  =  library  .  corba  .  integersHelper  .  read  (  $in  )  ; 
return   $result  ; 
}  catch  (  org  .  omg  .  CORBA  .  portable  .  ApplicationException   $ex  )  { 
$in  =  $ex  .  getInputStream  (  )  ; 
String   _id  =  $ex  .  getId  (  )  ; 
throw   new   org  .  omg  .  CORBA  .  MARSHAL  (  _id  )  ; 
}  catch  (  org  .  omg  .  CORBA  .  portable  .  RemarshalException   $rm  )  { 
return   confirmLoan  (  itemIDs  )  ; 
}  finally  { 
_releaseReply  (  $in  )  ; 
} 
} 

public   library  .  corba  .  ReaderAccountInfo   getReaderAccountInfo  (  )  { 
org  .  omg  .  CORBA  .  portable  .  InputStream   $in  =  null  ; 
try  { 
org  .  omg  .  CORBA  .  portable  .  OutputStream   $out  =  _request  (  "getReaderAccountInfo"  ,  true  )  ; 
$in  =  _invoke  (  $out  )  ; 
library  .  corba  .  ReaderAccountInfo   $result  =  library  .  corba  .  ReaderAccountInfoHelper  .  read  (  $in  )  ; 
return   $result  ; 
}  catch  (  org  .  omg  .  CORBA  .  portable  .  ApplicationException   $ex  )  { 
$in  =  $ex  .  getInputStream  (  )  ; 
String   _id  =  $ex  .  getId  (  )  ; 
throw   new   org  .  omg  .  CORBA  .  MARSHAL  (  _id  )  ; 
}  catch  (  org  .  omg  .  CORBA  .  portable  .  RemarshalException   $rm  )  { 
return   getReaderAccountInfo  (  )  ; 
}  finally  { 
_releaseReply  (  $in  )  ; 
} 
} 

public   void   exitReaderAccount  (  )  { 
org  .  omg  .  CORBA  .  portable  .  InputStream   $in  =  null  ; 
try  { 
org  .  omg  .  CORBA  .  portable  .  OutputStream   $out  =  _request  (  "exitReaderAccount"  ,  true  )  ; 
$in  =  _invoke  (  $out  )  ; 
return  ; 
}  catch  (  org  .  omg  .  CORBA  .  portable  .  ApplicationException   $ex  )  { 
$in  =  $ex  .  getInputStream  (  )  ; 
String   _id  =  $ex  .  getId  (  )  ; 
throw   new   org  .  omg  .  CORBA  .  MARSHAL  (  _id  )  ; 
}  catch  (  org  .  omg  .  CORBA  .  portable  .  RemarshalException   $rm  )  { 
exitReaderAccount  (  )  ; 
}  finally  { 
_releaseReply  (  $in  )  ; 
} 
} 

public   boolean   logout  (  )  { 
org  .  omg  .  CORBA  .  portable  .  InputStream   $in  =  null  ; 
try  { 
org  .  omg  .  CORBA  .  portable  .  OutputStream   $out  =  _request  (  "logout"  ,  true  )  ; 
$in  =  _invoke  (  $out  )  ; 
boolean   $result  =  $in  .  read_boolean  (  )  ; 
return   $result  ; 
}  catch  (  org  .  omg  .  CORBA  .  portable  .  ApplicationException   $ex  )  { 
$in  =  $ex  .  getInputStream  (  )  ; 
String   _id  =  $ex  .  getId  (  )  ; 
throw   new   org  .  omg  .  CORBA  .  MARSHAL  (  _id  )  ; 
}  catch  (  org  .  omg  .  CORBA  .  portable  .  RemarshalException   $rm  )  { 
return   logout  (  )  ; 
}  finally  { 
_releaseReply  (  $in  )  ; 
} 
} 

public   boolean   storeSession  (  library  .  corba  .  UserSession   usession  )  { 
org  .  omg  .  CORBA  .  portable  .  InputStream   $in  =  null  ; 
try  { 
org  .  omg  .  CORBA  .  portable  .  OutputStream   $out  =  _request  (  "storeSession"  ,  true  )  ; 
library  .  corba  .  UserSessionHelper  .  write  (  $out  ,  usession  )  ; 
$in  =  _invoke  (  $out  )  ; 
boolean   $result  =  $in  .  read_boolean  (  )  ; 
return   $result  ; 
}  catch  (  org  .  omg  .  CORBA  .  portable  .  ApplicationException   $ex  )  { 
$in  =  $ex  .  getInputStream  (  )  ; 
String   _id  =  $ex  .  getId  (  )  ; 
throw   new   org  .  omg  .  CORBA  .  MARSHAL  (  _id  )  ; 
}  catch  (  org  .  omg  .  CORBA  .  portable  .  RemarshalException   $rm  )  { 
return   storeSession  (  usession  )  ; 
}  finally  { 
_releaseReply  (  $in  )  ; 
} 
} 

public   library  .  corba  .  UserSession   readSession  (  )  { 
org  .  omg  .  CORBA  .  portable  .  InputStream   $in  =  null  ; 
try  { 
org  .  omg  .  CORBA  .  portable  .  OutputStream   $out  =  _request  (  "readSession"  ,  true  )  ; 
$in  =  _invoke  (  $out  )  ; 
library  .  corba  .  UserSession   $result  =  library  .  corba  .  UserSessionHelper  .  read  (  $in  )  ; 
return   $result  ; 
}  catch  (  org  .  omg  .  CORBA  .  portable  .  ApplicationException   $ex  )  { 
$in  =  $ex  .  getInputStream  (  )  ; 
String   _id  =  $ex  .  getId  (  )  ; 
throw   new   org  .  omg  .  CORBA  .  MARSHAL  (  _id  )  ; 
}  catch  (  org  .  omg  .  CORBA  .  portable  .  RemarshalException   $rm  )  { 
return   readSession  (  )  ; 
}  finally  { 
_releaseReply  (  $in  )  ; 
} 
} 

private   static   String  [  ]  __ids  =  {  "IDL:library/corba/CORBALibraryServerLibrarian:1.0"  }  ; 

public   String  [  ]  _ids  (  )  { 
return  (  String  [  ]  )  __ids  .  clone  (  )  ; 
} 

private   void   readObject  (  java  .  io  .  ObjectInputStream   s  )  throws   java  .  io  .  IOException  { 
String   str  =  s  .  readUTF  (  )  ; 
String  [  ]  args  =  null  ; 
java  .  util  .  Properties   props  =  null  ; 
org  .  omg  .  CORBA  .  Object   obj  =  org  .  omg  .  CORBA  .  ORB  .  init  (  args  ,  props  )  .  string_to_object  (  str  )  ; 
org  .  omg  .  CORBA  .  portable  .  Delegate   delegate  =  (  (  org  .  omg  .  CORBA  .  portable  .  ObjectImpl  )  obj  )  .  _get_delegate  (  )  ; 
_set_delegate  (  delegate  )  ; 
} 

private   void   writeObject  (  java  .  io  .  ObjectOutputStream   s  )  throws   java  .  io  .  IOException  { 
String  [  ]  args  =  null  ; 
java  .  util  .  Properties   props  =  null  ; 
String   str  =  org  .  omg  .  CORBA  .  ORB  .  init  (  args  ,  props  )  .  object_to_string  (  this  )  ; 
s  .  writeUTF  (  str  )  ; 
} 
} 

