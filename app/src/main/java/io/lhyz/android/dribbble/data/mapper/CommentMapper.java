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
public class CommentMapper implements Mapper<CommentModel, Comment> {

    private static final class Holder {
        private static final CommentMapper instance = new CommentMapper();
    }

    private CommentMapper() {

    }

    public static CommentMapper getInstance() {
        return Holder.instance;
    }

    @Override
    public CommentModel transform(Comment model) {
        if (model == null) {
            throw new IllegalArgumentException("Cannot transform a null value");
        }
        CommentModel commentModel = new CommentModel();
        commentModel.setShotId(model.getShotId());
        commentModel.setId(model.getId());
        commentModel.setBody(model.getBody());
        commentModel.setLikesCount(model.getLikesCount());
        commentModel.setUpdateTime(model.getUpdateTime());
        commentModel.setUserName(model.getUser().getName());
        commentModel.setUserAvatar(model.getUser().getAvatarUrl());
        return commentModel;
    }

    @Override
    public List<CommentModel> transform(List<Comment> list) {
        List<CommentModel> commentModels;
        if (list != null && !list.isEmpty()) {
            commentModels = new ArrayList<>(20);
            for (Comment comment : list) {
                commentModels.add(transform(comment));
            }
        } else {
            commentModels = Collections.emptyList();
        }
        return commentModels;
    }

    @Override
    public Comment convert(CommentModel type) {
        if (type == null) {
            throw new IllegalArgumentException("Cannot transform a null value");
        }
        return new Comment(
                type.getId(),
                type.getBody(),
                type.getLikesCount(),
                type.getUpdateTime(),
                new User(type.getUserName(), type.getUserAvatar())
        );
    }

    @Override
    public List<Comment> convert(List<CommentModel> list) {
        List<Comment> comments;
        if (list != null && !list.isEmpty()) {
            comments = new ArrayList<>(20);
            for (CommentModel comment : list) {
                comments.add(convert(comment));
            }
        } else {
            comments = Collections.emptyList();
        }
        return comments;
    }
}
