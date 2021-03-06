package   totalcommander  ; 

import   java  .  awt  .  event  .  ActionEvent  ; 
import   java  .  awt  .  event  .  ActionListener  ; 
import   java  .  io  .  BufferedInputStream  ; 
import   java  .  io  .  BufferedOutputStream  ; 
import   java  .  io  .  File  ; 
import   java  .  io  .  FileInputStream  ; 
import   java  .  io  .  FileOutputStream  ; 
import   java  .  io  .  IOException  ; 
import   java  .  io  .  InputStream  ; 
import   java  .  io  .  OutputStream  ; 
import   java  .  lang  .  Thread  .  State  ; 
import   java  .  util  .  Enumeration  ; 
import   java  .  util  .  zip  .  Deflater  ; 
import   java  .  util  .  zip  .  ZipEntry  ; 
import   java  .  util  .  zip  .  ZipFile  ; 
import   java  .  util  .  zip  .  ZipOutputStream  ; 
import   javax  .  swing  .  JList  ; 
import   javax  .  swing  .  JOptionPane  ; 

public   class   MainForm   extends   javax  .  swing  .  JFrame  { 

String   leftPath  =  (  "C:\\"  )  ; 

String   rightPath  =  (  "C:\\"  )  ; 

FileEntry   selectedEntry  ; 

int   flag  =  0  ; 


public   MainForm  (  )  { 
initComponents  (  )  ; 
File  [  ]  f  =  File  .  listRoots  (  )  ; 
for  (  int   i  =  0  ;  i  <  f  .  length  ;  i  ++  )  { 
cb_leftPath  .  addItem  (  f  [  i  ]  .  toString  (  )  )  ; 
cb_rightPath  .  addItem  (  f  [  i  ]  .  toString  (  )  )  ; 
} 
cb_leftPath  .  getItemAt  (  0  )  ; 
cb_rightPath  .  getItemAt  (  0  )  ; 
leftPath  =  (  cb_leftPath  .  getItemAt  (  0  )  .  toString  (  )  )  ; 
rightPath  =  (  cb_rightPath  .  getItemAt  (  0  )  .  toString  (  )  )  ; 
LeftPath  .  setText  (  leftPath  +  "*.*"  )  ; 
RightPath  .  setText  (  rightPath  +  "*.*"  )  ; 
jListDisplay  (  leftPath  ,  jList1  )  ; 
jListDisplay  (  rightPath  ,  jList2  )  ; 
ActionListener   actionListener1  =  new   ActionListener  (  )  { 

public   void   actionPerformed  (  ActionEvent   actionEvent  )  { 
leftPath  =  cb_leftPath  .  getSelectedItem  (  )  .  toString  (  )  ; 
jListDisplay  (  leftPath  ,  jList1  )  ; 
LeftPath  .  setText  (  leftPath  +  "*.*"  )  ; 
} 
}  ; 
ActionListener   actionListener2  =  new   ActionListener  (  )  { 

public   void   actionPerformed  (  ActionEvent   actionEvent  )  { 
rightPath  =  cb_rightPath  .  getSelectedItem  (  )  .  toString  (  )  ; 
jListDisplay  (  rightPath  ,  jList2  )  ; 
RightPath  .  setText  (  rightPath  +  "*.*"  )  ; 
} 
}  ; 
cb_leftPath  .  addActionListener  (  actionListener1  )  ; 
cb_rightPath  .  addActionListener  (  actionListener2  )  ; 
File   newFolder  =  new   File  (  "BackUp"  )  ; 
newFolder  .  mkdirs  (  )  ; 
} 

public   void   jListDisplay  (  String   Path  ,  JList   jList  )  { 
try  { 
ListFileEntries   lstFileEntries  =  new   ListFileEntries  (  Path  )  ; 
jList  .  setCellRenderer  (  new   TabListCellRenderer  (  )  )  ; 
if  (  lstFileEntries  .  getList  (  )  !=  null  )  { 
jList  .  setListData  (  lstFileEntries  .  getList  (  )  )  ; 
} 
}  catch  (  Exception   e  )  { 
} 
} 

public   void   jListReDisplay  (  )  { 
jListDisplay  (  leftPath  ,  jList1  )  ; 
jListDisplay  (  rightPath  ,  jList2  )  ; 
} 






@  SuppressWarnings  (  "unchecked"  ) 
private   void   initComponents  (  )  { 
Body  =  new   javax  .  swing  .  JPanel  (  )  ; 
LeftWindow  =  new   javax  .  swing  .  JScrollPane  (  )  ; 
jList1  =  new   javax  .  swing  .  JList  (  )  ; 
RightWindow  =  new   javax  .  swing  .  JScrollPane  (  )  ; 
jList2  =  new   javax  .  swing  .  JList  (  )  ; 
Header  =  new   javax  .  swing  .  JPanel  (  )  ; 
PanelPath  =  new   javax  .  swing  .  JPanel  (  )  ; 
LeftPath  =  new   javax  .  swing  .  JLabel  (  )  ; 
RightPath  =  new   javax  .  swing  .  JLabel  (  )  ; 
jPanel2  =  new   javax  .  swing  .  JPanel  (  )  ; 
jPanel1  =  new   javax  .  swing  .  JPanel  (  )  ; 
jPanel4  =  new   javax  .  swing  .  JPanel  (  )  ; 
cb_leftPath  =  new   javax  .  swing  .  JComboBox  (  )  ; 
jPanel5  =  new   javax  .  swing  .  JPanel  (  )  ; 
cb_rightPath  =  new   javax  .  swing  .  JComboBox  (  )  ; 
jPanel3  =  new   javax  .  swing  .  JPanel  (  )  ; 
Footer  =  new   javax  .  swing  .  JPanel  (  )  ; 
CommandButton  =  new   javax  .  swing  .  JPanel  (  )  ; 
F3View  =  new   javax  .  swing  .  JButton  (  )  ; 
F4Move  =  new   javax  .  swing  .  JButton  (  )  ; 
F5Copy  =  new   javax  .  swing  .  JButton  (  )  ; 
F6Zip  =  new   javax  .  swing  .  JButton  (  )  ; 
F7Unzip  =  new   javax  .  swing  .  JButton  (  )  ; 
F8Delete  =  new   javax  .  swing  .  JButton  (  )  ; 
AltF4Exit  =  new   javax  .  swing  .  JButton  (  )  ; 
Path  =  new   javax  .  swing  .  JPanel  (  )  ; 
lb_Path  =  new   javax  .  swing  .  JLabel  (  )  ; 
cb_Name  =  new   javax  .  swing  .  JComboBox  (  )  ; 
setDefaultCloseOperation  (  javax  .  swing  .  WindowConstants  .  EXIT_ON_CLOSE  )  ; 
Body  .  setBorder  (  javax  .  swing  .  BorderFactory  .  createLineBorder  (  new   java  .  awt  .  Color  (  0  ,  0  ,  0  )  )  )  ; 
Body  .  setLayout  (  new   java  .  awt  .  GridLayout  (  1  ,  0  ,  10  ,  10  )  )  ; 
jList1  .  setDragEnabled  (  true  )  ; 
jList1  .  setDropMode  (  javax  .  swing  .  DropMode  .  INSERT  )  ; 
jList1  .  addMouseListener  (  new   java  .  awt  .  event  .  MouseAdapter  (  )  { 

public   void   mouseClicked  (  java  .  awt  .  event  .  MouseEvent   evt  )  { 
jList1MouseClicked  (  evt  )  ; 
} 
}  )  ; 
LeftWindow  .  setViewportView  (  jList1  )  ; 
Body  .  add  (  LeftWindow  )  ; 
jList2  .  setDragEnabled  (  true  )  ; 
jList2  .  setDropMode  (  javax  .  swing  .  DropMode  .  INSERT  )  ; 
jList2  .  addMouseListener  (  new   java  .  awt  .  event  .  MouseAdapter  (  )  { 

public   void   mouseClicked  (  java  .  awt  .  event  .  MouseEvent   evt  )  { 
jList2MouseClicked  (  evt  )  ; 
} 
}  )  ; 
RightWindow  .  setViewportView  (  jList2  )  ; 
Body  .  add  (  RightWindow  )  ; 
getContentPane  (  )  .  add  (  Body  ,  java  .  awt  .  BorderLayout  .  CENTER  )  ; 
Header  .  setBorder  (  javax  .  swing  .  BorderFactory  .  createLineBorder  (  new   java  .  awt  .  Color  (  0  ,  0  ,  0  )  )  )  ; 
Header  .  setLayout  (  new   java  .  awt  .  BorderLayout  (  )  )  ; 
PanelPath  .  setBorder  (  javax  .  swing  .  BorderFactory  .  createLineBorder  (  new   java  .  awt  .  Color  (  0  ,  0  ,  0  )  )  )  ; 
PanelPath  .  setLayout  (  new   java  .  awt  .  GridLayout  (  1  ,  0  ,  10  ,  10  )  )  ; 
LeftPath  .  setBackground  (  new   java  .  awt  .  Color  (  51  ,  51  ,  255  )  )  ; 
LeftPath  .  setForeground  (  new   java  .  awt  .  Color  (  255  ,  255  ,  255  )  )  ; 
LeftPath  .  setText  (  "jLabel1"  )  ; 
LeftPath  .  setBorder  (  javax  .  swing  .  BorderFactory  .  createEmptyBorder  (  1  ,  1  ,  1  ,  1  )  )  ; 
LeftPath  .  setOpaque  (  true  )  ; 
PanelPath  .  add  (  LeftPath  )  ; 
RightPath  .  setBackground  (  new   java  .  awt  .  Color  (  51  ,  51  ,  255  )  )  ; 
RightPath  .  setForeground  (  new   java  .  awt  .  Color  (  255  ,  255  ,  255  )  )  ; 
RightPath  .  setText  (  "jLabel2"  )  ; 
RightPath  .  setBorder  (  javax  .  swing  .  BorderFactory  .  createEmptyBorder  (  1  ,  1  ,  1  ,  1  )  )  ; 
RightPath  .  setOpaque  (  true  )  ; 
PanelPath  .  add  (  RightPath  )  ; 
Header  .  add  (  PanelPath  ,  java  .  awt  .  BorderLayout  .  CENTER  )  ; 
jPanel2  .  setBorder  (  javax  .  swing  .  BorderFactory  .  createLineBorder  (  new   java  .  awt  .  Color  (  0  ,  0  ,  0  )  )  )  ; 
jPanel2  .  setLayout  (  new   java  .  awt  .  BorderLayout  (  )  )  ; 
jPanel1  .  setLayout  (  new   java  .  awt  .  GridLayout  (  )  )  ; 
cb_leftPath  .  addItemListener  (  new   java  .  awt  .  event  .  ItemListener  (  )  { 

public   void   itemStateChanged  (  java  .  awt  .  event  .  ItemEvent   evt  )  { 
cb_leftPathItemStateChanged  (  evt  )  ; 
} 
}  )  ; 
javax  .  swing  .  GroupLayout   jPanel4Layout  =  new   javax  .  swing  .  GroupLayout  (  jPanel4  )  ; 
jPanel4  .  setLayout  (  jPanel4Layout  )  ; 
jPanel4Layout  .  setHorizontalGroup  (  jPanel4Layout  .  createParallelGroup  (  javax  .  swing  .  GroupLayout  .  Alignment  .  LEADING  )  .  addGroup  (  jPanel4Layout  .  createSequentialGroup  (  )  .  addComponent  (  cb_leftPath  ,  javax  .  swing  .  GroupLayout  .  PREFERRED_SIZE  ,  javax  .  swing  .  GroupLayout  .  DEFAULT_SIZE  ,  javax  .  swing  .  GroupLayout  .  PREFERRED_SIZE  )  .  addContainerGap  (  411  ,  Short  .  MAX_VALUE  )  )  )  ; 
jPanel4Layout  .  setVerticalGroup  (  jPanel4Layout  .  createParallelGroup  (  javax  .  swing  .  GroupLayout  .  Alignment  .  LEADING  )  .  addGroup  (  jPanel4Layout  .  createSequentialGroup  (  )  .  addComponent  (  cb_leftPath  ,  javax  .  swing  .  GroupLayout  .  PREFERRED_SIZE  ,  javax  .  swing  .  GroupLayout  .  DEFAULT_SIZE  ,  javax  .  swing  .  GroupLayout  .  PREFERRED_SIZE  )  .  addContainerGap  (  javax  .  swing  .  GroupLayout  .  DEFAULT_SIZE  ,  Short  .  MAX_VALUE  )  )  )  ; 
jPanel1  .  add  (  jPanel4  )  ; 
javax  .  swing  .  GroupLayout   jPanel5Layout  =  new   javax  .  swing  .  GroupLayout  (  jPanel5  )  ; 
jPanel5  .  setLayout  (  jPanel5Layout  )  ; 
jPanel5Layout  .  setHorizontalGroup  (  jPanel5Layout  .  createParallelGroup  (  javax  .  swing  .  GroupLayout  .  Alignment  .  LEADING  )  .  addGroup  (  jPanel5Layout  .  createSequentialGroup  (  )  .  addComponent  (  cb_rightPath  ,  javax  .  swing  .  GroupLayout  .  PREFERRED_SIZE  ,  javax  .  swing  .  GroupLayout  .  DEFAULT_SIZE  ,  javax  .  swing  .  GroupLayout  .  PREFERRED_SIZE  )  .  addContainerGap  (  411  ,  Short  .  MAX_VALUE  )  )  )  ; 
jPanel5Layout  .  setVerticalGroup  (  jPanel5Layout  .  createParallelGroup  (  javax  .  swing  .  GroupLayout  .  Alignment  .  LEADING  )  .  addGroup  (  jPanel5Layout  .  createSequentialGroup  (  )  .  addComponent  (  cb_rightPath  ,  javax  .  swing  .  GroupLayout  .  PREFERRED_SIZE  ,  javax  .  swing  .  GroupLayout  .  DEFAULT_SIZE  ,  javax  .  swing  .  GroupLayout  .  PREFERRED_SIZE  )  .  addContainerGap  (  11  ,  Short  .  MAX_VALUE  )  )  )  ; 
jPanel1  .  add  (  jPanel5  )  ; 
jPanel2  .  add  (  jPanel1  ,  java  .  awt  .  BorderLayout  .  CENTER  )  ; 
javax  .  swing  .  GroupLayout   jPanel3Layout  =  new   javax  .  swing  .  GroupLayout  (  jPanel3  )  ; 
jPanel3  .  setLayout  (  jPanel3Layout  )  ; 
jPanel3Layout  .  setHorizontalGroup  (  jPanel3Layout  .  createParallelGroup  (  javax  .  swing  .  GroupLayout  .  Alignment  .  LEADING  )  .  addGap  (  0  ,  880  ,  Short  .  MAX_VALUE  )  )  ; 
jPanel3Layout  .  setVerticalGroup  (  jPanel3Layout  .  createParallelGroup  (  javax  .  swing  .  GroupLayout  .  Alignment  .  LEADING  )  .  addGap  (  0  ,  100  ,  Short  .  MAX_VALUE  )  )  ; 
jPanel2  .  add  (  jPanel3  ,  java  .  awt  .  BorderLayout  .  PAGE_START  )  ; 
Header  .  add  (  jPanel2  ,  java  .  awt  .  BorderLayout  .  PAGE_START  )  ; 
getContentPane  (  )  .  add  (  Header  ,  java  .  awt  .  BorderLayout  .  PAGE_START  )  ; 
Footer  .  setBorder  (  javax  .  swing  .  BorderFactory  .  createLineBorder  (  new   java  .  awt  .  Color  (  0  ,  0  ,  0  )  )  )  ; 
Footer  .  setLayout  (  new   java  .  awt  .  BorderLayout  (  )  )  ; 
CommandButton  .  setBorder  (  javax  .  swing  .  BorderFactory  .  createLineBorder  (  new   java  .  awt  .  Color  (  0  ,  0  ,  0  )  )  )  ; 
CommandButton  .  setLayout  (  new   java  .  awt  .  GridLayout  (  1  ,  0  )  )  ; 
F3View  .  setText  (  "F3 View"  )  ; 
F3View  .  addActionListener  (  new   java  .  awt  .  event  .  ActionListener  (  )  { 

public   void   actionPerformed  (  java  .  awt  .  event  .  ActionEvent   evt  )  { 
F3ViewActionPerformed  (  evt  )  ; 
} 
}  )  ; 
CommandButton  .  add  (  F3View  )  ; 
F4Move  .  setText  (  "F4 Move"  )  ; 
F4Move  .  addActionListener  (  new   java  .  awt  .  event  .  ActionListener  (  )  { 

public   void   actionPerformed  (  java  .  awt  .  event  .  ActionEvent   evt  )  { 
F4MoveActionPerformed  (  evt  )  ; 
} 
}  )  ; 
CommandButton  .  add  (  F4Move  )  ; 
F5Copy  .  setText  (  "F5 Copy"  )  ; 
F5Copy  .  addActionListener  (  new   java  .  awt  .  event  .  ActionListener  (  )  { 

public   void   actionPerformed  (  java  .  awt  .  event  .  ActionEvent   evt  )  { 
F5CopyActionPerformed  (  evt  )  ; 
} 
}  )  ; 
CommandButton  .  add  (  F5Copy  )  ; 
F6Zip  .  setText  (  "F6 Zip"  )  ; 
F6Zip  .  addActionListener  (  new   java  .  awt  .  event  .  ActionListener  (  )  { 

public   void   actionPerformed  (  java  .  awt  .  event  .  ActionEvent   evt  )  { 
F6ZipActionPerformed  (  evt  )  ; 
} 
}  )  ; 
CommandButton  .  add  (  F6Zip  )  ; 
F7Unzip  .  setText  (  "F7 Unzip"  )  ; 
F7Unzip  .  addActionListener  (  new   java  .  awt  .  event  .  ActionListener  (  )  { 

public   void   actionPerformed  (  java  .  awt  .  event  .  ActionEvent   evt  )  { 
F7UnzipActionPerformed  (  evt  )  ; 
} 
}  )  ; 
CommandButton  .  add  (  F7Unzip  )  ; 
F8Delete  .  setText  (  "F8 Delete"  )  ; 
F8Delete  .  addActionListener  (  new   java  .  awt  .  event  .  ActionListener  (  )  { 

public   void   actionPerformed  (  java  .  awt  .  event  .  ActionEvent   evt  )  { 
F8DeleteActionPerformed  (  evt  )  ; 
} 
}  )  ; 
CommandButton  .  add  (  F8Delete  )  ; 
AltF4Exit  .  setText  (  "Alt+F4 Exit"  )  ; 
AltF4Exit  .  addActionListener  (  new   java  .  awt  .  event  .  ActionListener  (  )  { 

public   void   actionPerformed  (  java  .  awt  .  event  .  ActionEvent   evt  )  { 
AltF4ExitActionPerformed  (  evt  )  ; 
} 
}  )  ; 
CommandButton  .  add  (  AltF4Exit  )  ; 
Footer  .  add  (  CommandButton  ,  java  .  awt  .  BorderLayout  .  CENTER  )  ; 
Path  .  setBorder  (  javax  .  swing  .  BorderFactory  .  createLineBorder  (  new   java  .  awt  .  Color  (  0  ,  0  ,  0  )  )  )  ; 
Path  .  setLayout  (  new   java  .  awt  .  GridLayout  (  1  ,  0  ,  10  ,  10  )  )  ; 
lb_Path  .  setHorizontalAlignment  (  javax  .  swing  .  SwingConstants  .  RIGHT  )  ; 
lb_Path  .  setText  (  "C:\\"  )  ; 
Path  .  add  (  lb_Path  )  ; 
cb_Name  .  setModel  (  new   javax  .  swing  .  DefaultComboBoxModel  (  new   String  [  ]  {  "Item 1"  ,  "Item 2"  ,  "Item 3"  ,  "Item 4"  }  )  )  ; 
cb_Name  .  addActionListener  (  new   java  .  awt  .  event  .  ActionListener  (  )  { 

public   void   actionPerformed  (  java  .  awt  .  event  .  ActionEvent   evt  )  { 
cb_NameActionPerformed  (  evt  )  ; 
} 
}  )  ; 
Path  .  add  (  cb_Name  )  ; 
Footer  .  add  (  Path  ,  java  .  awt  .  BorderLayout  .  PAGE_START  )  ; 
getContentPane  (  )  .  add  (  Footer  ,  java  .  awt  .  BorderLayout  .  PAGE_END  )  ; 
pack  (  )  ; 
} 

private   void   cb_NameActionPerformed  (  java  .  awt  .  event  .  ActionEvent   evt  )  { 
} 

private   void   jList1MouseClicked  (  java  .  awt  .  event  .  MouseEvent   evt  )  { 
flag  =  1  ; 
selectedEntry  =  (  FileEntry  )  jList1  .  getSelectedValue  (  )  ; 
if  (  evt  .  getClickCount  (  )  ==  2  )  { 
if  (  selectedEntry  .  isDir  (  )  ||  selectedEntry  .  isBackEntry  (  )  )  { 
if  (  selectedEntry  .  getName  (  )  .  equals  (  ".."  )  )  { 
String  [  ]  temp  =  leftPath  .  split  (  "\\\\"  )  ; 
leftPath  =  ""  ; 
for  (  int   i  =  0  ;  i  <  temp  .  length  -  1  ;  i  ++  )  { 
leftPath  +=  temp  [  i  ]  +  "\\"  ; 
} 
}  else  { 
leftPath  =  leftPath  +  selectedEntry  .  getName  (  )  +  "\\"  ; 
} 
jListDisplay  (  leftPath  ,  jList1  )  ; 
LeftPath  .  setText  (  leftPath  +  "*.*"  )  ; 
} 
} 
} 

private   void   jList2MouseClicked  (  java  .  awt  .  event  .  MouseEvent   evt  )  { 
flag  =  2  ; 
selectedEntry  =  (  FileEntry  )  jList2  .  getSelectedValue  (  )  ; 
if  (  evt  .  getClickCount  (  )  ==  2  )  { 
if  (  selectedEntry  .  isDir  (  )  ||  selectedEntry  .  isBackEntry  (  )  )  { 
if  (  selectedEntry  .  getName  (  )  .  equals  (  ".."  )  )  { 
String  [  ]  temp  =  rightPath  .  split  (  "\\\\"  )  ; 
rightPath  =  ""  ; 
for  (  int   i  =  0  ;  i  <  temp  .  length  -  1  ;  i  ++  )  { 
rightPath  +=  temp  [  i  ]  +  "\\"  ; 
} 
}  else  { 
rightPath  =  rightPath  +  selectedEntry  .  getName  (  )  +  "\\"  ; 
} 
jListDisplay  (  rightPath  ,  jList2  )  ; 
RightPath  .  setText  (  rightPath  +  "*.*"  )  ; 
} 
} 
} 

public   ProcessForm   processForm  ; 

myThread   thread  ; 

private   void   F8DeleteActionPerformed  (  java  .  awt  .  event  .  ActionEvent   evt  )  { 
if  (  selectedEntry  !=  null  )  { 
String   fileName  =  selectedEntry  .  getPath  (  )  ; 
if  (  fileName  ==  null  )  { 
return  ; 
} 
int   response  ; 
response  =  JOptionPane  .  showConfirmDialog  (  null  ,  "Are you sure?"  )  ; 
if  (  response  ==  JOptionPane  .  YES_OPTION  )  { 
processForm  =  new   ProcessForm  (  filesCount  (  selectedEntry  .  getPath  (  )  )  ,  this  )  ; 
processForm  .  setVisible  (  true  )  ; 
thread  =  new   myThread  (  selectedEntry  .  getPath  (  )  ,  this  ,  AConf  .  DELETE  )  ; 
thread  .  start  (  )  ; 
} 
} 
} 

private   void   F5CopyActionPerformed  (  java  .  awt  .  event  .  ActionEvent   evt  )  { 
if  (  selectedEntry  !=  null  )  { 
String   fileName  =  selectedEntry  .  getPath  (  )  ; 
if  (  fileName  ==  null  )  { 
return  ; 
} 
processForm  =  new   ProcessForm  (  filesCount  (  selectedEntry  .  getPath  (  )  )  ,  this  )  ; 
processForm  .  setVisible  (  true  )  ; 
if  (  flag  ==  1  )  { 
thread  =  new   myThread  (  fileName  ,  this  ,  AConf  .  COPY  )  ; 
thread  .  _toFilePath  =  rightPath  ; 
thread  .  start  (  )  ; 
}  else  { 
if  (  flag  ==  2  )  { 
thread  =  new   myThread  (  fileName  ,  this  ,  AConf  .  COPY  )  ; 
thread  .  _toFilePath  =  leftPath  ; 
thread  .  start  (  )  ; 
}  else  { 
return  ; 
} 
} 
} 
} 

private   void   F4MoveActionPerformed  (  java  .  awt  .  event  .  ActionEvent   evt  )  { 
if  (  selectedEntry  !=  null  )  { 
String   fileName  =  selectedEntry  .  getPath  (  )  ; 
if  (  fileName  ==  null  )  { 
return  ; 
} 
processForm  =  new   ProcessForm  (  filesCount  (  selectedEntry  .  getPath  (  )  )  *  2  ,  this  )  ; 
processForm  .  setVisible  (  true  )  ; 
thread  =  new   myThread  (  fileName  ,  this  ,  AConf  .  MOVE  )  ; 
String   newPath  ; 
if  (  flag  ==  1  )  { 
thread  .  _toFilePath  =  rightPath  ; 
}  else  { 
if  (  flag  ==  2  )  { 
thread  .  _toFilePath  =  leftPath  ; 
}  else  { 
return  ; 
} 
} 
thread  .  start  (  )  ; 
} 
} 

private   void   F6ZipActionPerformed  (  java  .  awt  .  event  .  ActionEvent   evt  )  { 
if  (  selectedEntry  !=  null  )  { 
String   fileName  =  selectedEntry  .  getPath  (  )  ; 
if  (  fileName  ==  null  )  { 
return  ; 
} 
if  (  selectedEntry  .  isDir  (  )  )  { 
try  { 
String   temp  =  selectedEntry  .  getPath  (  )  ; 
ZipOutputStream   zos  =  new   ZipOutputStream  (  new   FileOutputStream  (  selectedEntry  .  getPath  (  )  +  ".zip"  )  )  ; 
zipDir  (  selectedEntry  .  getPath  (  )  ,  zos  ,  selectedEntry  .  getName  (  )  )  ; 
zos  .  close  (  )  ; 
}  catch  (  Exception   e  )  { 
} 
}  else  { 
zipFile  (  selectedEntry  )  ; 
} 
jListDisplay  (  leftPath  ,  jList1  )  ; 
jListDisplay  (  rightPath  ,  jList2  )  ; 
} 
} 

private   void   F7UnzipActionPerformed  (  java  .  awt  .  event  .  ActionEvent   evt  )  { 
if  (  selectedEntry  !=  null  )  { 
String   fileName  =  selectedEntry  .  getPath  (  )  ; 
if  (  fileName  .  substring  (  fileName  .  length  (  )  -  3  )  .  equals  (  "zip"  )  )  { 
unzipFile  (  fileName  ,  selectedEntry  .  getParent  (  )  )  ; 
} 
jListDisplay  (  leftPath  ,  jList1  )  ; 
jListDisplay  (  rightPath  ,  jList2  )  ; 
} 
} 

private   void   F3ViewActionPerformed  (  java  .  awt  .  event  .  ActionEvent   evt  )  { 
if  (  selectedEntry  !=  null  )  { 
String   fileName  =  selectedEntry  .  getPath  (  )  ; 
if  (  fileName  .  substring  (  fileName  .  length  (  )  -  3  )  .  equals  (  "txt"  )  )  { 
try  { 
Runtime  .  getRuntime  (  )  .  exec  (  "notepad"  +  " "  +  fileName  )  ; 
}  catch  (  Exception   e  )  { 
System  .  out  .  println  (  "notepad is an unknown command."  )  ; 
} 
} 
} 
} 

private   void   AltF4ExitActionPerformed  (  java  .  awt  .  event  .  ActionEvent   evt  )  { 
} 

private   void   cb_leftPathItemStateChanged  (  java  .  awt  .  event  .  ItemEvent   evt  )  { 
} 

public   void   deleteFile  (  String   file  )  { 
File   f  =  new   File  (  file  )  ; 
if  (  f  .  isDirectory  (  )  )  { 
for  (  int   i  =  0  ;  i  <  f  .  listFiles  (  )  .  length  ;  i  ++  )  { 
deleteFile  (  f  .  listFiles  (  )  [  i  ]  .  getPath  (  )  )  ; 
i  --  ; 
} 
} 
boolean   success  =  f  .  delete  (  )  ; 
} 

public   boolean   copy  (  String   fromFileName  ,  String   toFileName  )  throws   IOException  { 
File   fromFile  =  new   File  (  fromFileName  )  ; 
File   toFile  =  new   File  (  toFileName  )  ; 
if  (  !  fromFile  .  exists  (  )  )  { 
return   false  ; 
} 
if  (  !  fromFile  .  isFile  (  )  )  { 
File   newFolder  =  new   File  (  toFile  ,  fromFile  .  getName  (  )  )  ; 
int   temp  =  0  ; 
if  (  newFolder  .  getPath  (  )  .  equals  (  fromFile  .  getPath  (  )  )  )  { 
while  (  newFolder  .  exists  (  )  )  { 
newFolder  =  new   File  (  toFile  ,  "Copy "  +  temp  +  " of "  +  fromFile  .  getName  (  )  )  ; 
temp  ++  ; 
} 
}  else  { 
if  (  newFolder  .  exists  (  )  )  { 
int   response  ; 
response  =  JOptionPane  .  showConfirmDialog  (  null  ,  "Overwrite existing folder "  +  newFolder  .  getName  (  )  )  ; 
if  (  response  !=  JOptionPane  .  YES_OPTION  )  { 
return   false  ; 
}  else  { 
deleteFile  (  newFolder  .  getPath  (  )  )  ; 
} 
} 
} 
newFolder  .  mkdirs  (  )  ; 
for  (  int   i  =  0  ;  i  <  fromFile  .  listFiles  (  )  .  length  ;  i  ++  )  { 
copy  (  fromFile  .  listFiles  (  )  [  i  ]  .  getPath  (  )  ,  newFolder  .  getPath  (  )  )  ; 
} 
return   true  ; 
} 
if  (  !  fromFile  .  canRead  (  )  )  { 
return   false  ; 
} 
if  (  toFile  .  isDirectory  (  )  )  { 
toFile  =  new   File  (  toFile  ,  fromFile  .  getName  (  )  )  ; 
} 
if  (  toFile  .  exists  (  )  )  { 
if  (  !  toFile  .  getPath  (  )  .  equals  (  fromFile  .  getPath  (  )  )  )  { 
if  (  !  toFile  .  canWrite  (  )  )  { 
return   false  ; 
} 
int   response  ; 
response  =  JOptionPane  .  showConfirmDialog  (  null  ,  "Overwrite existing file "  +  toFile  .  getName  (  )  )  ; 
if  (  response  !=  JOptionPane  .  YES_OPTION  )  { 
return   false  ; 
} 
}  else  { 
int   n  =  0  ; 
while  (  toFile  .  exists  (  )  )  { 
toFile  =  new   File  (  toFile  .  getParent  (  )  ,  "Copy "  +  n  +  " of "  +  fromFile  .  getName  (  )  )  ; 
n  ++  ; 
} 
} 
}  else  { 
String   parent  =  toFile  .  getParent  (  )  ; 
if  (  parent  ==  null  )  { 
parent  =  System  .  getProperty  (  "user.dir"  )  ; 
} 
File   dir  =  new   File  (  parent  )  ; 
if  (  !  dir  .  exists  (  )  )  { 
return   false  ; 
} 
if  (  dir  .  isFile  (  )  )  { 
return   false  ; 
} 
if  (  !  dir  .  canWrite  (  )  )  { 
return   false  ; 
} 
} 
FileInputStream   from  =  null  ; 
FileOutputStream   to  =  null  ; 
try  { 
from  =  new   FileInputStream  (  fromFile  )  ; 
to  =  new   FileOutputStream  (  toFile  )  ; 
byte  [  ]  buffer  =  new   byte  [  4096  ]  ; 
int   bytesRead  ; 
while  (  (  bytesRead  =  from  .  read  (  buffer  )  )  !=  -  1  )  { 
to  .  write  (  buffer  ,  0  ,  bytesRead  )  ; 
} 
return   true  ; 
}  finally  { 
if  (  from  !=  null  )  { 
try  { 
from  .  close  (  )  ; 
}  catch  (  IOException   e  )  { 
; 
} 
} 
if  (  to  !=  null  )  { 
try  { 
to  .  close  (  )  ; 
}  catch  (  IOException   e  )  { 
; 
} 
} 
} 
} 

public   void   renameFile  (  String   oldName  ,  String   newName  )  { 
File   file  =  new   File  (  oldName  )  ; 
File   file2  =  new   File  (  newName  )  ; 
boolean   success  =  file  .  renameTo  (  file2  )  ; 
if  (  !  success  )  { 
System  .  out  .  println  (  "Oops!"  )  ; 
} 
} 

public   void   zipFile  (  FileEntry   fileEntry  )  { 
String   zipfilename  =  fileEntry  .  getPath  (  )  ; 
if  (  fileEntry  .  isDir  (  )  )  { 
zipfilename  +=  ".zip"  ; 
}  else  { 
zipfilename  =  zipfilename  .  substring  (  0  ,  zipfilename  .  length  (  )  -  4  )  +  ".zip"  ; 
} 
try  { 
ZipOutputStream   out  =  new   ZipOutputStream  (  new   BufferedOutputStream  (  new   FileOutputStream  (  zipfilename  )  )  )  ; 
out  .  setLevel  (  Deflater  .  DEFAULT_COMPRESSION  )  ; 
byte  [  ]  data  =  new   byte  [  1000  ]  ; 
BufferedInputStream   in  =  new   BufferedInputStream  (  new   FileInputStream  (  fileEntry  .  getPath  (  )  )  )  ; 
int   count  ; 
out  .  putNextEntry  (  new   ZipEntry  (  fileEntry  .  getName  (  )  )  )  ; 
while  (  (  count  =  in  .  read  (  data  ,  0  ,  1000  )  )  !=  -  1  )  { 
out  .  write  (  data  ,  0  ,  count  )  ; 
} 
in  .  close  (  )  ; 
out  .  flush  (  )  ; 
out  .  close  (  )  ; 
}  catch  (  Exception   e  )  { 
e  .  printStackTrace  (  )  ; 
} 
} 

public   void   zipDir  (  String   dir2zip  ,  ZipOutputStream   zos  ,  String   rootPath  )  { 
try  { 
File   zipDir  =  new   File  (  dir2zip  )  ; 
String  [  ]  dirList  =  zipDir  .  list  (  )  ; 
byte  [  ]  readBuffer  =  new   byte  [  2156  ]  ; 
int   bytesIn  =  0  ; 
zos  .  putNextEntry  (  new   ZipEntry  (  rootPath  +  "/"  )  )  ; 
for  (  int   i  =  0  ;  i  <  dirList  .  length  ;  i  ++  )  { 
File   f  =  new   File  (  zipDir  ,  dirList  [  i  ]  )  ; 
if  (  f  .  isDirectory  (  )  )  { 
String   filePath  =  f  .  getPath  (  )  ; 
String   temp  =  rootPath  +  "\\"  +  f  .  getName  (  )  ; 
zipDir  (  filePath  ,  zos  ,  temp  )  ; 
continue  ; 
} 
FileInputStream   fis  =  new   FileInputStream  (  f  )  ; 
ZipEntry   anEntry  =  new   ZipEntry  (  rootPath  +  "\\"  +  f  .  getName  (  )  )  ; 
zos  .  putNextEntry  (  anEntry  )  ; 
while  (  (  bytesIn  =  fis  .  read  (  readBuffer  )  )  !=  -  1  )  { 
zos  .  write  (  readBuffer  ,  0  ,  bytesIn  )  ; 
} 
fis  .  close  (  )  ; 
} 
}  catch  (  Exception   e  )  { 
} 
} 

public   void   unzipFile  (  String   filename  ,  String   location  )  { 
try  { 
ZipFile   zipFile  =  new   ZipFile  (  filename  )  ; 
Enumeration   entries  =  zipFile  .  entries  (  )  ; 
while  (  entries  .  hasMoreElements  (  )  )  { 
ZipEntry   entry  =  (  ZipEntry  )  entries  .  nextElement  (  )  ; 
if  (  entry  .  isDirectory  (  )  )  { 
(  new   File  (  location  +  "\\"  +  entry  .  getName  (  )  )  )  .  mkdir  (  )  ; 
continue  ; 
} 
copyInputStream  (  zipFile  .  getInputStream  (  entry  )  ,  new   BufferedOutputStream  (  new   FileOutputStream  (  location  +  "\\"  +  entry  .  getName  (  )  )  )  )  ; 
} 
zipFile  .  close  (  )  ; 
}  catch  (  Exception   e  )  { 
} 
} 

public   static   final   void   copyInputStream  (  InputStream   in  ,  OutputStream   out  )  throws   IOException  { 
byte  [  ]  buffer  =  new   byte  [  1024  ]  ; 
int   len  ; 
while  (  (  len  =  in  .  read  (  buffer  )  )  >=  0  )  { 
out  .  write  (  buffer  ,  0  ,  len  )  ; 
} 
in  .  close  (  )  ; 
out  .  close  (  )  ; 
} 

public   int   filesCount  (  String   path  )  { 
int   count  =  0  ; 
File   file  =  new   File  (  path  )  ; 
if  (  file  .  isFile  (  )  )  { 
return   1  ; 
}  else  { 
for  (  int   i  =  0  ;  i  <  file  .  listFiles  (  )  .  length  ;  i  ++  )  { 
count  +=  filesCount  (  file  .  listFiles  (  )  [  i  ]  .  getPath  (  )  )  ; 
} 
} 
return   count  ; 
} 

public   void   StopThread  (  )  { 
if  (  thread  .  isAlive  (  )  )  { 
thread  .  stop  (  )  ; 
switch  (  thread  .  _type  )  { 
case   AConf  .  COPY  : 
String   newFile  =  thread  .  _toFilePath  +  "\\"  +  thread  .  _fileName  ; 
thread  =  new   myThread  (  newFile  ,  this  ,  AConf  .  UNDO_COPY  )  ; 
thread  .  start  (  )  ; 
break  ; 
case   AConf  .  DELETE  : 
String   backup  =  "BackUp\\"  +  thread  .  _fileName  ; 
String   toFilePath  =  thread  .  _parentPath  ; 
thread  =  new   myThread  (  backup  ,  this  ,  AConf  .  UNDO_DELETE  )  ; 
thread  .  _toFilePath  =  toFilePath  ; 
thread  .  start  (  )  ; 
break  ; 
case   AConf  .  MOVE  : 
if  (  thread  .  _moveState  ==  AConf  .  COPYING  )  { 
String   newfile  =  thread  .  _toFilePath  +  "\\"  +  thread  .  _fileName  ; 
thread  =  new   myThread  (  newfile  ,  this  ,  AConf  .  UNDO_COPY  )  ; 
thread  .  start  (  )  ; 
break  ; 
} 
if  (  thread  .  _moveState  ==  AConf  .  DELETING  )  { 
String   backUp  =  thread  .  _toFilePath  +  "\\"  +  thread  .  _fileName  ; 
String   toFilepath  =  thread  .  _parentPath  ; 
thread  =  new   myThread  (  backUp  ,  this  ,  AConf  .  UNDO_MOVE  )  ; 
thread  .  _toFilePath  =  toFilepath  ; 
thread  .  start  (  )  ; 
} 
break  ; 
} 
if  (  processForm  .  isVisible  (  )  )  { 
processForm  .  setVisible  (  false  )  ; 
} 
} 
} 




public   static   void   main  (  String   args  [  ]  )  { 
java  .  awt  .  EventQueue  .  invokeLater  (  new   Runnable  (  )  { 

public   void   run  (  )  { 
new   MainForm  (  )  .  setVisible  (  true  )  ; 
} 
}  )  ; 
} 

private   javax  .  swing  .  JButton   AltF4Exit  ; 

private   javax  .  swing  .  JPanel   Body  ; 

private   javax  .  swing  .  JPanel   CommandButton  ; 

private   javax  .  swing  .  JButton   F3View  ; 

private   javax  .  swing  .  JButton   F4Move  ; 

private   javax  .  swing  .  JButton   F5Copy  ; 

private   javax  .  swing  .  JButton   F6Zip  ; 

private   javax  .  swing  .  JButton   F7Unzip  ; 

private   javax  .  swing  .  JButton   F8Delete  ; 

private   javax  .  swing  .  JPanel   Footer  ; 

private   javax  .  swing  .  JPanel   Header  ; 

private   javax  .  swing  .  JLabel   LeftPath  ; 

private   javax  .  swing  .  JScrollPane   LeftWindow  ; 

private   javax  .  swing  .  JPanel   PanelPath  ; 

private   javax  .  swing  .  JPanel   Path  ; 

private   javax  .  swing  .  JLabel   RightPath  ; 

private   javax  .  swing  .  JScrollPane   RightWindow  ; 

private   javax  .  swing  .  JComboBox   cb_Name  ; 

private   javax  .  swing  .  JComboBox   cb_leftPath  ; 

private   javax  .  swing  .  JComboBox   cb_rightPath  ; 

private   javax  .  swing  .  JList   jList1  ; 

private   javax  .  swing  .  JList   jList2  ; 

private   javax  .  swing  .  JPanel   jPanel1  ; 

private   javax  .  swing  .  JPanel   jPanel2  ; 

private   javax  .  swing  .  JPanel   jPanel3  ; 

private   javax  .  swing  .  JPanel   jPanel4  ; 

private   javax  .  swing  .  JPanel   jPanel5  ; 

private   javax  .  swing  .  JLabel   lb_Path  ; 
} 

class   myThread   extends   Thread  { 

public   String   _filePath  ; 

public   MainForm   _form  ; 

public   int   _type  =  0  ; 

public   String   _toFilePath  ; 

public   String   _fileName  ; 

public   String   _parentPath  ; 

public   int   _moveState  ; 

myThread  (  String   filePath  ,  MainForm   form  ,  int   type  )  { 
_filePath  =  filePath  ; 
_form  =  form  ; 
_type  =  type  ; 
File   file  =  new   File  (  _filePath  )  ; 
_fileName  =  file  .  getName  (  )  ; 
_parentPath  =  file  .  getParent  (  )  ; 
} 

public   void   run  (  )  { 
switch  (  _type  )  { 
case   AConf  .  DELETE  : 
_form  .  processForm  .  DisableButton  (  )  ; 
try  { 
copy  (  _filePath  ,  "BackUp"  ,  AConf  .  BACKGROUND  )  ; 
}  catch  (  Exception   e  )  { 
} 
_form  .  processForm  .  EnableButton  (  )  ; 
deleteFile  (  _filePath  ,  AConf  .  ACTIVE  )  ; 
break  ; 
case   AConf  .  COPY  : 
try  { 
copy  (  _filePath  ,  _toFilePath  ,  AConf  .  ACTIVE  )  ; 
}  catch  (  Exception   e  )  { 
} 
break  ; 
case   AConf  .  MOVE  : 
try  { 
_moveState  =  AConf  .  COPYING  ; 
boolean   success  =  copy  (  _filePath  ,  _toFilePath  ,  AConf  .  ACTIVE  )  ; 
if  (  success  ==  true  )  { 
_moveState  =  AConf  .  DELETING  ; 
deleteFile  (  _filePath  ,  AConf  .  ACTIVE  )  ; 
} 
}  catch  (  Exception   e  )  { 
} 
break  ; 
case   AConf  .  UNDO_COPY  : 
deleteFile  (  _filePath  ,  AConf  .  BACKGROUND  )  ; 
break  ; 
case   AConf  .  UNDO_DELETE  : 
try  { 
copy  (  _filePath  ,  _toFilePath  ,  AConf  .  BACKGROUND  )  ; 
}  catch  (  Exception   e  )  { 
} 
break  ; 
case   AConf  .  UNDO_MOVE  : 
try  { 
copy  (  _filePath  ,  _toFilePath  ,  AConf  .  BACKGROUND  )  ; 
}  catch  (  Exception   e  )  { 
} 
deleteFile  (  _filePath  ,  AConf  .  BACKGROUND  )  ; 
break  ; 
} 
if  (  _form  .  processForm  .  isVisible  (  )  )  { 
_form  .  processForm  .  setVisible  (  false  )  ; 
if  (  _type  ==  AConf  .  DELETE  ||  _type  ==  AConf  .  UNDO_DELETE  )  { 
deleteFile  (  "BackUp\\"  +  _fileName  ,  AConf  .  BACKGROUND  )  ; 
} 
} 
_form  .  jListReDisplay  (  )  ; 
} 

public   void   deleteFile  (  String   file  ,  int   type  )  { 
File   f  =  new   File  (  file  )  ; 
if  (  f  .  isDirectory  (  )  )  { 
for  (  int   i  =  0  ;  i  <  f  .  listFiles  (  )  .  length  ;  i  ++  )  { 
deleteFile  (  f  .  listFiles  (  )  [  i  ]  .  getPath  (  )  ,  type  )  ; 
i  --  ; 
} 
} 
boolean   success  =  f  .  delete  (  )  ; 
if  (  type  ==  AConf  .  ACTIVE  )  { 
_form  .  processForm  .  _nFiles  ++  ; 
_form  .  processForm  .  repaint  (  )  ; 
} 
} 

public   boolean   copy  (  String   fromFileName  ,  String   toFileName  ,  int   type  )  throws   IOException  { 
File   fromFile  =  new   File  (  fromFileName  )  ; 
File   toFile  =  new   File  (  toFileName  )  ; 
if  (  !  fromFile  .  exists  (  )  )  { 
return   false  ; 
} 
if  (  !  fromFile  .  isFile  (  )  )  { 
File   newFolder  =  new   File  (  toFile  ,  fromFile  .  getName  (  )  )  ; 
int   temp  =  0  ; 
if  (  newFolder  .  getPath  (  )  .  equals  (  fromFile  .  getPath  (  )  )  )  { 
while  (  newFolder  .  exists  (  )  )  { 
newFolder  =  new   File  (  toFile  ,  "Copy "  +  temp  +  " of "  +  fromFile  .  getName  (  )  )  ; 
temp  ++  ; 
} 
}  else  { 
if  (  newFolder  .  exists  (  )  )  { 
if  (  type  ==  AConf  .  ACTIVE  )  { 
int   response  ; 
response  =  JOptionPane  .  showConfirmDialog  (  null  ,  "Overwrite existing folder "  +  newFolder  .  getName  (  )  )  ; 
if  (  response  !=  JOptionPane  .  YES_OPTION  )  { 
return   false  ; 
}  else  { 
deleteFile  (  newFolder  .  getPath  (  )  ,  AConf  .  BACKGROUND  )  ; 
} 
}  else  { 
deleteFile  (  newFolder  .  getPath  (  )  ,  AConf  .  BACKGROUND  )  ; 
} 
} 
} 
newFolder  .  mkdirs  (  )  ; 
for  (  int   i  =  0  ;  i  <  fromFile  .  listFiles  (  )  .  length  ;  i  ++  )  { 
copy  (  fromFile  .  listFiles  (  )  [  i  ]  .  getPath  (  )  ,  newFolder  .  getPath  (  )  ,  type  )  ; 
} 
return   true  ; 
} 
if  (  !  fromFile  .  canRead  (  )  )  { 
return   false  ; 
} 
if  (  toFile  .  isDirectory  (  )  )  { 
toFile  =  new   File  (  toFile  ,  fromFile  .  getName  (  )  )  ; 
} 
if  (  toFile  .  exists  (  )  )  { 
if  (  !  toFile  .  getPath  (  )  .  equals  (  fromFile  .  getPath  (  )  )  )  { 
if  (  !  toFile  .  canWrite  (  )  )  { 
return   false  ; 
} 
if  (  type  ==  AConf  .  ACTIVE  )  { 
int   response  ; 
response  =  JOptionPane  .  showConfirmDialog  (  null  ,  "Overwrite existing file "  +  toFile  .  getName  (  )  )  ; 
if  (  response  !=  JOptionPane  .  YES_OPTION  )  { 
return   false  ; 
} 
} 
}  else  { 
int   n  =  0  ; 
while  (  toFile  .  exists  (  )  )  { 
toFile  =  new   File  (  toFile  .  getParent  (  )  ,  "Copy "  +  n  +  " of "  +  fromFile  .  getName  (  )  )  ; 
n  ++  ; 
} 
} 
}  else  { 
String   parent  =  toFile  .  getParent  (  )  ; 
if  (  parent  ==  null  )  { 
parent  =  System  .  getProperty  (  "user.dir"  )  ; 
} 
File   dir  =  new   File  (  parent  )  ; 
if  (  !  dir  .  exists  (  )  )  { 
return   false  ; 
} 
if  (  dir  .  isFile  (  )  )  { 
return   false  ; 
} 
if  (  !  dir  .  canWrite  (  )  )  { 
return   false  ; 
} 
} 
FileInputStream   from  =  null  ; 
FileOutputStream   to  =  null  ; 
try  { 
from  =  new   FileInputStream  (  fromFile  )  ; 
to  =  new   FileOutputStream  (  toFile  )  ; 
byte  [  ]  buffer  =  new   byte  [  4096  ]  ; 
int   bytesRead  ; 
while  (  (  bytesRead  =  from  .  read  (  buffer  )  )  !=  -  1  )  { 
to  .  write  (  buffer  ,  0  ,  bytesRead  )  ; 
} 
if  (  type  ==  AConf  .  ACTIVE  )  { 
_form  .  processForm  .  _nFiles  ++  ; 
} 
_form  .  processForm  .  repaint  (  )  ; 
return   true  ; 
}  finally  { 
if  (  from  !=  null  )  { 
try  { 
from  .  close  (  )  ; 
}  catch  (  IOException   e  )  { 
; 
} 
} 
if  (  to  !=  null  )  { 
try  { 
to  .  close  (  )  ; 
}  catch  (  IOException   e  )  { 
; 
} 
} 
} 
} 
} 

