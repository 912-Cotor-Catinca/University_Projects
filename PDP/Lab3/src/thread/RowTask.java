package thread;

import model.Matrix;
import model.Pair;

public class RowTask extends MatrixTask{
    public RowTask(int iStart, int jStart, int count, Matrix a, Matrix b, Matrix result) {
        super(iStart, jStart, count, a, b, result);
    }
    @Override
    public void computeElements() {
        int i = iStart, j = jStart;
        int size = sizeofTask;
        while(size > 0 && i < result.n && j < result.m) {
            pairs.add(new Pair<>(i, j));
            j ++;
            size--;
            if(j == result.n) {
                j = 0;
                i++;
            }
        }
    }
}
