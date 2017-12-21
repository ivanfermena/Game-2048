package control.commands;

/** Autores: Alberto Pastor Moreno e Ivan Fernandez Mena - 2E -
 *
 * Funcion: Clase que controla el commando que realiza el cambio de juego y las especificaciones de estos dentro la aplicacion.
 */

import control.Controller;
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
    private final int defaultBoardSize = 4;
    private final int defaultInitCells = 2;

    /**
     * Constructor de PlayCommand que dicta Commando a introducir y el texto de ayuda.
     */
    public PlayCommand() {
        super("play", " <game>: Start a new game of one of the game types: original, fib, inverse.");
    }

    /**
     * Metodo que controla si se introduce bien el comando play y los trata para realizarlos correctamente.
     * @param commandWords String[] --> Array de candenas que tiene el comando lanzado para tratarlo
     * @param controller Controller --> Entorno al que se refiere o en donde se realiza la accion.
     * @return Command --> Devuelve el objeto Command tratado y si no es perteneciente a este commando return: null
     */
    @Override
    public Command parse(String[] commandWords, Controller controller) {
        if (!this.commandName.equals(commandWords[0]) && commandWords.length == 1) {
            if(!controller.isDoPrintAuxText()){
                controller.iniTextUnknown();
            }
            return null;
        }else {
            if (this.commandName.equals(commandWords[0]) && commandWords.length >= 2) {
                PlayCommand playCommand = new PlayCommand();
                playCommand.gameType = GameType.validGT(commandWords[1]);
                if (playCommand.gameType == null){
                    controller.setDoPrintAuxText(true);
                    controller.setAuxText("Unknown game type for play command. ( original(orig), fibonacci(fib) or inverse(inv))");
                    playCommand = null;
                }
                return playCommand;
            } else {
                if(!controller.isDoPrintAuxText()) {
                    controller.setAuxText("Play must be followed by a game type: original(orig), fibonacci(fib), inverse(inv)");
                    controller.setDoPrintAuxText(true);
                }
                return null;
            }
        }
    }

    /**
     * Metodo que trata que juego se va a ejecutar y recoge las especificaciones de este por teclado. Tras esto lanza el juego nuevo y resetea con las nuevas normas.
     * @param game Game --> Juego al que le afecta la accion a realizar.
     * @param controller Controller --> Entorno al que se refiere o en donde se realiza la accion.
     */
    public void execute(Game game, Controller controller)
    {
        Scanner in = new Scanner(System.in);

        // CONTROL DE SIZE

        System.out.print("Please enter the size of the board: ");
        String auxBoardSize = in.nextLine();

        if(auxBoardSize.isEmpty()){
            game.setSize(this.defaultBoardSize);
            auxBoardSize = String.valueOf(this.defaultBoardSize);
            System.out.println("Using the default size of the board: " + this.defaultBoardSize);
        }
        else {
            while (Integer.parseInt(auxBoardSize) <= 1) {
                System.out.println("ERROR: The size of the board must be positive and larger than 1.");
                System.out.print("Please enter the size of the board again: ");
                auxBoardSize = in.nextLine();
            }
            game.setSize(Integer.parseInt(auxBoardSize));
        }

        // CONTROL DE INICELL

        System.out.print("Please enter the number of initial cells: ");
        String auxInitCells = in.nextLine();

        if(auxInitCells.isEmpty()){
            this.initCells = game.setInitCells(this.defaultInitCells);
            System.out.println("Using the default number of initial cells: " + this.initCells);
        }
        else {
            while (Integer.parseInt(auxInitCells) > Integer.parseInt(auxBoardSize) * Integer.parseInt(auxBoardSize)) {
                System.out.println("ERROR: The number of initial cells must be less than the number of cells on the board");
                System.out.print("Please enter the size of the board again: ");
                auxInitCells = in.nextLine();
            }
            game.setInitCells(Integer.parseInt(auxInitCells));
        }

        // CONTROL DE SEED

        System.out.print("Please enter the seed for the pseudo-random number generator: ");
        String auxRand = in.nextLine();
        if(auxRand.isEmpty()){
            this.rand = game.setNewRandom(1000);
            System.out.println("Using the default seed for the pseudo-random number generator: "+ this.rand);
        }
        else game.setSeed(Long.parseLong(auxRand));

        switch(this.gameType){
            case ORIG: game.setCurrentRules(new Rules2048()); break;
            case FIB: game.setCurrentRules(new RulesFib()); break;
            case INV: game.setCurrentRules(new RulesInverse()); break;
        }
        game.reset();
        controller.setAuxText("Has cambiado el juego actual\n");
        controller.setDoPrintAuxText(true);
        controller.setDoPrintGame(true);
    }
}
