package agh.cs.sg;

import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

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
    public static int delay;
    public static int initialValueOfAnimalEnergy;
    public static int jungleEnergyValue;
    public static int steppeEnergyValue;
    public static int jungleRatio;

    public GameConfiguration()
    {
        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader("gameConfiguration.json"))
        {
            Object obj = jsonParser.parse(reader);
            JSONObject jsonObject = (JSONObject) obj;

            title = (String) jsonObject.get("title");
            width = Integer.parseInt((String) jsonObject.get("width"));
            height = Integer.parseInt((String) jsonObject.get("height"));
            tileSize = Integer.parseInt((String) jsonObject.get("tileSize"));
            startNumberOfAnimals = Integer.parseInt((String) jsonObject.get("startNumberOfAnimals"));
            valueOfDecreasingEnergy = Integer.parseInt((String) jsonObject.get("valueOfDecreasingEnergy"));
            numberOfWorlds = Integer.parseInt((String) jsonObject.get("numberOfWorlds"));
            grassSize = Integer.parseInt((String) jsonObject.get("grassSize"));
            grassSizeRespawn = Integer.parseInt((String) jsonObject.get("grassSizeRespawn"));
            delay = Integer.parseInt((String) jsonObject.get("delay"));
            initialValueOfAnimalEnergy = Integer.parseInt((String) jsonObject.get("initialValueOfAnimalEnergy"));
            jungleEnergyValue = Integer.parseInt((String) jsonObject.get("jungleEnergyValue"));
            steppeEnergyValue = Integer.parseInt((String) jsonObject.get("steppeEnergyValue"));
            jungleRatio = Integer.parseInt((String) jsonObject.get("jungleRatio"));

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }
}
