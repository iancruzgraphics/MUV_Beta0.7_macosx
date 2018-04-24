import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import ddf.minim.*; 
import ddf.minim.analysis.*; 
import ddf.minim.ugens.*; 
import themidibus.*; 
import javax.sound.midi.MidiMessage; 
import javax.sound.midi.SysexMessage; 
import javax.sound.midi.ShortMessage; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class MUV_beta_0_7 extends PApplet {

// library import- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -



Minim minim;

Gain       gain;
AudioInput AudioIn;
FFT fft;

// possible library for advanced graphic effects - - - - - - - - - - - - - - - - - - - - - -
// import AULib.*;

 //Import the library  - - - - - - - - - - - - - - - - - - - - - - - - -
 //Import the MidiMessage classes http://java.sun.com/j2se/1.5.0/docs/api/javax/sound/midi/MidiMessage.html



int initializeMIDI = 0;

// logo  - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
PImage logo;
// fonts - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
PFont Roboto12;
PFont Roboto18;
PFont Oswald24;
PFont Oswald36;
PFont Beijing48;
PFont Beijing60;

// sound spectrum  - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

// Variables that define the "zones" of the spectrum
float specLow = 0.04f; // low frequency sound (3% of spectrum)
float specMid = 0.22f; // mid frequency sound (15% of spectrum)
float specHi = 0.40f;  // hi frequency sound (40% of spectrum)
// everything above 35% of audible spectrum is barely audible to human ears.

// Scoring values for each zone
float scoreLow = 0;
float scoreMid = 0;
float scoreHi = 0;

// Previous value, to soften the reduction
float oldScoreLow = scoreLow;
float oldScoreMid = scoreMid;
float oldScoreHi = scoreHi;

// Scoring values, without reduction.
float scoreLowNull = 0;
float scoreMidNull = 0;
float scoreHiNull = 0;

// Softening value
float scoreDecreaseRate = 8;
float scoreGlobalDecreaseRate = 5;

float scoreGlobal;
float scoreGlobalNull;

// Soften the reduction just as with spectrum
float oldScoreGlobal = scoreGlobal;

float lowHeight;
float midHeight;
float hiHeight;
float lowHeightNull;
float midHeightNull;
float hiHeightNull;

// timer and splash screen - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
int trigger = 1; // this will be used for triggering the timer
int MIDItrigger = 1;

float splashOpacity = 255; // opacity of intro splash screen
float MIDIopacity = 255; // opacity of midi menu
float MIDIop = 255;

// State (selected by NumPad)  - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
int state = 0;

// Variables for visualizer 1
float xScroll = width/2;
float xScrollR = width/2;
float rotator = 1;
float shifter = 2;
int shifterTrigger = 0;

// visualizer colors

public void setup()
{  
  frameRate(60);
  
  //size(1280,720);
  
    
  background (0,0,0,255);
    
  // load Minim
  minim = new Minim(this);

  // Minim loads the first available Line In (usually a mic jack)
  AudioIn = minim.getLineIn(Minim.STEREO, 512, 24000);
  // Create the FFT object to analyze the song
  fft = new FFT(AudioIn.bufferSize(), AudioIn.sampleRate());
  
  // MidiBus initialize
  // vi25 keyboard with midi drum pad
  vi25 = new MidiBus(this, "vi25loop", "vi25loop");
  vi25mac = new MidiBus(this, "Out", "In");
  // mc7000 dj controller
  mc7000 = new MidiBus(this, "mc7000loop", "mc7000loop");
  mc7000mac = new MidiBus(this, "DENON DJ MC7000", "DENON DJ MC7000");
  
  // load assets
  logo = loadImage("MUVlogo.png");
  // fonts
  Roboto12 = loadFont("Roboto-Medium-12.vlw");
  Roboto18 = loadFont("Roboto-Medium-18.vlw");
  Oswald24 = loadFont("Oswald-Medium-24.vlw");
  Oswald36 = loadFont("Oswald-Medium-36.vlw");
  Beijing48 = loadFont("Beijing-Redux-Thick-48.vlw");
  Beijing60 = loadFont("Beijing-Redux-Thick-60.vlw");
}

public void draw()
{
  
  // console - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
  println ("// // // // // // // // // // // // // //");
  //println ("leftSpeaker = " + AudioIn.left.level() + "      rightSpeaker = " + AudioIn.left.level() );
  //println ("specLow = " + specLow + "      scoreLow = " + scoreLow + "      lowHeight = " + lowHeight);
  //println ("                scoreLowNull = " + scoreLowNull + "      lowHeightNull = " + lowHeightNull);
  //println ("specMid = " + specMid + "      scoreMid = " + scoreMid + "      midHeight = " + midHeight);
  //println ("                scoreMidNull = " + scoreMidNull + "      midHeightNull = " + midHeightNull);
  //println ("specHi = " + specHi + "      scoreHi = " + scoreHi + "      hiHeight = " + hiHeight);
  //println ("                scoreHiNull = " + scoreHiNull + "      hiHeightNull = " + hiHeightNull);
  //println ("scoreGlobal = " + scoreGlobal);
  println ("scoreGlobalNull = " + scoreGlobalNull + "      frameRate = " + frameRate);
  //println ("(height*(hiHeight)) = " + (height*(hiHeight))+ "      rotator = " + rotator);
  //println ("trigger = " + trigger + "      timer = " + timer + "      splashOpacity = " + splashOpacity + "      state = " + state);
  //println ("AudioIn.left.level() = " + AudioIn.left.level() + "      AudioIn.right.level() = " + AudioIn.right.level());
  println("CHANvar:     "+CHANvar 
      +"   PITCHvarON:  "+PITCHvarON
      +"   PITCHvarOFF: "+PITCHvarOFF 
      +"   VELvarON:    "+VELvarON
      +"   VELvarOFF:   "+VELvarOFF
      +"   ITEMvar:     "+ITEMvar
      +"   AMTvar:      "+AMTvar);
  println("padMode:     "+padMode
      +"   knobMode:    "+knobMode
      +"   mouseX:      "+mouseX
      +"   mouseY:      "+mouseY
      +"   shifter:     "+shifter);
      
  // Forward the audio in, One draw () for each "frame" of the song
  fft.forward(AudioIn.mix);
  
  // Calculations  - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
  // First, save old values
  oldScoreLow = scoreLow;
  oldScoreMid = scoreMid;
  oldScoreHi = scoreHi;
  oldScoreGlobal = scoreGlobal;
  
  // Reinitialize
  scoreLow = 0;
  scoreMid = 0;
  scoreHi = 0;
  scoreLowNull = 0;
  scoreMidNull = 0;
  scoreHiNull = 0;
  scoreGlobal = 100;

  // Calculate the new "scores"
  for(int i = 0; i < fft.specSize()*specLow; i++) {
    scoreLow += fft.getBand(i);
    scoreLowNull += fft.getBand(i);
  }
  for(int i = (int)(fft.specSize()*specLow); i < fft.specSize()*specMid; i++) {
    scoreMid += fft.getBand(i);
    scoreMidNull += fft.getBand(i);
  }
  for(int i = (int)(fft.specSize()*specMid); i < fft.specSize()*specHi; i++) {
    scoreHi += fft.getBand(i);
    scoreHiNull += fft.getBand(i);
  }
  
  // Slow downhill
  if (oldScoreLow > (scoreLow+8)) {
    scoreLow = oldScoreLow - scoreDecreaseRate;
  }
  if (oldScoreMid > (scoreMid+8)) {
    scoreMid = oldScoreMid - scoreDecreaseRate;
  }
  if (oldScoreHi > (scoreHi+8)) {
    scoreHi = oldScoreHi - scoreDecreaseRate;
  }
  
  if (scoreLow < 0) {
    scoreLow = 0;
  }
  else if (scoreLow < 0) {
    scoreLow = 0;
  }

  if (scoreMid < 0) {
    scoreMid = 0;
  }
  if (scoreHi < 0) {
    scoreHi = 0;
  }

  // Volume for all frequencies at this time, with the highest sounds higher.
  // This allows the animation to go faster for the higher pitched sounds, which is more noticeable
  // float scoreGlobal = 0.45*scoreLow + 1*scoreMid + 2*scoreHi;
  scoreGlobal = (scoreLow + .70f*scoreMid + .45f*scoreHi) +50;
  scoreGlobalNull = (scoreLow + .70f*scoreMid + .45f*scoreHi) +50;
  
  if (oldScoreGlobal > (scoreGlobal+5)) {
    scoreGlobal = oldScoreGlobal - scoreGlobalDecreaseRate;
  }
  if (scoreGlobal < 50.5f){
    scoreGlobal = 0;
  }
  if (scoreGlobal > 700){
    scoreGlobal = 700;
  }
  if (scoreGlobalNull < 50.5f){
    scoreGlobalNull = 0;
  }
  if (scoreGlobalNull > 700){
    scoreGlobalNull = 700;
  }

  lowHeight = scoreLow/scoreGlobal*1.1f;
  midHeight = scoreMid/scoreGlobal*1.1f;
  hiHeight = scoreHi/scoreGlobal*1.1f;
  lowHeightNull = scoreLowNull/scoreGlobal*1.1f;
  midHeightNull = scoreMidNull/scoreGlobal*1.1f;
  hiHeightNull = scoreHiNull/scoreGlobal*1.1f;

  if (scoreLow < 1){
  scoreLow = 0;
  }
  if (scoreMid < 1){
  scoreLow = 0;
  }
  if (scoreHi < 1){
  scoreLow = 0;
  }
  if (scoreLowNull < 1){
  scoreLowNull = 0;
  }
  if (scoreMidNull < 1){
  scoreLowNull = 0;
  }
  if (scoreHiNull < 1){
  scoreLowNull = 0;
  }
  
  // trigger logic - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
  if (trigger >= 2){
    trigger = 0;
  }
  if (MIDItrigger >= 2){
    MIDItrigger = 0;
  }
  
  // drawing - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    
  // keyboard logic // // // // // // //
  if (keyPressed == true){
    if (key == '0') {
      state = 0;
    }
    if (key == '1') {
      state = 1;
    }
    if (key == '2') {
      state = 2;
    }
    if (key == '3') {
      state = 3;
    }
    if (key == '4') {
      state = 4;
    }
    if (key == '5') {
      state = 5;
    }
    if (key == '6') {
      state = 6;
    }
    if (key == '7') {
      state = 7;
    }
    if (key == '8') {
      state = 8;
    }
    if (key == '9') {
      state = 9;
    }
    if(key == 'c' || key == 'C' || key == '*'){
     trigger += 1;
     key = '`';
    }
    if(key == 'q' || key == 'Q' || key == '/'){
     MIDItrigger += 1;
     key = '`';
    }    
  }
  
  // debugger // // // // // // // /// /// - - - - - - - - - - -
  debug();
  // visualizers // // // // // // /// /// - - - - - - - - - - -
  vis1();
  vis2();
  vis3();
  vis4();
  vis5();
  vis6();
  vis7();
  vis8();
  vis9();
  // credits // // // // // // // /// /// - - - - - - - - - - -
  credits();
  // MIDI instruments // // // // /// /// - - - - - - - - - - -
  MIDI();
}

public void polygon(float x, float y, float radius, int npoints) {
  float angle = TWO_PI / npoints;
  beginShape();
  for (float a = 0; a < TWO_PI; a += angle) {
    float sx = x + cos(a) * radius;
    float sy = y + sin(a) * radius;
    vertex(sx, sy);
  }
  endShape(CLOSE);
}

public void polygon2(float x, float y, float radius, int npoints) {
  float angle = TWO_PI / npoints;
  beginShape();
  for (float a = 0; a < TWO_PI; a += angle) {
    float sx = x + cos(a+PI/2) * radius;
    float sy = y + sin(a+PI/2) * radius;
    vertex(sx, sy);
  }
  endShape(CLOSE);
}

public void polygon3(float x, float y, float radius, int npoints) {
  float angle = TWO_PI / npoints;
  beginShape();
  for (float a = 0; a < TWO_PI; a += angle) {
    float sx = x + cos(a+PI/4) * radius;
    float sy = y + sin(a+PI/4) * radius;
    vertex(sx, sy);
  }
  endShape(CLOSE);
}

public void polygon4(float x, float y, float radius, int npoints) {
  float angle = TWO_PI / npoints;
  beginShape();
  for (float a = 0; a < TWO_PI; a += angle) {
    float sx = x + cos(a+PI) * radius;
    float sy = y + sin(a+PI) * radius;
    vertex(sx, sy);
  }
  endShape(CLOSE);
}
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


public void MIDI(){
  
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
  scale(.75f);
  translate(5.2f*width/16,34/4); // // // // // // // // // // // // // // // // // // // // // // // // // // // // //
  
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
        stroke(0xffF35E1F, 50);
        strokeWeight(pow(scoreGlobalNull/115, 3));
        line(0,0,-100*shifter,2*width);
        line(0,0,+100*shifter,2*width);
        polygon(0,0, 50+shifter,  round(scoreGlobal/80)+1);
        polygon(0,0, 500-50*shifter,  round(scoreGlobal/80)+1);
        stroke(0xffF35E1F, 50);
        strokeWeight( (pow(scoreGlobalNull/115, 3))/6);
        line(0,0,-100*shifter,2*width);
        line(0,0,+100*shifter,2*width);
        polygon(0,0, 50+shifter,  round(scoreGlobal/80)+1);
        polygon(0,0, 500-50*shifter,  round(scoreGlobal/80)+1);
      }
  
      if (pad[46] >= 100 || pad[51] >= 100 || pad[23] >= 100){
        rotate(radians(45));
        shifterTrigger = 1;
        stroke(0xffF35E1F, 50);
        strokeWeight(pow(scoreGlobalNull/115, 3));
        line(0,0,-100*shifter,2*width);
        line(0,0,+100*shifter,2*width);
        polygon(0,0, 50+shifter,  round(scoreGlobal/80)+1);
        polygon(0,0, 500-50*shifter,  round(scoreGlobal/80)+1);
        stroke(0xffF35E1F, 50);
        strokeWeight( (pow(scoreGlobalNull/115, 3))/6);
        line(0,0,-100*shifter,2*width);
        line(0,0,+100*shifter,2*width);
        polygon(0,0, 50+shifter,  round(scoreGlobal/80)+1);
        polygon(0,0, 500-50*shifter,  round(scoreGlobal/80)+1);
      }
      
      if (pad[36] >= 100 || pad[41] >= 100 || pad[24] >= 100){
        rotate(radians(-135));
        shifterTrigger = 1;
        stroke(0xff15A56B, 50);
        strokeWeight(pow(scoreGlobalNull/115, 3));
        line(0,0,-100*shifter,2*width);
        line(0,0,+100*shifter,2*width);
        polygon(0,0, 50+shifter,  round(scoreGlobal/80)+1);
        polygon(0,0, 500-50*shifter,  round(scoreGlobal/80)+1);
        stroke(0xff15A56B, 50);
        strokeWeight( (pow(scoreGlobalNull/115, 3))/6);
        line(0,0,-100*shifter,2*width);
        line(0,0,+100*shifter,2*width);
        polygon(0,0, 50+shifter,  round(scoreGlobal/80)+1);
        polygon(0,0, 500-50*shifter,  round(scoreGlobal/80)+1);
      }
      if (pad[42] >= 100 || pad[39] >= 100 || pad[27] >= 100){
        rotate(radians(135));
        shifterTrigger = 1;
        stroke(0xff15A56B, 50);
        strokeWeight(pow(scoreGlobalNull/115, 3));
        line(0,0,-100*shifter,2*width);
        line(0,0,+100*shifter,2*width);
        polygon(0,0, 50+shifter,  round(scoreGlobal/80)+1);
        polygon(0,0, 500-50*shifter,  round(scoreGlobal/80)+1);
        stroke(0xff15A56B, 50);
        strokeWeight( (pow(scoreGlobalNull/115, 3))/6);
        line(0,0,-100*shifter,2*width);
        line(0,0,+100*shifter,2*width);
        polygon(0,0, 50+shifter,  round(scoreGlobal/80)+1);
        polygon(0,0, 500-50*shifter,  round(scoreGlobal/80)+1);
      } 
      if (pad[49] >= 100 || pad[21] >= 100){
        rotate(radians(-30));
        shifterTrigger = 1;
        stroke(0xff15A56B, 50);
        strokeWeight(pow(scoreGlobalNull/115, 3));
        line(0,0,-100*shifter,2*width);
        line(0,0,+100*shifter,2*width);
        polygon(0,0, 50+shifter,  round(scoreGlobal/80)+1);
        polygon(0,0, 500-50*shifter,  round(scoreGlobal/80)+1);
        stroke(0xff15A56B, 50);
        strokeWeight( (pow(scoreGlobalNull/115, 3))/6);
        line(0,0,-100*shifter,2*width);
        line(0,0,+100*shifter,2*width);
        polygon(0,0, 50+shifter,  round(scoreGlobal/80)+1);
        polygon(0,0, 500-50*shifter,  round(scoreGlobal/80)+1);
      }
      if (pad[44] >= 100){
        rotate(radians(-75));
        shifterTrigger = 1;
        stroke(0xff15A56B, 50);
        strokeWeight(pow(scoreGlobalNull/115, 3));
        line(0,0,-100*shifter,2*width);
        line(0,0,+100*shifter,2*width);
        polygon(0,0, 50+shifter,  round(scoreGlobal/80)+1);
        polygon(0,0, 500-50*shifter,  round(scoreGlobal/80)+1);
        stroke(0xff15A56B, 50);
        strokeWeight( (pow(scoreGlobalNull/115, 3))/6);
        line(0,0,-100*shifter,2*width);
        line(0,0,+100*shifter,2*width);
        polygon(0,0, 50+shifter,  round(scoreGlobal/80)+1);
        polygon(0,0, 500-50*shifter,  round(scoreGlobal/80)+1);
      }
      if (pad[50] >= 100 || pad[22] >= 100){
        rotate(radians(15));
        shifterTrigger = 1;
        stroke(0xff15A56B, 50);
        strokeWeight(pow(scoreGlobalNull/115, 3));
        line(0,0,-100*shifter,2*width);
        line(0,0,+100*shifter,2*width);
        polygon(0,0, 50+shifter,  round(scoreGlobal/80)+1);
        polygon(0,0, 500-50*shifter,  round(scoreGlobal/80)+1);
        stroke(0xff15A56B, 50);
        strokeWeight( (pow(scoreGlobalNull/115, 3))/6);
        line(0,0,-100*shifter,2*width);
        line(0,0,+100*shifter,2*width);
        polygon(0,0, 50+shifter,  round(scoreGlobal/80)+1);
        polygon(0,0, 500-50*shifter,  round(scoreGlobal/80)+1);
      }
      if (pad[47] >= 100){
        rotate(radians(60));
        shifterTrigger = 1;
        stroke(0xff15A56B, 50);
        strokeWeight(pow(scoreGlobalNull/115, 3));
        line(0,0,-100*shifter,2*width);
        line(0,0,+100*shifter,2*width);
        polygon(0,0, 50+shifter,  round(scoreGlobal/80)+1);
        polygon(0,0, 500-50*shifter,  round(scoreGlobal/80)+1);
        stroke(0xff15A56B, 50);
        strokeWeight( (pow(scoreGlobalNull/115, 3))/6);
        line(0,0,-100*shifter,2*width);
        line(0,0,+100*shifter,2*width);
        polygon(0,0, 50+shifter,  round(scoreGlobal/80)+1);
        polygon(0,0, 500-50*shifter,  round(scoreGlobal/80)+1);
      }
      if (pad[37] >= 100 || pad[25] >= 100){
        rotate(radians(-165));
        shifterTrigger = 1;
        stroke(0xffF35E1F, 50);
        strokeWeight(pow(scoreGlobalNull/115, 3));
        line(0,0,-100*shifter,2*width);
        line(0,0,+100*shifter,2*width);
        polygon(0,0, 50+shifter,  round(scoreGlobal/80)+1);
        polygon(0,0, 500-50*shifter,  round(scoreGlobal/80)+1);
        stroke(0xffF35E1F, 50);
        strokeWeight( (pow(scoreGlobalNull/115, 3))/6);
        line(0,0,-100*shifter,2*width);
        line(0,0,+100*shifter,2*width);
        polygon(0,0, 50+shifter,  round(scoreGlobal/80)+1);
        polygon(0,0, 500-50*shifter,  round(scoreGlobal/80)+1);
      }
      if (pad[40] >= 100){
        rotate(radians(-105));
        shifterTrigger = 1;
        stroke(0xffF35E1F, 50);
        strokeWeight(pow(scoreGlobalNull/115, 3));
        line(0,0,-100*shifter,2*width);
        line(0,0,+100*shifter,2*width);
        polygon(0,0, 50+shifter,  round(scoreGlobal/80)+1);
        polygon(0,0, 500-50*shifter,  round(scoreGlobal/80)+1);
        stroke(0xffF35E1F, 50);
        strokeWeight( (pow(scoreGlobalNull/115, 3))/6);
        line(0,0,-100*shifter,2*width);
        line(0,0,+100*shifter,2*width);
        polygon(0,0, 50+shifter,  round(scoreGlobal/80)+1);
        polygon(0,0, 500-50*shifter,  round(scoreGlobal/80)+1);
      }
      if (pad[38] >= 100 || pad[26] >= 100){
        rotate(radians(165));
        shifterTrigger = 1;
        stroke(0xffF35E1F, 50);
        strokeWeight(pow(scoreGlobalNull/115, 3));
        line(0,0,-100*shifter,2*width);
        line(0,0,+100*shifter,2*width);
        polygon(0,0, 50+shifter,  round(scoreGlobal/80)+1);
        polygon(0,0, 500-50*shifter,  round(scoreGlobal/80)+1);
        stroke(0xffF35E1F, 50);
        strokeWeight( (pow(scoreGlobalNull/115, 3))/6);
        line(0,0,-100*shifter,2*width);
        line(0,0,+100*shifter,2*width);
        polygon(0,0, 50+shifter,  round(scoreGlobal/80)+1);
        polygon(0,0, 500-50*shifter,  round(scoreGlobal/80)+1);
      }
      if (pad[43] >= 100){
        rotate(radians(105));
        shifterTrigger = 1;
        stroke(0xffF35E1F, 50);
        strokeWeight(pow(scoreGlobalNull/115, 3));
        line(0,0,-100*shifter,2*width);
        line(0,0,+100*shifter,2*width);
        polygon(0,0, 50+shifter,  round(scoreGlobal/80)+1);
        polygon(0,0, 500-50*shifter,  round(scoreGlobal/80)+1);
        stroke(0xffF35E1F, 50);
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

public void noteOn(int chan, int pitchON, int velON) {
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

public void noteOff(int chan, int pitchOFF, int velOFF) {
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

public void controllerChange(int chan, int item, int amt) {
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

public void mouseClicked(){
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
public void credits(){
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
public void debug(){
  
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
    fill(0xff1D639D,150); // bass
    rect(width/40, height-(height/40), width*.25f, -(height*(lowHeight))  );
    fill(0xff15A56B,150); // mid
    rect(12*width/40, height-(height/40), width*.25f, -(height*(midHeight))  );
    fill(0xffF35E1F,150); // hi
    rect(23*width/40, height-(height/40), width*.25f, -(height*(hiHeight))  );
    fill(0xff2D2BAA,150); // global
    rect(18*width/20, height-(height/40), 3*width/40, -(height*(scoreGlobal/775))  );
    
    // "null", direct-responsive bars
    fill(0xff1D639D,255); // bass
    rect(width/40, height-(height/40), width*.25f, -(height*(lowHeightNull))  );
    fill(0xff15A56B,255); // mid
    rect(12*width/40, height-(height/40), width*.25f, -(height*(midHeightNull))  );
    fill(0xffF35E1F,255); // hi
    rect(23*width/40, height-(height/40), width*.25f, -(height*(hiHeightNull))  );
    fill(0xff2D2BAA,255); // global
    rect(18*width/20, height-(height/40), 3*width/40, -(height*(scoreGlobalNull/775))  );
    
    // left and right speaker input debug
    fill(255,255,255,255);
    rect( width/40, height/40+30, AudioIn.left.level()*width, height/40 );
    rect( width/40, 2.5f*height/40+30, AudioIn.right.level()*width, height/40 );
        
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
    text( "Right Speaker", width/40+5, 2.5f*height/40+50 );
  }
  
}

public void keyPressed() // live monitoring of audio - - - - - - - - - - - - - - - - - - - - - - - - - -
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
public void vis1(){
  
  if (state == 1){
    
    // background fun
    strokeWeight (0);
    stroke (0,0,0,0);
    fill (45*lowHeight/2, 43*midHeight/2, 170*hiHeight/2, 255);
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
      fill (scoreLow/3.5f, scoreHi/3.5f, scoreMid/3.5f, 30);
      strokeWeight(pow(scoreGlobal/115, 4));
        stroke(0xff042B4C,35); // bass
        line(-width, 0+0.85f*(height*(lowHeight)), width, 0+0.85f*(height*(lowHeight)));
        line(-width, 0-0.85f*(height*(lowHeight)), width, 0-0.85f*(height*(lowHeight)));
        stroke(0xff005030,35); // mid
        line(-width, 0+0.85f*(height*(midHeight)), width, 0+0.85f*(height*(midHeight)));
        line(-width, 0-0.85f*(height*(midHeight)), width, 0-0.85f*(height*(midHeight)));
        stroke(0xff693D00,35); // hi
        line(-width, 0+0.85f*(height*(hiHeight)), width, 0+0.85f*(height*(hiHeight)));
        line(-width, 0-0.85f*(height*(hiHeight)), width, 0-0.85f*(height*(hiHeight)));
      strokeWeight(pow(scoreGlobalNull/230, 4));
        stroke(0xff1D639D,155); // bassNull
        line(-width, 0+0.85f*(height*(lowHeightNull)), width, 0+0.85f*(height*(lowHeightNull)));
        line(-width, 0-0.85f*(height*(lowHeightNull)), width, 0-0.85f*(height*(lowHeightNull)));
        stroke(0xff15A56B,155);  // midNull
        line(-width, 0+0.85f*(height*(midHeightNull)), width, 0+0.85f*(height*(midHeightNull)));
        line(-width, 0-0.85f*(height*(midHeightNull)), width, 0-0.85f*(height*(midHeightNull)));
        stroke(0xffF35E1F,155);  // hiNull
        line(-width, 0+0.85f*(height*(hiHeightNull)), width, 0+0.85f*(height*(hiHeightNull)));
        line(-width, 0-0.85f*(height*(hiHeightNull)), width, 0-0.85f*(height*(hiHeightNull)));
        
        stroke(0xff042B4C,85); // bass
        polygon(0, 0+0.85f*(height*(lowHeight)), 70*(scoreGlobal/450), round(scoreGlobal/80)+1);
        stroke(0xff005030,85); // mid
        polygon(0, 0+0.85f*(height*(midHeight)), 55*(scoreGlobal/450), round(scoreGlobal/80)+1);
        stroke(0xff693D00,85); // hi
        polygon(0, 0+0.85f*(height*(hiHeight)), 40*(scoreGlobal/450), round(scoreGlobal/80)+1);
        stroke(0xff1D639D,155); // bassNull
        polygon(0, 0+0.85f*(height*(lowHeightNull)), 50*(scoreGlobal/500), round(scoreGlobal/80)+1);
        stroke(0xff15A56B,155); // midNull
        polygon(0, 0+0.85f*(height*(midHeightNull)), 35*(scoreGlobal/500), round(scoreGlobal/80)+1);
        stroke(0xffF35E1F,155); // hiNull
        polygon(0, 0+0.85f*(height*(hiHeightNull)), 20*(scoreGlobal/500), round(scoreGlobal/80)+1);
      rotate(radians(180));
        stroke(0xff042B4C,85); // bass
        polygon(0, 0+0.85f*(height*(lowHeight)), 70*(scoreGlobal/450), round(scoreGlobal/80)+1);
        stroke(0xff005030,85); // mid
        polygon(0, 0+0.85f*(height*(midHeight)), 55*(scoreGlobal/450), round(scoreGlobal/80)+1);
        stroke(0xff693D00,85); // hi
        polygon(0, 0+0.85f*(height*(hiHeight)), 40*(scoreGlobal/450), round(scoreGlobal/80)+1);
        stroke(0xff1D639D,155); // bassNull
        polygon(0, 0+0.85f*(height*(lowHeightNull)), 50*(scoreGlobal/500), round(scoreGlobal/80)+1);
        stroke(0xff15A56B,155); // midNull
        polygon(0, 0+0.85f*(height*(midHeightNull)), 35*(scoreGlobal/500), round(scoreGlobal/80)+1);
        stroke(0xffF35E1F,155); // hiNull
        polygon(0, 0+0.85f*(height*(hiHeightNull)), 20*(scoreGlobal/500), round(scoreGlobal/80)+1);
      noFill();
      rotate(radians(180));
        stroke(0xff042B4C,50); // bass
        polygon(width/2, 0+0.85f*(height*(lowHeight)), 70*(scoreGlobal/45), round(scoreGlobal/80)+1);
        polygon(width/2, 0-0.85f*(height*(lowHeight)), 70*(scoreGlobal/45), round(scoreGlobal/80)+1);
        stroke(0xff005030,50); // mid
        polygon(width/2, 0+0.85f*(height*(midHeight)), 55*(scoreGlobal/45), round(scoreGlobal/80)+1);
        polygon(width/2, 0-0.85f*(height*(midHeight)), 55*(scoreGlobal/45), round(scoreGlobal/80)+1);
        stroke(0xff693D00,50); // hi
        polygon(width/2, 0+0.85f*(height*(hiHeight)), 40*(scoreGlobal/45), round(scoreGlobal/80)+1);
        polygon(width/2, 0-0.85f*(height*(hiHeight)), 40*(scoreGlobal/45), round(scoreGlobal/80)+1);
        stroke(0xff1D639D,100); // bassNull
        polygon(width/2, 0+0.85f*(height*(lowHeightNull)), 50*(scoreGlobal/50), round(scoreGlobal/80)+1);
        polygon(width/2, 0-0.85f*(height*(lowHeightNull)), 50*(scoreGlobal/50), round(scoreGlobal/80)+1);
        stroke(0xff15A56B,100); // midNull
        polygon(width/2, 0+0.85f*(height*(midHeightNull)), 35*(scoreGlobal/50), round(scoreGlobal/80)+1);
        polygon(width/2, 0-0.85f*(height*(midHeightNull)), 35*(scoreGlobal/50), round(scoreGlobal/80)+1);
        stroke(0xffF35E1F,100); // hiNull
        polygon(width/2, 0+0.85f*(height*(hiHeightNull)), 20*(scoreGlobal/50), round(scoreGlobal/80)+1);
        polygon(width/2, 0-0.85f*(height*(hiHeightNull)), 20*(scoreGlobal/50), round(scoreGlobal/80)+1);
      rotate(radians(180));
        stroke(0xff042B4C,50); // bass
        polygon(width/2, 0+0.85f*(height*(lowHeight)), 70*(scoreGlobal/45), round(scoreGlobal/80)+1);
        polygon(width/2, 0-0.85f*(height*(lowHeight)), 70*(scoreGlobal/45), round(scoreGlobal/80)+1);
        stroke(0xff005030,50); // mid
        polygon(width/2, 0+0.85f*(height*(midHeight)), 55*(scoreGlobal/45), round(scoreGlobal/80)+1);
        polygon(width/2, 0-0.85f*(height*(midHeight)), 55*(scoreGlobal/45), round(scoreGlobal/80)+1);
        stroke(0xff693D00,50); // hi
        polygon(width/2, 0+0.85f*(height*(hiHeight)), 40*(scoreGlobal/45), round(scoreGlobal/80)+1);
        polygon(width/2, 0-0.85f*(height*(hiHeight)), 40*(scoreGlobal/45), round(scoreGlobal/80)+1);
        stroke(0xff1D639D,100); // bassNull
        polygon(width/2, 0+0.85f*(height*(lowHeightNull)), 50*(scoreGlobal/50), round(scoreGlobal/80)+1);
        polygon(width/2, 0-0.85f*(height*(lowHeightNull)), 50*(scoreGlobal/50), round(scoreGlobal/80)+1);
        stroke(0xff15A56B,100); // midNull
        polygon(width/2, 0+0.85f*(height*(midHeightNull)), 35*(scoreGlobal/50), round(scoreGlobal/80)+1);
        polygon(width/2, 0-0.85f*(height*(midHeightNull)), 35*(scoreGlobal/50), round(scoreGlobal/80)+1);
        stroke(0xffF35E1F,100); // hiNull
        polygon(width/2, 0+0.85f*(height*(hiHeightNull)), 20*(scoreGlobal/50), round(scoreGlobal/80)+1);
        polygon(width/2, 0-0.85f*(height*(hiHeightNull)), 20*(scoreGlobal/50), round(scoreGlobal/80)+1);
      popMatrix();
  }
}
public void vis2(){

  if (state == 2){
    
    // background fun
    strokeWeight (0);
    stroke (0,0,0,0);
    fill (45*lowHeight/2, 43*midHeight/2, 170*hiHeight/2, 255);
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
    curve(0.75f*width, 0.75f*height, xScroll, 0-height/8, xScroll, height+height/8, 0.75f*width, 0.25f*height);
    curve(0.25f*width, 0.75f*height, xScrollR, 0-height/8, xScrollR, height+height/8, 0.25f*width, 0.25f*height);
    
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
        
          strokeWeight( (pow((scoreGlobalNull/100),1.5f)) *5);
        rotate(radians(15));
          stroke(0xff042B4C,150-scoreGlobal/8); // bass
          polygon3(0, 0+0.85f*(height*(lowHeight)), 90*(scoreGlobal/450), 4);
          polygon3(0, 0-0.85f*(height*(lowHeight)), 90*(scoreGlobal/450), 4);
          stroke(0xff005030,150-scoreGlobal/8); // mid
          polygon3(0, 0+0.85f*(height*(midHeight)), 75*(scoreGlobal/450), 4);
          polygon3(0, 0-0.85f*(height*(midHeight)), 75*(scoreGlobal/450), 4);
          stroke(0xff693D00,150-scoreGlobal/8); // hi
          polygon3(0, 0+0.85f*(height*(hiHeight)), 60*(scoreGlobal/450), 4);
          polygon3(0, 0-0.85f*(height*(hiHeight)), 60*(scoreGlobal/450), 4);
        rotate(radians(30));
          stroke(0xff042B4C,150-scoreGlobal/8); // bass
          polygon3(0, 0+0.85f*(height*(lowHeight)), 90*(scoreGlobal/450), 4);
          polygon3(0, 0-0.85f*(height*(lowHeight)), 90*(scoreGlobal/450), 4);
          stroke(0xff005030,150-scoreGlobal/8); // mid
          polygon3(0, 0+0.85f*(height*(midHeight)), 75*(scoreGlobal/450), 4);
          polygon3(0, 0-0.85f*(height*(midHeight)), 75*(scoreGlobal/450), 4);
          stroke(0xff693D00,150-scoreGlobal/8); // hi
          polygon3(0, 0+0.85f*(height*(hiHeight)), 60*(scoreGlobal/450), 4);
          polygon3(0, 0-0.85f*(height*(hiHeight)), 60*(scoreGlobal/450), 4);
        rotate(radians(30));
          stroke(0xff042B4C,150-scoreGlobal/8); // bass
          polygon3(0, 0+0.85f*(height*(lowHeight)), 90*(scoreGlobal/450), 4);
          polygon3(0, 0-0.85f*(height*(lowHeight)), 90*(scoreGlobal/450), 4);
          stroke(0xff005030,150-scoreGlobal/8); // mid
          polygon3(0, 0+0.85f*(height*(midHeight)), 75*(scoreGlobal/450), 4);
          polygon3(0, 0-0.85f*(height*(midHeight)), 75*(scoreGlobal/450), 4);
          stroke(0xff693D00,150-scoreGlobal/8); // hi
          polygon3(0, 0+0.85f*(height*(hiHeight)), 60*(scoreGlobal/450), 4);
          polygon3(0, 0-0.85f*(height*(hiHeight)), 60*(scoreGlobal/450), 4);
        rotate(radians(30));
          stroke(0xff042B4C,150-scoreGlobal/8); // bass
          polygon3(0, 0+0.85f*(height*(lowHeight)), 90*(scoreGlobal/450), 4);
          polygon3(0, 0-0.85f*(height*(lowHeight)), 90*(scoreGlobal/450), 4);
          stroke(0xff005030,150-scoreGlobal/8); // mid
          polygon3(0, 0+0.85f*(height*(midHeight)), 75*(scoreGlobal/450), 4);
          polygon3(0, 0-0.85f*(height*(midHeight)), 75*(scoreGlobal/450), 4);
          stroke(0xff693D00,150-scoreGlobal/8); // hi
          polygon3(0, 0+0.85f*(height*(hiHeight)), 60*(scoreGlobal/450), 4);
          polygon3(0, 0-0.85f*(height*(hiHeight)), 60*(scoreGlobal/450), 4);
        rotate(radians(30));
          stroke(0xff042B4C,150-scoreGlobal/8); // bass
          polygon3(0, 0+0.85f*(height*(lowHeight)), 90*(scoreGlobal/450), 4);
          polygon3(0, 0-0.85f*(height*(lowHeight)), 90*(scoreGlobal/450), 4);
          stroke(0xff005030,150-scoreGlobal/8); // mid
          polygon3(0, 0+0.85f*(height*(midHeight)), 75*(scoreGlobal/450), 4);
          polygon3(0, 0-0.85f*(height*(midHeight)), 75*(scoreGlobal/450), 4);
          stroke(0xff693D00,150-scoreGlobal/8); // hi
          polygon3(0, 0+0.85f*(height*(hiHeight)), 60*(scoreGlobal/450), 4);
          polygon3(0, 0-0.85f*(height*(hiHeight)), 60*(scoreGlobal/450), 4);
        rotate(radians(30));
          stroke(0xff042B4C,150-scoreGlobal/8); // bass
          polygon3(0, 0+0.85f*(height*(lowHeight)), 90*(scoreGlobal/450), 4);
          polygon3(0, 0-0.85f*(height*(lowHeight)), 90*(scoreGlobal/450), 4);
          stroke(0xff005030,150-scoreGlobal/8); // mid
          polygon3(0, 0+0.85f*(height*(midHeight)), 75*(scoreGlobal/450), 4);
          polygon3(0, 0-0.85f*(height*(midHeight)), 75*(scoreGlobal/450), 4);
          stroke(0xff693D00,150-scoreGlobal/8); // hi
          polygon3(0, 0+0.85f*(height*(hiHeight)), 60*(scoreGlobal/450), 4);
          polygon3(0, 0-0.85f*(height*(hiHeight)), 60*(scoreGlobal/450), 4);
        
          strokeWeight(1+round(scoreGlobalNull/60));
          stroke(0xff1D639D,255); // bassNull
          polygon(0, 0+0.85f*(height*(lowHeightNull)), (5/( pow((lowHeightNull+midHeightNull+hiHeightNull),2))+width/100), 2);
          polygon(0, 0-0.85f*(height*(lowHeightNull)), (5/( pow((lowHeightNull+midHeightNull+hiHeightNull),2))+width/100), 2);
          stroke(0xff15A56B,255); // midNull
          polygon(0, 0+0.85f*(height*(midHeightNull)), (5/( pow((lowHeightNull+midHeightNull+hiHeightNull),2))+width/100), 2);
          polygon(0, 0-0.85f*(height*(midHeightNull)), (5/( pow((lowHeightNull+midHeightNull+hiHeightNull),2))+width/100), 2);
          stroke(0xffF35E1F,255); // hiNull
          polygon(0, 0+0.85f*(height*(hiHeightNull)), (5/( pow((lowHeightNull+midHeightNull+hiHeightNull),2))+width/100), 2);
          polygon(0, 0-0.85f*(height*(hiHeightNull)), (5/( pow((lowHeightNull+midHeightNull+hiHeightNull),2))+width/100), 2);
        rotate(radians(30));
          stroke(0xff1D639D,255); // bassNull
          polygon(0, 0+0.85f*(height*(lowHeightNull)), (5/( pow((lowHeightNull+midHeightNull+hiHeightNull),2))+width/100), 2);
          polygon(0, 0-0.85f*(height*(lowHeightNull)), (5/( pow((lowHeightNull+midHeightNull+hiHeightNull),2))+width/100), 2);
          stroke(0xff15A56B,255); // midNull
          polygon(0, 0+0.85f*(height*(midHeightNull)), (5/( pow((lowHeightNull+midHeightNull+hiHeightNull),2))+width/100), 2);
          polygon(0, 0-0.85f*(height*(midHeightNull)), (5/( pow((lowHeightNull+midHeightNull+hiHeightNull),2))+width/100), 2);
          stroke(0xffF35E1F,255); // hiNull
          polygon(0, 0+0.85f*(height*(hiHeightNull)), (5/( pow((lowHeightNull+midHeightNull+hiHeightNull),2))+width/100), 2);
          polygon(0, 0-0.85f*(height*(hiHeightNull)), (5/( pow((lowHeightNull+midHeightNull+hiHeightNull),2))+width/100), 2);
        rotate(radians(30));
          stroke(0xff1D639D,255); // bassNull
          polygon(0, 0+0.85f*(height*(lowHeightNull)), (5/( pow((lowHeightNull+midHeightNull+hiHeightNull),2))+width/100), 2);
          polygon(0, 0-0.85f*(height*(lowHeightNull)), (5/( pow((lowHeightNull+midHeightNull+hiHeightNull),2))+width/100), 2);
          stroke(0xff15A56B,255); // midNull
          polygon(0, 0+0.85f*(height*(midHeightNull)), (5/( pow((lowHeightNull+midHeightNull+hiHeightNull),2))+width/100), 2);
          polygon(0, 0-0.85f*(height*(midHeightNull)), (5/( pow((lowHeightNull+midHeightNull+hiHeightNull),2))+width/100), 2);
          stroke(0xffF35E1F,255); // hiNull
          polygon(0, 0+0.85f*(height*(hiHeightNull)), (5/( pow((lowHeightNull+midHeightNull+hiHeightNull),2))+width/100), 2);
          polygon(0, 0-0.85f*(height*(hiHeightNull)), (5/( pow((lowHeightNull+midHeightNull+hiHeightNull),2))+width/100), 2);
        rotate(radians(30));
          stroke(0xff1D639D,255); // bassNull
          polygon(0, 0+0.85f*(height*(lowHeightNull)), (5/( pow((lowHeightNull+midHeightNull+hiHeightNull),2))+width/100), 2);
          polygon(0, 0-0.85f*(height*(lowHeightNull)), (5/( pow((lowHeightNull+midHeightNull+hiHeightNull),2))+width/100), 2);
          stroke(0xff15A56B,255); // midNull
          polygon(0, 0+0.85f*(height*(midHeightNull)), (5/( pow((lowHeightNull+midHeightNull+hiHeightNull),2))+width/100), 2);
          polygon(0, 0-0.85f*(height*(midHeightNull)), (5/( pow((lowHeightNull+midHeightNull+hiHeightNull),2))+width/100), 2);
          stroke(0xffF35E1F,255); // hiNull
          polygon(0, 0+0.85f*(height*(hiHeightNull)), (5/( pow((lowHeightNull+midHeightNull+hiHeightNull),2))+width/100), 2);
          polygon(0, 0-0.85f*(height*(hiHeightNull)), (5/( pow((lowHeightNull+midHeightNull+hiHeightNull),2))+width/100), 2);
        rotate(radians(30));
          stroke(0xff1D639D,255); // bassNull
          polygon(0, 0+0.85f*(height*(lowHeightNull)), (5/( pow((lowHeightNull+midHeightNull+hiHeightNull),2))+width/100), 2);
          polygon(0, 0-0.85f*(height*(lowHeightNull)), (5/( pow((lowHeightNull+midHeightNull+hiHeightNull),2))+width/100), 2);
          stroke(0xff15A56B,255); // midNull
          polygon(0, 0+0.85f*(height*(midHeightNull)), (5/( pow((lowHeightNull+midHeightNull+hiHeightNull),2))+width/100), 2);
          polygon(0, 0-0.85f*(height*(midHeightNull)), (5/( pow((lowHeightNull+midHeightNull+hiHeightNull),2))+width/100), 2);
          stroke(0xffF35E1F,255); // hiNull
          polygon(0, 0+0.85f*(height*(hiHeightNull)), (5/( pow((lowHeightNull+midHeightNull+hiHeightNull),2))+width/100), 2);
          polygon(0, 0-0.85f*(height*(hiHeightNull)), (5/( pow((lowHeightNull+midHeightNull+hiHeightNull),2))+width/100), 2);
        rotate(radians(30));
          stroke(0xff1D639D,255); // bassNull
          polygon(0, 0+0.85f*(height*(lowHeightNull)), (5/( pow((lowHeightNull+midHeightNull+hiHeightNull),2))+width/100), 2);
          polygon(0, 0-0.85f*(height*(lowHeightNull)), (5/( pow((lowHeightNull+midHeightNull+hiHeightNull),2))+width/100), 2);
          stroke(0xff15A56B,255); // midNull
          polygon(0, 0+0.85f*(height*(midHeightNull)), (5/( pow((lowHeightNull+midHeightNull+hiHeightNull),2))+width/100), 2);
          polygon(0, 0-0.85f*(height*(midHeightNull)), (5/( pow((lowHeightNull+midHeightNull+hiHeightNull),2))+width/100), 2);
          stroke(0xffF35E1F,255); // hiNull
          polygon(0, 0+0.85f*(height*(hiHeightNull)), (5/( pow((lowHeightNull+midHeightNull+hiHeightNull),2))+width/100), 2);
          polygon(0, 0-0.85f*(height*(hiHeightNull)), (5/( pow((lowHeightNull+midHeightNull+hiHeightNull),2))+width/100), 2);
          
          
      popMatrix();
      
    //// background fun
    //strokeWeight (0);
    //stroke (0,0,0,0);
    ////background (scoreLow/2, scoreHi/2, scoreMid/2, 1);
    //fill (scoreLow/3.5, scoreHi/3.5, scoreMid/3.5, 200);
    //rect (0,0,width,height);
    
  }

}
int xspacing = 20;   // How far apart should each horizontal location be spaced
int w;              // Width of entire wave

// Start angle at 0
float theta = 0.0f;
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

public void vis3(){
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
    dxLow = (4*PI / period) * xspacing * pow(1.6f*lowHeight,4) ;
    dxMid = (4*PI / period) * xspacing * pow(1.6f*midHeight,4) ;
    dxHi = (4*PI / period) * xspacing * pow(1.6f*hiHeight,4) ;
    dxLowNull = (4*PI / period) * xspacing * pow(1.6f*lowHeightNull,4) ;
    dxMidNull = (4*PI / period) * xspacing * pow(1.6f*midHeightNull,4) ;
    dxHiNull = (4*PI / period) * xspacing * pow(1.6f*hiHeightNull,4) ;
    
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

public void calcWave(){
  // Increment theta (try different values for 'angular velocity' here)
  theta += 0.003f*scoreGlobalNull;
  amplitudeLow = 600*lowHeight*1.2f;
  amplitudeMid = 360*midHeight*1.4f;
  amplitudeHi = 240*hiHeight*1.8f;
  amplitudeLowNull = 250*lowHeightNull*1.2f;
  amplitudeMidNull = 200*midHeightNull*1.4f;
  amplitudeHiNull = 100*hiHeightNull*1.8f;

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

public void renderWave(){
  
  pushMatrix();
    noFill();
    translate(width/2, height/2);
      strokeWeight(pow(scoreGlobalNull/50, 2.5f));
      stroke(0xff042B4C,100-scoreGlobal/8);
      for (int x = 0; x < yvaluesLow.length; x++) { // lowNull
        polygon2(x*xspacing*6, yvaluesLowNull[x]*4, pow(14*lowHeightNull,2), 2);
      }
      stroke(0xff005030,100-scoreGlobal/8);
      for (int x = 0; x < yvaluesMidNull.length; x++) { // midNull
        polygon2(x*xspacing*6, yvaluesMidNull[x]*4, pow(14*midHeightNull,2), 2);
      }
      stroke(0xff693D00,100-scoreGlobal/8);
      for (int x = 0; x < yvaluesHiNull.length; x++) { // hiNull
        polygon2(x*xspacing*6, yvaluesHiNull[x]*4, pow(14*hiHeightNull,2), 2);
      }
    rotate(radians(180));
      strokeWeight(pow(scoreGlobalNull/50, 2.5f));
      stroke(0xff042B4C,100-scoreGlobal/8);
      for (int x = 0; x < yvaluesLow.length; x++) { // lowNull
        polygon2(x*xspacing*6, -yvaluesLowNull[x]*4, pow(14*lowHeightNull,2), 2);
      }
      stroke(0xff005030,100-scoreGlobal/8);
      for (int x = 0; x < yvaluesMidNull.length; x++) { // midNull
        polygon2(x*xspacing*6, -yvaluesMidNull[x]*4, pow(14*midHeightNull,2), 2);
      }
      stroke(0xff693D00,100-scoreGlobal/8);
      for (int x = 0; x < yvaluesHiNull.length; x++) { // hiNull
        polygon2(x*xspacing*6, -yvaluesHiNull[x]*4, pow(14*hiHeightNull,2), 2);
      }
    rotate(radians(180));
      strokeWeight(pow(scoreGlobalNull/50, 1.1f));
      stroke(0xff1D639D,255);
      for (int x = 0; x < yvaluesLow.length; x++) { // low
        polygon2(x*xspacing+10, yvaluesLow[x], pow(20*lowHeight,2), 2);
      }
      stroke(0xff15A56B,255);
      for (int x = 0; x < yvaluesMid.length; x++) { // mid
        polygon2(x*xspacing+10, yvaluesMid[x], pow(20*midHeight,2), 2);
      }
      stroke(0xffF35E1F,255);
      for (int x = 0; x < yvaluesHi.length; x++) { // hi
        polygon2(x*xspacing+10, yvaluesHi[x], pow(24*hiHeight,2), 2);
      }
    rotate(radians(180));
      strokeWeight(pow(scoreGlobalNull/50, 1.1f));
      stroke(0xff1D639D,255);
      for (int x = 0; x < yvaluesLow.length; x++) { // low
        polygon2(x*xspacing+10, -yvaluesLow[x], pow(20*lowHeight,2), 2);
      }
      stroke(0xff15A56B,255);
      for (int x = 0; x < yvaluesMid.length; x++) { // mid
        polygon2(x*xspacing+10, -yvaluesMid[x], pow(20*midHeight,2), 2);
      }
      stroke(0xffF35E1F,255);
      for (int x = 0; x < yvaluesHi.length; x++) { // hi
        polygon2(x*xspacing+10, -yvaluesHi[x], pow(24*hiHeight,2), 2);
      }
  popMatrix();

  }
public void vis4(){
  
  if (state == 4){
    
    // background fun
    strokeWeight (0);
    stroke (0,0,0,0);
    fill (45*lowHeight/2, 43*midHeight/2, 170*hiHeight/2, 150);
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
      fill (scoreLow/3.5f, scoreHi/3.5f, scoreMid/3.5f, 30);
      strokeWeight(pow(scoreGlobal/115, 4));
        stroke(0xff042B4C,35); // bass
        line(-width, 0+0.85f*(height*(lowHeight)), width, 0+0.85f*(height*(lowHeight)));
        line(-width, 0-0.85f*(height*(lowHeight)), width, 0-0.85f*(height*(lowHeight)));
        stroke(0xff005030,35); // mid
        line(-width, 0+0.85f*(height*(midHeight)), width, 0+0.85f*(height*(midHeight)));
        line(-width, 0-0.85f*(height*(midHeight)), width, 0-0.85f*(height*(midHeight)));
        stroke(0xff693D00,35); // hi
        line(-width, 0+0.85f*(height*(hiHeight)), width, 0+0.85f*(height*(hiHeight)));
        line(-width, 0-0.85f*(height*(hiHeight)), width, 0-0.85f*(height*(hiHeight)));
      strokeWeight(pow(scoreGlobalNull/230, 4));
        stroke(0xff1D639D,155); // bassNull
        line(-width, 0+0.85f*(height*(lowHeightNull)), width, 0+0.85f*(height*(lowHeightNull)));
        line(-width, 0-0.85f*(height*(lowHeightNull)), width, 0-0.85f*(height*(lowHeightNull)));
        stroke(0xff15A56B,155);  // midNull
        line(-width, 0+0.85f*(height*(midHeightNull)), width, 0+0.85f*(height*(midHeightNull)));
        line(-width, 0-0.85f*(height*(midHeightNull)), width, 0-0.85f*(height*(midHeightNull)));
        stroke(0xffF35E1F,155);  // hiNull
        line(-width, 0+0.85f*(height*(hiHeightNull)), width, 0+0.85f*(height*(hiHeightNull)));
        line(-width, 0-0.85f*(height*(hiHeightNull)), width, 0-0.85f*(height*(hiHeightNull)));
        
        stroke(0xff042B4C,85); // bass
        polygon(0, 0+0.85f*(height*(lowHeight)), 70*(scoreGlobal/450), round(scoreGlobal/80)+1);
        stroke(0xff005030,85); // mid
        polygon(0, 0+0.85f*(height*(midHeight)), 55*(scoreGlobal/450), round(scoreGlobal/80)+1);
        stroke(0xff693D00,85); // hi
        polygon(0, 0+0.85f*(height*(hiHeight)), 40*(scoreGlobal/450), round(scoreGlobal/80)+1);
        stroke(0xff1D639D,155); // bassNull
        polygon(0, 0+0.85f*(height*(lowHeightNull)), 50*(scoreGlobal/500), round(scoreGlobal/80)+1);
        stroke(0xff15A56B,155); // midNull
        polygon(0, 0+0.85f*(height*(midHeightNull)), 35*(scoreGlobal/500), round(scoreGlobal/80)+1);
        stroke(0xffF35E1F,155); // hiNull
        polygon(0, 0+0.85f*(height*(hiHeightNull)), 20*(scoreGlobal/500), round(scoreGlobal/80)+1);
      rotate(radians(180));
        stroke(0xff042B4C,85); // bass
        polygon(0, 0+0.85f*(height*(lowHeight)), 70*(scoreGlobal/450), round(scoreGlobal/80)+1);
        stroke(0xff005030,85); // mid
        polygon(0, 0+0.85f*(height*(midHeight)), 55*(scoreGlobal/450), round(scoreGlobal/80)+1);
        stroke(0xff693D00,85); // hi
        polygon(0, 0+0.85f*(height*(hiHeight)), 40*(scoreGlobal/450), round(scoreGlobal/80)+1);
        stroke(0xff1D639D,155); // bassNull
        polygon(0, 0+0.85f*(height*(lowHeightNull)), 50*(scoreGlobal/500), round(scoreGlobal/80)+1);
        stroke(0xff15A56B,155); // midNull
        polygon(0, 0+0.85f*(height*(midHeightNull)), 35*(scoreGlobal/500), round(scoreGlobal/80)+1);
        stroke(0xffF35E1F,155); // hiNull
        polygon(0, 0+0.85f*(height*(hiHeightNull)), 20*(scoreGlobal/500), round(scoreGlobal/80)+1);
      noFill();
      rotate(radians(180));
        stroke(0xff042B4C,50); // bass
        polygon(width/2, 0+0.85f*(height*(lowHeight)), 70*(scoreGlobal/45), round(scoreGlobal/80)+1);
        polygon(width/2, 0-0.85f*(height*(lowHeight)), 70*(scoreGlobal/45), round(scoreGlobal/80)+1);
        stroke(0xff005030,50); // mid
        polygon(width/2, 0+0.85f*(height*(midHeight)), 55*(scoreGlobal/45), round(scoreGlobal/80)+1);
        polygon(width/2, 0-0.85f*(height*(midHeight)), 55*(scoreGlobal/45), round(scoreGlobal/80)+1);
        stroke(0xff693D00,50); // hi
        polygon(width/2, 0+0.85f*(height*(hiHeight)), 40*(scoreGlobal/45), round(scoreGlobal/80)+1);
        polygon(width/2, 0-0.85f*(height*(hiHeight)), 40*(scoreGlobal/45), round(scoreGlobal/80)+1);
        stroke(0xff1D639D,100); // bassNull
        polygon(width/2, 0+0.85f*(height*(lowHeightNull)), 50*(scoreGlobal/50), round(scoreGlobal/80)+1);
        polygon(width/2, 0-0.85f*(height*(lowHeightNull)), 50*(scoreGlobal/50), round(scoreGlobal/80)+1);
        stroke(0xff15A56B,100); // midNull
        polygon(width/2, 0+0.85f*(height*(midHeightNull)), 35*(scoreGlobal/50), round(scoreGlobal/80)+1);
        polygon(width/2, 0-0.85f*(height*(midHeightNull)), 35*(scoreGlobal/50), round(scoreGlobal/80)+1);
        stroke(0xffF35E1F,100); // hiNull
        polygon(width/2, 0+0.85f*(height*(hiHeightNull)), 20*(scoreGlobal/50), round(scoreGlobal/80)+1);
        polygon(width/2, 0-0.85f*(height*(hiHeightNull)), 20*(scoreGlobal/50), round(scoreGlobal/80)+1);
      rotate(radians(180));
        stroke(0xff042B4C,50); // bass
        polygon(width/2, 0+0.85f*(height*(lowHeight)), 70*(scoreGlobal/45), round(scoreGlobal/80)+1);
        polygon(width/2, 0-0.85f*(height*(lowHeight)), 70*(scoreGlobal/45), round(scoreGlobal/80)+1);
        stroke(0xff005030,50); // mid
        polygon(width/2, 0+0.85f*(height*(midHeight)), 55*(scoreGlobal/45), round(scoreGlobal/80)+1);
        polygon(width/2, 0-0.85f*(height*(midHeight)), 55*(scoreGlobal/45), round(scoreGlobal/80)+1);
        stroke(0xff693D00,50); // hi
        polygon(width/2, 0+0.85f*(height*(hiHeight)), 40*(scoreGlobal/45), round(scoreGlobal/80)+1);
        polygon(width/2, 0-0.85f*(height*(hiHeight)), 40*(scoreGlobal/45), round(scoreGlobal/80)+1);
        stroke(0xff1D639D,100); // bassNull
        polygon(width/2, 0+0.85f*(height*(lowHeightNull)), 50*(scoreGlobal/50), round(scoreGlobal/80)+1);
        polygon(width/2, 0-0.85f*(height*(lowHeightNull)), 50*(scoreGlobal/50), round(scoreGlobal/80)+1);
        stroke(0xff15A56B,100); // midNull
        polygon(width/2, 0+0.85f*(height*(midHeightNull)), 35*(scoreGlobal/50), round(scoreGlobal/80)+1);
        polygon(width/2, 0-0.85f*(height*(midHeightNull)), 35*(scoreGlobal/50), round(scoreGlobal/80)+1);
        stroke(0xffF35E1F,100); // hiNull
        polygon(width/2, 0+0.85f*(height*(hiHeightNull)), 20*(scoreGlobal/50), round(scoreGlobal/80)+1);
        polygon(width/2, 0-0.85f*(height*(hiHeightNull)), 20*(scoreGlobal/50), round(scoreGlobal/80)+1);
      popMatrix();
  }
}
public void vis5(){

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
    curve(0.75f*width, 0.75f*height, xScroll, 0-height/8, xScroll, height+height/8, 0.75f*width, 0.25f*height);
    curve(0.25f*width, 0.75f*height, xScrollR, 0-height/8, xScrollR, height+height/8, 0.25f*width, 0.25f*height);
    
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
        
          strokeWeight( (pow((scoreGlobalNull/100),1.5f)) *5);
        rotate(radians(15));
          stroke(0xff042B4C,150-scoreGlobal/8); // bass
          polygon3(0, 0+0.85f*(height*(lowHeight)), 90*(scoreGlobal/450), 4);
          polygon3(0, 0-0.85f*(height*(lowHeight)), 90*(scoreGlobal/450), 4);
          stroke(0xff005030,150-scoreGlobal/8); // mid
          polygon3(0, 0+0.85f*(height*(midHeight)), 75*(scoreGlobal/450), 4);
          polygon3(0, 0-0.85f*(height*(midHeight)), 75*(scoreGlobal/450), 4);
          stroke(0xff693D00,150-scoreGlobal/8); // hi
          polygon3(0, 0+0.85f*(height*(hiHeight)), 60*(scoreGlobal/450), 4);
          polygon3(0, 0-0.85f*(height*(hiHeight)), 60*(scoreGlobal/450), 4);
        rotate(radians(30));
          stroke(0xff042B4C,150-scoreGlobal/8); // bass
          polygon3(0, 0+0.85f*(height*(lowHeight)), 90*(scoreGlobal/450), 4);
          polygon3(0, 0-0.85f*(height*(lowHeight)), 90*(scoreGlobal/450), 4);
          stroke(0xff005030,150-scoreGlobal/8); // mid
          polygon3(0, 0+0.85f*(height*(midHeight)), 75*(scoreGlobal/450), 4);
          polygon3(0, 0-0.85f*(height*(midHeight)), 75*(scoreGlobal/450), 4);
          stroke(0xff693D00,150-scoreGlobal/8); // hi
          polygon3(0, 0+0.85f*(height*(hiHeight)), 60*(scoreGlobal/450), 4);
          polygon3(0, 0-0.85f*(height*(hiHeight)), 60*(scoreGlobal/450), 4);
        rotate(radians(30));
          stroke(0xff042B4C,150-scoreGlobal/8); // bass
          polygon3(0, 0+0.85f*(height*(lowHeight)), 90*(scoreGlobal/450), 4);
          polygon3(0, 0-0.85f*(height*(lowHeight)), 90*(scoreGlobal/450), 4);
          stroke(0xff005030,150-scoreGlobal/8); // mid
          polygon3(0, 0+0.85f*(height*(midHeight)), 75*(scoreGlobal/450), 4);
          polygon3(0, 0-0.85f*(height*(midHeight)), 75*(scoreGlobal/450), 4);
          stroke(0xff693D00,150-scoreGlobal/8); // hi
          polygon3(0, 0+0.85f*(height*(hiHeight)), 60*(scoreGlobal/450), 4);
          polygon3(0, 0-0.85f*(height*(hiHeight)), 60*(scoreGlobal/450), 4);
        rotate(radians(30));
          stroke(0xff042B4C,150-scoreGlobal/8); // bass
          polygon3(0, 0+0.85f*(height*(lowHeight)), 90*(scoreGlobal/450), 4);
          polygon3(0, 0-0.85f*(height*(lowHeight)), 90*(scoreGlobal/450), 4);
          stroke(0xff005030,150-scoreGlobal/8); // mid
          polygon3(0, 0+0.85f*(height*(midHeight)), 75*(scoreGlobal/450), 4);
          polygon3(0, 0-0.85f*(height*(midHeight)), 75*(scoreGlobal/450), 4);
          stroke(0xff693D00,150-scoreGlobal/8); // hi
          polygon3(0, 0+0.85f*(height*(hiHeight)), 60*(scoreGlobal/450), 4);
          polygon3(0, 0-0.85f*(height*(hiHeight)), 60*(scoreGlobal/450), 4);
        rotate(radians(30));
          stroke(0xff042B4C,150-scoreGlobal/8); // bass
          polygon3(0, 0+0.85f*(height*(lowHeight)), 90*(scoreGlobal/450), 4);
          polygon3(0, 0-0.85f*(height*(lowHeight)), 90*(scoreGlobal/450), 4);
          stroke(0xff005030,150-scoreGlobal/8); // mid
          polygon3(0, 0+0.85f*(height*(midHeight)), 75*(scoreGlobal/450), 4);
          polygon3(0, 0-0.85f*(height*(midHeight)), 75*(scoreGlobal/450), 4);
          stroke(0xff693D00,150-scoreGlobal/8); // hi
          polygon3(0, 0+0.85f*(height*(hiHeight)), 60*(scoreGlobal/450), 4);
          polygon3(0, 0-0.85f*(height*(hiHeight)), 60*(scoreGlobal/450), 4);
        rotate(radians(30));
          stroke(0xff042B4C,150-scoreGlobal/8); // bass
          polygon3(0, 0+0.85f*(height*(lowHeight)), 90*(scoreGlobal/450), 4);
          polygon3(0, 0-0.85f*(height*(lowHeight)), 90*(scoreGlobal/450), 4);
          stroke(0xff005030,150-scoreGlobal/8); // mid
          polygon3(0, 0+0.85f*(height*(midHeight)), 75*(scoreGlobal/450), 4);
          polygon3(0, 0-0.85f*(height*(midHeight)), 75*(scoreGlobal/450), 4);
          stroke(0xff693D00,150-scoreGlobal/8); // hi
          polygon3(0, 0+0.85f*(height*(hiHeight)), 60*(scoreGlobal/450), 4);
          polygon3(0, 0-0.85f*(height*(hiHeight)), 60*(scoreGlobal/450), 4);
        
          strokeWeight(1+round(scoreGlobalNull/100));
          stroke(0xff1D639D,255); // bassNull
          polygon(0, 0+0.85f*(height*(lowHeightNull)), (5/( pow((lowHeightNull+midHeightNull+hiHeightNull),2))+width/100), 2);
          polygon(0, 0-0.85f*(height*(lowHeightNull)), (5/( pow((lowHeightNull+midHeightNull+hiHeightNull),2))+width/100), 2);
          stroke(0xff15A56B,255); // midNull
          polygon(0, 0+0.85f*(height*(midHeightNull)), (5/( pow((lowHeightNull+midHeightNull+hiHeightNull),2))+width/100), 2);
          polygon(0, 0-0.85f*(height*(midHeightNull)), (5/( pow((lowHeightNull+midHeightNull+hiHeightNull),2))+width/100), 2);
          stroke(0xffF35E1F,255); // hiNull
          polygon(0, 0+0.85f*(height*(hiHeightNull)), (5/( pow((lowHeightNull+midHeightNull+hiHeightNull),2))+width/100), 2);
          polygon(0, 0-0.85f*(height*(hiHeightNull)), (5/( pow((lowHeightNull+midHeightNull+hiHeightNull),2))+width/100), 2);
        rotate(radians(30));
          stroke(0xff1D639D,255); // bassNull
          polygon(0, 0+0.85f*(height*(lowHeightNull)), (5/( pow((lowHeightNull+midHeightNull+hiHeightNull),2))+width/100), 2);
          polygon(0, 0-0.85f*(height*(lowHeightNull)), (5/( pow((lowHeightNull+midHeightNull+hiHeightNull),2))+width/100), 2);
          stroke(0xff15A56B,255); // midNull
          polygon(0, 0+0.85f*(height*(midHeightNull)), (5/( pow((lowHeightNull+midHeightNull+hiHeightNull),2))+width/100), 2);
          polygon(0, 0-0.85f*(height*(midHeightNull)), (5/( pow((lowHeightNull+midHeightNull+hiHeightNull),2))+width/100), 2);
          stroke(0xffF35E1F,255); // hiNull
          polygon(0, 0+0.85f*(height*(hiHeightNull)), (5/( pow((lowHeightNull+midHeightNull+hiHeightNull),2))+width/100), 2);
          polygon(0, 0-0.85f*(height*(hiHeightNull)), (5/( pow((lowHeightNull+midHeightNull+hiHeightNull),2))+width/100), 2);
        rotate(radians(30));
          stroke(0xff1D639D,255); // bassNull
          polygon(0, 0+0.85f*(height*(lowHeightNull)), (5/( pow((lowHeightNull+midHeightNull+hiHeightNull),2))+width/100), 2);
          polygon(0, 0-0.85f*(height*(lowHeightNull)), (5/( pow((lowHeightNull+midHeightNull+hiHeightNull),2))+width/100), 2);
          stroke(0xff15A56B,255); // midNull
          polygon(0, 0+0.85f*(height*(midHeightNull)), (5/( pow((lowHeightNull+midHeightNull+hiHeightNull),2))+width/100), 2);
          polygon(0, 0-0.85f*(height*(midHeightNull)), (5/( pow((lowHeightNull+midHeightNull+hiHeightNull),2))+width/100), 2);
          stroke(0xffF35E1F,255); // hiNull
          polygon(0, 0+0.85f*(height*(hiHeightNull)), (5/( pow((lowHeightNull+midHeightNull+hiHeightNull),2))+width/100), 2);
          polygon(0, 0-0.85f*(height*(hiHeightNull)), (5/( pow((lowHeightNull+midHeightNull+hiHeightNull),2))+width/100), 2);
        rotate(radians(30));
          stroke(0xff1D639D,255); // bassNull
          polygon(0, 0+0.85f*(height*(lowHeightNull)), (5/( pow((lowHeightNull+midHeightNull+hiHeightNull),2))+width/100), 2);
          polygon(0, 0-0.85f*(height*(lowHeightNull)), (5/( pow((lowHeightNull+midHeightNull+hiHeightNull),2))+width/100), 2);
          stroke(0xff15A56B,255); // midNull
          polygon(0, 0+0.85f*(height*(midHeightNull)), (5/( pow((lowHeightNull+midHeightNull+hiHeightNull),2))+width/100), 2);
          polygon(0, 0-0.85f*(height*(midHeightNull)), (5/( pow((lowHeightNull+midHeightNull+hiHeightNull),2))+width/100), 2);
          stroke(0xffF35E1F,255); // hiNull
          polygon(0, 0+0.85f*(height*(hiHeightNull)), (5/( pow((lowHeightNull+midHeightNull+hiHeightNull),2))+width/100), 2);
          polygon(0, 0-0.85f*(height*(hiHeightNull)), (5/( pow((lowHeightNull+midHeightNull+hiHeightNull),2))+width/100), 2);
        rotate(radians(30));
          stroke(0xff1D639D,255); // bassNull
          polygon(0, 0+0.85f*(height*(lowHeightNull)), (5/( pow((lowHeightNull+midHeightNull+hiHeightNull),2))+width/100), 2);
          polygon(0, 0-0.85f*(height*(lowHeightNull)), (5/( pow((lowHeightNull+midHeightNull+hiHeightNull),2))+width/100), 2);
          stroke(0xff15A56B,255); // midNull
          polygon(0, 0+0.85f*(height*(midHeightNull)), (5/( pow((lowHeightNull+midHeightNull+hiHeightNull),2))+width/100), 2);
          polygon(0, 0-0.85f*(height*(midHeightNull)), (5/( pow((lowHeightNull+midHeightNull+hiHeightNull),2))+width/100), 2);
          stroke(0xffF35E1F,255); // hiNull
          polygon(0, 0+0.85f*(height*(hiHeightNull)), (5/( pow((lowHeightNull+midHeightNull+hiHeightNull),2))+width/100), 2);
          polygon(0, 0-0.85f*(height*(hiHeightNull)), (5/( pow((lowHeightNull+midHeightNull+hiHeightNull),2))+width/100), 2);
        rotate(radians(30));
          stroke(0xff1D639D,255); // bassNull
          polygon(0, 0+0.85f*(height*(lowHeightNull)), (5/( pow((lowHeightNull+midHeightNull+hiHeightNull),2))+width/100), 2);
          polygon(0, 0-0.85f*(height*(lowHeightNull)), (5/( pow((lowHeightNull+midHeightNull+hiHeightNull),2))+width/100), 2);
          stroke(0xff15A56B,255); // midNull
          polygon(0, 0+0.85f*(height*(midHeightNull)), (5/( pow((lowHeightNull+midHeightNull+hiHeightNull),2))+width/100), 2);
          polygon(0, 0-0.85f*(height*(midHeightNull)), (5/( pow((lowHeightNull+midHeightNull+hiHeightNull),2))+width/100), 2);
          stroke(0xffF35E1F,255); // hiNull
          polygon(0, 0+0.85f*(height*(hiHeightNull)), (5/( pow((lowHeightNull+midHeightNull+hiHeightNull),2))+width/100), 2);
          polygon(0, 0-0.85f*(height*(hiHeightNull)), (5/( pow((lowHeightNull+midHeightNull+hiHeightNull),2))+width/100), 2);
          
          
      popMatrix();
      
    //// background fun
    //strokeWeight (0);
    //stroke (0,0,0,0);
    ////background (scoreLow/2, scoreHi/2, scoreMid/2, 1);
    //fill (scoreLow/3.5, scoreHi/3.5, scoreMid/3.5, 200);
    //rect (0,0,width,height);
    
  }

}
public void vis6(){
  if (state == 6){
    
    // background fun
    strokeWeight (0);
    stroke (0,0,0,0);
    fill (45*lowHeight/2, 43*midHeight/2, 170*hiHeight/2, 175);
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
    dxLow = (4*PI / period) * xspacing * pow(1.6f*lowHeight,4) ;
    dxMid = (4*PI / period) * xspacing * pow(1.6f*midHeight,4) ;
    dxHi = (4*PI / period) * xspacing * pow(1.6f*hiHeight,4) ;
    dxLowNull = (4*PI / period) * xspacing * pow(1.6f*lowHeightNull,4) ;
    dxMidNull = (4*PI / period) * xspacing * pow(1.6f*midHeightNull,4) ;
    dxHiNull = (4*PI / period) * xspacing * pow(1.6f*hiHeightNull,4) ;
    
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
public void vis7(){
  
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
      fill (scoreLow/3.5f, scoreHi/3.5f, scoreMid/3.5f, 30);
      strokeWeight(pow(scoreGlobal/115, 4));
        stroke(0xff042B4C,35); // bass
        line(-width, 0+0.85f*(height*(lowHeight)), width, 0+0.85f*(height*(lowHeight)));
        line(-width, 0-0.85f*(height*(lowHeight)), width, 0-0.85f*(height*(lowHeight)));
        stroke(0xff005030,35); // mid
        line(-width, 0+0.85f*(height*(midHeight)), width, 0+0.85f*(height*(midHeight)));
        line(-width, 0-0.85f*(height*(midHeight)), width, 0-0.85f*(height*(midHeight)));
        stroke(0xff693D00,35); // hi
        line(-width, 0+0.85f*(height*(hiHeight)), width, 0+0.85f*(height*(hiHeight)));
        line(-width, 0-0.85f*(height*(hiHeight)), width, 0-0.85f*(height*(hiHeight)));
      strokeWeight(pow(scoreGlobalNull/230, 4));
        stroke(0xff1D639D,155); // bassNull
        line(-width, 0+0.85f*(height*(lowHeightNull)), width, 0+0.85f*(height*(lowHeightNull)));
        line(-width, 0-0.85f*(height*(lowHeightNull)), width, 0-0.85f*(height*(lowHeightNull)));
        stroke(0xff15A56B,155);  // midNull
        line(-width, 0+0.85f*(height*(midHeightNull)), width, 0+0.85f*(height*(midHeightNull)));
        line(-width, 0-0.85f*(height*(midHeightNull)), width, 0-0.85f*(height*(midHeightNull)));
        stroke(0xffF35E1F,155);  // hiNull
        line(-width, 0+0.85f*(height*(hiHeightNull)), width, 0+0.85f*(height*(hiHeightNull)));
        line(-width, 0-0.85f*(height*(hiHeightNull)), width, 0-0.85f*(height*(hiHeightNull)));
        
        stroke(0xff042B4C,85); // bass
        polygon(0, 0+0.85f*(height*(lowHeight)), 70*(scoreGlobal/450), round(scoreGlobal/80)+1);
        stroke(0xff005030,85); // mid
        polygon(0, 0+0.85f*(height*(midHeight)), 55*(scoreGlobal/450), round(scoreGlobal/80)+1);
        stroke(0xff693D00,85); // hi
        polygon(0, 0+0.85f*(height*(hiHeight)), 40*(scoreGlobal/450), round(scoreGlobal/80)+1);
        stroke(0xff1D639D,155); // bassNull
        polygon(0, 0+0.85f*(height*(lowHeightNull)), 50*(scoreGlobal/500), round(scoreGlobal/80)+1);
        stroke(0xff15A56B,155); // midNull
        polygon(0, 0+0.85f*(height*(midHeightNull)), 35*(scoreGlobal/500), round(scoreGlobal/80)+1);
        stroke(0xffF35E1F,155); // hiNull
        polygon(0, 0+0.85f*(height*(hiHeightNull)), 20*(scoreGlobal/500), round(scoreGlobal/80)+1);
      rotate(radians(180));
        stroke(0xff042B4C,85); // bass
        polygon(0, 0+0.85f*(height*(lowHeight)), 70*(scoreGlobal/450), round(scoreGlobal/80)+1);
        stroke(0xff005030,85); // mid
        polygon(0, 0+0.85f*(height*(midHeight)), 55*(scoreGlobal/450), round(scoreGlobal/80)+1);
        stroke(0xff693D00,85); // hi
        polygon(0, 0+0.85f*(height*(hiHeight)), 40*(scoreGlobal/450), round(scoreGlobal/80)+1);
        stroke(0xff1D639D,155); // bassNull
        polygon(0, 0+0.85f*(height*(lowHeightNull)), 50*(scoreGlobal/500), round(scoreGlobal/80)+1);
        stroke(0xff15A56B,155); // midNull
        polygon(0, 0+0.85f*(height*(midHeightNull)), 35*(scoreGlobal/500), round(scoreGlobal/80)+1);
        stroke(0xffF35E1F,155); // hiNull
        polygon(0, 0+0.85f*(height*(hiHeightNull)), 20*(scoreGlobal/500), round(scoreGlobal/80)+1);
      noFill();
      rotate(radians(180));
        stroke(0xff042B4C,50); // bass
        polygon(width/2, 0+0.85f*(height*(lowHeight)), 70*(scoreGlobal/45), round(scoreGlobal/80)+1);
        polygon(width/2, 0-0.85f*(height*(lowHeight)), 70*(scoreGlobal/45), round(scoreGlobal/80)+1);
        stroke(0xff005030,50); // mid
        polygon(width/2, 0+0.85f*(height*(midHeight)), 55*(scoreGlobal/45), round(scoreGlobal/80)+1);
        polygon(width/2, 0-0.85f*(height*(midHeight)), 55*(scoreGlobal/45), round(scoreGlobal/80)+1);
        stroke(0xff693D00,50); // hi
        polygon(width/2, 0+0.85f*(height*(hiHeight)), 40*(scoreGlobal/45), round(scoreGlobal/80)+1);
        polygon(width/2, 0-0.85f*(height*(hiHeight)), 40*(scoreGlobal/45), round(scoreGlobal/80)+1);
        stroke(0xff1D639D,100); // bassNull
        polygon(width/2, 0+0.85f*(height*(lowHeightNull)), 50*(scoreGlobal/50), round(scoreGlobal/80)+1);
        polygon(width/2, 0-0.85f*(height*(lowHeightNull)), 50*(scoreGlobal/50), round(scoreGlobal/80)+1);
        stroke(0xff15A56B,100); // midNull
        polygon(width/2, 0+0.85f*(height*(midHeightNull)), 35*(scoreGlobal/50), round(scoreGlobal/80)+1);
        polygon(width/2, 0-0.85f*(height*(midHeightNull)), 35*(scoreGlobal/50), round(scoreGlobal/80)+1);
        stroke(0xffF35E1F,100); // hiNull
        polygon(width/2, 0+0.85f*(height*(hiHeightNull)), 20*(scoreGlobal/50), round(scoreGlobal/80)+1);
        polygon(width/2, 0-0.85f*(height*(hiHeightNull)), 20*(scoreGlobal/50), round(scoreGlobal/80)+1);
      rotate(radians(180));
        stroke(0xff042B4C,50); // bass
        polygon(width/2, 0+0.85f*(height*(lowHeight)), 70*(scoreGlobal/45), round(scoreGlobal/80)+1);
        polygon(width/2, 0-0.85f*(height*(lowHeight)), 70*(scoreGlobal/45), round(scoreGlobal/80)+1);
        stroke(0xff005030,50); // mid
        polygon(width/2, 0+0.85f*(height*(midHeight)), 55*(scoreGlobal/45), round(scoreGlobal/80)+1);
        polygon(width/2, 0-0.85f*(height*(midHeight)), 55*(scoreGlobal/45), round(scoreGlobal/80)+1);
        stroke(0xff693D00,50); // hi
        polygon(width/2, 0+0.85f*(height*(hiHeight)), 40*(scoreGlobal/45), round(scoreGlobal/80)+1);
        polygon(width/2, 0-0.85f*(height*(hiHeight)), 40*(scoreGlobal/45), round(scoreGlobal/80)+1);
        stroke(0xff1D639D,100); // bassNull
        polygon(width/2, 0+0.85f*(height*(lowHeightNull)), 50*(scoreGlobal/50), round(scoreGlobal/80)+1);
        polygon(width/2, 0-0.85f*(height*(lowHeightNull)), 50*(scoreGlobal/50), round(scoreGlobal/80)+1);
        stroke(0xff15A56B,100); // midNull
        polygon(width/2, 0+0.85f*(height*(midHeightNull)), 35*(scoreGlobal/50), round(scoreGlobal/80)+1);
        polygon(width/2, 0-0.85f*(height*(midHeightNull)), 35*(scoreGlobal/50), round(scoreGlobal/80)+1);
        stroke(0xffF35E1F,100); // hiNull
        polygon(width/2, 0+0.85f*(height*(hiHeightNull)), 20*(scoreGlobal/50), round(scoreGlobal/80)+1);
        polygon(width/2, 0-0.85f*(height*(hiHeightNull)), 20*(scoreGlobal/50), round(scoreGlobal/80)+1);
      popMatrix();
  }
}
public void vis8(){

  if (state == 8){
    
    // background fun
    strokeWeight (0);
    stroke (0,0,0,0);
    fill (45*lowHeight/2, 43*midHeight/2, 170*hiHeight/2, 40);
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
    curve(0.75f*width, 0.75f*height, xScroll, 0-height/8, xScroll, height+height/8, 0.75f*width, 0.25f*height);
    curve(0.25f*width, 0.75f*height, xScrollR, 0-height/8, xScrollR, height+height/8, 0.25f*width, 0.25f*height);
    
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
        
          strokeWeight( (pow((scoreGlobalNull/100),1.5f)) *5);
        rotate(radians(15));
          stroke(0xff042B4C,150-scoreGlobal/8); // bass
          polygon3(0, 0+0.85f*(height*(lowHeight)), 90*(scoreGlobal/450), 4);
          polygon3(0, 0-0.85f*(height*(lowHeight)), 90*(scoreGlobal/450), 4);
          stroke(0xff005030,150-scoreGlobal/8); // mid
          polygon3(0, 0+0.85f*(height*(midHeight)), 75*(scoreGlobal/450), 4);
          polygon3(0, 0-0.85f*(height*(midHeight)), 75*(scoreGlobal/450), 4);
          stroke(0xff693D00,150-scoreGlobal/8); // hi
          polygon3(0, 0+0.85f*(height*(hiHeight)), 60*(scoreGlobal/450), 4);
          polygon3(0, 0-0.85f*(height*(hiHeight)), 60*(scoreGlobal/450), 4);
        rotate(radians(30));
          stroke(0xff042B4C,150-scoreGlobal/8); // bass
          polygon3(0, 0+0.85f*(height*(lowHeight)), 90*(scoreGlobal/450), 4);
          polygon3(0, 0-0.85f*(height*(lowHeight)), 90*(scoreGlobal/450), 4);
          stroke(0xff005030,150-scoreGlobal/8); // mid
          polygon3(0, 0+0.85f*(height*(midHeight)), 75*(scoreGlobal/450), 4);
          polygon3(0, 0-0.85f*(height*(midHeight)), 75*(scoreGlobal/450), 4);
          stroke(0xff693D00,150-scoreGlobal/8); // hi
          polygon3(0, 0+0.85f*(height*(hiHeight)), 60*(scoreGlobal/450), 4);
          polygon3(0, 0-0.85f*(height*(hiHeight)), 60*(scoreGlobal/450), 4);
        rotate(radians(30));
          stroke(0xff042B4C,150-scoreGlobal/8); // bass
          polygon3(0, 0+0.85f*(height*(lowHeight)), 90*(scoreGlobal/450), 4);
          polygon3(0, 0-0.85f*(height*(lowHeight)), 90*(scoreGlobal/450), 4);
          stroke(0xff005030,150-scoreGlobal/8); // mid
          polygon3(0, 0+0.85f*(height*(midHeight)), 75*(scoreGlobal/450), 4);
          polygon3(0, 0-0.85f*(height*(midHeight)), 75*(scoreGlobal/450), 4);
          stroke(0xff693D00,150-scoreGlobal/8); // hi
          polygon3(0, 0+0.85f*(height*(hiHeight)), 60*(scoreGlobal/450), 4);
          polygon3(0, 0-0.85f*(height*(hiHeight)), 60*(scoreGlobal/450), 4);
        rotate(radians(30));
          stroke(0xff042B4C,150-scoreGlobal/8); // bass
          polygon3(0, 0+0.85f*(height*(lowHeight)), 90*(scoreGlobal/450), 4);
          polygon3(0, 0-0.85f*(height*(lowHeight)), 90*(scoreGlobal/450), 4);
          stroke(0xff005030,150-scoreGlobal/8); // mid
          polygon3(0, 0+0.85f*(height*(midHeight)), 75*(scoreGlobal/450), 4);
          polygon3(0, 0-0.85f*(height*(midHeight)), 75*(scoreGlobal/450), 4);
          stroke(0xff693D00,150-scoreGlobal/8); // hi
          polygon3(0, 0+0.85f*(height*(hiHeight)), 60*(scoreGlobal/450), 4);
          polygon3(0, 0-0.85f*(height*(hiHeight)), 60*(scoreGlobal/450), 4);
        rotate(radians(30));
          stroke(0xff042B4C,150-scoreGlobal/8); // bass
          polygon3(0, 0+0.85f*(height*(lowHeight)), 90*(scoreGlobal/450), 4);
          polygon3(0, 0-0.85f*(height*(lowHeight)), 90*(scoreGlobal/450), 4);
          stroke(0xff005030,150-scoreGlobal/8); // mid
          polygon3(0, 0+0.85f*(height*(midHeight)), 75*(scoreGlobal/450), 4);
          polygon3(0, 0-0.85f*(height*(midHeight)), 75*(scoreGlobal/450), 4);
          stroke(0xff693D00,150-scoreGlobal/8); // hi
          polygon3(0, 0+0.85f*(height*(hiHeight)), 60*(scoreGlobal/450), 4);
          polygon3(0, 0-0.85f*(height*(hiHeight)), 60*(scoreGlobal/450), 4);
        rotate(radians(30));
          stroke(0xff042B4C,150-scoreGlobal/8); // bass
          polygon3(0, 0+0.85f*(height*(lowHeight)), 90*(scoreGlobal/450), 4);
          polygon3(0, 0-0.85f*(height*(lowHeight)), 90*(scoreGlobal/450), 4);
          stroke(0xff005030,150-scoreGlobal/8); // mid
          polygon3(0, 0+0.85f*(height*(midHeight)), 75*(scoreGlobal/450), 4);
          polygon3(0, 0-0.85f*(height*(midHeight)), 75*(scoreGlobal/450), 4);
          stroke(0xff693D00,150-scoreGlobal/8); // hi
          polygon3(0, 0+0.85f*(height*(hiHeight)), 60*(scoreGlobal/450), 4);
          polygon3(0, 0-0.85f*(height*(hiHeight)), 60*(scoreGlobal/450), 4);
        
          strokeWeight(1+round(scoreGlobalNull/160));
          stroke(0xff1D639D,255); // bassNull
          polygon(0, 0+0.85f*(height*(lowHeightNull)), (5/( pow((lowHeightNull+midHeightNull+hiHeightNull),2))+width/100), 2);
          polygon(0, 0-0.85f*(height*(lowHeightNull)), (5/( pow((lowHeightNull+midHeightNull+hiHeightNull),2))+width/100), 2);
          stroke(0xff15A56B,255); // midNull
          polygon(0, 0+0.85f*(height*(midHeightNull)), (5/( pow((lowHeightNull+midHeightNull+hiHeightNull),2))+width/100), 2);
          polygon(0, 0-0.85f*(height*(midHeightNull)), (5/( pow((lowHeightNull+midHeightNull+hiHeightNull),2))+width/100), 2);
          stroke(0xffF35E1F,255); // hiNull
          polygon(0, 0+0.85f*(height*(hiHeightNull)), (5/( pow((lowHeightNull+midHeightNull+hiHeightNull),2))+width/100), 2);
          polygon(0, 0-0.85f*(height*(hiHeightNull)), (5/( pow((lowHeightNull+midHeightNull+hiHeightNull),2))+width/100), 2);
        rotate(radians(30));
          stroke(0xff1D639D,255); // bassNull
          polygon(0, 0+0.85f*(height*(lowHeightNull)), (5/( pow((lowHeightNull+midHeightNull+hiHeightNull),2))+width/100), 2);
          polygon(0, 0-0.85f*(height*(lowHeightNull)), (5/( pow((lowHeightNull+midHeightNull+hiHeightNull),2))+width/100), 2);
          stroke(0xff15A56B,255); // midNull
          polygon(0, 0+0.85f*(height*(midHeightNull)), (5/( pow((lowHeightNull+midHeightNull+hiHeightNull),2))+width/100), 2);
          polygon(0, 0-0.85f*(height*(midHeightNull)), (5/( pow((lowHeightNull+midHeightNull+hiHeightNull),2))+width/100), 2);
          stroke(0xffF35E1F,255); // hiNull
          polygon(0, 0+0.85f*(height*(hiHeightNull)), (5/( pow((lowHeightNull+midHeightNull+hiHeightNull),2))+width/100), 2);
          polygon(0, 0-0.85f*(height*(hiHeightNull)), (5/( pow((lowHeightNull+midHeightNull+hiHeightNull),2))+width/100), 2);
        rotate(radians(30));
          stroke(0xff1D639D,255); // bassNull
          polygon(0, 0+0.85f*(height*(lowHeightNull)), (5/( pow((lowHeightNull+midHeightNull+hiHeightNull),2))+width/100), 2);
          polygon(0, 0-0.85f*(height*(lowHeightNull)), (5/( pow((lowHeightNull+midHeightNull+hiHeightNull),2))+width/100), 2);
          stroke(0xff15A56B,255); // midNull
          polygon(0, 0+0.85f*(height*(midHeightNull)), (5/( pow((lowHeightNull+midHeightNull+hiHeightNull),2))+width/100), 2);
          polygon(0, 0-0.85f*(height*(midHeightNull)), (5/( pow((lowHeightNull+midHeightNull+hiHeightNull),2))+width/100), 2);
          stroke(0xffF35E1F,255); // hiNull
          polygon(0, 0+0.85f*(height*(hiHeightNull)), (5/( pow((lowHeightNull+midHeightNull+hiHeightNull),2))+width/100), 2);
          polygon(0, 0-0.85f*(height*(hiHeightNull)), (5/( pow((lowHeightNull+midHeightNull+hiHeightNull),2))+width/100), 2);
        rotate(radians(30));
          stroke(0xff1D639D,255); // bassNull
          polygon(0, 0+0.85f*(height*(lowHeightNull)), (5/( pow((lowHeightNull+midHeightNull+hiHeightNull),2))+width/100), 2);
          polygon(0, 0-0.85f*(height*(lowHeightNull)), (5/( pow((lowHeightNull+midHeightNull+hiHeightNull),2))+width/100), 2);
          stroke(0xff15A56B,255); // midNull
          polygon(0, 0+0.85f*(height*(midHeightNull)), (5/( pow((lowHeightNull+midHeightNull+hiHeightNull),2))+width/100), 2);
          polygon(0, 0-0.85f*(height*(midHeightNull)), (5/( pow((lowHeightNull+midHeightNull+hiHeightNull),2))+width/100), 2);
          stroke(0xffF35E1F,255); // hiNull
          polygon(0, 0+0.85f*(height*(hiHeightNull)), (5/( pow((lowHeightNull+midHeightNull+hiHeightNull),2))+width/100), 2);
          polygon(0, 0-0.85f*(height*(hiHeightNull)), (5/( pow((lowHeightNull+midHeightNull+hiHeightNull),2))+width/100), 2);
        rotate(radians(30));
          stroke(0xff1D639D,255); // bassNull
          polygon(0, 0+0.85f*(height*(lowHeightNull)), (5/( pow((lowHeightNull+midHeightNull+hiHeightNull),2))+width/100), 2);
          polygon(0, 0-0.85f*(height*(lowHeightNull)), (5/( pow((lowHeightNull+midHeightNull+hiHeightNull),2))+width/100), 2);
          stroke(0xff15A56B,255); // midNull
          polygon(0, 0+0.85f*(height*(midHeightNull)), (5/( pow((lowHeightNull+midHeightNull+hiHeightNull),2))+width/100), 2);
          polygon(0, 0-0.85f*(height*(midHeightNull)), (5/( pow((lowHeightNull+midHeightNull+hiHeightNull),2))+width/100), 2);
          stroke(0xffF35E1F,255); // hiNull
          polygon(0, 0+0.85f*(height*(hiHeightNull)), (5/( pow((lowHeightNull+midHeightNull+hiHeightNull),2))+width/100), 2);
          polygon(0, 0-0.85f*(height*(hiHeightNull)), (5/( pow((lowHeightNull+midHeightNull+hiHeightNull),2))+width/100), 2);
        rotate(radians(30));
          stroke(0xff1D639D,255); // bassNull
          polygon(0, 0+0.85f*(height*(lowHeightNull)), (5/( pow((lowHeightNull+midHeightNull+hiHeightNull),2))+width/100), 2);
          polygon(0, 0-0.85f*(height*(lowHeightNull)), (5/( pow((lowHeightNull+midHeightNull+hiHeightNull),2))+width/100), 2);
          stroke(0xff15A56B,255); // midNull
          polygon(0, 0+0.85f*(height*(midHeightNull)), (5/( pow((lowHeightNull+midHeightNull+hiHeightNull),2))+width/100), 2);
          polygon(0, 0-0.85f*(height*(midHeightNull)), (5/( pow((lowHeightNull+midHeightNull+hiHeightNull),2))+width/100), 2);
          stroke(0xffF35E1F,255); // hiNull
          polygon(0, 0+0.85f*(height*(hiHeightNull)), (5/( pow((lowHeightNull+midHeightNull+hiHeightNull),2))+width/100), 2);
          polygon(0, 0-0.85f*(height*(hiHeightNull)), (5/( pow((lowHeightNull+midHeightNull+hiHeightNull),2))+width/100), 2);
          
          
      popMatrix();
      
    //// background fun
    //strokeWeight (0);
    //stroke (0,0,0,0);
    ////background (scoreLow/2, scoreHi/2, scoreMid/2, 1);
    //fill (scoreLow/3.5, scoreHi/3.5, scoreMid/3.5, 200);
    //rect (0,0,width,height);
    
  }

}
public void vis9(){
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
    dxLow = (4*PI / period) * xspacing * pow(1.6f*lowHeight,4) ;
    dxMid = (4*PI / period) * xspacing * pow(1.6f*midHeight,4) ;
    dxHi = (4*PI / period) * xspacing * pow(1.6f*hiHeight,4) ;
    dxLowNull = (4*PI / period) * xspacing * pow(1.6f*lowHeightNull,4) ;
    dxMidNull = (4*PI / period) * xspacing * pow(1.6f*midHeightNull,4) ;
    dxHiNull = (4*PI / period) * xspacing * pow(1.6f*hiHeightNull,4) ;
    
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
  public void settings() {  fullScreen(); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "--present", "--window-color=#666666", "--hide-stop", "MUV_beta_0_7" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
