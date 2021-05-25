package agh.cs.sg;

public class GameConfiguration {
    public static String title;
    public static int width;
    public static int height;
    public static int tileSize;
    public static int startNumberOfAnimals;
    public static int valueOfDecreasingEnergy;
    public static int fps;
    public static int minEnergyToReproduce;

    public GameConfiguration(String title, int width, int height, int tileSize, int startNumberOfAnimals, int valueOfDecreasingEnergy, int fps, int minEnergyToReproduce) {
        this.title = title;
        this.width = width;
        this.height = height;
        this.tileSize = tileSize;
        this.startNumberOfAnimals = startNumberOfAnimals;
        this.valueOfDecreasingEnergy = valueOfDecreasingEnergy;
        this.fps = fps;
        this.minEnergyToReproduce = minEnergyToReproduce;
    }
}
