import java.awt.Graphics;
import java.awt.Color;
import java.lang.Math;

public class HUD {

  public static int HEALTH = 100;

  private int greenValue = 255;

  private int score = 0;
  private int level = 1;


  public void tick() {
    // if(HEALTH > 0) {
    //   // HEALTH--;
    // }
    // HEALTH--;
    score++;
    if(HEALTH < 0) {
      HEALTH = 0;
    }
    if(HEALTH > 100) {
      HEALTH = 100;
    }
    greenValue = HEALTH * 2 + HEALTH / 2;
  }

  public void render(Graphics g) {
    g.setColor(Color.gray);
    g.fillRect(15, 15, 200, 32);
    g.setColor(new Color(255 - greenValue, greenValue, 0));
    g.fillRect(15, 15, HEALTH * 2, 32);
    g.setColor(Color.white);
    g.drawRect(15, 15, 200, 32);

    g.drawString("Score: " + score, 15, 66);
    g.drawString("Level: " + level, 15, 82);
  }

  public void setScore(int score) {
    this.score = score;
  }

  public int getScore() {
    return this.score;
  }

  public int getLevel() {
    return this.level;
  }

  public void setLevel(int level) {
    this.level = level;
  }

}
