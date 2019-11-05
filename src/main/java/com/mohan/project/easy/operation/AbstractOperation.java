package com.mohan.project.easy.operation;

import com.mohan.project.easy.operation.function.EasySupplier;

import java.util.Optional;

/**
 * 抽象操作处理类
 * @author mohan
 * @param <T>
 */
public abstract class AbstractOperation<T> implements Operation<T>{

    protected Optional<T> value;
    protected Optional<Throwable> error;

    protected AbstractOperation() {
        this.value = Optional.empty();
        this.error = Optional.empty();
    }

    protected AbstractOperation(T value) {
        this.value = Optional.ofNullable(value);
        this.error = Optional.empty();
    }

    protected AbstractOperation(Throwable throwable) {
        this.value = Optional.empty();
        this.error = Optional.ofNullable(throwable);
    }

    public T getOrElse(T defaultValue) {
        if(isSuccess()) {
            return getValue().get();
        }
        return defaultValue;
    }

    public T getOrElse(EasySupplier<T> supplier) {
        if(isSuccess()) {
            return getValue().get();
        }
        try {
            return supplier.get();
        }catch (Throwable throwable) {
            throw new ExecuteException(throwable);
        }
    }

    @Override
    public boolean isSuccess() {
        return !this.error.isPresent();
    }

    @Override
    public boolean fail() {
        return this.error.isPresent();
    }

    @Override
    public Optional<T> getValue() {
        return this.value;
    }

    @Override
    public Optional<Throwable> getError() {
        return this.error;
    }

    @Override
    public Optional<String> getErrorMessage() {
        if(fail()) {
            return Optional.ofNullable(this.error.get().getMessage());
        }
        return Optional.empty();
    }
}
