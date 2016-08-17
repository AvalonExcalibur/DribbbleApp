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

import io.lhyz.android.dribbble.interactor.DefaultSubscriber;
import io.lhyz.android.dribbble.Injections;
import io.lhyz.android.dribbble.data.bean.Comment;
import io.lhyz.android.dribbble.data.bean.Like;
import io.lhyz.android.dribbble.data.bean.Shot;
import io.lhyz.android.dribbble.data.source.DribbbleRepository;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * hello,android
 * Created by lhyz on 2016/8/14.
 */
public class DetailPresenter implements DetailContract.Presenter {

    private static final String TAG = "DetailPresenter";

    DetailContract.View mView;
    final Shot mShot;

    DribbbleRepository mRepository;
    CompositeSubscription mCompositeSubscription;

    public DetailPresenter(DetailContract.View view, Shot shot) {
        mView = view;
        mShot = shot;
        mView.setPresenter(this);

        mCompositeSubscription = new CompositeSubscription();
        mRepository = Injections.provideRepository();
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
            Subscription subscription = mRepository.getComments(mShot.getId())
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
            mCompositeSubscription.add(subscription);
        }
    }

    @Override
    public void likeShot() {
        mView.showLikesState(true);
        Subscription subscription = mRepository.likeShot(mShot.getId())
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
        mCompositeSubscription.add(subscription);
    }

    @Override
    public void unlikeShot() {
        mView.showLikesState(false);
        Subscription subscription = mRepository.unlikeShot(mShot.getId())
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
        mCompositeSubscription.add(subscription);
    }

    @Override
    public void isLikeShot() {
        Subscription subscription = mRepository.isLike(mShot.getId())
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
        mCompositeSubscription.add(subscription);
    }

    @Override
    public void postComment(String body) {
        Subscription subscription = mRepository.addComment(mShot.getId(), new Comment(body))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultSubscriber<Comment>() {
                    @Override
                    public void onSuccess(Comment result) {
                        super.onSuccess(result);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }
                });
        mCompositeSubscription.add(subscription);
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

    @Override
    public void unsubscribe() {
        mCompositeSubscription.clear();
    }
}
