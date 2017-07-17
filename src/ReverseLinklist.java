import com.sun.org.apache.regexp.internal.RE;

import java.util.ArrayList;

/**
 * Created by LiuHuiwen on 2017/5/23.
 */
public class ReverseLinklist {
    public static void main(String[] args){
        int[] test ={1,2,3,4,5};
        LinkNode list = ReverseLinklist.toLinklist(test);
        System.out.println(ReverseLinklist.toArrayList(list));
        LinkNode reverse = ReverseLinklist.reverseLinklist(list);
        ArrayList<Integer> ans = ReverseLinklist.toArrayList(reverse);
        System.out.println(ans);
    }
    public static LinkNode toLinklist(int[] array){
        int len = array.length;
        LinkNode header = new LinkNode(0);
        LinkNode point = header;
        for(int i = 0; i < len; i++){
            point.nextNode = new LinkNode(array[i]);
            point = point.nextNode;
        }
        return header.nextNode;
    }
    public static LinkNode reverseLinklist(LinkNode header){
        if(header == null) return null;
        LinkNode pre = null;
        LinkNode point = header;
        LinkNode after = header.nextNode;
        while(after!=null){
            point.nextNode = pre;
            pre = point;
            point = after;
            after = after.nextNode;
        }
        point.nextNode = pre;
        return point;
    }
    public static ArrayList<Integer> toArrayList(LinkNode header){
        ArrayList<Integer> ans = new ArrayList<>();
        while(header!=null){
            ans.add(header.value);
            header = header.nextNode;
        }
        return ans;
    }
}

class LinkNode{
    public int value;
    public LinkNode nextNode = null;
    public LinkNode(int num){
        this.value = num;
    }
}