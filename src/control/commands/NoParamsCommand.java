package control.commands;

/** Autores: Alberto Pastor Moreno e Ivan Fernandez Mena - 2E -
 *
 * Funcion: Clase que controla si la palabra introducida coincide a algun comando del sistema.
 */

import control.Controller;

public abstract class NoParamsCommand extends Command {

    /**
     * Constructor default: super --> Command
     * @param commandInfo String --> Cadena que dicta el commando introducido para su control posterior.
     * @param helpInfo String --> Cadena en donde se introduce el mensaje de ayuda de commandInfo.
     */
    public NoParamsCommand(String commandInfo, String helpInfo) {
        super(commandInfo, helpInfo);
    }

    /**
     * Metodo abstracto que genera una nueva instancia de el objeto que necesitas.
     * @return Command --> Devuelve aquella instancia del objeto creado.
     */
    public abstract Command createCommand();

    /**
     * Metodo que trata los comandos y redirige para ver si coincide con las palabras insertadas.
     * @param commandWords String[] --> Array de candenas que tiene el comando lanzado para tratarlo
     * @param controller Controller --> Entorno al que se refiere o en donde se realiza la accion.
     * @return Command --> Devuelve el objeto Command tratado y si no es perteneciente a este commando return: null
     */
    public Command parse(String[] commandWords, Controller controller)
    {
        if(this.commandName.equals(commandWords[0]) && commandWords.length == 1) {
            return this.createCommand(); // Modificar para devolver un objeto nuevo copia
        }else {
            return null;
        }
    }
}
