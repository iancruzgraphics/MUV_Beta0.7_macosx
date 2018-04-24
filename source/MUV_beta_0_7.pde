// library import- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

import ddf.minim.*;
import ddf.minim.analysis.*;
Minim minim;
import ddf.minim.ugens.*;
Gain       gain;
AudioInput AudioIn;
FFT fft;

// possible library for advanced graphic effects - - - - - - - - - - - - - - - - - - - - - -
// import AULib.*;

import themidibus.*; //Import the library  - - - - - - - - - - - - - - - - - - - - - - - - -
import javax.sound.midi.MidiMessage; //Import the MidiMessage classes http://java.sun.com/j2se/1.5.0/docs/api/javax/sound/midi/MidiMessage.html
import javax.sound.midi.SysexMessage;
import javax.sound.midi.ShortMessage;

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
float specLow = 0.04; // low frequency sound (3% of spectrum)
float specMid = 0.22; // mid frequency sound (15% of spectrum)
float specHi = 0.40;  // hi frequency sound (40% of spectrum)
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

void setup()
{  
  frameRate(60);
  
  //size(1280,720);
  fullScreen();
    
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

void draw()
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
  scoreGlobal = (scoreLow + .70*scoreMid + .45*scoreHi) +50;
  scoreGlobalNull = (scoreLow + .70*scoreMid + .45*scoreHi) +50;
  
  if (oldScoreGlobal > (scoreGlobal+5)) {
    scoreGlobal = oldScoreGlobal - scoreGlobalDecreaseRate;
  }
  if (scoreGlobal < 50.5){
    scoreGlobal = 0;
  }
  if (scoreGlobal > 700){
    scoreGlobal = 700;
  }
  if (scoreGlobalNull < 50.5){
    scoreGlobalNull = 0;
  }
  if (scoreGlobalNull > 700){
    scoreGlobalNull = 700;
  }

  lowHeight = scoreLow/scoreGlobal*1.1;
  midHeight = scoreMid/scoreGlobal*1.1;
  hiHeight = scoreHi/scoreGlobal*1.1;
  lowHeightNull = scoreLowNull/scoreGlobal*1.1;
  midHeightNull = scoreMidNull/scoreGlobal*1.1;
  hiHeightNull = scoreHiNull/scoreGlobal*1.1;

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

void polygon(float x, float y, float radius, int npoints) {
  float angle = TWO_PI / npoints;
  beginShape();
  for (float a = 0; a < TWO_PI; a += angle) {
    float sx = x + cos(a) * radius;
    float sy = y + sin(a) * radius;
    vertex(sx, sy);
  }
  endShape(CLOSE);
}

void polygon2(float x, float y, float radius, int npoints) {
  float angle = TWO_PI / npoints;
  beginShape();
  for (float a = 0; a < TWO_PI; a += angle) {
    float sx = x + cos(a+PI/2) * radius;
    float sy = y + sin(a+PI/2) * radius;
    vertex(sx, sy);
  }
  endShape(CLOSE);
}

void polygon3(float x, float y, float radius, int npoints) {
  float angle = TWO_PI / npoints;
  beginShape();
  for (float a = 0; a < TWO_PI; a += angle) {
    float sx = x + cos(a+PI/4) * radius;
    float sy = y + sin(a+PI/4) * radius;
    vertex(sx, sy);
  }
  endShape(CLOSE);
}

void polygon4(float x, float y, float radius, int npoints) {
  float angle = TWO_PI / npoints;
  beginShape();
  for (float a = 0; a < TWO_PI; a += angle) {
    float sx = x + cos(a+PI) * radius;
    float sy = y + sin(a+PI) * radius;
    vertex(sx, sy);
  }
  endShape(CLOSE);
}
