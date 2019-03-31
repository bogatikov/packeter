package my.packeter;


import java.nio.ByteBuffer;

public class IncomingPacket implements Packet {
    private ByteBuffer buffer;

    /***
     * Check the buffer are exist
     */
    private boolean check() {
        return buffer != null;
    }

    public byte readByte() {
        return buffer.get();

    }
    public short readShort( ) {
        return buffer.getShort();
    }
    public int readInt() {
        return buffer.getInt();
    }
    public long readLong() {
        return buffer.getLong();
    }
    public float readFloat() {
        return buffer.getFloat();
    }
    public double readDouble() {
        return buffer.getDouble();
    }
    public char readChar() {
        return buffer.getChar();
    }
    public String readString() {
        StringBuilder stringBuilder = new StringBuilder("");
        char ch;
        while ((ch = readChar()) != 0) {
            stringBuilder.append(ch);
        }
        return stringBuilder.toString();
    }

    @Override
    public ByteBuffer getBuffer() {
        return buffer;
    }

    public void setBuffer(ByteBuffer buffer) {
        this.buffer = buffer;
    }
}
