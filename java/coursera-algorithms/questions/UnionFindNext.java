/**
 * Coursera | Algorithms, Part I | Week 01
 * Successor with delete.
 * Given a set of n integers S = {0, 1, …, n-1} and a sequence of requests of the following form:
 *  - Remove x from S
 *  - Find the successor of x: the smallest y in S such that y ≥ x.
 * design a data type so that all operations (except construction) take logarithmic time or better in the worst case.
 */
public class UnionFindNext {
  private final int n;
  private final int[] id;
  private final int[] sz;
  private final int[] max;
  private final boolean[] removed;

  public UnionFindNext(int n) {
    this.n = n;
    id = new int[n];
    sz = new int[n];
    max = new int[n];
    removed = new boolean[n];
    for (int i = 0; i < n; i++) {
      id[i] = i;
      sz[i] = 1;
      max[i] = i;
      removed[i] = false;
    }
  }

  private int root(int i) {
    while (i != id[i]) {
      id[i] = id[id[i]];
      i = id[i];
    }
    return i;
  }

  private void union(int p, int q) {
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

  public void remove(int i) {
    if (i < 0 || i >= n) {
      return;
    }

    removed[i] = true;
    if (removed[i - 1]) {
      union(i - 1, i);
    }
    if (removed[i + 1]) {
      union(i, i + 1);
    }
  }

  public int next(int i) {
    if (i < 0 || i >= n) {
      return -1;
    }
    if (!removed[i]) {
      return i;
    }

    int m = max[root(i)];
    return m == n ? -1 : m + 1;
  }

  public static void main(String[] args) {
    UnionFindNext uf = new UnionFindNext(10);
    System.out.println(uf.next(5)); // Output: 5
    uf.remove(5);
    uf.remove(6);
    System.out.println(uf.next(5));  // Output: 7
  }
}
