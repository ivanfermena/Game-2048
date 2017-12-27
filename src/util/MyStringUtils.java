package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

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

    public static boolean validFileName(String filename) {
        File file = new File(filename);
        if (file.exists()) {
            return canWriteLocal(file);
        } else {
            try {
                file.createNewFile();
                file.delete();
                return true;
            } catch (Exception e) {
                return false;
            }
        }
    }

    private static boolean canWriteLocal(File ﬁle) {
        // works OK on Linux but not on Windows (apparently!)
        if (!ﬁle.canWrite()) {
            return false;
        }
        // works on Windows
        try {
            new FileOutputStream(ﬁle, true).close();
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    public static boolean canReaderLocal(File ﬁle) {
        // works OK on Linux but not on Windows (apparently!)
        if (!ﬁle.canRead()) {
            return false;
        }
        // works on Windows
        try {
            new FileInputStream(ﬁle).close();
        } catch (IOException e) {
            return false;
        }
        return true;
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