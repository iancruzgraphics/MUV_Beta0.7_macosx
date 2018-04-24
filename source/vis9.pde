void vis9(){
  if (state == 9){
    
    // background fun
    strokeWeight (0);
    stroke (0,0,0,0);
    fill (45*lowHeight/2, 43*midHeight/2, 170*hiHeight/2, 120);
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
