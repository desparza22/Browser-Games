
import java.awt.*;
import java.awt.image.*;

public abstract class GameObject {

  protected float x, y;
  protected ID id;
  protected float velX, velY;
  public boolean removed = false;
  private int speed = 6;
  // protected Handler handler;

  public GameObject(float x, float y, ID id) {
    this.x = x;
    this.y = y;
    this.id = id;
    // this.handler = handler;
  }

  public abstract void tick();
  public abstract void render(Graphics g);
  public abstract Rectangle getBounds();

  public void setX(float x) {
    this.x = x;
  }

  public int getSpeed() {
    return speed;
  }

  public void setY(float y) {
    this.y = y;
  }

  public float getX() {
    return this.x;
  }

  public float getY() {
    return this.y;
  }

  public void setId(ID id) {
    this.id = id;
  }

  public ID getId() {
    return this.id;
  }

  public void setVelX(float velX) {
    this.velX = velX;
  }

  public void setVelY(float velY) {
    this.velY = velY;
  }

  public float getVelX() {
    return this.velX;
  }

  public float getVelY() {
    return this.velY;
  }

  public void incrementVelX(float increment) {
    this.velX += increment;
  }

  public void incrementVelY(float increment) {
    this.velY += increment;
  }
}
