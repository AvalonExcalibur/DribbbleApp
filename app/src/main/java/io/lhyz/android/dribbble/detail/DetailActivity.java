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
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.drawable.ProgressBarDrawable;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;

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
import io.lhyz.android.dribbble.data.model.Like;
import io.lhyz.android.dribbble.data.model.Shot;
import io.lhyz.android.dribbble.net.ServiceCreator;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * hello,android
 * Created by lhyz on 2016/8/12.
 * <p/>
 * 横竖屏动态模板代码
 */
public class DetailActivity extends BaseActivity {
    public static final String EXTRA_PARAMS_SHOT = "EXTRA_PARAMS_SHOT";
    private static final int TAG_LIKE = 748;

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
    @BindView(R.id.tv_comments_count)
    @Nullable
    TextView tvCommentCount;
    @Nullable
    @BindView(R.id.list_comments)
    RecyclerView mRecyclerView;
    @BindView(R.id.img_author)
    @Nullable
    SimpleDraweeView imgAuthor;
    @BindView(R.id.tv_name)
    @Nullable
    TextView tvUserName;
    @BindView(R.id.tv_update_time)
    @Nullable
    TextView tvUpdateTime;

    DribbbleService mDribbbleService;
    CommentAdapter mAdapter;

    Shot mShot;

    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mShot = (Shot) getIntent().getSerializableExtra(EXTRA_PARAMS_SHOT);

        if (getResources().getConfiguration().orientation ==
                Configuration.ORIENTATION_PORTRAIT) {
            initInPortrait();
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

        Uri uri = Uri.parse(url);
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setUri(uri)
                .setAutoPlayAnimations(true)
                .build();
        mImageView.setController(controller);
    }

    private void initInPortrait() {
        setActionBarFeature();
        setBasicInfo();
        setCommentList();
    }

    @SuppressWarnings("deprecation")
    private void setBasicInfo() {
        if (btnLikes != null && tvDescription != null && mTagContainerLayout != null
                && imgAuthor != null && tvUserName != null && tvUpdateTime != null) {
            mDribbbleService = new ServiceCreator(AppPreference.getInstance().readToken())
                    .createService();

            mDribbbleService.isLikes(mShot.getId())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new DefaultSubscriber<Like>() {
                        @Override
                        public void onSuccess(Like result) {
                            btnLikes.setImageResource(R.drawable.ic_favorite_white_24dp);
                            btnLikes.setTag(TAG_LIKE);
                        }

                        @Override
                        public void onError(Throwable e) {
                        }
                    });
            btnLikes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (view.getTag(TAG_LIKE) == null) {
                        btnLikes.setImageResource(R.drawable.ic_favorite_white_24dp);
                    } else {
                        btnLikes.setImageResource(R.drawable.ic_favorite_outline_white_24dp);
                    }
                }
            });
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

            imgAuthor.setImageURI(Uri.parse(mShot.getUser().getAvatarUrl()));
            tvUserName.setText(mShot.getUser().getName());
            tvUpdateTime.setText(mShot.getUpdatedTime());
        }
    }

    private void setCommentList() {
        if (mRecyclerView != null && tvCommentCount != null) {
            if (mShot.getCommentsCount() > 0) {
                tvCommentCount.setText(mShot.getCommentsCount() + " Comments");

                mAdapter = new CommentAdapter(this);
                mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
                mRecyclerView.addItemDecoration(new DividerItemDecoration(this, getResources().getDisplayMetrics().density));
                mRecyclerView.setNestedScrollingEnabled(false);
                mRecyclerView.setAdapter(mAdapter);


                mDribbbleService.getComments(mShot.getId())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new DefaultSubscriber<List<Comment>>() {
                            @Override
                            public void onSuccess(List<Comment> result) {
                                mAdapter.setCommentList(result);
                            }

                            @Override
                            public void onError(Throwable e) {
                                e.printStackTrace();
                            }
                        });
            } else {
                tvCommentCount.setText("No Comments");
            }
        }
    }

    private void setActionBarFeature() {
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        ActionBar actionBar = getSupportActionBar();
        checkNotNull(actionBar);
        actionBar.setTitle("");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
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


    private static class DividerItemDecoration extends RecyclerView.ItemDecoration {
        private static final int[] ATTRS = new int[]{android.R.attr.listDivider};
        private final Drawable mDivider;
        private float density;

        /**
         * Default divider will be used
         */
        public DividerItemDecoration(Context context, float density) {
            final TypedArray styledAttributes = context.obtainStyledAttributes(ATTRS);
            mDivider = styledAttributes.getDrawable(0);
            styledAttributes.recycle();

            this.density = density;
        }

        @Override
        public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
            int left = parent.getPaddingLeft() + (int) (12 * density);
            int right = parent.getWidth() - parent.getPaddingRight() - (int) (12 * density);

            int childCount = parent.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View child = parent.getChildAt(i);

                RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

                int top = child.getBottom() + params.bottomMargin;
                int bottom = top + mDivider.getIntrinsicHeight();

                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(c);
            }
        }
    }
}
