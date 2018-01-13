/**
 * Autores: Alberto Pastor Moreno e Ivan Fernandez Mena - 2E
 *
 * Funcion: Clase que contiene el MAIN de la applicación
 */

import control.Controller;
import logic.multigames.GameRules;
import logic.multigames.games.Game;
import logic.multigames.games.Rules2048;
import util.GameType;

import java.util.Random;
import java.util.Scanner;

public class Game2048 {
    public static void main(String[]args){
        Scanner sc = new Scanner(System.in);

       try {
            long seed;
            if (args.length > 2) {
                //rand = new Random(Integer.parseInt(args[2]));
                seed = Integer.parseInt(args[2]);
            } else {
                seed = new Random().nextInt(1000);
                //rand = new Random(seed);
            }

            GameRules rules = new Rules2048();
            GameType gameType = GameType.ORIG;
            Game game = new Game(Integer.parseInt(args[0]),Integer.parseInt(args[1]), seed, rules, gameType);
            Controller ctr = new Controller(game, sc);
            ctr.run();
        }catch (NumberFormatException e){
            System.out.println(e.getMessage());
        }
    }
}
