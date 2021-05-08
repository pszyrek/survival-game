package agh.cs.sg;

public interface IPositionChangeObserver {
    Vector2d positionChange(Animal animal, Vector2d position);
}
