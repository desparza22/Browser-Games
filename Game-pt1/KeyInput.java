import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Iterator;
import java.util.Random;

public class KeyInput extends KeyAdapter {

  private Handler handler;
  private int[] keyDown = new int[4];
  private int xDirection; // -1, 0, 1
  // private int xPressed;
  private int yDirection; //yDirection * yDirection = 1 if pressed, 0 otherwise
  // private int yPressed;
  private float[][] playerVelocities = new float[2][3];

  private int playerSpeed;

  private int keyScramble = 0; // out of 5
  private int keyShift = 0; //out of 3

  private final int[][] keyMap =
    new int[][]{{KeyEvent.VK_W,KeyEvent.VK_S,KeyEvent.VK_A,KeyEvent.VK_D},
                {KeyEvent.VK_W,KeyEvent.VK_S,KeyEvent.VK_D,KeyEvent.VK_A},
                {KeyEvent.VK_W,KeyEvent.VK_A,KeyEvent.VK_D,KeyEvent.VK_S},
                {KeyEvent.VK_W,KeyEvent.VK_D,KeyEvent.VK_A,KeyEvent.VK_S},
                {KeyEvent.VK_W,KeyEvent.VK_D,KeyEvent.VK_S,KeyEvent.VK_A},
                {KeyEvent.VK_W,KeyEvent.VK_A,KeyEvent.VK_S,KeyEvent.VK_D}};
  private int[] currentMap = keyMap[0];

  private Random r;
  private GameObject player;

  public KeyInput(Handler handler) {
    this.handler = handler;
    // for(int i = 0; i < 4; i++) {
    //   keyDown[i] = false;
    // }
    // int playerSpeed = 3;

    // System.out.println(playerSpeed);
    this.r = new Random();
  }

  public void addPlayerSpeed(int speed) {
    // System.out.println(speed);
    this.playerSpeed = speed;
    // System.out.println("ps " + this.playerSpeed);
    playerVelocities[1][0] = -2 * (float) Math.sqrt(playerSpeed); //negative and other moving
    playerVelocities[1][1] = 0f; //not moving on this axis
    playerVelocities[1][2] = 2 * (float) Math.sqrt(playerSpeed); //positive and other moving
    playerVelocities[0][0] = -1 * (float)playerSpeed; //negative and other not moving
    playerVelocities[0][1] = 0f; //not moving on either
    playerVelocities[0][2] = (float)playerSpeed;
  }

  public void addPlayer(GameObject player) {
    this.player = player;
  }

  public void scramble() {
    this.keyScramble = r.nextInt(5) + 1; //don't want it to be original keys
    this.keyShift = r.nextInt(4);
    this.currentMap = this.keyMap[keyScramble];
    this.player.setVelX(0);
    this.player.setVelY(0);
    if(yDirection != 0) {
      keyPressed((yDirection + 1) / 2);
    }
    if(xDirection != 0) {
      keyPressed((5 + xDirection) / 2);
    }
  }

  public void unscramble() {
    this.currentMap = this.keyMap[0];
    this.keyShift = 0;
    this.player.setVelX(0);
    this.player.setVelY(0);
    if(yDirection != 0) {
      keyPressed((yDirection + 1) / 2);
    }
    if(xDirection != 0) {
      keyPressed((5 + xDirection) / 2);
    }
  }

  public void keyPressed(KeyEvent e) {
    int key = e.getKeyCode();

    // Iterator i = handler.objectI();
    // while(i.hasNext()) {
      // GameObject current = (GameObject) i.next();

      // if(current.getId() == ID.Player) {
        //key events for player 1
        // current = (Player) current;

    if(key == currentMap[this.keyShift]) {
      keyDown[0] = 1;
      yDirection = -1;
      player.setVelY(playerVelocities[xDirection * xDirection][yDirection + 1]);
      player.setVelX(playerVelocities[yDirection * yDirection][xDirection + 1]);
      // System.out.println(playerVelocities[0][0]);
      // System.out.println()
    } else if(key == currentMap[(this.keyShift + 1) % 4]) {
      keyDown[1] = 1;
      yDirection = 1;
      player.setVelY(playerVelocities[xDirection * xDirection][yDirection + 1]);
      player.setVelX(playerVelocities[yDirection * yDirection][xDirection + 1]);
    } else if(key == currentMap[(this.keyShift + 2) % 4]) {
      keyDown[2] = 1;
      xDirection = -1;
      player.setVelX(playerVelocities[yDirection * yDirection][xDirection + 1]);
      player.setVelY(playerVelocities[xDirection * xDirection][yDirection + 1]);
    } else if(key == currentMap[(this.keyShift + 3) % 4]) {
      keyDown[3] = 1;
      xDirection = 1;
      player.setVelX(playerVelocities[yDirection * yDirection][xDirection + 1]);
      player.setVelY(playerVelocities[xDirection * xDirection][yDirection + 1]);
    }
    // System.out.println(key);
  }

  public void keyReleased(KeyEvent e) {
    int key = e.getKeyCode();

      // GameObject current = (GameObject) i.next();
      // if(current.getId() == ID.Player) {
        //key events for player 1
        // current = (Player) current;

    if(key == currentMap[(this.keyShift + 0) % 4]) {
      keyDown[0] = 0;
      yDirection = keyDown[1];
      player.setVelX(playerVelocities[yDirection * yDirection][xDirection + 1]);
      player.setVelY(playerVelocities[xDirection * xDirection][yDirection + 1]);
    } else if(key == currentMap[(this.keyShift + 1) % 4]) {
      keyDown[1] = 0;
      yDirection = -1 * keyDown[0];
      player.setVelX(playerVelocities[yDirection * yDirection][xDirection + 1]);
      player.setVelY(playerVelocities[xDirection * xDirection][yDirection + 1]);
    } else if(key == currentMap[(this.keyShift + 2) % 4]) {
      keyDown[2] = 0;
      xDirection = keyDown[3];
      player.setVelX(playerVelocities[yDirection * yDirection][xDirection + 1]);
      player.setVelY(playerVelocities[xDirection * xDirection][yDirection + 1]);
    } else if(key == currentMap[(this.keyShift + 3) % 4]) {
      keyDown[3] = 0;
      xDirection = -1 * keyDown[2];
      player.setVelX(playerVelocities[yDirection * yDirection][xDirection + 1]);
      player.setVelY(playerVelocities[xDirection * xDirection][yDirection + 1]);
    }
  }

  public void keyPressed(int direction) {
    int key = currentMap[(this.keyShift + direction) % 4];
    // int key = e.getKeyCode();

    // Iterator i = handler.objectI();
    // while(i.hasNext()) {
      // GameObject current = (GameObject) i.next();

      // if(current.getId() == ID.Player) {
        //key events for player 1
        // current = (Player) current;

    if(key == KeyEvent.VK_W) {
      keyDown[0] = 1;
      yDirection = -1;
      player.setVelY(playerVelocities[xDirection * xDirection][yDirection + 1]);
      player.setVelX(playerVelocities[yDirection * yDirection][xDirection + 1]);
      // System.out.println(playerVelocities[0][0]);
      // System.out.println()
    } else if(key == KeyEvent.VK_S) {
      keyDown[1] = 1;
      yDirection = 1;
      player.setVelY(playerVelocities[xDirection * xDirection][yDirection + 1]);
      player.setVelX(playerVelocities[yDirection * yDirection][xDirection + 1]);
    } else if(key == KeyEvent.VK_A) {
      keyDown[2] = 1;
      xDirection = -1;
      player.setVelX(playerVelocities[yDirection * yDirection][xDirection + 1]);
      player.setVelY(playerVelocities[xDirection * xDirection][yDirection + 1]);
    } else if(key == KeyEvent.VK_D) {
      keyDown[3] = 1;
      xDirection = 1;
      player.setVelX(playerVelocities[yDirection * yDirection][xDirection + 1]);
      player.setVelY(playerVelocities[xDirection * xDirection][yDirection + 1]);
    }
    // System.out.println(key);
  }
}
