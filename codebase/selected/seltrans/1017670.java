package   raiser  .  gui  ; 

import   java  .  awt  .  event  .  *  ; 
import   java  .  util  .  *  ; 
import   javax  .  swing  .  JTable  ; 
import   javax  .  swing  .  event  .  TableModelEvent  ; 
import   javax  .  swing  .  table  .  *  ; 

public   class   TableSorter   extends   TableMap  { 

int   indexes  [  ]  ; 

Vector   sortingColumns  =  new   Vector  (  )  ; 

private   static   final   int   ORDER_UNSORTED  =  -  1  ; 

private   static   final   int   ORDER_DESCENDING  =  0  ; 

private   static   final   int   ORDER_ASCENDING  =  1  ; 

private   int   ascending  ; 

int   compares  ; 

public   TableSorter  (  )  { 
indexes  =  new   int  [  0  ]  ; 
this  .  ascending  =  true  ?  ORDER_ASCENDING  :  ORDER_DESCENDING  ; 
} 

public   TableSorter  (  TableModel   model  )  { 
setModel  (  model  )  ; 
} 

public   void   setModel  (  TableModel   model  )  { 
super  .  setModel  (  model  )  ; 
reallocateIndexes  (  )  ; 
} 

public   int   compareRowsByColumn  (  int   row1  ,  int   row2  ,  int   column  )  { 
Class   type  =  model  .  getColumnClass  (  column  )  ; 
TableModel   data  =  model  ; 
Object   o1  =  data  .  getValueAt  (  row1  ,  column  )  ; 
Object   o2  =  data  .  getValueAt  (  row2  ,  column  )  ; 
if  (  o1  ==  null  &&  o2  ==  null  )  { 
return   0  ; 
}  else   if  (  o1  ==  null  )  { 
return  -  1  ; 
}  else   if  (  o2  ==  null  )  { 
return   1  ; 
} 
if  (  type  .  getSuperclass  (  )  ==  java  .  lang  .  Number  .  class  )  { 
Number   n1  =  (  Number  )  data  .  getValueAt  (  row1  ,  column  )  ; 
double   d1  =  n1  .  doubleValue  (  )  ; 
Number   n2  =  (  Number  )  data  .  getValueAt  (  row2  ,  column  )  ; 
double   d2  =  n2  .  doubleValue  (  )  ; 
if  (  d1  <  d2  )  { 
return  -  1  ; 
}  else   if  (  d1  >  d2  )  { 
return   1  ; 
}  else  { 
return   0  ; 
} 
}  else   if  (  type  ==  java  .  util  .  Date  .  class  )  { 
Date   d1  =  (  Date  )  data  .  getValueAt  (  row1  ,  column  )  ; 
long   n1  =  d1  .  getTime  (  )  ; 
Date   d2  =  (  Date  )  data  .  getValueAt  (  row2  ,  column  )  ; 
long   n2  =  d2  .  getTime  (  )  ; 
if  (  n1  <  n2  )  { 
return  -  1  ; 
}  else   if  (  n1  >  n2  )  { 
return   1  ; 
}  else  { 
return   0  ; 
} 
}  else   if  (  type  ==  String  .  class  )  { 
String   s1  =  (  String  )  data  .  getValueAt  (  row1  ,  column  )  ; 
String   s2  =  (  String  )  data  .  getValueAt  (  row2  ,  column  )  ; 
int   result  =  s1  .  compareTo  (  s2  )  ; 
if  (  result  <  0  )  { 
return  -  1  ; 
}  else   if  (  result  >  0  )  { 
return   1  ; 
}  else  { 
return   0  ; 
} 
}  else   if  (  type  ==  Boolean  .  class  )  { 
Boolean   bool1  =  (  Boolean  )  data  .  getValueAt  (  row1  ,  column  )  ; 
boolean   b1  =  bool1  .  booleanValue  (  )  ; 
Boolean   bool2  =  (  Boolean  )  data  .  getValueAt  (  row2  ,  column  )  ; 
boolean   b2  =  bool2  .  booleanValue  (  )  ; 
if  (  b1  ==  b2  )  { 
return   0  ; 
}  else   if  (  b1  )  { 
return   1  ; 
}  else  { 
return  -  1  ; 
} 
}  else  { 
Object   v1  =  data  .  getValueAt  (  row1  ,  column  )  ; 
String   s1  =  v1  .  toString  (  )  ; 
Object   v2  =  data  .  getValueAt  (  row2  ,  column  )  ; 
String   s2  =  v2  .  toString  (  )  ; 
int   result  =  s1  .  compareTo  (  s2  )  ; 
if  (  result  <  0  )  { 
return  -  1  ; 
}  else   if  (  result  >  0  )  { 
return   1  ; 
}  else  { 
return   0  ; 
} 
} 
} 

public   int   compare  (  int   row1  ,  int   row2  )  { 
compares  ++  ; 
for  (  int   level  =  0  ;  level  <  sortingColumns  .  size  (  )  ;  level  ++  )  { 
Integer   column  =  (  Integer  )  sortingColumns  .  elementAt  (  level  )  ; 
int   result  =  compareRowsByColumn  (  row1  ,  row2  ,  column  .  intValue  (  )  )  ; 
if  (  result  !=  0  )  { 
return  (  ascending  ==  ORDER_ASCENDING  )  ?  result  :  -  result  ; 
} 
} 
return   0  ; 
} 

public   void   reallocateIndexes  (  )  { 
int   rowCount  =  model  .  getRowCount  (  )  ; 
indexes  =  new   int  [  rowCount  ]  ; 
for  (  int   row  =  0  ;  row  <  rowCount  ;  row  ++  )  { 
indexes  [  row  ]  =  row  ; 
} 
} 

public   void   tableChanged  (  TableModelEvent   e  )  { 
reallocateIndexes  (  )  ; 
super  .  tableChanged  (  e  )  ; 
} 

public   void   checkModel  (  )  { 
if  (  indexes  .  length  !=  model  .  getRowCount  (  )  )  { 
System  .  err  .  println  (  "Sorter not informed of a change in model."  )  ; 
} 
} 

public   void   sort  (  Object   sender  )  { 
checkModel  (  )  ; 
compares  =  0  ; 
shuttlesort  (  (  int  [  ]  )  indexes  .  clone  (  )  ,  indexes  ,  0  ,  indexes  .  length  )  ; 
} 

public   void   n2sort  (  )  { 
for  (  int   i  =  0  ;  i  <  getRowCount  (  )  ;  i  ++  )  { 
for  (  int   j  =  i  +  1  ;  j  <  getRowCount  (  )  ;  j  ++  )  { 
if  (  compare  (  indexes  [  i  ]  ,  indexes  [  j  ]  )  ==  -  1  )  { 
swap  (  i  ,  j  )  ; 
} 
} 
} 
} 

public   void   shuttlesort  (  int   from  [  ]  ,  int   to  [  ]  ,  int   low  ,  int   high  )  { 
if  (  high  -  low  <  2  )  { 
return  ; 
} 
int   middle  =  (  low  +  high  )  /  2  ; 
shuttlesort  (  to  ,  from  ,  low  ,  middle  )  ; 
shuttlesort  (  to  ,  from  ,  middle  ,  high  )  ; 
int   p  =  low  ; 
int   q  =  middle  ; 
if  (  high  -  low  >=  4  &&  compare  (  from  [  middle  -  1  ]  ,  from  [  middle  ]  )  <=  0  )  { 
for  (  int   i  =  low  ;  i  <  high  ;  i  ++  )  { 
to  [  i  ]  =  from  [  i  ]  ; 
} 
return  ; 
} 
for  (  int   i  =  low  ;  i  <  high  ;  i  ++  )  { 
if  (  q  >=  high  ||  (  p  <  middle  &&  compare  (  from  [  p  ]  ,  from  [  q  ]  )  <=  0  )  )  { 
to  [  i  ]  =  from  [  p  ++  ]  ; 
}  else  { 
to  [  i  ]  =  from  [  q  ++  ]  ; 
} 
} 
} 

public   void   swap  (  int   i  ,  int   j  )  { 
int   tmp  =  indexes  [  i  ]  ; 
indexes  [  i  ]  =  indexes  [  j  ]  ; 
indexes  [  j  ]  =  tmp  ; 
} 

public   Object   getValueAt  (  int   aRow  ,  int   aColumn  )  { 
checkModel  (  )  ; 
return   model  .  getValueAt  (  getRow  (  aRow  )  ,  aColumn  )  ; 
} 

private   int   getRow  (  int   aRow  )  { 
return   indexes  [  aRow  ]  ; 
} 

public   void   setValueAt  (  Object   aValue  ,  int   aRow  ,  int   aColumn  )  { 
checkModel  (  )  ; 
model  .  setValueAt  (  aValue  ,  indexes  [  aRow  ]  ,  aColumn  )  ; 
} 

public   void   sortByColumn  (  int   column  )  { 
sortByColumn  (  column  ,  true  )  ; 
} 

public   void   sortByColumn  (  int   column  ,  boolean   ascending  )  { 
this  .  ascending  =  ascending  ?  ORDER_ASCENDING  :  ORDER_DESCENDING  ; 
sortingColumns  .  removeAllElements  (  )  ; 
sortingColumns  .  addElement  (  new   Integer  (  column  )  )  ; 
sort  (  this  )  ; 
super  .  tableChanged  (  new   TableModelEvent  (  this  )  )  ; 
} 

public   void   unsortByColumn  (  int   column  )  { 
sortingColumns  .  removeAllElements  (  )  ; 
sortingColumns  .  addElement  (  new   Integer  (  column  )  )  ; 
int   rowCount  =  model  .  getRowCount  (  )  ; 
for  (  int   row  =  0  ;  row  <  rowCount  ;  row  ++  )  { 
indexes  [  row  ]  =  row  ; 
} 
super  .  tableChanged  (  new   TableModelEvent  (  this  )  )  ; 
} 

public   void   addMouseListenerToHeaderInTable  (  JTable   table  )  { 
final   TableSorter   sorter  =  this  ; 
final   JTable   tableView  =  table  ; 
tableView  .  setColumnSelectionAllowed  (  false  )  ; 
MouseAdapter   listMouseListener  =  new   MouseAdapter  (  )  { 

public   void   mouseClicked  (  MouseEvent   e  )  { 
TableColumnModel   columnModel  =  tableView  .  getColumnModel  (  )  ; 
int   viewColumn  =  columnModel  .  getColumnIndexAtX  (  e  .  getX  (  )  )  ; 
int   column  =  tableView  .  convertColumnIndexToModel  (  viewColumn  )  ; 
if  (  column  !=  -  1  )  { 
System  .  out  .  println  (  "click column="  +  column  )  ; 
nextColumn  (  column  )  ; 
switch  (  ascending  )  { 
case   ORDER_ASCENDING  : 
sorter  .  sortByColumn  (  column  ,  true  )  ; 
break  ; 
case   ORDER_DESCENDING  : 
sorter  .  sortByColumn  (  column  ,  false  )  ; 
break  ; 
case   ORDER_UNSORTED  : 
sorter  .  unsortByColumn  (  column  )  ; 
break  ; 
} 
} 
} 
}  ; 
JTableHeader   th  =  tableView  .  getTableHeader  (  )  ; 
th  .  addMouseListener  (  listMouseListener  )  ; 
} 

private   int   oldColumn  ; 

private   void   nextColumn  (  int   column  )  { 
if  (  column  !=  oldColumn  )  { 
ascending  =  ORDER_ASCENDING  ; 
oldColumn  =  column  ; 
}  else  { 
if  (  ascending  ==  ORDER_ASCENDING  )  { 
ascending  =  ORDER_DESCENDING  ; 
}  else   if  (  ascending  ==  ORDER_DESCENDING  )  { 
ascending  =  ORDER_UNSORTED  ; 
}  else  { 
ascending  =  ORDER_ASCENDING  ; 
} 
} 
} 

public   boolean   isCellEditable  (  int   row  ,  int   column  )  { 
return   super  .  isCellEditable  (  getRow  (  row  )  ,  column  )  ; 
} 

public   void   fireTableCellUpdated  (  int   row  ,  int   column  )  { 
super  .  fireTableCellUpdated  (  getRow  (  row  )  ,  column  )  ; 
} 

public   void   fireTableRowsDeleted  (  int   firstRow  ,  int   lastRow  )  { 
super  .  fireTableRowsDeleted  (  getRow  (  firstRow  )  ,  getRow  (  lastRow  )  )  ; 
} 

public   void   fireTableRowsInserted  (  int   firstRow  ,  int   lastRow  )  { 
super  .  fireTableRowsInserted  (  getRow  (  firstRow  )  ,  getRow  (  lastRow  )  )  ; 
} 

public   void   fireTableRowsUpdated  (  int   firstRow  ,  int   lastRow  )  { 
super  .  fireTableRowsUpdated  (  getRow  (  firstRow  )  ,  getRow  (  lastRow  )  )  ; 
} 
} 
