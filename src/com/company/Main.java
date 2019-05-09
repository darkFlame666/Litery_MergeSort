package com.company;

import java.io.*;
import java.util.ArrayList;

public class Main {
    /* This method sorts the input array and returns the
       number of inversions in the array */
    static long mergeSort(int arr[], int array_size)
    {
        int temp[] = new int[array_size];
        return _mergeSort(arr, temp, 0, array_size - 1);
    }

    /* An auxiliary recursive method that sorts the input array and
      returns the number of inversions in the array. */
    static long _mergeSort(int arr[], int temp[], int left, int right)
    {
        int mid=0;
        long inv_count = 0;
        if (right > left) {
            /* Divide the array into two parts and call _mergeSortAndCountInv()
           for each of the parts */
            mid = (right + left) / 2;

            /* Inversion count will be sum of inversions in left-part, right-part
          and number of inversions in merging */
            inv_count = _mergeSort(arr, temp, left, mid);
            inv_count += _mergeSort(arr, temp, mid + 1, right);

            /*Merge the two parts*/
            inv_count += merge(arr, temp, left, mid + 1, right);
        }
        return inv_count;
    }

    /* This method merges two sorted arrays and returns inversion count in
       the arrays.*/
    static long merge(int arr[], int temp[], int left, int mid, int right)
    {
        int i, j, k;
        long inv_count = 0;

        i = left; /* i is index for left subarray*/
        j = mid; /* j is index for right subarray*/
        k = left; /* k is index for resultant merged subarray*/
        while ((i <= mid - 1) && (j <= right)) {
            if (arr[i] <= arr[j]) {
                temp[k++] = arr[i++];
            }
            else {
                temp[k++] = arr[j++];

                /*this is tricky -- see above explanation/diagram for merge()*/
                inv_count = inv_count + (mid - i);
            }
        }

        /* Copy the remaining elements of left subarray
       (if there are any) to temp*/
        while (i <= mid - 1)
            temp[k++] = arr[i++];

        /* Copy the remaining elements of right subarray
       (if there are any) to temp*/
        while (j <= right)
            temp[k++] = arr[j++];

        /*Copy back the merged elements to original array*/
        for (i = left; i <= right; i++)
            arr[i] = temp[i];

        return inv_count;
    }

    public static void main(String[] args) {

        try {

            File file = new File("src/lit10b.in");
            BufferedReader br = new BufferedReader(new FileReader(file));
            int n = Integer.parseInt(br.readLine());
            String Jas = br.readLine();
            String Malgosia = br.readLine();

            char[] charJas = Jas.toCharArray();
            char[] charMalgosia = Malgosia.toCharArray();
            long result=0;
            int p[] = new int[n];
            ArrayList<Integer> tab_u[] = new ArrayList[255];
            ArrayList<Integer> tab_w[] = new ArrayList[255];


            for(int i=0; i<tab_u.length; i++)
                tab_u[i]=new ArrayList<>();
            for(int i=n-1; i>=0; --i) {
                tab_u[(int)charJas[i]].add(i);
            }


            for(int i=0; i<tab_w.length; i++)
                tab_w[i]=new ArrayList<>();
            for(int i=n-1; i>=0; --i) {
                tab_w[(int)charMalgosia[i]].add(i);
            }


            for(int ch='A'; ch <= 'Z'; ++ch){
                for(int i=0; i<tab_u[ch].size(); ++i){
                    p[tab_u[ch].get(i)] = tab_w[ch].get(i);
                }
            }

            result=mergeSort(p, p.length);

            FileWriter writer = new FileWriter("src/lit.out");
            writer.write(Long.toString(result));
            writer.close();

        } catch(IOException ex){
            ex.printStackTrace();
        }

    }
}

