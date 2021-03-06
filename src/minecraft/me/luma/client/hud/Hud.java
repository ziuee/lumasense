package me.luma.client.hud;

import java.awt.Color;
import java.util.Comparator;
import java.util.concurrent.atomic.AtomicInteger;

import me.luma.client.core.Luma;
import me.luma.client.core.registry.impl.ClientLoader;
import me.luma.client.management.module.Module;
import me.luma.client.management.module.impl.render.HUD;
import me.luma.client.management.utils.Draw;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;

public class Hud extends GuiIngame {

	public static FontRenderer font = Minecraft.getMinecraft().fontRendererObj;
	
	public Hud(Minecraft mcIn) {
		super(mcIn);
	}
	
	public static void ArrayList() {
		
		final ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
		final int[] yDist = {2};
		final int[] counter = {1};
		int count = 0;
		int yText = 3;
		//Color c2 = Color.getHSBColor(0.0F, 0.0F, HUD.flow.getSliderValue().floatValue());
		Color c2 = Color.getHSBColor(0.1F, 0.1F, 0F);
		Color c = Color.getHSBColor(HUD.hudHue.getSliderValue().floatValue(), HUD.hudSaturation.getSliderValue().floatValue(), 1.0F);
		//int color = astolfo(0, (int)rainbowSpeed * counter[0], 0.6f,-hue, 1f).getRGB();
		AtomicInteger arrayoffset = new AtomicInteger(3);
		//Color temp = astolfo(300, (int)fadeSpeed, 0.2f,-hue, 1f);
		//int outlineColor = new Color(temp.getRed(), temp.getGreen(), temp.getBlue(), 255).getRGB();
		
		
		for(Module m: ClientLoader.loaderInstance.moduleManager.getModules()) {
			m.isToggled();
			if(m.isToggled()) {
				count++;
			}
		}
		ClientLoader.loaderInstance.moduleManager.getModules().stream().filter(Module::isToggled).sorted(Comparator.comparingInt(module -> (int) -ClientLoader.loaderInstance.fontManager.getFont("SFL 10").getWidth(module.getDisplayName()))).forEach(module -> {
			int lumasense = getGradientOffset(c, c2, (Math.abs(((System.currentTimeMillis()) / 10)) / 80D) + (counter[0] * 300 / ((ClientLoader.loaderInstance.fontManager.getFont("SFL 10").getHeight(module.getDisplayName()) + 135)))).getRGB();
			if(HUD.arraylistBackground.getBooleanValue()) {
				GlStateManager.resetColor();
				//Gui.drawRect(sr.getScaledWidth() - 1, arrayoffset.get() + 3, sr.getScaledWidth(), arrayoffset.get() + 13, getRainbow(6000, counter[0] * 300));
				Gui.drawRect(sr.getScaledWidth() - HUD.arraylistX.getSliderValue().intValue() - ClientLoader.loaderInstance.fontManager.getFont("SFL 10").getWidth(module.getDisplayName()) - 4, arrayoffset.get() + 2 + HUD.arraylistY.getSliderValue().intValue(), sr.getScaledWidth() - 2 - HUD.arraylistX.getSliderValue().intValue(), arrayoffset.get() + 13 + HUD.arraylistY.getSliderValue().intValue(), Integer.MIN_VALUE);
				if(HUD.rainbowArraylist.getBooleanValue()) {
					Gui.drawRect(sr.getScaledWidth() - 2 - HUD.arraylistX.getSliderValue().intValue(), arrayoffset.get() + 2 + HUD.arraylistY.getSliderValue().intValue(), sr.getScaledWidth() - HUD.arraylistX.getSliderValue().intValue(), arrayoffset.get() + 13 + HUD.arraylistY.getSliderValue().intValue(), getRainbow(6000, counter[0] * 300));
				} 
				if(!HUD.rainbowArraylist.getBooleanValue()) {
					Gui.drawRect(sr.getScaledWidth() - 2 - HUD.arraylistX.getSliderValue().intValue(), arrayoffset.get() + 2 + HUD.arraylistY.getSliderValue().intValue(), sr.getScaledWidth() - HUD.arraylistX.getSliderValue().intValue(), arrayoffset.get() + 13 + HUD.arraylistY.getSliderValue().intValue(), lumasense);
				}
				//Gui.drawRect(sr.getScaledWidth() + ClientLoader.loaderInstance.fontManager.getFont("SFL 10").getWidth(module.getDisplayName()), arrayoffset.get() - 3, sr.getScaledWidth() -ClientLoader.loaderInstance.fontManager.getFont("SFL 10").getWidth(module.getDisplayName()) - 7, arrayoffset.get() + 10, new Color(0, 0, 255).getRGB());
				GlStateManager.resetColor();
				if(HUD.rainbowArraylist.getBooleanValue()) {
					//Gui.drawRect(sr.getScaledWidth() - 1, arrayoffset.get() + 3, sr.getScaledWidth(), arrayoffset.get() + 14, getRainbow(6000, counter[0] * 300));
					ClientLoader.loaderInstance.fontManager.getFont("SFL 10").drawString(module.getDisplayName(), sr.getScaledWidth() - 2.5 - HUD.arraylistX.getSliderValue().intValue() -ClientLoader.loaderInstance.fontManager.getFont("SFL 10").getWidth(module.getDisplayName()), yDist[0] + 3 + HUD.arraylistY.getSliderValue().intValue(), getRainbow(6000, counter[0] * 300));
				} else {
					ClientLoader.loaderInstance.fontManager.getFont("SFL 10").drawString(module.getDisplayName(), sr.getScaledWidth() - 2.5 - HUD.arraylistX.getSliderValue().intValue() -ClientLoader.loaderInstance.fontManager.getFont("SFL 10").getWidth(module.getDisplayName()), yDist[0] + 3 + HUD.arraylistY.getSliderValue().intValue(), lumasense);
				}
			} else {
				if(HUD.rainbowArraylist.getBooleanValue()) {
					ClientLoader.loaderInstance.fontManager.getFont("SFL 10").drawString(module.getDisplayName(), sr.getScaledWidth() - 2.5 - HUD.arraylistX.getSliderValue().intValue() -ClientLoader.loaderInstance.fontManager.getFont("SFL 10").getWidth(module.getDisplayName()), yDist[0] + 3 + HUD.arraylistY.getSliderValue().intValue(), getRainbow(6000, counter[0] * 300));
				} else {
					ClientLoader.loaderInstance.fontManager.getFont("SFL 10").drawString(module.getDisplayName(), sr.getScaledWidth() - 2.5 - HUD.arraylistX.getSliderValue().intValue() -ClientLoader.loaderInstance.fontManager.getFont("SFL 10").getWidth(module.getDisplayName()), yDist[0] + 3 + HUD.arraylistY.getSliderValue().intValue(), lumasense);
				}
			}
			yDist[0] += ClientLoader.loaderInstance.fontManager.getFont("SFL 10").getHeight(module.getDisplayName()) + 2;
			arrayoffset.addAndGet(font.FONT_HEIGHT + 2);
			counter[0]++;
		});
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
	
	public static void clientInfo() { //GuiIngame
		ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
		int counter = 0;
		Color c2 = Color.getHSBColor(0.1F, 0.1F, 0F);
		Color c = Color.getHSBColor(HUD.hudHue.getSliderValue().floatValue(), HUD.hudSaturation.getSliderValue().floatValue(), 1.0F);
		int lumasense = getGradientOffset(c, c2, (Math.abs(((System.currentTimeMillis()) / 10)) / 80D) + ((ClientLoader.loaderInstance.fontManager.getFont("SFL 10").getHeight(Luma.clientName) + 135))).getRGB();
		//drawRainbowRect(3, 2, 75, 2, 2);
		//Gui.drawRect(3, 16, 75, 2, 0xff000000);
		if(HUD.clientName.getArraListValue().equalsIgnoreCase("Logo")) {
			Draw.instance.drawImg(new ResourceLocation("luma/mainmenu/logobig.png"), -37, -39, 130, 130);
		}
		if(HUD.clientName.getArraListValue().equalsIgnoreCase("Text")) {
			ClientLoader.loaderInstance.fontManager.getFont("SFB 10").drawStringWithShadow("L\u00A7fuma", 19.0f, 3.0f, c.getRGB());
			//ClientLoader.loaderInstance.fontManager.getFont("SFB 10").drawStringWithShadow(Luma.clientFiles.substring(0), 12.0f, 3.0f, rainbow(counter*300));
		}
		if(HUD.clientName.getArraListValue().equalsIgnoreCase("Rect")) {
			
			ScaledResolution sr2 = new ScaledResolution(Minecraft.getMinecraft());
			
			//Gui.drawRect((int) (sr2.getScaledWidth() - this.positionX - 2.0D - length), y, (int) (sr2.getScaledWidth() - this.positionX + 3.5D), y + 12, (new Color(0, 0, 0, 163)).getRGB());
			//ClientLoader.loaderInstance.fontManager.getFont("SFL 10").drawStringWithShadow(name, (float) (sr2.getScaledWidth() - this.positionX - ClientLoader.loaderInstance.fontManager.getFont("SFL 10").getWidth(name)), (y + 2), color);
            
			/*
			 *  Set text for strings
			 */
			
			String server = Minecraft.getMinecraft().isSingleplayer() ? "local server" : Minecraft.getMinecraft().getCurrentServerData().serverIP.toLowerCase();
            String text = "luma.best | " + Minecraft.getMinecraft().getDebugFPS() + " fps | " + server + " | " + ClientLoader.clientUser;
            float width = ClientLoader.loaderInstance.fontManager.getFont("SFL 10").getWidth(text) + 9;
            int height = 20;
            int posX = 2;
            int posY = 2;
           
            /*
             *  Draw boxes
             */
            
            Draw.drawRect(posX, posY, posX + width + 2, posY + height, new Color(5, 5, 5, 255).getRGB());
            Draw.drawBorderedRect(posX + .5, posY + .5, posX + width + 1.5, posY + height - .5, 0.5, new Color(40, 40, 40, 255).getRGB(), new Color(60, 60, 60, 255).getRGB(), true);
            Draw.drawBorderedRect(posX + 2, posY + 2, posX + width, posY + height - 2, 0.5, new Color(22, 22, 22, 255).getRGB(), new Color(60, 60, 60, 255).getRGB(), true);
            Draw.drawRect(posX + 2.5, posY + 2.5, posX + width - .5, posY + 4.5, new Color(9, 9, 9, 255).getRGB());
            Draw.drawGradientSideways(4, posY + 3, 4 + (width / 3), posY + 4, new Color(81, 149, 219, 255).getRGB(), new Color(180, 49, 218, 255).getRGB());
            Draw.drawGradientSideways(4 + (width / 3), posY + 3, 4 + ((width / 3) * 2), posY + 4, new Color(180, 49, 218, 255).getRGB(), new Color(236, 93, 128, 255).getRGB());
            Draw.drawGradientSideways(4 + ((width / 3) * 2), posY + 3, ((width / 3) * 3) + 1, posY + 4, new Color(236, 93, 128, 255).getRGB(), new Color(167, 171, 90, 255).getRGB());
             
            /*
             *  Draw text
             */
            
            ClientLoader.loaderInstance.fontManager.getFont("SFL 10").drawString(text, 5 + posX, 6 + posY, -1);
			
		}
		
		/*
         *  Draw corner information
         */
		ScaledResolution sr2 = new ScaledResolution(Minecraft.getMinecraft());
		
        int locY = sr2.getScaledHeight() - Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT - 2;
        int locX = sr2.getScaledWidth() - Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT - 2;
        double xC = Math.round(Minecraft.getMinecraft().thePlayer.posX);
        double yC = Math.round(Minecraft.getMinecraft().thePlayer.posY);
        double zC = Math.round(Minecraft.getMinecraft().thePlayer.posZ);
        
		String messageBuilder = "?7(Build: " + Luma.build + "?7) ?7(User: " + ClientLoader.clientUser + "?7)";
		//ClientLoader.loaderInstance.fontManager.getFont("SFL 10").drawStringWithShadow(Luma.clientName + " ?7(Build: " + Luma.build + "?7) ?7(User: " + ClientLoader.clientUser + "?7)", 2, 3, -1);
		ClientLoader.loaderInstance.fontManager.getFont("SFL 10").drawStringWithShadow(messageBuilder, locX - ClientLoader.loaderInstance.fontManager.getFont("SFL 10").getWidth(messageBuilder) + 10, locY, -1);
	}
	
	public static void drawPotions() {
		ScaledResolution scaledRes = new ScaledResolution(Minecraft.getMinecraft());
        int screenX = scaledRes.getScaledWidth();
        int screenY = scaledRes.getScaledHeight();
        int potionY = 12;
        for (PotionEffect effect : Minecraft.getMinecraft().thePlayer.getActivePotionEffects()) {
            Potion potion = Potion.potionTypes[effect.getPotionID()];
            String effectName = I18n.format(potion.getName()) + " " + (effect.getAmplifier() + 1) + " \2477" + Potion.getDurationString(effect);
            //Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(effectName, screenX - 2 - Minecraft.getMinecraft().fontRendererObj.getStringWidth(effectName), screenY - potionY, potion.getLiquidColor());

            potionY += 10;
        }
	}
	
	public static long renderRainbowRect(int left, int top, int right, int bottom, double time, long difference, long delay, RainbowDirection rainbowDirection) {
        int i;
        long endDelay = 0L;
        if(rainbowDirection == RainbowDirection.RIGHT) {
            for (i = 0; i < right - left; i++)
                Gui.drawRect(left + i, top, right, bottom, getRainbow(endDelay = delay + i * -difference, time).getRGB()); 
        }
        if(rainbowDirection == RainbowDirection.LEFT) {
            for (i = 0; i < right - left; i++)
                Gui.drawRect(left + i, top, right, bottom, getRainbow(endDelay = delay + i * -difference, time).getRGB()); 
        } 
        if(rainbowDirection == null) {
            for (i = 0; i < bottom - top; i++)
                Gui.drawRect(left, top + i, right, bottom, getRainbow(endDelay = delay + i * -difference, time).getRGB()); 
        }
        if(rainbowDirection == RainbowDirection.UP) {
            for (i = 0; i < bottom - top; i++)
                Gui.drawRect(left, top + i, right, bottom, getRainbow(endDelay = delay + i * -difference, time).getRGB()); 
        }
        return endDelay;
    }
	
	public static Color getRainbow(long delay, double time) {
        double rainbowState = Math.ceil((System.currentTimeMillis() * time + delay) / 20.0D);
        rainbowState %= 360.0D;
        return Color.getHSBColor((float)(rainbowState / 360.0D), 1.0F, 1.0F);
    }
    public enum RainbowDirection {
        LEFT, UP, RIGHT, DOWN;
    }
    
    public static int rainbow(int delay) {
	      double rainbowState = Math.ceil((System.currentTimeMillis() + delay) / 20.0);
	      rainbowState %= 360;
	      return Color.getHSBColor((float) (rainbowState / 360.0f), 0.8f, 0.7f).getRGB();
	}
    
    public static long drawRainbowRect(int left, int top, int right, int bottom, int size) {
        int size2 = 1;
        long topCol = renderRainbowRect(left, top - size2, right, top, 2.0D, 10L, 0L, RainbowDirection.RIGHT);
        long downCol = renderRainbowRect(left - size2, top - size, left, bottom + size2, 2.0D, 10L, 0L, RainbowDirection.DOWN);
        renderRainbowRect(right, top - size2, right + size2, bottom + size2, 2.0D, 10L, topCol, RainbowDirection.DOWN);
        renderRainbowRect(left, bottom, right, bottom + size2, 2.0D, 10L, downCol, RainbowDirection.RIGHT);
        return topCol;
    }
    
    public static Color getGradientOffset(Color color1, Color color2, double offset) {
        if (offset > 1) {
            double left = offset % 1;
            int off = (int) offset;
            offset = off % 2 == 0 ? left : 1 - left;
        }
        double inverse_percent = 1 - offset;
        int redPart = (int) (color1.getRed() * inverse_percent + color2.getRed() * offset);
        int greenPart = (int) (color1.getGreen() * inverse_percent + color2.getGreen() * offset);
        int bluePart = (int) (color1.getBlue() * inverse_percent + color2.getBlue() * offset);
        return new Color(redPart, greenPart, bluePart);
    }
}
