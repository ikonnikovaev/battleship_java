package battleship;

import java.lang.Math;
import java.util.Scanner;


public class Main {
    public static final int FIELD_SIZE = 10;

    public static char[][] field;

    public static void initField() {
        field = new char[FIELD_SIZE][FIELD_SIZE];
        for (int i = 0; i < FIELD_SIZE; ++i) {
            for (int j = 0; j < FIELD_SIZE; ++j)  {
                 field[i][j] = '~';
            }
        }
    }

    public static void printField() {
        System.out.print(" ");
        for (int j = 0; j < FIELD_SIZE; ++j) {
            System.out.print(" " + (j + 1));
        }
        System.out.println();
        for (int i = 0; i < FIELD_SIZE; ++i) {
            System.out.print((char) ('A' + i));
            for (int j = 0; j < FIELD_SIZE; ++j) {
                System.out.print(" " + field[i][j]);
            }
            System.out.println();
        }
    }

    public static boolean placeShip(int r1, int c1, int r2, int c2, String type, int len) throws Exception {
        int dr = Math.abs(r1 - r2);
        int dc = Math.abs(c1 - c2);
        int rs = Math.min(r1, r2);
        int rt = Math.max(r1, r2);
        int cs = Math.min(c1, c2);
        int ct = Math.max(c1, c2);
        if (rs < 0 || rt >= FIELD_SIZE || cs < 0 || ct > FIELD_SIZE) {
            throw new Exception("You entered the wrong coordinates!");
        }
        if (dr * dc != 0) {
            throw new Exception("Wrong ship location!");
        }
        if (Math.max(dr, dc) + 1 != len) {
            throw new Exception(String.format("Wrong length of the %s!", type));
        }
        for (int i = rs - 1; i <= rt + 1; ++i) {
            for (int j = cs - 1; j <= ct + 1; ++j) {
                if (Math.min(i, j) >= 0 && Math.max(i, j) < FIELD_SIZE) {
                    if (field[i][j] == 'O') {
                        throw new Exception("You placed it too close to another one.");
                    }
                }
            }
        }
        for (int i = rs; i <= rt; ++i) {
            for (int j = cs; j <= ct; ++j) {
                field[i][j] = 'O';
            }
        }
        return true;
    }

    public static void createShip(String type, int len) {
        Scanner scanner = new Scanner(System.in);
        boolean shipCreated = false;
        String prompt = String.format("Enter the coordinates of the %s (%d cells):", type, len);
        System.out.println(prompt);
        while (!shipCreated) {
            String coord1 = scanner.next();
            String coord2 = scanner.next();
            int r1 = coord1.charAt(0) - 'A';
            int r2 = coord2.charAt(0) - 'A';
            int c1 = Integer.parseInt(coord1.substring(1)) - 1;
            int c2 = Integer.parseInt(coord2.substring(1)) - 1;
            try {
                shipCreated = placeShip(r1, c1, r2, c2, type, len);
            } catch (Exception e) {
                System.out.printf("Error! %s Try again:", e.getMessage());
            }
        }
    }

    public static boolean shoot(int r, int c) throws Exception {
        if (r < 0 || r >= FIELD_SIZE || c < 0 || c > FIELD_SIZE) {
            throw new Exception("You entered the wrong coordinates!");
        }
        if (field[r][c] == '~') {
            field[r][c] = 'M';
            printField();
            System.out.println("You missed!");
        } else {
            field[r][c] = 'X';
            printField();
            System.out.println("You hit a ship!");
        }
        return true;
    }


    public static void takeShot() {
        Scanner scanner = new Scanner(System.in);
        boolean shotMade = false;
        String prompt = "Take a shot!";
        System.out.println(prompt);
        while (!shotMade) {
            String coord = scanner.next();
            int r = coord.charAt(0) - 'A';
            int c = Integer.parseInt(coord.substring(1)) - 1;
            try {
                shotMade = shoot(r, c);
            } catch (Exception e) {
                System.out.printf("Error! %s Try again:", e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        initField();
        printField();
        String[] shipTypes = {"Aircraft Carrier", "Battleship", "Submarine", "Cruiser", "Destroyer"};
        int[] shipLengths = {5, 4, 3, 3, 2};
        for (int k = 0; k < shipTypes.length; ++k) {
            createShip(shipTypes[k], shipLengths[k]);
            printField();
        }
        System.out.println("The game starts!");
        takeShot();


    }
}
