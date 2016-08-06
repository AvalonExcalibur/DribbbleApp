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
package io.lhyz.android.boilerplate.domain.interactor;

import rx.Subscriber;

/**
 * hello,android
 * Created by lhyz on 2016/8/6.
 * <p/>
 * 实现一个默认的Subscriber，并更名onNext为onSuccess
 * <p/>
 * TODO 可能有问题，暂时实现先如此
 */
public class DefaultSubscriber<T> extends Subscriber<T> {
    @Override
    public void onCompleted() {
        //Pass
    }

    @Override
    public void onNext(T t) {
        onSuccess(t);
    }

    @Override
    public void onError(Throwable e) {

    }

    public void onSuccess(T result) {

    }
}
