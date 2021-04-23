package com.youjun.api.modules.office.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

/**
 * <p>
 *
 * </p>
 *
 * @author kirk
 * @since 2021/4/19
 */
@SpringBootTest
class OfficeControllerTest {
    @Autowired
    OfficeController officeController;

    @Test
    void readWordXML() {
        try {
            officeController.readWordXML();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void readWord2003() {
    }

    @Test
    void readWord2007() {
    }
}