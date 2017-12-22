package control.commands;

/** Autores: Alberto Pastor Moreno e Ivan Fernandez Mena - 2E -
 *
 * Funcion: Clase abstracta que dicta la estructura principal para los comandos de la aplicacion.
 */

import control.Controller;
import exceptions.MoveException;
import logic.multigames.games.Game;

public abstract class Command {

    /** -Atributtes- */
    private String helpText;
    private String commandText;
    protected final String commandName;

    /**
     * Constructor de Command.
     * @param commandInfo String --> Cadena que dicta el commando introducido para su control posterior.
     * @param helpInfo String --> Cadena en donde se introduce el mensaje de ayuda de commandInfo.
     */
    public Command(String commandInfo, String helpInfo){
        commandText = commandInfo;
        helpText = helpInfo;
        String[] commandInfoWords = commandText.split("\\s+");
        commandName = commandInfoWords[0];
    }

    /**
     * Metodo abstracto general que dicta la estructura de la accion que hara cada commando.
     * @param game Game --> Juego al que le afecta la accion a realizar.
     * @param controller Controller --> Entorno al que se refiere o en donde se realiza la accion.
     */
    public abstract void execute(Game game, Controller controller);

    /**
     * Metodo abstacto que controla el comandos metido y lo trata para ver si es determinado de ese comando.
     * @param commandWords String[] --> Array de candenas que tiene el comando lanzado para tratarlo
     * @param controller Controller --> Entorno al que se refiere o en donde se realiza la accion.
     * @return Command --> Retorno de clase para que comando ha sido correcto y si no return: null.
     */
    public abstract Command parse(String[] commandWords, Controller controller);

    /**
     * Metodo para controlar el formato de la ayuda.
     * @return String --> Retornamos dicho formato.
     */
    public String helpText(){return "-" + commandText + helpText;}
}
