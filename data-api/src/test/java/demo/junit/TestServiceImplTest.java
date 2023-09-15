package demo.junit;

import com.youjun.api.modules.test.demo.TestPojo;
import com.youjun.api.modules.test.demo.TestServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>
 *
 * </p>
 *
 * @author kirk
 * @since 2023/9/15
 */
@ExtendWith(SpringExtension.class)
class TestServiceImplTest {

    @InjectMocks
    private TestServiceImpl testService;
    @Test
    public void testSelectByIdPositive() {
        TestPojo expected = new TestPojo(1L, "test");
        TestPojo actual = testService.selectById(1L);
        assertEquals(expected, actual);
    }
    @Test
    public void testSelectByIdNegative() {
        TestPojo expected = new TestPojo(1L, "test");
        TestPojo actual = testService.selectById(2L);
        assertNotEquals(expected, actual);
    }
    @Test
    public void testSelectAllPositive() {
        List<TestPojo> expected = Arrays.asList(new TestPojo(1L, "test"), new TestPojo(2L, "test2"));
        List<TestPojo> actual = testService.selectAll();
        assertEquals(expected, actual);
    }
    @Test
    public void testSelectAllNegative() {
        List<TestPojo> expected = Arrays.asList(new TestPojo(1L, "test"), new TestPojo(2L, "test2"), new TestPojo(3L, "test3"));
        List<TestPojo> actual = testService.selectAll();
        assertNotEquals(expected, actual);
    }
    @Test
    public void testAddPositive() {
        TestPojo test = new TestPojo(1L, "test");
        boolean actual = testService.add(test);
        assertTrue(actual);
    }
    @Test
    public void testAddNegative() {
        TestPojo test = null;
        boolean actual = testService.add(test);
        assertFalse(actual);
    }
    @Test
    public void testUpdatePositive() {
        TestPojo test = new TestPojo(1L, "test");
        boolean actual = testService.update(test);
        assertTrue(actual);
    }
    @Test
    public void testUpdateNegative() {
        TestPojo test = null;
        boolean actual = testService.update(test);
        assertFalse(actual);
    }
}