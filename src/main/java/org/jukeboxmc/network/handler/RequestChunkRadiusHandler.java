package org.jukeboxmc.network.handler;

import org.apache.commons.math3.util.FastMath;
import org.jukeboxmc.network.packet.Packet;
import org.jukeboxmc.network.packet.RequestChunkRadiusPacket;
import org.jukeboxmc.player.Player;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class RequestChunkRadiusHandler implements PacketHandler {

    @Override
    public void handle( Packet packet, Player player ) {
        RequestChunkRadiusPacket chunkRadiusPacket = (RequestChunkRadiusPacket) packet;
        int radius = FastMath.min( chunkRadiusPacket.getRadius(), player.getServer().getViewDistance() );
        player.setViewDistance( radius );

        if ( !player.isSpawned() ) {
            player.getPlayerConnection().joinGame();
        }
    }
}