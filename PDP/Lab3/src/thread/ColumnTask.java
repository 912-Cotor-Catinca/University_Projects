package thread;

import model.Matrix;
import model.Pair;

public final class ColumnTask extends MatrixTask {
    public ColumnTask(int iStart, int jStart, int count, Matrix a, Matrix b, Matrix result) {
        super(iStart, jStart, count, a, b, result);
    }

    @Override
    public void computeElements() {
        int i = iStart, j = jStart;
        int size = sizeofTask;
        while (size > 0 && i < result.n && j < result.m) {
            pairs.add(new Pair<>(i, j));
            i++;
            size--;
            if(i == result.m) {
                i = 0;
                j++;
            }
        }
    }
}
