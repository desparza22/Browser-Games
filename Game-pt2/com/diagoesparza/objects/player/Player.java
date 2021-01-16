package com.diegoaesparza.objects.player;

import com.diegoaesparza.*;
import com.diegoaesparza.collisions.*;
import com.diegoaesparza.objects.*;
import com.diegoaesparza.objects.enemy.*;
import com.diegoaesparza.objects.obstacle.*;
import com.diegoaesparza.objects.pickup.*;
import com.diegoaesparza.objects.player.*;
import com.diegoaesparza.objects.visual.*;


public class Player extends GameObject {
  private float maxSpeed;

  public Player(float x, float y, Visual visual, Controller controller) {
    super(x, y, visual, controller);
    this.gravityX[0] = 0.001f;
    this.gravityY[0] = -0.2f;
  }
  public Player(float x, float y, float velX, float velY, Visual visual, Controller controller, float mass) {
    super(x, y, velX, velY, visual, controller, mass);
    this.gravityX[0] = 0.001f;
    this.gravityY[0] = -0.2f;
  }
  public Player(float x, float y, float velX, float velY, float gravityX, float gravityY, Visual visual, Controller controller, float mass) {
    super(x, y, velX, velY, visual, controller, mass);
    this.gravityX[0] = gravityX;
    this.gravityY[0] = gravityY;
  }

  public void tickCheck() {
    System.out.println(this.velX);//searchTerm
    this.xPrime = this.x;
    this.yPrime = this.y + this.velY;
    this.velXPrime = this.velX + this.accX + this.gravityX[this.state];
    this.velYPrime = this.velY + this.accY + this.gravityY[this.state];
    float cap = 3f;
    if(this.velYPrime < -cap) {
      this.velYPrime -= (this.velYPrime + cap)/4f;
    } else if(this.velYPrime > cap) {
      this.velYPrime -= (this.velYPrime - cap)/4f;
    }
    this.velYPrime += this.accY;
    // System.out.println(this.gravityY[this.state]);
    this.controller.update(this);
  }

  public void primeApply() {
    gameSpeedX = this.velXPrime;
    this.y += this.velYPrime;
    this.velX = this.velXPrime;
    this.velY = this.velYPrime;
    if(this.velX < this.maxSpeed) {
      this.accX = 0.05f;
    } else {
      this.accX = 0f;
      this.maxSpeed = this.velX;
    }
    if(this.y < 0) {
      this.y = 0;
      this.velY *= -1;
    } else if(this.y > Game.height - height) {
      this.y = Game.height - height;
      this.velY *= -1;
    }
  }

  @Override
  public String toString() {
    return "player";
  }
}
