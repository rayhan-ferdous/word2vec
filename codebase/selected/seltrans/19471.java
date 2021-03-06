package   edu  .  lcmi  .  grouppac  .  gui  ; 

import   java  .  awt  .  *  ; 
import   java  .  awt  .  event  .  ActionEvent  ; 
import   java  .  awt  .  event  .  InputEvent  ; 
import   java  .  awt  .  event  .  WindowAdapter  ; 
import   java  .  awt  .  event  .  WindowEvent  ; 
import   java  .  io  .  *  ; 
import   java  .  net  .  URL  ; 
import   java  .  net  .  URLConnection  ; 
import   java  .  util  .  Date  ; 
import   javax  .  swing  .  *  ; 









public   class   URLPostExceptionGUI   extends   javax  .  swing  .  JDialog  { 


public   static   final   int   RET_CANCEL  =  0  ; 


public   static   final   int   RET_OK  =  1  ; 

private   int   returnStatus  =  RET_CANCEL  ; 

private   Throwable   exception  ; 

private   JPanel   buttonPanel  ; 

private   JButton   cancelButton  ; 

private   JButton   jButton1  ; 

private   JTextField   mailAddress  ; 

private   JLabel   mailAddressLabel  ; 

private   JTextArea   mailBody  ; 

private   JLabel   mailBodyLabel  ; 

private   JScrollPane   mailBodyScrollPane  ; 

private   JLabel   mailHelp  ; 

private   JTextField   mailName  ; 

private   JLabel   mailNameLabel  ; 

private   JPanel   mailPanel  ; 

private   JLabel   msgExceptionClass  ; 

private   JLabel   msgLabel  ; 

private   JPanel   msgPanel  ; 

private   JButton   okButton  ; 

public   class   CancelDialog   extends   AbstractAction  { 




public   CancelDialog  (  )  { 
super  (  )  ; 
this  .  putValue  (  Action  .  NAME  ,  "Cancel"  )  ; 
this  .  putValue  (  Action  .  ACCELERATOR_KEY  ,  KeyStroke  .  getKeyStroke  (  'C'  ,  InputEvent  .  CTRL_DOWN_MASK  )  )  ; 
this  .  putValue  (  Action  .  MNEMONIC_KEY  ,  new   Integer  (  'c'  )  )  ; 
this  .  putValue  (  Action  .  SHORT_DESCRIPTION  ,  "Close this dialog"  )  ; 
} 





public   void   actionPerformed  (  ActionEvent   e  )  { 
doClose  (  RET_CANCEL  )  ; 
} 
} 

public   class   SaveToFile   extends   AbstractAction  { 




public   SaveToFile  (  )  { 
super  (  )  ; 
this  .  putValue  (  Action  .  NAME  ,  "Save to file"  )  ; 
this  .  putValue  (  Action  .  ACCELERATOR_KEY  ,  KeyStroke  .  getKeyStroke  (  'F'  ,  InputEvent  .  CTRL_DOWN_MASK  )  )  ; 
this  .  putValue  (  Action  .  MNEMONIC_KEY  ,  new   Integer  (  'f'  )  )  ; 
this  .  putValue  (  Action  .  SHORT_DESCRIPTION  ,  "Save to file 'grouppac.log'"  )  ; 
} 





public   void   actionPerformed  (  ActionEvent   e  )  { 
String   logFilename  =  "grouppac.log"  ; 
try  { 
OutputStream   fos  =  new   FileOutputStream  (  logFilename  ,  true  )  ; 
OutputStream   bos  =  new   BufferedOutputStream  (  fos  )  ; 
PrintStream   out  =  new   PrintStream  (  bos  )  ; 
out  .  println  (  "Unhandled exception "  +  new   Date  (  )  )  ; 
getException  (  )  .  printStackTrace  (  out  )  ; 
out  .  close  (  )  ; 
}  catch  (  Exception   ignore  )  { 
Object  [  ]  message  =  {  "Cannot save exception to file "  +  logFilename  +  ". Exception throwed:"  ,  getException  (  )  .  toString  (  )  }  ; 
JOptionPane  .  showConfirmDialog  (  null  ,  message  ,  "Unhandled Exception"  ,  JOptionPane  .  YES_NO_OPTION  ,  JOptionPane  .  ERROR_MESSAGE  )  ; 
} 
doClose  (  RET_OK  )  ; 
} 
} 

public   class   SendByEmail   extends   AbstractAction  { 

public   String   DEFAULT_FORM  =  "/sendmessage.php"  ; 

public   String   DEFAULT_HOST  =  "sourceforge.net"  ; 

public   String   DEFAULT_PROTOCOL  =  "http"  ; 

private   String   FORM_DATA1  =  "touser=137238"  ; 

private   String   FORM_DATA2  =  "toaddress="  ; 

private   String   USER_BODY  =  "body="  ; 

private   String   USER_EMAIL  =  "email="  ; 

private   String   USER_NAME  =  "name="  ; 

private   String   USER_SEND_MAIL  =  "send_mail=Send_Message"  ; 

private   String   USER_SUBJECT  =  "subject="  ; 




public   SendByEmail  (  )  { 
this  .  putValue  (  Action  .  NAME  ,  "Send e-mail"  )  ; 
this  .  putValue  (  Action  .  ACCELERATOR_KEY  ,  KeyStroke  .  getKeyStroke  (  'E'  ,  InputEvent  .  CTRL_DOWN_MASK  )  )  ; 
this  .  putValue  (  Action  .  MNEMONIC_KEY  ,  new   Integer  (  'e'  )  )  ; 
this  .  putValue  (  Action  .  SHORT_DESCRIPTION  ,  "Send e-mail to GroupPac team"  )  ; 
} 





public   void   actionPerformed  (  ActionEvent   e  )  { 
try  { 
postData  (  null  ,  null  ,  null  ,  makeHTTPPostData  (  getUserName  (  )  ,  getUserEmail  (  )  ,  getBody  (  )  ,  getException  (  )  )  )  ; 
doClose  (  RET_OK  )  ; 
}  catch  (  Exception   ex  )  { 
new   SaveToFile  (  )  .  actionPerformed  (  e  )  ; 
} 
} 











public   String   makeHTTPPostData  (  String   username  ,  String   useremail  ,  String   body  ,  Throwable   e  )  throws   UnsupportedEncodingException  { 
StringWriter   sw  =  new   StringWriter  (  )  ; 
sw  .  write  (  FORM_DATA1  )  ; 
sw  .  write  (  "&"  )  ; 
sw  .  write  (  FORM_DATA2  )  ; 
sw  .  write  (  "&"  )  ; 
sw  .  write  (  USER_EMAIL  +  useremail  )  ; 
sw  .  write  (  "&"  )  ; 
sw  .  write  (  USER_NAME  +  username  )  ; 
sw  .  write  (  "&"  )  ; 
sw  .  write  (  USER_SUBJECT  +  "[grouppac-user] Unhandled exception: "  +  e  .  toString  (  )  )  ; 
sw  .  write  (  "&"  )  ; 
sw  .  write  (  USER_BODY  +  body  +  "\n\n--- Exception Stack Trace ---\n"  )  ; 
e  .  printStackTrace  (  new   PrintWriter  (  sw  )  )  ; 
sw  .  write  (  "&"  )  ; 
sw  .  write  (  USER_SEND_MAIL  )  ; 
return   sw  .  toString  (  )  ; 
} 











public   void   postData  (  String   protocol  ,  String   host  ,  String   form  ,  String   data  )  throws   Exception  { 
if  (  (  protocol  ==  null  )  ||  (  protocol  .  equals  (  ""  )  )  )  protocol  =  DEFAULT_PROTOCOL  ; 
if  (  (  host  ==  null  )  ||  (  host  .  equals  (  ""  )  )  )  host  =  DEFAULT_HOST  ; 
if  (  form  ==  null  )  form  =  DEFAULT_FORM  ; 
if  (  data  ==  null  )  throw   new   IllegalArgumentException  (  "Invalid data"  )  ; 
URL   url  =  new   URL  (  protocol  ,  host  ,  form  )  ; 
URLConnection   con  =  url  .  openConnection  (  )  ; 
con  .  setDoOutput  (  true  )  ; 
con  .  setDoInput  (  true  )  ; 
con  .  setUseCaches  (  false  )  ; 
con  .  setRequestProperty  (  "Content-type"  ,  "application/x-www-form-urlencoded"  )  ; 
con  .  setRequestProperty  (  "Content-length"  ,  String  .  valueOf  (  data  .  length  (  )  )  )  ; 
PrintStream   out  =  new   PrintStream  (  con  .  getOutputStream  (  )  ,  true  )  ; 
out  .  print  (  data  )  ; 
out  .  close  (  )  ; 
DataInputStream   in  =  new   DataInputStream  (  con  .  getInputStream  (  )  )  ; 
while  (  (  in  .  read  (  )  )  !=  -  1  )  { 
} 
in  .  close  (  )  ; 
} 
} 







public   URLPostExceptionGUI  (  Frame   parent  ,  boolean   modal  ,  Throwable   ex  )  { 
super  (  parent  ,  modal  )  ; 
this  .  exception  =  ex  ; 
initComponents  (  )  ; 
pos_init  (  )  ; 
} 




public   String   getBody  (  )  { 
return   mailBody  .  getText  (  )  ; 
} 




public   Throwable   getException  (  )  { 
return   exception  ; 
} 




public   int   getReturnStatus  (  )  { 
return   returnStatus  ; 
} 




public   String   getUserEmail  (  )  { 
return   mailAddress  .  getText  (  )  ; 
} 




public   String   getUserName  (  )  { 
return   mailName  .  getText  (  )  ; 
} 




public   static   void   main  (  String  [  ]  args  )  { 
new   URLPostExceptionGUI  (  new   JFrame  (  )  ,  true  ,  new   Exception  (  "Some exception"  )  )  .  show  (  )  ; 
} 





protected   void   closeDialog  (  WindowEvent   evt  )  { 
doClose  (  RET_CANCEL  )  ; 
} 






protected   void   doClose  (  int   retStatus  )  { 
returnStatus  =  retStatus  ; 
setVisible  (  false  )  ; 
dispose  (  )  ; 
} 





private   void   initComponents  (  )  { 
GridBagConstraints   gridBagConstraints  ; 
buttonPanel  =  new   JPanel  (  )  ; 
jButton1  =  new   JButton  (  )  ; 
okButton  =  new   JButton  (  )  ; 
cancelButton  =  new   JButton  (  )  ; 
mailPanel  =  new   JPanel  (  )  ; 
mailHelp  =  new   JLabel  (  )  ; 
mailNameLabel  =  new   JLabel  (  )  ; 
mailName  =  new   JTextField  (  )  ; 
mailAddressLabel  =  new   JLabel  (  )  ; 
mailAddress  =  new   JTextField  (  )  ; 
mailBodyLabel  =  new   JLabel  (  )  ; 
mailBodyScrollPane  =  new   JScrollPane  (  )  ; 
mailBody  =  new   JTextArea  (  )  ; 
msgPanel  =  new   JPanel  (  )  ; 
msgLabel  =  new   JLabel  (  )  ; 
msgExceptionClass  =  new   JLabel  (  )  ; 
getContentPane  (  )  .  setLayout  (  new   GridBagLayout  (  )  )  ; 
setTitle  (  "Unhandled Exception"  )  ; 
addWindowListener  (  new   WindowAdapter  (  )  { 

public   void   windowClosing  (  WindowEvent   evt  )  { 
closeDialog  (  evt  )  ; 
} 
}  )  ; 
buttonPanel  .  setLayout  (  new   FlowLayout  (  FlowLayout  .  RIGHT  )  )  ; 
jButton1  .  setAction  (  new   SaveToFile  (  )  )  ; 
buttonPanel  .  add  (  jButton1  )  ; 
okButton  .  setAction  (  new   SendByEmail  (  )  )  ; 
buttonPanel  .  add  (  okButton  )  ; 
cancelButton  .  setAction  (  new   CancelDialog  (  )  )  ; 
buttonPanel  .  add  (  cancelButton  )  ; 
gridBagConstraints  =  new   GridBagConstraints  (  )  ; 
gridBagConstraints  .  gridx  =  0  ; 
gridBagConstraints  .  gridy  =  2  ; 
gridBagConstraints  .  fill  =  GridBagConstraints  .  HORIZONTAL  ; 
gridBagConstraints  .  insets  =  new   Insets  (  4  ,  4  ,  4  ,  4  )  ; 
gridBagConstraints  .  weightx  =  1.0  ; 
getContentPane  (  )  .  add  (  buttonPanel  ,  gridBagConstraints  )  ; 
mailPanel  .  setLayout  (  new   GridBagLayout  (  )  )  ; 
mailHelp  .  setFont  (  new   Font  (  "Dialog"  ,  0  ,  10  )  )  ; 
mailHelp  .  setText  (  "Privacy note: Your name and e-mail will be used only for feedback on this matter."  )  ; 
gridBagConstraints  =  new   GridBagConstraints  (  )  ; 
gridBagConstraints  .  gridwidth  =  GridBagConstraints  .  REMAINDER  ; 
gridBagConstraints  .  anchor  =  GridBagConstraints  .  WEST  ; 
gridBagConstraints  .  insets  =  new   Insets  (  4  ,  4  ,  4  ,  4  )  ; 
mailPanel  .  add  (  mailHelp  ,  gridBagConstraints  )  ; 
mailNameLabel  .  setText  (  "Name:"  )  ; 
gridBagConstraints  =  new   GridBagConstraints  (  )  ; 
gridBagConstraints  .  gridx  =  0  ; 
gridBagConstraints  .  gridy  =  1  ; 
gridBagConstraints  .  anchor  =  GridBagConstraints  .  WEST  ; 
gridBagConstraints  .  insets  =  new   Insets  (  4  ,  4  ,  4  ,  4  )  ; 
mailPanel  .  add  (  mailNameLabel  ,  gridBagConstraints  )  ; 
gridBagConstraints  =  new   GridBagConstraints  (  )  ; 
gridBagConstraints  .  gridx  =  1  ; 
gridBagConstraints  .  gridy  =  1  ; 
gridBagConstraints  .  fill  =  GridBagConstraints  .  HORIZONTAL  ; 
gridBagConstraints  .  weightx  =  0.5  ; 
gridBagConstraints  .  insets  =  new   Insets  (  4  ,  4  ,  4  ,  4  )  ; 
mailPanel  .  add  (  mailName  ,  gridBagConstraints  )  ; 
mailAddressLabel  .  setText  (  "E-mail:"  )  ; 
gridBagConstraints  =  new   GridBagConstraints  (  )  ; 
gridBagConstraints  .  anchor  =  GridBagConstraints  .  WEST  ; 
gridBagConstraints  .  insets  =  new   Insets  (  4  ,  4  ,  4  ,  4  )  ; 
mailPanel  .  add  (  mailAddressLabel  ,  gridBagConstraints  )  ; 
gridBagConstraints  =  new   GridBagConstraints  (  )  ; 
gridBagConstraints  .  fill  =  GridBagConstraints  .  HORIZONTAL  ; 
gridBagConstraints  .  weightx  =  0.5  ; 
gridBagConstraints  .  insets  =  new   Insets  (  4  ,  4  ,  4  ,  4  )  ; 
mailPanel  .  add  (  mailAddress  ,  gridBagConstraints  )  ; 
mailBodyLabel  .  setText  (  "Explain what you were doing when the exception was throwed:"  )  ; 
gridBagConstraints  =  new   GridBagConstraints  (  )  ; 
gridBagConstraints  .  gridx  =  0  ; 
gridBagConstraints  .  gridy  =  2  ; 
gridBagConstraints  .  gridwidth  =  GridBagConstraints  .  REMAINDER  ; 
gridBagConstraints  .  anchor  =  GridBagConstraints  .  WEST  ; 
gridBagConstraints  .  insets  =  new   Insets  (  4  ,  4  ,  4  ,  4  )  ; 
mailPanel  .  add  (  mailBodyLabel  ,  gridBagConstraints  )  ; 
mailBody  .  setPreferredSize  (  new   Dimension  (  192  ,  128  )  )  ; 
mailBodyScrollPane  .  setViewportView  (  mailBody  )  ; 
gridBagConstraints  =  new   GridBagConstraints  (  )  ; 
gridBagConstraints  .  gridwidth  =  GridBagConstraints  .  REMAINDER  ; 
gridBagConstraints  .  fill  =  GridBagConstraints  .  BOTH  ; 
gridBagConstraints  .  insets  =  new   Insets  (  4  ,  4  ,  4  ,  4  )  ; 
gridBagConstraints  .  weightx  =  1.0  ; 
gridBagConstraints  .  weighty  =  1.0  ; 
mailPanel  .  add  (  mailBodyScrollPane  ,  gridBagConstraints  )  ; 
gridBagConstraints  =  new   GridBagConstraints  (  )  ; 
gridBagConstraints  .  gridx  =  0  ; 
gridBagConstraints  .  gridy  =  1  ; 
gridBagConstraints  .  fill  =  GridBagConstraints  .  BOTH  ; 
gridBagConstraints  .  insets  =  new   Insets  (  4  ,  4  ,  4  ,  4  )  ; 
gridBagConstraints  .  weightx  =  1.0  ; 
gridBagConstraints  .  weighty  =  1.0  ; 
getContentPane  (  )  .  add  (  mailPanel  ,  gridBagConstraints  )  ; 
msgPanel  .  setLayout  (  new   FlowLayout  (  FlowLayout  .  LEFT  )  )  ; 
msgLabel  .  setText  (  "Unhandled Exception:"  )  ; 
msgPanel  .  add  (  msgLabel  )  ; 
msgExceptionClass  .  setFont  (  new   Font  (  "Dialog"  ,  0  ,  12  )  )  ; 
msgExceptionClass  .  setText  (  "Exception"  )  ; 
msgPanel  .  add  (  msgExceptionClass  )  ; 
gridBagConstraints  =  new   GridBagConstraints  (  )  ; 
gridBagConstraints  .  fill  =  GridBagConstraints  .  HORIZONTAL  ; 
gridBagConstraints  .  insets  =  new   Insets  (  4  ,  4  ,  4  ,  4  )  ; 
gridBagConstraints  .  weightx  =  1.0  ; 
getContentPane  (  )  .  add  (  msgPanel  ,  gridBagConstraints  )  ; 
pack  (  )  ; 
} 




private   void   pos_init  (  )  { 
msgExceptionClass  .  setText  (  getException  (  )  .  toString  (  )  )  ; 
} 
} 

