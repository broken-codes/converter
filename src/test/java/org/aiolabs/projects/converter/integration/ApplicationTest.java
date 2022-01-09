package org.aiolabs.projects.converter.integration;

import org.aiolabs.projects.converter.configurations.ConfigurationProps;
import org.aiolabs.projects.converter.types.impl.csv.CsvTypeReader;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;

import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ApplicationTest {

    @Autowired
    CsvTypeReader reader;

    @Autowired
    ConfigurationProps properties;

    @BeforeEach
    void setUp() throws Exception {
        ConfigurationProps.FilePaths paths = properties.getPaths();
        paths.setOutputFileLocation(FileUtils.getTempDirectoryPath());

        ReflectionTestUtils.setField(properties, "paths", paths);
    }

    @Test
    void testFileContainAllValidRecords() throws Exception {
        reader.read(properties.getPaths().getInputFileName());

        String directoryName = Paths.get(properties.getPaths().getOutputFileLocation())
                .toFile()
                .listFiles()[0]
                .getName();

        int generatedFilesCount = Paths.get(properties.getPaths().getOutputFileLocation(), directoryName)
                .toFile()
                .listFiles()
                .length;

        assertThat(generatedFilesCount).isEqualTo(3);
    }
}
