package me.luma.client.management.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.SecureRandom;
import java.text.DecimalFormat;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;

public class MathUtils {
    private static Random rng;

    public static double round(final double value, final int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public static Block getBlockUnderPlayer(EntityPlayer inPlayer, double height) {
        return Minecraft.getMinecraft().theWorld.getBlockState(new BlockPos(inPlayer.posX, inPlayer.posY - height, inPlayer.posZ)).getBlock();
    }

    public static double distance2D(double startX, double startZ, double endX, double endZ) {
        return Math.hypot(startX - endX, startZ - endZ);
    }
    
    public static double getBaseMovementSpeed() {
        double baseSpeed = 0.2873;
        if (Minecraft.getMinecraft().thePlayer.isPotionActive(Potion.moveSpeed)) {
            final int amplifier = Minecraft.getMinecraft().thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier();
            baseSpeed *= 1.0 + 0.2 * (amplifier + 1);
        }
        return baseSpeed;
    }

    public static double distance3D(Entity Player, Entity target) {
        float f = (float) (Player.posX - target.posX);
        float f1 = (float) (Player.posY - target.posY);
        float f2 = (float) (Player.posZ - target.posZ);
        return MathHelper.sqrt_float(f * f + f1 * f1 + f2 * f2);
    }

    public static double distance3D(double startX, double startY,double startZ, double endX, double endY,double endZ) {
        float f = (float) (startX - endX);
        float f1 = (float) (startY - endY);
        float f2 = (float) (startZ - endZ);
        return MathHelper.sqrt_float(f * f + f1 * f1 + f2 * f2);
    }

    public static double getRandomInRange(double min, double max) {
        Random random = new Random();
        double range = max - min;
        double scaled = random.nextDouble() * range;
        if (scaled > max) {
            scaled = max;
        }
        double shifted = scaled + min;

        if (shifted > max) {
            shifted = max;
        }
        return shifted;
    }

    public static double getHighestOffset(double max) {
        for (double i = 0.0D; i < max; i += 0.01D) {
            for (int offset : new int[]{-2, -1, 0, 1, 2}) {
                if (Minecraft.getMinecraft().theWorld.getCollidingBoundingBoxes(Minecraft.getMinecraft().thePlayer, Minecraft.getMinecraft().thePlayer.getEntityBoundingBox().offset(Minecraft.getMinecraft().thePlayer.motionX * offset, i, Minecraft.getMinecraft().thePlayer.motionZ * offset)).size() > 0) {
                    return i - 0.01D;
                }
            }
        }
        return max;
    }

    public static float[] constrainAngle(float[] vector) {

        vector[0] = (vector[0] % 360F);
        vector[1] = (vector[1] % 360F);

        while (vector[0] <= -180) {
            vector[0] = (vector[0] + 360);
        }

        while (vector[1] <= -180) {
            vector[1] = (vector[1] + 360);
        }

        while (vector[0] > 180) {
            vector[0] = (vector[0] - 360);
        }

        while (vector[1] > 180) {
            vector[1] = (vector[1] - 360);
        }

        return vector;
    }

    public static float getRandomInRange(float min, float max) {
        Random random = new Random();
        float range = max - min;
        float scaled = random.nextFloat() * range;
        float shifted = scaled + min;
        return shifted;
    }

    public static int getRandomInRange(int min, int max) {
        Random rand = new Random();
        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    }

    public double doRound(final double d, final int r) {
        String round = "#";
        for (int i = 0; i < r; ++i) {
            round = String.valueOf(round) + ".#";
        }
        final DecimalFormat twoDForm = new DecimalFormat(round);
        return Double.valueOf(twoDForm.format(d));
    }

    public static int getMiddle(final int i, final int i2) {
        return (i + i2) / 2;
    }

    public static Random getRng() {
        return MathUtils.rng;
    }

    public static int getNumberFor(final int start, final int end) {
        if (end >= start) {
            return 0;
        }
        if (end - start < 0) {
            return 0;
        }
        return end - start;
    }

    public static double roundToPlace(final double value, final int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public static float getRandom() {
        return MathUtils.rng.nextFloat();
    }

    public static int getRandom(final int cap) {
        return MathUtils.rng.nextInt(cap);
    }

    public static int getRandom(final int floor, final int cap) {
        return floor + MathUtils.rng.nextInt(cap - floor + 1);
    }

    public static double getRandomf(final double min, final double max) {
        return min + MathUtils.rng.nextDouble() * (max - min + 1.0);
    }

    public static double getMiddleDouble(final int i, final int i2) {
        return (i + i2) / 2.0;
    }

    public static double clamp(double value, double minimum, double maximum) {
        return value > maximum ? maximum : value < minimum ? minimum : value;
    }

    public static double normalizeAngle(double angle) {
        return (angle + 360) % 360;
    }

    public static float normalizeAngle(float angle) {
        return (angle + 360) % 360;
    }

    public static float clamp(float input, float max) {
        return clamp(input, max, -max);
    }

    public static float clamp(float input, float max, float min) {
        if (input > max) input = max;
        if (input < min) input = min;
        return input;
    }
    
    //private static final Random rng = new Random();

    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return false;
        } catch (NullPointerException e) {
            return false;
        }
        // only got here if we didn't return false
        return true;
    }

    /*public static Double clamp(double number, double min, double max) {
        if (number < min)
            return min;
        else if (number > max)
            return max;
        else
            return number;
    }*/

    public static Double getDifference(double num1, double num2) {
        if (num1 > num2) {
            double tempNum = num1;
            num1 = num2;
            num2 = tempNum;
        }
        return num2 - num1;
    }

    public static float randomSeed(long seed) {
        seed = System.currentTimeMillis() + seed;
        return 0.4F + (float) new Random(seed).nextInt(80000000) / 1.0E9F + 1.45E-9F;
    }

    public static float secRanFloat(float min, float max) {

        SecureRandom rand = new SecureRandom();

        return rand.nextFloat() * (max - min) + min;
    }

    public static int randInt(int min, int max) {

        SecureRandom rand = new SecureRandom();

        return rand.nextInt() * (max - min) + min;
    }

    public static double secRanDouble(double min, double max) {

        SecureRandom rand = new SecureRandom();

        return rand.nextDouble() * (max - min) + min;
    }

    public static float getAngleDifference(float direction, float rotationYaw) {
        float phi = Math.abs(rotationYaw - direction) % 360;
        float distance = phi > 180 ? 360 - phi : phi;
        return distance;
    }

    public static double getMiddle(double d, double e) {
        return (d + e) / 2;
    }

    public static float getMiddle(float i, float i1) {
        return (i + i1) / 2;
    }

    public static double getMiddleint(double d, double e) {
        return (d + e) / 2;
    }

    /*public static int getRandom(final int floor, final int cap) {
        return floor + Mafs.rng.nextInt(cap - floor + 1);
    }

    public static double getRandom(final double floor, final double cap) {
        return floor + Mafs.rng.nextInt((int) (cap - floor + 1));
    }

    public static double getRandomInRange(double min, double max) {
        Random random = new Random();
        double range = max - min;
        double scaled = random.nextDouble() * range;
        if (scaled > max) {
            scaled = max;
        }
        double shifted = scaled + min;

        if (shifted > max) {
            shifted = max;
        }
        return shifted;
    }

    public static float getRandomInRange(float min, float max) {
        Random random = new Random();
        float range = max - min;
        float scaled = random.nextFloat() * range;
        float shifted = scaled + min;
        return shifted;
    }

    public static int getRandomInRange(int min, int max) {
        Random rand = new Random();
        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    }

    public static double round(double value, int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }*/

    public static double[] getExpandCoords(double x, double z, float forward, float strafe, double exp, int pos, boolean aircheck) {
        BlockPos underPos = new BlockPos(x, Minecraft.getMinecraft().thePlayer.posY + pos, z);
        Block underBlock = Minecraft.getMinecraft().theWorld.getBlockState(underPos).getBlock();
        double xCalc = -999, zCalc = -999;
        double dist = 0;
        double expandDist = exp;
        while (!(underBlock.getMaterial().isReplaceable()) || !aircheck) {
            xCalc = x;
            zCalc = z;
            dist++;
            if (dist > expandDist) {
                dist = expandDist;
            }
            xCalc += (forward * 0.45 * Math.cos(Math.toRadians(Minecraft.getMinecraft().thePlayer.rotationYaw + 90.0f)) + strafe * 0.45 * Math.sin(Math.toRadians(Minecraft.getMinecraft().thePlayer.rotationYaw + 90.0f))) * dist;
            zCalc += (forward * 0.45 * Math.sin(Math.toRadians(Minecraft.getMinecraft().thePlayer.rotationYaw + 90.0f)) - strafe * 0.45 * Math.cos(Math.toRadians(Minecraft.getMinecraft().thePlayer.rotationYaw + 90.0f))) * dist;
            if (dist == expandDist) {
                break;
            }
            underPos = new BlockPos(xCalc, Minecraft.getMinecraft().thePlayer.posY + pos, zCalc);
            underBlock = Minecraft.getMinecraft().theWorld.getBlockState(underPos).getBlock();
        }
        return new double[]{xCalc, zCalc};
    }

    static {
        MathUtils.rng = new Random();
    }
}