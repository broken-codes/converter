package org.aiolabs.projects.converter.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.aiolabs.projects.converter.beans.Profile;
import org.aiolabs.projects.converter.configurations.ConfigurationProps;
import org.aiolabs.projects.converter.types.impl.csv.CsvTypeReader;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ApplicationTest {

    @Autowired
    CsvTypeReader reader;

    @Autowired
    ConfigurationProps properties;

    ObjectMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new ObjectMapper();
    }

    @AfterEach
    void cleanUp() throws Exception {
        Arrays.stream(Paths.get(properties.getPaths().getOutputFileLocation())
                        .toFile()
                        .listFiles()[0]
                        .listFiles())
                .forEach(it -> it.delete());
    }

    @Test
    void testJsonGeneratedSameAsDataRecordsInCSV() throws Exception {
        reader.read("src/test/resources/input/all_valid_records.csv");

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

    @Test
    void testValidJSONGeneratedForCSV() {
        reader.read("src/test/resources/input/single_valid_record.csv");

        String directoryName = Paths.get(properties.getPaths().getOutputFileLocation())
                .toFile()
                .listFiles()[0]
                .getName();

        int generatedFilesCount = Paths.get(properties.getPaths().getOutputFileLocation(), directoryName)
                .toFile()
                .listFiles()
                .length;

        assertThat(generatedFilesCount).isEqualTo(1);

        List<Profile> profiles = Arrays.stream(Paths.get(properties.getPaths().getOutputFileLocation(), directoryName)
                        .toFile()
                        .listFiles())
                .map(file -> {
                    try {
                        return mapper.readValue(file, Profile.class);
                    } catch (IOException e) {
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        assertThat(profiles.size()).isEqualTo(1);
        assertThat(profiles.get(0).getFirstName()).isEqualTo("firstName-A");
        assertThat(profiles.get(0).getLastName()).isEqualTo("lastName-A");
        assertThat(profiles.get(0).getAddress()).isEqualTo("address-A");
        assertThat(profiles.get(0).getCity()).isEqualTo("city-A");
        assertThat(profiles.get(0).getEmail()).isEqualTo("email-A@test.test");
        assertThat(profiles.get(0).getWeb()).isEqualTo("web");
    }

    @Test
    void testDuplicateEntriesAreMadeDistinct() {
        reader.read("src/test/resources/input/duplicate_valid_record.csv");

        String directoryName = Paths.get(properties.getPaths().getOutputFileLocation())
                .toFile()
                .listFiles()[0]
                .getName();

        int generatedFilesCount = Paths.get(properties.getPaths().getOutputFileLocation(), directoryName)
                .toFile()
                .listFiles()
                .length;

        assertThat(generatedFilesCount).isEqualTo(1);
    }

    @Test
    void testInvalidRecordsAreDiscarded() {
        reader.read("src/test/resources/input/has_invalid_record.csv");

        String directoryName = Paths.get(properties.getPaths().getOutputFileLocation())
                .toFile()
                .listFiles()[0]
                .getName();

        int generatedFilesCount = Paths.get(properties.getPaths().getOutputFileLocation(), directoryName)
                .toFile()
                .listFiles()
                .length;

        assertThat(generatedFilesCount).isEqualTo(1);

        List<Profile> profiles = Arrays.stream(Paths.get(properties.getPaths().getOutputFileLocation(), directoryName)
                        .toFile()
                        .listFiles())
                .map(file -> {
                    try {
                        return mapper.readValue(file, Profile.class);
                    } catch (IOException e) {
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        assertThat(profiles.size()).isEqualTo(1);
        assertThat(profiles.get(0).getFirstName()).isEqualTo("firstName-A");
        assertThat(profiles.get(0).getLastName()).isEqualTo("lastName-A");
        assertThat(profiles.get(0).getAddress()).isEqualTo("address-A");
        assertThat(profiles.get(0).getCity()).isEqualTo("city-A");
        assertThat(profiles.get(0).getEmail()).isEqualTo("email-A@test.test");
        assertThat(profiles.get(0).getWeb()).isEqualTo("web");
    }
}
