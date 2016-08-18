package io.lhyz.android.dribbble.main;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;

import butterknife.BindView;
import io.lhyz.android.dribbble.AppPreference;
import io.lhyz.android.dribbble.AppStart;
import io.lhyz.android.dribbble.R;
import io.lhyz.android.dribbble.base.DefaultSubscriber;
import io.lhyz.android.dribbble.base.app.BaseActivity;
import io.lhyz.android.dribbble.data.bean.User;
import io.lhyz.android.dribbble.data.DribbbleService;
import io.lhyz.android.dribbble.main.debut.DebutFragment;
import io.lhyz.android.dribbble.main.debut.DebutPresenter;
import io.lhyz.android.dribbble.main.following.FollowingFragment;
import io.lhyz.android.dribbble.main.following.FollowingPresenter;
import io.lhyz.android.dribbble.main.playoffs.PlayoffsFragment;
import io.lhyz.android.dribbble.main.playoffs.PlayoffsPresenter;
import io.lhyz.android.dribbble.main.popular.PopularFragment;
import io.lhyz.android.dribbble.main.popular.PopularPresenter;
import io.lhyz.android.dribbble.main.recent.RecentFragment;
import io.lhyz.android.dribbble.main.recent.RecentPresenter;
import io.lhyz.android.dribbble.main.team.TeamFragment;
import io.lhyz.android.dribbble.main.team.TeamPresenter;
import io.lhyz.android.dribbble.net.DribbbleServiceCreator;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.nav_view)
    NavigationView mNavigationView;
    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;

    DribbbleService mDribbbleService;

    @Override
    protected int getLayout() {
        return R.layout.act_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();

        setSupportActionBar(mToolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, mToolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        mNavigationView.setNavigationItemSelectedListener(this);

        ArrayList<TabInfo> tabInfoArrayList = new ArrayList<>();

        RecentFragment recentFragment = RecentFragment.newInstance();
        new RecentPresenter(recentFragment);
        tabInfoArrayList.add(new TabInfo(recentFragment, "Recent"));

        PopularFragment popularFragment = PopularFragment.newInstance();
        new PopularPresenter(popularFragment);
        tabInfoArrayList.add(new TabInfo(popularFragment, "Popular"));

        FollowingFragment followingFragment = FollowingFragment.newInstance();
        new FollowingPresenter(followingFragment);
        tabInfoArrayList.add(new TabInfo(followingFragment, "Following"));

        DebutFragment debutFragment = DebutFragment.newInstance();
        new DebutPresenter(debutFragment);
        tabInfoArrayList.add(new TabInfo(debutFragment, "Debuts"));

        TeamFragment teamFragment = TeamFragment.newInstance();
        new TeamPresenter(teamFragment);
        tabInfoArrayList.add(new TabInfo(teamFragment, "Teams"));

        PlayoffsFragment playoffsFragment = PlayoffsFragment.newInstance();
        new PlayoffsPresenter(playoffsFragment);
        tabInfoArrayList.add(new TabInfo(playoffsFragment, "Playoffs"));

        ViewPagerAdapter adapter = new ViewPagerAdapter(
                getSupportFragmentManager(),
                tabInfoArrayList);

        final int pos = AppPreference.getInstance().readTabPosition();
        mViewPager.setAdapter(adapter);
        mViewPager.setOffscreenPageLimit(6);
        mViewPager.setCurrentItem(pos);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    protected void onStart() {
        super.onStart();
        requestUser();
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        AppPreference.getInstance().saveTabPosition(mTabLayout.getSelectedTabPosition());
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //非正常退出
        AppPreference.getInstance().saveTabPosition(mTabLayout.getSelectedTabPosition());
    }

    private void initData() {
        final String token = AppPreference.getInstance().readToken();

        if (token == null) {
            startActivity(new Intent(this, AppStart.class));
            finish();
        } else {
            mDribbbleService = DribbbleServiceCreator.newInstance()
                    .createService();
        }
    }

    private void requestUser() {
        //如果缓存了user数据，那么直接拿来用
        final User user = AppPreference.getInstance().readUser();
        if (user != null) {
            showUser(user);
            return;
        }
        mDribbbleService.getUser().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mUserSubscriber);
    }

    private final DefaultSubscriber<User> mUserSubscriber = new DefaultSubscriber<User>() {
        @Override
        public void onSuccess(User user) {
            AppPreference.getInstance().saveUser(user);
            showUser(user);
        }

        @Override
        public void onError(Throwable e) {
            showError(e.getMessage());
        }
    };

    private void showUser(User user) {
        View headerView = mNavigationView.getHeaderView(0);
        SimpleDraweeView imgAvatar = (SimpleDraweeView) headerView.findViewById(R.id.img_avatar);
        TextView tvName = (TextView) headerView.findViewById(R.id.tv_name);
        TextView tvHost = (TextView) headerView.findViewById(R.id.tv_host);

        imgAvatar.setImageURI(Uri.parse(user.getAvatarUrl()));

        tvName.setText(user.getName());
        tvHost.setText(user.getHost());

        if (AppPreference.getInstance().isFirstStart()) {
            mDrawerLayout.openDrawer(GravityCompat.START);
        }
    }

    private void showError(String message) {
        Snackbar.make(findViewById(R.id.view_pager), message, Snackbar.LENGTH_LONG).show();
    }
}
