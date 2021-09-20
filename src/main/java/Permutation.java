import edu.princeton.cs.algs4.StdIn;


public class Permutation {
    public static void main(String[] args) {
        if (args.length != 1) {
            throw new IllegalArgumentException("exactly 1 argument expected");
        }
        Integer k = Integer.parseInt(args[0]);
        RandomizedQueue<String> q = new RandomizedQueue<>();
        while (!StdIn.isEmpty()) {
            String string = StdIn.readString();
            q.enqueue(string);
        }

        for (int i = 0; i < k; i++) {
            System.out.println(q.dequeue());
        }
    }

}
