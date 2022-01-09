package org.aiolabs.projects.converter.validations;

public interface BeanValidator<T> {

    /**
     * Validates the bean whose instance is being generated.
     *
     * @param data the bean that needs to be validated.
     * @return true of the validation was successful. false otherwise
     */
    boolean validate(T data);
}
