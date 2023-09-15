package demo.junit;


import com.youjun.api.modules.test.demo.TestController;
import com.youjun.api.modules.test.demo.TestPojo;
import com.youjun.api.modules.test.demo.TestService;
import com.youjun.common.api.CommonResult;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class TestControllerTest {
    @Mock
    private TestService testService;
    @InjectMocks
    private TestController testController;

    public TestControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void selectById_shouldReturnTestObject() {
        // Arrange
        Long id = 1L;
        TestPojo expectedTest = new TestPojo();
        when(testService.selectById(id)).thenReturn(expectedTest);
        // Act
        CommonResult<TestPojo> result = testController.selectById(id);
        // Assert
        assertNotNull(result);
        assertEquals(expectedTest, result.getData());
        assertEquals("200", result.getErrorCode());
    }

    @Test
    void selectById_shouldReturnErrorForInvalidId() {
        // Arrange
        Long id = -1L;
        when(testService.selectById(id)).thenReturn(null);
        // Act
        CommonResult<TestPojo> result = testController.selectById(id);
        // Assert
        assertNotNull(result);
        assertNull(result.getData());
    }

    @Test
    void list_shouldReturnListOfTests() {
        // Arrange
        List<TestPojo> expectedTests = List.of(new TestPojo(), new TestPojo());
        when(testService.selectAll()).thenReturn(expectedTests);
        // Act
        CommonResult<List<TestPojo>> result = testController.list();
        // Assert
        assertNotNull(result);
        assertEquals(expectedTests, result.getData());
        assertEquals("200", result.getErrorCode());
    }

    @Test
    void add_shouldReturnTrueForSuccessfulAddition() {
        // Arrange
        TestPojo test = new TestPojo();
        when(testService.add(test)).thenReturn(true);
        // Act
        CommonResult<Boolean> result = testController.add(test);
        // Assert
        assertNotNull(result);
        assertTrue(result.getData());
        assertEquals("200", result.getErrorCode());
    }

    @Test
    void add_shouldReturnFalseForFailedAddition() {
        // Arrange
        TestPojo test = new TestPojo();
        when(testService.add(test)).thenReturn(false);
        // Act
        CommonResult<Boolean> result = testController.add(test);
        // Assert
        assertNotNull(result);
        assertFalse(result.getData());
    }

    @Test
    void update_shouldReturnTrueForSuccessfulUpdate() {
        // Arrange
        TestPojo test = new TestPojo();
        when(testService.update(test)).thenReturn(true);
        // Act
        CommonResult<Boolean> result = testController.update(test);
        // Assert
        assertNotNull(result);
        assertTrue(result.getData());
        assertEquals("200", result.getErrorCode());
    }

    @Test
    void update_shouldReturnFalseForFailedUpdate() {
        // Arrange
        TestPojo test = new TestPojo();
        when(testService.update(test)).thenReturn(false);
        // Act
        CommonResult<Boolean> result = testController.update(test);
        // Assert
        assertNotNull(result);
        assertFalse(result.getData());
    }
}