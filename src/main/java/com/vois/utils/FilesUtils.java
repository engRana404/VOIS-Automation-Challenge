package com.vois.utils;

public class FilesUtils {
    private FilesUtils() {
        // Prevent instantiation
        LogsUtil.error("Trying to instantiate utility class FilesUtils");
        throw new UnsupportedOperationException("Utility class");
    }

    public static void cleanDirectory(String path) {
        java.io.File dir = new java.io.File(path);
        if (dir.exists() && dir.isDirectory()) {
            for (java.io.File file : dir.listFiles()) {
                if (file.isFile()) {
                    if (file.delete()) {
                        LogsUtil.info("Deleted file: " + file.getAbsolutePath());
                    } else {
                        LogsUtil.error("Failed to delete file: " + file.getAbsolutePath());
                    }
                }
            }
        } else {
            LogsUtil.error("Directory does not exist: " + path);
        }
    }
}
