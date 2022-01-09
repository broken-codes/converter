package org.aiolabs.projects.converter.configurations;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Set;

@Data
@Component
@ConfigurationProperties(prefix = "application")
public class ConfigurationProps {

    /**
     * Provides input and output locations.
     *
     * @see FilePaths
     */
    private FilePaths paths;

    /**
     * Separator that should be used to split the CSV file columns.
     */
    private String separator;

    /**
     * Set of {@link org.aiolabs.projects.converter.annotations.Observer} that should be loaded for conversion.
     */
    private Set<String> outputFormats;

    @Data
    public static class FilePaths {

        /**
         * Name of the CSV file with complete path.
         */
        private String inputFileName;

        /**
         * Name of the directory where the JSON files would be generated. The application will create a directory within
         * this directory with name same as the file name without file extension.
         */
        private String outputFileLocation;
    }
}
