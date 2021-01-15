
import java.util.Random;
import java.awt.*;

public class Collision extends GameObject {

  private int width;
  private int height;
  private Handler handler;
  private int level;
  private Random r;
  private Color color;
  private int directionIndex;
  private final float[][] speeds = new float[][]{{1f,0f}, {-1f,0f}, {0f,1f}, {0f,-1f},
  {-1*(float)Math.sqrt(2),(float)Math.sqrt(2)}, {(float)Math.sqrt(2),-1*(float)Math.sqrt(2)}, {-1*(float)Math.sqrt(2),-1*(float)Math.sqrt(2)},
  {(float)Math.sqrt(2),(float)Math.sqrt(2)}};
  private int velocity;
  private int zeroSeven;
  // private int xSpeed;
  // private int ySpeed;

  public Collision(float x, float y, ID id, Handler handler, int width, int height, int level, int zeroSeven, int velocity, Color color) {
    super(x, y, id);
    this.width = width;
    this.height = height;
    this.level = level;
    // this.velX = xSpeed;
    // this.velY = ySpeed;
    this.r = new Random();
    this.handler = handler;
    this.velocity = velocity;
    this.zeroSeven = zeroSeven;
  }

  public void tick() {
    // System.out.println("got here");
    this.x += speeds[zeroSeven][0];
    this.y += speeds[zeroSeven][1];
    // System.out.println("got here 2");
    if(this.level % 20 == 0) {
      // System.out.println("got here 3");
      GameObject collidyOne =
        new Collision(x + r.nextInt(width) - width,
        y + r.nextInt(height) - height, ID.Collision,
        handler, width, height, level - 1,
        r.nextInt(8), velocity, this.color);
      // System.out.println
      // GameObject collidyTwo =
      //   new Collision(x + r.nextInt(width) - width,
      //   y + r.nextInt(height) - height, ID.Collision,
      //   handler, width*level, height*level/, level - 1,
      //   r.nextInt(8), velocity, this.color);
      //
      // System.out.println(collidyOne);
      // System.out.println(handler);
      this.handler.addCollision(collidyOne);
      // this.handler.addCollision(collidyTwo);
      // this.removed = true;
    } else if(level == 1){
      //
      //
      //
      this.removed = true;
    }
    // System.out.println(this.level);
    this.level--;
    // if(this.level == 0) {
    //   this.removed = true;
    // }
  }

  public void render(Graphics g) {
    g.setColor(this.color);
    g.fillRect((int)x, (int)y, width * level/10, height * level/10);
  }

  public Rectangle getBounds() {
    return new Rectangle((int)x, (int)y, width, height);
  }

  @Override
  public String toString() {
    return "colliiisssion";
  }
}
