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
package io.lhyz.android.dribbble.data.local;

import android.content.Context;
import android.support.annotation.NonNull;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.List;

import io.lhyz.android.dribbble.data.DataSource;
import io.lhyz.android.dribbble.data.model.Comment;

/**
 * hello,android
 * Created by lhyz on 2016/8/14.
 */
public class LocalDataSource implements DataSource {

    private static LocalDataSource INSTANCE;

    Dao<Comment, Long> mDao;

    public LocalDataSource(Context context) {
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        try {
            mDao = databaseHelper.getTasksDao();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static LocalDataSource getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new LocalDataSource(context);
        }
        return INSTANCE;
    }

    @Override
    public void loadComments(long shotId, boolean force, @NonNull LoadCommentCallback callback) {
        try {
            QueryBuilder<Comment, Long> queryBuilder = mDao.queryBuilder();
            queryBuilder.where().eq("shotId",shotId);
            PreparedQuery<Comment> preparedQuery = queryBuilder.prepare();
            List<Comment> result = mDao.query(preparedQuery);
            if (result == null || result.size() == 0) {
                callback.onNoComments();
            }
            callback.onLoadedComments(result);
        } catch (SQLException e) {
            callback.onNoComments();
            e.printStackTrace();
        }
    }

    @Override
    public void saveComments(long shotId, @NonNull List<Comment> comments) {
        try {
            for (Comment comment : comments) {
                comment.setShotId(shotId);
            }
            mDao.create(comments);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
