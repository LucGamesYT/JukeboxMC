package org.jukeboxmc.entity.item;

import org.jukeboxmc.Server;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.entity.Entity;
import org.jukeboxmc.event.player.PlayerPickupItemEvent;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.network.packet.AddItemEntityPacket;
import org.jukeboxmc.network.packet.Packet;
import org.jukeboxmc.player.Player;

/**
 * @author LucGamesYT
 * @version 1.0
 */

public class EntityItem extends Entity {

    private Item item;
    private long pickupTime;

    public EntityItem() {
        this.gravity = 0.04f;
    }

    @Override
    public String getName() {
        return "EntityItem";
    }

    @Override
    public String getEntityType() {
        return "minecraft:item";
    }

    @Override
    public float getWidth() {
        return 0.25f;
    }

    @Override
    public float getHeight() {
        return 0.25f;
    }

    @Override
    public Packet createSpawnPacket() {
        AddItemEntityPacket addItemEntityPacket = new AddItemEntityPacket();
        addItemEntityPacket.setEntityId( this.getEntityId() );
        addItemEntityPacket.setRuntimeEntityId( this.getEntityId() );
        addItemEntityPacket.setItem( this.item );
        addItemEntityPacket.setVector( this.location );
        addItemEntityPacket.setVelocity( this.velocity );
        addItemEntityPacket.setMetadata( this.metadata );
        return addItemEntityPacket;
    }

    @Override
    public void onCollideWithPlayer( Player player ) {
        PlayerPickupItemEvent playerPickupItemEvent = new PlayerPickupItemEvent( player, this.item );
        Server.getInstance().getPluginManager().callEvent( playerPickupItemEvent );
        if ( playerPickupItemEvent.isCancelled() ) {
            return;
        }
        player.sendMessage( "CALL" );
    }

    public Item getItem() {
        return this.item.clone();
    }

    public void setItem( Item item ) {
        this.item = item;
    }

    public long getPickupTime() {
        return this.pickupTime;
    }

    public void setPickupTime( long pickupTime ) {
        this.pickupTime = pickupTime;
    }


}
