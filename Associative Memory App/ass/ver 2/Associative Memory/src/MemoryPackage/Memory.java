package MemoryPackage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.Vector;

public class Memory {
	Scanner in = new Scanner(System.in);
	Vectoor Vx1, Vx2, Vx3, X0, Y0, Z0;
	Matrix w;

	public void initRecall(int type) throws IOException {
		ImageProcessing img = new ImageProcessing("images/recall" + type
				+ ".bmp", type);
		Vector<Integer> x = img.readImage();
		x = img.BiPolarFunction();
		img.writeToFile(String.valueOf(type));

		int vect[] = new int[x.size()];

		for (int i = 0; i < x.size(); i++)
			vect[i] = x.get(i);

		if (type == 1) {
			X0 = new Vectoor(vect, x.size());
		} else if (type == 3) {
			Y0 = new Vectoor(vect, x.size());
		} else {
			Z0 = new Vectoor(vect, x.size());
		}
	}

	public int chooseExperiment() {
		Scanner in = new Scanner(System.in);
		System.out.println("***********************************");
		System.out.println("choose [1] to do Experiment #[1] : ");
		System.out.println("choose [2] to do Experiment #[2] : ");
		System.out.println("choose [3] to do Experiment #[3] : ");
		System.out.println("choose [4] to do Experiment #[4] : ");
		System.out.println("***********************************");
		int choice = in.nextInt();
		return choice;
	}

	public void initVectors(String type) throws IOException {

		ImageProcessing img1 = new ImageProcessing("images/" + type + ".bmp",
				Integer.valueOf(type));

		img1.readImage();
		img1.BiPolarFunction();
		img1.writeToFile(type + type);

		BufferedReader reader = new BufferedReader(new FileReader("vectorX"
				+ type + ".txt"));

		String line = "";
		line = reader.readLine();
		String[] s = line.split(" ");
		int vect[] = new int[s.length];

		for (int j = 0; j < s.length; j++)
			vect[j] = Integer.valueOf(s[j]);

		if (type.equals("1")) {
			Vx1 = new Vectoor(vect, vect.length);
		} else if (type.equals("3")) {
			Vx2 = new Vectoor(vect, vect.length);
		} else {
			Vx3 = new Vectoor(vect, vect.length);
		}
		reader.close();
	}

	public void init(int exp) throws IOException {
		if (exp == 1) {
			initRecall(1);
			initVectors("1");
			initVectors("3");
			initVectors("8");
		} else if (exp == 2) {
			initRecall(3);
			initVectors("1");
			initVectors("3");
			initVectors("8");
		} else if (exp == 3) {
			initRecall(8);
			initVectors("1");
			initVectors("3");
			initVectors("8");
		} else {
			initRecall(1);
			initRecall(3);
			initRecall(8);
			initVectors("1");
			initVectors("3");
			initVectors("8");
		}

	}

	public Matrix ComputeMemory() {
		Matrix result1 = new Matrix(Vx1.size, Vx1.size);
		result1.Multiply2Vector(Vx1, Vx1);

		Matrix result2 = new Matrix(Vx2.size, Vx2.size);
		result2.Multiply2Vector(Vx2, Vx2);

		Matrix result3 = new Matrix(Vx3.size, Vx3.size);
		result3.Multiply2Vector(Vx3, Vx3);

		Matrix ww = result1.Add2Matrix(result2);
		w = ww.Add2Matrix(result3);
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

	public boolean checkReasoning(Vectoor prev, Vectoor curr) {
		for (int i = 0; i < prev.size; i++)
			if (prev.vect[i] != curr.vect[i])
				return false;
		return true;
	}

	public Vectoor hebbRule(Vectoor v) {
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

		if (v.getDiff(Vx1) <= v.getDiff(Vx2)
				&& v.getDiff(Vx1) <= v.getDiff(Vx3))
			v = Vx1;
		else if (v.getDiff(Vx2) <= v.getDiff(Vx1)
				&& v.getDiff(Vx2) <= v.getDiff(Vx3))
			v = Vx2;
		else
			v = Vx3;
		return v;
	}

	public void recallX() throws NumberFormatException, IOException {
		// Y' <---- W * X0

		Matrix op = new Matrix(w.row, w.column);
		Vectoor prevX = null, prevXX = null, px = null, py = null;

		prevXX = op.MultiplyMatrixWithVector(w, X0);
		prevXX = hebbRule(prevXX);
		System.out.println("X' = ");
		prevXX.printVector();

		int i = 1;

		String x = "x'", y = "X''";
		while (true) {
			if (i % 2 == 0) {
				py = op.MultiplyMatrixWithVector(w, prevX);
				py = hebbRule(py);
				System.out.println(y);
				py.printVector();
				y += "'";
			} else {
				px = op.MultiplyVectorWithMatrix(w, prevXX);
				px = hebbRule(px);
				System.out.println(x);
				px.printVector();
				x += "'";
			}

			if (px != null && py != null && prevXX != null && prevX != null
					&& checkReasoning(prevXX, py) && checkReasoning(prevX, px)) {
				System.out.println(i + 1 + "  Stop , Reasoning Occured");
				break;
			}

			prevX = px;
			prevXX = py;
			i++;
		}

		ImageProcessing img = new ImageProcessing("", 1);
		img.writeImage(prevX.vect, "Xresult");
	}

	public void recallY() throws NumberFormatException, IOException {
		// Y0 * W ----> Y'

		Matrix op = new Matrix(w.row, w.column);
		Vectoor prevYY = null, prevY = null, px = null, py = null;

		// Y0.printVector();

		prevYY = op.MultiplyVectorWithMatrix(w, Y0);
		prevYY = hebbRule(prevYY);
		System.out.println("Y' = ");
		prevYY.printVector();

		int i = 1;

		String x = "y'", y = "Y''";
		while (true) {
			if (i % 2 == 0) {
				px = op.MultiplyVectorWithMatrix(w, prevY);
				px = hebbRule(px);
				System.out.println(y);
				px.printVector();
				y += "'";
			} else {
				py = op.MultiplyMatrixWithVector(w, prevYY);
				py = hebbRule(py);
				System.out.println(x);
				py.printVector();
				x += "'";
			}

			if (px != null && py != null && prevY != null && prevYY != null
					&& checkReasoning(prevY, py) && checkReasoning(prevYY, px)) {
				System.out.println(i + 1 + "  Stop , Reasoning Occured");
				break;
			}

			prevYY = px;
			prevY = py;

			i++;
		}
		ImageProcessing img = new ImageProcessing("", 3);
		img.writeImage(prevY.vect, "Yresult");
	}

	public void recallZ() throws NumberFormatException, IOException {
		// Z0 * W ----> Z'

		Matrix op = new Matrix(w.row, w.column);
		Vectoor prevZ = null, prevZZ = null, px = null, py = null;

		// Z0.printVector();

		prevZ = op.MultiplyVectorWithMatrix(w, Z0);
		prevZ = hebbRule(prevZ);
		System.out.println("Z' = ");
		prevZ.printVector();

		int i = 1;

		String x = "z'", y = "Z''";
		while (true) {
			if (i % 2 == 0) {
				px = op.MultiplyVectorWithMatrix(w, prevZZ);
				px = hebbRule(px);
				System.out.println(y);
				px.printVector();
				y += "'";
			} else {
				py = op.MultiplyMatrixWithVector(w, prevZ);
				py = hebbRule(py);
				System.out.println(x);
				py.printVector();
				x += "'";
			}

			if (px != null && py != null && prevZZ != null && prevZ != null
					&& checkReasoning(prevZZ, py) && checkReasoning(prevZ, px)) {
				System.out.println(i + 1 + "  Stop , Reasoning Occured");
				break;
			}

			prevZ = px;
			prevZZ = py;

			i++;
		}

		ImageProcessing img = new ImageProcessing("", 1);
		img.writeImage(prevZ.vect, "Zresult");
	}

	public void computationalEnergy() {
		Matrix op = new Matrix(w.row, w.column);
		Vectoor energy1 = op.MultiplyVectorWithMatrix(w, Vx1);
		System.out.println("resonanceEnergy [-x1 * w * x1]= "
				+ -energy1.Multiply2Vectors(Vx1));

		op = new Matrix(w.row, w.column);
		Vectoor energy2 = op.MultiplyVectorWithMatrix(w, Vx2);
		System.out.println("resonanceEnergy [-x2 * w * x2]= "
				+ -energy2.Multiply2Vectors(Vx2));

		op = new Matrix(w.row, w.column);
		Vectoor energy3 = op.MultiplyVectorWithMatrix(w, Vx3);
		System.out.println("resonanceEnergy [-x3 * w * x3]= "
				+ -energy3.Multiply2Vectors(Vx3));
	}
}
