package org.aiolabs.projects.converter.processors;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aiolabs.projects.converter.configurations.ConfigurationProps;
import org.aiolabs.projects.converter.types.contracts.ObservableTypeReader;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * This is the application driver. The application start file processing from this place. This class is designed to
 * process one file at a time.
 */
@Component
@Qualifier("singleFileProcessor")
@RequiredArgsConstructor
@Slf4j
public class SingleFileProcessorImpl implements FileProcessor {

    private final ConfigurationProps properties;

    private final ObservableTypeReader reader;

    /**
     * Processes the CSV file and converts each record of CSV to JSON files.
     */
    @Override
    public void process() {
        reader.read(properties.getPaths().getInputFileName());
    }
}

