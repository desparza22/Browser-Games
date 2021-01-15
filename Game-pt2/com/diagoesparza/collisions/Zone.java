package com.diegoaesparza.collisions;

import com.diegoaesparza.*;
import com.diegoaesparza.collisions.*;
import com.diegoaesparza.objects.*;
import com.diegoaesparza.objects.enemy.*;
import com.diegoaesparza.objects.obstacle.*;
import com.diegoaesparza.objects.pickup.*;
import com.diegoaesparza.objects.player.*;
import com.diegoaesparza.objects.visual.*;

import java.util.ArrayList;
import java.util.HashMap;

public class Zone {

  private HashMap<Long, Long> collisions;
  private ArrayList<GameObject> presentObjects;
  private int numObjects = 0;

  public Zone(HashMap<Long, Long> collisions) {
    this.presentObjects = new ArrayList<GameObject>();
    this.collisions = collisions;
  }

  public void reset() {
    if(this.numObjects > 0) {
      this.presentObjects.clear();
      this.numObjects = 0;
    }
  }

  public synchronized void addObject(GameObject object, long finishes) {
    int topOne = object.top();
    int bottomOne = object.bottom();
    int leftOne = object.left();
    int rightOne = object.right();
    for(int i = 0; i < this.numObjects; i++) {
      GameObject two = this.presentObjects.get(i);
      if(topOne < two.bottom()) {
        if(two.top() < bottomOne) {
          if(leftOne < two.right()) {
            if(two.left() < rightOne) {
              collide(object, two, finishes);
            }
          }
        }
      }
    }
    this.presentObjects.add(object);
    this.numObjects++;
  }

  private void collide(GameObject one, GameObject two, long finishes) {
    // one.primeApply();
    // two.primeApply(); //NOTE: change later
    //if colliding, apply physics
    long idOne = (long)one.idCode();
    long idTwo = (long)two.idCode();
    long maxId = (idOne > idTwo)? idOne: idTwo;
    long negMinId = maxId - idOne - idTwo;
    long combined = (negMinId << 32) | (maxId);
    if(this.collisions.containsKey(combined)) {
      Long value = this.collisions.get(combined);
      if(finishes - value < 50) {
        this.collisions.put(combined, finishes);
        return;
      }
    }
    System.out.println("real collision " + idOne + " " + idTwo + " " + finishes);//searchTerm

    float velOneX = one.velXPrime();
    float velTwoX = two.velXPrime();
    float velOneY = one.velYPrime();
    float velTwoY = two.velYPrime();
    float massOne = one.mass();
    float massTwo = two.mass();
    System.out.println(massOne + " massOne " + massTwo + " massTwo");
    float velOneXAfter = (massOne * velOneX - massTwo * velOneX + 2 * massTwo * velTwoX) / (massOne + massTwo);
    float velTwoXAfter = velOneXAfter + velOneX - velTwoX;
    float velOneYAfter = (massOne * velOneY - massTwo * velOneY + 2 * massTwo * velTwoY) / (massOne + massTwo);
    float velTwoYAfter = velOneYAfter + velOneY - velTwoY;
    System.out.println("velOneX = " + velOneX + " velTwoX = " + velTwoX +
                       "\n velOneXAfter = " + velOneXAfter + " velTwoXAfter = " + velTwoXAfter);
    one.setVelXPrime(velOneXAfter);
    one.setVelYPrime(velOneYAfter);
    two.setVelXPrime(velTwoXAfter);
    two.setVelYPrime(velTwoYAfter);

    this.collisions.put(combined, finishes);

  }
}
