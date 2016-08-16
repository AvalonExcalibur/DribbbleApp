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
public class ShotModelMapper implements Mapper<Shot, ShotModel> {

    private static final class Holder {
        private static final ShotModelMapper instance = new ShotModelMapper();
    }

    private ShotModelMapper() {

    }

    public static ShotModelMapper getInstance() {
        return Holder.instance;
    }

    @Override
    public Shot transform(ShotModel model) {
        List<String> tags = new ArrayList<>(20);
        tags.addAll(Arrays.asList(model.getTags().split("|")));
        return new Shot(
                model.getType(),
                model.getId(),
                model.getTitle(),
                model.getDescription(),
                new Shot.Image(model.getHiDPI(), model.getNormalDPI(), model.getTeaserDPI()),
                model.getViewsCount(),
                model.getCommentsCount(),
                new User(model.getUserName(), model.getUserAvatar()),
                tags,
                model.getUpdatedTime()
        );
    }

    @Override
    public List<Shot> transform(List<ShotModel> collection) {
        List<Shot> shots;
        if (collection != null && collection.size() != 0) {
            shots = new ArrayList<>(20);
            for (ShotModel shotModel : collection) {
                shots.add(transform(shotModel));
            }
        } else {
            shots = Collections.emptyList();
        }
        return shots;
    }
}
