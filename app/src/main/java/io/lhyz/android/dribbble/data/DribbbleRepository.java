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
package io.lhyz.android.dribbble.data;

import android.support.annotation.NonNull;

import java.util.List;

import io.lhyz.android.dribbble.data.model.Comment;

/**
 * hello,android
 * Created by lhyz on 2016/8/14.
 */
public class DribbbleRepository implements DataSource {

    DataSource mLoadDataSource;
    DataSource mRemoteDataSource;

    public DribbbleRepository(DataSource loadDataSource, DataSource remoteDataSource) {
        mLoadDataSource = loadDataSource;
        mRemoteDataSource = remoteDataSource;
    }

    @Override
    public void loadComments(final long shotId, boolean force, @NonNull final LoadCommentCallback callback) {
        if (force) {
            mRemoteDataSource.loadComments(shotId, true, new LoadCommentCallback() {
                @Override
                public void onLoadedComments(List<Comment> comments) {
                    if (comments.size() == 0) {
                        callback.onNoComments();
                        return;
                    }
                    mLoadDataSource.saveComments(shotId, comments);
                    callback.onLoadedComments(comments);
                }

                @Override
                public void onNoComments() {
                    //Pass
                }
            });
        } else {
            mLoadDataSource.loadComments(shotId, false, callback);
        }

    }

    @Override
    public void saveComments(long shotId, @NonNull List<Comment> comments) {
        mLoadDataSource.saveComments(shotId, comments);
    }
}
