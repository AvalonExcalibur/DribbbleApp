/*
 * Copyright (c) 2016 lhyz Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.lhyz.android.boilerplate.interactor;

import io.lhyz.android.boilerplate.executor.PostThreadExecutor;
import io.lhyz.android.boilerplate.executor.ThreadExecutor;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.schedulers.Schedulers;

/**
 * hello,android
 * Created by lhyz on 2016/8/6.
 * <p>
 * 交互器，用来执行异步操作
 */
public abstract class Interactor<T> {
    private final ThreadExecutor mThreadExecutor;
    private final PostThreadExecutor mPostThreadExecutor;

    private Subscription mSubscription;

    public Interactor(ThreadExecutor threadExecutor,
                      PostThreadExecutor postThreadExecutor) {
        mThreadExecutor = threadExecutor;
        mPostThreadExecutor = postThreadExecutor;
    }

    protected abstract Observable<T> buildObservable();

    public void execute(Subscriber<T> subscriber) {
        this.mSubscription = this.buildObservable()
                .subscribeOn(Schedulers.from(mThreadExecutor))
                .observeOn(mPostThreadExecutor.getScheduler())
                .subscribe(subscriber);
    }

    public void unsubscribe() {
        if (!mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
    }
}
