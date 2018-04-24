void vis7(){
  
  if (state == 7){
    
    // background fun
    strokeWeight (0);
    stroke (0,0,0,0);
    fill (45*lowHeight/2, 43*midHeight/2, 170*hiHeight/2, 60);
    // 45, 43, 170
    if(lowHeight>255 && midHeight>255 && midHeight>255){
      fill (0,0,0, 255);
    }
    rect (0,0,width,height);
            
    // beat detection scrolling lines
    strokeWeight(pow(scoreGlobalNull/115, 5));
    stroke(255, 25);
    
    xScroll = xScroll-(width*(15/scoreGlobalNull)); 
    xScrollR = xScrollR+(width*(15/scoreGlobalNull)); 
    if (scoreGlobalNull > 0 && scoreGlobalNull < 200 ) {
      xScroll = width/2; 
      xScrollR = width/2;
      strokeWeight(0);
      stroke(0,0);
    }    if (scoreGlobalNull > 320 && scoreGlobalNull < 430) {
      xScroll = width/2; 
      xScrollR = width/2;
      strokeWeight(0);
      stroke(0,0);
    }
    if (scoreGlobalNull > 645) {
      xScroll = width/2; 
      xScrollR = width/2;
      strokeWeight(0);
      stroke(0,0);
    }
        
    line(xScroll, 0, xScroll, height);
    line(xScrollR, 0, xScrollR, height);
            
    pushMatrix();
    translate(width/2, height/2);
      fill (scoreLow/3.5, scoreHi/3.5, scoreMid/3.5, 30);
      strokeWeight(pow(scoreGlobal/115, 4));
        stroke(#042B4C,35); // bass
        line(-width, 0+0.85*(height*(lowHeight)), width, 0+0.85*(height*(lowHeight)));
        line(-width, 0-0.85*(height*(lowHeight)), width, 0-0.85*(height*(lowHeight)));
        stroke(#005030,35); // mid
        line(-width, 0+0.85*(height*(midHeight)), width, 0+0.85*(height*(midHeight)));
        line(-width, 0-0.85*(height*(midHeight)), width, 0-0.85*(height*(midHeight)));
        stroke(#693D00,35); // hi
        line(-width, 0+0.85*(height*(hiHeight)), width, 0+0.85*(height*(hiHeight)));
        line(-width, 0-0.85*(height*(hiHeight)), width, 0-0.85*(height*(hiHeight)));
      strokeWeight(pow(scoreGlobalNull/230, 4));
        stroke(#1D639D,155); // bassNull
        line(-width, 0+0.85*(height*(lowHeightNull)), width, 0+0.85*(height*(lowHeightNull)));
        line(-width, 0-0.85*(height*(lowHeightNull)), width, 0-0.85*(height*(lowHeightNull)));
        stroke(#15A56B,155);  // midNull
        line(-width, 0+0.85*(height*(midHeightNull)), width, 0+0.85*(height*(midHeightNull)));
        line(-width, 0-0.85*(height*(midHeightNull)), width, 0-0.85*(height*(midHeightNull)));
        stroke(#F35E1F,155);  // hiNull
        line(-width, 0+0.85*(height*(hiHeightNull)), width, 0+0.85*(height*(hiHeightNull)));
        line(-width, 0-0.85*(height*(hiHeightNull)), width, 0-0.85*(height*(hiHeightNull)));
        
        stroke(#042B4C,85); // bass
        polygon(0, 0+0.85*(height*(lowHeight)), 70*(scoreGlobal/450), round(scoreGlobal/80)+1);
        stroke(#005030,85); // mid
        polygon(0, 0+0.85*(height*(midHeight)), 55*(scoreGlobal/450), round(scoreGlobal/80)+1);
        stroke(#693D00,85); // hi
        polygon(0, 0+0.85*(height*(hiHeight)), 40*(scoreGlobal/450), round(scoreGlobal/80)+1);
        stroke(#1D639D,155); // bassNull
        polygon(0, 0+0.85*(height*(lowHeightNull)), 50*(scoreGlobal/500), round(scoreGlobal/80)+1);
        stroke(#15A56B,155); // midNull
        polygon(0, 0+0.85*(height*(midHeightNull)), 35*(scoreGlobal/500), round(scoreGlobal/80)+1);
        stroke(#F35E1F,155); // hiNull
        polygon(0, 0+0.85*(height*(hiHeightNull)), 20*(scoreGlobal/500), round(scoreGlobal/80)+1);
      rotate(radians(180));
        stroke(#042B4C,85); // bass
        polygon(0, 0+0.85*(height*(lowHeight)), 70*(scoreGlobal/450), round(scoreGlobal/80)+1);
        stroke(#005030,85); // mid
        polygon(0, 0+0.85*(height*(midHeight)), 55*(scoreGlobal/450), round(scoreGlobal/80)+1);
        stroke(#693D00,85); // hi
        polygon(0, 0+0.85*(height*(hiHeight)), 40*(scoreGlobal/450), round(scoreGlobal/80)+1);
        stroke(#1D639D,155); // bassNull
        polygon(0, 0+0.85*(height*(lowHeightNull)), 50*(scoreGlobal/500), round(scoreGlobal/80)+1);
        stroke(#15A56B,155); // midNull
        polygon(0, 0+0.85*(height*(midHeightNull)), 35*(scoreGlobal/500), round(scoreGlobal/80)+1);
        stroke(#F35E1F,155); // hiNull
        polygon(0, 0+0.85*(height*(hiHeightNull)), 20*(scoreGlobal/500), round(scoreGlobal/80)+1);
      noFill();
      rotate(radians(180));
        stroke(#042B4C,50); // bass
        polygon(width/2, 0+0.85*(height*(lowHeight)), 70*(scoreGlobal/45), round(scoreGlobal/80)+1);
        polygon(width/2, 0-0.85*(height*(lowHeight)), 70*(scoreGlobal/45), round(scoreGlobal/80)+1);
        stroke(#005030,50); // mid
        polygon(width/2, 0+0.85*(height*(midHeight)), 55*(scoreGlobal/45), round(scoreGlobal/80)+1);
        polygon(width/2, 0-0.85*(height*(midHeight)), 55*(scoreGlobal/45), round(scoreGlobal/80)+1);
        stroke(#693D00,50); // hi
        polygon(width/2, 0+0.85*(height*(hiHeight)), 40*(scoreGlobal/45), round(scoreGlobal/80)+1);
        polygon(width/2, 0-0.85*(height*(hiHeight)), 40*(scoreGlobal/45), round(scoreGlobal/80)+1);
        stroke(#1D639D,100); // bassNull
        polygon(width/2, 0+0.85*(height*(lowHeightNull)), 50*(scoreGlobal/50), round(scoreGlobal/80)+1);
        polygon(width/2, 0-0.85*(height*(lowHeightNull)), 50*(scoreGlobal/50), round(scoreGlobal/80)+1);
        stroke(#15A56B,100); // midNull
        polygon(width/2, 0+0.85*(height*(midHeightNull)), 35*(scoreGlobal/50), round(scoreGlobal/80)+1);
        polygon(width/2, 0-0.85*(height*(midHeightNull)), 35*(scoreGlobal/50), round(scoreGlobal/80)+1);
        stroke(#F35E1F,100); // hiNull
        polygon(width/2, 0+0.85*(height*(hiHeightNull)), 20*(scoreGlobal/50), round(scoreGlobal/80)+1);
        polygon(width/2, 0-0.85*(height*(hiHeightNull)), 20*(scoreGlobal/50), round(scoreGlobal/80)+1);
      rotate(radians(180));
        stroke(#042B4C,50); // bass
        polygon(width/2, 0+0.85*(height*(lowHeight)), 70*(scoreGlobal/45), round(scoreGlobal/80)+1);
        polygon(width/2, 0-0.85*(height*(lowHeight)), 70*(scoreGlobal/45), round(scoreGlobal/80)+1);
        stroke(#005030,50); // mid
        polygon(width/2, 0+0.85*(height*(midHeight)), 55*(scoreGlobal/45), round(scoreGlobal/80)+1);
        polygon(width/2, 0-0.85*(height*(midHeight)), 55*(scoreGlobal/45), round(scoreGlobal/80)+1);
        stroke(#693D00,50); // hi
        polygon(width/2, 0+0.85*(height*(hiHeight)), 40*(scoreGlobal/45), round(scoreGlobal/80)+1);
        polygon(width/2, 0-0.85*(height*(hiHeight)), 40*(scoreGlobal/45), round(scoreGlobal/80)+1);
        stroke(#1D639D,100); // bassNull
        polygon(width/2, 0+0.85*(height*(lowHeightNull)), 50*(scoreGlobal/50), round(scoreGlobal/80)+1);
        polygon(width/2, 0-0.85*(height*(lowHeightNull)), 50*(scoreGlobal/50), round(scoreGlobal/80)+1);
        stroke(#15A56B,100); // midNull
        polygon(width/2, 0+0.85*(height*(midHeightNull)), 35*(scoreGlobal/50), round(scoreGlobal/80)+1);
        polygon(width/2, 0-0.85*(height*(midHeightNull)), 35*(scoreGlobal/50), round(scoreGlobal/80)+1);
        stroke(#F35E1F,100); // hiNull
        polygon(width/2, 0+0.85*(height*(hiHeightNull)), 20*(scoreGlobal/50), round(scoreGlobal/80)+1);
        polygon(width/2, 0-0.85*(height*(hiHeightNull)), 20*(scoreGlobal/50), round(scoreGlobal/80)+1);
      popMatrix();
  }
}
