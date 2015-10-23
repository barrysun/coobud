package com.coobud.agent.utils;

import com.google.common.base.Preconditions;
import java.io.File;
import java.io.IOException;

// copied from Google Guava as these methods are now deprecated
// NOTE: removed the line of code documented: Symbolic links will have different canonical and absolute paths
public class DirectoryUtils
{
    public static void deleteRecursively(File file) throws IOException
    {
        if (file.isDirectory()) {
            deleteDirectoryContents(file);
        }
        if (!file.delete()) {
            throw new IOException("Failed to delete " + file);
        }
    }

    public static void deleteDirectoryContents(File directory)
        throws IOException {
        Preconditions.checkArgument(directory.isDirectory(),
            "Not a directory: %s", directory);
        File[] files = directory.listFiles();
        if (files == null) {
            throw new IOException("Error listing files for " + directory);
        }
        for (File file : files) {
            deleteRecursively(file);
        }
    }
}
