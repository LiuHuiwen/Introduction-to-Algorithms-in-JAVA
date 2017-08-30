import org.omg.CosNaming.NamingContextExtPackage.AddressHelper;

import java.io.NotActiveException;
import java.util.*;
import java.lang.*;

/**
 * Created by LiuHuiwen on 2017/7/4.
 */
class JustForTest{
    public static void main(String args[]){
//        Graph test = new UndirectedGraph();
        OrientedGraph test = new OrientedGraph();
//        String[] nodes = {"a", "b", "c", "d", "e", "f", "g", "h", "i"};
//        String[] edgenodes1 = {"a", "b", "c", "d", "e", "f", "g", "h", "h", "h", "i", "c", "i", "d"};
//        String[] edgenodes2 = {"b", "c", "d", "e", "f", "g", "h", "a", "b", "i", "c", "f", "g", "f"};
//        int[] weight = {4, 8, 7, 9, 10, 2, 1, 8, 11, 7, 2, 4, 6, 14};
        String[] nodes = {"s", "v1", "v3", "v4", "v2", "t"};
        String[] edgenodes1 = {"s", "v1", "v3", "v4", "v4", "v3", "v2", "s", "v2"};
        String[] edgenodes2 = {"v1", "v3", "t", "v3", "t", "v2", "v4", "v2", "v1"};
        int[] weight = {16, 12, 20, 7, 4, 9, 14, 13, 4};
        for(String str:nodes){
            test.addNode(str);
        }
        for(int i = 0; i < edgenodes1.length; i++){
//            test.addEdge(edgenode1[i], edgenode2[i]);
            test.addEdge(edgenodes1[i], edgenodes2[i], weight[i]);
        }
//        test.naiveShortPaths();
//        test.showMatrix();
//        test.showPaths();
//        test.FloydWarshall();
//        test.showPaths();
//        test.showMatrix();
//        test.showPath("i", "a");
//        test.BellmanFord("i");
//        test.showPredecessor();
//        test.walk("i", "e");
//        test.createMatrix();
//        test.Dijkstra("i");
//        test.showPredecessor();
//        test.walk("i", "e");
////        test.MSTPrime("a");
//        test.BFS("i");
//        System.out.println();
//        test.BFS("u");
        test.maxFlow("s", "t");
        test.showFlow("t");
    }
}
class Vertex{
    public static enum Status{White, Gray, Black};
    public String name;
    public AdjEdgeNode List;
    private int identity;
    public int distance = 1;
    public int key = Integer.MAX_VALUE;
    public Vertex primeParent = null;
    Status color = Status.White;
    public Vertex predecessor = null;
    public AdjEdgeNode preEdge = null;
    public void setIdentity(int id){
        this.identity = id;
    }
    public int getIdentity(){
        return this.identity;
    }
    Vertex(String name){
        this.name = name;
    }
    public boolean isEdgeExist(Vertex node){
        AdjEdgeNode point = List;
        while(point != null){
            if(point.node.equals(node)) return true;
            point = point.next;
        }
        return false;
    }
    public AdjEdgeNode findadjEdge(Vertex node){
        AdjEdgeNode point = List;
        while(point != null){
            if(point.node.equals(node)) return point;
            point = point.next;
        }
        return null;
    }
    public void delAdjEdgeNode(AdjEdgeNode adj){
        if(adj == List){
            List = adj.next;
        }
        else{
            adj.pre.next = adj.next;
            if(adj.next != null) adj.next.pre = adj.pre;
        }
    }
    public void addAdjEdgeNode(Vertex endNode, int weight){ // maxflow
        AdjEdgeNode point = List;
        if(point != null){
            while(point.next != null){
                point = point.next;
            }
            point.next = new AdjEdgeNode(endNode,weight);
            point.next.pre = point;
            point.next.Source = this;
        }
        else{
            this.List = new AdjEdgeNode(endNode,weight);
            this.List.Source = this;
        }
    }
}
class Edge{
    public Vertex source;
    public Vertex end;
    public int weight = 1;
    Edge(Vertex source, Vertex end, int weight){
        this.source = source;
        this.end = end;
        this.weight = weight;
    }
    Edge(Vertex source, Vertex end){
        this.source = source;
        this.end = end;
    }
}
class AdjEdgeNode{
    public int weight = 1;
    public AdjEdgeNode pre = null;
    public AdjEdgeNode next = null;
    public Vertex node = null;
    public Vertex Source = null;
    public int flow = 0;
    AdjEdgeNode(Vertex node, int weight){
        this.weight = weight;
        this.node = node;
    }
    AdjEdgeNode(Vertex node){
        this.node = node;
    }
}
abstract class Graph {
    HashMap<String, Vertex> NodeTable = new HashMap<>();
    ArrayList<Vertex> NodeList = new ArrayList<>();
    protected int count = 0;
    public int[][] adjMatrix;
    public int[][] path;
    public Vertex addNode(String name){
        if(this.NodeTable.containsKey(name)){
            System.out.print("The node has in this Graph.");
            return null;
        }
        Vertex newMember = new Vertex(name);
        newMember.setIdentity(count++);
        NodeTable.put(name, newMember);
        NodeList.add(newMember);
        return newMember;
    };
    public abstract void addEdge(String node1, String node2);
    public abstract void addEdge(String node1, String node2, int weight);
    public List<Vertex> BFS(String startNode){
        List<Vertex> ans = new ArrayList<>();
        if(!this.NodeTable.containsKey(startNode)){
            System.out.println("Start Node is not exist.");
            return ans;
        }
        this.resetColor();
        Vertex start = this.NodeTable.get(startNode);
        start.color = Vertex.Status.Gray;
        ans.add(start);
        Queue<Vertex> queue = new LinkedList<>();
        queue.offer(start);
        while(!queue.isEmpty()){
            Vertex point = queue.poll();
            AdjEdgeNode adjPoint = point.List;
            while(adjPoint != null){
                if(adjPoint.node.color == Vertex.Status.White){
                    adjPoint.node.color = Vertex.Status.Gray;
                    ans.add(adjPoint.node);
                    adjPoint.node.distance = point.distance + 1;
                    queue.offer(adjPoint.node);
                }
                adjPoint = adjPoint.next;
            }
            point.color = Vertex.Status.Black;
        }
        for(int element = 0; element < ans.size(); element++){
            System.out.print(ans.get(element).name);
            System.out.print(", ");
        }
        return ans;
    }
    public List<Vertex> DFS(String startNode){
        List<Vertex> result = new ArrayList<>();
        if(!this.NodeTable.containsKey((startNode))){
            System.out.printf("node %s is not exist.", startNode);
            return result;
        }
        this.resetColor();
        result.add(this.NodeTable.get(startNode));
        result.get(0).color = Vertex.Status.Gray;
        this.dfsRecursive(startNode, result);
        result.get(0).color = Vertex.Status.Black;
        for(int index = 0; index < result.size(); index++){
            System.out.print(result.get(index).name);
            System.out.print(", ");
        }
        return result;
    }
    private void dfsRecursive(String startNode, List<Vertex> lst){
        Vertex start = this.NodeTable.get(startNode);
        AdjEdgeNode point = start.List;
        while(point != null){
            if(point.node.color == Vertex.Status.White){
                lst.add(point.node);
                point.node.color = Vertex.Status.Gray;
                this.dfsRecursive(point.node.name, lst);
                point.node.color = Vertex.Status.Black;
            }
            point = point.next;
        }
    }
    public void resetColor(){
         Set<String> set = this.NodeTable.keySet();
         for(String key:set){
             this.NodeTable.get(key).color = Vertex.Status.White;
         }
    }
    public void MSTPrime(String startNode){
        if(!this.NodeTable.containsKey(startNode)){
            System.out.printf("node %s is not exist", startNode);
        }
        Vertex root = this.NodeTable.get(startNode);
        root.key = 0;
        root.primeParent = null;
        Queue<Vertex> queue = new PriorityQueue<Vertex>(this.NodeTable.size(), new Comparator<Vertex>() {
            @Override
            public int compare(Vertex o1, Vertex o2) {
                return o1.key- o2.key;
            }//get min
        });
        for(String name: this.NodeTable.keySet()){
            queue.offer(this.NodeTable.get(name));
        }
        while(!queue.isEmpty()){
            Vertex pre = queue.poll();
            System.out.printf("(%s, %d), ", pre.name, pre.key);
            AdjEdgeNode point = pre.List;
            while (point != null){
                if(queue.contains(point.node) && point.weight < point.node.key){
                    queue.remove(point.node);
                    point.node.key = point.weight;
                    point.node.primeParent = pre;
                    queue.offer(point.node);
                }
                point = point.next;
            }
        }
    }
}
class OrientedGraph extends Graph{
    Set<Edge> edges = new HashSet<>();
    @Override
    public void addEdge(String node1, String node2) {
        if(!this.NodeTable.containsKey(node1)){
            System.out.printf("Error: node %s is no exist now.", node1);
            return;
        }
        else if(!this.NodeTable.containsKey(node2)) {
            System.out.printf("Error: node %s is no exist.", node2);
            return;
        }
        Vertex startNode = this.NodeTable.get(node1);
        Vertex endNode = this.NodeTable.get(node2);
        AdjEdgeNode point = startNode.List;
        if(startNode.isEdgeExist(endNode)){
            System.out.println("Link has exist.");
            return;
        }
        if(point != null){
            while(point.next != null){
                point = point.next;
            }
            point.next = new AdjEdgeNode(endNode);
            point.next.pre = point;
        }
        else{
            startNode.List = new AdjEdgeNode(endNode);
        }
        edges.add(new Edge(startNode, endNode));
    }
    @Override
    public void addEdge(String node1, String node2, int weight) {
        if(!this.NodeTable.containsKey(node1)){
            System.out.printf(" Error: node %s is no exist.", node1);
            return;
        }
        else if(!this.NodeTable.containsKey(node2)) {
            System.out.printf("Error: node %s is no exist.", node2);
            return;
        }
        Vertex startNode = this.NodeTable.get(node1);
        Vertex endNode = this.NodeTable.get(node2);
        AdjEdgeNode point = startNode.List;
        if(startNode.isEdgeExist(endNode)){
            System.out.println("Link has exist.");
            return;
        }
        if(point != null){
            while(point.next != null){
                point = point.next;
            }
            point.next = new AdjEdgeNode(endNode,weight);
            point.next.pre = point;
            point.next.Source = startNode;
        }
        else{
            startNode.List = new AdjEdgeNode(endNode,weight);
            startNode.List.Source = startNode;
        }
        edges.add(new Edge(startNode, endNode, weight));
    }
    public Edge getEdge(Vertex u, Vertex v){
        if(!(NodeTable.containsValue(u) && NodeTable.containsValue(v))){
            System.out.println("node is no existed.");
            return null;
        }
        AdjEdgeNode point = u.List;
        while(point != null){
            if(point.node == v){
                return new Edge(u, v, point.weight);
            }
            point = point.next;
        }
        System.out.println("Edge is not existed.");
        return null;
    }
    public void relax(Vertex u, Vertex v){
        Edge w = getEdge(u,v);
        if(w==null) return;
        if(u.distance != Integer.MAX_VALUE && v.distance >  u.distance + w.weight){
            v.distance = u.distance + w.weight;
            v.predecessor = u;
        }
    }
    public void relax(Vertex u, Vertex v, int weight){
        //without checking the input data.
        if(u.distance != Integer.MAX_VALUE && v.distance >  u.distance + weight){
            v.distance = u.distance + weight;
            v.predecessor = u;
        }
    }
    public boolean BellmanFord(String source){
        if(!NodeTable.containsKey(source)){
            System.out.println("node is no existed.");
            return false;
        }
        resetDistance();
        resetPredecessor();
        NodeTable.get(source).distance = 0;
        for(int i = 1; i < NodeTable.size(); i++){
            for(Edge edge:edges){
                relax(edge.source, edge.end, edge.weight);
            }
        }
        for(Edge edge:edges){
            if(edge.end.distance > edge.source.distance + edge.weight){
                System.out.println("circle in this graph.");
                return false;
            }
        }
        return true;
    }
    public ArrayList<Vertex> walk(String source, String terminal){
        if(!(NodeTable.containsKey(source) && NodeTable.containsKey(terminal))){
            System.out.println("node is no existed.");
            return null;
        }
        Vertex sourceNode = NodeTable.get(source);
        Vertex point = NodeTable.get(terminal);
        ArrayList<Vertex> path = new ArrayList<>();
        path.add(point);
        int count = NodeTable.size();
        while(count > 0 && point != sourceNode && point != null){
            point = point.predecessor;
            path.add(point);
            --count;
        }
        System.out.println();
        for(int i = path.size() - 1; i >= 0; --i){
            if(i == 0) System.out.print(path.get(i).name);
            else System.out.print(path.get(i).name + " -> ");
        }
        System.out.println();
        if(path.get(path.size() - 1) != sourceNode) System.out.println("path is not existed.");
        return path;
    }
    private void resetPredecessor(){
        Set<String> nodes = NodeTable.keySet();
        for(String node:nodes){
            NodeTable.get(node).predecessor = null;
        }
    }
    public void showPredecessor(){
        Set<String> nodes = NodeTable.keySet();
        System.out.println();
        for(String node:nodes){
            if(NodeTable.get(node).predecessor != null){
                System.out.print(NodeTable.get(node).name + ":" + NodeTable.get(node).predecessor.name + " ");
            }
        }
        System.out.println();
    }
    public void resetDistance(){
        Set<String> nodes = NodeTable.keySet();
        for(String node:nodes){
            NodeTable.get(node).distance = Integer.MAX_VALUE;
        }
    }
    public void Dijkstra(String source){
        if(!NodeTable.containsKey(source)){
            System.out.println("node is no existed.");
            return;
        }
        resetDistance();
        resetPredecessor();
        NodeTable.get(source).distance = 0;
        Queue<Vertex> queue = new PriorityQueue<>(NodeTable.size(), new Comparator<Vertex>() {
            @Override
            public int compare(Vertex o1, Vertex o2) {
                return o1.distance - o2.distance;
            }
        });
        queue.addAll(NodeTable.values());
        while(!queue.isEmpty()){
            Vertex u = queue.poll();
            AdjEdgeNode point = u.List;
            while(point != null){
                if(u.distance != Integer.MAX_VALUE && point.node.distance >  u.distance + point.weight){
                    queue.remove(point.node);
                    point.node.distance = u.distance + point.weight;
                    point.node.predecessor = u;
                    queue.offer(point.node);
                }
                point = point.next;
            }
        }
    }
    public void createMatrix(){
        List<Vertex> nodeList = new ArrayList<>();
        nodeList.addAll(NodeTable.values());
        adjMatrix = new int[nodeList.size()][nodeList.size()];
        for(int i = 0; i < nodeList.size(); i++){
            for(int j = 0; j < nodeList.size(); j++){
                adjMatrix[i][j] = Integer.MAX_VALUE;
            }
        }
        for(Vertex node:nodeList){
            AdjEdgeNode point = node.List;
            while(point != null){
                adjMatrix[node.getIdentity()][point.node.getIdentity()] = point.weight;
                point = point.next;
            }
        }
        for(int k = 0; k < nodeList.size(); k++){
            adjMatrix[k][k] = 0;
        }
    }
    public void naiveShortPaths(){
        createMatrix();
        path = new int[adjMatrix.length][adjMatrix.length];
        for(int i = 0; i < path.length; i++){
            for(int j = 0; j < path.length; j++){
                path[i][j] = j;
            }
        }
        int[][] Matrix = adjMatrix;
        for(int l = 0; l < path.length; l++){
            Matrix = extendShortestPaths(Matrix);
        }
        adjMatrix = Matrix.clone();
    }
    private int[][] extendShortestPaths(int[][] Matrix){
        int[][] Matrix_ = new int[Matrix.length][Matrix.length];
        for(int i = 0; i < Matrix_.length; i++){
            for(int j = 0; j < Matrix_.length; j++){
                Matrix_[i][j] = Matrix[i][j];
                for(int k = 0; k < Matrix_.length; k++){
                    if(Matrix[i][k] != Integer.MAX_VALUE && adjMatrix[k][j] != Integer.MAX_VALUE && Matrix_[i][j] > Matrix[i][k] + adjMatrix[k][j]){
                        Matrix_[i][j] = Matrix[i][k] + adjMatrix[k][j];
                        this.path[i][j] = this.path[i][k];
                    }
                }
            }
        }
        return Matrix_;
    }
    public void showMatrix(){
        for(int i = 0; i < adjMatrix.length; i++){
            System.out.println();
            for(int j = 0; j < adjMatrix.length; j++){
                if(adjMatrix[i][j] != Integer.MAX_VALUE) System.out.print(adjMatrix[i][j] + ", ");
                else System.out.print("N, ");
            }
            System.out.println();
        }
    }
    public void FloydWarshall(){
        createMatrix();
        path = new int[adjMatrix.length][adjMatrix.length];
        for(int i = 0; i < path.length; i++){
            for(int j = 0; j < path.length; j++){
                path[i][j] = j;
            }
        }
        for(int mid = 0; mid < path.length; mid++){//中间节点的集合
            for(int start = 0; start < path.length; start++){
                for(int end = 0; end < path.length; end++){
                    if(adjMatrix[start][mid] != Integer.MAX_VALUE && adjMatrix[mid][end] != Integer.MAX_VALUE && adjMatrix[start][end] > adjMatrix[start][mid] + adjMatrix[mid][end]){
                        adjMatrix[start][end] = adjMatrix[start][mid] + adjMatrix[mid][end];
                        path[start][end] = path[start][mid];
                    }
                }
            }
        }
    }
    public void showPath(String name1, String name2){
        if(!(NodeTable.containsKey(name1) && NodeTable.containsKey(name2))){
            System.out.println("node is not existed.");
        }
        int index1 = NodeTable.get(name1).getIdentity();
        int index2 = NodeTable.get(name2).getIdentity();
        int point = index1;
        if(path[index1][index2] < Integer.MAX_VALUE){
            while(point != index2){
                System.out.print(NodeList.get(point).name + "->");
                point = path[point][index2];
            }
            System.out.print(NodeList.get(point).name);
        }
        else{
            System.out.println("path is not existed.");
        }
    }
    public void showPaths(){
        for(int i = 0; i < path.length; i++){
            System.out.println();
            for(int j = 0; j < path.length; j++){
                System.out.print(path[i][j] + ", ");
            }
            System.out.println();
        }
    }
    public void maxFlow(String sourcename, String sinkname){
        ArrayList<AdjEdgeNode> adjPath = new ArrayList<>();
        if(!(NodeTable.containsKey(sourcename) && NodeTable.containsKey(sinkname))){
            System.out.println("node is not existed.");
            return;
        }
        Vertex Source = NodeTable.get(sourcename);
        Vertex Sink = NodeTable.get(sinkname);
        int addFlow = findPathforFlow(Source, Sink, adjPath);
        while(addFlow > 0){
            for(AdjEdgeNode adj:adjPath){
                adj.weight = adj.weight - addFlow;
                AdjEdgeNode pair = adj.node.findadjEdge(adj.Source);
                if(pair == null){
                    adj.node.addAdjEdgeNode(adj.Source, addFlow);
                }
                else{
                    pair.weight = pair.weight + addFlow;
                }
                if(adj.weight == 0){
                    Vertex s = adj.Source;
                    s.delAdjEdgeNode(adj);
                }
            }
            adjPath.clear();
            addFlow = findPathforFlow(Source, Sink, adjPath);
        }
    }
    public int findPathforFlow(Vertex Source, Vertex Sink, ArrayList<AdjEdgeNode> adjPath){
        resetColor();
        Queue<Vertex> queue = new LinkedList<>();
        queue.offer(Source);
        Vertex node = null;
        boolean existPath = false;
        while(!(queue.isEmpty() || existPath)){
            node = queue.poll();
            node.color = Vertex.Status.Gray;
            AdjEdgeNode point = node.List;
            while(point != null){
//                point.Source = node;
                if(point.node.color == Vertex.Status.White){
                    point.node.predecessor = node;
                    point.node.preEdge = point;
                    queue.offer(point.node);
                    point.node.color = Vertex.Status.Gray;
                    if(point.node == Sink){
                        existPath = true;
                        break;
                    }
                }
                point = point.next;
            }
            node.color = Vertex.Status.Black;
        }
        if(existPath){
            int min = Sink.preEdge.weight;
            node = Sink;
            while(node != Source){
                adjPath.add(node.preEdge);
                min = Math.min(min, node.preEdge.weight);
                node = node.predecessor;
            }
            return min;
        }
        else{
            return 0;
        }
    }
    public void showFlow(String name){
        if(!NodeTable.containsKey(name)){
            return;
        }
        Vertex Source = NodeTable.get(name);
        resetColor();
        Queue<Vertex> queue = new LinkedList<>();
        queue.offer(Source);
        Vertex node = null;
        while(!queue.isEmpty()){
            node = queue.poll();
            node.color = Vertex.Status.Gray;
            AdjEdgeNode point = node.List;
            while(point != null){
                if(point.node.color == Vertex.Status.White){
                    queue.offer(point.node);
                    point.node.color = Vertex.Status.Gray;
                }
                System.out.print(point.Source.name + "->" + point.node.name + ": " + point.weight);
                System.out.println();
                point = point.next;
            }
            node.color = Vertex.Status.Black;
        }
    }
}
class UndirectedGraph extends Graph{
    @Override
    public void addEdge(String node1, String node2) {
        if(!this.NodeTable.containsKey(node1)){
            System.out.printf(" Error: node %s is no exist.", node1);
            return;
        }
        else if(!this.NodeTable.containsKey(node2)) {
            System.out.printf(" Error: node %s is no exist.", node2);
            return;
        }
        Vertex startNode = this.NodeTable.get(node1);
        Vertex endNode = this.NodeTable.get(node2);
        AdjEdgeNode point = startNode.List;
        if(startNode.isEdgeExist(endNode)){
            System.out.println("Link has exist.");
            return;
        }
        if(point != null){
            while(point.next != null){
                point = point.next;
            }
            point.next = new AdjEdgeNode(endNode);
        }
        else{
            startNode.List = new AdjEdgeNode(endNode);
        }
        point = endNode.List;
        if(point != null){
            while(point.next != null){
                point = point.next;
            }
            point.next = new AdjEdgeNode(startNode);
        }
        else{
            endNode.List = new AdjEdgeNode(startNode);
        }
    }

    @Override
    public void addEdge(String node1, String node2, int weight) {
        if(!this.NodeTable.containsKey(node1)){
            System.out.printf("Error: node %s is no exist.", node1);
            return;
        }
        else if(!this.NodeTable.containsKey(node2)) {
            System.out.printf("Error: node %s is no exist.", node2);
            return;
        }
        Vertex startNode = this.NodeTable.get(node1);
        Vertex endNode = this.NodeTable.get(node2);
        AdjEdgeNode point = startNode.List;
        if(startNode.isEdgeExist(endNode)){
            System.out.println("Link has exist.");
            return;
        }
        if(point != null){
        while(point.next != null){
            point = point.next;
        }
        point.next = new AdjEdgeNode(endNode,weight);
    }
        else{
        startNode.List = new AdjEdgeNode(endNode,weight);
    }
    point = endNode.List;
        if(point != null){
        while(point.next != null){
            point = point.next;
        }
        point.next = new AdjEdgeNode(startNode,weight);
    }
        else{
        endNode.List = new AdjEdgeNode(startNode,weight);
    }
    }
}
