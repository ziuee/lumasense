package me.luma.client.management.module.impl.render;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import me.luma.client.management.event.EventTarget;
import me.luma.client.management.event.impl.Event2D;
import me.luma.client.management.gui.clickgui.settings.SettingSlider;
import me.luma.client.management.module.Category;
import me.luma.client.management.module.Module;
import me.luma.client.management.module.impl.combat.KillAura;
import me.luma.client.management.utils.Draw;
import me.luma.client.management.utils.MathUtils;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.EntityLivingBase;

public class TargetHUD extends Module {

	SettingSlider redColor;
	SettingSlider greenColor;
	SettingSlider blueColor;
	
	SettingSlider xValue;
	SettingSlider yValue;
	
	public TargetHUD() {
		super("TargetHUD", 0, Category.RENDER);
		
		xValue = new SettingSlider("X", this, 30, 0.0, 85, true, false);
		yValue = new SettingSlider("Y", this, 40, 0.0, 50, true, false);
	}
	
	@EventTarget
	public void onRender(Event2D event) {
		
        /*int x = (int) (xValue.getSliderValue() * 10);
        int y = (int) (yValue.getSliderValue() * 10);

        EntityLivingBase target = KillAura.entityAttacked;
		
		//Rectangle Color
        Draw.color(new Color(0.1f,0.1f,0.1f,0.9f).getRGB());

        if(target == null) {
        	return;
        }
        if(KillAura.entityAttacked.getDistanceToEntity(mc.thePlayer) <= KillAura.reach.getSliderValue()) {
	        //Name
	        mc.fontRendererObj.drawStringWithShadow(target.getName(), x + 31,y + 5, 0xffffffff);
	
	        //Health
	        GL11.glPushMatrix();
	        GlStateManager.translate(x,y,1);
	        GL11.glScalef(2,2,2);
	        GlStateManager.translate(-x,-y,1);
	        mc.fontRendererObj.drawStringWithShadow(MathUtils.round((target.getHealth() / 2.0f), 1) + " \u2764",
	                x + 16,y + 10, -1);
	        GL11.glPopMatrix();
	
	        GlStateManager.color(1,1,1,1);
	        GuiInventory.drawEntityOnScreen(x + 16,y + 55, 25, target.rotationYaw, -target.rotationPitch, target);
	
	        int xHealthbar = 30;
	        int yHealthbar = 46;
	
	        //Background rect
	        //Gui.drawRect(x + 100,y + yHealthbar + 10, 630, 185, 0xff000000);
	
	        //Delayed Bar
	        //Gui.drawRect(x + xHealthbar,y + yHealthbar,(target.getHealth()) / target.getMaxHealth() * 120,10, new Color(color).darker().getRGB());
	
	        //Health Bar
	        //Gui.drawRect(x + xHealthbar,y + yHealthbar,target.getHealth() / target.getMaxHealth() * 120, 10, new Color(color).getRGB());
	
	        for (int index = 1; index < 5; index++) {
	
	            if (target.getEquipmentInSlot(index) == null)
	                continue;
	
	            //Nametags.renderItem(target.getEquipmentInSlot(index), (int) (x + 80 + mc.fontRendererObj.getStringWidth(target.getName())), (int) (y + (index * -11.5) + 36));
	        }
        }*/
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
