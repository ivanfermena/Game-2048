package control.commands;

/** Autores: Alberto Pastor Moreno e Ivan Fernandez Mena - 2E -
 *
 * Funcion: Clase que controla el commando que realiza el cambio de juego y las especificaciones de estos dentro la aplicacion.
 */

import control.Controller;
import exceptions.CommandParserException;
import logic.multigames.GameRules;
import logic.multigames.games.Game;
import logic.multigames.games.Rules2048;
import logic.multigames.games.RulesFib;
import logic.multigames.games.RulesInverse;
import util.GameType;
import java.util.Scanner;

public class PlayCommand extends Command {

    /** -Atributtes- */
    private GameType gameType;
    private int initCells;
    private long rand;
    private static final int defaultBoardSize = 4;
    private static final int defaultInitCells = 2;
    private static final String CommandInfo = "play";
    private static final String HelpInfo = "<game>: Start a new game of one of the game types: original, fib, inverse.";

    /**
     * Constructor de PlayCommand que dicta Commando a introducir y el texto de ayuda.
     */
    public PlayCommand(GameType gameType) {
        super(CommandInfo, HelpInfo);
        this.gameType = gameType;
    }

    /**
     * Metodo que controla si se introduce bien el comando play y los trata para realizarlos correctamente.
     * @param commandWords String[] --> Array de candenas que tiene el comando lanzado para tratarlo
     * @param controller Controller --> Entorno al que se refiere o en donde se realiza la accion.
     * @return Command --> Devuelve el objeto Command tratado y si no es perteneciente a este commando return: null
     */
    @Override
    public Command parse(String[] commandWords, Controller controller) throws CommandParserException{
        if (!this.commandName.equals(commandWords[0])) {
            return null;
        }else {
            if (commandWords.length == 2) {
                PlayCommand playCommand = new PlayCommand(GameType.validGT(commandWords[1]));
                if (playCommand.gameType == null){
                    throw new CommandParserException("Unknown game type for play command. ( original<orig>, fibonacci<fib> or inverse<inv> )\n");
                } else return playCommand;
            } else { // Se podria anadir diferencia caso tam = 1 y tam >2
                throw new CommandParserException("Play must be followed only by a game type: original(orig), fibonacci(fib), inverse(inv)\n");
            }
        }
    }

    /**
     * Metodo que trata que juego se va a ejecutar y recoge las especificaciones de este por teclado. Tras esto lanza el juego nuevo y resetea con las nuevas normas.
     * @param game Game --> Juego al que le afecta la accion a realizar.
     * @param controller Controller --> Entorno al que se refiere o en donde se realiza la accion.
     */
    public boolean execute(Game game, Controller controller) {

        boolean validInput = true;

        // CONTROL DE SIZE
        controller.printSoutText("\nPlease enter the size of the board: ");
        String auxBoardSize = controller.readLineScanner();

        if(auxBoardSize.isEmpty()){
            game.setSize(defaultBoardSize);
            auxBoardSize = String.valueOf(defaultBoardSize);
            controller.printSoutText("Using the default size of the board: " + defaultBoardSize + "\n");
        }
        else {
            do {
                try{
                    game.setSize(Integer.parseInt(auxBoardSize));
                    validInput = Integer.parseInt(auxBoardSize) <= 1;
                }catch (NumberFormatException e){
                    controller.printSoutText("ERROR: The size of the board must be positive and larger than 1.");
                    controller.printSoutText("Please enter the size of the board again: ");
                    auxBoardSize = controller.readLineScanner();
                }

            }while(!validInput);
        }

        // CONTROL DE INICELL
        validInput = true;
        controller.printSoutText("\nPlease enter the number of initial cells: ");
        String auxInitCells =  controller.readLineScanner();

        if (auxInitCells.isEmpty()) {
            this.initCells = game.setInitCells(defaultInitCells);
            controller.printSoutText("Using the default number of initial cells: " + defaultInitCells + "\n");
        }else {
            do {
                validInput = (Integer.parseInt(auxInitCells) > Integer.parseInt(auxBoardSize) * Integer.parseInt(auxBoardSize));
                try{
                    game.setSize(Integer.parseInt(auxInitCells));

                }catch (NumberFormatException e){
                    validInput = false;
                    controller.printSoutText("ERROR: The number of initial cells must be less than the number of cells on the board");
                    controller.printSoutText("Please enter the size of the board again: ");
                    auxBoardSize = controller.readLineScanner();
                }

            }while(!validInput);
        }

        // CONTROL DE SEED
        validInput = true;
        controller.printSoutText("\nPlease enter the seed for the pseudo-random number generator: ");
        String auxRand =  controller.readLineScanner();
        try {
            if (auxRand.isEmpty()) {
                this.rand = game.setNewRandom(1000);
                controller.printSoutText("Using the default seed for the pseudo-random number generator: " + this.rand + "\n");
            } else game.setSeed(Long.parseLong(auxRand));
        }catch (NumberFormatException e){
            throw new NumberFormatException("The seed for the pseudo-random number generator must be a valid number. ");
        }

        game.setCurrentRules(GameRules.validGR(this.gameType));
        controller.setAuxText("Has cambiado el juego actual\n");
        controller.setDoPrintAuxText(true);
        game.reset();
        return true;
    }
}
