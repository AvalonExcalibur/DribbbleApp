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
package io.lhyz.android.dribbble.home;

import android.content.Intent;

import io.lhyz.android.dribbble.base.BasePresenter;
import io.lhyz.android.dribbble.base.BaseView;
import io.lhyz.android.dribbble.data.model.User;

/**
 * hello,android
 * Created by lhyz on 2016/8/7.
 */
public class MainContract {
    public interface Presenter extends BasePresenter {
        void result(int requestCode, int resultCode, Intent data);

        void loadUser();
    }

    public interface View extends BaseView<Presenter> {
        void showError(String message);

        void showLoading(boolean shown);

        void showEmptyView(boolean shown);

        void redirectToLogin();

        void showUser(User user);

        void showPopularList();
    }
}
