/*GemManager is implemented as a singleton.
 * It is not threadsafe and cannot be subclassed
*/
package com.tyz.androidgames.gem;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import com.tyz.androidgames.gem.Gem.Carat;
import com.tyz.androidgames.gem.Gem.Clarity;
import com.tyz.androidgames.gem.Gem.Color;
import com.tyz.androidgames.gem.Gem.Cut;

/**
 * @author daniel
 *
 */
public class GemManager {
	private static GemManager instance = null;
	static private String matchError = null;
	public final int GEMS_IN_PALETTE = 4;
	public final int GEMS_IN_POOL = 4;
	public final int GEMS_SELECTABLE = 3;
	
	LinkedList <Gem> gemPool = null;
	List <Gem> gemPalette = null;
	List <Gem> gemSelected = null;
	boolean[] gemUsedPool = new boolean[8];
	
	public String getMatchError(){
		return matchError;
	}
	private GemManager(){
		//Defeat instantiation
	}
	
	public List<Gem> getGemPool() {
		if(gemPool==null){
			updatePool();
		}
			
		return gemPool;
	}
	
	public void usedReset(){
		for(int i=0;i<gemUsedPool.length;i++){
			gemUsedPool[i] = false;
		}
	}
	
	public boolean[] usedGetArray(){
		return gemUsedPool;
	}
	
	public boolean isUsed(int whichArray, int item){
		return gemUsedPool[(whichArray*GEMS_IN_PALETTE)+item];
	}
	
	//WARNING! assumes GEMS_IN_PALETTE == GEMS_IN_POOL
	public void usedSet(int whichArray, int item){
		//whichArray:
		//GEMS_IN_PALETTE = 0
		//GEMS_IN_POOL = 1
		gemUsedPool[(whichArray*GEMS_IN_PALETTE)+item] = true;
	}
	
	synchronized public void usedReplace(){
		for(int i=0; i<GEMS_IN_PALETTE;i++){
			if(gemUsedPool[i]){
				gemPalette.remove(i);
				gemPalette.add(i,GemManager.getRandomGem());
			}
		}
		for(int i=0; i<GEMS_IN_POOL;i++){
			if(gemUsedPool[GEMS_IN_PALETTE+i]){
				gemPool.remove(i);
				gemPool.add(i,GemManager.getRandomGem());
			}
		}
		//usedReset(); // Doing this here ignores incorrect pyramids
			
	}
	
	public static boolean isMatch(Gem g1, Gem g2, Gem g3){
		StringBuffer sb = new StringBuffer("No Match: \n");
		Color co = Color.getThird(g1.getColor(),g2.getColor());
		Carat ca = Carat.getThird(g1.getCarat(),g2.getCarat());
		Cut cu = Cut.getThird(g1.getCut(),g2.getCut());
		Clarity cl = Clarity.getThird(g1.getClarity(),g2.getClarity());


		Gem gem = new Gem(co,ca,cu,cl); 
		if(gem.equals(g3)){
			return true;
		}else{
			sb.append("Gem1(" + g1.getColor().toString() + ", " + g1.getCarat().toString() + ", " + g1.getCut().toString() + ", " + g1.getClarity().toString() + ")\n");
			sb.append("\nGem2(" + g2.getColor().toString() + ", " + g2.getCarat().toString() + ", " + g2.getCut().toString() + ", " + g2.getClarity().toString()+ ")\n");
			sb.append("\nGem3(" + g3.getColor().toString() + ", " + g3.getCarat().toString() + ", " + g3.getCut().toString() + ", " + g3.getClarity().toString()+ ")\n");
			
			matchError = sb.toString();
			return false;
		}
	}

	public List<Gem> getGemPalette() {
		if(gemPalette==null){
			updatePalette();
		}		
		return gemPalette;
	}
	
	public List<Gem> getGemSelected() {
    	if(gemSelected == null){
        	gemSelected = new ArrayList<Gem>();    		
    	}
		return gemSelected;
	}

	public static GemManager getInstance(){
		if(instance == null){
			instance = new GemManager();
		}
		return instance;
	}
	
	public List<Gem> advance(){
		// Add a gem to the tail and remove one from the head.
		gemPool.removeFirst();
        Gem gem = null;
        do{
    		gem = GemManager.getRandomGem();
    	}while(gemPool.contains(gem) );
    	gemPool.add(gem);		
		return gemPool;
	}

    public void updatePool(){
        // Graphics g = game.getGraphics();
     	gemPool = null;
     	gemPool = new LinkedList<Gem>();
        Gem gem = null;
        for(int i = 0; i< GEMS_IN_POOL; i++){
        	do{
        		gem = GemManager.getRandomGem();
        	}while(gemPool.contains(gem) );
        	
        	gemPool.add(gem);
        }   	

    }
    public void updateSelected(Gem g){
    	if(gemSelected == null){
        	gemSelected = new ArrayList<Gem>();    		
    	}

    	gemSelected.add(g);
    }
     
    public void updatePalette(){
         // Graphics g = game.getGraphics();
      	gemPalette = null;
      	gemPalette = new ArrayList<Gem>();
		Gem gem = null;
		for(int i = 0; i< GEMS_IN_PALETTE; i++){
		 	do{
				gem = GemManager.getRandomGem();
			}while(gemPalette.contains(gem));
			gemPalette.add(gem);
		}   	

    }	
    
    static public Gem getGem(Gem.Color co,Gem.Cut cu,Gem.Clarity cl,Gem.Carat ca){
    	return new Gem(co,ca,cu,cl);
    }
    
	static public Gem getRandomGem(){
    	Random random =  new Random();
    	Gem.Color co = null;
    	Gem.Cut cu = null;
    	Gem.Clarity cl = null;
    	Gem.Carat ca = null;
    	int NUM_ATTRS = 3;
    	
    	int i = 0;
    	int r = random.nextInt(NUM_ATTRS);
		
    	for(Gem.Color c: Gem.Color.values()){
    		if(i++ >= r){
    			co = c;
    			i = 0;
    			break;
    		}    		
    	}
    	r = random.nextInt(NUM_ATTRS);
    	for(Gem.Cut c: Gem.Cut.values()){
    		if(i++ >= r){
    			cu = c;
    			i = 0;
    			break;
    		}    		
    	}
    	r = random.nextInt(NUM_ATTRS);
    	for(Gem.Clarity c: Gem.Clarity.values()){
    		if(i++ >= r){
    			cl = c;
    			i = 0;
    			break;
    		}    		
    	}
    	r = random.nextInt(NUM_ATTRS);
    	for(Gem.Carat c: Gem.Carat.values()){
    		if(i++ >= r){
    			ca = c;
    			i = 0;
    			break;
    		}    		
    	}
    	return new Gem(co,ca,cu,cl);
    }
}
