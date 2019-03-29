package my.packeter;

import java.nio.ByteBuffer;

public interface Packet {
    public static final int INCOMING_BUFFER_SIZE = 1024;

    // Not great then short type capacity
    public static final int OUTGOING_BUFFER_SIZE = 1024;

    public ByteBuffer getBuffer();
    public void setBuffer(ByteBuffer buffer);
}
