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
package io.lhyz.android.dribbble.home;

import android.app.Activity;
import android.content.Intent;

import io.lhyz.android.boilerplate.domain.interactor.DefaultSubscriber;
import io.lhyz.android.dribbble.AppPreference;
import io.lhyz.android.dribbble.data.DribbbleService;
import io.lhyz.android.dribbble.data.model.User;
import io.lhyz.android.dribbble.login.AuthActivity;
import io.lhyz.android.dribbble.net.CookieManager;
import io.lhyz.android.dribbble.net.RetrofitManager;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * hello,android
 * Created by lhyz on 2016/8/7.
 */
public class MainPresenter implements MainContract.Presenter {
    //View
    private MainContract.View mView;
    //Model
    private DribbbleService mDribbbleService;

    public MainPresenter(MainContract.View view) {
        mView = view;
        mView.setPresenter(this);

        mDribbbleService = null;
    }

    @Override
    public void result(int requestCode, int resultCode, Intent data) {
        if (requestCode == AuthActivity.REQUEST_AUTH && resultCode == Activity.RESULT_OK) {
            String access_token = data.getData().toString();
            AppPreference.getInstance().saveToken(access_token);
            start();
        }
    }

    @Override
    public void start() {
        if (AppPreference.getInstance().readToken() == null) {
            mView.redirectToLogin();
        } else {
            CookieManager cookieManager = new CookieManager(AppPreference.getInstance().readToken());
            RetrofitManager manager = new RetrofitManager(cookieManager);
            mDribbbleService = manager.buildRetrofit().create(DribbbleService.class);
            loadUser();
        }
    }

    @Override
    public void loadUser() {
        if (mDribbbleService == null) {
            start();
        } else {
            mDribbbleService.getUser().subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new DefaultSubscriber<User>() {
                        @Override
                        public void onSuccess(User result) {
                            mView.showUser(result);
                        }

                        @Override
                        public void onError(Throwable e) {
                            mView.showError(e.getMessage());
                        }
                    });
        }
    }
}
