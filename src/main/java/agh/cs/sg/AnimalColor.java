package agh.cs.sg;

import org.w3c.dom.css.RGBColor;

import java.awt.*;

public enum AnimalColor {
    LIGHTEST,
    LIGHTER,
    LIGHT,
    MEDIUM,
    DARK,
    DARKER,
    DARKEST;

    public Color colorNameToRgb() {
        return switch(this) {
            case LIGHTEST -> new Color(255,205,187);
            case LIGHTER -> new Color(255, 175, 150);
            case LIGHT -> new Color(255, 152, 126);
            case MEDIUM -> new Color(255, 129, 102);
            case DARK -> new Color(255, 104, 77);
            case DARKER -> new Color(255, 104, 49);
            case DARKEST -> new Color(255, 78, 9);
        };
    }

    public AnimalColor colorName(Integer energy) {
        if(energy > 20) {
            return LIGHTER;
        }

        if(energy > 40) {
            return LIGHT;
        }

        if(energy > 80) {
            return MEDIUM;
        }

        if(energy > 160) {
            return DARK;
        }

        if(energy > 320) {
            return DARKER;
        }

        if(energy > 640) {
            return DARKEST;
        }

        return LIGHTEST;
    }
}
