
import java.awt.*;

public class KeyScramble extends GameObject {

  private int newUp;
  private int newDown;
  private int newLeft;
  private int newRight;
  private int turns = 0;
  private int height = 16;
  private int width = 16;
  private final Color[] colors = new Color[] {Color.blue, Color.green, Color.red, Color.pink, Color.cyan};
  private Handler handler;

  public KeyScramble(float x, float y, ID id, Handler handler) {
    super(x, y, id);
    this.handler = handler;
  }

  public void tick() {
    // collision();
    turns++;
  }

  public void render(Graphics g) {
    g.setColor(Color.white);
    g.fillRect((int)x, (int)y, 10, 10);
    g.fillRect((int)x+10, (int)y, 10, 10);
    g.fillRect((int)x+5, (int)y+10, 10, 10);
    g.setColor(colors[(turns/30) % 5]);
    Graphics2D g2 = (Graphics2D) g;
    Stroke oldStroke = g2.getStroke();
    g2.setStroke(new BasicStroke(4));
    g2.drawRect((int)x, (int)y, 10, 10);
    g2.drawRect((int)x+10, (int)y, 10, 10);
    g2.drawRect((int)x+5,(int)y+10, 10, 10);
    g2.setStroke(oldStroke);
  }

  // public void collision() {
  //
  // }

  public Rectangle getBounds() {
    return new Rectangle((int)x, (int)y, width, height);
  }

  public void scramble() {
    this.handler.scramble();
  }
}
