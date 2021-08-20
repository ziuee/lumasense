package me.luma.client.management.event.impl;

import me.luma.client.management.event.Event;
import net.minecraft.client.Minecraft;

public class EventUpdate extends Event {
	public boolean onGround;
	public float yaw;
	public float pitch;
	public double y;
	public boolean pre;

	public EventUpdate(final float yaw, final float pitch, final double y, final boolean onGround, final boolean pre) {
		this.yaw = yaw;
		this.pitch = pitch;
		this.y = y;
		this.onGround = onGround;
		this.pre = pre;
	}

	public EventUpdate(final float yaw, final float pitch, final double y, final boolean onGround) {
		this.yaw = yaw;
		this.pitch = pitch;
		this.y = y;
		this.onGround = onGround;
	}
	
	public float getYaw() {
		return yaw;
	}

	public float getPitch() {
		return pitch;
	}

	public double getY() {
		return y;
	}

	public boolean isOnGround() {
		return onGround;
	}

	public void setYaw(float yaw) {
		Minecraft.getMinecraft().thePlayer.renderYawOffset = yaw;
		Minecraft.getMinecraft().thePlayer.rotationYawHead = yaw;
		this.yaw = yaw;
	}

	public void setPitch(float pitch) {
		this.pitch = pitch;
	}

	public void setRotations(float[] rotations) {
		setYaw(rotations[0]);
		setPitch(rotations[1]);
	}

	public void setY(double y) {
		this.y = y;
	}

	public void setOnGround(boolean onGround) {
		this.onGround = onGround;
	}

	public boolean isPre() {
		return pre;
	}
}
