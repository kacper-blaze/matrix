import static org.junit.Assert.*;
import org.junit.Test;

public class MatrixTest {

    @Test
    public void testMatrixDimensions() {
        Matrix m = new Matrix(3, 4);
        assertEquals(3, m.shape()[0]);
        assertEquals(4, m.shape()[1]);
    }

    @Test
    public void testMatrixFrom2DArray() {
        double[][] data = {
                {1.0, 2.0, 3.0},
                {4.0}
        };

        Matrix m = new Matrix(data);
        double[][] array = m.asArray();

        assertEquals(2, array.length, 0.0001);
        assertEquals(3, array[0].length, 0.0001);

        assertEquals(1.0, array[0][0], 0.0001);
        assertEquals(2.0, array[0][1], 0.0001);
        assertEquals(3.0, array[0][2], 0.0001);
        assertEquals(4.0, array[1][0], 0.0001);
        assertEquals(0.0, array[1][1], 0.0001);
        assertEquals(0.0, array[1][2], 0.0001);
    }

    @org.junit.Test
    public void asArray() {
        Matrix m = new Matrix(2, 3);
        m.set(0, 0, 1.0);
        m.set(0, 1, 2.0);
        m.set(0, 2, 3.0);
        m.set(1, 0, 4.0);
        m.set(1, 1, 5.0);
        m.set(1, 2, 6.0);

        double[][] arr = m.asArray();
        assertEquals(2, arr.length, 0.0001);
        assertEquals(3, arr[0].length, 0.0001);

        assertEquals(1.0, arr[0][0], 0.0001);
        assertEquals(2.0, arr[0][1], 0.0001);
        assertEquals(3.0, arr[0][2], 0.0001);
        assertEquals(4.0, arr[1][0], 0.0001);
        assertEquals(5.0, arr[1][1], 0.0001);
        assertEquals(6.0, arr[1][2], 0.0001);
    }

    @org.junit.Test
    public void get() {
        double[][] data = {
                {1.0, 2.0},
                {3.0, 4.0}
        };
        Matrix m = new Matrix(data);

        assertEquals(1.0, m.get(0, 0), 0.0001);
        assertEquals(2.0, m.get(0, 1), 0.0001);
        assertEquals(3.0, m.get(1, 0), 0.0001);
        assertEquals(4.0, m.get(1, 1), 0.0001);
    }

    @org.junit.Test
    public void set() {
        Matrix m = new Matrix(2, 2);
        m.set(0, 0, 1.0);
        m.set(0, 1, 2.0);
        m.set(1, 0, 3.0);
        m.set(1, 1, 4.0);

        double[][] expected = {
                {1.0, 2.0},
                {3.0, 4.0}
        };
        assertArrayEquals(expected, m.asArray());
    }

    @org.junit.Test
    public void testToString() {
        String s= "[[1.0,2.3,4.56], [12.3,  45, 21.8]]";
        s= s.replaceAll("(\\[|\\]|\\s)+","");
        String[] t = s.split("(,)+");
        for(String x:t){
            System.out.println(String.format("\'%s\'",x ));
        }

        double[]d=new double[t.length];
        for(int i=0;i<t.length;i++) {
            d[i] = Double.parseDouble(t[i]);
        }

        double arr[][]=new double[1][];
        arr[0]=d;

        for(int i=0;i<arr.length;i++){
            for(int j=0;j<arr[i].length;j++){
                System.out.println(arr[i][j]);
            }
        }
    }

    @org.junit.Test
    public void testToStringCount() {
        Matrix m = new Matrix(2, 3);
        String str = m.toString();
        
        int commas = str.length() - str.replace(",", "").length();
        int brackets = str.length() - str.replace("[", "").length();
        
        assertEquals(5, commas);
        assertEquals(3, brackets);
    }

    @org.junit.Test
    public void reshape() {
        Matrix m = new Matrix(2, 2);
        m.set(0, 0, 1.0);
        m.set(0, 1, 2.0);
        m.set(1, 0, 3.0);
        m.set(1, 1, 4.0);

        m.reshape(1, 4);
        assertEquals(1.0, m.get(0, 0), 0.0001);
        assertEquals(2.0, m.get(0, 1), 0.0001);
        assertEquals(3.0, m.get(0, 2), 0.0001);
        assertEquals(4.0, m.get(0, 3), 0.0001);
    }

    @org.junit.Test(expected = RuntimeException.class)
    public void testReshapeException() {
        Matrix m = new Matrix(2, 3);
        m.reshape(2, 2);
    }

    @org.junit.Test
    public void shape() {
        Matrix m = new Matrix(2, 2);
        assertEquals(2, m.shape()[0]);
        assertEquals(2, m.shape()[1]);
    }

    @org.junit.Test
    public void add() {
        double[][] data = {
            {1.0, 2.0},
            {3.0, 4.0}
        };
        Matrix m = new Matrix(data);
        Matrix negative = m.mul(-1);
        Matrix result = m.add(negative);
        
        assertEquals(0.0, result.frobenius(), 0.0001);
    }

    @org.junit.Test(expected = RuntimeException.class)
    public void testAddException() {
        Matrix m = new Matrix(2, 2);
        Matrix n = new Matrix(2, 3);
        m.add(n);
    }

    @org.junit.Test
    public void sub() {
        double[][] data = {
            {1.0, 2.0},
            {3.0, 4.0}
        };
        Matrix m = new Matrix(data);
        Matrix n = new Matrix(data);

        Matrix result = m.sub(n);
        assertEquals(0.0, result.frobenius(), 0.0001);
    }

    @org.junit.Test(expected = RuntimeException.class)
    public void testSubException() {
        Matrix m = new Matrix(2, 2);
        Matrix n = new Matrix(2, 3);
        m.sub(n);
    }

    @org.junit.Test
    public void mul() {
        double[][] data = {
            {1.0, 2.0},
            {3.0, 4.0}
        };
        Matrix m = new Matrix(data);
        Matrix result = m.mul(-1);
        Matrix sum = m.add(result);
        
        assertEquals(0.0, sum.frobenius(), 0.0001);
    }

    @org.junit.Test(expected = RuntimeException.class)
    public void testMulException() {
        Matrix m = new Matrix(2, 2);
        Matrix n = new Matrix(2, 3);
        m.mul(n);
    }

    @org.junit.Test
    public void div() {
        double[][] data = {
            {1.0, 2.0, 3.0},
            {4.0, 5.0, 6.0}
        };
        Matrix m = new Matrix(data);
        Matrix result = m.div(m);
        
        double expectedNorm = Math.sqrt(2 * 3);
        assertEquals(expectedNorm, result.frobenius(), 0.0001);
    }

    @org.junit.Test(expected = RuntimeException.class)
    public void testDivException() {
        Matrix m = new Matrix(2, 2);
        Matrix n = new Matrix(2, 3);
        m.div(n);
    }
    //dzielenie przez 0 daje inf wiec dziala i nie daje wyjatku

    @org.junit.Test
    public void testAdd() {
        Matrix m = new Matrix(2, 2);
        m.set(0, 0, 1.0);
        m.set(0, 1, 2.0);
        m.set(1, 0, 3.0);
        m.set(1, 1, 4.0);

        int n = 2;

        Matrix result = m.add(n);
        assertEquals(3.0, result.get(0, 0), 0.0001);
        assertEquals(4.0, result.get(0, 1), 0.0001);
        assertEquals(5.0, result.get(1, 0), 0.0001);
        assertEquals(6.0, result.get(1, 1), 0.0001);
    }

    @org.junit.Test
    public void testSub() {
        Matrix m = new Matrix(2, 2);
        m.set(0, 0, 1.0);
        m.set(0, 1, 2.0);
        m.set(1, 0, 3.0);
        m.set(1, 1, 4.0);

        int n = 2;

        Matrix result = m.sub(n);
        assertEquals(-1.0, result.get(0, 0), 0.0001);
        assertEquals(0.0, result.get(0, 1), 0.0001);
        assertEquals(1.0, result.get(1, 0), 0.0001);
        assertEquals(2.0, result.get(1, 1), 0.0001);
    }

    @org.junit.Test
    public void testMul() {
        Matrix m = new Matrix(2, 2);
        m.set(0, 0, 1.0);
        m.set(0, 1, 2.0);
        m.set(1, 0, 3.0);
        m.set(1, 1, 4.0);

        int n = 2;

        Matrix result = m.mul(n);
        assertEquals(2.0, result.get(0, 0), 0.0001);
        assertEquals(4.0, result.get(0, 1), 0.0001);
        assertEquals(6.0, result.get(1, 0), 0.0001);
        assertEquals(8.0, result.get(1, 1), 0.0001);
    }

    @org.junit.Test
    public void testDiv() {
        Matrix m = new Matrix(2, 2);
        m.set(0, 0, 1.0);
        m.set(0, 1, 2.0);
        m.set(1, 0, 3.0);
        m.set(1, 1, 4.0);

        int n = 2;

        Matrix result = m.div(n);
        assertEquals(0.5, result.get(0, 0), 0.0001);
        assertEquals(1.0, result.get(0, 1), 0.0001);
        assertEquals(1.5, result.get(1, 0), 0.0001);
        assertEquals(2.0, result.get(1, 1), 0.0001);
    }

    @org.junit.Test
    public void dot() {
        Matrix m = new Matrix(2, 2);
        m.set(0, 0, 1.0);
        m.set(0, 1, 2.0);
        m.set(1, 0, 3.0);
        m.set(1, 1, 4.0);

        Matrix n = new Matrix(2, 2);
        n.set(0, 0, 1.0);
        n.set(0, 1, 2.0);
        n.set(1, 0, 3.0);
        n.set(1, 1, 4.0);

        Matrix result = m.dot(n);
        assertEquals(7.0, result.get(0, 0), 0.0001);
        assertEquals(10.0, result.get(0, 1), 0.0001);
        assertEquals(15.0, result.get(1, 0), 0.0001);
        assertEquals(22.0, result.get(1, 1), 0.0001);
    }

    @org.junit.Test
    public void frobenius() {
        Matrix m = new Matrix(2, 2);
        m.set(0, 0, 1.0);
        m.set(0, 1, 2.0);
        m.set(1, 0, 3.0);
        m.set(1, 1, 4.0);

        double result = m.frobenius();
        assertEquals(5.477225575051661, result, 0.0001);
    }

    @org.junit.Test
    public void random() {
        Matrix m = new Matrix(2, 2);
        m.random(2,2);
        assertEquals(2, m.shape()[0]);
        assertEquals(2, m.shape()[1]);
    }

    @Test
    public void eye() {
        Matrix m = Matrix.eye(2);
        m.eye(2);
        assertEquals(2, m.shape()[0]);
        assertEquals(2, m.shape()[1]);
        assertEquals(1.0, m.get(0, 0), 0.0001);
        assertEquals(0.0, m.get(0, 1), 0.0001);
        assertEquals(0.0, m.get(1, 0), 0.0001);
        assertEquals(1.0, m.get(1, 1), 0.0001);
    }

    @org.junit.Test
    public void eyeFrobenius() {
        int n = 5;
        Matrix m = Matrix.eye(n);
        
        double expectedNorm = Math.sqrt(n);
        assertEquals(expectedNorm, m.frobenius(), 0.0001);
    }

    @org.junit.Test
    public void determinant() {
        Matrix m = new Matrix(2, 2);
        m.set(0, 0, 1.0);
        m.set(0, 1, 2.0);
        m.set(1, 0, 3.0);
        m.set(1, 1, 4.0);

        double result = m.determinant();
        assertEquals(-2.0, result, 0.0001);
    }

    @org.junit.Test
    public void inverseVector() {
        double[][] data = {
            {4.0, 7.0},
            {2.0, 6.0}
        };
        Matrix m = new Matrix(data);
        Matrix mInv = m.inverse();
        
        double[][] vectorData = {{1.0}, {2.0}};
        Matrix v = new Matrix(vectorData);
        
        Matrix result = m.dot(v);
        Matrix backToOriginal = mInv.dot(result);
        
        assertEquals(v.get(0, 0), backToOriginal.get(0, 0), 0.0001);
        assertEquals(v.get(1, 0), backToOriginal.get(1, 0), 0.0001);
    }

    @org.junit.Test
    public void testInverseIdentity() {
        double[][] data = {
            {4.0, 7.0},
            {2.0, 6.0}
        };
        Matrix m = new Matrix(data);
        Matrix mInv = m.inverse();
        Matrix identity = mInv.dot(m);
        
        Matrix expected = Matrix.eye(2);
        double norm = identity.sub(expected).frobenius();
        assertEquals(0.0, norm, 0.0001);
    }

    @org.junit.Test
    public void inverse() {
        Matrix m = new Matrix(2, 2);
        m.set(0, 0, 1.0);
        m.set(0, 1, 2.0);
        m.set(1, 0, 3.0);
        m.set(1, 1, 4.0);

        Matrix result = m.inverse();
        assertEquals(-2.0, result.get(0, 0), 0.0001);
        assertEquals(1.0, result.get(0, 1), 0.0001);
        assertEquals(1.5, result.get(1, 0), 0.0001);
        assertEquals(-0.5, result.get(1, 1), 0.0001);
    }
}