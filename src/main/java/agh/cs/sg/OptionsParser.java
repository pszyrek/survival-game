package agh.cs.sg;


import java.util.LinkedList;
import java.util.List;

public class OptionsParser {
    public static List parse(String[] args) {
        List moveDirectionsList = new LinkedList<MoveDirection>();

        for (String arg : args) {
            if(arg.equals("f") || arg.equals("forward")) {
                moveDirectionsList.add(MoveDirection.FORWARD);
            }

            if(arg.equals("l") || arg.equals("left")) {
                moveDirectionsList.add(MoveDirection.LEFT);
            }

            if(arg.equals("r") || arg.equals("right")) {
                moveDirectionsList.add(MoveDirection.RIGHT);
            }

            if(arg.equals("b") || arg.equals("backward")) {
                moveDirectionsList.add(MoveDirection.BACKWARD);
            }
        }

        return moveDirectionsList;
    }
}
