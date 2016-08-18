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
package io.lhyz.android.dribbble.data.source.remote;

import com.orhanobut.logger.Logger;

import java.util.List;

import io.lhyz.android.dribbble.base.DefaultSubscriber;
import io.lhyz.android.dribbble.data.bean.Comment;
import io.lhyz.android.dribbble.data.bean.Like;
import io.lhyz.android.dribbble.data.bean.Shot;
import io.lhyz.android.dribbble.data.source.DataSource;
import io.lhyz.android.dribbble.data.DribbbleService;
import io.lhyz.android.dribbble.data.ShotType;
import io.lhyz.android.dribbble.net.DribbbleServiceCreator;
import io.lhyz.android.dribbble.util.TagHelper;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * hello,android
 * Created by lhyz on 2016/8/14.
 */
public class RemoteDataSource implements DataSource {

    private static final String TAG = TagHelper.from(RemoteDataSource.class);

    private static class Holder {
        private static final RemoteDataSource instance = new RemoteDataSource();
    }

    DribbbleService mDribbbleService;

    CompositeSubscription mCompositeSubscription;

    public RemoteDataSource() {
        mDribbbleService = DribbbleServiceCreator.newInstance()
                .createService();

        mCompositeSubscription = new CompositeSubscription();
    }

    public static RemoteDataSource getInstance() {
        return Holder.instance;
    }

    @Override
    public void getShotList(int type, boolean force, final LoadShotsCallback callback) {
        Observable<List<Shot>> observable = null;
        switch (type) {
            case ShotType.RECENT:
                observable = mDribbbleService.getRecentList();
                break;
            case ShotType.POPULAR:
                observable = mDribbbleService.getPopularList();
                break;
            case ShotType.DEBUT:
                observable = mDribbbleService.getDebutList();
                break;
            case ShotType.TEAM:
                observable = mDribbbleService.getTeamList();
                break;
            case ShotType.PLAYOFFS:
                observable = mDribbbleService.getPlayoffList();
                break;
            case ShotType.FOLLOWING:
                observable = mDribbbleService.getFollowingShotList();
                break;
            default:
                break;
        }
        if (observable == null) {
            callback.onNoShotsAvailable();
            return;
        }

        Subscription subscription =
                observable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new DefaultSubscriber<List<Shot>>() {
                            @Override
                            public void onSuccess(List<Shot> result) {
                                if (result.size() == 0) {
                                    callback.onNoShotsAvailable();
                                    return;
                                }
                                callback.onShotsLoaded(result);
                            }

                            @Override
                            public void onError(Throwable e) {
                                Logger.e(TAG, e.getMessage());
                                callback.onNoShotsAvailable();
                            }
                        });
        mCompositeSubscription.add(subscription);
    }

    @Override
    public void getCommentList(long shotId, boolean force, final LoadCommentsCallback callback) {
        Subscription subscription = mDribbbleService.getComments(shotId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultSubscriber<List<Comment>>() {
                    @Override
                    public void onSuccess(List<Comment> result) {
                        if (result.size() == 0) {
                            callback.onNoCommentsAvailable();
                            return;
                        }
                        callback.onCommentsLoaded(result);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.e(TAG, e.getMessage());
                        callback.onNoCommentsAvailable();
                    }
                });
        mCompositeSubscription.add(subscription);
    }

    @Override
    public void addComment(long shotId, Comment comment, final AddCommentCallback callback) {
        Subscription subscription = mDribbbleService.postComment(shotId, comment.getBody())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultSubscriber<Comment>() {
                    @Override
                    public void onSuccess(Comment result) {
                        if (result == null) {
                            callback.onFailure();
                            return;
                        }
                        callback.onSuccess(result);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.e(TAG, e.getMessage());
                        callback.onFailure();
                    }
                });
        mCompositeSubscription.add(subscription);
    }

    @Override
    public void isLike(long shotId, final LikeCallback callback) {
        Subscription subscription = mDribbbleService.isLike(shotId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultSubscriber<Like>() {
                    @Override
                    public void onSuccess(Like result) {
                        callback.onSuccess(result);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.e(TAG, e.getMessage());
                        callback.onFailure();
                    }
                });
        mCompositeSubscription.add(subscription);
    }

    @Override
    public void likeShot(long shotId, final LikeCallback callback) {
        Subscription subscription = mDribbbleService.likeShot(shotId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultSubscriber<Like>() {
                    @Override
                    public void onSuccess(Like result) {
                        callback.onSuccess(result);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.e(TAG, e.getMessage());
                        callback.onFailure();
                    }
                });
        mCompositeSubscription.add(subscription);
    }

    @Override
    public void unlikeShot(long shotId, final LikeCallback callback) {
        Subscription subscription = mDribbbleService.isLike(shotId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultSubscriber<Like>() {
                    @Override
                    public void onSuccess(Like result) {
                        callback.onSuccess(result);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.e(TAG, e.getMessage());
                        callback.onFailure();
                    }
                });
        mCompositeSubscription.add(subscription);
    }

    @Override
    public void cancel() {
        mCompositeSubscription.clear();
    }

    @Override
    public void saveShotList(int type, List<Shot> shots) {
        //Remote DataSource Not Need This
    }

    @Override
    public void saveCommentList(long shotId, List<Comment> comment) {
        //Remote DataSource Not Need This
    }
}