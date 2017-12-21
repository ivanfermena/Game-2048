package util;

/**
 * Autores: Alberto Pastor Moreno e Ivan Fernandez Mena - 2E
 *
 * Funcion: Clase para tratar metodos que faciliten acciones matematicas
 */

public class MyMathsUtil {

    /**
     * Metodo que te devuelve el valor fibonacci que necesitaras despues de la union
     * @param previous int --> Valor previo de la uncion
     * @return int --> Valor tras la unicon en fibonacci.
     */
    public static int nextFib(int previous) {
        double phi = (1 + Math.sqrt(5))/2;
        return (int) Math.round(phi*previous);
    }
}