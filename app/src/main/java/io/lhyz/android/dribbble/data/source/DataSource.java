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

/**
 * hello,android
 * Created by lhyz on 2016/8/14.
 */
public interface DataSource {

    interface LoadShotsCallback {
        void onShotsLoaded(List<Shot> shots);

        void onNoShotsAvailable();
    }

    interface LoadCommentsCallback {
        void onCommentsLoaded(List<Comment> comments);

        void onNoCommentsAvailable();
    }

    interface AddCommentCallback {
        void onSuccess(Comment comment);

        void onFailure();
    }

    interface LikeCallback {
        void onSuccess(Like like);

        void onFailure();
    }

    /**
     * For Shot
     */
    void getShotList(int type, boolean force, LoadShotsCallback callback);

    void saveShotList(int type, List<Shot> shots);

    /**
     * For Comments
     */
    void getCommentList(long shotId, boolean force, LoadCommentsCallback callback);

    void addComment(long shotId, Comment comment, AddCommentCallback callback);

    void saveCommentList(long shotId, List<Comment> comment);
    /**
     * For Likes
     */

    void isLike(long shotId, LikeCallback callback);

    void likeShot(long shotId, LikeCallback callback);

    void unlikeShot(long shotId, LikeCallback callback);

    void cancel();
}
