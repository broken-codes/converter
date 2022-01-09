package org.aiolabs.projects.converter.processors;

import org.aiolabs.projects.converter.configurations.ConfigurationProps;
import org.aiolabs.projects.converter.types.contracts.ObservableTypeReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

class SingleFileProcessorImplTest {

    @InjectMocks
    SingleFileProcessorImpl sut;

    @Mock
    ConfigurationProps properties;

    @Mock
    ObservableTypeReader reader;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void process() {
        ConfigurationProps.FilePaths mockedFilePaths = mock(ConfigurationProps.FilePaths.class);
        String fileName = "/some/file/path/test.csv";
        when(mockedFilePaths.getInputFileName()).thenReturn(fileName);
        when(properties.getPaths()).thenReturn(mockedFilePaths);
        doNothing().when(reader).read(fileName);

        sut.process();
        verify(properties, times(1)).getPaths();
        verify(reader, times(1)).read(eq(fileName));
    }
}