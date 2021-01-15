package com.diegoaesparza.objects;

import com.diegoaesparza.*;
import com.diegoaesparza.collisions.*;
import com.diegoaesparza.objects.*;
import com.diegoaesparza.objects.enemy.*;
import com.diegoaesparza.objects.obstacle.*;
import com.diegoaesparza.objects.pickup.*;
import com.diegoaesparza.objects.player.*;
import com.diegoaesparza.objects.visual.*;

public class Blaster extends GameObject {

  public Blaster(float x, float y, Visual visual) {
    super(x, y, visual);
    this.primeX = this.x;
    this.primeY = this.y;
  }

  public void tickCheck() {
    
  }

  public void primeApply() {

  }
}
