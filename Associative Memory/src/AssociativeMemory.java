import java.io.IOException;
import java.util.Scanner;

/**
 * @author Ibrahim Ali
 *
 */
public class AssociativeMemory {
	public void choose() {
		System.out.println("-------------------------------");
		System.out.println("choose [1] to Calculate W : ");
		System.out.println("choose [2] to Recall X : ");
		System.out.println("choose [3] to Recall Y : ");
		System.out.println("choose [4] to Calculate E : ");
		System.out.println("choose [5] to Computaional E : ");
		System.out.println("choose [6] to EXIT : ");
		System.out.println("-------------------------------");
	}

	public static void main(String[] args) throws IOException {
		System.out.println("hema");
		Memory o = new Memory();
		Scanner in = new Scanner(System.in);
		AssociativeMemory op = new AssociativeMemory();
		op.choose();
		o.init();
		int n = in.nextInt();
		while (n != 6) {
			if (n == 1)
				o.ComputeMemory();
			else if (n == 2)
				o.recallX();
			else if (n == 3)
				o.recallY();
			else if (n == 4)
				o.lypanuovEnergyFunction();
			else if (n == 5)
				o.computationalEnergy();

			op.choose();
			n = in.nextInt();
		}
	}

}
