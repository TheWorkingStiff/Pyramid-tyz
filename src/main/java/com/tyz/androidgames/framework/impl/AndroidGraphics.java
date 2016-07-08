package com.tyz.androidgames.framework.impl;

import java.io.IOException;
import java.io.InputStream;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;

import com.tyz.androidgames.framework.Graphics;
import com.tyz.androidgames.framework.Pixmap;
import com.tyz.androidgames.pyramid.Assets;

public class AndroidGraphics implements Graphics {
    AssetManager assets;
    Bitmap frameBuffer;
    Canvas canvas;
    Paint paint;
    Rect srcRect = new Rect();
    Rect dstRect = new Rect();

    public AndroidGraphics(AssetManager assets, Bitmap frameBuffer) {
        this.assets = assets;
        this.frameBuffer = frameBuffer;
        this.canvas = new Canvas(frameBuffer);
        this.paint = new Paint();
    }

    @Override
    public Pixmap newPixmap(String fileName, PixmapFormat format) {
        Config config = null;
        if (format == PixmapFormat.RGB565)
            config = Config.RGB_565;
        else if (format == PixmapFormat.ARGB4444)
            config = Config.ARGB_4444;
        else
            config = Config.ARGB_8888;

        Options options = new Options();
        options.inPreferredConfig = config;

        InputStream in = null;
        Bitmap bitmap = null;
        try {
            in = assets.open(fileName);
            bitmap = BitmapFactory.decodeStream(in);
            if (bitmap == null)
                throw new RuntimeException("Couldn't load bitmap from asset '"
                        + fileName + "'");
        } catch (IOException e) {
            throw new RuntimeException("Couldn't load bitmap from asset '"
                    + fileName + "'");
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                }
            }
        }

        if (bitmap.getConfig() == Config.RGB_565)
            format = PixmapFormat.RGB565;
        else if (bitmap.getConfig() == Config.ARGB_4444)
            format = PixmapFormat.ARGB4444;
        else
            format = PixmapFormat.ARGB8888;

        return new AndroidPixmap(bitmap, format);
    }

    @Override
    public void clear(int color) {
        canvas.drawRGB((color & 0xff0000) >> 16, (color & 0xff00) >> 8,
                (color & 0xff));
    }

    @Override
    public void drawPixel(int x, int y, int color) {
        paint.setColor(color);
        canvas.drawPoint(x, y, paint);
    }

    @Override
    public void drawLine(int x, int y, int x2, int y2, int color) {
        paint.setColor(color);
        canvas.drawLine(x, y, x2, y2, paint);
    }

    @Override
    public void drawRect(int x, int y, int width, int height, int color) {
        paint.setColor(color);
        paint.setStyle(Style.FILL);
        canvas.drawRect(x, y, x + width - 1, y + width - 1, paint);
    }

    @Override
    public void drawPixmap(Pixmap pixmap, int x, int y, int srcX, int srcY,
            int srcWidth, int srcHeight) {
        srcRect.left = srcX;
        srcRect.top = srcY;
        srcRect.right = srcX + srcWidth - 1;
        srcRect.bottom = srcY + srcHeight - 1;

        dstRect.left = x;
        dstRect.top = y;
        dstRect.right = x + srcWidth - 1;
        dstRect.bottom = y + srcHeight - 1;

        canvas.drawBitmap(((AndroidPixmap) pixmap).bitmap, srcRect, dstRect,
                null);
    }
    
    @Override
    public void drawPixmap(Pixmap pixmap, int x, int y) {
        canvas.drawBitmap(((AndroidPixmap)pixmap).bitmap, x, y, null);
    }
    
    //DMK 
    public void drawPixmap(Bitmap bitmap, int x, int y){
    	canvas.drawBitmap(bitmap, x, y, null);
    }

    @Override
    public int getWidth() {
        return frameBuffer.getWidth();
    }

    @Override
    public int getHeight() {
        return frameBuffer.getHeight();
    }
    
    //ddroid changed to return width of written text line
    public void drawText(Graphics g, String line, int x, int y, Pixmap font) {
        int len = line.length();
        int charWidth = 16;
        int srcX = 5;
        int totalWidth = 0;
        for (int i = 0; i < len; i++) {
            char character = line.charAt(i);

            if (character == ' ') {
                x += charWidth;
                continue;
            }

            int srcWidth = 0;
            
            if (character == '.') {
                srcX = 10*charWidth;
            	srcWidth = 11;
            } else if (character == '-') {
            	srcX = 10*charWidth+11;
                srcWidth = 11;
            } else {
                srcX = (character - '0') * charWidth;
                srcWidth = charWidth;
            }

            g.drawPixmap(font, x, y, srcX, 0, srcWidth, 22);
            x += srcWidth;
        }

    }
    
    public static int getDrawTextWidth(String line, Pixmap font) {
        int len = line.length();
        int charWidth = 16;
        int srcX = 0;
        int totalWidth = 0;
        int x = 0;
        for (int i = 0; i < len; i++) {
            char character = line.charAt(i);

            if (character == ' ') {
                totalWidth = (x += charWidth);
                continue;
            }

            int srcWidth = 0;
            
            if (character == '.') {
                srcX = 10*charWidth;
            	srcWidth = 11;
            } else if (character == '-') {
            	srcX = 10*charWidth+11;
                srcWidth = 11;
            } else {
                srcX = (character - '0') * charWidth;
                srcWidth = charWidth;
            }

            totalWidth = (x += srcWidth);
        }
        return totalWidth;

    }
}
