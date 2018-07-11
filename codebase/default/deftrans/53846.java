import   java  .  io  .  *  ; 
import   java  .  util  .  ArrayList  ; 
import   java  .  util  .  Iterator  ; 
import   java  .  util  .  Locale  ; 
import   javax  .  swing  .  *  ; 






public   class   jQuantum   extends   javax  .  swing  .  JFrame  { 

private   final   String   version  =  "jQuantum 1.0 (beta)"  ; 

private   final   String   copyright  =  "&copy; 2004 Andreas de Vries under GNU GPL"  ; 

private   String   language  ; 

int   xRegisterSize  =  0  ; 

int   yRegisterSize  =  0  ; 

Register   xRegister  ; 

Register   yRegister  ; 

ArrayList  [  ]  entanglementXY  ; 

int  [  ]  initialQubits  ; 

private   boolean   lengthColoring  ; 


public   jQuantum  (  )  { 
this  .  language  =  getLocale  (  )  .  getLanguage  (  )  ; 
openFrame  (  )  ; 
} 


public   jQuantum  (  String   language  )  { 
this  .  language  =  language  ; 
openFrame  (  )  ; 
} 

private   void   openFrame  (  )  { 
try  { 
UIManager  .  setLookAndFeel  (  UIManager  .  getSystemLookAndFeelClassName  (  )  )  ; 
if  (  UIManager  .  getSystemLookAndFeelClassName  (  )  .  equals  (  "javax.swing.plaf.metal.MetalLookAndFeel"  )  )  { 
UIManager  .  setLookAndFeel  (  "com.sun.java.swing.plaf.motif.MotifLookAndFeel"  )  ; 
} 
}  catch  (  Exception   e  )  { 
} 
initComponents  (  )  ; 
lengthColoring  =  lengthColoringItem  .  isSelected  (  )  ; 
deleteButton  .  setMnemonic  (  java  .  awt  .  event  .  KeyEvent  .  VK_BACK_SPACE  )  ; 
circuitPanel  =  new   CircuitPanel  (  )  ; 
circuitPanel  .  setBackground  (  new   java  .  awt  .  Color  (  255  ,  255  ,  255  )  )  ; 
circuitPanel  .  setBorder  (  new   javax  .  swing  .  border  .  EtchedBorder  (  )  )  ; 
circuitScrollPane  .  setViewportView  (  circuitPanel  )  ; 
xPanel  =  new   RegisterPanel  (  768  ,  160  )  ; 
xPanel  .  setBackground  (  new   java  .  awt  .  Color  (  255  ,  255  ,  255  )  )  ; 
xPanel  .  setBorder  (  new   javax  .  swing  .  border  .  TitledBorder  (  "x-Register"  )  )  ; 
xScrollPane  .  setViewportView  (  xPanel  )  ; 
yPanel  =  new   RegisterPanel  (  768  ,  80  )  ; 
yPanel  .  setBackground  (  new   java  .  awt  .  Color  (  255  ,  255  ,  255  )  )  ; 
yPanel  .  setBorder  (  new   javax  .  swing  .  border  .  TitledBorder  (  "y-Register"  )  )  ; 
yScrollPane  .  setViewportView  (  yPanel  )  ; 
if  (  language  .  equals  (  Locale  .  GERMAN  .  toString  (  )  )  )  { 
toGerman  (  )  ; 
} 
java  .  awt  .  Dimension   screenSize  =  java  .  awt  .  Toolkit  .  getDefaultToolkit  (  )  .  getScreenSize  (  )  ; 
setLocation  (  (  screenSize  .  width  -  getSize  (  )  .  width  )  /  2  ,  (  screenSize  .  height  -  getSize  (  )  .  height  )  /  2  -  20  )  ; 
setVisible  (  true  )  ; 
} 






private   void   initComponents  (  )  { 
coloringButtonGroup  =  new   javax  .  swing  .  ButtonGroup  (  )  ; 
controlPanel  =  new   javax  .  swing  .  JPanel  (  )  ; 
iniButton  =  new   javax  .  swing  .  JButton  (  )  ; 
jSeparator2  =  new   javax  .  swing  .  JSeparator  (  )  ; 
nextButton  =  new   javax  .  swing  .  JButton  (  )  ; 
prevButton  =  new   javax  .  swing  .  JButton  (  )  ; 
jSeparator4  =  new   javax  .  swing  .  JSeparator  (  )  ; 
goButton  =  new   javax  .  swing  .  JButton  (  )  ; 
centerPanel  =  new   javax  .  swing  .  JPanel  (  )  ; 
constructPanel  =  new   javax  .  swing  .  JPanel  (  )  ; 
gatePanel  =  new   javax  .  swing  .  JPanel  (  )  ; 
iniQubitButton  =  new   javax  .  swing  .  JButton  (  )  ; 
jSeparator0  =  new   javax  .  swing  .  JSeparator  (  )  ; 
hadamardButton  =  new   javax  .  swing  .  JButton  (  )  ; 
cNOTButton  =  new   javax  .  swing  .  JButton  (  )  ; 
toffoliButton  =  new   javax  .  swing  .  JButton  (  )  ; 
qftButton  =  new   javax  .  swing  .  JButton  (  )  ; 
invQftButton  =  new   javax  .  swing  .  JButton  (  )  ; 
U_fButton  =  new   javax  .  swing  .  JButton  (  )  ; 
rotationButton  =  new   javax  .  swing  .  JButton  (  )  ; 
jSeparator1  =  new   javax  .  swing  .  JSeparator  (  )  ; 
measureButton  =  new   javax  .  swing  .  JButton  (  )  ; 
jSeparator3  =  new   javax  .  swing  .  JSeparator  (  )  ; 
deleteButton  =  new   javax  .  swing  .  JButton  (  )  ; 
circuitScrollPane  =  new   javax  .  swing  .  JScrollPane  (  )  ; 
registerPanel  =  new   javax  .  swing  .  JPanel  (  )  ; 
xScrollPane  =  new   javax  .  swing  .  JScrollPane  (  )  ; 
yScrollPane  =  new   javax  .  swing  .  JScrollPane  (  )  ; 
jMenuBar  =  new   javax  .  swing  .  JMenuBar  (  )  ; 
fileMenu  =  new   javax  .  swing  .  JMenu  (  )  ; 
loadMenuItem  =  new   javax  .  swing  .  JMenuItem  (  )  ; 
saveMenuItem  =  new   javax  .  swing  .  JMenuItem  (  )  ; 
jSeparator5  =  new   javax  .  swing  .  JSeparator  (  )  ; 
exitMenuItem  =  new   javax  .  swing  .  JMenuItem  (  )  ; 
configMenu  =  new   javax  .  swing  .  JMenu  (  )  ; 
coloringMenu  =  new   javax  .  swing  .  JMenu  (  )  ; 
lengthColoringItem  =  new   javax  .  swing  .  JCheckBoxMenuItem  (  )  ; 
noLengthColoringItem  =  new   javax  .  swing  .  JCheckBoxMenuItem  (  )  ; 
helpMenu  =  new   javax  .  swing  .  JMenu  (  )  ; 
aboutMenuItem  =  new   javax  .  swing  .  JMenuItem  (  )  ; 
helpMenuItem  =  new   javax  .  swing  .  JMenuItem  (  )  ; 
setTitle  (  "jQuantum"  )  ; 
addWindowListener  (  new   java  .  awt  .  event  .  WindowAdapter  (  )  { 

public   void   windowClosing  (  java  .  awt  .  event  .  WindowEvent   evt  )  { 
exitForm  (  evt  )  ; 
} 
}  )  ; 
controlPanel  .  setBorder  (  new   javax  .  swing  .  border  .  TitledBorder  (  "Control"  )  )  ; 
controlPanel  .  setPreferredSize  (  new   java  .  awt  .  Dimension  (  72  ,  76  )  )  ; 
iniButton  .  setIcon  (  new   javax  .  swing  .  ImageIcon  (  getClass  (  )  .  getResource  (  "/icons/initialize.gif"  )  )  )  ; 
iniButton  .  setToolTipText  (  "Initialize registers"  )  ; 
iniButton  .  addActionListener  (  new   java  .  awt  .  event  .  ActionListener  (  )  { 

public   void   actionPerformed  (  java  .  awt  .  event  .  ActionEvent   evt  )  { 
iniButtonActionPerformed  (  evt  )  ; 
} 
}  )  ; 
controlPanel  .  add  (  iniButton  )  ; 
jSeparator2  .  setPreferredSize  (  new   java  .  awt  .  Dimension  (  66  ,  2  )  )  ; 
controlPanel  .  add  (  jSeparator2  )  ; 
nextButton  .  setIcon  (  new   javax  .  swing  .  ImageIcon  (  getClass  (  )  .  getResource  (  "/icons/next.gif"  )  )  )  ; 
nextButton  .  setToolTipText  (  "Do next step"  )  ; 
nextButton  .  addActionListener  (  new   java  .  awt  .  event  .  ActionListener  (  )  { 

public   void   actionPerformed  (  java  .  awt  .  event  .  ActionEvent   evt  )  { 
nextButtonActionPerformed  (  evt  )  ; 
} 
}  )  ; 
controlPanel  .  add  (  nextButton  )  ; 
prevButton  .  setIcon  (  new   javax  .  swing  .  ImageIcon  (  getClass  (  )  .  getResource  (  "/icons/prev.gif"  )  )  )  ; 
prevButton  .  setToolTipText  (  "Do previous step"  )  ; 
prevButton  .  addActionListener  (  new   java  .  awt  .  event  .  ActionListener  (  )  { 

public   void   actionPerformed  (  java  .  awt  .  event  .  ActionEvent   evt  )  { 
prevButtonActionPerformed  (  evt  )  ; 
} 
}  )  ; 
controlPanel  .  add  (  prevButton  )  ; 
jSeparator4  .  setPreferredSize  (  new   java  .  awt  .  Dimension  (  64  ,  2  )  )  ; 
controlPanel  .  add  (  jSeparator4  )  ; 
goButton  .  setIcon  (  new   javax  .  swing  .  ImageIcon  (  getClass  (  )  .  getResource  (  "/icons/start.gif"  )  )  )  ; 
goButton  .  setToolTipText  (  "Start quantum computation"  )  ; 
goButton  .  addActionListener  (  new   java  .  awt  .  event  .  ActionListener  (  )  { 

public   void   actionPerformed  (  java  .  awt  .  event  .  ActionEvent   evt  )  { 
goButtonActionPerformed  (  evt  )  ; 
} 
}  )  ; 
controlPanel  .  add  (  goButton  )  ; 
getContentPane  (  )  .  add  (  controlPanel  ,  java  .  awt  .  BorderLayout  .  WEST  )  ; 
centerPanel  .  setLayout  (  new   java  .  awt  .  BorderLayout  (  )  )  ; 
constructPanel  .  setLayout  (  new   java  .  awt  .  BorderLayout  (  )  )  ; 
constructPanel  .  setBorder  (  new   javax  .  swing  .  border  .  TitledBorder  (  "Circuit Design"  )  )  ; 
gatePanel  .  setLayout  (  new   java  .  awt  .  FlowLayout  (  java  .  awt  .  FlowLayout  .  LEFT  )  )  ; 
iniQubitButton  .  setIcon  (  new   javax  .  swing  .  ImageIcon  (  getClass  (  )  .  getResource  (  "/icons/iniQubit.gif"  )  )  )  ; 
iniQubitButton  .  setToolTipText  (  "Initialize qubits"  )  ; 
iniQubitButton  .  addActionListener  (  new   java  .  awt  .  event  .  ActionListener  (  )  { 

public   void   actionPerformed  (  java  .  awt  .  event  .  ActionEvent   evt  )  { 
iniQubitButtonActionPerformed  (  evt  )  ; 
} 
}  )  ; 
gatePanel  .  add  (  iniQubitButton  )  ; 
jSeparator0  .  setOrientation  (  javax  .  swing  .  SwingConstants  .  VERTICAL  )  ; 
jSeparator0  .  setPreferredSize  (  new   java  .  awt  .  Dimension  (  2  ,  32  )  )  ; 
gatePanel  .  add  (  jSeparator0  )  ; 
hadamardButton  .  setIcon  (  new   javax  .  swing  .  ImageIcon  (  getClass  (  )  .  getResource  (  "/icons/hadamard.gif"  )  )  )  ; 
hadamardButton  .  setToolTipText  (  "Hadamard gate"  )  ; 
hadamardButton  .  addActionListener  (  new   java  .  awt  .  event  .  ActionListener  (  )  { 

public   void   actionPerformed  (  java  .  awt  .  event  .  ActionEvent   evt  )  { 
hadamardButtonActionPerformed  (  evt  )  ; 
} 
}  )  ; 
gatePanel  .  add  (  hadamardButton  )  ; 
cNOTButton  .  setIcon  (  new   javax  .  swing  .  ImageIcon  (  getClass  (  )  .  getResource  (  "/icons/cNOT.gif"  )  )  )  ; 
cNOTButton  .  setToolTipText  (  "c-NOT gate"  )  ; 
cNOTButton  .  addActionListener  (  new   java  .  awt  .  event  .  ActionListener  (  )  { 

public   void   actionPerformed  (  java  .  awt  .  event  .  ActionEvent   evt  )  { 
cNOTButtonActionPerformed  (  evt  )  ; 
} 
}  )  ; 
gatePanel  .  add  (  cNOTButton  )  ; 
toffoliButton  .  setIcon  (  new   javax  .  swing  .  ImageIcon  (  getClass  (  )  .  getResource  (  "/icons/toffoli.gif"  )  )  )  ; 
toffoliButton  .  setToolTipText  (  "Toffoli gate"  )  ; 
toffoliButton  .  addActionListener  (  new   java  .  awt  .  event  .  ActionListener  (  )  { 

public   void   actionPerformed  (  java  .  awt  .  event  .  ActionEvent   evt  )  { 
toffoliButtonActionPerformed  (  evt  )  ; 
} 
}  )  ; 
gatePanel  .  add  (  toffoliButton  )  ; 
qftButton  .  setIcon  (  new   javax  .  swing  .  ImageIcon  (  getClass  (  )  .  getResource  (  "/icons/qft.gif"  )  )  )  ; 
qftButton  .  setToolTipText  (  "quantum Fourier transform"  )  ; 
qftButton  .  addActionListener  (  new   java  .  awt  .  event  .  ActionListener  (  )  { 

public   void   actionPerformed  (  java  .  awt  .  event  .  ActionEvent   evt  )  { 
qftButtonActionPerformed  (  evt  )  ; 
} 
}  )  ; 
gatePanel  .  add  (  qftButton  )  ; 
invQftButton  .  setIcon  (  new   javax  .  swing  .  ImageIcon  (  getClass  (  )  .  getResource  (  "/icons/invQft.gif"  )  )  )  ; 
invQftButton  .  setToolTipText  (  "inverse quantum Fourier transform"  )  ; 
invQftButton  .  addActionListener  (  new   java  .  awt  .  event  .  ActionListener  (  )  { 

public   void   actionPerformed  (  java  .  awt  .  event  .  ActionEvent   evt  )  { 
invQftButtonActionPerformed  (  evt  )  ; 
} 
}  )  ; 
gatePanel  .  add  (  invQftButton  )  ; 
U_fButton  .  setIcon  (  new   javax  .  swing  .  ImageIcon  (  getClass  (  )  .  getResource  (  "/icons/U_f.gif"  )  )  )  ; 
U_fButton  .  setToolTipText  (  "Function evaluation"  )  ; 
U_fButton  .  addActionListener  (  new   java  .  awt  .  event  .  ActionListener  (  )  { 

public   void   actionPerformed  (  java  .  awt  .  event  .  ActionEvent   evt  )  { 
U_fButtonActionPerformed  (  evt  )  ; 
} 
}  )  ; 
gatePanel  .  add  (  U_fButton  )  ; 
rotationButton  .  setIcon  (  new   javax  .  swing  .  ImageIcon  (  getClass  (  )  .  getResource  (  "/icons/rotation.gif"  )  )  )  ; 
rotationButton  .  setToolTipText  (  "Rotation operator"  )  ; 
rotationButton  .  addActionListener  (  new   java  .  awt  .  event  .  ActionListener  (  )  { 

public   void   actionPerformed  (  java  .  awt  .  event  .  ActionEvent   evt  )  { 
rotationButtonActionPerformed  (  evt  )  ; 
} 
}  )  ; 
gatePanel  .  add  (  rotationButton  )  ; 
jSeparator1  .  setOrientation  (  javax  .  swing  .  SwingConstants  .  VERTICAL  )  ; 
jSeparator1  .  setPreferredSize  (  new   java  .  awt  .  Dimension  (  2  ,  32  )  )  ; 
gatePanel  .  add  (  jSeparator1  )  ; 
measureButton  .  setIcon  (  new   javax  .  swing  .  ImageIcon  (  getClass  (  )  .  getResource  (  "/icons/measurement.gif"  )  )  )  ; 
measureButton  .  setToolTipText  (  "Measurement"  )  ; 
measureButton  .  addActionListener  (  new   java  .  awt  .  event  .  ActionListener  (  )  { 

public   void   actionPerformed  (  java  .  awt  .  event  .  ActionEvent   evt  )  { 
measureButtonActionPerformed  (  evt  )  ; 
} 
}  )  ; 
gatePanel  .  add  (  measureButton  )  ; 
jSeparator3  .  setOrientation  (  javax  .  swing  .  SwingConstants  .  VERTICAL  )  ; 
jSeparator3  .  setPreferredSize  (  new   java  .  awt  .  Dimension  (  2  ,  32  )  )  ; 
gatePanel  .  add  (  jSeparator3  )  ; 
deleteButton  .  setIcon  (  new   javax  .  swing  .  ImageIcon  (  getClass  (  )  .  getResource  (  "/icons/delete.gif"  )  )  )  ; 
deleteButton  .  setMnemonic  (  'V'  )  ; 
deleteButton  .  setToolTipText  (  "Delete last gate (also: 'ALT-BACKSPACE')"  )  ; 
deleteButton  .  addActionListener  (  new   java  .  awt  .  event  .  ActionListener  (  )  { 

public   void   actionPerformed  (  java  .  awt  .  event  .  ActionEvent   evt  )  { 
deleteButtonActionPerformed  (  evt  )  ; 
} 
}  )  ; 
gatePanel  .  add  (  deleteButton  )  ; 
constructPanel  .  add  (  gatePanel  ,  java  .  awt  .  BorderLayout  .  NORTH  )  ; 
circuitScrollPane  .  setPreferredSize  (  new   java  .  awt  .  Dimension  (  800  ,  200  )  )  ; 
constructPanel  .  add  (  circuitScrollPane  ,  java  .  awt  .  BorderLayout  .  SOUTH  )  ; 
centerPanel  .  add  (  constructPanel  ,  java  .  awt  .  BorderLayout  .  NORTH  )  ; 
registerPanel  .  setLayout  (  new   java  .  awt  .  BorderLayout  (  )  )  ; 
registerPanel  .  setBorder  (  new   javax  .  swing  .  border  .  TitledBorder  (  "Register Basis States"  )  )  ; 
xScrollPane  .  setPreferredSize  (  new   java  .  awt  .  Dimension  (  780  ,  187  )  )  ; 
registerPanel  .  add  (  xScrollPane  ,  java  .  awt  .  BorderLayout  .  NORTH  )  ; 
yScrollPane  .  setPreferredSize  (  new   java  .  awt  .  Dimension  (  800  ,  107  )  )  ; 
registerPanel  .  add  (  yScrollPane  ,  java  .  awt  .  BorderLayout  .  CENTER  )  ; 
centerPanel  .  add  (  registerPanel  ,  java  .  awt  .  BorderLayout  .  CENTER  )  ; 
getContentPane  (  )  .  add  (  centerPanel  ,  java  .  awt  .  BorderLayout  .  CENTER  )  ; 
fileMenu  .  setText  (  "File"  )  ; 
loadMenuItem  .  setText  (  "Load Circuit"  )  ; 
loadMenuItem  .  addActionListener  (  new   java  .  awt  .  event  .  ActionListener  (  )  { 

public   void   actionPerformed  (  java  .  awt  .  event  .  ActionEvent   evt  )  { 
loadMenuItemActionPerformed  (  evt  )  ; 
} 
}  )  ; 
fileMenu  .  add  (  loadMenuItem  )  ; 
saveMenuItem  .  setText  (  "Save Circuit"  )  ; 
saveMenuItem  .  addActionListener  (  new   java  .  awt  .  event  .  ActionListener  (  )  { 

public   void   actionPerformed  (  java  .  awt  .  event  .  ActionEvent   evt  )  { 
saveMenuItemActionPerformed  (  evt  )  ; 
} 
}  )  ; 
fileMenu  .  add  (  saveMenuItem  )  ; 
fileMenu  .  add  (  jSeparator5  )  ; 
exitMenuItem  .  setText  (  "Quit"  )  ; 
exitMenuItem  .  addActionListener  (  new   java  .  awt  .  event  .  ActionListener  (  )  { 

public   void   actionPerformed  (  java  .  awt  .  event  .  ActionEvent   evt  )  { 
exitMenuItemActionPerformed  (  evt  )  ; 
} 
}  )  ; 
fileMenu  .  add  (  exitMenuItem  )  ; 
jMenuBar  .  add  (  fileMenu  )  ; 
configMenu  .  setText  (  "Configuration"  )  ; 
coloringMenu  .  setText  (  "qubit coloring"  )  ; 
coloringMenu  .  setToolTipText  (  "qubit states color brightness depends on its length"  )  ; 
lengthColoringItem  .  setText  (  "length coloring"  )  ; 
coloringButtonGroup  .  add  (  lengthColoringItem  )  ; 
lengthColoringItem  .  addActionListener  (  new   java  .  awt  .  event  .  ActionListener  (  )  { 

public   void   actionPerformed  (  java  .  awt  .  event  .  ActionEvent   evt  )  { 
lengthColoringItemActionPerformed  (  evt  )  ; 
} 
}  )  ; 
coloringMenu  .  add  (  lengthColoringItem  )  ; 
noLengthColoringItem  .  setSelected  (  true  )  ; 
noLengthColoringItem  .  setText  (  "no length coloring"  )  ; 
coloringButtonGroup  .  add  (  noLengthColoringItem  )  ; 
noLengthColoringItem  .  addActionListener  (  new   java  .  awt  .  event  .  ActionListener  (  )  { 

public   void   actionPerformed  (  java  .  awt  .  event  .  ActionEvent   evt  )  { 
noLengthColoringItemActionPerformed  (  evt  )  ; 
} 
}  )  ; 
coloringMenu  .  add  (  noLengthColoringItem  )  ; 
configMenu  .  add  (  coloringMenu  )  ; 
jMenuBar  .  add  (  configMenu  )  ; 
helpMenu  .  setText  (  "Help"  )  ; 
helpMenu  .  setHorizontalAlignment  (  javax  .  swing  .  SwingConstants  .  RIGHT  )  ; 
helpMenu  .  setHorizontalTextPosition  (  javax  .  swing  .  SwingConstants  .  RIGHT  )  ; 
aboutMenuItem  .  setText  (  "About"  )  ; 
aboutMenuItem  .  addActionListener  (  new   java  .  awt  .  event  .  ActionListener  (  )  { 

public   void   actionPerformed  (  java  .  awt  .  event  .  ActionEvent   evt  )  { 
aboutMenuItemActionPerformed  (  evt  )  ; 
} 
}  )  ; 
helpMenu  .  add  (  aboutMenuItem  )  ; 
helpMenuItem  .  setText  (  "Help on jQuantum"  )  ; 
helpMenuItem  .  addActionListener  (  new   java  .  awt  .  event  .  ActionListener  (  )  { 

public   void   actionPerformed  (  java  .  awt  .  event  .  ActionEvent   evt  )  { 
helpMenuItemActionPerformed  (  evt  )  ; 
} 
}  )  ; 
helpMenu  .  add  (  helpMenuItem  )  ; 
jMenuBar  .  add  (  helpMenu  )  ; 
setJMenuBar  (  jMenuBar  )  ; 
pack  (  )  ; 
} 

private   void   aboutMenuItemActionPerformed  (  java  .  awt  .  event  .  ActionEvent   evt  )  { 
String   title  ; 
String   output  =  "<html><h3>"  ; 
output  +=  version  ; 
output  +=  "</h3>"  ; 
output  +=  "<p>"  ; 
if  (  language  .  equals  (  Locale  .  GERMAN  .  toString  (  )  )  )  { 
title  =  "Über das Programm"  ; 
output  +=  "Simulation eines Quantenrechners."  ; 
}  else  { 
title  =  "About"  ; 
output  +=  "Simulation of a quantum computer."  ; 
} 
output  +=  "</p>"  ; 
output  +=  "<br>"  ; 
output  +=  copyright  ; 
output  +=  "</html>"  ; 
new   InfoFrame  (  output  ,  title  ,  250  ,  150  )  ; 
} 

private   void   helpMenuItemActionPerformed  (  java  .  awt  .  event  .  ActionEvent   evt  )  { 
int   width  =  700  ,  height  =  500  ; 
String   title  ; 
String   output  =  "<html>"  ; 
if  (  language  .  equals  (  Locale  .  GERMAN  .  toString  (  )  )  )  { 
width  =  700  ; 
height  =  600  ; 
title  =  "Kurzanleitung"  ; 
output  +=  "<h3>1. Struktur der Arbeitsfl&auml;che</h3>"  ; 
output  +=  "<p>"  ; 
output  +=  " Links befindet sich die Steuerleiste, in der"  ; 
output  +=  " die Schaltkn&ouml;pfe zur Initialisierung der Quantenschaltung"  ; 
output  +=  " und zur Durchf&uuml;hrung des Quantenprogramms eingerichtet sind."  ; 
output  +=  " Die obere horizontale Leiste des Fensters besteht aus Schaltkn&ouml;pfen,"  ; 
output  +=  " mit denen Quantengatter zum Quantenschaltkreis hinzugef&uuml;gt werden k&ouml;nnen."  ; 
output  +=  " Das Entwurfsbrett f&uuml;r die Schaltkreise befindet sich direkt darunter."  ; 
output  +=  " Im unteren Fensterbereich ist der aktuelle Zustand des Quantenregisters"  ; 
output  +=  " abgebildet. Die Darstellung ist unterteilt in das <i>x</i>-Register,"  ; 
output  +=  " das die Eingabe empf&auml;ngt und die Ausgabe liefert, sowie das"  ; 
output  +=  " <i>y</i>-Register, das haupts&auml;chlich als Zwischenspeicher f&uuml;r Qubits dient,"  ; 
output  +=  " wie z.B. f&uuml;r Algorithmen nach dem Shor'schen Prinzip der Funktionsauswertung."  ; 
output  +=  "</p><br>"  ; 
output  +=  "<h3>2. Initialisierung des Quantenregisters</h3>"  ; 
output  +=  "<p>"  ; 
output  +=  " Das Quantenregister wird mit der Schaltfl&auml;che <span style=\"color:#009D00\"><i>&#968;</i><sub>0</sub></span>"  ; 
output  +=  " eingerichtet, indem die Anzahl der Qubits f&uuml;r jedes der Register bestimmt wird."  ; 
output  +=  " Ein Qubit-Register der Gr&ouml;&szlig;e <i>n</i> wird bestimmt durch 2<sup><i>n</i></sup>"  ; 
output  +=  " Basiszust&auml;nde. In der Registerfl&auml;che ist jeder dieser Basiszust&auml;nde"  ; 
output  +=  " durch ein einzelnes Quadrat dargestellt, dessen F&auml;rbung der"  ; 
output  +=  " (komplexen) Amplitude des Basiszustands entspricht, siehe die Dokumentation."  ; 
output  +=  "</p><br>"  ; 
output  +=  "<h3>3. Entwurf der Quantenschaltung</h3>"  ; 
output  +=  "<p>"  ; 
output  +=  " Nach der Initialisierung des Quantenregisters k&ouml;nnen Sie die Quantengatter"  ; 
output  +=  " durch Anklicken der Schaltkn&ouml;pfe in der Entwurfsleiste in die Quantenschaltung"  ; 
output  +=  " einbauen. Normalerweise werden Sie durch ein Dialogfenster nach notwendigen Zusatzinformationen"  ; 
output  +=  " f&uuml;r das jeweilige Gatter befragt."  ; 
output  +=  " Das jeweils zuletzt eingef&uuml;gte Gatter kann durch die L&ouml;schtaste ganz rechts"  ; 
output  +=  " wieder entfernt werden."  ; 
output  +=  "</p><br>"  ; 
output  +=  "<h3>4. Ausf&uuml;hrung des Algorithmus</h3>"  ; 
output  +=  "<p>"  ; 
output  +=  " Gestartet und ausgef&uuml;hrt wird der Quantenalgorithmus durch die"  ; 
output  +=  " Schaltkn&ouml;pfe in der Steuerleiste links. Es gibt zwei Ausf&uuml;hrungsweisen:"  ; 
output  +=  " einerseits kann man den Algorithmus schrittweise ausf&uuml;hren mit den"  ; 
output  +=  " beiden oberen \"blockierten\" Pfeiltasten, andererseits kann man ihn in einem"  ; 
output  +=  " in einem vollst&auml;ndigen Durchlauf ausf&uuml;hren mit der unteren Pfeiltaste."  ; 
output  +=  "</p>"  ; 
}  else  { 
title  =  "Short User's Manual"  ; 
output  +=  "<h3>1. Structure of the work space window</h3>"  ; 
output  +=  "<p>"  ; 
output  +=  " On the lleft, you find the control panel, in which there are"  ; 
output  +=  " the buttons to initialize and to control the run of the quantum program."  ; 
output  +=  " The upper horizontal panel consists of buttons to add quantum gates"  ; 
output  +=  " to the circuit canvas directly below it."  ; 
output  +=  " At the bottom you find the representation of the quantum register."  ; 
output  +=  " It is divided into a part which receives the input and supplies the output,"  ; 
output  +=  " called the <i>x</i>-register, and into a part called <i>y</i>-register,"  ; 
output  +=  " which serves mainly as a temporary qubit storage, as e.g. for Shor's"  ; 
output  +=  " function evaluation principle."  ; 
output  +=  "</p><br>"  ; 
output  +=  "<h3>2. Initializing the quantum register</h3>"  ; 
output  +=  "<p>"  ; 
output  +=  " To determine the size of the register, first click on the button"  ; 
output  +=  " <span style=\"color:#009D00\"><i>&#968;</i><sub>0</sub></span>."  ; 
output  +=  " A qubit register of size <i>n</i> is determined by 2<sup><i>n</i></sup>"  ; 
output  +=  " base states. In the register panel, each basis state is represented"  ; 
output  +=  " as a single square whose color specifies the (complex) amplitude"  ; 
output  +=  " of the base state, cf. the documentation."  ; 
output  +=  "</p><br>"  ; 
output  +=  "<h3>3. Designing the quantum circuit</h3>"  ; 
output  +=  "<p>"  ; 
output  +=  " Having initialized the quantum register, you can add several quantum gates to the circuit"  ; 
output  +=  " by simply clicking on the buttons in the circuit"  ; 
output  +=  " design panel. Usually you are prompted for further necessary information."  ; 
output  +=  " The currently last added gate can be deleted by using the button on the right-most position."  ; 
output  +=  "</p><br>"  ; 
output  +=  "<h3>4. Running the algorithm</h3>"  ; 
output  +=  "<p>"  ; 
output  +=  " You can run the algorithm in two modes. Either you can go stepwise"  ; 
output  +=  " by clicking the two \"blocked\" arrow buttons in the control panel, or you can perform a single whole-run"  ; 
output  +=  " by clicking the green arrow lower-most."  ; 
output  +=  "</p>"  ; 
} 
output  +=  "</html>"  ; 
new   InfoFrame  (  output  ,  title  ,  width  ,  height  )  ; 
} 

private   void   noLengthColoringItemActionPerformed  (  java  .  awt  .  event  .  ActionEvent   evt  )  { 
lengthColoring  =  false  ; 
xPanel  .  setLengthColoring  (  lengthColoring  )  ; 
xPanel  .  repaint  (  )  ; 
yPanel  .  setLengthColoring  (  lengthColoring  )  ; 
yPanel  .  repaint  (  )  ; 
} 

private   void   lengthColoringItemActionPerformed  (  java  .  awt  .  event  .  ActionEvent   evt  )  { 
lengthColoring  =  true  ; 
xPanel  .  setLengthColoring  (  lengthColoring  )  ; 
xPanel  .  repaint  (  )  ; 
yPanel  .  setLengthColoring  (  lengthColoring  )  ; 
yPanel  .  repaint  (  )  ; 
} 

private   void   saveMenuItemActionPerformed  (  java  .  awt  .  event  .  ActionEvent   evt  )  { 
JFileChooser   fileChooser  =  new   JFileChooser  (  )  ; 
ExampleFileFilter   filter  =  new   ExampleFileFilter  (  new   String  [  ]  {  "qc"  }  ,  "quantum circuits"  )  ; 
fileChooser  .  addChoosableFileFilter  (  filter  )  ; 
int   returnVal  =  fileChooser  .  showSaveDialog  (  this  )  ; 
if  (  returnVal  ==  JFileChooser  .  APPROVE_OPTION  )  { 
File   file  =  fileChooser  .  getSelectedFile  (  )  ; 
ObjectOutputStream   output  =  null  ; 
try  { 
output  =  new   ObjectOutputStream  (  new   FileOutputStream  (  file  )  )  ; 
try  { 
int  [  ]  size  =  {  xRegister  .  size  ,  yRegister  .  size  }  ; 
output  .  writeObject  (  size  )  ; 
output  .  writeObject  (  circuitPanel  .  gates  )  ; 
output  .  flush  (  )  ; 
}  catch  (  EOFException   eof  )  { 
}  catch  (  IOException   ioe  )  { 
ioe  .  printStackTrace  (  )  ; 
} 
}  catch  (  IOException   ioe  )  { 
ioe  .  printStackTrace  (  )  ; 
}  finally  { 
try  { 
if  (  output  !=  null  )  output  .  close  (  )  ; 
}  catch  (  IOException   ioe  )  { 
ioe  .  printStackTrace  (  )  ; 
} 
} 
} 
} 

private   void   loadMenuItemActionPerformed  (  java  .  awt  .  event  .  ActionEvent   evt  )  { 
JFileChooser   fileChooser  =  new   JFileChooser  (  )  ; 
ExampleFileFilter   filter  =  new   ExampleFileFilter  (  new   String  [  ]  {  "qc"  }  ,  "quantum circuits"  )  ; 
fileChooser  .  addChoosableFileFilter  (  filter  )  ; 
int   returnVal  =  fileChooser  .  showOpenDialog  (  this  )  ; 
if  (  returnVal  ==  JFileChooser  .  APPROVE_OPTION  )  { 
File   file  =  fileChooser  .  getSelectedFile  (  )  ; 
ObjectInputStream   input  =  null  ; 
try  { 
input  =  new   ObjectInputStream  (  new   FileInputStream  (  file  )  )  ; 
try  { 
int  [  ]  size  =  (  int  [  ]  )  input  .  readObject  (  )  ; 
circuitPanel  .  setWires  (  size  [  0  ]  ,  size  [  1  ]  )  ; 
circuitPanel  .  gates  =  (  ArrayList  )  input  .  readObject  (  )  ; 
initialQubits  =  (  (  QuantumGate  )  circuitPanel  .  gates  .  get  (  0  )  )  .  qubits  ; 
xRegisterSize  =  size  [  0  ]  ; 
yRegisterSize  =  size  [  1  ]  ; 
xRegister  =  new   Register  (  xRegisterSize  )  ; 
yRegister  =  new   Register  (  yRegisterSize  )  ; 
int   initialState  =  0  ; 
for  (  int   i  =  0  ;  i  <  xRegisterSize  ;  i  ++  )  { 
int   k  =  xRegisterSize  -  i  -  1  ; 
initialState  +=  initialQubits  [  k  ]  *  (  1  <<  i  )  ; 
} 
double  [  ]  tmp  =  new   double  [  1  <<  xRegisterSize  ]  ; 
tmp  [  initialState  ]  =  1  ; 
xRegister  .  real  =  tmp  ; 
initialState  =  0  ; 
for  (  int   i  =  0  ;  i  <  yRegisterSize  ;  i  ++  )  { 
int   k  =  xRegisterSize  +  yRegisterSize  -  i  -  1  ; 
initialState  +=  initialQubits  [  k  ]  *  (  1  <<  i  )  ; 
} 
tmp  =  new   double  [  1  <<  yRegisterSize  ]  ; 
tmp  [  initialState  ]  =  1  ; 
yRegister  .  real  =  tmp  ; 
circuitPanel  .  repaint  (  )  ; 
xPanel  .  setLengthColoring  (  lengthColoring  )  ; 
yPanel  .  setLengthColoring  (  lengthColoring  )  ; 
xPanel  .  setQubitStates  (  xRegister  .  real  ,  xRegister  .  imaginary  )  ; 
yPanel  .  setQubitStates  (  yRegister  .  real  ,  yRegister  .  imaginary  )  ; 
}  catch  (  ClassNotFoundException   cnf  )  { 
String   title  =  "File Error"  ; 
String   message  =  "The file "  +  file  .  getName  (  )  +  " does not contain a circuit!"  ; 
JOptionPane  .  showMessageDialog  (  this  ,  message  ,  title  ,  JOptionPane  .  ERROR_MESSAGE  )  ; 
}  catch  (  ClassCastException   cce  )  { 
String   title  =  "File Error"  ; 
String   message  =  "The file "  +  file  .  getName  (  )  +  " does not contain a circuit or has the wrong format!"  ; 
JOptionPane  .  showMessageDialog  (  this  ,  message  ,  title  ,  JOptionPane  .  ERROR_MESSAGE  )  ; 
}  catch  (  EOFException   eof  )  { 
}  catch  (  IOException   ioe  )  { 
ioe  .  printStackTrace  (  )  ; 
} 
}  catch  (  IOException   ioe  )  { 
ioe  .  printStackTrace  (  )  ; 
}  finally  { 
try  { 
if  (  input  !=  null  )  input  .  close  (  )  ; 
}  catch  (  IOException   ioe  )  { 
ioe  .  printStackTrace  (  )  ; 
} 
} 
} 
} 

private   void   exitMenuItemActionPerformed  (  java  .  awt  .  event  .  ActionEvent   evt  )  { 
dispose  (  )  ; 
try  { 
System  .  exit  (  0  )  ; 
}  catch  (  Exception   e  )  { 
} 
} 

private   void   rotationButtonActionPerformed  (  java  .  awt  .  event  .  ActionEvent   evt  )  { 
RotationDialog   dialog  =  new   RotationDialog  (  this  ,  true  ,  language  )  ; 
dialog  .  setVisible  (  true  )  ; 
if  (  dialog  .  cancelButtonClicked  )  return  ; 
try  { 
circuitPanel  .  addRotation  (  dialog  .  qubits  ,  dialog  .  yRegisterChosen  ,  dialog  .  axis  ,  dialog  .  phiAsPartOfPi  )  ; 
}  catch  (  Exception   e  )  { 
e  .  printStackTrace  (  )  ; 
}  finally  { 
dialog  .  removeAll  (  )  ; 
dialog  .  dispose  (  )  ; 
} 
} 

private   void   U_fButtonActionPerformed  (  java  .  awt  .  event  .  ActionEvent   evt  )  { 
FunctionDialog   dialog  =  new   FunctionDialog  (  this  ,  true  ,  language  )  ; 
dialog  .  setVisible  (  true  )  ; 
if  (  dialog  .  cancelButtonClicked  )  return  ; 
try  { 
circuitPanel  .  addFunction  (  dialog  .  function  )  ; 
}  catch  (  Exception   e  )  { 
e  .  printStackTrace  (  )  ; 
}  finally  { 
dialog  .  removeAll  (  )  ; 
dialog  .  dispose  (  )  ; 
} 
} 

private   void   iniQubitButtonActionPerformed  (  java  .  awt  .  event  .  ActionEvent   evt  )  { 
if  (  xRegisterSize  +  yRegisterSize  >  0  )  { 
if  (  initialQubits  ==  null  )  initialQubits  =  new   int  [  xRegisterSize  +  yRegisterSize  ]  ; 
InitialStateDialog   dialog  =  new   InitialStateDialog  (  this  ,  true  ,  language  ,  initialQubits  ,  xRegisterSize  )  ; 
dialog  .  setVisible  (  true  )  ; 
if  (  dialog  .  cancelButtonClicked  )  return  ; 
try  { 
initialQubits  =  dialog  .  getQubits  (  )  ; 
int   initialState  =  0  ; 
for  (  int   i  =  0  ;  i  <  xRegisterSize  ;  i  ++  )  { 
int   k  =  xRegisterSize  -  i  -  1  ; 
initialState  +=  initialQubits  [  k  ]  *  (  1  <<  i  )  ; 
} 
double  [  ]  tmp  =  new   double  [  1  <<  xRegisterSize  ]  ; 
tmp  [  initialState  ]  =  1  ; 
xRegister  .  real  =  tmp  ; 
xRegister  .  imaginary  =  new   double  [  1  <<  xRegisterSize  ]  ; 
initialState  =  0  ; 
for  (  int   i  =  0  ;  i  <  yRegisterSize  ;  i  ++  )  { 
int   k  =  xRegisterSize  +  yRegisterSize  -  i  -  1  ; 
initialState  +=  initialQubits  [  k  ]  *  (  1  <<  i  )  ; 
} 
tmp  =  new   double  [  1  <<  yRegisterSize  ]  ; 
tmp  [  initialState  ]  =  1  ; 
yRegister  .  real  =  tmp  ; 
yRegister  .  imaginary  =  new   double  [  1  <<  yRegisterSize  ]  ; 
circuitPanel  .  setInitialState  (  initialQubits  )  ; 
xPanel  .  setQubitStates  (  xRegister  .  real  ,  xRegister  .  imaginary  )  ; 
yPanel  .  setQubitStates  (  yRegister  .  real  ,  yRegister  .  imaginary  )  ; 
}  catch  (  Exception   e  )  { 
e  .  printStackTrace  (  )  ; 
}  finally  { 
dialog  .  removeAll  (  )  ; 
dialog  .  dispose  (  )  ; 
} 
} 
} 

private   void   prevButtonActionPerformed  (  java  .  awt  .  event  .  ActionEvent   evt  )  { 
try  { 
if  (  circuitPanel  .  getActualGate  (  )  >  0  )  { 
QuantumGate   gate  =  (  QuantumGate  )  circuitPanel  .  gates  .  get  (  circuitPanel  .  getActualGate  (  )  -  1  )  ; 
unperform  (  gate  )  ; 
circuitPanel  .  repaint  (  )  ; 
xPanel  .  setQubitStates  (  xRegister  .  real  ,  xRegister  .  imaginary  )  ; 
yPanel  .  setQubitStates  (  yRegister  .  real  ,  yRegister  .  imaginary  )  ; 
} 
}  catch  (  Exception   e  )  { 
e  .  printStackTrace  (  )  ; 
} 
} 

private   void   nextButtonActionPerformed  (  java  .  awt  .  event  .  ActionEvent   evt  )  { 
try  { 
QuantumGate   gate  =  (  QuantumGate  )  circuitPanel  .  gates  .  get  (  circuitPanel  .  getActualGate  (  )  )  ; 
circuitPanel  .  setNextStep  (  )  ; 
perform  (  gate  )  ; 
circuitPanel  .  repaint  (  )  ; 
xPanel  .  setQubitStates  (  xRegister  .  real  ,  xRegister  .  imaginary  )  ; 
yPanel  .  setQubitStates  (  yRegister  .  real  ,  yRegister  .  imaginary  )  ; 
}  catch  (  Exception   e  )  { 
} 
} 

private   void   deleteButtonActionPerformed  (  java  .  awt  .  event  .  ActionEvent   evt  )  { 
ArrayList   gates  =  circuitPanel  .  gates  ; 
if  (  gates  !=  null  )  { 
if  (  gates  .  size  (  )  >  0  )  { 
gates  .  remove  (  circuitPanel  .  gates  .  size  (  )  -  1  )  ; 
circuitPanel  .  repaint  (  )  ; 
} 
} 
} 

private   void   iniButtonActionPerformed  (  java  .  awt  .  event  .  ActionEvent   evt  )  { 
InitDialog   dialog  =  new   InitDialog  (  this  ,  true  ,  language  )  ; 
dialog  .  setVisible  (  true  )  ; 
if  (  dialog  .  cancelButtonClicked  )  return  ; 
int  [  ]  values  =  dialog  .  getValues  (  )  ; 
xRegisterSize  =  values  [  0  ]  ; 
yRegisterSize  =  values  [  1  ]  ; 
int   initialState  =  values  [  2  ]  ; 
if  (  xRegisterSize  +  yRegisterSize  >  0  )  { 
xRegister  =  new   Register  (  xRegisterSize  )  ; 
yRegister  =  new   Register  (  yRegisterSize  )  ; 
xRegister  .  real  [  0  ]  =  0  ; 
xRegister  .  real  [  initialState  ]  =  1  ; 
xPanel  .  setLengthColoring  (  lengthColoring  )  ; 
yPanel  .  setLengthColoring  (  lengthColoring  )  ; 
initialQubits  =  new   int  [  xRegisterSize  +  yRegisterSize  ]  ; 
int   j  =  1  ; 
while  (  initialState  >  0  )  { 
initialQubits  [  xRegisterSize  -  j  ]  =  initialState  %  2  ; 
initialState  /=  2  ; 
j  ++  ; 
} 
circuitPanel  .  setWires  (  xRegisterSize  ,  yRegisterSize  )  ; 
circuitPanel  .  setInitialState  (  initialQubits  )  ; 
xPanel  .  setQubitStates  (  xRegister  .  real  ,  xRegister  .  imaginary  )  ; 
yPanel  .  setQubitStates  (  yRegister  .  real  ,  yRegister  .  imaginary  )  ; 
} 
dialog  .  dispose  (  )  ; 
circuitPanel  .  repaint  (  )  ; 
} 

private   void   goButtonActionPerformed  (  java  .  awt  .  event  .  ActionEvent   evt  )  { 
try  { 
int   initialState  =  0  ; 
for  (  int   i  =  0  ;  i  <  xRegisterSize  ;  i  ++  )  { 
int   k  =  xRegisterSize  -  i  -  1  ; 
initialState  +=  initialQubits  [  k  ]  *  (  1  <<  i  )  ; 
} 
double  [  ]  tmp  =  new   double  [  1  <<  xRegisterSize  ]  ; 
tmp  [  initialState  ]  =  1  ; 
xRegister  .  real  =  tmp  ; 
initialState  =  0  ; 
for  (  int   i  =  0  ;  i  <  yRegisterSize  ;  i  ++  )  { 
int   k  =  xRegisterSize  +  yRegisterSize  -  i  -  1  ; 
initialState  +=  initialQubits  [  k  ]  *  (  1  <<  i  )  ; 
} 
tmp  =  new   double  [  1  <<  yRegisterSize  ]  ; 
tmp  [  initialState  ]  =  1  ; 
yRegister  .  real  =  tmp  ; 
circuitPanel  .  setFirstStep  (  )  ; 
circuitPanel  .  setInitialState  (  initialQubits  )  ; 
circuitPanel  .  repaint  (  )  ; 
xPanel  .  setQubitStates  (  xRegister  .  real  ,  xRegister  .  imaginary  )  ; 
yPanel  .  setQubitStates  (  yRegister  .  real  ,  yRegister  .  imaginary  )  ; 
for  (  Iterator   i  =  circuitPanel  .  gates  .  iterator  (  )  ;  i  .  hasNext  (  )  ;  )  { 
perform  (  (  QuantumGate  )  i  .  next  (  )  )  ; 
} 
circuitPanel  .  setFinalStep  (  )  ; 
}  catch  (  Exception   e  )  { 
e  .  getMessage  (  )  ; 
} 
xPanel  .  setQubitStates  (  xRegister  .  real  ,  xRegister  .  imaginary  )  ; 
yPanel  .  setQubitStates  (  yRegister  .  real  ,  yRegister  .  imaginary  )  ; 
} 

private   void   measureButtonActionPerformed  (  java  .  awt  .  event  .  ActionEvent   evt  )  { 
MeasureDialog   dialog  =  new   MeasureDialog  (  this  ,  true  ,  language  ,  xRegisterSize  ,  yRegisterSize  )  ; 
if  (  dialog  .  cancelButtonClicked  )  return  ; 
dialog  .  setVisible  (  true  )  ; 
if  (  dialog  .  cancelButtonClicked  )  return  ; 
int  [  ]  qubits  ; 
try  { 
if  (  dialog  .  xRegisterChosen  )  { 
qubits  =  new   int  [  xRegisterSize  ]  ; 
for  (  int   i  =  0  ;  i  <  xRegisterSize  ;  i  ++  )  { 
qubits  [  i  ]  =  i  +  1  ; 
} 
circuitPanel  .  addMeasurement  (  qubits  ,  dialog  .  yRegisterChosen  )  ; 
}  else   if  (  dialog  .  yRegisterChosen  )  { 
qubits  =  new   int  [  yRegisterSize  ]  ; 
for  (  int   i  =  0  ;  i  <  yRegisterSize  ;  i  ++  )  { 
qubits  [  i  ]  =  i  +  1  ; 
} 
circuitPanel  .  addMeasurement  (  qubits  ,  dialog  .  yRegisterChosen  )  ; 
}  else   if  (  dialog  .  qubitChosen  )  { 
qubits  =  new   int  [  1  ]  ; 
qubits  [  0  ]  =  dialog  .  qubit  ; 
circuitPanel  .  addMeasurement  (  qubits  ,  dialog  .  yQubitChosen  )  ; 
} 
}  catch  (  Exception   e  )  { 
e  .  printStackTrace  (  )  ; 
}  finally  { 
dialog  .  removeAll  (  )  ; 
dialog  .  dispose  (  )  ; 
} 
} 

private   void   toffoliButtonActionPerformed  (  java  .  awt  .  event  .  ActionEvent   evt  )  { 
GateDialog   dialog  =  new   GateDialog  (  this  ,  true  ,  language  ,  "Toffoli"  ,  xRegisterSize  ,  yRegisterSize  )  ; 
dialog  .  setVisible  (  true  )  ; 
if  (  dialog  .  cancelButtonClicked  )  return  ; 
try  { 
int  [  ]  qubits  =  dialog  .  getQubits  (  )  ; 
circuitPanel  .  addToffoli  (  qubits  ,  dialog  .  yRegisterChosen  )  ; 
}  catch  (  Exception   e  )  { 
e  .  printStackTrace  (  )  ; 
}  finally  { 
dialog  .  removeAll  (  )  ; 
dialog  .  dispose  (  )  ; 
} 
} 

private   void   invQftButtonActionPerformed  (  java  .  awt  .  event  .  ActionEvent   evt  )  { 
GateDialog   dialog  =  new   GateDialog  (  this  ,  true  ,  language  ,  "invQFT"  ,  xRegisterSize  ,  yRegisterSize  )  ; 
dialog  .  setVisible  (  true  )  ; 
if  (  dialog  .  cancelButtonClicked  )  return  ; 
try  { 
circuitPanel  .  addInvQFT  (  dialog  .  yRegisterChosen  )  ; 
}  catch  (  Exception   e  )  { 
e  .  printStackTrace  (  )  ; 
}  finally  { 
dialog  .  removeAll  (  )  ; 
dialog  .  dispose  (  )  ; 
} 
} 

private   void   qftButtonActionPerformed  (  java  .  awt  .  event  .  ActionEvent   evt  )  { 
GateDialog   dialog  =  new   GateDialog  (  this  ,  true  ,  language  ,  "QFT"  ,  xRegisterSize  ,  yRegisterSize  )  ; 
dialog  .  setVisible  (  true  )  ; 
if  (  dialog  .  cancelButtonClicked  )  return  ; 
try  { 
circuitPanel  .  addQFT  (  dialog  .  yRegisterChosen  )  ; 
}  catch  (  Exception   e  )  { 
e  .  printStackTrace  (  )  ; 
}  finally  { 
dialog  .  removeAll  (  )  ; 
dialog  .  dispose  (  )  ; 
} 
} 

private   void   cNOTButtonActionPerformed  (  java  .  awt  .  event  .  ActionEvent   evt  )  { 
GateDialog   dialog  =  new   GateDialog  (  this  ,  true  ,  language  ,  "cNOT"  ,  xRegisterSize  ,  yRegisterSize  )  ; 
dialog  .  setVisible  (  true  )  ; 
if  (  dialog  .  cancelButtonClicked  )  return  ; 
try  { 
int  [  ]  qubits  =  dialog  .  getQubits  (  )  ; 
circuitPanel  .  addCNOT  (  qubits  ,  dialog  .  yRegisterChosen  )  ; 
}  catch  (  Exception   e  )  { 
e  .  printStackTrace  (  )  ; 
}  finally  { 
dialog  .  removeAll  (  )  ; 
dialog  .  dispose  (  )  ; 
} 
} 

private   void   hadamardButtonActionPerformed  (  java  .  awt  .  event  .  ActionEvent   evt  )  { 
GateDialog   dialog  =  new   GateDialog  (  this  ,  true  ,  language  ,  "Hadamard"  ,  xRegisterSize  ,  yRegisterSize  )  ; 
dialog  .  setVisible  (  true  )  ; 
if  (  dialog  .  cancelButtonClicked  )  return  ; 
try  { 
int   qubit  =  dialog  .  getQubits  (  )  [  0  ]  ; 
circuitPanel  .  addHadamard  (  qubit  ,  dialog  .  yRegisterChosen  )  ; 
}  catch  (  Exception   e  )  { 
e  .  printStackTrace  (  )  ; 
}  finally  { 
dialog  .  removeAll  (  )  ; 
dialog  .  dispose  (  )  ; 
} 
} 


private   void   exitForm  (  java  .  awt  .  event  .  WindowEvent   evt  )  { 
dispose  (  )  ; 
try  { 
System  .  exit  (  0  )  ; 
}  catch  (  Exception   e  )  { 
} 
} 

private   void   perform  (  QuantumGate   gate  )  { 
if  (  gate  .  name  .  equals  (  "hadamard"  )  )  { 
int   qubit  =  gate  .  qubits  [  0  ]  ; 
if  (  gate  .  yRegister  )  { 
yRegister  .  hadamard  (  qubit  )  ; 
}  else  { 
xRegister  .  hadamard  (  qubit  )  ; 
} 
}  else   if  (  gate  .  name  .  equals  (  "cNOT"  )  )  { 
if  (  gate  .  yRegister  )  { 
yRegister  .  cNOT  (  gate  .  qubits  [  0  ]  ,  gate  .  qubits  [  1  ]  )  ; 
}  else  { 
xRegister  .  cNOT  (  gate  .  qubits  [  0  ]  ,  gate  .  qubits  [  1  ]  )  ; 
} 
}  else   if  (  gate  .  name  .  equals  (  "Toffoli"  )  )  { 
if  (  gate  .  yRegister  )  { 
yRegister  .  toffoli  (  gate  .  qubits  [  0  ]  ,  gate  .  qubits  [  1  ]  ,  gate  .  qubits  [  2  ]  )  ; 
}  else  { 
xRegister  .  toffoli  (  gate  .  qubits  [  0  ]  ,  gate  .  qubits  [  1  ]  ,  gate  .  qubits  [  2  ]  )  ; 
} 
}  else   if  (  gate  .  name  .  equals  (  "QFT"  )  )  { 
if  (  gate  .  yRegister  )  { 
yRegister  .  qft  (  (  1  <<  yRegisterSize  )  ,  (  1  <<  yRegisterSize  )  )  ; 
}  else  { 
xRegister  .  qft  (  (  1  <<  xRegisterSize  )  ,  (  1  <<  xRegisterSize  )  )  ; 
} 
}  else   if  (  gate  .  name  .  equals  (  "invQFT"  )  )  { 
if  (  gate  .  yRegister  )  { 
yRegister  .  inverseQft  (  (  1  <<  yRegisterSize  )  ,  (  1  <<  yRegisterSize  )  )  ; 
}  else  { 
xRegister  .  inverseQft  (  (  1  <<  xRegisterSize  )  ,  (  1  <<  xRegisterSize  )  )  ; 
} 
}  else   if  (  gate  .  name  .  equals  (  "function"  )  )  { 
int   zMin  =  0  ; 
int   zMax  =  (  1  <<  (  yRegisterSize  -  1  )  )  ; 
int   z  =  (  int  )  (  zMin  +  (  zMax  -  zMin  )  *  Math  .  random  (  )  )  ; 
yRegister  =  xRegister  .  evaluateFunction  (  yRegister  ,  gate  .  function  ,  z  )  ; 
yRegisterSize  =  yRegister  .  size  ; 
entanglementXY  =  xRegister  .  entanglement  ; 
}  else   if  (  gate  .  name  .  equals  (  "rotation"  )  )  { 
double   phi  =  Math  .  PI  /  gate  .  phiAsPartOfPi  ; 
if  (  gate  .  yRegister  )  { 
yRegister  .  rotate  (  gate  .  qubits  ,  gate  .  axis  ,  phi  )  ; 
}  else  { 
xRegister  .  rotate  (  gate  .  qubits  ,  gate  .  axis  ,  phi  )  ; 
} 
}  else   if  (  gate  .  name  .  equals  (  "Measurement"  )  )  { 
int   value  ; 
if  (  gate  .  yRegister  )  { 
if  (  gate  .  qubits  .  length  ==  1  )  { 
value  =  yRegister  .  measure  (  gate  .  qubits  [  0  ]  )  ; 
}  else  { 
value  =  yRegister  .  measure  (  )  ; 
} 
double  [  ]  realTmp  =  new   double  [  xRegister  .  real  .  length  ]  ; 
double   squaredLength  =  0  ; 
for  (  Iterator   i  =  entanglementXY  [  value  ]  .  iterator  (  )  ;  i  .  hasNext  (  )  ;  )  { 
realTmp  [  (  (  Integer  )  i  .  next  (  )  )  .  intValue  (  )  ]  =  1  ; 
squaredLength  +=  1  ; 
} 
for  (  Iterator   i  =  entanglementXY  [  value  ]  .  iterator  (  )  ;  i  .  hasNext  (  )  ;  )  { 
int   index  =  (  (  Integer  )  i  .  next  (  )  )  .  intValue  (  )  ; 
realTmp  [  index  ]  =  realTmp  [  index  ]  /  Math  .  sqrt  (  squaredLength  )  ; 
} 
xRegister  .  real  =  realTmp  ; 
}  else  { 
if  (  gate  .  qubits  .  length  ==  1  )  { 
xRegister  .  measure  (  gate  .  qubits  [  0  ]  )  ; 
}  else  { 
xRegister  .  measure  (  )  ; 
} 
} 
} 
} 

private   void   unperform  (  QuantumGate   gate  )  { 
if  (  gate  .  name  .  equals  (  "hadamard"  )  )  { 
int   qubit  =  gate  .  qubits  [  0  ]  ; 
if  (  gate  .  yRegister  )  { 
yRegister  .  hadamard  (  qubit  )  ; 
}  else  { 
xRegister  .  hadamard  (  qubit  )  ; 
} 
circuitPanel  .  setPrevStep  (  )  ; 
}  else   if  (  gate  .  name  .  equals  (  "cNOT"  )  )  { 
if  (  gate  .  yRegister  )  { 
yRegister  .  cNOT  (  gate  .  qubits  [  0  ]  ,  gate  .  qubits  [  1  ]  )  ; 
}  else  { 
xRegister  .  cNOT  (  gate  .  qubits  [  0  ]  ,  gate  .  qubits  [  1  ]  )  ; 
} 
circuitPanel  .  setPrevStep  (  )  ; 
}  else   if  (  gate  .  name  .  equals  (  "Toffoli"  )  )  { 
if  (  gate  .  yRegister  )  { 
yRegister  .  toffoli  (  gate  .  qubits  [  0  ]  ,  gate  .  qubits  [  1  ]  ,  gate  .  qubits  [  2  ]  )  ; 
}  else  { 
xRegister  .  toffoli  (  gate  .  qubits  [  0  ]  ,  gate  .  qubits  [  1  ]  ,  gate  .  qubits  [  2  ]  )  ; 
} 
circuitPanel  .  setPrevStep  (  )  ; 
}  else   if  (  gate  .  name  .  equals  (  "QFT"  )  )  { 
if  (  gate  .  yRegister  )  { 
yRegister  .  inverseQft  (  (  1  <<  yRegisterSize  )  ,  (  1  <<  yRegisterSize  )  )  ; 
}  else  { 
xRegister  .  inverseQft  (  (  1  <<  xRegisterSize  )  ,  (  1  <<  xRegisterSize  )  )  ; 
} 
circuitPanel  .  setPrevStep  (  )  ; 
}  else   if  (  gate  .  name  .  equals  (  "invQFT"  )  )  { 
if  (  gate  .  yRegister  )  { 
yRegister  .  qft  (  (  1  <<  yRegisterSize  )  ,  (  1  <<  yRegisterSize  )  )  ; 
}  else  { 
xRegister  .  qft  (  (  1  <<  xRegisterSize  )  ,  (  1  <<  xRegisterSize  )  )  ; 
} 
circuitPanel  .  setPrevStep  (  )  ; 
}  else   if  (  gate  .  name  .  equals  (  "function"  )  )  { 
yRegister  .  real  =  new   double  [  (  1  <<  yRegisterSize  )  ]  ; 
yRegister  .  real  [  0  ]  =  1f  ; 
yRegister  .  imaginary  =  new   double  [  (  1  <<  yRegisterSize  )  ]  ; 
circuitPanel  .  setPrevStep  (  )  ; 
}  else   if  (  gate  .  name  .  equals  (  "rotation"  )  )  { 
double   phi  =  -  Math  .  PI  /  gate  .  phiAsPartOfPi  ; 
if  (  gate  .  yRegister  )  { 
yRegister  .  rotate  (  gate  .  qubits  ,  gate  .  axis  ,  phi  )  ; 
}  else  { 
xRegister  .  rotate  (  gate  .  qubits  ,  gate  .  axis  ,  phi  )  ; 
} 
circuitPanel  .  setPrevStep  (  )  ; 
}  else   if  (  gate  .  name  .  equals  (  "Measurement"  )  )  { 
JOptionPane  .  showMessageDialog  (  null  ,  "A measurement is not reversible!\n(Restart by initializing)"  )  ; 
} 
} 

private   void   toGerman  (  )  { 
controlPanel  .  setBorder  (  new   javax  .  swing  .  border  .  TitledBorder  (  "Steuerung"  )  )  ; 
iniButton  .  setToolTipText  (  "Initialisierung der Register"  )  ; 
nextButton  .  setToolTipText  (  "nächster Schritt"  )  ; 
prevButton  .  setToolTipText  (  "vorheriger Schritt"  )  ; 
goButton  .  setToolTipText  (  "Start der Quantenrechnung"  )  ; 
constructPanel  .  setBorder  (  new   javax  .  swing  .  border  .  TitledBorder  (  "Entwurfsbrett für die Quantenschaltung"  )  )  ; 
iniQubitButton  .  setToolTipText  (  "Initializierung der Qubits"  )  ; 
hadamardButton  .  setToolTipText  (  "Hadamard Gatter"  )  ; 
cNOTButton  .  setToolTipText  (  "c-NOT Gatter"  )  ; 
toffoliButton  .  setToolTipText  (  "Toffoli Gatter"  )  ; 
qftButton  .  setToolTipText  (  "Quanten-Fourier-Transformation"  )  ; 
invQftButton  .  setToolTipText  (  "inverse Quanten-Fourier-Transformation"  )  ; 
U_fButton  .  setToolTipText  (  "Funktionsauswertung"  )  ; 
rotationButton  .  setToolTipText  (  "Rotationsoperator"  )  ; 
measureButton  .  setToolTipText  (  "Messung"  )  ; 
deleteButton  .  setToolTipText  (  "Löschen letztes Gatter (auch: 'ALT-BACKSPACE')"  )  ; 
registerPanel  .  setBorder  (  new   javax  .  swing  .  border  .  TitledBorder  (  "Quantenregister Basiszustände"  )  )  ; 
fileMenu  .  setText  (  "Datei"  )  ; 
loadMenuItem  .  setText  (  "Schaltung laden"  )  ; 
saveMenuItem  .  setText  (  "Schaltung speichern"  )  ; 
exitMenuItem  .  setText  (  "Beenden"  )  ; 
configMenu  .  setText  (  "Konfiguration"  )  ; 
coloringMenu  .  setText  (  "Qubit Farbhelligkeit"  )  ; 
coloringMenu  .  setToolTipText  (  "Helligkeit des Qubitzustands hängt von seiner Länge ab"  )  ; 
lengthColoringItem  .  setText  (  "Helligkeitscodierung der Länge"  )  ; 
noLengthColoringItem  .  setText  (  "keine Helligkeitscodierung der Länge"  )  ; 
helpMenu  .  setText  (  "Hilfe"  )  ; 
aboutMenuItem  .  setText  (  "Über das Programm"  )  ; 
helpMenuItem  .  setText  (  "Hilfe für jQuantum"  )  ; 
} 




public   static   void   main  (  String   args  [  ]  )  { 
new   jQuantum  (  )  ; 
} 

private   javax  .  swing  .  JButton   U_fButton  ; 

private   javax  .  swing  .  JMenuItem   aboutMenuItem  ; 

private   javax  .  swing  .  JButton   cNOTButton  ; 

private   javax  .  swing  .  JPanel   centerPanel  ; 

private   javax  .  swing  .  JScrollPane   circuitScrollPane  ; 

private   javax  .  swing  .  ButtonGroup   coloringButtonGroup  ; 

private   javax  .  swing  .  JMenu   coloringMenu  ; 

private   javax  .  swing  .  JMenu   configMenu  ; 

private   javax  .  swing  .  JPanel   constructPanel  ; 

private   javax  .  swing  .  JPanel   controlPanel  ; 

private   javax  .  swing  .  JButton   deleteButton  ; 

private   javax  .  swing  .  JMenuItem   exitMenuItem  ; 

private   javax  .  swing  .  JMenu   fileMenu  ; 

private   javax  .  swing  .  JPanel   gatePanel  ; 

private   javax  .  swing  .  JButton   goButton  ; 

private   javax  .  swing  .  JButton   hadamardButton  ; 

private   javax  .  swing  .  JMenu   helpMenu  ; 

private   javax  .  swing  .  JMenuItem   helpMenuItem  ; 

private   javax  .  swing  .  JButton   iniButton  ; 

private   javax  .  swing  .  JButton   iniQubitButton  ; 

private   javax  .  swing  .  JButton   invQftButton  ; 

private   javax  .  swing  .  JMenuBar   jMenuBar  ; 

private   javax  .  swing  .  JSeparator   jSeparator0  ; 

private   javax  .  swing  .  JSeparator   jSeparator1  ; 

private   javax  .  swing  .  JSeparator   jSeparator2  ; 

private   javax  .  swing  .  JSeparator   jSeparator3  ; 

private   javax  .  swing  .  JSeparator   jSeparator4  ; 

private   javax  .  swing  .  JSeparator   jSeparator5  ; 

private   javax  .  swing  .  JCheckBoxMenuItem   lengthColoringItem  ; 

private   javax  .  swing  .  JMenuItem   loadMenuItem  ; 

private   javax  .  swing  .  JButton   measureButton  ; 

private   javax  .  swing  .  JButton   nextButton  ; 

private   javax  .  swing  .  JCheckBoxMenuItem   noLengthColoringItem  ; 

private   javax  .  swing  .  JButton   prevButton  ; 

private   javax  .  swing  .  JButton   qftButton  ; 

private   javax  .  swing  .  JPanel   registerPanel  ; 

private   javax  .  swing  .  JButton   rotationButton  ; 

private   javax  .  swing  .  JMenuItem   saveMenuItem  ; 

private   javax  .  swing  .  JButton   toffoliButton  ; 

private   javax  .  swing  .  JScrollPane   xScrollPane  ; 

private   javax  .  swing  .  JScrollPane   yScrollPane  ; 

private   CircuitPanel   circuitPanel  ; 

private   RegisterPanel   xPanel  ; 

private   RegisterPanel   yPanel  ; 
} 
