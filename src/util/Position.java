package util;


/**
 * Autores: Alberto Pastor Moreno e Ivan Fernandez Mena - 2E
 *
 * Funcion: Clase que permite representar posiciones del tablero.
 */

public class Position {

    /** -Atributtes- */
    private int x, y;

    /** -Constructor- */
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /** -Constructor- */
    public Position(int x, int y, Direction dir) {
        if( dir == Direction.UP || dir == Direction.DOWN) {
            this.x = y;
            this.y = x;
        }
        else {
            this.x = x;
            this.y = y;
        }
    }

    /** -Getters and Setters- */
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public void setX(int x) {
        this.x = x;
    }
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Gestiona la siguiente posicion dependiendo de la direccion dictada.
     * @param dir -> Direccion que se va a realizar
     */
    public void neighbour(Direction dir){
        switch (dir){
            case DOWN:
                this.setX(x-1);
                break;
            case UP:
                this.setX(x+1);
                break;
            case LEFT:
                this.setY(y+1);
                break;
            case RIGHT:
                this.setY(y-1);
                break;
        }
    }

    /**
     * Gestina si una posicion es valida, no se salga del tablero establedio.
     * @param _boardSize -> Size de tablero para controlarlo.
     * @return boolean -> Si la posicion e svalida o no.
     */
    public boolean posValid(int _boardSize) {
        return (this.getX() < _boardSize && this.getY() < _boardSize && this.getX() >= 0 && this.getY() >= 0);
    }
}
