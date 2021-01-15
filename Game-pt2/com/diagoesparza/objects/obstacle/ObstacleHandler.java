package com.diegoaesparza.objects.obstacle;

import com.diegoaesparza.collisions.*;
import com.diegoaesparza.objects.*;
import com.diegoaesparza.objects.enemy.*;
import com.diegoaesparza.objects.obstacle.*;
import com.diegoaesparza.objects.pickup.*;
import com.diegoaesparza.objects.player.*;
import com.diegoaesparza.objects.visual.*;

import java.util.ArrayList;
import java.util.concurrent.*;
import java.lang.Thread;

public class ObstacleHandler extends Handler {
  public ObstacleHandler(int state) {
      super(state);
  }

  // public void tick() {
  //   // this.threadNotify.await();
  //
  //   this.time++;
  // }

  // @Override
  // public void changeState(int state) {
  //   this.state = state;
  // }
}
