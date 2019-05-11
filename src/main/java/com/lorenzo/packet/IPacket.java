package com.lorenzo.packet;

import com.lorenzo.utils.PacketBufferIn;
import com.lorenzo.utils.PacketBufferOut;

public interface IPacket {

    void write(PacketBufferOut packetBufferOut) throws Exception;
    void read(PacketBufferIn packetBufferIn) throws Exception;

}
