package com.diegoaesparza.objects.obstacle;

import com.diegoaesparza.*;
import com.diegoaesparza.collisions.*;
import com.diegoaesparza.objects.*;
import com.diegoaesparza.objects.enemy.*;
import com.diegoaesparza.objects.obstacle.*;
import com.diegoaesparza.objects.pickup.*;
import com.diegoaesparza.objects.player.*;
import com.diegoaesparza.objects.visual.*;

import java.util.Random;

public class ObstacleSpawner extends Spawner {
  private Random r;

  public ObstacleSpawner() {
    r = new Random();
    this.controller = new ObstacleController();
  }

  protected boolean shouldSpawn(int time) { //spawns once every two seconds
    return time % 120 == 119;
  }

  protected GameObject newObject(Visual visual) {
    return new Obstacle(Game.width, (float) r.nextInt(Game.height - 32), 0f, 0f, visual, this.controller, 70f);
  }
}
