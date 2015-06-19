package MemoryPackage;
public class Matrix {
	public int row, column;
	public int matrix[][];

	public Matrix(int row, int column) {
		this.row = row;
		this.column = column;
		matrix = new int[row][column];
	}
	
	public int getRow(){
		return row;
	}
	
	public int getColumn(){
		return column;
	}
	
	public int[][] getvect(){
		return matrix;
	}
	
	public Matrix Multiply2Vector(Vectoor y, Vectoor x) {
		for (int i = 0; i < y.size; ++i) {
			for (int j = 0; j < x.size; ++j) {
				this.matrix[i][j] = y.vect[i] * x.vect[j];
			}
		}
		return this;
	}
	
	public Vectoor MultiplyMatrixWithVector(Matrix w, Vectoor v) {
		int arr[] = new int[w.row];
		int s = 0;
		for (int i = 0; i < w.getRow(); ++i) {
			for (int j = 0; j < v.size; ++j) {
				s += w.matrix[i][j] * v.vect[j];
			}
			arr[i] = s;
			s = 0;
		}
		Vectoor op = new Vectoor(arr,w.row);
		op.vect = arr;
		return op;
	}
	
	public Matrix PrintMatrix() {
		int matrix[][] = getvect();
		for (int i = 0; i < getRow(); ++i) {
			for (int j = 0; j < getColumn(); ++j) {
				System.out.print(matrix[i][j] + " ");
			}
			System.out.println();
		}
		return null;
	}
	
	public Vectoor MultiplyVectorWithMatrix(Matrix w, Vectoor v) {
		int arr[] = new int[w.column];
		int s = 0;
		for (int i = 0; i < w.getColumn(); ++i) {
			for (int j = 0; j < v.size; ++j) {
				s += w.matrix[j][i] * v.vect[j];
			}
			arr[i] = s;
			s = 0;
		}
		Vectoor op = new Vectoor(arr,w.column);
		op.vect = arr;
		return op;
	}
	
	public Matrix Add2Matrix(Matrix sec) {
		Matrix result = new Matrix(this.row, this.column);
		for (int i = 0; i < this.row; ++i) {
			for (int j = 0; j < this.column; ++j) {
				result.matrix[i][j] = this.matrix[i][j] + sec.matrix[i][j];
			}
		}		
		return result;
	}
}
