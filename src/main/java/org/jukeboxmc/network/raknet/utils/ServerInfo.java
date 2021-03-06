package org.jukeboxmc.network.raknet.utils;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jukeboxmc.Server;
import org.jukeboxmc.network.Protocol;
import org.jukeboxmc.network.raknet.Listener;
import org.jukeboxmc.player.GameMode;

import java.util.StringJoiner;

/**
 * @author LucGamesYT
 * @version 1.0
 */

@Getter
@Setter
@NoArgsConstructor
public class ServerInfo {

    private String motd;
    private String submotd;
    private int onlinePlayers = 0;
    private int maxPlayers;
    private GameMode gameMode;
    private long serverId;

    public ServerInfo( Server server, Listener listener ) {
        this.serverId = listener.getServerId();
        this.motd = server.getServerConfig().getString( "motd" );
        this.submotd = server.getServerConfig().getString("submotd");
        this.maxPlayers = server.getServerConfig().getInt( "maxplayers" );
        this.gameMode = GameMode.valueOf( server.getServerConfig().getString( "gamemode" ).toUpperCase() );
    }

    @Override
    public String toString() {
        StringJoiner stringJoiner = new StringJoiner( ";" );
        stringJoiner.add( "MCPE" );
        stringJoiner.add( this.motd );
        stringJoiner.add( Integer.toString( Protocol.PROTOCOL ) );
        stringJoiner.add( Protocol.MINECRAFT_VERSION );
        stringJoiner.add( Integer.toString( this.onlinePlayers ) );
        stringJoiner.add( Integer.toString( this.maxPlayers ) );
        stringJoiner.add( Long.toString( this.serverId ) );
        stringJoiner.add( this.submotd );
        stringJoiner.add( this.gameMode.getGamemode() );
        stringJoiner.add( "1" );
        return stringJoiner.toString();
    }
}
