package com.fms.core.file;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileUtil {

    @SuppressWarnings({"IOResourceOpenedButNotSafelyClosed", "ChannelOpenedButNotSafelyClosed"})
    public static void write(final InputStream stream)
        throws IOException {
//        final FileChannel outChannel = new FileOutputStream("D://time.txt").getChannel();
//        final ReadableByteChannel inChannel = Channels.newChannel(stream);
//        final ByteBuffer buffer = ByteBuffer.allocate(1024);
//        while (inChannel.read(buffer) >= 0 || buffer.position() > 0)
//        {
//            buffer.flip();
//            outChannel.write(buffer);
//            buffer.compact();
//        }
//        inChannel.close();
//        outChannel.close();



        Files.copy(stream, Paths.get("D://test.pdf"));

    }
}
