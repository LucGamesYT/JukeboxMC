package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockCactus;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemCactus extends Item {

    public ItemCactus() {
        super ( "minecraft:cactus" );
    }

    @Override
    public BlockCactus getBlock() {
        return new BlockCactus();
    }
}
