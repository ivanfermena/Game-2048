package util;

/**
 * Autores: Alberto Pastor Moreno e Ivan Fernandez Mena - 2E
 *
 * Funcion: Clase que trata la totalidad de commandos distintos y los lanza de manera iterativa viendo si es alguno el solicitado por el usuario.
 */

import control.Controller;
import control.commands.*;
import exceptions.CommandParserException;

public class CommandParser {
    private static Command[ ] availableCommands = { new HelpCommand(), new ResetCommand(), new ExitCommand(), new MoveCommand(null), new UndoCommand(), new RedoCommand(), new PlayCommand(null), new SaveCommand(null), new LoadCommand(null) };
    public static Command parseCommand(String[ ] commandWords, Controller controller) throws CommandParserException
    {
        Command command = null;
        int i = 0;
        while( i < availableCommands.length && command == null)
        {
            command = availableCommands[i].parse(commandWords,controller);
            i++;
        }
        if(command == null) throw new CommandParserException("Unknown command.  Use ’help’ to see the available commands.\n");
        else return command;
    }
    public static String commandHelp()
    {
        String helpText = "\nThe available commands are:\n";
        for (int i = 0; i < availableCommands.length; i++) {
            helpText += "\t" + availableCommands[i].helpText() + "\n";
        }
        return helpText;
    }
}
