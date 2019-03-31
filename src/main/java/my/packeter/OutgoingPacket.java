package my.packeter;

import java.nio.BufferOverflowException;
import java.nio.ByteBuffer;

/**
 * Packet will have the next view: SIZE[2 bytes] TYPE DATA
 * Before send the packet need to put to the first and the second position
 * the size of buffer. It`s mean that byte on position 0 and byte on position 1
 * indicate how many bytes need to read the packet successful
 */
public class OutgoingPacket implements Packet {

    protected ByteBuffer buffer;


    /***
     * Check the buffer are exist
     */
    private void check() {
        if (buffer == null) {
            buffer = ByteBuffer.allocate(Packet.OUTGOING_BUFFER_SIZE);
        }
    }

    public void write(byte val) {
        check();

        buffer.put(val);
    }
    public void write(short val) {
        check();

        buffer.putShort(val);
    }
    public void write(int val) {
        check();

        buffer.putInt(val);
    }
    public void write(long val) {
        check();

        buffer.putLong(val);
    }
    public void write(float val) {
        check();

        buffer.putFloat(val);
    }
    public void write(double val) {
        check();

        buffer.putDouble(val);
    }
    public void write(char ch) {
        check();

        buffer.putChar(ch);
    }
    public void write(byte [] val) {
        check();

        buffer.put(val);
    }
    public void write(String str) {
        check();

        if (str == null || str.length() == 0)
            return;

        for (char ch:
             str.toCharArray()) {
            buffer.putChar(ch);
        }
        buffer.putChar('\000');
    }

    public ByteBuffer getBuffer() {
        return buffer;
    }

    public void setBuffer(ByteBuffer buffer) {
        this.buffer = buffer;
    }

    /**
     * After we wrote all data need to calculate the packet size
     * and put it at 0 position and 1 position
     */
    public ByteBuffer prepareAndGet() {

        // Rest limit to position
        buffer.flip();

        int limit = buffer.limit();

        if (limit+2 > OUTGOING_BUFFER_SIZE) {
            //System.out.println("Outgoing packet capacity not enough to put the packet size");
            // TODO: create
            throw new BufferOverflowException();
        }

        byte [] bytes = new byte[limit];
        buffer.get(bytes);

        // Create new buffer
        ByteBuffer out = ByteBuffer.allocate(limit+2);

        out.putShort((short) limit);
        out.put(bytes);

        out.flip();

        return out;
    }
}
