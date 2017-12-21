package util; /**
 * Autores: Alberto Pastor Moreno e Ivan Fernandez Mena - 2E
 *
 * Funcion: Clase que imita el comportamiento de ArrayList, lo usamos para guardar posiciones vacias.
 */

import java.util.Random;

public class ArrayAsList {

    /** -Atributtes- */
    private final static int CAPACITY = 100;
    private Object[] list;
    private int countList = 0;

    /** -Constructor- */
    public ArrayAsList() {
        this.list  = new Object[CAPACITY];
    }

    /** -Getters- */
    private boolean listFull() {
        return countList == CAPACITY;
    }
    public int size() {
        return countList;
    }
    public Object get(int listPos) {
        if((listPos < 0) || (listPos > countList)) return null;
        return list[listPos];
    }

    /**
     * Metodo que inserta un elemento(Objeto) en la lista de vacios
     * @param auxObj -> Objeto que se quiere insertar en la lista
     * @return: True -> Si se ha realizado bien; False -> Si la lista esta llena.
     */
    public boolean addElem(Object auxObj) {
        if (listFull()) return false;
        this.list[countList] = auxObj;
        countList++;
        return true;
    }

    /**
     * Metodo que borra un elemento(Objeto) en la lista de vacios
     * @param pos -> Posicion que se quiere borrar
     * @return: True -> Si se ha realizado bien; False -> Si la lista esta vacia.
     */
    public boolean delElem(int pos){
        if((pos < 1) || (pos > countList)) return false;
        for(int i = pos-1; i < countList-1; i++)
            list[i] = list[i+1];
        countList--;
        return true;
    }

    @Override
    public String toString() {
        String retStr = "Elements of list: \n\n";
        for(int i = 0; i < this.countList; i++)
            retStr += this.list[i] + " ";
        return retStr;
    }

    /**
     * Metodo que baraja la lista para posteriormente coger las primeras segun el argumento dado
     * @param list -> Lista de vacios
     * @param random -> Random necesasio para barajar el array de vacios
     */
    public static void shuffle(ArrayAsList list, Random random) {
        for (int i = list.size(); i > 1; i--) {
            swap(list.list, i - 1, random.nextInt(i));
        }
    }

    /**
     * Metodo que devuelve una posicion aleatoria del array de vacios
     * @param list -> Lista de vacios
     * @param random -> Random necesasio para barajar el array de vacios
     * @return -> Devuelve el objeto solicitado.
     */
    public static Object choice(ArrayAsList list, Random random) {
        return list.get(random.nextInt(list.size()));
    }

    /**
     * Metodo que intercambia dos posiciones del array de objetos
     * @param anArray -> Array de objetos del cual se quiere hacer el cambio
     * @param i -> Posicion a cambiar
     * @param j -> Posicion nueva a poner
     */
    private static void swap(Object[] anArray, int i, int j) {
        Object temp = anArray[i];
        anArray[i] = anArray[j];
        anArray[j] = temp;
    }
}
