package week1;
class LinkedList {
    static class Node {
        int val;
        Node next;
        Node(int val) { this.val = val; }
    }
    public boolean hasCycle(Node head) {
        Node slow = head, fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) return true;
        }
        return false;
    }
    public static void main(String[] args) {
        LinkedList.Node head = new LinkedList.Node(1);
        head.next = new LinkedList.Node(2);
        head.next.next = head;
        System.out.println(new LinkedList().hasCycle(head));
    }
}