package demo.integration;

import com.youjun.api.DataApiApplication;
import com.youjun.api.modules.test.demo.TestPojo;
import com.youjun.api.modules.test.demo.TestService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest(classes = DataApiApplication.class,webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private TestService testService;

    @Test
    public void selectById_shouldReturnTestPojo() throws Exception {
        Long id = 1L;
        TestPojo testPojo = new TestPojo();
        when(testService.selectById(id)).thenReturn(testPojo);
        mockMvc.perform(MockMvcRequestBuilders.get("/test/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data").value(testPojo))
                .andExpect(jsonPath("$.errorCode").value("200"))
                .andExpect(jsonPath("$.errorMessage").value("操作成功"));
    }

    @Test
    public void list_shouldReturnListOfTestPojo() throws Exception {
        List<TestPojo> testPojoList = Arrays.asList(new TestPojo(1L,"test1"), new TestPojo(2L,"test2"));
        when(testService.selectAll()).thenReturn(testPojoList);
        mockMvc.perform(MockMvcRequestBuilders.get("/test/list"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.data").value(testPojoList))
                .andExpect(jsonPath("$.data", hasSize(2))) // 检查数据部分是否包含两个元素
                .andExpect(jsonPath("$.data[0].id").value(1)) // 检查第一个元素的id
                .andExpect(jsonPath("$.data[0].name").value("test1")) // 检查第一个元素的name
                .andExpect(jsonPath("$.data[1].id").value(2)) // 检查第二个元素的id
                .andExpect(jsonPath("$.data[1].name").value("test2")) // 检查第二个元素的name
                .andExpect(jsonPath("$.errorCode").value("200"))
                .andExpect(jsonPath("$.errorMessage").value("操作成功"));
    }

    @Test
    public void add_shouldReturnTrueForSuccessfulAddition() throws Exception {
        TestPojo testPojo = new TestPojo();
        when(testService.add(any(TestPojo.class))).thenReturn(true);
        mockMvc.perform(MockMvcRequestBuilders.post("/test/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\": 1, \"name\": \"test\"}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data").value(true))
                .andExpect(jsonPath("$.errorCode").value("200"))
                .andExpect(jsonPath("$.errorMessage").value("操作成功"));
    }

    @Test
    public void update_shouldReturnTrueForSuccessfulUpdate() throws Exception {
        TestPojo testPojo = new TestPojo();
        when(testService.update(any(TestPojo.class))).thenReturn(true);
        mockMvc.perform(MockMvcRequestBuilders.put("/test/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\": 1, \"name\": \"test\"}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data").value(true))
                .andExpect(jsonPath("$.errorCode").value("200"))
                .andExpect(jsonPath("$.errorMessage").value("操作成功"));
    }
}