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
package io.lhyz.android.dribbble.detail;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import io.lhyz.android.dribbble.R;
import io.lhyz.android.dribbble.data.bean.Comment;

/**
 * hello,android
 * Created by lhyz on 2016/8/14.
 */
public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {

    private final List<Comment> mComments;
    private final LayoutInflater mLayoutInflater;

    public CommentAdapter(Context context) {
        mComments = new ArrayList<>(0);
        mLayoutInflater = LayoutInflater.from(context);
    }

    public void setCommentList(List<Comment> data) {
        if (mComments.size() > 0) {
            mComments.clear();
        }
        mComments.addAll(data);
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView draweeView;
        TextView tvUserName;
        TextView tvUpdateTime;
        TextView tvLikesCount;
        TextView tvCommentBody;

        public ViewHolder(View itemView) {
            super(itemView);
            draweeView = (SimpleDraweeView) itemView.findViewById(R.id.img_avatar);
            tvUserName = (TextView) itemView.findViewById(R.id.tv_username);
            tvUpdateTime = (TextView) itemView.findViewById(R.id.tv_update_time);
            tvLikesCount = (TextView) itemView.findViewById(R.id.tv_likes_count);
            tvCommentBody = (TextView) itemView.findViewById(R.id.tv_comment_body);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mLayoutInflater.inflate(R.layout.item_comments, parent, false));
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final int pos = holder.getAdapterPosition();
        final Comment comment = mComments.get(pos);

        holder.draweeView.setImageURI(Uri.parse(comment.getUser().getAvatarUrl()));

        holder.tvUserName.setText(comment.getUser().getName());
        holder.tvUpdateTime.setText(comment.getUpdateTime());
        holder.tvLikesCount.setText("" + comment.getLikesCount());
        holder.tvCommentBody.setText(Html.fromHtml(comment.getBody()));
    }

    @Override
    public int getItemCount() {
        return mComments.size();
    }
}
