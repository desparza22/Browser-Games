package com.diegoaesparza;

import com.diegoaesparza.*;
import com.diegoaesparza.collisions.*;
import com.diegoaesparza.objects.*;
import com.diegoaesparza.objects.enemy.*;
import com.diegoaesparza.objects.obstacle.*;
import com.diegoaesparza.objects.pickup.*;
import com.diegoaesparza.objects.player.*;
import com.diegoaesparza.objects.visual.*;

import java.util.Random;

public class VerifySpawn1 extends VerifySpawn {

  private Random r = new Random();
  private int[] obstacleIntervals = new int[] {120, 120, 120, 120, 90, 30, 10, 10};
  private int currentInterval = 120;

  public VerifySpawn1() {}

  public boolean playerSpawn(int time) {
    return time == 0;
  }

  public boolean enemySpawn(int time) {
    return time % 600 == 599;
  }

  public boolean obstacleSpawn(int time) {
    if(time % currentInterval == currentInterval - 1) {
      this.currentInterval = obstacleIntervals[r.nextInt(obstacleIntervals.length)];
      return true;
    }
    return false;
  }

  public boolean pickupSpawner(int time) {
    return time % 900 == 899;
  }
}
