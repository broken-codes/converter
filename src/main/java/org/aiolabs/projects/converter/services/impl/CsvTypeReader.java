package org.aiolabs.projects.converter.services.impl;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.extern.slf4j.Slf4j;
import org.aiolabs.projects.converter.beans.Profile;
import org.aiolabs.projects.converter.exceptions.ConverterException;
import org.aiolabs.projects.converter.services.contracts.AbstractObservableTypeReader;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@Service
public class CsvTypeReader extends AbstractObservableTypeReader<Profile> {

    /**
     * Reads a record of data to {@link Profile} object and publishes it to the observers.
     *
     * @param inputFileName
     */
    @Override
    public void read(String inputFileName) {
        BufferedReader reader = null;
        final Set<Integer> readHashes = new HashSet<>();
        int count = 0;

        try {
            reader = Files.newBufferedReader(Paths.get(inputFileName));
        } catch (IOException e) {
            throw new ConverterException(String.format("Error reading file: %s", inputFileName));
        }

        CsvToBean<Profile> converter = new CsvToBeanBuilder<Profile>(reader)
                .withType(Profile.class)
                .withIgnoreEmptyLine(Boolean.TRUE)
                .withIgnoreLeadingWhiteSpace(Boolean.TRUE)
                .build();

        for (final Profile profile : converter) {
            if (readHashes.contains(profile.hashCode())) {
                // Avoid duplicates. I guess using MD5 on toString() should have been a better solution but this
                // works for now. Profile relies on hashCode() implementation of toString()
                continue;
            }
            readHashes.add(profile.hashCode());
            publish(profile, ++count);
        }
    }
}
