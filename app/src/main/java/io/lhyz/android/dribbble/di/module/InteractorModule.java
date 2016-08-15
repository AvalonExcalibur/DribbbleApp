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
package io.lhyz.android.dribbble.di.module;

import java.util.List;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.lhyz.android.boilerplate.executor.PostThreadExecutor;
import io.lhyz.android.boilerplate.executor.ThreadExecutor;
import io.lhyz.android.boilerplate.interactor.Interactor;
import io.lhyz.android.dribbble.data.Shot;
import io.lhyz.android.dribbble.di.annotation.Debut;
import io.lhyz.android.dribbble.di.annotation.Playoffs;
import io.lhyz.android.dribbble.di.annotation.Popular;
import io.lhyz.android.dribbble.di.annotation.Recent;
import io.lhyz.android.dribbble.di.annotation.Team;
import io.lhyz.android.dribbble.main.debut.DebutInteractor;
import io.lhyz.android.dribbble.main.playoffs.PlayoffsInteractor;
import io.lhyz.android.dribbble.main.popular.PopularInteractor;
import io.lhyz.android.dribbble.main.recent.RecentInteractor;
import io.lhyz.android.dribbble.main.team.TeamInteractor;

/**
 * hello,android
 * Created by lhyz on 2016/8/8.
 */
@Module
public class InteractorModule {

    @Popular
    @Singleton
    @Provides
    Interactor<List<Shot>> providePopularInteractor(PostThreadExecutor postThreadExecutor,
                                                    ThreadExecutor threadExecutor) {
        return new PopularInteractor(threadExecutor, postThreadExecutor);
    }

    @Debut
    @Singleton
    @Provides
    Interactor<List<Shot>> provideDebutInteractor(PostThreadExecutor postThreadExecutor,
                                                  ThreadExecutor threadExecutor) {
        return new DebutInteractor(threadExecutor, postThreadExecutor);
    }

    @Playoffs
    @Singleton
    @Provides
    Interactor<List<Shot>> providePlayoffsInteractor(PostThreadExecutor postThreadExecutor,
                                                     ThreadExecutor threadExecutor) {
        return new PlayoffsInteractor(threadExecutor, postThreadExecutor);
    }

    @Recent
    @Singleton
    @Provides
    Interactor<List<Shot>> provideRecentInteractor(PostThreadExecutor postThreadExecutor,
                                                   ThreadExecutor threadExecutor) {
        return new RecentInteractor(threadExecutor, postThreadExecutor);
    }

    @Team
    @Singleton
    @Provides
    Interactor<List<Shot>> provideTeamInteractor(PostThreadExecutor postThreadExecutor,
                                                 ThreadExecutor threadExecutor) {
        return new TeamInteractor(threadExecutor, postThreadExecutor);
    }
}
