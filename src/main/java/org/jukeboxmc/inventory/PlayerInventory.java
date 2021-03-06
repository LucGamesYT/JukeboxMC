package org.jukeboxmc.inventory;

import org.jukeboxmc.Server;
import org.jukeboxmc.entity.passive.EntityHuman;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemType;
import org.jukeboxmc.network.packet.InventoryContentPacket;
import org.jukeboxmc.network.packet.InventorySlotPacket;
import org.jukeboxmc.network.packet.MobEquipmentPacket;
import org.jukeboxmc.player.Player;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class PlayerInventory extends ContainerInventory {

    private int itemInHandSlot;

    public PlayerInventory( InventoryHolder holder ) {
        super( holder, 36 );
    }

    @Override
    public InventoryType getInventoryType() {
        return InventoryType.PLAYER;
    }

    @Override
    public WindowTypeId getWindowTypeId() {
        return WindowTypeId.INVENTORY;
    }

    @Override
    public void sendContents( Player player ) {
        InventoryContentPacket inventoryContentPacket = new InventoryContentPacket();
        if ( player.getCurrentInventory() == this ) {
            inventoryContentPacket.setWindowId( WindowId.OPEN_CONTAINER );
            inventoryContentPacket.setItems( this.contents );
            player.getPlayerConnection().sendPacket( inventoryContentPacket );
        }
        inventoryContentPacket.setWindowId( WindowId.PLAYER );
        inventoryContentPacket.setItems( this.contents );
        player.getPlayerConnection().sendPacket( inventoryContentPacket );
    }

    @Override
    public void sendContents( int slot, Player player ) {
        InventorySlotPacket inventorySlotPacket = new InventorySlotPacket();
        if ( player.getCurrentInventory() != null && player.getCurrentInventory() == this ) {
            inventorySlotPacket.setWindowId( WindowId.OPEN_CONTAINER );
        } else {
            inventorySlotPacket.setWindowId( WindowId.PLAYER );
        }
        inventorySlotPacket.setSlot( slot );
        inventorySlotPacket.setItem( this.contents[slot] );
        player.getPlayerConnection().sendPacket( inventorySlotPacket );
    }

    @Override
    public void setItem( int slot, Item item ) {
        super.setItem( slot, item );

        if ( slot == this.itemInHandSlot && this.holder instanceof Player ) {
            this.updateItemInHandForAll();
        }
    }

    public Item getItemInHand() {
        Item content = this.contents[this.itemInHandSlot];
        if ( content != null ) {
            return content;
        } else {
            return ItemType.AIR.getItem();
        }
    }

    public int getItemInHandSlot() {
        return this.itemInHandSlot;
    }

    public void setItemInHandSlot( int itemInHandSlot ) {
        if ( itemInHandSlot >= 0 && itemInHandSlot < 9 ) {
            this.itemInHandSlot = itemInHandSlot;
            this.updateItemInHandForAll();
        }
    }

    public void setItemInHand( Item itemInHand ) {
        this.setItem( this.itemInHandSlot, itemInHand );
        this.sendItemInHand();
    }

    public MobEquipmentPacket createMobEquipmentPacket( EntityHuman entityHuman ) {
        MobEquipmentPacket mobEquipmentPacket = new MobEquipmentPacket();
        mobEquipmentPacket.setEntityId( entityHuman.getEntityId() );
        mobEquipmentPacket.setItem( this.getItemInHand() );
        mobEquipmentPacket.setWindowId( WindowId.PLAYER.getId() );
        mobEquipmentPacket.setHotbarSlot( this.itemInHandSlot );
        mobEquipmentPacket.setInventarSlot( this.itemInHandSlot );
        return mobEquipmentPacket;
    }

    public void sendItemInHand() {
        if ( this.holder instanceof Player ) {
            Player player = (Player) this.holder;
            player.getPlayerConnection().sendPacket( this.createMobEquipmentPacket( player ) );
            this.sendContents( this.itemInHandSlot, player );
        }
    }

    public void updateItemInHandForAll() {
        if ( this.holder instanceof EntityHuman ) {
            EntityHuman entityHuman = (EntityHuman) this.holder;

            MobEquipmentPacket mobEquipmentPacket = this.createMobEquipmentPacket( entityHuman );

            for ( Player onlinePlayers : Server.getInstance().getOnlinePlayers() ) { //Get world players
                if ( onlinePlayers != entityHuman ) {
                    onlinePlayers.getPlayerConnection().sendPacket( mobEquipmentPacket );
                }
            }
        }
    }
}
