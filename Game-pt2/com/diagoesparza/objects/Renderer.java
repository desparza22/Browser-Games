package com.diegoaesparza.objects;

import com.diegoaesparza.*;
import com.diegoaesparza.collisions.*;
import com.diegoaesparza.objects.*;
import com.diegoaesparza.objects.enemy.*;
import com.diegoaesparza.objects.obstacle.*;
import com.diegoaesparza.objects.pickup.*;
import com.diegoaesparza.objects.player.*;
import com.diegoaesparza.objects.visual.*;

import java.awt.*;
import java.util.concurrent.Semaphore;
import java.util.Iterator;

public abstract class Renderer implements Runnable {

  protected Handler handler;
  protected Semaphore mainNotifyRender;
  protected Semaphore threadNotify;
  protected boolean running = true;
  protected int delta = 0;
  // protected boolean tick = false;
  // protected Graphics graphic;

  public Renderer(Handler handler) {
    this.handler = handler;

  }

  private void render() {
    Iterator<GameObject> it = this.handler.objectI();
    while(it.hasNext()) {
      GameObject current = it.next();
      Game.currentG().drawImage(current.image(handler.getTime()), current.xRen(), current.yRen(), null);
    }
  }
  //this may be more of an object method than a renderer method

  public void run() {
    while(running) {
      try{
        this.threadNotify.acquire();
        while(delta >= 1) {
          this.handler.tick();
          delta--;
        }
        render();
        // this.run();
      } catch(InterruptedException e) {
        e.printStackTrace();
        System.exit(0);
      }
      this.mainNotifyRender.release(1);
      // rotations++;
    }
  }

  public void giveSemaphoreMain(Semaphore mainNotifyRender) {
    this.mainNotifyRender = mainNotifyRender;
  }

  public void giveSemaphoreThread(Semaphore threadNotify) {
    this.threadNotify = threadNotify;
    this.handler.giveSemaphoreThread(this.threadNotify);
  }

  public void stop() {
    this.running = false;
  }

  public void updateDelta(int delta) {
    this.delta = delta;
  }
}
