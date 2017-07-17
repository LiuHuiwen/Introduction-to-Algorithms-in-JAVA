/**
 * Created by LiuHuiwen on 2017/5/23.
 */
public class Sort {
    public static void main(String[] args){
        int[] example ={1,3,2,9,8,4,5,6};
//        Sort.InsertSort(example);
        Sort.mergeSort(example,0,(example.length - 1)/2,example.length - 1);
        int[] tmp = example;
        for(int i = 0; i < tmp.length; i++){
            System.out.println(tmp[i]);
        }
    }
    public static void InsertSort(int[] array){ //该操作直接对原数组进行了更改。
        if(array == null) return ;
        int len = array.length;
        for(int i =1;i < len; i++){
            int j = i - 1;
            int var = array[i];
            while(j >= 0&&array[j] > var){
                array[j + 1] = array[j--];
            }
            array[j + 1] = var;
        }
    }
    public static void mergeSort(int[] array, int p, int q,int r){
        if(p==r) return;
        mergeSort(array, p, (p+q)/2, q);
        mergeSort(array, q + 1, (q + r + 1)/2, r);
        merge(array, p, q, r);
    }
    public static void merge(int[] array, int p, int q, int r){
        int[] left = new int[q - p + 2];
        int[] right = new int[r - q + 1];
        for(int i = 0;i < q - p + 1; i++){
            left[i] = array[p + i];
        }
        for(int j = 0;j < r - q; j++){
            right[j] = array[q + 1 + j];
        }
        left[q - p + 1] = Integer.MAX_VALUE;
        right[r - q] = Integer.MAX_VALUE;
        int i = 0, j = 0;
        for(int k = p; k <= r; k++){
            if(left[i] > right[j]) array[k] = right[j++];
            else array[k] = left[i++];
        }
    }
}
