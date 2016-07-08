package com.tyz.androidgames.gem;

public class Gem {
	private Color color=Color.Black;
	private Carat carat=Carat.Hollow;
	private Cut cut=Cut.Triangle;
	private Clarity clarity=Clarity.Straight;
	
	public static enum Color {Red,White,Black;
		static Color getThird(Color a, Color b){
			Color retVal = a;
			if(a == b) {
				retVal = a;
			}else{
				for(Color c: Color.values()){
					if (a != c && b != c) retVal = c;
					//break;
				}
			}
			return retVal;
		}
	};
	public static enum Carat {TwoD,ThreeD,Hollow;
	static Carat getThird(Carat a, Carat b){
		Carat retVal = a;
		if(a == b) {
			retVal = a;
		}else{
			for(Carat c: Carat.values()){
				if (a != c && b != c) retVal = c;
				//break;
			}
		}
		return retVal;
	}
	};
	
	public static enum Cut {Triangle,Circle,Square;
		static Cut getThird(Cut a, Cut b){
			Cut retVal = Triangle;
/*			if(a == b) {
				retVal = a;
			}else{
				for(Cut c: Cut.values()){
					if (a != c && b != c) retVal = c;
					break;
				}
			}*/
			return retVal;
		}
	};
	public static enum Clarity{Straight,Wavy,Warped;
		static Clarity getThird(Clarity a, Clarity b){
			Clarity retVal = a;
			if(a == b) {
				retVal = a;
			}else{
				for(Clarity c: Clarity.values()){
					if (a != c && b != c) retVal = c;
					//break;
				}
			}
			return retVal;
		}
	};
	
	public Gem(Color color, Carat carat, Cut cut, Clarity clarity) {
		super();
		this.color = color;
		this.carat = carat;
		this.clarity = clarity;
		this.cut = Cut.Triangle;
		
	}
	

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((carat == null) ? 0 : carat.hashCode());
		result = prime * result + ((clarity == null) ? 0 : clarity.hashCode());
		result = prime * result + ((color == null) ? 0 : color.hashCode());
		result = prime * result + ((cut == null) ? 0 : cut.hashCode());
		return result;
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Gem other = (Gem) obj;
		if (carat != other.carat)
			return false;
		if (clarity != other.clarity)
			return false;
		if (color != other.color)
			return false;
		if (cut != other.cut)
			return false;
		return true;
	}



	public static Gem GetMatch(Gem g1, Gem g2) {
		return new Gem(	Color.getThird(g1.color,g2.color),
						Carat.getThird(g1.carat,g2.carat),
						Cut.getThird(g1.cut,g2.cut),
						Clarity.getThird(g1.clarity,g2.clarity));
	}
	

	
	public Color getColor() {
		return color;
	}
	public void setColor(Color color) {
		this.color = color;
	}
	public Carat getCarat() {
		return carat;
	}
	public void setCarat(Carat carat) {
		this.carat = carat;
	}
	public Cut getCut() {
		return Cut.Triangle;
	}
	public void setCut(Cut cut) {
		this.cut = cut;
	}
	public Clarity getClarity() {
		return clarity;
	}
	public void setClarity(Clarity clarity) {
		this.clarity = clarity;
	};
}
