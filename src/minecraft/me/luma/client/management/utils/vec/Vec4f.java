package me.luma.client.management.utils.vec;

public class Vec4f
{
    public float x;
    public float y;
    public float z;
    public float w;
    
    public Vec4f() {
        this(0.0f, 0.0f, 0.0f, 0.0f);
    }
    
    public Vec4f sub(final Vec4f vec) {
        this.x -= vec.x;
        this.y -= vec.y;
        this.z -= vec.z;
        this.w -= vec.w;
        return this;
    }
    
    public Vec4f add(final Vec4f vec) {
        this.x += vec.x;
        this.y += vec.y;
        this.z += vec.z;
        this.w += vec.w;
        return this;
    }
    
    public Vec4f mul(final Vec4f vec) {
        this.x *= vec.x;
        this.y *= vec.y;
        this.z *= vec.z;
        this.w *= vec.w;
        return this;
    }
    
    public Vec4f mul(final float scale) {
        return this.mul(new Vec4f(scale, scale, scale, scale));
    }
    
    public Vec4f clone() {
        return new Vec4f(this.x, this.y, this.z, this.w);
    }
    
    public Vec4f(final float x, final float y, final float z, final float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }
}
