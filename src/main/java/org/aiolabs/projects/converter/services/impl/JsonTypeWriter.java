package org.aiolabs.projects.converter.services.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aiolabs.projects.converter.annotations.Observer;
import org.aiolabs.projects.converter.beans.Profile;
import org.aiolabs.projects.converter.configurations.ConfigurationProps;
import org.aiolabs.projects.converter.exceptions.ConverterException;
import org.aiolabs.projects.converter.services.contracts.ObservingTypeWriter;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

@Observer(format = JsonTypeWriter.FORMAT)
@Slf4j
@RequiredArgsConstructor
public class JsonTypeWriter implements ObservingTypeWriter<Profile> {

    private static final String JSON_FILE_NAME_FORMAT = "%s-%s.json";

    public static final String FORMAT = "JSON";

    private final ConfigurationProps properties;

    private final ConverterIOUtilsImpl ioUtils;

    private final ExecutorService executorService;

    private ObjectMapper objectMapper;

    @PostConstruct
    public void postConstruct() {

        objectMapper = new ObjectMapper();

        Path outputPath = getOutputPath();
        File outputLocationFile = outputPath.toFile();
        ioUtils.createDirectoryIfNotExist(outputLocationFile);
    }

    @Override
    public CompletableFuture<Void> write(Profile profile, int dataPositionInFile) {
        String jsonFileName = String.format(JSON_FILE_NAME_FORMAT, dataPositionInFile, System.nanoTime());
        Path path = Paths.get(getOutputPath().toString(), jsonFileName);
        return CompletableFuture.runAsync(() -> task(profile, path), executorService);
    }

    private void task(Profile profile, Path path) {
        try {
            objectMapper.writerWithDefaultPrettyPrinter()
                    .writeValue(path.toFile(), profile);
        } catch (IOException e) {
            throw new ConverterException(String.format("Error writing profile: %s to file: %s", profile, path), e);
        }
    }

    private Path getOutputPath() {
        return ioUtils.defaultOutputDirectory(properties.getPaths().getInputFileName(),
                properties.getPaths().getOutputFileLocation(),
                FORMAT);
    }

}
