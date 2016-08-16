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

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * hello,android
 * Created by lhyz on 2016/8/16.
 */
@DatabaseTable(tableName = "shots")
public class ShotModel implements Serializable {
    private static final long serialVersionUID = -6504143955460827956L;

    //自动生成主键（取了id的全拼作为区别）
    @DatabaseField(generatedId = true)
    long identity;

    //shot type
    @DatabaseField
    int type;
    //由于在不同列表中可能出现同一个shot，所以这里我不敢用作主键
    @DatabaseField
    long id;
    @DatabaseField
    String title;
    @DatabaseField
    String description;
    @DatabaseField
    String hiDPI;
    @DatabaseField
    String normalDPI;
    @DatabaseField
    String teaserDPI;
    @DatabaseField
    int viewsCount;
    @DatabaseField
    int commentsCount;
    @DatabaseField
    String userName;
    @DatabaseField
    String userAvatar;
    //使用|作为分隔符保存一串tags
    @DatabaseField
    String tags;
    @DatabaseField
    String updatedTime;

    public ShotModel() {
        //For OrmLite
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setHiDPI(String hiDPI) {
        this.hiDPI = hiDPI;
    }

    public void setNormalDPI(String normalDPI) {
        this.normalDPI = normalDPI;
    }

    public void setTeaserDPI(String teaserDPI) {
        this.teaserDPI = teaserDPI;
    }

    public void setViewsCount(int viewsCount) {
        this.viewsCount = viewsCount;
    }

    public void setCommentsCount(int commentsCount) {
        this.commentsCount = commentsCount;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public void setUpdatedTime(String updatedTime) {
        this.updatedTime = updatedTime;
    }

    public int getType() {
        return type;
    }

    public long getIdentity() {
        return identity;
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

    public String getHiDPI() {
        return hiDPI;
    }

    public String getNormalDPI() {
        return normalDPI;
    }

    public String getTeaserDPI() {
        return teaserDPI;
    }

    public int getViewsCount() {
        return viewsCount;
    }

    public int getCommentsCount() {
        return commentsCount;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public String getTags() {
        return tags;
    }

    public String getUpdatedTime() {
        return updatedTime;
    }
}
