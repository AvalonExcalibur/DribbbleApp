package io.lhyz.android.dribbble.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import javax.inject.Inject;

import butterknife.BindView;
import io.lhyz.android.boilerplate.domain.interactor.DefaultSubscriber;
import io.lhyz.android.dribbble.AppPreference;
import io.lhyz.android.dribbble.AppStart;
import io.lhyz.android.dribbble.R;
import io.lhyz.android.dribbble.base.BaseActivity;
import io.lhyz.android.dribbble.data.DribbbleService;
import io.lhyz.android.dribbble.data.model.User;
import io.lhyz.android.dribbble.di.component.DaggerAppComponent;
import io.lhyz.android.dribbble.di.module.DribbbleModule;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
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

    @Inject
    DribbbleService mDribbbleService;

    @Override
    protected int getLayout() {
        return R.layout.act_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initInjector();

        setSupportActionBar(mToolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        mNavigationView.setNavigationItemSelectedListener(this);
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

    private void initInjector() {
        final String token = AppPreference.getInstance().readToken();
        if (token == null) {
            startActivity(new Intent(this, AppStart.class));
            finish();
        } else {
            DaggerAppComponent.builder()
                    .dribbbleModule(new DribbbleModule(token))
                    .build()
                    .inject(this);
        }
    }

    private void requestUser() {
        mDribbbleService.getUser().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mUserSubscriber);
    }

    private final DefaultSubscriber<User> mUserSubscriber = new DefaultSubscriber<User>() {
        @Override
        public void onSuccess(User user) {
            showUser(user);
        }

        @Override
        public void onError(Throwable e) {
            showError(e.getMessage());
        }
    };

    private void showUser(User user) {
        View headerView = mNavigationView.getHeaderView(0);
        ImageView imgAvatar = (ImageView) headerView.findViewById(R.id.img_avatar);
        TextView tvName = (TextView) headerView.findViewById(R.id.tv_name);
        TextView tvHost = (TextView) headerView.findViewById(R.id.tv_host);

        Glide.with(this).load(user.getAvatarUrl())
                .bitmapTransform(new CropCircleTransformation(this))
                .into(imgAvatar);

        tvName.setText(user.getName());
        tvHost.setText(user.getHost());

        if (AppPreference.getInstance().isFirstStart()) {
            mDrawerLayout.openDrawer(GravityCompat.START);
        }
    }

    private void showError(String message) {
        Snackbar.make(findViewById(R.id.main_container), message, Snackbar.LENGTH_LONG).show();
    }
}
