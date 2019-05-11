package com.lorenzo.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public class IOUtils {

    public static int getVarIntSize(int val)
    {
        return (val & 0xFFFFFF80) == 0 ? 1 : (val & 0xFFFFC000) == 0 ? 2 : (val & 0xFFE00000) == 0 ? 3 : (val & 0xF0000000) == 0 ? 4 : 5;
    }

    public static void putVarInt(byte[] buf, int pos, int val)
    {
        while ((val & ~0x7F) != 0) {
            buf[pos++] = (byte)((val & 0x7F) | 0x80);
            val >>>= 7;
        }
        buf[pos++] = (byte)val;
    }

    public static byte[] deflate(byte[] input) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream(input.length);
        byte[] buf = new byte[4096];

        Deflater def = new Deflater(9);
        def.setInput(input);
        def.finish();

        while (!def.finished()) {
            int bytes = def.deflate(buf);
            baos.write(buf, 0, bytes);
        }
        def.end();

        return baos.toByteArray();
    }
    public static byte[] inflate(byte[] input) throws DataFormatException {
        Inflater inf = new Inflater();
        inf.setInput(input, 0, input.length);

        ByteArrayOutputStream baos = new ByteArrayOutputStream(input.length);
        byte[] buf = new byte[4096];

        while (!inf.finished()) {
            int bytes = inf.inflate(buf);
            baos.write(buf, 0, bytes);
        }
        inf.end();

        return baos.toByteArray();
    }

    public static int readVarInt(InputStream is) throws IOException
    {
        int result = 0;
        int shift = 0;
        byte b;

        do {
            b = (byte)is.read();
            result |= (b & 0x7F) << shift++ * 7;

            if (shift > 5)
                throw new IllegalStateException("VarInt is too big");
        } while ((b & 0x80) != 0);

        return result;
    }

}
