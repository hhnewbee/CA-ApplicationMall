package com.example.downloadui.update;

import android.content.Intent;
import android.graphics.drawable.Drawable;

/**
 * Created by user on 2016/8/10.
 * 软件信息加载的对应类
 */

public class AppInfo {

    private String AppName;     //应用程序名
    private Intent intent;      //应用程序启动Intent
    private String pkgName;     //应用程序包名
    private Drawable AppIcon;   //应用程序图标
    private String Version;
    private Intent Uninstall;

    public String getVersion() {
        return Version;
    }
    public void setVersion(String version) {
        Version = version;
    }

    public String getAppName() {
        return AppName;
    }

    public void setAppName(String appName) {
        AppName = appName;
    }

    public Drawable getAppIcon() {
        return AppIcon;
    }

    public void setAppIcon(Drawable appIcon) {
        AppIcon = appIcon;
    }

    public Intent getIntent() {
        return intent;
    }

    public void setIntent(Intent intent) {
        this.intent = intent;
    }

    public Intent getUninstall() {
        return Uninstall;
    }

    public void setUninstall(Intent uninstall) {
        Uninstall = uninstall;
    }

    public String getPkgName() {
        return pkgName;
    }

    public void setPkgName(String pkgName) {
        this.pkgName = pkgName;
    }
}
