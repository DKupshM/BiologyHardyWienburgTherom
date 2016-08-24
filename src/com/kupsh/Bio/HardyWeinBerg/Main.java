package com.kupsh.Bio.HardyWeinBerg;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
//Note this code is not efficient in any way...
//It should be pretty accurate however

public class Main {

	// P is frequency of Dominant
	// Set Starting Frequency here
	private double pPercentage = 50;

	// Natural Selection is for negative to P
	// Set Natural Selection here
	private double naturalSelectionFactor = 0.1;

	// Amount in population
	private long organismAmount = 1000000L;

	// Bookeeping variables
	private long trialNumber = 0L;
	private Random random = new Random();
	private ArrayList<Organism> organisms = new ArrayList<Organism>();

	public static void main(String[] args) {
		Main main = new Main();
		main.start();
	}

	public void start() {
		System.out.println("Starting Experiment with random seed: ");
		while (true) {
			setOrganisms();
			logResults();
			setupNewTrial();
			trialNumber++;
			if (pPercentage <= 0 || pPercentage >= 100)
				break;
		}
	}

	public void setOrganisms() {
		organisms.clear();
		for (int i = 0; i < organismAmount; i++) {
			double firstRandom = generateRandomNumber();
			boolean firstAllele;
			if (pPercentage < firstRandom) {
				// Recessive Gene
				firstAllele = false;
			} else {
				// Dominant Gene
				firstAllele = true;
			}
			double secondRandom = generateRandomNumber();
			boolean secondAllele;
			if (pPercentage < secondRandom) {
				// Recessive Gene
				secondAllele = false;
			} else {
				// Dominant Gene
				secondAllele = true;
			}
			organisms.add(new Organism(firstAllele, secondAllele));
		}
	}

	public double generateRandomNumber() {
		double rand = random.nextDouble() * 100;
		if (rand < 0)
			rand = 0;
		if (rand > 100)
			rand = 0;
		return rand;
	}

	public void logResults() {
		// System.out.println("Logging Results");
		float Dominant = 0;
		float Recessive = 0;
		float Heterozygous = 0;
		float total = 0;
		for (Organism org : organisms) {
			if (org.getType() == Type.Dominant)
				Dominant++;
			else if (org.getType() == Type.Recessive)
				Recessive++;
			else
				Heterozygous++;
			total++;
		}
		String log = "Trial " + trialNumber + " Finished (" + total
				+ " total)\n" + "Dominant Count " + Dominant + " ("
				+ (int) ((Dominant / total) * 100f) + "%)\n"
				+ "Recessive Count " + Recessive + " ("
				+ (int) ((Recessive / total) * 100f) + "%)\n"
				+ "Heterozygous Count " + Heterozygous + " ("
				+ (int) ((Heterozygous / total) * 100f) + "%)\n";
		System.out.print(log);
		writeToFile(log);
	}

	public void setupNewTrial() {
		float total = 0;
		float dominantAlleles = 0;
		for (Organism org : organisms) {
			total += 2;
			dominantAlleles += org.getDominantAlleleAmount();
		}
		pPercentage = (double) ((dominantAlleles / total) * 100);
		pPercentage -= naturalSelectionFactor;
		String log = "Number of Dominant Alleles " + dominantAlleles + " ("
				+ (int) ((dominantAlleles / total) * 100) + "%)\n"
				+ "Number of Recessive Alleles " + (total - dominantAlleles)
				+ " ("
				+ (int) (((Math.abs(dominantAlleles - total)) / total) * 100)
				+ "%)\n" + "=========================\n" + "Starting Trial " + (trialNumber + 1)
				+ " with p Percentage at " + pPercentage + "\n" +"Natural Selection Factor is "
				+ naturalSelectionFactor + "\n";
		System.out.print(log);
		writeToFile(log);
	}

	public void writeToFile(String log) {
		try {
			String filename = "/Users/kupshfamily/Documents/workspace/Biology/Logs/log";
			FileWriter fw = new FileWriter(filename, true);
			fw.write(log);
			fw.close();
		} catch (IOException ioe) {
			System.err.println("IOException: " + ioe.getMessage());
		}
	}
}
