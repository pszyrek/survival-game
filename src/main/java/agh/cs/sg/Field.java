package agh.cs.sg;

import agh.cs.sg.grass.Grass;

import java.util.ArrayList;
import java.util.List;

public class Field {
    private final List<MapElement> elements = new ArrayList<>();

    public Field(MapElement element) {
        elements.add(element);
    }

    public void addElement(MapElement mapElement) {
        this.elements.add(mapElement);
    }

    public void removeElement(MapElement mapElement) {
        this.elements.remove(mapElement);
    }

    public List<MapElement> getElements() {
        return this.elements;
    }

    public List<Animal> getAnimals() {
        List<Animal> animals = new ArrayList<>();
        for(MapElement mapElement : elements) {
            if(mapElement instanceof Animal) {
                animals.add((Animal) mapElement);
            }
        }

        return animals;
    }

    public Grass getGrass() {
        for(MapElement mapElement : elements) {
            if(mapElement instanceof Grass) {
                return (Grass) mapElement;
            }
        }

        return null;
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
