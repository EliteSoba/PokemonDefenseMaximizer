
public class DefenseMax {
	
	public class Distribution {
		int HP, Def, SDef;
		public Distribution() {
			HP = 0;
			Def = 0;
			SDef = 0;
		}
		public Distribution (int D, int S, int H) {
			HP = H;
			Def = D;
			SDef = S;
		}
		public int getHP() {
			return HP;
		}
		public void setHP(int hP) {
			HP = hP;
		}
		public int getDef() {
			return Def;
		}
		public void setDef(int def) {
			Def = def;
		}
		public int getSDef() {
			return SDef;
		}
		public void setSDef(int sDef) {
			SDef = sDef;
		}
		
		public String toString() {
			String ret = "HP EVs: " + (4*HP) + "\n";
			ret += "Def EVs: " + (4*Def) + "\n";
			ret += "SDef EVs: " + (4*SDef);
			return ret;
		}
		
	}

	double HP, Def, SDef, EVs, EVPoints, DefMult, SDefMult;
	boolean Intimidate;
	boolean benDef, benSDef;
	boolean assVest, mScale;
	boolean sand;
	boolean furCoat;
	
	
	public DefenseMax(double HP, double Def, double SDef, double EVs) {
		this.HP = HP;
		this.Def = Def;
		this.SDef = SDef;
		this.EVs = EVs;
		EVPoints = EVs/4;
		DefMult = 1;
		SDefMult = 1;
		Intimidate = false;
		benDef = false;
		benSDef = false;
		assVest = false;
		mScale = false;
		sand = false;
		furCoat = false;
	}
	
	public void addIntimidate() {
		Intimidate = true;
	}
	
	public void removeIntimidate() {
		Intimidate = false;
	}
	
	public void addAssaultVest() {
		assVest = true;
	}
	
	public void removeAssaultVest() {
		assVest = false;
	}

	public void addBeneficialDef() {
		benDef = true;
		if (benSDef)
			benSDef = false;
	}
	
	public void removeBeneficialDef() {
		benDef = false;
	}
	
	public void addBeneficialSDef() {
		benSDef = true;
		if (benDef)
			benDef = false;
	}
	
	public void removeBeneficialSDef() {
		benSDef = false;
	}
	
	public void addMarvelScale() {
		mScale = true;
	}
	
	public void removeMarvelScale() {
		mScale = false;
	}
	
	public void addSand() {
		sand = true;
	}
	
	public void removeSand() {
		sand = false;
	}
	
	public void addFurCoat() {
		furCoat = true;
	}
	
	public void removeFurCoat() {
		furCoat = false;
	}
	
	
	
	public double getHP() {
		return HP;
	}

	public void setHP(double hP) {
		HP = hP;
	}

	public double getDef() {
		return Def;
	}

	public void setDef(double def) {
		Def = def;
	}

	public double getSDef() {
		return SDef;
	}

	public void setSDef(double sDef) {
		SDef = sDef;
	}

	public double getEVs() {
		return EVs;
	}

	public void setEVs(double eVs) {
		EVs = eVs;
	}

	public double getDefMult() {
		return DefMult;
	}

	public void setDefMult(double defMult) {
		DefMult = defMult;
	}

	public double getSDefMult() {
		return SDefMult;
	}

	public void setSDefMult(double sDefMult) {
		SDefMult = sDefMult;
	}
	
	public boolean hasIntimidate() {
		return Intimidate;
	}

	public void setIntimidate(boolean intimidate) {
		Intimidate = intimidate;
	}

	public boolean hasBenDef() {
		return benDef;
	}

	public void setBenDef(boolean benDef) {
		this.benDef = benDef;
	}

	public boolean hasBenSDef() {
		return benSDef;
	}

	public void setBenSDef(boolean benSDef) {
		this.benSDef = benSDef;
	}

	public boolean hasmScale() {
		return mScale;
	}

	public void setmScale(boolean mScale) {
		this.mScale = mScale;
	}
	
	public boolean hasSand() {
		return sand;
	}
	
	public void setSand(boolean sand) {
		this.sand = sand;
	}
	
	public double getTrueDef(int EVs) {
		double def = calcStat(Def, EVs);
		if (benDef)
			def *= 1.1;
		if (mScale)
			def *= 1.5;
		if (furCoat)
			def *= 2;
		return def;
	}
	
	public double getTrueSDef(int EVs) {
		double sdef = calcStat(SDef, EVs);
		if (benSDef)
			sdef *= 1.1;
		if (assVest)
			sdef *= 1.5;
		if (Intimidate)
			sdef /= 1.5;
		if (sand)
			sdef *= 1.5;
		return sdef;
	}

	public static double calcStat(int base, int EVs) {
		return EVs + 31 + 5 + 2.0*base;
	}
	
	public static double calcStat(double base, int EVs) {
		return EVs + 31 + 5 + 2.0*base;
	}
	
	public static double calcHP(int base, int EVs) {
		return EVs + 31 + 110 + 2.0*base;
	}

	public static double calcHP(double base, int EVs) {
		return EVs + 31 + 110 + 2.0*base;
	}
	
	public Distribution getDistribution() {
		double max = -1;
		int maxi = 0, maxj = 0, maxk = 0;
		for (int i = 0; i <= 63; i++) {
			for (int j = 0; j <= 63; j++) {
				for (int k = 0; k <= 63; k++) {
					if (i + j + k > EVPoints)
						continue;
					double cur = getHarm(i, j, k);
					if (max < 0 || cur < max) {
						max = cur;
						maxi = i;
						maxj = j;
						maxk = k;
					}
				}
			}
		}
		
		return new Distribution(maxi, maxj, maxk);
	}
	
	public double getHarm(int d, int s, int h) {
		double cur = 20000 * (DefMult*getTrueDef(d) + SDefMult*getTrueSDef(s)) + 4*(DefMult*getTrueDef(d))*(SDefMult*getTrueSDef(s));
		cur /= (calcHP(HP, h)) * (DefMult*getTrueDef(d))*(SDefMult*getTrueSDef(s));
		return cur;
	}

	public static void main(String[] args) {
		DefenseMax Tyranitar = new DefenseMax(100, 110, 100, 510-76);
		Tyranitar.addBeneficialSDef();
		Tyranitar.addAssaultVest();
		Tyranitar.addSand();
		System.out.println(Tyranitar.getDistribution());

	}

}
