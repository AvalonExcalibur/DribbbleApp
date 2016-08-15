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
package io.lhyz.android.dribbble.main.playoffs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import io.lhyz.android.dribbble.R;
import io.lhyz.android.dribbble.base.BaseFragment;
import io.lhyz.android.dribbble.data.Shot;
import io.lhyz.android.dribbble.main.OnShotClickListener;
import io.lhyz.android.dribbble.main.ShotAdapter;
import io.lhyz.android.dribbble.navigation.Navigator;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * hello,android
 * Created by lhyz on 2016/8/9.
 */
public class PlayoffsFragment extends BaseFragment implements PlayoffsContract.View {

    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.recycler_list)
    RecyclerView mRecyclerView;
    @BindView(R.id.no_items)
    View mEmptyView;

    PlayoffsContract.Presenter mPresenter;
    ShotAdapter mAdapter;

    public static PlayoffsFragment newInstance() {
        return new PlayoffsFragment();
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
                mPresenter.loadPlayoffs();
            }
        });

        mAdapter = new ShotAdapter(getContext(), mOnShotClickListener);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.pause();
    }

    @Override
    protected int getLayout() {
        return R.layout.frag_list;
    }


    @Override
    public void showPlayoffs(List<Shot> shots) {
        mEmptyView.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);

        mAdapter.setShotList(shots);
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
        description.setText("No Playoffs");
    }

    @Override
    public void showError(String message) {
        Snackbar.make(mEmptyView, message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void setPresenter(PlayoffsContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    private final OnShotClickListener mOnShotClickListener = new OnShotClickListener() {
        @Override
        public void onShotClick(Shot shot) {
            Navigator.startShotDetailsActivity(getActivity(), shot);
        }
    };
}
