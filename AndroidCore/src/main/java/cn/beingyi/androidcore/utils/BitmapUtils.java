package cn.beingyi.androidcore.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;

import java.io.ByteArrayOutputStream;

import cn.beingyi.androidcore.AndroidCore;
import cn.beingyi.androidcore.R;

public class BitmapUtils
{
    private static int calculateInSampleSize(BitmapFactory.Options options,
                                             int reqWidth, int reqHeight)
    {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth)
        {
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;
            while ((halfHeight / inSampleSize) > reqHeight
                   && (halfWidth / inSampleSize) > reqWidth)
            {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }

    // 如果是放大图片，filter决定是否平滑，如果是缩小图片，filter无影响
    private static Bitmap createScaleBitmap(Bitmap src, int dstWidth,
                                            int dstHeight)
    {
        Bitmap dst = Bitmap.createScaledBitmap(src, dstWidth, dstHeight, false);
        if (src != dst)
        { // 如果没有缩放，那么不回收
            src.recycle(); // 释放Bitmap的native像素数组
        }
        return dst;
    }

    // 从Resources中加载图片
    public static Bitmap decodeSampledBitmapFromResource(Resources res,
                                                         int resId, int reqWidth, int reqHeight)
    {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options); // 读取图片长款
        options.inSampleSize = calculateInSampleSize(options, reqWidth,
                                                     reqHeight); // 计算inSampleSize
        options.inJustDecodeBounds = false;
        Bitmap src = BitmapFactory.decodeResource(res, resId, options); // 载入一个稍大的缩略图
        return createScaleBitmap(src, reqWidth, reqHeight); // 进一步得到目标大小的缩略图
    }

    // 从sd卡上加载图片
    public static Bitmap decodeSampledBitmapFromSd(String pathName, int reqWidth, int reqHeight)
    {
        try
        {
            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(pathName, options);
            options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
            options.inJustDecodeBounds = false;
            Bitmap src = BitmapFactory.decodeFile(pathName, options);
            return createScaleBitmap(src, reqWidth, reqHeight);
        }
        catch (Exception e)
        {
            //return BitmapFactory.decodeResource(AndroidCore.getContext().getResources(), R.drawable.ic_unable_png);
            return null;
        }

    }

    /**
     * Drawable转换成一个Bitmap
     *
     * @param drawable drawable对象
     * @return
     */
    public static final Bitmap drawableToBitmap(Drawable drawable) {
        Bitmap bitmap = Bitmap.createBitmap( drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(),
                drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bitmap;
    }




    //虚化图片
    public static Bitmap rsBlur(Context context, Bitmap source, int radius){
        Bitmap inputBmp = source;
        RenderScript renderScript =  RenderScript.create(context);
        final Allocation input = Allocation.createFromBitmap(renderScript,inputBmp);
        final Allocation output = Allocation.createTyped(renderScript,input.getType());
        ScriptIntrinsicBlur scriptIntrinsicBlur = ScriptIntrinsicBlur.create(renderScript, Element.U8_4(renderScript));
        scriptIntrinsicBlur.setInput(input);
        scriptIntrinsicBlur.setRadius(radius);
        scriptIntrinsicBlur.forEach(output);
        output.copyTo(inputBmp);
        renderScript.destroy();
        return inputBmp;
    }



    public static byte[] toByteArray(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }





}
