package me.luma.client.management.module.impl.combat;

import java.awt.Color;
import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import me.luma.client.core.registry.impl.ClientLoader;
import me.luma.client.management.event.EventTarget;
import me.luma.client.management.event.impl.Event3D;
import me.luma.client.management.event.impl.EventMove;
import me.luma.client.management.gui.clickgui.settings.SettingArrayList;
import me.luma.client.management.gui.clickgui.settings.SettingBoolean;
import me.luma.client.management.gui.clickgui.settings.SettingSlider;
import me.luma.client.management.module.Category;
import me.luma.client.management.module.Module;
import me.luma.client.management.utils.MoveUtils;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

public class TargetStrafe extends Module {
	
	SettingArrayList colorMode;
	
	SettingBoolean renderOnPlayer;
	SettingBoolean render;
	
	SettingBoolean speedOnly;
	
	SettingSlider circleSides;
	public static SettingSlider radius;
	SettingSlider speed;
	
	double strafe;
	
	public static int direction = 1;

	public TargetStrafe() {
		super("TargetStrafe", 0, Category.COMBAT);
		
		ArrayList<String> colorModes = new ArrayList<String>();
		colorModes.add("Rainbow");
		colorModes.add("Astolfo");
		colorMode = new SettingArrayList("Color Mode", this, colorModes, "Rainbow");
		
		circleSides = new SettingSlider("Circle Sides", this, 10.0D, 3.0D, 10.0D, true, false);
		radius = new SettingSlider("Radius", this, 2.0D, 0.1D, 4.0D, false, true);
		speed = new SettingSlider("Speed", this, 0.25D, 0.01D, 1.00D, false, false);
		
		renderOnPlayer = new SettingBoolean("Render On Player", this, true);
		render = new SettingBoolean("Render", this, true);
		
		speedOnly = new SettingBoolean("Speed Only", this, true); // if toggled
	}
	
	@EventTarget
	public void onMove(EventMove event) {
		if(KillAura.entityAttacked == null) {
			return;
		}
		if(mc.thePlayer.moveStrafing != 0) {
			strafe = mc.thePlayer.moveStrafing;
		}
		if(this.canStrafe()) {
			
			if(mc.gameSettings.keyBindRight.isPressed()) {
		    	this.direction = -1;
		    }
			if(mc.gameSettings.keyBindLeft.isPressed()) {
		    	this.direction = 1;
		    }
			
			if(speedOnly.getBooleanValue() && ClientLoader.loaderInstance.moduleManager.getModule("Speed").isToggled()) {
				strafe(event, speed.getSliderValue());
				if(KillAura.entityAttacked != null && mc.thePlayer.getDistanceToEntity(KillAura.entityAttacked) < KillAura.reach.getSliderValue()) {
					if(mc.thePlayer.onGround) {
						mc.thePlayer.jump();
					}
				}
			}
			if(!speedOnly.getBooleanValue()) {
				strafe(event, speed.getSliderValue());
				if(KillAura.entityAttacked != null && mc.thePlayer.getDistanceToEntity(KillAura.entityAttacked) < KillAura.reach.getSliderValue()) {
					if(mc.thePlayer.onGround) {
						mc.thePlayer.jump();
					}
				}
			}
		}
		if(!this.canStrafe()) { 
			mc.gameSettings.keyBindJump.pressed = false;
		}
	}
	
	@EventTarget
	public void onRender3D(Event3D event) {
		if(renderOnPlayer.getBooleanValue()) {
			drawCircleAtTarget();
			GL11.glColor3f(255, 255, 255);
		}
		
		if(this.canStrafe()) {
			if(KillAura.entityAttacked != null && mc.thePlayer.getDistanceToEntity(KillAura.entityAttacked) < KillAura.reach.getSliderValue()) {
				if(render.getBooleanValue()) {
					drawCircleAtTarget();
					GL11.glColor3f(255, 255, 255);
				}
			}
		}
	}
	
	public boolean drawCircleAtTarget() {
		GL11.glPushMatrix();
		GL11.glDisable(GL11.GL_TEXTURE_2D);
        startSmooth();
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(false);
        GL11.glLineWidth(6.0f);
        GL11.glBegin(GL11.GL_LINE_STRIP);
        
        EntityLivingBase entity = mc.thePlayer;
        if(this.canStrafe()) {
        	if(KillAura.entityAttacked != null && mc.thePlayer.getDistanceToEntity(KillAura.entityAttacked) < KillAura.reach.getSliderValue()) {
        		entity = KillAura.entityAttacked;
        	} else {
        		entity = mc.thePlayer;
        	}
        }
        
        double x = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * mc.timer.elapsedPartialTicks - mc.getRenderManager().viewerPosX;
        double y = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * mc.timer.elapsedPartialTicks - mc.getRenderManager().viewerPosY;
        double z = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * mc.timer.elapsedPartialTicks - mc.getRenderManager().viewerPosZ;
        
        int color1 = new Color(255, 255, 255, 255).getRGB();
        
        if(colorMode.getArraListValue().equalsIgnoreCase("Rainbow")) {
        	color1 = rainbow(0, 12).getRGB();
        } else if(colorMode.getArraListValue().equalsIgnoreCase("Astolfo")) {
        	color1 = getAstolfoColor(3000, 0).getRGB();
        }
        double pix2 = Math.PI * 2.0D;
        
        for(int i = 0; i <= 90; i++) {
        	color(color1);
        	GL11.glVertex3d(x + radius.getSliderValue() * Math.cos(i * pix2 / circleSides.getSliderValue()), y, z + radius.getSliderValue() * Math.sin(i * pix2 / circleSides.getSliderValue()));
        }
        
        GL11.glEnd();
        GL11.glDepthMask(true);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        endSmooth();
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glPopMatrix();

        GL11.glPushMatrix();
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        startSmooth();
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(false);
        GL11.glLineWidth(2.0f);
        GL11.glBegin(GL11.GL_LINE_STRIP);
        
        float r1 = ((float) 1 / 255) * Color.black.getRed();
        float g1 = ((float) 1 / 255) * Color.black.getGreen();
        float b1 = ((float) 1 / 255) * Color.black.getBlue();
        
        for (int i = 0; i <= 90; ++i) {
            GL11.glColor3f(r1, g1, b1);
            GL11.glVertex3d(x + (radius.getSliderValue() + 0.01) * Math.cos(i * pix2 / circleSides.getSliderValue()), y, z + (radius.getSliderValue() + 0.01) * Math.sin(i * pix2 / circleSides.getSliderValue()));
        }
        
        GL11.glEnd();
        GL11.glDepthMask(true);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        endSmooth();
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glPopMatrix();

        GL11.glPushMatrix();
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        startSmooth();
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(false);
        GL11.glLineWidth(2.0f);
        GL11.glBegin(GL11.GL_LINE_STRIP);
        
        for (int i = 0; i <= 90; ++i) {
            GL11.glColor3f(r1, g1, b1);
            GL11.glVertex3d(x + (radius.getSliderValue() - 0.01) * Math.cos(i * pix2 / circleSides.getSliderValue()), y, z + (radius.getSliderValue() - 0.01) * Math.sin(i * pix2 / circleSides.getSliderValue()));
        }
        
        GL11.glEnd();
        GL11.glDepthMask(true);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glPopMatrix();
        endSmooth();
        return true;
	}

	public static void strafe(EventMove event, final double moveSpeed) {
		final EntityLivingBase target = KillAura.entityAttacked;
		float rotations[] = getRotationsEntity(KillAura.entityAttacked);
		if(target != null && mc.thePlayer.getDistanceToEntity(target) < KillAura.reach.getSliderValue()) { // Tell Me... Are You Talking About The Circle?
			if(mc.thePlayer.getDistanceToEntity(target) <= radius.getSliderValue()) {
				setSpeed(event, moveSpeed, rotations[0], direction, 0);
			} else {
				setSpeed(event, moveSpeed, rotations[0], direction, 1); // When Entity Is Null.... This Moves BRBRBRBRBBR....
			}
		}
	}
	
	public static boolean canStrafe() {
		//return (MoveUtils.isMoving() && KillAura.entityAttacked != null && KillAura.entityAttacked instanceof EntityPlayer && MoveUtils.isBlockUnderneath(KillAura.entityAttacked.getPosition()));
		return (ClientLoader.loaderInstance.moduleManager.getModule("KillAura").isToggled() && KillAura.entityAttacked != null && KillAura.entityAttacked instanceof EntityPlayer);
	}
	
	public static void setSpeed(final EventMove moveEvent, final double moveSpeed) {
        setSpeed(moveEvent, moveSpeed, mc.thePlayer.rotationYaw, mc.thePlayer.movementInput.moveStrafe, mc.thePlayer.movementInput.moveForward);
    }
	
	public static void setSpeed(final EventMove moveEvent, final double moveSpeed, final float pseudoYaw, final double pseudoStrafe, final double pseudoForward) {
        double forward = pseudoForward;
        double strafe = pseudoStrafe;
        float yaw = pseudoYaw;

        if (forward != 0.0) {
            if (strafe > 0.0) {
                yaw += ((forward > 0.0) ? -45 : 45);
            } else if (strafe < 0.0) {
                yaw += ((forward > 0.0) ? 45 : -45);
            }
            strafe = 0.0F;
            if (forward > 0.0) {
                forward = 1F;
            } else if (forward < 0.0) {
                forward = -1F;
            }
        }

        if (strafe > 0.0) {
            strafe = 1F;
        } else if (strafe < 0.0) {
            strafe = -1F;
        }
        double mx = Math.cos(Math.toRadians((yaw + 90.0F)));
        double mz = Math.sin(Math.toRadians((yaw + 90.0F)));
        moveEvent.x = (forward * moveSpeed * mx + strafe * moveSpeed * mz);
        moveEvent.z = (forward * moveSpeed * mz - strafe * moveSpeed * mx);

    }
	
	public static float[] getRotationsEntity(EntityLivingBase entity) {
        return getRotations(entity.posX + randomNumber(0.03D, -0.03D), entity.posY + entity.getEyeHeight() - 0.4D + randomNumber(0.07D, -0.07D), entity.posZ + randomNumber(0.03D, -0.03D));
    }
	
	public static float[] getRotations(double posX, double posY, double posZ) {
		EntityLivingBase player = mc.thePlayer;
	    double x = posX - player.posX;
	    double y = posY - player.posY + player.getEyeHeight();
	    double z = posZ - player.posZ;
	    double dist = Math.sqrt(x * x + z * z);
	    float yaw = (float)(Math.atan2(z, x) * 180.0D / Math.PI) - 90.0F;
	    float pitch = (float)-(Math.atan2(y, dist) * 180.0D / Math.PI);
	    return new float[] { yaw, pitch };
	}
	
	public static double randomNumber(double max, double min) {
        return Math.random() * (max - min) + min;
    }
	
	public static void startSmooth() {
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glEnable(GL11.GL_POLYGON_SMOOTH);
        GL11.glEnable(GL11.GL_POINT_SMOOTH);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glHint(GL11.GL_LINE_SMOOTH_HINT, GL11.GL_NICEST);
        GL11.glHint(GL11.GL_POLYGON_SMOOTH_HINT, GL11.GL_NICEST);
        GL11.glHint(GL11.GL_POINT_SMOOTH_HINT, GL11.GL_NICEST);
    }

    public static void endSmooth() {
    	GL11.glDisable(GL11.GL_LINE_SMOOTH);
    	GL11.glDisable(GL11.GL_POLYGON_SMOOTH);
    	GL11.glEnable(GL11.GL_POINT_SMOOTH);
    }
    
    public static Color rainbow(int index, double speed) {
    	int angle = (int) ((System.currentTimeMillis() / speed + index) % 360);
        float hue = angle / 360f;
        int color = Color.HSBtoRGB(hue, 1, 1);
        return new Color(color);
    }
    
    public static Color getAstolfoColor(int delay, float offset) {
    	int yStart = 20;
        float speed = 3000f;
        float index = 0.3f;
        float hue = (float) (System.currentTimeMillis() % delay) + (offset);
        while (hue > speed) {
            hue -= speed;
        }
        hue /= speed;
        if (hue > 0.5) {
            hue = 0.5F - (hue - 0.5f);
        }
        hue += 0.5F;
        return Color.getHSBColor(hue, 0.5F, 1F);
    }
    
    public static int getAstolfoColor2(int delay, float offset) {
    	int yStart = 20;
        float speed = 3000f;
        float index = 0.3f;
        float hue = (float) (System.currentTimeMillis() % delay) + (offset);
        while (hue > speed) {
            hue -= speed;
        }
        hue /= speed;
        if (hue > 0.5) {
            hue = 0.5F - (hue - 0.5f);
        }
        hue += 0.5F;
        return Color.HSBtoRGB(hue, 0.5F, 1F);
    }
    
    public static void color(int color) {
        float red = (color & 255) / 255f,
                green = (color >> 8 & 255) / 255f,
                blue = (color >> 16 & 255) / 255f,
                alpha = (color >> 24 & 255) / 255f;

        GlStateManager.color(red, green, blue, alpha);
    }
    @Override
    public void onEnable() {
    	super.onEnable();
    }
    @Override
    public void onDisable() {
    	super.onDisable();
    }
}
