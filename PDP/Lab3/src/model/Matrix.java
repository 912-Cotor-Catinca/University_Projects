package model;

import java.util.Random;

public final class Matrix {
    public final int n, m;
    public int[][] matrix;

    public Matrix(int n, int m) {
        this.n = n;
        this.m = m;
        matrix = new int[n][m];
    }

    public void populate() {
        Random random = new Random();
        for (int i = 0; i < n; ++i)
            for (int j =0; j < m; ++j)
                matrix[i][j] = 1;
    }

    public int get(int r, int c) {
        return matrix[r][c];
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < m; ++j) {
                stringBuilder.append(matrix[i][j]).append(" ");
            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    public void set(int r, int c, int val) {
        matrix[r][c] = val;
    }
}
