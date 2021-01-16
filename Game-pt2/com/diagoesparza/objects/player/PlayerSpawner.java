package com.diegoaesparza.objects.player;

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

// import java.util.Random;

public class PlayerSpawner extends Spawner {
  // private Random r;

  public PlayerSpawner() {
    this.controller = new PlayerController();
    this.keyInput = new KeyInput(KeyEvent.VK_SPACE, KeyEvent.VK_W, this.controller);
    // this.addKeyListener(this.keyInput);
    // this.blastHUD = new HUD(40, 30, 200, 60);
    // this.controller.giveHUD(this.blastHUD);
  }

  protected boolean shouldSpawn(int time) { //spawns at the start
    return time == 0;
  }

  protected GameObject newObject(Visual visual) {
    return new Player(60f, Game.height - 100f, 3f, 0f, visual, this.controller, 10f);
  }
}
