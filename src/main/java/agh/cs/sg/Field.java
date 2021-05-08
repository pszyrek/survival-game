package agh.cs.sg;

import agh.cs.sg.grass.Grass;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Field {
    private final List<MapElement> elements = new ArrayList<>();

    public Field(MapElement element) {
        elements.add(element);
    }

    public Optional<MapElement> getAnimal() {
        for(MapElement mapElement : elements) {
            if(mapElement instanceof Animal) {
                return Optional.of(mapElement);
            }
        }

        return Optional.empty();
    }

    public boolean isGrassExists() {
        return elements.stream().anyMatch(mapElement -> mapElement instanceof Grass);
    }

    public boolean isAnimalExists() {
        return elements.stream().anyMatch(mapElement -> mapElement instanceof Animal);
    }

    @Override
    public String toString() {
        if(elements.size() > 1) {
            return elements.size() + "";
        }

        return elements.get(0).toString();
    }
}
