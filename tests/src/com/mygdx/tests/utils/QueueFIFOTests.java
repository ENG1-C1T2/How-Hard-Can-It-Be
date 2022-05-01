package com.mygdx.tests.utils;

import com.mygdx.utils.QueueFIFO;

import com.mygdx.tests.GdxTestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(GdxTestRunner.class)

public class QueueFIFOTests {

    @Test
    public void size() {
        QueueFIFO<Object> queue = new QueueFIFO<>();
        queue.set(new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5)));

        QueueFIFO<Object> emptyQueue = new QueueFIFO<>();
        emptyQueue.set(new ArrayList<>());

        assertAll(
                () -> assertEquals(queue.size(), 5),
                () -> assertEquals(emptyQueue.size(), 0));
    }

    @Test
    public void isEmpty() {
        QueueFIFO<Object> emptyQueue = new QueueFIFO<>();
        emptyQueue.set(new ArrayList<>());

        assertAll(
                () -> assertTrue(emptyQueue.isEmpty()));
    }

    @Test
    public void contains() {
        QueueFIFO<Object> queue = new QueueFIFO<>();
        queue.set(new ArrayList<>(Arrays.asList(1, 2, 3)));

        boolean testExpected = queue.contains(2);
        boolean shouldFail = queue.contains(0);

        assertAll("An error has occurred with the contains method",
                () -> assertTrue(testExpected),
                () -> assertFalse(shouldFail)
     

        );
    }

    @Test
    public void iterator() {

    }

    @Test
    public void toArray() {

    }

    @Test
    public void add() {

    }

    @Test
    public void remove() {

    }

    @Test
    public void containsAll() {

    }

    @Test
    public void addAll() {

    }

    @Test
    public void removeAll() {

    }

    @Test
    public void retainAll() {

    }

    @Test
    public void clear() {
    //Not implemented
    }

    @Test
    public void offer() {

    }

    @Test
    public void poll() {

    }

    @Test
    public void element() {

    }

    @Test
    public void peek() {

    }
}