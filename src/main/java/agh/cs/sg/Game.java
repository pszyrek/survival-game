package agh.cs.sg;

import agh.cs.sg.display.Display;
import agh.cs.sg.grass.Grass;
import agh.cs.sg.grass.GrassType;

import java.awt.*;
import java.awt.image.BufferStrategy;

public class Game implements Runnable {
    private Display display;
    public int width, height;
    public int frameWidth, frameHeight;
    public String title;
    private int tileSize;

    private boolean isRunning = false;
    private Thread thread;

    private BufferStrategy bs;
    private Graphics g;

    private World world;
    private IEngine engine;

    public Game(String title, int width, int height, int tileSize) {
        this.title = title;
        this.world = new World(width, height);

        this.width = width;
        this.height = height;

        this.frameWidth = width * tileSize;
        this.frameHeight = height * tileSize;

        this.tileSize = tileSize;
    }

    private void init() {
        this.display = new Display(title, frameWidth, frameHeight);

        this.engine =  new SimulationEngine(world, width, height, 100);
    }

    private void tick() {
        engine.run();

        int grassCount = 0;

        for(Field field : world.getMap().values()) {
            for(Animal animal : field.getAnimals()) {
                System.out.println("Animal " + animal.getPosition() + " have " + animal.getEnergy() + " energy.");
            }

            if(field.isGrassExists()) {
                grassCount  += 1;
            }
        }

        System.out.println("It's " + grassCount + " Grass left.");
    }

    private void render() {
        bs = display.getCanvas().getBufferStrategy();
        if(bs ==   null){
            display.getCanvas().createBufferStrategy(3);
            return;
        }

        g = bs.getDrawGraphics();
        // Clear Screen
        g.clearRect(0, 0, frameWidth, frameHeight);

        // START
        for(int x = 0; x < this.frameWidth; x++) {
            for(int y = 0; y < this.frameHeight; y++) {
                Vector2d position = new Vector2d(x, y);
                if(!world.isOccupied(position)) {
                    g.setColor(new Color(177,207,114));
                    g.fillRect(x * tileSize, y * tileSize, tileSize, tileSize);
                } else if(world.isAnimalOccupied(position)) {
                    g.setColor(new Color(255,205,187));
                    g.fillRect(x * tileSize, y * tileSize, tileSize, tileSize);
                } else {
                    if(world.isGrassOccupied(position)) {
                        Field field = (Field) world.objectAt(position);
                        Grass grass = field.getGrass();

                        if(grass.getType() == GrassType.STEPPE) {
                            g.setColor(new Color(190,242,2));
                            g.fillRect(x * tileSize, y * tileSize, tileSize, tileSize);
                        } else {
                            g.setColor(new Color(81,149,72));
                            g.fillRect(x * tileSize, y * tileSize, tileSize, tileSize);
                        }
                    }
                }
            }
        }

        // END

        bs.show();
        g.dispose();
    }

    public void run() {
        init();


        int fps = 60;
        double timePerTick = 1000000000 / fps;
        double delta = 0;
        long now;
        long lastTime = System.nanoTime();
        long timer = 0;
        int ticks = 0;

        while(isRunning) {
            now = System.nanoTime();
            delta += (now - lastTime) / timePerTick;
            timer += now - lastTime;
            lastTime = now;

            if(delta >= 1) {
                tick();
                render();
                ticks++;
                delta--;
            }
        }

        stop();
    }

    public synchronized void start() {
        if(isRunning)
            return;
        isRunning = true;
        thread = new Thread(this);
        thread.start();
    }

    public synchronized void stop() {
        if(!isRunning)
            return;
        isRunning = false;
        try {
            thread.join();
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
    }
}
