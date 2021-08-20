package me.luma.client.management.event.impl;

import me.luma.client.management.event.Event;
import net.minecraft.entity.Entity;

public class EventStep extends Event {
	
	public float stepHeight;
    public Entity entity;

    public EventStep(float stepHeight, Entity entity) {
        this.stepHeight = stepHeight;
        this.entity = entity;
    }

    public float getStepHeight() {
        return stepHeight;
    }

    public void setStepHeight(float stepHeight) {
        this.stepHeight = stepHeight;
    }

    public Entity getEntity() {
        return entity;
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
    }
}
