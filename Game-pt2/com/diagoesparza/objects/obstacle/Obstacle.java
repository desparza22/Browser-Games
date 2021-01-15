package com.diegoaesparza.objects.obstacle;

import com.diegoaesparza.*;
import com.diegoaesparza.collisions.*;
import com.diegoaesparza.objects.*;
import com.diegoaesparza.objects.enemy.*;
import com.diegoaesparza.objects.obstacle.*;
import com.diegoaesparza.objects.pickup.*;
import com.diegoaesparza.objects.player.*;
import com.diegoaesparza.objects.visual.*;

public class Obstacle extends GameObject {

  public Obstacle(float x, float y, Visual visual, Controller controller) {
    super(x, y, visual, controller);
  }

  public Obstacle(float x, float y, float velX, float velY, Visual visual, Controller controller, float mass) {
    super(x, y, velX, velY, visual, controller, mass);
  }

  public void tickCheck() {
    this.xPrime = this.x + this.velX - gameSpeedX;
    this.yPrime = this.y + this.velY;
    this.velXPrime = this.velX + this.accX + this.gravityX[this.state];
    this.velYPrime = this.velY + this.accY + this.gravityY[this.state];
    this.controller.update(this);
  }

  public void primeApply() {
    this.x += this.velXPrime - gameSpeedX;
    this.y += this.velYPrime;
    this.velX = this.velXPrime;
    this.velY = this.velYPrime;
    if(this.y < 0) {
      this.y = 0;
      this.velY *= -1;
    } else if(this.y > Game.height - height) {
      this.y = Game.height - height;
      this.velY *= -1;
    }
    shouldRemove();
 }

  @Override
  public String toString() {
    return "obstacle";
  }
}
