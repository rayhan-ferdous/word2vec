package   reports  .  circulation  ; 

import   java  .  awt  .  event  .  ActionEvent  ; 
import   java  .  awt  .  event  .  ActionListener  ; 
import   java  .  io  .  File  ; 
import   java  .  io  .  FileWriter  ; 
import   java  .  sql  .  Connection  ; 
import   java  .  sql  .  ResultSet  ; 
import   java  .  sql  .  SQLException  ; 
import   java  .  sql  .  Statement  ; 
import   java  .  util  .  HashMap  ; 
import   java  .  util  .  Iterator  ; 
import   java  .  util  .  Set  ; 
import   java  .  util  .  StringTokenizer  ; 
import   java  .  util  .  Vector  ; 
import   java  .  util  .  logging  .  Level  ; 
import   java  .  util  .  logging  .  Logger  ; 
import   javax  .  swing  .  JFileChooser  ; 
import   javax  .  swing  .  JOptionPane  ; 
import   javax  .  swing  .  Timer  ; 
import   javax  .  swing  .  filechooser  .  FileFilter  ; 





public   class   SemesterDetails   extends   javax  .  swing  .  JInternalFrame  { 

private   HashMap   jCombo1HashMap  =  new   HashMap  (  )  ; 

private   HashMap   jCombo2HashMap  =  new   HashMap  (  )  ; 

private   HashMap   jCombo3Sem  =  new   HashMap  (  )  ; 

private   HashMap   jCombo4Dept  =  new   HashMap  (  )  ; 

private   Integer   catId  =  0  ; 

private   Integer   ownLibId  =  0  ; 

private   String   barcode  =  ""  ; 

File   createFile  =  null  ; 

private   javax  .  swing  .  table  .  DefaultTableModel   defTbModel  =  null  ; 

private   Timer   timer  =  null  ; 


public   SemesterDetails  (  )  { 
initComponents  (  )  ; 
this  .  setSize  (  800  ,  550  )  ; 
fillData  (  )  ; 
Set   keys  =  jCombo1HashMap  .  keySet  (  )  ; 
Iterator   it  =  keys  .  iterator  (  )  ; 
Vector   addL  =  new   Vector  (  )  ; 
while  (  it  .  hasNext  (  )  )  { 
String   key  =  it  .  next  (  )  .  toString  (  )  ; 
addL  .  add  (  key  )  ; 
} 
java  .  util  .  Collections  .  sort  (  addL  )  ; 
jCsemester  .  setModel  (  new   javax  .  swing  .  DefaultComboBoxModel  (  addL  )  )  ; 
keys  =  jCombo2HashMap  .  keySet  (  )  ; 
it  =  keys  .  iterator  (  )  ; 
addL  =  new   Vector  (  )  ; 
while  (  it  .  hasNext  (  )  )  { 
addL  .  add  (  it  .  next  (  )  )  ; 
} 
java  .  util  .  Collections  .  sort  (  addL  )  ; 
jCdepartment  .  setModel  (  new   javax  .  swing  .  DefaultComboBoxModel  (  addL  )  )  ; 
} 

public   void   fillData  (  )  { 
java  .  sql  .  Connection   con  =  null  ; 
java  .  sql  .  PreparedStatement   ps  =  null  ; 
java  .  sql  .  ResultSet   rs  =  null  ; 
java  .  sql  .  Statement   st  =  null  ; 
try  { 
String   LibId  =  reports  .  utility  .  StaticValues  .  getInstance  (  )  .  getLoginLibraryId  (  )  ; 
if  (  !  jTbarcode  .  getText  (  )  .  equals  (  ""  )  )  { 
} 
con  =  reports  .  utility  .  database  .  ConnectionPoolFactory  .  getInstance  (  )  .  getConnectionPool  (  )  .  getConnection  (  )  ; 
ps  =  con  .  prepareStatement  (  "select id,name from semester_config where library_id=?"  )  ; 
ps  .  setInt  (  1  ,  Integer  .  parseInt  (  LibId  )  )  ; 
rs  =  ps  .  executeQuery  (  )  ; 
while  (  rs  .  next  (  )  )  { 
jCombo1HashMap  .  put  (  rs  .  getString  (  "name"  )  ,  rs  .  getInt  (  "id"  )  )  ; 
} 
ps  .  close  (  )  ; 
rs  .  close  (  )  ; 
st  =  con  .  createStatement  (  )  ; 
rs  =  st  .  executeQuery  (  "select dept_id,dept_name from dept where library_id="  +  Integer  .  parseInt  (  LibId  )  )  ; 
while  (  rs  .  next  (  )  )  { 
jCombo2HashMap  .  put  (  rs  .  getString  (  "dept_name"  )  ,  rs  .  getString  (  "dept_id"  )  )  ; 
} 
st  .  close  (  )  ; 
rs  .  close  (  )  ; 
this  .  defTbModel  =  new   javax  .  swing  .  table  .  DefaultTableModel  (  new   Object  [  ]  {  "DeptId"  ,  "Department name"  ,  "semId"  ,  "Semester id"  }  ,  0  )  { 

@  Override 
public   boolean   isCellEditable  (  int   r  ,  int   c  )  { 
return   false  ; 
} 

@  Override 
public   Class   getColumnClass  (  int   c  )  { 
return   getValueAt  (  0  ,  c  )  .  getClass  (  )  ; 
} 
}  ; 
jTable1  .  setModel  (  defTbModel  )  ; 
jTable1  .  getSelectionModel  (  )  .  setSelectionMode  (  javax  .  swing  .  ListSelectionModel  .  SINGLE_SELECTION  )  ; 
jTable1  .  getColumnModel  (  )  .  getColumn  (  0  )  .  setPreferredWidth  (  0  )  ; 
jTable1  .  getColumnModel  (  )  .  getColumn  (  0  )  .  setMaxWidth  (  0  )  ; 
jTable1  .  getColumnModel  (  )  .  getColumn  (  0  )  .  setMinWidth  (  0  )  ; 
jTable1  .  getColumnModel  (  )  .  getColumn  (  0  )  .  setPreferredWidth  (  0  )  ; 
jTable1  .  getColumnModel  (  )  .  getColumn  (  2  )  .  setPreferredWidth  (  0  )  ; 
jTable1  .  getColumnModel  (  )  .  getColumn  (  2  )  .  setMaxWidth  (  0  )  ; 
jTable1  .  getColumnModel  (  )  .  getColumn  (  2  )  .  setMinWidth  (  0  )  ; 
jTable1  .  getColumnModel  (  )  .  getColumn  (  2  )  .  setPreferredWidth  (  0  )  ; 
}  catch  (  Exception   e  )  { 
e  .  printStackTrace  (  )  ; 
}  finally  { 
if  (  con  !=  null  )  { 
try  { 
con  .  close  (  )  ; 
}  catch  (  SQLException   ex  )  { 
Logger  .  getLogger  (  SemesterDetails  .  class  .  getName  (  )  )  .  log  (  Level  .  SEVERE  ,  null  ,  ex  )  ; 
} 
} 
} 
} 






@  SuppressWarnings  (  "unchecked"  ) 
private   void   initComponents  (  )  { 
java  .  awt  .  GridBagConstraints   gridBagConstraints  ; 
jDialog1  =  new   javax  .  swing  .  JDialog  (  )  ; 
jPanel7  =  new   javax  .  swing  .  JPanel  (  )  ; 
jLabel13  =  new   javax  .  swing  .  JLabel  (  )  ; 
jComboBox1  =  new   javax  .  swing  .  JComboBox  (  )  ; 
jLabel15  =  new   javax  .  swing  .  JLabel  (  )  ; 
jComboBox2  =  new   javax  .  swing  .  JComboBox  (  )  ; 
jLabel17  =  new   javax  .  swing  .  JLabel  (  )  ; 
jTextField1  =  new   javax  .  swing  .  JTextField  (  )  ; 
jButton1  =  new   javax  .  swing  .  JButton  (  )  ; 
jProgressBar1  =  new   javax  .  swing  .  JProgressBar  (  )  ; 
jPanel8  =  new   javax  .  swing  .  JPanel  (  )  ; 
jBGenerate  =  new   javax  .  swing  .  JButton  (  )  ; 
jBCcancel  =  new   javax  .  swing  .  JButton  (  )  ; 
jPanel2  =  new   javax  .  swing  .  JPanel  (  )  ; 
jLbarcode  =  new   javax  .  swing  .  JLabel  (  )  ; 
jTbarcode  =  new   javax  .  swing  .  JTextField  (  )  ; 
jBGo  =  new   javax  .  swing  .  JButton  (  )  ; 
jLabel1  =  new   javax  .  swing  .  JLabel  (  )  ; 
jCsemester  =  new   javax  .  swing  .  JComboBox  (  )  ; 
jLabel2  =  new   javax  .  swing  .  JLabel  (  )  ; 
jCdepartment  =  new   javax  .  swing  .  JComboBox  (  )  ; 
jLabel3  =  new   javax  .  swing  .  JLabel  (  )  ; 
jTshelfLife  =  new   javax  .  swing  .  JTextField  (  )  ; 
jLabel4  =  new   javax  .  swing  .  JLabel  (  )  ; 
jTcirculationDays  =  new   javax  .  swing  .  JTextField  (  )  ; 
jLabel5  =  new   javax  .  swing  .  JLabel  (  )  ; 
jTLongtermLending  =  new   javax  .  swing  .  JTextField  (  )  ; 
jLabel6  =  new   javax  .  swing  .  JLabel  (  )  ; 
jTmrp  =  new   javax  .  swing  .  JTextField  (  )  ; 
jLabel7  =  new   javax  .  swing  .  JLabel  (  )  ; 
jTdiscount  =  new   javax  .  swing  .  JTextField  (  )  ; 
jLabel8  =  new   javax  .  swing  .  JLabel  (  )  ; 
jTinvoiceCost  =  new   javax  .  swing  .  JTextField  (  )  ; 
jBadd  =  new   javax  .  swing  .  JButton  (  )  ; 
jPanel3  =  new   javax  .  swing  .  JPanel  (  )  ; 
jPanel1  =  new   javax  .  swing  .  JPanel  (  )  ; 
jLabel9  =  new   javax  .  swing  .  JLabel  (  )  ; 
jLtitle  =  new   javax  .  swing  .  JLabel  (  )  ; 
jLabel10  =  new   javax  .  swing  .  JLabel  (  )  ; 
jLauthor  =  new   javax  .  swing  .  JLabel  (  )  ; 
jLabel12  =  new   javax  .  swing  .  JLabel  (  )  ; 
jLedition  =  new   javax  .  swing  .  JLabel  (  )  ; 
jLabel14  =  new   javax  .  swing  .  JLabel  (  )  ; 
jLpublisher  =  new   javax  .  swing  .  JLabel  (  )  ; 
jLabel16  =  new   javax  .  swing  .  JLabel  (  )  ; 
jLisbn  =  new   javax  .  swing  .  JLabel  (  )  ; 
jLabel11  =  new   javax  .  swing  .  JLabel  (  )  ; 
jLstatus  =  new   javax  .  swing  .  JLabel  (  )  ; 
jPanel4  =  new   javax  .  swing  .  JPanel  (  )  ; 
jScrollPane1  =  new   javax  .  swing  .  JScrollPane  (  )  ; 
jTable1  =  new   javax  .  swing  .  JTable  (  )  ; 
jPanel5  =  new   javax  .  swing  .  JPanel  (  )  ; 
jBSideDelete  =  new   javax  .  swing  .  JButton  (  )  ; 
jPanel6  =  new   javax  .  swing  .  JPanel  (  )  ; 
jBDeleteMain  =  new   javax  .  swing  .  JButton  (  )  ; 
jBDeleteAll  =  new   javax  .  swing  .  JButton  (  )  ; 
jBViewCompleteList  =  new   javax  .  swing  .  JButton  (  )  ; 
jDialog1  .  setModal  (  true  )  ; 
jDialog1  .  getContentPane  (  )  .  setLayout  (  new   java  .  awt  .  GridBagLayout  (  )  )  ; 
jPanel7  .  setBorder  (  javax  .  swing  .  BorderFactory  .  createEtchedBorder  (  )  )  ; 
jPanel7  .  setLayout  (  new   java  .  awt  .  GridBagLayout  (  )  )  ; 
jLabel13  .  setText  (  "Semester :"  )  ; 
gridBagConstraints  =  new   java  .  awt  .  GridBagConstraints  (  )  ; 
gridBagConstraints  .  anchor  =  java  .  awt  .  GridBagConstraints  .  EAST  ; 
jPanel7  .  add  (  jLabel13  ,  gridBagConstraints  )  ; 
gridBagConstraints  =  new   java  .  awt  .  GridBagConstraints  (  )  ; 
gridBagConstraints  .  fill  =  java  .  awt  .  GridBagConstraints  .  HORIZONTAL  ; 
gridBagConstraints  .  anchor  =  java  .  awt  .  GridBagConstraints  .  WEST  ; 
jPanel7  .  add  (  jComboBox1  ,  gridBagConstraints  )  ; 
jLabel15  .  setText  (  "Department :"  )  ; 
gridBagConstraints  =  new   java  .  awt  .  GridBagConstraints  (  )  ; 
gridBagConstraints  .  gridy  =  1  ; 
gridBagConstraints  .  anchor  =  java  .  awt  .  GridBagConstraints  .  EAST  ; 
jPanel7  .  add  (  jLabel15  ,  gridBagConstraints  )  ; 
gridBagConstraints  =  new   java  .  awt  .  GridBagConstraints  (  )  ; 
gridBagConstraints  .  gridx  =  1  ; 
gridBagConstraints  .  gridy  =  1  ; 
gridBagConstraints  .  fill  =  java  .  awt  .  GridBagConstraints  .  HORIZONTAL  ; 
gridBagConstraints  .  anchor  =  java  .  awt  .  GridBagConstraints  .  WEST  ; 
jPanel7  .  add  (  jComboBox2  ,  gridBagConstraints  )  ; 
jLabel17  .  setText  (  "Location :"  )  ; 
gridBagConstraints  =  new   java  .  awt  .  GridBagConstraints  (  )  ; 
gridBagConstraints  .  gridy  =  2  ; 
gridBagConstraints  .  anchor  =  java  .  awt  .  GridBagConstraints  .  EAST  ; 
jPanel7  .  add  (  jLabel17  ,  gridBagConstraints  )  ; 
jTextField1  .  setColumns  (  10  )  ; 
jTextField1  .  setEditable  (  false  )  ; 
gridBagConstraints  =  new   java  .  awt  .  GridBagConstraints  (  )  ; 
gridBagConstraints  .  gridx  =  1  ; 
gridBagConstraints  .  gridy  =  2  ; 
gridBagConstraints  .  fill  =  java  .  awt  .  GridBagConstraints  .  HORIZONTAL  ; 
gridBagConstraints  .  anchor  =  java  .  awt  .  GridBagConstraints  .  WEST  ; 
jPanel7  .  add  (  jTextField1  ,  gridBagConstraints  )  ; 
jButton1  .  setText  (  "..."  )  ; 
jButton1  .  addActionListener  (  new   java  .  awt  .  event  .  ActionListener  (  )  { 

public   void   actionPerformed  (  java  .  awt  .  event  .  ActionEvent   evt  )  { 
jButton1ActionPerformed  (  evt  )  ; 
} 
}  )  ; 
gridBagConstraints  =  new   java  .  awt  .  GridBagConstraints  (  )  ; 
gridBagConstraints  .  gridx  =  2  ; 
gridBagConstraints  .  gridy  =  2  ; 
jPanel7  .  add  (  jButton1  ,  gridBagConstraints  )  ; 
gridBagConstraints  =  new   java  .  awt  .  GridBagConstraints  (  )  ; 
gridBagConstraints  .  gridx  =  0  ; 
gridBagConstraints  .  gridy  =  0  ; 
gridBagConstraints  .  ipadx  =  120  ; 
gridBagConstraints  .  ipady  =  60  ; 
jDialog1  .  getContentPane  (  )  .  add  (  jPanel7  ,  gridBagConstraints  )  ; 
gridBagConstraints  =  new   java  .  awt  .  GridBagConstraints  (  )  ; 
gridBagConstraints  .  gridy  =  1  ; 
gridBagConstraints  .  fill  =  java  .  awt  .  GridBagConstraints  .  HORIZONTAL  ; 
jDialog1  .  getContentPane  (  )  .  add  (  jProgressBar1  ,  gridBagConstraints  )  ; 
jBGenerate  .  setText  (  "Generate"  )  ; 
jBGenerate  .  addActionListener  (  new   java  .  awt  .  event  .  ActionListener  (  )  { 

public   void   actionPerformed  (  java  .  awt  .  event  .  ActionEvent   evt  )  { 
jBGenerateActionPerformed  (  evt  )  ; 
} 
}  )  ; 
jPanel8  .  add  (  jBGenerate  )  ; 
jBCcancel  .  setText  (  "Cancel"  )  ; 
jBCcancel  .  addActionListener  (  new   java  .  awt  .  event  .  ActionListener  (  )  { 

public   void   actionPerformed  (  java  .  awt  .  event  .  ActionEvent   evt  )  { 
jBCcancelActionPerformed  (  evt  )  ; 
} 
}  )  ; 
jPanel8  .  add  (  jBCcancel  )  ; 
gridBagConstraints  =  new   java  .  awt  .  GridBagConstraints  (  )  ; 
gridBagConstraints  .  gridy  =  2  ; 
gridBagConstraints  .  fill  =  java  .  awt  .  GridBagConstraints  .  HORIZONTAL  ; 
jDialog1  .  getContentPane  (  )  .  add  (  jPanel8  ,  gridBagConstraints  )  ; 
setClosable  (  true  )  ; 
setIconifiable  (  true  )  ; 
setMaximizable  (  true  )  ; 
setResizable  (  true  )  ; 
setTitle  (  "Details of semesters"  )  ; 
getContentPane  (  )  .  setLayout  (  new   java  .  awt  .  GridBagLayout  (  )  )  ; 
jPanel2  .  setBorder  (  javax  .  swing  .  BorderFactory  .  createEtchedBorder  (  )  )  ; 
jPanel2  .  setLayout  (  new   java  .  awt  .  GridBagLayout  (  )  )  ; 
jLbarcode  .  setText  (  "Barcode :"  )  ; 
gridBagConstraints  =  new   java  .  awt  .  GridBagConstraints  (  )  ; 
gridBagConstraints  .  gridx  =  1  ; 
gridBagConstraints  .  anchor  =  java  .  awt  .  GridBagConstraints  .  EAST  ; 
jPanel2  .  add  (  jLbarcode  ,  gridBagConstraints  )  ; 
jTbarcode  .  setColumns  (  8  )  ; 
gridBagConstraints  =  new   java  .  awt  .  GridBagConstraints  (  )  ; 
gridBagConstraints  .  gridx  =  2  ; 
gridBagConstraints  .  fill  =  java  .  awt  .  GridBagConstraints  .  HORIZONTAL  ; 
jPanel2  .  add  (  jTbarcode  ,  gridBagConstraints  )  ; 
jBGo  .  setText  (  "Go"  )  ; 
jBGo  .  addActionListener  (  new   java  .  awt  .  event  .  ActionListener  (  )  { 

public   void   actionPerformed  (  java  .  awt  .  event  .  ActionEvent   evt  )  { 
jBGoActionPerformed  (  evt  )  ; 
} 
}  )  ; 
gridBagConstraints  =  new   java  .  awt  .  GridBagConstraints  (  )  ; 
gridBagConstraints  .  gridx  =  3  ; 
gridBagConstraints  .  anchor  =  java  .  awt  .  GridBagConstraints  .  WEST  ; 
jPanel2  .  add  (  jBGo  ,  gridBagConstraints  )  ; 
jLabel1  .  setText  (  "Semester :"  )  ; 
gridBagConstraints  =  new   java  .  awt  .  GridBagConstraints  (  )  ; 
gridBagConstraints  .  gridy  =  1  ; 
gridBagConstraints  .  anchor  =  java  .  awt  .  GridBagConstraints  .  EAST  ; 
jPanel2  .  add  (  jLabel1  ,  gridBagConstraints  )  ; 
gridBagConstraints  =  new   java  .  awt  .  GridBagConstraints  (  )  ; 
gridBagConstraints  .  gridx  =  1  ; 
gridBagConstraints  .  gridy  =  1  ; 
gridBagConstraints  .  fill  =  java  .  awt  .  GridBagConstraints  .  HORIZONTAL  ; 
jPanel2  .  add  (  jCsemester  ,  gridBagConstraints  )  ; 
jLabel2  .  setText  (  "Department :"  )  ; 
gridBagConstraints  =  new   java  .  awt  .  GridBagConstraints  (  )  ; 
gridBagConstraints  .  gridx  =  2  ; 
gridBagConstraints  .  gridy  =  1  ; 
gridBagConstraints  .  anchor  =  java  .  awt  .  GridBagConstraints  .  EAST  ; 
jPanel2  .  add  (  jLabel2  ,  gridBagConstraints  )  ; 
gridBagConstraints  =  new   java  .  awt  .  GridBagConstraints  (  )  ; 
gridBagConstraints  .  gridx  =  3  ; 
gridBagConstraints  .  gridy  =  1  ; 
gridBagConstraints  .  fill  =  java  .  awt  .  GridBagConstraints  .  HORIZONTAL  ; 
gridBagConstraints  .  anchor  =  java  .  awt  .  GridBagConstraints  .  WEST  ; 
jPanel2  .  add  (  jCdepartment  ,  gridBagConstraints  )  ; 
jLabel3  .  setText  (  "Shelf life :"  )  ; 
gridBagConstraints  =  new   java  .  awt  .  GridBagConstraints  (  )  ; 
gridBagConstraints  .  gridy  =  2  ; 
gridBagConstraints  .  anchor  =  java  .  awt  .  GridBagConstraints  .  EAST  ; 
jPanel2  .  add  (  jLabel3  ,  gridBagConstraints  )  ; 
jTshelfLife  .  setColumns  (  10  )  ; 
gridBagConstraints  =  new   java  .  awt  .  GridBagConstraints  (  )  ; 
gridBagConstraints  .  gridx  =  1  ; 
gridBagConstraints  .  gridy  =  2  ; 
gridBagConstraints  .  fill  =  java  .  awt  .  GridBagConstraints  .  HORIZONTAL  ; 
gridBagConstraints  .  anchor  =  java  .  awt  .  GridBagConstraints  .  WEST  ; 
jPanel2  .  add  (  jTshelfLife  ,  gridBagConstraints  )  ; 
jLabel4  .  setText  (  "Circulation days :"  )  ; 
gridBagConstraints  =  new   java  .  awt  .  GridBagConstraints  (  )  ; 
gridBagConstraints  .  gridx  =  2  ; 
gridBagConstraints  .  gridy  =  2  ; 
gridBagConstraints  .  anchor  =  java  .  awt  .  GridBagConstraints  .  EAST  ; 
jPanel2  .  add  (  jLabel4  ,  gridBagConstraints  )  ; 
jTcirculationDays  .  setColumns  (  10  )  ; 
gridBagConstraints  =  new   java  .  awt  .  GridBagConstraints  (  )  ; 
gridBagConstraints  .  gridx  =  3  ; 
gridBagConstraints  .  gridy  =  2  ; 
gridBagConstraints  .  fill  =  java  .  awt  .  GridBagConstraints  .  HORIZONTAL  ; 
gridBagConstraints  .  anchor  =  java  .  awt  .  GridBagConstraints  .  WEST  ; 
jPanel2  .  add  (  jTcirculationDays  ,  gridBagConstraints  )  ; 
jLabel5  .  setText  (  "Long term lending :"  )  ; 
gridBagConstraints  =  new   java  .  awt  .  GridBagConstraints  (  )  ; 
gridBagConstraints  .  gridy  =  4  ; 
gridBagConstraints  .  anchor  =  java  .  awt  .  GridBagConstraints  .  EAST  ; 
jPanel2  .  add  (  jLabel5  ,  gridBagConstraints  )  ; 
jTLongtermLending  .  setColumns  (  10  )  ; 
gridBagConstraints  =  new   java  .  awt  .  GridBagConstraints  (  )  ; 
gridBagConstraints  .  gridx  =  1  ; 
gridBagConstraints  .  gridy  =  4  ; 
gridBagConstraints  .  fill  =  java  .  awt  .  GridBagConstraints  .  HORIZONTAL  ; 
gridBagConstraints  .  anchor  =  java  .  awt  .  GridBagConstraints  .  WEST  ; 
jPanel2  .  add  (  jTLongtermLending  ,  gridBagConstraints  )  ; 
jLabel6  .  setText  (  "MRP :"  )  ; 
gridBagConstraints  =  new   java  .  awt  .  GridBagConstraints  (  )  ; 
gridBagConstraints  .  gridy  =  3  ; 
gridBagConstraints  .  anchor  =  java  .  awt  .  GridBagConstraints  .  EAST  ; 
jPanel2  .  add  (  jLabel6  ,  gridBagConstraints  )  ; 
jTmrp  .  setColumns  (  5  )  ; 
jTmrp  .  addFocusListener  (  new   java  .  awt  .  event  .  FocusAdapter  (  )  { 

public   void   focusLost  (  java  .  awt  .  event  .  FocusEvent   evt  )  { 
jTmrpFocusLost  (  evt  )  ; 
} 
}  )  ; 
gridBagConstraints  =  new   java  .  awt  .  GridBagConstraints  (  )  ; 
gridBagConstraints  .  gridx  =  1  ; 
gridBagConstraints  .  gridy  =  3  ; 
gridBagConstraints  .  anchor  =  java  .  awt  .  GridBagConstraints  .  WEST  ; 
jPanel2  .  add  (  jTmrp  ,  gridBagConstraints  )  ; 
jLabel7  .  setText  (  "Discount :"  )  ; 
gridBagConstraints  =  new   java  .  awt  .  GridBagConstraints  (  )  ; 
gridBagConstraints  .  gridx  =  2  ; 
gridBagConstraints  .  gridy  =  3  ; 
gridBagConstraints  .  anchor  =  java  .  awt  .  GridBagConstraints  .  EAST  ; 
jPanel2  .  add  (  jLabel7  ,  gridBagConstraints  )  ; 
jTdiscount  .  setColumns  (  4  )  ; 
jTdiscount  .  addFocusListener  (  new   java  .  awt  .  event  .  FocusAdapter  (  )  { 

public   void   focusLost  (  java  .  awt  .  event  .  FocusEvent   evt  )  { 
jTdiscountFocusLost  (  evt  )  ; 
} 
}  )  ; 
gridBagConstraints  =  new   java  .  awt  .  GridBagConstraints  (  )  ; 
gridBagConstraints  .  gridx  =  3  ; 
gridBagConstraints  .  gridy  =  3  ; 
gridBagConstraints  .  anchor  =  java  .  awt  .  GridBagConstraints  .  WEST  ; 
jPanel2  .  add  (  jTdiscount  ,  gridBagConstraints  )  ; 
jLabel8  .  setText  (  "Invoice cost :"  )  ; 
gridBagConstraints  =  new   java  .  awt  .  GridBagConstraints  (  )  ; 
gridBagConstraints  .  gridx  =  2  ; 
gridBagConstraints  .  gridy  =  4  ; 
gridBagConstraints  .  anchor  =  java  .  awt  .  GridBagConstraints  .  EAST  ; 
jPanel2  .  add  (  jLabel8  ,  gridBagConstraints  )  ; 
jTinvoiceCost  .  setColumns  (  6  )  ; 
jTinvoiceCost  .  setEditable  (  false  )  ; 
gridBagConstraints  =  new   java  .  awt  .  GridBagConstraints  (  )  ; 
gridBagConstraints  .  gridx  =  3  ; 
gridBagConstraints  .  gridy  =  4  ; 
gridBagConstraints  .  anchor  =  java  .  awt  .  GridBagConstraints  .  WEST  ; 
jPanel2  .  add  (  jTinvoiceCost  ,  gridBagConstraints  )  ; 
jBadd  .  setText  (  "Add"  )  ; 
jBadd  .  addActionListener  (  new   java  .  awt  .  event  .  ActionListener  (  )  { 

public   void   actionPerformed  (  java  .  awt  .  event  .  ActionEvent   evt  )  { 
jBaddActionPerformed  (  evt  )  ; 
} 
}  )  ; 
gridBagConstraints  =  new   java  .  awt  .  GridBagConstraints  (  )  ; 
gridBagConstraints  .  gridx  =  2  ; 
gridBagConstraints  .  gridy  =  5  ; 
jPanel2  .  add  (  jBadd  ,  gridBagConstraints  )  ; 
gridBagConstraints  =  new   java  .  awt  .  GridBagConstraints  (  )  ; 
gridBagConstraints  .  fill  =  java  .  awt  .  GridBagConstraints  .  HORIZONTAL  ; 
getContentPane  (  )  .  add  (  jPanel2  ,  gridBagConstraints  )  ; 
jPanel3  .  setBorder  (  javax  .  swing  .  BorderFactory  .  createEtchedBorder  (  )  )  ; 
jPanel3  .  setLayout  (  new   java  .  awt  .  BorderLayout  (  )  )  ; 
jPanel1  .  setLayout  (  new   java  .  awt  .  GridBagLayout  (  )  )  ; 
jLabel9  .  setText  (  "Title :"  )  ; 
gridBagConstraints  =  new   java  .  awt  .  GridBagConstraints  (  )  ; 
gridBagConstraints  .  anchor  =  java  .  awt  .  GridBagConstraints  .  EAST  ; 
jPanel1  .  add  (  jLabel9  ,  gridBagConstraints  )  ; 
gridBagConstraints  =  new   java  .  awt  .  GridBagConstraints  (  )  ; 
gridBagConstraints  .  anchor  =  java  .  awt  .  GridBagConstraints  .  WEST  ; 
jPanel1  .  add  (  jLtitle  ,  gridBagConstraints  )  ; 
jLabel10  .  setText  (  "Author :"  )  ; 
gridBagConstraints  =  new   java  .  awt  .  GridBagConstraints  (  )  ; 
gridBagConstraints  .  gridy  =  1  ; 
gridBagConstraints  .  anchor  =  java  .  awt  .  GridBagConstraints  .  EAST  ; 
jPanel1  .  add  (  jLabel10  ,  gridBagConstraints  )  ; 
gridBagConstraints  =  new   java  .  awt  .  GridBagConstraints  (  )  ; 
gridBagConstraints  .  gridx  =  1  ; 
gridBagConstraints  .  gridy  =  1  ; 
gridBagConstraints  .  anchor  =  java  .  awt  .  GridBagConstraints  .  WEST  ; 
jPanel1  .  add  (  jLauthor  ,  gridBagConstraints  )  ; 
jLabel12  .  setText  (  "Edition :"  )  ; 
gridBagConstraints  =  new   java  .  awt  .  GridBagConstraints  (  )  ; 
gridBagConstraints  .  gridy  =  2  ; 
gridBagConstraints  .  anchor  =  java  .  awt  .  GridBagConstraints  .  EAST  ; 
jPanel1  .  add  (  jLabel12  ,  gridBagConstraints  )  ; 
gridBagConstraints  =  new   java  .  awt  .  GridBagConstraints  (  )  ; 
gridBagConstraints  .  gridx  =  1  ; 
gridBagConstraints  .  gridy  =  2  ; 
gridBagConstraints  .  anchor  =  java  .  awt  .  GridBagConstraints  .  WEST  ; 
jPanel1  .  add  (  jLedition  ,  gridBagConstraints  )  ; 
jLabel14  .  setText  (  "Publisher :"  )  ; 
gridBagConstraints  =  new   java  .  awt  .  GridBagConstraints  (  )  ; 
gridBagConstraints  .  gridx  =  0  ; 
gridBagConstraints  .  gridy  =  3  ; 
gridBagConstraints  .  anchor  =  java  .  awt  .  GridBagConstraints  .  EAST  ; 
jPanel1  .  add  (  jLabel14  ,  gridBagConstraints  )  ; 
gridBagConstraints  =  new   java  .  awt  .  GridBagConstraints  (  )  ; 
gridBagConstraints  .  gridx  =  1  ; 
gridBagConstraints  .  gridy  =  3  ; 
gridBagConstraints  .  anchor  =  java  .  awt  .  GridBagConstraints  .  WEST  ; 
jPanel1  .  add  (  jLpublisher  ,  gridBagConstraints  )  ; 
jLabel16  .  setText  (  "Isbn :"  )  ; 
gridBagConstraints  =  new   java  .  awt  .  GridBagConstraints  (  )  ; 
gridBagConstraints  .  gridy  =  4  ; 
gridBagConstraints  .  anchor  =  java  .  awt  .  GridBagConstraints  .  EAST  ; 
jPanel1  .  add  (  jLabel16  ,  gridBagConstraints  )  ; 
gridBagConstraints  =  new   java  .  awt  .  GridBagConstraints  (  )  ; 
gridBagConstraints  .  gridx  =  1  ; 
gridBagConstraints  .  gridy  =  4  ; 
gridBagConstraints  .  anchor  =  java  .  awt  .  GridBagConstraints  .  WEST  ; 
jPanel1  .  add  (  jLisbn  ,  gridBagConstraints  )  ; 
jLabel11  .  setText  (  "Status :"  )  ; 
gridBagConstraints  =  new   java  .  awt  .  GridBagConstraints  (  )  ; 
gridBagConstraints  .  gridy  =  5  ; 
gridBagConstraints  .  anchor  =  java  .  awt  .  GridBagConstraints  .  EAST  ; 
jPanel1  .  add  (  jLabel11  ,  gridBagConstraints  )  ; 
gridBagConstraints  =  new   java  .  awt  .  GridBagConstraints  (  )  ; 
gridBagConstraints  .  gridx  =  1  ; 
gridBagConstraints  .  gridy  =  5  ; 
gridBagConstraints  .  anchor  =  java  .  awt  .  GridBagConstraints  .  WEST  ; 
jPanel1  .  add  (  jLstatus  ,  gridBagConstraints  )  ; 
jPanel3  .  add  (  jPanel1  ,  java  .  awt  .  BorderLayout  .  CENTER  )  ; 
jPanel4  .  setLayout  (  new   java  .  awt  .  BorderLayout  (  )  )  ; 
jScrollPane1  .  setPreferredSize  (  new   java  .  awt  .  Dimension  (  452  ,  250  )  )  ; 
jTable1  .  setModel  (  new   javax  .  swing  .  table  .  DefaultTableModel  (  new   Object  [  ]  [  ]  {  {  null  ,  null  }  ,  {  null  ,  null  }  ,  {  null  ,  null  }  ,  {  null  ,  null  }  }  ,  new   String  [  ]  {  "Department"  ,  "Semester"  }  )  )  ; 
jScrollPane1  .  setViewportView  (  jTable1  )  ; 
jPanel4  .  add  (  jScrollPane1  ,  java  .  awt  .  BorderLayout  .  CENTER  )  ; 
jPanel5  .  setBorder  (  javax  .  swing  .  BorderFactory  .  createEtchedBorder  (  )  )  ; 
jPanel5  .  setLayout  (  new   java  .  awt  .  GridBagLayout  (  )  )  ; 
jBSideDelete  .  setText  (  "Del"  )  ; 
jBSideDelete  .  addActionListener  (  new   java  .  awt  .  event  .  ActionListener  (  )  { 

public   void   actionPerformed  (  java  .  awt  .  event  .  ActionEvent   evt  )  { 
jBSideDeleteActionPerformed  (  evt  )  ; 
} 
}  )  ; 
jPanel5  .  add  (  jBSideDelete  ,  new   java  .  awt  .  GridBagConstraints  (  )  )  ; 
jPanel4  .  add  (  jPanel5  ,  java  .  awt  .  BorderLayout  .  EAST  )  ; 
jPanel3  .  add  (  jPanel4  ,  java  .  awt  .  BorderLayout  .  PAGE_END  )  ; 
gridBagConstraints  =  new   java  .  awt  .  GridBagConstraints  (  )  ; 
gridBagConstraints  .  gridy  =  1  ; 
gridBagConstraints  .  fill  =  java  .  awt  .  GridBagConstraints  .  HORIZONTAL  ; 
getContentPane  (  )  .  add  (  jPanel3  ,  gridBagConstraints  )  ; 
jPanel6  .  setLayout  (  new   java  .  awt  .  GridBagLayout  (  )  )  ; 
jBDeleteMain  .  setText  (  "Delete"  )  ; 
jBDeleteMain  .  setToolTipText  (  "Delete"  )  ; 
jBDeleteMain  .  addActionListener  (  new   java  .  awt  .  event  .  ActionListener  (  )  { 

public   void   actionPerformed  (  java  .  awt  .  event  .  ActionEvent   evt  )  { 
jBDeleteMainActionPerformed  (  evt  )  ; 
} 
}  )  ; 
gridBagConstraints  =  new   java  .  awt  .  GridBagConstraints  (  )  ; 
gridBagConstraints  .  fill  =  java  .  awt  .  GridBagConstraints  .  HORIZONTAL  ; 
jPanel6  .  add  (  jBDeleteMain  ,  gridBagConstraints  )  ; 
jBDeleteAll  .  setText  (  "Delete all"  )  ; 
jBDeleteAll  .  setToolTipText  (  "Delete All"  )  ; 
jBDeleteAll  .  addActionListener  (  new   java  .  awt  .  event  .  ActionListener  (  )  { 

public   void   actionPerformed  (  java  .  awt  .  event  .  ActionEvent   evt  )  { 
jBDeleteAllActionPerformed  (  evt  )  ; 
} 
}  )  ; 
gridBagConstraints  =  new   java  .  awt  .  GridBagConstraints  (  )  ; 
gridBagConstraints  .  fill  =  java  .  awt  .  GridBagConstraints  .  HORIZONTAL  ; 
jPanel6  .  add  (  jBDeleteAll  ,  gridBagConstraints  )  ; 
jBViewCompleteList  .  setText  (  "View comp"  )  ; 
jBViewCompleteList  .  setToolTipText  (  "View complete list"  )  ; 
jBViewCompleteList  .  addActionListener  (  new   java  .  awt  .  event  .  ActionListener  (  )  { 

public   void   actionPerformed  (  java  .  awt  .  event  .  ActionEvent   evt  )  { 
jBViewCompleteListActionPerformed  (  evt  )  ; 
} 
}  )  ; 
gridBagConstraints  =  new   java  .  awt  .  GridBagConstraints  (  )  ; 
gridBagConstraints  .  fill  =  java  .  awt  .  GridBagConstraints  .  HORIZONTAL  ; 
jPanel6  .  add  (  jBViewCompleteList  ,  gridBagConstraints  )  ; 
gridBagConstraints  =  new   java  .  awt  .  GridBagConstraints  (  )  ; 
gridBagConstraints  .  gridy  =  2  ; 
gridBagConstraints  .  fill  =  java  .  awt  .  GridBagConstraints  .  HORIZONTAL  ; 
getContentPane  (  )  .  add  (  jPanel6  ,  gridBagConstraints  )  ; 
pack  (  )  ; 
} 

private   void   jBGoActionPerformed  (  java  .  awt  .  event  .  ActionEvent   evt  )  { 
java  .  sql  .  Connection   con  =  null  ; 
java  .  sql  .  PreparedStatement   ps  =  null  ; 
java  .  sql  .  ResultSet   rs  =  null  ; 
java  .  sql  .  Statement   st  =  null  ; 
try  { 
jLauthor  .  setText  (  ""  )  ; 
jLpublisher  .  setText  (  ""  )  ; 
jLedition  .  setText  (  ""  )  ; 
jLisbn  .  setText  (  ""  )  ; 
jLstatus  .  setText  (  ""  )  ; 
jLtitle  .  setText  (  ""  )  ; 
int   i  =  defTbModel  .  getRowCount  (  )  ; 
for  (  int   j  =  0  ;  j  <  i  ;  j  ++  )  { 
defTbModel  .  removeRow  (  0  )  ; 
} 
if  (  !  jTbarcode  .  getText  (  )  .  equals  (  ""  )  )  { 
jTmrp  .  setText  (  ""  )  ; 
jTdiscount  .  setText  (  ""  )  ; 
jTinvoiceCost  .  setText  (  ""  )  ; 
jTshelfLife  .  setText  (  ""  )  ; 
jTcirculationDays  .  setText  (  ""  )  ; 
jTLongtermLending  .  setText  (  ""  )  ; 
barcode  =  jTbarcode  .  getText  (  )  ; 
con  =  reports  .  utility  .  database  .  ConnectionPoolFactory  .  getInstance  (  )  .  getConnectionPool  (  )  .  getConnection  (  )  ; 
st  =  con  .  createStatement  (  )  ; 
rs  =  st  .  executeQuery  (  "select d.mrp_value,d.discount,d.invoice_cost,c.shelf_life,c.circulation_days,c.long_term_lending,cat.cataloguerecordid,cat.owner_library_id from document d,cat_volume cat,cataloguerecord c where d.accession_number='"  +  jTbarcode  .  getText  (  )  +  "' and d.volume_id=cat.volume_id and cat.cataloguerecordid=c.cataloguerecordid and cat.owner_library_id=c.owner_library_id"  )  ; 
boolean   accNo_Exists  =  false  ; 
while  (  rs  .  next  (  )  )  { 
accNo_Exists  =  true  ; 
jTmrp  .  setText  (  rs  .  getDouble  (  "mrp_value"  )  +  ""  )  ; 
jTdiscount  .  setText  (  rs  .  getDouble  (  "discount"  )  +  ""  )  ; 
jTinvoiceCost  .  setText  (  rs  .  getDouble  (  "invoice_cost"  )  +  ""  )  ; 
jTshelfLife  .  setText  (  rs  .  getInt  (  "shelf_life"  )  +  ""  )  ; 
jTcirculationDays  .  setText  (  rs  .  getInt  (  "circulation_days"  )  +  ""  )  ; 
jTLongtermLending  .  setText  (  rs  .  getInt  (  "long_term_lending"  )  +  ""  )  ; 
catId  =  rs  .  getInt  (  "cataloguerecordid"  )  ; 
ownLibId  =  rs  .  getInt  (  "owner_library_id"  )  ; 
} 
if  (  accNo_Exists  ==  false  )  { 
JOptionPane  .  showMessageDialog  (  jPanel2  ,  "The book you are searching is not available"  ,  ""  ,  JOptionPane  .  CANCEL_OPTION  )  ; 
} 
st  .  close  (  )  ; 
rs  .  close  (  )  ; 
}  else  { 
JOptionPane  .  showMessageDialog  (  jPanel2  ,  "Please enter barcode to search"  ,  ""  ,  JOptionPane  .  CANCEL_OPTION  )  ; 
} 
}  catch  (  Exception   e  )  { 
e  .  printStackTrace  (  )  ; 
JOptionPane  .  showMessageDialog  (  jPanel2  ,  "Server is temporarily overloaded"  ,  ""  ,  JOptionPane  .  CANCEL_OPTION  )  ; 
}  finally  { 
try  { 
if  (  con  !=  null  )  { 
con  .  close  (  )  ; 
} 
}  catch  (  Exception   e  )  { 
e  .  printStackTrace  (  )  ; 
} 
} 
} 

public   String   testString  (  Object   o  )  { 
String   retVal  =  ""  ; 
try  { 
if  (  o  !=  null  )  { 
retVal  =  o  .  toString  (  )  .  replaceAll  (  ","  ,  ""  )  ; 
retVal  =  retVal  .  replaceAll  (  "'"  ,  ""  )  ; 
} 
}  catch  (  Exception   e  )  { 
e  .  printStackTrace  (  )  ; 
} 
return   retVal  ; 
} 

private   void   jBaddActionPerformed  (  java  .  awt  .  event  .  ActionEvent   evt  )  { 
java  .  sql  .  Connection   con  =  null  ; 
java  .  sql  .  PreparedStatement   ps  =  null  ; 
java  .  sql  .  Statement   st  =  null  ; 
java  .  sql  .  ResultSet   rs  =  null  ; 
boolean   check_status  =  false  ; 
String   status  =  ""  ; 
try  { 
if  (  !  barcode  .  equals  (  ""  )  )  { 
con  =  reports  .  utility  .  database  .  ConnectionPoolFactory  .  getInstance  (  )  .  getConnectionPool  (  )  .  getConnection  (  )  ; 
st  =  con  .  createStatement  (  )  ; 
rs  =  st  .  executeQuery  (  "select * from cataloguerecord_semester where cataloguerecordid='"  +  catId  +  "' and ownerlibraryid='"  +  ownLibId  +  "' and semesterid='"  +  Integer  .  parseInt  (  jCombo1HashMap  .  get  (  jCsemester  .  getSelectedItem  (  )  )  .  toString  (  )  )  +  "' and dept_id='"  +  Integer  .  parseInt  (  jCombo2HashMap  .  get  (  jCdepartment  .  getSelectedItem  (  )  )  .  toString  (  )  )  +  "'"  )  ; 
while  (  rs  .  next  (  )  )  { 
check_status  =  true  ; 
status  =  "Already available"  ; 
} 
rs  .  close  (  )  ; 
st  .  close  (  )  ; 
if  (  check_status  ==  false  )  { 
ps  =  con  .  prepareStatement  (  "insert into cataloguerecord_semester values(?,?,?,?)"  )  ; 
ps  .  setInt  (  1  ,  catId  )  ; 
ps  .  setInt  (  2  ,  ownLibId  )  ; 
ps  .  setInt  (  3  ,  Integer  .  parseInt  (  jCombo1HashMap  .  get  (  jCsemester  .  getSelectedItem  (  )  )  .  toString  (  )  )  )  ; 
ps  .  setInt  (  4  ,  Integer  .  parseInt  (  jCombo2HashMap  .  get  (  jCdepartment  .  getSelectedItem  (  )  )  .  toString  (  )  )  )  ; 
int   i  =  ps  .  executeUpdate  (  )  ; 
if  (  i  ==  1  )  { 
status  =  "New entry created successfully"  ; 
String   sq  =  "update document set mrp_value=?,discount=?,invoice_cost=? where accession_number=? and library_id=?"  ; 
java  .  sql  .  PreparedStatement   ps1  =  con  .  prepareStatement  (  sq  )  ; 
ps1  .  setDouble  (  1  ,  Double  .  parseDouble  (  jTmrp  .  getText  (  )  )  )  ; 
ps1  .  setDouble  (  2  ,  Double  .  parseDouble  (  jTdiscount  .  getText  (  )  )  )  ; 
ps1  .  setDouble  (  3  ,  Double  .  parseDouble  (  jTinvoiceCost  .  getText  (  )  )  )  ; 
ps1  .  setString  (  4  ,  jTbarcode  .  getText  (  )  )  ; 
ps1  .  setInt  (  5  ,  Integer  .  parseInt  (  reports  .  utility  .  StaticValues  .  getInstance  (  )  .  getLoginLibraryId  (  )  )  )  ; 
int   i1  =  ps1  .  executeUpdate  (  )  ; 
if  (  i1  ==  1  )  { 
ps1  .  close  (  )  ; 
sq  =  "update cataloguerecord set shelf_life=?,circulation_days=?,long_term_lending=? where cataloguerecordid=? and owner_library_id=?"  ; 
ps1  =  con  .  prepareStatement  (  sq  )  ; 
ps1  .  setInt  (  1  ,  Integer  .  parseInt  (  jTshelfLife  .  getText  (  )  )  )  ; 
ps1  .  setInt  (  2  ,  Integer  .  parseInt  (  jTcirculationDays  .  getText  (  )  )  )  ; 
ps1  .  setInt  (  3  ,  Integer  .  parseInt  (  jTLongtermLending  .  getText  (  )  )  )  ; 
ps1  .  setInt  (  4  ,  catId  .  intValue  (  )  )  ; 
ps1  .  setInt  (  5  ,  ownLibId  .  intValue  (  )  )  ; 
ps1  .  executeUpdate  (  )  ; 
ps1  .  close  (  )  ; 
} 
} 
ps  .  close  (  )  ; 
} 
defTbModel  .  setRowCount  (  0  )  ; 
st  =  con  .  createStatement  (  )  ; 
rs  =  st  .  executeQuery  (  "select cat_sem.semesterid,cat_sem.dept_id,s.name,d.dept_name from cataloguerecord_semester cat_sem,dept d,semester_config s where cat_sem.cataloguerecordid='"  +  catId  +  "' and cat_sem.ownerlibraryid='"  +  ownLibId  +  "' and cat_sem.semesterid = s.id and cat_sem.dept_id=d.dept_id order by d.dept_name"  )  ; 
while  (  rs  .  next  (  )  )  { 
Object  [  ]  obj  =  new   Object  [  ]  {  rs  .  getInt  (  "dept_id"  )  ,  rs  .  getString  (  "dept_name"  )  ,  rs  .  getInt  (  "semesterid"  )  ,  rs  .  getString  (  "name"  )  }  ; 
defTbModel  .  addRow  (  obj  )  ; 
} 
st  .  close  (  )  ; 
rs  .  close  (  )  ; 
String   LibId  =  reports  .  utility  .  StaticValues  .  getInstance  (  )  .  getLoginLibraryId  (  )  ; 
reports  .  utility  .  datamodel  .  technicalprocessing  .  DOCUMENT_KEY   document_key  =  new   reports  .  utility  .  datamodel  .  technicalprocessing  .  DOCUMENT_KEY  (  )  ; 
document_key  .  setAccession_number  (  barcode  )  ; 
document_key  .  setLibrary_id  (  Integer  .  parseInt  (  LibId  )  )  ; 
org  .  hibernate  .  Session   session  =  tools  .  HibernateUtil  .  getSessionFactory  (  )  .  openSession  (  )  ; 
reports  .  utility  .  datamodel  .  technicalprocessing  .  DOCUMENT_MANAGER   document_manager  =  new   reports  .  utility  .  datamodel  .  technicalprocessing  .  DOCUMENT_MANAGER  (  )  ; 
java  .  util  .  Hashtable   catalogueRecordsHashtable  =  document_manager  .  getCatalogueDetails  (  session  ,  document_key  )  ; 
jLtitle  .  setText  (  testString  (  catalogueRecordsHashtable  .  get  (  "TITLE"  )  )  )  ; 
jLauthor  .  setText  (  testString  (  catalogueRecordsHashtable  .  get  (  "AUTHOR"  )  )  )  ; 
jLedition  .  setText  (  testString  (  catalogueRecordsHashtable  .  get  (  "EDITION"  )  )  )  ; 
jLpublisher  .  setText  (  testString  (  catalogueRecordsHashtable  .  get  (  "PUBLISHER"  )  )  )  ; 
jLisbn  .  setText  (  testString  (  catalogueRecordsHashtable  .  get  (  "ISBN"  )  )  )  ; 
jLstatus  .  setText  (  status  )  ; 
}  else  { 
JOptionPane  .  showMessageDialog  (  jPanel2  ,  "Please enter barcode to search"  ,  ""  ,  JOptionPane  .  CANCEL_OPTION  )  ; 
} 
}  catch  (  Exception   e  )  { 
e  .  printStackTrace  (  )  ; 
JOptionPane  .  showMessageDialog  (  jPanel2  ,  "Server is temporarily overloaded"  ,  ""  ,  JOptionPane  .  CANCEL_OPTION  )  ; 
}  finally  { 
try  { 
if  (  con  !=  null  )  { 
con  .  close  (  )  ; 
} 
}  catch  (  Exception   e  )  { 
e  .  printStackTrace  (  )  ; 
JOptionPane  .  showMessageDialog  (  jPanel2  ,  "Server is temporarily overloaded"  ,  ""  ,  JOptionPane  .  CANCEL_OPTION  )  ; 
} 
} 
} 

private   void   jBSideDeleteActionPerformed  (  java  .  awt  .  event  .  ActionEvent   evt  )  { 
java  .  sql  .  Connection   con  =  null  ; 
java  .  sql  .  PreparedStatement   ps  =  null  ; 
java  .  sql  .  Statement   st  =  null  ; 
java  .  sql  .  ResultSet   rs  =  null  ; 
try  { 
if  (  jTable1  .  getSelectedRow  (  )  !=  -  1  )  { 
if  (  JOptionPane  .  showConfirmDialog  (  null  ,  "Are you sure, You want to delete this item"  ,  "Delete "  ,  JOptionPane  .  YES_NO_OPTION  ,  JOptionPane  .  INFORMATION_MESSAGE  )  ==  0  )  { 
int   dptId  =  Integer  .  parseInt  (  defTbModel  .  getValueAt  (  jTable1  .  getSelectedRow  (  )  ,  0  )  .  toString  (  )  )  ; 
int   semId  =  Integer  .  parseInt  (  defTbModel  .  getValueAt  (  jTable1  .  getSelectedRow  (  )  ,  2  )  .  toString  (  )  )  ; 
con  =  reports  .  utility  .  database  .  ConnectionPoolFactory  .  getInstance  (  )  .  getConnectionPool  (  )  .  getConnection  (  )  ; 
ps  =  con  .  prepareStatement  (  "delete from cataloguerecord_semester where cataloguerecordid=? and ownerlibraryid=? and semesterid=? and dept_id=?"  )  ; 
ps  .  setInt  (  1  ,  catId  )  ; 
ps  .  setInt  (  2  ,  ownLibId  )  ; 
ps  .  setInt  (  3  ,  semId  )  ; 
ps  .  setInt  (  4  ,  dptId  )  ; 
int   i  =  ps  .  executeUpdate  (  )  ; 
if  (  i  ==  1  )  { 
defTbModel  .  removeRow  (  jTable1  .  getSelectedRow  (  )  )  ; 
}  else  { 
System  .  out  .  println  (  "Row not removed"  +  i  )  ; 
} 
ps  .  close  (  )  ; 
} 
}  else  { 
JOptionPane  .  showMessageDialog  (  null  ,  "Please select an department and semester to delete"  )  ; 
} 
}  catch  (  Exception   e  )  { 
e  .  printStackTrace  (  )  ; 
JOptionPane  .  showMessageDialog  (  jPanel2  ,  "Server is temporarily overloaded"  ,  ""  ,  JOptionPane  .  CANCEL_OPTION  )  ; 
}  finally  { 
try  { 
if  (  con  !=  null  )  { 
con  .  close  (  )  ; 
} 
}  catch  (  Exception   e  )  { 
e  .  printStackTrace  (  )  ; 
} 
} 
} 

private   void   jBDeleteMainActionPerformed  (  java  .  awt  .  event  .  ActionEvent   evt  )  { 
java  .  sql  .  Connection   con  =  null  ; 
java  .  sql  .  PreparedStatement   ps  =  null  ; 
java  .  sql  .  Statement   st  =  null  ; 
java  .  sql  .  ResultSet   rs  =  null  ; 
try  { 
if  (  catId  !=  0  &&  ownLibId  !=  0  )  { 
int   dptId  =  Integer  .  parseInt  (  jCombo2HashMap  .  get  (  jCdepartment  .  getSelectedItem  (  )  )  .  toString  (  )  )  ; 
int   semId  =  Integer  .  parseInt  (  jCombo1HashMap  .  get  (  jCsemester  .  getSelectedItem  (  )  )  .  toString  (  )  )  ; 
con  =  reports  .  utility  .  database  .  ConnectionPoolFactory  .  getInstance  (  )  .  getConnectionPool  (  )  .  getConnection  (  )  ; 
ps  =  con  .  prepareStatement  (  "delete from cataloguerecord_semester where cataloguerecordid=? and ownerlibraryid=? and semesterid=? and dept_id=?"  )  ; 
ps  .  setInt  (  1  ,  catId  )  ; 
ps  .  setInt  (  2  ,  ownLibId  )  ; 
ps  .  setInt  (  3  ,  semId  )  ; 
ps  .  setInt  (  4  ,  dptId  )  ; 
int   i  =  ps  .  executeUpdate  (  )  ; 
if  (  i  ==  1  )  { 
JOptionPane  .  showMessageDialog  (  jPanel2  ,  "Task successfull"  ,  ""  ,  JOptionPane  .  CANCEL_OPTION  )  ; 
}  else  { 
JOptionPane  .  showMessageDialog  (  jPanel2  ,  "Failed to delete"  ,  ""  ,  JOptionPane  .  CANCEL_OPTION  )  ; 
} 
ps  .  close  (  )  ; 
}  else  { 
JOptionPane  .  showMessageDialog  (  jPanel2  ,  "Pls enter the barcode number and search"  ,  ""  ,  JOptionPane  .  CANCEL_OPTION  )  ; 
} 
}  catch  (  Exception   e  )  { 
e  .  printStackTrace  (  )  ; 
JOptionPane  .  showMessageDialog  (  jPanel2  ,  "Server is temporarily overloaded"  ,  ""  ,  JOptionPane  .  CANCEL_OPTION  )  ; 
}  finally  { 
try  { 
if  (  con  !=  null  )  { 
con  .  close  (  )  ; 
} 
}  catch  (  Exception   e  )  { 
e  .  printStackTrace  (  )  ; 
} 
} 
} 

private   void   jBDeleteAllActionPerformed  (  java  .  awt  .  event  .  ActionEvent   evt  )  { 
java  .  sql  .  Connection   con  =  null  ; 
java  .  sql  .  PreparedStatement   ps  =  null  ; 
java  .  sql  .  Statement   st  =  null  ; 
java  .  sql  .  ResultSet   rs  =  null  ; 
try  { 
if  (  catId  !=  0  &&  ownLibId  !=  0  )  { 
con  =  reports  .  utility  .  database  .  ConnectionPoolFactory  .  getInstance  (  )  .  getConnectionPool  (  )  .  getConnection  (  )  ; 
ps  =  con  .  prepareStatement  (  "delete from cataloguerecord_semester where cataloguerecordid=? and ownerlibraryid=?"  )  ; 
ps  .  setInt  (  1  ,  catId  )  ; 
ps  .  setInt  (  2  ,  ownLibId  )  ; 
int   i  =  ps  .  executeUpdate  (  )  ; 
if  (  i  ==  1  )  { 
JOptionPane  .  showMessageDialog  (  jPanel2  ,  "Task successfull"  ,  ""  ,  JOptionPane  .  CANCEL_OPTION  )  ; 
}  else  { 
JOptionPane  .  showMessageDialog  (  jPanel2  ,  "Failed to delete"  ,  ""  ,  JOptionPane  .  CANCEL_OPTION  )  ; 
} 
ps  .  close  (  )  ; 
}  else  { 
JOptionPane  .  showMessageDialog  (  jPanel2  ,  "Pls enter the barcode number and search"  ,  ""  ,  JOptionPane  .  CANCEL_OPTION  )  ; 
} 
}  catch  (  Exception   e  )  { 
e  .  printStackTrace  (  )  ; 
JOptionPane  .  showMessageDialog  (  jPanel2  ,  "Server is temporarily overloaded"  ,  ""  ,  JOptionPane  .  CANCEL_OPTION  )  ; 
}  finally  { 
try  { 
if  (  con  !=  null  )  { 
con  .  close  (  )  ; 
} 
}  catch  (  Exception   e  )  { 
e  .  printStackTrace  (  )  ; 
} 
} 
} 

private   void   jBViewCompleteListActionPerformed  (  java  .  awt  .  event  .  ActionEvent   evt  )  { 
System  .  out  .  println  (  "Entered//////////////////////////"  )  ; 
java  .  sql  .  Connection   con  =  null  ; 
java  .  sql  .  PreparedStatement   ps  =  null  ; 
java  .  sql  .  ResultSet   rs  =  null  ; 
java  .  sql  .  Statement   st  =  null  ; 
try  { 
String   LibId  =  reports  .  utility  .  StaticValues  .  getInstance  (  )  .  getLoginLibraryId  (  )  ; 
con  =  reports  .  utility  .  database  .  ConnectionPoolFactory  .  getInstance  (  )  .  getConnectionPool  (  )  .  getConnection  (  )  ; 
ps  =  con  .  prepareStatement  (  "select id,name from semester_config where library_id=?"  )  ; 
ps  .  setInt  (  1  ,  Integer  .  parseInt  (  LibId  )  )  ; 
rs  =  ps  .  executeQuery  (  )  ; 
while  (  rs  .  next  (  )  )  { 
jCombo3Sem  .  put  (  rs  .  getString  (  "name"  )  ,  rs  .  getInt  (  "id"  )  )  ; 
} 
ps  .  close  (  )  ; 
rs  .  close  (  )  ; 
st  =  con  .  createStatement  (  )  ; 
rs  =  st  .  executeQuery  (  "select dept_id,dept_name from dept where library_id="  +  Integer  .  parseInt  (  LibId  )  )  ; 
while  (  rs  .  next  (  )  )  { 
jCombo4Dept  .  put  (  rs  .  getString  (  "dept_name"  )  ,  rs  .  getString  (  "dept_id"  )  )  ; 
} 
st  .  close  (  )  ; 
rs  .  close  (  )  ; 
}  catch  (  Exception   e  )  { 
e  .  printStackTrace  (  )  ; 
} 
Set   keys  =  jCombo3Sem  .  keySet  (  )  ; 
Iterator   it  =  keys  .  iterator  (  )  ; 
Vector   addL  =  new   Vector  (  )  ; 
while  (  it  .  hasNext  (  )  )  { 
String   key  =  it  .  next  (  )  .  toString  (  )  ; 
addL  .  add  (  key  )  ; 
} 
java  .  util  .  Collections  .  sort  (  addL  )  ; 
addL  .  add  (  "All"  )  ; 
jComboBox1  .  setModel  (  new   javax  .  swing  .  DefaultComboBoxModel  (  addL  )  )  ; 
jComboBox1  .  setSelectedItem  (  "All"  )  ; 
keys  =  jCombo4Dept  .  keySet  (  )  ; 
it  =  keys  .  iterator  (  )  ; 
addL  =  new   Vector  (  )  ; 
while  (  it  .  hasNext  (  )  )  { 
addL  .  add  (  it  .  next  (  )  )  ; 
} 
java  .  util  .  Collections  .  sort  (  addL  )  ; 
addL  .  add  (  "All"  )  ; 
jComboBox2  .  setModel  (  new   javax  .  swing  .  DefaultComboBoxModel  (  addL  )  )  ; 
jComboBox2  .  setSelectedItem  (  "All"  )  ; 
jDialog1  .  setSize  (  500  ,  280  )  ; 
jDialog1  .  setTitle  (  "Generate Semester report"  )  ; 
jDialog1  .  setLocation  (  250  ,  200  )  ; 
jProgressBar1  .  setValue  (  0  )  ; 
jDialog1  .  setVisible  (  true  )  ; 
} 

private   class   PrograssbarStatus   implements   Runnable  { 

private   Statement   st  =  null  ; 

private   ResultSet   rs  =  null  ; 

private   Connection   con  =  null  ; 

private   int   totalCount  =  0  ; 

private   int   statusCount  =  1  ; 

@  Override 
public   void   run  (  )  { 
try  { 
FileWriter   fileWriter  =  null  ; 
if  (  createFile  !=  null  )  { 
fileWriter  =  new   FileWriter  (  createFile  )  ; 
} 
String   sem  =  jComboBox1  .  getSelectedItem  (  )  .  toString  (  )  ; 
String   dept  =  jComboBox2  .  getSelectedItem  (  )  .  toString  (  )  ; 
String   sql  =  ""  ; 
if  (  sem  .  equalsIgnoreCase  (  "All"  )  &&  dept  .  equalsIgnoreCase  (  "All"  )  )  { 
sql  =  "select * from cataloguerecord_semester"  ; 
}  else   if  (  sem  .  equalsIgnoreCase  (  "All"  )  )  { 
int   i  =  Integer  .  parseInt  (  jCombo4Dept  .  get  (  jComboBox2  .  getSelectedItem  (  )  )  .  toString  (  )  )  ; 
sql  =  "select * from cataloguerecord_semester where dept_id='"  +  i  +  "'"  ; 
}  else   if  (  dept  .  equalsIgnoreCase  (  "All"  )  )  { 
int   i  =  Integer  .  parseInt  (  jCombo3Sem  .  get  (  jComboBox1  .  getSelectedItem  (  )  )  .  toString  (  )  )  ; 
sql  =  "select * from cataloguerecord_semester where semesterid='"  +  i  +  "'"  ; 
}  else  { 
int   i  =  Integer  .  parseInt  (  jCombo4Dept  .  get  (  jComboBox2  .  getSelectedItem  (  )  )  .  toString  (  )  )  ; 
int   j  =  Integer  .  parseInt  (  jCombo3Sem  .  get  (  jComboBox1  .  getSelectedItem  (  )  )  .  toString  (  )  )  ; 
sql  =  "select * from cataloguerecord_semester where dept_id='"  +  i  +  "' and semesterid='"  +  j  +  "'"  ; 
} 
con  =  reports  .  utility  .  database  .  ConnectionPoolFactory  .  getInstance  (  )  .  getConnectionPool  (  )  .  getConnection  (  )  ; 
st  =  con  .  createStatement  (  )  ; 
rs  =  st  .  executeQuery  (  "select count(*) from cataloguerecord_semester c,cat_volume cat,document d where c.cataloguerecordid=cat.cataloguerecordid and c.ownerlibraryid = cat.owner_library_id and cat.volume_id=d.volume_id"  )  ; 
while  (  rs  .  next  (  )  )  { 
totalCount  =  rs  .  getInt  (  1  )  ; 
} 
rs  .  close  (  )  ; 
st  .  close  (  )  ; 
st  =  con  .  createStatement  (  )  ; 
rs  =  st  .  executeQuery  (  sql  )  ; 
while  (  rs  .  next  (  )  )  { 
String   semester  =  ""  ; 
String   department  =  ""  ; 
int   catId  =  rs  .  getInt  (  "cataloguerecordid"  )  ; 
int   ownLibid  =  rs  .  getInt  (  "ownerlibraryid"  )  ; 
int   semId  =  rs  .  getInt  (  "semesterid"  )  ; 
int   dptId  =  rs  .  getInt  (  "dept_id"  )  ; 
Statement   st1  =  con  .  createStatement  (  )  ; 
ResultSet   rs1  =  st1  .  executeQuery  (  "select d.dept_name,s.name from dept d,semester_config s where d.dept_id='"  +  dptId  +  "' and s.id='"  +  semId  +  "'"  )  ; 
while  (  rs1  .  next  (  )  )  { 
if  (  fileWriter  !=  null  )  { 
semester  =  rs1  .  getString  (  "name"  )  ; 
department  =  rs1  .  getString  (  "dept_name"  )  ; 
fileWriter  .  write  (  semester  +  ","  +  department  +  "\n"  )  ; 
} 
} 
st1  .  close  (  )  ; 
rs1  .  close  (  )  ; 
fileWriter  .  write  (  "TITLE,AUTHOR,EDITION,PUBLISHER,ISBN,SEMESTER,DEPARTMENT"  )  ; 
fileWriter  .  write  (  "\n"  )  ; 
String   s  =  "select d.accession_number,d.library_id from cat_volume cat,document d where cat.cataloguerecordid='"  +  catId  +  "' and cat.owner_library_id='"  +  ownLibid  +  "' and cat.volume_id=d.volume_id"  ; 
st1  =  con  .  createStatement  (  )  ; 
rs1  =  st1  .  executeQuery  (  s  )  ; 
while  (  rs1  .  next  (  )  )  { 
statusCount  ++  ; 
System  .  out  .  println  (  "accessionnumber is :::::::::::"  +  rs1  .  getString  (  "accession_number"  )  )  ; 
reports  .  utility  .  datamodel  .  technicalprocessing  .  DOCUMENT_KEY   document_key  =  new   reports  .  utility  .  datamodel  .  technicalprocessing  .  DOCUMENT_KEY  (  )  ; 
document_key  .  setAccession_number  (  rs1  .  getString  (  "accession_number"  )  )  ; 
document_key  .  setLibrary_id  (  rs1  .  getInt  (  "library_id"  )  )  ; 
org  .  hibernate  .  Session   session  =  tools  .  HibernateUtil  .  getSessionFactory  (  )  .  openSession  (  )  ; 
reports  .  utility  .  datamodel  .  technicalprocessing  .  DOCUMENT_MANAGER   document_manager  =  new   reports  .  utility  .  datamodel  .  technicalprocessing  .  DOCUMENT_MANAGER  (  )  ; 
java  .  util  .  Hashtable   catalogueRecordsHashtable  =  document_manager  .  getCatalogueDetails  (  session  ,  document_key  )  ; 
System  .  out  .  println  (  "title is ::::::"  +  testString  (  catalogueRecordsHashtable  .  get  (  "TITLE"  )  )  )  ; 
if  (  fileWriter  !=  null  )  { 
fileWriter  .  write  (  testString  (  catalogueRecordsHashtable  .  get  (  "TITLE"  )  )  +  ","  +  testString  (  catalogueRecordsHashtable  .  get  (  "AUTHOR"  )  )  +  ","  +  testString  (  catalogueRecordsHashtable  .  get  (  "EDITION"  )  )  +  ","  +  testString  (  catalogueRecordsHashtable  .  get  (  "PUBLISHER"  )  )  +  ","  +  testString  (  catalogueRecordsHashtable  .  get  (  "ISBN"  )  )  +  ","  +  semester  +  ","  +  department  +  "\n"  )  ; 
} 
session  .  close  (  )  ; 
} 
rs1  .  close  (  )  ; 
st1  .  close  (  )  ; 
} 
rs  .  close  (  )  ; 
st  .  close  (  )  ; 
if  (  fileWriter  !=  null  )  { 
fileWriter  .  flush  (  )  ; 
fileWriter  .  close  (  )  ; 
} 
}  catch  (  Exception   e  )  { 
e  .  printStackTrace  (  )  ; 
}  finally  { 
try  { 
if  (  con  !=  null  )  { 
con  .  close  (  )  ; 
} 
}  catch  (  Exception   e  )  { 
e  .  printStackTrace  (  )  ; 
} 
} 
} 
} 

public   void   fileChooser  (  )  { 
try  { 
JFileChooser   chooser  =  new   JFileChooser  (  )  ; 
chooser  .  setFileFilter  (  new   FileFilter  (  )  { 

@  Override 
public   boolean   accept  (  File   file  )  { 
boolean   status  =  false  ; 
try  { 
String   fileName  =  file  .  getName  (  )  .  toLowerCase  (  )  ; 
status  =  fileName  .  endsWith  (  ".csv"  )  ; 
}  catch  (  Exception   e  )  { 
e  .  printStackTrace  (  )  ; 
} 
return   status  ; 
} 

@  Override 
public   String   getDescription  (  )  { 
return  ".csv"  ; 
} 
}  )  ; 
int   i  =  chooser  .  showSaveDialog  (  this  )  ; 
chooser  .  setFileSelectionMode  (  JFileChooser  .  FILES_ONLY  )  ; 
if  (  i  ==  JFileChooser  .  APPROVE_OPTION  )  { 
String   file  =  chooser  .  getSelectedFile  (  )  .  toString  (  )  ; 
StringTokenizer   str  =  new   StringTokenizer  (  file  ,  "."  )  ; 
if  (  str  .  countTokens  (  )  <=  2  )  { 
if  (  str  .  countTokens  (  )  ==  1  )  { 
createFile  =  new   File  (  chooser  .  getSelectedFile  (  )  .  toString  (  )  +  ".csv"  )  ; 
if  (  createFile  .  exists  (  )  )  { 
int   cnt  =  JOptionPane  .  showConfirmDialog  (  this  ,  "This file already exists ! Are you sure \n you want to over write it."  ,  "check"  ,  JOptionPane  .  OK_CANCEL_OPTION  )  ; 
if  (  cnt  ==  0  )  { 
jTextField1  .  setText  (  createFile  .  toString  (  )  )  ; 
System  .  out  .  println  (  "override"  )  ; 
}  else  { 
createFile  =  null  ; 
jTextField1  .  setText  (  ""  )  ; 
} 
}  else  { 
jTextField1  .  setText  (  createFile  .  toString  (  )  )  ; 
} 
}  else  { 
str  .  nextToken  (  )  ; 
String   s1  =  str  .  nextToken  (  "."  )  ; 
if  (  s1  .  equalsIgnoreCase  (  "csv"  )  )  { 
createFile  =  new   File  (  chooser  .  getSelectedFile  (  )  .  toString  (  )  )  ; 
if  (  createFile  .  exists  (  )  )  { 
int   cnt  =  JOptionPane  .  showConfirmDialog  (  this  ,  "This file already exists ! Are you sure \n you want to over write it."  ,  "check"  ,  JOptionPane  .  OK_CANCEL_OPTION  )  ; 
if  (  cnt  ==  0  )  { 
jTextField1  .  setText  (  createFile  .  toString  (  )  )  ; 
System  .  out  .  println  (  "override"  )  ; 
}  else  { 
createFile  =  null  ; 
jTextField1  .  setText  (  ""  )  ; 
} 
}  else  { 
jTextField1  .  setText  (  createFile  .  toString  (  )  )  ; 
} 
}  else  { 
JOptionPane  .  showMessageDialog  (  this  ,  "The given file is not in .csv format \n Please create .csv extension."  ,  "check"  ,  JOptionPane  .  CANCEL_OPTION  )  ; 
} 
} 
}  else  { 
JOptionPane  .  showMessageDialog  (  this  ,  "The given file name is not correct \n Please create a new file."  ,  "check"  ,  JOptionPane  .  YES_OPTION  )  ; 
} 
}  else  { 
jTextField1  .  setText  (  ""  )  ; 
} 
}  catch  (  Exception   e  )  { 
e  .  printStackTrace  (  )  ; 
} 
} 

private   void   jButton1ActionPerformed  (  java  .  awt  .  event  .  ActionEvent   evt  )  { 
fileChooser  (  )  ; 
} 

private   void   jBGenerateActionPerformed  (  java  .  awt  .  event  .  ActionEvent   evt  )  { 
if  (  !  jTextField1  .  getText  (  )  .  equalsIgnoreCase  (  ""  )  )  { 
final   PrograssbarStatus   t  =  new   PrograssbarStatus  (  )  ; 
Thread   t1  =  new   Thread  (  t  )  ; 
t1  .  start  (  )  ; 
try  { 
t1  .  sleep  (  1000  )  ; 
}  catch  (  Exception   e  )  { 
e  .  printStackTrace  (  )  ; 
} 
jProgressBar1  .  setMaximum  (  t  .  totalCount  +  1  )  ; 
jProgressBar1  .  setMinimum  (  0  )  ; 
timer  =  new   Timer  (  100  ,  new   ActionListener  (  )  { 

@  Override 
public   void   actionPerformed  (  ActionEvent   ae  )  { 
jProgressBar1  .  setValue  (  t  .  statusCount  )  ; 
if  (  t  .  totalCount  +  1  ==  t  .  statusCount  )  { 
if  (  timer  !=  null  )  { 
timer  .  setRepeats  (  false  )  ; 
timer  .  stop  (  )  ; 
JOptionPane  .  showMessageDialog  (  null  ,  "Report generated"  ,  "check"  ,  JOptionPane  .  YES_OPTION  )  ; 
} 
} 
} 
}  )  ; 
timer  .  setRepeats  (  true  )  ; 
timer  .  start  (  )  ; 
}  else  { 
JOptionPane  .  showMessageDialog  (  null  ,  "Please select a csv file."  ,  "check"  ,  JOptionPane  .  YES_OPTION  )  ; 
} 
} 

private   void   jBCcancelActionPerformed  (  java  .  awt  .  event  .  ActionEvent   evt  )  { 
jDialog1  .  dispose  (  )  ; 
} 

private   void   jTdiscountFocusLost  (  java  .  awt  .  event  .  FocusEvent   evt  )  { 
double   mrp  =  Double  .  parseDouble  (  jTmrp  .  getText  (  )  )  ; 
double   discount  =  Double  .  parseDouble  (  jTdiscount  .  getText  (  )  )  ; 
double   invoiceCost  =  mrp  -  discount  /  100  *  mrp  ; 
java  .  math  .  BigDecimal   bd  =  new   java  .  math  .  BigDecimal  (  invoiceCost  )  ; 
invoiceCost  =  bd  .  setScale  (  2  ,  java  .  math  .  BigDecimal  .  ROUND_FLOOR  )  .  doubleValue  (  )  ; 
jTinvoiceCost  .  setText  (  invoiceCost  +  ""  )  ; 
} 

private   void   jTmrpFocusLost  (  java  .  awt  .  event  .  FocusEvent   evt  )  { 
jTdiscountFocusLost  (  evt  )  ; 
} 

private   javax  .  swing  .  JButton   jBCcancel  ; 

private   javax  .  swing  .  JButton   jBDeleteAll  ; 

private   javax  .  swing  .  JButton   jBDeleteMain  ; 

private   javax  .  swing  .  JButton   jBGenerate  ; 

private   javax  .  swing  .  JButton   jBGo  ; 

private   javax  .  swing  .  JButton   jBSideDelete  ; 

private   javax  .  swing  .  JButton   jBViewCompleteList  ; 

private   javax  .  swing  .  JButton   jBadd  ; 

private   javax  .  swing  .  JButton   jButton1  ; 

private   javax  .  swing  .  JComboBox   jCdepartment  ; 

private   javax  .  swing  .  JComboBox   jComboBox1  ; 

private   javax  .  swing  .  JComboBox   jComboBox2  ; 

private   javax  .  swing  .  JComboBox   jCsemester  ; 

private   javax  .  swing  .  JDialog   jDialog1  ; 

private   javax  .  swing  .  JLabel   jLabel1  ; 

private   javax  .  swing  .  JLabel   jLabel10  ; 

private   javax  .  swing  .  JLabel   jLabel11  ; 

private   javax  .  swing  .  JLabel   jLabel12  ; 

private   javax  .  swing  .  JLabel   jLabel13  ; 

private   javax  .  swing  .  JLabel   jLabel14  ; 

private   javax  .  swing  .  JLabel   jLabel15  ; 

private   javax  .  swing  .  JLabel   jLabel16  ; 

private   javax  .  swing  .  JLabel   jLabel17  ; 

private   javax  .  swing  .  JLabel   jLabel2  ; 

private   javax  .  swing  .  JLabel   jLabel3  ; 

private   javax  .  swing  .  JLabel   jLabel4  ; 

private   javax  .  swing  .  JLabel   jLabel5  ; 

private   javax  .  swing  .  JLabel   jLabel6  ; 

private   javax  .  swing  .  JLabel   jLabel7  ; 

private   javax  .  swing  .  JLabel   jLabel8  ; 

private   javax  .  swing  .  JLabel   jLabel9  ; 

private   javax  .  swing  .  JLabel   jLauthor  ; 

private   javax  .  swing  .  JLabel   jLbarcode  ; 

private   javax  .  swing  .  JLabel   jLedition  ; 

private   javax  .  swing  .  JLabel   jLisbn  ; 

private   javax  .  swing  .  JLabel   jLpublisher  ; 

private   javax  .  swing  .  JLabel   jLstatus  ; 

private   javax  .  swing  .  JLabel   jLtitle  ; 

private   javax  .  swing  .  JPanel   jPanel1  ; 

private   javax  .  swing  .  JPanel   jPanel2  ; 

private   javax  .  swing  .  JPanel   jPanel3  ; 

private   javax  .  swing  .  JPanel   jPanel4  ; 

private   javax  .  swing  .  JPanel   jPanel5  ; 

private   javax  .  swing  .  JPanel   jPanel6  ; 

private   javax  .  swing  .  JPanel   jPanel7  ; 

private   javax  .  swing  .  JPanel   jPanel8  ; 

private   javax  .  swing  .  JProgressBar   jProgressBar1  ; 

private   javax  .  swing  .  JScrollPane   jScrollPane1  ; 

private   javax  .  swing  .  JTextField   jTLongtermLending  ; 

private   javax  .  swing  .  JTable   jTable1  ; 

private   javax  .  swing  .  JTextField   jTbarcode  ; 

private   javax  .  swing  .  JTextField   jTcirculationDays  ; 

private   javax  .  swing  .  JTextField   jTdiscount  ; 

private   javax  .  swing  .  JTextField   jTextField1  ; 

private   javax  .  swing  .  JTextField   jTinvoiceCost  ; 

private   javax  .  swing  .  JTextField   jTmrp  ; 

private   javax  .  swing  .  JTextField   jTshelfLife  ; 
} 

