package me.luma.client.management.module;

import java.util.ArrayList;

import me.luma.client.management.module.impl.combat.*;
import me.luma.client.management.module.impl.exploits.*;
import me.luma.client.management.module.impl.misc.*;
import me.luma.client.management.module.impl.movement.*;
import me.luma.client.management.module.impl.player.*;
import me.luma.client.management.module.impl.render.*;

public class ModuleManager {
	
	public ArrayList<Module> modules = new ArrayList<Module>();
	
	public ModuleManager() {
		modules.add(new TargetStrafe());
		modules.add(new Criticals());
		modules.add(new KillAura());
		modules.add(new Velocity());
		modules.add(new AntiBot());
		
		modules.add(new ScaffoldTest());
		modules.add(new NoSlowdown());
		modules.add(new Scaffold());
		modules.add(new LongJump());
		modules.add(new AntiVoid());
		modules.add(new InvMove());
		modules.add(new Sprint());
		modules.add(new Phase());
		modules.add(new Speed());
		modules.add(new Step());
		modules.add(new Fly());
		
		modules.add(new ChestStealer());
		modules.add(new InvManager());
		modules.add(new AntiDesync());
		modules.add(new AutoTool());
		modules.add(new AutoPlay());
		modules.add(new AutoPot());
		modules.add(new NoFall());
		
		modules.add(new DamageParticles());
		modules.add(new CustomHitColor());
		modules.add(new TimeChanger());
		modules.add(new Animations());
		modules.add(new Fullbright());
		modules.add(new MotionBlur());
		modules.add(new TargetHUD());
		modules.add(new NameTags());
		modules.add(new Chams());
		modules.add(new Radar());
		modules.add(new ESP());
		modules.add(new HUD());
		modules.add(new BPS());
		
		modules.add(new PPESP());
		
		modules.add(new Disabler());
		
		modules.add(new KillSults());
		modules.add(new ClickGui());
		modules.add(new Logger());
	}

	public ArrayList<Module> getModules() {
		return modules;
	}
	
	public Module getModule(String name) {
		return modules.stream().filter(module -> module.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
	}
	public ArrayList<Module> getModulesInCategory(Category categoryIn){
		ArrayList<Module> mods = new ArrayList<Module>();
		for(Module m : modules) {
			if(m.getCategory() == categoryIn)
				mods.add(m);
		}
		return mods;
	}
}
