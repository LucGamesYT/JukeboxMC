package org.jukeboxmc.world.chunk;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.buffer.PooledByteBufAllocator;
import lombok.ToString;
import org.apache.commons.math3.util.FastMath;
import org.iq80.leveldb.DB;
import org.iq80.leveldb.WriteBatch;
import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockPalette;
import org.jukeboxmc.blockentity.BlockEntity;
import org.jukeboxmc.entity.Entity;
import org.jukeboxmc.math.Location;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.nbt.NBTOutputStream;
import org.jukeboxmc.nbt.NbtMap;
import org.jukeboxmc.nbt.NbtUtils;
import org.jukeboxmc.network.packet.LevelChunkPacket;
import org.jukeboxmc.utils.BinaryStream;
import org.jukeboxmc.utils.Palette;
import org.jukeboxmc.utils.Utils;
import org.jukeboxmc.world.Biome;
import org.jukeboxmc.world.Dimension;
import org.jukeboxmc.world.World;
import org.jukeboxmc.world.leveldb.LevelDBChunk;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

/**
 * @author LucGamesYT
 * @version 1.0
 */

@ToString ( exclude = { "subChunks" } )
public class Chunk extends LevelDBChunk {

    public static final int CHUNK_LAYERS = 2;

    public SubChunk[] subChunks;

    private World world;
    private int chunkX;
    private int chunkZ;
    public Dimension dimension;
    public byte chunkVersion = 21;

    private List<Entity> entitys = new CopyOnWriteArrayList<>();

    public Chunk( World world, int chunkX, int chunkZ, Dimension dimension ) {
        super( world, chunkX, chunkZ );
        this.world = world;
        this.chunkX = chunkX;
        this.chunkZ = chunkZ;
        this.dimension = dimension;
        this.subChunks = new SubChunk[16];
    }

    public void setHeightMap( int x, int z, int value ) {
        this.height[(z << 4) | x] = (byte) value;
    }

    public int getHeightMap( int x, int z ) {
        return this.height[(z << 4) | x] & 0xFF;
    }

    public void setBlock( Vector location, int layer, int runtimeId ) {
        int subY = location.getFloorY() >> 4;
        this.checkAndCreateSubChunks( subY );
        this.subChunks[subY].setBlock( location.getFloorX() & 15, location.getFloorY() & 15, location.getFloorZ() & 15, layer, runtimeId );
    }

    public void setBlock( int x, int y, int z, int layer, int runtimeId ) {
        int subY = y >> 4;
        this.checkAndCreateSubChunks( subY );
        this.subChunks[subY].setBlock( x & 15, y & 15, z & 15, layer, runtimeId );
    }

    public void setBlock( int x, int y, int z, int layer, Block block ) {
        int subY = y >> 4;
        this.checkAndCreateSubChunks( subY );
        this.subChunks[subY].setBlock( x & 15, y & 15, z & 15, layer, block );
    }

    public int getRuntimeId( int x, int y, int z, int layer ) {
        int subY = y >> 4;
        this.checkAndCreateSubChunks( subY );
        return this.subChunks[subY].getRuntimeId( x & 15, y & 15, z & 15, layer );
    }

    public Block getBlock( int x, int y, int z, int layer ) {
        int subY = y >> 4;
        this.checkAndCreateSubChunks( subY );
        Block block = this.subChunks[subY].getBlock( x & 15, y & 15, z & 15, layer );
        block.setLocation( new Location( this.world, new Vector( x, y, z ) ) );
        block.setLayer( layer );
        return block;
    }

    public void setBlockEntity( int x, int y, int z, BlockEntity blockEntity ) {
        int subY = y >> 4;
        this.checkAndCreateSubChunks( subY );
        this.subChunks[subY].setBlockEntity( x & 15, y & 15, z & 15, blockEntity );
    }

    public BlockEntity getBlockEntity( int x, int y, int z ) {
        int subY = y >> 4;
        this.checkAndCreateSubChunks( subY );
        return this.subChunks[subY].getBlockEntity( x & 15, y & 15, z & 15 );
    }

    public void removeBlockEntity( int x, int y, int z ) {
        int subY = y >> 4;
        this.checkAndCreateSubChunks( subY );
        this.subChunks[subY].removeBlockEntity( x & 15, y & 15, z & 15 );
    }

    public void setDimension( Dimension dimension ) {
        this.dimension = dimension;
    }

    public Dimension getDimension() {
        return this.dimension;
    }

    public List<BlockEntity> getBlockEntitys() {
        List<BlockEntity> blockEntities = new ArrayList<>();
        for ( SubChunk subChunk : this.subChunks ) {
            if ( subChunk != null && subChunk.getBlockEntitys() != null ) {
                blockEntities.addAll( subChunk.getBlockEntitys() );
            }
        }
        return blockEntities;
    }

    public void setBiome( int x, int z, Biome biome ) {
        this.biomes[( x << 4 ) | z] = (byte) biome.getId();
    }

    public Biome getBiome( int x, int z ) {
        return Biome.findById( this.biomes[( x << 4 ) | z] & 0xFF ) ;
    }

    public void checkAndCreateSubChunks( int subY ) {
        for ( int y = 0; y <= subY; y++ ) {
            if ( this.subChunks[y] == null ) {
                this.subChunks[y] = new SubChunk( y );
            }
        }
    }

    public void iterateEntities( Consumer<Entity> consumer ) {
        for ( Entity player : this.entitys ) {
            consumer.accept( player );
        }
    }

    public BinaryStream writeChunk() {
        BinaryStream binaryStream = new BinaryStream();
        for ( SubChunk subChunk : this.subChunks ) {
            if ( subChunk != null ) {
                subChunk.writeTo( binaryStream );
            }
        }
        binaryStream.writeBytes( this.biomes );
        binaryStream.writeUnsignedVarInt( 0 ); //Extradata

        List<BlockEntity> blockEntitys = this.getBlockEntitys();
        if ( !blockEntitys.isEmpty() ) {
            NBTOutputStream writer = NbtUtils.createNetworkWriter( new ByteBufOutputStream( binaryStream.getBuffer() ) );

            for ( BlockEntity blockEntity : blockEntitys ) {
                try {
                    writer.writeTag( blockEntity.toCompound().build() );
                } catch ( IOException e ) {
                    e.printStackTrace();
                }
            }
        }
        return binaryStream;
    }

    public LevelChunkPacket createLevelChunkPacket() {
        LevelChunkPacket levelChunkPacket = new LevelChunkPacket();
        levelChunkPacket.setChunkX( this.getChunkX() );
        levelChunkPacket.setChunkZ( this.getChunkZ() );
        levelChunkPacket.setSubChunkCount( this.getAvailableSubChunks() );
        levelChunkPacket.setData( this.writeChunk().getBuffer() );
        return levelChunkPacket;
    }

    public void save( DB db ) {
        WriteBatch writeBatch = db.createWriteBatch();

        for ( int subY = 0; subY < this.subChunks.length; subY++ ) {
            if ( this.subChunks[subY] == null ) {
                continue;
            }
            this.saveChunkSlice( subY, writeBatch );
        }

        //ELSE
        byte[] versionKey = Utils.getKey( this.chunkX, this.chunkZ, this.dimension, (byte) 0x2c );
        BinaryStream versionBuffer = new BinaryStream();
        versionBuffer.writeByte( this.chunkVersion );
        writeBatch.put( versionKey, versionBuffer.getArray() );

        byte[] finalizedKey = Utils.getKey( this.chunkX, this.chunkZ, this.dimension, (byte) 0x36 );
        ByteBuf byteBuf = PooledByteBufAllocator.DEFAULT.directBuffer( 1 );
        byteBuf.writeByte( this.populated ? 2 : 0 ).writeByte( 0 ).writeByte( 0 ).writeByte( 0 );
        writeBatch.put( finalizedKey, new BinaryStream( byteBuf ).getArray() );

        BinaryStream blockEntityBuffer = new BinaryStream();
        NBTOutputStream networkWriter = NbtUtils.createWriterLE( new ByteBufOutputStream( blockEntityBuffer.getBuffer() ) );
        for ( BlockEntity blockEntity : this.getBlockEntitys() ) {
            try {
                NbtMap build = blockEntity.toCompound().build();
                networkWriter.writeTag( build );
            } catch ( IOException e ) {
                e.printStackTrace();
            }
        }

        if ( blockEntityBuffer.readableBytes() > 0 ) {
            byte[] blockEntityKey = Utils.getKey( this.chunkX, this.chunkZ, this.dimension, (byte) 0x31 );
            writeBatch.put( blockEntityKey, blockEntityBuffer.getArray() );
        }

        byte[] biomeKey = Utils.getKey( this.chunkX, this.chunkZ, this.dimension, (byte) 0x2d );
        BinaryStream biomeBuffer = new BinaryStream();

        for ( short height : this.height ) {
            biomeBuffer.writeLShort( height );
        }
        biomeBuffer.writeBytes( this.biomes );
        writeBatch.put( biomeKey, biomeBuffer.getArray() );

        db.write( writeBatch );
        try {
            writeBatch.close();
        } catch ( IOException e ) {
            e.printStackTrace();
        }
    }

    private void saveChunkSlice( int subY, WriteBatch writeBatch ) {
        BinaryStream buffer = new BinaryStream();
        SubChunk subChunk = this.subChunks[subY];

        buffer.writeByte( (byte) 8 );
        buffer.writeByte( (byte) Chunk.CHUNK_LAYERS );

        for ( int layer = 0; layer < Chunk.CHUNK_LAYERS; layer++ ) {
            Integer[] layerBlocks = subChunk.blocks[layer];
            int[] blockIds = new int[4096];

            Map<Integer, Integer> indexList = new LinkedHashMap<>();
            List<Integer> runtimeIds = new ArrayList<>();

            Integer foundIndex = 0;
            int nextIndex = 0;
            int lastRuntimeId = -1;

            for ( short blockIndex = 0; blockIndex < layerBlocks.length; blockIndex++ ) {
                int runtimeId = layerBlocks[blockIndex];
                if ( runtimeId != lastRuntimeId ) {
                    foundIndex = indexList.get( runtimeId );
                    if ( foundIndex == null ) {
                        runtimeIds.add( runtimeId );
                        indexList.put( runtimeId, nextIndex );
                        foundIndex = nextIndex;
                        nextIndex++;
                    }
                    lastRuntimeId = runtimeId;
                }
                blockIds[blockIndex] = foundIndex;
            }

            float numberOfBits = Utils.log2( indexList.size() ) + 1;
            int amountOfBlocks = (int) FastMath.floor( 32 / numberOfBits );
            Palette palette = new Palette( buffer, amountOfBlocks, false );

            byte paletteWord = (byte) ( (byte) ( palette.getPaletteVersion().getVersionId() << 1 ) | 1 );
            buffer.writeByte( paletteWord );
            palette.addIndexIDs( blockIds );
            palette.finish();

            buffer.writeLInt( indexList.size() );

            for ( int runtimeId : runtimeIds ) {
                try {
                    NBTOutputStream networkWriter = NbtUtils.createWriterLE( new ByteBufOutputStream( buffer.getBuffer() ) );
                    networkWriter.writeTag( BlockPalette.getBlockNBT( runtimeId ) );
                } catch ( IOException e ) {
                    e.printStackTrace();
                }
            }
        }
        byte[] subChunkKey = Utils.getSubChunkKey( this.chunkX, this.chunkZ, this.dimension, (byte) 0x2f, (byte) subY );
        writeBatch.put( subChunkKey, buffer.getArray() );
    }

    public int getAvailableSubChunks() {
        return Arrays.stream( this.subChunks ).mapToInt( o -> o == null ? 0 : 1 ).sum();
    }

    public int getChunkX() {
        return this.chunkX;
    }

    public int getChunkZ() {
        return this.chunkZ;
    }

    public SubChunk[] getSubChunks() {
        return this.subChunks;
    }

    public void addEntity( Entity entity ) {
        if ( !this.entitys.contains( entity ) ) {
            this.entitys.add( entity );
        }
    }

    public void removeEntity( Entity entity ) {
        this.entitys.remove( entity );
    }

    public Collection<Entity> getEntitys() {
        return this.entitys;
    }
}
