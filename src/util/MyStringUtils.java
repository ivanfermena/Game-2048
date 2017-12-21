package util;

/**
 * Autores: Alberto Pastor Moreno e Ivan Fernandez Mena - 2E
 *
 * Funcion: Clase que tiene la gestion del muestreo del tablero (Todo lo alrededor de cada celda)
 */

public class MyStringUtils {

    /** -Atributtes- */
    private int cellSize;
    private String space;
    private String vDelimeter;
    private String hDelimeter;

    /** -Constructor- */
    public MyStringUtils(int cellSize) {
        this.cellSize = cellSize;
        this.space = "  ";
        this.vDelimeter = "|";
        this.hDelimeter = "-";
    }

    /**
     *
     * @param elmnt
     * @param length
     * @return
     */
    public static String repeat(String elmnt, int length) {
        String result = "";
        for (int i = 0; i < length; i++) {
            result += elmnt;
        }
        return result;
    }

    /**
     *
     * @param text
     * @param len
     * @return
     */
    public static String centre(String text, int len) {
        String out = String.format("%" + len + "s%s%" + len + "s", "", text, "");
        float mid = (out.length() / 2);
        float start = mid - (len / 2);
        float end = start + len;
        return out.substring((int) start, (int) end);
    }

    /** -Getters and Setters- */
    public String getSpace() {
        return space;
    }
    public String getvDelimeter() {
        return vDelimeter;
    }
    public String gethDelimeter() {
        return hDelimeter;
    }
}