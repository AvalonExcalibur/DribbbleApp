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

import java.util.List;

import javax.inject.Inject;

import io.lhyz.android.dribbble.DribbbleApp;
import io.lhyz.android.dribbble.data.bean.Comment;
import io.lhyz.android.dribbble.data.bean.Like;
import io.lhyz.android.dribbble.data.bean.Shot;
import io.lhyz.android.dribbble.data.source.DataSource;
import io.lhyz.android.dribbble.data.source.DribbbleRepository;

/**
 * hello,android
 * Created by lhyz on 2016/8/14.
 */
public class DetailPresenter implements DetailContract.Presenter {

    DetailContract.View mView;
    final Shot mShot;

    @Inject
    DribbbleRepository mRepository;

    public DetailPresenter(DetailContract.View view, Shot shot) {
        mView = view;
        mShot = shot;
        mView.setPresenter(this);

        DribbbleApp.getApp().getAppComponent().inject(this);
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
            mRepository.getCommentList(mShot.getId(), true, new DataSource.LoadCommentsCallback() {
                @Override
                public void onCommentsLoaded(List<Comment> comments) {
                    if (comments.size() == 0) {
                        onNoCommentsAvailable();
                    }
                    mView.hideLoadingComments();
                    mView.showAllComments(comments);
                }

                @Override
                public void onNoCommentsAvailable() {
                    mView.hideLoadingComments();
                    mView.showNoComments();
                }
            });
        }
    }

    @Override
    public void likeShot() {
        mView.showLikesState(true);
        mRepository.likeShot(mShot.getId(), new DataSource.LikeCallback() {
            @Override
            public void onSuccess(Like like) {
                mView.showLikesState(true);
            }

            @Override
            public void onFailure() {
                mView.showLikesState(false);
            }
        });
    }

    @Override
    public void unlikeShot() {
        mView.showLikesState(false);
        mRepository.unlikeShot(mShot.getId(), new DataSource.LikeCallback() {
            @Override
            public void onSuccess(Like like) {
                mView.showLikesState(false);
            }

            @Override
            public void onFailure() {
                mView.showLikesState(true);
            }
        });
    }

    @Override
    public void isLikeShot() {
        mRepository.isLike(mShot.getId(), new DataSource.LikeCallback() {
            @Override
            public void onSuccess(Like like) {
                mView.showLikesState(true);
            }

            @Override
            public void onFailure() {
                mView.showLikesState(false);
            }
        });
    }

    @Override
    public void postComment(String body) {
        mRepository.addComment(mShot.getId(), new Comment(body), new DataSource.AddCommentCallback() {
            @Override
            public void onSuccess(Comment comment) {

            }

            @Override
            public void onFailure() {

            }
        });
    }

    @Override
    public void start() {
        setBasicInfo();
        isLikeShot();
        loadComments();
    }

    @Override
    public void pause() {
        mRepository.cancel();
    }

    @Override
    public void destroy() {
        mRepository.cancel();
        mView = null;
    }
}
