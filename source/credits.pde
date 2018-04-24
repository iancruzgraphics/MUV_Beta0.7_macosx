void credits(){
  // trigger  
  if (trigger == 0)  // 03 - - Audio Visualizer 3 // //
  {
    splashOpacity = splashOpacity - 30;
  }
  if (trigger == 1)
  {
    splashOpacity = 255;
  }
  if (splashOpacity <= 0){
    splashOpacity = 0;
  }
  
  // white screen
  if (splashOpacity > 0){
    strokeWeight (0);
    fill(0,splashOpacity);
    rect(0,0,width, height);
    strokeWeight (2);
    stroke (150,splashOpacity);
    fill(30,splashOpacity);
    rect(10,10,width-20,height-20, 10);
    
    tint(255, splashOpacity);
      image(logo, 48, 42);
      fill(255, splashOpacity-100);
      textFont(Oswald36);
      text("Beta v0.7               Modified 19 April 2018", 46, 500);
    
    textFont(Oswald24);
    text("Credits", 48, height-186);
    
    textFont(Roboto18);
    text("Inspiration and Referenced Projects", 48, height-166);
    text("Samuel Lapointe's 'ProcessingCubes' for framework of audio spectrum. - github.com/samuellapointe", 48, height-149);
    text("Margaret Erlano's 'wishful_sky_with_shooting_stars' - understandingnewmedia.com/f2014/margaret", 48, height-132);
    text("Lee Jacob Hilado's 'molecule_party' -- understandingnewmedia.com/f2014/lee/final/web-export/", 48, height-115);
    
    textFont(Oswald24);
    text("Special Thanks", 48, height-77);
    
    textFont(Roboto18);
    text("Matthew Neviks of 'Take The World Productions' -- https://www.twitch.tv/mr_neviks", 48, height-57);
    text("Processomg's Reference and community forum -- https://www.processing.org/reference/", 48, height-40);
  }
}
