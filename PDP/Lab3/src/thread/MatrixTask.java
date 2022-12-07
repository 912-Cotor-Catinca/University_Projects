package thread;

import Utils.Utils;
import model.Matrix;
import model.MatrixException;
import model.Pair;

import java.util.ArrayList;
import java.util.List;

public abstract class MatrixTask extends Thread {
    public List<Pair<Integer, Integer>> pairs;
    public final int iStart, jStart, sizeofTask;
    public final Matrix a, b, result;
    public int k;

    public MatrixTask(int iStart, int jStart, int sizeofTask, Matrix a, Matrix b, Matrix result, int k) {
        this.iStart = iStart;
        this.jStart = jStart;
        this.sizeofTask = sizeofTask;
        this.a = a;
        this.b = b;
        this.result = result;
        this.pairs = new ArrayList<>();
        this.k = k;
        computeElements();
    }

    public MatrixTask(int iStart, int jStart, int sizeofTask, Matrix a, Matrix b, Matrix result) {
        this.iStart = iStart;
        this.jStart = jStart;
        this.sizeofTask = sizeofTask;
        this.a = a;
        this.b = b;
        this.result = result;
        this.pairs = new ArrayList<>();
        computeElements();
    }

    public abstract void computeElements();

    @Override
    public void run() {
        for(Pair<Integer, Integer> p : pairs) {
            int i = p.first;
            int j = p.second;
            try {
                result.set(i, j, Utils.buildElement(a, b, i, j));
            } catch (MatrixException e) {
                e.printStackTrace();
            }
        }
    }
}
