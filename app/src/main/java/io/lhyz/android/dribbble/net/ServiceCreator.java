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
package io.lhyz.android.dribbble.net;

import io.lhyz.android.dribbble.AppPreference;
import io.lhyz.android.dribbble.data.DribbbleService;

/**
 * hello,android
 * Created by lhyz on 2016/8/10.
 */
public class ServiceCreator {
    private static class Holder {
        private static final ServiceCreator creator = new ServiceCreator();
    }

    private final String token;

    public ServiceCreator() {
        this.token = AppPreference.getInstance().readToken();
    }

    public static ServiceCreator getInstance() {
        return Holder.creator;
    }

    public DribbbleService createService() {
        AuthorizationInterceptor interceptorManager = new AuthorizationInterceptor(token);
        RetrofitManager retrofitManager = new RetrofitManager(interceptorManager);
        return retrofitManager.buildRetrofit().create(DribbbleService.class);
    }
}
