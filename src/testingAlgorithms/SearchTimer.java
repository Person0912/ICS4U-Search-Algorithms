package testingAlgorithms;

import java.util.Arrays;
import java.util.Random;

/**
 * A class that demonstrates the performance of linear and binary search algorithms.
 * It generates random arrays, performs searches, and measures their execution time and iterations.
 */
public class SearchTimer {

    /**
     * Performs a linear search for a target value in an array.
     *
     * @param x       the target value to search for.
     * @param numArray the array to search within.
     * @return an array where the first element is the index of the target value (or -1 if not found)
     * and the second element is the number of iterations.
     */
    public static int[] searchLinear(int x, int[] numArray) {
        int n = 0; // Number of iterations
        for (int i = 0; i < numArray.length; i++) {
            n++;
            if (numArray[i] == x) {
                return new int[]{i, n};
            }
        }
        return new int[]{-1, n}; // -1 if not found
    }

    /**
     * Performs a binary search for a target value in a sorted array.
     *
     * @param x       the target value to search for.
     * @param numArray the sorted array to search within.
     * @return an array where the first element is the index of the target value (or -1 if not found)
     * and the second element is the number of iterations.
     */
    public static int[] searchBinary(int x, int[] numArray) {
        int low = 0, high = numArray.length - 1, n = 0;

        while (low <= high) {
            n++;
            int mid = (low + high) / 2;
            int midItem = numArray[mid];

            if (x == midItem) {
                return new int[]{mid, n};
            } else if (x < midItem) {
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        return new int[]{-1, n}; // -1 if not found
    }

    /**
     * Simulates a search using either linear or binary search.
     *
     * @param searchType the type of search to perform ("linear" or "binary").
     * @param numRange   the range of numbers in the array.
     * @param simArray    the array to search within.
     * @return the number of iterations taken during the search.
     */
    public static int simSearch(String searchType, int numRange, int[] simArray) {
        Random rand = new Random();
        int x = rand.nextInt(numRange) + 1; // Random target value
        int passCount;

        if (searchType.equalsIgnoreCase("linear")) {
            passCount = searchLinear(x, simArray)[1];
        } else {
            passCount = searchBinary(x, simArray)[1];
        }
        return passCount;
    }

    /**
     * Generates a random array of unique integers within a specified range.
     *
     * @param size     the number of elements in the array.
     * @param numRange the range of values (exclusive).
     * @return an array of unique random integers.
     */
    public static int[] generateNumArray(int size, int numRange) {
        Random rand = new Random();
        int[] numArray = new int[size];
        boolean[] used = new boolean[numRange + 1];

        for (int i = 0; i < size; i++) {
            int num;
            do {
                num = rand.nextInt(numRange) + 1;
            } while (used[num]);
            numArray[i] = num;
            used[num] = true;
        }
        return numArray;
    }

    /**
     * Main method to execute and compare the performance of linear and binary search algorithms.
     *
     * @param args command-line arguments (not used).
     */
    public static void main(String[] args) {
        // Define the array size as a constant
        //
        // MODIFY THIS CONSTANT BELOW FOR YOUR TEST RUNS!
        //
        final int ARRAY_SIZE = 1000;

        // Generate random arrays
        int[] searchArray = generateNumArray(ARRAY_SIZE, ARRAY_SIZE);
        int[] sortedSearchArray = Arrays.copyOf(searchArray, searchArray.length);
        Arrays.sort(sortedSearchArray);

        long linearPassCount = 0, binaryPassCount = 0;

        // Measure linear search time
        long linearStart = System.nanoTime();
        for (int i = 0; i < ARRAY_SIZE; i++) {
            linearPassCount += simSearch("linear", ARRAY_SIZE, searchArray);
        }
        long linearEnd = System.nanoTime();

        // Measure binary search time
        long binaryStart = System.nanoTime();
        for (int i = 0; i < ARRAY_SIZE; i++) {
            binaryPassCount += simSearch("binary", ARRAY_SIZE, sortedSearchArray);
        }
        long binaryEnd = System.nanoTime();

        // Calculate averages
        double linearAvgPass = (double) linearPassCount / ARRAY_SIZE;
        double binaryAvgPass = (double) binaryPassCount / ARRAY_SIZE;
        double linearAvgTime = (linearEnd - linearStart) / (double) ARRAY_SIZE / 1_000_000; // In milliseconds
        double binaryAvgTime = (binaryEnd - binaryStart) / (double) ARRAY_SIZE / 1_000_000; // In milliseconds

        // Print results
        System.out.printf("%-12s %-12s %-12s %-20s %-20s%n", "Search Type", "# Searches", "Array Size", "Avg. Iterations", "Average Time (ms)");
        System.out.println("-------------------------------------------------------------------------------");
        System.out.printf("%-12s %-12d %-12d %-20.2f %-20.6f%n", "Linear", ARRAY_SIZE, ARRAY_SIZE, linearAvgPass, linearAvgTime);
        System.out.printf("%-12s %-12d %-12d %-20.2f %-20.6f%n", "Binary", ARRAY_SIZE, ARRAY_SIZE, binaryAvgPass, binaryAvgTime);
    }
}