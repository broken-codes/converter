package org.aiolabs.projects.converter.types.contracts;

import java.util.List;

public interface ObservableTypeReader<T> {

    /**
     * Read a file and return a {@link List} of type to which the file needs to be converted to.
     *
     * @param inputFileName
     *
     */
    void read(String inputFileName);
}
