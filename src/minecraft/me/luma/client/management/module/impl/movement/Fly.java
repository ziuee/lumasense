package me.luma.client.management.module.impl.movement;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import me.luma.client.core.registry.impl.ClientLoader;
import me.luma.client.management.event.EventTarget;
import me.luma.client.management.event.impl.EventMove;
import me.luma.client.management.event.impl.EventReceivePacket;
import me.luma.client.management.event.impl.EventSendPacket;
import me.luma.client.management.event.impl.EventUpdate;
import me.luma.client.management.gui.clickgui.settings.SettingArrayList;
import me.luma.client.management.gui.clickgui.settings.SettingBoolean;
import me.luma.client.management.gui.clickgui.settings.SettingSlider;
import me.luma.client.management.module.Category;
import me.luma.client.management.module.Module;
import me.luma.client.management.module.impl.combat.TargetStrafe;
import me.luma.client.management.utils.MathUtils;
import me.luma.client.management.utils.MoveUtils;
import me.luma.client.management.utils.Stopwatch;
import me.luma.client.management.utils.TimerTEst;
import net.minecraft.block.Block;
import net.minecraft.block.BlockGlass;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.Packet;
import net.minecraft.network.handshake.client.C00Handshake;
import net.minecraft.network.login.client.C00PacketLoginStart;
import net.minecraft.network.play.client.C00PacketKeepAlive;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.MovementInput;
import net.minecraft.util.Timer;
import net.minecraft.util.Vec3;

public class Fly extends Module {
	
	public static ArrayList<Packet> packetListZ = new ArrayList<Packet>();
	
	TimerTEst timerUtil = new TimerTEst();
	private final Stopwatch stopwatch = new Stopwatch();
    public double positionOffset, speedMult, stageTwoMult, startOffset, jumpModifier, damageOffset, damageY, damageYTwo, yDown;
    private int ticks;
    private Vec3 lastPos;
    private boolean tp;
	private int stage;
	private double moveSpeed;
	private double lastDist;
	private static double yPos;
	private static boolean damaged;
	private boolean boosted;
	private boolean hypixelboost;
    private boolean decreasing2;
    private boolean canboost;
    private static double hypixel = 0;
    private float timervalue;
    private int delay = 0;
    double count;
    int level;
    
    SettingBoolean Damage;
    SettingBoolean blink;
    SettingBoolean flyMultiplier;
    SettingSlider multiplytime;
    SettingSlider multiplyspeed;
    SettingSlider speed;
    SettingArrayList FlyMode;
    
	public Fly() {
		super("Fly", Keyboard.KEY_F, Category.MOVEMENT);
		
		ArrayList<String> flymodeS = new ArrayList<String>();
		flymodeS.add("Verus");
		flymodeS.add("Vanilla");
		flymodeS.add("Hypixel");
		flymodeS.add("HypixelDev");
		FlyMode = new SettingArrayList("Fly Mode", this, flymodeS, "HypixelDev");
		
		//multiplyspeed = new SettingSlider("Multiplier Speed", this, 4, 3, 5, true, false);
		//multiplytime = new SettingSlider("Multiplier Time", this, 100.0D, 50.0D, 3000.0D, true, false);
		
		//flyMultiplier = new SettingBoolean("Multiplier", this, true);
		Damage = new SettingBoolean("Damage", this, true);
		blink = new SettingBoolean("Blink", this, false);
		
	}
	
	@EventTarget
	public void onUpdate(EventUpdate event) {
		if(FlyMode.getArraListValue().equalsIgnoreCase("Vanilla")) {
			mc.thePlayer.capabilities.isFlying = true;
			mc.thePlayer.motionY = 0;
			//mc.thePlayer.onGround = true;
			if (mc.gameSettings.keyBindJump.isKeyDown()) {
				mc.thePlayer.motionY = 0.8;
	        }
	        if (mc.gameSettings.keyBindSneak.isKeyDown()) {
	            mc.thePlayer.motionY = -0.8;
	        }
			Speed.setSpeed2(4);
		}
		if(FlyMode.getArraListValue().equalsIgnoreCase("Verus")) {
			mc.thePlayer.fallDistance = 0.0f;
			mc.thePlayer.motionX = 0.0;
	        mc.thePlayer.motionY = 0.0;
	        mc.thePlayer.motionZ = 0.0;
	        mc.thePlayer.posY += 1.1;
	        mc.thePlayer.posY -= 0.1;
			if (MoveUtils.isMoving()) {
				if (mc.thePlayer.ticksExisted % 1 == 0) {
					mc.thePlayer.motionY = 0;
					mc.timer.timerSpeed = 0.2F;
					MoveUtils.doStrafe(9);
				} else {
					mc.thePlayer.motionY = -.1;
					mc.timer.timerSpeed = 0.2F;
					MoveUtils.doStrafe(3.0);
				}
			} else {
				stop(true);
				mc.timer.timerSpeed = 0.2F;
			}
		}
		
		if(FlyMode.getArraListValue().equalsIgnoreCase("Hypixel")) {
			mc.thePlayer.fallDistance = 0.0f; //Verus Values
	        mc.thePlayer.motionX = 0.0;
	        mc.thePlayer.motionY = 0.0;
	        mc.thePlayer.motionZ = 0.0;
	        mc.thePlayer.posY += 0.1;
	        mc.thePlayer.posY -= 0.1;
			//mc.timer.timerSpeed = 0.25f;
			if (mc.gameSettings.keyBindJump.isKeyDown()) {
				mc.thePlayer.motionY = 0.4;
	        }
	        if (mc.gameSettings.keyBindSneak.isKeyDown()) {
	            mc.thePlayer.motionY = -0.4;
	        }
	        mc.thePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ);
	        mc.thePlayer.stepHeight = 0f;
	        mc.thePlayer.cameraYaw = 0.00f;
	        if(boosted && timerUtil.hasElapsed(200)) {
	        	mc.thePlayer.cameraYaw = 0.06f;
	        } else {
	        	mc.thePlayer.cameraYaw = 0.00f;
	        }
	        mc.thePlayer.onGround = true;
	        if (packetListZ.size() >= 250) {
	            //sendPackets();
	        }
	        if (mc.thePlayer.hurtTime > 3)
	            damaged = true;
	        mc.thePlayer.motionY = 0.0;
	        
	        if (!this.boosted) {
                Timer.timerSpeed = 1f;
                this.boosted = true;
            } else if (this.timerUtil.hasElapsed(1500)) {
                Timer.timerSpeed = 1f;
                MoveUtils.setMotion(null, 0.0);
            }
            final double xDist = mc.thePlayer.posX - mc.thePlayer.prevPosX;
            final double zDist = mc.thePlayer.posZ - mc.thePlayer.prevPosZ;
            this.lastDist = Math.sqrt(xDist * xDist + zDist * zDist);
            if (this.canboost && this.hypixelboost) {
                this.timervalue += (float) (this.decreasing2 ? -0.01 : 0.05);
                if (this.timervalue >= 1.4) {
                    this.decreasing2 = true;
                }
                if (this.timervalue <= 0.9) {
                    this.decreasing2 = false;
                }
                if (this.timerUtil.hasElapsed(2000)) {
                    this.canboost = false;
                    mc.thePlayer.cameraYaw = 0.105f;
                }
            }
	        
			/*mc.thePlayer.fallDistance = 0.0f;
	        mc.thePlayer.motionX = 0.0;
	        mc.thePlayer.motionY = 0.0;
	        mc.thePlayer.motionZ = 0.0;
	        mc.thePlayer.posY += 0.1;
	        mc.thePlayer.posY -= 0.1;
	        mc.timer.timerSpeed = 0.25F;
	        if(flyMultiplier.getBooleanValue() && !mc.gameSettings.keyBindJump.isKeyDown()) {
	        	if (!timerUtil.hasElapsed(multiplytime.getSliderValue().longValue())) {
	        		setSpeed(multiplyspeed.getSliderValue());
	        		 //mc.timer.timerSpeed = 0.30F;
	        	} else {
	        		 //mc.timer.timerSpeed = 0.25F;
                    setSpeed(1.8);
	        	}
		        /*if (!timerUtil.elapsed((long) 500L)) {
		        	setSpeed(multiplyspeed.getSliderValue());
		        	ClientLoader.addChatMessage("1");
		        } else {
		        	ClientLoader.addChatMessage("default");
		        	setSpeed(1.8);
		        }*/
	        /*} else {
	        	setSpeed(1.8);
	        }
	        if (mc.gameSettings.keyBindJump.isKeyDown()) {
	            EntityPlayerSP player = mc.thePlayer;
	            mc.thePlayer.motionY += 1.0;
	        }
	        if (mc.gameSettings.keyBindSneak.isKeyDown()) {
	            mc.thePlayer.motionY -= 1.0;
	        }*/
		}
	}
	
	public final void stop(boolean y) {
    	mc.thePlayer.motionX = 0;
    	mc.thePlayer.motionZ = 0;
    	if (y) mc.thePlayer.motionY = 0;
    }
	
	@EventTarget
	public void onMove(EventMove e) {
		if(FlyMode.getArraListValue().equalsIgnoreCase("HypixelDev")) {
			boolean hasspeed = mc.thePlayer.isPotionActive(Potion.moveSpeed);
			double boost = (MoveUtils.getSpeed() * ((0.6) * 0.5)) / (hasspeed ? 0.6 :  0.25);
			mc.thePlayer.onGround = true;
			mc.thePlayer.motionY = 0;
			switch(this.stage) {
				case 0: {
					if(mc.thePlayer.hurtTime != 0 && mc.thePlayer.onGround && mc.thePlayer.isCollidedVertically) {
						this.moveSpeed = 0.635;
					}
					break;
				}
				case 1: {
					if(mc.thePlayer.onGround) {
						e.setY(mc.thePlayer.motionY = MoveUtils.getJumpBoostModifier((float) 0.22f));
					}
					mc.timer.timerSpeed = 0.99f;
					this.moveSpeed *= 0.5f;
					break;
				}
				case 2: {
					moveSpeed = boost;
				}
				default: {
					e.setY(mc.thePlayer.motionY = mc.thePlayer.ticksExisted % 2 == 0 ? -0.001F : 0.001);
					moveSpeed -= moveSpeed / 189;
					break;
				}
			}
			moveSpeed = Math.max(moveSpeed, MoveUtils.getSpeed());
			++stage;
			MoveUtils.setSpeed(e, moveSpeed);

		}
		if(FlyMode.getArraListValue().equalsIgnoreCase("Hypixel")) {
			final float yaw = mc.thePlayer.rotationYaw;
	        double strafe = MovementInput.moveStrafe;
	        double forward = MovementInput.moveForward;
	        final double mx = -Math.sin(Math.toRadians(yaw));
	        final double mz = Math.cos(Math.toRadians(yaw));
	        
	        if (forward == 0.0 && strafe == 0.0) {
	            e.setX(0.0);
	            e.setZ(0.0);
	        }
	        if (forward != 0.0 && strafe != 0.0) {
	            forward *= Math.sin(0.7853981633974483);
	            strafe *= Math.cos(0.7853981633974483);
	        }
	        if (this.level != 1 || (mc.thePlayer.moveForward == 0.0f && mc.thePlayer.moveStrafing == 0.0f)) {
	            if (this.level == 2) {
	                this.level = 3;
	                this.moveSpeed *= 2.0499999;
	            } else if (this.level == 3) {
	                this.level = 4;
	                double difference;
	                if (MoveUtils.getSpeedEffect() > 0) {
	                    difference = 1.1 - 0.7
	                            * (this.lastDist - MathUtils.getBaseMovementSpeed());
	                } else {
	                    difference = 1.0 - 0.7
	                            * (this.lastDist - MathUtils.getBaseMovementSpeed());
	                }
	                this.moveSpeed = this.lastDist - difference;
	            } else {
	                if (mc.theWorld
	                        .getCollidingBoundingBoxes(mc.thePlayer,
	                                mc.thePlayer.getEntityBoundingBox().offset(0.0, mc.thePlayer.motionY, 0.0))
	                        .size() > 0 || mc.thePlayer.isCollidedVertically) {
	                    this.level = 1;
	                }
	                this.moveSpeed = this.lastDist - this.lastDist / 159.99999;
	            }
	        } else {
	            this.level = 2;
	            final double boost = mc.thePlayer.isPotionActive(Potion.moveSpeed) ? 1.706 : 2.034;
	            this.moveSpeed = boost * MathUtils.getBaseMovementSpeed() - 0.01;
	        }
	        this.moveSpeed = Math.max(this.moveSpeed, MathUtils.getBaseMovementSpeed());
	        e.setX(forward * this.moveSpeed * mx + strafe * this.moveSpeed * mz);
	        e.setZ(forward * this.moveSpeed * mz - strafe * this.moveSpeed * mx);
	        if (forward == 0.0 && strafe == 0.0) {
	            e.setX(0.0);
	            e.setZ(0.0);
	        }
	        if (this.timerUtil.hasElapsed(1700) && this.hypixelboost) {
	            this.hypixelboost = false;
	        }
	        if (TargetStrafe.canStrafe()) {
	        	TargetStrafe.strafe(e, Math.max(MoveUtils.getBaseSpeed(), this.moveSpeed));
	        }
		}
	}
	
	public static double getJumpBoostModifier(double baseJumpHeight) {
	      if (mc.thePlayer.isPotionActive(Potion.jump)) {
	         int amplifier = mc.thePlayer.getActivePotionEffect(Potion.jump).getAmplifier();
	         baseJumpHeight += (double)((float)(amplifier + 1) * 0.1F);
	      }

	      return baseJumpHeight;
	   }
	
	public void damage2() {
	      NetHandlerPlayClient netHandler = mc.getNetHandler();
	      EntityPlayerSP player = mc.thePlayer;
	      double x = player.posX;
	      double y = player.posY;
	      double z = player.posZ;

          netHandler.addToSendQueueSilent(new C03PacketPlayer.C04PacketPlayerPosition(x, y, z, true));
          netHandler.addToSendQueueSilent(new C03PacketPlayer.C04PacketPlayerPosition(x, y + 6.1D, z, true));
          netHandler.addToSendQueueSilent(new C03PacketPlayer.C04PacketPlayerPosition(x, y, z, true));
	}
	
	public float getMaxFallDist() {
        PotionEffect potioneffect = mc.thePlayer.getActivePotionEffect(Potion.jump);
        int f = potioneffect != null ? potioneffect.getAmplifier() + 1 : 0;
        return (float) (mc.thePlayer.getMaxFallHeight() + f);
    }
	
	public static double getBaseMoveSpeed() {
	      double baseSpeed = 0.2875D;
	      if (mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
	         baseSpeed *= 1.0D + 0.2D * (double)(mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier() + 1);
	      }
	      return baseSpeed;
	   }
	
	
	@EventTarget
	public void onSendPacket(EventSendPacket event) {
		Packet packet = event.getPacket();
		if(blink.getBooleanValue()) {
			 if (event.getPacket() instanceof C00PacketKeepAlive || event.getPacket() instanceof C00Handshake || event.getPacket() instanceof C00PacketLoginStart) {
                 return;
             }
             packetListZ.add(event.getPacket());
             event.setCancelled(true);
		} 
	}
	
	@EventTarget
	public void onReceivePacket(EventReceivePacket event) {
		Packet packet = event.getPacket();
		if(blink.getBooleanValue()) {
			if (packet instanceof C03PacketPlayer) {
				packetListZ.add(packet);
	            event.setCancelled(true);
	            System.out.println("Packet Received Got Cancelled");
	        }
		}
	}
	
	public static void setSpeed(EventMove moveEvent, double moveSpeed) {
		setSpeed(moveEvent, moveSpeed, mc.thePlayer.rotationYaw, (double)mc.thePlayer.movementInput.moveStrafe, (double)mc.thePlayer.movementInput.moveForward);
	}
	
	public static void setSpeed(EventMove moveEvent, double moveSpeed, float pseudoYaw, double pseudoStrafe, double pseudoForward) {
	      double forward = pseudoForward;
	      double strafe = pseudoStrafe;
	      float yaw = pseudoYaw;
	      if (pseudoForward != 0.0D) {
	         if (pseudoStrafe > 0.0D) {
	            yaw = pseudoYaw + (float)(pseudoForward > 0.0D ? -45 : 45);
	         } else if (pseudoStrafe < 0.0D) {
	            yaw = pseudoYaw + (float)(pseudoForward > 0.0D ? 45 : -45);
	         }

	         strafe = 0.0D;
	         if (pseudoForward > 0.0D) {
	            forward = 1.0D;
	         } else if (pseudoForward < 0.0D) {
	            forward = -1.0D;
	         }
	      }

	      if (strafe > 0.0D) {
	         strafe = 1.0D;
	      } else if (strafe < 0.0D) {
	         strafe = -1.0D;
	      }

	      double mx = Math.cos(Math.toRadians((double)(yaw + 90.0F)));
	      double mz = Math.sin(Math.toRadians((double)(yaw + 90.0F)));
	      moveEvent.x = forward * moveSpeed * mx + strafe * moveSpeed * mz;
	      moveEvent.z = forward * moveSpeed * mz - strafe * moveSpeed * mx;
	   }
	
	@Override
	public void onEnable() {
		super.onEnable();
		this.tp = false;
		this.damaged = false;
		timerUtil.reset();
		if(Damage.getBooleanValue()) {
			damage2();
			/*sendPacketNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, false));
            sendPacketNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 3.001, mc.thePlayer.posZ, true));
            sendPacketNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posY, false));
            sendPacketNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posY, true));
            //mc.thePlayer.swingItem();
            /*mc.thePlayer.sendQueue.addToSendQueueNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 3.0001, mc.thePlayer.posZ, false));
            mc.thePlayer.sendQueue.addToSendQueueNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, false));
            mc.thePlayer.sendQueue.addToSendQueueNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, true));*/
		}
	}
	
	public static void sendPacketNoEvent(Packet packet){
        mc.getNetHandler().getNetworkManager().sendPacket(packet);
    }
	
	@Override
	public void onDisable() {
		super.onDisable();
		this.damaged = false;
		if(blink.getBooleanValue()) {
			for(final Packet packet : packetListZ){
    			mc.getNetHandler().addToSendQueue(packet);
    		}
    		packetListZ.clear();
		}
		timerUtil.reset();
		setSpeed(0);
		mc.thePlayer.capabilities.isFlying = false;
		mc.timer.timerSpeed = 1F;
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
	
	private void setMotion(double speed) {
        double forward = mc.thePlayer.movementInput.moveForward;
        double strafe = mc.thePlayer.movementInput.moveStrafe;
        float yaw = mc.thePlayer.rotationYaw;
        if ((forward == 0.0D) && (strafe == 0.0D)) {
        	mc.thePlayer.motionX = 0;
        	mc.thePlayer.motionZ = 0;
        } else {
            if (forward != 0.0D) {
            	if(hypixel <= 0)
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
            mc.thePlayer.motionX = forward * speed * Math.cos(Math.toRadians(yaw + 90.0F)) + strafe * speed * Math.sin(Math.toRadians(yaw + 90.0F));
            mc.thePlayer.motionZ = forward * speed * Math.sin(Math.toRadians(yaw + 90.0F)) - strafe * speed * Math.cos(Math.toRadians(yaw + 90.0F));
        }
    }
	
	public static double randomNumber(double max, double min) {
        return Math.random() * (max - min) + min;
    }
}
