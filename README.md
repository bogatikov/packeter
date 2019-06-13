## Packeter

Incoming/outgoing packet handler. Based on ByteBuffer.

Packet represent as |...(2 bytes meaning count of bytes of the packet)...|....(data)....|
The length of packet also include two bytes needed for itself.


### Outgoing packet
To get the "ready to send" outgoing packet: invoke prepareAndGet() method. This method will calculate the packet length, **create new ByteBuffer** with size length + 2. Put at 0 and 1 positions the length of containing data. Will return the ByteBuffer.

### Incoming packet
Just a wrapper for ByteBuffer.

### Example
NIO reciving bytes not determinate time. That means we have no guarantee the ByteBuffer have full packet. This is why we put length to the packet. 
```java
public Packet readPacket(ByteBuffer readBuffer) throws IOException {

        Packet packet = null;

        if (!readBuffer.hasRemaining()) {
            readBuffer.clear();
            return packet;
        }

        // Current position
        int pos = readBuffer.position();

        // If available count of bytes greater then two
        if (readBuffer.remaining() > 2) {

            // Get pending size of packet
            int size = readBuffer.getShort();

            // if size less then two byte
            if (size <= 2) {
                throw new IOException("Incorrect packet size <= 2");
            }



            // If bytes not enough
            if (size <= readBuffer.remaining()) {

                size += 2;

                // Current limit
                int limit = readBuffer.limit();

                //change limit cause we want read only one packet

                readBuffer.position(pos);
                readBuffer.limit(pos + size);
                //copy bytes to new array
                byte [] bytes = new byte[size];
                readBuffer.get(bytes);


                packet = new Packet(ByteBuffer.wrap(decrypt(bytes)));


                //Set the limit back
                readBuffer.limit(limit);

                return packet;
            }
        }
        return packet;
    }
```


Somewhere in Readable key handler method. Before read the buffer invoke clear method by read buffer.
Clear method set the postion on 0 and limit to the buffer capacity. So we don't miss the packet wich was not readed from last iteration. 
```java
...
// Try to read packet and direct to handler
Packet packet;
while ((packet = packetReader.readPacket(readBuffer)) != null) {
    //transfer the packet to the handler
}
...
```
