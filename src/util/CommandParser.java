package util;

/**
 * Autores: Alberto Pastor Moreno e Ivan Fernandez Mena - 2E
 *
 * Funcion: Clase que trata la totalidad de commandos distintos y los lanza de manera iterativa viendo si es alguno el solicitado por el usuario.
 */

import control.Controller;
import control.commands.*;

public class CommandParser {
    private static Command[ ] availableCommands = { new HelpCommand(), new ResetCommand(), new ExitCommand(), new MoveCommand(), new UndoCommand(), new RedoCommand(), new PlayCommand() };
    public static Command parseCommand(String[ ] commandWords, Controller controller)
    {
        Command command = null;
        int i = 0;
        while( i < availableCommands.length && command == null) // Revisar posible bucle erroneo
        {
            command = availableCommands[i].parse(commandWords,controller);
            i++;
        }
        return command;
    }
    public static String commandHelp()
    {
        String helpText = "\nThe available commands are:\n";
        for (int i = 0; i < availableCommands.length; i++) {
            helpText += "\t" + availableCommands[i].helpText() + "\n"; // Revisar aviso de concatenacion
        }
        return helpText;
    }
}
