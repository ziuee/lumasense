package me.luma.client.management.module.impl.movement;

import java.util.HashMap;
import java.util.Map;

import me.luma.client.core.registry.impl.ClientLoader;
import me.luma.client.management.event.EventTarget;
import me.luma.client.management.event.impl.EventMove;
import me.luma.client.management.event.impl.EventStep;
import me.luma.client.management.event.impl.EventUpdate;
import me.luma.client.management.gui.clickgui.settings.SettingBoolean;
import me.luma.client.management.gui.clickgui.settings.SettingSlider;
import me.luma.client.management.module.Category;
import me.luma.client.management.module.Module;
import me.luma.client.management.utils.TimerTEst;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.BlockPos;

public class Step extends Module {
	
	private static Map<Float, float[]> offsets = new HashMap<>();
	public static long lastStep, lastFuck, lastPacket;
	private boolean stepping;
	private int ncpNextStep;
	private int ticks;
	
	TimerTEst delay = new TimerTEst();

	private boolean hasStep;
	
	SettingSlider stepHeight;
	SettingSlider stepDelay;
	
	SettingBoolean smooth;
	
	public Step() {
		super("Step", 0, Category.MOVEMENT);
		
		//stepHeight = new SettingSlider("Step Height", this, 1, 1, 9, true, false);
		//stepDelay = new SettingSlider("Step Delay", this, 300, 1, 2000, true, false);
		
		//smooth = new SettingBoolean("Smooth", this, true);
	}
	
	@EventTarget
    public void onStep(EventStep e) {
         e.setStepHeight(1);
	}
	
	@Override
	public void onEnable() {
		super.onEnable();
		delay.reset();
	}
	
	@Override
	public void onDisable() {
		mc.timer.timerSpeed = 1.0f;
		mc.thePlayer.stepHeight = 0.5F;
		super.onDisable();
		delay.reset();
	}

}
