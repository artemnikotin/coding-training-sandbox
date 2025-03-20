package cts.coursera;
/**
 * Coursera | Algorithms, Part I | Week 01
 * Union-find with specific canonical element.
 * Add a method find() to the union-find data type so that find(i) returns the largest element in the connected component
 * containing ii. The operations, union(), connected(), and find() should all take logarithmic time or better.
 */
public class UnionFindMax {
  private final int[] id;
  private final int[] sz;
  private final int[] max;

  public UnionFindMax(int n) {
    id = new int[n];
    sz = new int[n];
    max = new int[n];
    for (int i = 0; i < n; i++) {
      id[i] = i;
      sz[i] = 1;
      max[i] = i;
    }
  }

  private int root(int i) {
    while (i != id[i]) {
      id[i] = id[id[i]];
      i = id[i];
    }
    return i;
  }

  public boolean connected(int p, int q) {
    return root(p) == root(q);
  }

  public void union(int p, int q) {
    int i = root(p);
    int j = root(q);
    if (i == j)
      return;
    if (sz[i] < sz[j]) {
      id[i] = j;
      sz[j] += sz[i];
      max[j] = Math.max(max[j], max[i]);
    } else {
      id[j] = i;
      sz[i] += sz[j];
      max[i] = Math.max(max[j], max[i]);
    }
  }

  public int find(int i) {
    return max[root(i)];
  }

  public static void main(String[] args) {
    UnionFindMax uf = new UnionFindMax(10);
    uf.union(1 , 2);
    uf.union(6, 9);
    uf.union(2 , 6);
    System.out.println(uf.connected(1, 9)); // Output: true
    System.out.println(uf.connected(5, 2)); // Output: false
    System.out.println(uf.find(2)); // Output: 9
  }
}
