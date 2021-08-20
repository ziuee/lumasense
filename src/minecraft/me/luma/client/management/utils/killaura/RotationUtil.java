package me.luma.client.management.utils.killaura;

import me.luma.client.core.registry.impl.ClientLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

public class RotationUtil {
	
	private static final Minecraft MC = Minecraft.getMinecraft();
	
	public float serverYaw, serverPitch;
	
	public final float[] getRotations(Entity entity, boolean predict, double predictionFactor) {
		final Vec3 playerPos = new Vec3(MC.thePlayer.posX + (predict ? MC.thePlayer.motionX * predictionFactor : 0), MC.thePlayer.posY + (entity instanceof EntityLivingBase ? MC.thePlayer.getEyeHeight() : 0) + (predict ? MC.thePlayer.motionY * predictionFactor : 0), MC.thePlayer.posZ + (predict ? MC.thePlayer.motionZ * predictionFactor : 0));
		final Vec3 entityPos = new Vec3(entity.posX + (predict ? (entity.posX - entity.prevPosX) * predictionFactor : 0), entity.posY + (predict ? (entity.posY - entity.prevPosY) * predictionFactor : 0), entity.posZ + (predict ? (entity.posZ - entity.prevPosZ) * predictionFactor : 0));
		
		final double diffX = entityPos.xCoord - playerPos.xCoord;
		final double diffY = (entity instanceof EntityLivingBase ? entityPos.yCoord + ((EntityLivingBase) entity).getEyeHeight() - playerPos.yCoord : entityPos.yCoord - playerPos.yCoord);
		final double diffZ = entityPos.zCoord - playerPos.zCoord;
		
		final double dist = Math.sqrt(diffX * diffX + diffZ * diffZ);
		
		final double yaw = Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0;
		final double pitch = -Math.toDegrees(Math.atan2(diffY, dist));
		
		return new float[] {(float) yaw, (float) pitch};
		/*final Vec3 playerPos = new Vec3(MC.thePlayer.posX + (predict ? MC.thePlayer.motionX * predictionFactor : 0), MC.thePlayer.posY + (entity instanceof EntityLivingBase ? MC.thePlayer.getEyeHeight() : 0) + (predict ? MC.thePlayer.motionY * predictionFactor : 0), MC.thePlayer.posZ + (predict ? MC.thePlayer.motionZ * predictionFactor : 0));
		final Vec3 entityPos = new Vec3(entity.posX + (predict ? (entity.posX - entity.prevPosX) * predictionFactor : 0), entity.posY + (predict ? (entity.posY - entity.prevPosY) * predictionFactor : 0), entity.posZ + (predict ? (entity.posZ - entity.prevPosZ) * predictionFactor : 0));
		
		final double diffX = entityPos.xCoord - playerPos.xCoord;
		final double diffY = (entity instanceof EntityLivingBase ? entityPos.yCoord + ((EntityLivingBase) entity).getEyeHeight() - playerPos.yCoord : entityPos.yCoord - playerPos.yCoord);
		final double diffZ = entityPos.zCoord - playerPos.zCoord;
		
		final double dist = Math.sqrt(diffX * diffX + diffZ * diffZ);
		
		final double yaw = Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0;
		final double pitch = -Math.toDegrees(Math.atan2(diffY, dist));
		
		return new float[] {(float) yaw, (float) pitch};*/
	}
	
	public static float[] getRotations3(Entity e)
	  {
	        double deltaX = e.boundingBox.minX + (e.boundingBox.maxX - e.boundingBox.minX + 0.1) - Minecraft.getMinecraft().thePlayer.posX, deltaY = e.posY - 4.25 + e.getEyeHeight() - Minecraft.getMinecraft().thePlayer.posY + Minecraft.getMinecraft().thePlayer.getEyeHeight(), deltaZ = e.boundingBox.minZ + (e.boundingBox.maxX - e.boundingBox.minX) - Minecraft.getMinecraft().thePlayer.posZ, distance = Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaZ, 2));
	        float yaw = (float) Math.toDegrees(-Math.atan(deltaX / deltaZ)), pitch = (float) -Math.toDegrees(Math.atan(deltaY / distance));
	        final double v = Math.toDegrees(Math.atan(deltaZ / deltaX));
	        if (deltaX < 0 && deltaZ < 0) yaw = (float) (90 + v);
	        else if (deltaX > 0 && deltaZ < 0) yaw = (float) (-90 + v);
	        return new float[] {yaw, pitch};
	  }
	
    public final float[] getRotations(Vec3 pos, boolean predict, double predictionFactor) {
    	final Vec3 playerPos = new Vec3(MC.thePlayer.posX + (predict ? MC.thePlayer.motionX * predictionFactor : 0), MC.thePlayer.posY+ (predict ? MC.thePlayer.motionY * predictionFactor : 0), MC.thePlayer.posZ + (predict ? MC.thePlayer.motionZ * predictionFactor : 0));
		
    	final double diffX = pos.xCoord + 0.5 - playerPos.xCoord;
    	final double diffY = pos.yCoord + 0.5 - (playerPos.yCoord + MC.thePlayer.getEyeHeight());
    	final double diffZ = pos.zCoord + 0.5 - playerPos.zCoord;
        
    	final double dist = MathHelper.sqrt_double(diffX * diffX + diffZ * diffZ);
        double yaw = Math.toDegrees (Math.atan2(diffZ, diffX)) - 90.0f;
        double pitch = -Math.toDegrees(Math.atan2(diffY, dist));
        yaw = MC.thePlayer.rotationYaw + MathHelper.wrapAngleTo180_double(yaw - MC.thePlayer.rotationYaw);
        pitch = MC.thePlayer.rotationPitch + MathHelper.wrapAngleTo180_double(pitch - MC.thePlayer.rotationPitch);
        return new float[] { (float) yaw, (float) pitch };
    }
    
    public static float[] getRotations(Entity target) {
        double var7, var4 = target.posX - MC.thePlayer.posX;
        double var5 = target.posZ - MC.thePlayer.posZ;
        if (target instanceof EntityLivingBase) {
            EntityLivingBase var6 = (EntityLivingBase) target;
            var7 = var6.posY + var6.getEyeHeight() - MC.thePlayer.posY + MC.thePlayer.getEyeHeight();
        } else {
            var7 = ((target.getEntityBoundingBox()).minY + (target.getEntityBoundingBox()).maxY) / 2.0D - MC.thePlayer.posY + MC.thePlayer.getEyeHeight();
        }
        double var8 = MathHelper.sqrt_double(var4 * var4 + var5 * var5);
        float var9 = (float) (Math.atan2(var5, var4) * 180.0D / Math.PI) - 90.0F;
        float var10 = (float) -(Math.atan2(var7 - ((target instanceof net.minecraft.entity.player.EntityPlayer) ? 0.25D : 0.0D), var8) * 180.0D / Math.PI);
        float pitch = changeRotation(MC.thePlayer.rotationPitch, var10);
        float yaw = changeRotation(MC.thePlayer.rotationYaw, var9);
        return new float[]{yaw, pitch};
    }
    
    public static float changeRotation(float p_706631, float p_706632) {
        float var4 = MathHelper.wrapAngleTo180_float(p_706632 - p_706631);
        if (var4 > 1000.0F)
            var4 = 1000.0F;
        if (var4 < -1000.0F)
            var4 = -1000.0F;
        return p_706631 + var4;
    }
    
    public final Vec3 getVectorForRotation(float yaw, float pitch)
    {
    	final double f = Math.cos(Math.toRadians(-yaw) - Math.PI);
    	final double f1 = Math.sin(Math.toRadians(-yaw) - Math.PI);
    	final double f2 = -Math.cos(Math.toRadians(-pitch));
    	final double f3 = Math.sin(Math.toRadians(-pitch));
        return new Vec3((double)(f1 * f2), (double)f3, (double)(f * f2));
    }
    
    public final float getDifference(float a, float b) {
        float r = (float) ((a - b) % 360.0);
        
        if (r < -180.0) {
        	r += 360.0;
        }
        
        if (r >= 180.0) {
        	r -= 360.0;
        }
        
        return r;
    }
    
    public final double getRotationDifference(float[] clientRotations, float[] serverRotations) {
    	return Math.hypot(getDifference(clientRotations[0], serverRotations[0]), clientRotations[1] - serverRotations[1]);
    }
    
    public final double getRotationDifference(Entity entity) {
    	final float[] rotations = getRotations(entity, false, 1);
    	return getRotationDifference(rotations, new float[] {MC.thePlayer.rotationYaw, MC.thePlayer.rotationPitch});
    }
    
    public final float[] smoothRotation(float[] currentRotations, float[] neededRotations, float rotationSpeed) {
    	final float yawDiff = getDifference(neededRotations[0], currentRotations[0]);
    	final float pitchDiff = getDifference(neededRotations[1], currentRotations[1]);
    	
    	float rotationSpeedYaw = rotationSpeed;
    	
    	if (yawDiff > rotationSpeed) {
    		rotationSpeedYaw = rotationSpeed;
    	} else {
    		rotationSpeedYaw = Math.max(yawDiff, -rotationSpeed);
    	}
    	
    	float rotationSpeedPitch = rotationSpeed;
    	
    	if (pitchDiff > rotationSpeed) {
    		rotationSpeedPitch = rotationSpeed;
    	} else {
    		rotationSpeedPitch = Math.max(pitchDiff, -rotationSpeed);
    	}
    	
    	final float newYaw = currentRotations[0] + rotationSpeedYaw;
    	final float newPitch = currentRotations[1] + rotationSpeedPitch;
    	
    	return new float[] { newYaw, newPitch };
    }
    
    public final boolean isFaced(double range) {
    	return ClientLoader.loaderInstance.RAYCAST_UTIL.raycastEntity(range, new float[] {serverYaw, serverPitch}) != null;
    }
    
    public final void setRotations(float yaw, float pitch) {
    	serverYaw = yaw;
    	serverPitch = pitch;
    }
    
    public final void setRotations(float[] rotations) {
    	setRotations(rotations[0], rotations[1]);
    }
    
    public final float[] getServerRotations() {
    	return new float[] {serverYaw, serverPitch};
    }
    
}
