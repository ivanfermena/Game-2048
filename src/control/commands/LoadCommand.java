package control.commands;

import control.Controller;
import exceptions.CommandExecuteException;
import exceptions.CommandParserException;
import logic.multigames.games.Game;
import util.MyStringUtils;

import java.io.*;

public class LoadCommand extends Command {

    private static final String CommandInfo = "load";
    private static final String HelpInfo = ": Load.";

    private File inputFile;

    /**
     * Constructor default: super --> Command
     */
    public LoadCommand(File inputFile) {
        super(CommandInfo, HelpInfo);
        this.inputFile = inputFile;
    }

    @Override
    public Command parse(String[] commandWords, Controller controller) throws CommandParserException {

        if (!this.commandName.equals(commandWords[0])) {
            return null;
        }else {
            if(commandWords.length == 1) throw new CommandParserException("Load command must be followed by a filename.\n");
            else if (commandWords.length==2) {
                this.inputFile = new File(commandWords[1]);
                if (!inputFile.exists()) throw new CommandParserException("File not found.\n");
                else if (!MyStringUtils.canReaderLocal(inputFile)) throw new CommandParserException("File with restricted permissions.\n");
                else return new LoadCommand(inputFile);
            }
            else throw new CommandParserException("Invalid Command: the filename contains spaces.\n");
        }
    }

    @Override
    public boolean execute(Game game, Controller controller) throws CommandExecuteException {
        try(FileReader input = new FileReader(this.inputFile);
            BufferedReader bufInput = new BufferedReader(input)){ // Habria que hacerlo resource pero habria que ver como controlar que sobreescriba bien
            controller.printSoutText("Game successfully loaded from file, 2048. " + game.load(bufInput) +"\n");

            bufInput.close();
            return true;
        } catch (IOException | IndexOutOfBoundsException | NullPointerException | NumberFormatException e) {
            throw new CommandExecuteException("The file does not contain a saved game with a valid format.\n");
        }
        finally {
                // bufInput.close(); // Deberia cerrar en todos los casos
        }
    }

}
