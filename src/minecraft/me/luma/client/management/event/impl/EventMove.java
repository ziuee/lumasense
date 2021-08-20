package me.luma.client.management.event.impl;

import me.luma.client.management.event.Event;

public class EventMove extends Event {
	
	public double x;
	public double y;
	public double z;
	
	public EventMove(double x, double y, double z) {
		this.x = x;
	    this.y = y;
	    this.z = z;
	}
	
	public double getX() {
		return x;
	}

	public double getZ() {
		return z;
	}

	public double getY() {
		return y;
	}

	public void setX(double x) {
		this.x = x;
	}

	public void setY(double y) {
		this.y = y;
	}

	public void setZ(double z) {
		this.z = z;
	}

}
