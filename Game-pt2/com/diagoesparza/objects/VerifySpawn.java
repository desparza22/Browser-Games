package com.diegoaesparza.objects;

import com.diegoaesparza.*;
import com.diegoaesparza.collisions.*;
import com.diegoaesparza.objects.*;
import com.diegoaesparza.objects.enemy.*;
import com.diegoaesparza.objects.obstacle.*;
import com.diegoaesparza.objects.pickup.*;
import com.diegoaesparza.objects.player.*;
import com.diegoaesparza.objects.visual.*;

public abstract class VerifySpawn {

  public abstract boolean playerSpawn(int time);
  public abstract boolean enemySpawn(int time);
  public abstract boolean pickupSpawn(int time);
  public abstract boolean obstacleSpawn(int time);
}
