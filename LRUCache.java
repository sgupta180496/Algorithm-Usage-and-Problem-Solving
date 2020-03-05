import java.util.HashMap;

class LRUCache {

    public class Node{
        int key,value;
        Node next, prev;
        public Node(int key, int value){
            this.key = key;
            this.value = value;
        }
        
        public Node(){}
    }
        
    
    HashMap<Integer, Node> map = new HashMap<>();
    int capacity, count;
    Node head, tail;
    
    public LRUCache(int capacity){
        this.capacity = capacity;
        this.count = 0;
        
        head = new Node();
        tail = new Node();
        head.prev = null;
        head.next = tail;
        tail.prev = head;
        tail.next = null;
        
    }
    
    public void insertNodeAfterHead(Node node){
        node.next = head.next;
        node.prev = head;
        head.next.prev = node;
        head.next = node;        
    }
    
    public void removeNode(Node node){
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }
    
    public Node removeTailNode(){
        Node result = tail.prev;
        removeNode(result);
        return result;
        
    }
    
    public void moveNodeToHead(Node node){
        removeNode(node);
        insertNodeAfterHead(node);
    }
    
    public int get(int key){
        Node node = map.get(key);
        
        if(node==null)
            return -1;
        
        moveNodeToHead(node);
        
        return node.value;
    }
    
    public void put(int key, int value){
        Node node = map.get(key);
        if(node!=null){
            node.value = value;
            moveNodeToHead(node);
        } else if(node==null){
            Node newNode = new Node(key, value);
            map.put(key, newNode);
            count++;
            insertNodeAfterHead(newNode);
            if(count>capacity){
                Node tailNode = removeTailNode();
                count = count-1;
                map.remove(tailNode.key);
            }
        }
        
    }
}

/**
 * Your LRUCache object will be instantiated and called as such:
 * LRUCache obj = new LRUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */