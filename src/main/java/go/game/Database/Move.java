package go.game.Database;

public class Move {
    private final int idGame;
    private final int idMove;
    private final String typeOfMove;
    private final int x;
    private final int y;
    private final String color;

    private Move(Builder builder) {
        this.idGame = builder.idGame;
        this.idMove = builder.idMove;
        this.typeOfMove = builder.typeOfMove;
        this.x = builder.x;
        this.y = builder.y;
        this.color = builder.color;
    }

    public int getIdGame() {
        return idGame;
    }

    public int getIdMove() {
        return idMove;
    }

    public String getTypeOfMove() {
        return typeOfMove;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getColor() {
        return color;
    }

    public static class Builder {
        private final int idGame;
        private final int idMove;
        private String typeOfMove;
        private int x;
        private int y;
        private String color;

        public Builder(int idGame, int idMove) {
            this.idGame = idGame;
            this.idMove = idMove;
        }

        public Builder typeOfMove(String typeOfMove) {
            this.typeOfMove = typeOfMove;
            return this;
        }

        public Builder x(int x) {
            this.x = x;
            return this;
        }

        public Builder y(int y) {
            this.y = y;
            return this;
        }

        public Builder color(String color) {
            this.color = color;
            return this;
        }

        public Move build() {
            return new Move(this);
        }
    }
}

