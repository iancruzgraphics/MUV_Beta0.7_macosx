MidiBus vi25;
MidiBus vi25mac;
MidiBus mc7000;
MidiBus mc7000mac;

//int[] CHAN = new int[16];
int[] PITCH = new int[128];
int[] VEL = new int[128];
int[] ITEM = new int[128];
int[] AMT = new int[128];

int CHANvar = 0;
int PITCHvarON = 0;
int PITCHvarOFF = 0;
int VELvarON = 0;
int VELvarOFF = 0;
int ITEMvar = 0;
int AMTvar = 0;

int[] pad = new int[128];
int[] knobR = new int [64];

int fader1;
int fader2;
int fader3;
int fader4;
int crossFader = 64;

int padMode = 0;
int knobMode = 0;

int colorMode = 0;
String L = "#042B4C";
String M = "#005030";
String H = "#693D00";
String L2 = "#1D639D";
String M2 = "#15A56B";
String H2 = "#F35E1F";
String G  = "#2D2BAA";

int[] visTrigger = new int[128];
int mouseTrigger = 0;


void MIDI(){
  
  // MIDItrigger  
  if (MIDItrigger == 0)  // 03 - - Audio Visualizer 3 // //
  {
    MIDIopacity = MIDIopacity - 30;
  }
  if (MIDItrigger == 1)
  {
    MIDIopacity = 255;
  }
  if (MIDIopacity <= 0){
    MIDIopacity = 0;
  }

  MIDIop = MIDIopacity-255;
  
  for (int i=0; i<64; i++){ // - - - - all knob MIDItriggers!
    if (CHANvar >= 0 && CHANvar < 5){  // - - - - initialize!
      knobR[i] = 64;
    }
    if(CHANvar == 14){
      knobR[i] = 0;
    }
    if (ITEMvar == i){
      knobR[i] = AMTvar;
    }
  }
  
  for (int i=0; i<128; i++){ // - - - - individual pad and button opacity MIDItriggers!
    if (PITCH[i] == 1 && VEL[i] > 0){
      pad[i] = 255;
    }
    if (VEL[i] == 0 || PITCH[i] == 0){
      pad[i] = pad[i]-15;
    }
    if (pad[i] < 250){
      PITCH[i] = 0;
    }
    
    if (i >= 48){ // - - - - - - - - - - 
      if (ITEM[i] == 1 && AMT[i] > 0){
        pad[i] = 255;
      }
      if (pad[i] < 0){
        pad[i] = 0;
      }
      if (pad[i] < 250){
        ITEM[i] = 0;
      }
    }
    
    if (visTrigger[i] >= 4){
      
    }
  }
    
  pushMatrix(); // // // // // // // // // // // // // // // // // // // // // // // // // // // // // // //
  translate(-32,34); // // // // // // // // // // // // // // // // // // // // // // // // // // // // //
  scale(.75);
  translate(5.2*width/16,34/4); // // // // // // // // // // // // // // // // // // // // // // // // // // // // //
  
  noStroke();
        
  // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - Alesis VI25 Keyboard
  // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - chan 13 finger pads
  pushMatrix();  // // // // // // // VI25 note 51 - - - - - - - - -
    translate(width-20, 20);
    fill(100,MIDIop+255);
    polygon3(0,0,18,4);
    if(CHANvar == 13){
      fill(255,MIDIop+pad[51]);
      polygon3(0,0,14,4);
    }
  popMatrix();
  pushMatrix();  // // // // // // // VI25 note 50 - - - - - - - - -
    translate(width-50, 20);
    fill(100,MIDIop+255);
    polygon3(0,0,18,4);
    if(CHANvar == 13){
      fill(255,MIDIop+pad[50]);
      polygon3(0,0,14,4);
    }
  popMatrix();
  pushMatrix();  // // // // // // // VI25 note 49 - - - - - - - - -
    translate(width-80, 20);
    fill(100,MIDIop+255);
    polygon3(0,0,18,4);
    if(CHANvar == 13){
      fill(255,MIDIop+pad[49]);
      polygon3(0,0,14,4);
    }
  popMatrix();
  pushMatrix();  // // // // // // // VI25 note 48 - - - - - - - - -
    translate(width-110, 20);
    fill(100,MIDIop+255);
    polygon3(0,0,18,4);
    if(CHANvar == 13){
      fill(255,MIDIop+pad[48]);
      polygon3(0,0,14,4);
    }
  popMatrix();
  pushMatrix();  // // // // // // // VI25 note 47 - - - - - - - - -
    translate(width-20, 50);
    fill(100,MIDIop+255);
    polygon3(0,0,18,4);
    if(CHANvar == 13){
      fill(255,MIDIop+pad[47]);
      polygon3(0,0,14,4);
    }
  popMatrix();
  pushMatrix();  // // // // // // // VI25 note 46 - - - - - - - - -
    translate(width-50, 50);
    fill(100,MIDIop+255);
    polygon3(0,0,18,4);
    if(CHANvar == 13){
      fill(255,MIDIop+pad[46]);
      polygon3(0,0,14,4);
    }
  popMatrix();
  pushMatrix();  // // // // // // // VI25 note 45 - - - - - - - - -
    translate(width-80, 50);
    fill(100,MIDIop+255);
    polygon3(0,0,18,4);
    if(CHANvar == 13){
      fill(255,MIDIop+pad[45]);
      polygon3(0,0,14,4);
    }
  popMatrix();
  pushMatrix();  // // // // // // // VI25 note 44 - - - - - - - - -
    translate(width-110, 50);
    fill(100,MIDIop+255);
    polygon3(0,0,18,4);
    if(CHANvar == 13){
      fill(255,MIDIop+pad[44]);
      polygon3(0,0,14,4);
    }
  popMatrix();
  pushMatrix();  // // // // // // // VI25 note 43 - - - - - - - - -
    translate(width-20, 80);
    fill(100,MIDIop+255);
    polygon3(0,0,18,4);
    if(CHANvar == 13){
      fill(255,MIDIop+pad[43]);
      polygon3(0,0,14,4);
    }
  popMatrix();
  pushMatrix();  // // // // // // // VI25 note 42 - - - - - - - - -
    translate(width-50, 80);
    fill(100,MIDIop+255);
    polygon3(0,0,18,4);
    if(CHANvar == 13){
      fill(255,MIDIop+pad[42]);
      polygon3(0,0,14,4);
    }
  popMatrix();
  pushMatrix();  // // // // // // // VI25 note 41 - - - - - - - - -
    translate(width-80, 80);
    fill(100,MIDIop+255);
    polygon3(0,0,18,4);
    fill(255,MIDIop+pad[41]);
    polygon3(0,0,14,4);
  popMatrix();
  pushMatrix();  // // // // // // // VI25 note 40 - - - - - - - - -
    translate(width-110, 80);
    fill(100,MIDIop+255);
    polygon3(0,0,18,4);
    if(CHANvar == 13){
      fill(255,MIDIop+pad[40]);
      polygon3(0,0,14,4);
    }
  popMatrix();
  pushMatrix();  // // // // // // // VI25 note 39 - - - - - - - - -
    translate(width-20, 110);
    fill(100,MIDIop+255);
    polygon3(0,0,18,4);
    if(CHANvar == 13){
      fill(255,MIDIop+pad[39]);
      polygon3(0,0,14,4);
    }
  popMatrix();
  pushMatrix();  // // // // // // // VI25 note 38 - - - - - - - - -
    translate(width-50, 110);
    fill(100,MIDIop+255);
    polygon3(0,0,18,4);
    if(CHANvar == 13){
      fill(255,MIDIop+pad[38]);
      polygon3(0,0,14,4);
    }
  popMatrix();
  pushMatrix();  // // // // // // // VI25 note 37 - - - - - - - - -
    translate(width-80, 110);
    fill(100,MIDIop+255);
    polygon3(0,0,18,4);
    if(CHANvar == 13){
      fill(255,MIDIop+pad[37]);
      polygon3(0,0,14,4);
    }
  popMatrix();
  pushMatrix();  // // // // // // // VI25 note 36 - - - - - - - - -
    translate(width-110, 110);
    fill(100,MIDIop+255);
    polygon3(0,0,18,4);
    if(CHANvar == 13){
      fill(255,MIDIop+pad[36]);
      polygon3(0,0,14,4);
    }
  popMatrix();
  // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - chan 12 keybed
  pushMatrix();  // // // // // // // VI25 keybed 72 - - - - - - - - -
    translate(width-15, 150);
    fill(100,MIDIop+255);
    rect(0,0,8,-20);
    if(CHANvar == 12){
      fill(255,MIDIop+pad[72]);
      rect(2,-2,4,-16);
    }
  popMatrix();
  pushMatrix();  // // // // // // // VI25 keybed 71 - - - - - - - - -
    translate(width-25, 150);
    fill(100,MIDIop+255);
    rect(0,0,8,-20);
    if(CHANvar == 12){
      fill(255,MIDIop+pad[71]);
      rect(2,-2,4,-16);
    }
  popMatrix();
  pushMatrix();  // // // // // // // VI25 keybed 70 - - - - - - - - -
    translate(width-33, 144);
    fill(100,MIDIop+255);
    rect(0,0,6,-18);
    if(CHANvar == 12){
      fill(255,MIDIop+pad[70]);
      rect(1,-1,4,-16);
    }
  popMatrix();
  pushMatrix();  // // // // // // // VI25 keybed 69 - - - - - - - - -
    translate(width-43, 150);
    fill(100,MIDIop+255);
    rect(0,0,8,-20);
    if(CHANvar == 12){
      fill(255,MIDIop+pad[69]);
      rect(2,-2,4,-16);
    }
  popMatrix();
  pushMatrix();  // // // // // // // VI25 keybed 68 - - - - - - - - -
    translate(width-51, 144);
    fill(100,MIDIop+255);
    rect(0,0,6,-18);
    if(CHANvar == 12){
      fill(255,MIDIop+pad[68]);
      rect(1,-1,4,-16);
    }
  popMatrix();
  pushMatrix();  // // // // // // // VI25 keybed 67 - - - - - - - - -
    translate(width-61, 150);
    fill(100,MIDIop+255);
    rect(0,0,8,-20);
    if(CHANvar == 12){
      fill(255,MIDIop+pad[67]);
      rect(2,-2,4,-16);
    }
  popMatrix();
  pushMatrix();  // // // // // // // VI25 keybed 66 - - - - - - - - -
    translate(width-69, 144);
    fill(100,MIDIop+255);
    rect(0,0,6,-18);
    if(CHANvar == 12){
      fill(255,MIDIop+pad[66]);
      rect(1,-1,4,-16);
    }
  popMatrix();
  pushMatrix();  // // // // // // // VI25 keybed 65 - - - - - - - - -
    translate(width-79, 150);
    fill(100,MIDIop+255);
    rect(0,0,8,-20);
    if(CHANvar == 12){
      fill(255,MIDIop+pad[65]);
      rect(2,-2,4,-16);
    }
  popMatrix();
  pushMatrix();  // // // // // // // VI25 keybed 64 - - - - - - - - -
    translate(width-89, 150);
    fill(100,MIDIop+255);
    rect(0,0,8,-20);
    if(CHANvar == 12){
      fill(255,MIDIop+pad[64]);
      rect(2,-2,4,-16);
    }
  popMatrix();
  pushMatrix();  // // // // // // // VI25 keybed 63 - - - - - - - - -
    translate(width-97, 144);
    fill(100,MIDIop+255);
    rect(0,0,6,-18);
    if(CHANvar == 12){
      fill(255,MIDIop+pad[63]);
      rect(1,-1,4,-16);
    }
  popMatrix();
  pushMatrix();  // // // // // // // VI25 keybed 62 - - - - - - - - -
    translate(width-107, 150);
    fill(100,MIDIop+255);
    rect(0,0,8,-20);
    if(CHANvar == 12){
      fill(255,MIDIop+pad[62]);
      rect(2,-2,4,-16);
    }
  popMatrix();
  pushMatrix();  // // // // // // // VI25 keybed 61 - - - - - - - - -
    translate(width-115, 144);
    fill(100,MIDIop+255);
    rect(0,0,6,-18);
    if(CHANvar == 12){
      fill(255,MIDIop+pad[61]);
      rect(1,-1,4,-16);
    }
  popMatrix();
  pushMatrix();  // // // // // // // VI25 keybed 60 - - - - - - - - -
    translate(width-125, 150);
    fill(100,MIDIop+255);
    rect(0,0,8,-20);
    if(CHANvar == 12){
      fill(255,MIDIop+pad[60]);
      rect(2,-2,4,-16);
    }
  popMatrix();
  pushMatrix();  // // // // // // // VI25 keybed 59 - - - - - - - - -
    translate(width-135, 150);
    fill(100,MIDIop+255);
    rect(0,0,8,-20);
    if(CHANvar == 12){
      fill(255,MIDIop+pad[59]);
      rect(2,-2,4,-16);
    }
  popMatrix();
  pushMatrix();  // // // // // // // VI25 keybed 58 - - - - - - - - -
    translate(width-143, 144);
    fill(100,MIDIop+255);
    rect(0,0,6,-18);
    if(CHANvar == 12){
      fill(255,MIDIop+pad[58]);
      rect(1,-1,4,-16);
    }
  popMatrix();
  pushMatrix();  // // // // // // // VI25 keybed 57 - - - - - - - - -
    translate(width-153, 150);
    fill(100,MIDIop+255);
    rect(0,0,8,-20);
    if(CHANvar == 12){
      fill(255,MIDIop+pad[57]);
      rect(2,-2,4,-16);
    }
  popMatrix();
  pushMatrix();  // // // // // // // VI25 keybed 56 - - - - - - - - -
    translate(width-161, 144);
    fill(100,MIDIop+255);
    rect(0,0,6,-18);
    if(CHANvar == 12){
      fill(255,MIDIop+pad[56]);
      rect(1,-1,4,-16);
    }
  popMatrix();
  pushMatrix();  // // // // // // // VI25 keybed 55 - - - - - - - - -
    translate(width-171, 150);
    fill(100,MIDIop+255);
    rect(0,0,8,-20);
    if(CHANvar == 12){
      fill(255,MIDIop+pad[55]);
      rect(2,-2,4,-16);
    }
  popMatrix();
  pushMatrix();  // // // // // // // VI25 keybed 54 - - - - - - - - -
    translate(width-179, 144);
    fill(100,MIDIop+255);
    rect(0,0,6,-18);
    if(CHANvar == 12){
      fill(255,MIDIop+pad[54]);
      rect(1,-1,4,-16);
    }
  popMatrix();
  pushMatrix();  // // // // // // // VI25 keybed 53 - - - - - - - - -
    translate(width-189, 150);
    fill(100,MIDIop+255);
    rect(0,0,8,-20);
    if(CHANvar == 12){
      fill(255,MIDIop+pad[53]);
      rect(2,-2,4,-16);
    }
  popMatrix();
  pushMatrix();  // // // // // // // VI25 keybed 52 - - - - - - - - -
    translate(width-199, 150);
    fill(100,MIDIop+255);
    rect(0,0,8,-20);
    if(CHANvar == 12){
      fill(255,MIDIop+pad[52]);
      rect(2,-2,4,-16);
    }
  popMatrix();
  pushMatrix();  // // // // // // // VI25 keybed 51 - - - - - - - - -
    translate(width-207, 144);
    fill(100,MIDIop+255);
    rect(0,0,6,-18);
    if(CHANvar == 12){
      fill(255,MIDIop+pad[51]);
      rect(1,-1,4,-16);
    }
  popMatrix();
  pushMatrix();  // // // // // // // VI25 keybed 50 - - - - - - - - -
    translate(width-217, 150);
    fill(100,MIDIop+255);
    rect(0,0,8,-20);
    if(CHANvar == 12){
      fill(255,MIDIop+pad[50]);
      rect(2,-2,4,-16);
    }
  popMatrix();
  pushMatrix();  // // // // // // // VI25 keybed 49 - - - - - - - - -
    translate(width-225, 144);
    fill(100,MIDIop+255);
    rect(0,0,6,-18);
    if(CHANvar == 12){
      fill(255,MIDIop+pad[49]);
      rect(1,-1,4,-16);
    }
  popMatrix();
  pushMatrix();  // // // // // // // VI25 keybed 48 - - - - - - - - -
    translate(width-235, 150);
    fill(100,MIDIop+255);
    rect(0,0,8,-20);
    if(CHANvar == 12){
      fill(255,MIDIop+pad[48]);
      rect(2,-2,4,-16);
    }
  popMatrix();
  // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - chan 14 knobs
  pushMatrix();  // // // // // // // VI25 knob 27 - - - - - - - - -
    translate(width-20, 168);
    fill(100,MIDIop+255);
    polygon(0,0,16,6);
    fill(165,MIDIop+255);
    if(CHANvar == 14 && knobR[27] > 0){
      rotate(radians(-2*(map(64-knobR[27],-63,64,-60,60))));
      fill(255,MIDIop+255);
      polygon(0,0,12,6);
      fill(0,MIDIop+255);
      rect(-2,0,4,-17);
    }
    else{
      rotate(radians(-120));
      polygon(0,0,12,6);
      fill(100,MIDIop+255);
      rect(-2,0,4,-17);
    }
  popMatrix();
  pushMatrix();  // // // // // // // VI25 knob 26 - - - - - - - - -
    translate(width-50, 168);
    fill(100,MIDIop+255);
    polygon(0,0,16,6);
    fill(165,MIDIop+255);
    if(CHANvar == 14 && knobR[26] > 0){
      rotate(radians(-2*(map(64-knobR[26],-63,64,-60,60))));
      fill(255,MIDIop+255);
      polygon(0,0,12,6);
      fill(0,MIDIop+255);
      rect(-2,0,4,-17);
    }
    else{
      rotate(radians(-120));
      polygon(0,0,12,6);
      fill(100,MIDIop+255);
      rect(-2,0,4,-17);
    }
  popMatrix();
  pushMatrix();  // // // // // // // VI25 knob 25 - - - - - - - - -
    translate(width-80, 168);
    fill(100,MIDIop+255);
    polygon(0,0,16,6);
    fill(165,MIDIop+255);
    if(CHANvar == 14 && knobR[25] > 0){
      rotate(radians(-2*(map(64-knobR[25],-63,64,-60,60))));
      fill(255,MIDIop+255);
      polygon(0,0,12,6);
      fill(0,MIDIop+255);
      rect(-2,0,4,-17);
    }
    else{
      rotate(radians(-120));
      polygon(0,0,12,6);
      fill(100,MIDIop+255);
      rect(-2,0,4,-17);
    }
  popMatrix();
  pushMatrix();  // // // // // // // VI25 knob 24 - - - - - - - - -
    translate(width-110, 168);
    fill(100,MIDIop+255);
    polygon(0,0,16,6);
    fill(165,MIDIop+255);
    if(CHANvar == 14 && knobR[24] > 0){
      rotate(radians(-2*(map(64-knobR[24],-63,64,-60,60))));
      fill(255,MIDIop+255);
      polygon(0,0,12,6);
      fill(0,MIDIop+255);
      rect(-2,0,4,-17);
    }
    else{
      rotate(radians(-120));
      polygon(0,0,12,6);
      fill(100,MIDIop+255);
      rect(-2,0,4,-17);
    }
  popMatrix();
  pushMatrix();  // // // // // // // VI25 knob 23 - - - - - - - - -
    translate(width-140, 168);
    fill(100,MIDIop+255);
    polygon(0,0,16,6);
    fill(165,MIDIop+255);
    if(CHANvar == 14 && knobR[23] > 0){
      rotate(radians(-2*(map(64-knobR[23],-63,64,-60,60))));
      fill(255,MIDIop+255);
      polygon(0,0,12,6);
      fill(0,MIDIop+255);
      rect(-2,0,4,-17);
      knobR[23] = 0;
    }
    else{
      rotate(radians(-120));
      polygon(0,0,12,6);
      fill(100,MIDIop+255);
      rect(-2,0,4,-17);
    }
  popMatrix();
  pushMatrix();  // // // // // // // VI25 knob 22 - - - - - - - - -
    translate(width-170, 168);
    fill(100,MIDIop+255);
    polygon(0,0,16,6);
    fill(165,MIDIop+255);
    if(CHANvar == 14 && knobR[22] > 0){
      rotate(radians(-2*(map(64-knobR[22],-63,64,-60,60))));
      fill(255,MIDIop+255);
      polygon(0,0,12,6);
      fill(0,MIDIop+255);
      rect(-2,0,4,-17);
    }
    else{
      rotate(radians(-120));
      polygon(0,0,12,6);
      fill(100,MIDIop+255);
      rect(-2,0,4,-17);
    }
  popMatrix();
  pushMatrix();  // // // // // // // VI25 knob 21 - - - - - - - - -
    translate(width-200, 168);
    fill(100,MIDIop+255);
    polygon(0,0,16,6);
    fill(165,MIDIop+255);
    if(CHANvar == 14 && knobR[21] > 0){
      rotate(radians(-2*(map(64-knobR[21],-63,64,-60,60))));
      fill(255,MIDIop+255);
      polygon(0,0,12,6);
      fill(0,MIDIop+255);
      rect(-2,0,4,-17);
    }
    else{
      rotate(radians(-120));
      polygon(0,0,12,6);
      fill(100,MIDIop+255);
      rect(-2,0,4,-17);
    }
  popMatrix();
  pushMatrix();  // // // // // // // VI25 knob 20 - - - - - - - - -
    translate(width-230, 168);
    fill(100,MIDIop+255);
    polygon(0,0,16,6);
    fill(165,MIDIop+255);
    if(CHANvar == 14 && knobR[20] > 0){
      rotate(radians(-2*(map(64-knobR[20],-63,64,-60,60))));
      fill(255,MIDIop+255);
      polygon(0,0,12,6);
      fill(0,MIDIop+255);
      rect(-2,0,4,-17);
    }
    else{
      rotate(radians(-120));
      polygon(0,0,12,6);
      fill(100,MIDIop+255);
      rect(-2,0,4,-17);
    }
  popMatrix();
  // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - chan 14 button row 1
  pushMatrix();  // // // // // // // VI25 item 55 - - - - - - - - - -
    translate(width-20, 200);
    fill(100,MIDIop+255);
    rect(-13,0,26,-14);
    if(CHANvar == 14){
      fill(255,MIDIop+pad[55]);
      rect(-10,-3,20,-8);
    }
  popMatrix();
  pushMatrix();  // // // // // // // VI25 item 62 - - - - - - - - - -
    translate(width-50, 200);
    fill(100,MIDIop+255);
    rect(-13,0,26,-14);
    if(CHANvar == 14){
      fill(255,MIDIop+pad[54]);
      rect(-10,-3,20,-8);
    }
  popMatrix();
  pushMatrix();  // // // // // // // VI25 item 53 - - - - - - - - - -
    translate(width-80, 200);
    fill(100,MIDIop+255);
    rect(-13,0,26,-14);
    if(CHANvar == 14){
      fill(255,MIDIop+pad[53]);
      rect(-10,-3,20,-8);
    }
  popMatrix();
  pushMatrix();  // // // // // // // VI25 item 52 - - - - - - - - - -
    translate(width-110, 200);
    fill(100,MIDIop+255);
    rect(-13,0,26,-14);
    if(CHANvar == 14){
      fill(255,MIDIop+pad[52]);
      rect(-10,-3,20,-8);
    }
  popMatrix();
  pushMatrix();  // // // // // // // VI25 item 51 - - - - - - - - - -
    translate(width-140, 200);
    fill(100,MIDIop+255);
    rect(-13,0,26,-14);
    if(CHANvar == 14){
      fill(255,MIDIop+pad[51]);
      rect(-10,-3,20,-8);
    }
  popMatrix();
  pushMatrix();  // // // // // // // VI25 item 50 - - - - - - - - - -
    translate(width-170, 200);
    fill(100,MIDIop+255);
    rect(-13,0,26,-14);
    if(CHANvar == 14){
      fill(255,MIDIop+pad[50]);
      rect(-10,-3,20,-8);
    }
  popMatrix();
  pushMatrix();  // // // // // // // VI25 item 49 - - - - - - - - - -
    translate(width-200, 200);
    fill(100,MIDIop+255);
    rect(-13,0,26,-14);
    if(CHANvar == 14){
      fill(255,MIDIop+pad[49]);
      rect(-10,-3,20,-8);
    }
  popMatrix();
  pushMatrix();  // // // // // // // VI25 item 48 - - - - - - - - - -
    translate(width-230, 200);
    fill(100,MIDIop+255);
    rect(-13,0,26,-14);
    if(CHANvar == 14){
      fill(255,MIDIop+pad[48]);
      rect(-10,-3,20,-8);
    }
  popMatrix();
  // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - chan 14 button row 2
  pushMatrix();  // // // // // // // VI25 item 63 - - - - - - - - - -
    translate(width-20, 218);
    fill(100,MIDIop+255);
    rect(-13,0,26,-14);
    if(CHANvar == 14){
      fill(255,MIDIop+pad[63]);
      rect(-10,-3,20,-8);
    }
  popMatrix();
  pushMatrix();  // // // // // // // VI25 item 62 - - - - - - - - - -
    translate(width-50, 218);
    fill(100,MIDIop+255);
    rect(-13,0,26,-14);
    if(CHANvar == 14){
      fill(255,MIDIop+pad[62]);
      rect(-10,-3,20,-8);
    }
  popMatrix();
  pushMatrix();  // // // // // // // VI25 item 61 - - - - - - - - - -
    translate(width-80, 218);
    fill(100,MIDIop+255);
    rect(-13,0,26,-14);
    if(CHANvar == 14){
      fill(255,MIDIop+pad[61]);
      rect(-10,-3,20,-8);
    }
  popMatrix();
  pushMatrix();  // // // // // // // VI25 item 60 - - - - - - - - - -
    translate(width-110, 218);
    fill(100,MIDIop+255);
    rect(-13,0,26,-14);
    if(CHANvar == 14){
      fill(255,MIDIop+pad[60]);
      rect(-10,-3,20,-8);
    }
  popMatrix();
  pushMatrix();  // // // // // // // VI25 item 59 - - - - - - - - - -
    translate(width-140, 218);
    fill(100,MIDIop+255);
    rect(-13,0,26,-14);
    if(CHANvar == 14){
      fill(255,MIDIop+pad[59]);
      rect(-10,-3,20,-8);
    }
  popMatrix();
  pushMatrix();  // // // // // // // VI25 item 58 - - - - - - - - - -
    translate(width-170, 218);
    fill(100,MIDIop+255);
    rect(-13,0,26,-14);
    if(CHANvar == 14){
      fill(255,MIDIop+pad[58]);
      rect(-10,-3,20,-8);
    }
  popMatrix();
  pushMatrix();  // // // // // // // VI25 item 57 - - - - - - - - - -
    translate(width-200, 218);
    fill(100,MIDIop+255);
    rect(-13,0,26,-14);
    if(CHANvar == 14){
      fill(255,MIDIop+pad[57]);
      rect(-10,-3,20,-8);
    }
  popMatrix();
  pushMatrix();  // // // // // // // VI25 item 56 - - - - - - - - - -
    translate(width-230, 218);
    fill(100,MIDIop+255);
    rect(-13,0,26,-14);
    if(CHANvar == 14){
      fill(255,MIDIop+pad[56]);
      rect(-10,-3,20,-8);
    }
  popMatrix();
  // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - chan 14 button row 3
  pushMatrix();  // // // // // // // VI25 item 71 - - - - - - - - - -
    translate(width-20, 236);
    fill(100,MIDIop+255);
    rect(-13,0,26,-14);
    if(CHANvar == 14){
      fill(255,MIDIop+pad[71]);
      rect(-10,-3,20,-8);
    }
  popMatrix();
  pushMatrix();  // // // // // // // VI25 item 70 - - - - - - - - - -
    translate(width-50, 236);
    fill(100,MIDIop+255);
    rect(-13,0,26,-14);
    if(CHANvar == 14){
      fill(255,MIDIop+pad[70]);
      rect(-10,-3,20,-8);
    }
  popMatrix();
  pushMatrix();  // // // // // // // VI25 item 69 - - - - - - - - - -
    translate(width-80, 236);
    fill(100,MIDIop+255);
    rect(-13,0,26,-14);
    if(CHANvar == 14){
      fill(255,MIDIop+pad[69]);
      rect(-10,-3,20,-8);
    }
  popMatrix();
  pushMatrix();  // // // // // // // VI25 item 68 - - - - - - - - - -
    translate(width-110, 236);
    fill(100,MIDIop+255);
    rect(-13,0,26,-14);
    if(CHANvar == 14){
      fill(255,MIDIop+pad[68]);
      rect(-10,-3,20,-8);
    }
  popMatrix();
  pushMatrix();  // // // // // // // VI25 item 67 - - - - - - - - - -
    translate(width-140, 236);
    fill(100,MIDIop+255);
    rect(-13,0,26,-14);
    if(CHANvar == 14){
      fill(255,MIDIop+pad[67]);
      rect(-10,-3,20,-8);
    }
  popMatrix();
  pushMatrix();  // // // // // // // VI25 item 66 - - - - - - - - - -
    translate(width-170, 236);
    fill(100,MIDIop+255);
    rect(-13,0,26,-14);
    if(CHANvar == 14){
      fill(255,MIDIop+pad[66]);
      rect(-10,-3,20,-8);
    }
  popMatrix();
  pushMatrix();  // // // // // // // VI25 item 65 - - - - - - - - - -
    translate(width-200, 236);
    fill(100,MIDIop+255);
    rect(-13,0,26,-14);
    if(CHANvar == 14){
      fill(255,MIDIop+pad[65]);
      rect(-10,-3,20,-8);
    }
  popMatrix();
  pushMatrix();  // // // // // // // VI25 item 64 - - - - - - - - - -
    translate(width-230, 236);
    fill(100,MIDIop+255);
    rect(-13,0,26,-14);
    if(CHANvar == 14){
      fill(255,MIDIop+pad[64]);
      rect(-10,-3,20,-8);
    }
  popMatrix();
  // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - Denon DJ MC7000 Chan 3
  pushMatrix();  // // // // // // // MC7000 knob 23 - - - - - - - - -
    translate(width-20, 260);
    fill(100,MIDIop+255);
    polygon(0,0,14,6);
    fill(165,MIDIop+255);
    if(CHANvar == 3 & knobR[23] > 64 || CHANvar == 3 & knobR[23] < 64){
      rotate(radians(-2*(map(64-knobR[23],-63,64,-60,60))));
      fill(255,MIDIop+255);
      polygon(0,0,10,6);
      fill(0,MIDIop+255);
      rect(-2,0,4,-15);
    }
    else{
      polygon(0,0,10,6);
      fill(100,MIDIop+255);
      rect(-2,0,4,-15);
    }
  popMatrix();
  pushMatrix();  // // // // // // // MC7000 knob 24 - - - - - - - - -
    translate(width-20, 290);
    fill(100,MIDIop+255);
    polygon(0,0,14,6);
    fill(165,MIDIop+255);
    if(CHANvar == 3 & knobR[24] > 64 || CHANvar == 3 & knobR[24] < 64){
      rotate(radians(-2*(map(64-knobR[24],-63,64,-60,60))));
      fill(255,MIDIop+255);
      polygon(0,0,10,6);
      fill(0,MIDIop+255);
      rect(-2,0,4,-15);
    }
    else{
      polygon(0,0,10,6);
      fill(100,MIDIop+255);
      rect(-2,0,4,-15);
    }
  popMatrix();
  pushMatrix();  // // // // // // // MC7000 knob 25 - - - - - - - - -
    translate(width-20, 320);
    fill(100,MIDIop+255);
    polygon(0,0,14,6);
    fill(165,MIDIop+255);
    if(CHANvar == 3 & knobR[25] > 64 || CHANvar == 3 & knobR[25] < 64){
      rotate(radians(-2*(map(64-knobR[25],-63,64,-60,60))));
      fill(255,MIDIop+255);
      polygon(0,0,10,6);
      fill(0,MIDIop+255);
      rect(-2,0,4,-15);
    }
    else{
      polygon(0,0,10,6);
      fill(100,MIDIop+255);
      rect(-2,0,4,-15);
    }
  popMatrix();
  pushMatrix();  // // // // // // // MC7000 knob 26 - - - - - - - - -
    translate(width-20, 352);
    fill(100,MIDIop+255);
    polygon(0,0,16,6);
    fill(165,MIDIop+255);
    if(CHANvar == 3 & knobR[26] > 64 || CHANvar == 3 & knobR[26] < 64){
      rotate(radians(-2*(map(64-knobR[26],-63,64,-60,60))));
      fill(255,MIDIop+255);
      polygon(0,0,12,6);
      fill(0,MIDIop+255);
      rect(-2,0,4,-17);
    }
    else{
      polygon(0,0,12,6);
      fill(100,MIDIop+255);
      rect(-2,0,4,-17);
    }
  popMatrix();
  pushMatrix();  // // // // // // // MC7000 fader4 - - - - - - - - -
    translate(width-20, 375);
    fill(100,MIDIop+255);
    rect(-4,0,8,103);
    fill(0,MIDIop+255);
    rect(-2,2,4,99);
    if(CHANvar == 3 && fader4 >= 1){
      fill(255,MIDIop+255);
    }
    else{
      fill(100,MIDIop+255);
    }
    rect(-12, 95 - map(fader4, 0, 127, 0, 95), 24, 8);
  popMatrix();
  // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - Denon DJ MC7000 Chan 1
  pushMatrix();  // // // // // // // MC7000 knob 23 - - - - - - - - -
    translate(width-50, 260);
    fill(100,MIDIop+255);
    polygon(0,0,14,6);
    fill(165,MIDIop+255);
    if(CHANvar == 1 & knobR[23] > 64 || CHANvar == 1 & knobR[23] < 64){
      rotate(radians(-2*(map(64-knobR[23],-63,64,-60,60))));
      fill(255,MIDIop+255);
      polygon(0,0,10,6);
      fill(0,MIDIop+255);
      rect(-2,0,4,-15);
    }
    else{
      polygon(0,0,10,6);
      fill(100,MIDIop+255);
      rect(-2,0,4,-15);
    }
  popMatrix();
  pushMatrix();  // // // // // // // MC7000 knob 24 - - - - - - - - -
    translate(width-50, 290);
    fill(100,MIDIop+255);
    polygon(0,0,14,6);
    fill(165,MIDIop+255);
    if(CHANvar == 1 & knobR[24] > 64 || CHANvar == 1 & knobR[24] < 64){
      rotate(radians(-2*(map(64-knobR[24],-63,64,-60,60))));
      fill(255,MIDIop+255);
      polygon(0,0,10,6);
      fill(0,MIDIop+255);
      rect(-2,0,4,-15);
    }
    else{
      polygon(0,0,10,6);
      fill(100,MIDIop+255);
      rect(-2,0,4,-15);
    }
  popMatrix();
  pushMatrix();  // // // // // // // MC7000 knob 25 - - - - - - - - -
    translate(width-50, 320);
    fill(100,MIDIop+255);
    polygon(0,0,14,6);
    fill(165,MIDIop+255);
    if(CHANvar == 1 & knobR[25] > 64 || CHANvar == 1 & knobR[25] < 64){
      rotate(radians(-2*(map(64-knobR[25],-63,64,-60,60))));
      fill(255,MIDIop+255);
      polygon(0,0,10,6);
      fill(0,MIDIop+255);
      rect(-2,0,4,-15);
    }
    else{
      polygon(0,0,10,6);
      fill(100,MIDIop+255);
      rect(-2,0,4,-15);
    }
  popMatrix();
  pushMatrix();  // // // // // // // MC7000 knob 26 - - - - - - - - -
    translate(width-50, 352);
    fill(100,MIDIop+255);
    polygon(0,0,16,6);
    fill(165,MIDIop+255);
    if(CHANvar == 1 & knobR[26] > 64 || CHANvar == 1 & knobR[26] < 64){
      rotate(radians(-2*(map(64-knobR[26],-63,64,-60,60))));
      fill(255,MIDIop+255);
      polygon(0,0,12,6);
      fill(0,MIDIop+255);
      rect(-2,0,4,-17);
    }
    else{
      polygon(0,0,12,6);
      fill(100,MIDIop+255);
      rect(-2,0,4,-17);
    }
  popMatrix();
  pushMatrix();  // // // // // // // MC7000 fader2 - - - - - - - - -
    translate(width-50, 375);
    fill(100,MIDIop+255);
    rect(-4,0,8,103);
    fill(0,MIDIop+255);
    rect(-2,2,4,99);
    if(CHANvar == 1 && fader2 >= 1){
      fill(255,MIDIop+255);
    }
    else{
      fill(100,MIDIop+255);
    }
    rect(-12, 95 - map(fader2, 0, 127, 0, 95), 24, 8);
  popMatrix();
  // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - Denon DJ MC7000 Chan 0
  pushMatrix();  // // // // // // // MC7000 knob 23 - - - - - - - - -
    translate(width-80, 260);
    fill(100,MIDIop+255);
    polygon(0,0,14,6);
    fill(165,MIDIop+255);
    if(CHANvar == 0 & knobR[23] > 64 || CHANvar == 0 & knobR[23] < 64){
      rotate(radians(-2*(map(64-knobR[23],-63,64,-60,60))));
      fill(255,MIDIop+255);
      polygon(0,0,10,6);
      fill(0,MIDIop+255);
      rect(-2,0,4,-15);
    }
    else{
      polygon(0,0,10,6);
      fill(100,MIDIop+255);
      rect(-2,0,4,-15);
    }
  popMatrix();
  pushMatrix();  // // // // // // // MC7000 knob 24 - - - - - - - - -
    translate(width-80, 290);
    fill(100,MIDIop+255);
    polygon(0,0,14,6);
    fill(165,MIDIop+255);
    if(CHANvar == 0 & knobR[24] > 64 || CHANvar == 0 & knobR[24] < 64){
      rotate(radians(-2*(map(64-knobR[24],-63,64,-60,60))));
      fill(255,MIDIop+255);
      polygon(0,0,10,6);
      fill(0,MIDIop+255);
      rect(-2,0,4,-15);
    }
    else{
      polygon(0,0,10,6);
      fill(100,MIDIop+255);
      rect(-2,0,4,-15);
    }
  popMatrix();
  pushMatrix();  // // // // // // // MC7000 knob 25 - - - - - - - - -
    translate(width-80, 320);
    fill(100,MIDIop+255);
    polygon(0,0,14,6);
    fill(165,MIDIop+255);
    if(CHANvar == 0 & knobR[25] > 64 || CHANvar == 0 & knobR[25] < 64){
      rotate(radians(-2*(map(64-knobR[25],-63,64,-60,60))));
      fill(255,MIDIop+255);
      polygon(0,0,10,6);
      fill(0,MIDIop+255);
      rect(-2,0,4,-15);
    }
    else{
      polygon(0,0,10,6);
      fill(100,MIDIop+255);
      rect(-2,0,4,-15);
    }
  popMatrix();
  pushMatrix();  // // // // // // // MC7000 knob 26 - - - - - - - - -
    translate(width-80, 352);
    fill(100,MIDIop+255);
    polygon(0,0,16,6);
    fill(165,MIDIop+255);
    if(CHANvar == 0 & knobR[26] > 64 || CHANvar == 0 & knobR[26] < 64){
      rotate(radians(-2*(map(64-knobR[26],-63,64,-60,60))));
      fill(255,MIDIop+255);
      polygon(0,0,12,6);
      fill(0,MIDIop+255);
      rect(-2,0,4,-17);
    }
    else{
      polygon(0,0,12,6);
      fill(100,MIDIop+255);
      rect(-2,0,4,-17);
    }
  popMatrix();
  pushMatrix();  // // // // // // // MC7000 fader1 - - - - - - - - -
    translate(width-80, 375);
    fill(100,MIDIop+255);
    rect(-4,0,8,103);
    fill(0,MIDIop+255);
    rect(-2,2,4,99);
    if(CHANvar == 0 && fader1 >= 1){
      fill(255,MIDIop+255);
    }
    else{
      fill(100,MIDIop+255);
    }
    rect(-12, 95 - map(fader1, 0, 127, 0, 95), 24, 8);
  popMatrix();
  // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - Denon DJ MC7000 Chan 2
  pushMatrix();  // // // // // // // MC7000 knob 23 - - - - - - - - -
    translate(width-110, 260);
    fill(100,MIDIop+255);
    polygon(0,0,14,6);
    fill(165,MIDIop+255);
    if(CHANvar == 2 & knobR[23] > 64 || CHANvar == 2 & knobR[23] < 64){
      rotate(radians(-2*(map(64-knobR[23],-63,64,-60,60))));
      fill(255,MIDIop+255);
      polygon(0,0,10,6);
      fill(0,MIDIop+255);
      rect(-2,0,4,-15);
    }
    else{
      polygon(0,0,10,6);
      fill(100,MIDIop+255);
      rect(-2,0,4,-15);
    }
  popMatrix();
  pushMatrix();  // // // // // // // MC7000 knob 24 - - - - - - - - -
    translate(width-110, 290);
    fill(100,MIDIop+255);
    polygon(0,0,14,6);
    fill(165,MIDIop+255);
    if(CHANvar == 2 & knobR[24] > 64 || CHANvar == 2 & knobR[24] < 64){
      rotate(radians(-2*(map(64-knobR[24],-63,64,-60,60))));
      fill(255,MIDIop+255);
      polygon(0,0,10,6);
      fill(0,MIDIop+255);
      rect(-2,0,4,-15);
    }
    else{
      polygon(0,0,10,6);
      fill(100,MIDIop+255);
      rect(-2,0,4,-15);
    }
  popMatrix();
  pushMatrix();  // // // // // // // MC7000 knob 25 - - - - - - - - -
    translate(width-110, 320);
    fill(100,MIDIop+255);
    polygon(0,0,14,6);
    fill(165,MIDIop+255);
    if(CHANvar == 2 & knobR[25] > 64 || CHANvar == 2 & knobR[25] < 64){
      rotate(radians(-2*(map(64-knobR[25],-63,64,-60,60))));
      fill(255,MIDIop+255);
      polygon(0,0,10,6);
      fill(0,MIDIop+255);
      rect(-2,0,4,-15);
    }
    else{
      polygon(0,0,10,6);
      fill(100,MIDIop+255);
      rect(-2,0,4,-15);
    }
  popMatrix();
  pushMatrix();  // // // // // // // MC7000 knob 26 - - - - - - - - -
    translate(width-110, 352);
    fill(100,MIDIop+255);
    polygon(0,0,16,6);
    fill(165,MIDIop+255);
    if(CHANvar == 2 & knobR[26] > 64 || CHANvar == 2 & knobR[26] < 64){
      rotate(radians(-2*(map(64-knobR[26],-63,64,-60,60))));
      fill(255,MIDIop+255);
      polygon(0,0,12,6);
      fill(0,MIDIop+255);
      rect(-2,0,4,-17);
    }
    else{
      polygon(0,0,12,6);
      fill(100,MIDIop+255);
      rect(-2,0,4,-17);
    }
  popMatrix();
  pushMatrix();  // // // // // // // MC7000 fader3 - - - - - - - - -
    translate(width-110, 375);
    fill(100,MIDIop+255);
    rect(-4,0,8,103);
    fill(0,MIDIop+255);
    rect(-2,2,4,99);
    if(CHANvar == 2 && fader3 >= 1){
      fill(255,MIDIop+255);
    }
    else{
      fill(100,MIDIop+255);
    }
    rect(-12, 95 - map(fader3, 0, 127, 0, 95), 24, 8);
  popMatrix();
  // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - Crossfader
  pushMatrix();
    translate(width-65, 495);
    fill(100,MIDIop+255);
    rect(-27,-4,54,8);
    fill(0,MIDIop+255);
    rect(-25,-2,50,4);
    if(CHANvar == 15 & crossFader == 0 || CHANvar == 15 & crossFader == 127){
      fill(255,MIDIop+255);
    }
    else{
      fill(100,MIDIop+255);
    }
    rect(-27 + map(crossFader, 0, 127, 0, 46), -12, 8, 24);
  popMatrix();
  // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - Denon DJ MC7000 pads
  // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - deck 1
  pushMatrix();  // // // // // // // MC7000 pad 1 CUE - - - - - - - - -
    translate(width-260, 255);
    rotate( radians(360/16) );
    fill(100,MIDIop+255);
    polygon(0,0,11,8);
    if(CHANvar == 0){
      fill(255,MIDIop+pad[1]);
      polygon(0,0,7,8);
    }
  popMatrix();
  pushMatrix();  // // // // // // // MC7000 pad 20 - - - - - - - - -
    translate(width-230, 255);
    fill(100,MIDIop+255);
    polygon3(0,0,18,4);
    if(CHANvar == 4){
      fill(255,MIDIop+pad[20]);
      polygon3(0,0,14,4);
    }
  popMatrix();
  pushMatrix();  // // // // // // // MC7000 pad 21 - - - - - - - - -
    translate(width-200, 255);
    fill(100,MIDIop+255);
    polygon3(0,0,18,4);
    if(CHANvar == 4){
      fill(255,MIDIop+pad[21]);
      polygon3(0,0,14,4);
    }
  popMatrix();
  pushMatrix();  // // // // // // // MC7000 pad 22 - - - - - - - - -
    translate(width-170, 255);
    fill(100,MIDIop+255);
    polygon3(0,0,18,4);
    if(CHANvar == 4){
      fill(255,MIDIop+pad[22]);
      polygon3(0,0,14,4);
    }
  popMatrix();
  pushMatrix();  // // // // // // // MC7000 pad 23 - - - - - - - - -
    translate(width-140, 255);
    fill(100,MIDIop+255);
    polygon3(0,0,18,4);
    if(CHANvar == 4){
      fill(255,MIDIop+pad[23]);
      polygon3(0,0,14,4);
    }
  popMatrix();
  pushMatrix();  // // // // // // // MC7000 pad 0 PLAY - - - - - - - - -
    translate(width-263, 284);
    fill(100,MIDIop+255);
    polygon(0,0,13,3);
    if(CHANvar == 0){
      fill(255,MIDIop+pad[0]);
      polygon(0,0,8,3);
    }
  popMatrix();
  pushMatrix();  // // // // // // // MC7000 pad 24 - - - - - - - - -
    translate(width-230, 284);
    fill(100,MIDIop+255);
    polygon3(0,0,18,4);
    if(CHANvar == 4){
      fill(255,MIDIop+pad[24]);
      polygon3(0,0,14,4);
    }
  popMatrix();
  pushMatrix();  // // // // // // // MC7000 pad 25 - - - - - - - - -
    translate(width-200, 284);
    fill(100,MIDIop+255);
    polygon3(0,0,18,4);
    if(CHANvar == 4){
      fill(255,MIDIop+pad[25]);
      polygon3(0,0,14,4);
    }
  popMatrix();
  pushMatrix();  // // // // // // // MC7000 pad 26 - - - - - - - - -
    translate(width-170, 284);
    fill(100,MIDIop+255);
    polygon3(0,0,18,4);
    if(CHANvar == 4){
      fill(255,MIDIop+pad[26]);
      polygon3(0,0,14,4);
    }
  popMatrix();
  pushMatrix();  // // // // // // // MC7000 pad 27 - - - - - - - - -
    translate(width-140, 284);
    fill(100,MIDIop+255);
    polygon3(0,0,18,4);
    if(CHANvar == 4){
      fill(255,MIDIop+pad[27]);
      polygon3(0,0,14,4);
    }
  popMatrix();
  // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - deck 2
  pushMatrix();  // // // // // // // MC7000 pad 1 CUE - - - - - - - - -
    translate(width-260, 316);
    rotate( radians(360/16) );
    fill(100,MIDIop+255);
    polygon(0,0,11,8);
    if(CHANvar == 1){
      fill(255,MIDIop+pad[1]);
      polygon(0,0,7,8);
    }
  popMatrix();
  pushMatrix();  // // // // // // // MC7000 pad 20 - - - - - - - - -
    translate(width-230, 316);
    fill(100,MIDIop+255);
    polygon3(0,0,18,4);
    if(CHANvar == 5){
      fill(255,MIDIop+pad[20]);
      polygon3(0,0,14,4);
    }
  popMatrix();
  pushMatrix();  // // // // // // // MC7000 pad 21 - - - - - - - - -
    translate(width-200, 316);
    fill(100,MIDIop+255);
    polygon3(0,0,18,4);
    if(CHANvar == 5){
      fill(255,MIDIop+pad[21]);
      polygon3(0,0,14,4);
    }
  popMatrix();
  pushMatrix();  // // // // // // // MC7000 pad 22 - - - - - - - - -
    translate(width-170, 316);
    fill(100,MIDIop+255);
    polygon3(0,0,18,4);
    if(CHANvar == 5){
      fill(255,MIDIop+pad[22]);
      polygon3(0,0,14,4);
    }
  popMatrix();
  pushMatrix();  // // // // // // // MC7000 pad 23 - - - - - - - - -
    translate(width-140, 316);
    fill(100,MIDIop+255);
    polygon3(0,0,18,4);
    if(CHANvar == 5){
      fill(255,MIDIop+pad[23]);
      polygon3(0,0,14,4);
    }
  popMatrix();
  pushMatrix();  // // // // // // // MC7000 pad 0 PLAY - - - - - - - - -
    translate(width-263, 345);
    fill(100,MIDIop+255);
    polygon(0,0,13,3);
    if(CHANvar == 1){
      fill(255,MIDIop+pad[0]);
      polygon(0,0,8,3);
    }
  popMatrix();
  pushMatrix();  // // // // // // // MC7000 pad 24 - - - - - - - - -
    translate(width-230, 345);
    fill(100,MIDIop+255);
    polygon3(0,0,18,4);
    if(CHANvar == 5){
      fill(255,MIDIop+pad[24]);
      polygon3(0,0,14,4);
    }
  popMatrix();
  pushMatrix();  // // // // // // // MC7000 pad 25 - - - - - - - - -
    translate(width-200, 345);
    fill(100,MIDIop+255);
    polygon3(0,0,18,4);
    if(CHANvar == 5){
      fill(255,MIDIop+pad[25]);
      polygon3(0,0,14,4);
    }
  popMatrix();
  pushMatrix();  // // // // // // // MC7000 pad 26 - - - - - - - - -
    translate(width-170, 345);
    fill(100,MIDIop+255);
    polygon3(0,0,18,4);
    if(CHANvar == 5){
      fill(255,MIDIop+pad[26]);
      polygon3(0,0,14,4);
    }
  popMatrix();
  pushMatrix();  // // // // // // // MC7000 pad 27 - - - - - - - - -
    translate(width-140, 345);
    fill(100,MIDIop+255);
    polygon3(0,0,18,4);
    if(CHANvar == 5){
      fill(255,MIDIop+pad[27]);
      polygon3(0,0,14,4);
    }
  popMatrix();
  // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - deck 3
  pushMatrix();  // // // // // // // MC7000 pad 1 CUE - - - - - - - - -
    translate(width-260, 376);
    rotate( radians(360/16) );
    fill(100,MIDIop+255);
    polygon(0,0,11,8);
    if(CHANvar == 2){
      fill(255,MIDIop+pad[1]);
      polygon(0,0,7,8);
    }
  popMatrix();
  pushMatrix();  // // // // // // // MC7000 pad 20 - - - - - - - - -
    translate(width-230, 376);
    fill(100,MIDIop+255);
    polygon3(0,0,18,4);
    if(CHANvar == 6){
      fill(255,MIDIop+pad[20]);
      polygon3(0,0,14,4);
    }
  popMatrix();
  pushMatrix();  // // // // // // // MC7000 pad 21 - - - - - - - - -
    translate(width-200, 376);
    fill(100,MIDIop+255);
    polygon3(0,0,18,4);
    if(CHANvar == 6){
      fill(255,MIDIop+pad[21]);
      polygon3(0,0,14,4);
    }
  popMatrix();
  pushMatrix();  // // // // // // // MC7000 pad 22 - - - - - - - - -
    translate(width-170, 376);
    fill(100,MIDIop+255);
    polygon3(0,0,18,4);
    if(CHANvar == 6){
      fill(255,MIDIop+pad[22]);
      polygon3(0,0,14,4);
    }
  popMatrix();
  pushMatrix();  // // // // // // // MC7000 pad 23 - - - - - - - - -
    translate(width-140, 376);
    fill(100,MIDIop+255);
    polygon3(0,0,18,4);
    if(CHANvar == 6){
      fill(255,MIDIop+pad[23]);
      polygon3(0,0,14,4);
    }
  popMatrix();
  pushMatrix();  // // // // // // // MC7000 pad 0 PLAY - - - - - - - - -
    translate(width-263, 405);
    fill(100,MIDIop+255);
    polygon(0,0,13,3);
    if(CHANvar == 2){
      fill(255,MIDIop+pad[0]);
      polygon(0,0,8,3);
    }
  popMatrix();
  pushMatrix();  // // // // // // // MC7000 pad 24 - - - - - - - - -
    translate(width-230, 405);
    fill(100,MIDIop+255);
    polygon3(0,0,18,4);
    if(CHANvar == 6){
      fill(255,MIDIop+pad[24]);
      polygon3(0,0,14,4);
    }
  popMatrix();
  pushMatrix();  // // // // // // // MC7000 pad 25 - - - - - - - - -
    translate(width-200, 405);
    fill(100,MIDIop+255);
    polygon3(0,0,18,4);
    if(CHANvar == 6){
      fill(255,MIDIop+pad[25]);
      polygon3(0,0,14,4);
    }
  popMatrix();
  pushMatrix();  // // // // // // // MC7000 pad 26 - - - - - - - - -
    translate(width-170, 405);
    fill(100,MIDIop+255);
    polygon3(0,0,18,4);
    if(CHANvar == 6){
      fill(255,MIDIop+pad[26]);
      polygon3(0,0,14,4);
    }
  popMatrix();
  pushMatrix();  // // // // // // // MC7000 pad 27 - - - - - - - - -
    translate(width-140, 405);
    fill(100,MIDIop+255);
    polygon3(0,0,18,4);
    if(CHANvar == 6){
      fill(255,MIDIop+pad[27]);
      polygon3(0,0,14,4);
    }
  popMatrix();
  // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - deck 4
  pushMatrix();  // // // // // // // MC7000 pad 1 CUE - - - - - - - - -
    translate(width-260, 436);
    rotate( radians(360/16) );
    fill(100,MIDIop+255);
    polygon(0,0,11,8);
    if(CHANvar == 3){
      fill(255,MIDIop+pad[1]);
      polygon(0,0,7,8);
    }
  popMatrix();
  pushMatrix();  // // // // // // // MC7000 pad 20 - - - - - - - - -
    translate(width-230, 436);
    fill(100,MIDIop+255);
    polygon3(0,0,18,4);
    if(CHANvar == 7){
      fill(255,MIDIop+pad[20]);
      polygon3(0,0,14,4);
    }
  popMatrix();
  pushMatrix();  // // // // // // // MC7000 pad 21 - - - - - - - - -
    translate(width-200, 436);
    fill(100,MIDIop+255);
    polygon3(0,0,18,4);
    if(CHANvar == 7){
      fill(255,MIDIop+pad[21]);
      polygon3(0,0,14,4);
    }
  popMatrix();
  pushMatrix();  // // // // // // // MC7000 pad 22 - - - - - - - - -
    translate(width-170, 436);
    fill(100,MIDIop+255);
    polygon3(0,0,18,4);
    if(CHANvar == 7){
      fill(255,MIDIop+pad[22]);
      polygon3(0,0,14,4);
    }
  popMatrix();
  pushMatrix();  // // // // // // // MC7000 pad 23 - - - - - - - - -
    translate(width-140, 436);
    fill(100,MIDIop+255);
    polygon3(0,0,18,4);
    if(CHANvar == 7){
      fill(255,MIDIop+pad[23]);
      polygon3(0,0,14,4);
    }
  popMatrix();
  pushMatrix();  // // // // // // // MC7000 pad 0 PLAY - - - - - - - - -
    translate(width-263, 465);
    fill(100,MIDIop+255);
    polygon(0,0,13,3);
    if(CHANvar == 3){
      fill(255,MIDIop+pad[0]);
      polygon(0,0,8,3);
    }
  popMatrix();
  pushMatrix();  // // // // // // // MC7000 pad 24 - - - - - - - - -
    translate(width-230, 465);
    fill(100,MIDIop+255);
    polygon3(0,0,18,4);
    if(CHANvar == 7){
      fill(255,MIDIop+pad[24]);
      polygon3(0,0,14,4);
    }
  popMatrix();
  pushMatrix();  // // // // // // // MC7000 pad 25 - - - - - - - - -
    translate(width-200, 465);
    fill(100,MIDIop+255);
    polygon3(0,0,18,4);
    if(CHANvar == 7){
      fill(255,MIDIop+pad[25]);
      polygon3(0,0,14,4);
    }
  popMatrix();
  pushMatrix();  // // // // // // // MC7000 pad 26 - - - - - - - - -
    translate(width-170, 465);
    fill(100,MIDIop+255);
    polygon3(0,0,18,4);
    if(CHANvar == 7){
      fill(255,MIDIop+pad[26]);
      polygon3(0,0,14,4);
    }
  popMatrix();
  pushMatrix();  // // // // // // // MC7000 pad 27 - - - - - - - - -
    translate(width-140, 465);
    fill(100,MIDIop+255);
    polygon3(0,0,18,4);
    if(CHANvar == 7){
      fill(255,MIDIop+pad[27]);
      polygon3(0,0,14,4);
    }
  popMatrix();

  // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - Labels
  textFont(Oswald24);
  pushMatrix();  // // // // // // // performance pads - - - - - - - - -
    translate(width-300, 7);
    noFill();
    strokeWeight(3);
    stroke(165,MIDIop+255);
    line(0,0,0,115);
    line(0,0,10,0);
    line(0,115,10,115);
      fill(165,MIDIop+255);
      text( "Performance Pads",  -200,  18);
  popMatrix();
  pushMatrix();  // // // // // // // keybed - - - - - - - - -
    translate(width-300, 128);
    noFill();
    strokeWeight(3);
    stroke(165,MIDIop+255);
    line(0,0,0,20);
    line(0,0,10,0);
    line(0,20,10,20);
      fill(165,MIDIop+255);
      text( "Key Bed",  -200,  18);
  popMatrix();
  pushMatrix();  // // // // // // // knobs and parameters - - - - - - - - -
    translate(width-300, 154);
    noFill();
    strokeWeight(3);
    stroke(165, MIDIop+255);
    line(0,0,0,80);
    line(0,0,10,0);
    line(0,80,10,80);
      fill(165, MIDIop+255);
      text( "Knobs / Parameters",  -200,  18);
  popMatrix();
  pushMatrix();  // // // // // // // Deck one - - - - - - - - -
    translate(width-300, 240);
    noFill();
    strokeWeight(3);
    stroke(165, MIDIop+255);
    line(0,0,0,56);
    line(0,0,10,0);
    line(0,56,10,56);
      fill(165, MIDIop+255);
      text( "Deck One",  -200,  18);
  popMatrix();
  pushMatrix();  // // // // // // // Deck two - - - - - - - - -
    translate(width-300, 302);
    noFill();
    strokeWeight(3);
    stroke(165, MIDIop+255);
    line(0,0,0,56);
    line(0,0,10,0);
    line(0,56,10,56);
      fill(165, MIDIop+255);
      text( "Deck Two",  -200,  18);
  popMatrix();
  pushMatrix();  // // // // // // // Deck three - - - - - - - - -
    translate(width-300, 364);
    noFill();
    strokeWeight(3);
    stroke(165, MIDIop+255);
    line(0,0,0,56);
    line(0,0,10,0);
    line(0,56,10,56);
      fill(165, MIDIop+255);
      text( "Deck Three",  -200,  18);
  popMatrix();
  pushMatrix();  // // // // // // // Deck four - - - - - - - - -
    translate(width-300, 426);
    noFill();
    strokeWeight(3);
    stroke(165, MIDIop+255);
    line(0,0,0,52);
    line(0,0,10,0);
    line(0,52,10,52);
      fill(165, MIDIop+255);
      text( "Deck Four",  -200,  18);
  popMatrix();
  pushMatrix();  // // // // // // // Mixer - - - - - - - - -
    translate(width-8, 520);
    noFill();
    strokeWeight(3);
    stroke(165, MIDIop+255);
    line(0,0,-115,0);
    line(0,0,0,-10);
    line(-115,0,-115,-10);
      fill(165, MIDIop+255);
      text( "Mixer",  -118,  28);
  popMatrix();
  
  popMatrix(); // // // // // // // // // // // // // // // // // // // // // // // // // // // // // // // //
  
  // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - Options
  strokeWeight(2);
  scale(1);
    fill(255, MIDIop+255);        // - - - - - - - - - - - - - - - - - - - - - - colorMode
    textFont(Oswald24);
    text("COLOR", width-110,height-232);
    //textFont(Roboto18);
    stroke(165, MIDIop+255);
    if (colorMode == 0){
      fill(165, MIDIop+255);
      text("screen", width-110,height-205);
       //L = "#042B4C";
       //M = "#005030";
       //H = "#693D00";
       //L2 = "#042B4C";
       //M2 = "#15A56B";
       //H2 = "#F35E1F";
       //G  = "#2D2BAA";
    }
    if (colorMode == 1){
      stroke(255, MIDIop+255);
      text("projector", width-110,height-205);
       //L = "#042B4C";
       //M = "#005030";
       //H = "#693D00";
       //L2 = "#042B4C";
       //M2 = "#15A56B";
       //H2 = "#F35E1F";
       //G  = "#2D2BAA";
    }
      noFill();
      line(width-110, height-229, width-110+66, height-229);
      //rect(width-114, height-262, 73, 60);
  
    fill(255, MIDIop+255);        // - - - - - - - - - - - - - - - - - - - - - - padmode
    textFont(Oswald36);
    text("PAD Mode", width-302-160,height-232);
    textFont(Oswald24);
    stroke(165, MIDIop+255);
    if (padMode == 0){
      fill(165, MIDIop+255);
      text("NONE", width-302-160,height-205);
    }
    if (padMode == 1){
      stroke(255, MIDIop+255);
      text("TARGET TRAILS", width-302-160,height-205);
    }
    if (padMode == 2){
      stroke(255, MIDIop+255);
      text("GRID GATE", width-302-160,height-205);
    }
    if (padMode == 3){
      stroke(255, MIDIop+255);
      text("UNTO VISUAL", width-302-160,height-205);
    }
      noFill();
      line(width-302-160, height-229, width-138-182, height-229);
      //rect(width-306-160, height-262, 150, 60);
    
    fill(255, MIDIop+255);        // - - - - - - - - - - - - - - - - - - - - - - knobmode
    textFont(Oswald36);
    text("KNOB Mode", width-302,height-232);
    textFont(Oswald24);
    stroke(165, MIDIop+255);
    if (knobMode == 0){
      fill(165, MIDIop+255);
      text("NONE", width-302,height-205);
    }
    if (knobMode == 1){
      stroke(255, MIDIop+255);
      text("SINGLE AXIS SCALE", width-302,height-205);
    }
    if (knobMode == 2){
      stroke(255, MIDIop+255);
      text("SPECIAL SCALE", width-302,height-205);
    }
    if (knobMode == 3){
      stroke(255, MIDIop+255);
      text("DESATURATION", width-302,height-205);
    }
      noFill();
      line(width-302, height-229, width-138, height-229);
      //rect(width-306,height-262, 200, 60);
    
  fill(175, MIDIop+255);
  
    textFont(Roboto18);
    text("'C' or '*' for Software Credits", width-302,height-180);
    textFont(Roboto18);
    text("'Q' or '/' for Quick Menu / MIDI", width-302,height-160);
    textFont(Roboto18);
    text("'M' to toggle Audio Monitoring", width-302,height-140);
    textFont(Roboto18);
    text("'B' to Re-initialize MIDI", width-302,height-120);
    textFont(Roboto18);
    text("Keys '1-3' for non-blur visuals", width-302,height-100);
    textFont(Roboto18);
    text("Keys '4-6' for mid-blur visuals", width-302,height-80);
    textFont(Roboto18);
    text("Keys '7-9' for full-blur visuals", width-302,height-60);
    textFont(Roboto18);
    text("Key '0' for Debug / Audio Screen", width-302,height-40);
    
  
  // M O D I F I E R S - L O G I C - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
  if (padMode >=4){
    padMode = 0;
  }
  if (knobMode >= 4){
    knobMode = 0;
  }
  if (colorMode >= 2){
    colorMode = 0;
  }
  
  // M I D I  -  D R A W           - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - // // // //
  
  if(shifterTrigger == 1){
    shifter = shifter + 2;
  }
    if(shifter>=100){
      shifter = 2;
      shifterTrigger = 0;
    }
  
  pushMatrix(); translate(width/2, height/2); rotate(radians(180));// // //
  
  if (CHANvar == 13 || CHANvar>= 4 & CHANvar <= 7){ // // // // performance paddies - - -
    if (padMode == 1){
      noFill();
      if (pad[48] >= 100 || pad[45] >= 100 || pad[20] >= 100){
        rotate(radians(-45));
        shifterTrigger = 1;
        stroke(#F35E1F, 50);
        strokeWeight(pow(scoreGlobalNull/115, 3));
        line(0,0,-100*shifter,2*width);
        line(0,0,+100*shifter,2*width);
        polygon(0,0, 50+shifter,  round(scoreGlobal/80)+1);
        polygon(0,0, 500-50*shifter,  round(scoreGlobal/80)+1);
        stroke(#F35E1F, 50);
        strokeWeight( (pow(scoreGlobalNull/115, 3))/6);
        line(0,0,-100*shifter,2*width);
        line(0,0,+100*shifter,2*width);
        polygon(0,0, 50+shifter,  round(scoreGlobal/80)+1);
        polygon(0,0, 500-50*shifter,  round(scoreGlobal/80)+1);
      }
  
      if (pad[46] >= 100 || pad[51] >= 100 || pad[23] >= 100){
        rotate(radians(45));
        shifterTrigger = 1;
        stroke(#F35E1F, 50);
        strokeWeight(pow(scoreGlobalNull/115, 3));
        line(0,0,-100*shifter,2*width);
        line(0,0,+100*shifter,2*width);
        polygon(0,0, 50+shifter,  round(scoreGlobal/80)+1);
        polygon(0,0, 500-50*shifter,  round(scoreGlobal/80)+1);
        stroke(#F35E1F, 50);
        strokeWeight( (pow(scoreGlobalNull/115, 3))/6);
        line(0,0,-100*shifter,2*width);
        line(0,0,+100*shifter,2*width);
        polygon(0,0, 50+shifter,  round(scoreGlobal/80)+1);
        polygon(0,0, 500-50*shifter,  round(scoreGlobal/80)+1);
      }
      
      if (pad[36] >= 100 || pad[41] >= 100 || pad[24] >= 100){
        rotate(radians(-135));
        shifterTrigger = 1;
        stroke(#15A56B, 50);
        strokeWeight(pow(scoreGlobalNull/115, 3));
        line(0,0,-100*shifter,2*width);
        line(0,0,+100*shifter,2*width);
        polygon(0,0, 50+shifter,  round(scoreGlobal/80)+1);
        polygon(0,0, 500-50*shifter,  round(scoreGlobal/80)+1);
        stroke(#15A56B, 50);
        strokeWeight( (pow(scoreGlobalNull/115, 3))/6);
        line(0,0,-100*shifter,2*width);
        line(0,0,+100*shifter,2*width);
        polygon(0,0, 50+shifter,  round(scoreGlobal/80)+1);
        polygon(0,0, 500-50*shifter,  round(scoreGlobal/80)+1);
      }
      if (pad[42] >= 100 || pad[39] >= 100 || pad[27] >= 100){
        rotate(radians(135));
        shifterTrigger = 1;
        stroke(#15A56B, 50);
        strokeWeight(pow(scoreGlobalNull/115, 3));
        line(0,0,-100*shifter,2*width);
        line(0,0,+100*shifter,2*width);
        polygon(0,0, 50+shifter,  round(scoreGlobal/80)+1);
        polygon(0,0, 500-50*shifter,  round(scoreGlobal/80)+1);
        stroke(#15A56B, 50);
        strokeWeight( (pow(scoreGlobalNull/115, 3))/6);
        line(0,0,-100*shifter,2*width);
        line(0,0,+100*shifter,2*width);
        polygon(0,0, 50+shifter,  round(scoreGlobal/80)+1);
        polygon(0,0, 500-50*shifter,  round(scoreGlobal/80)+1);
      } 
      if (pad[49] >= 100 || pad[21] >= 100){
        rotate(radians(-30));
        shifterTrigger = 1;
        stroke(#15A56B, 50);
        strokeWeight(pow(scoreGlobalNull/115, 3));
        line(0,0,-100*shifter,2*width);
        line(0,0,+100*shifter,2*width);
        polygon(0,0, 50+shifter,  round(scoreGlobal/80)+1);
        polygon(0,0, 500-50*shifter,  round(scoreGlobal/80)+1);
        stroke(#15A56B, 50);
        strokeWeight( (pow(scoreGlobalNull/115, 3))/6);
        line(0,0,-100*shifter,2*width);
        line(0,0,+100*shifter,2*width);
        polygon(0,0, 50+shifter,  round(scoreGlobal/80)+1);
        polygon(0,0, 500-50*shifter,  round(scoreGlobal/80)+1);
      }
      if (pad[44] >= 100){
        rotate(radians(-75));
        shifterTrigger = 1;
        stroke(#15A56B, 50);
        strokeWeight(pow(scoreGlobalNull/115, 3));
        line(0,0,-100*shifter,2*width);
        line(0,0,+100*shifter,2*width);
        polygon(0,0, 50+shifter,  round(scoreGlobal/80)+1);
        polygon(0,0, 500-50*shifter,  round(scoreGlobal/80)+1);
        stroke(#15A56B, 50);
        strokeWeight( (pow(scoreGlobalNull/115, 3))/6);
        line(0,0,-100*shifter,2*width);
        line(0,0,+100*shifter,2*width);
        polygon(0,0, 50+shifter,  round(scoreGlobal/80)+1);
        polygon(0,0, 500-50*shifter,  round(scoreGlobal/80)+1);
      }
      if (pad[50] >= 100 || pad[22] >= 100){
        rotate(radians(15));
        shifterTrigger = 1;
        stroke(#15A56B, 50);
        strokeWeight(pow(scoreGlobalNull/115, 3));
        line(0,0,-100*shifter,2*width);
        line(0,0,+100*shifter,2*width);
        polygon(0,0, 50+shifter,  round(scoreGlobal/80)+1);
        polygon(0,0, 500-50*shifter,  round(scoreGlobal/80)+1);
        stroke(#15A56B, 50);
        strokeWeight( (pow(scoreGlobalNull/115, 3))/6);
        line(0,0,-100*shifter,2*width);
        line(0,0,+100*shifter,2*width);
        polygon(0,0, 50+shifter,  round(scoreGlobal/80)+1);
        polygon(0,0, 500-50*shifter,  round(scoreGlobal/80)+1);
      }
      if (pad[47] >= 100){
        rotate(radians(60));
        shifterTrigger = 1;
        stroke(#15A56B, 50);
        strokeWeight(pow(scoreGlobalNull/115, 3));
        line(0,0,-100*shifter,2*width);
        line(0,0,+100*shifter,2*width);
        polygon(0,0, 50+shifter,  round(scoreGlobal/80)+1);
        polygon(0,0, 500-50*shifter,  round(scoreGlobal/80)+1);
        stroke(#15A56B, 50);
        strokeWeight( (pow(scoreGlobalNull/115, 3))/6);
        line(0,0,-100*shifter,2*width);
        line(0,0,+100*shifter,2*width);
        polygon(0,0, 50+shifter,  round(scoreGlobal/80)+1);
        polygon(0,0, 500-50*shifter,  round(scoreGlobal/80)+1);
      }
      if (pad[37] >= 100 || pad[25] >= 100){
        rotate(radians(-165));
        shifterTrigger = 1;
        stroke(#F35E1F, 50);
        strokeWeight(pow(scoreGlobalNull/115, 3));
        line(0,0,-100*shifter,2*width);
        line(0,0,+100*shifter,2*width);
        polygon(0,0, 50+shifter,  round(scoreGlobal/80)+1);
        polygon(0,0, 500-50*shifter,  round(scoreGlobal/80)+1);
        stroke(#F35E1F, 50);
        strokeWeight( (pow(scoreGlobalNull/115, 3))/6);
        line(0,0,-100*shifter,2*width);
        line(0,0,+100*shifter,2*width);
        polygon(0,0, 50+shifter,  round(scoreGlobal/80)+1);
        polygon(0,0, 500-50*shifter,  round(scoreGlobal/80)+1);
      }
      if (pad[40] >= 100){
        rotate(radians(-105));
        shifterTrigger = 1;
        stroke(#F35E1F, 50);
        strokeWeight(pow(scoreGlobalNull/115, 3));
        line(0,0,-100*shifter,2*width);
        line(0,0,+100*shifter,2*width);
        polygon(0,0, 50+shifter,  round(scoreGlobal/80)+1);
        polygon(0,0, 500-50*shifter,  round(scoreGlobal/80)+1);
        stroke(#F35E1F, 50);
        strokeWeight( (pow(scoreGlobalNull/115, 3))/6);
        line(0,0,-100*shifter,2*width);
        line(0,0,+100*shifter,2*width);
        polygon(0,0, 50+shifter,  round(scoreGlobal/80)+1);
        polygon(0,0, 500-50*shifter,  round(scoreGlobal/80)+1);
      }
      if (pad[38] >= 100 || pad[26] >= 100){
        rotate(radians(165));
        shifterTrigger = 1;
        stroke(#F35E1F, 50);
        strokeWeight(pow(scoreGlobalNull/115, 3));
        line(0,0,-100*shifter,2*width);
        line(0,0,+100*shifter,2*width);
        polygon(0,0, 50+shifter,  round(scoreGlobal/80)+1);
        polygon(0,0, 500-50*shifter,  round(scoreGlobal/80)+1);
        stroke(#F35E1F, 50);
        strokeWeight( (pow(scoreGlobalNull/115, 3))/6);
        line(0,0,-100*shifter,2*width);
        line(0,0,+100*shifter,2*width);
        polygon(0,0, 50+shifter,  round(scoreGlobal/80)+1);
        polygon(0,0, 500-50*shifter,  round(scoreGlobal/80)+1);
      }
      if (pad[43] >= 100){
        rotate(radians(105));
        shifterTrigger = 1;
        stroke(#F35E1F, 50);
        strokeWeight(pow(scoreGlobalNull/115, 3));
        line(0,0,-100*shifter,2*width);
        line(0,0,+100*shifter,2*width);
        polygon(0,0, 50+shifter,  round(scoreGlobal/80)+1);
        polygon(0,0, 500-50*shifter,  round(scoreGlobal/80)+1);
        stroke(#F35E1F, 50);
        strokeWeight( (pow(scoreGlobalNull/115, 3))/6);
        line(0,0,-100*shifter,2*width);
        line(0,0,+100*shifter,2*width);
        polygon(0,0, 50+shifter,  round(scoreGlobal/80)+1);
        polygon(0,0, 500-50*shifter,  round(scoreGlobal/80)+1);
      }
    }
  }
  
  popMatrix(); // // // // // // // // // // // // // // // // // // //
  
  
}

void noteOn(int chan, int pitchON, int velON) {
      CHANvar = chan;
      PITCHvarON = pitchON;
      VELvarON = velON;
    
      if (VELvarON == 0) {
        velON = 0;
      }
      
      PITCH[pitchON] = 0; // initialize array of pitch variables
      if (velON > 0){
        PITCH[pitchON] = 1;
      }
      else if (velON == 0){
        PITCH[pitchON] = 0;
      }
        if (PITCH[pitchON] == 1 && velON > 1){
          VEL[pitchON] = velON;
        }
      
      if (chan == 12 && PITCH[pitchON] == 1){
        pad[pitchON] = 248;
        PITCH[pitchON] = 0;
      }
}

void noteOff(int chan, int pitchOFF, int velOFF) {
      CHANvar = chan;
      PITCHvarOFF = pitchOFF;
      VELvarOFF = velOFF;
      
      if (PITCHvarON == PITCHvarOFF){
        VELvarON = 0;
      }
        if (PITCH[pitchOFF] == 1 && velOFF == 0){
          VEL[pitchOFF] = 0;
        }
}

void controllerChange(int chan, int item, int amt) {
      CHANvar = chan;
      ITEMvar = item;
      AMTvar = amt;
      
      ITEM[item] = 0; // initialize array of item variables
      if (AMTvar > 0){
        ITEM[item] = 1;
      }
      if (AMTvar == 0 && ITEM[item] == 1){
        ITEM[item] = 0;
      }
      
        if (ITEM[item] == 1 && amt > 1){
          AMT[item] = amt;
        }
        if (ITEM[item] == 1 && amt == 0){
          AMT[item] = 0;
        }
      
      if (chan == 0 && item == 28){
        fader1 = amt;
      }
      if (chan == 1 && item == 28){
        fader2 = amt;
      }
      if (chan == 2 && item == 28){
        fader3 = amt;
      }
      if (chan == 3 && item == 28){
        fader4 = amt;
      }
      if (chan == 15 && item == 8){
        crossFader = amt;
      }
}

void mouseClicked(){
  if(mouseX >= 10 && mouseX <= 50      // test
  && mouseY >= 10 && mouseY <= 50){
    fill(255,255);
    rect(10,10,40,40);
    mouseTrigger = 1;
    padMode ++;
    knobMode ++;
  }
  if(mouseX >= width-114 &&  mouseX <= width-114+73      // colorMode
  && mouseY >= height-262 && mouseY <= height-262+60 ){
    colorMode ++;
  }
  if(mouseX >= width-306-160 &&  mouseX <= width-306-160+150      // padMode
  && mouseY >= height-262 && mouseY <= height-262+60 ){
    padMode ++;
  }
  if(mouseX >= width-306 &&  mouseX <= width-306+190      // knobMode
  && mouseY >= height-262 && mouseY <= height-262+60 ){
    knobMode ++;
  }
  //else {
  //  mouseTrigger = 0;
  //  padMode = padMode;
  //  knobMode = knobMode;
  //}
}
