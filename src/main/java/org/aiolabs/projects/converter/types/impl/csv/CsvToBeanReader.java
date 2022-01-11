package org.aiolabs.projects.converter.types.impl.csv;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.RequiredArgsConstructor;
import org.aiolabs.projects.converter.beans.Profile;
import org.aiolabs.projects.converter.configurations.ConfigurationProps;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class CsvToBeanReader {

    private static final char SEP = ',';

    private final ConfigurationProps properties;

    public CsvToBean<Profile> toReader(BufferedReader reader) {
        return new CsvToBeanBuilder<Profile>(reader)
                .withType(Profile.class)
                .withSeparator(Objects.isNull(properties.getSeparator()) ? SEP : properties.getSeparator().charAt(0))
                .withIgnoreEmptyLine(Boolean.TRUE)
                .withIgnoreLeadingWhiteSpace(Boolean.TRUE)
                .build();
    }
}
