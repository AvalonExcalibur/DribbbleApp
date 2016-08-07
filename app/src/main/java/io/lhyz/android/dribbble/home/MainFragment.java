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
package io.lhyz.android.dribbble.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import io.lhyz.android.dribbble.R;
import io.lhyz.android.dribbble.base.BaseFragment;
import io.lhyz.android.dribbble.data.model.User;
import io.lhyz.android.dribbble.login.AuthActivity;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * hello,android
 * Created by lhyz on 2016/8/7.
 */
public class MainFragment extends BaseFragment implements MainContract.View {

    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.popular_list)
    RecyclerView mRecyclerView;
    @BindView(R.id.no_shots)
    View mEmptyView;

    ImageView imgAvatar;
    TextView tvFollowers;
    TextView tvFollowing;
    TextView tvLikes;

    MainContract.Presenter mPresenter;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter.start();

        NavigationView navigationView = (NavigationView) getActivity().findViewById(R.id.nav_view);
        View header = navigationView.getHeaderView(0);
        imgAvatar = (ImageView) header.findViewById(R.id.img_avatar);
        tvFollowers = (TextView) header.findViewById(R.id.tv_followers);
        tvFollowing = (TextView) header.findViewById(R.id.tv_followings);
        tvLikes = (TextView) header.findViewById(R.id.tv_likes);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        showEmptyView(true);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mPresenter.result(requestCode, resultCode, data);
    }

    @Override
    protected int getLayout() {
        return R.layout.frag_main;
    }

    @Override
    public void showError(String message) {
        Snackbar.make(getActivity().findViewById(R.id.main_container), message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showLoading(boolean shown) {
        mSwipeRefreshLayout.setRefreshing(shown);
    }

    @Override
    public void setPresenter(MainContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void redirectToLogin() {
        startActivityForResult(new Intent(getActivity(), AuthActivity.class), AuthActivity.REQUEST_AUTH);
    }

    @Override
    public void showUser(User user) {
        Glide.with(getContext()).load(user.getAvatarUrl())
                .bitmapTransform(new CropCircleTransformation(getContext()))
                .into(imgAvatar);
        tvFollowers.setText("" + user.getFollowers());
        tvFollowing.setText("" + user.getFollowings());
        tvLikes.setText("" + user.getLikes());

        DrawerLayout layout = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
        layout.openDrawer(GravityCompat.START);
    }

    @Override
    public void showPopularList() {

    }

    @Override
    public void showEmptyView(boolean shown) {
        if (shown) {
            mEmptyView.setVisibility(View.VISIBLE);
        } else {
            mEmptyView.setVisibility(View.GONE);
        }
    }
}
