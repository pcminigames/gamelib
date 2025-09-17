package com.pythoncraft.gamelib;

import org.bukkit.scoreboard.Criteria;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

public class Score {
    public static Scoreboard scoreboard = GameLib.getInstance().getServer().getScoreboardManager().getMainScoreboard();
    public Objective objective;

    public Score(String name, String displayName) {
        if (scoreboard.getObjective(name) != null) {
            scoreboard.getObjective(name).unregister();
        }

        this.objective = scoreboard.registerNewObjective(name, Criteria.DUMMY, displayName);
    }

    public Score(String name) {
        this(name, name);
    }

    public void setDisplayName(String displayName) {
        this.objective.setDisplayName(displayName);
    }

    public void setDisplaySlot(DisplaySlot slot) {
        this.objective.setDisplaySlot(slot);
    }

    public void clearDisplaySlot() {
        this.objective.setDisplaySlot(null);
    }

    public void setScore(String entry, int score) {
        this.objective.getScore(entry).setScore(score);
    }

    public int getScore(String entry) {
        return this.objective.getScore(entry).getScore();
    }

    public void resetScores() {
        scoreboard.resetScores(this.objective.getName());
    }

    public void unregister() {
        this.objective.unregister();
    }
}
