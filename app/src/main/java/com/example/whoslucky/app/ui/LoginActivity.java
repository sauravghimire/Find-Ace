package com.example.whoslucky.app.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;

import com.example.whoslucky.app.MainActivity;
import com.example.whoslucky.app.R;
import com.example.whoslucky.app.utils.AppText;
import com.sromku.simple.fb.Permission;
import com.sromku.simple.fb.SimpleFacebook;
import com.sromku.simple.fb.entities.Profile;
import com.sromku.simple.fb.listeners.OnLoginListener;
import com.sromku.simple.fb.listeners.OnProfileListener;
import com.sromku.simple.fb.utils.Attributes;
import com.sromku.simple.fb.utils.PictureAttributes;

/**
 * Created by susan on 7/17/14.
 */
public class LoginActivity extends ActionBarActivity implements View.OnClickListener {

    private SimpleFacebook simpleFacebook;
    private Button buttonFacebookLogin, buttonSkipLogin;
    private ProgressDialog progressDialog;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_login_acitivity);

        simpleFacebook = SimpleFacebook.getInstance(this);

        progressDialog = new ProgressDialog(this);
        sharedPreferences = getSharedPreferences("FB_REG", 0);
        String accessToken = sharedPreferences.getString(AppText.KEY_FB_ACCESS_TOKEN, "");
        simpleFacebook = SimpleFacebook.getInstance(this);
        if (simpleFacebook.isLogin() && simpleFacebook.getSession().getAccessToken().equals(accessToken)) {
            startActivity(new Intent(LoginActivity.this, MainMenuActivity.class));
            finish();
        }

        progressDialog = new ProgressDialog(this);
        buttonFacebookLogin = (Button) findViewById(R.id.buttonFacebookLogin);
        buttonFacebookLogin.setOnClickListener(this);

        buttonSkipLogin = (Button) findViewById(R.id.buttonSkipLogin);
        buttonSkipLogin.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        simpleFacebook = SimpleFacebook.getInstance(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonFacebookLogin:
                if (!simpleFacebook.isLogin()) {
                    progressDialog.setCancelable(false);
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.setMessage("Logging In");
                    progressDialog.show();
                    simpleFacebook.login(loginListener);
                }
                break;

            case R.id.buttonSkipLogin:
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean(AppText.KEY_IS_FB_LOGIN, false);
                editor.putString(AppText.KEY_FB_USER_NAME, "");
                editor.putString(AppText.KEY_FB_ACCESS_TOKEN, "");
                editor.commit();
                startActivity(new Intent(LoginActivity.this, MainMenuActivity.class));
                finish();
                break;
        }
    }

    private OnLoginListener loginListener = new OnLoginListener() {
        @Override
        public void onLogin() {
            PictureAttributes pictureAttributes = Attributes.createPictureAttributes();
            pictureAttributes.setWidth(150);
            pictureAttributes.setHeight(150);
            pictureAttributes.setType(PictureAttributes.PictureType.SQUARE);

            Profile.Properties properties = new Profile.Properties.Builder()
                    .add(Profile.Properties.NAME)
                    .add(Profile.Properties.EMAIL)
                    .add(Profile.Properties.BIRTHDAY)
                    .add(Profile.Properties.PICTURE, pictureAttributes)
                    .build();
            progressDialog.setMessage("Getting Infos...");

            simpleFacebook.getProfile(properties, profileListener);

        }

        @Override
        public void onNotAcceptingPermissions(Permission.Type type) {
            progressDialog.dismiss();
        }

        @Override
        public void onThinking() {

        }

        @Override
        public void onException(Throwable throwable) {
            progressDialog.dismiss();
        }

        @Override
        public void onFail(String reason) {
            progressDialog.dismiss();
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        simpleFacebook.onActivityResult(this, requestCode, resultCode, data);
    }

    private OnProfileListener profileListener = new OnProfileListener() {
        @Override
        public void onComplete(Profile response) {
            super.onComplete(response);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(AppText.KEY_FB_ACCESS_TOKEN, simpleFacebook.getSession().getAccessToken());
            editor.putString(AppText.KEY_FB_USER_NAME, response.getUsername());
            editor.putBoolean(AppText.KEY_IS_FB_LOGIN, true);
            editor.commit();
            progressDialog.dismiss();
            startActivity(new Intent(LoginActivity.this, MainMenuActivity.class));
            finish();
        }

        @Override
        public void onException(Throwable throwable) {
            super.onException(throwable);
            progressDialog.dismiss();
        }

        @Override
        public void onFail(String reason) {
            super.onFail(reason);
            progressDialog.dismiss();
        }

        @Override
        public void onThinking() {
            super.onThinking();
        }
    };
}
