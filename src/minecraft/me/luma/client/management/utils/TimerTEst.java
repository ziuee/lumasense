package me.luma.client.management.utils;

public final class TimerTEst
{
    private long currentMs;
    
    public long lastMS = System.currentTimeMillis();
    
    public TimerTEst() {
        this.reset();
    }
    
    public boolean hasTimeElapsed(long time, boolean reset) {
        if (this.currentMs >= time) {
            if (reset) {
                this.reset();
            }
            return true;
        }
        return false;
    }
    
    public long lastReset() {
        return this.currentMs;
    }
    
    public boolean hasElapsed(final long milliseconds) {
        return this.elapsed() > milliseconds;
    }
    
    public long elapsed() {
        return System.currentTimeMillis() - this.currentMs;
    }
    
    public boolean isDelayComplete(float f) {
        if(System.currentTimeMillis() - this.lastMS >= f) {
            return true;
        }
        return false;
    }
    
    public void reset() {
        this.currentMs = System.currentTimeMillis();
    }
    public boolean delay(final float milliSec) {
        return currentMs > milliSec;
    }
    
    public void setTime(long time) {
        lastMS = time;
    }
}