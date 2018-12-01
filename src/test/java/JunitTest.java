import com.zeromk.study.util.DbUtil;
import com.zeromk.study.util.TarGzUtil;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * 测试
 * @author cbx
 * @date 2018/10/5
 **/
//@ExtendWith(SpringExtension.class)
public class JunitTest {

    private static final Logger logger = LoggerFactory.getLogger(JunitTest.class);

    @Test
    public void zipTest(){
        // zip压缩
        /*String sourcePath = "/data/jsp/springTest/logs/";
        String targePath = "/data/jsp/springTest/logs.zip";
        ZipUtil.createZip(sourcePath,targePath);*/

        // zip解压
        /*String sourcePath = "/data/jsp/springTest/logs.zip";
        String targetPath = "/data/jsp/springTest/uzlogs";
        ZipUtil.unZip(sourcePath,targetPath);*/


        // tar压缩
        /*String sourcePath = "/data/jsp/springTest/logs/";
        String targetPath = "/data/jsp/springTest/logs.tar";
        TarGzUtil.tarFile(sourcePath,targetPath);*/

        // tara解压
        /*String sourcePath = "/data/jsp/springTest/logs.tar";
        String targetPath = "/data/jsp/springTest/untarLogs/";
        TarGzUtil.unTarFile(sourcePath,targetPath);*/

        // gz压缩
        /*String sourcePath = "/data/jsp/springTest/logs.tar";
        String targetPath = "/data/jsp/springTest/logs.tar.gz";
        TarGzUtil.gzFile(sourcePath,targetPath);*/

        // gz解压
        String sourcePath = "/data/jsp/springTest/logs.tar.gz";
        String targetPath = "/data/jsp/";
        TarGzUtil.unGzFile(sourcePath,targetPath);

    }

    @Test
    public void testDbUtil(){
        String sql = "select * from study_login";
        Connection connection = DbUtil.getConnection();
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                System.out.println(resultSet.getInt(1));
                System.out.println(resultSet.getString(2));
                System.out.println(resultSet.getString(3));
            }
        }catch (Exception e){
            logger.error("testDbUtil exception", e);
        }


    }

}
