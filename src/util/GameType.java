package util;

import control.Controller;
import exceptions.CommandParserException;
import logic.multigames.games.Rules2048;

import java.io.File;

public enum GameType
{
    ORIG("original"),FIB("fib"),INV("inverse");

    private String nameEnumGame;

    GameType(String nameEnumGame){
        this.nameEnumGame = nameEnumGame;
    }

    public static final String ﬁlenameInUseMsg = "The ﬁle already exists ; do you want to overwrite it ? (Y/N)";

    public static GameType validGT(String gtText){
        GameType gamety = null;
        gtText = gtText.toUpperCase();
        int i = 0;
        while(i < GameType.values().length && gamety == null){
            if(GameType.values()[i].name().equals(gtText)){
                gamety = GameType.values()[i];
                gamety.nameEnumGame = "" + GameType.values()[i];
            }
            i++;
        }
        return gamety;
    }

    public static String conﬁrmFileNameStringForWrite(String ﬁlenameString, Controller controller) throws CommandParserException {
        String loadName = ﬁlenameString;
        boolean ﬁlename_conﬁrmed = false;
        while (!ﬁlename_conﬁrmed) {
            if (MyStringUtils.validFileName(loadName)) {
                File ﬁle = new File(loadName);
                if (!ﬁle.exists())
                    ﬁlename_conﬁrmed = true;
                else {
                    loadName = getLoadName(ﬁlenameString, controller);
                    ﬁlename_conﬁrmed = true;
                }
            } else {
                throw new CommandParserException("Invalid filename: the filename contains invalid characters.\n");
            }
        }
        return loadName;
    }

    private static String getLoadName(String filenameString, Controller controller) throws CommandParserException {
        String newFilename = null;
        boolean yesOrNo = false;
        while (!yesOrNo) {
            System.out.print(ﬁlenameInUseMsg + ": ");
            String[] responseYorN = controller.readLineScanner().toLowerCase().trim().split("\\s+");
            if (responseYorN.length == 1) {
                switch (responseYorN[0]) {
                    case "y":
                        yesOrNo = true;
                        newFilename = filenameString;
                        controller.setBoolOverwrite(false);
                        break;
                    case "n":
                        yesOrNo = true;
                        controller.printSoutText("Please enter another filename: ");
                        newFilename = controller.readLineScanner();
                        controller.setBoolOverwrite(true);
                        if (!MyStringUtils.validFileName(newFilename))
                            throw new CommandParserException("Invalid filename: the filename contains invalid characters.");
                        break;

                    default:
                        controller.printSoutText("Please answer ’Y’ or ’N’\n");
                }
            } else {
                throw new CommandParserException("Invalid filename: the filename contains spaces.\n");
            }
        }
        return newFilename;
    }
}
