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

import javax.inject.Inject;

import io.lhyz.android.dribbble.DribbbleApp;
import io.lhyz.android.dribbble.data.bean.Shot;
import io.lhyz.android.dribbble.data.source.DataSource;
import io.lhyz.android.dribbble.data.source.DribbbleRepository;
import io.lhyz.android.dribbble.data.ShotType;

/**
 * hello,android
 * Created by lhyz on 2016/8/9.
 */
public class RecentPresenter implements RecentContract.Presenter {

    RecentContract.View mView;

    @Inject
    DribbbleRepository mRepository;

    public RecentPresenter(RecentContract.View view) {
        mView = view;
        mView.setPresenter(this);

        DribbbleApp.getApp().getAppComponent().inject(this);
    }

    @Override
    public void loadRecent(boolean force) {
        mView.showLoading();
        mRepository.getShotList(ShotType.RECENT, force, new DataSource.LoadShotsCallback() {
            @Override
            public void onShotsLoaded(List<Shot> shots) {
                mView.hideLoading();
                mView.showRecent(shots);
            }

            @Override
            public void onNoShotsAvailable() {
                mView.hideLoading();
                mView.showEmptyView();
            }
        });
    }

    @Override
    public void start() {
        loadRecent(false);
    }

    @Override
    public void pause() {
        mRepository.cancel();
    }

    @Override
    public void destroy() {
        mRepository.cancel();
        mView = null;
    }
}
