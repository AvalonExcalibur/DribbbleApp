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

/**
 * hello,android
 * Created by lhyz on 2016/8/7.
 */
public class User extends BaseResponse {
    long id;
    String name;
    String username;
    @SerializedName("avatar_url")
    String avatarUrl;
    String location;

    @SerializedName("buckets_count")
    int buckets;
    @SerializedName("followers_count")
    int followers;
    @SerializedName("followings_count")
    int followings;
    @SerializedName("likes_count")
    int likes;
    @SerializedName("projects_count")
    int projects;
    @SerializedName("shots_count")
    int shots;

    String buckets_url;
    String followers_url;
    String likes_url;
    String shots_url;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public String getLocation() {
        return location;
    }

    public int getBuckets() {
        return buckets;
    }

    public int getFollowers() {
        return followers;
    }

    public int getFollowings() {
        return followings;
    }

    public int getLikes() {
        return likes;
    }

    public int getProjects() {
        return projects;
    }

    public int getShots() {
        return shots;
    }

    public String getBuckets_url() {
        return buckets_url;
    }

    public String getFollowers_url() {
        return followers_url;
    }

    public String getLikes_url() {
        return likes_url;
    }

    public String getShots_url() {
        return shots_url;
    }
}
