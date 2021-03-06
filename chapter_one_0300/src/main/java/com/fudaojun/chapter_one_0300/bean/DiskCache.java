package com.fudaojun.chapter_one_0300.bean;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import com.fudaojun.chapter_one_0300.base.ImageCache;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by ZhijunHong on 2017/11/8.
 */

public class DiskCache implements ImageCache {
    @Override
    public void put(String url, Bitmap bitmap) {
        FileOutputStream fileOutputStream = null;
        try {
            File rootFilePath = getRootFilePath();
            if (rootFilePath != null) {
                File imageFile = new File(getRootFilePath().getPath() + "/" + url);
                fileOutputStream = new FileOutputStream(imageFile);
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public Bitmap get(String url) {
        return BitmapFactory.decodeFile(getRootFilePath().getPath() + "/" + url);
    }


    /**
     * 获取存储图片根路径
     *
     * @return
     */
    private File getRootFilePath() {
        File file = null;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {//如果存储器已经挂载
            file = new File(Environment.getExternalStorageDirectory(), "fudaojun/image/");
            if (!file.exists()) {
                file.mkdir();
            }
        }
        return file;
    }
}
