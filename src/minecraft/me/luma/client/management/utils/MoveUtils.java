package me.luma.client.management.utils;

import me.luma.client.management.event.impl.EventMove;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.potion.Potion;
import net.minecraft.util.BlockPos;

public class MoveUtils {

	public static double defaultSpeed() {
        double baseSpeed = 0.2873D;
        if (Minecraft.getMinecraft().thePlayer.isPotionActive(Potion.moveSpeed)) {
            int amplifier = Minecraft.getMinecraft().thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier();
          //  if(((Options) settings.get(MODE).getValue()).getSelected().equalsIgnoreCase("Hypixel")){
           // 	baseSpeed *= (1.0D + 0.225D * (amplifier + 1));
           // }else
            	baseSpeed *= (1.0D + 0.2D * (amplifier + 1));
        }
        return baseSpeed;
    }
	
	public static boolean isMoving() {
        return Minecraft.getMinecraft().gameSettings.keyBindForward.isKeyDown()
                || Minecraft.getMinecraft().gameSettings.keyBindBack.isKeyDown()
                || Minecraft.getMinecraft().gameSettings.keyBindLeft.isKeyDown()
                || Minecraft.getMinecraft().gameSettings.keyBindRight.isKeyDown();
    }
	
	public static double getBaseSpeed() {
        double baseSpeed = 0.2873;
        if (Minecraft.getMinecraft().thePlayer.isPotionActive(Potion.moveSpeed)) {
            final int amplifier = Minecraft.getMinecraft().thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier();
            baseSpeed *= 1.0 + 0.2 * (amplifier + 1);
        }
        return baseSpeed;
    }
	
	public static Block getBlockAtPos(BlockPos inBlockPos) {
        IBlockState s = Minecraft.getMinecraft().theWorld.getBlockState(inBlockPos);
        return s.getBlock();
    }
	
	public final static void doStrafe(double speed) {
        if(!isMoving()) return;

        final double yaw = getYaw(true);
        Minecraft.getMinecraft().thePlayer.motionX = -Math.sin(yaw) * speed;
        Minecraft.getMinecraft().thePlayer.motionZ = Math.cos(yaw) * speed;
    }
	
	public final static double getYaw(boolean strafing) {
        float rotationYaw = strafing ? Minecraft.getMinecraft().thePlayer.rotationYawHead : Minecraft.getMinecraft().thePlayer.rotationYaw;
        float forward = 1F;

        final double moveForward = Minecraft.getMinecraft().thePlayer.movementInput.moveForward;
        final double moveStrafing = Minecraft.getMinecraft().thePlayer.movementInput.moveStrafe;
        final float yaw = Minecraft.getMinecraft().thePlayer.rotationYaw;
        
        if (moveForward < 0) {
        	rotationYaw += 180F;
        }

        if (moveForward < 0) {
        	forward = -0.5F;
        } else if(moveForward > 0) {
        	forward = 0.5F;
        }

        if (moveStrafing > 0) {
        	rotationYaw -= 90F * forward;
        } else if(moveStrafing < 0) {
        	rotationYaw += 90F * forward;
        }

        return Math.toRadians(rotationYaw);
    }
	
	public static void setMoveSpeed(EventMove eventMotion, double speed, String mode) {
		
		double f = Minecraft.getMinecraft().thePlayer.movementInput.moveForward;
		double s = Minecraft.getMinecraft().thePlayer.movementInput.moveStrafe;
		
		float playerYaw = Minecraft.getMinecraft().thePlayer.rotationYaw;
		if(f != 0) {
			if(s > 0) {
				playerYaw += (f > 0 ? -42 : 42);
			} else if (s < 0) {
                playerYaw += (f > 0 ? 42 : -42);
            }
			s = 0;
			if(f > 0) {
				f = 1;
			} else if (f < 0) {
				f = -1;
			}
		}
		
		Minecraft.getMinecraft().thePlayer.motionX = f * speed * Math.cos(Math.toRadians(playerYaw + 90)) + s * speed * Math.sin(Math.toRadians(playerYaw + 90));
		Minecraft.getMinecraft().thePlayer.motionZ = f * speed * Math.sin(Math.toRadians(playerYaw + 90)) - s * speed * Math.cos(Math.toRadians(playerYaw + 90));
	}
	
	public static void setMotion(EventMove e, final double speed) {
        double forward = Minecraft.getMinecraft().thePlayer.movementInput.moveForward;
        double strafe = Minecraft.getMinecraft().thePlayer.movementInput.moveStrafe;
        float yaw = Minecraft.getMinecraft().thePlayer.rotationYaw;
        if (forward == 0.0 && strafe == 0.0) {
        	if (e != null) {
        		e.setX(0);
        		e.setZ(0);
        	} else {
                Minecraft.getMinecraft().thePlayer.motionX = 0.0;
                Minecraft.getMinecraft().thePlayer.motionZ = 0.0;
        	}
        }
        else {
            if (forward != 0.0) {
                if (strafe > 0.0) {
                    yaw += ((forward > 0.0) ? -45 : 45);
                }
                else if (strafe < 0.0) {
                    yaw += ((forward > 0.0) ? 45 : -45);
                }
                strafe = 0.0;
                if (forward > 0.0) {
                    forward = 1.0;
                }
                else if (forward < 0.0) {
                    forward = -1.0;
                }
            }
            if (e != null) {
                e.setX(forward * speed * Math.cos(Math.toRadians(yaw + 90.0f)) + strafe * speed * Math.sin(Math.toRadians(yaw + 91.50F)));
                e.setZ(forward * speed * Math.sin(Math.toRadians(yaw + 90.0f)) - strafe * speed * Math.cos(Math.toRadians(yaw + 90.25F)));
            } else {
            	Minecraft.getMinecraft().thePlayer.motionX = forward * speed * Math.cos(Math.toRadians(yaw + 90.0f)) + strafe * speed * Math.sin(Math.toRadians(yaw + 91.50F));
            	Minecraft.getMinecraft().thePlayer.motionZ = forward * speed * Math.sin(Math.toRadians(yaw + 90.0f)) - strafe * speed * Math.cos(Math.toRadians(yaw + 90.25F));
            }
        }
    }
	
	public static boolean isBlockUnderneath(BlockPos pos) {
	    for (int k = 0; k < pos.getY() + 1; k++) {
	      if (Minecraft.getMinecraft().theWorld.getBlockState(new BlockPos(pos.getX(), k, pos.getZ())).getBlock().getMaterial() != Material.air)
	        return true; 
	    } 
	    return false;
	  }
	
	public static int getSpeedEffect() {
        if (Minecraft.getMinecraft().thePlayer.isPotionActive(Potion.moveSpeed)) {
            return Minecraft.getMinecraft().thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier() + 1;
        }
        return 0;
    }
	
	public static int getJumpEffect() {
        if (Minecraft.getMinecraft().thePlayer.isPotionActive(Potion.jump)) {
            return  Minecraft.getMinecraft().thePlayer.getActivePotionEffect(Potion.jump).getAmplifier() + 1;
        }
        return 0;
    }
	
	public static boolean isOnGround(double height) {
        if (!Minecraft.getMinecraft().theWorld.getCollidingBoundingBoxes(Minecraft.getMinecraft().thePlayer, Minecraft.getMinecraft().thePlayer.getEntityBoundingBox().offset(0.0D, -height, 0.0D)).isEmpty()) {
            return true;
        } else {
            return false;
        }
    }
	
	public static boolean isOnGround() {
		return Minecraft.getMinecraft().thePlayer.onGround && Minecraft.getMinecraft().thePlayer.isCollidedVertically;
	}
	
}
