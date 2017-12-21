package logic;

/**
 * Autores: Alberto Pastor Moreno e Ivan Fernandez Mena - 2E
 *
 * Funcion: Clase que permite representar los resultados de la ejecucion de un movimiento.
 */

public class MoveResults {

    /** -Atributtes- */
    private boolean moved; // movimiento si/no
    private int points; // puntos obtenidos en el movimiento

    /**
     * Contructor de MoveResult
     * @param moved Boolean --> Movimiento a realizar
     * @param points int --> Puntos que vale el moviemnto
     */
    public MoveResults(boolean moved, int points) {
        this.moved = moved;
        this.points = points;
    }

    /** -Getters and Setters- */
    public boolean isMoved() {
        return this.moved;
    }
    public int getPoints() {
        return this.points;
    }

}
