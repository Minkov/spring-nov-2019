package com.minkov.heroes.web.base;

import com.minkov.heroes.base.TestBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc
public class ViewTestBase extends TestBase {
    @Autowired
    protected MockMvc mockMvc;
}
