class Timer{
  
  int milis;
  int tOver;
  int minute = 0;
  int second = 0;
  int hour = 0; 
  Timer(int millisecs)
  {
    milis=millis();
    tOver=millisecs;
  }
   
   void Tstart(){
     if(over()){
     second++;
     }
     if(second==60){
     second=0;
     minute++;
     }
     if(minute==60){
     minute=0;
     hour++;
     }
     
    
   }
   
  boolean over()
  {
    if ((millis() - milis)>tOver)
    {
      milis=millis();
      return true;
    }
    else
      return false;
  }
   
  void Treset(){
   minute = 0;
   second = 0;
   hour = 0;
  
  }
   
  void setOver(int millisecs)
  {
    tOver=millisecs;
    Treset();
  }
 ////////////////////////////////////////////////////////////
String seeTimer(){
   return nf(hour,2)+":"+nf(minute,2)+":"+nf(second,2);
   } 
////////////////////////////////////////////////////////////
String Time(){
  String h=str(hour()),m=str(minute()),s=str(second());
   
  h=(h.length()==1)?"0"+h:h;
  m=(m.length()==1)?"0"+m:m;
  s=(s.length()==1)?"0"+s:s;
 
   
  return h+":"+m+":"+s;
}
////////////////////////////////////////////////////////////
public String date(){
  String d=str(day()),m=str(month());
   
  d=(d.length()==1)?"0"+d:d;
  m=(m.length()==1)?"0"+m:m;
 
  return d+"/"+m+"/"+str(year());
}

}
