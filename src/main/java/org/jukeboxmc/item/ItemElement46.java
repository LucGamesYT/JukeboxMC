package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockElement47;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemElement46 extends Item {

    public ItemElement46() {
        super ( "minecraft:element_46" );
    }

    @Override
    public Block getBlock() {
        return new BlockElement47();
    }
}
