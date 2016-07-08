/*
 * This utility is intended to keep magic location numbers out of the 
 * framework code.
 * It is used in the following ways:
	1)
	//To test the location of an event 
	if(sr.getRect("PyramidSighted").contains(event.x,event.y)) {
	2)
	//To draw an asset:
   	g.drawPixmap(Assets.selectionFrame, sr.getRect("SelectBox1").left,
   										sr.getRect("SelectBox1").top);                	

 */
package com.tyz.androidgames.pyramid;

import java.util.Hashtable;

import android.graphics.Rect;

import com.tyz.androidgames.framework.Graphics;

public class ScreenRects {
	Hashtable <String, Rect> mRects = new Hashtable<String, Rect>();
	ScreenRects(Graphics g){
		int CENTER_VERT = g.getHeight()/2;
		int CENTER_HORIZ = g.getWidth()/2;
		int FLUSH_LEFT = 0;
		int FLUSH_TOP = 0;
		int FLUSH_BOTTOM = g.getHeight();
		int FLUSH_RIGHT = g.getWidth();
		
		mRects.put("Pause", new Rect((CENTER_HORIZ -90),185,CENTER_HORIZ,250));
		mRects.put("Resume", new Rect((CENTER_HORIZ -85),185,CENTER_HORIZ,232));
		mRects.put("Quit", new Rect((CENTER_HORIZ -85),233,CENTER_HORIZ,281));
		mRects.put("Ready", new Rect((CENTER_HORIZ -104),185,CENTER_HORIZ,250));
		mRects.put("SelectBox1", new Rect(FLUSH_LEFT,175,126,249));
		mRects.put("SelectBox2", new Rect(105,175,252,249));
		mRects.put("SelectBox3", new Rect(210,175,378,249));
		mRects.put("Pool", new Rect(FLUSH_LEFT,75,g.getWidth(),175));
		mRects.put("Palette", new Rect(FLUSH_LEFT,300,g.getWidth(),400));
		mRects.put("Score", new Rect(FLUSH_LEFT+5,FLUSH_TOP+3,CENTER_HORIZ+25,15));
//		mRects.put("Spotted", new Rect(CENTER_HORIZ-(224/2),FLUSH_BOTTOM-88,CENTER_HORIZ+(224/2),FLUSH_BOTTOM));
		//Note the ChooseButtons are 226 wide but there is shadow space the the right.
		mRects.put("ChooseButtons", new Rect(CENTER_HORIZ-(200/2),FLUSH_BOTTOM-100,CENTER_HORIZ+(200/2),FLUSH_BOTTOM));		
		mRects.put("Pyramid", new Rect(CENTER_HORIZ-137,CENTER_VERT,CENTER_HORIZ+137,CENTER_VERT+77));
		mRects.put("Play", new Rect(CENTER_HORIZ-96,FLUSH_BOTTOM-128,CENTER_HORIZ+96,FLUSH_BOTTOM-84));
		mRects.put("HighScores", new Rect(CENTER_HORIZ-96,FLUSH_BOTTOM-128,CENTER_HORIZ+96,FLUSH_BOTTOM-42));
		mRects.put("Help", new Rect(CENTER_HORIZ-96,FLUSH_BOTTOM-128,CENTER_HORIZ+96,FLUSH_BOTTOM));
		mRects.put("GameOver", new Rect(CENTER_HORIZ-150,CENTER_VERT,CENTER_HORIZ+150,CENTER_VERT+51));
	}
	
	Rect getRect(String s){
		return mRects.get(s);
	}
}
