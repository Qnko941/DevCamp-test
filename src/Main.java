import java.util.Arrays;
import java.util.Scanner;

public class Main {

    static int currentBrick = 1;
    static int currentRow = 0;
    static int currentCol = 0;
    static StringBuilder sb;

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        //reads provided rows and columns and makes them integers
        String[] input = scanner.nextLine().split(" ");
        int n = Integer.parseInt(input[0]);
        int m = Integer.parseInt(input[1]);

        boolean inputIsValid = true;

        if (n > 100 && m > 100) {
            inputIsValid = false;
        }

        //makes 2 Multidimensional arrays - First Layer and Second Layer
        int[][] firstLayer = new int[n][m];
        int[][] secondLayer = new int[n][m];
        readArrayInput(firstLayer, scanner, n, m);
        //validate there are no bricks spanning 3 rows/cols
        boolean brickCountIsValid = checkIfBricksAreValid(firstLayer);

        //first cycle - checks validness of inputs
        while (currentRow < n && inputIsValid && brickCountIsValid) {

            //second cycle - fills in second layer && makes more validations
            while (currentCol < m) {
                //checks if brick in firstLayer is horizontal or vertical
                if (firstLayer[currentRow][currentCol] == firstLayer[currentRow][currentCol + 1]) {
                    //if brick is horizontal makes first move
                    makeFirstMove(secondLayer);
                } else {
                    //if brick is vertical makes second move
                    makeSecondMove(secondLayer);
                }
            }
            if (!checkIfLayerIsValid(firstLayer, n, m)) {
                break;
            }
            //after the cycle ends we go to the second row of bricks
            currentRow += 2;
            currentCol = 0;

        }

        //it solution is not possible, prints -1
        if (!checkIfLayerIsValid(firstLayer, n, m) || !inputIsValid || !brickCountIsValid) {
            System.out.println("-1");
            System.out.println("No solution exists!");
        } else {
            //if input was valid, prints second layer
            printWall(secondLayer);
        }

    }

    private static boolean checkIfBricksAreValid(int[][] firstLayer) {
        int sameBricks = -1;
        for (int[] ints : firstLayer) {
            int temp = ints[0];
            for (int anInt : ints) {
                if (anInt == temp) {
                    sameBricks++;
                    if (sameBricks == 3) {
                        return false;
                    }
                } else {
                    sameBricks = 1;
                }
                temp = anInt;
            }
            sameBricks = -1;
        }
        return true;
    }


    //puts 2 horizontal bricks
    private static void makeSecondMove(int[][] secondLayer) {
        secondLayer[currentRow][currentCol] = currentBrick;
        secondLayer[currentRow][currentCol + 1] = currentBrick;
        currentRow++;
        currentBrick++;

        secondLayer[currentRow][currentCol] = currentBrick;
        secondLayer[currentRow][currentCol + 1] = currentBrick;
        currentBrick++;
        currentRow--;
        currentCol++;
        currentCol++;
    }

    //puts 4 bricks : 1 vertical 2 horizontal and 1 vertical
    private static void makeFirstMove(int[][] secondLayer) {
        secondLayer[currentRow][currentCol] = currentBrick;
        secondLayer[currentRow + 1][currentCol] = currentBrick;
        currentCol++;
        currentBrick++;

        secondLayer[currentRow][currentCol] = currentBrick;
        secondLayer[currentRow][currentCol + 1] = currentBrick;
        currentRow++;
        currentBrick++;

        secondLayer[currentRow][currentCol] = currentBrick;
        secondLayer[currentRow][currentCol + 1] = currentBrick;
        currentBrick++;
        currentRow--;
        currentCol++;
        currentCol++;
        secondLayer[currentRow][currentCol] = currentBrick;
        secondLayer[currentRow + 1][currentCol] = currentBrick;

        currentCol++;
        currentBrick++;
    }

    //reads array from console
    private static void readArrayInput(int[][] firstLayer, Scanner scanner, int n, int m) {
        for (int i = 0; i < n; i++) {
            String[] arrInput = scanner.nextLine().split(" ");
            for (int j = 0; j < m; j++) {
                firstLayer[i][j] = Integer.parseInt(arrInput[j]);

            }
        }
    }

    //prints console on console
    public static void printWall(int[][] firstLayer) {
        for (int[] ints : firstLayer) {
            for (int anInt : ints) {
                System.out.print(anInt + " ");
            }
            System.out.println();
        }

    }

    //checks if input is valid - if we have 2 subsequent vertical bricks in the first layer, then it's *NOT* valid
    public static boolean checkIfLayerIsValid(int[][] firstLayer, int n, int m) {
        if (currentRow < n - 1 && currentCol < m - 1) {
            if (currentRow < n && firstLayer[currentRow][currentCol] == firstLayer[currentRow + 1][currentCol] && firstLayer[currentRow][currentCol + 1] == firstLayer[currentRow + 1][currentCol + 1]) {
                return false;
            }
        }
        return true;
    }
}
