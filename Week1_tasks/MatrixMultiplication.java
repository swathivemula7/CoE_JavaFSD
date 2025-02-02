package week1;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class MatrixMultiplication {

    static class MultiplyTask implements Callable<Integer> {
        private final int[][] matrixA;
        private final int[][] matrixB;
        private final int row;
        private final int col;

        public MultiplyTask(int[][] matrixA, int[][] matrixB, int row, int col) {
            this.matrixA = matrixA;
            this.matrixB = matrixB;
            this.row = row;
            this.col = col;
        }

        @Override
        public Integer call() {
            int sum = 0;
            for (int k = 0; k < matrixA[0].length; k++) {
                sum += matrixA[row][k] * matrixB[k][col];
            }
            return sum;
        }
    }

    public static int[][] multiplyMatrices(int[][] matrixA, int[][] matrixB) throws InterruptedException, ExecutionException {
        int rows = matrixA.length;
        int cols = matrixB[0].length;

        int[][] result = new int[rows][cols];

        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        List<Future<Integer>> futures = new ArrayList<>();

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                MultiplyTask task = new MultiplyTask(matrixA, matrixB, i, j);
                futures.add(executor.submit(task));
            }
        }

        int index = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result[i][j] = futures.get(index++).get();
            }
        }

        executor.shutdown();
        return result;
    }

    public static void main(String[] args) {
        int[][] matrixA = {{1, 2}, {3, 4}};
        int[][] matrixB = {{2, 0}, {1, 2}};
        
        try {
            int[][] result = multiplyMatrices(matrixA, matrixB);

            System.out.println("Result of the multiplication:");
            for (int i = 0; i < result.length; i++) {
                for (int j = 0; j < result[i].length; j++) {
                    System.out.print(result[i][j] + " ");
                }
                System.out.println();
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}
