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

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.ActionBar;
import android.text.Html;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.drawable.ProgressBarDrawable;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.Collections;

import butterknife.BindView;
import co.lujun.androidtagview.TagContainerLayout;
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
    SimpleDraweeView mImageView;
    @BindView(R.id.tv_description)
    TextView tvDescription;
    @BindView(R.id.action_likes)
    Button btnLikes;
    @BindView(R.id.action_comments)
    Button btnComments;

    @BindView(R.id.tags_view)
    TagContainerLayout mTagContainerLayout;

    Shot mShot;

    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewCompat.setTransitionName(mImageView, VIEW_NAME_IMG);

        mShot = (Shot) getIntent().getSerializableExtra(EXTRA_PARAMS_SHOT);
        setActionBarTitle(mShot.getTitle());
        btnLikes.setText("" + mShot.getLikesCount());
        btnComments.setText("" + mShot.getCommentsCount());
        if (mShot.getDescription() != null) {
            tvDescription.setText(Html.fromHtml(mShot.getDescription()));
        } else {
            tvDescription.setText("No Description");
        }
        if (mShot.getTags() != null) {
            mTagContainerLayout.setTags(mShot.getTags());
        } else {
            mTagContainerLayout.setTags(Collections.singletonList("No Tag"));
        }

        Shot.Image image = mShot.getImages();
        String url = image.getHidpi() == null ?
                (image.getNormal() == null ? image.getTeaser() : image.getNormal()) :
                image.getHidpi();

        GenericDraweeHierarchyBuilder builder = GenericDraweeHierarchyBuilder.newInstance(getResources());
        GenericDraweeHierarchy hierarchy = builder.setProgressBarImage(new ProgressBarDrawable())
                .build();
        mImageView.setHierarchy(hierarchy);
        Uri uri = Uri.parse(url);
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setUri(uri)
                .setAutoPlayAnimations(true)
                .build();
        mImageView.setController(controller);
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
