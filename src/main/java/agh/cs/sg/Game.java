package agh.cs.sg;

import agh.cs.sg.display.GameFrame;

import java.util.concurrent.TimeUnit;

public class Game implements Runnable {
    private GameFrame gameFrame;
    public int width, height;

    private volatile boolean pauseWork = false;
    private boolean isRunning = true;

    public World world;
    private IEngine engine;

    public Game() {
        this.width = GameConfiguration.width;
        this.height = GameConfiguration.height;

        this.world = new World(this.width , this.height, GameConfiguration.minEnergyToReproduce);
    }

    public void exit() {
        isRunning = false;
    }

    private void init() {
        this.gameFrame = new GameFrame(this);
        this.engine = new SimulationEngine(world, width, height);
    }

    private void tick() {
        engine.run();
        this.gameFrame.getGamePanel().updatePopup();
        this.gameFrame.updateStatsPopup();
    }

    private void render() {
        this.gameFrame.repaint();
    }

    @Override
    public void run() {
        init();

        while(isRunning) {
            while (pauseWork) Thread.onSpinWait();
            try {
                TimeUnit.MILLISECONDS.sleep(GameConfiguration.delay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            tick();
            render();

            world.increaseEra();
        }
    }

    public void pause() {
        this.pauseWork = true;
    }

    public void resume() {
        this.pauseWork = false;
    }

    public void start(boolean startImmediately) {
        this.pauseWork = !startImmediately;
        Thread workerThread = new Thread(this);
        workerThread.start();
    }
}
