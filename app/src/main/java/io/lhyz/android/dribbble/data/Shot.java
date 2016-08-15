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
package io.lhyz.android.dribbble.data;

import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.util.List;

/**
 * hello,android
 * Created by lhyz on 2016/8/9.
 */
@DatabaseTable(tableName = "shots")
public class Shot implements Serializable {
    private static final long serialVersionUID = -1546919630642750560L;

    //shot type
    @DatabaseField
    int type;

    //自动生成主键（取了id的全拼作为区别）
    @DatabaseField(generatedId = true)
    @SuppressWarnings("unused")
    long identity;

    //由于在不同列表中可能出现同一个shot，所以这里我不敢用作主键
    @DatabaseField
    long id;
    @DatabaseField
    String title;
    @DatabaseField
    String description;
    @DatabaseField
    Image images;
    @DatabaseField
    @SerializedName("views_count")
    int viewsCount;
    @DatabaseField
    @SerializedName("comments_count")
    int commentsCount;
    @DatabaseField
    User user;
    @ForeignCollectionField
    List<String> tags;
    @DatabaseField
    @SerializedName("updated_at")
    String updatedTime;

    public Shot() {
        //For ORMLite
    }

    public void setType(int type) {
        this.type = type;
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

    public User getUser() {
        return user;
    }

    public List<String> getTags() {
        return tags;
    }

    public int getViewsCount() {
        return viewsCount;
    }

    public String getUpdatedTime() {
        return updatedTime;
    }

    public int getCommentsCount() {
        return commentsCount;
    }

    public static class Image implements Serializable {
        private static final long serialVersionUID = 8243845973394454688L;

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
