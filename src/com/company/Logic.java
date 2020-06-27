package com.company;
import com.company.graphics.Render;
import com.company.graphics.Screen;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferInt;
import java.nio.Buffer;

public class Logic extends Canvas implements Runnable {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private static final long serialVersionUID = 1L;
    private static final String TITLE = "MyGame 0.01";

    private Thread thread;
    private Screen screen;
    private boolean running = false;
    private Render render;
    private BufferedImage img;
    private int[] pixels;


    public Logic(){
        screen = new Screen(WIDTH, HEIGHT);
        img = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);

        pixels = ((DataBufferInt)img.getRaster().getDataBuffer()).getData();


    }

    private void start(){
        if (running) {
            return;
        }

        running = true;
        thread = new Thread(this);
        thread.start();
    }

    private void stop() {
        if(!running) {
            return;
        }
        running = false;

        try {
            thread.join();
        }
        catch(InterruptedException ie){
            ie.printStackTrace();
            System.exit(-1);
        }
    }

    public int getWIDTH(){
        return WIDTH;
    }

    public int getHEIGHT(){
        return HEIGHT;
    }

    public String getTITLE(){
        return TITLE;
    }

    public void setStart(){
        this.start();
    }

    @Override
    public void run() {

        int frames = 0;
        double unpSeconds = 0;
        long prevTime = System.nanoTime();
        double secondsPerTick = 1 / 60.0;
        int tickCount = 0;
        boolean ticked = false;

        while(running) {
            long currentTime = System.nanoTime();
            long passedTime = currentTime - prevTime;
            prevTime = currentTime;
            unpSeconds += passedTime / 1000000000.0;

            while (unpSeconds > secondsPerTick) {
                tick();
                unpSeconds -= secondsPerTick;
                ticked = true;
                tickCount++;
                if(tickCount % 60 == 0) {
                    System.out.println(frames + " FPS");
                    prevTime += 1000;
                    frames = 0;
                }

            }

            if (ticked) {
                render();
                frames++;
            }

            render();
            frames++;
        }

    }

    private void render() {
        BufferStrategy bs = this.getBufferStrategy();

        if(bs == null) {
            createBufferStrategy(3);
            return;
        }

        screen.render();

        for (int i = 0 ; i< WIDTH * HEIGHT; i++){

            pixels[i] = screen.pixels[i];

        }

        Graphics g = bs.getDrawGraphics();
        g.drawImage(img, 0,0,WIDTH, HEIGHT, null);
        g.dispose();
        bs.show();
    }

    private void tick() {
    }
}
