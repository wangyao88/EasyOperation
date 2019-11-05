package com.mohan.project.easy.operation;

import java.util.Optional;

/**
 * 操作抽象类
 * @author mohan
 */
public interface Operation<T> {

    /**
     * 是否执行成功
     * @return true 成功 false 失败
     */
    boolean isSuccess();

    /**
     * 是否执行失败
     * @return true 失败 false 成功
     */
    boolean fail();

    /**
     * 获取操作返回结果
     * @return 操作返回结果
     */
    Optional<T> getValue();

    /**
     * 获取异常类
     * @return 异常类
     */
    Optional<Throwable> getError();

    /**
     * 获取异常信息
     * @return 异常信息
     */
    Optional<String> getErrorMessage();
}
