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

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.lhyz.android.dribbble.executor.JobExecutor;
import io.lhyz.android.dribbble.executor.UIThread;
import io.lhyz.android.boilerplate.executor.PostThreadExecutor;
import io.lhyz.android.boilerplate.executor.ThreadExecutor;

/**
 * hello,android
 * Created by lhyz on 2016/8/10.
 */
@Module
public class AppModule {
    @Provides
    @Singleton
    PostThreadExecutor providePostThreadExecutor(UIThread uiThread) {
        return uiThread;
    }

    @Provides
    @Singleton
    ThreadExecutor provideThreadExecutor(JobExecutor jobExecutor) {
        return jobExecutor;
    }
}