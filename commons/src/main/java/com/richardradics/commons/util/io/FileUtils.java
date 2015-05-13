package com.richardradics.commons.util.io;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;


/**
 * Utility for the {@link java.io.File}.
 */
@SuppressWarnings("unused") // public APIs
public final class FileUtils {
    private FileUtils() {}

    /**
     * Create a new directory if not exists.
     * @param dir to create.
     * @return true if already exists, or newly created.
     */
    public static boolean makeDirsIfNeeded(File dir) {
        return dir.exists() || dir.mkdirs();
    }

    /**
     * Copy the file from the source to the destination.
     * @param source the source file to be copied.
     * @param destination the destination file to copy.
     * @see java.nio.channels.FileChannel#transferTo(long, long, java.nio.channels.WritableByteChannel).
     * @return the transferred byte count.
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static long copy(File source, File destination) throws FileNotFoundException, IOException {
        FileInputStream in = null;
        FileOutputStream out = null;
        try {
            in = new FileInputStream(source);
            out = new FileOutputStream(destination);
            return copy(in, out);
        } finally {
            CloseableUtils.close(in);
            CloseableUtils.close(out);
        }
    }

    /**
     * Copy the file using streams.
     * @param in source.
     * @param out destination.
     * @see java.nio.channels.FileChannel#transferTo(long, long, java.nio.channels.WritableByteChannel)
     * @return the transferred byte count.
     * @throws IOException
     */
    public static long copy(FileInputStream in, FileOutputStream out) throws IOException {
        FileChannel source = null;
        FileChannel destination = null;
        try {
            source = in.getChannel();
            destination = out.getChannel();
            return source.transferTo(0, source.size(), destination);
        } finally {
            CloseableUtils.close(source);
            CloseableUtils.close(destination);
        }
    }
}