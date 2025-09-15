package com.pythoncraft.gamelib;

import java.util.function.Consumer;

import org.bukkit.scheduler.BukkitRunnable;

public class Timer extends BukkitRunnable {
    private int tickTimeLeft; // Infinite time when set to -1
    private final int tickPeriod;
    private final Consumer<Integer> onTick;
    private final Runnable onFinish;

    public Timer(int tickTotalTime, int tickPeriod, Consumer<Integer> onTick, Runnable onFinish) {
        /*
            Create a timer that runs for `tickTotalTime` ticks, calling `onTick` every `tickPeriod` ticks.
            If `tickTotalTime` is -1, the timer will run indefinitely.
            If `tickPeriod` is 0, the timer will not tick.
        */
        this.tickTimeLeft = -1;

        if (tickPeriod <= 0) {throw new IllegalArgumentException("tickPeriod must be greater than 0");}

        if (tickTotalTime > 0) {
            // Round up to the next multiple of interval
            this.tickTimeLeft = ((tickTotalTime + tickPeriod - 1) / tickPeriod) * tickPeriod;
        }

        this.onTick = onTick;
        this.onFinish = onFinish;
        this.tickPeriod = tickPeriod;
    }

    public static Timer loop(int tickPeriod, Consumer<Integer> onTick) {
        /*
            Create a repeating timer.
            The timer will run indefinitely, calling `onTick` with the current tick count every `tickPeriod` ticks.
            The tick count starts at 1 and increments with each call.
        */
        return new Timer(-1, tickPeriod, onTick, null);
    }

    public static Timer after(int tickTime, Runnable onFinish) {
        /*
            Create a timer that runs once after `tickTime` ticks.
            The timer will call `onFinish` when it reaches zero.
        */
        return new Timer(tickTime, tickTime, null, onFinish);
    }

    @Override
    public void run() {
        if (this.tickTimeLeft == 0) {
            if (this.onFinish != null) {this.onFinish.run();}

            this.cancel();
            return;
        }

        this.tickTimeLeft -= this.tickPeriod;

        if (this.onTick != null) {this.onTick.accept(Math.abs(this.tickTimeLeft) / this.tickPeriod + 1);}
    }

    public void start() {
        this.runTaskTimer(GameLib.getInstance(), 0, this.tickPeriod);
    }
}