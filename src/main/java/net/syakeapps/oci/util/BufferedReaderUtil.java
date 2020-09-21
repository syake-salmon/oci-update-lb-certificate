package net.syakeapps.oci.util;

import java.io.BufferedReader;
import java.util.stream.Collectors;

/**
 * Utilities for BufferedReader.
 *
 */
public class BufferedReaderUtil {

    /**
     * Convert BufferedReader to String.
     * 
     * @param reader source
     * @return converted String
     */
    public static String bufferedReaderToString(BufferedReader reader) {
        return reader.lines().collect(Collectors.joining(System.getProperty("line.separator")));
    }
}
