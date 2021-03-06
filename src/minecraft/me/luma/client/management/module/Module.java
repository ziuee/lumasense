package me.luma.client.management.module;

import java.util.ArrayList;

import com.google.gson.JsonObject;

import me.luma.client.core.registry.impl.ClientLoader;
import me.luma.client.hud.notifications.NotificationManager;
import me.luma.client.hud.notifications.NotificationType;
import me.luma.client.management.config.Serializable;
import me.luma.client.management.gui.clickgui.settings.Setting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.util.EnumChatFormatting;

public class Module implements Serializable {
	public ArrayList<Setting> settingList = new ArrayList<>();
	public static Minecraft mc = Minecraft.getMinecraft();
	public String name, displayName;
	protected boolean toggled;
	private Category category;
	private int key;
	
	public Module(String name, int key, Category category) {
		this.name = name;
		this.key = key;
		this.category = category;
		//ClientLoader.loaderInstance.moduleManager.modules.add(this);
	}
	
	public void onEnable() {
		ClientLoader.loaderInstance.eventManager.register(this);
	}
	
	public void onDisable() {
		ClientLoader.loaderInstance.eventManager.unregister(this);
	}
	
	public void onToggle() {}
	
	public void toggleLagBack() {
		if(toggled) {
			onDisable();
		}
	}
	
	public void toggle() {
		toggled = !toggled;
		if(toggled) {
			onEnable();
			NotificationManager.show(NotificationType.INFO, "Manager", EnumChatFormatting.GREEN + "Enabled ?r" + this.getName(), 2);
		} else {
			onDisable();
			NotificationManager.show(NotificationType.WARNING, "Manager", EnumChatFormatting.RED + "Disabled ?r" + this.getName(), 2);
		}
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getKey() {
		return key;
	}
	
	public void setKey(int key) {
		this.key = key;
	}
	
	public Category getCategory() {
		return category;
	}
	
	public void setCategory(Category category) {
		this.category = category;
	}
	
	public boolean isToggled() {
		return toggled;
	}
	
	public String getDisplayName() {
		return displayName == null ? name : displayName;
	}
	
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	
	public boolean onSendChatMessage(String s) {
		return true;
	}
	
	public boolean onRecieveChatMessage(S02PacketChat packet) {
		return true;
	}
	
	public void setToggled(boolean toggle) {
		this.toggle();
		//this.toggleLagBack();
		if (toggle) {
			this.onEnable();
			this.toggled = true;
		} else {
			this.onDisable();
			this.toggled = false;
		}
	}
	
	public void setToggled(Module module, boolean toggle) {
		this.toggle();
		//this.toggleLagBack();
		if (toggle && module.isToggled()) {
			this.onEnable();
			this.toggled = true;
		} else if(!module.isToggled() && !toggle) {
			this.onDisable();
			this.toggled = false;
		}
	}
	
	@Override
    public JsonObject save() {
        JsonObject object = new JsonObject();
        //object.addProperty("toggled", isToggled());
        object.addProperty("key", getKey());
        ArrayList<Setting> properties = this.settingList;
        if (properties != null && !properties.isEmpty()) {
            JsonObject propertiesObject = new JsonObject();

            for (Setting property : properties) {
            	if(property.getType() == property.type.BOOLEAN) {
                	propertiesObject.addProperty(property.getName(), property.getBooleanValue());
                }
            	if(property.getType() == property.type.ARRAYLIST) {
                	propertiesObject.addProperty(property.getName(), property.getArraListValue());
                }
            	if(property.getType() == property.type.SLIDER) {
                	propertiesObject.addProperty(property.getName(), property.getSliderValue());
                }
            }

            object.add("Properties", propertiesObject);
        }
        return object;
    }

	@Override
    public void load(JsonObject object) {
        /*if (object.has("toggled"))
            setToggled(object.get("toggled").getAsBoolean());*/

        if (object.has("key"))
            setKey(object.get("key").getAsInt());

        ArrayList<Setting> settings = this.settingList;

        if (object.has("Properties") && settings != null && !settings.isEmpty()) {
            JsonObject propertiesObject = object.getAsJsonObject("Properties");
            for (Setting property : settings) {
            	if(property.getType() == property.type.BOOLEAN) {
            		property.setValue(propertiesObject.get(property.getName()).getAsBoolean());
                }
            	if(property.getType() == property.type.ARRAYLIST) {
            		property.setValue(propertiesObject.get(property.getName()).getAsString());
                }
            	if(property.getType() == property.type.SLIDER) {
            		property.setValue(propertiesObject.get(property.getName()).getAsDouble());
                }
            }
        }
    }
}