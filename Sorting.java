
 /*THIS CODE IS MY OWN WORK, IT WAS WRITTEN WITHOUT CONSULTING
   CODE WRITTEN BY OTHER STUDENTS. Milap Naik */

//This program implements various sorting algorithms, and then runs a performance test 
//to show what scenarios optimizes each one.

import java.text.DecimalFormat;
import java.util.Arrays;

public class Sorting {
 
	public static void BubbleSort(long[] list) { //Compare every adjacent pair and switch if necessary
		int length = list.length;
		int i, j;//counting variables
		long swap;
		for(i = 0; i < length; i++){
			for(j = 1; j < (length-i); j++){
				if(list[j-1] > list[j]){
					swap = list[j-1];
					list[j-1]=list[j];
					list[j]=swap;
				}
			}
		}
	}//BubbleSort()
 
	public static void MergeSortNonRec(long[] list) {
	
		long[] aux = new long[list.length];
        sort(list, aux, 0, list.length-1);
        
	}//MergeSortNonRec()
 
	//-------------------------------------------------------------
	//---------- Below is an implementation of Selection Sort -----
	//-------------------------------------------------------------  
	public static void SelectionSort(long[] list) {
		int N = list.length;
		for (int i = 0; i < N; i++) {
			int min = i;
			for (int j = i+1; j < N; j++) {
				if (list[j] < list[min]) min = j;
			}
			exch(list, i, min);
		}
	}//SelectionSort()
 
 
	//-----------------------------------------------------------------------
	//---------- Below is an implementation of Insertion Sort ----------
	//-----------------------------------------------------------------------
	public static void InsertionSort(long[] list) {
		int N = list.length;
        for (int i = 0; i < N; i++) {
            for (int j = i; j > 0 && list[j] < list[j-1]; j--) {
                exch(list, j, j-1);
            }
        }
	}//InsertionSort()

	//-----------------------------------------------------------------------
	//---------- Below is an implementation of recursive MergeSort ----------
	//-----------------------------------------------------------------------
 
    private static void merge(long[] list, long[] aux, int lo, int mid, int hi) {

        // copy to aux[]
        for (int k = lo; k <= hi; k++)
            aux[k] = list[k]; 


        // merge back to a[]
        int i = lo, j = mid+1;
        for (int k = lo; k <= hi; k++) {
            if      (i > mid)           list[k] = aux[j++];
            else if (j > hi)            list[k] = aux[i++];
            else if (aux[j] < aux[i])   list[k] = aux[j++];
            else                        list[k] = aux[i++];
        }
    }

    
    // mergesort list[lo..hi] using auxiliary array aux[lo..hi]
    private static void sort(long[] list, long[] aux, int lo, int hi) {
        if (hi <= lo) return;
        int mid = lo + (hi - lo) / 2;
        sort(list, aux, lo, mid);
        sort(list, aux, mid + 1, hi);
        merge(list, aux, lo, mid, hi);
    }

    public static void MergeSort(long[] list) {
        long[] aux = new long[list.length];
        sort(list, aux, 0, list.length-1);
    }
    
    //------------------------------------------------------
    //---------- below are several helper methods ----------
    //------------------------------------------------------
 
    // This tests whether your sorted result is correct by comparing it to reference result
    public static boolean testSort(long[] list) {
    	long[] compare = new long[list.length];
    	System.arraycopy(list, 0, compare, 0, list.length);
    	Arrays.sort(list);
    	for(int i = 0; i < list.length; i++)
    		if(compare[i] != list[i]) 
    			return false;
    	return true;
    }//testSort()
 
 
    // This creates an array with n randomly generated elements between (0, n*10]
    private static long[] randArray(int number) {
    	long[] rand = new long[number];
    	for(int i=0; i<number; i++)
    		rand[i] = (int) (Math.random() * number * 10);
    	return rand;
    }//randArray()
 
    private static void startTimer() { 
    	timestamp = System.nanoTime();
    }//startTimer()
 
    private static double endTimer() {
    	return (System.nanoTime() - timestamp)/1000000.0;
    }//endTimer()
         
    // exchange list[i] and list[j]
    private static void exch(long[] list, int i, int j) {
        long swap = list[i];
        list[i] = list[j];
        list[j] = swap;
    }
 
    private static long timestamp;
 
    //---------------------------------------------
    //---------- This is the main method ----------
    //---------------------------------------------  
    public static void main(String[] args) {
 
    	// run experiments
    	final int BUBBLE = 0, SELECT = 1, INSERT = 2, MERGEREC = 3, MERGENONREC = 4;
    	int[] algorithms = {BUBBLE, SELECT, INSERT, MERGEREC, MERGENONREC};
  
    	// max defines the maximum size of the array to be tested, which is 2^max
    	// runs defines the number of rounds to be performed per test, in order to get an average running time.
    	int max = 14, runs = 5;
    	double[][] stats = new double[algorithms.length][max];
    	for(int i=0; i<algorithms.length; i++) {             //loop through each sorting algorithm
    		switch(i) {
    			case BUBBLE: System.out.print("Running Bubble Sort ..."); break;
    			case SELECT: System.out.print("Running Selection Sort ..."); break;
    			case INSERT: System.out.print("Running Insertion Sort ..."); break;
    			case MERGEREC: System.out.print("Running MergeSort Recursive ..."); break;
    			case MERGENONREC: System.out.print("Running MergeSort Non Recursive ..."); break;
    		}//switch
    		for(int j=0; j<max; j++) {        //loop through each array size 
    			double avg = 0;
    			for(int k=0; k<runs; k++) {    // loop through each run
    				long[] list = randArray((int) Math.pow(2, j+1));
    				startTimer();
    				switch(i) {
    					case BUBBLE: BubbleSort(list); break;
    					case SELECT: SelectionSort(list); break;
    					case INSERT: InsertionSort(list); break;
    					case MERGEREC: MergeSort(list); break;
    					case MERGENONREC: MergeSortNonRec(list); break;
    				}//switch
    				avg += endTimer();
    				if (testSort(list) == false)
    					System.out.println("The sorting is INCORRECT!" + "(N=" + list.length + ", round=" + k + ").");
    			}//for
    			avg /= runs;
    			stats[i][j] = avg;
    		}//for
    		System.out.println("done.");
    	}//for
  
    	DecimalFormat format = new DecimalFormat("0.0000");
    	System.out.println();
    	System.out.println("Average running time:");
    	System.out.println("N\t Bubble Sort\t Selection Sort\t Insertion Sort\tMergeSortRec\tMergeSortNon");
    	for(int i=0; i<stats[0].length; i++) {
    		System.out.print((int) Math.pow(2, i+1) + "\t  ");
    		for(int j=0; j<stats.length; j++) {
    			System.out.print(format.format(stats[j][i]) + "\t  ");
    		}//for
    		System.out.println();
    	}//for
    }//main()
 
}//end of class