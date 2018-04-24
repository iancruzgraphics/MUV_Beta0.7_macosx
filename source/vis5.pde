void vis5(){

  if (state == 5){
    
    // background fun
    strokeWeight (0);
    stroke (0,0,0,0);
    fill (45*lowHeight/2, 43*midHeight/2, 170*hiHeight/2, 120);
    // 45, 43, 170
    if(lowHeight>255 && midHeight>255 && midHeight>255){
      fill (0,0,0, 255);
    }
    rect (0,0,width,height);
    
    // beat detection scrolling lines
    strokeWeight(pow(scoreGlobalNull/115, 3));
    stroke(255, 50);
    
    xScroll = xScroll-(width*(15/scoreGlobalNull)); 
    xScrollR = xScrollR+(width*(15/scoreGlobalNull)); 
    
    if (scoreGlobalNull > 0 && scoreGlobalNull < 200 ) {
      xScroll = width/2; 
      xScrollR = width/2;
      strokeWeight(0);
      stroke(0,0);
      rotator = rotator * -1;
    }    if (scoreGlobalNull > 320 && scoreGlobalNull < 430) {
      xScroll = width/2; 
      xScrollR = width/2;
      strokeWeight(0);
      stroke(0,0);
      rotator = rotator * -1;
    }
    if (scoreGlobalNull > 645) {
      xScroll = width/2; 
      xScrollR = width/2;
      strokeWeight(0);
      stroke(0,0);
      rotator = rotator * -1;
    }
    
    //line(xScroll, 0, xScroll, height);
    //line(xScrollR, 0, xScrollR, height);
    curve(0.75*width, 0.75*height, xScroll, 0-height/8, xScroll, height+height/8, 0.75*width, 0.25*height);
    curve(0.25*width, 0.75*height, xScrollR, 0-height/8, xScrollR, height+height/8, 0.25*width, 0.25*height);
    
    line(width*2, -width/4, xScroll+width/4, height+width/4);
    line(-width, -width/4, xScrollR-width/4, height+width/4);
    line(xScroll+width/4, -width/4, width*2, height+width/4);
    line(xScrollR-width/4, -width/4, -width, height+width/4);
    
    // Projectile
    
      //stroke(0,0);
      //fill(255,0,0,60); // bass
      //ellipse(width/2, height/2+0.65*(height*(lowHeight)), 70, 70);
      //ellipse(width/2, height/2-0.65*(height*(lowHeight)), 70, 70);
      //fill(255,50,50,125); // bassNull
      //ellipse(width/2, height/2+0.65*(height*(lowHeight)), 50, 50);
      //ellipse(width/2, height/2-0.65*(height*(lowHeight)), 50, 50);
      //fill(0,0,255,60); // mid
      //ellipse(width/2, height/2+0.65*(height*(midHeight)), 55, 55);
      //ellipse(width/2, height/2-0.65*(height*(midHeight)), 55, 55);
      //fill(50,50,255,125); // midNull
      //ellipse(width/2, height/2+0.65*(height*(midHeight)), 35, 35);
      //ellipse(width/2, height/2-0.65*(height*(midHeight)), 35, 35);
      //fill(0,255,0,60); // hi
      //ellipse(width/2, height/2+0.65*(height*(hiHeight)), 40, 40);
      //ellipse(width/2, height/2-0.65*(height*(hiHeight)), 40, 40);
      //fill(50,255,50,125); // hiNull
      //ellipse(width/2, height/2+0.65*(height*(hiHeightNull)), 20, 20);
      //ellipse(width/2, height/2-0.65*(height*(hiHeightNull)), 20, 20);
      
      fill(0,0);
      
      pushMatrix();
      
      translate(width/2, height/2);
        rotate(radians(rotator*20 + (pow(scoreGlobalNull/300, 4))*3));
        
          strokeWeight( (pow((scoreGlobalNull/100),1.5)) *5);
        rotate(radians(15));
          stroke(#042B4C,150-scoreGlobal/8); // bass
          polygon3(0, 0+0.85*(height*(lowHeight)), 90*(scoreGlobal/450), 4);
          polygon3(0, 0-0.85*(height*(lowHeight)), 90*(scoreGlobal/450), 4);
          stroke(#005030,150-scoreGlobal/8); // mid
          polygon3(0, 0+0.85*(height*(midHeight)), 75*(scoreGlobal/450), 4);
          polygon3(0, 0-0.85*(height*(midHeight)), 75*(scoreGlobal/450), 4);
          stroke(#693D00,150-scoreGlobal/8); // hi
          polygon3(0, 0+0.85*(height*(hiHeight)), 60*(scoreGlobal/450), 4);
          polygon3(0, 0-0.85*(height*(hiHeight)), 60*(scoreGlobal/450), 4);
        rotate(radians(30));
          stroke(#042B4C,150-scoreGlobal/8); // bass
          polygon3(0, 0+0.85*(height*(lowHeight)), 90*(scoreGlobal/450), 4);
          polygon3(0, 0-0.85*(height*(lowHeight)), 90*(scoreGlobal/450), 4);
          stroke(#005030,150-scoreGlobal/8); // mid
          polygon3(0, 0+0.85*(height*(midHeight)), 75*(scoreGlobal/450), 4);
          polygon3(0, 0-0.85*(height*(midHeight)), 75*(scoreGlobal/450), 4);
          stroke(#693D00,150-scoreGlobal/8); // hi
          polygon3(0, 0+0.85*(height*(hiHeight)), 60*(scoreGlobal/450), 4);
          polygon3(0, 0-0.85*(height*(hiHeight)), 60*(scoreGlobal/450), 4);
        rotate(radians(30));
          stroke(#042B4C,150-scoreGlobal/8); // bass
          polygon3(0, 0+0.85*(height*(lowHeight)), 90*(scoreGlobal/450), 4);
          polygon3(0, 0-0.85*(height*(lowHeight)), 90*(scoreGlobal/450), 4);
          stroke(#005030,150-scoreGlobal/8); // mid
          polygon3(0, 0+0.85*(height*(midHeight)), 75*(scoreGlobal/450), 4);
          polygon3(0, 0-0.85*(height*(midHeight)), 75*(scoreGlobal/450), 4);
          stroke(#693D00,150-scoreGlobal/8); // hi
          polygon3(0, 0+0.85*(height*(hiHeight)), 60*(scoreGlobal/450), 4);
          polygon3(0, 0-0.85*(height*(hiHeight)), 60*(scoreGlobal/450), 4);
        rotate(radians(30));
          stroke(#042B4C,150-scoreGlobal/8); // bass
          polygon3(0, 0+0.85*(height*(lowHeight)), 90*(scoreGlobal/450), 4);
          polygon3(0, 0-0.85*(height*(lowHeight)), 90*(scoreGlobal/450), 4);
          stroke(#005030,150-scoreGlobal/8); // mid
          polygon3(0, 0+0.85*(height*(midHeight)), 75*(scoreGlobal/450), 4);
          polygon3(0, 0-0.85*(height*(midHeight)), 75*(scoreGlobal/450), 4);
          stroke(#693D00,150-scoreGlobal/8); // hi
          polygon3(0, 0+0.85*(height*(hiHeight)), 60*(scoreGlobal/450), 4);
          polygon3(0, 0-0.85*(height*(hiHeight)), 60*(scoreGlobal/450), 4);
        rotate(radians(30));
          stroke(#042B4C,150-scoreGlobal/8); // bass
          polygon3(0, 0+0.85*(height*(lowHeight)), 90*(scoreGlobal/450), 4);
          polygon3(0, 0-0.85*(height*(lowHeight)), 90*(scoreGlobal/450), 4);
          stroke(#005030,150-scoreGlobal/8); // mid
          polygon3(0, 0+0.85*(height*(midHeight)), 75*(scoreGlobal/450), 4);
          polygon3(0, 0-0.85*(height*(midHeight)), 75*(scoreGlobal/450), 4);
          stroke(#693D00,150-scoreGlobal/8); // hi
          polygon3(0, 0+0.85*(height*(hiHeight)), 60*(scoreGlobal/450), 4);
          polygon3(0, 0-0.85*(height*(hiHeight)), 60*(scoreGlobal/450), 4);
        rotate(radians(30));
          stroke(#042B4C,150-scoreGlobal/8); // bass
          polygon3(0, 0+0.85*(height*(lowHeight)), 90*(scoreGlobal/450), 4);
          polygon3(0, 0-0.85*(height*(lowHeight)), 90*(scoreGlobal/450), 4);
          stroke(#005030,150-scoreGlobal/8); // mid
          polygon3(0, 0+0.85*(height*(midHeight)), 75*(scoreGlobal/450), 4);
          polygon3(0, 0-0.85*(height*(midHeight)), 75*(scoreGlobal/450), 4);
          stroke(#693D00,150-scoreGlobal/8); // hi
          polygon3(0, 0+0.85*(height*(hiHeight)), 60*(scoreGlobal/450), 4);
          polygon3(0, 0-0.85*(height*(hiHeight)), 60*(scoreGlobal/450), 4);
        
          strokeWeight(1+round(scoreGlobalNull/100));
          stroke(#1D639D,255); // bassNull
          polygon(0, 0+0.85*(height*(lowHeightNull)), (5/( pow((lowHeightNull+midHeightNull+hiHeightNull),2))+width/100), 2);
          polygon(0, 0-0.85*(height*(lowHeightNull)), (5/( pow((lowHeightNull+midHeightNull+hiHeightNull),2))+width/100), 2);
          stroke(#15A56B,255); // midNull
          polygon(0, 0+0.85*(height*(midHeightNull)), (5/( pow((lowHeightNull+midHeightNull+hiHeightNull),2))+width/100), 2);
          polygon(0, 0-0.85*(height*(midHeightNull)), (5/( pow((lowHeightNull+midHeightNull+hiHeightNull),2))+width/100), 2);
          stroke(#F35E1F,255); // hiNull
          polygon(0, 0+0.85*(height*(hiHeightNull)), (5/( pow((lowHeightNull+midHeightNull+hiHeightNull),2))+width/100), 2);
          polygon(0, 0-0.85*(height*(hiHeightNull)), (5/( pow((lowHeightNull+midHeightNull+hiHeightNull),2))+width/100), 2);
        rotate(radians(30));
          stroke(#1D639D,255); // bassNull
          polygon(0, 0+0.85*(height*(lowHeightNull)), (5/( pow((lowHeightNull+midHeightNull+hiHeightNull),2))+width/100), 2);
          polygon(0, 0-0.85*(height*(lowHeightNull)), (5/( pow((lowHeightNull+midHeightNull+hiHeightNull),2))+width/100), 2);
          stroke(#15A56B,255); // midNull
          polygon(0, 0+0.85*(height*(midHeightNull)), (5/( pow((lowHeightNull+midHeightNull+hiHeightNull),2))+width/100), 2);
          polygon(0, 0-0.85*(height*(midHeightNull)), (5/( pow((lowHeightNull+midHeightNull+hiHeightNull),2))+width/100), 2);
          stroke(#F35E1F,255); // hiNull
          polygon(0, 0+0.85*(height*(hiHeightNull)), (5/( pow((lowHeightNull+midHeightNull+hiHeightNull),2))+width/100), 2);
          polygon(0, 0-0.85*(height*(hiHeightNull)), (5/( pow((lowHeightNull+midHeightNull+hiHeightNull),2))+width/100), 2);
        rotate(radians(30));
          stroke(#1D639D,255); // bassNull
          polygon(0, 0+0.85*(height*(lowHeightNull)), (5/( pow((lowHeightNull+midHeightNull+hiHeightNull),2))+width/100), 2);
          polygon(0, 0-0.85*(height*(lowHeightNull)), (5/( pow((lowHeightNull+midHeightNull+hiHeightNull),2))+width/100), 2);
          stroke(#15A56B,255); // midNull
          polygon(0, 0+0.85*(height*(midHeightNull)), (5/( pow((lowHeightNull+midHeightNull+hiHeightNull),2))+width/100), 2);
          polygon(0, 0-0.85*(height*(midHeightNull)), (5/( pow((lowHeightNull+midHeightNull+hiHeightNull),2))+width/100), 2);
          stroke(#F35E1F,255); // hiNull
          polygon(0, 0+0.85*(height*(hiHeightNull)), (5/( pow((lowHeightNull+midHeightNull+hiHeightNull),2))+width/100), 2);
          polygon(0, 0-0.85*(height*(hiHeightNull)), (5/( pow((lowHeightNull+midHeightNull+hiHeightNull),2))+width/100), 2);
        rotate(radians(30));
          stroke(#1D639D,255); // bassNull
          polygon(0, 0+0.85*(height*(lowHeightNull)), (5/( pow((lowHeightNull+midHeightNull+hiHeightNull),2))+width/100), 2);
          polygon(0, 0-0.85*(height*(lowHeightNull)), (5/( pow((lowHeightNull+midHeightNull+hiHeightNull),2))+width/100), 2);
          stroke(#15A56B,255); // midNull
          polygon(0, 0+0.85*(height*(midHeightNull)), (5/( pow((lowHeightNull+midHeightNull+hiHeightNull),2))+width/100), 2);
          polygon(0, 0-0.85*(height*(midHeightNull)), (5/( pow((lowHeightNull+midHeightNull+hiHeightNull),2))+width/100), 2);
          stroke(#F35E1F,255); // hiNull
          polygon(0, 0+0.85*(height*(hiHeightNull)), (5/( pow((lowHeightNull+midHeightNull+hiHeightNull),2))+width/100), 2);
          polygon(0, 0-0.85*(height*(hiHeightNull)), (5/( pow((lowHeightNull+midHeightNull+hiHeightNull),2))+width/100), 2);
        rotate(radians(30));
          stroke(#1D639D,255); // bassNull
          polygon(0, 0+0.85*(height*(lowHeightNull)), (5/( pow((lowHeightNull+midHeightNull+hiHeightNull),2))+width/100), 2);
          polygon(0, 0-0.85*(height*(lowHeightNull)), (5/( pow((lowHeightNull+midHeightNull+hiHeightNull),2))+width/100), 2);
          stroke(#15A56B,255); // midNull
          polygon(0, 0+0.85*(height*(midHeightNull)), (5/( pow((lowHeightNull+midHeightNull+hiHeightNull),2))+width/100), 2);
          polygon(0, 0-0.85*(height*(midHeightNull)), (5/( pow((lowHeightNull+midHeightNull+hiHeightNull),2))+width/100), 2);
          stroke(#F35E1F,255); // hiNull
          polygon(0, 0+0.85*(height*(hiHeightNull)), (5/( pow((lowHeightNull+midHeightNull+hiHeightNull),2))+width/100), 2);
          polygon(0, 0-0.85*(height*(hiHeightNull)), (5/( pow((lowHeightNull+midHeightNull+hiHeightNull),2))+width/100), 2);
        rotate(radians(30));
          stroke(#1D639D,255); // bassNull
          polygon(0, 0+0.85*(height*(lowHeightNull)), (5/( pow((lowHeightNull+midHeightNull+hiHeightNull),2))+width/100), 2);
          polygon(0, 0-0.85*(height*(lowHeightNull)), (5/( pow((lowHeightNull+midHeightNull+hiHeightNull),2))+width/100), 2);
          stroke(#15A56B,255); // midNull
          polygon(0, 0+0.85*(height*(midHeightNull)), (5/( pow((lowHeightNull+midHeightNull+hiHeightNull),2))+width/100), 2);
          polygon(0, 0-0.85*(height*(midHeightNull)), (5/( pow((lowHeightNull+midHeightNull+hiHeightNull),2))+width/100), 2);
          stroke(#F35E1F,255); // hiNull
          polygon(0, 0+0.85*(height*(hiHeightNull)), (5/( pow((lowHeightNull+midHeightNull+hiHeightNull),2))+width/100), 2);
          polygon(0, 0-0.85*(height*(hiHeightNull)), (5/( pow((lowHeightNull+midHeightNull+hiHeightNull),2))+width/100), 2);
          
          
      popMatrix();
      
    //// background fun
    //strokeWeight (0);
    //stroke (0,0,0,0);
    ////background (scoreLow/2, scoreHi/2, scoreMid/2, 1);
    //fill (scoreLow/3.5, scoreHi/3.5, scoreMid/3.5, 200);
    //rect (0,0,width,height);
    
  }

}
