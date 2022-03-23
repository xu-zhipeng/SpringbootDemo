import com.youjun.api.modules.office.util.excelSax.ExcelSaxRead;
import com.youjun.common.util.CalculateTimeUtils;
import com.youjun.common.util.JsonUtils;

import java.io.FileInputStream;
import java.util.Map;

/**
 * <p>
 *
 * </p>
 *
 * @author kirk
 * @since 2021/5/13
 */
public class ExcelAnalyzeTest {
    public static void main(String[] args) throws Exception {
//        ExcelUtil.readBySax("D:\\Users\\86133\\Downloads\\YC - 副本(1).xlsx", 0, createRowHandler());
//        ExcelUtil.readBySax("D:\\Users\\86133\\Downloads\\A1005_A1016_ADD_CSI_1647241114065.xlsx", 1, createRowHandler());
        String filePath = "D:\\Users\\86133\\Downloads\\工作簿1.xlsx";
        CalculateTimeUtils.start();
        CalculateTimeUtils.spendTime("文件流");
        ExcelSaxRead excelSaxRead = new ExcelSaxRead();
        Map<String, Object[][]> dataArraySax = excelSaxRead.read(new FileInputStream(filePath));
        String str1= JsonUtils.toJson(dataArraySax);
        CalculateTimeUtils.spendTime("sax");
        System.out.println("end");
    }
}
