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

import io.lhyz.android.dribbble.data.bean.Comment;
import io.lhyz.android.dribbble.data.bean.User;
import io.lhyz.android.dribbble.data.model.CommentModel;

/**
 * hello,android
 * Created by lhyz on 2016/8/16.
 */
public class CommentModelMapper implements Mapper<Comment, CommentModel> {

    private static final class Holder {
        private static final CommentModelMapper instance = new CommentModelMapper();
    }

    private CommentModelMapper() {

    }

    public static CommentModelMapper getInstance() {
        return Holder.instance;
    }

    @Override
    public Comment transform(CommentModel model) {
        if (model == null) {
            throw new IllegalArgumentException("Cannot transform a null value");
        }
        return new Comment(
                model.getId(),
                model.getBody(),
                model.getLikesCount(),
                model.getUpdateTime(),
                new User(model.getUserName(), model.getUserAvatar())
        );
    }

    @Override
    public List<Comment> transform(List<CommentModel> collection) {
        List<Comment> comments;
        if (collection != null && !collection.isEmpty()) {
            comments = new ArrayList<>(20);
            for (CommentModel comment : collection) {
                comments.add(transform(comment));
            }
        } else {
            comments = Collections.emptyList();
        }
        return comments;
    }
}
