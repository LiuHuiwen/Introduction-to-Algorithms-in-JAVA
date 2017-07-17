import java.util.Random;

/**
 * Created by LiuHuiwen on 2017/6/2.
 */
public class QuickSort {
    public static void main(String arg[]){
        int[] example ={1,3,2,9,8,4,5,6};
        QuickSort.randomQuickSort(example,0,example.length);
        int[] tmp = example;
        for(int i = 0; i < tmp.length; i++){
            System.out.println(tmp[i]);
        }
    }
    public static int randomPartition(int[] array, int start, int end){
        {
            Random rand = new Random();
            int randomNum = rand.nextInt(end - start) + start;
            int tmp = array[randomNum];
            array[randomNum] = array[end - 1];
            array[end - 1] = tmp;
        }
        int i = start;
        for(int j = start;j < end;j++){
            if(array[j]<array[end - 1]){
                int tmp = array[j];
                array[j] = array[i];
                array[i++] = tmp;
            }
        }
        int tmp = array[end -1];
        array[end -1] = array[i];
        array[i] = tmp;
        return i;
    }
    public static void randomQuickSort(int[] array, int start, int end){
        if(end<=start + 1) return;
        int i = randomPartition(array, start, end);
        randomQuickSort(array,start,i);
        randomQuickSort(array,i,end);
    }
}
