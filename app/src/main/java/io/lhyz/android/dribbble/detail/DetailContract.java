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

import io.lhyz.android.dribbble.base.BasePresenter;
import io.lhyz.android.dribbble.base.BaseView;
import io.lhyz.android.dribbble.data.bean.Comment;
import io.lhyz.android.dribbble.data.bean.Shot;

/**
 * hello,android
 * Created by lhyz on 2016/8/14.
 */
public interface DetailContract {

    interface Presenter extends BasePresenter {
        void setBasicInfo();

        void loadComments();

        void likeShot();

        void unlikeShot();

        void isLikeShot();

        void postComment(String body);

        void unsubscribe();
    }

    interface View extends BaseView<Presenter> {
        void showBasicInfo(Shot shot);

        void showAllComments(List<Comment> comments);

        void showLoadingComments();

        void hideLoadingComments();

        void showNoComments();

        void showLikesState(boolean likes);

        boolean isPortrait();
    }
}
