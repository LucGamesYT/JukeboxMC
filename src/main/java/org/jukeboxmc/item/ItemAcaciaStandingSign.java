package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockAcaciaStandingSign;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemAcaciaStandingSign extends Item {

    public ItemAcaciaStandingSign() {
        super( "minecraft:acacia_standing_sign", -190 );
    }

    @Override
    public BlockAcaciaStandingSign getBlock() {
        return new BlockAcaciaStandingSign();
    }
}
