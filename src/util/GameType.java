package util;

public enum GameType
{
    ORIG,FIB,INV;

    public static GameType validGT(String gtText){
        GameType gamety = null;
        gtText = gtText.toUpperCase();
        int i = 0;
        while(i < GameType.values().length && gamety == null){
            if(GameType.values()[i].name().equals(gtText)){
                gamety = GameType.values()[i];
            }
            i++;
        }
        return gamety;
    }
}
