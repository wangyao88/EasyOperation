package com.mohan.project.easy.operation;

import com.mohan.project.easy.operation.function.EasyConsumer;
import com.mohan.project.easy.operation.function.EasyFunction;
import com.mohan.project.easy.operation.function.EasySupplier;

import java.util.Optional;

/**
 * 操作处理类
 * @author mohan
 * @param <T>
 */
public final class EasyOperation<T> extends AbstractOperation<T>{

    private final Optional<T> value;
    private final Optional<Throwable> error;

    private EasyOperation(T value) {
        super();
        this.value = Optional.ofNullable(value);
        this.error = Optional.empty();
    }

    private EasyOperation(Throwable error) {
        super();
        this.value = Optional.empty();
        this.error = Optional.ofNullable(error);
    }

    public static <T> EasyOperation<T> of(EasySupplier<T> supplier) {
        try {
            T result = supplier.get();
            return new EasyOperation<>(result);
        }catch (Throwable throwable) {
            return new EasyOperation<>(throwable);
        }
    }

    public <U> EasyOperation<U> map(EasyFunction<T, U> function) {
        if(fail()) {
            return new EasyOperation<>(this.getError().get());
        }
        try {
            T t = getValue().get();
            U u = function.apply(t);
            return new EasyOperation<>(u);
        }catch (Throwable throwable) {
            return new EasyOperation<>(throwable);
        }
    }

    public <U> EasyOperation<U> flatMap(EasyFunction<T, EasyOperation<U>> function) {
        if(fail()) {
            return new EasyOperation<>(this.getError().get());
        }
        try {
            T t = getValue().get();
            return function.apply(t);
        }catch (Throwable throwable) {
            return new EasyOperation<>(throwable);
        }
    }

    public EasyOperation<T> onFailed(EasyConsumer<Throwable> consumer) {
        if(fail()) {
            try {
                Throwable throwable = this.getError().get();
                consumer.accept(throwable);
            }catch (Throwable throwable) {
                return new EasyOperation<>(throwable);
            }
        }
        return this;
    }

    public EasyOperation<T> recover(EasyFunction<Throwable, T> recoverFunction) {
        if(fail()) {
            try {
                Throwable throwable = this.getError().get();
                T result = recoverFunction.apply(throwable);
                return new EasyOperation<>(result);
            }catch (Throwable throwable) {
                return new EasyOperation<>(throwable);
            }
        }
        return this;
    }
}
