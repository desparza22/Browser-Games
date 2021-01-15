package com.diegoaesparza;

import com.diegoaesparza.*;
import com.diegoaesparza.collisions.*;
import com.diegoaesparza.objects.*;
import com.diegoaesparza.objects.enemy.*;
import com.diegoaesparza.objects.obstacle.*;
import com.diegoaesparza.objects.pickup.*;
import com.diegoaesparza.objects.player.*;
import com.diegoaesparza.objects.visual.*;

import java.awt.*;
import java.awt.image.*;
import java.util.Random;
import java.io.*;
import java.lang.Math;
import java.util.concurrent.Semaphore;
import java.lang.Thread;

public class Game extends Canvas implements Runnable {

  private int gameState = 0;

  public static final int width = 640, height = 480;
  //NOTE: WAIT!!!!! if width or height ever become not divisible by 40,
  //the parameters for the zone array in collisions need to have plus ones to
  //account for the remainder space

  private Environment environment;

  private Handler playerH;
  private Handler enemyH;
  private Handler pickupH;
  private Handler obstacleH;
  private Renderer playerR;
  private Renderer enemyR;
  private Renderer pickupR;
  private Renderer obstacleR;

  private PlayerHandler justSoItCompilesPlH;
  private EnemyHandler justSoItCompilesEH;
  private PickupHandler justSoItCompilesPiH;
  private ObstacleHandler justSoItCompilesOH;
  private PlayerRenderer justSoItCompilesPlR;
  private EnemyRenderer justSoItCompilesER;
  private PickupRenderer justSoItCompilesPiR;
  private ObstacleRenderer justSoItCompilesOR;
  private GameObject justSoItCompilesGO;
  private KeyInput justSoItCompilesKi;
  private Controller justSoItCompilesCon;
  private PlayerController justSoItCompilesPiCon;
  private EnemyController justSoItCompilesECon;
  private PickupController justSoItCompilesPlCon;
  private ObstacleController justSoItCompilesOCon;
  private Collision justSoItCompilesCol;
  private Zone justSoItCompilesZo;
  private Spawner justSoItCompilesSp;
  private PlayerSpawner justSoItCompilesPlS;
  private EnemySpawner justSoItCompilesES;
  private PickupSpawner justSoItCompilesPiS;
  private ObstacleSpawner justSoItCompilesOS;
  private Visual justSoItCompilesVi;
  private GameObject justSoItCompilesGamOb;
  private Player justSoItCompilesPlayer;
  private Enemy justSoItCompilesEnemy;
  private Pickup justSoItCompilesPickup;
  private Obstacle justSoItCompilesObstacle;
  private IDGen justSoItCompilesIDGen;
  private VerifySpawn justSoItCompilesVS;
  private VerifySpawn justSoItCompilesVS1;
  private Environment justSoItCompilesE;
  // private HUD justSoItCompilesHUD;

  private static Graphics currentG;

  private int state;

  // private Thread playerTickThread;
  // private Thread enemyTickThread;
  // private Thread pickupTickThread;
  // private Thread obstacleTickThread;
  private Thread playerRenderThread;
  private Thread enemyRenderThread;
  private Thread pickupRenderThread;
  private Thread obstacleRenderThread;
  private Thread gameThread;



  // private Semaphore threadNotifyPlayerH;
  // private Semaphore threadNotifyEnemyH;
  // private Semaphore threadNotifyPickupH;
  // private Semaphore threadNotifyObstacleH;
  // private Semaphore mainNotifyTick;
  private Semaphore threadNotifyPlayerR;
  private Semaphore threadNotifyEnemyR;
  private Semaphore threadNotifyPickupR;
  private Semaphore threadNotifyObstacleR;
  private Semaphore mainNotifyRender;

  private boolean running = true;

  public static volatile int round;

  public Game() {
    this.state = 0;

    this.playerH = new PlayerHandler(state);
    this.pickupH = new PickupHandler(state);
    this.enemyH = new EnemyHandler(state);
    this.obstacleH = new ObstacleHandler(state);

    this.playerR = new PlayerRenderer(this.playerH);
    this.enemyR = new EnemyRenderer(this.enemyH);
    this.pickupR = new PickupRenderer(this.pickupH);
    this.obstacleR = new ObstacleRenderer(this.obstacleH);

    Spawner playerSpawner = new PlayerSpawner(this.playerH);
    playerSpawner.giveListenerToGame(this);
    // this.blastHUD = playerSpawner.getHUD();
    this.playerH.giveSpawner(playerSpawner);
    Spawner enemySpawner = new EnemySpawner(this.enemyH);
    this.enemyH.giveSpawner(enemySpawner);
    Spawner obstacleSpawner = new ObstacleSpawner(this.obstacleH);
    this.obstacleH.giveSpawner(obstacleSpawner);
    Spawner pickupSpawner = new PickupSpawner(this.pickupH);
    this.pickupH.giveSpawner(pickupSpawner);

    // Collision collision = new Collision(width, height, 4);
    // this.playerH.giveCollision(collision);
    // this.enemyH.giveCollision(collision);
    // this.pickupH.giveCollision(collision);
    // this.obstacleH.giveCollision(collision);

    new Window(width, height, "running man", this);
  }

  public void start() {
    // this.playerTickThread = new Thread(this.playerH, "player tick thread");
    // this.pickupTickThread = new Thread(this.pickupH, "pickup tick thread");
    // this.enemyTickThread = new Thread(this.enemyH, "enemy tick thread");
    // this.obstacleTickThread = new Thread(this.obstacleH, "obstacle tick thread");

    this.playerRenderThread = new Thread(this.playerR, "player render thread");
    this.enemyRenderThread = new Thread(this.enemyR, "enemy render thread");
    this.pickupRenderThread = new Thread(this.pickupR, "pickup render thread");
    this.obstacleRenderThread = new Thread(this.obstacleR, "obstacle render thread");

    this.gameThread = new Thread(this, "game thread");

    this.gameThread.start();
  }

  public void run() {
    this.requestFocus();
    long lastTime = System.nanoTime();
    double amountOfTicks = 60.0; //ends up being num ticks per second
    double nanoSeconds = 1000000000 / amountOfTicks;
    double delta = 0;
    long timer = System.currentTimeMillis();
    int frames = 0;
    // try { //NOTE: haven't implemented this yet, but may be useful
    //   gameStart.acquire(); // makes sure that player speed has been added to KeyInput object
    // } catch(InterruptedException e) {
    //   e.printStackTrace();
    //   System.exit(0);
    // }


    // this.threadNotifyPlayerH = new Semaphore(0);
    // this.threadNotifyEnemyH = new Semaphore(0);
    // this.threadNotifyPickupH = new Semaphore(0);
    // this.threadNotifyObstacleH = new Semaphore(0);
    // this.mainNotifyTick = new Semaphore(0);
    this.threadNotifyPlayerR = new Semaphore(0);
    this.threadNotifyEnemyR = new Semaphore(0);
    this.threadNotifyPickupR = new Semaphore(0);
    this.threadNotifyObstacleR = new Semaphore(0);
    this.mainNotifyRender = new Semaphore(0);
    // this.playerH.giveSemaphoreThread(this.threadNotifyPlayerH);
    // this.pickupH.giveSemaphoreThread(this.threadNotifyEnemyH);
    // this.enemyH.giveSemaphoreThread(this.threadNotifyPickupH);
    // this.obstacleH.giveSemaphoreThread(this.threadNotifyObstacleH);
    // this.playerH.giveSemaphoreMain(this.mainNotifyTick);
    // this.pickupH.giveSemaphoreMain(this.mainNotifyTick);
    // this.enemyH.giveSemaphoreMain(this.mainNotifyTick);
    // this.obstacleH.giveSemaphoreMain(this.mainNotifyTick);
    this.playerR.giveSemaphoreThread(this.threadNotifyPlayerR);
    this.pickupR.giveSemaphoreThread(this.threadNotifyEnemyR);
    this.enemyR.giveSemaphoreThread(this.threadNotifyPickupR);
    this.obstacleR.giveSemaphoreThread(this.threadNotifyObstacleR);
    this.playerR.giveSemaphoreMain(this.mainNotifyRender);
    this.pickupR.giveSemaphoreMain(this.mainNotifyRender);
    this.enemyR.giveSemaphoreMain(this.mainNotifyRender);
    this.obstacleR.giveSemaphoreMain(this.mainNotifyRender);

    int numHandlers = 4;
    Collision collision = new Collision(this.width, this.height, numHandlers);
    // collision.giveSemaphoreThread(this.threadNotifyPlayerR);
    // collision.giveSemaphoreThread(this.threadNotifyEnemyR);
    // collision.giveSemaphoreThread(this.threadNotifyPickupR);
    // collision.giveSemaphoreThread(this.threadNotifyObstacleR);
    //width, height, num handler types. each handler type represents an object type
    this.playerH.giveCollision(collision); //must go after giving handler to
    this.enemyH.giveCollision(collision);
    this.pickupH.giveCollision(collision);
    this.obstacleH.giveCollision(collision);

    // this.playerTickThread.start();
    // this.enemyTickThread.start();
    // this.pickupTickThread.start();
    // this.obstacleTickThread.start();
    this.playerRenderThread.start();
    this.enemyRenderThread.start();
    this.pickupRenderThread.start();
    this.obstacleRenderThread.start();

    // System.out.println("got here");
    this.createBufferStrategy(3);
    while(running) {
      // System.out.println("got here");
      long now = System.nanoTime();
      // System.out.println(now);
      delta += (now - lastTime) / nanoSeconds;
      lastTime = now;
      if(delta >= 1) {
        // tick(); //replace with something along the lines of:
        int deltaInt = (int) delta;
        this.playerR.updateDelta(deltaInt);
        this.enemyR.updateDelta(deltaInt);
        this.pickupR.updateDelta(deltaInt);
        this.obstacleR.updateDelta(deltaInt);
        delta -= deltaInt;
      }
      // if(running) { //do we need if running?
        //render(); //replace with something along the lines of
      BufferStrategy bs = this.getBufferStrategy();
      // System.out.println(bs);
      // System.out.println(bs);
      // bs.getDrawGraphics();
      currentG = bs.getDrawGraphics();
      currentG.setColor(Color.white);
      currentG.fillRect(0,0,width,height);

      // System.out.println("got here");
      this.threadNotifyPlayerR.release();
      this.threadNotifyEnemyR.release();
      this.threadNotifyPickupR.release();
      this.threadNotifyObstacleR.release();
        // System.out.println("got here");
      try{
        this.mainNotifyRender.acquire(4);
      } catch(InterruptedException e) {
        e.printStackTrace();
        System.exit(0);
      }
      // System.out.println("got here");
      currentG.dispose();
      bs.show();
      // System.out.println(running);
    }
    stop();
  }

  public static Graphics currentG() {
    return currentG;
  }

  public synchronized void stop() {
    try{
      this.gameThread.join();
      this.running = false;
    } catch(Exception e) {
      e.printStackTrace();
    }
    // this.playerH.stop();
    // this.enemyH.stop();
    // this.pickupH.stop();
    // this.obstacleH.stop();
    this.playerR.stop();
    this.enemyR.stop();
    this.pickupR.stop();
    this.obstacleR.stop();
  }

  private void setEnvironment() {
    //all of the customizable aspects of the environment are set here
    VerifySpawn verifySpawn1 = new VerifySpawn1();
    this.environment = new Environment(0f, 0.001f, createVisual(), createSpawners(), verifySpawn1);
  }

  private Visual[] createVisual() {
    //pass visual init parameters
    Visual[] visuals = new Visual[4];
    int[][] playerWhich = new int[][] {{0,1,2,3},{4,5,6,7}};
    int[][] playerCounts = new int[][] {{1,1,1,1},{1,1,1,1}};
    int playerDivisor = 3;
    int playerShiftX = 6;
    int playerWidth = 20;
    int playerShiftY = 3;
    int playerHeight = 27;
    visuals[0] = new Visual("com/diegoaesparza/objects/visual/skiier32", playerWhich, playerCounts, playerDivisor, playerShiftX, playerShiftY, playerWidth, playerHeight);
    int[][] enemyWhich = new int[][] {{0,1}};
    int[][] enemyCounts = new int[][] {{1,1}};
    int enemyDivisor = 8;
    int enemyShiftX = 0;
    int enemyShiftY = 0;
    int enemyWidth = 0;
    int enemyHeight = 0;
    visuals[1] = new Visual("com/diegoaesparza/objects/visual/pacman32", enemyWhich, enemyCounts, enemyDivisor, enemyShiftX, enemyShiftY, enemyWidth, enemyHeight);
    int[][] pickupWhich = new int[][] {{0,1,2,3}};
    int[][] pickupCounts = new int[][] {1,1,1,1};
    int pickupDivisor = 5;
    int pickupShiftX = 10;
    int pickupShiftY = 10;
    int pickupWidth = 12;
    int pickupHeight = 12;
    visuals[2] = new Visual("com/diegoaesparza/objects/visual/present32", pickupWhich, pickupCounts, pickupDivisor, pickupShiftX, pickupShiftY, pickupWidth, pickupHeight);
    int[][] obstacleWhich = new int[][] {{0}, {1}, {2}};
    int[][] obstacleCounts = new int[][] {{1}, {1}, {1}};
    int obstacleDivisor = 20;
    int obstacleShiftX = 6;
    int obstacleShiftY = 10;
    int obstacleWidth = 20;
    int obstacleHeight = 22;
    visuals[3] = new Visual("com/diegoaesparza/objects/visual/tree32", obstacleWhich, obstacleCounts, obstacleDivisor, obstacleShiftX, obstacleShiftY, obstacleWidth, obstacleHeight);

    return visuals;
  }

  private Spawner[] createSpawner() {
    //might want to generalize spawner class, except maybe useful to have special
    //spawner for player. maybe player shouldn't be a spawner, or even it's own thread
    Spawner[] spawners = new Spawner[4];
    spawners[0] = new PlayerSpawner();
    spawners[1] = new EnemySpawner();
    spawners[2] = new PickupSpawner();
    spawners[3] = new ObstacleSpawner();
    return spawners;
  }

  public static void main(String[] args) {
    new Game();
  }

}
