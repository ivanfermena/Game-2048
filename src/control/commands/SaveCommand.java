package control.commands;

import control.Controller;
import exceptions.CommandExecuteException;
import exceptions.CommandParserException;
import exceptions.GameOverException;
import logic.multigames.games.Game;
import util.GameType;

import java.io.*;


public class SaveCommand extends Command{

    private static final String CommandInfo = "save";
    private static final String HelpInfo = ": Save help .";

    private File inputFile;

    /**
     * Constructor default: super --> Command
     */
    public SaveCommand(File inputFile){
        super(CommandInfo, HelpInfo);
        this.inputFile = inputFile;
    }

    @Override
    public Command parse(String[] commandWords, Controller controller) throws CommandParserException {

        if (!this.commandName.equals(commandWords[0])) {
            return null;
        }else {
            SaveCommand saveCommand = null;
            String auxStr = GameType.conﬁrmFileNameStringForWrite(commandWords[1], controller);
            if (auxStr != null) {
                    this.inputFile = new File(auxStr);
                    saveCommand = new SaveCommand(inputFile);
            } else {
                throw new CommandParserException("Load must be followed by a filename.\n");
            }
            return saveCommand;
        }
    }

    @Override
    public boolean execute(Game game, Controller controller) throws CommandExecuteException, GameOverException {

        try{ // Habria que hacerlo resource pero habria que ver como controlar que sobreescriba bien

            FileWriter input = null;
            if (controller.isBoolOverwrite()) input = new FileWriter(inputFile, controller.isBoolOverwrite());
            else input = new FileWriter(inputFile);

            BufferedWriter bufInput = new BufferedWriter(input);
            game.store(bufInput);
            controller.printSoutText("Game successfully saved to file; use load command to reload it.\n");
        }catch (IOException e){
            throw new CommandExecuteException("The redo command could not be completed.\n");
        }
        return false;
    }


}
