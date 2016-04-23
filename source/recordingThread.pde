

import codeanticode.gsvideo.*;
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
 
  int getCount() {
    return count;
  }
 
  // Overriding "start()"
  void start () {
    
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
 void addpix(int []pix){
   mm.addFrame(pix);
 }
 
  // Plantilla run encadenada start() en base al modelo de Threat Processing
  void run () {
    while (running) {
      println(id + ": " + count);
      count++;
    
    
      // Tiempo de espera del threat
      try {
        sleep((long)(wait));
      } catch (Exception e) {
      }
    }
    System.out.println(id + " tu video ha sido guardado! revisa el siguiente link: "+path);  // Mensaje de finalización
  }
 
 
  // Metodo de finiquitación
  void quit() {
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
