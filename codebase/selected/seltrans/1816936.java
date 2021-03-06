package   anakata  .  sound  .  output  ; 

import   javax  .  sound  .  sampled  .  *  ; 
import   anakata  .  modplay  .  player  .  PlayerException  ; 
import   anakata  .  util  .  Logger  ; 









public   class   JavaSoundOutput   implements   Output  { 

private   int  [  ]  supportedRates  =  {  11025  ,  22050  ,  44100  }  ; 

private   int  [  ]  supportedBits  =  {  8  ,  16  }  ; 

private   int  [  ]  supportedChannels  =  {  1  ,  2  }  ; 

private   SoundDataFormat   format  ; 

private   SourceDataLine   sdl  ; 

private   AudioFormat   af  ; 

private   boolean   opened  =  false  ; 

private   int   bufferTime  ; 

private   boolean   signed  =  true  ; 

private   boolean   bigendian  =  false  ; 

private   long   deliveredData  =  0  ; 






public   JavaSoundOutput  (  SoundDataFormat   format  ,  int   bufferTime  )  throws   PlayerException  { 
this  .  bufferTime  =  bufferTime  ; 
this  .  format  =  format  ; 
init  (  )  ; 
} 

public   boolean   supports  (  SoundDataFormat   format  )  { 
boolean   rateSupported  =  false  ; 
int   rate  =  format  .  getRate  (  )  ; 
for  (  int   n  =  0  ;  n  <  supportedRates  .  length  ;  n  ++  )  if  (  supportedRates  [  n  ]  ==  rate  )  rateSupported  =  true  ; 
boolean   bitsSupported  =  false  ; 
int   bits  =  format  .  getBits  (  )  ; 
for  (  int   n  =  0  ;  n  <  supportedBits  .  length  ;  n  ++  )  if  (  supportedBits  [  n  ]  ==  bits  )  bitsSupported  =  true  ; 
boolean   channelsSupported  =  false  ; 
int   channels  =  format  .  getChannels  (  )  ; 
for  (  int   n  =  0  ;  n  <  supportedChannels  .  length  ;  n  ++  )  if  (  supportedChannels  [  n  ]  ==  channels  )  channelsSupported  =  true  ; 
return   rateSupported  &&  bitsSupported  &&  channelsSupported  ; 
} 

public   SoundDataFormat   getFormat  (  )  { 
return   format  ; 
} 

private   void   init  (  )  throws   PlayerException  { 
if  (  !  supports  (  format  )  )  throw   new   PlayerException  (  "data format not supported: "  +  format  )  ; 
af  =  new   AudioFormat  (  format  .  getRate  (  )  ,  format  .  getBits  (  )  ,  format  .  getChannels  (  )  ,  signed  ,  bigendian  )  ; 
DataLine  .  Info   dli  =  new   DataLine  .  Info  (  SourceDataLine  .  class  ,  af  )  ; 
if  (  AudioSystem  .  isLineSupported  (  dli  )  )  { 
Line   l  =  null  ; 
try  { 
l  =  AudioSystem  .  getLine  (  dli  )  ; 
}  catch  (  LineUnavailableException   e  )  { 
throw   new   PlayerException  (  "Can't get line"  ,  e  )  ; 
} 
if  (  !  (  l   instanceof   SourceDataLine  )  )  throw   new   PlayerException  (  "line not a SourceDataLine!"  )  ; 
sdl  =  (  SourceDataLine  )  l  ; 
}  else   throw   new   PlayerException  (  "Line not supported"  )  ; 
if  (  sdl  ==  null  )  throw   new   PlayerException  (  "line not found"  )  ; 
} 

public   boolean   isOpen  (  )  { 
return   opened  ; 
} 

public   boolean   open  (  )  { 
boolean   res  =  true  ; 
opened  =  true  ; 
try  { 
sdl  .  open  (  af  )  ; 
sdl  .  start  (  )  ; 
}  catch  (  Exception   e  )  { 
res  =  false  ; 
opened  =  false  ; 
} 
return   res  ; 
} 

public   boolean   close  (  )  { 
boolean   res  =  true  ; 
try  { 
sdl  .  stop  (  )  ; 
sdl  .  close  (  )  ; 
}  catch  (  Exception   e  )  { 
Logger  .  warning  (  "Could not close or stop SoundDataLine: "  +  sdl  )  ; 
res  =  false  ; 
} 
opened  =  false  ; 
return   res  ; 
} 

public   int   write  (  byte  [  ]  data  ,  int   ofs  ,  int   len  )  { 
int   maxTries  =  10  ; 
int   totWrite  =  0  ; 
int   nofs  =  ofs  ,  nlen  =  len  ; 
while  (  nofs  <  ofs  +  len  &&  maxTries  >  0  )  { 
int   written  =  0  ; 
if  (  format  .  getBits  (  )  ==  16  &&  format  .  getChannels  (  )  ==  2  &&  format  .  getRate  (  )  ==  INPUT_RATE  )  written  =  sdl  .  write  (  data  ,  nofs  ,  nlen  )  ;  else   written  =  writeConv  (  data  ,  nofs  ,  nlen  )  ; 
nofs  +=  written  ; 
nlen  -=  written  ; 
totWrite  +=  written  ; 
maxTries  --  ; 
deliveredData  +=  written  ; 
int   deliveredTime  =  (  int  )  (  1000  *  deliveredData  /  (  INPUT_RATE  *  4  )  )  ; 
long   pos  =  sdl  .  getMicrosecondPosition  (  )  /  1000  ; 
long   sleepTime  =  deliveredTime  -  pos  -  bufferTime  ; 
if  (  sleepTime  <  0  )  sleepTime  =  0  ; 
try  { 
Thread  .  sleep  (  sleepTime  )  ; 
}  catch  (  InterruptedException   e  )  { 
} 
} 
return   totWrite  ; 
} 

byte  [  ]  mixbuffer  =  new   byte  [  1000  ]  ; 

protected   int   writeConv  (  byte  [  ]  data  ,  int   ofs  ,  int   len  )  { 
double   grad  =  (  double  )  INPUT_RATE  /  format  .  getRate  (  )  ; 
int   length  =  0  ; 
if  (  format  .  getBits  (  )  ==  8  &&  format  .  getChannels  (  )  ==  1  )  length  =  mono8  (  data  ,  ofs  ,  len  ,  grad  )  ;  else   if  (  format  .  getBits  (  )  ==  8  &&  format  .  getChannels  (  )  ==  2  )  length  =  stereo8  (  data  ,  ofs  ,  len  ,  grad  )  ;  else   if  (  format  .  getBits  (  )  ==  16  &&  format  .  getChannels  (  )  ==  1  )  length  =  mono16  (  data  ,  ofs  ,  len  ,  grad  )  ;  else   if  (  format  .  getBits  (  )  ==  16  &&  format  .  getChannels  (  )  ==  2  )  length  =  stereo16  (  data  ,  ofs  ,  len  ,  grad  )  ; 
return   length  ; 
} 

protected   int   mono8  (  byte  [  ]  data  ,  int   ofs  ,  int   len  ,  double   grad  )  { 
int   offset  =  ofs  ; 
double   add  =  0  ; 
int   n  ; 
while  (  offset  <  len  +  ofs  )  { 
for  (  n  =  0  ;  offset  <  len  +  ofs  &&  n  <  mixbuffer  .  length  ;  n  ++  ,  offset  =  ofs  +  (  (  int  )  add  )  *  4  )  { 
mixbuffer  [  n  ]  =  (  byte  )  (  (  data  [  offset  +  1  ]  +  data  [  offset  +  3  ]  )  >  >  1  )  ; 
add  +=  grad  ; 
} 
writeData  (  mixbuffer  ,  0  ,  n  )  ; 
} 
return   offset  -  ofs  ; 
} 

protected   int   stereo8  (  byte  [  ]  data  ,  int   ofs  ,  int   len  ,  double   grad  )  { 
int   offset  =  ofs  ; 
double   add  =  0  ; 
int   n  ; 
while  (  offset  <  len  +  ofs  )  { 
for  (  n  =  0  ;  offset  <  len  +  ofs  &&  n  <  mixbuffer  .  length  ;  n  +=  2  ,  offset  =  ofs  +  (  (  int  )  add  )  *  4  )  { 
mixbuffer  [  n  +  0  ]  =  (  byte  )  (  data  [  offset  +  1  ]  )  ; 
mixbuffer  [  n  +  1  ]  =  (  byte  )  (  data  [  offset  +  3  ]  )  ; 
add  +=  grad  ; 
} 
writeData  (  mixbuffer  ,  0  ,  n  )  ; 
} 
return   offset  -  ofs  ; 
} 

protected   int   mono16  (  byte  [  ]  data  ,  int   ofs  ,  int   len  ,  double   grad  )  { 
int   offset  =  0  ; 
double   add  =  0  ; 
int   n  ; 
while  (  offset  <  len  +  ofs  )  { 
for  (  n  =  0  ;  offset  <  len  +  ofs  &&  n  <  mixbuffer  .  length  ;  n  +=  2  ,  offset  =  ofs  +  (  int  )  add  *  4  )  { 
mixbuffer  [  n  +  0  ]  =  (  byte  )  (  (  data  [  offset  +  0  ]  +  data  [  offset  +  2  ]  )  >  >  1  )  ; 
mixbuffer  [  n  +  1  ]  =  (  byte  )  (  (  data  [  offset  +  1  ]  +  data  [  offset  +  3  ]  )  >  >  1  )  ; 
add  +=  grad  ; 
} 
writeData  (  mixbuffer  ,  0  ,  n  )  ; 
} 
return   offset  -  ofs  ; 
} 

protected   int   stereo16  (  byte  [  ]  data  ,  int   ofs  ,  int   len  ,  double   grad  )  { 
int   offset  =  0  ; 
double   add  =  0  ; 
int   n  ; 
while  (  offset  <  len  +  ofs  )  { 
for  (  n  =  0  ;  offset  <  len  +  ofs  &&  n  <  mixbuffer  .  length  ;  n  +=  4  ,  offset  =  ofs  +  (  int  )  add  *  4  )  { 
mixbuffer  [  n  +  0  ]  =  data  [  offset  +  0  ]  ; 
mixbuffer  [  n  +  1  ]  =  data  [  offset  +  1  ]  ; 
mixbuffer  [  n  +  2  ]  =  data  [  offset  +  2  ]  ; 
mixbuffer  [  n  +  3  ]  =  data  [  offset  +  3  ]  ; 
add  +=  grad  ; 
} 
writeData  (  mixbuffer  ,  0  ,  n  )  ; 
} 
return   offset  -  ofs  ; 
} 









private   void   writeData  (  byte  [  ]  data  ,  int   ofs  ,  int   len  )  { 
int   count  =  0  ; 
int   written  =  0  ; 
while  (  written  <  len  )  { 
written  +=  sdl  .  write  (  data  ,  ofs  +  written  ,  len  -  written  )  ; 
count  ++  ; 
if  (  count  >=  100  )  { 
try  { 
Thread  .  sleep  (  10  )  ; 
}  catch  (  InterruptedException   e  )  { 
} 
count  =  0  ; 
} 
} 
} 






public   static   void   main  (  String  [  ]  args  )  { 
try  { 
int   rate  =  INPUT_RATE  ; 
byte  [  ]  data  =  new   byte  [  10  *  2  *  2  *  rate  ]  ; 
for  (  int   n  =  0  ;  n  <  data  .  length  ;  n  +=  4  )  { 
int   s1  =  (  int  )  (  Math  .  sin  (  n  *  2  *  3.14  *  100  /  rate  )  *  32767  )  ; 
int   s2  =  (  int  )  (  Math  .  sin  (  n  *  2  *  3.14  *  100  /  rate  )  *  32767  )  ; 
data  [  n  +  0  ]  =  (  byte  )  s1  ; 
data  [  n  +  1  ]  =  (  byte  )  (  s1  >  >  >  8  )  ; 
data  [  n  +  2  ]  =  (  byte  )  s2  ; 
data  [  n  +  3  ]  =  (  byte  )  (  s2  >  >  >  8  )  ; 
} 
SoundDataFormat   sdf  =  new   SoundDataFormat  (  8  ,  rate  ,  2  )  ; 
JavaSoundOutput   sso  =  new   JavaSoundOutput  (  sdf  ,  300  )  ; 
boolean   ok  =  true  ; 
ok  =  sso  .  open  (  )  ; 
if  (  !  ok  )  throw   new   Exception  (  "init"  )  ; 
for  (  int   n  =  0  ;  n  <  data  .  length  ;  n  +=  8000  )  sso  .  write  (  data  ,  n  ,  8000  )  ; 
ok  =  sso  .  close  (  )  ; 
if  (  !  ok  )  throw   new   Exception  (  "init"  )  ; 
}  catch  (  Exception   e  )  { 
e  .  printStackTrace  (  )  ; 
System  .  exit  (  1  )  ; 
} 
} 
} 

