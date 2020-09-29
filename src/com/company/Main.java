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

    int[] list;

    list = generateUnsortedList(N,min,max);

    for (int i = 0; i < N; i++)
    {
        for( int j = 0; j < i; j++){
            if (list[i]== list[j]){
                System.out.println("\nERROR DUPLICATE FOUND\n");
            }
        }
        System.out.print(list[i]+" ");
    }

    int count = bruteForce(list);


    System.out.println();
    System.out.println("Sets of numbers that add up to zero: " + count);
    System.out.println();
    count = faster(list);
    System.out.println("2nd Run Sets of numbers that add up to zero: " + count);
    System.out.println();
    count = fastest(list);
    System.out.println("3rd Run Sets of numbers that add up to zero: " + count);
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

    public static int fastest(int[] list)
    {
        Arrays.sort(list);
        int N = list.length;
        int count = 0;
        for( int i = 0; i < N; i++) {
            int low = i + 1;
            int high = N - 1;

            for (int j =  low; low < high;  )
            {
                if(list[i] + list[low] + list[high] == 0)
                {
                    count++;
                    low++;
                    high--;
                }else if(list[i] + list[low] + list[high] < 0){
                    low++;
                }else{
                    high--;
                }
            }
        }
        return count;
    }
    public static int rank(int a, int[] list)
    {
        int low = 0;
        int high = list.length - 1;

        while (low <= high)
        {
            int mid = low + (high - low) / 2;
            if (a < list[mid]){
                high = mid - 1;
            }
            else if ( a > list[mid]){
                low = mid + 1;
            }
            else {
                return mid;
            }
        }
        return -1;
    }

    public static int faster(int[] list)
    {
        Arrays.sort(list);
        int N = list.length;
        int count = 0;

        for (int i = 0; i < N; i++) {
            for (int j = i + 1; j < N; j++){
                if(rank(-list[i]-list[j], list) > j){
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

        for ( int i = 0; i < N; i++){
            for ( int j = i + 1; j < N; j++){
                for ( int k = j + 1; k < N; k++){
                    if (list[i] + list[j] + list[k] == 0)
                    {
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
        {
            list[i] = rand.nextInt(max - min + 1 ) + min;
            for ( int j = 0; j < i; j++)
            {
                if (list[i] == list[j])
                {
                    i--;
                }
            }
        }
        return list;
    }
}
