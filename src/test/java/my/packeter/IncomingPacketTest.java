package my.packeter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.ByteBuffer;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

    class IncomingPacketTest {
        ByteBuffer buffer;

    @BeforeEach
    void setup() {
        buffer = ByteBuffer.allocate(Packet.INCOMING_BUFFER_SIZE);
    }

    @Test
    void checkSize() {
        OutgoingPacket packet = new OutgoingPacket();
        packet.setBuffer(buffer);

        int expectedSize = 150;

        Random random = new Random();
        byte [] bytes = new byte[expectedSize];
        random.nextBytes(bytes);

        packet.write(bytes);

        IncomingPacket incomingPacket = new IncomingPacket();
        ByteBuffer incomingBuffer = packet.prepareAndGet();
        incomingPacket.setBuffer(incomingBuffer);

        int actualSize = incomingPacket.readShort();

        assertEquals(expectedSize, actualSize);
    }

    @Test
    void readByte() {
        OutgoingPacket packet = new OutgoingPacket();
        packet.setBuffer(buffer);
        packet.write(Byte.MAX_VALUE);

        IncomingPacket incomingPacket = new IncomingPacket();
        ByteBuffer incomingBuffer = packet.prepareAndGet();
        incomingPacket.setBuffer(incomingBuffer);
        int size = incomingPacket.readShort();

        assertEquals(Byte.MAX_VALUE, incomingPacket.readByte());
    }

    @Test
    void readShort() {
        OutgoingPacket packet = new OutgoingPacket();
        packet.setBuffer(buffer);
        packet.write(Short.MAX_VALUE);

        IncomingPacket incomingPacket = new IncomingPacket();
        ByteBuffer incomingBuffer = packet.prepareAndGet();
        incomingPacket.setBuffer(incomingBuffer);
        int size = incomingPacket.readShort();

        assertEquals(Short.MAX_VALUE, incomingPacket.readShort());
    }

    @Test
    void readInt() {
        OutgoingPacket packet = new OutgoingPacket();
        packet.setBuffer(buffer);
        packet.write(Integer.MAX_VALUE);

        IncomingPacket incomingPacket = new IncomingPacket();
        ByteBuffer incomingBuffer = packet.prepareAndGet();
        incomingPacket.setBuffer(incomingBuffer);
        int size = incomingPacket.readShort();

        assertEquals(Integer.MAX_VALUE, incomingPacket.readInt());
    }

    @Test
    void readLong() {
        OutgoingPacket packet = new OutgoingPacket();
        packet.setBuffer(buffer);
        packet.write(Long.MAX_VALUE);

        IncomingPacket incomingPacket = new IncomingPacket();
        ByteBuffer incomingBuffer = packet.prepareAndGet();
        incomingPacket.setBuffer(incomingBuffer);
        int size = incomingPacket.readShort();

        assertEquals(Long.MAX_VALUE, incomingPacket.readLong());
    }

    @Test
    void readFloat() {
        OutgoingPacket packet = new OutgoingPacket();
        packet.setBuffer(buffer);
        packet.write(Float.MAX_VALUE);

        IncomingPacket incomingPacket = new IncomingPacket();
        ByteBuffer incomingBuffer = packet.prepareAndGet();
        incomingPacket.setBuffer(incomingBuffer);
        int size = incomingPacket.readShort();

        assertEquals(Float.MAX_VALUE, incomingPacket.readFloat());
    }

    @Test
    void readDouble() {
        OutgoingPacket packet = new OutgoingPacket();
        packet.setBuffer(buffer);
        packet.write(Double.MAX_VALUE);

        IncomingPacket incomingPacket = new IncomingPacket();
        ByteBuffer incomingBuffer = packet.prepareAndGet();
        incomingPacket.setBuffer(incomingBuffer);
        int size = incomingPacket.readShort();

        assertEquals(Double.MAX_VALUE, incomingPacket.readDouble());
    }

    @Test
    void readChar() {
        OutgoingPacket packet = new OutgoingPacket();
        packet.setBuffer(buffer);
        packet.write(Character.MAX_VALUE);

        IncomingPacket incomingPacket = new IncomingPacket();
        ByteBuffer incomingBuffer = packet.prepareAndGet();
        incomingPacket.setBuffer(incomingBuffer);
        int size = incomingPacket.readShort();

        assertEquals(Character.MAX_VALUE, incomingPacket.readChar());
    }

    @Test
    void readString() {
        OutgoingPacket packet = new OutgoingPacket();
        packet.setBuffer(buffer);
        packet.write("Hello, packeter!");

        IncomingPacket incomingPacket = new IncomingPacket();
        ByteBuffer incomingBuffer = packet.prepareAndGet();
        incomingPacket.setBuffer(incomingBuffer);
        int size = incomingPacket.readShort();

        assertEquals("Hello, packeter!", incomingPacket.readString());
    }
}