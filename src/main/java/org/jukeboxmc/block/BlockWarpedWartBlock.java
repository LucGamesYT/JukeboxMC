package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemWarpedWartBlock;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockWarpedWartBlock extends Block {

    public BlockWarpedWartBlock() {
        super( "minecraft:warped_wart_block" );
    }

    @Override
    public ItemWarpedWartBlock toItem() {
        return new ItemWarpedWartBlock();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.WARPED_WART_BLOCK;
    }

}
