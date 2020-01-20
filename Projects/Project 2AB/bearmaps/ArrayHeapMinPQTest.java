package bearmaps;

import org.junit.Test;

import java.util.NoSuchElementException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ArrayHeapMinPQTest {

    @Test
    public void testAdd() {
        ArrayHeapMinPQ<String> pq = new ArrayHeapMinPQ<>();
        pq.add("Test1", 3);
        pq.add("Test2", 1);
        pq.add("Test3", 2);
        pq.add("Test4", 0);
        assertEquals(4, pq.size());
    }

    @Test
    public void testGetSmallest() {
        ArrayHeapMinPQ<String> pq = new ArrayHeapMinPQ<>();
        pq.add("Test1", 3);
        pq.add("Test2", 1);
        pq.add("Test3", 2);
        pq.add("Test4", 0);
        assertEquals("Test4", pq.getSmallest());
    }

    @Test
    public void testRemoveSmallest() {
        ArrayHeapMinPQ<String> pq = new ArrayHeapMinPQ<>();
        pq.add("Test1", 3);
        pq.add("Test2", 1);
        pq.add("Test3", 2);
        pq.add("Test4", 0);
        assertEquals("Test4", pq.removeSmallest());
        assertEquals(3, pq.size());
    }

    @Test
    public void testChangePriority() {
        ArrayHeapMinPQ<String> pq = new ArrayHeapMinPQ<>();
        pq.add("Test1", 3);
        pq.add("Test2", 1);
        pq.add("Test3", 2);
        pq.add("Test4", 2);
        pq.changePriority("Test3", 0);
        pq.changePriority("Test4", 5);
        assertEquals("Test3", pq.getSmallest());
    }

    @Test(expected = NoSuchElementException.class)
    public void testNoSuchElement() {
        ArrayHeapMinPQ<String> pq = new ArrayHeapMinPQ<>();
        pq.add("Test1", 3);
        pq.add("Test2", 1);
        pq.add("Test3", 2);
        pq.changePriority("Test4", 7);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIllegalArgument() {
        ArrayHeapMinPQ<String> pq = new ArrayHeapMinPQ<>();
        pq.add("Test1", 3);
        pq.add("Test1", 2);
    }
}

