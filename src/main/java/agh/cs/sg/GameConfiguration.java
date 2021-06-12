package agh.cs.sg;

import java.io.FileNotFoundException;
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

            this.title = (String) jsonObject.get("title");
            this.width = Integer.parseInt((String) jsonObject.get("width"));
            this.height = Integer.parseInt((String) jsonObject.get("height"));
            this.tileSize = Integer.parseInt((String) jsonObject.get("tileSize"));
            this.startNumberOfAnimals = Integer.parseInt((String) jsonObject.get("startNumberOfAnimals"));
            this.valueOfDecreasingEnergy = Integer.parseInt((String) jsonObject.get("valueOfDecreasingEnergy"));
            this.numberOfWorlds = Integer.parseInt((String) jsonObject.get("numberOfWorlds"));
            this.grassSize = Integer.parseInt((String) jsonObject.get("grassSize"));
            this.grassSizeRespawn = Integer.parseInt((String) jsonObject.get("grassSizeRespawn"));
            this.delay = Integer.parseInt((String) jsonObject.get("delay"));
            this.initialValueOfAnimalEnergy = Integer.parseInt((String) jsonObject.get("initialValueOfAnimalEnergy"));
            this.jungleEnergyValue = Integer.parseInt((String) jsonObject.get("jungleEnergyValue"));
            this.steppeEnergyValue = Integer.parseInt((String) jsonObject.get("steppeEnergyValue"));
            this.jungleRatio = Integer.parseInt((String) jsonObject.get("jungleRatio"));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
