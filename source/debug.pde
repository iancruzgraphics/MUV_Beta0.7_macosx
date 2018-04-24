void debug(){
  
  if (state == 0){
    
    
    // background fun
    strokeWeight (0);
    stroke (0,0,0,0);
    fill (45*lowHeight/2, 43*midHeight/2, 170*hiHeight/2, 255);
    // 45, 43, 170
    if(lowHeight>255 && midHeight>255 && midHeight>255){
      fill (0,0,0, 255);
    }
    rect (0,0,width,height);
    
    // falloff bars
    fill(#1D639D,150); // bass
    rect(width/40, height-(height/40), width*.25, -(height*(lowHeight))  );
    fill(#15A56B,150); // mid
    rect(12*width/40, height-(height/40), width*.25, -(height*(midHeight))  );
    fill(#F35E1F,150); // hi
    rect(23*width/40, height-(height/40), width*.25, -(height*(hiHeight))  );
    fill(#2D2BAA,150); // global
    rect(18*width/20, height-(height/40), 3*width/40, -(height*(scoreGlobal/775))  );
    
    // "null", direct-responsive bars
    fill(#1D639D,255); // bass
    rect(width/40, height-(height/40), width*.25, -(height*(lowHeightNull))  );
    fill(#15A56B,255); // mid
    rect(12*width/40, height-(height/40), width*.25, -(height*(midHeightNull))  );
    fill(#F35E1F,255); // hi
    rect(23*width/40, height-(height/40), width*.25, -(height*(hiHeightNull))  );
    fill(#2D2BAA,255); // global
    rect(18*width/20, height-(height/40), 3*width/40, -(height*(scoreGlobalNull/775))  );
    
    // left and right speaker input debug
    fill(255,255,255,255);
    rect( width/40, height/40+30, AudioIn.left.level()*width, height/40 );
    rect( width/40, 2.5*height/40+30, AudioIn.right.level()*width, height/40 );
        
    // left and right waveforms
    for(int i = 0; i < AudioIn.bufferSize()-1; i++)
    {
      strokeWeight(1+round(scoreGlobalNull/60));
      stroke(45*lowHeight/2, 43*midHeight/2, 170*hiHeight/2, 255);
      //line(i*(width/300), 350 + AudioIn.left.get(i)*25, (i+1)*(width/300), 350 + AudioIn.left.get(i+1)*25 ); // right
      //line(i*(width/300), 400 + AudioIn.right.get(i)*25, (i+1)*(width/300), 400 + AudioIn.right.get(i+1)*25 ); // right
      line( 37*width/40 + AudioIn.left.get(i)*50, i+i*(height/512), 37*width/40 + AudioIn.left.get(i+1)*50, (i+1)+(i+1)*(height/512) ); // right
      line( 38*width/40 + AudioIn.right.get(i)*50, i+i*(height/512), 38*width/40 + AudioIn.right.get(i+1)*50, (i+1)+(i+1)*(height/512) ); // right
    }
    
    // midi input, 16 pads test
    
    
    // audio monitor state
    String monitoringState = AudioIn.isMonitoring() ? "enabled" : "disabled";
    textFont(Oswald36);
    text( "Input monitoring is currently " + monitoringState + ".  Toggle using 'M' key.", width/40, 48 );
    fill(0,255);
    textFont(Roboto18);
    text( "Left Speaker", width/40+5, height/40+50 );
    text( "Right Speaker", width/40+5, 2.5*height/40+50 );
  }
  
}

void keyPressed() // live monitoring of audio - - - - - - - - - - - - - - - - - - - - - - - - - -
{
  
  if ( key == 'm' || key == 'M' ) {
    if ( AudioIn.isMonitoring() ) {
      AudioIn.disableMonitoring();
    }
    else {
      AudioIn.enableMonitoring();
    }
  }
  
  if ( key == 'b' || key == 'B' ) {
    initializeMIDI = 1;
    
    // debug broken MidiBus
    if (initializeMIDI == 1) {
      // vi25 keyboard with midi drum pad INITIALIZE
      vi25 = new MidiBus(this, "vi25loop", "vi25loop");
      vi25mac = new MidiBus(this, "Out", "In");
      // mc7000 dj controller INITIALIZE
      mc7000 = new MidiBus(this, "mc7000loop", "mc7000loop");
      mc7000mac = new MidiBus(this, "DENON DJ MC7000", "DENON DJ MC7000");
      initializeMIDI = 0;
      key = 'i';
    }
  }
  
    
}
