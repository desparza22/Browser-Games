package com.diegoaesparza.collisions;

import com.diegoaesparza.*;
import com.diegoaesparza.collisions.*;
import com.diegoaesparza.objects.*;
import com.diegoaesparza.objects.enemy.*;
import com.diegoaesparza.objects.obstacle.*;
import com.diegoaesparza.objects.pickup.*;
import com.diegoaesparza.objects.player.*;
import com.diegoaesparza.objects.visual.*;

import java.util.concurrent.Semaphore;
import java.util.HashMap;
import java.util.Iterator;
// import java.util.ArrayList;

public class Collision {
  private int handlers;
  private int visited;
  private Semaphore[] semaphores;
  private int added = 0;
  private int width;
  private int height;
  private boolean additionsStarted = false;

  //zones in which collisions will be checked for. must be taller and wider
  //than the objects, as objects will be added to zones in which their corners
  //lie. this could easily be changed, but sufficient for now
  private int zoneHeight = 40;
  private int zoneWidth = 40;

  private Zone[][] zones;

  private HashMap<Long, Long> collisions;
  private long finishes;
  // private HashMap<Long, Integer> current;

  public Collision(int width, int height, int handlers) {
    this.handlers = handlers;
    this.width = width;
    this.height = height;
    this.semaphores = new Semaphore[handlers];
    this.zones = new Zone[width/zoneWidth + 1][height/zoneHeight + 1];
    //NOTE: if width or height ever become not divisible by 40, those parameters
    //need to be plus one. or plus two, because you also need a buffer the width
    //of the objects so they can spawn off screen
    this.collisions = new HashMap<Long, Long>(2000); //number of collisions? could be way more
    this.fillZones();

  }

  private void fillZones() {
    for(int i = 0; i < this.zones.length; i++) {
      for(int j = 0; j < this.zones[i].length; j++) {
        this.zones[i][j] = new Zone(this.collisions);
      }
    }
  }

  //pre: added < this.handlers
  public void giveSemaphoreThread(Semaphore threadNotify) {
    this.semaphores[added] = threadNotify;
    this.added++;
  }

  private synchronized void checkReset() {
    if(!this.additionsStarted) {
      //NOTE: instead, zones should be reset, not the array of zones. have to loop
      //through zones to reset?
      this.resetZones();
      this.additionsStarted = true;
    }
  }

  public void giveIterator(Iterator<GameObject> iterator) {
    checkReset();

    while(iterator.hasNext()) {
      GameObject physical = iterator.next();
      if(physical.removed()) {
        iterator.remove();
        continue;
      }
      physical.tickCheck();
      int left = physical.left()/zoneWidth;
      int top = physical.top()/zoneHeight;
      int right = physical.right()/zoneWidth;
      int bottom = physical.bottom()/zoneHeight;
      boolean leftGood = left >= 0 && left < this.zones.length;
      boolean topGood = top >= 0 && top < this.zones[0].length;
      boolean rightGood = right >= 0 && right < this.zones.length;
      boolean bottomGood = bottom >= 0 && bottom < this.zones[0].length;

      if(leftGood && topGood)
        this.zones[left][top].addObject(physical, finishes);

      if(left != right && rightGood && topGood) {
        this.zones[right][top].addObject(physical, finishes);
      }

      if(top != bottom && bottomGood && leftGood) {
        this.zones[left][bottom].addObject(physical, finishes);
        if(left != right && rightGood) {
          this.zones[right][bottom].addObject(physical, finishes);
        }
      }
    }
  }

  public void addObject(GameObject physical) {
    //NOTE: threads should pass an iterator so that checkReset only needs to be run once per tick
    //instead of making the entire function synchronized, make object with
    //instance object array, that represents a zone;
    checkReset();

    int left = physical.left()/zoneWidth;
    int top = physical.top()/zoneHeight;
    int right = physical.right()/zoneWidth;
    int bottom = physical.bottom()/zoneHeight;
    boolean leftGood = left >= 0 && left < this.zones.length;
    boolean topGood = top >= 0 && top < this.zones[0].length;
    boolean rightGood = right >= 0 && right < this.zones.length;
    boolean bottomGood = bottom >= 0 && bottom < this.zones[0].length;

    if(leftGood && topGood)
      this.zones[left][top].addObject(physical, finishes);

    if(left != right && rightGood && topGood) {
      this.zones[right][top].addObject(physical, finishes);
    }

    if(top != bottom && bottomGood && leftGood) {
      this.zones[left][bottom].addObject(physical, finishes);
      if(left != right && rightGood) {
        this.zones[right][bottom].addObject(physical, finishes);
      }
    }
  }

  private void resetZones() {
    for(int i = 0; i < this.zones.length; i++) {
      for(int j = 0; j < this.zones[i].length; j++) {
        zones[i][j].reset();
      }
    }
    this.finishes++;
  }


  public synchronized void finished() {
    this.visited++;
    if(this.visited == this.handlers) {
      this.visited = 0;
      for(int i = 0; i < this.handlers; i++) {
        this.semaphores[i].release();
        this.additionsStarted = false;
      }
    }
  }
}
