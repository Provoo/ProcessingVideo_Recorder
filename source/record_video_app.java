import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import codeanticode.gsvideo.*; 
import processing.video.*; 
import java.io.InputStreamReader; 
import codeanticode.gsvideo.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class record_video_app extends PApplet {

//Todo: Configurar driver para SO, pausar el video, agreagar filtros con los pipelines







BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
Capture video;
Timer t;
recordingThread recordT;
PImage photo;
String sTexto;
Boolean record=false;
Boolean setPrhase=true;
String nombreVideo = "tituloVideo";
String fraseVideo = "Ingresa tu frase"; 

public void setup() {
 size(640, 504);
  video = new Capture(this, 640, 480, 15);
  video.start(); 
  t= new Timer(1000);
  
  frameRate(15);
  PFont font = createFont("Ubuntu", 14);
  textFont(font, 14);
   photo = loadImage("../data/no_rec.png");
   photo.resize(0,30);
  println("\r\n\r\n______                                  _                _   _  _      _                ______                            _             \r\n| ___ \\                                (_)              | | | |(_)    | |               | ___ \\                          | |            \r\n| |_/ /_ __  ___    ___  ___  ___  ___  _  _ __    __ _ | | | | _   __| |  ___   ___    | |_/ / ___   ___  ___   _ __  __| |  ___  _ __ \r\n|  __/| '__|/ _ \\  / __|/ _ \\/ __|/ __|| || '_ \\  / _` || | | || | / _` | / _ \\ / _ \\   |    / / _ \\ / __|/ _ \\ | '__|/ _` | / _ \\| '__|\r\n| |   | |  | (_) || (__|  __/\\__ \\\\__ \\| || | | || (_| |\\ \\_/ /| || (_| ||  __/| (_) |  | |\\ \\|  __/| (__| (_) || |  | (_| ||  __/| |   \r\n\\_|   |_|   \\___/  \\___|\\___||___/|___/|_||_| |_| \\__, | \\___/ |_| \\__,_| \\___| \\___/   \\_| \\_|\\___| \\___|\\___/ |_|   \\__,_| \\___||_|   \r\n                                                   __/ |                                                                                \r\n                                                  |___/                                                                                 \r\n\r\n");
  println("Copyright (C) 2016  Carlos Torres\n    This program is free software: you can redistribute it and/or modify\r\n    it under the terms of the GNU General Public License as published by\r\n    the Free Software Foundation, either version 3 of the License, or\r\n    (at your option) any later version.\r\n\r\n    This program is distributed in the hope that it will be useful,\r\n    but WITHOUT ANY WARRANTY; without even the implied warranty of\r\n    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the\r\n    GNU General Public License for more details.\r\n\r\n    You should have received a copy of the GNU General Public License\r\n    along with this program.  If not, see <http://www.gnu.org/licenses/>.  \r Version: 0.1");
  println("\nControls: \n\n   Record: F1 \n SaveRecord: F2 \n Photo: F3 ");
  
  
}

//Principal
public void draw() {

 
   fill(0);
  noStroke();
  rect(0,480, 640, 24);
   video.read(); // Read the new frame from the camera
   video.loadPixels();
   image(video,0,0);
   image(photo, 0, 0);
   if(setPrhase){
   fill(255);
   text(fraseVideo, 35, 18);
   }

  
  
  if(keyCode== 112)
    record=true;
  if(record)
    record();
  if(keyCode== 114)
    saveFrame("photo.jpg"); 
 
 
}

//Llama al threat record 
public void record(){

  int a = recordT.getCount();
  t.Tstart();
  String s = " "+t.date()+"  "+t.Time();
  //Nuevo nombre o letras en el video
  String s2 =  t.seeTimer()+" "+fraseVideo;
  fill(255);
  photo = loadImage("../data/rec.png");
  photo.resize(0,30);
  image(photo, 0, 0);
  text(s2, 35, 18);
  text(s, 460, 495);
  loadPixels();
  recordT.addpix(pixels);

}
//Controles
public void keyPressed(){
  //Con F1 empeizas a grabar
  if(keyCode== 112){
    setPrhase=false;
    recordT = new recordingThread(1000,this, nombreVideo);
    recordT.start();  
    }
    
  //Con F2 empeizas a guardas

   if(keyCode== 113){
    setPrhase=true;
    recordT.quit();
    t.Treset(); 
    record=false;
    photo = loadImage("../data/no_rec.png");
    photo.resize(0,30);
  }
  if (keyCode == BACKSPACE) {
    if (fraseVideo.length() > 0) {
      fraseVideo = fraseVideo.substring(0, fraseVideo.length()-1);
    }
  } else if (keyCode == DELETE) {
    fraseVideo = "";
  } else if (keyCode != SHIFT && keyCode != CONTROL && keyCode != ALT) {
    fraseVideo = fraseVideo + key;
  }
  
    
 
 
  
  
}

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
   
   public void Tstart(){
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
   
  public boolean over()
  {
    if ((millis() - milis)>tOver)
    {
      milis=millis();
      return true;
    }
    else
      return false;
  }
   
  public void Treset(){
   minute = 0;
   second = 0;
   hour = 0;
  
  }
   
  public void setOver(int millisecs)
  {
    tOver=millisecs;
    Treset();
  }
 ////////////////////////////////////////////////////////////
public String seeTimer(){
   return nf(hour,2)+":"+nf(minute,2)+":"+nf(second,2);
   } 
////////////////////////////////////////////////////////////
public String Time(){
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



class recordingThread extends Thread {
 
  PApplet papplet;
  GSPipeline audio;
  GSMovieMaker mm;
  boolean running;           
  String id;                 
  int count;
  int wait; 
  String path= dataPath("");
  String nombreVideo;
  GSPipeline recor;

  
  
  
  
  recordingThread ( int wait, PApplet papplet, String nombreVideo) {
    this.papplet = papplet;
    this.wait = wait;
    this.nombreVideo=nombreVideo;
    running = false; 
    count = 0;
    audio = new GSPipeline(papplet, "alsasrc ! audioconvert ! vorbisenc ! oggmux ! filesink location="+path+"/audioP.ogg", GSVideo.AUDIO);


    
  }
 
  public int getCount() {
    return count;
  }
 
  // Overriding "start()"
  public void start () {
    
    // Set cuando se igual true
    audio.play();
    running = true;
    mm = new GSMovieMaker(papplet, width, height, path+"/videoP.ogg", GSMovieMaker.THEORA, GSMovieMaker.HIGH,15);
    mm.setQueueSize(50, 10);
    mm.start();
    
    // Puedes imprimir mensajes
    println("Empezando el threat de recordo"+papplet); 
    // Do whatever start does in Thread, don't forget this!
    super.start();
  }
  
 //agrega el array de pixeles de cada frame
 public void addpix(int []pix){
   mm.addFrame(pix);
 }
 
  // Plantilla run encadenada start() en base al modelo de Threat Processing
  public void run () {
    while (running) {
      println(id + ": " + count);
      count++;
    
    
      // Tiempo de espera del threat
      try {
        sleep((long)(wait));
      } catch (Exception e) {
      }
    }
    System.out.println(id + " tu video ha sido guardado! revisa el siguiente link: "+path);  // Mensaje de finalizaci\u00f3n
  }
 
 
  // Metodo de finiquitaci\u00f3n
  public void quit() {
    System.out.println("Quitting."); 
    running = false;  // Setiando running  a false para dar terminar el loop in run()
  
    mm.finish();
    audio.stop();
    //Lanzamos el el pipeline que hara el mux del archivo de video y audio
    recor = new GSPipeline(papplet, "filesrc location="+path+"/videoP.ogg ! oggdemux ! queue ! oggmux name=mux ! filesink location="+path+"/"+nombreVideo+".ogg filesrc location="+path+"/audioP.ogg ! decodebin ! audioconvert ! vorbisenc ! queue ! mux.",GSVideo.AUDIO );
    recor.play();
    interrupt();
  }
}
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "record_video_app" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
