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
package io.lhyz.android.dribbble.data.remote;

import android.support.annotation.NonNull;

import com.orhanobut.logger.Logger;

import java.util.List;

import io.lhyz.android.boilerplate.interactor.DefaultSubscriber;
import io.lhyz.android.dribbble.AppPreference;
import io.lhyz.android.dribbble.data.DataSource;
import io.lhyz.android.dribbble.data.DribbbleService;
import io.lhyz.android.dribbble.data.model.Comment;
import io.lhyz.android.dribbble.net.ServiceCreator;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * hello,android
 * Created by lhyz on 2016/8/14.
 */
public class RemoteDataSource implements DataSource {

    private static final String TAG = RemoteDataSource.class.getSimpleName();

    private static class Holder {
        private static final RemoteDataSource instance = new RemoteDataSource();
    }

    DribbbleService mDribbbleService;

    public RemoteDataSource() {
        mDribbbleService = new ServiceCreator(AppPreference.getInstance().readToken())
                .createService();
    }

    public static RemoteDataSource getInstance() {
        return Holder.instance;
    }

    @Override
    public void loadComments(long shotId, boolean force, @NonNull final LoadCommentCallback callback) {
        mDribbbleService.getComments(shotId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultSubscriber<List<Comment>>() {
                    @Override
                    public void onSuccess(List<Comment> result) {
                        if (result.size() == 0) {
                            callback.onNoComments();
                            return;
                        }
                        callback.onLoadedComments(result);
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e != null) {
                            Logger.d(TAG, e.getMessage());
                        }
                        callback.onNoComments();
                    }
                });
    }

    @Override
    public void saveComments(long shotId, @NonNull List<Comment> comments) {
        //Not Available
    }
}
