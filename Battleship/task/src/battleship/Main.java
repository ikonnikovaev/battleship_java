package battleship;

import java.lang.Math;
import java.util.Scanner;


public class Main {


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Field[] fields = new Field[2];
        for (int i = 0; i < fields.length; ++i) {
            fields[i] = new Field();
            if (i == 0) {
                System.out.println("Player 1, place your ships on the game field");
            } else {
                System.out.println("Player 2, place your ships to the game field");
            }
            // System.out.printf("Player %d, place your ships on the game field\n", i + 1);
            fields[i].printField(false);
            fields[i].createShips();
            System.out.println("Press Enter and pass the move to another player");
            scanner.nextLine();
        }


        System.out.println("The game starts!");
        int i = 0;
        while (true) {
            i %= 2;
            fields[1 - i].printField(true);
            System.out.println("---------------------");
            fields[i].printField(false);
            System.out.printf("Player %d, it's your turn:\n", i);
            Shot shot = fields[1 - i].takeShot();
            if (shot == Shot.SANK_LAST) {
                break;
            }
            System.out.println("Press Enter and pass the move to another player");
            scanner.nextLine();
            i += 1;
        }

    }
}
