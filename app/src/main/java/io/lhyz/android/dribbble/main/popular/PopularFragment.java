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
package io.lhyz.android.dribbble.main.popular;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.lhyz.android.dribbble.R;
import io.lhyz.android.dribbble.base.BaseFragment;
import io.lhyz.android.dribbble.data.model.Shot;
import io.lhyz.android.dribbble.view.ScrollChildSwipeRefreshLayout;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * hello,android
 * Created by lhyz on 2016/8/8.
 */
public class PopularFragment extends BaseFragment implements PopularContract.View {
    @BindView(R.id.refresh_layout)
    ScrollChildSwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.recycler_list)
    RecyclerView mRecyclerView;
    @BindView(R.id.no_items)
    View mEmptyView;

    PopularContract.Presenter mPresenter;
    PopularAdapter mAdapter;

    public static PopularFragment newInstance() {
        return new PopularFragment();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mSwipeRefreshLayout.setColorSchemeColors(
                ContextCompat.getColor(getContext(), R.color.color_pink),
                ContextCompat.getColor(getContext(), R.color.color_pro),
                ContextCompat.getColor(getContext(), R.color.color_teams));
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.loadPopular();
            }
        });

        mAdapter = new PopularAdapter(getContext(), mShotItemListener);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mAdapter);

        mSwipeRefreshLayout.setScrollUpChild(mRecyclerView);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    protected int getLayout() {
        return R.layout.frag_list;
    }


    @Override
    public void showLoading() {
        mSwipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideLoading() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showEmptyView() {
        mEmptyView.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.GONE);

        TextView description = (TextView) mEmptyView.findViewById(R.id.description);
        description.setText("No Populars");
    }

    @Override
    public void showError(String message) {
        Snackbar.make(mEmptyView, message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showPopular(List<Shot> shots) {
        mEmptyView.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);

        mAdapter.setPopularList(shots);
    }

    @Override
    public void setPresenter(PopularContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    private static class PopularAdapter extends RecyclerView.Adapter<PopularAdapter.PopularViewHolder> {
        Context mContext;
        LayoutInflater mInflater;
        List<Shot> mShots;
        ShotItemListener mShotItemListener;

        public PopularAdapter(Context context, ShotItemListener listener) {
            mContext = context;
            mShotItemListener = listener;
            mInflater = LayoutInflater.from(context);
            mShots = new ArrayList<>(0);
        }

        static class PopularViewHolder extends RecyclerView.ViewHolder {
            ImageView imgArt;
            ImageView imgAuthor;
            TextView tvName;

            public PopularViewHolder(View itemView) {
                super(itemView);
                imgArt = (ImageView) itemView.findViewById(R.id.img_art);
                imgAuthor = (ImageView) itemView.findViewById(R.id.img_author);
                tvName = (TextView) itemView.findViewById(R.id.tv_name);
            }
        }

        public void setPopularList(@NonNull List<Shot> shots) {
            if (mShots.size() != 0) {
                mShots.clear();
            }
            mShots.addAll(shots);

            notifyDataSetChanged();
        }

        @Override
        public PopularViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new PopularViewHolder(mInflater.inflate(R.layout.item_popular, parent, false));
        }

        @Override
        public void onBindViewHolder(PopularViewHolder holder, int position) {
            final int pos = holder.getAdapterPosition();
            final Shot shot = mShots.get(pos);

            //Glide默认解决了列表重用下的ImageView设置混乱
            final ImageView imgArt = holder.imgArt;
            Glide.with(mContext).load(shot.getImages().getNormal()).into(imgArt);

            final ImageView imgAuthor = holder.imgAuthor;
            Glide.with(mContext).load(shot.getUser().getAvatarUrl())
                    .bitmapTransform(new CropCircleTransformation(mContext))
                    .into(imgAuthor);

            holder.tvName.setText(shot.getUser().getName());

            imgArt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mShotItemListener.onShotClick(shot);
                }
            });
        }

        @Override
        public int getItemCount() {
            return mShots.size();
        }
    }

    ShotItemListener mShotItemListener = new ShotItemListener() {
        @Override
        public void onShotClick(Shot shot) {
            Log.d("TAG", "" + shot.getId());
        }
    };

    interface ShotItemListener {
        void onShotClick(Shot shot);
    }
}
