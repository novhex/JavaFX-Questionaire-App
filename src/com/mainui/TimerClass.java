/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mainui;

import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author Novi
 */
public class TimerClass {
    static int interval;
static Timer timer;

public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    System.out.print("Input seconds => : ");
    final String secs = "360";
    int delay = 1000;
    int period = 1000;
    timer = new Timer();
    interval = Integer.parseInt(secs);
   // System.out.println(secs);
    timer.scheduleAtFixedRate(new TimerTask() {

        public void run() {
            setInterval();
        	
        }
    }, delay, period);
}


private static final int setInterval() {
	
    int min=interval/60;
    int second=interval%60;
    
    if (interval == 0)
        timer.cancel();
    
    System.err.println(String.format("%02d: %02d",min,second));
    return --interval;
   
}
}
