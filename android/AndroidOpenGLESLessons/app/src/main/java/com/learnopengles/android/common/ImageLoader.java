package com.learnopengles.android.common;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.opengl.GLES20;
import android.opengl.GLUtils;
import android.util.Log;

import com.learnopengles.android.lesson4.gles.EglCore;
import com.learnopengles.android.lesson4.gles.OffscreenSurface;

import java.nio.ByteBuffer;

import static com.learnopengles.android.lesson4.gles.EglCore.FLAG_RECORDABLE;

public class ImageLoader {
    private static final String TAG = ImageLoader.class.getSimpleName();

    public static Bitmap loadBitmap(String fileName) {
        Log.i(TAG, "loadBitmap path: " + fileName);

        Bitmap bitmap = BitmapFactory.decodeFile(fileName);
        return bitmap;
    }

    public static int loadTextureId(String fileName) {
        Log.d(TAG, "loadTextureId path: " + fileName);
        Bitmap bitmap = BitmapFactory.decodeFile(fileName);
//        Log.d(TAG, "Bitmap is: " + bitmap);
        ByteBuffer buffer = ByteBuffer.allocate(bitmap.getByteCount());
        bitmap.copyPixelsToBuffer(buffer);
        int textures[] = new int[1];
        GLES20.glGenTextures(1, textures, 0);
        int textureId = textures[0];
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureId);
        GLUtils.texImage2D(GLES20.GL_TEXTURE_2D,0, bitmap,0);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S,
                GLES20.GL_CLAMP_TO_EDGE);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T,
                GLES20.GL_CLAMP_TO_EDGE);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0);
        Log.d(TAG, "texture id returned: " + textureId);
        return textureId;
    }



    public static int loadTextureId() {
        Log.d(TAG, "loadTextureId");

        Bitmap bitmap = loadBitmapFromSystemDrawable();
        return getTextureIdOfLoadedImage(bitmap);
    }

    public static Bitmap loadBitmapFromSystemDrawable() {
        Log.i(TAG, "loadBitmapFromSystemDrawable");

        // The size of sym_def_app_icon is 144*144
        Drawable drawable = Resources.getSystem().getDrawable(android.R.drawable.sym_def_app_icon);
        Bitmap bitmap = Bitmap.createBitmap(drawable.getMinimumWidth(), drawable.getMinimumHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }

    private static int getTextureIdOfLoadedImage(Bitmap bitmap) {
        if (bitmap == null) {
            Log.e(TAG, "getTextureIdOfLoadedImage: invalid bitmap");
            return 0;
        }

        Log.i(TAG, "getTextureIdOfLoadedImage Image size: " + bitmap.getWidth() + " x " + bitmap.getHeight());
//
//        EglCore eglCore = new EglCore(null, FLAG_RECORDABLE);
//        OffscreenSurface offscreenSurface = new OffscreenSurface(eglCore, 10, 10);
//        offscreenSurface.makeCurrent();
        int textures[] = new int[1];
        GLES20.glGenTextures(1, textures, 0);
        int textureId = textures[0];
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureId);
        GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S,
                GLES20.GL_CLAMP_TO_EDGE);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T,
                GLES20.GL_CLAMP_TO_EDGE);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0);

        Log.d(TAG, "getTextureIdOfLoadedImage textureId: " + textureId);
        return textureId;
    }
}
