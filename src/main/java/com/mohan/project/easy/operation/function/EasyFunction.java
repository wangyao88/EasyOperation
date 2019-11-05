package com.mohan.project.easy.operation.function;

import java.util.Objects;

/**
 * @author mohan
 */
@FunctionalInterface
public interface EasyFunction<T, R> {

    R apply(T t) throws Exception;

    default <V> EasyFunction<V, R> compose(EasyFunction<? super V, ? extends T> before) throws Exception {
        Objects.requireNonNull(before);
        return (V v) -> apply(before.apply(v));
    }

    default <V> EasyFunction<T, V> andThen(EasyFunction<? super R, ? extends V> after) throws Exception {
        Objects.requireNonNull(after);
        return (T t) -> after.apply(apply(t));
    }

    static <T> EasyFunction<T, T> identity() throws Exception {
        return t -> t;
    }
}
