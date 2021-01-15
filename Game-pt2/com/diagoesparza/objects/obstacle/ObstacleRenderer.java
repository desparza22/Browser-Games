package com.diegoaesparza.objects.obstacle;

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

public class ObstacleRenderer extends Renderer {

  // private Handler handler;
  private BufferedImage[] cars = new BufferedImage[8];
  private BufferedImage[] oranges = new BufferedImage[4];

  public ObstacleRenderer(Handler handler) {
    super(handler);
  }
}
