package org.aiolabs.projects.converter.types.contracts;

import org.aiolabs.projects.converter.exceptions.ConverterException;

import java.io.BufferedReader;
import java.io.File;
import java.nio.file.Path;

public interface ConverterIOUtils {

    Path defaultOutputDirectory(String inputFileName, String outputFileLocation, String type);

    boolean createDirectoryIfNotExist(File file);

    BufferedReader reader(String inputFileName) throws ConverterException;
}
