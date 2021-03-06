package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockAcaciaButton;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemAcaciaButton extends Item {

    public ItemAcaciaButton() {
        super( "minecraft:acacia_button");
    }

    @Override
    public BlockAcaciaButton getBlock() {
        return new BlockAcaciaButton();
    }
}
