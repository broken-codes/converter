package org.aiolabs.projects.converter.runners;

import lombok.RequiredArgsConstructor;
import org.aiolabs.projects.converter.processors.FileProcessor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ConversionRunner implements CommandLineRunner {

    private final FileProcessor processor;

    @Override
    public void run(String... args) throws Exception {
        processor.process();
    }
}
