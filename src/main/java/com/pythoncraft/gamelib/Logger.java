package com.pythoncraft.gamelib;

import java.util.logging.Level;

public class Logger {
    public static void log(Level level, String message, Object... params) {
        GameLib.getInstance().getLogger().log(level, message, params);
    }

    public static void info(String message, Object... params) {
        log(Level.INFO, message, params);
    }

    public static void warn(String message, Object... params) {
        log(Level.WARNING, message, params);
    }

    public static void error(String message, Object... params) {
        log(Level.SEVERE, message, params);
    }
}
