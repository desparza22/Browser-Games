package com.diegoaesparza.collisions;

import com.diegoaesparza.*;
import com.diegoaesparza.collisions.*;
import com.diegoaesparza.objects.*;
import com.diegoaesparza.objects.enemy.*;
import com.diegoaesparza.objects.obstacle.*;
import com.diegoaesparza.objects.pickup.*;
import com.diegoaesparza.objects.player.*;
import com.diegoaesparza.objects.visual.*;

public class IDGen {

  private int count = 1;
  private int minCount;
  private LinkedID head = new LinkedID(1);
  private LinkedID tail = head;
  private int current = 2;

  public IDGen(int initial) {
    this.minCount = initial;
    grow();
  }

  private void grow() {
    while(this.count < this.minCount*2) {
      LinkedID last = new LinkedID(current);
      current++;
      this.count++;
      tail.next = last;
      this.tail = last;
    }
  }

  public synchronized void assignID(GameObject object) {
    object.setID(this.head.number);
    this.head = head.next;
    this.count--;
    if(this.count < this.minCount)
      grow();
  }

  public synchronized void returnObject(GameObject object) {
    LinkedID giveBack = new LinkedID(object.idCode());
    this.tail.next = giveBack;
    this.tail = giveBack;
    this.count++;
  }

  private class LinkedID {
    public int number;
    public LinkedID next;

    public LinkedID(int number) {
      this.number = number;
    }

    public LinkedID(int number, LinkedID next) {
      this.number = number;
      this.next = next;
    }
  }
}
