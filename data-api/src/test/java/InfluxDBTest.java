//import com.youjun.api.DataApiApplication;
//import com.youjun.api.util.InfluxDbUtils;
//import org.influxdb.InfluxDB;
//import org.influxdb.dto.Point;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.util.concurrent.TimeUnit;
//
///**
// * <p>
// *
// * </p>
// *
// * @author kirk
// * @since 2020/10/22
// */
//@SpringBootTest(classes = DataApiApplication.class)
//public class InfluxDBTest {
//    @Autowired
//    InfluxDbUtils influxDbUtils;
//    @Test
//    void insetTest(){
//        InfluxDB influxDB = influxDbUtils.getInfluxDB();
//        //IM_MONITOR为表名
//        influxDB.write(Point.measurement("test")
//                .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
//                .addField("proc", "proc")
//                .addField("hostName", "hostName")
//                .addField("content", "content")
//                .addField("utime","time")
//                .addField("type","type")
//                .build());
//    }
//
//    @Test
//    void insetTest1(){
//        System.out.println(System.currentTimeMillis());
//        InfluxDB influxDB = influxDbUtils.getInfluxDB();
//        //IM_MONITOR为表名
//        influxDB.write(Point.measurement("weather")
//                .time(1603272360, TimeUnit.MILLISECONDS)
//                .addField("timestamp", 1603264680000L)
//                .addField("type", "kLines")
//                .build());
//    }
//}
