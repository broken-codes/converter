package org.aiolabs.projects.converter.types.contracts;

import java.util.concurrent.CompletableFuture;

public interface ObservingTypeWriter<T> {

    /**
     * Writes the value represented by T to a file
     *  @param type
     * @param dataPositionInFile
     * @return
     */
    CompletableFuture<Void> write(T type, int dataPositionInFile);

}
