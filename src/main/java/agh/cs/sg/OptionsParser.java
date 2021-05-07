package agh.cs.sg;


import java.util.LinkedList;
import java.util.List;

public class OptionsParser {
    public static List parse(String[] args) {
        List moveDirectionsList = new LinkedList<Integer>();

        for (String arg : args) {
            if(arg.equals("0")) {
                moveDirectionsList.add(0);
            }

            if(arg.equals("1")) {
                moveDirectionsList.add(1);
            }

            if(arg.equals("2")) {
                moveDirectionsList.add(2);
            }

            if(arg.equals("3")) {
                moveDirectionsList.add(3);
            }

            if(arg.equals("4")) {
                moveDirectionsList.add(4);
            }

            if(arg.equals("5")) {
                moveDirectionsList.add(5);
            }

            if(arg.equals("6")) {
                moveDirectionsList.add(6);
            }

            if(arg.equals("7")) {
                moveDirectionsList.add(7);
            }
        }

        return moveDirectionsList;
    }
}
