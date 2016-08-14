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
package io.lhyz.android.dribbble.login;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.afollestad.materialdialogs.MaterialDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import io.lhyz.android.dribbble.BuildConfig;
import io.lhyz.android.dribbble.R;
import io.lhyz.android.dribbble.base.BaseActivity;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * hello,android
 * Created by lhyz on 2016/8/6.
 */
public class AuthActivity extends BaseActivity {

    public static final int REQUEST_AUTH = 593;

    @BindView(R.id.auth_view)
    WebView mWebView;

    private MaterialDialog mDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDialog = new MaterialDialog.Builder(this)
                .title(R.string.progress_dialog)
                .content(R.string.please_wait)
                .progress(true, 0)
                .build();

        mWebView.setWebViewClient(new WebViewClient() {
            @SuppressWarnings("deprecation")
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.startsWith(BuildConfig.Callback_URL)) {
                    String code = url.substring(url.indexOf("=") + 1);
                    executeAuthenticate(code);
                } else {
                    mDialog.dismiss();
                }
                return super.shouldOverrideUrlLoading(view, url);
            }
        });

        mWebView.loadUrl("https://dribbble.com/oauth/authorize?scope=public+write+comment+upload&client_id=" + BuildConfig.Client_ID);
        mDialog.show();
    }

    @Override
    protected int getLayout() {
        return R.layout.act_auth;
    }

    private void executeAuthenticate(String code) {
        mDialog.show();
        OkHttpClient client = new OkHttpClient();

        FormBody requestBody = new FormBody.Builder()
                .add("client_id", BuildConfig.Client_ID)
                .add("client_secret", BuildConfig.Client_Secret)
                .add("code", code)
                .build();

        Request request = new Request.Builder()
                .url("https://dribbble.com/oauth/token")
                .post(requestBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                setResult(RESULT_CANCELED);
                mDialog.dismiss();
                finish();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    String token = jsonObject.get("access_token").toString();
                    Uri data = Uri.parse(token);
                    Intent intent = new Intent();
                    intent.setData(data);
                    setResult(RESULT_OK, intent);
                    mDialog.dismiss();
                    finish();
                } catch (JSONException e) {
                    onFailure(call, new IOException(e.getMessage()));
                }
            }
        });
    }
}
