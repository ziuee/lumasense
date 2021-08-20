package me.luma.client.management.gui.clickgui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import com.google.common.collect.Lists;

import me.luma.client.core.Luma;
import me.luma.client.core.registry.impl.ClientLoader;
import me.luma.client.management.config.Config;
import me.luma.client.management.gui.clickgui.comp.GuiCheckBox;
import me.luma.client.management.gui.clickgui.comp.GuiSlider;
import me.luma.client.management.gui.clickgui.settings.Setting;
import me.luma.client.management.gui.clickgui.settings.SettingSlider;
import me.luma.client.management.module.Category;
import me.luma.client.management.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public class ClickGui extends GuiScreen {
	public static Minecraft mc = Minecraft.getMinecraft();
	public double x;
	public double y;
	private double x2;
	private double y2;
	public double width;
	public double height;
	public boolean dragging;
	public static double tempX;
	public static double tempY;
	public boolean listeningToSetAKey;
	
	protected List<GuiButton> categoryList = Lists.newArrayList();
	protected List<GuiButton> modulesList = Lists.newArrayList();
	protected static List<GuiButton> settingsList = Lists.newArrayList();
	protected List<GuiButton> configList = Lists.newArrayList();
	protected List<GuiButton> configsList = Lists.newArrayList();
	Category category;
	ArrayList<Module> modules = new ArrayList<>();
	ArrayList<Config> configs = new ArrayList<>();
	GuiTextField configName;
	public Module module = ClientLoader.loaderInstance.moduleManager.modules.get(0);
	public Module moduleToggle = ClientLoader.loaderInstance.moduleManager.modules.get(0);
	public Config selectedConfig;
	static float modulesOffset = 0.0F;
	static float settingsOffset = 0.0F;
	float modulesMotion = 0.0F;
	float settingsMotion = 0.0F;
	
	static float configsOffset = 0.0F;
	float configsMotion = 0.0F;
	public boolean isSettingNamed;
	public boolean makeConfigScreen;
	
	@Override
	public void initGui() {
		if(me.luma.client.management.module.impl.misc.ClickGui.clickguiblur.getBooleanValue()) {
			if (OpenGlHelper.shadersSupported && mc.getRenderViewEntity() instanceof EntityPlayer) {
				if (mc.entityRenderer.theShaderGroup != null) {
					mc.entityRenderer.theShaderGroup.deleteShaderGroup();
				}
				mc.entityRenderer.loadShader(new ResourceLocation("shaders/post/blur.json"));
			}
		}
		double pwidth = 450;
		double pheight = 275;
		double px = 35;
		double py = 25;
		double pxplus = pwidth + 25;
		
		if(tempX != 0 && tempY != 0) {
			this.x = tempX;
			this.y = tempY;
		} else {
			this.x = px;
			this.y = py;
		}
		this.width = pwidth;
		this.height = pheight;
		
		this.modulesOffset = 0.0F;
	    this.categoryList.clear();
	    for (int i = 0; i < (Category.values()).length; i++) {
	    	this.categoryList.add(new GuiButton(i, (int) x, (int) (y + 15 + i * 22), 75, 20, Character.toUpperCase(Category.values()[i].name().toLowerCase().charAt(0)) + Category.values()[i].name().toLowerCase().substring(1)));
	    }
	    //this.configList.add(new GuiButton(1, (int)x, (int)y, 100, 20, false, "Config"));
	    //this.configList.add(new GuiButton(2, (int)x, (int)y, 162, 20, true, "Add"));
	    //this.configList.add(new GuiButton(3, (int)x, (int)y, 162, 20, true, "Remove"));
	    //this.configList.add(new GuiButton(4, (int)x, (int)y, 162, 20, true, "Load"));
	    addModules();
	    addSettings();
	    this.settingsList.clear();
	    /*if(ClientLoader.loaderInstance.configManager.getContents().size() > 0) {
	    	selectedConfig = ClientLoader.loaderInstance.configManager.getContents().get(0);
	    } else {
	    	// Nothing Don't Remove Else Or Crash WARNING
	    }
	    this.configName = new GuiTextField(-1, this.fontRendererObj, (int)x, (int)y, 160, 20);
	    this.configName.setMaxStringLength(256);
	    this.configName.setFocused(true);
	    this.configName.setCanLoseFocus(true);*/
	}
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		if (this.dragging) {
			x = x2 + mouseX;
			y = y2 + mouseY;
			tempX = x + mouseX;
			tempY = y + mouseY;
			//ClientLoader.addChatMessage("tempX = " + tempX);
			//ClientLoader.addChatMessage("tempY = " + tempY); // At Least Values Works XD
		}
		int offset = (int) (-Mouse.getDWheel() / 7);
	    if(isHoveredOnTheModules(mouseX, mouseY)) {
	    	this.modulesMotion += offset;
	    }
	    if(isHoveredOnTheSettings(mouseX, mouseY)) {
	    	this.settingsMotion += offset;
	    }
	    if(isHoveredOnTheConfigs(mouseX, mouseY)) {
	    	this.configsMotion += offset;
	    }
		
		//Gui.drawRect(x, y, x + width, y + height - 2, 0xff121212);
		Gui.drawRect(x, y, x + width, y + 15, 0xff202020); 
	    //drawBorderedRect((int) x, (int) y, (int) x + (int) width, (int) y + 15, 1, 0x90FFFFFF, 0xff202020); // Main
		Gui.drawRect(x, y + 15, x + width - 350, y + height - 2, 0xff222222); // Category!
	    //drawBorderedRect((int) x, (int) y + 14, (int) x + (int) width - 330, (int) y + (int) height - 2, 1, 0x90FFFFFF, 0xff222222); // Category
		//drawBorderedRect((int) x + (450 - 350), (int) y + 14, (int) x + (int) width - 170, (int) y + (int) height - 2, 1, 0x90FFFFFF, 0xff191919); // Module
		Gui.drawRect(x + (450 - 350), y + 15, x + width - 170, y + height - 2, 0xff191919); // This Module!
		//drawBorderedRect((int) x - 1 + (int) width, (int) y + 14, (int) x + (int) width - 170, (int) y + (int) height - 2, 1, 0x90FFFFFF, 0xff222222);
		Gui.drawRect(x + width, y + 15, x + width - 170, y + height - 2, 0xff222222); // Settings Panel

		/*for (int i = 0; i < this.categoryList.size(); i++) {
	    	((GuiButton)this.categoryList.get(i)).drawButton(mc, mouseX, mouseY);
	    }*/
		GL11.glPushMatrix();
	    prepareScissorBox((int)x, (int)y + 15, (float) ((int)this.width + x), (int)(y + this.height - 2)); // Esto Es Lo Que Hace La Cortina Para El Scroll
	    GL11.glEnable(3089);
	    for (GuiButton m : categoryList) {
			m.xPosition = (int) x + 13;
			m.yPosition = (int) y + 18 + m.id * 22;
		    m.drawButton(mc, mouseX, mouseY);
		}
	    for (GuiButton m : modulesList) {
			m.xPosition = (int) x + 8 + (450 - 350);
			m.yPosition = (int) y + 18 + m.id * 22 - (int)this.modulesOffset;
		    m.drawButton(mc, mouseX, mouseY);
		}
	    for (GuiButton m : settingsList) {
			//m.yPosition = (int) y + 18 + m.id * 22 - (int)this.settingsOffset;
		    m.drawButton(mc, mouseX, mouseY);
		    int tempy = 32;
		    int color = 14737632;
		    if(this.isSettingNamed) {
		    	//mc.fontRendererObj.drawString(module.getName() + ":", x + 285, y + 20 - settingsOffset, color);
		    	ClientLoader.loaderInstance.fontManager.getFont("SFL 10").drawString(module.getName() + ":", x + 285, y + 20 - settingsOffset, color);
		    }
		    if(m instanceof GuiCheckBox) {
		    	m.xPosition = (int) ((int) x + width - 165);
	        	m.yPosition = (int) y + tempy + 4 + m.id * 22 - (int)this.settingsOffset; // Check Box
	        } else if(!m.isModule2) {
	        	m.xPosition = (int) ((int) x + width - 165);
	        	m.yPosition = (int) ((int) y + tempy + m.id * 22 - (int)this.settingsOffset); // This Is Sliders And ArrayThingi
	        } else {
	        	m.xPosition = (int) x + 265;
	        	m.yPosition =(int) (y + 15 - settingsOffset);
	        }
		}
	    /*for (GuiButton m : configList) {
	    	if(m.id == 1) {
				m.xPosition = (int) x;
				m.yPosition = (int) y + 240;
			    m.drawButton(mc, mouseX, mouseY);
	    	}
	    	if(m.id == 2) {
	    		if(this.makeConfigScreen) {
	    			m.xPosition = (int) x + 284; // Add
					m.yPosition = (int) y + 40;
				    m.drawButton(mc, mouseX, mouseY);
	    		}
	    	}
	    	if(m.id == 3) {
	    		if(this.makeConfigScreen) {
	    			m.xPosition = (int) x + 284; // Remove
					m.yPosition = (int) y + 60;
				    m.drawButton(mc, mouseX, mouseY);
	    		}
	    	}
	    	
	    	if(m.id == 4) {
	    		if(this.makeConfigScreen) {
	    			m.xPosition = (int) x + 284; // Load
					m.yPosition = (int) y + 80;
				    m.drawButton(mc, mouseX, mouseY);
	    		}
	    	}
	    }
	    if(this.makeConfigScreen) {
		    this.configName.xPosition = (int) (x + 285);
		    this.configName.yPosition = (int) (y + 20);
		    this.configName.drawTextBox();
	    }
	    for (GuiButton m : configsList) {
	    	if(this.makeConfigScreen) {
		    	m.xPosition = (int) x + 8 + (450 - 350);
				m.yPosition = (int) ((int) y + 18 + m.id * 22 - configsOffset);
				m.drawButton(mc, mouseX, mouseY);
	    	}
	    }*/
	    GL11.glDisable(3089);
	    GL11.glPopMatrix();
	    for (GuiButton m : settingsList) {
	    	if (m instanceof GuiSlider) {
	    		if(((GuiSlider) m).dragging) {
		    		SettingSlider setting = (SettingSlider) this.module.settingList.get(m.id);
		    		setting.action((GuiSlider) m);
		        	continue;
	    		}
		    }
	    }
	    ScaledResolution scr = new ScaledResolution(mc);
	    listen:
		    if(listeningToSetAKey == true) {
		    	drawRect(0, 0, scr.getScaledWidth(), scr.getScaledHeight(), 0x88101010);
				GL11.glPushMatrix();
				GL11.glTranslatef(scr.getScaledWidth() / 2, scr.getScaledHeight() / 2, 0.0F);
				GL11.glScalef(4.0F, 4.0F, 0F);
				ClientLoader.loaderInstance.fontManager.getFont("SFL 10").drawStringWithShadow("Current Key: " + (this.moduleToggle.getKey() > -1 ? " (" + Keyboard.getKeyName(this.moduleToggle.getKey())+ ")" : ""), -55, -12, 0xffffffff);
				GL11.glScalef(0.5F, 0.5F, 0F);
				ClientLoader.loaderInstance.fontManager.getFont("SFL 10").drawStringWithShadow("Press ESC To Unbind " + this.moduleToggle.getName(), -90, 0, 0xffffffff);
				GL11.glPopMatrix();
				break listen;
	    	}
	    
	    ClientLoader.loaderInstance.fontManager.getFont("SFL 10").drawStringWithShadow(Luma.clientName, (int) x + 15, (int) y + 2, -1);
	}
	
	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		if (listeningToSetAKey) {
			if (keyCode != Keyboard.KEY_ESCAPE) {
				this.moduleToggle.setKey(keyCode);
			} else {
				this.moduleToggle.setKey(Keyboard.KEY_NONE);
			}
			listeningToSetAKey = false;
		}
	    Keyboard.enableRepeatEvents(true);
	    super.keyTyped(typedChar, keyCode);
	    //this.configName.textboxKeyTyped(typedChar, keyCode);
	}
	
	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		//this.configName.mouseClicked(mouseX, mouseY, mouseButton);
		if (isHovered(mouseX, mouseY)) {
			x2 = this.x - mouseX;
			y2 = this.y - mouseY;
			dragging = true;
		}
		for (int i = 0; i < this.categoryList.size(); i++) {
	        GuiButton guibutton = this.categoryList.get(i);
	        if (guibutton.mousePressed(mc, mouseX, mouseY)) {
	            guibutton.playPressSound(mc.getSoundHandler());
	            this.category = Category.values()[i];
	            addModules();
	            settingsList.clear();
	            isSettingNamed = false;
	            makeConfigScreen = false;
	            for (GuiButton t : this.categoryList) {
	            	t.enabled = true;
	            }
	            guibutton.enabled = false;
	        }
	    }
		for (GuiButton m : this.modulesList) {
	    	if (m.id == -1) {
		        continue;
		    }
	       // m.yPosition = 40 + m.id * 25 - (int)this.modulesOffset;
	    	m.xPosition = (int) x + (435 - 320);
			m.yPosition = (int) y + 18 + m.id * 22 - (int)this.modulesOffset;
			
	        if (m.mousePressed(mc, mouseX, mouseY)) {
		        m.playPressSound(mc.getSoundHandler());
		        this.moduleToggle = this.modules.get(m.id);
		        if (mouseButton == 0) {
					this.moduleToggle.toggle();
				} else if (mouseButton == 1) {
					this.module = this.modules.get(m.id);
					isSettingNamed = true;
					//Luar.addChatMessage("Right Click");
					addSettings();
				} else if (mouseButton == 2) {
					listeningToSetAKey = true;
				}
	        }
	    }
		for (GuiButton m : settingsList) {
	    	if (m.id == -1) {
	    		continue;
	    	}
	    	/*m.xPosition = (int) ((int) x + width - 165);
			m.yPosition = (int) y + 18 + m.id * 22 - (int)this.settingsOffset;*/
	        if (m.mousePressed(mc, mouseX, mouseY)) {
	        	if(m != null && module != null && this.module.settingList.size() > 0 && this.module.settingList.size() > m.id) {
        			Setting setting = null;
	        		if(module.settingList.get(m.id) != null) {
	        			setting = this.module.settingList.get(m.id);
	        			if(setting != null) {
			        		setting.action(m);
				        	m.displayString = (setting.getGui()).displayString;
	        			}
	        		}
        		}
	        }
	        //ClientLoader.loaderInstance.configManager.saveConfig("configsss");
	    }
		/*for (GuiButton m : configList) {
			//m.xPosition = (int) x + (435 - 320);
			//m.yPosition = (int) y + 18 + m.id * 22 - (int)this.configsOffset;
			
			if (m.mousePressed(mc, mouseX, mouseY)) {
				if(m.id == 1) {
					modulesList.clear();
					settingsList.clear();
					makeConfigScreen = true;
					addConfigs();
				}
				if(m.id == 2) { // Add
					//addConfigs();
					if(this.configName.getText().length() == 0) {
						ClientLoader.addChatMessage("Please specify a name.");
					} else {
						ClientLoader.loaderInstance.configManager.saveConfig(this.configName.getText());
						addConfigs();
						ClientLoader.addChatMessage("§aAdded§r Config: " + this.configName.getText());
					}
				}
				if(m.id == 3) { // Remove
					if(this.selectedConfig == null) {
						
					} else {
						ClientLoader.loaderInstance.configManager.deleteConfig(this.selectedConfig.getName());
						addConfigs();
						ClientLoader.addChatMessage("§cRemoved§r Config: " + this.selectedConfig.getName());
					}
				}
				if(m.id == 4) { // Load
					//ClientLoader.addChatMessage("Loading Config " + this.selectedConfig.getName());
					if(this.selectedConfig == null) {
						
					} else {
						ClientLoader.loaderInstance.configManager.loadConfig(this.selectedConfig.getName());
						ClientLoader.addChatMessage("§eLoaded§r Config: " + this.selectedConfig.getName());
					}
				}
				//ClientLoader.addChatMessage("Click Settings DSDDSSD");
			}
		}
		for (GuiButton m : this.configsList) {
	        if (m.mousePressed(mc, mouseX, mouseY)) {
		        m.playPressSound(mc.getSoundHandler());
		        this.selectedConfig = this.configs.get(m.id);
		     
	        }
	    }*/
		super.mouseClicked(mouseX, mouseY, mouseButton);
	}
	@Override
	protected void mouseReleased(int mouseX, int mouseY, int state) {
		if(dragging) {
			tempX = x + mouseX;
			tempY = y + mouseY;
			dragging = false;
		}
		for (GuiButton m : settingsList) {
	    	if (m instanceof GuiSlider) {
	    		if(((GuiSlider) m).dragging) {
		    		SettingSlider setting = (SettingSlider) this.module.settingList.get(m.id);
		    		((GuiSlider) m).dragging = false;
		    		continue;
	    		}
		    }
	    }
		super.mouseReleased(mouseX, mouseY, state);
	}
	public void handleMouseInput() throws IOException {
	    super.handleMouseInput();
	}
	public boolean isHoveredOnTheModules(int mouseX, int mouseY) {
		int x = (int) (this.x + (450 - 350));
		int y = (int) this.y + 15;
		int w = (int) (this.x + width - 170);
		int h = (int) ((int) this.y + height - 2);
		if(mouseX > x && mouseX < w && mouseY > y && mouseY < h) {
			return true;
		}
		//Gui.drawRect(x + (450 - 350), y + 15, x + width - 170, y + height - 2, 0xff202020); // This Module!
		return false;
	}
	public boolean isHoveredOnTheConfigs(int mouseX, int mouseY) {
		int x = (int) (this.x + (450 - 350));
		int y = (int) this.y + 15;
		int w = (int) (this.x + width - 170);
		int h = (int) ((int) this.y + height - 2);
		if(mouseX > x && mouseX < w && mouseY > y && mouseY < h) {
			return true;
		}
		//Gui.drawRect(x + (450 - 350), y + 15, x + width - 170, y + height - 2, 0xff202020); // This Module!
		return false;
	}
	public boolean isHoveredOnTheSettings(int mouseX, int mouseY) {
		int x = (int) (this.x + width);
		int y = (int) this.y + 15;
		int w = (int) (this.x + width - 170);
		int h = (int) ((int) this.y + height - 2);
		if(mouseX < x && mouseX > w && mouseY > y && mouseY < h) {
			return true;
		}
		//Gui.drawRect(x + width, y + 15, x + width - 170, y + height - 2, 0xff424547);
		return false;
	}
	public void updateScreen() {
		//this.configName.updateCursorCounter();
	    this.modulesMotion /= 2.0F;
	    this.modulesOffset += this.modulesMotion;
	    this.modulesOffset = (float) Math.min((this.modulesList.size() * 25 - this.height + 50), this.modulesOffset);
	    this.modulesOffset = Math.max(0.0F, this.modulesOffset);
	    this.settingsMotion /= 2.0F;
	    this.settingsOffset += this.settingsMotion;
	    this.settingsOffset = (float) Math.min((settingsList.size() * 25 - this.height + 50), this.settingsOffset);
	    this.settingsOffset = Math.max(0.0F, this.settingsOffset);
	    
	    /*this.configsMotion /= 2.0F;
	    this.configsOffset += this.configsMotion;
	    this.configsOffset = (float) Math.min((configsList.size() * 25 - this.height + 50), this.configsOffset);
	    this.configsOffset = Math.max(0.0F, this.configsOffset);*/
	}
	@Override
	public void onGuiClosed() {
		if (mc.entityRenderer.theShaderGroup != null) {
			mc.entityRenderer.theShaderGroup.deleteShaderGroup();
			mc.entityRenderer.theShaderGroup = null;
		}
		tempX = x;
		tempY = y;
		isSettingNamed = false;
	}
	public boolean isHovered(int mouseX, int mouseY) {
		return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + 15;
	}
	public void addModules() {
		this.modulesList.clear();
	    this.modules = new ArrayList<>();
	    for (Module m : ClientLoader.moduleManager.getModulesInCategory(category)) {
	    	this.modules.add(m);
	    }
	    for (int i = 0; i < this.modules.size(); i++) {
	    	this.modulesList.add(new GuiButton(i, (int) x, (int) y + 25 * i, (int) ((this.width - 120) / 2), 20, ((Module)this.modules.get(i)).getName(), true, ((Module)this.modules.get(i)))); 
	    }
	    this.modulesOffset = 0.0F;
	}
	public void addSettings() {
		settingsList.clear();
		ClientLoader.loaderInstance.fontManager.getFont("SFL 10").drawString(module.getName() + ":", x + 285, y + 20 - settingsOffset, -1);
		//this.settingsList.add(new GuiButton(-1, (int) x + 275, (int) ((int) y + 2 - settingsOffset), 75, 20, module.getName(), true));
	    for (int i = 0; i < this.module.settingList.size(); i++) {
	        GuiButton gui = ((Setting)this.module.settingList.get(i)).getGui();
	        gui.id = i;
	        /*gui.xPosition = (int) (x + width + 5);
	        gui.yPosition = (int) (y + i * 25);*/
	        if(gui instanceof GuiCheckBox) {
	        	gui.yPosition = (int) (y + i * 25);
	        }
	        gui.width = (int) ((this.width - 120) / 2);
	        settingsList.add(gui);
	    }
	    this.settingsOffset = 0.0F;
	}
	public void addConfigs() {
		this.configsList.clear();
	    this.configs = new ArrayList<>();
	    for (Config m : ClientLoader.loaderInstance.configManager.getContents()) {
	    	this.configs.add(m);
	    }
	    //this.configs.add(m);
	    //this.configsList.add(new GuiButton(5, (int) x, (int) y + 100, (int) ((this.width - 120) / 2), 20, "FAKE")); 
	    for (int i = 0; i < this.configs.size(); i++) {
	    	//GuiButton gui = (GuiButton) ClientLoader.loaderInstance.configManager.getContents();
	    	this.configsList.add(new GuiButton(i, (int) x, (int) ((int) y + 25 * i - configsOffset), (int) ((this.width - 120) / 2), 20, configs.get(i).getName())); 
	    	//this.configsList.add(gui);
	    }
	    this.configsOffset = 0.0F;
	}
	private void prepareScissorBox(float x, float y, float x2, float y2) {
	    ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
	    int factor = sr.getScaleFactor();
	    GL11.glScissor((int)(x * factor), (int)((sr.getScaledHeight() - y2) * factor), (int)((x2 - x) * factor), (int)((y2 - y) * factor));
	}
	
	public static void drawBorderedRect(int x, int y, int x1, int y1, int size, int borderC, int insideC) {
		drawRect(x + size, y + size, x1 - size, y1 - size, insideC);
		drawRect(x + size, y + size, x1, y, borderC);
		drawRect(x, y, x + size, y1, borderC);
        drawRect(x1, y1, x1 - size, y + size, borderC);
        drawRect(x, y1 - size, x1, y1, borderC);
	}
}