//Todo: Configurar driver para SO, pausar el video, agreagar filtros con los pipelines


import codeanticode.gsvideo.*;
import processing.video.*;
import java.io.InputStreamReader;


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

void setup() {
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
void draw() {

 
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
void record(){

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
void keyPressed(){
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

