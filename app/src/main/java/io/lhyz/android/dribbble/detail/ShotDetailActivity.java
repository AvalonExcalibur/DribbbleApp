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

import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.drawable.ProgressBarDrawable;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.BasePostprocessor;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.facebook.imagepipeline.request.Postprocessor;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import co.lujun.androidtagview.TagContainerLayout;
import io.lhyz.android.boilerplate.interactor.DefaultSubscriber;
import io.lhyz.android.dribbble.AppPreference;
import io.lhyz.android.dribbble.R;
import io.lhyz.android.dribbble.base.BaseActivity;
import io.lhyz.android.dribbble.data.DribbbleService;
import io.lhyz.android.dribbble.data.model.Comment;
import io.lhyz.android.dribbble.data.model.Shot;
import io.lhyz.android.dribbble.net.ServiceCreator;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * hello,android
 * Created by lhyz on 2016/8/12.
 * <p>
 * 横竖屏动态模板代码
 */
public class ShotDetailActivity extends BaseActivity {
    public static final String EXTRA_PARAMS_SHOT = "EXTRA_PARAMS_SHOT";

    @BindView(R.id.img_shot)
    SimpleDraweeView mImageView;
    @BindView(R.id.tv_description)
    @Nullable
    TextView tvDescription;
    @BindView(R.id.action_likes)
    @Nullable
    FloatingActionButton btnLikes;
    @BindView(R.id.tags_view)
    @Nullable
    TagContainerLayout mTagContainerLayout;
    @BindView(R.id.toolbar)
    @Nullable
    Toolbar mToolbar;

    DribbbleService mDribbbleService;

    Shot mShot;

    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mShot = (Shot) getIntent().getSerializableExtra(EXTRA_PARAMS_SHOT);

        if (getResources().getConfiguration().orientation ==
                Configuration.ORIENTATION_PORTRAIT) {
            if (btnLikes != null && tvDescription != null && mTagContainerLayout != null) {
                setActionBarTitle(mShot.getTitle());
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
            }
        }

        Shot.Image image = mShot.getImages();
        String url = image.getHidpi() == null ?
                (image.getNormal() == null ? image.getTeaser() : image.getNormal()) :
                image.getHidpi();

        GenericDraweeHierarchyBuilder builder =
                GenericDraweeHierarchyBuilder.newInstance(getResources());
        GenericDraweeHierarchy hierarchy =
                builder.setProgressBarImage(new ProgressBarDrawable())
                        .build();
        mImageView.setHierarchy(hierarchy);

        Postprocessor postprocessor = new BasePostprocessor() {
            @Override
            public void process(Bitmap bitmap) {

                super.process(bitmap);
            }
        };
        Uri uri = Uri.parse(url);
        ImageRequest imageRequest = ImageRequestBuilder.newBuilderWithSource(uri)
                .setPostprocessor(postprocessor)
                .build();
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setImageRequest(imageRequest)
                .setUri(uri)
                .setAutoPlayAnimations(true)
                .build();
        mImageView.setController(controller);


        mDribbbleService = new ServiceCreator(AppPreference.getInstance().readToken())
                .createService();

        mDribbbleService.getComments(mShot.getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultSubscriber<List<Comment>>() {
                    @Override
                    public void onSuccess(List<Comment> result) {
                        super.onSuccess(result);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }
                });
    }

    private void setActionBarTitle(CharSequence title) {
        setSupportActionBar(mToolbar);

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

    @Override
    protected void setWindowFeature() {
        if (getResources().getConfiguration().orientation ==
                Configuration.ORIENTATION_PORTRAIT) {
            super.setWindowFeature();
        } else {
            /**
             * 在此activity中{@link #requestWindowFeature(int)} 不起作用,
             * AppCompatActivity中需要使用{@link #supportRequestWindowFeature(int)}
             */
            supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }

    }
}
