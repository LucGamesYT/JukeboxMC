package org.jukeboxmc.entity;

import com.google.common.collect.Lists;
import org.apache.commons.math3.util.FastMath;
import org.jukeboxmc.Server;
import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.direction.Direction;
import org.jukeboxmc.entity.metadata.EntityFlag;
import org.jukeboxmc.entity.metadata.Metadata;
import org.jukeboxmc.entity.metadata.MetadataFlag;
import org.jukeboxmc.event.entity.EntityVelocityEvent;
import org.jukeboxmc.math.AxisAlignedBB;
import org.jukeboxmc.math.Location;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.network.packet.*;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.utils.Utils;
import org.jukeboxmc.world.Dimension;
import org.jukeboxmc.world.Particle;
import org.jukeboxmc.world.World;
import org.jukeboxmc.world.chunk.Chunk;

import java.util.*;

/**
 * @author LucGamesYT, geNAZt
 * @version 1.0
 */
public abstract class Entity {

    protected Location location;
    protected Vector velocity;
    protected Metadata metadata;
    protected AxisAlignedBB boundingBox;
    protected Dimension dimension = Dimension.OVERWORLD;

    public static long entityCount = 1;
    private long entityId = 0;

    private boolean isOnGround = false;
    private boolean stuckInBlock = false;
    protected boolean isCollidedVertically;
    protected boolean isCollidedHorizontally;
    protected boolean isCollided;

    private int stuckInBlockTicks = 0;

    protected float fallDistance = 0;
    protected float stepHeight = 0;
    protected float gravity = 0.08f;
    protected float drag = 0.02f;

    protected Set<Block> collidedWith = new HashSet<>();

    public Entity() {
        this.metadata = new Metadata();
        this.metadata.setLong( MetadataFlag.INDEX, 0 );
        this.metadata.setShort( MetadataFlag.MAX_AIR_SUPPLY, (short) 400 );
        this.metadata.setFloat( MetadataFlag.SCALE, 1 );
        this.metadata.setFloat( MetadataFlag.BOUNDING_BOX_WIDTH, this.getWidth() );
        this.metadata.setFloat( MetadataFlag.BOUNDING_BOX_HEIGHT, this.getHeight() );
        this.metadata.setShort( MetadataFlag.AIR_SUPPLY, (short) 0 );
        this.metadata.setDataFlag( MetadataFlag.INDEX, EntityFlag.HAS_COLLISION, true );
        this.metadata.setDataFlag( MetadataFlag.INDEX, EntityFlag.HAS_GRAVITY, true );
        this.metadata.setDataFlag( MetadataFlag.INDEX, EntityFlag.CAN_CLIMB, true );

        this.location = new Location( Server.getInstance().getDefaultWorld(), 0, 7, 0, 0, 0 );
        this.location.setDimension( this.dimension );

        this.velocity = new Vector( 0, 0, 0, this.dimension );

        this.boundingBox = new AxisAlignedBB( 0, 0, 0, 0, 0, 0 );
        this.recalcBoundingBox();
    }

    public abstract String getName();

    public abstract String getEntityType();

    public abstract float getWidth();

    public abstract float getHeight();

    public abstract Packet createSpawnPacket();

    protected Entity handleFall() {
        return this;
    }

    public void update( long currentTick ) {
        if ( !this.isImmobile() ) {
            float movX = this.velocity.getX();
            float movY = this.velocity.getY();
            float movZ = this.velocity.getZ();

            Vector moved = null;
            if ( this.shouldMove() ) {
                moved = this.safeMove( movX, movY, movZ );
            }

            if ( this.hasGravity() ) {
                // Calc motion
                this.velocity.add( 0, -this.gravity, 0 );

                // Calculate friction
                float friction = 1 - this.drag;
                if ( this.isOnGround && ( FastMath.abs( this.velocity.getX() ) > 0.00001 || FastMath.abs( this.velocity.getZ() ) > 0.00001 ) ) {
                    friction = 0.6f * 0.91f;
                    System.out.println( "CALL" );
                }

                // Calculate new motion
                float newMovX = this.velocity.getX() * friction;
                float newMovY = this.velocity.getY() * ( 1 - this.drag );
                float newMovZ = this.velocity.getZ() * friction;

                if ( this.isOnGround ) {
                    this.velocity.setY( (float) ( this.velocity.getY() * -0.5D ) );
                }

                this.velocity = new Vector( newMovX, newMovY, newMovZ );
                this.checkAfterGravity();
            }

            if ( moved != null ) {
                // We did not move so we collided, set motion to 0 to escape hell
                if ( movX != moved.getX() ) {
                    this.velocity.setX( 0 );
                }

                if ( movY != moved.getY() ) {
                    this.velocity.setY( 0 );
                }

                if ( movZ != moved.getZ() ) {
                    this.velocity.setZ( 0 );
                }
            }
        }

        // Check for block collision
        this.checkBlockCollisions();

        // Check for void damage
        if ( this.getY() < -16.0f ) {
            //this.dealVoidDamage();
        }
    }

    protected void checkAfterGravity() {
    }

    protected boolean shouldMove() {
        return true;
    }

    public void onCollideWithPlayer( Player player ) {
    }

    protected boolean needsToBePushedOutOfBlocks() {
        return true;
    }

    public float getEyeHeight() {
        return this.getHeight() / 2 + 0.1f;
    }

    public Metadata getMetadata() {
        return this.metadata;
    }

    public long getEntityId() {
        return entityId;
    }

    public void setEntityId( long entityId ) {
        this.entityId = entityId;
    }

    public Location getLocation() {
        return this.location;
    }

    public Vector getVelocity() {
        return this.velocity;
    }

    public World getWorld() {
        return this.location.getWorld();
    }

    public float getX() {
        return this.getLocation().getX();
    }

    public int getFloorX() {
        return (int) Math.floor( this.getLocation().getX() );
    }

    public float getY() {
        return this.location.getY();
    }

    public int getFloorY() {
        return (int) Math.floor( this.getLocation().getY() );
    }

    public float getZ() {
        return this.location.getZ();
    }

    public int getFloorZ() {
        return (int) Math.floor( this.getLocation().getZ() );
    }

    public float getHeadYaw() {
        return this.location.getHeadYaw();
    }

    public float getYaw() {
        return this.location.getYaw();
    }

    public float getPitch() {
        return this.location.getPitch();
    }

    public int getChunkX() {
        return (int) this.location.getX() >> 4;
    }

    public int getChunkZ() {
        return (int) this.location.getZ() >> 4;
    }

    public Chunk getChunk() {
        return this.location.getWorld().getChunk( this.getChunkX(), this.getChunkZ(), this.dimension );
    }

    public void setLocation( Location location ) {
        this.location = location;
        this.recalcBoundingBox();
    }

    public void setX( float x ) {
        this.location.setX( x );
    }

    public void setY( float y ) {
        this.location.setY( y );
    }

    public void setZ( float z ) {
        this.location.setZ( z );
    }

    public void setHeadYaw( float headYaw ) {
        this.location.setHeadYaw( headYaw );
    }

    public void setYaw( float yaw ) {
        this.location.setYaw( yaw );
    }

    public void setPitch( float pitch ) {
        this.location.setPitch( pitch );
    }

    public AxisAlignedBB getBoundingBox() {
        return this.boundingBox;
    }

    public Direction getDirection() {
        double rotation = this.location.getYaw() % 360;
        if ( rotation < 0 ) {
            rotation += 360.0;
        }

        if ( 45 <= rotation && rotation < 135 ) {
            return Direction.WEST;
        } else if ( 135 <= rotation && rotation < 225 ) {
            return Direction.NORTH;
        } else if ( 225 <= rotation && rotation < 315 ) {
            return Direction.EAST;
        } else {
            return Direction.SOUTH;
        }
    }

    public boolean isOnGround() {
        return this.isOnGround;
    }

    public void setOnGround( boolean onGround ) {
        this.isOnGround = onGround;
    }

    public String getNameTag() {
        return this.metadata.getString( MetadataFlag.NAMETAG );
    }

    public void setNameTag( String value ) {
        this.updateMetadata( this.metadata.setString( MetadataFlag.NAMETAG, value ) );
    }

    public boolean isNameTagVisible() {
        return this.metadata.getDataFlag( MetadataFlag.INDEX, EntityFlag.SHOW_NAMETAG );
    }

    public void setNameTagVisible( boolean value ) {
        if ( value != this.isNameTagVisible() ) {
            this.updateMetadata( this.metadata.setDataFlag( MetadataFlag.INDEX, EntityFlag.SHOW_NAMETAG, value ) );
        }
    }

    public boolean isNameTagAlwaysVisible() {
        return this.metadata.getDataFlag( MetadataFlag.INDEX, EntityFlag.SHOW_ALWAYS_NAMETAG );
    }

    public void setNameTagAlwaysVisible( boolean value ) {
        if ( value != this.isNameTagAlwaysVisible() ) {
            this.updateMetadata( this.metadata.setDataFlag( MetadataFlag.INDEX, EntityFlag.SHOW_ALWAYS_NAMETAG, value ) );
        }
    }

    public boolean canClimb() {
        return this.metadata.getDataFlag( MetadataFlag.INDEX, EntityFlag.CAN_CLIMB );
    }

    public void setCanClimb( boolean value ) {
        if ( value != this.canClimb() ) {
            this.updateMetadata( this.metadata.setDataFlag( MetadataFlag.INDEX, EntityFlag.CAN_CLIMB, value ) );
        }
    }

    public boolean isInvisible() {
        return this.metadata.getDataFlag( MetadataFlag.INDEX, EntityFlag.INVISIBLE );
    }

    public void setInvisible( boolean value ) {
        if ( value != this.isInvisible() ) {
            this.updateMetadata( this.metadata.setDataFlag( MetadataFlag.INDEX, EntityFlag.INVISIBLE, value ) );
        }
    }

    public boolean isBurning() {
        return this.metadata.getDataFlag( MetadataFlag.INDEX, EntityFlag.ON_FIRE );
    }

    public void setBurning( boolean value ) {
        if ( value != this.isBurning() ) {
            this.updateMetadata( this.metadata.setDataFlag( MetadataFlag.INDEX, EntityFlag.ON_FIRE, value ) );
        }
    }

    public boolean isImmobile() {
        return this.metadata.getDataFlag( MetadataFlag.INDEX, EntityFlag.IMMOBILE );
    }

    public void setImmobile( boolean value ) {
        if ( value != this.isImmobile() ) {
            this.updateMetadata( this.metadata.setDataFlag( MetadataFlag.INDEX, EntityFlag.IMMOBILE, value ) );
        }
    }

    public float getScale() {
        return this.metadata.getFloat( MetadataFlag.SCALE );
    }

    public void setScale( float value ) {
        this.updateMetadata( this.metadata.setFloat( MetadataFlag.SCALE, value ) );
        this.recalcBoundingBox();
    }

    public boolean hasCollision() {
        return this.metadata.getDataFlag( MetadataFlag.INDEX, EntityFlag.HAS_COLLISION );
    }

    public void setCollision( boolean value ) {
        if ( this.hasCollision() != value ) {
            this.updateMetadata( this.metadata.setDataFlag( MetadataFlag.INDEX, EntityFlag.HAS_COLLISION, value ) );
        }
    }

    public boolean hasGravity() {
        return this.metadata.getDataFlag( MetadataFlag.INDEX, EntityFlag.HAS_GRAVITY );
    }

    public void setGravity( boolean value ) {
        if ( this.hasGravity() != value ) {
            this.updateMetadata( this.metadata.setDataFlag( MetadataFlag.INDEX, EntityFlag.HAS_GRAVITY, value ) );
        }
    }

    public void updateMetadata( Metadata metadata ) {
        SetEntityDataPacket setEntityDataPacket = new SetEntityDataPacket();
        setEntityDataPacket.setEntityId( this.entityId );
        setEntityDataPacket.setMetadata( metadata );
        setEntityDataPacket.setTick( 0 );
        for ( Player onlinePlayer : Server.getInstance().getOnlinePlayers() ) {
            onlinePlayer.getPlayerConnection().sendPacket( setEntityDataPacket );
        }
    }

    public void recalcBoundingBox() {
        Location location = this.getLocation();
        this.boundingBox.setBounds(
                location.getX() - ( this.getWidth() / 2 ),
                location.getY(),
                location.getZ() - ( this.getWidth() / 2 ),
                location.getX() + ( this.getWidth() / 2 ),
                location.getY() + this.getEyeHeight(),
                location.getZ() + ( this.getWidth() / 2 )
        );
    }

    public void spawnParticle( Particle particle, Vector position ) {
        SpawnParticleEffectPacket spawnParticleEffectPacket = new SpawnParticleEffectPacket();
        spawnParticleEffectPacket.setParticle( particle );
        spawnParticleEffectPacket.setPosition( position );
        spawnParticleEffectPacket.setEntityId( this.entityId );
        spawnParticleEffectPacket.setDimension( position.getDimension() );
        for ( Player player : this.location.getWorld().getPlayers() ) {
            player.getPlayerConnection().sendPacket( spawnParticleEffectPacket );
        }
    }

    public Dimension getDimension() {
        return this.dimension;
    }

    public void setDimension( Dimension dimension ) {
        this.dimension = dimension;
    }

    public void setVelocity( Vector velocity ) {
        this.setVelocity( velocity, true );
    }

    public void setVelocity( Vector velocity, boolean send ) {
        EntityVelocityEvent entityVelocityEvent = new EntityVelocityEvent( this, velocity );
        if ( entityVelocityEvent.isCancelled() ) {
            return;
        }
        this.velocity = velocity;
        if ( send ) {
            EntityVelocityPacket entityVelocityPacket = new EntityVelocityPacket();
            entityVelocityPacket.setEntityId( entityVelocityEvent.getEntity().getEntityId() );
            entityVelocityPacket.setVelocity( entityVelocityEvent.getVelocity() );
            this.getWorld().sendDimensionPacket( entityVelocityPacket, velocity.getDimension() );
        }
    }

    public Vector safeMove( float movX, float movY, float movZ ) {
        // Security check so we don't move and collect bounding boxes like crazy
        if ( Math.abs( movX ) > 20 || Math.abs( movZ ) > 20 || Math.abs( movY ) > 20 ) {
            return Vector.ZERO;
        }

        float dX = movX;
        float dY = movY;
        float dZ = movZ;

        AxisAlignedBB oldBoundingBox = this.boundingBox.clone();

        // Check if we collide with some blocks when we would move that fast
        List<AxisAlignedBB> collisionList = this.getWorld().collisionCubes( this, this.boundingBox.getOffsetBoundingBox( dX, dY, dZ ), false );
        if ( collisionList != null && !this.stuckInBlock ) {
            // Check if we would hit a y border block
            for ( AxisAlignedBB axisAlignedBB : collisionList ) {
                dY = axisAlignedBB.calculateYOffset( this.boundingBox, dY );
                if ( dY != movY ) {
                    Block block = this.getWorld().getBlock( (int) axisAlignedBB.getMinX(), (int) axisAlignedBB.getMinY(), (int) axisAlignedBB.getMinZ() );
                    this.collidedWith.add( block );
                }
            }

            this.boundingBox.offset( 0, dY, 0 );

            // Check if we would hit a x border block
            for ( AxisAlignedBB axisAlignedBB : collisionList ) {
                dX = axisAlignedBB.calculateXOffset( this.boundingBox, dX );
                if ( dX != movX ) {
                    Block block = this.getWorld().getBlock( (int) axisAlignedBB.getMinX(), (int) axisAlignedBB.getMinY(), (int) axisAlignedBB.getMinZ() );

                    this.collidedWith.add( block );
                }
            }

            this.boundingBox.offset( dX, 0, 0 );

            // Check if we would hit a z border block
            for ( AxisAlignedBB axisAlignedBB : collisionList ) {
                dZ = axisAlignedBB.calculateZOffset( this.boundingBox, dZ );
                if ( dZ != movZ ) {
                    Block block = this.getWorld().getBlock( (int) axisAlignedBB.getMinX(), (int) axisAlignedBB.getMinY(), (int) axisAlignedBB.getMinZ() );

                    this.collidedWith.add( block );
                }
            }

            this.boundingBox.offset( 0, 0, dZ );
        } else {
            this.boundingBox.offset( dX, dY, dZ );
        }

        // Check if we can jump
        boolean notFallingFlag = ( this.isOnGround || ( dY != movY && movY < 0 ) );
        if ( this.stepHeight > 0 && notFallingFlag && ( movX != dX || movZ != dZ ) ) {
            float oldDX = dX;
            float oldDY = dY;
            float oldDZ = dZ;

            dX = movX;
            dY = this.stepHeight;
            dZ = movZ;

            // Save and restore old bounding box
            AxisAlignedBB oldBoundingBox1 = this.boundingBox.clone();
            this.boundingBox.setBounds( oldBoundingBox );

            // Check for collision
            collisionList = this.getWorld().collisionCubes( this, this.boundingBox.addCoordinates( dX, dY, dZ ), false );
            if ( collisionList != null ) {
                // Check if we would hit a y border block
                for ( AxisAlignedBB axisAlignedBB : collisionList ) {
                    dY = axisAlignedBB.calculateYOffset( this.boundingBox, dY );
                }

                this.boundingBox.offset( 0, dY, 0 );

                // Check if we would hit a x border block
                for ( AxisAlignedBB axisAlignedBB : collisionList ) {
                    dX = axisAlignedBB.calculateXOffset( this.boundingBox, dX );
                }

                this.boundingBox.offset( dX, 0, 0 );

                // Check if we would hit a z border block
                for ( AxisAlignedBB axisAlignedBB : collisionList ) {
                    dZ = axisAlignedBB.calculateZOffset( this.boundingBox, dZ );
                }

                this.boundingBox.offset( 0, 0, dZ );
            }

            // Check if we moved left or right
            if ( Utils.square( oldDX ) + Utils.square( oldDZ ) >= Utils.square( dX ) + Utils.square( dZ ) ) {
                // Revert this decision of moving the bounding box up
                dX = oldDX;
                dY = oldDY;
                dZ = oldDZ;
                this.boundingBox.setBounds( oldBoundingBox1 );
            }
        }

        if ( dX != 0 || dY != 0 || dZ != 0 ) {
            // Move by new bounding box
            this.location.setLocation(
                    ( this.boundingBox.getMinX() + this.boundingBox.getMaxX() / 2 ),
                    this.boundingBox.getMinY(),
                    ( this.boundingBox.getMinZ() + this.boundingBox.getMaxZ() ) / 2 );
        }

        // Check for grounding states
        this.checkIfCollided( movX, movY, movZ, dX, dY, dZ );
        this.updateFallState( dY );

        // Check if we are stuck in a block
        if ( this.needsToBePushedOutOfBlocks() ) {
            this.checkInsideBlock();
        }

        return new Vector( dX, dY, dZ );
    }

    protected void checkIfCollided( float movX, float movY, float movZ, float dX, float dY, float dZ ) {
        this.isCollidedVertically = movY != dY;
        this.isCollidedHorizontally = ( movX != dX || movZ != dZ );
        this.isCollided = ( this.isCollidedHorizontally || this.isCollidedVertically );
        this.isOnGround = ( movY != dY && movY < 0 );
        System.out.println("Date: " + isOnGround);
    }

    private void updateFallState( float dY ) {
        // When we are onground again we need to deal damage
        if ( this.isOnGround ) {
            if ( this.fallDistance > 0 ) this.handleFall();

            this.fallDistance = 0;
        } else if ( dY < 0 ) {
            this.fallDistance -= dY;
        }
    }

    private void checkInsideBlock() {
        // Check in which block we are
        int fullBlockX = this.getFloorX();
        int fullBlockY = this.getFloorY();
        int fullBlockZ = this.getFloorZ();

        // Are we stuck inside a block?
        Block block = this.getWorld().getBlock( fullBlockX, fullBlockY, fullBlockZ );
        if ( block.isSolid() && block.getBoundingBox().intersectsWith( this.boundingBox ) ) {
            // We need to check for "smooth" movement when its a player (it climbs .5 steps in .3 -> .420 -> .468 .487 .495 .498 .499 steps
            if ( this instanceof Player && ( this.stuckInBlockTicks++ <= 20 || ( (Player) this ).getAdventureSettings().isNoClip() ) ) { // Yes we can "smooth" for up to 20 ticks, thanks mojang :D
                return;
            }
            // Calc with how much force we can get out of here, this depends on how far we are in
            float diffX = this.getX() - fullBlockX;
            float diffY = this.getY() - fullBlockY;
            float diffZ = this.getZ() - fullBlockZ;

            // Random out the force
            double force = Math.random() * 0.2 + 0.1;

            // Check for free blocks
            boolean freeMinusX = !this.getWorld().getBlock( fullBlockX - 1, fullBlockY, fullBlockZ ).isSolid();
            boolean freePlusX = !this.getWorld().getBlock( fullBlockX + 1, fullBlockY, fullBlockZ ).isSolid();
            boolean freeMinusY = !this.getWorld().getBlock( fullBlockX, fullBlockY - 1, fullBlockZ ).isSolid();
            boolean freePlusY = !this.getWorld().getBlock( fullBlockX, fullBlockY + 1, fullBlockZ ).isSolid();
            boolean freeMinusZ = !this.getWorld().getBlock( fullBlockX, fullBlockY, fullBlockZ - 1 ).isSolid();
            boolean freePlusZ = !this.getWorld().getBlock( fullBlockX, fullBlockY, fullBlockZ + 1 ).isSolid();

            // Since we want the lowest amount of push we have to select the smallest side
            byte direction = -1;
            float lowest = 9999;

            // The -X side is free, use it for now
            if ( freeMinusX ) {
                direction = 0;
                lowest = diffX;
            }

            // Choose +X side only when free and we need to move less
            if ( freePlusX && 1 - diffX < lowest ) {
                direction = 1;
                lowest = 1 - diffX;
            }

            // Choose -Y side only when free and we need to move less
            if ( freeMinusY && diffY < lowest ) {
                direction = 2;
                lowest = diffY;
            }

            // Choose +Y side only when free and we need to move less
            if ( freePlusY && 1 - diffY < lowest ) {
                direction = 3;
                lowest = 1 - diffY;
            }

            // Choose -Z side only when free and we need to move less
            if ( freeMinusZ && diffZ < lowest ) {
                direction = 4;
                lowest = diffZ;
            }

            // Choose +Z side only when free and we need to move less
            if ( freePlusZ && 1 - diffZ < lowest ) {
                direction = 5;
            }

            // Push to the side we selected
            switch ( direction ) {
                case 0:
                    this.velocity = new Vector( (float) -force, 0, 0 );
                    break;
                case 1:
                    this.velocity = new Vector( (float) force, 0, 0 );
                    break;
                case 2:
                    this.velocity = new Vector( 0, (float) -force, 0 );
                    break;
                case 3:
                    this.velocity = new Vector( 0, (float) force, 0 );
                    break;
                case 4:
                    this.velocity = new Vector( 0, 0, (float) -force );
                    break;
                case 5:
                    this.velocity = new Vector( 0, 0, (float) force );
                    break;
            }
            this.stuckInBlock = true;
        } else {
            this.stuckInBlock = false;
            this.stuckInBlockTicks = 0;
        }
    }

    protected void checkBlockCollisions() {
        List<Block> blockList = this.getWorld().getCollisionBlocks( this, true );
        if ( blockList != null ) {
            Vector pushedByBlocks = new Vector( 0, 0, 0 );

            for ( Block block : blockList ) {
                block.onEntityCollision( this );
                pushedByBlocks = block.addVelocity( this, pushedByBlocks );
            }

            if ( pushedByBlocks.lengthSquared() > 0 ) {
                pushedByBlocks = pushedByBlocks.normalize().multiply( 0.014f );
                Vector newMotion = this.velocity.add( pushedByBlocks );
                this.velocity = new Vector( newMotion.getX(), newMotion.getY(), newMotion.getZ() );
            }
        }
    }

}
