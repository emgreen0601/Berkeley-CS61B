import edu.princeton.cs.algs4.Queue;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class TestSortAlgs {

    @Test
    public void testQuickSort() {
        Queue<Integer> test = new Queue<Integer>();
        test.enqueue(1);
        test.enqueue(4);
        test.enqueue(9);
        test.enqueue(7);
        test.enqueue(3);
        test.enqueue(8);
        test.enqueue(6);
        test.enqueue(4);
        test.enqueue(2);
        test.enqueue(10);
        MergeSort.mergeSort(test);
        assertTrue(isSorted(test));
    }

    @Test
    public void testMergeSort() {
        Queue<String> tas = new Queue<String>();
        tas.enqueue("Gus Fring");
        tas.enqueue("Mike Ehrmantraut");
        tas.enqueue("Walter White");
        tas.enqueue("Jessie Pinkman");
        MergeSort.mergeSort(tas);
        assertTrue(isSorted(tas));

        Queue<Integer> test = new Queue<Integer>();
        test.enqueue(1);
        test.enqueue(4);
        test.enqueue(9);
        test.enqueue(7);
        test.enqueue(3);
        test.enqueue(8);
        test.enqueue(6);
        test.enqueue(4);
        test.enqueue(2);
        test.enqueue(10);
        MergeSort.mergeSort(test);
        assertTrue(isSorted(test));
    }

    /**
     * Returns whether a Queue is sorted or not.
     *
     * @param items  A Queue of items
     * @return       true/false - whether "items" is sorted
     */
    private <Item extends Comparable> boolean isSorted(Queue<Item> items) {
        if (items.size() <= 1) {
            return true;
        }
        Item curr = items.dequeue();
        Item prev = curr;
        while (!items.isEmpty()) {
            prev = curr;
            curr = items.dequeue();
            if (curr.compareTo(prev) < 0) {
                return false;
            }
        }
        return true;
    }
}
