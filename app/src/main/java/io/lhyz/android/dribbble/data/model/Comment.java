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
package io.lhyz.android.dribbble.data.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * hello,android
 * Created by lhyz on 2016/8/13.
 */
@SuppressWarnings("unused")
public class Comment extends BaseResponse implements Serializable {
    int id;
    String body;
    @SerializedName("likes_count")
    int likesCount;
    @SerializedName("updated_at")
    String updateTime;

    User user;

    public Comment(int id, String body, int likesCount, String updateTime, User user) {
        this.id = id;
        this.body = body;
        this.likesCount = likesCount;
        this.updateTime = updateTime;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public String getBody() {
        return body;
    }

    public int getLikesCount() {
        return likesCount;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public User getUser() {
        return user;
    }
}
