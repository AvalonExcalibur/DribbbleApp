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

import io.lhyz.android.dribbble.data.Comment;
import io.lhyz.android.dribbble.data.Like;
import io.lhyz.android.dribbble.data.Shot;
import rx.Observable;

/**
 * hello,android
 * Created by lhyz on 2016/8/14.
 */
public interface DataSource {

    /**
     * For Shot
     */
    Observable<List<Shot>> getShotList(int type);

    void saveShotList(int type, List<Shot> shots);

    void refreshShots(int type);

    /**
     * For Comments
     */
    Observable<List<Comment>> getComments(long shotId);

    Observable<Comment> addComment(long shotId, Comment comment);

    void saveCommentList(long shotId, List<Comment> comment);

    void refreshComment();

    /**
     * For Likes
     */

    Observable<Like> isLike(long shotId);

    Observable<Like> likeShot(long shotId);

    Observable<Like> unlikeShot(long shotId);
}
