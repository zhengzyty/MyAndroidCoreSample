package cn.beingyi.androidcore.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.util.DisplayMetrics;

import androidx.core.content.FileProvider;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import cn.beingyi.androidcore.AndroidCore;
import cn.beingyi.androidcore.R;

public class APKUtils
{


    public static Drawable getApkIcon(Context context, String apkPath) {
        PackageManager pm = context.getPackageManager();
        PackageInfo info = pm.getPackageArchiveInfo(apkPath, PackageManager.GET_ACTIVITIES);
        if (info != null) {
            ApplicationInfo appInfo = info.applicationInfo;
            appInfo.sourceDir = apkPath;
            appInfo.publicSourceDir = apkPath;
            try {
                return appInfo.loadIcon(pm);
            } catch (OutOfMemoryError e) {
            }
        }
        return ImgHelper.getDrawableFromResources(context, R.drawable.ic_apk);
    }
    
    
    
    public static String getLabel(Context context, String apkPath) {
        PackageManager pm = context.getPackageManager();
        PackageInfo info = pm.getPackageArchiveInfo(apkPath, PackageManager.GET_ACTIVITIES);
        if (info != null) {
            ApplicationInfo appInfo = info.applicationInfo;
            appInfo.sourceDir = apkPath;
            appInfo.publicSourceDir = apkPath;
            try {
                return appInfo.loadLabel(pm).toString();
            } catch (OutOfMemoryError e) {
            }
        }
        return null;
        
    }
    
    public static String getPkgName(Context context, String apkPath) {
        PackageManager pm = context.getPackageManager();
        PackageInfo info = pm.getPackageArchiveInfo(apkPath, PackageManager.GET_ACTIVITIES);
        if (info != null) {
            ApplicationInfo appInfo = info.applicationInfo;
            appInfo.sourceDir = apkPath;
            appInfo.publicSourceDir = apkPath;
            try {
                return appInfo.packageName.toString();
            } catch (OutOfMemoryError e) {
            }
        }
        return null;

    }



    public static boolean isValid(Context context, String apkPath) {
        PackageManager pm = context.getPackageManager();
        PackageInfo info = pm.getPackageArchiveInfo(apkPath, PackageManager.GET_ACTIVITIES);
        if (info != null) {
            ApplicationInfo appInfo = info.applicationInfo;
            appInfo.sourceDir = apkPath;
            appInfo.publicSourceDir = apkPath;
            try {
                appInfo.packageName.toString();
                return true;
            } catch (OutOfMemoryError e) {
            }
        }
        return false;

    }


    
    public static int getVersionCode(Context context, String apkPath) {
        PackageManager pm = context.getPackageManager();
        PackageInfo info = pm.getPackageArchiveInfo(apkPath, PackageManager.GET_ACTIVITIES);
        if (info != null) {
            ApplicationInfo appInfo = info.applicationInfo;
            appInfo.sourceDir = apkPath;
            appInfo.publicSourceDir = apkPath;
            try {
                return info.versionCode==0?0:info.versionCode;
            } catch (OutOfMemoryError e) {
            }
        }
        return 0;

    }
    


    public static String getPermissions(Context context, String apkPath) {
        PackageManager pm = context.getPackageManager();
        PackageInfo info = pm.getPackageArchiveInfo(apkPath, PackageManager.GET_ACTIVITIES);
        if (info != null) {
            ApplicationInfo appInfo = info.applicationInfo;
            appInfo.sourceDir = apkPath;
            appInfo.publicSourceDir = apkPath;
            try {
                return appInfo.permission;
            } catch (OutOfMemoryError e) {
            }
        }
        return null;

    }
    
    
    
    public static String getVersionName(Context context, String apkPath) {
        PackageManager pm = context.getPackageManager();
        PackageInfo info = pm.getPackageArchiveInfo(apkPath, PackageManager.GET_ACTIVITIES);
        if (info != null) {
            ApplicationInfo appInfo = info.applicationInfo;
            appInfo.sourceDir = apkPath;
            appInfo.publicSourceDir = apkPath;
            try {
                return info.versionName==null?"0":info.versionName;
            } catch (OutOfMemoryError e) {
            }
        }
        return null;

    }
    
    
    public static boolean isApkInstalled(Context context, String packagename)
    {
        PackageManager localPackageManager = ((Activity)context).getPackageManager();
        try
        {
            PackageInfo localPackageInfo = localPackageManager.getPackageInfo(packagename, PackageManager.GET_UNINSTALLED_PACKAGES);
            return true;
        }
        catch (PackageManager.NameNotFoundException localNameNotFoundException)
        {
            return false;
        }

    }


    public static String getSignatureByPkg(Context context,String PkgName){

        try {
            /** 通过包管理器获得指定包名包含签名的包信息 **/
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(PkgName, PackageManager.GET_SIGNATURES);
            /******* 通过返回的包信息获得签名数组 *******/
            Signature[] signatures = packageInfo.signatures;
            /******* 循环遍历签名数组拼接应用签名 *******/
            return signatures[0].toCharsString();
            /************** 得到应用签名 **************/
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getSignatureByPath(Context context,String path){

        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageArchiveInfo(path, PackageManager.GET_SIGNATURES);
            Signature[] signatures = packageInfo.signatures;
            return signatures[0].toCharsString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }



    public static String getSignByReflection(Context context,String apkPath) {
        try {
            Class contextClass=Class.forName("android.content.Context");
            Method getPackageManager =contextClass.getDeclaredMethod("getPackageManager");
            Object PackageManagerObject=getPackageManager.invoke(context);

            //Class PackageManagerClass=Class.forName("android.content.pm.PackageManager");
            //Method getPackageArchiveInfo=PackageManagerClass.getDeclaredMethod(String.class,PackageParser.class);

            PackageManager pm= (PackageManager) PackageManagerObject;

            PackageInfo packageInfo = pm.getPackageArchiveInfo(apkPath, PackageManager.GET_SIGNATURES);
            Signature[] signatures = packageInfo.signatures;
            String result= signatures[0].toCharsString();

            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }



    public String getPMParent()throws Exception{
        Class<?> activityThreadClass = Class.forName("android.app.ActivityThread");
        Method currentActivityThreadMethod =
                activityThreadClass.getDeclaredMethod("currentActivityThread");
        Object currentActivityThread = currentActivityThreadMethod.invoke(null);
        Field sPackageManagerField = activityThreadClass.getDeclaredField("sPackageManager");
        sPackageManagerField.setAccessible(true);
        Object sPackageManager = sPackageManagerField.get(currentActivityThread);

        return ((PackageManager)sPackageManager).getClass().getSuperclass().getName();
    }

    public static boolean installAPK(Context context, String path) {
        File apkFile=new File(path);
        if (apkFile.exists()) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                Uri contentUri = FileProvider.getUriForFile(context, AndroidCore.getFileProvider(), apkFile);
                intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
            } else {
                intent.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            }
            if (context.getPackageManager().queryIntentActivities(intent, 0).size() > 0) {
                context.startActivity(intent);
            }
            return true;
        } else {
            return false;
        }
    }
    
    
    
    public void launchAPP(Context context, String packagename)
    {
        PackageManager packageManager = context.getPackageManager();
        Intent intent=new Intent();
        intent =packageManager.getLaunchIntentForPackage(packagename); 
        context.startActivity(intent);
    }
    
    
    
    
}
