package org.aiolabs.projects.converter.types.impl.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.aiolabs.projects.converter.beans.Profile;
import org.aiolabs.projects.converter.configurations.ConfigurationProps;
import org.aiolabs.projects.converter.exceptions.ConverterException;
import org.aiolabs.projects.converter.types.impl.ConverterIOUtilsImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.File;
import java.nio.file.Paths;
import java.util.concurrent.ExecutorService;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class JsonTypeWriterTest {

    @InjectMocks
    JsonTypeWriter sut;

    @Mock
    ConfigurationProps properties;

    @Mock
    ConverterIOUtilsImpl ioUtils;

    @Mock
    ExecutorService executorService;

    ObjectMapper mapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mapper = mock(ObjectMapper.class);
        ReflectionTestUtils.setField(sut, "objectMapper", mapper);
    }

    @Test
    void postConstruct() {
        ConfigurationProps.FilePaths mockedFilePaths = mock(ConfigurationProps.FilePaths.class);
        when(properties.getPaths()).thenReturn(mockedFilePaths);
        when(mockedFilePaths.getInputFileName()).thenReturn("/a/a");
        when(mockedFilePaths.getOutputFileLocation()).thenReturn("p/q");
        when(ioUtils.defaultOutputDirectory(anyString(), anyString(), anyString()))
                .thenReturn(Paths.get("/src/test/resources/"));
        when(ioUtils.createDirectoryIfNotExist(any(File.class))).thenReturn(Boolean.TRUE);
        sut.postConstruct();

        verify(ioUtils).defaultOutputDirectory(anyString(), anyString(), anyString());
        verify(ioUtils).createDirectoryIfNotExist(any(File.class));
    }

    @Test
    @Disabled
    void write() throws Exception {

        ConfigurationProps.FilePaths mockedFilePaths = mock(ConfigurationProps.FilePaths.class);
        when(properties.getPaths()).thenReturn(mockedFilePaths);
        when(mockedFilePaths.getInputFileName()).thenReturn("/a/a");
        when(mockedFilePaths.getOutputFileLocation()).thenReturn("p/q");
        when(ioUtils.defaultOutputDirectory(anyString(), anyString(), anyString()))
                .thenReturn(Paths.get("/src/test/resources/"));

        ObjectWriter writer = mock(ObjectWriter.class);
        when(mapper.writerWithDefaultPrettyPrinter())
                .thenReturn(writer)
                .thenThrow(RuntimeException.class);
        doNothing().when(writer).writeValue(any(File.class), any(Profile.class));

        sut.write(new Profile(), 10).thenAccept(i -> {
        });
        verify(mapper).writerWithDefaultPrettyPrinter();

        assertThrows(ConverterException.class, () -> sut.write(new Profile(), 10)
                .thenAccept(i -> {
                }));
        verify(mapper, times(2)).writerWithDefaultPrettyPrinter();
    }
}