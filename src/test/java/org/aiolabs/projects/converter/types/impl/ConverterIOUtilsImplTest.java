package org.aiolabs.projects.converter.types.impl;

import org.aiolabs.projects.converter.exceptions.ConverterException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ConverterIOUtilsImplTest {

    ConverterIOUtilsImpl sut;

    @BeforeEach
    void setUp() {
        sut = new ConverterIOUtilsImpl();
        Path existingDirectoryPath = Paths.get("src/test/resources/test_dir_existing");
        existingDirectoryPath.toFile().mkdir();
    }

    @Test
    void defaultOutputDirectory() {
        String inputFileName = "/a/b/in/xyz.csv";
        String outputFilePath = "/a/b/out";
        String type = "XML";
        Path result = sut.defaultOutputDirectory(inputFileName, outputFilePath, type);
        assertThat(result).isNotNull();
        assertThat(result.toString()).isEqualTo("/a/b/out/XYZ-XML");
    }

    @Test
    void createDirectoryIfNotExist() {
        Path existingDirectoryPath = Paths.get("src/test/resources/test_dir_existing");
        boolean resultDirectoryExists = sut.createDirectoryIfNotExist(existingDirectoryPath.toFile());
        assertThat(resultDirectoryExists).isFalse();

        File nonExistingDirectoryFile = Paths.get("src/test/resources/test_dir_not_existing").toFile();
        boolean resultDirectoryDoesNotExists = sut.createDirectoryIfNotExist(nonExistingDirectoryFile);
        assertThat(resultDirectoryDoesNotExists).isTrue();
    }

    @AfterEach
    public void destroy() {
        Path existingDirectoryPath = Paths.get("src/test/resources/test_dir_existing");
        existingDirectoryPath.toFile().delete();

        File nonExistingDirectoryFile = Paths.get("src/test/resources/test_dir_not_existing").toFile();
        nonExistingDirectoryFile.delete();
    }

    @Test
    void reader() {
        assertThrows(ConverterException.class, () -> sut.reader("*"));
    }
}