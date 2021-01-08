package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockGoldBlock;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemGoldBlock extends Item {

    public ItemGoldBlock() {
        super( "minecraft:gold_block", 41 );
    }

    @Override
    public BlockGoldBlock getBlock() {
        return new BlockGoldBlock();
    }
}
