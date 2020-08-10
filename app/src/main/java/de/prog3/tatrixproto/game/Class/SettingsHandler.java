package de.prog3.tatrixproto.game.Class;

import de.prog3.tatrixproto.MainActivity;

public class SettingsHandler {


    public static void setSoundON() {
        MainActivity.sharedPref.edit().putBoolean("sound", true ).commit();
    }
    public static void setSoundOFF() {
        MainActivity.sharedPref.edit().putBoolean("sound", false ).commit();
    }
    public static boolean isSoundON() {
        return MainActivity.sharedPref.getBoolean("sound",false);
    }
}
