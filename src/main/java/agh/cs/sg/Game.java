package agh.cs.sg;

import agh.cs.sg.display.Display;

import java.awt.*;
import java.awt.image.BufferStrategy;

public class Game implements Runnable {
    private Display display;
    public int width, height;
    public int frameWidth, frameHeight;
    public String title;
    private int tileSize;
    private final int startNumberOfAnimals;
    private final int fps;
    private final int valueOfDecreasingEnergy;

    private volatile boolean pauseWork = false;
    private volatile String state = "New";
    private Thread workerThread;
    private boolean isRunning = true;

    private BufferStrategy bs;
    private Graphics g;

    private World world;
    private IEngine engine;

    public Game() {
        this.title = GameConfiguration.title;
        this.width = GameConfiguration.width;
        this.height = GameConfiguration.height;
        this.world = new World(this.width , this.height, GameConfiguration.minEnergyToReproduce);

        this.frameWidth = GameConfiguration.width * GameConfiguration.tileSize;
        this.frameHeight = GameConfiguration.height * GameConfiguration.tileSize;

        this.tileSize = GameConfiguration.tileSize;

        this.startNumberOfAnimals = GameConfiguration.startNumberOfAnimals;
        this.fps = GameConfiguration.numberOfFps;

        this.valueOfDecreasingEnergy = GameConfiguration.valueOfDecreasingEnergy;
    }

    public void exit() {
        isRunning = false;
    }

    private void init() {
        this.display = new Display(title, frameWidth, frameHeight, this);
        this.engine = new SimulationEngine(world, width, height, startNumberOfAnimals, valueOfDecreasingEnergy);
    }

    private void tick() {
        engine.run();

        int grassCount = 0;
        int animalsCount = 0;

        for(Field field : world.getMap().values()) {
            for(Animal animal : field.getAnimals()) {
                animalsCount += 1;
            }

            if(field.isGrassExists()) {
                grassCount  += 1;
            }
        }

        System.out.println("It's " + animalsCount + " Animals left.");
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
                    if(world.isInJungleRange(position)) {
                        g.setColor(new Color(81,149,72));
                        g.fillRect(x * tileSize, y * tileSize, tileSize, tileSize);
                    } else  {
                        g.setColor(new Color(160,197,95));
                        g.fillRect(x * tileSize, y * tileSize, tileSize, tileSize);
                    }
                } else if(world.isAnimalOccupied(position)) {
                    g.setColor(new Color(255,205,187));
                    g.fillRect(x * tileSize, y * tileSize, tileSize, tileSize);
                } else {
                    if(world.isGrassOccupied(position)) {
                        g.setColor(new Color(190,242,2));
                        g.fillRect(x * tileSize, y * tileSize, tileSize, tileSize);
                    }
                }
            }
        }

        // END

        bs.show();
        g.dispose();
    }

    @Override
    public void run() {
        init();

        int fps = this.fps;
        double timePerTick = 1000000000 / fps;
        double delta = 0;
        long now;
        long lastTime = System.nanoTime();

        while(isRunning) {
            while(pauseWork) {
                setState("Paused");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ie) {
                    System.out.println("Huston, we have a problem. : |");
                }
            }
            setState("Running");

            now = System.nanoTime();
            delta += (now - lastTime) / timePerTick;
            lastTime = now;

            if(delta >= 1) {
                tick();
                render();
                delta--;
            }
        }
    }

    public void pause() {
        this.pauseWork = true;
    }

    public void resume() {
        this.pauseWork = false;
        if (workerThread != null)
            workerThread.interrupt(); //wakeup if sleeping
    }

    private void setState(String state) {
        this.state = state;
    }

    /** startImmediately = true to begin work right away, false = start Work in paused state, call resume() to do work */
    public void start(boolean startImmediately) {
        this.pauseWork = !startImmediately;
        workerThread = new Thread(this);
        workerThread.start();
    }
}
