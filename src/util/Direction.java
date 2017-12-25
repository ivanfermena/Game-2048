package util;

/**
 * Autores: Alberto Pastor Moreno e Ivan Fernandez Mena - 2E
 *
 * Funcion: Enumerador que representa la direcciones de un movimiento.
 */

public enum Direction
{
    UP, DOWN, LEFT, RIGHT;

    public static Direction validDir(String dirText){
        Direction dir = null;
        dirText = dirText.toUpperCase();
        int i = 0;
        while(i < Direction.values().length && dir == null){
            if(Direction.values()[i].name().equals(dirText)){
                dir = Direction.values()[i];
            }
            i++;
        }
        return dir;
    }
}
