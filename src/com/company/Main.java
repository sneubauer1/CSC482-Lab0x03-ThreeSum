package com.company;

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
