package me.luma.client.management.module.impl.movement;

import org.lwjgl.input.Keyboard;

import me.luma.client.management.event.EventTarget;
import me.luma.client.management.event.impl.EventUpdate;
import me.luma.client.management.module.Category;
import me.luma.client.management.module.Module;
import net.minecraft.util.MovementInput;

public class LongJump extends Module {
	
	private static float groundTicks;

	public LongJump() {
		super("LongJump", Keyboard.KEY_X, Category.MOVEMENT);
	}
	
	@EventTarget
	public void onUpdate(EventUpdate event) {
		if(mc.thePlayer.onGround && (mc.gameSettings.keyBindRight.pressed || mc.gameSettings.keyBindLeft.pressed || mc.gameSettings.keyBindForward.pressed || mc.gameSettings.keyBindBack.pressed)) {
			if(groundTicks > 0) {
				groundTicks = 0;
				this.toggle();
				return;
			}
			groundTicks++;
			mc.thePlayer.jump();
		}
        setSpeed(1.7);
	}
	
	public static void setSpeed(double speed) {
        double forward = MovementInput.moveForward;
        double strafe = MovementInput.moveStrafe;
        float yaw = mc.thePlayer.rotationYaw;
        if (forward == 0.0 && strafe == 0.0) {
            mc.thePlayer.motionX = 0.0;
            mc.thePlayer.motionZ = 0.0;
        } else {
            if (forward != 0.0) {
                if (strafe > 0.0) {
                    yaw += (float)(forward > 0.0 ? -45 : 45);
                } else if (strafe < 0.0) {
                    yaw += (float)(forward > 0.0 ? 45 : -45);
                }
                strafe = 0.0;
                if (forward > 0.0) {
                    forward = 1.0;
                } else if (forward < 0.0) {
                    forward = -1.0;
                }
            }
            mc.thePlayer.motionX = forward * speed * Math.cos(Math.toRadians(yaw + 90.0f)) + strafe * speed * Math.sin(Math.toRadians(yaw + 90.0f));
            mc.thePlayer.motionZ = forward * speed * Math.sin(Math.toRadians(yaw + 90.0f)) - strafe * speed * Math.cos(Math.toRadians(yaw + 90.0f));
        }
    }

	@Override
	public void onEnable() {
		super.onEnable();
	}
	
	@Override
	public void onDisable() {
		super.onDisable();
		setSpeed(1);
		mc.timer.timerSpeed = 1F;
	}
	
}
