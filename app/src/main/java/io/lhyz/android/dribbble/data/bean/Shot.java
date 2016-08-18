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
package io.lhyz.android.dribbble.data.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * hello,android
 * Created by lhyz on 2016/8/9.
 */
public class Shot implements Serializable {
    private static final long serialVersionUID = -1546919630642750560L;

    int type;
    long id;
    String title;
    String description;
    Image images;
    @SerializedName("views_count")
    int viewsCount;
    @SerializedName("comments_count")
    int commentsCount;
    User user;
    List<String> tags;
    @SerializedName("created_at")
    String createdTime;

    public Shot(int type, long id, String title, String description, Image images,
                int viewsCount, int commentsCount, User user, List<String> tags,
                String createdTime) {
        this.type = type;
        this.id = id;
        this.title = title;
        this.description = description;
        this.images = images;
        this.viewsCount = viewsCount;
        this.commentsCount = commentsCount;
        this.user = user;
        this.tags = tags;
        this.createdTime = createdTime;
    }

    public int getType() {
        return type;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Image getImages() {
        return images;
    }

    public int getViewsCount() {
        return viewsCount;
    }

    public int getCommentsCount() {
        return commentsCount;
    }

    public User getUser() {
        return user;
    }

    public List<String> getTags() {
        return tags;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public static class Image implements Serializable {
        private static final long serialVersionUID = 4949847134997846173L;

        String hidpi;
        String normal;
        String teaser;

        public Image(String hidpi, String normal, String teaser) {
            this.hidpi = hidpi;
            this.normal = normal;
            this.teaser = teaser;
        }

        public String getHidpi() {
            return hidpi;
        }

        public String getNormal() {
            return normal;
        }

        public String getTeaser() {
            return teaser;
        }
    }
}
