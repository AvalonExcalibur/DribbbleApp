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
package io.lhyz.android.dribbble.di.component;

import dagger.Component;
import io.lhyz.android.dribbble.detail.DetailPresenter;
import io.lhyz.android.dribbble.di.ForApplication;
import io.lhyz.android.dribbble.di.module.AppModule;
import io.lhyz.android.dribbble.main.debut.DebutPresenter;
import io.lhyz.android.dribbble.main.following.FollowingPresenter;
import io.lhyz.android.dribbble.main.playoffs.PlayoffsPresenter;
import io.lhyz.android.dribbble.main.popular.PopularPresenter;
import io.lhyz.android.dribbble.main.recent.RecentPresenter;
import io.lhyz.android.dribbble.main.team.TeamPresenter;

/**
 * hello,android
 * Created by lhyz on 2016/8/7.
 */
@ForApplication
@Component(modules = {AppModule.class})
public interface AppComponent {
    void inject(DetailPresenter presenter);

    void inject(DebutPresenter presenter);

    void inject(FollowingPresenter presenter);

    void inject(PlayoffsPresenter presenter);

    void inject(PopularPresenter presenter);

    void inject(RecentPresenter presenter);

    void inject(TeamPresenter presenter);
}
