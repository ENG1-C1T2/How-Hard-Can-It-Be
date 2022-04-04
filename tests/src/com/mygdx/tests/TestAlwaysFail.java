package com.mygdx.tests;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(GdxTestRunner.class)
public class TestAlwaysFail {
    @Test
    public void testAlwaysFail() {
        fail("This test should always fail.");
    }
}
