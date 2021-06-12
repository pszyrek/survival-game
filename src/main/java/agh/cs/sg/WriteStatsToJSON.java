package agh.cs.sg;

import java.io.FileWriter;
import java.io.IOException;
import org.json.simple.JSONObject;

public class WriteStatsToJSON {
    public static void saveFile(String averageEnergy, String numberOfAnimals, String Era, String numberOfGrass, String averageNumberOfChildren, String averageLifeTimeForDeadAnimals)
    {
        JSONObject stats = new JSONObject();
        stats.put("averageEnergy", averageEnergy);
        stats.put("numberOfAnimals", numberOfAnimals);
        stats.put("era", Era);
        stats.put("numberOfGrass", numberOfGrass);
        stats.put("averageNumberOfChildren", averageNumberOfChildren);
        stats.put("averageLifeTimeForDeadAnimals", averageLifeTimeForDeadAnimals);

        try (FileWriter file = new FileWriter("stats.json")) {
            file.write(stats.toJSONString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}