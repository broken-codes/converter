package org.aiolabs.projects.converter.types.impl.csv;

import com.opencsv.bean.CsvToBean;
import org.aiolabs.projects.converter.beans.Profile;
import org.aiolabs.projects.converter.types.contracts.ConverterIOUtils;
import org.aiolabs.projects.converter.types.impl.ConverterIOUtilsImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class CsvTypeReaderTest {

    CsvToBeanReader beanReader;

    ConverterIOUtilsImpl ioUtils;

    LocalSUT sut;

    @BeforeEach
    void setUp() {
        beanReader = mock(CsvToBeanReader.class);
        ioUtils = mock(ConverterIOUtilsImpl.class);

        sut = new LocalSUT(beanReader, ioUtils);
    }

    @Test
    void read() {
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
        when(mockedConverter.iterator())
                .thenReturn(Arrays.asList(profile).iterator(), Arrays.asList(profile, profile).iterator());

        sut.read("test.csv");
        verify(ioUtils).reader(anyString());
        verify(beanReader).toReader(any(BufferedReader.class));

        sut.read("test.csv");
        verify(ioUtils, times(2)).reader(anyString());
        verify(beanReader, times(2)).toReader(any(BufferedReader.class));

    }

    class LocalSUT extends CsvTypeReader {

        public LocalSUT(CsvToBeanReader beanReader, ConverterIOUtils ioUtils) {
            super(beanReader, ioUtils);
        }

        @Override
        public void read(String inputFileName) {
            super.read(inputFileName);
        }

        @Override
        public void publish(Profile data, int dataPositionInFile) {

        }
    }
}