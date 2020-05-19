package cn.beingyi.androidcore.update;

import java.io.File;

/**
 * created by zhengyu
 * on 2020/5/19
 **/
public class UpdateConfig {

    String title;
    String content;
    int versionCode;
    String versionName;
    boolean isForceUpdate;
    String downloadUrl;
    File saveFilePath;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public boolean isForceUpdate() {
        return isForceUpdate;
    }

    public void setForceUpdate(boolean forceUpdate) {
        isForceUpdate = forceUpdate;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public File getSaveFilePath() {
        return saveFilePath;
    }

    public void setSaveFilePath(File saveFilePath) {
        this.saveFilePath = saveFilePath;
    }
}
