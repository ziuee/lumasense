package me.luma.client.management.module.impl.combat;

import org.lwjgl.opengl.GL11;

import io.netty.util.internal.ThreadLocalRandom;
import me.luma.client.core.registry.impl.ClientLoader;
import me.luma.client.management.command.utils.friend.Friend;
import me.luma.client.management.event.EventManager;
import me.luma.client.management.event.EventTarget;
import me.luma.client.management.event.impl.Event3D;
import me.luma.client.management.event.impl.EventPreMotionUpdate;
import me.luma.client.management.gui.clickgui.settings.SettingBoolean;
import me.luma.client.management.gui.clickgui.settings.SettingSlider;
import me.luma.client.management.module.Category;
import me.luma.client.management.module.Module;
import me.luma.client.management.utils.TimeHelper;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemSword;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.util.Vec3;

public class KillAura extends Module
{
    public static EntityLivingBase entityAttacked;
    private EntityLivingBase renderingTarget;
    public boolean fakeEntityAttacked;
    private TimeHelper timeHelper;
    SettingBoolean autoblock;
    SettingBoolean predict;
    SettingBoolean aimlock;
    SettingBoolean rotations;
    SettingBoolean Invisibles;
    SettingBoolean Players;
    SettingBoolean Animals;
    SettingBoolean Monsters;
    SettingBoolean Villagers;
    SettingBoolean Teams;
    SettingBoolean Friends;
    SettingBoolean AfterDeath;
    SettingBoolean targetespthingyay69420;
    public static SettingSlider reach;
    SettingSlider delay;
    SettingSlider minSpeed;
    SettingSlider maxSpeed;
    
    public KillAura() {
        super("KillAura", 19, Category.COMBAT);
        this.timeHelper = new TimeHelper();
        this.minSpeed = new SettingSlider("Min Speed", (Module)this, 90.0, 0.0, 180.0, false, true);
        this.maxSpeed = new SettingSlider("Max Speed", (Module)this, 150.0, 0.0, 180.0, false, true);
        this.reach = new SettingSlider("Reach", (Module)this, 4.0, 3.0, 6.0, false, true);
        this.delay = new SettingSlider("Delay", (Module)this, 10.0, 1.0, 20.0, false, true);
        this.autoblock = new SettingBoolean("Auto Block", (Module)this, true);
        this.rotations = new SettingBoolean("Rotations", (Module)this, true);
        this.aimlock = new SettingBoolean("Aim Lock", (Module)this, false);
        this.predict = new SettingBoolean("Predict", (Module)this, true);
        this.Invisibles = new SettingBoolean("Invisibles", (Module)this, false);
        this.Players = new SettingBoolean("Players", (Module)this, true);
        this.Animals = new SettingBoolean("Animals", (Module)this, false);
        this.Monsters = new SettingBoolean("Monsters", (Module)this, false);
        this.Villagers = new SettingBoolean("Villagers", (Module)this, false);
        this.Villagers = new SettingBoolean("Teams", (Module)this, true);
        this.AfterDeath = new SettingBoolean("AfterDeath", (Module)this, false);
        this.targetespthingyay69420 = new SettingBoolean("Target ESP THingy", (Module)this, false);
    }
    
    @EventTarget
    public void onTick(final EventPreMotionUpdate event) {
        for (final Object o : KillAura.mc.theWorld.loadedEntityList) {
            if (o instanceof EntityLivingBase && o != KillAura.mc.thePlayer) {
                final EntityLivingBase e = (EntityLivingBase)o;
                final boolean block = this.check(e) && this.autoblock.getBooleanValue() && KillAura.mc.thePlayer.getHeldItem() != null && KillAura.mc.thePlayer.getHeldItem().getItem() != null && KillAura.mc.thePlayer.getHeldItem().getItem() instanceof ItemSword;
                if (block && e.getDistanceToEntity((Entity)KillAura.mc.thePlayer) < KillAura.reach.getSliderValue()) {
                    mc.playerController.sendUseItem((EntityPlayer)KillAura.mc.thePlayer, mc.theWorld, KillAura.mc.thePlayer.getHeldItem());
                }
                if (!this.check(e) || !this.timeHelper.isDelayComplete((float)(1000.0 / this.delay.getSliderValue()))) {
                    continue;
                }
                this.attack(e);
                KillAura.entityAttacked = e;
            }
        }
        if (KillAura.entityAttacked == null) {
            return;
        }
        
        renderingTarget = entityAttacked;
        
        final float[] rotationsCurrent = { ClientLoader.ROTATION_UTIL.serverYaw, ClientLoader.ROTATION_UTIL.serverPitch };
        final float[] rotationsInstant = ClientLoader.ROTATION_UTIL.getRotations((Entity)KillAura.entityAttacked, (boolean)this.predict.getBooleanValue(), 1.0);
        final float[] rotationsSmooth = ClientLoader.ROTATION_UTIL.smoothRotation(rotationsCurrent, rotationsInstant, this.calculateRotationSpeed() + ThreadLocalRandom.current().nextInt(5));
        final float[] rotations = rotations(entityAttacked);
        final float yaw = rotations[0];
        final float pitch = rotations[1];
        if (KillAura.entityAttacked.getDistanceToEntity((Entity)KillAura.mc.thePlayer) <= KillAura.reach.getSliderValue()) {
        	event.setYaw(rotations[0]);
        	event.setPitch(rotations[1]);
        	KillAura.mc.thePlayer.rotationYawHead = yaw;
        	KillAura.mc.thePlayer.renderYawOffset = yaw;
        }
        this.fakeEntityAttacked = true;
    }
    
    private boolean down;
    private double time;
    
    /*private boolean check(Entity e) {
    	 if (e.isInvisible()) {
             return this.Invisibles.getBooleanValue();
         }
         if (e instanceof EntityPlayer) {
             return this.Players.getBooleanValue();
         }
         if (e instanceof EntityAnimal) {
             return this.Animals.getBooleanValue();
         }
         if (e instanceof EntityMob) {
             return this.Monsters.getBooleanValue();
         }
         if (e instanceof EntityVillager) {
             return this.Villagers.getBooleanValue();
         }
         if (isTeam((EntityLivingBase) e) && this.Teams.getBooleanValue()) { 
             return this.Teams.getBooleanValue();
         }
         return true;
    }*/
    
    
    private boolean check(final EntityLivingBase e) {
        if (e.isInvisible()) {
            return this.Invisibles.getBooleanValue();
        }
        
        if (e instanceof EntityPlayer) {
            return this.Players.getBooleanValue();
        }
        if (e instanceof EntityAnimal) {
            return this.Animals.getBooleanValue();
        }
        if (e instanceof EntityMob) {
            return this.Monsters.getBooleanValue();
        }
        if (e instanceof EntityVillager) {
            return this.Villagers.getBooleanValue();
        }
        if (isTeam((EntityLivingBase) e) && this.Teams.getBooleanValue()) {
            return this.Teams.getBooleanValue();
        }
        return e.getHealth() > 0.0f || this.AfterDeath.getBooleanValue();
    } 
    
    public static String getTabName(EntityPlayer player) {
    	String realName = "";
    	for(Object o5 : mc.ingameGUI.getTabList().getPlayerList()) {
    		NetworkPlayerInfo o = (NetworkPlayerInfo) o5;
            String mcName = mc.ingameGUI.getTabList().getPlayerName(o);
            if ((mcName.contains(player.getName())) && (player.getName() != mcName)) {
                realName = mcName;
            }
    	}
    	return realName;
    }
    
    private void attack(final EntityLivingBase e) {
        if (e.getDistanceToEntity((Entity)KillAura.mc.thePlayer) <= KillAura.reach.getSliderValue()) {
            if (this.fakeEntityAttacked) {
                KillAura.mc.thePlayer.swingItem();
                KillAura.mc.thePlayer.sendQueue.addToSendQueue((Packet)new C02PacketUseEntity((Entity)e, C02PacketUseEntity.Action.ATTACK));
                this.timeHelper.setLastMS();
            }
            KillAura.entityAttacked = null;
            this.fakeEntityAttacked = false;
        }
    }
    
    public final boolean isTeam(EntityLivingBase e) {
    	if(!(e instanceof EntityPlayer))
    		return false;
    	if(mc.thePlayer.getDisplayName() != null && e.getDisplayName() != null) {
    		final String playerName = mc.thePlayer.getDisplayName().getFormattedText().replace("?r", "");
    		final String entityName = e.getDisplayName().getFormattedText().replace("?r", "");
    		if(playerName.isEmpty() || entityName.isEmpty())
    			return false;
    		return playerName.charAt(1) == entityName.charAt(1);
    	}
    	return false;
    }
    
    /*@EventTarget
    public void onSendPacket(final EventSendPacket event) {
        final Packet packet = event.getPacket();
        if (this.rotations.getBooleanValue()) {
            if (KillAura.entityAttacked == null) {
                return;
            }
            if (KillAura.mc.thePlayer == null) {
                return;
            }
            if (KillAura.entityAttacked.getDistanceToEntity((Entity)KillAura.mc.thePlayer) <= KillAura.reach.getSliderValue()) {
                final float[] rotationsCurrent = { ClientLoader.ROTATION_UTIL.serverYaw, ClientLoader.ROTATION_UTIL.serverPitch };
                final float[] rotationsInstant = ClientLoader.ROTATION_UTIL.getRotations((Entity)KillAura.entityAttacked, (boolean)this.predict.getBooleanValue(), 1.0);
                final float[] rotationsSmooth = ClientLoader.ROTATION_UTIL.smoothRotation(rotationsCurrent, rotationsInstant, this.calculateRotationSpeed() + ThreadLocalRandom.current().nextInt(5));
                if (packet instanceof C03PacketPlayer) {
                    final float yaw = rotationsInstant[0];
                    final float pitch = rotationsInstant[1];
                    if (!this.aimlock.getBooleanValue()) {
                        KillAura.mc.thePlayer.rotationYaw = yaw;
                        KillAura.mc.thePlayer.rotationPitch = pitch;
                        C03PacketPlayer.yaw = yaw;
                        C03PacketPlayer.pitch = pitch;
                        KillAura.mc.thePlayer.rotationYawHead = yaw;
                        KillAura.mc.thePlayer.renderYawOffset = yaw;
                    }
                }
            }
        }
    }*/
    
    public float[] rotations(Entity e) {
        double deltaX = e.boundingBox.minX + (e.boundingBox.maxX - e.boundingBox.minX + 0.1) - mc.thePlayer.posX, deltaY = e.posY - 4.25 + e.getEyeHeight() - mc.thePlayer.posY + mc.thePlayer.getEyeHeight(), deltaZ = e.boundingBox.minZ + (e.boundingBox.maxX - e.boundingBox.minX) - mc.thePlayer.posZ, distance = Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaZ, 2));
        float yaw = (float) Math.toDegrees(-Math.atan(deltaX / deltaZ)), pitch = (float) -Math.toDegrees(Math.atan(deltaY / distance));
        final double v = Math.toDegrees(Math.atan(deltaZ / deltaX));
        if (deltaX < 0 && deltaZ < 0) yaw = (float) (90 + v);
        else if (deltaX > 0 && deltaZ < 0) yaw = (float) (-90 + v);
        return new float[] {yaw, pitch};
    }
    
    private float calculateRotationSpeed() {
        if (this.minSpeed.getSliderValue() == this.maxSpeed.getSliderValue()) {
            return 180.0f;
        }
        return (float)ThreadLocalRandom.current().nextDouble((double)this.minSpeed.getSliderValue(), (double)this.maxSpeed.getSliderValue());
    }
    
    public void onEnable() {
        super.onEnable();
        EventManager.register(this);
        TimeHelper.reset();
    }
    
    public void onDisable() {
        super.onDisable();
        EventManager.unregister(this);
        KillAura.entityAttacked = null;
        this.fakeEntityAttacked = false;
        TimeHelper.reset();
    }
}
