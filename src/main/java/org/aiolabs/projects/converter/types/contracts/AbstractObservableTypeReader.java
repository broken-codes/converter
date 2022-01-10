package org.aiolabs.projects.converter.types.contracts;

import lombok.extern.slf4j.Slf4j;
import org.aiolabs.projects.converter.annotations.Observer;
import org.aiolabs.projects.converter.configurations.ConfigurationProps;
import org.aiolabs.projects.converter.exceptions.ConverterException;
import org.aiolabs.projects.converter.validations.BeanValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Slf4j
public abstract class AbstractObservableTypeReader<T> implements ObservableTypeReader<T> {

    public static final String ERROR_NO_WRITER_CONFIGURED = "No writers could be loaded as the configuration file does " +
            "not provide any writer. You should check the configuration file and add at least one writer using " +
            "property: application.output-formats";

    private BeanValidator<T> validator;

    private ApplicationContext context;

    private ConfigurationProps properties;

    private List<ObservingTypeWriter<T>> observers;

    @PostConstruct
    public void postConstruct() {
        observers = context.getBeansWithAnnotation(Observer.class).values().stream()
                .filter(it -> it.getClass().getAnnotation(Observer.class).enabled())
                .filter(it -> properties.getOutputFormats().contains(it.getClass().getAnnotation(Observer.class).format()))
                .filter(it -> it instanceof ObservingTypeWriter)
                .map(it -> ((ObservingTypeWriter<T>) it))
                .peek(it -> log.debug("Registered observer: {}", it.getClass().getAnnotation(Observer.class).format()))
                .collect(Collectors.toList());
        log.debug("Number of enabled observers found: {}", observers.size());
    }

    /**
     * When the data is ready to be written to the file, publish the data over by calling this method. For the
     * requirement, each object has to be written to a separate file. This operation waits until all files have been
     * written.
     *
     * @param data tha data that needs to be written to the file.
     */
    protected void publish(T data, int dataPositionInFile) {
        if (!validator.validate(data)) {
            log.error(String.format("Validation failed for the object: %s found at position: %s", data, dataPositionInFile));
            return;
        }
        observers.stream()
                .map(it -> it.write(data, dataPositionInFile))
                .forEach(CompletableFuture::join);
    }

    @Autowired
    public final void setApplicationContext(ApplicationContext applicationContext) {
        context = applicationContext;
    }

    @Autowired
    public final void setConfigurationProps(ConfigurationProps configurationProps) {
        properties = configurationProps;
        if (Objects.isNull(properties.getOutputFormats())) {
            log.error(ERROR_NO_WRITER_CONFIGURED);
            throw new ConverterException(ERROR_NO_WRITER_CONFIGURED);
        }
    }

    @Autowired
    public final void setValidator(BeanValidator<T> validator) {
        this.validator = validator;
    }
}
