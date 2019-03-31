import my.packeter.packets.incoming.HelloPacket;
import my.packeter.packets.outgoing.WelcomePacket;

import java.nio.ByteBuffer;

public class Bootstrap {
    public static void main(String[] args) {
        WelcomePacket welcomePacket = new WelcomePacket();
        welcomePacket.write((short) 0x01);
        welcomePacket.write("Hello, packeter!");
        welcomePacket.write("Hello, packeter!");
        welcomePacket.write("Hello, packeter!");


        ByteBuffer buffer = welcomePacket.prepareAndGet();

        if (tryToReadPacket(buffer)) {
            int pos = buffer.position();
            int size = buffer.getShort();

            System.out.println("Expected size " + size);

            int id = buffer.getShort();
            buffer.position(pos);
            switch (id) {
                case 0x01:
                    HelloPacket helloPacket = new HelloPacket();
                    helloPacket.setBuffer(buffer);
                    System.out.println("Packet size: " + helloPacket.readShort());
                    System.out.println("ID: " + helloPacket.readShort());
                    System.out.println("Text: " + helloPacket.readString());
                    System.out.println("Text: " + helloPacket.readString());
                    System.out.println("Text: " + helloPacket.readString());
                    break;


            }
        }
    }

    private static boolean tryToReadPacket(ByteBuffer buffer) {
        int pos = buffer.position();
        int limit = buffer.limit();

        int size = buffer.getShort();

        if (size > buffer.remaining()) {
            return false;
        }

        buffer.position(pos);
        return true;
    }
}
