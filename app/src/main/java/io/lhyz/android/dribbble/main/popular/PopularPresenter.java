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
package io.lhyz.android.dribbble.main.popular;

import java.util.List;

import javax.inject.Inject;

import io.lhyz.android.boilerplate.interactor.DefaultSubscriber;
import io.lhyz.android.boilerplate.interactor.Interactor;
import io.lhyz.android.dribbble.DribbbleApp;
import io.lhyz.android.dribbble.data.model.Shot;
import io.lhyz.android.dribbble.di.annotation.Popular;

/**
 * hello,android
 * Created by lhyz on 2016/8/8.
 */
public class PopularPresenter implements PopularContract.Presenter {

    PopularContract.View mView;

    @Inject
    @Popular
    Interactor<List<Shot>> mInteractor;

    public PopularPresenter(PopularContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void start() {
        initInjector();
        loadPopular();
    }

    void initInjector() {
        DribbbleApp.getAppComponent().inject(this);
    }

    @Override
    public void loadPopular() {
        mView.showLoading();
        mInteractor.execute(mPopularSubscriber);
    }

    private final DefaultSubscriber<List<Shot>> mPopularSubscriber = new DefaultSubscriber<List<Shot>>() {
        @Override
        public void onSuccess(List<Shot> result) {
            mView.hideLoading();
            mView.showPopular(result);
        }

        @Override
        public void onError(Throwable e) {
            mView.hideLoading();
            mView.showEmptyView();
            mView.showError(e.getMessage());
        }
    };

}
