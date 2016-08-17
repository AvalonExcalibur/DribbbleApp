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
package io.lhyz.android.dribbble.data.source;

import java.util.List;

import javax.inject.Inject;

import io.lhyz.android.dribbble.data.bean.Comment;
import io.lhyz.android.dribbble.data.bean.Shot;

/**
 * hello,android
 * Created by lhyz on 2016/8/14.
 */
public class DribbbleRepository implements DataSource {
    //本地数据源
    DataSource mLocalDataSource;
    //远程数据源
    DataSource mRemoteDataSource;

    @Inject
    public DribbbleRepository(DataSource localDataSource, DataSource remoteDataSource) {
        mLocalDataSource = localDataSource;
        mRemoteDataSource = remoteDataSource;
    }

    @Override
    public void getShotList(final int type, boolean force, final LoadShotsCallback callback) {
        if (force) {
            mRemoteDataSource.getShotList(type, true, new LoadShotsCallback() {
                @Override
                public void onShotsLoaded(List<Shot> shots) {
                    mLocalDataSource.saveShotList(type, shots);
                    callback.onShotsLoaded(shots);
                }

                @Override
                public void onNoShotsAvailable() {
                    callback.onNoShotsAvailable();
                }
            });
        } else {
            mLocalDataSource.getShotList(type, false, callback);
        }
    }

    @Override
    public void getCommentList(final long shotId, boolean force, final LoadCommentsCallback callback) {
        if (force) {
            mRemoteDataSource.getCommentList(shotId, true, new LoadCommentsCallback() {
                @Override
                public void onCommentsLoaded(List<Comment> comments) {
                    mLocalDataSource.saveCommentList(shotId, comments);
                    callback.onCommentsLoaded(comments);
                }

                @Override
                public void onNoCommentsAvailable() {
                    callback.onNoCommentsAvailable();
                }
            });
        } else {
            mLocalDataSource.getCommentList(shotId, false, callback);
        }
    }

    @Override
    public void addComment(final long shotId, Comment comment, final AddCommentCallback callback) {
        mRemoteDataSource.addComment(shotId, comment, new AddCommentCallback() {
            @Override
            public void onSuccess(Comment comment) {
                mLocalDataSource.addComment(shotId, comment, null);
                callback.onSuccess(comment);
            }

            @Override
            public void onFailure() {
                callback.onFailure();
            }
        });
    }

    @Override
    public void isLike(long shotId, LikeCallback callback) {
        mRemoteDataSource.isLike(shotId, callback);
    }

    @Override
    public void likeShot(long shotId, LikeCallback callback) {
        mRemoteDataSource.likeShot(shotId, callback);
    }

    @Override
    public void unlikeShot(long shotId, LikeCallback callback) {
        mRemoteDataSource.unlikeShot(shotId, callback);
    }

    @Override
    public void saveShotList(int type, List<Shot> shots) {

    }

    @Override
    public void saveCommentList(long shotId, List<Comment> comment) {

    }

    @Override
    public void cancel() {
        mRemoteDataSource.cancel();
        mLocalDataSource.cancel();
    }
}
