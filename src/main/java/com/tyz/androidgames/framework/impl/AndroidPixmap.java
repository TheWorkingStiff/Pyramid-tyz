//DMK 6/24/2011
//Added public  Bitmap getBitmap() used in GemView

package com.tyz.androidgames.framework.impl;


import android.graphics.Bitmap;

import com.tyz.androidgames.framework.Graphics.PixmapFormat;
import com.tyz.androidgames.framework.Pixmap;

public class AndroidPixmap implements Pixmap {
    Bitmap bitmap;
    PixmapFormat format;
    
    public AndroidPixmap(Bitmap bitmap, PixmapFormat format) {
        this.bitmap = bitmap;
        this.format = format;
    }

    @Override
    public int getWidth() {
        return bitmap.getWidth();
    }

    @Override
    public int getHeight() {
        return bitmap.getHeight();
    }

    @Override
    public PixmapFormat getFormat() {
        return format;
    }

    @Override
    public void dispose() {
        bitmap.recycle();
    }    
    
    //DMK
    public Bitmap getBitmap(){
    	return bitmap;
    }
}
