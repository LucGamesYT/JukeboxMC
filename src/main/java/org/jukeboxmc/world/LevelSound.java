package org.jukeboxmc.world;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public enum LevelSound {

    ITEM_USE_ON( 0 ),
    HIT( 1 ),
    STEP( 2 ),
    FLY( 3 ),
    JUMP( 4 ),
    BREAK( 5 ),
    PLACE( 6 ),
    HEAVY_STEP( 7 ),
    GALLOP( 8 ),
    FALL( 9 ),
    AMBIENT( 10 ),
    AMBIENT_BABY( 11 ),
    AMBIENT_IN_WATER( 12 ),
    BREATHE( 13 ),
    DEATH( 14 ),
    DEATH_IN_WATER( 15 ),
    DEATH_TO_ZOMBIE( 16 ),
    HURT( 17 ),
    HURT_IN_WATER( 18 ),
    MAD( 19 ),
    BOOST( 20 ),
    BOW( 21 ),
    SQUISH_BIG( 22 ),
    SQUISH_SMALL( 23 ),
    FALL_BIG( 24 ),
    FALL_SMALL( 25 ),
    SPLASH( 26 ),
    FIZZ( 27 ),
    FLAP( 28 ),
    SWIM( 29 ),
    DRINK( 30 ),
    EAT( 31 ),
    TAKEOFF( 32 ),
    SHAKE( 33 ),
    PLOP( 34 ),
    LAND( 35 ),
    SADDLE( 36 ),
    ARMOR( 37 ),
    MOB_ARMOR_STAND_PLACE( 38 ),
    ADD_CHEST( 39 ),
    THROW( 40 ),
    ATTACK( 41 ),
    ATTACK_NODAMAGE( 42 ),
    ATTACK_STRONG( 43 ),
    WARN( 44 ),
    SHEAR( 45 ),
    MILK( 46 ),
    THUNDER( 47 ),
    EXPLODE( 48 ),
    FIRE( 49 ),
    IGNITE( 50 ),
    FUSE( 51 ),
    STARE( 52 ),
    SPAWN( 53 ),
    SHOOT( 54 ),
    BREAK_BLOCK( 55 ),
    LAUNCH( 56 ),
    BLAST( 57 ),
    LARGE_BLAST( 58 ),
    TWINKLE( 59 ),
    REMEDY( 60 ),
    UNFECT( 61 ),
    LEVELUP( 62 ),
    BOW_HIT( 63 ),
    BULLET_HIT( 64 ),
    EXTINGUISH_FIRE( 65 ),
    ITEM_FIZZ( 66 ),
    CHEST_OPEN( 67 ),
    CHEST_CLOSED( 68 ),
    SHULKERBOX_OPEN( 69 ),
    SHULKERBOX_CLOSED( 70 ),
    ENDERCHEST_OPEN( 71 ),
    ENDERCHEST_CLOSED( 72 ),
    POWER_ON( 73 ),
    POWER_OFF( 74 ),
    ATTACH( 75 ),
    DETACH( 76 ),
    DENY( 77 ),
    TRIPOD( 78 ),
    POP( 79 ),
    DROP_SLOT( 80 ),
    NOTE( 81 ),
    THORNS( 82 ),
    PISTON_IN( 83 ),
    PISTON_OUT( 84 ),
    PORTAL( 85 ),
    WATER( 86 ),
    LAVA_POP( 87 ),
    LAVA( 88 ),
    BURP( 89 ),
    BUCKET_FILL_WATER( 90 ),
    BUCKET_FILL_LAVA( 91 ),
    BUCKET_EMPTY_WATER( 92 ),
    BUCKET_EMPTY_LAVA( 93 ),
    ARMOR_EQUIP_CHAIN( 94 ),
    ARMOR_EQUIP_DIAMOND( 95 ),
    ARMOR_EQUIP_GENERIC( 96 ),
    ARMOR_EQUIP_GOLD( 97 ),
    ARMOR_EQUIP_IRON( 98 ),
    ARMOR_EQUIP_LEATHER( 99 ),
    ARMOR_EQUIP_ELYTRA( 100 ),
    RECORD_13( 101 ),
    RECORD_CAT( 102 ),
    RECORD_BLOCKS( 103 ),
    RECORD_CHIRP( 104 ),
    RECORD_FAR( 105 ),
    RECORD_MALL( 106 ),
    RECORD_MELLOHI( 107 ),
    RECORD_STAL( 108 ),
    RECORD_STRAD( 109 ),
    RECORD_WARD( 110 ),
    RECORD_11( 111 ),
    RECORD_WAIT( 112 ),
    STOP_RECORD( 113 ),
    FLOP( 114 ),
    ELDERGUARDIAN_CURSE( 115 ),
    MOB_WARNING( 116 ),
    MOB_WARNING_BABY( 117 ),
    TELEPORT( 118 ),
    SHULKER_OPEN( 119 ),
    SHULKER_CLOSE( 120 ),
    HAGGLE( 121 ),
    HAGGLE_YES( 122 ),
    HAGGLE_NO( 123 ),
    HAGGLE_IDLE( 124 ),
    CHORUS_GROW( 125 ),
    CHORUS_DEATH( 126 ),
    GLASS( 127 ),
    POTION_BREWED( 128 ),
    CAST_SPELL( 129 ),
    PREPARE_ATTACK( 130 ),
    PREPARE_SUMMON( 131 ),
    PREPARE_WOLOLO( 132 ),
    FANG( 133 ),
    CHARGE( 134 ),
    CAMERA_TAKE_PICTURE( 135 ),
    LEASHKNOT_PLACE( 136 ),
    LEASHKNOT_BREAK( 137 ),
    GROWL( 138 ),
    WHINE( 139 ),
    PANT( 140 ),
    PURR( 141 ),
    PURREOW( 142 ),
    DEATH_MIN_VOLUME( 143 ),
    DEATH_MID_VOLUME( 144 ),
    IMITATE_BLAZE( 145 ),
    IMITATE_CAVE_SPIDER( 146 ),
    IMITATE_CREEPER( 147 ),
    IMITATE_ELDER_GUARDIAN( 148 ),
    IMITATE_ENDER_DRAGON( 149 ),
    IMITATE_ENDERMAN( 150 ),
    IMITATE_EVOCATION_ILLAGER( 152 ),
    IMITATE_GHAST( 153 ),
    IMITATE_HUSK( 154 ),
    IMITATE_ILLUSION_ILLAGER( 155 ),
    IMITATE_MAGMA_CUBE( 156 ),
    IMITATE_POLAR_BEAR( 157 ),
    IMITATE_SHULKER( 158 ),
    IMITATE_SILVERFISH( 159 ),
    IMITATE_SKELETON( 160 ),
    IMITATE_SLIME( 161 ),
    IMITATE_SPIDER( 162 ),
    IMITATE_STRAY( 163 ),
    IMITATE_VEX( 164 ),
    IMITATE_VINDICATION_ILLAGER( 165 ),
    IMITATE_WITCH( 166 ),
    IMITATE_WITHER( 167 ),
    IMITATE_WITHER_SKELETON( 168 ),
    IMITATE_WOLF( 169 ),
    IMITATE_ZOMBIE( 170 ),
    IMITATE_ZOMBIE_PIGMAN( 171 ),
    IMITATE_ZOMBIE_VILLAGER( 172 ),
    BLOCK_END_PORTAL_FRAME_FILL( 173 ),
    BLOCK_END_PORTAL_SPAWN( 174 ),
    RANDOM_ANVIL_USE( 175 ),
    BOTTLE_DRAGONBREATH( 176 ),
    PORTAL_TRAVEL( 177 ),
    ITEM_TRIDENT_HIT( 178 ),
    ITEM_TRIDENT_RETURN( 179 ),
    ITEM_TRIDENT_RIPTIDE_1( 180 ),
    ITEM_TRIDENT_RIPTIDE_2( 181 ),
    ITEM_TRIDENT_RIPTIDE_3( 182 ),
    ITEM_TRIDENT_THROW( 183 ),
    ITEM_TRIDENT_THUNDER( 184 ),
    ITEM_TRIDENT_HIT_GROUND( 185 ),
    DEFAULT( 186 ),
    FLETCHING_TABLE_USE( 187 ),
    ELEMENT_CONSTRUCTOR_OPEN( 188 ),
    ICE_BOMB_HIT( 189 ),
    BALLOON_POP( 190 ),
    LT_REACTION_ICE_BOMB( 191 ),
    LT_REACTION_BLEACH( 192 ),
    LT_REACTION_E_PASTE( 193 ),
    LT_REACTION_E_PASTE2( 194 ),
    LT_REACTION_FERTILIZER( 199 ),
    LT_REACTION_FIREBALL( 200 ),
    LT_REACTION_MG_SALT( 201 ),
    LT_REACTION_MISC_FIRE( 202 ),
    LT_REACTION_FIRE( 203 ),
    LT_REACTION_MISC_EXPLOSION( 204 ),
    LT_REACTION_MISC_MYSTICAL( 205 ),
    LT_REACTION_MISC_MYSTICAL2( 206 ),
    LT_REACTION_PRODUCT( 207 ),
    SPARKLER_USE( 208 ),
    GLOWSTICK_USE( 209 ),
    SPARKLER_ACTIVE( 210 ),
    CONVERT_TO_DROWNED( 211 ),
    BUCKET_FILL_FISH( 212 ),
    BUCKET_EMPTY_FISH( 213 ),
    BUBBLE_UP( 214 ),
    BUBBLE_DOWN( 215 ),
    BUBBLE_POP( 216 ),
    BUBBLE_UP_INSIDE( 217 ),
    BUBBLE_DOWN_INSIDE( 218 ),
    BABY_HURT( 219 ),
    BABY_DEATH( 220 ),
    BABY_STEP( 221 ),
    BABY_SPAWN( 222 ),
    BORN( 223 ),
    BLOCK_TURTLE_EGG_BREAK( 224 ),
    BLOCK_TURTLE_EGG_CRACK( 225 ),
    BLOCK_TURTLE_EGG_HATCH( 226 ),
    TURTLE_LAY_EGG( 227 ),
    BLOCK_TURTLE_EGG_ATTACK( 228 ),
    BEACON_ACTIVATE( 229 ),
    BEACON_AMBIENT( 230 ),
    BEACON_DEACTIVATE( 231 ),
    BEACON_POWER( 232 ),
    CONDUIT_ACTIVATE( 233 ),
    CONDUIT_AMBIENT( 234 ),
    CONDUIT_ATTACK( 235 ),
    CONDUIT_DEACTIVATE( 236 ),
    CONDUIT_SHORT( 237 ),
    SWOOP( 238 ),
    BLOCK_BAMBOO_SAPLING_PLACE( 239 ),
    PRE_SNEEZE( 240 ),
    SNEEZE( 241 ),
    AMBIENT_TAME( 242 ),
    SCARED( 243 ),
    BLOCK_SCAFFOLDING_CLIMB( 244 ),
    CROSSBOW_LOADING_START( 245 ),
    CROSSBOW_LOADING_MIDDLE( 246 ),
    CROSSBOW_LOADING_END( 247 ),
    CROSSBOW_SHOOT( 248 ),
    CROSSBOW_QUICK_CHARGE_START( 249 ),
    CROSSBOW_QUICK_CHARGE_MIDDLE( 250 ),
    CROSSBOW_QUICK_CHARGE_END( 251 ),
    AMBIENT_AGGRESSIVE( 252 ),
    AMBIENT_WORRIED( 253 ),
    CANT_BREED( 254 ),
    SHIELD_BLOCK( 255 ),
    LECTERN_BOOK_PLACE( 256 ),
    GRINDSTONE_USE( 257 ),
    BELL( 258 ),
    CAMPFIRE_CRACKLE( 259 ),
    ROAR( 260 ),
    STUN( 261 ),
    SWEET_BERRY_BUSH_HURT( 262 ),
    SWEET_BERRY_BUSH_PICK( 263 ),
    CARTOGRAPHY_TABLE_USE( 264 ),
    STONECUTTER_USE( 265 ),
    COMPOSTER_EMPTY( 266 ),
    COMPOSTER_FILL( 267 ),
    COMPOSTER_FILL_LAYER( 268 ),
    COMPOSTER_READY( 269 ),
    BARREL_OPEN( 270 ),
    BARREL_CLOSE( 271 ),
    RAID_HORN( 272 ),
    LOOM_USE( 273 ),
    AMBIENT_IN_RAID( 274 ),
    UI_CARTOGRAPHY_TABLE_USE( 275 ),
    UI_STONECUTTER_USE( 276 ),
    UI_LOOM_USE( 277 ),
    SMOKER_USE( 278 ),
    BLAST_FURNACE_USE( 279 ),
    SMITHING_TABLE_USE( 280 ),
    SCREECH( 281 ),
    SLEEP( 282 ),
    FURNACE_USE( 283 ),
    MOOSHROOM_CONVERT( 284 ),
    MILK_SUSPICIOUSLY( 285 ),
    CELEBRATE( 286 ),
    JUMP_PREVENT( 287 ),
    AMBIENT_POLLINATE( 288 ),
    BEEHIVE_DRIP( 289 ),
    BEEHIVE_ENTER( 290 ),
    BEEHIVE_EXIT( 291 ),
    BEEHIVE_WORK( 292 ),
    BEEHIVE_SHEAR( 293 ),
    HONEYBOTTLE_DRINK( 294 ),
    AMBIENT_CAVE( 295 ),
    RETREAT( 296 ),
    CONVERT_TO_ZOMBIFIED( 297 ),
    ADMIRE( 298 ),
    STEP_LAVA( 299 ),
    TEMPT( 300 ),
    PANIC( 301 ),
    ANGRY( 302 ),
    AMBIENT_WARPED_FOREST( 303 ),
    AMBIENT_SOULSAND_VALLEY( 304 ),
    AMBIENT_NETHER_WASTES( 305 ),
    AMBIENT_BASALT_DELTAS( 306 ),
    AMBIENT_CRIMSON_FOREST( 307 ),
    RESPAWN_ANCHOR_CHARGE( 308 ),
    RESPAWN_ANCHOR_DEPLETE( 309 ),
    RESPAWN_ANCHOR_SET_SPAWN( 310 ),
    RESPAWN_ANCHOR_AMBIENT( 311 ),
    SOUL_ESCAPE_QUIET( 312 ),
    SOUL_ESCAPE_LOUD( 313 ),
    RECORD_PIGSTEP( 314 ),
    LINK_COMPASS_TO_LODESTONE( 315 ),
    USE_SMITHING_TABLE( 316 ),
    EQUIP_NETHERITE( 317 ),
    AMBIENT_LOOP_WARPED_FOREST( 318 ),
    AMBIENT_LOOP_SOULSAND_VALLEY( 319 ),
    AMBIENT_LOOP_NETHER_WASTES( 320 ),
    AMBIENT_LOOP_BASALT_DELTAS( 321 ),
    AMBIENT_LOOP_CRIMSON_FOREST( 322 ),
    AMBIENT_ADDITION_WARPED_FOREST( 323 ),
    AMBIENT_ADDITION_SOULSAND_VALLEY( 324 ),
    AMBIENT_ADDITION_NETHER_WASTES( 325 ),
    AMBIENT_ADDITION_BASALT_DELTAS( 326 ),
    AMBIENT_ADDITION_CRIMSON_FOREST( 327 ),
    SCULK_SENSOR_POWER_ON( 328 ),
    SCULK_SENSOR_POWER_OFF( 329 ),
    BUCKET_FILL_POWDER_SNOW( 330 ),
    BUCKET_EMPTY_POWDER_SNOW( 331 ),
    POINTED_DRIPSTONE_CAULDRON_DRIP_LAVA( 332 ),
    POINTED_DRIPSTONE_CAULDRON_DRIP_WATER( 333 ),
    POINTED_DRIPSTONE_DRIP_LAVA( 334 ),
    POINTED_DRIPSTONE_DRIP_WATER( 335 ),
    CAVE_VINES_PICK_BERRIES( 336 ),
    BIG_DRIPLEAF_TILT_DOWN( 337 ),
    BIG_DRIPLEAF_TILT_UP( 338 ),
    UNDEFINED( 339 );

    private int id;

    LevelSound( int id ) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }
}
