import java.util.Random;

public class Matrix {
    double[] data;
    int rows;
    int cols;

    Matrix(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        data = new double[rows * cols];
    }

    Matrix(double[][] d) {
        rows = d.length;
//        System.out.println(rows);
        cols = 0;
        for (int i = 0; i < rows; ++i){
            if (d[i].length > cols){
                cols = d[i].length;
            }
        }
//        System.out.println(cols);
        data = new double[rows * cols];
        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < cols; ++j) {
                if (j < d[i].length) {
                    data[i * cols + j] = d[i][j];
                } else {
                    data[i * cols + j] = 0;
                }
//                System.out.print(data[i * cols + j] + " ");
            }
//            System.out.println();
        }
    }

    double[][] asArray() {
        double[][] result = new double[rows][cols];
        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < cols; ++j) {
                result[i][j] = data[i * cols + j];
            }
        }
        return result;
    }

    double get(int r,int c){
        if (r < 0 || r > rows) {
            throw new RuntimeException(String.format("%d is not a valid index for this matrix",rows));
        }
        if (c < 0 || c > cols) {
            throw new RuntimeException(String.format("%d is not a valid index for this matrix",cols));
        }
        return data[r * cols + c];
    }

    void set (int r,int c, double value){
        if (r < 0 || r > rows) {
            throw new RuntimeException(String.format("%d is not a valid index for this matrix",rows));
        }
        if (c < 0 || c > cols) {
            throw new RuntimeException(String.format("%d is not a valid index for this matrix",cols));
        }
        data[r * cols + c] = value;
    }

    public String toString(){
        StringBuilder buf = new StringBuilder();
        buf.append("[");
        for (int i = 0; i < rows; ++i) {
            if (i > 0) {
                buf.append(",\n ");
            }
            buf.append("[");
            for (int j = 0; j < cols; ++j) {
                if (j > 0) {
                    buf.append(", ");
                }
                buf.append(data[i * cols + j]);
            }
            buf.append("]");
        }
        buf.append("]");
//        System.out.println(buf.toString());
        return buf.toString();
    }

    void reshape(int newRows, int newCols){
        if(rows * cols != newRows * newCols)
            throw new RuntimeException(String.format("%d x %d matrix can't be reshaped to %d x %d",rows,cols,newRows,newCols));
        rows = newRows;
        cols = newCols;
    }

    int[] shape(){
        return new int[]{rows, cols};
    }

    Matrix add(Matrix m){
        if(rows != m.rows || cols != m.cols)
            throw new RuntimeException(String.format("Matrices must have the same shape to be added"));
        Matrix result = new Matrix(rows, cols);
        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < cols; ++j) {
                result.set(i,j,get(i,j) + m.get(i,j));
            }
        }
        return result;
    }

    Matrix sub(Matrix m){
        if(rows != m.rows || cols != m.cols)
            throw new RuntimeException(String.format("Matrices must have the same shape to be subtracted"));
        Matrix result = new Matrix(rows, cols);
        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < cols; ++j) {
                result.set(i,j,get(i,j) - m.get(i,j));
            }
        }
        return result;
    }
    Matrix mul(Matrix m){
        if(rows != m.rows || cols != m.cols)
            throw new RuntimeException(String.format("Matrices must have the same shape to be multiplied"));
        Matrix result = new Matrix(rows, cols);
        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < cols; ++j) {
                result.set(i,j,get(i,j) * m.get(i,j));
            }
        }
        return result;
    }
    Matrix div(Matrix m){
        if(rows != m.rows || cols != m.cols)
            throw new RuntimeException(String.format("Matrices must have the same shape to be divided"));
        Matrix result = new Matrix(rows, cols);
        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < cols; ++j) {
                result.set(i,j,get(i,j) / m.get(i,j));
            }
        }
        return result;
    }

    Matrix add(double w){
        Matrix result = new Matrix(rows, cols);
        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < cols; ++j) {
                result.set(i,j,get(i,j) + w);
            }
        }
        return result;
    }
    Matrix sub(double w){
        Matrix result = new Matrix(rows, cols);
        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < cols; ++j) {
                result.set(i,j,get(i,j) - w);
            }
        }
        return result;
    }
    Matrix mul(double w){
        Matrix result = new Matrix(rows, cols);
        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < cols; ++j) {
                result.set(i,j,get(i,j) * w);
            }
        }
        return result;
    }
    Matrix div(double w){
        Matrix result = new Matrix(rows, cols);
        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < cols; ++j) {
                result.set(i,j,get(i,j) / w);
            }
        }
        return result;
    }

    Matrix dot(Matrix m){
        if(cols != m.rows)
            throw new RuntimeException(String.format("Matrices must have the same shape to be dotted"));
        Matrix result = new Matrix(rows, m.cols);
        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < m.cols; ++j) {
                double sum = 0;
                for (int k = 0; k < cols; ++k) {
                    sum += get(i, k) * m.get(k, j);
                }
                result.set(i, j, sum);
            }
        }
        return result;
    }

    double frobenius(){
        double sum = 0;
        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < cols; ++j) {
                sum += get(i,j) * get(i,j);
            }
        }
        return Math.sqrt(sum);
    }

    public static Matrix random(int rows, int cols){
        Matrix m = new Matrix(rows, cols);
        Random r = new Random();
        m.set(0,0,r.nextDouble());
        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < cols; ++j) {
                m.set(i,j, r.nextDouble());
            }
        }
        return m;
    }

    public static Matrix eye(int n){
        Matrix m = new Matrix(n,n);
        for (int i = 0; i < n; ++i) {
            m.set(i,i,1);
        }
        return m;
    }

    double determinant() {
        if (rows != cols)
            throw new RuntimeException("You can only calculate the determinant of a square matrix");

        int n = rows;
        double[][] a = new double[n][n];

        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                a[i][j] = get(i, j);
            }
        }

        double det = 1.0;

        for (int i = 0; i < n; ++i) {
            int pivot = i;
            for (int j = i + 1; j < n; ++j) {
                if (Math.abs(a[j][i]) > Math.abs(a[pivot][i])) {
                    pivot = j;
                }
            }

            if (Math.abs(a[pivot][i]) < 1e-10) {
                return 0.0;
            }

            if (pivot != i) {
                double[] temp = a[i];
                a[i] = a[pivot];
                a[pivot] = temp;
                det *= -1;
            }

            det *= a[i][i];

            for (int j = i + 1; j < n; ++j) {
                double factor = a[j][i] / a[i][i];
                for (int k = i; k < n; ++k) {
                    a[j][k] -= factor * a[i][k];
                }
            }
        }

        return det;
    }

    private void swapRows(int i1, int i2) {
        for (int j = 0; j < cols; j++) {
            double tmp = get(i1, j);
            set(i1, j, get(i2, j));
            set(i2, j, tmp);
        }
    }

    public Matrix inverse() {
        if (rows != cols) {
            throw new RuntimeException("Matrix must be square to compute inverse");
        }

        int n = rows;
        Matrix out = Matrix.eye(n);
        Matrix temp = new Matrix(this.asArray());
        final double verySmall = 1e-10;

        for (int i = 0; i < n; i++) {
            double max = Math.abs(temp.get(i, i));
            int pivot = i;
            for (int k = i; k < n; k++) {
                if (Math.abs(temp.get(k, i)) > max) {
                    max = Math.abs(temp.get(k, i));
                    pivot = k;
                }
            }

            if (i != pivot) {
                temp.swapRows(i, pivot);
                out.swapRows(i, pivot);
            }

            if (Math.abs(temp.get(i, i)) < verySmall) {
                throw new RuntimeException("Matrix is singular or nearly singular");
            }

            double divby = temp.get(i, i);
            for (int j = 0; j < n; j++) {
                out.set(i, j, out.get(i, j) / divby);
                temp.set(i, j, temp.get(i, j) / divby);
            }

            for (int j = 0; j < n; j++) {
                if (j != i && Math.abs(temp.get(j, i)) > verySmall) {
                    double mulby = temp.get(j, i);
                    for (int k = 0; k < n; k++) {
                        temp.set(j, k, temp.get(j, k) - mulby * temp.get(i, k));
                        out.set(j, k, out.get(j, k) - mulby * out.get(i, k));
                    }
                }
            }
        }
        
        return out;
    }

    public static void main(String[] args) {
        // Example usage
        Matrix n = new Matrix(new double[][]{{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}, {9, 10, 11, 12}});
        Matrix m = new Matrix(new double[][]{{1,2,3,4},{5,6},{7,8},{9}});
//        System.out.println("Matrix created with " + m.rows + " rows and " + m.cols + " columns");
//        m.toString();
//        m.reshape(2,8);
//        m.toString();
//        System.out.print(m.shape()[1]);
//        Matrix test = m.add(n);
//        test.toString();
//        n.div(0).toString();
//        Matrix r = Matrix.random(2,3);
    }
}