package com.diegoaesparza.objects.visual;

import com.diegoaesparza.collisions.*;
import com.diegoaesparza.objects.*;
import com.diegoaesparza.objects.enemy.*;
import com.diegoaesparza.objects.obstacle.*;
import com.diegoaesparza.objects.pickup.*;
import com.diegoaesparza.objects.player.*;
import com.diegoaesparza.objects.visual.*;

import java.io.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class Visual {
  // private BufferedImage[] icons;
  private BufferedImages[][] icons;
  private int[] lengths;
  private int divisor;
  private int shiftX, shiftY, width, height;
  // private int

  // public Visual(String folderName, int[] which, int[] counts, int divisor, int shiftX, int shiftY, int width, int height) {
  //   //alteranting integers indicating which image, and for relatively how long
  //   this.divisor = divisor;
  //   fillBuffered(folderName, which, counts);
  //   this.shiftX = shiftX;
  //   this.shiftY = shiftY;
  //   this.width = width;
  //   this.height = height;
  // }

  public Visual(String folderName, int[][] which, int[][] counts, int divisor, int shiftX, int shiftY, int width, int height) {
    //alteranting integers indicating which image, and for relatively how long
    this.divisor = divisor;
    fillBuffered(folderName, which, counts);
    this.shiftX = shiftX;
    this.shiftY = shiftY;
    this.width = width;
    this.height = height;
  }

  private BufferedImage[] convertFiles(File[] files) {
    BufferedImage[] images = new BufferedImage[files.length];
    try{
      for(int i = 0; i < files.length; i++) {
        images[i] = ImageIO.read(files[i]);
      }
    } catch(IOException e) {
      e.printStackTrace();
      System.exit(0);
    }
    return images;
  }

  // private void fillBuffered(String folderName, int[] which, int[] counts) {
  //   if(which.length != counts.length) {
  //     System.out.println("incorrect parameters for visual with folder " + folderName);
  //     System.exit(0);
  //   }
  //   // this.icons = new BufferedImage[whichWhere.length / 2];
  //   File folder = new File(folderName);
  //   File[] files = folder.listFiles((dir, name) -> !name.equals(".DS_Store"));
  //   BufferedImage[] images = convertFiles(files);
  //
  //   int length = 0;
  //   for(int i = 0; i < counts.length; i++) {
  //     length += counts[i];
  //   }
  //   this.length = length;
  //   this.icons = new BufferedImage[this.length];
  //   int counter = 0;
  //   for(int i = 0; i < which.length; i++) {
  //     for(int j = 0; j < counts[i]; j++) {
  //       this.icons[counter] = images[which[i]];
  //       counter++;
  //     }
  //   }
  // }

  private void fillBuffered(String folderName, int[][] which, int[][] counts) {
    if(which.length != counts.length || which[0].length != counts[0].length) {
      System.out.println("incorrect parameters for visual with folder " + folderName);
      System.exit(0);
    }
    // this.icons = new BufferedImage[whichWhere.length / 2];
    File folder = new File(folderName);
    File[] files = folder.listFiles((dir, name) -> !name.equals(".DS_Store"));
    BufferedImage[] images = convertFiles(files);

    this.lengths = new int[counts.length];
    for(int i = 0; i < counts.length; i++) {
      int length = 0;
      for(int j = 0; j < counts[0].length; j++) {
        length += counts[i][j];
      }
      this.lengths[i] = length;
    }

    this.icons = new BufferedImage[this.lengths.length][];
    for(int h = 0; h < this.icons.length; h++) {
      this.icons[h] = new BufferedImage[this.lengths[h]];
    }

    for(int i = 0; i < which.length; i++) {
      int counter = 0;
      for(int ii = 0; ii < which[i].length; i++) {
        for(int j = 0; j < counts[i][ii]; j++) {
          this.icons[i][counter] = images[which[i][ii]];
          counter++;
        }
      }
    }
  }

  public BufferedImage image(int time, int state) { //int state
    return this.icons[state][(time / this.divisor) % this.lengths[state]];
  }

  // public BufferedImage image(int time, int state) {
  //   return this.icons[(time / divisor) % length][state];
  // }

  public int xRen(int x) {
    return x + this.shiftX;
  }

  public int yRen(int y) {
    return y + this.shiftY;
  }

  public int width() {
    return this.width;
  }

  public int height() {
    return this.height;
  }


}
