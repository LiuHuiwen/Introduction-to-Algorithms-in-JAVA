/**
 * Created by LiuHuiwen on 2017/5/18.
 */
public class FindSumMaxSubarray {
    public static void main(String[] args) {
        int [] array1 = {1};//{5, -6, 9, -2, 6, 8, 0, -8, 9, 10};
        FindSumMaxSubarray S = new FindSumMaxSubarray();
        int[] ans = S.findMaxSubarray(array1, 0, array1.length - 1);
        System.out.printf("left point: %d, right point: %d, sum: %d", ans[0], ans[1], ans[2]);
        System.out.println();
        int[] ans1 = S.norecursive(array1);
        System.out.printf("left point: %d, right point: %d, sum: %d", ans1[0], ans1[1], ans1[2]);
    }
    public int[] findMaxSubarray(int[] array, int low, int high) {
        if(low == high) return new int[]{low, high, array[low]};
        int mid = (low + high)/2;
        int[] left = findMaxSubarray(array, low, mid);
        int[] right = findMaxSubarray(array, mid+1, high);
        int[] cross = findCrossMaxSubarray(array, low, mid, high);
        if(left[2]>=right[2]&&left[2]>=cross[2]) return left;
        if(right[2]>=left[2]&&right[2]>=cross[2]) return right;
        else return cross;
    }
    public int[] findCrossMaxSubarray(int[] array, int low, int mid, int high){
        int maxLeft = Integer.MIN_VALUE, left = mid, sum = 0;
        for(int i = mid;i >= low; i--){
            sum += array[i];
            if(sum >= maxLeft){
                maxLeft = sum;
                left = i;
            }
        }
        int maxRight = Integer.MIN_VALUE, right = mid + 1;
        sum = 0;
        for(int j = mid + 1; j <= high; j++){
            sum += array[j];
            if(sum >= maxRight){
                maxRight = sum;
                right = j;
            }
        }
        return new int[] {left, right, maxLeft + maxRight};
    }
    public int[] norecursive(int[] array){
        int left = 0, right = 0, sum = 0, maxSum = Integer.MIN_VALUE;
        for(int i = 0; i < array.length; i++){
            sum += array[i];
            if(sum >= maxSum){
                maxSum = sum;
                right = i;
            }
        }
        sum = 0;
        for(int j = right; j >= 0; j--){
            sum += array[j];
            if(sum >= maxSum){
                maxSum = sum;
                left = j;
            }
        }
        return new int[] {left, right, maxSum};
    }
}
