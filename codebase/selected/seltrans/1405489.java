package   org  .  hsqldb  ; 

import   org  .  hsqldb  .  HsqlNameManager  .  HsqlName  ; 
import   org  .  hsqldb  .  error  .  Error  ; 
import   org  .  hsqldb  .  error  .  ErrorCode  ; 
import   org  .  hsqldb  .  lib  .  HsqlArrayList  ; 
import   org  .  hsqldb  .  lib  .  OrderedHashSet  ; 
import   org  .  hsqldb  .  navigator  .  RowIterator  ; 
import   org  .  hsqldb  .  result  .  Result  ; 
import   org  .  hsqldb  .  rights  .  Grantee  ; 
import   org  .  hsqldb  .  rights  .  GranteeManager  ; 
import   org  .  hsqldb  .  rights  .  Right  ; 
import   org  .  hsqldb  .  store  .  ValuePool  ; 
import   org  .  hsqldb  .  types  .  Charset  ; 
import   org  .  hsqldb  .  types  .  Type  ; 








public   class   StatementSchema   extends   Statement  { 

int   order  ; 

Object  [  ]  arguments  =  ValuePool  .  emptyObjectArray  ; 

boolean   isSchemaDefinition  ; 

Token  [  ]  statementTokens  ; 

StatementSchema  (  )  { 
super  (  StatementTypes  .  CREATE_SCHEMA  ,  StatementTypes  .  X_SQL_SCHEMA_DEFINITION  )  ; 
isTransactionStatement  =  true  ; 
} 

StatementSchema  (  String   sql  ,  int   type  ,  Object  [  ]  args  )  { 
this  (  sql  ,  type  ,  args  ,  (  HsqlName  [  ]  )  null  ,  null  )  ; 
} 

StatementSchema  (  String   sql  ,  int   type  ,  Object  [  ]  args  ,  HsqlName  [  ]  readName  ,  HsqlName  [  ]  writeName  )  { 
super  (  type  )  ; 
isTransactionStatement  =  true  ; 
this  .  sql  =  sql  ; 
if  (  args  !=  null  )  { 
arguments  =  args  ; 
} 
if  (  readName  !=  null  )  { 
readTableNames  =  readName  ; 
} 
if  (  writeName  !=  null  )  { 
writeTableNames  =  writeName  ; 
} 
switch  (  type  )  { 
case   StatementTypes  .  RENAME_OBJECT  : 
group  =  StatementTypes  .  X_SQL_SCHEMA_MANIPULATION  ; 
break  ; 
case   StatementTypes  .  ALTER_DOMAIN  : 
case   StatementTypes  .  ALTER_ROUTINE  : 
case   StatementTypes  .  ALTER_SEQUENCE  : 
case   StatementTypes  .  ALTER_TYPE  : 
case   StatementTypes  .  ALTER_TABLE  : 
case   StatementTypes  .  ALTER_TRANSFORM  : 
case   StatementTypes  .  ALTER_VIEW  : 
group  =  StatementTypes  .  X_SQL_SCHEMA_MANIPULATION  ; 
break  ; 
case   StatementTypes  .  DROP_ASSERTION  : 
case   StatementTypes  .  DROP_CHARACTER_SET  : 
case   StatementTypes  .  DROP_COLLATION  : 
case   StatementTypes  .  DROP_TYPE  : 
case   StatementTypes  .  DROP_DOMAIN  : 
case   StatementTypes  .  DROP_ROLE  : 
case   StatementTypes  .  DROP_USER  : 
case   StatementTypes  .  DROP_ROUTINE  : 
case   StatementTypes  .  DROP_SCHEMA  : 
case   StatementTypes  .  DROP_SEQUENCE  : 
case   StatementTypes  .  DROP_TABLE  : 
case   StatementTypes  .  DROP_TRANSFORM  : 
case   StatementTypes  .  DROP_TRANSLATION  : 
case   StatementTypes  .  DROP_TRIGGER  : 
case   StatementTypes  .  DROP_CAST  : 
case   StatementTypes  .  DROP_ORDERING  : 
case   StatementTypes  .  DROP_VIEW  : 
case   StatementTypes  .  DROP_INDEX  : 
case   StatementTypes  .  DROP_CONSTRAINT  : 
case   StatementTypes  .  DROP_COLUMN  : 
group  =  StatementTypes  .  X_SQL_SCHEMA_MANIPULATION  ; 
break  ; 
case   StatementTypes  .  GRANT  : 
group  =  StatementTypes  .  X_SQL_SCHEMA_MANIPULATION  ; 
order  =  10  ; 
break  ; 
case   StatementTypes  .  GRANT_ROLE  : 
group  =  StatementTypes  .  X_SQL_SCHEMA_MANIPULATION  ; 
order  =  10  ; 
break  ; 
case   StatementTypes  .  REVOKE  : 
case   StatementTypes  .  REVOKE_ROLE  : 
group  =  StatementTypes  .  X_SQL_SCHEMA_MANIPULATION  ; 
break  ; 
case   StatementTypes  .  CREATE_SCHEMA  : 
group  =  StatementTypes  .  X_SQL_SCHEMA_DEFINITION  ; 
break  ; 
case   StatementTypes  .  CREATE_ROLE  : 
group  =  StatementTypes  .  X_SQL_SCHEMA_DEFINITION  ; 
order  =  1  ; 
break  ; 
case   StatementTypes  .  CREATE_ROUTINE  : 
group  =  StatementTypes  .  X_SQL_SCHEMA_DEFINITION  ; 
order  =  7  ; 
break  ; 
case   StatementTypes  .  CREATE_SEQUENCE  : 
group  =  StatementTypes  .  X_SQL_SCHEMA_DEFINITION  ; 
order  =  1  ; 
break  ; 
case   StatementTypes  .  CREATE_TABLE  : 
group  =  StatementTypes  .  X_SQL_SCHEMA_DEFINITION  ; 
order  =  2  ; 
break  ; 
case   StatementTypes  .  CREATE_TRANSFORM  : 
group  =  StatementTypes  .  X_SQL_SCHEMA_DEFINITION  ; 
order  =  1  ; 
break  ; 
case   StatementTypes  .  CREATE_TRANSLATION  : 
group  =  StatementTypes  .  X_SQL_SCHEMA_DEFINITION  ; 
order  =  1  ; 
break  ; 
case   StatementTypes  .  CREATE_TRIGGER  : 
group  =  StatementTypes  .  X_SQL_SCHEMA_DEFINITION  ; 
order  =  7  ; 
break  ; 
case   StatementTypes  .  CREATE_CAST  : 
group  =  StatementTypes  .  X_SQL_SCHEMA_DEFINITION  ; 
order  =  2  ; 
break  ; 
case   StatementTypes  .  CREATE_TYPE  : 
group  =  StatementTypes  .  X_SQL_SCHEMA_DEFINITION  ; 
order  =  1  ; 
break  ; 
case   StatementTypes  .  CREATE_ORDERING  : 
group  =  StatementTypes  .  X_SQL_SCHEMA_DEFINITION  ; 
order  =  1  ; 
break  ; 
case   StatementTypes  .  CREATE_VIEW  : 
group  =  StatementTypes  .  X_SQL_SCHEMA_DEFINITION  ; 
order  =  5  ; 
break  ; 
case   StatementTypes  .  CREATE_USER  : 
group  =  StatementTypes  .  X_SQL_SCHEMA_DEFINITION  ; 
order  =  1  ; 
break  ; 
case   StatementTypes  .  CREATE_ASSERTION  : 
group  =  StatementTypes  .  X_SQL_SCHEMA_DEFINITION  ; 
order  =  9  ; 
break  ; 
case   StatementTypes  .  CREATE_CHARACTER_SET  : 
group  =  StatementTypes  .  X_SQL_SCHEMA_DEFINITION  ; 
order  =  1  ; 
break  ; 
case   StatementTypes  .  CREATE_COLLATION  : 
group  =  StatementTypes  .  X_SQL_SCHEMA_DEFINITION  ; 
order  =  1  ; 
break  ; 
case   StatementTypes  .  CREATE_DOMAIN  : 
group  =  StatementTypes  .  X_SQL_SCHEMA_DEFINITION  ; 
order  =  1  ; 
break  ; 
case   StatementTypes  .  CREATE_ALIAS  : 
group  =  StatementTypes  .  X_SQL_SCHEMA_DEFINITION  ; 
order  =  8  ; 
break  ; 
case   StatementTypes  .  CREATE_INDEX  : 
group  =  StatementTypes  .  X_SQL_SCHEMA_MANIPULATION  ; 
order  =  4  ; 
break  ; 
case   StatementTypes  .  COMMENT  : 
group  =  StatementTypes  .  X_SQL_SCHEMA_MANIPULATION  ; 
order  =  11  ; 
break  ; 
case   StatementTypes  .  CHECK  : 
group  =  StatementTypes  .  X_SQL_SCHEMA_MANIPULATION  ; 
statementTokens  =  (  Token  [  ]  )  args  [  0  ]  ; 
break  ; 
case   StatementTypes  .  LOG_SCHEMA_STATEMENT  : 
group  =  StatementTypes  .  X_SQL_SCHEMA_MANIPULATION  ; 
break  ; 
default  : 
throw   Error  .  runtimeError  (  ErrorCode  .  U_S0500  ,  "StatemntSchema"  )  ; 
} 
} 

public   Result   execute  (  Session   session  )  { 
Result   result  ; 
try  { 
result  =  getResult  (  session  )  ; 
}  catch  (  Throwable   t  )  { 
result  =  Result  .  newErrorResult  (  t  ,  null  )  ; 
} 
if  (  result  .  isError  (  )  )  { 
result  .  getException  (  )  .  setStatementType  (  group  ,  type  )  ; 
return   result  ; 
} 
session  .  database  .  schemaManager  .  setSchemaChangeTimestamp  (  )  ; 
try  { 
if  (  isLogged  )  { 
session  .  database  .  logger  .  writeOtherStatement  (  session  ,  sql  )  ; 
} 
}  catch  (  Throwable   e  )  { 
return   Result  .  newErrorResult  (  e  ,  sql  )  ; 
} 
return   result  ; 
} 

Result   getResult  (  Session   session  )  { 
SchemaManager   schemaManager  =  session  .  database  .  schemaManager  ; 
if  (  this  .  isExplain  )  { 
return   Result  .  newSingleColumnStringResult  (  "OPERATION"  ,  describe  (  session  )  )  ; 
} 
switch  (  type  )  { 
case   StatementTypes  .  RENAME_OBJECT  : 
{ 
HsqlName   name  =  (  HsqlName  )  arguments  [  0  ]  ; 
HsqlName   newName  =  (  HsqlName  )  arguments  [  1  ]  ; 
SchemaObject   object  ; 
if  (  name  .  type  ==  SchemaObject  .  CATALOG  )  { 
try  { 
session  .  checkAdmin  (  )  ; 
session  .  checkDDLWrite  (  )  ; 
name  .  rename  (  newName  )  ; 
break  ; 
}  catch  (  HsqlException   e  )  { 
return   Result  .  newErrorResult  (  e  ,  sql  )  ; 
} 
}  else   if  (  name  .  type  ==  SchemaObject  .  SCHEMA  )  { 
checkSchemaUpdateAuthorisation  (  session  ,  name  )  ; 
schemaManager  .  checkSchemaNameCanChange  (  name  )  ; 
schemaManager  .  renameSchema  (  name  ,  newName  )  ; 
break  ; 
} 
try  { 
name  .  setSchemaIfNull  (  session  .  getCurrentSchemaHsqlName  (  )  )  ; 
if  (  name  .  type  ==  SchemaObject  .  COLUMN  )  { 
Table   table  =  schemaManager  .  getUserTable  (  session  ,  name  .  parent  )  ; 
int   index  =  table  .  getColumnIndex  (  name  .  name  )  ; 
object  =  table  .  getColumn  (  index  )  ; 
}  else  { 
object  =  schemaManager  .  getSchemaObject  (  name  )  ; 
if  (  object  ==  null  )  { 
throw   Error  .  error  (  ErrorCode  .  X_42501  ,  name  .  name  )  ; 
} 
name  =  object  .  getName  (  )  ; 
} 
checkSchemaUpdateAuthorisation  (  session  ,  name  .  schema  )  ; 
newName  .  setSchemaIfNull  (  name  .  schema  )  ; 
if  (  name  .  schema  !=  newName  .  schema  )  { 
HsqlException   e  =  Error  .  error  (  ErrorCode  .  X_42505  )  ; 
return   Result  .  newErrorResult  (  e  ,  sql  )  ; 
} 
newName  .  parent  =  name  .  parent  ; 
switch  (  object  .  getType  (  )  )  { 
case   SchemaObject  .  COLUMN  : 
HsqlName   parent  =  object  .  getName  (  )  .  parent  ; 
schemaManager  .  checkColumnIsReferenced  (  parent  ,  object  .  getName  (  )  )  ; 
Table   table  =  schemaManager  .  getUserTable  (  session  ,  parent  )  ; 
table  .  renameColumn  (  (  ColumnSchema  )  object  ,  newName  )  ; 
break  ; 
default  : 
schemaManager  .  renameSchemaObject  (  name  ,  newName  )  ; 
} 
break  ; 
}  catch  (  HsqlException   e  )  { 
return   Result  .  newErrorResult  (  e  ,  sql  )  ; 
} 
} 
case   StatementTypes  .  ALTER_SEQUENCE  : 
{ 
try  { 
NumberSequence   sequence  =  (  NumberSequence  )  arguments  [  0  ]  ; 
NumberSequence   settings  =  (  NumberSequence  )  arguments  [  1  ]  ; 
checkSchemaUpdateAuthorisation  (  session  ,  sequence  .  getSchemaName  (  )  )  ; 
sequence  .  reset  (  settings  )  ; 
break  ; 
}  catch  (  HsqlException   e  )  { 
return   Result  .  newErrorResult  (  e  ,  sql  )  ; 
} 
} 
case   StatementTypes  .  ALTER_DOMAIN  : 
try  { 
int   subType  =  (  Integer  )  arguments  [  0  ]  ; 
Type   domain  =  (  Type  )  arguments  [  1  ]  ; 
switch  (  subType  )  { 
case   StatementTypes  .  ADD_CONSTRAINT  : 
{ 
Constraint   c  =  (  Constraint  )  arguments  [  2  ]  ; 
session  .  database  .  schemaManager  .  checkSchemaObjectNotExists  (  c  .  getName  (  )  )  ; 
domain  .  userTypeModifier  .  addConstraint  (  c  )  ; 
session  .  database  .  schemaManager  .  addSchemaObject  (  c  )  ; 
break  ; 
} 
case   StatementTypes  .  ADD_DEFAULT  : 
{ 
Expression   e  =  (  Expression  )  arguments  [  2  ]  ; 
domain  .  userTypeModifier  .  setDefaultClause  (  e  )  ; 
break  ; 
} 
case   StatementTypes  .  DROP_CONSTRAINT  : 
{ 
HsqlName   name  =  (  HsqlName  )  arguments  [  2  ]  ; 
session  .  database  .  schemaManager  .  removeSchemaObject  (  name  )  ; 
break  ; 
} 
case   StatementTypes  .  DROP_DEFAULT  : 
{ 
domain  .  userTypeModifier  .  removeDefaultClause  (  )  ; 
break  ; 
} 
} 
break  ; 
}  catch  (  HsqlException   e  )  { 
return   Result  .  newErrorResult  (  e  ,  sql  )  ; 
} 
case   StatementTypes  .  ALTER_TABLE  : 
try  { 
int   subType  =  (  Integer  )  arguments  [  0  ]  ; 
Table   table  =  (  Table  )  arguments  [  1  ]  ; 
switch  (  subType  )  { 
case   StatementTypes  .  ADD_CONSTRAINT  : 
{ 
Constraint   c  =  (  Constraint  )  arguments  [  2  ]  ; 
switch  (  c  .  getConstraintType  (  )  )  { 
case   SchemaObject  .  ConstraintTypes  .  PRIMARY_KEY  : 
{ 
TableWorks   tableWorks  =  new   TableWorks  (  session  ,  table  )  ; 
tableWorks  .  addPrimaryKey  (  c  )  ; 
break  ; 
} 
case   SchemaObject  .  ConstraintTypes  .  UNIQUE  : 
{ 
TableWorks   tableWorks  =  new   TableWorks  (  session  ,  table  )  ; 
tableWorks  .  addUniqueConstraint  (  c  )  ; 
break  ; 
} 
case   SchemaObject  .  ConstraintTypes  .  FOREIGN_KEY  : 
{ 
TableWorks   tableWorks  =  new   TableWorks  (  session  ,  table  )  ; 
tableWorks  .  addForeignKey  (  c  )  ; 
break  ; 
} 
case   SchemaObject  .  ConstraintTypes  .  CHECK  : 
{ 
TableWorks   tableWorks  =  new   TableWorks  (  session  ,  table  )  ; 
tableWorks  .  addCheckConstraint  (  c  )  ; 
break  ; 
} 
} 
break  ; 
} 
case   StatementTypes  .  ADD_COLUMN  : 
{ 
ColumnSchema   column  =  (  ColumnSchema  )  arguments  [  2  ]  ; 
int   colIndex  =  (  Integer  )  arguments  [  3  ]  ; 
HsqlArrayList   list  =  (  HsqlArrayList  )  arguments  [  4  ]  ; 
TableWorks   tableWorks  =  new   TableWorks  (  session  ,  table  )  ; 
tableWorks  .  addColumn  (  column  ,  colIndex  ,  list  )  ; 
break  ; 
} 
case   StatementTypes  .  ALTER_COLUMN_TYPE  : 
{ 
ColumnSchema   column  =  (  ColumnSchema  )  arguments  [  2  ]  ; 
Type   type  =  (  Type  )  arguments  [  3  ]  ; 
ColumnSchema   newCol  =  column  .  duplicate  (  )  ; 
newCol  .  setType  (  type  )  ; 
TableWorks   tw  =  new   TableWorks  (  session  ,  table  )  ; 
tw  .  retypeColumn  (  column  ,  newCol  )  ; 
break  ; 
} 
case   StatementTypes  .  ALTER_COLUMN_TYPE_IDENTITY  : 
{ 
ColumnSchema   column  =  (  ColumnSchema  )  arguments  [  2  ]  ; 
Type   type  =  (  Type  )  arguments  [  3  ]  ; 
NumberSequence   sequence  =  (  NumberSequence  )  arguments  [  4  ]  ; 
ColumnSchema   newCol  =  column  .  duplicate  (  )  ; 
newCol  .  setType  (  type  )  ; 
newCol  .  setIdentity  (  sequence  )  ; 
TableWorks   tw  =  new   TableWorks  (  session  ,  table  )  ; 
tw  .  retypeColumn  (  column  ,  newCol  )  ; 
break  ; 
} 
case   StatementTypes  .  ALTER_COLUMN_SEQUENCE  : 
{ 
ColumnSchema   column  =  (  ColumnSchema  )  arguments  [  2  ]  ; 
int   columnIndex  =  (  Integer  )  arguments  [  3  ]  ; 
NumberSequence   sequence  =  (  NumberSequence  )  arguments  [  4  ]  ; 
if  (  column  .  isIdentity  (  )  )  { 
column  .  getIdentitySequence  (  )  .  reset  (  sequence  )  ; 
}  else  { 
column  .  setIdentity  (  sequence  )  ; 
table  .  setColumnTypeVars  (  columnIndex  )  ; 
} 
break  ; 
} 
case   StatementTypes  .  ALTER_COLUMN_NULL  : 
{ 
ColumnSchema   column  =  (  ColumnSchema  )  arguments  [  2  ]  ; 
boolean   nullable  =  (  Boolean  )  arguments  [  3  ]  ; 
TableWorks   tw  =  new   TableWorks  (  session  ,  table  )  ; 
tw  .  setColNullability  (  column  ,  nullable  )  ; 
break  ; 
} 
case   StatementTypes  .  ALTER_COLUMN_DEFAULT  : 
{ 
ColumnSchema   column  =  (  ColumnSchema  )  arguments  [  2  ]  ; 
int   columnIndex  =  (  Integer  )  arguments  [  3  ]  ; 
Expression   e  =  (  Expression  )  arguments  [  4  ]  ; 
TableWorks   tw  =  new   TableWorks  (  session  ,  table  )  ; 
tw  .  setColDefaultExpression  (  columnIndex  ,  e  )  ; 
break  ; 
} 
case   StatementTypes  .  ALTER_COLUMN_DROP_DEFAULT  : 
{ 
ColumnSchema   column  =  (  ColumnSchema  )  arguments  [  2  ]  ; 
int   columnIndex  =  (  Integer  )  arguments  [  3  ]  ; 
TableWorks   tw  =  new   TableWorks  (  session  ,  table  )  ; 
tw  .  setColDefaultExpression  (  columnIndex  ,  null  )  ; 
table  .  setColumnTypeVars  (  columnIndex  )  ; 
break  ; 
} 
case   StatementTypes  .  ALTER_COLUMN_DROP_GENERATED  : 
{ 
ColumnSchema   column  =  (  ColumnSchema  )  arguments  [  2  ]  ; 
int   columnIndex  =  (  Integer  )  arguments  [  3  ]  ; 
column  .  setIdentity  (  null  )  ; 
table  .  setColumnTypeVars  (  columnIndex  )  ; 
break  ; 
} 
} 
break  ; 
}  catch  (  HsqlException   e  )  { 
return   Result  .  newErrorResult  (  e  ,  sql  )  ; 
} 
case   StatementTypes  .  ALTER_ROUTINE  : 
{ 
Routine   routine  =  (  Routine  )  arguments  [  0  ]  ; 
try  { 
routine  .  resolveReferences  (  session  )  ; 
Routine   oldRoutine  =  (  Routine  )  schemaManager  .  getSchemaObject  (  routine  .  getSpecificName  (  )  )  ; 
schemaManager  .  replaceReferences  (  oldRoutine  ,  routine  )  ; 
oldRoutine  .  setAsAlteredRoutine  (  routine  )  ; 
break  ; 
}  catch  (  HsqlException   e  )  { 
return   Result  .  newErrorResult  (  e  ,  sql  )  ; 
} 
} 
case   StatementTypes  .  ALTER_TYPE  : 
case   StatementTypes  .  ALTER_TRANSFORM  : 
{ 
throw   Error  .  runtimeError  (  ErrorCode  .  U_S0500  ,  "StatementSchema"  )  ; 
} 
case   StatementTypes  .  ALTER_VIEW  : 
{ 
View   view  =  (  View  )  arguments  [  0  ]  ; 
try  { 
checkSchemaUpdateAuthorisation  (  session  ,  view  .  getSchemaName  (  )  )  ; 
View   oldView  =  (  View  )  schemaManager  .  getSchemaObject  (  view  .  getName  (  )  )  ; 
if  (  oldView  ==  null  )  { 
throw   Error  .  error  (  ErrorCode  .  X_42501  ,  view  .  getName  (  )  .  name  )  ; 
} 
view  .  setName  (  oldView  .  getName  (  )  )  ; 
view  .  compile  (  session  ,  null  )  ; 
OrderedHashSet   dependents  =  schemaManager  .  getReferencingObjectNames  (  oldView  .  getName  (  )  )  ; 
if  (  dependents  .  getCommonElementCount  (  view  .  getReferences  (  )  )  >  0  )  { 
throw   Error  .  error  (  ErrorCode  .  X_42502  )  ; 
} 
int   i  =  schemaManager  .  getTableIndex  (  oldView  )  ; 
schemaManager  .  setTable  (  i  ,  view  )  ; 
OrderedHashSet   set  =  new   OrderedHashSet  (  )  ; 
set  .  add  (  view  )  ; 
try  { 
schemaManager  .  recompileDependentObjects  (  set  )  ; 
}  catch  (  HsqlException   e  )  { 
schemaManager  .  setTable  (  i  ,  oldView  )  ; 
schemaManager  .  recompileDependentObjects  (  set  )  ; 
} 
break  ; 
}  catch  (  HsqlException   e  )  { 
return   Result  .  newErrorResult  (  e  ,  sql  )  ; 
} 
} 
case   StatementTypes  .  DROP_COLUMN  : 
{ 
try  { 
HsqlName   name  =  (  HsqlName  )  arguments  [  0  ]  ; 
int   objectType  =  (  (  Integer  )  arguments  [  1  ]  )  .  intValue  (  )  ; 
boolean   cascade  =  (  (  Boolean  )  arguments  [  2  ]  )  .  booleanValue  (  )  ; 
boolean   ifExists  =  (  (  Boolean  )  arguments  [  3  ]  )  .  booleanValue  (  )  ; 
Table   table  =  schemaManager  .  getUserTable  (  session  ,  name  .  parent  )  ; 
int   colindex  =  table  .  getColumnIndex  (  name  .  name  )  ; 
if  (  table  .  getColumnCount  (  )  ==  1  )  { 
throw   Error  .  error  (  ErrorCode  .  X_42591  )  ; 
} 
checkSchemaUpdateAuthorisation  (  session  ,  table  .  getSchemaName  (  )  )  ; 
TableWorks   tableWorks  =  new   TableWorks  (  session  ,  table  )  ; 
tableWorks  .  dropColumn  (  colindex  ,  cascade  )  ; 
break  ; 
}  catch  (  HsqlException   e  )  { 
return   Result  .  newErrorResult  (  e  ,  sql  )  ; 
} 
} 
case   StatementTypes  .  DROP_ASSERTION  : 
case   StatementTypes  .  DROP_CHARACTER_SET  : 
case   StatementTypes  .  DROP_COLLATION  : 
case   StatementTypes  .  DROP_TYPE  : 
case   StatementTypes  .  DROP_DOMAIN  : 
case   StatementTypes  .  DROP_ROLE  : 
case   StatementTypes  .  DROP_USER  : 
case   StatementTypes  .  DROP_ROUTINE  : 
case   StatementTypes  .  DROP_SCHEMA  : 
case   StatementTypes  .  DROP_SEQUENCE  : 
case   StatementTypes  .  DROP_TABLE  : 
case   StatementTypes  .  DROP_TRANSFORM  : 
case   StatementTypes  .  DROP_TRANSLATION  : 
case   StatementTypes  .  DROP_TRIGGER  : 
case   StatementTypes  .  DROP_CAST  : 
case   StatementTypes  .  DROP_ORDERING  : 
case   StatementTypes  .  DROP_VIEW  : 
case   StatementTypes  .  DROP_INDEX  : 
case   StatementTypes  .  DROP_CONSTRAINT  : 
{ 
try  { 
HsqlName   name  =  (  HsqlName  )  arguments  [  0  ]  ; 
int   objectType  =  (  (  Integer  )  arguments  [  1  ]  )  .  intValue  (  )  ; 
boolean   cascade  =  (  (  Boolean  )  arguments  [  2  ]  )  .  booleanValue  (  )  ; 
boolean   ifExists  =  (  (  Boolean  )  arguments  [  3  ]  )  .  booleanValue  (  )  ; 
switch  (  type  )  { 
case   StatementTypes  .  DROP_ROLE  : 
case   StatementTypes  .  DROP_USER  : 
session  .  checkAdmin  (  )  ; 
session  .  checkDDLWrite  (  )  ; 
break  ; 
case   StatementTypes  .  DROP_SCHEMA  : 
checkSchemaUpdateAuthorisation  (  session  ,  name  )  ; 
if  (  !  schemaManager  .  schemaExists  (  name  .  name  )  )  { 
if  (  ifExists  )  { 
return   Result  .  updateZeroResult  ; 
} 
} 
break  ; 
default  : 
if  (  name  .  schema  ==  null  )  { 
name  .  schema  =  session  .  getCurrentSchemaHsqlName  (  )  ; 
}  else  { 
if  (  !  schemaManager  .  schemaExists  (  name  .  schema  .  name  )  )  { 
if  (  ifExists  )  { 
return   Result  .  updateZeroResult  ; 
} 
} 
} 
name  .  schema  =  schemaManager  .  getUserSchemaHsqlName  (  name  .  schema  .  name  )  ; 
checkSchemaUpdateAuthorisation  (  session  ,  name  .  schema  )  ; 
SchemaObject   object  =  schemaManager  .  getSchemaObject  (  name  )  ; 
if  (  object  ==  null  )  { 
if  (  ifExists  )  { 
return   Result  .  updateZeroResult  ; 
} 
throw   Error  .  error  (  ErrorCode  .  X_42501  ,  name  .  name  )  ; 
} 
if  (  name  .  type  ==  SchemaObject  .  SPECIFIC_ROUTINE  )  { 
name  =  (  (  Routine  )  object  )  .  getSpecificName  (  )  ; 
}  else  { 
name  =  object  .  getName  (  )  ; 
} 
} 
if  (  !  cascade  )  { 
schemaManager  .  checkObjectIsReferenced  (  name  )  ; 
} 
switch  (  type  )  { 
case   StatementTypes  .  DROP_ROLE  : 
dropRole  (  session  ,  name  ,  cascade  )  ; 
break  ; 
case   StatementTypes  .  DROP_USER  : 
dropUser  (  session  ,  name  ,  cascade  )  ; 
break  ; 
case   StatementTypes  .  DROP_SCHEMA  : 
dropSchema  (  session  ,  name  ,  cascade  )  ; 
break  ; 
case   StatementTypes  .  DROP_ASSERTION  : 
break  ; 
case   StatementTypes  .  DROP_CHARACTER_SET  : 
case   StatementTypes  .  DROP_COLLATION  : 
case   StatementTypes  .  DROP_SEQUENCE  : 
case   StatementTypes  .  DROP_TRIGGER  : 
dropObject  (  session  ,  name  ,  cascade  )  ; 
break  ; 
case   StatementTypes  .  DROP_TYPE  : 
dropType  (  session  ,  name  ,  cascade  )  ; 
break  ; 
case   StatementTypes  .  DROP_DOMAIN  : 
dropDomain  (  session  ,  name  ,  cascade  )  ; 
break  ; 
case   StatementTypes  .  DROP_ROUTINE  : 
dropRoutine  (  session  ,  name  ,  cascade  )  ; 
break  ; 
case   StatementTypes  .  DROP_TABLE  : 
case   StatementTypes  .  DROP_VIEW  : 
dropTable  (  session  ,  name  ,  cascade  )  ; 
break  ; 
case   StatementTypes  .  DROP_TRANSFORM  : 
case   StatementTypes  .  DROP_TRANSLATION  : 
case   StatementTypes  .  DROP_CAST  : 
case   StatementTypes  .  DROP_ORDERING  : 
break  ; 
case   StatementTypes  .  DROP_INDEX  : 
checkSchemaUpdateAuthorisation  (  session  ,  name  .  schema  )  ; 
schemaManager  .  dropIndex  (  session  ,  name  )  ; 
break  ; 
case   StatementTypes  .  DROP_CONSTRAINT  : 
checkSchemaUpdateAuthorisation  (  session  ,  name  .  schema  )  ; 
schemaManager  .  dropConstraint  (  session  ,  name  ,  cascade  )  ; 
break  ; 
} 
break  ; 
}  catch  (  HsqlException   e  )  { 
return   Result  .  newErrorResult  (  e  ,  sql  )  ; 
} 
} 
case   StatementTypes  .  GRANT  : 
case   StatementTypes  .  REVOKE  : 
{ 
try  { 
boolean   grant  =  type  ==  StatementTypes  .  GRANT  ; 
OrderedHashSet   granteeList  =  (  OrderedHashSet  )  arguments  [  0  ]  ; 
HsqlName   name  =  (  HsqlName  )  arguments  [  1  ]  ; 
this  .  setSchemaName  (  session  ,  null  ,  name  )  ; 
name  =  schemaManager  .  getSchemaObjectName  (  name  .  schema  ,  name  .  name  ,  name  .  type  ,  true  )  ; 
SchemaObject   schemaObject  =  schemaManager  .  getSchemaObject  (  name  )  ; 
Right   right  =  (  Right  )  arguments  [  2  ]  ; 
Grantee   grantor  =  (  Grantee  )  arguments  [  3  ]  ; 
boolean   cascade  =  (  (  Boolean  )  arguments  [  4  ]  )  .  booleanValue  (  )  ; 
boolean   isGrantOption  =  (  (  Boolean  )  arguments  [  5  ]  )  .  booleanValue  (  )  ; 
if  (  grantor  ==  null  )  { 
grantor  =  isSchemaDefinition  ?  schemaName  .  owner  :  session  .  getGrantee  (  )  ; 
} 
GranteeManager   gm  =  session  .  database  .  granteeManager  ; 
switch  (  schemaObject  .  getType  (  )  )  { 
case   SchemaObject  .  CHARSET  : 
System  .  out  .  println  (  "grant charset!"  )  ; 
break  ; 
case   SchemaObject  .  VIEW  : 
case   SchemaObject  .  TABLE  : 
{ 
Table   t  =  (  Table  )  schemaObject  ; 
right  .  setColumns  (  t  )  ; 
if  (  t  .  getTableType  (  )  ==  TableBase  .  TEMP_TABLE  &&  !  right  .  isFull  (  )  )  { 
return   Result  .  newErrorResult  (  Error  .  error  (  ErrorCode  .  X_42595  )  ,  sql  )  ; 
} 
} 
} 
if  (  grant  )  { 
gm  .  grant  (  granteeList  ,  schemaObject  ,  right  ,  grantor  ,  isGrantOption  )  ; 
}  else  { 
gm  .  revoke  (  granteeList  ,  schemaObject  ,  right  ,  grantor  ,  isGrantOption  ,  cascade  )  ; 
} 
break  ; 
}  catch  (  HsqlException   e  )  { 
return   Result  .  newErrorResult  (  e  ,  sql  )  ; 
} 
} 
case   StatementTypes  .  GRANT_ROLE  : 
case   StatementTypes  .  REVOKE_ROLE  : 
{ 
try  { 
boolean   grant  =  type  ==  StatementTypes  .  GRANT_ROLE  ; 
OrderedHashSet   granteeList  =  (  OrderedHashSet  )  arguments  [  0  ]  ; 
OrderedHashSet   roleList  =  (  OrderedHashSet  )  arguments  [  1  ]  ; 
Grantee   grantor  =  (  Grantee  )  arguments  [  2  ]  ; 
boolean   cascade  =  (  (  Boolean  )  arguments  [  3  ]  )  .  booleanValue  (  )  ; 
GranteeManager   gm  =  session  .  database  .  granteeManager  ; 
gm  .  checkGranteeList  (  granteeList  )  ; 
for  (  int   i  =  0  ;  i  <  granteeList  .  size  (  )  ;  i  ++  )  { 
String   grantee  =  (  String  )  granteeList  .  get  (  i  )  ; 
gm  .  checkRoleList  (  grantee  ,  roleList  ,  grantor  ,  grant  )  ; 
} 
if  (  grant  )  { 
for  (  int   i  =  0  ;  i  <  granteeList  .  size  (  )  ;  i  ++  )  { 
String   grantee  =  (  String  )  granteeList  .  get  (  i  )  ; 
for  (  int   j  =  0  ;  j  <  roleList  .  size  (  )  ;  j  ++  )  { 
String   roleName  =  (  String  )  roleList  .  get  (  j  )  ; 
gm  .  grant  (  grantee  ,  roleName  ,  grantor  )  ; 
} 
} 
}  else  { 
for  (  int   i  =  0  ;  i  <  granteeList  .  size  (  )  ;  i  ++  )  { 
String   grantee  =  (  String  )  granteeList  .  get  (  i  )  ; 
for  (  int   j  =  0  ;  j  <  roleList  .  size  (  )  ;  j  ++  )  { 
gm  .  revoke  (  grantee  ,  (  String  )  roleList  .  get  (  j  )  ,  grantor  )  ; 
} 
} 
} 
break  ; 
}  catch  (  HsqlException   e  )  { 
return   Result  .  newErrorResult  (  e  ,  sql  )  ; 
} 
} 
case   StatementTypes  .  CREATE_ASSERTION  : 
{ 
return   Result  .  updateZeroResult  ; 
} 
case   StatementTypes  .  CREATE_CHARACTER_SET  : 
{ 
Charset   charset  =  (  Charset  )  arguments  [  0  ]  ; 
try  { 
setOrCheckObjectName  (  session  ,  null  ,  charset  .  getName  (  )  ,  true  )  ; 
schemaManager  .  addSchemaObject  (  charset  )  ; 
break  ; 
}  catch  (  HsqlException   e  )  { 
return   Result  .  newErrorResult  (  e  ,  sql  )  ; 
} 
} 
case   StatementTypes  .  CREATE_COLLATION  : 
{ 
return   Result  .  updateZeroResult  ; 
} 
case   StatementTypes  .  CREATE_ROLE  : 
{ 
try  { 
session  .  checkAdmin  (  )  ; 
session  .  checkDDLWrite  (  )  ; 
HsqlName   name  =  (  HsqlName  )  arguments  [  0  ]  ; 
session  .  database  .  getGranteeManager  (  )  .  addRole  (  name  )  ; 
break  ; 
}  catch  (  HsqlException   e  )  { 
return   Result  .  newErrorResult  (  e  ,  sql  )  ; 
} 
} 
case   StatementTypes  .  CREATE_USER  : 
{ 
HsqlName   name  =  (  HsqlName  )  arguments  [  0  ]  ; 
String   password  =  (  String  )  arguments  [  1  ]  ; 
Grantee   grantor  =  (  Grantee  )  arguments  [  2  ]  ; 
boolean   admin  =  (  (  Boolean  )  arguments  [  3  ]  )  .  booleanValue  (  )  ; 
try  { 
session  .  checkAdmin  (  )  ; 
session  .  checkDDLWrite  (  )  ; 
session  .  database  .  getUserManager  (  )  .  createUser  (  name  ,  password  )  ; 
if  (  admin  )  { 
session  .  database  .  getGranteeManager  (  )  .  grant  (  name  .  name  ,  SqlInvariants  .  DBA_ADMIN_ROLE_NAME  ,  grantor  )  ; 
} 
break  ; 
}  catch  (  HsqlException   e  )  { 
return   Result  .  newErrorResult  (  e  ,  sql  )  ; 
} 
} 
case   StatementTypes  .  CREATE_SCHEMA  : 
{ 
HsqlName   name  =  (  HsqlName  )  arguments  [  0  ]  ; 
Grantee   owner  =  (  Grantee  )  arguments  [  1  ]  ; 
try  { 
session  .  checkDDLWrite  (  )  ; 
if  (  schemaManager  .  schemaExists  (  name  .  name  )  )  { 
if  (  session  .  isProcessingScript  &&  SqlInvariants  .  PUBLIC_SCHEMA  .  equals  (  name  .  name  )  )  { 
}  else  { 
throw   Error  .  error  (  ErrorCode  .  X_42504  ,  name  .  name  )  ; 
} 
}  else  { 
schemaManager  .  createSchema  (  name  ,  owner  )  ; 
Schema   schema  =  schemaManager  .  findSchema  (  name  .  name  )  ; 
this  .  sql  =  schema  .  getSQL  (  )  ; 
if  (  session  .  isProcessingScript  (  )  &&  session  .  database  .  getProperties  (  )  .  isVersion18  (  )  )  { 
session  .  setCurrentSchemaHsqlName  (  schema  .  getName  (  )  )  ; 
} 
} 
break  ; 
}  catch  (  HsqlException   e  )  { 
return   Result  .  newErrorResult  (  e  ,  sql  )  ; 
} 
} 
case   StatementTypes  .  CREATE_ROUTINE  : 
{ 
Routine   routine  =  (  Routine  )  arguments  [  0  ]  ; 
try  { 
routine  .  resolve  (  session  )  ; 
setOrCheckObjectName  (  session  ,  null  ,  routine  .  getName  (  )  ,  false  )  ; 
schemaManager  .  addSchemaObject  (  routine  )  ; 
break  ; 
}  catch  (  HsqlException   e  )  { 
return   Result  .  newErrorResult  (  e  ,  sql  )  ; 
} 
} 
case   StatementTypes  .  CREATE_ALIAS  : 
{ 
HsqlName   name  =  (  HsqlName  )  arguments  [  0  ]  ; 
Routine  [  ]  routines  =  (  Routine  [  ]  )  arguments  [  1  ]  ; 
try  { 
session  .  checkAdmin  (  )  ; 
session  .  checkDDLWrite  (  )  ; 
if  (  name  !=  null  )  { 
for  (  int   i  =  0  ;  i  <  routines  .  length  ;  i  ++  )  { 
routines  [  i  ]  .  setName  (  name  )  ; 
schemaManager  .  addSchemaObject  (  routines  [  i  ]  )  ; 
} 
} 
break  ; 
}  catch  (  HsqlException   e  )  { 
return   Result  .  newErrorResult  (  e  ,  sql  )  ; 
} 
} 
case   StatementTypes  .  CREATE_SEQUENCE  : 
{ 
NumberSequence   sequence  =  (  NumberSequence  )  arguments  [  0  ]  ; 
try  { 
setOrCheckObjectName  (  session  ,  null  ,  sequence  .  getName  (  )  ,  true  )  ; 
schemaManager  .  addSchemaObject  (  sequence  )  ; 
break  ; 
}  catch  (  HsqlException   e  )  { 
return   Result  .  newErrorResult  (  e  ,  sql  )  ; 
} 
} 
case   StatementTypes  .  CREATE_DOMAIN  : 
{ 
Type   type  =  (  Type  )  arguments  [  0  ]  ; 
Constraint  [  ]  constraints  =  type  .  userTypeModifier  .  getConstraints  (  )  ; 
try  { 
setOrCheckObjectName  (  session  ,  null  ,  type  .  getName  (  )  ,  true  )  ; 
for  (  int   i  =  0  ;  i  <  constraints  .  length  ;  i  ++  )  { 
Constraint   c  =  constraints  [  i  ]  ; 
setOrCheckObjectName  (  session  ,  type  .  getName  (  )  ,  c  .  getName  (  )  ,  true  )  ; 
schemaManager  .  addSchemaObject  (  c  )  ; 
} 
schemaManager  .  addSchemaObject  (  type  )  ; 
break  ; 
}  catch  (  HsqlException   e  )  { 
return   Result  .  newErrorResult  (  e  ,  sql  )  ; 
} 
} 
case   StatementTypes  .  CREATE_TABLE  : 
{ 
Table   table  =  (  Table  )  arguments  [  0  ]  ; 
HsqlArrayList   tempConstraints  =  (  HsqlArrayList  )  arguments  [  1  ]  ; 
StatementDMQL   statement  =  (  StatementDMQL  )  arguments  [  2  ]  ; 
Boolean   ifNotExists  =  (  Boolean  )  arguments  [  3  ]  ; 
HsqlArrayList   foreignConstraints  =  null  ; 
try  { 
setOrCheckObjectName  (  session  ,  null  ,  table  .  getName  (  )  ,  true  )  ; 
}  catch  (  HsqlException   e  )  { 
if  (  ifNotExists  !=  null  &&  ifNotExists  .  booleanValue  (  )  )  { 
return   Result  .  updateZeroResult  ; 
}  else  { 
return   Result  .  newErrorResult  (  e  ,  sql  )  ; 
} 
} 
try  { 
if  (  isSchemaDefinition  )  { 
foreignConstraints  =  new   HsqlArrayList  (  )  ; 
} 
if  (  tempConstraints  .  size  (  )  !=  0  )  { 
table  =  ParserDDL  .  addTableConstraintDefinitions  (  session  ,  table  ,  tempConstraints  ,  foreignConstraints  ,  true  )  ; 
arguments  [  1  ]  =  foreignConstraints  ; 
} 
table  .  compile  (  session  ,  null  )  ; 
schemaManager  .  addSchemaObject  (  table  )  ; 
if  (  statement  !=  null  )  { 
Result   result  =  statement  .  execute  (  session  )  ; 
table  .  insertIntoTable  (  session  ,  result  )  ; 
} 
if  (  table  .  hasLobColumn  )  { 
RowIterator   it  =  table  .  rowIterator  (  session  )  ; 
while  (  it  .  hasNext  (  )  )  { 
Row   row  =  it  .  getNextRow  (  )  ; 
Object  [  ]  data  =  row  .  getData  (  )  ; 
session  .  sessionData  .  adjustLobUsageCount  (  table  ,  data  ,  1  )  ; 
} 
} 
return   Result  .  updateZeroResult  ; 
}  catch  (  HsqlException   e  )  { 
schemaManager  .  removeExportedKeys  (  table  )  ; 
schemaManager  .  removeDependentObjects  (  table  .  getName  (  )  )  ; 
return   Result  .  newErrorResult  (  e  ,  sql  )  ; 
} 
} 
case   StatementTypes  .  CREATE_TRANSFORM  : 
return   Result  .  updateZeroResult  ; 
case   StatementTypes  .  CREATE_TRANSLATION  : 
return   Result  .  updateZeroResult  ; 
case   StatementTypes  .  CREATE_TRIGGER  : 
{ 
TriggerDef   trigger  =  (  TriggerDef  )  arguments  [  0  ]  ; 
HsqlName   otherName  =  (  HsqlName  )  arguments  [  1  ]  ; 
try  { 
checkSchemaUpdateAuthorisation  (  session  ,  trigger  .  getSchemaName  (  )  )  ; 
schemaManager  .  checkSchemaObjectNotExists  (  trigger  .  getName  (  )  )  ; 
if  (  otherName  !=  null  )  { 
if  (  schemaManager  .  getSchemaObject  (  otherName  )  ==  null  )  { 
throw   Error  .  error  (  ErrorCode  .  X_42501  ,  otherName  .  name  )  ; 
} 
} 
trigger  .  table  .  addTrigger  (  trigger  ,  otherName  )  ; 
schemaManager  .  addSchemaObject  (  trigger  )  ; 
break  ; 
}  catch  (  HsqlException   e  )  { 
return   Result  .  newErrorResult  (  e  ,  sql  )  ; 
} 
} 
case   StatementTypes  .  CREATE_CAST  : 
return   Result  .  updateZeroResult  ; 
case   StatementTypes  .  CREATE_TYPE  : 
{ 
Type   type  =  (  Type  )  arguments  [  0  ]  ; 
try  { 
setOrCheckObjectName  (  session  ,  null  ,  type  .  getName  (  )  ,  true  )  ; 
schemaManager  .  addSchemaObject  (  type  )  ; 
break  ; 
}  catch  (  HsqlException   e  )  { 
return   Result  .  newErrorResult  (  e  ,  sql  )  ; 
} 
} 
case   StatementTypes  .  CREATE_ORDERING  : 
return   Result  .  updateZeroResult  ; 
case   StatementTypes  .  CREATE_VIEW  : 
{ 
View   view  =  (  View  )  arguments  [  0  ]  ; 
try  { 
checkSchemaUpdateAuthorisation  (  session  ,  view  .  getSchemaName  (  )  )  ; 
schemaManager  .  checkSchemaObjectNotExists  (  view  .  getName  (  )  )  ; 
view  .  compile  (  session  ,  null  )  ; 
schemaManager  .  addSchemaObject  (  view  )  ; 
break  ; 
}  catch  (  HsqlException   e  )  { 
return   Result  .  newErrorResult  (  e  ,  sql  )  ; 
} 
} 
case   StatementTypes  .  CREATE_INDEX  : 
{ 
Table   table  ; 
HsqlName   name  ; 
int  [  ]  indexColumns  ; 
boolean   unique  ; 
table  =  (  Table  )  arguments  [  0  ]  ; 
indexColumns  =  (  int  [  ]  )  arguments  [  1  ]  ; 
name  =  (  HsqlName  )  arguments  [  2  ]  ; 
unique  =  (  (  Boolean  )  arguments  [  3  ]  )  .  booleanValue  (  )  ; 
try  { 
setOrCheckObjectName  (  session  ,  table  .  getName  (  )  ,  name  ,  true  )  ; 
TableWorks   tableWorks  =  new   TableWorks  (  session  ,  table  )  ; 
tableWorks  .  addIndex  (  indexColumns  ,  name  ,  unique  )  ; 
break  ; 
}  catch  (  HsqlException   e  )  { 
return   Result  .  newErrorResult  (  e  ,  sql  )  ; 
} 
} 
case   StatementTypes  .  COMMENT  : 
{ 
HsqlName   name  =  (  HsqlName  )  arguments  [  0  ]  ; 
String   comment  =  (  String  )  arguments  [  1  ]  ; 
switch  (  name  .  type  )  { 
case   SchemaObject  .  COLUMN  : 
{ 
Table   table  =  (  Table  )  schemaManager  .  getSchemaObject  (  name  .  parent  .  name  ,  name  .  parent  .  schema  .  name  ,  SchemaObject  .  TABLE  )  ; 
if  (  !  session  .  getGrantee  (  )  .  isFullyAccessibleByRole  (  table  .  getName  (  )  )  )  { 
throw   Error  .  error  (  ErrorCode  .  X_42501  )  ; 
} 
int   index  =  table  .  getColumnIndex  (  name  .  name  )  ; 
if  (  index  <  0  )  { 
throw   Error  .  error  (  ErrorCode  .  X_42501  )  ; 
} 
ColumnSchema   column  =  table  .  getColumn  (  index  )  ; 
column  .  getName  (  )  .  comment  =  comment  ; 
break  ; 
} 
case   SchemaObject  .  ROUTINE  : 
{ 
RoutineSchema   routine  =  (  RoutineSchema  )  schemaManager  .  getSchemaObject  (  name  .  name  ,  name  .  schema  .  name  ,  SchemaObject  .  ROUTINE  )  ; 
if  (  !  session  .  getGrantee  (  )  .  isFullyAccessibleByRole  (  routine  .  getName  (  )  )  )  { 
throw   Error  .  error  (  ErrorCode  .  X_42501  )  ; 
} 
routine  .  getName  (  )  .  comment  =  comment  ; 
break  ; 
} 
case   SchemaObject  .  TABLE  : 
{ 
Table   table  =  (  Table  )  schemaManager  .  getSchemaObject  (  name  .  name  ,  name  .  schema  .  name  ,  SchemaObject  .  TABLE  )  ; 
if  (  !  session  .  getGrantee  (  )  .  isFullyAccessibleByRole  (  table  .  getName  (  )  )  )  { 
throw   Error  .  error  (  ErrorCode  .  X_42501  )  ; 
} 
table  .  getName  (  )  .  comment  =  comment  ; 
break  ; 
} 
} 
break  ; 
} 
case   StatementTypes  .  LOG_SCHEMA_STATEMENT  : 
break  ; 
default  : 
throw   Error  .  runtimeError  (  ErrorCode  .  U_S0500  ,  "StatementSchema"  )  ; 
} 
return   Result  .  updateZeroResult  ; 
} 

private   void   dropType  (  Session   session  ,  HsqlName   name  ,  boolean   cascade  )  { 
checkSchemaUpdateAuthorisation  (  session  ,  name  .  schema  )  ; 
Type   distinct  =  (  Type  )  session  .  database  .  schemaManager  .  getSchemaObject  (  name  )  ; 
session  .  database  .  schemaManager  .  removeSchemaObject  (  name  ,  cascade  )  ; 
distinct  .  userTypeModifier  =  null  ; 
} 

private   static   void   dropDomain  (  Session   session  ,  HsqlName   name  ,  boolean   cascade  )  { 
Type   domain  =  (  Type  )  session  .  database  .  schemaManager  .  getSchemaObject  (  name  )  ; 
OrderedHashSet   set  =  session  .  database  .  schemaManager  .  getReferencingObjectNames  (  domain  .  getName  (  )  )  ; 
if  (  !  cascade  &&  set  .  size  (  )  >  0  )  { 
HsqlName   objectName  =  (  HsqlName  )  set  .  get  (  0  )  ; 
throw   Error  .  error  (  ErrorCode  .  X_42502  ,  objectName  .  getSchemaQualifiedStatementName  (  )  )  ; 
} 
Constraint  [  ]  constraints  =  domain  .  userTypeModifier  .  getConstraints  (  )  ; 
set  =  new   OrderedHashSet  (  )  ; 
for  (  int   i  =  0  ;  i  <  constraints  .  length  ;  i  ++  )  { 
set  .  add  (  constraints  [  i  ]  .  getName  (  )  )  ; 
} 
session  .  database  .  schemaManager  .  removeSchemaObjects  (  set  )  ; 
session  .  database  .  schemaManager  .  removeSchemaObject  (  domain  .  getName  (  )  ,  cascade  )  ; 
domain  .  userTypeModifier  =  null  ; 
} 

private   static   void   dropRole  (  Session   session  ,  HsqlName   name  ,  boolean   cascade  )  { 
Grantee   role  =  session  .  database  .  getGranteeManager  (  )  .  getRole  (  name  .  name  )  ; 
if  (  !  cascade  &&  session  .  database  .  schemaManager  .  hasSchemas  (  role  )  )  { 
HsqlArrayList   list  =  session  .  database  .  schemaManager  .  getSchemas  (  role  )  ; 
Schema   schema  =  (  Schema  )  list  .  get  (  0  )  ; 
throw   Error  .  error  (  ErrorCode  .  X_42502  ,  schema  .  getName  (  )  .  statementName  )  ; 
} 
session  .  database  .  schemaManager  .  dropSchemas  (  session  ,  role  ,  cascade  )  ; 
session  .  database  .  getGranteeManager  (  )  .  dropRole  (  name  .  name  )  ; 
} 

private   static   void   dropUser  (  Session   session  ,  HsqlName   name  ,  boolean   cascade  )  { 
Grantee   grantee  =  session  .  database  .  getUserManager  (  )  .  get  (  name  .  name  )  ; 
if  (  session  .  database  .  getSessionManager  (  )  .  isUserActive  (  name  .  name  )  )  { 
throw   Error  .  error  (  ErrorCode  .  X_42539  )  ; 
} 
if  (  !  cascade  &&  session  .  database  .  schemaManager  .  hasSchemas  (  grantee  )  )  { 
HsqlArrayList   list  =  session  .  database  .  schemaManager  .  getSchemas  (  grantee  )  ; 
Schema   schema  =  (  Schema  )  list  .  get  (  0  )  ; 
throw   Error  .  error  (  ErrorCode  .  X_42502  ,  schema  .  getName  (  )  .  statementName  )  ; 
} 
session  .  database  .  schemaManager  .  dropSchemas  (  session  ,  grantee  ,  cascade  )  ; 
session  .  database  .  getUserManager  (  )  .  dropUser  (  name  .  name  )  ; 
} 

private   void   dropSchema  (  Session   session  ,  HsqlName   name  ,  boolean   cascade  )  { 
HsqlName   schema  =  session  .  database  .  schemaManager  .  getUserSchemaHsqlName  (  name  .  name  )  ; 
checkSchemaUpdateAuthorisation  (  session  ,  schema  )  ; 
session  .  database  .  schemaManager  .  dropSchema  (  session  ,  name  .  name  ,  cascade  )  ; 
} 

private   void   dropRoutine  (  Session   session  ,  HsqlName   name  ,  boolean   cascade  )  { 
checkSchemaUpdateAuthorisation  (  session  ,  name  .  schema  )  ; 
session  .  database  .  schemaManager  .  removeSchemaObject  (  name  ,  cascade  )  ; 
} 

private   void   dropObject  (  Session   session  ,  HsqlName   name  ,  boolean   cascade  )  { 
name  =  session  .  database  .  schemaManager  .  getSchemaObjectName  (  name  .  schema  ,  name  .  name  ,  name  .  type  ,  true  )  ; 
session  .  database  .  schemaManager  .  removeSchemaObject  (  name  ,  cascade  )  ; 
} 

private   void   dropTable  (  Session   session  ,  HsqlName   name  ,  boolean   cascade  )  { 
Table   table  =  session  .  database  .  schemaManager  .  findUserTable  (  session  ,  name  .  name  ,  name  .  schema  .  name  )  ; 
session  .  database  .  schemaManager  .  dropTableOrView  (  session  ,  table  ,  cascade  )  ; 
} 

static   void   checkSchemaUpdateAuthorisation  (  Session   session  ,  HsqlName   schema  )  { 
if  (  session  .  isProcessingLog  )  { 
return  ; 
} 
if  (  SqlInvariants  .  isSystemSchemaName  (  schema  .  name  )  )  { 
throw   Error  .  error  (  ErrorCode  .  X_42503  )  ; 
} 
if  (  session  .  parser  .  isSchemaDefinition  )  { 
if  (  schema  ==  session  .  getCurrentSchemaHsqlName  (  )  )  { 
return  ; 
} 
Error  .  error  (  ErrorCode  .  X_42505  ,  schema  .  name  )  ; 
} 
session  .  getGrantee  (  )  .  checkSchemaUpdateOrGrantRights  (  schema  .  name  )  ; 
session  .  checkDDLWrite  (  )  ; 
} 

void   setOrCheckObjectName  (  Session   session  ,  HsqlName   parent  ,  HsqlName   name  ,  boolean   check  )  { 
if  (  name  .  schema  ==  null  )  { 
name  .  schema  =  schemaName  ==  null  ?  session  .  getCurrentSchemaHsqlName  (  )  :  schemaName  ; 
}  else  { 
name  .  schema  =  session  .  getSchemaHsqlName  (  name  .  schema  .  name  )  ; 
if  (  name  .  schema  ==  null  )  { 
throw   Error  .  error  (  ErrorCode  .  X_42505  )  ; 
} 
if  (  isSchemaDefinition  &&  schemaName  !=  name  .  schema  )  { 
throw   Error  .  error  (  ErrorCode  .  X_42505  )  ; 
} 
} 
name  .  parent  =  parent  ; 
if  (  !  isSchemaDefinition  )  { 
checkSchemaUpdateAuthorisation  (  session  ,  name  .  schema  )  ; 
} 
if  (  check  )  { 
session  .  database  .  schemaManager  .  checkSchemaObjectNotExists  (  name  )  ; 
} 
} 

void   setSchemaName  (  Session   session  ,  HsqlName   parent  ,  HsqlName   name  )  { 
if  (  name  .  schema  ==  null  )  { 
name  .  schema  =  schemaName  ==  null  ?  session  .  getCurrentSchemaHsqlName  (  )  :  schemaName  ; 
}  else  { 
name  .  schema  =  session  .  getSchemaHsqlName  (  name  .  schema  .  name  )  ; 
if  (  name  .  schema  ==  null  )  { 
throw   Error  .  error  (  ErrorCode  .  X_42505  )  ; 
} 
if  (  isSchemaDefinition  &&  schemaName  !=  name  .  schema  )  { 
throw   Error  .  error  (  ErrorCode  .  X_42505  )  ; 
} 
} 
} 

public   boolean   isAutoCommitStatement  (  )  { 
return   true  ; 
} 

public   String   describe  (  Session   session  )  { 
return   sql  ; 
} 
} 
