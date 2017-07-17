/**
 * Created by LiuHuiwen on 2017/6/2.
 */
public class CountSort {
    public static void main(String arg[]) {
        int[] example = {1, 3, 2, 9, 8, 4, 5, 6};
        int[] ans = CountSort.countSort(example,10);
        int[] tmp = ans;
        for (int i = 0; i < tmp.length; i++) {
            System.out.println(tmp[i]);
        }
    }
    public static int[] countSort(int[] array, int k){
        if(k < 1){
            System.out.print("return null");
            return null;
        }
        int[] tmp = new int[k];
        for(int i = 0; i < k; i++) tmp[i] = 0;
        for(int j = 0; j < array.length;j++) tmp[array[j]]++;
        for(int m = 0; m < k - 1; m++) tmp[m + 1] = tmp[m] + tmp[m + 1];
        int[] ans = new int[array.length];
        for(int n = 0; n < array.length; n++){
            ans[tmp[array[n]] - 1] = array[n];
            tmp[array[n]]--;
        }
        return ans;
    }
}
