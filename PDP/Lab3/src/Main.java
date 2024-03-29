import model.Matrix;
import model.MatrixException;
import runner.NormalThreadRunner;
import runner.ThreadPoolRunner;

public class Main {
    private static final int n1=100;
    private static final int m1=100;
    private static final int n2=100;
    private static final int m2=100;

    private static final int NO_THREADS = 4;
    private static final String APPROACH = "Pool";
    private static final String FUNCTION = "Column";

    public static void main(String[] args) {
        Matrix a = new Matrix(n1, m1);
        Matrix b = new Matrix(n2, m2);

        a.populate();
        b.populate();

        System.out.println(a);
        System.out.println(b);

        if (a.m == b.n) {
            Matrix result = new Matrix(a.n, b.m);
            float start = System.nanoTime() / 1_000_000;
            if(APPROACH.equals("Pool")) {
                try{
                    ThreadPoolRunner.run(a,b,result, NO_THREADS, FUNCTION);
                } catch (MatrixException e) {
                    System.err.println(e.getMessage());
                }
            }
            else if (APPROACH.equals("Normal")) {
                try{
                    NormalThreadRunner.run(a,b,result, NO_THREADS, FUNCTION);
                } catch (MatrixException e) {
                    System.err.println(e.getMessage());
                }
            }
            else {
                System.err.println("Invalid approach");
            }

            float end = System.nanoTime() / 1_000_000;

            System.out.println("Time elapsed: " + (end - start) / 1000 + " seconds");

        }
        else {
            System.err.println("The matrices cannot be multiplied");
        }

    }
}
