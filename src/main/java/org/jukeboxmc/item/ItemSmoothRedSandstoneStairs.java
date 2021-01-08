package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockSmoothRedSandstoneStairs;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemSmoothRedSandstoneStairs extends Item {

    public ItemSmoothRedSandstoneStairs() {
        super( "minecraft:smooth_red_sandstone_stairs", -176 );
    }

    @Override
    public BlockSmoothRedSandstoneStairs getBlock() {
        return new BlockSmoothRedSandstoneStairs();
    }
}
