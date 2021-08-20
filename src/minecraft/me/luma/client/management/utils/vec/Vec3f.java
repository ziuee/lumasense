package me.luma.client.management.utils.vec;

import net.minecraft.util.*;
import org.lwjgl.util.vector.*;

public class Vec3f extends Vector3f
{
    public Vec3f(final float x, final float y, final float z) {
        super(x, y, z);
    }
    
    public Vec3f(final BlockPos pos) {
        super((float)pos.getX(), (float)pos.getY(), (float)pos.getZ());
    }
    
    public Vec3f(final Vec3i vec) {
        super((float)vec.getX(), (float)vec.getY(), (float)vec.getZ());
    }
    
    public double squareDistanceTo(final Vec3f vec) {
        final double var2 = vec.x - this.x;
        final double var3 = vec.y - this.y;
        final double var4 = vec.z - this.z;
        return var2 * var2 + var3 * var3 + var4 * var4;
    }
    
    public Vec3f add(final float x, final float y, final float z) {
        this.x += x;
        this.y += y;
        this.z += z;
        return this;
    }
    
    public Vec3f add(final Vec3f vec) {
        this.x += vec.x;
        this.y += vec.y;
        this.z += vec.z;
        return this;
    }
    
    public Vec3f scale(final float amount) {
        this.x *= amount;
        this.y *= amount;
        this.z *= amount;
        return this;
    }
    
    public Vec3f clone() {
        return new Vec3f(this.x, this.y, this.z);
    }
}

