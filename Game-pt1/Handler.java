
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Iterator;

import java.awt.*;
import java.awt.image.*;

public class Handler {

  private ArrayList<GameObject> object = new ArrayList<GameObject>();
  private ArrayList<GameObject> trails = new ArrayList<GameObject>();
  private ArrayList<GameObject> collisions = new ArrayList<GameObject>();
  private KeyInput ki;

  public void tick() {

    for(int i = 0; i < object.size(); i++) {
      GameObject current = object.get(i);
      if(current.removed) {
        object.remove(i);
        i--;
      } else {
        current.tick();
      }
    }

    for(int j = 0; j < trails.size(); j++) {
      GameObject current = trails.get(j);
      current.tick();
      if(current.removed) {
        trails.remove(j);
        j--;
      }
      // System.out.println("called at least once on: " + current.toString());
    }

    int collisionSize = collisions.size(); //need this because size will increase
    for(int k = 0; k < collisionSize; k++) {
      GameObject current = collisions.get(k);
      current.tick();
      if(current.removed) {
        collisions.remove(k);//no k-- because size is set
        k--;
        collisionSize--;
      }
    }

  }

  public Iterator objectI() {
    return this.object.iterator();
  }

  public void render(Graphics g) {
    for(Iterator i = object.iterator(); i.hasNext();) {
      GameObject currentObject = (GameObject) i.next();
      currentObject.render(g);
    }

    for(Iterator j = trails.iterator(); j.hasNext();) {
      GameObject current = (GameObject) j.next();
      current.render(g);
    }

    for(Iterator k = collisions.iterator(); k.hasNext();) {
      GameObject current = (GameObject) k.next();
      current.render(g);
    }
  }

  public void addObject(GameObject object) {
    this.object.add(object);
  }

  public void addTrail(GameObject trail) {
    // System.out.println("adding: " + trail);
    // trail.tick();
    this.trails.add(trail);
  }

  public void addCollision(GameObject collision) {
    this.collisions.add(collision);
  }

  public void removeObject(GameObject object) {
    this.object.remove(object);
  }

  public void removeTrail(GameObject trail) {
    this.trails.remove(trail);
  }

  public void addKeyInput(KeyInput ki) {
    this.ki = ki;
  }

  public void scramble() {
    this.ki.scramble();
  }
}
