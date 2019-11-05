package com.mohan.project.easy.operation.function;

/**
 * @author mohan
 */
@FunctionalInterface
public interface EasySupplier<T> {

    T get() throws Exception;
}
