package   edu  .  cmu  .  sphinx  .  linguist  .  acoustic  .  tiedstate  ; 

import   edu  .  cmu  .  sphinx  .  linguist  .  acoustic  .  *  ; 
import   edu  .  cmu  .  sphinx  .  linguist  .  acoustic  .  tiedstate  .  HTK  .  GMMDiag  ; 
import   edu  .  cmu  .  sphinx  .  linguist  .  acoustic  .  tiedstate  .  HTK  .  HMMSet  ; 
import   edu  .  cmu  .  sphinx  .  linguist  .  acoustic  .  tiedstate  .  HTK  .  HMMState  ; 
import   edu  .  cmu  .  sphinx  .  linguist  .  acoustic  .  tiedstate  .  HTK  .  SingleHMM  ; 
import   static   edu  .  cmu  .  sphinx  .  linguist  .  acoustic  .  tiedstate  .  Pool  .  Feature  .  *  ; 
import   edu  .  cmu  .  sphinx  .  util  .  *  ; 
import   edu  .  cmu  .  sphinx  .  util  .  props  .  *  ; 
import   java  .  io  .  *  ; 
import   java  .  net  .  URL  ; 
import   java  .  util  .  *  ; 
import   java  .  util  .  logging  .  Level  ; 
import   java  .  util  .  logging  .  Logger  ; 


































public   class   HTKLoader   implements   Loader  { 


@  S4Component  (  type  =  LogMath  .  class  ) 
public   static   final   String   PROP_LOG_MATH  =  "logMath"  ; 


@  S4Component  (  type  =  UnitManager  .  class  ) 
public   static   final   String   PROP_UNIT_MANAGER  =  "unitManager"  ; 


@  S4Boolean  (  defaultValue  =  true  ) 
public   static   final   String   PROP_IS_BINARY  =  "isBinary"  ; 


@  S4String  (  mandatory  =  true  ,  defaultValue  =  "hmmdefs"  ) 
public   static   final   String   PROP_MODEL  =  "modelDefinition"  ; 


@  S4Boolean  (  defaultValue  =  true  ) 
public   static   final   String   PROP_TIE_1PH  =  "tie1ph"  ; 


@  S4String  (  defaultValue  =  "model.props"  ) 
public   static   final   String   PROP_PROPERTIES_FILE  =  "propertiesFile"  ; 


@  S4Integer  (  defaultValue  =  39  ) 
public   static   final   String   PROP_VECTOR_LENGTH  =  "vectorLength"  ; 


@  S4Double  (  defaultValue  =  0.0f  ) 
public   static   final   String   PROP_MC_FLOOR  =  "MixtureComponentScoreFloor"  ; 


@  S4Double  (  defaultValue  =  0.0001f  ) 
public   static   final   String   PROP_VARIANCE_FLOOR  =  "varianceFloor"  ; 


@  S4Double  (  defaultValue  =  1e-7f  ) 
public   static   final   String   PROP_MW_FLOOR  =  "mixtureWeightFloor"  ; 

protected   static   final   String   FILLER  =  "filler"  ; 

protected   static   final   String   SILENCE_CIPHONE  =  "SIL"  ; 

protected   static   final   int   BYTE_ORDER_MAGIC  =  0x11223344  ; 


public   static   final   String   MODEL_VERSION  =  "0.3"  ; 

protected   static   final   int   CONTEXT_SIZE  =  1  ; 

private   Pool  <  float  [  ]  >  meansPool  ; 

private   Pool  <  float  [  ]  >  variancePool  ; 

private   Pool  <  float  [  ]  [  ]  >  matrixPool  ; 

private   Pool  <  float  [  ]  [  ]  >  meanTransformationMatrixPool  ; 

private   Pool  <  float  [  ]  >  meanTransformationVectorPool  ; 

private   Pool  <  float  [  ]  [  ]  >  varianceTransformationMatrixPool  ; 

private   Pool  <  float  [  ]  >  varianceTransformationVectorPool  ; 

private   Pool  <  float  [  ]  >  mixtureWeightsPool  ; 

private   Pool  <  Senone  >  senonePool  ; 

private   Map  <  String  ,  Unit  >  contextIndependentUnits  ; 

private   HMMManager   hmmManager  ; 

private   LogMath   logMath  ; 

private   UnitManager   unitManager  ; 

private   Properties   properties  ; 

private   boolean   swap  ; 

protected   static   final   String   DENSITY_FILE_VERSION  =  "1.0"  ; 

protected   static   final   String   MIXW_FILE_VERSION  =  "1.0"  ; 

protected   static   final   String   TMAT_FILE_VERSION  =  "1.0"  ; 

private   String   name  ; 

private   Logger   logger  ; 

private   String   location  ; 

private   String   model  ; 

private   String   dataDir  ; 

private   String   propsFile  ; 

private   float   distFloor  ; 

private   float   mixtureWeightFloor  ; 

private   float   varianceFloor  ; 

private   boolean   useCDUnits  ; 

private   boolean   loaded  ; 

private   boolean   tie1ph  ; 

public   HTKLoader  (  String   propsFile  ,  LogMath   logMath  ,  UnitManager   unitManager  ,  boolean   isBinary  ,  int   vectorLength  ,  String   model  ,  boolean   tie1ph  ,  float   distFloor  ,  float   mixtureWeightFloor  ,  float   varianceFloor  )  { 
this  .  logger  =  Logger  .  getLogger  (  getClass  (  )  .  getName  (  )  )  ; 
this  .  propsFile  =  propsFile  ; 
loadProperties  (  )  ; 
this  .  logMath  =  logMath  ; 
this  .  unitManager  =  unitManager  ; 
this  .  model  =  model  ; 
this  .  tie1ph  =  tie1ph  ; 
this  .  distFloor  =  distFloor  ; 
this  .  mixtureWeightFloor  =  mixtureWeightFloor  ; 
this  .  varianceFloor  =  varianceFloor  ; 
} 

public   HTKLoader  (  )  { 
} 

@  Override 
public   void   newProperties  (  PropertySheet   ps  )  throws   PropertyException  { 
logger  =  ps  .  getLogger  (  )  ; 
propsFile  =  ps  .  getString  (  PROP_PROPERTIES_FILE  )  ; 
loadProperties  (  )  ; 
logMath  =  (  LogMath  )  ps  .  getComponent  (  PROP_LOG_MATH  )  ; 
unitManager  =  (  UnitManager  )  ps  .  getComponent  (  PROP_UNIT_MANAGER  )  ; 
String   mdef  =  (  String  )  properties  .  get  (  PROP_MODEL  )  ; 
model  =  mdef  !=  null  ?  mdef  :  ps  .  getString  (  PROP_MODEL  )  ; 
tie1ph  =  ps  .  getBoolean  (  PROP_TIE_1PH  )  ; 
distFloor  =  ps  .  getFloat  (  PROP_MC_FLOOR  )  ; 
mixtureWeightFloor  =  ps  .  getFloat  (  PROP_MW_FLOOR  )  ; 
varianceFloor  =  ps  .  getFloat  (  PROP_VARIANCE_FLOOR  )  ; 
} 

private   void   loadProperties  (  )  { 
if  (  properties  ==  null  )  { 
properties  =  new   Properties  (  )  ; 
try  { 
URL   url  =  getClass  (  )  .  getResource  (  propsFile  )  ; 
if  (  url  !=  null  )  properties  .  load  (  url  .  openStream  (  )  )  ; 
}  catch  (  IOException   ioe  )  { 
ioe  .  printStackTrace  (  )  ; 
} 
} 
} 

@  Override 
public   void   load  (  )  throws   IOException  { 
if  (  !  loaded  )  { 
hmmManager  =  new   HMMManager  (  )  ; 
contextIndependentUnits  =  new   LinkedHashMap  <  String  ,  Unit  >  (  )  ; 
meanTransformationMatrixPool  =  null  ; 
meanTransformationVectorPool  =  null  ; 
varianceTransformationMatrixPool  =  null  ; 
varianceTransformationVectorPool  =  null  ; 
loadModelFiles  (  model  )  ; 
System  .  err  .  println  (  "HTK -> S4 conversion finished"  )  ; 
loaded  =  true  ; 
} 
} 

public   String   getName  (  )  { 
return   name  ; 
} 






protected   Properties   getProperties  (  )  { 
if  (  properties  ==  null  )  { 
loadProperties  (  )  ; 
} 
return   properties  ; 
} 






protected   String   getLocation  (  )  { 
return   location  ; 
} 










private   void   loadModelFiles  (  String   MMFname  )  throws   IOException  { 
logger  .  config  (  "Loading HTK acoustic model: "  +  MMFname  )  ; 
logger  .  config  (  "    Path      : "  +  location  )  ; 
logger  .  config  (  "    modellName: "  +  model  )  ; 
logger  .  config  (  "    dataDir   : "  +  dataDir  )  ; 
HTKStruct   htkmods  =  new   HTKStruct  (  )  ; 
htkmods  .  load  (  MMFname  )  ; 
meansPool  =  htkmods  .  htkMeans  (  MMFname  )  ; 
variancePool  =  htkmods  .  htkVars  (  MMFname  ,  varianceFloor  )  ; 
mixtureWeightsPool  =  htkmods  .  htkWeights  (  MMFname  ,  mixtureWeightFloor  )  ; 
matrixPool  =  htkmods  .  htkTrans  (  MMFname  )  ; 
senonePool  =  createSenonePool  (  distFloor  ,  varianceFloor  )  ; 
loadHMMPool  (  useCDUnits  ,  htkmods  ,  location  +  File  .  separator  +  model  )  ; 
} 

@  Override 
public   Map  <  String  ,  Unit  >  getContextIndependentUnits  (  )  { 
return   contextIndependentUnits  ; 
} 









private   Pool  <  Senone  >  createSenonePool  (  float   distFloor  ,  float   varianceFloor  )  { 
Pool  <  Senone  >  pool  =  new   Pool  <  Senone  >  (  "senones"  )  ; 
int   numMixtureWeights  =  mixtureWeightsPool  .  size  (  )  ; 
int   numMeans  =  meansPool  .  size  (  )  ; 
int   numVariances  =  variancePool  .  size  (  )  ; 
int   numGaussiansPerSenone  =  mixtureWeightsPool  .  getFeature  (  NUM_GAUSSIANS_PER_STATE  ,  0  )  ; 
int   numSenones  =  mixtureWeightsPool  .  getFeature  (  NUM_SENONES  ,  0  )  ; 
int   whichGaussian  =  0  ; 
logger  .  fine  (  "NG "  +  numGaussiansPerSenone  )  ; 
logger  .  fine  (  "NS "  +  numSenones  )  ; 
logger  .  fine  (  "NMIX "  +  numMixtureWeights  )  ; 
logger  .  fine  (  "NMNS "  +  numMeans  )  ; 
logger  .  fine  (  "NMNS "  +  numVariances  )  ; 
assert   numGaussiansPerSenone  >  0  ; 
assert   numMixtureWeights  ==  numSenones  ; 
assert   numVariances  ==  numSenones  *  numGaussiansPerSenone  ; 
assert   numMeans  ==  numSenones  *  numGaussiansPerSenone  ; 
float  [  ]  [  ]  meansTransformationMatrix  =  meanTransformationMatrixPool  ==  null  ?  null  :  meanTransformationMatrixPool  .  get  (  0  )  ; 
float  [  ]  meansTransformationVector  =  meanTransformationVectorPool  ==  null  ?  null  :  meanTransformationVectorPool  .  get  (  0  )  ; 
float  [  ]  [  ]  varianceTransformationMatrix  =  varianceTransformationMatrixPool  ==  null  ?  null  :  varianceTransformationMatrixPool  .  get  (  0  )  ; 
float  [  ]  varianceTransformationVector  =  varianceTransformationVectorPool  ==  null  ?  null  :  varianceTransformationVectorPool  .  get  (  0  )  ; 
for  (  int   i  =  0  ;  i  <  numSenones  ;  i  ++  )  { 
MixtureComponent  [  ]  mixtureComponents  =  new   MixtureComponent  [  numGaussiansPerSenone  ]  ; 
for  (  int   j  =  0  ;  j  <  numGaussiansPerSenone  ;  j  ++  )  { 
mixtureComponents  [  j  ]  =  new   MixtureComponent  (  logMath  ,  meansPool  .  get  (  whichGaussian  )  ,  meansTransformationMatrix  ,  meansTransformationVector  ,  variancePool  .  get  (  whichGaussian  )  ,  varianceTransformationMatrix  ,  varianceTransformationVector  ,  distFloor  ,  varianceFloor  )  ; 
whichGaussian  ++  ; 
} 
Senone   senone  =  new   GaussianMixture  (  logMath  ,  mixtureWeightsPool  .  get  (  i  )  ,  mixtureComponents  ,  i  )  ; 
pool  .  put  (  i  ,  senone  )  ; 
} 
return   pool  ; 
} 










String   readWord  (  DataInputStream   dis  )  throws   IOException  { 
StringBuilder   sb  =  new   StringBuilder  (  )  ; 
char   c  ; 
do  { 
c  =  readChar  (  dis  )  ; 
}  while  (  Character  .  isWhitespace  (  c  )  )  ; 
do  { 
sb  .  append  (  c  )  ; 
c  =  readChar  (  dis  )  ; 
}  while  (  !  Character  .  isWhitespace  (  c  )  )  ; 
return   sb  .  toString  (  )  ; 
} 










private   char   readChar  (  DataInputStream   dis  )  throws   IOException  { 
return  (  char  )  dis  .  readByte  (  )  ; 
} 










protected   int   readInt  (  DataInputStream   dis  )  throws   IOException  { 
if  (  swap  )  { 
return   Utilities  .  readLittleEndianInt  (  dis  )  ; 
}  else  { 
return   dis  .  readInt  (  )  ; 
} 
} 










protected   float   readFloat  (  DataInputStream   dis  )  throws   IOException  { 
float   val  ; 
if  (  swap  )  { 
val  =  Utilities  .  readLittleEndianFloat  (  dis  )  ; 
}  else  { 
val  =  dis  .  readFloat  (  )  ; 
} 
return   val  ; 
} 











protected   float  [  ]  readFloatArray  (  DataInputStream   dis  ,  int   size  )  throws   IOException  { 
float  [  ]  data  =  new   float  [  size  ]  ; 
for  (  int   i  =  0  ;  i  <  size  ;  i  ++  )  { 
data  [  i  ]  =  readFloat  (  dis  )  ; 
} 
return   data  ; 
} 













protected   void   loadHMMPool  (  boolean   useCDUnits  ,  HTKStruct   htkModels  ,  String   path  )  throws   IOException  { 
int   numStatePerHMM  ; 
if  (  !  tie1ph  )  { 
for  (  Iterator  <  SingleHMM  >  monoPhones  =  htkModels  .  hmmsHTK  .  get1phIt  (  )  ;  monoPhones  .  hasNext  (  )  ;  )  { 
SingleHMM   hmm  =  monoPhones  .  next  (  )  ; 
if  (  hmm  ==  null  )  break  ; 
String   name  =  hmm  .  getName  (  )  ; 
String   attribute  ; 
if  (  name  .  equals  (  "sil"  )  ||  name  .  equals  (  "sp"  )  ||  name  .  equals  (  "bb"  )  ||  name  .  equals  (  "xx"  )  ||  name  .  equals  (  "hh"  )  )  attribute  =  FILLER  ;  else   attribute  =  "nofiller"  ; 
int   tmat  =  hmm  .  trIdx  ; 
numStatePerHMM  =  hmm  .  getNstates  (  )  ; 
int  [  ]  stid  =  new   int  [  hmm  .  getNbEmittingStates  (  )  ]  ; 
int   j  =  0  ; 
for  (  int   ii  =  0  ;  ii  <  numStatePerHMM  ;  ii  ++  )  { 
if  (  hmm  .  isEmitting  (  ii  )  )  { 
HMMState   s  =  hmm  .  getState  (  ii  )  ; 
stid  [  j  ]  =  htkModels  .  hmmsHTK  .  getStateIdx  (  s  )  ; 
j  ++  ; 
} 
} 
Unit   unit  =  unitManager  .  getUnit  (  name  ,  attribute  .  equals  (  FILLER  )  )  ; 
contextIndependentUnits  .  put  (  unit  .  getName  (  )  ,  unit  )  ; 
if  (  logger  .  isLoggable  (  Level  .  FINE  )  )  { 
logger  .  fine  (  "Loaded "  +  unit  )  ; 
} 
if  (  unit  .  isFiller  (  )  &&  unit  .  getName  (  )  .  equals  (  SILENCE_CIPHONE  )  )  { 
unit  =  UnitManager  .  SILENCE  ; 
} 
float  [  ]  [  ]  transitionMatrix  =  matrixPool  .  get  (  tmat  )  ; 
SenoneSequence   ss  =  getSenoneSequence  (  stid  )  ; 
HMM   hmm2  =  new   SenoneHMM  (  unit  ,  ss  ,  transitionMatrix  ,  HMMPosition  .  lookup  (  "-"  )  )  ; 
hmmManager  .  put  (  hmm2  )  ; 
} 
}  else  { 
for  (  int   i  =  0  ;  i  <  htkModels  .  hmmsHTK  .  getNhmms  (  )  ;  i  ++  )  { 
SingleHMM   hmm  =  htkModels  .  hmmsHTK  .  getHMM  (  i  )  ; 
if  (  hmm  ==  null  )  break  ; 
String   name  =  hmm  .  getBaseName  (  )  ; 
if  (  !  contextIndependentUnits  .  containsKey  (  name  )  )  { 
String   attribute  ; 
if  (  name  .  equals  (  "SIL"  )  ||  name  .  equals  (  "SP"  )  ||  name  .  equals  (  "BB"  )  ||  name  .  equals  (  "XX"  )  ||  name  .  equals  (  "HH"  )  )  attribute  =  FILLER  ;  else   attribute  =  "nofiller"  ; 
int   tmat  =  hmm  .  trIdx  ; 
numStatePerHMM  =  hmm  .  getNstates  (  )  ; 
int  [  ]  stid  =  new   int  [  hmm  .  getNbEmittingStates  (  )  ]  ; 
int   j  =  0  ; 
for  (  int   ii  =  0  ;  ii  <  numStatePerHMM  ;  ii  ++  )  { 
if  (  hmm  .  isEmitting  (  ii  )  )  { 
HMMState   s  =  hmm  .  getState  (  ii  )  ; 
stid  [  j  ]  =  htkModels  .  hmmsHTK  .  getStateIdx  (  s  )  ; 
j  ++  ; 
} 
} 
Unit   unit  =  unitManager  .  getUnit  (  name  ,  attribute  .  equals  (  FILLER  )  )  ; 
contextIndependentUnits  .  put  (  unit  .  getName  (  )  ,  unit  )  ; 
if  (  logger  .  isLoggable  (  Level  .  FINE  )  )  { 
logger  .  fine  (  "Loaded "  +  unit  )  ; 
} 
if  (  unit  .  isFiller  (  )  &&  unit  .  getName  (  )  .  equals  (  SILENCE_CIPHONE  )  )  { 
unit  =  UnitManager  .  SILENCE  ; 
} 
float  [  ]  [  ]  transitionMatrix  =  matrixPool  .  get  (  tmat  )  ; 
SenoneSequence   ss  =  getSenoneSequence  (  stid  )  ; 
HMM   hmm2  =  new   SenoneHMM  (  unit  ,  ss  ,  transitionMatrix  ,  HMMPosition  .  lookup  (  "-"  )  )  ; 
hmmManager  .  put  (  hmm2  )  ; 
} 
} 
} 
String   lastUnitName  =  ""  ; 
Unit   lastUnit  =  null  ; 
int  [  ]  lastStid  =  null  ; 
SenoneSequence   lastSenoneSequence  =  null  ; 
List  <  String  >  HMMdejavu  =  new   ArrayList  <  String  >  (  )  ; 
for  (  Iterator  <  SingleHMM  >  triPhones  =  htkModels  .  hmmsHTK  .  get3phIt  (  )  ;  triPhones  .  hasNext  (  )  ;  )  { 
SingleHMM   hmm  =  triPhones  .  next  (  )  ; 
if  (  hmm  ==  null  )  break  ; 
String   name  =  hmm  .  getBaseName  (  )  ; 
String   left  =  hmm  .  getLeft  (  )  ; 
String   right  =  hmm  .  getRight  (  )  ; 
{ 
if  (  left  .  equals  (  "-"  )  )  left  =  "SIL"  ; 
if  (  right  .  equals  (  "-"  )  )  right  =  "SIL"  ; 
String   s  =  left  +  ' '  +  name  +  ' '  +  right  ; 
if  (  HMMdejavu  .  contains  (  s  )  )  { 
continue  ; 
} 
HMMdejavu  .  add  (  s  )  ; 
} 
String   position  =  "i"  ; 
int   tmat  =  hmm  .  trIdx  ; 
numStatePerHMM  =  hmm  .  getNstates  (  )  ; 
int  [  ]  stid  =  new   int  [  hmm  .  getNbEmittingStates  (  )  ]  ; 
int   j  =  0  ; 
for  (  int   ii  =  0  ;  ii  <  numStatePerHMM  ;  ii  ++  )  { 
if  (  hmm  .  isEmitting  (  ii  )  )  { 
HMMState   s  =  hmm  .  getState  (  ii  )  ; 
stid  [  j  ]  =  htkModels  .  hmmsHTK  .  getStateIdx  (  s  )  ; 
j  ++  ; 
} 
} 
if  (  useCDUnits  )  { 
Unit   unit  ; 
String   unitName  =  (  name  +  ' '  +  left  +  ' '  +  right  )  ; 
if  (  unitName  .  equals  (  lastUnitName  )  )  { 
unit  =  lastUnit  ; 
}  else  { 
Unit  [  ]  leftContext  =  new   Unit  [  1  ]  ; 
leftContext  [  0  ]  =  contextIndependentUnits  .  get  (  left  )  ; 
Unit  [  ]  rightContext  =  new   Unit  [  1  ]  ; 
rightContext  [  0  ]  =  contextIndependentUnits  .  get  (  right  )  ; 
Context   context  =  LeftRightContext  .  get  (  leftContext  ,  rightContext  )  ; 
unit  =  unitManager  .  getUnit  (  name  ,  false  ,  context  )  ; 
} 
lastUnitName  =  unitName  ; 
lastUnit  =  unit  ; 
if  (  logger  .  isLoggable  (  Level  .  FINE  )  )  { 
logger  .  fine  (  "Loaded "  +  unit  )  ; 
} 
float  [  ]  [  ]  transitionMatrix  =  matrixPool  .  get  (  tmat  )  ; 
SenoneSequence   ss  =  lastSenoneSequence  ; 
if  (  ss  ==  null  ||  !  sameSenoneSequence  (  stid  ,  lastStid  )  )  { 
ss  =  getSenoneSequence  (  stid  )  ; 
} 
lastSenoneSequence  =  ss  ; 
lastStid  =  stid  ; 
HMM   hmm2  =  new   SenoneHMM  (  unit  ,  ss  ,  transitionMatrix  ,  HMMPosition  .  lookup  (  position  )  )  ; 
hmmManager  .  put  (  hmm2  )  ; 
} 
} 
} 







protected   boolean   sameSenoneSequence  (  int  [  ]  ssid1  ,  int  [  ]  ssid2  )  { 
if  (  ssid1  .  length  ==  ssid2  .  length  )  { 
for  (  int   i  =  0  ;  i  <  ssid1  .  length  ;  i  ++  )  { 
if  (  ssid1  [  i  ]  !=  ssid2  [  i  ]  )  { 
return   false  ; 
} 
} 
return   true  ; 
}  else  { 
return   false  ; 
} 
} 







protected   SenoneSequence   getSenoneSequence  (  int  [  ]  stateId  )  { 
Senone  [  ]  senones  =  new   Senone  [  stateId  .  length  ]  ; 
for  (  int   i  =  0  ;  i  <  stateId  .  length  ;  i  ++  )  { 
senones  [  i  ]  =  senonePool  .  get  (  stateId  [  i  ]  )  ; 
} 
return   new   SenoneSequence  (  senones  )  ; 
} 

@  Override 
public   Pool  <  float  [  ]  >  getMeansPool  (  )  { 
return   meansPool  ; 
} 

@  Override 
public   Pool  <  float  [  ]  [  ]  >  getMeansTransformationMatrixPool  (  )  { 
return   meanTransformationMatrixPool  ; 
} 

@  Override 
public   Pool  <  float  [  ]  >  getMeansTransformationVectorPool  (  )  { 
return   meanTransformationVectorPool  ; 
} 

@  Override 
public   Pool  <  float  [  ]  >  getVariancePool  (  )  { 
return   variancePool  ; 
} 

@  Override 
public   Pool  <  float  [  ]  [  ]  >  getVarianceTransformationMatrixPool  (  )  { 
return   varianceTransformationMatrixPool  ; 
} 

@  Override 
public   Pool  <  float  [  ]  >  getVarianceTransformationVectorPool  (  )  { 
return   varianceTransformationVectorPool  ; 
} 

@  Override 
public   Pool  <  float  [  ]  >  getMixtureWeightPool  (  )  { 
return   mixtureWeightsPool  ; 
} 

@  Override 
public   Pool  <  float  [  ]  [  ]  >  getTransitionMatrixPool  (  )  { 
return   matrixPool  ; 
} 

@  Override 
public   float  [  ]  [  ]  getTransformMatrix  (  )  { 
return   null  ; 
} 

@  Override 
public   Pool  <  Senone  >  getSenonePool  (  )  { 
return   senonePool  ; 
} 

@  Override 
public   int   getLeftContextSize  (  )  { 
return   CONTEXT_SIZE  ; 
} 

@  Override 
public   int   getRightContextSize  (  )  { 
return   CONTEXT_SIZE  ; 
} 

@  Override 
public   HMMManager   getHMMManager  (  )  { 
return   hmmManager  ; 
} 

@  Override 
public   void   logInfo  (  )  { 
logger  .  info  (  "HTKLoader"  )  ; 
meansPool  .  logInfo  (  logger  )  ; 
variancePool  .  logInfo  (  logger  )  ; 
matrixPool  .  logInfo  (  logger  )  ; 
senonePool  .  logInfo  (  logger  )  ; 
if  (  meanTransformationMatrixPool  !=  null  )  meanTransformationMatrixPool  .  logInfo  (  logger  )  ; 
if  (  meanTransformationVectorPool  !=  null  )  meanTransformationVectorPool  .  logInfo  (  logger  )  ; 
if  (  varianceTransformationMatrixPool  !=  null  )  varianceTransformationMatrixPool  .  logInfo  (  logger  )  ; 
if  (  varianceTransformationVectorPool  !=  null  )  varianceTransformationVectorPool  .  logInfo  (  logger  )  ; 
senonePool  .  logInfo  (  logger  )  ; 
logger  .  info  (  "Context Independent Unit Entries: "  +  contextIndependentUnits  .  size  (  )  )  ; 
hmmManager  .  logInfo  (  logger  )  ; 
} 

class   HTKStruct  { 

HMMSet   hmmsHTK  ; 

public   void   load  (  String   name  )  { 
System  .  err  .  println  (  "HTK loading..."  )  ; 
hmmsHTK  =  new   HMMSet  (  )  ; 
hmmsHTK  .  loadHTK  (  name  )  ; 
System  .  err  .  println  (  "HTK loading finished"  )  ; 
} 

int   getNumStates  (  )  { 
return   hmmsHTK  .  getNstates  (  )  ; 
} 

int   getGMMSize  (  )  { 
GMMDiag   gmm  =  hmmsHTK  .  gmms  .  get  (  0  )  ; 
return   gmm  .  getNgauss  (  )  ; 
} 

int   getNcoefs  (  )  { 
GMMDiag   gmm  =  hmmsHTK  .  gmms  .  get  (  0  )  ; 
return   gmm  .  getNcoefs  (  )  ; 
} 

int   getNumHMMs  (  )  { 
return   hmmsHTK  .  getNhmms  (  )  ; 
} 

public   Pool  <  float  [  ]  >  htkMeans  (  String   path  )  { 
Pool  <  float  [  ]  >  pool  =  new   Pool  <  float  [  ]  >  (  path  )  ; 
int   numStates  =  getNumStates  (  )  ; 
int   numStreams  =  1  ; 
int   numGaussiansPerState  =  getGMMSize  (  )  ; 
pool  .  setFeature  (  NUM_SENONES  ,  numStates  )  ; 
pool  .  setFeature  (  NUM_STREAMS  ,  numStreams  )  ; 
pool  .  setFeature  (  NUM_GAUSSIANS_PER_STATE  ,  numGaussiansPerState  )  ; 
int   ncoefs  =  getNcoefs  (  )  ; 
for  (  int   i  =  0  ;  i  <  numStates  ;  i  ++  )  { 
GMMDiag   gmm  =  hmmsHTK  .  gmms  .  get  (  i  )  ; 
for  (  int   j  =  0  ;  j  <  numGaussiansPerState  ;  j  ++  )  { 
float  [  ]  density  =  new   float  [  ncoefs  ]  ; 
for  (  int   k  =  0  ;  k  <  ncoefs  ;  k  ++  )  { 
density  [  k  ]  =  gmm  .  getMean  (  j  ,  k  )  ; 
} 
int   id  =  i  *  numGaussiansPerState  +  j  ; 
pool  .  put  (  id  ,  density  )  ; 
} 
} 
return   pool  ; 
} 

public   Pool  <  float  [  ]  >  htkVars  (  String   path  ,  float   floor  )  { 
Pool  <  float  [  ]  >  pool  =  new   Pool  <  float  [  ]  >  (  path  )  ; 
int   numStates  =  getNumStates  (  )  ; 
int   numStreams  =  1  ; 
int   numGaussiansPerState  =  getGMMSize  (  )  ; 
pool  .  setFeature  (  NUM_SENONES  ,  numStates  )  ; 
pool  .  setFeature  (  NUM_STREAMS  ,  numStreams  )  ; 
pool  .  setFeature  (  NUM_GAUSSIANS_PER_STATE  ,  numGaussiansPerState  )  ; 
int   ncoefs  =  getNcoefs  (  )  ; 
for  (  int   i  =  0  ;  i  <  numStates  ;  i  ++  )  { 
GMMDiag   gmm  =  hmmsHTK  .  gmms  .  get  (  i  )  ; 
for  (  int   j  =  0  ;  j  <  numGaussiansPerState  ;  j  ++  )  { 
float  [  ]  vars  =  new   float  [  ncoefs  ]  ; 
for  (  int   k  =  0  ;  k  <  ncoefs  ;  k  ++  )  { 
vars  [  k  ]  =  gmm  .  getVar  (  j  ,  k  )  ; 
} 
Utilities  .  floorData  (  vars  ,  varianceFloor  )  ; 
int   id  =  i  *  numGaussiansPerState  +  j  ; 
pool  .  put  (  id  ,  vars  )  ; 
} 
} 
return   pool  ; 
} 

public   Pool  <  float  [  ]  >  htkWeights  (  String   path  ,  float   floor  )  { 
Pool  <  float  [  ]  >  pool  =  new   Pool  <  float  [  ]  >  (  path  )  ; 
int   numStates  =  getNumStates  (  )  ; 
int   numStreams  =  1  ; 
int   numGaussiansPerState  =  getGMMSize  (  )  ; 
pool  .  setFeature  (  NUM_SENONES  ,  numStates  )  ; 
pool  .  setFeature  (  NUM_STREAMS  ,  numStreams  )  ; 
pool  .  setFeature  (  NUM_GAUSSIANS_PER_STATE  ,  numGaussiansPerState  )  ; 
for  (  int   i  =  0  ;  i  <  numStates  ;  i  ++  )  { 
GMMDiag   gmm  =  hmmsHTK  .  gmms  .  get  (  i  )  ; 
float  [  ]  logWeights  =  new   float  [  numGaussiansPerState  ]  ; 
for  (  int   j  =  0  ;  j  <  numGaussiansPerState  ;  j  ++  )  { 
logWeights  [  j  ]  =  gmm  .  getWeight  (  j  )  ; 
} 
Utilities  .  floorData  (  logWeights  ,  mixtureWeightFloor  )  ; 
logMath  .  linearToLog  (  logWeights  )  ; 
pool  .  put  (  i  ,  logWeights  )  ; 
} 
return   pool  ; 
} 

public   Pool  <  float  [  ]  [  ]  >  htkTrans  (  String   path  )  { 
Pool  <  float  [  ]  [  ]  >  pool  =  new   Pool  <  float  [  ]  [  ]  >  (  path  )  ; 
int   numMatrices  =  getNumHMMs  (  )  ; 
int   i  =  0  ; 
if  (  hmmsHTK  .  transitions  !=  null  )  for  (  ;  i  <  hmmsHTK  .  transitions  .  size  (  )  ;  i  ++  )  { 
float  [  ]  [  ]  tr  =  hmmsHTK  .  transitions  .  get  (  i  )  ; 
float  [  ]  [  ]  tmat  =  new   float  [  tr  .  length  ]  [  tr  [  0  ]  .  length  ]  ; 
for  (  int   j  =  0  ;  j  <  tmat  .  length  ;  j  ++  )  for  (  int   k  =  0  ;  k  <  tmat  [  j  ]  .  length  ;  k  ++  )  { 
tmat  [  j  ]  [  k  ]  =  logMath  .  linearToLog  (  tr  [  j  ]  [  k  ]  )  ; 
} 
pool  .  put  (  i  ,  tmat  )  ; 
} 
for  (  int   l  =  0  ;  l  <  numMatrices  ;  l  ++  )  { 
SingleHMM   hmm  =  hmmsHTK  .  getHMM  (  l  )  ; 
if  (  hmm  .  trans  !=  null  )  { 
float  [  ]  [  ]  tr  =  hmm  .  trans  ; 
float  [  ]  [  ]  tmat  =  new   float  [  tr  .  length  ]  [  tr  [  0  ]  .  length  ]  ; 
for  (  int   j  =  0  ;  j  <  tmat  .  length  ;  j  ++  )  for  (  int   k  =  0  ;  k  <  tmat  [  j  ]  .  length  ;  k  ++  )  { 
tmat  [  j  ]  [  k  ]  =  logMath  .  linearToLog  (  tr  [  j  ]  [  k  ]  )  ; 
} 
hmm  .  trIdx  =  i  ; 
pool  .  put  (  i  ++  ,  tmat  )  ; 
}  else  { 
hmm  .  trIdx  =  hmm  .  getTransIdx  (  )  ; 
} 
} 
return   pool  ; 
} 
} 
} 

