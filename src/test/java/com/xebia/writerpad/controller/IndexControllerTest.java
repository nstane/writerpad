package com.xebia.writerpad.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class IndexControllerTest {

    @Test
    public void indexControllerTest() {
        IndexController controller = new IndexController();
        Assertions.assertEquals(controller.index(), "Welcome to WriterPad. Please visit http://localhost:8080/swagger-ui.html for API documentation.");
    }
}
