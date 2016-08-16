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

import io.lhyz.android.dribbble.data.bean.Comment;
import io.lhyz.android.dribbble.data.bean.Like;
import io.lhyz.android.dribbble.data.bean.Shot;
import rx.Observable;
import rx.functions.Action1;

/**
 * hello,android
 * Created by lhyz on 2016/8/14.
 */
public class DribbbleRepository implements DataSource {
    //本地数据源
    DataSource mLocalDataSource;
    //远程数据源
    DataSource mRemoteDataSource;

    public DribbbleRepository(DataSource localDataSource, DataSource remoteDataSource) {
        mLocalDataSource = localDataSource;
        mRemoteDataSource = remoteDataSource;
    }

    @Override
    public Observable<List<Shot>> getShotList(final int type) {
        return mRemoteDataSource.getShotList(type).doOnNext(new Action1<List<Shot>>() {
            @Override
            public void call(List<Shot> shots) {
                mLocalDataSource.saveShotList(type, shots);
            }
        });
    }

    @Override
    public void saveShotList(int type, List<Shot> shots) {
        mLocalDataSource.saveShotList(type, shots);
    }

    @Override
    public void refreshShots(int type) {
        mRemoteDataSource.refreshShots(type);
    }

    @Override
    public Observable<List<Comment>> getComments(final long shotId) {
        return mRemoteDataSource.getComments(shotId).doOnNext(new Action1<List<Comment>>() {
            @Override
            public void call(List<Comment> comments) {
                mLocalDataSource.saveCommentList(shotId, comments);
            }
        });
    }

    @Override
    public Observable<Comment> addComment(long shotId, Comment comment) {
        return mRemoteDataSource.addComment(shotId, comment);
    }

    @Override
    public void saveCommentList(long shotId, List<Comment> comment) {
        mLocalDataSource.saveCommentList(shotId, comment);
    }

    @Override
    public void refreshComment() {
        mRemoteDataSource.refreshComment();
    }

    @Override
    public Observable<Like> isLike(long shotId) {
        return mRemoteDataSource.isLike(shotId);
    }

    @Override
    public Observable<Like> likeShot(long shotId) {
        return mRemoteDataSource.likeShot(shotId);
    }

    @Override
    public Observable<Like> unlikeShot(long shotId) {
        return mRemoteDataSource.unlikeShot(shotId);
    }
}
