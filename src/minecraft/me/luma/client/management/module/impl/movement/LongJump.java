package me.luma.client.management.module.impl.movement;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;

import me.luma.client.core.registry.impl.ClientLoader;
import me.luma.client.management.event.EventTarget;
import me.luma.client.management.event.impl.Event3D;
import me.luma.client.management.event.impl.EventMove;
import me.luma.client.management.event.impl.EventPreMotionUpdate;
import me.luma.client.management.event.impl.EventSendPacket;
import me.luma.client.management.event.impl.EventUpdate;
import me.luma.client.management.module.Category;
import me.luma.client.management.module.Module;
import me.luma.client.management.module.impl.combat.Velocity;
import me.luma.client.management.utils.MoveUtils;
import me.luma.client.management.utils.TimerTEst;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.network.play.server.S27PacketExplosion;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

public class LongJump extends Module {
	
	private TimerTEst timer = new TimerTEst();
	
	private boolean canLongJump = false;

    private boolean check = false;
    
    private float air;

    private double posY = 0;

    private final List<C03PacketPlayer> aac5C03List = new ArrayList<>();

    private boolean nigger = false;

    private int slot = 0;
	
	private static float groundTicks;

	public LongJump() {
		super("LongJump", Keyboard.KEY_X, Category.MOVEMENT);
	}
	
	@EventTarget
	public void onUpdate(EventPreMotionUpdate event) {
		if(mc.thePlayer.hurtTime > 0 && canLongJump && !check) {
			mc.thePlayer.motionY = 0.438; // 0.414 / 0.430 / 0.440 / hypixel working
			MoveUtils.doStrafe((float) ((0.17310627) * 4.2)); // 0.17320627 hypixel working
			check = true;
		}
		
		if(!this.canLongJump) { 
			event.setPitch(-90.0F);
			mc.thePlayer.sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1), 255, mc.thePlayer.getCurrentEquippedItem(), 0.0F, 0.0F, 0.0F));
			if(this.timer.hasElapsed(200)) {
				mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
				this.canLongJump = true;
				
				this.timer.reset();
			}
		}
		
		/*if(mc.thePlayer.onGround && (mc.gameSettings.keyBindRight.pressed || mc.gameSettings.keyBindLeft.pressed || mc.gameSettings.keyBindForward.pressed || mc.gameSettings.keyBindBack.pressed)) {
			if(groundTicks > 0) {
				groundTicks = 0;
				this.toggle();
				return;
			}
			groundTicks++;
			mc.thePlayer.jump();
		}*/
	}
	
	@EventTarget
	public void onMove(EventMove event) {
		if(!(mc.thePlayer.hurtTime > 0) && !check) {
			event.setX(0);
			event.setY(0);
			event.setZ(0);
		}
	}
	
	@EventTarget
	public void onPacket(EventSendPacket event) {
		if(event.getPacket() instanceof S12PacketEntityVelocity) {
			event.setCancelled(true);
		}
		
		if(event.getPacket() instanceof S27PacketExplosion) {
			event.setCancelled(true);
		}
	}
	
	@EventTarget
	public void onRender(Event3D event) {
		if(check && mc.thePlayer.hurtTime == 0) {
			toggle();
		}
	}
	
	private void setMotion(EventPreMotionUpdate em, double speed) {
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


        return Math.max(value, MoveUtils.defaultSpeed() + (0.028 * MoveUtils.getSpeedEffect()));
    }

	@Override
	public void onEnable() {
		super.onEnable();
		slot = mc.thePlayer.inventory.currentItem;
		for (int i = 36; i < 45; ++i) {
			if (mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
	        	final ItemStack is = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
	            final Item tems = is.getItem();
	            if (Items.bow == tems) {
	            	mc.thePlayer.inventory.currentItem = i - 36;
	            }
			}
	    }
		//ClientLoader.loaderInstance.moduleManager.getModule("Velocity").setToggled(false);
		canLongJump = false;
		check = false;
		nigger = false;
		posY = mc.thePlayer.posY;
		timer.reset();
		/*if(ClientLoader.loaderInstance.moduleManager.getModule("Velocity").isToggled()) {
			ClientLoader.loaderInstance.moduleManager.getModule("Velocity").setToggled(false);
		} else
			return;*/
	}
	
	@Override
	public void onDisable() {
		//ClientLoader.loaderInstance.moduleManager.getModule("Velocity").setToggled(true);
		super.onDisable();
		mc.thePlayer.inventory.currentItem = slot;
		timer.reset();
		mc.timer.timerSpeed = 1F;
	}
	
}
