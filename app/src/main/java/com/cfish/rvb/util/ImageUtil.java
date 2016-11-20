package com.cfish.rvb.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * Created by dfish on 2016/11/19.
 */
public class ImageUtil {
    public static File imageCompress(Context context, String uri) {
        Bitmap bitmap =  BitmapFactory.decodeFile(uri);
        int width = bitmap.getWidth();

        int height = bitmap.getHeight();

        // 设置想要的大小
        if (width <720 ) {
            Log.d("ImageCompress"," Width < 720");
            return new File(uri);
        } else {
            int newWidth = 720;


            // 计算缩放比例

            float scale= ((float) newWidth) / width;

            //float scaleHeight = ((float) newHeight) / height;

            // 取得想要缩放的matrix参数

            Matrix matrix = new Matrix();

            matrix.postScale(scale, scale);

            // 得到新的图片
            Log.d("ImageCompress"," Width > 720");
            Bitmap newbm = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
            Log.d("new bitmap width,",newbm.getWidth()+"");
            File dirFile  = new File(context.getCacheDir(), "temp.jpg");
            if(!dirFile.exists()){
                if (dirFile.isFile()) {
                    dirFile.delete();
                }
            }
            try {
                BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(dirFile));
                newbm.compress(Bitmap.CompressFormat.JPEG, 80, bos);
                return dirFile;
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }


        }

        return new File(uri);
    }
}
