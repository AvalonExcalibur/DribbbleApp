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
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * hello,android
 * Created by lhyz on 2016/8/13.
 */
@DatabaseTable(tableName = "comments")
public class Comment implements Serializable {
    private static final long serialVersionUID = -7279496175692649917L;

    //shot Id for this comment( not exist in json data)
    @DatabaseField
    long shotId;

    @DatabaseField(id = true)
    long id;
    @DatabaseField
    String body;
    @SerializedName("likes_count")
    @DatabaseField
    int likesCount;
    @DatabaseField
    @SerializedName("updated_at")
    String updateTime;
    @DatabaseField(dataType = DataType.SERIALIZABLE)
    User user;

    public Comment() {
        //For ORMLite
    }

    /**
     * 作为临时添加的comment使用
     */
    public Comment(String body) {
        this.body = body;
    }

    public void setShotId(long shotId) {
        this.shotId = shotId;
    }

    public long getId() {
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
