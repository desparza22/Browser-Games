package com.diegoaesparza.objects.pickup;

import com.diegoaesparza.*;
import com.diegoaesparza.collisions.*;
import com.diegoaesparza.objects.*;
import com.diegoaesparza.objects.enemy.*;
import com.diegoaesparza.objects.obstacle.*;
import com.diegoaesparza.objects.pickup.*;
import com.diegoaesparza.objects.player.*;
import com.diegoaesparza.objects.visual.*;

public class PickupController extends Controller {

  public PickupController() {
  }

  public void update(GameObject object) {
    object.setAccX(this.accX);
    object.setAccY(this.accY);
  }
}
