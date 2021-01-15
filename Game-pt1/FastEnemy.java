// import java.awt.Color;
// import java.awt.Graphics;
import java.awt.*;
import java.util.Random;
import java.util.Iterator;

public class FastEnemy extends GameObject {

  private Handler handler;
  public static final int WIDTH = 16;
  public static final int HEIGHT = 16;

  private Random r;

  public FastEnemy(float x, float y, ID id, Handler handler) {
    super(x, y, id);
    this.handler = handler;

    velX = 2;
    velY = 9;
    r = new Random();


  }

  public Rectangle getBounds() {
    return new Rectangle((int)x, (int)y, WIDTH, HEIGHT);
  }

  public void tick() {
    if(y + velY < 0 || y + velY > Game.HEIGHT - (HEIGHT + HEIGHT / 2)) {
      velY *= -1;
      if(x + velX < 0 || x + velX > Game.WIDTH - WIDTH) {
        velX *= -1;
        // velY = r.nextInt(6) - 3;
      } else {
        velX = r.nextInt(5) - 2;
      }
    } else if(x + velX < 0 || x + velX > Game.WIDTH - WIDTH) {
      velX *= -1;
      velY = r.nextInt(19) - 9;
    }


    x += velX;
    y += velY;

    Trail trail = new Trail(x, y, ID.Trail, Color.red, WIDTH, HEIGHT, 0.2f, handler);
    // System.out.println("printing in basic enemy" + trail);

    handler.addTrail((GameObject) trail);

    collision();
  }

  private void collision() {
    Iterator i = handler.objectI();
    while(i.hasNext()) {
      GameObject current = (GameObject) i.next();
      if(current != this) {
        if(current.getId() == ID.BasicEnemy || current.getId() == ID.FastEnemy || current.getId() == ID.SmartEnemy) {
          if(getBounds().intersects(current.getBounds())) {
            HUD.HEALTH += 1;
            current.removed = true;
            this.removed = true;
            GameObject collidyOne = new Collision(x, y, ID.Collision, handler, WIDTH / 8, HEIGHT / 8, 98, r.nextInt(8), 3, Color.red);
            GameObject collidyTwo = new Collision(x, y, ID.Collision, handler, WIDTH / 8, HEIGHT / 8, 98, r.nextInt(8), 3, Color.red);
            handler.addCollision(collidyOne);
            handler.addCollision(collidyTwo);
          }
        }
      }
    }
  }

  public void render(Graphics g) {
    g.setColor(Color.red);
    g.fillRect((int)x, (int)y, WIDTH, HEIGHT);
  }
}
