package com.diegoaesparza.objects.pickup;

import com.diegoaesparza.*;
import com.diegoaesparza.collisions.*;
import com.diegoaesparza.objects.*;
import com.diegoaesparza.objects.enemy.*;
import com.diegoaesparza.objects.obstacle.*;
import com.diegoaesparza.objects.pickup.*;
import com.diegoaesparza.objects.player.*;
import com.diegoaesparza.objects.visual.*;

import java.util.Random;

public class PickupSpawner extends Spawner {
  private Random r;

  public PickupSpawner() {
    r = new Random();
    this.controller = new PickupController();
  }

  protected boolean shouldSpawn(int time) { //spawns once every fifteen seconds
    return time % 900 == 899;
  }

  protected GameObject newObject(Visual visual) {
    // Controller pickupController = new PickupController();
    return new Pickup(Game.width, (float) r.nextInt(Game.height - 32), 0f, 0f, visual, this.controller, 3f);
  }


}
