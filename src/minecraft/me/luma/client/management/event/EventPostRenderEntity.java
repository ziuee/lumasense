package me.luma.client.management.event;

import net.minecraft.entity.Entity;

public class EventPostRenderEntity extends Event {
	private Entity ent;
	
	public EventPostRenderEntity(Entity ent) { 
		this.ent = ent; 
	}
	
	public Entity getEntity() { 
		return this.ent; 
	}
}
