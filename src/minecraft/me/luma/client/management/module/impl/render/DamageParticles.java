package me.luma.client.management.module.impl.render;

import java.awt.Color;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import org.lwjgl.opengl.GL11;

import me.luma.client.core.registry.impl.ClientLoader;
import me.luma.client.management.event.EventTarget;
import me.luma.client.management.event.impl.Event3D;
import me.luma.client.management.event.impl.EventUpdate;
import me.luma.client.management.gui.clickgui.settings.SettingBoolean;
import me.luma.client.management.gui.clickgui.settings.SettingSlider;
import me.luma.client.management.module.Category;
import me.luma.client.management.module.Module;
import me.luma.client.management.module.impl.combat.KillAura;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.BlockPos;

public class DamageParticles extends Module {
	
	private ArrayList<hit> hits = new ArrayList<hit>();
    private float lastHealth;
    private EntityLivingBase lastTarget = null;
    
    public static SettingSlider particleSize;
    public static SettingSlider particleHue;
    
    public static SettingBoolean rainbowParticles;
    
	public DamageParticles() {
		super("DamageParticles", 0, Category.RENDER);
		
		particleSize = new SettingSlider("Particle Size", this, 5.5F, 1.0F, 10.0F, false, true);
		particleHue = new SettingSlider("Particle Hue", this, 0.6F, 0F, 1.0F, false, true);
		
		rainbowParticles = new SettingBoolean("Rainbow Particles", this, false);
	}
	
	@EventTarget
	public void onUpdate(EventUpdate event) {
		if (KillAura.entityAttacked == null) {
            this.lastHealth = 20;
            lastTarget = null;
            return;
        }
        if (this.lastTarget == null || KillAura.entityAttacked != this.lastTarget) {
            this.lastTarget = KillAura.entityAttacked;
            this.lastHealth = KillAura.entityAttacked.getHealth();
            return;
        }
        if (KillAura.entityAttacked.getHealth() != this.lastHealth) {
            if (KillAura.entityAttacked.getHealth() < this.lastHealth) {
                this.hits.add(new hit(KillAura.entityAttacked.getPosition().add(ThreadLocalRandom.current().nextDouble(-5.5, 0.5), ThreadLocalRandom.current().nextDouble(1, 1.5), ThreadLocalRandom.current().nextDouble(-0.5, 0.5)), this.lastHealth - KillAura.entityAttacked.getHealth()));
            }
            this.lastHealth = KillAura.entityAttacked.getHealth();
        }
	}
	
	@EventTarget
	public void onRender3D(Event3D event) {
		try {
            for (hit h : hits) {
                if (h.isFinished()) {
                    hits.remove(h);
                } else {
                    h.onRender();
                }
            }
        } catch (Exception e) {
        	// Do nothing
        }
	}
}

class hit {
    protected static Minecraft mc = Minecraft.getMinecraft();
    private long startTime = System.currentTimeMillis();
    private BlockPos pos;
    private double healthVal;
    private long maxTime = 1000;

    public hit(BlockPos pos, double healthVal) {
        this.startTime = System.currentTimeMillis();
        this.pos = pos;
        this.healthVal = healthVal;
    }

    public void onRender() {
        final double x = this.pos.getX() + (this.pos.getX() - this.pos.getX()) * mc.timer.renderPartialTicks - RenderManager.viewerPosX + 1.5;
        final double y = this.pos.getY() + (this.pos.getY() - this.pos.getY()) * mc.timer.renderPartialTicks - RenderManager.viewerPosY;
        final double z = this.pos.getZ() + (this.pos.getZ() - this.pos.getZ()) * mc.timer.renderPartialTicks - RenderManager.viewerPosZ;

        final float var10001 = (mc.gameSettings.thirdPersonView == 2) ? -1.0f : 1.0f;
        double size = (DamageParticles.particleSize.getSliderValue().floatValue());
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glDisable(3553);
        GL11.glDisable(2929);
        Minecraft.getMinecraft().entityRenderer.setupCameraTransform(mc.timer.renderPartialTicks, 0);
        GL11.glTranslated(x, y, z);
        GL11.glNormal3f(0.0f, 1.0f, 0.0f);
        GL11.glRotatef(-RenderManager.playerViewY, 0.0f, 1.0f, 0.0f);
        GL11.glRotatef(mc.getRenderManager().playerViewX, var10001, 0.0f, 0.0f);
        GL11.glScaled(-0.01666666753590107 * size, -0.01666666753590107 * size, 0.01666666753590107 * size);
        float sizePercentage;
        long timeLeft = (this.startTime + this.maxTime) - System.currentTimeMillis();
        float yPercentage = 0;
        if (timeLeft < 75) {
            sizePercentage = Math.min((float) timeLeft / 75F, 1F);
            yPercentage = Math.min((float) timeLeft / 75F, 1F);
        } else {
            sizePercentage = Math.min((float) (System.currentTimeMillis() - this.startTime) / 300F, 1F);
            yPercentage = Math.min((float) (System.currentTimeMillis() - this.startTime) / 600F, 1F);
        }
        GlStateManager.scale(0.8 * sizePercentage, 0.8 * sizePercentage, 0.8 * sizePercentage);
        Gui.drawRect(-100, -100, 100, 100, new Color(255, 0, 0, 0).getRGB());
        Color c = Color.getHSBColor(DamageParticles.particleHue.getSliderValue().floatValue(), 1.0F, 1.0F);
        if (DamageParticles.rainbowParticles.getBooleanValue()) {
            ClientLoader.loaderInstance.fontManager.getFont("SFL 10").drawStringWithShadow(new DecimalFormat("#.#").format(this.healthVal), 0, -(yPercentage * 1), getRainbow(6000, -15));
        } else
        	ClientLoader.loaderInstance.fontManager.getFont("SFL 10").drawStringWithShadow(new DecimalFormat("#.#").format(this.healthVal), 0, -(yPercentage * 1), c.getRGB());
        GL11.glDisable(3042);
        GL11.glEnable(3553);
        GL11.glDisable(2848);
        GL11.glDisable(3042);
        GL11.glEnable(2929);
        GlStateManager.color(1.0f, 1.0f, 1.0f);
        GlStateManager.popMatrix();
    }
    
    public static int getRainbow(int speed, int offset) {
        long rainspeed = HUD.rainbowSpeed.getSliderValue().longValue();
        long rainoffset = HUD.rainbowOffset.getSliderValue().longValue();
        float hue = (float) ((System.currentTimeMillis() * rainspeed + offset / rainoffset) % speed * 2);
        float saturation = HUD.rainbowSaturation.getSliderValue().longValue();
        float brightness = HUD.rainbowBrightness.getSliderValue().longValue();
        hue /= speed;
        return Color.getHSBColor(hue, saturation, brightness).getRGB();
    }

    public boolean isFinished() {
        return System.currentTimeMillis() - this.startTime >= maxTime;
    }
}
