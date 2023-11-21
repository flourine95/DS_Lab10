package Queue;

import java.util.LinkedList;
import java.util.Queue;

public class TestQueue {
    public static void main(String[] args) {
        Queue<Integer> queue = new LinkedList<>();
        queue.add(1);
        queue.add(2);
        queue.add(3);
        MyFIFO_App.stutter(queue);
        MyFIFO_App.mirror(queue);
        System.out.println(queue);
    }
}
