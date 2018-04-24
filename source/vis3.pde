int xspacing = 20;   // How far apart should each horizontal location be spaced
int w;              // Width of entire wave

// Start angle at 0
float theta = 0.0;
// Height of wave
float amplitudeLow;
float amplitudeMid;
float amplitudeHi;
float amplitudeLowNull;
float amplitudeMidNull;
float amplitudeHiNull;
// Using an array to store height values for the wave
float[] yvaluesLow;
float[] yvaluesMid;
float[] yvaluesHi;
float[] yvaluesLowNull;
float[] yvaluesMidNull;
float[] yvaluesHiNull;
// Value for incrementing X, a function of period and xspacing
float dxLow;
float dxMid;
float dxHi;
float dxLowNull;
float dxMidNull;
float dxHiNull;

// How many pixels before the wave repeats
float period;

void vis3(){
  if (state == 3){
    
    // background fun
    strokeWeight (0);
    stroke (0,0,0,0);
    fill (45*lowHeight/2, 43*midHeight/2, 170*hiHeight/2, 255);
    // 45, 43, 170
    if(lowHeight>255 && midHeight>255 && midHeight>255){
      fill (0,0,0, 255);
    }
    rect (0,0,width,height);
    
    // infinity fix
    if (lowHeight > 255){
      lowHeight = 0;
    }
    if (midHeight > 255){
      midHeight = 0;
    }
    if (hiHeight > 255){
      hiHeight = 0;
    }

    xspacing = 20;

    w = width/2+20;
    period = width;
    dxLow = (4*PI / period) * xspacing * pow(1.6*lowHeight,4) ;
    dxMid = (4*PI / period) * xspacing * pow(1.6*midHeight,4) ;
    dxHi = (4*PI / period) * xspacing * pow(1.6*hiHeight,4) ;
    dxLowNull = (4*PI / period) * xspacing * pow(1.6*lowHeightNull,4) ;
    dxMidNull = (4*PI / period) * xspacing * pow(1.6*midHeightNull,4) ;
    dxHiNull = (4*PI / period) * xspacing * pow(1.6*hiHeightNull,4) ;
    
    yvaluesLow = new float[w/xspacing];
    yvaluesMid = new float[w/xspacing];
    yvaluesHi = new float[w/xspacing];
    yvaluesLowNull = new float[w/xspacing*6];
    yvaluesMidNull = new float[w/xspacing*6];
    yvaluesHiNull = new float[w/xspacing*6];
  
    calcWave();
    renderWave();
  }
}

void calcWave(){
  // Increment theta (try different values for 'angular velocity' here)
  theta += 0.003*scoreGlobalNull;
  amplitudeLow = 600*lowHeight*1.2;
  amplitudeMid = 360*midHeight*1.4;
  amplitudeHi = 240*hiHeight*1.8;
  amplitudeLowNull = 250*lowHeightNull*1.2;
  amplitudeMidNull = 200*midHeightNull*1.4;
  amplitudeHiNull = 100*hiHeightNull*1.8;

  // For every x value, calculate a y value with sine function
  float x = theta;
  for (int i = 0; i < yvaluesLow.length; i++) {
    yvaluesLow[i] = sin(x)*amplitudeLow;
    x+=dxLow;
  }
  for (int i = 0; i < yvaluesMid.length; i++) {
    yvaluesMid[i] = sin(x)*amplitudeMid;
    x+=dxMid;
  }
  for (int i = 0; i < yvaluesHi.length; i++) {
    yvaluesHi[i] = sin(x)*amplitudeHi;
    x+=dxHi;
  }
  for (int i = 0; i < yvaluesLowNull.length; i++) {
    yvaluesLowNull[i] = sin(x)*amplitudeLowNull;
    x+=dxLowNull;
  }
  for (int i = 0; i < yvaluesMidNull.length; i++) {
    yvaluesMidNull[i] = sin(x)*amplitudeMidNull;
    x+=dxMidNull;
  }
  for (int i = 0; i < yvaluesHiNull.length; i++) {
    yvaluesHiNull[i] = sin(x)*amplitudeHiNull;
    x+=dxHiNull;
  }
}

void renderWave(){
  
  pushMatrix();
    noFill();
    translate(width/2, height/2);
      strokeWeight(pow(scoreGlobalNull/50, 2.5));
      stroke(#042B4C,100-scoreGlobal/8);
      for (int x = 0; x < yvaluesLow.length; x++) { // lowNull
        polygon2(x*xspacing*6, yvaluesLowNull[x]*4, pow(14*lowHeightNull,2), 2);
      }
      stroke(#005030,100-scoreGlobal/8);
      for (int x = 0; x < yvaluesMidNull.length; x++) { // midNull
        polygon2(x*xspacing*6, yvaluesMidNull[x]*4, pow(14*midHeightNull,2), 2);
      }
      stroke(#693D00,100-scoreGlobal/8);
      for (int x = 0; x < yvaluesHiNull.length; x++) { // hiNull
        polygon2(x*xspacing*6, yvaluesHiNull[x]*4, pow(14*hiHeightNull,2), 2);
      }
    rotate(radians(180));
      strokeWeight(pow(scoreGlobalNull/50, 2.5));
      stroke(#042B4C,100-scoreGlobal/8);
      for (int x = 0; x < yvaluesLow.length; x++) { // lowNull
        polygon2(x*xspacing*6, -yvaluesLowNull[x]*4, pow(14*lowHeightNull,2), 2);
      }
      stroke(#005030,100-scoreGlobal/8);
      for (int x = 0; x < yvaluesMidNull.length; x++) { // midNull
        polygon2(x*xspacing*6, -yvaluesMidNull[x]*4, pow(14*midHeightNull,2), 2);
      }
      stroke(#693D00,100-scoreGlobal/8);
      for (int x = 0; x < yvaluesHiNull.length; x++) { // hiNull
        polygon2(x*xspacing*6, -yvaluesHiNull[x]*4, pow(14*hiHeightNull,2), 2);
      }
    rotate(radians(180));
      strokeWeight(pow(scoreGlobalNull/50, 1.1));
      stroke(#1D639D,255);
      for (int x = 0; x < yvaluesLow.length; x++) { // low
        polygon2(x*xspacing+10, yvaluesLow[x], pow(20*lowHeight,2), 2);
      }
      stroke(#15A56B,255);
      for (int x = 0; x < yvaluesMid.length; x++) { // mid
        polygon2(x*xspacing+10, yvaluesMid[x], pow(20*midHeight,2), 2);
      }
      stroke(#F35E1F,255);
      for (int x = 0; x < yvaluesHi.length; x++) { // hi
        polygon2(x*xspacing+10, yvaluesHi[x], pow(24*hiHeight,2), 2);
      }
    rotate(radians(180));
      strokeWeight(pow(scoreGlobalNull/50, 1.1));
      stroke(#1D639D,255);
      for (int x = 0; x < yvaluesLow.length; x++) { // low
        polygon2(x*xspacing+10, -yvaluesLow[x], pow(20*lowHeight,2), 2);
      }
      stroke(#15A56B,255);
      for (int x = 0; x < yvaluesMid.length; x++) { // mid
        polygon2(x*xspacing+10, -yvaluesMid[x], pow(20*midHeight,2), 2);
      }
      stroke(#F35E1F,255);
      for (int x = 0; x < yvaluesHi.length; x++) { // hi
        polygon2(x*xspacing+10, -yvaluesHi[x], pow(24*hiHeight,2), 2);
      }
  popMatrix();

  }
