import java.util.Random;

public class Main {
    private static class ListNode {
        int val;
        ListNode next;
    }

    //1.1
    ListNode reverseList(ListNode head) {
        if (head == null) return null;
        ListNode node = head.next;
        if (node == null) return head;
        ListNode root = reverseList(node);
        node.next = head;
        head.next = null;
        return root;
    }
    
	//1.2
    ListNode mergeList(ListNode node1, ListNode node2) {
        if (node1 == null) return node2;
        if (node2 == null) return node1;
        if (node1.val < node2.val) {
            node1.next = mergeList(node1.next, node2);
            return node1;
        }
        node2.next = mergeList(node1, node2.next);
        return node2;
    }

	//2.1
    String plus(String a, String b) {
        StringBuilder sb = new StringBuilder();
        int m = a.length(), n = b.length(), k = 0;

        while (m > 0 || n > 0) {
            if (--m >= 0) k += a.charAt(m) - '0';
            if (--n >= 0) k += b.charAt(n) - '0';
            sb.insert(0, k % 10);
            k /= 10;
        }
        if (k > 0) sb.insert(0, k);
        return sb.toString();
    }

	//2.2
    static int len = 9;
    int mod(String s, int n) {
        int val = Integer.parseInt(s.substring(Math.max(s.length() - len, 0))) % n;
        if (s.length() <= len) return val;
        return (int)(((long)Math.pow(10, len) % n * mod(s.substring(0, s.length() - len), n) % n + val) % n);
    }

	//3.1
    int kthSmallest(int[][] matrix, int k) {
        int l = matrix[0][0], r = matrix[matrix.length - 1][matrix[0].length - 1];
        while (l < r) {
            int mid = (r - l) / 2 + l;
            if (numSmallerThanMid(matrix, mid) < k) l = mid + 1;
            else r = mid;
        }
        return l;
    }

    int numSmallerThanMid(int[][] matrix, int mid) {
        int i = matrix.length - 1;
        int j = 0;
        int k = 0;
        while (i >= 0 && j < matrix[i].length) {
            if (matrix[i][j] > mid) i--;
            else {
                k += i + 1;
                j++;
            }
        }
        return k;
    }
	
	//3.2
	static double target = new Random().nextDouble() * new Random().nextInt(Integer.MAX_VALUE);

    static int compare(double val) {
        double diff = val - target;
        if (Math.abs(diff) <= EPSILON) return 0;
        return  diff > 0 ? 1 : -1;
    }

    private static final double EPSILON = 1e-5;

    private static double findDouble() {
        double l = 0;
        double r = Integer.MAX_VALUE;
        while (l < r) {
            double mid = l + (r - l) / 2;
            int v = compare(mid);
            if (v == 0) return mid;
            if (v < 0) l = mid + EPSILON;
            else r = mid;
        }
        return 0;
    }

	//4.1
    int maximalSquare(int[][] matrix) {
        int[][] a = new int[matrix.length][matrix[0].length];
        int max = 0;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j] == 0) continue;
                if (i == 0 || j == 0 || matrix[i - 1][j - 1] == 0) a[i][j] = 1;
                else {
                    a[i][j] = a[i - 1][j - 1] + 1;
                    for (int k = 1; k <= a[i - 1][j - 1]; k++) {
                        if (matrix[i - k][j] == 0 || matrix[i][j - k] == 0) {
                            a[i][j] = k;
                            break;
                        }
                    }
                }
                if (a[i][j] > max) max = a[i][j];
            }
        }
        return max * max;
    }

	//4.2
    int maxSubArray(int[] array) {
        int[][] a = new int[array.length][2];
        a[0][0] = 0;
        int max = a[0][1] = array[0];
        for (int i = 1; i < array.length; i++) {
            max = Math.max(max, array[i]);
            a[i][0] = Math.max(a[i - 1][0], a[i - 1][1]);
            a[i][1] = Math.max(a[i - 1][1] + array[i], array[i]);
        }
        return max < 0 ? max : Math.max(a[array.length - 1][0], a[array.length - 1][1]);
    }
}
