package com.diegoaesparza.objects.enemy;

import com.diegoaesparza.*;
import com.diegoaesparza.collisions.*;
import com.diegoaesparza.objects.*;
import com.diegoaesparza.objects.enemy.*;
import com.diegoaesparza.objects.obstacle.*;
import com.diegoaesparza.objects.pickup.*;
import com.diegoaesparza.objects.player.*;
import com.diegoaesparza.objects.visual.*;
import java.util.Random;

public class EnemySpawner extends Spawner {
  private Random r;

  public EnemySpawner() {
    r = new Random();
    this.controller = new EnemyController();
  }

  protected boolean shouldSpawn(int time) { //spawns once every ten seconds
    return time % 600 == 599;
  }

  protected GameObject newObject(Visual visual) {
    //spawns off the edge and creeps in
    return new Enemy(-32f, (float) r.nextInt(Game.height - 32), GameObject.gameSpeedX + 0.5f, 0f, visual, this.controller, 6f);
  }
}
