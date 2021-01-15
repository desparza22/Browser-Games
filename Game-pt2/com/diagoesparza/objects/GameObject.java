package com.diegoaesparza.objects;

import com.diegoaesparza.*;
import com.diegoaesparza.collisions.*;
import com.diegoaesparza.objects.*;
import com.diegoaesparza.objects.enemy.*;
import com.diegoaesparza.objects.obstacle.*;
import com.diegoaesparza.objects.pickup.*;
import com.diegoaesparza.objects.player.*;
import com.diegoaesparza.objects.visual.*;

import java.awt.*;
// import javax.imageio.ImageIO;
import java.io.*;
import java.awt.image.BufferedImage;

public abstract class GameObject {

  protected float x, y;
  protected float velX, velY, accX, accY;
  protected float xPrime, yPrime;
  protected float velXPrime, velYPrime, accXPrime, accYPrime;
  protected int width, height;
  protected float mass; //centered around 10 where 10 is player
  // protected float
  protected int state;

  //this should maybe be customizable, no need for objects to have state types,
  //rather their parameters and maybe their controller classes change
  protected float[] gravityX = new float[1];
  protected float[] gravityY = new float[1];
  // protected BufferedImage[] icons;
  protected boolean removed = false;
  //specify the default gravity values for each state
  protected Visual visual;

  // protected int coolDown;
  //could be useful for jumping functions, or other actions
  // protected boolean indicator;
  //good for demonstrating that an enemy has reached a certain part of the
  //screen and their acceleration can continue decreasing

  protected Controller controller;
  // protected int coolDown; //for example, don't cap player when jumping

  public static float gameSpeedX;//always update to the player's velcity,
  //other objects will position themselves in relation to player

  protected int idCode;

  protected static IDGen objectIDs = new IDGen(100);

  protected boolean blaster = false;


  public GameObject(float x, float y, Visual visual, Controller controller) {
    this.x = x;
    this.y = y;
    this.visual = visual;
    this.width = visual.width();
    this.height = visual.height();
    this.controller = controller;
    objectIDs.assignID(this);
    // this.state = 0;
  }

  public GameObject(float x, float y, float velX, float velY, Visual visual, Controller controller, float mass) {
    this.x = x;
    this.y = y;
    this.velX = velX;
    this.velY = velY;
    this.visual = visual;
    this.width = visual.width();
    this.height = visual.height();
    this.controller = controller;
    this.mass = mass;
    objectIDs.assignID(this);
    // this.state = 0;
  }

  public GameObject(float x, float y, Visual visual) {
    this.x = x;
    this.y = y;
    this.visual = visual;
    this.blaster = true;
  }

  // public BufferedImage getIcon(int time) {
  //   return icons[timeToIndex(time)];
  // }

  public void remove() {
    this.removed = true;
    objectIDs.returnObject(this);
  }

  public int idCode() {
    return this.idCode;
  }

  public void setID(int id) {
    this.idCode = id;
  }

  public boolean removed() {
    return this.removed;
  }

  public int left() {
    return (int) this.xPrime;
  }
  public int right() {
    return (int) this.xPrime + this.width;
  }
  public int top() {
    return (int) this.yPrime;
  }
  public int bottom() {
    return (int) this.yPrime + this.height;
  }

  public BufferedImage image(int time) {
    return this.visual.image(time);
  }
  public int xRen() {
    return this.visual.xRen((int) this.x);
  } //gives the x position for rendering
  public int yRen() {
    return this.visual.yRen((int) this.y);
  } //gives the y position for rendering


  // protected abstract int timeToIndex(int time);

  public abstract void tickCheck();

  public float mass() {
    return this.mass;
  }

  // public float xPrime() {
  //   return this.xPrime;
  // }
  //
  // public float yPrime() {
  //   return this.yPrime;
  // }

  public float velXPrime() {
    return this.velXPrime;
  }

  public float velYPrime() {
    return this.velYPrime;
  }

  public void setXPrime(float xPrime) {
    this.xPrime = xPrime;
  }

  public void setYPrime(float yPrime) {
    this.yPrime = yPrime;
  }

  public void setVelXPrime(float velXPrime) {
    this.velXPrime = velXPrime;
  }

  public void setVelYPrime(float velYPrime) {
    this.velYPrime = velYPrime;
  }

  public float accX() {
    return this.accX;
  }

  public float accY() {
    return this.accY;
  }

  public void setAccX(float accX) {
    this.accX = accX;
  }

  public void setAccY(float accY) {
    this.accY = accY;
  }

  public void setAccXPrime(float accXPrime) {
    this.accXPrime = accXPrime;
  }

  public void setAccYPrime(float accYPrime) {
    this.accYPrime = accYPrime;
  }

  public void setGravX(float gravY) {
    this.gravityY[this.state] = gravY;
  }

  public void setGravY(float gravX) {
    this.gravityX[this.state] = gravX;
  }

  public void multiplyGravX(int multiplier) {
    this.gravityX[this.state] *= multiplier;
  }

  public void multiplyGravY(int multiplier) {
    this.gravityY[this.state] *= multiplier;
  }

  public abstract void primeApply();

  protected void shouldRemove() {
    if(this.x < 0 - 4*this.width || this.x > Game.width + 4*this.width) {
      this.removed = true;
    }
  }

  // public abstract String toString();
}
