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
package io.lhyz.android.dribbble;

import android.app.Application;
import android.content.Context;

import com.bumptech.glide.request.target.ViewTarget;
import com.tencent.bugly.crashreport.CrashReport;

import io.lhyz.android.dribbble.di.component.AppComponent;
import io.lhyz.android.dribbble.di.component.DaggerAppComponent;

/**
 * hello,android
 * Created by lhyz on 2016/8/6.
 */
public class DribbbleApp extends Application {

    private static DribbbleApp INSTANCE;
    private static AppComponent sAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        ViewTarget.setTagId(R.id.glide_tag);

        INSTANCE = this;
        sAppComponent = DaggerAppComponent.create();

        CrashReport.initCrashReport(getApplicationContext(), "900046206", false);
    }

    public static DribbbleApp getApp() {
        return INSTANCE;
    }

    public static Context getAppContext() {
        return getApp();
    }

    public static AppComponent getAppComponent() {
        return sAppComponent;
    }
}
