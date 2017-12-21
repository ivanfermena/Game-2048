package logic;

import logic.multigames.GameRules;

/**
 * Autores: Alberto Pastor Moreno e Ivan Fernandez Mena - 2E
 *
 * Funcion: Clase que permite representar cada una de las baldosas del tablero.
 */

public class Cell {

    /** -Atributtes- */
    private int value;

    /** -Constructor- */
    public Cell(int value) {
        this.value = value;
    }

    /** -Getters and Setters- */
    public int getValue() {return this.value;}
    public void setValue(int value) {
        this.value = value;
    }

    /**
     * Método que permita comprobar si una baldosa está vacía.
     * @return: True ->  Esta vacia; False -> Tiene algun elemento
     */
    public boolean isEmpty(){
        boolean empty = false;
        if(this.getValue() == 0)
        {
            empty = true;
        }
        return empty;
    }

    /**
     * Método que implementa la acción Fusionar de dos baldosas
     * @param neighbour -> Valor que se va fusionar
     * @return -> Valor despues de realizar la fusion
     */
    public int doMerge(Cell neighbour, GameRules rules) {
        return rules.merge(this,neighbour);
    }

    @Override
    public String toString() {
        if (this.isEmpty())
            return " ";
        else return String.valueOf(this.getValue());
    }
}
