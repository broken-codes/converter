package org.aiolabs.projects.converter.types.impl.csv;

import com.opencsv.bean.CsvToBean;
import org.aiolabs.projects.converter.beans.Profile;
import org.aiolabs.projects.converter.configurations.ConfigurationProps;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

class CsvToBeanReaderTest {

    @InjectMocks
    CsvToBeanReader sut;

    @Mock
    ConfigurationProps properties;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void toReader() throws Exception {
        CsvToBean<Profile> result = sut.toReader(Files.newBufferedReader(Paths.get("src/test/resources/input/test")));
        assertThat(result).isNotNull();
        assertThat(result).isInstanceOf(CsvToBean.class);
    }
}