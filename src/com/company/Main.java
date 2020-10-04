package com.company;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.Arrays;
import java.util.Random;

public class Main {

    public static void main(String[] args) {

    int N = 100;
    int min = -(2*N);
    int max = (2*N);
    int maxTime = 1000000000;
    int maxTrials = 1000;
    int N_min = 4;
    int N_max = Integer.MAX_VALUE;
    //runTimeTestsBrute(maxTime, maxTrials, N_min, N_max);

    int[] list = generateUnsortedList(N,min,max);
    System.out.println(Arrays.toString(list));
    int count = bruteForce(list);
    System.out.println("\nbrute is "+count+"\n");
    count = faster(list);
    System.out.println("\nfaster is "+count+"\n");
    count = fastest(list);
    System.out.println("\nfastest is "+count+"\n");

    }
    /** Get CPU time in nanoseconds since the program(thread) started. */
    /**
     * from: http://nadeausoftware.com/articles/2008/03/java_tip_how_get_cpu_and_user_time_benchmarking#TimingasinglethreadedtaskusingCPUsystemandusertime
     **/
    public static long getCpuTime() {
        ThreadMXBean bean = ManagementFactory.getThreadMXBean();
        return bean.isCurrentThreadCpuTimeSupported() ?
                bean.getCurrentThreadCpuTime() : 0L;
    }

    public static void runTimeTestsBrute(int maxTime, int maxTrials, int N_min, int N_max)
    {
            int N = 4;

            int min = -(2*N);
            int max = 2*N;
            long timeStampBefore;
            long timeStampAfter;
            long totalTime = 0;
            long timeMeasured = 0;
            int trialCount = 0;
            long bruteAverageTimeMeasured = 0;
            long fasterAverageTimeMeasured = 0;
            long fastestAverageTimeMeasured = 0;
            double bruteDoubleRatio = 0;
            double fasterDoubleRatio = 0;
            double fastestDoubleRatio = 0;
            double bruteExpectedDoubleRatio = 0;
            double fasterExpectedDoubleRatio = 0;
            double fastestExpectedDoubleRatio = 0;
            long brutePreviousTimeMeasured = 1;
            long fasterPreviousTimeMeasured = 1;
            long fastestPreviousTimeMeasured = 1;

            /**Print Column Headings**/
            System.out.printf("\n%13s %20s %20s %22s %20s %20s %22s %20s %20s %22s\n", "N","Brute Force Time","Doubling Ratio","Exp. Doubling Ratio", "Faster 3sum Time", "Doubling Ratio", "Exp. Doubling Ratio","Fastest 3sum Time","Doubling Ratio","Exp. Doubling Ratio");
            System.out.printf("%210s\n", "--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");

            for( N = N_min; N <= N_max; N = N * 2)
            {
                int[] list = generateUnsortedList(N,-(N/2), (N/2));
                totalTime = 0;
                trialCount = 0;
                // Time trial for brute force method
                while ( totalTime < maxTime && trialCount < maxTrials )
                {
                    timeStampBefore = getCpuTime();
                    bruteForce(list);
                    timeStampAfter = getCpuTime();
                    timeMeasured = timeStampAfter - timeStampBefore;
                    totalTime = totalTime + timeMeasured;
                    trialCount++;
                }
                bruteAverageTimeMeasured = totalTime / trialCount;
                bruteDoubleRatio = (double) bruteAverageTimeMeasured / brutePreviousTimeMeasured;
                brutePreviousTimeMeasured = bruteAverageTimeMeasured;
                if ( ((N / 2) * (N / 2) * (N / 2)) != 0) {
                    bruteExpectedDoubleRatio = (N * N * N) / ((N / 2) * (N / 2) * (N / 2));
                } else{
                    bruteExpectedDoubleRatio = 8.0;
                }


                totalTime = 0;
                trialCount = 0;
                // Time trial for faster method
                while ( totalTime < maxTime && trialCount < maxTrials )
                {
                    timeStampBefore = getCpuTime();
                    faster(list);
                    timeStampAfter = getCpuTime();
                    timeMeasured = timeStampAfter - timeStampBefore;
                    totalTime = totalTime + timeMeasured;
                    trialCount++;
                }
                fasterAverageTimeMeasured = totalTime / trialCount;
                fasterDoubleRatio = (double) fasterAverageTimeMeasured / fasterPreviousTimeMeasured;
                fasterPreviousTimeMeasured = fasterAverageTimeMeasured;
                fasterExpectedDoubleRatio = ((N*N)*log2(N))/(((N / 2) * (N / 2))*log2(N-1));


                totalTime = 0;
                trialCount = 0;
                // Time trial for fastest method
                while ( totalTime < maxTime && trialCount < maxTrials )
                {
                    timeStampBefore = getCpuTime();
                    faster(list);
                    timeStampAfter = getCpuTime();
                    timeMeasured = timeStampAfter - timeStampBefore;
                    totalTime = totalTime + timeMeasured;
                    trialCount++;
                }
                fastestAverageTimeMeasured = totalTime / trialCount;
                fastestDoubleRatio = (double) fastestAverageTimeMeasured / fastestPreviousTimeMeasured;
                fastestPreviousTimeMeasured = fastestAverageTimeMeasured;
                fastestExpectedDoubleRatio = (N*N)/ ((N/2) * (N/2));



                if ( N == N_min ) {
                    String notApplicable = "na";
                    System.out.printf("%13s %20s %20s %22s %20s %20s %22s %20s %20s %22s\n", N, bruteAverageTimeMeasured, notApplicable, notApplicable, fasterAverageTimeMeasured, notApplicable, notApplicable, fastestAverageTimeMeasured, notApplicable, notApplicable);
                }
                else{
                    System.out.printf("%13s %20s %20.2f %22s %20s %20.2f %22s %20s %20.2f %22s\n", N, bruteAverageTimeMeasured, bruteDoubleRatio, bruteExpectedDoubleRatio, fasterAverageTimeMeasured, fasterDoubleRatio, fasterExpectedDoubleRatio, fastestAverageTimeMeasured, fastestDoubleRatio, fastestExpectedDoubleRatio);
                }
            }
    }

    public static int log2(int x){
        return (int) (Math.log(x) / Math.log(2));
    }

    public static int fastest(int[] list)
    {   //pre-sort the list
        Arrays.sort(list);
        int N = list.length;
        int count = 0;
        for( int i = 0; i < N - 2; i++) {
            int lowerBound = i + 1;
            int upperBound = N - 1;
            // while lowerbound is less than upperbound, the lowerbound will increment and the upperbound will decrement
            // this shrinks the items we are searching for the list as we are searching for 3 sums
            for ( ; lowerBound < upperBound;  )
            {
                if(list[i] + list[lowerBound] + list[upperBound] == 0)
                {   // count the 3 sums
                    count++;
                    lowerBound++;
                    upperBound--;
                    // sum of 3 elements is less than 0 increment the lowerbound
                }else if(list[i] + list[lowerBound] + list[upperBound] < 0){
                    lowerBound++;
                }else{
                    // sum of 3 elements is more than 0 decrement the lowerbound
                    upperBound--;
                }
            }
        }
        return count;
    }
    public static int binarySearch(int a, int[] list)
    {
        int lowerBound = 0;
        int upperBound = list.length - 1;

        while (lowerBound <= upperBound)
        {
            int middle = lowerBound + (upperBound - lowerBound) / 2;
            // If key value is lower than middle index, then we focus on the lower half of the list
            if (a < list[middle]){
                upperBound = middle - 1;
            }
            // If key value is greater than middle index, then we focus on the upper half of the list
            else if ( a > list[middle]){
                lowerBound = middle + 1;
            }
            // The key value is present at middle index, return middle index
            else {
                return middle;
            }
        }
        // The key value was not present in the list
        return -1;
    }

    public static int faster(int[] list)
    {
        // pre-sort the list
        Arrays.sort(list);
        int N = list.length;
        int count = 0;

        for (int i = 0; i < N; i++) {
            for (int j = i + 1; j < N; j++){
                // use binary search to determine if there is a 3 sum
                if(binarySearch(-list[i]-list[j], list) > j){
                   // count the 3 sums
                    count++;
                }
            }
        }
        return count;
    }
    public static int bruteForce(int[] list)
    {
        int N = list.length;
        int count = 0;
        // iterate through 3 indices looking for 3 sums; explicitly search through every index
        for ( int i = 0; i < N; i++){
            for ( int j = i + 1; j < N; j++){
                for ( int k = j + 1; k < N; k++){
                    if (list[i] + list[j] + list[k] == 0)
                    {   //count the 3 sums
                        count++;
                    }
                }
            }
        }
        return count;
    }

    public static int[] generateUnsortedList( int N, int min, int max)
    {
        int[] list = new int[N];
        Random rand = new Random();

        for (int i = 0; i < N; i++)
        {   // generate random list between max and min
            list[i] = rand.nextInt(max - min + 1 ) + min;
            for ( int j = 0; j < i; j++)
            {   //remove duplicates
                if (list[i] == list[j])
                {
                    i--;
                }
            }
        }
        return list;
    }
}
