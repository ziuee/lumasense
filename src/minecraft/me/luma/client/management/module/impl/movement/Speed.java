package me.luma.client.management.module.impl.movement;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import me.luma.client.core.registry.impl.ClientLoader;
import me.luma.client.hud.notifications.NotificationManager;
import me.luma.client.hud.notifications.NotificationType;
import me.luma.client.management.event.EventTarget;
import me.luma.client.management.event.impl.EventMove;
import me.luma.client.management.event.impl.EventReceivePacket;
import me.luma.client.management.gui.clickgui.settings.SettingArrayList;
import me.luma.client.management.gui.clickgui.settings.SettingBoolean;
import me.luma.client.management.module.Category;
import me.luma.client.management.module.Module;
import me.luma.client.management.module.impl.combat.KillAura;
import me.luma.client.management.utils.MoveUtils;
import me.luma.client.management.utils.TimeHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.potion.Potion;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;

public class Speed extends Module {

	private boolean boosted;
	public static TimeHelper timer = new TimeHelper();
	TimeHelper lastCheck = new TimeHelper();

	double Motion;
	double speed;
	boolean collided = false, lessSlow;
	private double moveSpeed, lastDist, moveSpeedOG, lastDistOG;
	double less, stair;
	public boolean shouldslow = false;
	private int stage = 1;
	 
	SettingBoolean lagbackcheck;
	
	public SettingArrayList speedMode;
	
	public Speed() {
		super("Speed", Keyboard.KEY_V, Category.MOVEMENT);
		
		ArrayList<String> speedModes = new ArrayList<String>();
		speedModes.add("Verus");
		speedModes.add("Hypixel");
		speedModes.add("HypixelTimer");
		speedMode = new SettingArrayList("Speed Mode", this, speedModes, "Hypixel");
		
		lagbackcheck = new SettingBoolean("Lagback Check", this, true);
		
		//Speed = new SettingSlider("Speed", this, 0.36F, 0.01F, 1F, false, true);
	}
	
	@EventTarget
	public void onUpdate(EventMove event) {
		
		if(ClientLoader.loaderInstance.moduleManager.getModule("TargetStrafe").isToggled() && mc.thePlayer.onGround) {
			if(KillAura.entityAttacked != null && mc.thePlayer.getDistanceToEntity(KillAura.entityAttacked) < KillAura.reach.getSliderValue()) {
				mc.thePlayer.jump();
			}
		}
		
		if(speedMode.getArraListValue().equalsIgnoreCase("Verus")) {
			this.setDisplayName("Speed | ?7Verus");
			if(mc.thePlayer.onGround && (mc.gameSettings.keyBindRight.pressed || mc.gameSettings.keyBindLeft.pressed || mc.gameSettings.keyBindForward.pressed || mc.gameSettings.keyBindBack.pressed)) {
				mc.gameSettings.keyBindJump.pressed = true;
				//mc.thePlayer.jump();
			} else {
				mc.gameSettings.keyBindJump.pressed = false;
			}
			if(mc.thePlayer.hurtTime > 9) {
	            speed = 1;
	        }else {
	            if(!mc.thePlayer.onGround) {
	            	speed = 0.34; // remove on release
	            	//speed = 0.369;;
	            }else {
	            	speed = 0.2;
	                //speed = 0.5;
	            }
	        }
	        setSpeed2(speed);
		}
		
		if(speedMode.getArraListValue().equalsIgnoreCase("Hypixel")) {
			this.setDisplayName("Speed | ?7Hypixel");
			if(mc.thePlayer.onGround && (mc.gameSettings.keyBindRight.pressed || mc.gameSettings.keyBindLeft.pressed || mc.gameSettings.keyBindForward.pressed || mc.gameSettings.keyBindBack.pressed)) {
				mc.thePlayer.jump();
			}
			if (mc.thePlayer.isCollidedHorizontally) {
	            collided = true;
	        }
	        if (collided) {
	            mc.timer.timerSpeed = 1.2f;
	            stage = -1;
	        }
	        if (stair > 0)
	            stair -= 0.25;
	        less -= less > 1 ? 0.12 : 0.11;
	        if (less < 0)
	            less = 0;
	        if (!isInLiquid() && MoveUtils.isOnGround(0.01) && (isMoving2())) {
	            collided = mc.thePlayer.isCollidedHorizontally;
	            if (stage >= 0 || collided) {
	                stage = 0;

	                double motY = 0.407 + MoveUtils.getJumpEffect() * 0.1;
	                if (stair == 0) {
	                    mc.thePlayer.jump();
	                    event.setY(mc.thePlayer.motionY = motY);
	                } else {

	                }

	                less++;
	                if (less > 1 && !lessSlow)
	                    lessSlow = true;
	                else
	                    lessSlow = false;
	                if (less > 1.32)
	                    less = 1.32;
	            }
	        }
	        speed = getHypixelSpeed(stage) + 0.02322; // 0.0232 max, might flag
	        speed *= 0.81;
	        if (stair > 0) {
	            speed *= 0.67 - MoveUtils.getSpeedEffect() * 0.1;
	        }

	        if (stage < 0)
	            speed = MoveUtils.defaultSpeed();
	        if (lessSlow) {
	            speed *= 0.955;
	        }


	        if (isInLiquid()) {
	            speed = 0.22;
	        }

	        if ((mc.thePlayer.moveForward != 0.0f || mc.thePlayer.moveStrafing != 0.0f)) {
	            setMotion(event, speed);
	            ++stage;
	        }
		}
		
		if(speedMode.getArraListValue().equalsIgnoreCase("HypixelTimer")) {
			this.setDisplayName("Speed | ?7HypixelTimer");
			if(mc.thePlayer.onGround && (mc.gameSettings.keyBindRight.pressed || mc.gameSettings.keyBindLeft.pressed || mc.gameSettings.keyBindForward.pressed || mc.gameSettings.keyBindBack.pressed)) {
				mc.thePlayer.jump();
			}
			if (mc.thePlayer.isCollidedHorizontally) {
	            collided = true;
	        }
	        if (collided) {
	            mc.timer.timerSpeed = 1;
	            stage = -1;
	        }
	        if (stair > 0)
	            stair -= 0.25;
	        less -= less > 1 ? 0.12 : 0.11;
	        if (less < 0)
	            less = 0;
	        if (!isInLiquid() && MoveUtils.isOnGround(0.01) && (isMoving2())) {
	            collided = mc.thePlayer.isCollidedHorizontally;
	            if (stage >= 0 || collided) {
	                stage = 0;

	                double motY = 0.407 + MoveUtils.getJumpEffect() * 0.1;
	                if (stair == 0) {
	                    mc.thePlayer.jump();
	                    event.setY(mc.thePlayer.motionY = motY);
	                } else {

	                }

	                less++;
	                if (less > 1 && !lessSlow)
	                    lessSlow = true;
	                else
	                    lessSlow = false;
	                if (less > 1.12)
	                    less = 1.12;
	            }
	        }
	        speed = getHypixelSpeedTimer(stage) + 0.02322; // 0.0232 max, might flag
	        speed *= 0.82;
	        if (stair > 0) {
	            speed *= 0.67 - MoveUtils.getSpeedEffect() * 0.1;
	        }
	        
	        if(!mc.thePlayer.onGround) {
	        	speed *= 1;
	        }

	        if (stage < 0)
	            speed = MoveUtils.defaultSpeed();
	        if (lessSlow) {
	            speed *= 0.925;
	        }


	        if (isInLiquid()) {
	            speed = 0.42;
	        }

	        if ((mc.thePlayer.moveForward != 0.0f || mc.thePlayer.moveStrafing != 0.0f)) {
	            setMotion(event, speed);
	            ++stage;
	        }
		}
		
		//setSpeed(Speed.getSliderValue());
        /*if(mc.thePlayer.hurtTime > 9) {
            speed = 1;
        }else {
            if(!mc.thePlayer.onGround) {
            	speed = 0.34; // remove on release
            	//speed = 0.369;;
            }else {
            	speed = 0.2;
                //speed = 0.5;
            }
        }
        setSpeed2(speed);*/
		
		/*if(timerUtil.hasReached(600)) {
			Random r = new Random();
			this.mc.thePlayer.sendChatMessage("LumaSense B1 | Made by ziue | Buy https://discord.gg/vP4nPb3z | [" + r.nextInt(346262) + "]");
	    	this.timerUtil.reset();
		}*/
		//mc.thePlayer.jump();
		//ClientLoader.addChatMessage("BPS " + getBlocksPerSecond());
	}
	
	/*public static void setSpeed(double speed) {
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
    }*/
	
	@EventTarget
	public void onReceivePacket(EventReceivePacket e) {
		Packet packet = e.getPacket();
		if(packet instanceof S08PacketPlayerPosLook) {
			if(lagbackcheck.getBooleanValue()) {
				mc.thePlayer.onGround = false;
				mc.thePlayer.motionX *= 0;
				mc.thePlayer.motionZ *= 0;
				mc.thePlayer.jumpMovementFactor = 0;
				NotificationManager.show(NotificationType.INFO, "Lagback", EnumChatFormatting.RED + "Disabled ?r" + this.getName(), 2);
				toggle();
			} else if(lastCheck.delay(300)) {
				S08PacketPlayerPosLook.yaw = mc.thePlayer.rotationYaw;
				S08PacketPlayerPosLook.pitch = mc.thePlayer.rotationPitch;
			}
		}
	}
	
	private void setMotion(EventMove em, double speed) {
        double forward = mc.thePlayer.movementInput.moveForward;
        double strafe = mc.thePlayer.movementInput.moveStrafe;
        float yaw = mc.thePlayer.rotationYaw;
        if ((forward == 0.0D) && (strafe == 0.0D)) {
            em.setX(0.0D);
            em.setZ(0.0D);
        } else {
            if (forward != 0.0D) {
                if (strafe > 0.0D) {
                    yaw += (forward > 0.0D ? -45 : 45);
                } else if (strafe < 0.0D) {
                    yaw += (forward > 0.0D ? 45 : -45);
                }
                strafe = 0.0D;
                if (forward > 0.0D) {
                    forward = 1;
                } else if (forward < 0.0D) {
                    forward = -1;
                }
            }
            em.setX(forward * speed * Math.cos(Math.toRadians(yaw + 90.0F)) + strafe * speed * Math.sin(Math.toRadians(yaw + 90.0F)));
            em.setZ(forward * speed * Math.sin(Math.toRadians(yaw + 90.0F)) - strafe * speed * Math.cos(Math.toRadians(yaw + 90.0F)));
        }
    }
	
	public static boolean isInLiquid() {
        if (mc.thePlayer.isInWater()) {
            return true;
        }
        boolean inLiquid = false;
        final int y = (int) mc.thePlayer.getEntityBoundingBox().minY;
        for (int x = MathHelper.floor_double(mc.thePlayer.getEntityBoundingBox().minX); x < MathHelper.floor_double(mc.thePlayer.getEntityBoundingBox().maxX) + 1; x++) {
            for (int z = MathHelper.floor_double(mc.thePlayer.getEntityBoundingBox().minZ); z < MathHelper.floor_double(mc.thePlayer.getEntityBoundingBox().maxZ) + 1; z++) {
                final Block block = mc.theWorld.getBlockState(new BlockPos(x, y, z)).getBlock();
                if (block != null && block.getMaterial() != Material.air) {
                    if (!(block instanceof BlockLiquid)) return false;
                    inLiquid = true;
                }
            }
        }
        return inLiquid;
    }
	
	public static boolean isMoving2() {
		return ((mc.thePlayer.moveForward != 0.0F || mc.thePlayer.moveStrafing != 0.0F));
    }
	
	private double getHypixelSpeed(int stage) {
        double value = MoveUtils.defaultSpeed() + (0.028 * MoveUtils.getSpeedEffect()) + (double) MoveUtils.getSpeedEffect() / 15;
        double firstvalue = 0.4145 + (double) MoveUtils.getSpeedEffect() / 12.5;
        double decr = (((double) stage / 500) * 2);


        if (stage == 0) {
            //JUMP
            if (timer.delay(300)) {
                timer.reset();
                mc.timer.timerSpeed = 1.08f;
            }
            if (!lastCheck.delay(500)) {
                if (!shouldslow)
                    shouldslow = true;
            } else {
                if (shouldslow)
                    shouldslow = false;
            }
            value = 0.71 + (MoveUtils.getSpeedEffect() + (0.038 * MoveUtils.getSpeedEffect())) * 0.147;

        } else if (stage == 1) {
            if (mc.timer.timerSpeed == 1.354f) {
                //mc.timer.timerSpeed = 1.254f;
            }
            value = firstvalue;
        } else if (stage >= 2) {
            if (mc.timer.timerSpeed == 1.254f) {
                //mc.timer.timerSpeed = 1f;
            }
            value = firstvalue - decr;
        }
        if (shouldslow || !lastCheck.delay(500) || collided) {
            value = 0.2;
            if (stage == 0)
                value = 0;
        }


        return Math.max(value, shouldslow ? value : MoveUtils.defaultSpeed() + (0.028 * MoveUtils.getSpeedEffect()));
    }
	
	private double getHypixelSpeedTimer(int stage) {
        double value = MoveUtils.defaultSpeed() + (0.028 * MoveUtils.getSpeedEffect()) + (double) MoveUtils.getSpeedEffect() / 15;
        double firstvalue = 0.4145 + (double) MoveUtils.getSpeedEffect() / 12.5;
        double decr = (((double) stage / 500) * 2);


        if (stage == 0) {
            //JUMP
            if (timer.delay(300)) {
                timer.reset();
                mc.timer.timerSpeed = 1.3f;
            }
            if (!lastCheck.delay(500)) {
                if (!shouldslow)
                    shouldslow = true;
            } else {
                if (shouldslow)
                    shouldslow = false;
            }
            value = 0.71 + (MoveUtils.getSpeedEffect() + (0.038 * MoveUtils.getSpeedEffect())) * 0.147;

        } else if (stage == 1) {
            if (mc.timer.timerSpeed == 1.354f) {
                //mc.timer.timerSpeed = 1.254f;
            }
            value = firstvalue;
        } else if (stage >= 2) {
            if (mc.timer.timerSpeed == 1.254f) {
                //mc.timer.timerSpeed = 1f;
            }
            value = firstvalue - decr;
        }
        if (shouldslow || !lastCheck.delay(500) || collided) {
            value = 0.2;
            if (stage == 0)
                value = 0;
        }


        return Math.max(value, shouldslow ? value : MoveUtils.defaultSpeed() + (0.028 * MoveUtils.getSpeedEffect()));
    }
	
	public static void setSpeed(double moveSpeed, float yaw, double strafe, double forward) {

        double fforward = forward;
        double sstrafe = strafe;
        float yyaw = yaw;
        if (forward != 0.0D) {
            if (strafe > 0.0D) {
                yaw += ((forward > 0.0D) ? -45 : 45);
            } else if (strafe < 0.0D) {
                yaw += ((forward > 0.0D) ? 45 : -45);
            }
            strafe = 0.0D;
            if (forward > 0.0D) {
                forward = 1.0D;
            } else if (forward < 0.0D) {
                forward = -1.0D;
            }
        }
        if (strafe > 0.0D) {
            strafe = 1.0D;
        } else if (strafe < 0.0D) {
            strafe = -1.0D;
        }
        double mx = Math.cos(Math.toRadians((yaw + 90.0F)));
        double mz = Math.sin(Math.toRadians((yaw + 90.0F)));
        mc.thePlayer.motionX = forward * moveSpeed * mx + strafe * moveSpeed * mz;
        mc.thePlayer.motionZ = forward * moveSpeed * mz - strafe * moveSpeed * mx;
    }
	
	public static void setSpeed2(double moveSpeed) {
        setSpeed(moveSpeed, mc.thePlayer.rotationYaw, mc.thePlayer.movementInput.moveStrafe, mc.thePlayer.movementInput.moveForward);
    }
	
	@Override
	public void onEnable() {
		super.onEnable();
		lastCheck.reset();
		timer.reset();
	}
	
	@Override
	public void onDisable() {
		super.onDisable();
		lastCheck.reset();
		timer.reset();
		mc.thePlayer.stepHeight = 0.5F;
		mc.timer.timerSpeed = 1F;
	}
	
	private double getBaseMoveSpeed() {
        double baseSpeed = 0.2873;
        if (mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
            final int amplifier = mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier();
            baseSpeed *= 1.0 + (0.2 * amplifier);
        }
        return baseSpeed;
    }
	
	public double getPosForSetPosX(double value) {
		double yaw = Math.toRadians(Minecraft.getMinecraft().thePlayer.rotationYaw);
		double x = -Math.sin(yaw) * value;
		return x;
	}

	public double getPosForSetPosZ(double value) {
		double yaw = Math.toRadians(Minecraft.getMinecraft().thePlayer.rotationYaw);
		double z = Math.cos(yaw) * value;
		return z;
	}
	
	public static float getDirection() {
		float var1 = mc.thePlayer.rotationYaw;
		if (mc.thePlayer.moveForward < 0.0F) {
	           var1 += 180.0F;
	        }

	        float forward = 1.0F;
	        if (mc.thePlayer.moveForward < 0.0F) {
	           forward = -0.5F;
	        } else if (mc.thePlayer.moveForward > 0.0F) {
	           forward = 0.5F;
	        }

	        if (mc.thePlayer.moveStrafing > 0.0F) {
	           var1 -= 90.0F * forward;
	        }

	        if (mc.thePlayer.moveStrafing < 0.0F) {
	           var1 += 90.0F * forward;
	        }

	        var1 *= 0.017453292F;
	        return var1;
	}

}
