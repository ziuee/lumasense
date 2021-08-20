package me.luma.client.management.module.impl.render;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import me.luma.client.management.event.EventTarget;
import me.luma.client.management.event.impl.Event2D;
import me.luma.client.management.gui.clickgui.settings.SettingBoolean;
import me.luma.client.management.gui.clickgui.settings.SettingSlider;
import me.luma.client.management.module.Category;
import me.luma.client.management.module.Module;
import me.luma.client.management.utils.RenderUtils;
import me.luma.client.management.utils.killaura.RotationUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;

public class Radar extends Module {

	SettingSlider xValue;
	SettingSlider yValue;
	SettingSlider size;
	
	SettingBoolean players;
	SettingBoolean monsters;
	SettingBoolean animals;
	SettingBoolean passives;
	SettingBoolean invisibles;
	SettingBoolean items;
	
	public Radar() {
		super("Radar", 0, Category.RENDER);
		
		players = new SettingBoolean("Players", this, true);
		monsters = new SettingBoolean("Monsters", this, false);
		animals = new SettingBoolean("Animals", this, false);
		passives = new SettingBoolean("Passives", this, false);
		invisibles = new SettingBoolean("Invisibles", this, true);
		items = new SettingBoolean("Items", this, false);
		
		size = new SettingSlider("Size", this,70, 25, 200, true, true);
		xValue = new SettingSlider("X", this, 2, 0, 897, true, true);
		yValue = new SettingSlider("Y", this, 51, 0, 340, true, true);
	}

	@EventTarget
	public void onRender2D(Event2D event) {
			GL11.glPushMatrix();
			
			int x = xValue.getSliderValue().intValue();
			int y = yValue.getSliderValue().intValue();
			int width = size.getSliderValue().intValue();
			int height = size.getSliderValue().intValue();
			float cx = x + (width / 2f);
			float cy = y + (height /2f);
			
			RenderUtils.drawBorderedRect(x, y, x + width, y + height, 1, 0xFF444444, 0xFF222222);
			RenderUtils.drawRectSized(x + (width / 2f) - 0.5f, y, 1, height, 0xFF444444);
            RenderUtils.drawRectSized(x, y + (height / 2f) - 0.5f, width, 1, 0xFF444444);
            RenderUtils.drawRectSized(cx - 1, cy - 1, 2, 2, 0xFF7F00FF);
            
            int maxDist = size.getSliderValue().intValue() / 2;
            for(Entity entity : mc.theWorld.loadedEntityList) {
            	if (qualifies(entity)) {
            		// X difference
                    double dx = RenderUtils.lerp(entity.prevPosX, entity.posX, event.getTicks())
                            - RenderUtils.lerp(mc.thePlayer.prevPosX, mc.thePlayer.posX,
                            event.getTicks());
                    // Z difference
                    double dz = RenderUtils.lerp(entity.prevPosZ, entity.posZ, event.getTicks())
                            - RenderUtils.lerp(mc.thePlayer.prevPosZ, mc.thePlayer.posZ,
                            event.getTicks());
                    
                 // Make sure they're within the available rendering range
                    if ((dx * dx + dz * dz) <= (maxDist * maxDist)) {
                        float dist = MathHelper.sqrt_double(dx * dx + dz * dz);
                        double[] vector = getLookVector(
                                RotationUtil.getRotations(entity)[0] - (float) RenderUtils.lerp(mc.thePlayer.prevRotationYawHead, mc.thePlayer.rotationYawHead, event.getTicks()));
                        if (entity instanceof EntityMob) {
                            RenderUtils.drawRectSized(cx - 1 - ((float) vector[0] * dist), cy - 1 - ((float) vector[1] * dist), 2, 2,
                                    new Color(0, 252, 103).getRGB());
                        } else if (entity instanceof EntityPlayer) {
                            RenderUtils.drawRectSized(cx - 1 - ((float) vector[0] * dist), cy - 1 - ((float) vector[1] * dist), 2, 2,
                                    new Color(248, 0, 0).getRGB());
                        } else if (entity instanceof EntityAnimal || entity instanceof EntitySquid || entity instanceof EntityVillager || entity instanceof EntityGolem) {
                            RenderUtils.drawRectSized(cx - 1 - ((float) vector[0] * dist), cy - 1 - ((float) vector[1] * dist), 2, 2,
                                    new Color(248, 178, 0).getRGB());
                        } else if (entity instanceof EntityItem) {
                            RenderUtils.drawRectSized(cx - 1 - ((float) vector[0] * dist), cy - 1 - ((float) vector[1] * dist), 2, 2,
                                    new Color(0, 147, 241).getRGB());
                        }
                    }
            	}
            }
		GL11.glPopMatrix();
	}
	
	public double[] getLookVector(float yaw) {
        yaw *= MathHelper.deg2Rad;
        return new double[]{
                -MathHelper.sin(yaw),
                MathHelper.cos(yaw)
        };
    }
	
	private boolean qualifies(Entity entity) {
        return ((entity instanceof EntityPlayer && this.players.getBooleanValue())
                || (entity instanceof EntityMob && this.monsters.getBooleanValue())
                || ((entity instanceof EntityAnimal || entity instanceof EntitySquid) && this.animals.getBooleanValue())
                || ((entity instanceof EntityVillager || entity instanceof EntityGolem) && this.passives.getBooleanValue())
                || (entity instanceof EntityItem && items.getBooleanValue()))
                && (!entity.isInvisible() || this.invisibles.getBooleanValue()) && entity != mc.thePlayer;
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
