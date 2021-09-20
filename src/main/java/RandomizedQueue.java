import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

import java.util.Iterator;
import java.util.NoSuchElementException;


public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] items;
    private int INIT_CAP = 8;
    private int size;

    // construct an empty randomized queue
    public RandomizedQueue() {
        items = (Item[]) new Object[INIT_CAP];
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return size() == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return size;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("");
        }
        if (size == items.length) {
            resize(items.length * 2);
        }
        items[size++] = item;
    }

    // remove and return a random item
    public Item dequeue() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
        int rndIndex = StdRandom.uniform(size);
        Item remove = items[rndIndex];
        if (rndIndex < size - 1) {
            items[rndIndex] = items[size-1];
        }
        items[size-1] = null;
        size--;
        if (size > 0 && items.length / size == 4 && items.length > INIT_CAP) {
            resize(items.length / 2);
        }
        return remove;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
        int rndIndex = StdRandom.uniform(size);
        return items[rndIndex];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator(){
        return new Iter();
    }

    private class Iter implements Iterator<Item> {

        int sz;
        int[] randomIndex;
        int cursor;

        Iter() {
            sz = size;
            randomIndex = new int[sz];
            for (int i = 0; i < sz; i++) {
                randomIndex[i] = i;
            }
            StdRandom.shuffle(randomIndex);
            cursor = 0;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean hasNext() {
            if (sz != size) {
                throw new IllegalStateException("queue was modified");
            }

            return cursor < randomIndex.length;
        }

        @Override
        public Item next() {
            if (sz != size) {
                throw new IllegalStateException("queue was modified");
            }
            if (cursor == randomIndex.length) {
                throw new NoSuchElementException();
            }
            return items[randomIndex[cursor++]];
        }
    }

    private void resize(int newCapacity) {
        Item[] copy = (Item[])new Object[newCapacity];
        for (int i = 0; i < size; i++) {
            copy[i] = items[i];
            items[i] = null;
        }
        items = copy;
    }

    // unit testing (required)
    public static void main(String[] args) {
        Runnable[] tests = new Runnable[]{
                RandomizedQueue::testSimple,
                RandomizedQueue::testIterator,
                RandomizedQueue::testQueueModifiedWhileIterate,
                RandomizedQueue::testNoSuchElementException,
                RandomizedQueue::testResize,
                RandomizedQueue::testStat
        };
        System.out.println("========== Execute tests ==========");
        for (int i = 0; i < tests.length; i++) {
            tests[i].run();
        }
        System.out.println("========== END =========");
    }

    private static void testSimple() {
        System.out.println("testSimple");
        RandomizedQueue<Character> q = new RandomizedQueue<>();
        String input = "rgbyo";
        StringBuilder sb = new StringBuilder();
        for (Character i : input.toCharArray()) {
            q.enqueue(i);
        }
        while (!q.isEmpty()) {
            sb.append(q.dequeue());
        }

        test(sb.length() == input.length(), String.format("Wrong size. Expected %d, but was %d", input.length(), sb.length()));
        for (int i = 0; i < sb.length(); i++) {
            test(input.contains(String.valueOf(sb.charAt(i))), "Element from the input was not found in the output");
        }
    }

    private static void testIterator() {
        System.out.println("testIterator");
        RandomizedQueue<Character> q = new RandomizedQueue<>();
        String input = "rgbyo";
        StringBuilder sb = new StringBuilder();
        for (Character i : input.toCharArray()) {
            q.enqueue(i);
        }
        Iterator<Character> it = q.iterator();
        while (it.hasNext()) {
            sb.append(it.next());
        }
        test(sb.length() == input.length(), String.format("Wrong size. Expected %d, but was %d", input.length(), sb.length()));
        for (int i = 0; i < sb.length(); i++) {
            test(input.contains(String.valueOf(sb.charAt(i))), "Element from the input was not found in the output");
        }
    }

    private static void testQueueModifiedWhileIterate() {
        System.out.println("testQueueModifiedWhileIterate");
        RandomizedQueue<Character> q = new RandomizedQueue<>();
        q.enqueue('r');
        q.enqueue('g');
        q.enqueue('b');
        Iterator<Character> it = q.iterator();
        try {
            while (it.hasNext()) {
                it.next();

                q.enqueue('s');
            }
        }
        catch (IllegalStateException c) {
            return;
        }

        test(false, IllegalStateException.class.getName() + " expected, but was not thrown");
    }

    private static void testNoSuchElementException() {
        System.out.println("testNoSuchElementException");
        RandomizedQueue<Character> q = new RandomizedQueue<>();
        q.enqueue('r');
        q.enqueue('g');
        q.enqueue('b');
        Iterator<Character> it = q.iterator();
        try {
            while (it.hasNext()) {
                it.next();
            }
            it.next();
        }
        catch (NoSuchElementException c) {
            return;
        }

        test(false,  NoSuchElementException.class.getName() + " expected, but was not thrown");
    }

    private static void testResize() {
        System.out.println("testResize");
        RandomizedQueue<Integer> q = new RandomizedQueue<>();
        for (int i = 0; i< 1000; i++) {
            q.enqueue(i);
        }
        while(!q.isEmpty()) {
            q.dequeue();
        }
    }

    private static void testStat(){
        System.out.println("testStat");
        RandomizedQueue<Integer> q = new RandomizedQueue<>();
        int[] stat = new int[5];
        int n = 1000000;
        for (int i=0; i<n; i++) {
            q.enqueue(1);
            q.enqueue(2);
            q.enqueue(3);
            q.enqueue(4);
            q.enqueue(5);
            int j = 0;
            while (!q.isEmpty()) {
                Integer v = q.dequeue();
                stat[j++] += v;
            }
        }

        for (int i = 0; i<stat.length; i++) {
            System.out.println(stat[i]);
        }

        double dev = StdStats.stddev(stat);
        double mean = StdStats.mean(stat);
        test(dev/mean  < 0.01, "queue is not random");

    }

    private static void test(boolean cond, String msg) {
        if (!cond)
            throw new AssertionError(msg);
    }
}