package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemWeb;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockWeb extends BlockWaterlogable {

    public BlockWeb() {
        super( "minecraft:web" );
    }

    @Override
    public ItemWeb toItem() {
        return new ItemWeb();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.WEB;
    }

    @Override
    public boolean isSolid() {
        return false;
    }

    @Override
    public boolean isTransparent() {
        return true;
    }

}
