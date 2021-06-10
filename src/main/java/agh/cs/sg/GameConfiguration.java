package agh.cs.sg;

public class GameConfiguration {
    public static String title;
    public static int width;
    public static int height;
    public static int tileSize;
    public static int startNumberOfAnimals;
    public static int valueOfDecreasingEnergy;
    public static int minEnergyToReproduce;
    public static int numberOfWorlds;
    public static int grassSize;
    public static int grassSizeRespawn;

    public GameConfiguration(String title, int width, int height, int tileSize, int startNumberOfAnimals, int valueOfDecreasingEnergy, int minEnergyToReproduce, int numberOfWorlds, int grassSize, int  grassSizeRespawn) {
        this.title = title;
        this.width = width;
        this.height = height;
        this.tileSize = tileSize;
        this.startNumberOfAnimals = startNumberOfAnimals;
        this.valueOfDecreasingEnergy = valueOfDecreasingEnergy;
        this.minEnergyToReproduce = minEnergyToReproduce;
        this.numberOfWorlds = numberOfWorlds;
        this.grassSize = grassSize;
        this.grassSizeRespawn = grassSizeRespawn;
    }
}
