package org.jukeboxmc.network.packet;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jukeboxmc.network.Protocol;

/**
 * @author LucGamesYT
 * @version 1.0
 */
@Data
@AllArgsConstructor
@EqualsAndHashCode ( callSuper = true )
public class EntityMovePacket extends Packet {

    private long entityId;

    private float oldX;
    private float oldY;
    private float oldZ;

    private float x;
    private float y;
    private float z;

    private float oldPitch;
    private float oldYaw;
    private float oldHeadYaw;

    private float pitch;
    private float yaw;
    private float headYaw;

    @Override
    public int getPacketId() {
        return 0x6f;
    }

    @Override
    public void write() {
        super.write();
        this.writeUnsignedVarLong( this.entityId );

        short flags = 0;
        if ( this.x != this.oldX ) {
            flags |= 1;
        }

        if ( this.y != this.oldY ) {
            flags |= 2;
        }

        if ( this.z != this.oldZ ) {
            flags |= 4;
        }

        if ( this.pitch != this.oldPitch ) {
            flags |= 8;
        }

        if ( this.headYaw != this.oldHeadYaw ) {
            flags |= 16;
        }

        if ( this.yaw != this.oldYaw ) {
            flags |= 32;
        }

        this.writeLShort( flags );

        if ( this.x != this.oldX ) {
            this.writeLFloat( this.x );
        }

        if ( this.y != this.oldY ) {
            this.writeLFloat( this.y );
        }

        if ( this.z != this.oldZ ) {
            this.writeLFloat( this.z );
        }

        if ( this.pitch != this.oldPitch ) {
            this.writeByte( (byte) (this.pitch / 360f / 256f) );
        }

        if ( this.headYaw != this.oldHeadYaw ) {
            this.writeByte( (byte) (this.headYaw / 360f / 256f) );
        }

        if ( this.yaw != this.oldYaw ) {
            this.writeByte( (byte) (this.yaw / 360f / 256f) );
        }
    }
}
