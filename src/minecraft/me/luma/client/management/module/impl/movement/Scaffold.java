package me.luma.client.management.module.impl.movement;

import java.awt.Color;
import java.util.Arrays;
import java.util.List;

import org.lwjgl.input.Keyboard;

import me.luma.client.core.registry.impl.ClientLoader;
import me.luma.client.management.event.EventTarget;
import me.luma.client.management.event.impl.Event2D;
import me.luma.client.management.event.impl.EventPreMotionUpdate;
import me.luma.client.management.event.impl.EventSafeWalk;
import me.luma.client.management.event.impl.EventTick;
import me.luma.client.management.gui.clickgui.settings.SettingBoolean;
import me.luma.client.management.gui.clickgui.settings.SettingSlider;
import me.luma.client.management.module.Category;
import me.luma.client.management.module.Module;
import me.luma.client.management.utils.TEstTimer;
import me.luma.client.management.utils.TimerTEst;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCarpet;
import net.minecraft.block.BlockLadder;
import net.minecraft.block.BlockSkull;
import net.minecraft.block.BlockSnow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.util.Vec3i;

public class Scaffold extends Module {

	TimerTEst timerMotion = new TimerTEst();
	TEstTimer pfrrrr = new TEstTimer();
	TimerTEst boostCap = new TimerTEst();
	TimerTEst timer = new TimerTEst();
	private final List<Block> invalid = Arrays.asList(Blocks.air, Blocks.water, Blocks.fire, Blocks.flowing_water, Blocks.lava, Blocks.flowing_lava,
            Blocks.command_block, Blocks.enchanting_table, Blocks.chest, Blocks.crafting_table, Blocks.furnace, Blocks.noteblock);
    public float lastYaw, lastPitch;
    
	//public static float yaw;
    //public static float pitch;
    
    public boolean grounded;
    public boolean headTurned;
    public BlockData lastBlockData;
    public double vectorOne, vectorTwo, vectorThree;
    int returnSlot, packetSlot, switchTicks;
    int itemStackSize;
    int currentSlot;
    int currentItem;
    double groundy = 0;
    private int slot;
    
    SettingBoolean keepY;
    SettingBoolean safeWalk;
    SettingBoolean silent;
    SettingBoolean tower;
    SettingBoolean boost;
    
    SettingSlider boostDuration;
    SettingSlider delay;
	
	public Scaffold() {
		super("Scaffold", Keyboard.KEY_G, Category.MOVEMENT);
		
		safeWalk = new SettingBoolean("Safe Walk", this, true);
		keepY = new SettingBoolean("Keep Y", this, false);
		silent = new SettingBoolean("Silent", this, false);
		tower = new SettingBoolean("Tower", this, true);
	}
	
    private BlockData data;

    private int startingSlot;


    private double y;
    @Override
    public void onEnable(){
        y = mc.thePlayer.posY;
     //  startingSlot = mc.thePlayer.inventory.currentItem;
       super.onEnable();
    }

    @Override
    public void onDisable(){
      //  mc.thePlayer.inventory.currentItem = startingSlot;
        super.onDisable();
    }


    @EventTarget
    public void onUpdate(EventPreMotionUpdate e) {
        if (data != null) {
            //e.setYaw(mc.thePlayer.rotationYaw + 180);
            //mc.thePlayer.rotationYawHead = mc.thePlayer.rotationYaw + 180;
            //mc.thePlayer.renderYawOffset = mc.thePlayer.rotationYaw + 180;
        	float yaw = mc.thePlayer.rotationYaw + (mc.gameSettings.keyBindBack.pressed && mc.gameSettings.keyBindLeft.pressed ? 45 : (mc.gameSettings.keyBindLeft.pressed && mc.gameSettings.keyBindForward.pressed ? 135 : mc.gameSettings.keyBindLeft.pressed ? 90 : mc.gameSettings.keyBindForward.pressed && mc.gameSettings.keyBindRight.pressed ? 225 : mc.gameSettings.keyBindForward.pressed ? 180 : mc.gameSettings.keyBindRight.pressed && mc.gameSettings.keyBindBack.pressed ? 315 : mc.gameSettings.keyBindRight.pressed ? 270: 0));
        	//e.setPitch(90);
        	e.setYaw(yaw);
            mc.thePlayer.rotationYawHead = yaw;
            mc.thePlayer.renderYawOffset = yaw;
        	if (!mc.thePlayer.isMoving() && mc.gameSettings.keyBindJump.pressed) {
        		e.setPitch(90);
        	}
            for(float i=70;i<=90;i+= 0.1){
                Vec3 vec3 = Minecraft.getMinecraft().thePlayer.getPositionEyes(1.0f);
                float f = MathHelper.cos(-(mc.thePlayer.rotationYaw + 180) * 0.017453292F - (float)Math.PI);
                float f1 = MathHelper.sin(-(mc.thePlayer.rotationYaw + 180) * 0.017453292F - (float)Math.PI);
                float f2 = -MathHelper.cos(-i * 0.017453292F);
                float f3 = MathHelper.sin(-i * 0.017453292F);
                Vec3 rotationVec = new Vec3((double)(f1 * f2), (double)f3, (double)(f * f2));
                Vec3 vec31 = rotationVec;
                Vec3 vec32 = vec3.addVector(vec31.xCoord * 5, vec31.yCoord * 5, vec31.zCoord * 5);
                MovingObjectPosition obj = Minecraft.getMinecraft().theWorld.rayTraceBlocks(vec3, vec32, false);
                /** Vec3 hitVec = RayTraceUtil.getVectorForRotation(i,mc.thePlayer.rotationYaw + 180);
                 Vec3 src = mc.thePlayer.getPositionEyes(1.0F);
                 MovingObjectPosition obj = mc.theWorld.rayTraceBlocks(src, hitVec, false);*/
                if (obj != null && obj.hitVec != null && obj.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
                    if (obj.sideHit != EnumFacing.DOWN && obj.sideHit != EnumFacing.UP) {
                        e.setPitch(i);
                    }
                }
            }
        }
    }

    @EventTarget
    public void onUpdate(EventTick e) {
        data = getBlockData(new BlockPos(mc.thePlayer.posX, y - 0.5, mc.thePlayer.posZ), 1);
        if (data == null) {
            return;
        }
        if(mc.thePlayer == null) {
        	return;
        }
        
        if(data != null) {

        int slot = mc.thePlayer.inventory.currentItem;
        for (int i = 36; i < 45; ++i) {
            if (mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
                final ItemStack is = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
                final Item tems = is.getItem();
                if (tems instanceof ItemBlock) {
                    mc.thePlayer.inventory.currentItem = i - 36;
                    break;
                }
            }
        }
        
        if (mc.gameSettings.keyBindJump.isKeyDown() && !mc.thePlayer.isMoving() && tower.getBooleanValue()) {
        	mc.thePlayer.motionY = 0.41999998688697815;
            mc.thePlayer.motionX *= 0.75;
            mc.thePlayer.motionZ *= 0.75;
        }

        if (mc.gameSettings.keyBindJump.pressed){
            y = mc.thePlayer.posY;
        }

        /*mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld, mc.thePlayer.inventory.getCurrentItem(), data.pos, data.facing, getHitVec());
        if(silent.getBooleanValue()) {
    		return;
    	} else {
    		mc.thePlayer.swingItem();
    	}*/
        if (mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld, mc.thePlayer.inventory.getCurrentItem(), data.pos, data.facing, getHitVec())) {
        	if(!silent.getBooleanValue()) {
        		mc.thePlayer.swingItem();
        	}
        }

        mc.thePlayer.inventory.currentItem = slot;

        }
    }
    
    @EventTarget
	public void onSafewalk(EventSafeWalk event) {
		if(safeWalk.getBooleanValue()) {
			if (mc.thePlayer != null)
				event.setCancelled(mc.thePlayer.onGround);
		}
	}
    
    @EventTarget
    public void onRender(Event2D e) {
        ScaledResolution sr = new ScaledResolution(mc);
        int blockCount = getBlockCount();
        Color color = new Color(0, 255, 0);
        if (this.getBlockCount() > 128) {
            color = new Color(0, 255, 0);
        }
        if (this.getBlockCount() < 128) {
            color = new Color(255, 255, 0);
        }
        if (this.getBlockCount() < 64) {
            color = new Color(255, 0, 0);
        }
        ClientLoader.loaderInstance.fontManager.getFont("SFL 10").drawCenteredString(blockCount + " Blocks left", (sr.getScaledWidth() / 2 - -27) - ClientLoader.loaderInstance.fontManager.getFont("SFL 10").getWidth(blockCount + " Blocks left") / 2, (sr.getScaledHeight() / 2 + 30) + -21, -1);
    }

    public int getBlockCount() {
        int blockCount = 0;
        for (int i = 0; i < 45; ++i) {
            if (mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
                final ItemStack is = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
                final Item item = is.getItem();
                if (is.getItem() instanceof ItemBlock && canIItemBePlaced(item)) {
                    blockCount += is.stackSize;
                }
            }
        }
        return blockCount;
    }
    
    private boolean canIItemBePlaced(Item item) {
        if (Item.getIdFromItem(item) == 116)
            return false;
        if (Item.getIdFromItem(item) == 30)
            return false;
        if (Item.getIdFromItem(item) == 31)
            return false;
        if (Item.getIdFromItem(item) == 175)
            return false;
        if (Item.getIdFromItem(item) == 28)
            return false;
        if (Item.getIdFromItem(item) == 27)
            return false;
        if (Item.getIdFromItem(item) == 66)
            return false;
        if (Item.getIdFromItem(item) == 157)
            return false;
        if (Item.getIdFromItem(item) == 31)
            return false;
        if (Item.getIdFromItem(item) == 6)
            return false;
        if (Item.getIdFromItem(item) == 31)
            return false;
        if (Item.getIdFromItem(item) == 32)
            return false;
        if (Item.getIdFromItem(item) == 140)
            return false;
        if (Item.getIdFromItem(item) == 390)
            return false;
        if (Item.getIdFromItem(item) == 37)
            return false;
        if (Item.getIdFromItem(item) == 38)
            return false;
        if (Item.getIdFromItem(item) == 39)
            return false;
        if (Item.getIdFromItem(item) == 40)
            return false;
        if (Item.getIdFromItem(item) == 69)
            return false;
        if (Item.getIdFromItem(item) == 50)
            return false;
        if (Item.getIdFromItem(item) == 75)
            return false;
        if (Item.getIdFromItem(item) == 76)
            return false;
        if (Item.getIdFromItem(item) == 54)
            return false;
        if (Item.getIdFromItem(item) == 130)
            return false;
        if (Item.getIdFromItem(item) == 146)
            return false;
        if (Item.getIdFromItem(item) == 342)
            return false;
        if (Item.getIdFromItem(item) == 12)
            return false;
        if (Item.getIdFromItem(item) == 77)
            return false;
        if (Item.getIdFromItem(item) == 143)
            return false;
        return true;
    }

    private Vec3 getHitVec() {
        Vec3i directionVec = data.facing.getDirectionVec();
        double x = (double)directionVec.getX() * 0.5D;
        double z = (double)directionVec.getZ() * 0.5D;
        if (data.facing.getAxisDirection() == EnumFacing.AxisDirection.NEGATIVE) {
            x = -x;
            z = -z;
        }

        Vec3 hitVec = (new Vec3(data.pos)).addVector(x + z, (double)directionVec.getY() * 0.5D, x + z);
        Vec3 src = mc.thePlayer.getPositionEyes(1.0F);
        MovingObjectPosition obj = mc.theWorld.rayTraceBlocks(src, hitVec, false, false, true);
        if (obj != null && obj.hitVec != null && obj.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
            if (data.facing != EnumFacing.DOWN && data.facing != EnumFacing.UP) {
                obj.hitVec = obj.hitVec.addVector(0.0D, -0.2D, 0.0D);
            }

            return obj.hitVec;
        } else {
            return null;
        }
    }


    private Vec3 getBlockSide(BlockPos pos, EnumFacing face) {
        if (face == EnumFacing.NORTH) {
            return new Vec3(pos.getX(), pos.getY(), pos.getZ() - .5);
        }
        if (face == EnumFacing.EAST) {
            return new Vec3(pos.getX() + .5, pos.getY(), pos.getZ());
        }
        if (face == EnumFacing.SOUTH) {
            return new Vec3(pos.getX(), pos.getY(), pos.getZ() + .5);
        }
        if (face == EnumFacing.WEST) {
            return new Vec3(pos.getX() - .5, pos.getY(), pos.getZ());
        }
        return new Vec3(pos.getX(), pos.getY(), pos.getZ());
    }

    public final float[] getRotations(Vec3 pos, boolean predict, double predictionFactor) {
        final Vec3 playerPos = new Vec3(mc.thePlayer.posX + (predict ? mc.thePlayer.motionX * predictionFactor : 0), mc.thePlayer.posY+ (predict ? mc.thePlayer.motionY * predictionFactor : 0), mc.thePlayer.posZ + (predict ? mc.thePlayer.motionZ * predictionFactor : 0));

        final double diffX = pos.xCoord + 0.5 - playerPos.xCoord;
        final double diffY = pos.yCoord + 0.5 - (playerPos.yCoord + mc.thePlayer.getEyeHeight());
        final double diffZ = pos.zCoord + 0.5 - playerPos.zCoord;

        final double dist = MathHelper.sqrt_double(diffX * diffX + diffZ * diffZ);
        double yaw = Math.toDegrees (Math.atan2(diffZ, diffX)) - 90.0f;
        double pitch = -Math.toDegrees(Math.atan2(diffY, dist));
        yaw = mc.thePlayer.rotationYaw + MathHelper.wrapAngleTo180_double(yaw - mc.thePlayer.rotationYaw);
        pitch = mc.thePlayer.rotationPitch + MathHelper.wrapAngleTo180_double(pitch - mc.thePlayer.rotationPitch);
        return new float[] { (float) yaw, (float) pitch };
    }





    private BlockData getBlockData(BlockPos blockPos, int expand)
    {
        if (isValidBlock(blockPos.add(0, -expand, 0))) {
            return new BlockData(blockPos.add(0, -expand, 0), EnumFacing.UP);
        }
        if (isValidBlock(blockPos.add(-expand, 0, 0))) {
            return new BlockData(blockPos.add(-expand, 0, 0), EnumFacing.EAST);
        }
        if (isValidBlock(blockPos.add(expand, 0, 0))) {
            return new BlockData(blockPos.add(expand, 0, 0), EnumFacing.WEST);
        }
        if (isValidBlock(blockPos.add(0, 0, expand))) {
            return new BlockData(blockPos.add(0, 0, expand), EnumFacing.NORTH);
        }
        if (isValidBlock(blockPos.add(0, 0, -expand))) {
            return new BlockData(blockPos.add(0, 0, -expand), EnumFacing.SOUTH);
        }
        BlockPos blockPos1 = blockPos.add(-expand, 0, 0);
        if (isValidBlock(blockPos1.add(0, -expand, 0))) {
            return new BlockData(blockPos1.add(0, -expand, 0), EnumFacing.UP);
        }
        if (isValidBlock(blockPos1.add(-expand, 0, 0))) {
            return new BlockData(blockPos1.add(-expand, 0, 0), EnumFacing.EAST);
        }
        if (isValidBlock(blockPos1.add(expand, 0, 0))) {
            return new BlockData(blockPos1.add(expand, 0, 0), EnumFacing.WEST);
        }
        if (isValidBlock(blockPos1.add(0, 0, expand))) {
            return new BlockData(blockPos1.add(0, 0, expand), EnumFacing.NORTH);
        }
        if (isValidBlock(blockPos1.add(0, 0, -expand))) {
            return new BlockData(blockPos1.add(0, 0, -expand), EnumFacing.SOUTH);
        }
        BlockPos blockPos2 = blockPos.add(expand, 0, 0);
        if (isValidBlock(blockPos2.add(0, -expand, 0))) {
            return new BlockData(blockPos2.add(0, -expand, 0), EnumFacing.UP);
        }
        if (isValidBlock(blockPos2.add(-expand, 0, 0))) {
            return new BlockData(blockPos2.add(-expand, 0, 0), EnumFacing.EAST);
        }
        if (isValidBlock(blockPos2.add(expand, 0, 0))) {
            return new BlockData(blockPos2.add(expand, 0, 0), EnumFacing.WEST);
        }
        if (isValidBlock(blockPos2.add(0, 0, expand))) {
            return new BlockData(blockPos2.add(0, 0, expand), EnumFacing.NORTH);
        }
        if (isValidBlock(blockPos2.add(0, 0, -expand))) {
            return new BlockData(blockPos2.add(0, 0, -expand), EnumFacing.SOUTH);
        }
        BlockPos blockPos3 = blockPos.add(0, 0, expand);
        if (isValidBlock(blockPos3.add(0, -expand, 0))) {
            return new BlockData(blockPos3.add(0, -expand, 0), EnumFacing.UP);
        }
        if (isValidBlock(blockPos3.add(-expand, 0, 0))) {
            return new BlockData(blockPos3.add(-expand, 0, 0), EnumFacing.EAST);
        }
        if (isValidBlock(blockPos3.add(expand, 0, 0))) {
            return new BlockData(blockPos3.add(expand, 0, 0), EnumFacing.WEST);
        }
        if (isValidBlock(blockPos3.add(0, 0, expand))) {
            return new BlockData(blockPos3.add(0, 0, expand), EnumFacing.NORTH);
        }
        if (isValidBlock(blockPos3.add(0, 0, -expand))) {
            return new BlockData(blockPos3.add(0, 0, -expand), EnumFacing.SOUTH);
        }
        BlockPos blockPos4 = blockPos.add(0, 0, -expand);
        if (isValidBlock(blockPos4.add(0, -expand, 0))) {
            return new BlockData(blockPos4.add(0, -expand, 0), EnumFacing.UP);
        }
        if (isValidBlock(blockPos4.add(-expand, 0, 0))) {
            return new BlockData(blockPos4.add(-expand, 0, 0), EnumFacing.EAST);
        }
        if (isValidBlock(blockPos4.add(expand, 0, 0))) {
            return new BlockData(blockPos4.add(expand, 0, 0), EnumFacing.WEST);
        }
        if (isValidBlock(blockPos4.add(0, 0, expand))) {
            return new BlockData(blockPos4.add(0, 0, expand), EnumFacing.NORTH);
        }
        if (isValidBlock(blockPos4.add(0, 0, -expand))) {
            return new BlockData(blockPos4.add(0, 0, -expand), EnumFacing.SOUTH);
        }
        BlockPos blockPos5 = blockPos.add(0, -expand, 0);
        if (isValidBlock(blockPos5.add(0, -expand, 0))) {
            return new BlockData(blockPos5.add(0, -expand, 0), EnumFacing.UP);
        }
        if (isValidBlock(blockPos5.add(-expand, 0, 0))) {
            return new BlockData(blockPos5.add(-expand, 0, 0), EnumFacing.EAST);
        }
        if (isValidBlock(blockPos5.add(expand, 0, 0))) {
            return new BlockData(blockPos5.add(expand, 0, 0), EnumFacing.WEST);
        }
        if (isValidBlock(blockPos5.add(0, 0, expand))) {
            return new BlockData(blockPos5.add(0, 0, expand), EnumFacing.NORTH);
        }
        if (isValidBlock(blockPos5.add(0, 0, -expand))) {
            return new BlockData(blockPos5.add(0, 0, -expand), EnumFacing.SOUTH);
        }
        BlockPos blockPos6 = blockPos5.add(expand, 0, 0);
        if (isValidBlock(blockPos6.add(0, -expand, 0))) {
            return new BlockData(blockPos6.add(0, -expand, 0), EnumFacing.UP);
        }
        if (isValidBlock(blockPos6.add(-expand, 0, 0))) {
            return new BlockData(blockPos6.add(-expand, 0, 0), EnumFacing.EAST);
        }
        if (isValidBlock(blockPos6.add(expand, 0, 0))) {
            return new BlockData(blockPos6.add(expand, 0, 0), EnumFacing.WEST);
        }
        if (isValidBlock(blockPos6.add(0, 0, expand))) {
            return new BlockData(blockPos6.add(0, 0, expand), EnumFacing.NORTH);
        }
        if (isValidBlock(blockPos6.add(0, 0, -expand))) {
            return new BlockData(blockPos6.add(0, 0, -expand), EnumFacing.SOUTH);
        }
        BlockPos blockPos7 = blockPos5.add(-expand, 0, 0);
        if (isValidBlock(blockPos7.add(0, -expand, 0))) {
            return new BlockData(blockPos7.add(0, -expand, 0), EnumFacing.UP);
        }
        if (isValidBlock(blockPos7.add(-expand, 0, 0))) {
            return new BlockData(blockPos7.add(-expand, 0, 0), EnumFacing.EAST);
        }
        if (isValidBlock(blockPos7.add(expand, 0, 0))) {
            return new BlockData(blockPos7.add(expand, 0, 0), EnumFacing.WEST);
        }
        if (isValidBlock(blockPos7.add(0, 0, expand))) {
            return new BlockData(blockPos7.add(0, 0, expand), EnumFacing.NORTH);
        }
        if (isValidBlock(blockPos7.add(0, 0, -expand))) {
            return new BlockData(blockPos7.add(0, 0, -expand), EnumFacing.SOUTH);
        }
        BlockPos blockPos8 = blockPos5.add(0, 0, expand);
        if (isValidBlock(blockPos8.add(0, -expand, 0))) {
            return new BlockData(blockPos8.add(0, -expand, 0), EnumFacing.UP);
        }
        if (isValidBlock(blockPos8.add(-expand, 0, 0))) {
            return new BlockData(blockPos8.add(-expand, 0, 0), EnumFacing.EAST);
        }
        if (isValidBlock(blockPos8.add(expand, 0, 0))) {
            return new BlockData(blockPos8.add(expand, 0, 0), EnumFacing.WEST);
        }
        if (isValidBlock(blockPos8.add(0, 0, expand))) {
            return new BlockData(blockPos8.add(0, 0, expand), EnumFacing.NORTH);
        }
        if (isValidBlock(blockPos8.add(0, 0, -expand))) {
            return new BlockData(blockPos8.add(0, 0, -expand), EnumFacing.SOUTH);
        }
        BlockPos blockPos9 = blockPos5.add(0, 0, -expand);
        if (isValidBlock(blockPos9.add(0, -expand, 0))) {
            return new BlockData(blockPos9.add(0, -expand, 0), EnumFacing.UP);
        }
        if (isValidBlock(blockPos9.add(-expand, 0, 0))) {
            return new BlockData(blockPos9.add(-expand, 0, 0), EnumFacing.EAST);
        }
        if (isValidBlock(blockPos9.add(expand, 0, 0))) {
            return new BlockData(blockPos9.add(expand, 0, 0), EnumFacing.WEST);
        }
        if (isValidBlock(blockPos9.add(0, 0, expand))) {
            return new BlockData(blockPos9.add(0, 0, expand), EnumFacing.NORTH);
        }
        if (isValidBlock(blockPos9.add(0, 0, -expand))) {
            return new BlockData(blockPos9.add(0, 0, -expand), EnumFacing.SOUTH);
        }
        return null;
    }

    private boolean isValidBlock(BlockPos pos)
    {
        Block localBlock = mc.theWorld.getBlockState(pos).getBlock();
        if ((localBlock.getMaterial().isSolid()) || (!localBlock.isTranslucent()) ||  ((localBlock instanceof BlockLadder)) || ((localBlock instanceof BlockCarpet)) || ((localBlock instanceof BlockSnow)) || ((localBlock instanceof BlockSkull))) {
            if (!localBlock.getMaterial().isLiquid()) {
                return true;
            }
        }
        return false;
    }

    public class BlockData
    {
        public BlockPos pos;
        public EnumFacing facing;

        private BlockData(BlockPos paramBlockPos, EnumFacing paramEnumFacing)
        {
            this.pos = paramBlockPos;
            this.facing = paramEnumFacing;
        }
    }
}
