package io.metersphere.ui.controller;

import com.jayway.jsonpath.JsonPath;
import io.metersphere.sdk.util.JSON;
import io.metersphere.ui.domain.UiElement;
import io.metersphere.ui.dto.LocationType;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UiElementControllerTest {

    @Resource
    private MockMvc mockMvc;

    private static String elementId;

    // 添加元素
    @Test
    @Order(1)
    public void testAddUiElement() throws Exception {
        UiElement uiElement = new UiElement();
        uiElement.setName("test");
        uiElement.setCreateUser("admin");
        uiElement.setProjectId("1");
        uiElement.setLocationType(LocationType.ID.getName());
        uiElement.setLocation("kw");
        uiElement.setModuleId("null");

        var result = mockMvc.perform(MockMvcRequestBuilders.post("/ui_element/add")
                        .content(JSON.toJSONString(uiElement))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        elementId = JsonPath.read(result.getResponse().getContentAsString(), "$.data.id");
    }

    @Test
    @Order(2)
    public void testEditUiElement() throws Exception {
        UiElement uiElement = new UiElement();
        uiElement.setId(elementId);
        uiElement.setProjectId("1");
        uiElement.setName("test");
        uiElement.setCreateUser("admin");
        uiElement.setLocationType(LocationType.ID.getName());
        uiElement.setLocation("su");
        uiElement.setModuleId("null");

        mockMvc.perform(MockMvcRequestBuilders.post("/ui_element/update")
                        .content(JSON.toJSONString(uiElement))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print());
    }

    @Test
    @Order(3)
    public void testSelectAll() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/ui_element/list"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.person.name").value("Jason"))
                .andDo(print());
    }
}