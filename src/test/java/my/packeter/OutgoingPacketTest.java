package my.packeter;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

import java.nio.BufferOverflowException;
import java.nio.ByteBuffer;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;


class OutgoingPacketTest {

    private ByteBuffer byteBuffer;
    private final AtomicInteger counter = new AtomicInteger(1);
    @BeforeEach
    void setup() {
        byteBuffer = ByteBuffer.allocate(1024);
    }

    @Test
    @DisplayName("Output buffer have two more bytes then was written")
    void prepareAndGet_size_are_written_bytes_plus_two() {

        OutgoingPacket outgoingPacket = new OutgoingPacket();
        outgoingPacket.setBuffer(byteBuffer);
        int b = 10;
        byte [] bytes = new byte[b];
        for (int i = 0; i < b; i++) {
            bytes[i] = (byte) i;
        }
        outgoingPacket.write(bytes);

        int expected = b + 2;
        int actual = outgoingPacket.prepareAndGet().limit();
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Bytes on positions 0 and 1 are size of packet")
    void prepareAndGet_check_two_bytes_on_start() {
        OutgoingPacket outgoingPacket = new OutgoingPacket();
        outgoingPacket.setBuffer(byteBuffer);
        int b = 10;
        byte [] bytes = new byte[b];
        for (int i = 0; i < b; i++) {
            bytes[i] = (byte) i;
        }
        outgoingPacket.write(bytes);


        ByteBuffer buffer = outgoingPacket.prepareAndGet();
        int actual = buffer.getShort();
        assertEquals(b, actual);
    }

    @TestFactory
    @DisplayName("Not enough capacity to put size")
    Stream<DynamicTest> dynamicTestPrepareAngGet_not_enough_capacity_to_put_size() {
        return Stream.of(Packet.OUTGOING_BUFFER_SIZE - 1, Packet.OUTGOING_BUFFER_SIZE)
                .mapToInt(k -> k)
                .mapToObj(v ->
                        DynamicTest.dynamicTest("Not enough capacity " + v, () -> {
                    ByteBuffer buf = ByteBuffer.allocate(Packet.OUTGOING_BUFFER_SIZE);
                    OutgoingPacket outgoingPacket = new OutgoingPacket();
                    outgoingPacket.setBuffer(buf);

                    byte [] bytes = new byte[v];
                    for (int i = 0; i < v; i++) {
                        bytes[i] = Byte.MAX_VALUE;
                    }
                    outgoingPacket.write(bytes);

                    assertThrows(
                            BufferOverflowException.class,
                            outgoingPacket::prepareAndGet
                    );
                }));
    }

    @TestFactory
    Stream<DynamicTest> dynamicTestsFromStream() {
        return Stream.generate(counter::getAndIncrement)
                .limit(OutgoingPacket.OUTGOING_BUFFER_SIZE-2)
                .mapToInt(v -> v)
                .mapToObj(b -> DynamicTest.dynamicTest("Test for " + b, () -> {

                    ByteBuffer buf = ByteBuffer.allocate(Packet.OUTGOING_BUFFER_SIZE);
                    OutgoingPacket outgoingPacket = new OutgoingPacket();
                    outgoingPacket.setBuffer(buf);

                    byte [] bytes = new byte[b];
                    for (int i = 0; i < b; i++) {
                        bytes[i] = Byte.MAX_VALUE;
                    }
                    outgoingPacket.write(bytes);


                    ByteBuffer buffer = outgoingPacket.prepareAndGet();
                    int actual = buffer.getShort();
                    assertEquals(b, actual);
                }));
    }
}