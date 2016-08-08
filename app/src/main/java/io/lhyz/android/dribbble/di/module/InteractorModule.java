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

import dagger.Module;
import dagger.Provides;
import io.lhyz.android.boilerplate.data.executor.JobExecutor;
import io.lhyz.android.boilerplate.data.executor.UIThread;
import io.lhyz.android.boilerplate.domain.executor.PostThreadExecutor;
import io.lhyz.android.boilerplate.domain.executor.ThreadExecutor;
import io.lhyz.android.dribbble.di.annotation.ForApplication;

/**
 * hello,android
 * Created by lhyz on 2016/8/8.
 */
@Module
public class InteractorModule {

    @Provides
    @ForApplication
    PostThreadExecutor providePostThreadExecutor() {
        return new UIThread();
    }

    @Provides
    @ForApplication
    ThreadExecutor provideThreadExecutor() {
        return new JobExecutor();
    }
}
