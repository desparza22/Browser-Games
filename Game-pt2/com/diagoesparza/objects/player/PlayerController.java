package com.diegoaesparza.objects.player;

import com.diegoaesparza.*;
import com.diegoaesparza.collisions.*;
import com.diegoaesparza.objects.*;
import com.diegoaesparza.objects.enemy.*;
import com.diegoaesparza.objects.obstacle.*;
import com.diegoaesparza.objects.pickup.*;
import com.diegoaesparza.objects.player.*;
import com.diegoaesparza.objects.visual.*;

public class PlayerController extends Controller {

  private int gravSwitchY = 1;
  private int direction = 1; // jump direction
  private int dashCoolDown = 0;
  private int blastCoolDown = 0; //initial cool down values

  public PlayerController() {
  }

  public void update(GameObject object) {
    object.setAccX(this.accX);
    object.setAccY(this.accY);
    this.accX = 0;
    this.accY = 0;
    object.multiplyGravY(this.gravSwitchY);
    this.gravSwitchY = 1;
    this.dashCoolDown--;
    this.blastCoolDown--;
  }

  public void gravSwitch() {
    this.gravSwitchY = -1;
    this.direction *= -1;
  }

  public void jump() {
    if(this.dashCoolDown <= 0) {
      this.accY = 23f * this.direction;
      this.dashCoolDown = 20;
    }
  }

  public void blast() {
    if(this.blastCoolDown <= 0) {
      //blast function
      this.blastCoolDown = 1000;
    }
  }
}
