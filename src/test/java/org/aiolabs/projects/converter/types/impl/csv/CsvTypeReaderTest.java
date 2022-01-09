package org.aiolabs.projects.converter.types.impl.csv;

import com.opencsv.bean.CsvToBean;
import org.aiolabs.projects.converter.beans.Profile;
import org.aiolabs.projects.converter.types.contracts.AbstractObservableTypeReader;
import org.aiolabs.projects.converter.types.contracts.ConverterIOUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.BufferedReader;
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CsvTypeReaderTest {

    @InjectMocks
    CsvTypeReader sut;

    @Mock
    CsvToBeanReader beanReader;

    @Mock
    ConverterIOUtils ioUtils;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void read() {
        TestableCsvTypeReader mockedSut = mock(TestableCsvTypeReader.class);
        BufferedReader mockedReader = mock(BufferedReader.class);
        CsvToBean mockedConverter = mock(CsvToBean.class);
        Profile profile = Profile.builder()
                .firstName("firstName")
                .lastName("lastName")
                .address("address")
                .city("city")
                .companyName("companyName")
                .email("email")
                .phone1("phone1")
                .phone2("phone2")
                .state("state")
                .zip("zip")
                .web("web")
                .build();
        when(ioUtils.reader(anyString())).thenReturn(mockedReader);
        when(beanReader.toReader(mockedReader)).thenReturn(mockedConverter);
        when(mockedConverter.iterator()).thenReturn(Arrays.asList(profile).iterator());
        doNothing().when(mockedSut).publish(any(Profile.class), anyInt());

        doCallRealMethod().when(mockedSut).read("test.csv");
        mockedSut.read("test.csv");

        verify(mockedSut).publish(any(Profile.class), anyInt());
        verify(ioUtils).reader(anyString());
        verify(beanReader).toReader(any(BufferedReader.class));
    }

    /*
     * Testing protected methods is a general cause of anxiety among developers.
     */
    class TestableCsvTypeReader extends AbstractObservableTypeReader {

        @Override
        public void read(String inputFileName) {
            sut.read(inputFileName);
        }

        @Override
        public void publish(Object data, int dataPositionInFile) {

        }
    }
}