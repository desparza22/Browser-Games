// package com.diegoaesparza;
//
// import com.diegoaesparza.*;
// import com.diegoaesparza.collisions.*;
// import com.diegoaesparza.objects.*;
// import com.diegoaesparza.objects.enemy.*;
// import com.diegoaesparza.objects.obstacle.*;
// import com.diegoaesparza.objects.pickup.*;
// import com.diegoaesparza.objects.player.*;
// import com.diegoaesparza.objects.visual.*;
//
// import java.awt.Color;
//
// public class HUD {
//   private int xPosition, yPosition;
//   private int coolDown = 0;
//   private float colorRatio;
//   private int width;
//   private int height;
//   private float widthRatio;
//
//
//   public HUD(int xPosition, int yPosition, int width, int height) {
//     this.xPosition = xPosition;
//     this.yPosition = yPosition;
//     this.colorRatio = 255f / 1000f;
//     this.width = width;
//     this.height = height;
//     this.widthRatio = this.width / 1000;
//   }
//
//   public void blasterPaint() {
//     int redGreenValues = 255 - (int) (this.colorRatio * this.coolDown);
//     Game.currentG().setColor(Color.white);
//     Game.currentG().fillRect(this.xPosition, this.yPosition, this.width, this.height);
//     Game.currentG().setColor(new Color(redGreenValues, redGreenValues, 255));
//     int rectWidth = this.width - (int) (this.coolDown * this.widthRatio);
//     Game.currentG().fillRect(this.xPosition, this.yPosition, rectWidth, this.height);
//     Game.currentG().setColor(Color.white);
//     Game.currentG().drawRect(this.xPosition, this.yPosition, this.width, this.height);
//   }
//
//   public void updateCoolDown(int coolDown) {
//     this.coolDown = coolDown;
//   }
// }
