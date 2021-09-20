import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private Node first = null;
    private Node last = null;
    private int size = 0;

    // construct an empty deque
    public Deque() {

    }

    // is the deque empty?
    public boolean isEmpty() {
        return size() == 0;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("item is null");
        }

        Node newNode = new Node();
        if (first != null) {
            newNode.next = first;
            first.prev = newNode;
        }
        newNode.value = item;
        first = newNode;
        if (last == null) {
            last = first;
        }
        size++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("item is null");
        }

        Node newNode = new Node();
        newNode.next = null;
        newNode.value = item;
        if (last != null) {
            newNode.prev = last;
            last.next = newNode;
        }
        last = newNode;
        if (first == null) {
            first = last;
        }
        size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (size() == 0) {
            throw new NoSuchElementException();
        }

        Node n = first;
        first = n.next;
        if (first != null) {
            first.prev = null;
        }
        n.next = null;
        size--;
        if (size == 0) {
            last.next = null;
            last.prev = null;
            last = null;
        }
        return n.value;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (size() == 0) {
            throw new NoSuchElementException();
        }

        Node n = last;
        last = n.prev;
        if (last != null) {
            last.next = null;
        }
        n.prev = null;
        size--;
        if (size == 0) {
            first.next = null;
            first.prev = null;
            first = null;
        }
        return n.value;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new Iter();
    }

    private class Node {
        Item value;
        Node next;
        Node prev;
    }

    private class Iter implements Iterator<Item> {

        int sz;

        private Iter() {
            cursor = first;
            sz = size;
        }

        private Node cursor;

        @Override
        public boolean hasNext() {
            if (sz != size) {
                throw new IllegalStateException("deque was modified from the outside");
            }
            return cursor != null;
        }

        @Override
        public void remove(){
            throw new UnsupportedOperationException();
        }

        @Override
        public Item next() {
            if (sz != size) {
                throw new IllegalStateException("deque was modified from the outside");
            }
            if (cursor == null) {
                throw new NoSuchElementException();
            }
            Node n = cursor;
            cursor = cursor.next;
            return n.value;
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        Runnable[] tests = new Runnable[]{
                Deque::testSimple,
                Deque::test2,
                Deque::testIterator,
                //Deque::testCoursera,
                Deque::testFailed
        };
        System.out.println("========== Execute tests ==========");
        for (int i = 0; i < tests.length; i++) {
            tests[i].run();
        }
        System.out.println("========== END =========");
    }

    private static void testSimple() {
        System.out.println("Fill queue by calling addLast N times, remove all by calling removeFirst N times, compare result with ethalon.");
        Deque<Character> q = new Deque<>();
        String input = "abcde";
        StringBuilder sb = new StringBuilder();
        int size = q.size();
        test(q.isEmpty(), "Queue was empty, but isEmpty() returned FALSE");
        test(size == 0, "Queue was empty, but size() returned " + size);
        for (Character i : input.toCharArray()) {
            q.addLast(i);
        }
        test(!q.isEmpty(), "Queue was not empty, but isEmpty() returned TRUE");
        size = q.size();
        test(size == 5, "Queue size is 5, but size() returned " + size);
        while (!q.isEmpty()) {
            sb.append(q.removeFirst());
        }

        String expected = "abcde";
        String actual = sb.toString();
        test(expected.equals(actual), String.format("Wrong result. Expected %s, but was %s", expected, actual));
    }

    private static void test2() {
        System.out.println("Fill queue by randomly calling addFirst addLast N times, remove all by calling removeLast N times, compare result with ethalon");
        Deque<Character> q = new Deque<>();
        String input = "abcde";
        StringBuilder sb = new StringBuilder();
        int c = 4;
        for (Character i : input.toCharArray()) {
            if (c++%2==0) {
                q.addFirst(i);
            }
            else {
                q.addLast(i);
            }
        }
        while (!q.isEmpty()) {
            sb.append(q.removeLast());
        }

        String expected = "dbace";
        String actual = sb.toString();
        test(expected.equals(actual), String.format("Wrong result. Expected %s, but was %s", expected, actual));
    }

    private static void testIterator(){
        System.out.println("iterator");
        Deque<Character> q = new Deque<>();
        String input = "abcde";
        for (Character i : input.toCharArray()) {
            q.addLast(i);
        }

        Iterator<Character> iter = q.iterator();
        StringBuilder sb = new StringBuilder();
        while(iter.hasNext()) {
             sb.append(iter.next());
        }

        String expected = "abcde";
        String actual = sb.toString();
        test(expected.equals(actual), String.format("Wrong result. Expected %s, but was %s", expected, actual));
    }

    private static void testFailed() {
        Deque<Integer> deque = new Deque<>();
        deque.addLast(1);
        System.out.println(deque.removeLast());
        deque.addLast(3);
        System.out.println(deque.removeFirst());
    }

    private static void test(boolean cond, String msg) {
        if (!cond)
            throw new AssertionError(msg);
    }
}