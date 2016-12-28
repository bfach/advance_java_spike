package com.xyzcorp;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.*;

/**
 * Created by bfach on 10/12/16.
 */
public class FastCopyFile {

    public static void main(String[] args) throws IOException {
        if (args.length < 2) {
            System.out.println("Usage: java FastCopyFile infile output");
            System.exit(-1);
        }

        final String infile = args[0];
        final String outfile = args[1];

        //copyFileNIO1(infile, outfile);

        copyFileNIO2(infile, outfile);

    }

    private static void copyFileNIO2(String infile, String outfile) throws IOException {
        Path inPath = Paths.get(infile);
        Path outPath = Paths.get(outfile);

        CopyOption[] options = new CopyOption[]{
                StandardCopyOption.REPLACE_EXISTING,
                StandardCopyOption.COPY_ATTRIBUTES
        };

        Files.copy(inPath, outPath, options);
    }

    private static void copyFileNIO1(String infile, String outfile) throws IOException {

        //fixes all the resource stuff...
        try (

                FileInputStream fileInputStream = new FileInputStream(infile);
                FileOutputStream fileOutputStream = new FileOutputStream(outfile);

                FileChannel inChannel = fileInputStream.getChannel();
                FileChannel outChannel = fileOutputStream.getChannel();
        ) {
            ByteBuffer buffer = ByteBuffer.allocate(1024);

            while (true) {
                buffer.clear();
                int r = inChannel.read(buffer);

                if (r == -1) {
                    break;
                }

                //prepare the buffer for reading
                buffer.flip();

                //now process the types into the output
                outChannel.write(buffer);

            }
        }
    }
}
