package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockEndPortal;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemEndPortal extends Item {

    public ItemEndPortal() {
        super( "minecraft:end_portal", 119 );
    }

    @Override
    public BlockEndPortal getBlock() {
        return new BlockEndPortal();
    }
}
