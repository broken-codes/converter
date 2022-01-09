package org.aiolabs.projects.converter.types.impl.csv;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.aiolabs.projects.converter.beans.Profile;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;

@Component
public class CsvToBeanReader {

    public CsvToBean<Profile> toReader(BufferedReader reader) {
        return new CsvToBeanBuilder<Profile>(reader)
                .withType(Profile.class)
                .withIgnoreEmptyLine(Boolean.TRUE)
                .withIgnoreLeadingWhiteSpace(Boolean.TRUE)
                .build();
    }
}
