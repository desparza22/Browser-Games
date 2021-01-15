package com.diegoaesparza.objects.enemy;

import com.diegoaesparza.*;
import com.diegoaesparza.collisions.*;
import com.diegoaesparza.objects.*;
import com.diegoaesparza.objects.enemy.*;
import com.diegoaesparza.objects.obstacle.*;
import com.diegoaesparza.objects.pickup.*;
import com.diegoaesparza.objects.player.*;
import com.diegoaesparza.objects.visual.*;

import java.awt.*;
import javax.imageio.ImageIO;
import java.io.*;
import java.awt.image.BufferedImage;
import java.util.concurrent.Semaphore;

public class EnemyRenderer extends Renderer {

  // private Handler handler;

  private BufferedImage[] planes = new BufferedImage[4];
  private BufferedImage[] pacmans = new BufferedImage[2];

  public EnemyRenderer(Handler handler) {
    super(handler);
    // try{
    //   BufferedImage flat = ImageIO.read(new File("plane32/planeFlat32.png"));
    //   BufferedImage tilted = ImageIO.read(new File("plane32/planeTilt32.png"));
    //   // BufferedImage retracted = ImageIO.read(new File("playerRunning32/retracted32.png"));
    //   planes[0] = flat;
    //   planes[1] = flat;
    //   planes[2] = flat;
    //   planes[3] = tilted;
    //
    //   BufferedImage open = ImageIO.read(new File("pacman32/pacOpen32.png"));
    //   BufferedImage closed = ImageIO.read(new File("pacman32/pacClosed32.png"));
    //   pacmans[0] = open;
    //   pacmans[1] = closed;
    // } catch(IOException e) {
    //   e.printStackTrace();
    //   System.exit(0);
    // }
  }

  // protected void render() {
  //   Game.currentG().drawImage(planes[(this.handler.getTime()/16) % 4], 240, 140, null);
  //   Game.currentG().drawImage(pacmans[(this.handler.getTime()/16) % 2], 180, 240, null);
  // }


}
