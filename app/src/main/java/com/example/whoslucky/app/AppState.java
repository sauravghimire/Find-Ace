package com.example.whoslucky.app;

import android.app.Application;

import com.example.whoslucky.app.ui.SharedObjects;
import com.facebook.SessionDefaultAudience;
import com.sromku.simple.fb.Permission;
import com.sromku.simple.fb.SimpleFacebook;
import com.sromku.simple.fb.SimpleFacebookConfiguration;

/**
 * Created by susan on 7/17/14.
 */
public class AppState extends Application {
    private static final String APP_ID = "631680366924085";

    @Override
    public void onCreate() {
        super.onCreate();
        SharedObjects.context = this;

        Permission[] permissions = new Permission[]{
                Permission.EMAIL,
                Permission.USER_FRIENDS,
                Permission.USER_BIRTHDAY,
                Permission.PUBLIC_PROFILE,
        };

        SimpleFacebookConfiguration configuration = new SimpleFacebookConfiguration.Builder()
                .setAppId(APP_ID)
                .setPermissions(permissions)
                .setDefaultAudience(SessionDefaultAudience.FRIENDS)
                .setAskForAllPermissionsAtOnce(true)
                .build();

        SimpleFacebook.setConfiguration(configuration);
    }
}
