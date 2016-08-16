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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.lhyz.android.dribbble.data.bean.Shot;
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
        shotModel.setTags(tags);
        shotModel.setUpdatedTime(model.getUpdatedTime());
        return shotModel;
    }

    @Override
    public List<ShotModel> transform(List<Shot> collection) {
        List<ShotModel> shotModels;
        if (collection != null && !collection.isEmpty()) {
            shotModels = new ArrayList<>(20);
            for (Shot shot : collection) {
                shotModels.add(transform(shot));
            }
        } else {
            shotModels = Collections.emptyList();
        }
        return shotModels;
    }
}
