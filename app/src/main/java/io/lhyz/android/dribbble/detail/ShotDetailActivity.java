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

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import butterknife.BindView;
import io.lhyz.android.dribbble.R;
import io.lhyz.android.dribbble.base.BaseActivity;
import io.lhyz.android.dribbble.data.model.Shot;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * hello,android
 * Created by lhyz on 2016/8/12.
 */
public class ShotDetailActivity extends BaseActivity {
    public static final String EXTRA_PARAMS_SHOT = "EXTRA_PARAMS_SHOT";
    public static final String VIEW_NAME_IMG = "VIEW_NAME_IMG";

    @BindView(R.id.img_shot)
    ImageView mImageView;
    @BindView(R.id.progress_loading)
    ProgressBar mProgressBar;

    @BindView(R.id.action_views)
    ImageButton ibViewsCount;
    @BindView(R.id.action_likes)
    ImageButton ibLikesCount;
    @BindView(R.id.action_comments)
    ImageButton ibCommentsCount;

    Shot mShot;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewCompat.setTransitionName(mImageView, VIEW_NAME_IMG);

        mShot = (Shot) getIntent().getSerializableExtra(EXTRA_PARAMS_SHOT);
        setActionBarTitle(mShot.getTitle());

        Shot.Image image = mShot.getImages();
        String url = image.getHidpi() == null ?
                (image.getNormal() == null ? image.getTeaser() : image.getNormal()) :
                image.getHidpi();
        mProgressBar.setVisibility(View.VISIBLE);
        Glide.with(this)
                .load(url)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        mProgressBar.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        mProgressBar.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(mImageView);
    }

    private void setActionBarTitle(CharSequence title) {
        ActionBar actionBar = getSupportActionBar();
        checkNotNull(actionBar);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setTitle(title);
    }

    @Override
    protected int getLayout() {
        return R.layout.act_detail_shot;
    }
}
