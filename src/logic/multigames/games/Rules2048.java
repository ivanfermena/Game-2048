package logic.multigames.games;

/** Autores: Alberto Pastor Moreno e Ivan Fernandez Mena - 2E -
 *
 * Funcion: Clase que controla toda la logica del modo de juego: 2048
 */

import logic.Board;
import logic.Cell;
import logic.multigames.GameRules;
import util.Position;

import java.util.Random;

public class Rules2048 implements GameRules {

    private final int conditionWin = 2048;
    /**
     * @param board Board --> Tablero con el estado actual del juego
     * @param pos Position --> Posicion aleatoria a la que se quiere insertar el nuevo numero
     * @param rand Random --> Objeto Random para la seleccion aleatoria
     */
    @Override
    public void addNewCellAt(Board board, Position pos, Random rand) {
        int value;
        int randValue = rand.nextInt(100);
        if(randValue < 10){
            value = 4;
        }
        else value = 2;
        board.setCell(pos, value);
    }

    /**
     * @param self Cell --> Celda uno a tratar
     * @param other Cell --> Celda dos a tratar
     * @return int --> Valor de la union de las dos celdas.
     */
    @Override
    public int merge(Cell self, Cell other) {
        int merge = 0;
        if(self.getValue() == other.getValue() ) {
            self.setValue(other.getValue() * 2);
            other.setValue(0);
            merge = self.getValue();
        }
        return merge;
    }

    /**
     * @param board Board --> Tablero con el estado actual del juego.
     * @return int --> Devuelve el valor mas grande el tablero
     */
    @Override
    public int getWinValue(Board board) { return board.highestValue();
    }

    /**
     * @param board Board --> Estado actual del tablero
     * @return boolean --> Devuelve si has ganado el juego o no.
     */
    @Override
    public boolean win(Board board) {
        return getWinValue(board) == conditionWin;
    }

    @Override
    public String RulesName() {
        return "original";
    }
}
