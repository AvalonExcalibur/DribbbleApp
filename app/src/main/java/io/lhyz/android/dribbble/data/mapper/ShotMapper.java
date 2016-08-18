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
package io.lhyz.android.dribbble.data.mapper;

import android.text.TextUtils;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import io.lhyz.android.dribbble.data.bean.Shot;
import io.lhyz.android.dribbble.data.bean.User;
import io.lhyz.android.dribbble.data.model.ShotModel;

/**
 * hello,android
 * Created by lhyz on 2016/8/16.
 */
public class ShotMapper implements Mapper<ShotModel, Shot> {

    private static final class Holder {
        private static final ShotMapper instance = new ShotMapper();
    }

    private ShotMapper() {

    }

    public static ShotMapper getInstance() {
        return Holder.instance;
    }

    @Override
    public ShotModel transform(Shot model) {
        ShotModel shotModel = new ShotModel();
        shotModel.setType(model.getType());
        shotModel.setId(model.getId());
        shotModel.setTitle(model.getTitle());
        shotModel.setDescription(model.getDescription());
        shotModel.setHiDPI(model.getImages().getHidpi());
        shotModel.setNormalDPI(model.getImages().getNormal());
        shotModel.setTeaserDPI(model.getImages().getTeaser());
        shotModel.setViewsCount(model.getViewsCount());
        shotModel.setCommentsCount(model.getCommentsCount());
        shotModel.setUserName(model.getUser().getName());
        shotModel.setUserAvatar(model.getUser().getAvatarUrl());
        String tags = "";
        if (model.getTags() != null && model.getTags().size() != 0) {
            for (String tag : model.getTags()) {
                tags += tag + '|';
            }
        }
        if (TextUtils.isEmpty(tags)) {
            shotModel.setTags("");
        } else {
            tags = tags.substring(0, tags.length() - 1);
            shotModel.setTags(tags);
        }
        shotModel.setCreatedTime(new DateTime(model.getCreatedTime()).toDate());
        return shotModel;
    }

    @Override
    public List<ShotModel> transform(List<Shot> list) {
        List<ShotModel> shotModels;
        if (list != null && !list.isEmpty()) {
            shotModels = new ArrayList<>(20);
            for (Shot shot : list) {
                shotModels.add(transform(shot));
            }
        } else {
            shotModels = Collections.emptyList();
        }
        return shotModels;
    }

    @Override
    public Shot convert(ShotModel type) {
        List<String> tags;
        if (TextUtils.isEmpty(type.getTags())) {
            tags = Collections.emptyList();
        } else {
            tags = new ArrayList<>(20);
            List<String> tag_list = Arrays.asList(type.getTags().split("\\|"));
            tags.addAll(tag_list);
        }
        return new Shot(
                type.getType(),
                type.getId(),
                type.getTitle(),
                type.getDescription(),
                new Shot.Image(type.getHiDPI(), type.getNormalDPI(), type.getTeaserDPI()),
                type.getViewsCount(),
                type.getCommentsCount(),
                new User(type.getUserName(), type.getUserAvatar()),
                tags,
                new DateTime(type.getCreatedTime()).toDateTimeISO().toString()
        );
    }

    @Override
    public List<Shot> convert(List<ShotModel> list) {
        List<Shot> shots;
        if (list != null && list.size() != 0) {
            shots = new ArrayList<>(20);
            for (ShotModel shotModel : list) {
                shots.add(convert(shotModel));
            }
        } else {
            shots = Collections.emptyList();
        }
        return shots;
    }
}
