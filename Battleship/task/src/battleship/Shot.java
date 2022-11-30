package battleship;

public enum Shot {
    INVALID_SHOT,
    MISS,
    HIT,
    SANK,
    SANK_LAST;

    public String getMessage() {
        switch (this) {
            case INVALID_SHOT:
                return "Error! You entered the wrong coordinates!";
            case MISS:
                return "You missed! Try again:";
            case HIT:
                return "You hit a ship! Try again:";
            case SANK:
                return "You sank a ship! Specify a new target:";
            case SANK_LAST:
                return "You sank the last ship. You won. Congratulations!";
            default:
                return "";
        }
    }
}