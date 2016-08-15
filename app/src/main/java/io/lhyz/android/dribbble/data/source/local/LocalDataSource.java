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
package io.lhyz.android.dribbble.data.source.local;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.orhanobut.logger.Logger;

import java.sql.SQLException;
import java.util.List;

import io.lhyz.android.boilerplate.util.TagHelper;
import io.lhyz.android.dribbble.data.Comment;
import io.lhyz.android.dribbble.data.Like;
import io.lhyz.android.dribbble.data.Shot;
import io.lhyz.android.dribbble.data.source.DataSource;
import rx.Observable;
import rx.Subscriber;

/**
 * hello,android
 * Created by lhyz on 2016/8/14.
 */
public class LocalDataSource implements DataSource {
    private static final String TAG = TagHelper.from(LocalDataSource.class);

    private static LocalDataSource INSTANCE;

    Dao<Comment, Long> mCommentDao;
    Dao<Shot, Long> mShotDao;

    public LocalDataSource(Context context) {
        CommentDBHelper commentDBHelper = new CommentDBHelper(context);
        ShotDBHelper shotDBHelper = new ShotDBHelper(context);
        //TODO Error when create shot(i think)
        try {
            mCommentDao = commentDBHelper.getDao();
            mShotDao = shotDBHelper.getDao();
        } catch (SQLException e) {
            Logger.e(TAG, e.getMessage());
        }
    }

    public static LocalDataSource getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new LocalDataSource(context);
        }
        return INSTANCE;
    }

    /**
     * 根据shot列表类型获取列表
     */
    @Override
    public Observable<List<Shot>> getShotList(final int type) {
        //TODO 排序问题（时间排序）
        return Observable.create(new Observable.OnSubscribe<List<Shot>>() {
            @Override
            public void call(Subscriber<? super List<Shot>> subscriber) {
                try {
                    QueryBuilder<Shot, Long> queryBuilder = mShotDao.queryBuilder();
                    //根据类型查询
                    queryBuilder.where().eq("type", type);
                    PreparedQuery<Shot> preparedQuery = queryBuilder.prepare();
                    List<Shot> result = mShotDao.query(preparedQuery);
                    subscriber.onNext(result);
                    subscriber.onCompleted();
                } catch (SQLException e) {
                    subscriber.onError(e);
                }
            }
        });
    }

    /**
     * 保存一种类型的shot列表
     */
    @Override
    public void saveShotList(int type, List<Shot> shots) {
        for (Shot shot : shots) {
            shot.setType(type);
        }
        try {
            mShotDao.create(shots);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void refreshShots(int type) {
        //Not Need For Local DataSource
    }

    /**
     * 获取某个shot的所有comment
     */
    @Override
    public Observable<List<Comment>> getComments(final long shotId) {
        //TODO 排序问题（时间排序）
        return Observable.create(new Observable.OnSubscribe<List<Comment>>() {
            @Override
            public void call(Subscriber<? super List<Comment>> subscriber) {
                try {
                    QueryBuilder<Comment, Long> queryBuilder = mCommentDao.queryBuilder();
                    //根据shotId查询
                    queryBuilder.where().eq("shotId", shotId);
                    PreparedQuery<Comment> preparedQuery = queryBuilder.prepare();
                    List<Comment> result = mCommentDao.query(preparedQuery);
                    subscriber.onNext(result);
                    subscriber.onCompleted();
                } catch (SQLException e) {
                    subscriber.onError(e);
                }
            }
        });
    }

    /**
     * 本地添加一条comment（远程添加完毕之后再调用本地保存）
     */
    @Override
    public Observable<Comment> addComment(final long shotId, final Comment comment) {
        return Observable.create(new Observable.OnSubscribe<Comment>() {
            @Override
            public void call(Subscriber<? super Comment> subscriber) {
                //本地添加一条数据(这条数据必须是服务器返回的)
                comment.setShotId(shotId);
                try {
                    mCommentDao.create(comment);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                subscriber.onNext(comment);
                subscriber.onCompleted();
            }
        });
    }

    /**
     * 保存一条shot所有comment列表
     */
    @Override
    public void saveCommentList(long shotId, List<Comment> comments) {
        for (Comment comment : comments) {
            comment.setShotId(shotId);
        }
        try {
            mCommentDao.create(comments);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void refreshComment() {
        //Not Need For Local DataSource
    }

    @Override
    public Observable<Like> isLike(long shotId) {
        //本地无法检测
        return null;
    }

    @Override
    public Observable<Like> likeShot(long shotId) {
        //本地无法操作
        return null;
    }

    @Override
    public Observable<Like> unlikeShot(long shotId) {
        //本地无法操作
        return null;
    }
}
