package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockWarpedWartBlock;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemWarpedWartBlock extends Item {

    public ItemWarpedWartBlock() {
        super ( "minecraft:warped_wart_block" );
    }

    @Override
    public BlockWarpedWartBlock getBlock() {
        return new BlockWarpedWartBlock();
    }
}
