import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Memory {
	Scanner in = new Scanner(System.in);
	Vector Vx1, Vx2, Vy1, Vy2, X0, Y0;
	Matrix w;

	public void init() throws IOException {
		BufferedReader reader = new BufferedReader(
				new FileReader("vectors.txt"));

		String line = "";
		int i = 0;
		while ((line = reader.readLine()) != null) {
			String[] s = line.split(" ");
			int vect[] = new int[s.length];
			if (i == 0) {
				for (int j = 0; j < s.length; j++)
					vect[j] = Integer.valueOf(s[j]);

				Vx1 = new Vector(vect, vect.length);
			} else if (i == 1) {
				for (int j = 0; j < s.length; j++)
					vect[j] = Integer.valueOf(s[j]);

				Vx2 = new Vector(vect, vect.length);
			} else if (i == 2) {
				for (int j = 0; j < s.length; j++)
					vect[j] = Integer.valueOf(s[j]);

				Vy1 = new Vector(vect, vect.length);
			} else if (i == 3) {
				for (int j = 0; j < s.length; j++)
					vect[j] = Integer.valueOf(s[j]);

				Vy2 = new Vector(vect, vect.length);
			}
			++i;
		}
		reader.close();
	}

	public Matrix ComputeMemory() {
		Matrix result1 = new Matrix(Vy1.size, Vx1.size);
		result1.Multiply2Vector(Vy1, Vx1);
		Matrix result2 = new Matrix(Vy2.size, Vx2.size);
		result2.Multiply2Vector(Vy2, Vx2);
		w = result1.Add2Matrix(result2);
		PrintMemory();
		return null;
	}

	public Matrix PrintMemory() {
		System.out.println("----------  The Memory  --------");
		int matrix[][] = w.getvect();
		for (int i = 0; i < w.getRow(); ++i) {
			for (int j = 0; j < w.getColumn(); ++j) {
				System.out.print(matrix[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println("--------------------------------");
		return null;
	}

	public void lypanuovEnergyFunction() {
		int minEnergy = 0;
		for (int i = 0; i < w.getRow(); ++i)
			for (int j = 0; j < w.getColumn(); ++j)
				minEnergy += Math.abs(w.matrix[i][j]);

		System.out.println("minEnergy = " + -minEnergy);
	}

	public Vector readRecall(String x) throws NumberFormatException,
			IOException {
		if (x.equals("x")) {
			BufferedReader reader = new BufferedReader(new FileReader(
					"recallx.txt"));

			String line = "";
			while ((line = reader.readLine()) != null) {
				String[] s = line.split(" ");
				int vect[] = new int[s.length];
				for (int j = 0; j < s.length; j++)
					vect[j] = Integer.valueOf(s[j]);

				X0 = new Vector(vect, vect.length);
			}
			reader.close();
			return X0;
		} else {
			BufferedReader reader = new BufferedReader(new FileReader(
					"recally.txt"));

			String line = "";
			while ((line = reader.readLine()) != null) {
				String[] s = line.split(" ");
				int vect[] = new int[s.length];
				for (int j = 0; j < s.length; j++)
					vect[j] = Integer.valueOf(s[j]);

				Y0 = new Vector(vect, vect.length);
			}
			reader.close();
			return Y0;
		}
	}

	public boolean checkReasoning(Vector prev, Vector curr) {
		for (int i = 0; i < prev.size; i++)
			if (prev.vect[i] != curr.vect[i])
				return false;
		return true;
	}

	public Vector hebbRule(Vector v) {
		for (int i = 0; i < v.size; i++) {
			if (v.vect[i] > 0)
				v.vect[i] = 1;
			else if (v.vect[i] < 0)
				v.vect[i] = -1;
			else
				v.vect[i] = 0;
		}

		for (int i = 0; i < v.size; i++) {
			if (v.vect[i] == 0) {
				System.out.println("Indterminism Occured");
				break;
			}
			if (i == v.size - 1 && v.vect[i] != 0)
				return v;
		}

		if (v.size == Vy1.size)
			v = v.getDiff(Vy1) <= v.getDiff(Vy2) ? Vy1 : Vy2;
		else
			v = v.getDiff(Vx1) <= v.getDiff(Vx2) ? Vx1 : Vx2;

		return v;
	}

	public void recallX() throws NumberFormatException, IOException {
		// Y' <---- W * X0
		X0 = readRecall("x");

		Matrix op = new Matrix(w.row, w.column);
		Vector prevX = null, prevY = null, px = null, py = null;

		prevY = op.MultiplyMatrixWithVector(w, X0);
		prevY = hebbRule(prevY);
		System.out.println("Y' = ");
		prevY.printVector();

		int i = 1;

		String x = "x'", y = "y''";
		while (true) {
			if (i % 2 == 0) {
				py = op.MultiplyMatrixWithVector(w, prevX);
				py = hebbRule(py);
				System.out.println(y);
				py.printVector();
				y += "'";
			} else {
				px = op.MultiplyVectorWithMatrix(w, prevY);
				px = hebbRule(px);
				System.out.println(x);
				px.printVector();
				x += "'";
			}

			if (px != null && py != null && prevY != null && prevX != null
					&& checkReasoning(prevY, py) && checkReasoning(prevX, px)) {
				System.out.println(i + 1 + "  Stop , Reasoning Occured");
				break;
			}

			prevX = px;
			prevY = py;

			// PREVX.PRINTVECTOR();
			// PREVY.PRINTVECTOR();

			i++;
		}
	}

	public void recallY() throws NumberFormatException, IOException {
		// Y0 * W ----> X'
		Y0 = readRecall("y");

		Matrix op = new Matrix(w.row, w.column);
		Vector prevX = null, prevY = null, px = null, py = null;

		Y0.printVector();

		prevX = op.MultiplyVectorWithMatrix(w, Y0);
		prevX = hebbRule(prevX);
		System.out.println("X' = ");
		prevX.printVector();

		int i = 1;

		String x = "x'", y = "y''";
		while (true) {
			if (i % 2 == 0) {
				px = op.MultiplyVectorWithMatrix(w, prevY);
				px = hebbRule(px);
				System.out.println(y);
				px.printVector();
				y += "'";
			} else {
				py = op.MultiplyMatrixWithVector(w, prevX);
				py = hebbRule(py);
				System.out.println(x);
				py.printVector();
				x += "'";
			}

			if (px != null && py != null && prevY != null && prevX != null
					&& checkReasoning(prevY, py) && checkReasoning(prevX, px)) {
				System.out.println(i + 1 + "  Stop , Reasoning Occured");
				break;
			}

			prevX = px;
			prevY = py;

			// PREVX.PRINTVECTOR();
			// PREVY.PRINTVECTOR();

			i++;
		}
	}

	public void computationalEnergy() {
		Matrix op = new Matrix(w.row, w.column);
		Vector energy1 = op.MultiplyVectorWithMatrix(w, Vy1);
		System.out.println("computationalEnergy [-y1 * w * x1]= "
				+ -energy1.Multiply2Vectors(Vx1));

		Vector energy2 = op.MultiplyVectorWithMatrix(w, Vy2);
		System.out.println("computationalEnergy [-y2 * w * x2]= "
				+ -energy2.Multiply2Vectors(Vx2));
	}
}
