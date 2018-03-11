package com.sky.techtest.presenters;


import com.sky.techtest.domain.executor.Executor;
import com.sky.techtest.domain.executor.MainThread;

/**
 * Created by leslied on 09/03/2018.
 */
public abstract class AbstractPresenter {
    protected Executor mExecutor;
    protected MainThread mMainThread;

    public AbstractPresenter(Executor executor, MainThread mainThread) {
        mExecutor = executor;
        mMainThread = mainThread;
    }
}
