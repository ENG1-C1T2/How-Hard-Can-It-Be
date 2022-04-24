package com.mygdx.tests.utils;

import com.mygdx.utils.QueueFIFO;

import com.mygdx.tests.GdxTestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(GdxTestRunner.class)
public class QueueFIFOTests {

    @Test
    public void size() {
        QueueFIFO<Object> queue = new QueueFIFO<>();
        queue.set(new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5)));

        QueueFIFO<Object> emptyQueue = new QueueFIFO<>();
        emptyQueue.set(new ArrayList<>());

        assertAll(() -> assertEquals(queue.size(), 5, "Fail: Size of Queue Is Wrong"),
                () -> assertEquals(emptyQueue.size(), 0, "Fail: Size of Empty Queue Is Wrong"));
    }
}