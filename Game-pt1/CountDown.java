
import java.awt.*;

public class CountDown extends GameObject {

  private Handler handler;
  private GameObject toSpawn;
  private long milliseconds;
  private long timeCreated;
  private Color color;
  private int height = 16;
  private int width = 16;
  private String seconds;
  private int secondsInt;
  // private int seconds;

  public CountDown(float x, float y, ID id, Handler handler, GameObject toSpawn, long milliseconds) {
    super(x, y, id);
    this.id = id;
    this.x = toSpawn.getX();
    this.y = toSpawn.getY();
    this.handler = handler;
    this.toSpawn = toSpawn;
    this.milliseconds = milliseconds;
    this.timeCreated = System.currentTimeMillis();
    if(toSpawn.getId() == ID.SmartEnemy) {
      this.color = Color.blue;
    } else if(toSpawn.getId() == ID.KeyScramble) {
      this.color = Color.white;
    } else {
      this.color = Color.red;
    }
    this.secondsInt = (int) (milliseconds / 1000);
    this.seconds = this.secondsInt + "";

  }

  public void tick() {
    long currentTime = System.currentTimeMillis();
    if(((((int) (this.milliseconds - currentTime + this.timeCreated)) / 1000) + 1) != secondsInt) {
      secondsInt = ((int) (this.milliseconds - currentTime + this.timeCreated) / 1000) + 1;
      if(secondsInt < 1) {
        seconds = secondsInt + "";
        this.removed = true;
        handler.addObject(toSpawn);
      } else {
        seconds = secondsInt + "";
      }
    }
  }

  public void render(Graphics g) {
    g.setColor(this.color);
    g.fillRect((int)x, (int)y, width, height);
    g.setColor(Color.white);
    g.drawString(seconds, (int)x + 8, (int)y + 8);
  }

  public Rectangle getBounds() {
    return new Rectangle((int)x, (int)y, width, height);
  }
}
