package com.diegoaesparza.objects;

import com.diegoaesparza.collisions.*;
import com.diegoaesparza.objects.*;
import com.diegoaesparza.objects.enemy.*;
import com.diegoaesparza.objects.obstacle.*;
import com.diegoaesparza.objects.pickup.*;
import com.diegoaesparza.objects.player.*;
import com.diegoaesparza.objects.visual.*;

public abstract class Controller {
  // private int last;
  protected float accX;
  protected float accY;
  //allows controller to update acceleration when key is pressed, then update
  //player's acceleration when the turn comes around
  // protected HUD hud; //hudTerm

  public Controller() {

  }

  public abstract void update(GameObject object);

  public void jump() {}
  public void gravSwitch() {}
  public void blast() {}

  // public void giveHUD(HUD hud) { //hudTerm
  //   this.hud = hud;
  // }
}
