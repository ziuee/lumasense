package me.luma.client.management.event.impl;

import me.luma.client.management.event.Event;

public class Event2D extends Event {
	
	private float width, height;
	
	public Event2D(float width, float height) {
		this.width = width;
		this.height = height;
	}
	
	/*public Event2D(float ticks, float width, float height) {
		this.partialTicks = ticks;
		this.width = width;
		this.height = height;
	}*/
	
	float partialTicks;
	
	public float getTicks() {
		return this.partialTicks;
	}
	
	public float getWidth() {
		return width;
	}
	
	public float getHeight() {
		return height;
	}

}
