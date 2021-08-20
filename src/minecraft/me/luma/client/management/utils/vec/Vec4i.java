package me.luma.client.management.utils.vec;

public class Vec4i
{
    public int x;
    public int y;
    public int z;
    public int a;
    
    public Vec4i() {
        this(0, 0, 0, 0);
    }
    
    public Vec4i(final int x, final int y, final int z, final int a) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.a = a;
    }
}
