package MemoryPackage;

import java.io.IOException;
import java.util.Scanner;
import java.util.Vector;

/**
 * @author Ibrahim Ali
 *
 *
 */

public class Auto_AssociativeMemory {
	public void choose() {
		System.out.println("-------------------------------");
		System.out.println("choose [1] to Calculate W : ");
		System.out.println("choose [2] to Recall X With Image From Class 1 : ");
		System.out.println("choose [3] to Recall X With Image From Class 3 : ");
		System.out.println("choose [4] to Recall X With Image From Class 8 : ");
		System.out.println("choose [5] to Calculate E : ");
		System.out.println("choose [6] to resonance E : ");
		System.out.println("choose [7] to EXIT : ");
		System.out.println("-------------------------------");
	}

	public static void main(String[] args) throws IOException {
		System.out.println("hema");
		Memory o = new Memory();
		o.init(o.chooseExperiment());
		Scanner in = new Scanner(System.in);
		Auto_AssociativeMemory op = new Auto_AssociativeMemory();
		op.choose();
		int n = in.nextInt();
		while (n != 7) {
			if (n == 1)
				o.ComputeMemory();
			else if (n == 2)
				o.recallX(1);
			else if (n == 3)
				o.recallX(3);
			else if (n == 4)
				o.recallX(8);
			else if (n == 5)
				o.lypanuovEnergyFunction();
			else if (n == 6)
				o.computationalEnergy();

			op.choose();
			n = in.nextInt();
		}
	}
}