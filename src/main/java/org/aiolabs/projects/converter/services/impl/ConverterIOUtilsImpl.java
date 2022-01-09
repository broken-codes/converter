package org.aiolabs.projects.converter.services.impl;

import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Locale;

@Component
public class ConverterIOUtilsImpl {

    public static final String LEAF_DIR_FORMAT = "%s-%s";

    /**
     * Given input file name and the output directory location, this method returns the {@link Path} of the directory
     * where the generated files should be placed.
     * For example, If the input file name is <code>/a/b/in/xyz.csv</code> and the output directory is
     * <code>/a/b/out</code> the method generates
     *
     * @param inputFileName
     * @param outputFileLocation
     * @return
     */
    public Path defaultOutputDirectory(String inputFileName, String outputFileLocation, String type) {
        String leafDirectory = FilenameUtils.removeExtension(FilenameUtils.getName(inputFileName));
        return Paths.get(outputFileLocation, String.format(LEAF_DIR_FORMAT,
                leafDirectory.toUpperCase(Locale.ROOT),
                type));
    }

    /**
     * Given the {@link File}, the method creates this {@link File} if it does not already exist.
     *
     * @param file
     * @return true if directory was created, false otherwise
     */
    public boolean createDirectoryIfNotExist(File file) {
        if (file.exists()) {
            return false;
        }
        return file.mkdir();
    }

}
