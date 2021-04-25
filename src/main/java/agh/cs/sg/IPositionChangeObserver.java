package agh.cs.sg;

public interface IPositionChangeObserver {
   void positionChanged(Vector2d oldPosition, Vector2d newPosition);
}
