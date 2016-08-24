package com.kupsh.Bio.HardyWeinBerg;

public class Organism {
	
	public boolean firstAllele;
	public boolean secondAllele;
	
	//True = dominant
	//False = Recessive
	public Organism(boolean firstAllele, boolean secondAllele){
		this.firstAllele = firstAllele;
		this.secondAllele = secondAllele;
 	}
	
	public Type getType(){
		if(firstAllele && secondAllele) return Type.Dominant;
		else if (!firstAllele && !secondAllele) return Type.Recessive;
		else return Type.Heterozygous;
	}
	
	public double getDominantAlleleAmount(){
		double amount = 0;
		if(firstAllele) amount ++;
		if(secondAllele) amount++;
		return amount;
	}
}
