package com.diegoaesparza.objects;

import com.diegoaesparza.*;
import com.diegoaesparza.collisions.*;
import com.diegoaesparza.objects.*;
import com.diegoaesparza.objects.enemy.*;
import com.diegoaesparza.objects.obstacle.*;
import com.diegoaesparza.objects.pickup.*;
import com.diegoaesparza.objects.player.*;
import com.diegoaesparza.objects.visual.*;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public abstract class Spawner {

  // private Handler[] handlers;
  // private int[][] spawnZones;
  ////each zone is a list of four ints in which a character can be spawned
  protected Handler handler;
  // protected Visual visual;
  //can later be made an array when there are more objects of the same type
  protected Controller controller;
  //NOTE: spawner cannot have its own controller because objects need their own
  //controllers? or maybe it would work to have controllers just decrease the
  //acceleration of an object, rather than determine for itself. if there
  //are ever two players, that controller can keep an array of keys pressed or
  //something. does make sense to have initial velocity
  protected KeyInput keyInput;

  // protected HUD blastHUD; //hudTerm

  public Spawner() {
  }

  public void tick(int time) {
    if(shouldSpawn(time)) {
      handler.addObject(newObject());
    }
  }

  protected abstract boolean shouldSpawn(int time);

  protected abstract GameObject newObject(Visual visual);

  public void giveListenerToGame(Game game) {
    game.addKeyListener(this.keyInput);
  }

  // public HUD getHUD() { //hudTerm
  //   return this.blastHUD;
  // }
}
