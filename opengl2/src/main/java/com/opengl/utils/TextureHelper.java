package com.opengl.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLUtils;
import android.util.Log;

/**
 * Created by Administrator on 2016/11/23.
 */

public class TextureHelper {
    private static final String TAG = "TextureHelper";

    public static int loadTexture(Context context, int resourceID) {
        int[] textureObjectIDs = new int[1];
        GLES20.glGenTextures(1, textureObjectIDs, 0);
        if (textureObjectIDs[0] == 0) {
            if (LoggerConfig.ON) {
                Log.e(TAG, "Could not generate a new OpenGL texture object.");
            }
            return 0;
        }

        BitmapFactory.Options options = new BitmapFactory.Options();
        // 原始图像数据
        options.inScaled = true;

        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resourceID, options);
        if (bitmap == null) {
            if (LoggerConfig.ON) {
                Log.e(TAG, "Resource ID : " + resourceID + " cound not be decoded.");
            }
            GLES20.glDeleteTextures(1, textureObjectIDs, 0);
            return 0;
        }

        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureObjectIDs[0]);

        // 纹理过滤
        // 缩小
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR_MIPMAP_LINEAR);
        // 放大
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
        // Load the bitmap into the bound texture.
        GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0);
        // Note: Following code may cause an error to be reported in the
        // ADB log as follows: E/IMGSRV(20095): :0: HardwareMipGen:
        // Failed to generate texture mipmap levels (error=3)
        // No OpenGL error will be encountered (glGetError() will return
        // 0). If this happens, just squash the source image to be
        // square. It will look the same because of texture coordinates,
        // and mipmap generation will work.
        GLES20.glGenerateMipmap(GLES20.GL_TEXTURE_2D);
        // Recycle the bitmap, since its data has been loaded into
        // OpenGL.
        bitmap.recycle();
        // Unbind from the texture.
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0);

        return textureObjectIDs[0];
    }

}
