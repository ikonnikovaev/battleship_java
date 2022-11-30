package battleship;

import java.util.Scanner;

public class Field {
    public final int FIELD_SIZE = 10;
    public char[][] field;

    public Field() {
        field = new char[FIELD_SIZE][FIELD_SIZE];
        for (int i = 0; i < FIELD_SIZE; ++i) {
            for (int j = 0; j < FIELD_SIZE; ++j) {
                field[i][j] = '~';
            }
        }
    }

    public void printField(boolean fogged) {
        System.out.print(" ");
        for (int j = 0; j < FIELD_SIZE; ++j) {
            System.out.print(" " + (j + 1));
        }
        System.out.println();
        for (int i = 0; i < FIELD_SIZE; ++i) {
            System.out.print((char) ('A' + i));
            for (int j = 0; j < FIELD_SIZE; ++j) {
                char ch = field[i][j];
                if (fogged && ch == 'O') {
                    ch = '~';
                }
                System.out.print(" " + ch);
            }
            System.out.println();
        }
    }

    public boolean neighborsAlive(int r, int c) {
        for (int i = 0; i <= r + 1; ++i) {
            for (int j = c - 1; j <= c + 1; ++j) {
                if (Math.min(i, j) >= 0 && Math.max(i, j) < FIELD_SIZE) {
                    if (field[i][j] == 'O') {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean shipAlive() {
        for (int i = 0; i < FIELD_SIZE; ++i) {
            for (int j = 0; j < FIELD_SIZE; ++j) {
                if (field[i][j] == 'O') {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean placeShip(int r1, int c1, int r2, int c2, Ship ship) throws Exception {
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
        if (Math.max(dr, dc) + 1 != ship.getLength()) {
            throw new Exception(String.format("Wrong length of the %s!", ship.getType()));
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

    public void createShip(Ship ship) {
        Scanner scanner = new Scanner(System.in);
        boolean shipCreated = false;
        String prompt = String.format("Enter the coordinates of the %s (%d cells):", ship.getType(), ship.getLength());
        System.out.println(prompt);
        while (!shipCreated) {
            String coord1 = scanner.next();
            String coord2 = scanner.next();
            int r1 = coord1.charAt(0) - 'A';
            int r2 = coord2.charAt(0) - 'A';
            int c1 = Integer.parseInt(coord1.substring(1)) - 1;
            int c2 = Integer.parseInt(coord2.substring(1)) - 1;
            try {
                shipCreated = placeShip(r1, c1, r2, c2, ship);
            } catch (Exception e) {
                System.out.printf("Error! %s Try again:", e.getMessage());
            }
        }
    }

    public void createShips() {
        for (Ship ship : Ship.values()) {
            createShip(ship);
            printField(false);
        }
    }

    public Shot shoot(int r, int c) {
        if (r < 0 || r >= FIELD_SIZE || c < 0 || c > FIELD_SIZE) {
            return Shot.INVALID_SHOT;
        }
        if (field[r][c] == '~' || field[r][c] == 'M') {
            field[r][c] = 'M';
            return Shot.MISS;
        } else {
            field[r][c] = 'X';
            if (neighborsAlive(r, c)) {
                return Shot.HIT;
            }
            if (shipAlive()) {
                return Shot.SANK;
            }
            return Shot.SANK_LAST;

        }
    }

    public Shot takeShot() {
        Scanner scanner = new Scanner(System.in);
        // String prompt = "Take a shot!";
        // System.out.println(prompt);
        while (true) {
            String coord = scanner.next();
            int r = coord.charAt(0) - 'A';
            int c = Integer.parseInt(coord.substring(1)) - 1;
            Shot shot = shoot(r, c);
            if (shot == Shot.INVALID_SHOT) {
                System.out.println(shot.getMessage());
            } else {
                printField(true);
                System.out.println(shot.getMessage());
                return shot;
            }
        }
    }
}
