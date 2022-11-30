package battleship;

public enum Ship {

    AIRCRAFT_CARRIER(5, "Aircraft Carrier"),
    BATTLESHIP(4, "Battleship"),
    SUBMARINE(3, "Submarine"),
    CRUISER(3, "Cruiser"),
    DESTROYER(2, "Destroyer");

    int length;
    String type;

    Ship(int length, String type) {
        this.length = length;
        this.type = type;
    }

    public int getLength() {
        return this.length;
    }

    public String getType() {
        return this.type;
    }
}
