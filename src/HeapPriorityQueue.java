import java.util.*;
/**
 * Created by LiuHuiwen on 2017/7/5.
 */
public class HeapPriorityQueue {
    public List<Key> queue =  new ArrayList<>();
    private boolean nondecrese;
    public boolean offer(Key element){
        if(queue.contains(element)){
            System.out.println("Object has in this queue.");
            return false;
        }
        queue.add(element);
        this.recursiveHeapify(queue.size());
        return true;
    }
    public void recursiveHeapify(int size){
        if(size < 2) return;
        if(queue.get(size - 1).key < queue.get((size - 2)/2).key){
            Key tmp = queue.get(size - 1);
            queue.set(size - 1, queue.get((size -2)/2));
            queue.set((size - 2)/2, tmp);
            this.recursiveHeapify(size/2);
        }
    }
    public Key poll(){
        if(queue.isEmpty()){
            System.out.println("Error: queue is empty.");
            return null;
        }
        Key ans = queue.get(0);
        Key tmp = queue.remove(queue.size() - 1);
        if(!queue.isEmpty()){
            queue.set(0, tmp);
            minHeapify(0);
        }
        return ans;
    }
    public void minHeapify(int startPoint){
        if(queue.size() < 2 || startPoint > queue.size()/2 - 1) return;
        int point = 2*startPoint + 1;
        if(startPoint*2 + 2 < queue.size() && queue.get(startPoint*2 + 1).key > queue.get(startPoint*2 + 2).key){
            point = 2*startPoint + 2;
        }
        Key tmp = queue.get(startPoint);
        if(tmp.key > queue.get(point).key){
            queue.set(startPoint, queue.get(point));
            queue.set(point, tmp);
            this.minHeapify(point);
        }
    }
    public boolean remove(Key element){
        if(!queue.contains(element)){
            System.out.println("the element is not exist in this queue.");
            return false;
        }
        int point = queue.indexOf(element);
        Key tmp = queue.remove(queue.size() - 1);
        queue.set(point, tmp);
        this.minHeapify(point);
        return true;
    }
    public int size(){
        return queue.size();
    }
    public boolean isEmpty(){
        return queue.isEmpty();
    }
    HeapPriorityQueue(boolean nondecrese){
        this.nondecrese = nondecrese;
    }
}
abstract class Key{
    public int key;
    Key(int key){
        this.key = key;
    }
}
class Link extends Key{
    public String inf1;
    public String intf2;
    Link(int key){
         super(key);
    }
}
class test{
    public static void main(String args[]){
        HeapPriorityQueue lst = new HeapPriorityQueue(true);
        int[] proir = {9,3,6,4,2,8,1};
        for(int i = 0; i < proir.length; i++){
            lst.offer(new Link(proir[i]));
        }
        lst.remove(lst.queue.get(2));
        while(!lst.isEmpty()){
            Key show = lst.poll();
            System.out.println(show.key);
        }
    }
}