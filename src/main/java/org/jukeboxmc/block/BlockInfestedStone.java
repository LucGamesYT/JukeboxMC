package org.jukeboxmc.block;

import org.jukeboxmc.block.type.MonsterEggStoneType;
import org.jukeboxmc.item.ItemMonsterEgg;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockInfestedStone extends Block {

    public BlockInfestedStone() {
        super( "minecraft:monster_egg" );
    }

    @Override
    public ItemMonsterEgg toItem() {
        return new ItemMonsterEgg( this.runtimeId );
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.INFESTED_STONE;
    }

    public BlockInfestedStone setMonsterEggStoneType( MonsterEggStoneType monsterEggStoneType ) {
        return this.setState( "monster_egg_stone_type", monsterEggStoneType.name().toLowerCase() );
    }

    public MonsterEggStoneType getMonsterEggStoneType() {
        return this.stateExists( "monster_egg_stone_type" ) ? MonsterEggStoneType.valueOf( this.getStringState( "monster_egg_stone_type" ) ) : MonsterEggStoneType.STONE;
    }
}
