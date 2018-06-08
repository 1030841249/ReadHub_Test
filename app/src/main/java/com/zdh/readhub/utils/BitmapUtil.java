package com.zdh.readhub.utils;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
import android.view.View;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * Created by zdh on 2018/5/29.
 */

public class BitmapUtil {

    /**
     * 保存view 为image
     * @param view 要保存的view
     * @param fileName 文件名
     * @return
     */
    public static boolean saveViewAsImage(View view, String fileName) {
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(),
                view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        /*
        设置view 的布局位置
        并将bitmap绘制出来
         */
        view.layout(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
        view.draw(canvas);
        return saveImage(view.getContext(), bitmap, fileName);
    }

    private static boolean saveImage(Context context, Bitmap bitmap, String fileName) {
        // 判断外部存储空间是否可用（是否有sd卡）
        // 或者不给使用sd卡
        if (!(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
            && (ContextCompat.checkSelfPermission(context, Manifest.permission_group.STORAGE)
                != PackageManager.PERMISSION_GRANTED))) {
                return false;
        }

        File dirFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                "readhub");
        dirFile.mkdirs();
        File file = new File(dirFile, fileName + ".jpg");
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            /*
            like PNG which is lossless,
            PNG压缩是无损的
             */
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
            context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(file)));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
