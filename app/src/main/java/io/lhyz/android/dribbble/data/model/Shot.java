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
import java.util.List;

/**
 * hello,android
 * Created by lhyz on 2016/8/9.
 */
@SuppressWarnings("unused")
public class Shot extends BaseResponse implements Serializable {
    int id;
    String title;
    String description;
    int width;
    int height;
    Image images;
    @SerializedName("views_count")
    int viewsCount;
    @SerializedName("likes_count")
    int likesCount;
    @SerializedName("comments_count")
    int commentsCount;
    User user;
    List<String> tags;
    @SerializedName("updated_at")
    String updatedTime;

    public Shot(int id, String title,
                String description, int width, int height,
                Image images, User author, List<String> tags) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.width = width;
        this.height = height;
        this.images = images;
        this.user = author;
        this.tags = tags;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Image getImages() {
        return images;
    }

    public User getUser() {
        return user;
    }

    public List<String> getTags() {
        return tags;
    }

    public int getViewsCount() {
        return viewsCount;
    }

    public int getLikesCount() {
        return likesCount;
    }

    public String getUpdatedTime() {
        return updatedTime;
    }

    public int getCommentsCount() {
        return commentsCount;
    }

    public static class Image implements Serializable {
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
