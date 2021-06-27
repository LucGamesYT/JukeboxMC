package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockAzaleaLeaves;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemAzaleaLeaves extends Item{

    public ItemAzaleaLeaves() {
        super( "minecraft:azalea_leaves" );
    }

    @Override
    public BlockAzaleaLeaves getBlock() {
        return new BlockAzaleaLeaves();
    }
}
