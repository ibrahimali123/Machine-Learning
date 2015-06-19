public class Vector {
	public int vect[];
	public int size;

	public Vector() {

	}

	public Vector(int vect[], int size) {
		this.size = size;
		this.vect = new int[size];
		this.vect = vect;
	}

	public int getDiff(Vector v) {
		int h = 0;
		for (int i = 0; i < v.size; i++)
			if (this.vect[i] != v.vect[i])
				h++;

		return h;
	}

	public int Multiply2Vectors(Vector v) {
		int res = 0;
		for (int i = 0; i < v.size; i++) {
			res += this.vect[i] * v.vect[i];
		}
		return res;
	}

	public void printVector() {
		for (int i = 0; i < vect.length; i++) {
			System.out.print(vect[i] + " ");
		}
		System.out.println();
	}
}
