/**
 * Created by LiuHuiwen on 2017/5/27.
 */
class Heap {
    public static void main(String arg[]){
        int[] a = {3,4,5,2,1,8,6,7};
        Heap.heapSort(a);
        for(int i=0;i<a.length;i++){
            System.out.println(a[i]);
        }
    }
    public static void maxHeapify(int[] array, int index, int heap_size){
        int l = 2*index + 1;
        int r = 2*index + 2;
        int largest = index;
        if(heap_size>array.length||l>heap_size - 1||index<0) return;
        if(array[index]<=array[l]) largest = l;
        if(r<heap_size&&array[largest]<=array[r]) largest = r;
        if(largest!=index){
            int tmp = array[largest];
            array[largest] = array[index];
            array[index] = tmp;
            maxHeapify(array,largest,heap_size);
        }
    }
    public static void buildMaxHeap(int[]array,int heap_size){
        if(heap_size>array.length) heap_size = array.length;
        for(int i = heap_size / 2;i >= 0 ;i--){
            maxHeapify(array,i,heap_size);
        }
    }
    public static void heapSort(int[] array){
        int heap_size = array.length;
        buildMaxHeap(array, heap_size);
        for(int i = heap_size - 1; i > 0; i--){
            int tmp = array[0];
            array[0] = array[i];
            array[i] = tmp;
            heap_size--;
            maxHeapify(array,0,heap_size);
        }
    }
}

