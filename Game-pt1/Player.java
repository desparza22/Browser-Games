
import java.awt.*;
import java.util.Random;
import java.util.Iterator;

public class Player extends GameObject {

  Random r = new Random();
  Handler handler;
  public static final int HEIGHT = 32;
  public static final int WIDTH = 32;
  private int speed = 6;

  public Player(float x, float y, ID id, Handler handler) {
    super(x, y, id);

    this.handler = handler;
    //
    // velX = r.nextInt(6) - 3;
    // velY = r.nextInt(6) - 3;
    // HEIGHT = r.nextInt(32) + 1;
    // WIDTH = HEIGHT;
  }

  public Rectangle getBounds() {
    return new Rectangle((int)x, (int)y, WIDTH, HEIGHT);
  }



  public void tick() {
    // System.out.println("got here");
    // velX = r.nextInt(7) - 3;
    // velY = r.nextInt(7) - 3;
    // System.out.println("getting to player tick");
    //NOTE: 2 and 24 were guess and check
    if(x + velX >= 2 && x + velX <= Game.WIDTH - WIDTH) {
      x += velX;
    }
    if(y + velY >= 0 && y + velY <= Game.HEIGHT - HEIGHT - 24) {
      y += velY;
    }

    collision();

  }

  private void collision() {
    Iterator i = handler.objectI();
    while(i.hasNext()) {
      GameObject current = (GameObject) i.next();
      if(current.getId() == ID.BasicEnemy || current.getId() == ID.FastEnemy || current.getId() == ID.SmartEnemy) {
        if(getBounds().intersects(current.getBounds())) {
          HUD.HEALTH -= 5;
          current.removed = true;
        }
      }
      if(current.getId() == ID.KeyScramble) {
        if(getBounds().intersects(current.getBounds())) {
          KeyScramble currentS = (KeyScramble) current;
          currentS.scramble();
          currentS.removed = true;
        }
      }
    }
  }

  // public void incrementVelY(int increment) {
  //   this.velY += increment;
  // }
  //
  // public void incrementVelX(int increment) {
  //   this.velX += increment;
  // }



  public void render(Graphics g) {
    if(this.id == ID.Player) {
      g.setColor(Color.orange);
    }
    // g.setColor(Color.white);
    g.fillRect((int)x,(int)y,WIDTH,HEIGHT);
    g.setColor(Color.yellow);
    g.fillRect((int)x, (int)y - 8, 52, 20);
    g.setColor(Color.black);
    g.fillRect((int)x + 25, (int) y + 16, 6, 6);
    g.setColor(Color.red);
    g.fillRect((int)x + 16, (int)y + 26, 16, 4);
  }
}
