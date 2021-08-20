package me.luma.client.management.utils;

import net.minecraft.client.Minecraft;

public class TEstTimer {
    public long lastMS = System.currentTimeMillis();

    public void reset() {
        this.lastMS = System.currentTimeMillis();
    }

    public boolean hasTimeElapsed(long time, boolean reset) {
        if (System.currentTimeMillis() - this.lastMS > time) {
            if (reset) {
                this.reset();
            }

            return true;
        } else {
            return false;
        }
    }

    public long getTime() {
        return System.currentTimeMillis() - this.lastMS;
    }

    public void setTime(long time) {
        this.lastMS = time;
    }

    public void mcTimeSpeed(double speed) {
        Minecraft.getMinecraft().timer.timerSpeed = (float)speed;
    }
}

