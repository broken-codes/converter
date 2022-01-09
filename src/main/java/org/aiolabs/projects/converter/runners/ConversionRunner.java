package org.aiolabs.projects.converter.runners;

import lombok.RequiredArgsConstructor;
import org.aiolabs.projects.converter.processors.FileProcessor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@ConditionalOnProperty(
        prefix = "application.runner",
        value = "enabled",
        havingValue = "true",
        matchIfMissing = true
)
@Component
@RequiredArgsConstructor
public class ConversionRunner implements CommandLineRunner {

    private final FileProcessor processor;

    @Override
    public void run(String... args) throws Exception {
        processor.process();
    }
}
