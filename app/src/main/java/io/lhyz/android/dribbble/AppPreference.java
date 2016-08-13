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

import android.content.Context;
import android.content.SharedPreferences;

/**
 * hello,android
 * Created by lhyz on 2016/8/7.
 */
public class AppPreference {
    private static final String SHARED_NAME = "dribbble_app_conf";

    private static final String KEY_ACCESS_TOKEN = "access_token";
    private static final String KEY_FIRST_START = "first_start";
    private static final String KEY_TAB_POSITION = "tab_position";

    private SharedPreferences mSharedPreferences;

    private static AppPreference INSTANCE;

    private AppPreference() {
        mSharedPreferences = DribbbleApp.getAppContext().getSharedPreferences(SHARED_NAME, Context.MODE_PRIVATE);
    }

    public static AppPreference getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new AppPreference();
        }
        return INSTANCE;
    }

    public void saveToken(String token) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(KEY_ACCESS_TOKEN, token);
        editor.apply();
    }

    public String readToken() {
        return mSharedPreferences.getString(KEY_ACCESS_TOKEN, null);
    }

    public boolean isFirstStart() {
        boolean isFirstStart = mSharedPreferences.getBoolean(KEY_FIRST_START, true);
        if (isFirstStart) {
            SharedPreferences.Editor editor = mSharedPreferences.edit();
            editor.putBoolean(KEY_FIRST_START, false);
            editor.apply();
        }
        return isFirstStart;
    }


    public void saveTabPosition(int pos) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putInt(KEY_TAB_POSITION, pos);
        editor.apply();
    }

    public int readTabPosition() {
        return mSharedPreferences.getInt(KEY_TAB_POSITION, 0);
    }
}
