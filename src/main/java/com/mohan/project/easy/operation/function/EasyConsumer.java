package com.mohan.project.easy.operation.function;

import java.util.Objects;

/**
 * @author mohan
 */
@FunctionalInterface
public interface EasyConsumer<T> {

    void accept(T t) throws Exception;

    default EasyConsumer<T> andThen(EasyConsumer<? super T> after) throws Exception {
        Objects.requireNonNull(after);
        return (T t) -> { accept(t); after.accept(t); };
    }
}
