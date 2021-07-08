package org.jukeboxmc.math;

import lombok.ToString;
import org.apache.commons.math3.util.FastMath;
import org.jukeboxmc.utils.Utils;
import org.jukeboxmc.world.Dimension;

/**
 * @author LucGamesYT
 * @version 1.0
 */
@ToString
public class Vector {

    public static final Vector ZERO = new Vector( 0, 0, 0 );

    public static final Vector UP = new Vector( 0, 1, 0 );
    public static final Vector DOWN = new Vector( 0, -1, 0 );

    public static final Vector NORTH = new Vector( 0, 0, -1 );
    public static final Vector EAST = new Vector( 1, 0, 0 );
    public static final Vector SOUTH = new Vector( 0, 0, 1 );
    public static final Vector WEST = new Vector( -1, 0, 0 );

    protected float x;
    protected float y;
    protected float z;
    protected Dimension dimension = Dimension.OVERWORLD;

    public Vector( float x, float y, float z, Dimension dimension ) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.dimension = dimension;
    }

    public Vector( float x, float y, float z ) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public float getX() {
        return this.x;
    }

    public int getFloorX() {
        return (int) FastMath.floor( this.x );
    }

    public void setX( float x ) {
        this.x = x;
    }

    public float getY() {
        return this.y;
    }

    public int getFloorY() {
        return (int) FastMath.floor( this.y );
    }

    public void setY( float y ) {
        this.y = y;
    }

    public float getZ() {
        return this.z;
    }

    public int getFloorZ() {
        return (int) FastMath.floor( this.z );
    }

    public void setZ( float z ) {
        this.z = z;
    }

    public Dimension getDimension() {
        return this.dimension;
    }

    public void setDimension( Dimension dimension ) {
        this.dimension = dimension;
    }

    public Vector add( float x, float y, float z ) {
        return new Vector( this.x + x, this.y + y, this.z + z, this.dimension );
    }

    public Vector add( Vector vector ) {
        return new Vector( this.x + vector.x, this.y + vector.y, this.z + vector.z, vector.dimension );
    }

    public Vector subtract( float x, float y, float z ) {
        return new Vector( this.x - x, this.y - y, this.z - z, this.dimension );
    }

    public Vector subtract( Vector vector ) {
        return new Vector( this.x - vector.x, this.y - vector.y, this.z - vector.z, vector.dimension );
    }

    public Vector multiply( float x, float y, float z ) {
        return new Vector( this.x * x, this.y * y, this.z * z );
    }

    public Vector multiply( Vector vector ) {
        return this.multiply( vector.x, vector.y, vector.z );
    }

    public Vector multiply( float value ) {
        return this.multiply( value, value, value );
    }

    public double distanceSquared( Vector vector ) {
        return FastMath.pow( this.x - vector.x, 2 ) + FastMath.pow( this.y - vector.y, 2 ) + FastMath.pow( this.z - vector.z, 2 );
    }

    public double distance( Vector vector ) {
        return FastMath.sqrt( this.distanceSquared( vector ) );
    }

    public Vector getVectorWhenXIsOnLine( Vector other, float x ) {
        float xDiff = other.x - this.x;
        float yDiff = other.y - this.y;
        float zDiff = other.z - this.z;

        float f = ( x - this.x ) / xDiff;
        return ( f >= 0F && f <= 1F ) ? new Vector( this.x + xDiff * f, this.y + yDiff * f, this.z + zDiff * f, this.dimension ) : null;
    }

    public Vector getVectorWhenYIsOnLine( Vector other, float y ) {
        float xDiff = other.x - this.x;
        float yDiff = other.y - this.y;
        float zDiff = other.z - this.z;

        float f = ( y - this.y ) / yDiff;
        return ( f >= 0F && f <= 1F ) ? new Vector( this.x + xDiff * f, this.y + yDiff * f, this.z + zDiff * f, this.dimension ) : null;
    }

    public Vector getVectorWhenZIsOnLine( Vector other, float z ) {
        float xDiff = other.x - this.x;
        float yDiff = other.y - this.y;
        float zDiff = other.z - this.z;

        float f = ( z - this.z ) / zDiff;
        return ( f >= 0F && f <= 1F ) ? new Vector( this.x + xDiff * f, this.y + yDiff * f, this.z + zDiff * f, this.dimension ) : null;
    }

    public Vector substract( int x, int y, int z ) {
        return new Vector( this.x - x, this.y - y, this.z - z, this.dimension );
    }

    public float lengthSquared() {
        return Utils.sqrt( this.x * this.x + this.y * this.y + this.z * this.z );
    }

    public Vector normalize() {
        float length = this.lengthSquared();
        if ( length == 0.0 ) {
            return new Vector( this.x, this.y, this.z );
        }
        return new Vector( this.x / length, this.y / length, this.z / length );
    }
}
