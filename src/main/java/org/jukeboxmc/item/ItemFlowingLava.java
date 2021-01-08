package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockFlowingLava;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemFlowingLava extends Item {

    public ItemFlowingLava() {
        super( "minecraft:flowing_lava", 10 );
    }

    @Override
    public BlockFlowingLava getBlock() {
        return new BlockFlowingLava();
    }
}
