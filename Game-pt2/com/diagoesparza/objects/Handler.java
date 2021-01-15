package com.diegoaesparza.objects;

import com.diegoaesparza.collisions.*;
import com.diegoaesparza.objects.*;
import com.diegoaesparza.objects.enemy.*;
import com.diegoaesparza.objects.obstacle.*;
import com.diegoaesparza.objects.pickup.*;
import com.diegoaesparza.objects.player.*;
import com.diegoaesparza.objects.visual.*;

import java.util.LinkedList;
import java.util.concurrent.Semaphore;
import java.util.Iterator;

public abstract class Handler {

  private LinkedList<GameObject> objects = new LinkedList<GameObject>();
  protected Semaphore threadNotify;
  protected Semaphore mainNotifyTick;

  // protected Semaphore collisionNotify;
  // protected Semaphore threadNotify;

  protected int state;
  protected volatile boolean running;

  protected int rotations;

  protected int time;
  //NOTE:remove after testing running dude. or after showing grandma the moving
  //characters, or actually this might be useful

  protected Collision collision;

  protected Spawner spawner;

  // protected boolean collisionRunner;

  public Handler(int state) {
    this.state = state;
    this.running = true;
    this.rotations = 0;
    // this.collisionRunner = collisionRunner;
  }

  public void giveSemaphoreThread(Semaphore threadNotify) {
    this.threadNotify = threadNotify;
    if(this.collision != null) {
      this.collision.giveSemaphoreThread(this.threadNotify); //comes twice so order doesn't matter
    }
  }

  public void giveSemaphoreMain(Semaphore mainNotifyTick) {
    this.mainNotifyTick = mainNotifyTick;
  }

  public void giveCollision(Collision collision) {
    this.collision = collision;
    if(this.threadNotify != null) {
      this.collision.giveSemaphoreThread(this.threadNotify); //comes twice so order doesn't matter
    }    // this.collisionNotify = this.collision.getSemaphoreCollision();
  }

  public void giveSpawner(Spawner spawner) {
    this.spawner = spawner;
  }

  // @Override
  public void tick() {
    // GameObject current;
    // for(Iterator<GameObject> i = this.objects.iterator(); i.hasNext();) {
    //   current = i.next();
    //   if(!current.removed()) {
    //     current.tickCheck();
    //     this.collision.addObject(current);
    //     current.primeApply(); //collision changes velXPrime and velYPrime
    //   } else {
    //     i.remove();
    //   }
    // }
    this.collision.giveIterator(this.objects.iterator());


    this.collision.finished();

    try {
      this.threadNotify.acquire();
    } catch(InterruptedException e) {
      e.printStackTrace();
    }
    //must be done out here, after all collisions have been added
    // GameObject current;
    for(Iterator<GameObject> i = this.objects.iterator(); i.hasNext();) {
      i.next().primeApply();
    }
    this.spawner.tick(this.time);

    this.time++; // player spawns at 0, so should increase after spawn check
  }

  public void changeState(int state) {
    this.state = state;
  }

  public void stop() {
    this.running = false;
  }

  public int rotations() {
    return this.rotations;
  }

  public int getTime() {
    return this.time;
  }

  public Iterator<GameObject> objectI() {
    return objects.iterator();
  }

  public void addObject(GameObject object) {
    this.objects.add(object);
  }
}
