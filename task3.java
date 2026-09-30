import java.util.*;

public class Huffman {

    static class Node implements Comparable<Node> {
        char ch;
        int freq;
        Node left;
        Node right;

        Node(char ch, int freq) {
            this.ch = ch;
            this.freq = freq;
        }

        Node(int freq, Node left, Node right) {
            this.freq = freq;
            this.left = left;
            this.right = right;
        }

        boolean isLeaf() {
            return left == null && right == null;
        }

        @Override
        public int compareTo(Node other) {
            return Integer.compare(this.freq, other.freq);
        }
    }

    public static String encode(String text) {
        if (text == null || text.isEmpty()) {
            return "";
        }

        Map<Character, Integer> frequencies = new HashMap<>();

        for (char ch : text.toCharArray()) {
            frequencies.put(ch, frequencies.getOrDefault(ch, 0) + 1);
        }

        PriorityQueue<Node> queue = new PriorityQueue<>();

        for (Map.Entry<Character, Integer> entry : frequencies.entrySet()) {
            queue.offer(new Node(entry.getKey(), entry.getValue()));
        }

        if (queue.size() == 1) {
            Node only = queue.poll();
            queue.offer(new Node(only.freq, only, null));
        }

        while (queue.size() > 1) {
            Node left = queue.poll();
            Node right = queue.poll();

            Node parent = new Node(
                    left.freq + right.freq,
                    left,
                    right
            );

            queue.offer(parent);
        }

        Node root = queue.poll();

        Map<Character, String> codes = new HashMap<>();
        buildCodes(root, "", codes);

        StringBuilder result = new StringBuilder();

        for (char ch : text.toCharArray()) {
            result.append(codes.get(ch));
        }

        return result.toString();
    }

    private static void buildCodes(
            Node node,
            String code,
            Map<Character, String> codes
    ) {
        if (node == null) {
            return;
        }

        if (node.isLeaf()) {
            codes.put(node.ch, code);
            return;
        }

        buildCodes(node.left, code + "0", codes);
        buildCodes(node.right, code + "1", codes);
    }

    public static void main(String[] args) {
        String text = "aaabbc";

        String encoded = encode(text);

        System.out.println(encoded);
    }
}
