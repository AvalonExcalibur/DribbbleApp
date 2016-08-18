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

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.util.Date;

/**
 * hello,android
 * Created by lhyz on 2016/8/16.
 */
@DatabaseTable(tableName = "comments")
public class CommentModel implements Serializable {
    private static final long serialVersionUID = -9066176212611645547L;

    //shot Id for this comment
    @DatabaseField
    long shotId;
    @DatabaseField(id = true)
    long id;
    @DatabaseField
    String body;
    @DatabaseField
    int likesCount;
    @DatabaseField(dataType = DataType.DATE)
    Date updateTime;
    @DatabaseField
    String userName;
    @DatabaseField
    String userAvatar;

    public CommentModel() {
        //For OrmLite
    }

    public void setShotId(long shotId) {
        this.shotId = shotId;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setLikesCount(int likesCount) {
        this.likesCount = likesCount;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }

    public long getShotId() {
        return shotId;
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

    public Date getUpdateTime() {
        return updateTime;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserAvatar() {
        return userAvatar;
    }
}
