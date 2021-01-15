package com.diegoaesparza.objects;

import com.diegoaesparza.*;
import com.diegoaesparza.collisions.*;
import com.diegoaesparza.objects.*;
import com.diegoaesparza.objects.enemy.*;
import com.diegoaesparza.objects.obstacle.*;
import com.diegoaesparza.objects.pickup.*;
import com.diegoaesparza.objects.player.*;
import com.diegoaesparza.objects.visual.*;


public class Environment {
  private float gravityX;
  private float gravityY;
  private Visual playerVisual;
  private Visual enemyVisual;
  private Visual pickupVisual;
  private Visual obstacleVisual;
  private Spawner playerSpawner;
  private Spawner enemySpawner;
  private Spawner pickupSpawner;
  private Spawner obstacleSpawner;
  private VerifySpawn verifier;

  public Environment(float gravityX, float gravityY, Visual[] visuals, Spawner[] spawners, VerifySpawn verifier) {
    this.gravityX = gravityX;
    this.gravityY = gravityY;
    this.playerVisual = visuals[0];
    this.enemyVisual = visuals[1];
    this.pickupVisual = visuals[2];
    this.obstacleVisual = visuals[3];
    this.playerSpawner = spawners[0];
    this.enemySpawner = spawners[1];
    this.pickupSpawner = spawners[2];
    this.obstacleSpawner = spawners[3];
    this.verifier = verifier;
  }

  public void spawnPlayer(List<GameObject> objectList, int time) {
    if(this.verifier.spawnPlayer(int time))
      objectList.add(this.playerSpawner.newObject(this.playerVisual));
  }

  public void spawnEnemy(List<GameObject> objectList, int time) {
    if(this.verifier.spawnEnemy(int time))
      objectList.add(this.enemySpawner.newObject(this.enemyVisual));
  }

  public void spawnPickup(List<GameObject> objectList, int time) {
    if(this.verifier.spawnPickup(int time))
      objectList.add(this.pickupSpawner.newObject(this.pickupVisual));
  }

  public void spawnObstacle(List<GameObject> objectList, int time) {
    if(this.verifier.spawnObject(int time))
      objectList.add(this.obstacleSpawner.newObject(this.obstacleVisual));
  }

}
