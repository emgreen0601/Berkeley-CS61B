package es.datastructur.synthesizer;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tests the ArrayRingBuffer class.
 *
 * @author Josh Hug
 */

public class TestArrayRingBuffer {
    @Test
    public void someTest() {
        ArrayRingBuffer<String> arb = new ArrayRingBuffer<String>(10);
        assertTrue(arb.isEmpty());
        assertFalse(arb.isFull());
        arb.enqueue("test0");
        assertEquals(arb.fillCount(), 1);
        assertEquals(arb.peek(), "test0");

        for (int i = 0; i <= 3; i++) {
            arb.enqueue("test1");
        }
        for (int i = 0; i <= 3; i++) {
            arb.dequeue();
        }
        assertEquals(arb.fillCount(), 1);
        assertEquals(arb.peek(), "test1");
    }
}
