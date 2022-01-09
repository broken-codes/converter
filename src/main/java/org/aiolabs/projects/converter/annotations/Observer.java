package org.aiolabs.projects.converter.annotations;

import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * All writers should be marked with this annotation. The annotation extends the functionalities already provided by
 * {@link Component} annotation. Note that for an {@link Observer} to get notifications from
 * {@link org.aiolabs.projects.converter.services.contracts.ObservableTypeReader} it should both be enabled and listed
 * in the configuration files.
 */
@Component
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Observer {

    /**
     * Should the {@link Observer} be enabled?
     *
     * @return true if the {@link Observer} is observing, false otherwise
     */
    boolean enabled() default true;

    /**
     * Name of the {@link Observer}. This is the format that must be provided in the configuration files to add
     * {@link Observer} for getting notifications.
     *
     * @return Format of the {@link Observer}
     */
    String format();
}
