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
package io.lhyz.android.dribbble.main.recent;

import java.util.List;

import io.lhyz.android.dribbble.interactor.DefaultSubscriber;
import io.lhyz.android.dribbble.Injections;
import io.lhyz.android.dribbble.data.bean.Shot;
import io.lhyz.android.dribbble.data.source.DribbbleRepository;
import io.lhyz.android.dribbble.data.source.ShotType;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * hello,android
 * Created by lhyz on 2016/8/9.
 */
public class RecentPresenter implements RecentContract.Presenter {

    RecentContract.View mView;

    DribbbleRepository mRepository;
    Subscription mSubscription;

    public RecentPresenter(RecentContract.View view) {
        mView = view;
        mView.setPresenter(this);

        mRepository = Injections.provideRepository();
    }

    @Override
    public void loadRecent() {
        mView.showLoading();
        mSubscription = mRepository.getShotList(ShotType.RECENT)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultSubscriber<List<Shot>>() {
                    @Override
                    public void onSuccess(List<Shot> result) {
                        mView.hideLoading();
                        mView.showRecent(result);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.hideLoading();
                        mView.showEmptyView();
                    }
                });
    }

    @Override
    public void start() {
        loadRecent();
    }

    @Override
    public void pause() {
        unsubscribe();
    }

    @Override
    public void destroy() {
        unsubscribe();
        mView = null;
    }

    private void unsubscribe() {
        if (mSubscription == null) {
            return;
        }
        if (!mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
    }
}
