package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockGrass;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemGrass extends Item {

    public ItemGrass() {
        super ( "minecraft:grass" );
    }

    @Override
    public BlockGrass getBlock() {
        return new BlockGrass();
    }
}
