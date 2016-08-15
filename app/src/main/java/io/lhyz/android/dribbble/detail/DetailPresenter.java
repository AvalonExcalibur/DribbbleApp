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
package io.lhyz.android.dribbble.detail;

import com.orhanobut.logger.Logger;

import java.util.List;

import io.lhyz.android.boilerplate.interactor.DefaultSubscriber;
import io.lhyz.android.dribbble.AppPreference;
import io.lhyz.android.dribbble.data.DribbbleService;
import io.lhyz.android.dribbble.data.model.Comment;
import io.lhyz.android.dribbble.data.model.Like;
import io.lhyz.android.dribbble.data.model.Shot;
import io.lhyz.android.dribbble.net.ServiceCreator;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * hello,android
 * Created by lhyz on 2016/8/14.
 */
public class DetailPresenter implements DetailContract.Presenter {

    private static final String TAG = "DetailPresenter";

    DetailContract.View mView;
    final Shot mShot;

    DribbbleService mDribbbleService;
    Subscription mSubscription;
    Subscription mLikeSubscription;

    public DetailPresenter(DetailContract.View view, Shot shot) {
        mView = view;
        mShot = shot;
        mView.setPresenter(this);

        mDribbbleService = new ServiceCreator(AppPreference.getInstance().readToken())
                .createService();
    }

    @Override
    public void setBasicInfo() {
        mView.showBasicInfo(mShot);
    }

    @Override
    public void loadComments() {
        if (mShot.getCommentsCount() == 0) {
            mView.showNoComments();
            return;
        }
        if (mView.isPortrait()) {
            mView.showLoadingComments();
            mSubscription = mDribbbleService.getComments(mShot.getId())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new DefaultSubscriber<List<Comment>>() {
                        @Override
                        public void onSuccess(List<Comment> result) {
                            mView.hideLoadingComments();
                            mView.showAllComments(result);
                        }

                        @Override
                        public void onError(Throwable e) {
                            if (e != null) {
                                Logger.d(TAG, e.getMessage());
                            }
                            mView.hideLoadingComments();
                            mView.showNoComments();
                        }
                    });
        }
    }

    @Override
    public void likeShot() {
        mView.showLikesState(true);
        mLikeSubscription = mDribbbleService.likeShot(mShot.getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultSubscriber<Like>() {
                    @Override
                    public void onSuccess(Like result) {
                        mView.showLikesState(true);
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e != null) {
                            Logger.d(TAG, e.getMessage());
                        }
                        mView.showLikesState(false);
                    }
                });
    }

    @Override
    public void unlikeShot() {
        mView.showLikesState(false);
        mLikeSubscription = mDribbbleService.unlikeShot(mShot.getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultSubscriber<Like>() {
                    @Override
                    public void onSuccess(Like result) {
                        mView.showLikesState(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e != null) {
                            Logger.d(TAG, e.getMessage());
                        }
                        mView.showLikesState(true);
                    }
                });
    }

    @Override
    public void isLikeShot() {
        mLikeSubscription = mDribbbleService.isLikes(mShot.getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultSubscriber<Like>() {
                    @Override
                    public void onSuccess(Like result) {
                        mView.showLikesState(true);
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e != null) {
                            Logger.d(TAG, e.getMessage());
                        }
                        mView.showLikesState(false);
                    }
                });
    }

    @Override
    public void start() {
        setBasicInfo();
        isLikeShot();
        loadComments();
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
        if (mSubscription != null) {
            if (!mSubscription.isUnsubscribed()) {
                mSubscription.unsubscribe();
            }
        }
        if (mLikeSubscription != null) {
            if (!mLikeSubscription.isUnsubscribed()) {
                mLikeSubscription.unsubscribe();
            }
        }
    }
}
