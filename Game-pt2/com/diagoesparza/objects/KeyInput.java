package com.diegoaesparza.objects;

import com.diegoaesparza.collisions.*;
import com.diegoaesparza.objects.*;
import com.diegoaesparza.objects.enemy.*;
import com.diegoaesparza.objects.obstacle.*;
import com.diegoaesparza.objects.pickup.*;
import com.diegoaesparza.objects.player.*;
import com.diegoaesparza.objects.visual.*;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter {
  private int jump;
  private int blast;
  private boolean jumpReleased = true;
  private boolean blastReleased = true;
  private Controller controller;

  public KeyInput(int jump, int blast, Controller controller) {
    this.jump = jump;
    this.blast = blast;
    this.controller = controller;
  }

  @Override
  public void keyPressed(KeyEvent e) {
    int key = e.getKeyCode();
    if(key == this.jump) {
      if(this.jumpReleased) {
        this.controller.jump();
        this.jumpReleased = false;
      }
    } else if(key == this.blast) {
      if(this.blastReleased) {
        this.controller.gravSwitch();
        this.blastReleased = false;
      }
    }
  }

  @Override
  public void keyReleased(KeyEvent e) {
    int key = e.getKeyCode();
    if(key == this.jump) {
      this.jumpReleased = true;
    } else if(key == this.blast) {
      this.blastReleased = true;
    }
  }


}
