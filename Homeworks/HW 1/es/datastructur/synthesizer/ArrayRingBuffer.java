package es.datastructur.synthesizer;

import java.util.Iterator;
import java.util.Objects;

public class ArrayRingBuffer<T> implements BoundedQueue<T> {
    /* Index for the next dequeue or peek. */
    private int first;
    /* Index for the next enqueue. */
    private int last;
    /* Variable for the fillCount. */
    private int fillCount;
    /* Array for storing the buffer data. */
    private T[] rb;

    /**
     * Create a new ArrayRingBuffer with the given capacity.
     */
    public ArrayRingBuffer(int capacity) {
        rb = (T[]) new Object[capacity];
        first = 0;
        last = 0;
        fillCount = 0;
    }

    @Override
    public int capacity() {
        return rb.length;
    }

    @Override
    public int fillCount() {
        return fillCount;
    }

    /**
     * Adds x to the end of the ring buffer. If there is no room, then
     * throw new RuntimeException("Ring buffer overflow").
     *
     * @param x
     */
    @Override
    public void enqueue(T x) {
        if (fillCount() == capacity()) {
            throw new RuntimeException("Ring Buffer overflow");
        } else {
            rb[last] = x;
            fillCount += 1;
            if (last == capacity() - 1) {
                last = 0;
            } else {
                last += 1;
            }
        }
    }

    /**
     * Dequeue oldest item in the ring buffer. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow").
     */
    public T dequeue() {
        if (fillCount == 0) {
            throw new RuntimeException("Ring Buffer underflow");
        }
        T cache = rb[first];
        fillCount -= 1;
        rb[first] = null;
        if (first == capacity() - 1) {
            first = 0;
        } else {
            first += 1;
        }
        return cache;
    }

    /**
     * Return oldest item, but don't remove it. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow").
     */
    public T peek() {
        if (isEmpty()) {
            throw new RuntimeException("Ring buffer underflow");
        } else {
            return rb[first];
        }
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null) {
            return false;
        }
        if (other.getClass() != this.getClass()) {
            return false;
        }
        ArrayRingBuffer<T> o = (ArrayRingBuffer<T>) other;
        if (fillCount() != o.fillCount() || capacity() != o.capacity()) {
            return false;
        }

        Iterator<T> thisIterator = this.iterator();
        Iterator<T> otherIterator = o.iterator();
        while (thisIterator.hasNext() && otherIterator.hasNext()) {
            if (thisIterator.next() != otherIterator.next()) {
                return false;
            }
        }
        return true;
    }

    public Iterator<T> iterator() {
        return new ArraySetIterator();
    }

    private class ArraySetIterator implements Iterator<T> {
        private int wizPos;
        private int size;

        public ArraySetIterator() {
            wizPos = first;
            size = 0;
        }

        public boolean hasNext() {
            return size <= fillCount;
        }

        public T next() {
            T returnItem = rb[wizPos];
            size += 1;
            if (wizPos == capacity() - 1) {
                wizPos = 0;
            } else {
                wizPos += 1;
            }
            return returnItem;
        }
    }
}
