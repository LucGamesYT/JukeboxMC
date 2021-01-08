package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockStructureVoid;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemStructureVoid extends Item {

    public ItemStructureVoid() {
        super( "minecraft:structure_void", 217 );
    }

    @Override
    public BlockStructureVoid getBlock() {
        return new BlockStructureVoid();
    }
}
