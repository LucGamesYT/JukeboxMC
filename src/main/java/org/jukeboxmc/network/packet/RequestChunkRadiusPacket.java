package org.jukeboxmc.network.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jukeboxmc.network.Protocol;

/**
 * @author LucGamesYT
 * @version 1.0
 */
@Data
@EqualsAndHashCode ( callSuper = true )
public class RequestChunkRadiusPacket extends Packet {

    private int radius;

    @Override
    public int getPacketId() {
        return Protocol.REQUEST_CHUNK_RADIUS_PACKET;
    }

    @Override
    public void read() {
        super.read();
        this.radius = this.readSignedVarInt();
    }
}
