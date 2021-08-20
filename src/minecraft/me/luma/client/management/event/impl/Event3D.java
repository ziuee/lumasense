package me.luma.client.management.event.impl;

import me.luma.client.management.event.Event;
import net.minecraft.client.gui.ScaledResolution;

public class Event3D extends Event {
	
    public static float partialTicks;
    
    public Event3D(float partialTicks) {
        this.partialTicks = partialTicks;
    }
    
    public static float getPartialTicks() {
        return partialTicks;
    }
}
