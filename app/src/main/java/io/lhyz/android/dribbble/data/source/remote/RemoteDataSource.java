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

import java.util.List;

import io.lhyz.android.boilerplate.util.TagHelper;
import io.lhyz.android.dribbble.data.bean.Comment;
import io.lhyz.android.dribbble.data.bean.Like;
import io.lhyz.android.dribbble.data.bean.Shot;
import io.lhyz.android.dribbble.data.source.DataSource;
import io.lhyz.android.dribbble.data.source.DribbbleService;
import io.lhyz.android.dribbble.data.source.ShotType;
import io.lhyz.android.dribbble.net.DribbbleServiceCreator;
import rx.Observable;

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

    public RemoteDataSource() {
        mDribbbleService = DribbbleServiceCreator.getInstance()
                .createService();
    }

    public static RemoteDataSource getInstance() {
        return Holder.instance;
    }

    @Override
    public Observable<List<Shot>> getShotList(int type) {
        switch (type) {
            case ShotType.RECENT:
                return mDribbbleService.getRecentList();
            case ShotType.POPULAR:
                return mDribbbleService.getPopularList();
            case ShotType.DEBUT:
                return mDribbbleService.getDebutList();
            case ShotType.TEAM:
                return mDribbbleService.getTeamList();
            case ShotType.PLAYOFFS:
                return mDribbbleService.getPlayoffList();
            case ShotType.FOLLOWING:
                return mDribbbleService.getFollowingShotList();
            default:
                break;
        }
        return Observable.empty();
    }

    @Override
    public void saveShotList(int type, List<Shot> shots) {
        //Remote DataSource Not Need This
    }

    @Override
    public void refreshShots(int type) {
        //Remote DataSource Not Need This
    }

    @Override
    public Observable<List<Comment>> getComments(long shotId) {
        return mDribbbleService.getComments(shotId);
    }

    @Override
    public Observable<Comment> addComment(long shotId, Comment comment) {
        return mDribbbleService.postComment(shotId, comment.getBody());
    }

    @Override
    public void saveCommentList(long shotId, List<Comment> comment) {
        //Remote DataSource Not Need This
    }

    @Override
    public void refreshComment() {
        //Remote DataSource Not Need This
    }

    @Override
    public Observable<Like> isLike(long shotId) {
        return mDribbbleService.isLike(shotId);
    }

    @Override
    public Observable<Like> likeShot(long shotId) {
        return mDribbbleService.likeShot(shotId);
    }

    @Override
    public Observable<Like> unlikeShot(long shotId) {
        return mDribbbleService.unlikeShot(shotId);
    }
}