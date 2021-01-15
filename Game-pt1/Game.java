// package com.tutorial.main;

import java.awt.*;
import java.awt.image.*;
import java.util.Random;
import java.io.*;
import java.lang.Math;
import java.util.concurrent.Semaphore;


public class Game extends Canvas implements Runnable {

  private static final long serialVersionUID = 1550691097823471818L;

  public static final int WIDTH = 640, HEIGHT = WIDTH / 12 * 9;

  private Thread thread;
  private boolean running = false;

  private Random r;
  private Handler handler;
  private HUD hud;

  private Spawn spawner;


  private SmartEnemy justSoItCompilesSmart;
  private CountDown justSoItCompilesCount;
  private Collision justSoItCompilesCollision;
  private KeyScramble justSoItCompilesKeyScramble;

  private Semaphore gameStart = new Semaphore(0);

  public Game() {
    // SmartEnemy.fillSmartVelocities(new File("smartVelocities.txt"));

    this.handler = new Handler();
    KeyInput ki = new KeyInput(this.handler);
    this.handler.addKeyInput(ki);
    this.addKeyListener(ki);

    new Window(WIDTH, HEIGHT, "The first", this);

    hud = new HUD();
    spawner = new Spawn(handler, hud);

    this.r = new Random();
    // ID[] playerTypes = new ID[] {ID.Player, ID.Player2};
    GameObject player = new Player((float) WIDTH/2 - Player.WIDTH, (float) HEIGHT/2 - Player.HEIGHT, ID.Player, handler);
    this.handler.addObject(player);
    spawner.addPlayer(player);
    ki.addPlayerSpeed(player.getSpeed());
    ki.addPlayer(player); //when you're done being lazy, combine these steps
    // System.out.println(player.getSpeed());



    this.handler.addObject(new BasicEnemy((float) r.nextInt(WIDTH - BasicEnemy.WIDTH), (float) r.nextInt(HEIGHT - BasicEnemy.HEIGHT), ID.BasicEnemy, handler));

    // this.handler.addObject(new BasicEnemy(WIDTH/2 - BasicEnemy.WIDTH + 50, HEIGHT/2 - BasicEnemy.HEIGHT, ID.BasicEnemy));
    gameStart.release();
  }

  public synchronized void start() {
    thread = new Thread(this);
    thread.start();
    running = true;
  }

  public synchronized void stop() {
    try{
      thread.join();
      running = false;
    } catch(Exception e) {
      e.printStackTrace();
    }
  }

  public void run() {
    this.requestFocus();
    long lastTime = System.nanoTime();
    double amountOfTicks = 60.0; //ends up being num ticks per second
    double nanoSeconds = 1000000000 / amountOfTicks;
    double delta = 0;
    long timer = System.currentTimeMillis();
    int frames = 0;
    try {
      gameStart.acquire(); // makes sure that player speed has been added to KeyInput object
    } catch(InterruptedException e) {
      e.printStackTrace();
      System.exit(0);
    }
    while(running) {
      long now = System.nanoTime();
      // System.out.println(now);
      delta += (now - lastTime) / nanoSeconds;
      lastTime = now;
      while(delta >= 1) {
        tick();
        delta--;
      }
      // if(running) {
      render();
      // }
      frames++;

      if(System.currentTimeMillis() - timer > 1000) {
        timer += 1000;
        // System.out.println("FPS: " + frames);
        frames = 0;
      }
    }
    stop();
  }

  private void tick() {
    handler.tick();
    hud.tick();
    spawner.tick();
  }

  private void render() {
    BufferStrategy bs = this.getBufferStrategy();
    if(bs == null) {
      this.createBufferStrategy(3);
      return;
    }
    Graphics g = bs.getDrawGraphics();

    g.setColor(Color.black);
    g.fillRect(0,0,WIDTH,HEIGHT);

    handler.render(g);

    hud.render(g);

    g.dispose();
    bs.show();
  }

  private static long sBothBins(int disX, int disY, int velX, int velY, int speedSmart, int speedPlayer) {
    //velX and velY are the speeds at which the distance is increasing on the part of the player.
    //-1 means -speedPlayer, +1 means +speedPlayer, 0 means 0. If both have 1 or -1, then they are sqrt speed player


    float distance = (float) Math.sqrt(disX * disX + disY * disY);

    //maybe more logical to do speedSmart, but safer to do speedPlayer. This will
    //ensure that if the player is moving towards the smartEnemy, the smartEnemy
    //will not move away from the player
    float halfTime = distance / (speedPlayer * 2);

    float velXF;
    float velYF;

    if(velX != 0) {
      if(velY != 0) {
        velXF = velX * (float)Math.sqrt(speedPlayer);
        velYF = velY * (float)Math.sqrt(speedPlayer);
      } else {
        velXF = speedPlayer * velX;
        velYF = 0;
      }
    } else {
      if(velY != 0) {
        velYF = velY * speedPlayer;
      } else {
        velYF = 0;
      }
      velXF = 0;
    }

    float newDisX = disX + halfTime * velXF;
    float newDisY = disY + halfTime * velYF;

    float slope = (float) newDisY  / (float) newDisX;
    float slopeInv = (float) newDisX / (float) newDisY;

    float sVelX = (float) speedSmart / (float) Math.sqrt(slope*slope + 1);
    float sVelY = (float) speedSmart / (float) Math.sqrt(slopeInv*slopeInv + 1);

    int sVelXBin = Float.floatToIntBits(sVelX);
    int sVelYBin = Float.floatToIntBits(sVelY);

    long sBothBins = ((long) sVelXBin) << 32 | (long) sVelYBin;

    return sBothBins;
  }

  private static long pBothBins(int disX, int disY, int velX, int velY) {
    int velXN = velX + 1;
    int velYN = velY + 1;

    long disVelXBin = (long) ((velXN << 20) | (long) disX);
    disVelXBin = disVelXBin << 22;
    long disVelYBin = (long) ((velYN << 20) | (long) disY);

    long pBothBins = disVelXBin | disVelYBin;
    return pBothBins;
  }

  public static void main(String[] args) {
    // try{
    //   PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(new File("smartVelocities.txt"), true)));
    //   for(int i = 0; i < 480; i++) {
    //     for(int j = 0; j < 640; j++) {
    //       for(int k = -1; k < 2; k++) {
    //         for(int l = -1; l < 2; l++) {
    //           long playerInfo = pBothBins(i, j, k, l);
    //           long smartInfo = sBothBins(i, j, k, l, 2, 3);
    //           String nextLine = "" + playerInfo + " " + smartInfo;
    //           pw.write(nextLine + "\n");
    //           pw.flush();
    //         }
    //       }
    //     }
    //   }
    // } catch(IOException e) {
    //   e.printStackTrace();
    //   System.exit(0);
    // }


    new Game();


  }
}
