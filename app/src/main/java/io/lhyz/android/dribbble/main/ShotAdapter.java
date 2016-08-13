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
package io.lhyz.android.dribbble.main;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import io.lhyz.android.dribbble.R;
import io.lhyz.android.dribbble.data.model.Shot;

/**
 * hello,android
 * Created by lhyz on 2016/8/12.
 */
public class ShotAdapter extends RecyclerView.Adapter<ShotAdapter.ShotViewHolder> {

    Context mContext;
    LayoutInflater mInflater;
    List<Shot> mShots;
    OnShotClickListener mOnShotClickListener;

    public ShotAdapter(Context context, OnShotClickListener onShotClickListener) {
        mContext = context;
        mOnShotClickListener = onShotClickListener;
        mInflater = LayoutInflater.from(mContext);
        mShots = new ArrayList<>(0);
    }

    public void setShotList(@NonNull List<Shot> shots) {
        if (mShots.size() != 0) {
            mShots.clear();
        }
        mShots.addAll(shots);

        notifyDataSetChanged();
    }

    public static final class ShotViewHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView imgArt;
        SimpleDraweeView imgAuthor;
        TextView tvName;
        TextView tvViewsCount;

        View itemView;

        public ShotViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            imgArt = (SimpleDraweeView) itemView.findViewById(R.id.img_art);
            imgAuthor = (SimpleDraweeView) itemView.findViewById(R.id.img_author);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            tvViewsCount = (TextView) itemView.findViewById(R.id.views_count);
        }
    }

    @Override
    public ShotViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ShotViewHolder(mInflater.inflate(R.layout.item_shot, parent, false));
    }

    @Override
    public void onBindViewHolder(ShotViewHolder holder, int position) {
        final View view = holder.itemView;
        final int pos = holder.getAdapterPosition();
        final Shot shot = mShots.get(pos);

        final SimpleDraweeView imgArt = holder.imgArt;
        imgArt.setImageURI(Uri.parse(shot.getImages().getNormal()));

        final ImageView imgAuthor = holder.imgAuthor;
        imgAuthor.setImageURI(Uri.parse(shot.getUser().getAvatarUrl()));

        holder.tvName.setText(shot.getUser().getName());
        holder.tvViewsCount.setText("" + shot.getViewsCount());

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnShotClickListener.onShotClick(view, shot);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mShots.size();
    }
}
