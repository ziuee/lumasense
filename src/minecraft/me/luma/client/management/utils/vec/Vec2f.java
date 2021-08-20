package me.luma.client.management.utils.vec;

import javax.vecmath.Vector2f;

public class Vec2f extends Vector2f
{
    public float x;
    public float y;
    
    public Vec2f() {
        this(0.0f, 0.0f);
    }
    
    public Vec2f add(final double x, final double y) {
        this.x += (float)x;
        this.y += (float)y;
        return this;
    }
    
    public Vec2f add(final float x, final float y) {
        this.x += x;
        this.y += y;
        return this;
    }
    
    public Vec2f add(final int x, final int y) {
        this.x += x;
        this.y += y;
        return this;
    }
    
    public Vec2f add(final Vec2f vec) {
        this.x += vec.x;
        this.y += vec.y;
        return this;
    }
    
    public Vec2f sub(final float x, final float y) {
        this.x -= x;
        this.y -= y;
        return this;
    }
    
    public Vec2f div(final float x, final float y) {
        this.x /= x;
        this.y /= y;
        return this;
    }
    
    public Vec2f mul(final float x, final float y) {
        this.x *= x;
        this.y *= y;
        return this;
    }
    
    @Override
    public Vec2f clone() {
        return new Vec2f(this.x, this.y);
    }
    
    public Vec2f subtract(final Vec2f vec) {
        this.x -= vec.x;
        this.y -= vec.y;
        return this;
    }
    
    public Vec2f multiply(final float number) {
        this.x *= this.x;
        this.y *= this.y;
        return this;
    }
    
    public Vec2f multiply(final Vec2f vec) {
        this.x *= vec.x;
        this.y *= vec.y;
        return this;
    }
    
    public Vec2f multiply(final float x, final float y) {
        this.x *= x;
        this.y *= y;
        return this;
    }
    
    public Vec2f set(final Vec2f vec) {
        this.x = vec.x;
        this.y = vec.y;
        return this;
    }
    
    public void setVecX(final float f) {
        this.x = f;
    }
    
    public void setVecY(final float f) {
        this.y = f;
    }
    
    public float getVecX() {
        return this.x;
    }
    
    public float getVecY() {
        return this.y;
    }
    
    public Vec2f(final float x, final float y) {
        this.x = x;
        this.y = y;
    }
}

