package me.luma.client.management.gui.alt.threading;

import java.util.ArrayList;
import java.util.List;

public class AltManager
{
    public static List<Alt> alts;
    public static Alt lastAlt;
    
    public static void init() {
        setupAlts();
        getAlts();
    }
    
    public Alt getLastAlt() {
        return AltManager.lastAlt;
    }
    
    public void setLastAlt(final Alt alt) {
        AltManager.lastAlt = alt;
    }
    
    public static void setupAlts() {
        AltManager.alts = new ArrayList<Alt>();
    }
    
    public static List<Alt> getAlts() {
        return AltManager.alts;
    }
}
