package org.aiolabs.projects.converter.types.impl.csv;

import com.opencsv.bean.CsvToBean;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aiolabs.projects.converter.beans.Profile;
import org.aiolabs.projects.converter.types.contracts.AbstractObservableTypeReader;
import org.aiolabs.projects.converter.types.contracts.ConverterIOUtils;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.util.HashSet;
import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
public class CsvTypeReader extends AbstractObservableTypeReader<Profile> {

    private final CsvToBeanReader beanReader;

    private final ConverterIOUtils ioUtils;

    /**
     * Reads a record of data to {@link Profile} object and publishes it to the observers.
     *
     * @param inputFileName
     */
    @Override
    public void read(String inputFileName) {
        final Set<Integer> readHashes = new HashSet<>();
        int count = 0;

        BufferedReader reader = ioUtils.reader(inputFileName);

        CsvToBean<Profile> converter = beanReader.toReader(reader);

        for (final Profile profile : converter) {
            if (readHashes.contains(profile.hashCode())) {
                continue;
            }
            readHashes.add(profile.hashCode());
            publish(profile, ++count);
        }
    }
}

