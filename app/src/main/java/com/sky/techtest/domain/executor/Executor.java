package com.sky.techtest.domain.executor;


import com.sky.techtest.domain.interactors.base.AbstractInteractor;

/**
 * This executor is responsible for running interactors on background threads.
 * <p/>
 * Created by leslied on 11/03/2018.
 * https://github.com/dmilicic/Android-Clean-Boilerplate
 */
public interface Executor {

    /**
     * This method should call the interactor's run method and thus start the interactor. This should be called
     * on a background thread as interactors might do lengthy operations.
     *
     * @param interactor The interactor to run.
     */
    void execute(final AbstractInteractor interactor);
}
