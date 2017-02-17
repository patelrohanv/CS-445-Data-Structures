import java.util.*;
import java.io.*;

public class FriendsCoupon {
    final static long startTime = System.currentTimeMillis();
    static ArrayList<ArrayList<Integer>> couponList = new ArrayList<ArrayList<Integer>>();
    public static void main(String[] args) {
        int numCoupons = -1;
        int[][] grid = null;
        int[] partial = null;
        List<Integer> converter = new ArrayList<Integer>();
        
        if (args.length >= 1 && args[0].equals("t")) {
            //Test
            testReject();
            testIsFullSolution();
            testExtend();
            testNext();
        }
        else {
            try{
                Scanner inFile = new Scanner(new File(args[0]));
                numCoupons= Integer.parseInt(args[1]);
                while(inFile.hasNextInt()){
                    converter.add(inFile.nextInt());
                }
                int size = (int)Math.sqrt(converter.size()); //number of users
                grid = new int[size][size];
                int spot = 0;
                for(int i = 0; i < size; i++){
                    for (int j = 0; j < size; j++){
                        grid[i][j] = converter.get(spot++);
                    }
                }
                partial = new int[size];
                inFile.close();
            }
            catch(FileNotFoundException e){
                System.out.println("File not found");
            }
        }
        if(args[0].equals("t")){

        }
        else if (numCoupons > 0 && checkIfValid(grid)){
            solve(grid, numCoupons, partial);
        }
        else{
            System.out.println("Enter a number of coupons in the command line or a valid file");
        }
    }

    public static void solve(int[][] grid, int numCoupons, int[] partial) {
        //System.out.println("reached solve");
        if (reject(grid, numCoupons, partial)){
            return;
        }
        if (isFullSolution(grid, numCoupons, partial)){
            //System.out.println(Arrays.toString(partial));
            printSol(partial);
        }
        int[] attempt = extend(grid, numCoupons, partial);
        while (attempt != null) {
            solve(grid, numCoupons, attempt);
            attempt = next(grid, numCoupons, attempt);
        }
     }
  
    //Checks if the grid is valid
    public static boolean checkIfValid(int[][] test){
        boolean valid = true;
        for (int i = 0; i < test.length; i++){
            for(int j = 0; j < test[i].length; j++){
                if (test[i][j] != test [j][i]){
                    valid = false;
                    if (valid = false){
                        return false;
                    }
                }
            }
        }
        return valid;
    }
    public static void printSol(int[] sol){
        for(int i = 0; i < sol.length; i++){
            char c = (char)(sol[i]+64);
            int j = i + 1;
            System.out.println("User " + j + ": Coupon " + c);
        }
        final long endTime = System.currentTimeMillis();
        //System.out.println("Total execution time: " + (endTime - startTime) );
        System.exit(0);
    }
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * Checks if a partial solution is a complete and valid solution.
     * @param partial The partial solution
     * @return true if the partial solution is complete and valid, false otherwise.
     */
    public static boolean isFullSolution(int[][] grid, int numCoupons, int[] partial){
        for (int i = 0; i < partial.length; i++) {
            if (partial[i] == 0) {
                return false;
            }
        }
        // The solution is known to be complete, check if it is valid
        if (reject(grid, numCoupons, partial)) {
            return false;
        }
        // The solution is complete and valid
        return true;
    }

    /**
     * Checks if a partial solution should be rejected because it can never be extended to a
     * complete solution.
     * @param partial The partial solution
     * @return true if the partial solution should be rejected, false otherwise.
     */
    public static boolean reject(int[][] grid, int numCoupons, int[] partial){
       // System.out.print("hit reject");
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid.length; j++) {
                if (partial[i] == partial[j] && grid[i][j] == 1 && partial[i] != 0 && partial[j] != 0){
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Extends a partial solution by adding one additional.
     * @param partial The partial solution
     * @return a partial solution with one more queen added, or null if no queen can be added.
     */
    public static int[] extend(int[][] grid, int numCoupons, int[] partial){
        // Initialize the new partial solution
        int[] temp = new int[partial.length];
        //start loop through grid
        // for (int i = 0; i < grid.length ; i++) {
        //  for (int j = 0; j < grid.length; j++)
        //         if (grid[i][j] != 0) {
        //         partial[i+1] =
        //          //null if you have a higher counter than number of coupons
        //         } 
        //     else {
        //         temp[i] = 1;
        //         return temp;
        //     }
        // }

        for (int i = 0; i < temp.length; i++) {
            if (partial[i] != 0) {
                // Copy each queen that has been placed
                temp[i] = partial[i];
            } 
            else {
                // Add a queen in row 1 of the first empty column
                temp[i] = 1;
                return temp;
            }
        }
        // reach the point you can't extend anymore
        return null;
    }

    /**
     * Moves the most recently-placed coupon to its next possible position.
     * @param partial The partial solution
     * @return a partial solution with the most recently-placed queen moved to its next possible
     * position, or null if we are out of options for the most recent queen.
     */
    public static int[] next(int[][] grid, int numCoupons, int[] partial){
        int[] temp = new int[partial.length];
        //int i = 0;
        for (int i = 0; i < grid.length; i++) {
            if (i == grid.length-1 || partial[i+1] == 0) {
                if (partial[i] >= numCoupons) {//hit the max coupon number for that index
                    return null;
                } 
                else{
                    temp[i] = partial[i] + 1;
                    break;
                }
            }
            else {
                temp[i] = partial[i];
            }
            //i++;
        }
        return temp;
    }
/////////////////////////////////////////////////////////////////////////////////////////
    

    static void testIsFullSolutionUnit(int[][] grid, int numCoupons,int[] test) {
        if (isFullSolution(grid, numCoupons,test)) {
            System.err.println("Full sol'n:\t" + Arrays.toString(test));
        } else {
            System.err.println("Not full sol'n:\t" + Arrays.toString(test));
        }
    }
    
    public static void testIsFullSolution() {
        System.err.println("Testing isFullSolution()");
    
        int[][] testGrid = {{0,1,1,0},{1,0,0,0},{1,0,0,1},{0,0,1,0}};
    //    0 1 1 0
    //    1 0 0 0
    //    1 0 0 1
    //    0 0 1 0
        //ABBA or BAAB
        int testCoupon = 2;
        // Full solutions:
        testIsFullSolutionUnit(testGrid, testCoupon, new int[] {1,2,2,1});
        testIsFullSolutionUnit(testGrid, testCoupon, new int[] {2,1,1,2});
    
        // Not full solutions:
        testIsFullSolutionUnit(testGrid, testCoupon, new int[] {0,0,0,0});
        testIsFullSolutionUnit(testGrid, testCoupon, new int[] {0,1,2,0});
        testIsFullSolutionUnit(testGrid, testCoupon, new int[] {1,1,0,1});
        testIsFullSolutionUnit(testGrid, testCoupon, new int[] {1,1,1,1});
    }
    
    static void testRejectUnit(int[][] grid, int numCoupons,int[] test) {
        if (reject(grid, numCoupons,test)) {
            System.err.println("Rejected:\t" + Arrays.toString(test));
        } else {
            System.err.println("Not rejected:\t" + Arrays.toString(test));
        }
    }

    public static void testReject() {
        System.err.println("Testing reject()");
        int[][] testGrid = {{0,1,1,0},{1,0,0,0},{1,0,0,1},{0,0,1,0}};
    //  0 1 1 0
    //  1 0 0 0
    //  1 0 0 1
    //  0 0 1 0
      int testCoupon = 2;
    
        // Should not be rejected:
        testRejectUnit(testGrid, testCoupon, new int[] {1,2,2,1});
        testRejectUnit(testGrid, testCoupon, new int[] {2,1,1,2});
    
        // Should be rejected:
        testRejectUnit(testGrid, testCoupon, new int[] {1,1,1,1});
        testRejectUnit(testGrid, testCoupon, new int[] {1,1,2,2});
        testRejectUnit(testGrid, testCoupon, new int[] {2,2,1,1});
    }
    

    static void testExtendUnit(int[][] grid, int numCoupons,int[] test) {
        System.err.println("Extended " + Arrays.toString(test) + " to " + Arrays.toString(extend(grid, numCoupons,test)));
    }
    

    public static void testExtend() {
        System.err.println("Testing extend()");
        int[][] testGrid = {{0,1,1,0},{1,0,0,0},{1,0,0,1},{0,0,1,0}};
    //  0 1 1 0
    //  1 0 0 0
    //  1 0 0 1
    //  0 0 1 0
      int testCoupon = 2;
        // Cannot be extended:
        testExtendUnit(testGrid, testCoupon, new int[] {1,1,1,1});
        testExtendUnit(testGrid, testCoupon, new int[] {2,1,1,2});
        testExtendUnit(testGrid, testCoupon, new int[] {1,2,1,2});
    
        // Can be extended:
        testExtendUnit(testGrid, testCoupon, new int[] {0,0,0,0});
        testExtendUnit(testGrid, testCoupon, new int[] {1,2,0,0});
        testExtendUnit(testGrid, testCoupon, new int[] {2,1,1,0});
    }
    

    static void testNextUnit(int[][] grid, int numCoupons,int[] test) {
        System.err.println("Nexted " + Arrays.toString(test) + " to " + Arrays.toString(next(grid, numCoupons,test)));
    }

    public static void testNext() {
        int[][] testGrid = {{0,1,1,0},{1,0,0,0},{1,0,0,1},{0,0,1,0}};
    //  0 1 1 0
    //  1 0 0 0
    //  1 0 0 1
    //  0 0 1 0
      int testCoupon = 2;
        
        System.err.println("Testing next()");
    
        // Cannot be next'd:
        testNextUnit(testGrid, testCoupon, new int[] {2,1,1,2});
    
        // Can be next'd:
        testNextUnit(testGrid, testCoupon, new int[] {1,0,0,0});
    }
}