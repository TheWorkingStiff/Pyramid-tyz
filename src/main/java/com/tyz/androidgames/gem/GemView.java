package com.tyz.androidgames.gem;

import android.graphics.Bitmap;

import com.tyz.androidgames.framework.Pixmap;
import com.tyz.androidgames.framework.impl.AndroidPixmap;
import com.tyz.androidgames.pyramid.Assets;


public class GemView {
//	static final private Bitmap source = BitmapFactory.decodeFile("gems.png");

	static final private Pixmap atlas_tri2d = ((AndroidPixmap)Assets.gemsTri2d);
	static final private Pixmap atlas_tri3d = ((AndroidPixmap)Assets.gemsTri3d);
	static final private Pixmap atlas_triHollow = ((AndroidPixmap)Assets.gemsTriHollow);
	
	public static Bitmap getBitmap(Gem myGem){
		/*
		 * It is necessary to locate the 
		 * correct image on the Gem sheet.
		 * Color is Column (red, black, white)
		 * Cut is is Ignored for now (Triangle, Square, Circle)
		 * Clarity is Row (Warped, Wavy, Straight)
		 * Carat is which page (hollow, 2d, 3d)
		 */
		final int rowHeight = 300;
		//final int blockHeight = 3 * rowHeight;
		final int columnWidth = 300;
		
		int x_coord = 0;
		int y_coord = 0; 
		
		//Bitmap retVal = null;
		
		switch (myGem.getColor()){
		case Red:
			x_coord = 0; // column 0;
			break;
		case Black:
			x_coord = (columnWidth); // column 1;
			break;
		case White:
			x_coord = (2 * columnWidth); // column 2;
			break;
		}
		
		switch (myGem.getClarity()){
		case Warped:
			y_coord = 0; //first block 
			break;
		case Wavy:
			y_coord = rowHeight; //second block
			break;
		case Straight:
			y_coord = rowHeight * 2;  //third block
			break;
		}
		
		switch (myGem.getCut()){
		case Circle:
		case Square:
		case Triangle:
			// use "atlas_tri..."
			break;
		}


		Bitmap bm = null;
		
		switch (myGem.getCarat()){
		case Hollow:
			bm = Bitmap.createBitmap(((AndroidPixmap)atlas_triHollow).getBitmap(),x_coord,y_coord,columnWidth-7,rowHeight-3);
			break;
		case ThreeD:
			bm = Bitmap.createBitmap(((AndroidPixmap)atlas_tri3d).getBitmap(),x_coord,y_coord,columnWidth-7,rowHeight-3);
			break;
		case TwoD:
			bm = Bitmap.createBitmap(((AndroidPixmap)atlas_tri2d).getBitmap(),x_coord,y_coord,columnWidth-7,rowHeight-3);
			break;
		}

		
		return Bitmap.createScaledBitmap(bm, 75, 75, true);
	}

}
