import com.zeromk.study.util.TarGzUtil;
import org.junit.jupiter.api.Test;

/**
 * 测试
 * @author cbx
 * @date 2018/10/5
 **/
//@ExtendWith(SpringExtension.class)
public class JunitTest {

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

}
