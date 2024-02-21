package top.kirisamemarisa.onebotspring.utils;

import java.io.File;
import java.io.FileInputStream;

public class FileUtil {

    /**
     * 获取文件的内容（字符串形式）
     * <p>这个方法只建议拿来读一些小的、内容是文本的文件</p>
     *
     * @param path 文件地址
     * @return .
     */
    public static String getContentString(String path) {
        File file = new File(path);
        try (FileInputStream fis = new FileInputStream(file)) {
            int available = fis.available();
            byte[] bytes = new byte[available];
            int read = fis.read(bytes);
            if (read < 0) return null;
            return new String(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
