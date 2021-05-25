package agh.cs.sg;

public class Launcher {
    public static void main(String[] args) {

        new GameConfiguration("Survival Game", 40, 40, 20, 100, 1, 60, 4);

        Game game = new Game();
        game.start();
    }
}
