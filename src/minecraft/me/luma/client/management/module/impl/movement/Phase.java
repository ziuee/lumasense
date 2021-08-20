package me.luma.client.management.module.impl.movement;

import me.luma.client.management.event.EventTarget;
import me.luma.client.management.event.impl.EventCollide;
import me.luma.client.management.event.impl.EventUpdate;
import me.luma.client.management.module.Category;
import me.luma.client.management.module.Module;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockHopper;
import net.minecraft.client.Minecraft;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;

public class Phase extends Module {

	private int reset;
	
	public Phase() {
		super("Phase", 0, Category.MOVEMENT);
	}
	
	@EventTarget
	public void onUpdate(EventUpdate event) {
		reset -= 1;
		double xOff = 0;
        double zOff = 0;
        double multi = 2.6D;
        double mx = Math.cos(Math.toRadians(mc.thePlayer.rotationYaw + 90F));
        double mz = Math.sin(Math.toRadians(mc.thePlayer.rotationYaw + 90F));
        xOff = mc.thePlayer.moveForward * 1.6D * mx + mc.thePlayer.moveStrafing * 1.6D * mz;
        zOff = mc.thePlayer.moveForward * 1.6D * mz + mc.thePlayer.moveStrafing * 1.6D * mx;
        if(isInsideBlock() && mc.thePlayer.isSneaking())
            reset = 1;
        if(reset > 0)
            mc.thePlayer.boundingBox.offsetAndUpdate(xOff, 0, zOff);
	}
	
	@EventTarget
	public boolean onCollision(EventCollide event) {
		if((isInsideBlock() && mc.gameSettings.keyBindJump.isKeyDown()) || (!(isInsideBlock()) && event.getBoundingBox() != null && event.getBoundingBox().maxY > mc.thePlayer.boundingBox.minY && mc.thePlayer.isSneaking()))
            event.setBoundingBox(null);
        return true;
	}
	
	 public static boolean isInsideBlock() {
	        for(int x = MathHelper.floor_double(Minecraft.getMinecraft().thePlayer.boundingBox.minX); x < MathHelper.floor_double(Minecraft.getMinecraft().thePlayer.boundingBox.maxX) + 1; x++) {
	            for(int y = MathHelper.floor_double(Minecraft.getMinecraft().thePlayer.boundingBox.minY); y < MathHelper.floor_double(Minecraft.getMinecraft().thePlayer.boundingBox.maxY) + 1; y++) {
	                for(int z = MathHelper.floor_double(Minecraft.getMinecraft().thePlayer.boundingBox.minZ); z < MathHelper.floor_double(Minecraft.getMinecraft().thePlayer.boundingBox.maxZ) + 1; z++) {
	                    Block block = Minecraft.getMinecraft().theWorld.getBlockState(new BlockPos(x, y, z)).getBlock();
	                    if(block != null && !(block instanceof BlockAir)) {
	                        AxisAlignedBB boundingBox = block.getCollisionBoundingBox(Minecraft.getMinecraft().theWorld, new BlockPos(x, y, z), Minecraft.getMinecraft().theWorld.getBlockState(new BlockPos(x, y, z)));
	                        if(block instanceof BlockHopper)
	                            boundingBox = new AxisAlignedBB(x, y, z, x + 1, y + 1, z + 1);
	                        if(boundingBox != null && Minecraft.getMinecraft().thePlayer.boundingBox.intersectsWith(boundingBox))
	                            return true;
	                    }
	                }
	            }
	        }
	        return false;
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
