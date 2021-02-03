package com.ideayp.leaf.onlyoffice.util;

import com.ideayp.leaf.common.util.FileUtil;
import com.ideayp.leaf.common.util.RequestUtils;
import com.ideayp.leaf.onlyoffice.dto.DocumentType;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

/**
 * <p>email: ypasdf@163.com</p>
 * <p>Copyright: Copyright (c) 2018</p>
 * <P>Date: 2018/12/10 </P>
 *
 * @author leaf
 * @version 1.0
 */
@Slf4j
public class OnlyOfficeUtil {

    /**
     * 通过可访问的url 获取文件的字节
     *
     * @param downloadUri 可访问的url
     * @return 文件字节
     */
    public static byte[] getFileByte(String downloadUri) {
        byte[] fileBytes = null;
        try {
            URL url = new URL(downloadUri);
            log.info("正在下载文件:" + downloadUri);
            java.net.HttpURLConnection connection = (java.net.HttpURLConnection) url.openConnection();
            InputStream stream = connection.getInputStream();
            try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
                int read;
                final byte[] bytes = new byte[1024];
                while ((read = stream.read(bytes)) != -1) {
                    out.write(bytes, 0, read);
                }
                out.flush();
                fileBytes = out.toByteArray();
            }
            connection.disconnect();
        } catch (IOException e) {
            log.error("文件下载失败[{}]:", downloadUri, e);
        }
        return fileBytes;
    }

    /**
     * 通过文件名获取文件类型
     *
     * @param fileName 文件名
     * @return 结果
     */
    public static DocumentType getDocumentType(String fileName) {
        // 获取文件的扩展名 转换为小写
        String ext = "." + FileUtil.getTypePart(fileName).toLowerCase();
        if (ExtsDocument.contains(ext)) {
            return DocumentType.Text;
        } else if (ExtsSpreadsheet.contains(ext)) {
            return DocumentType.Spreadsheet;
        } else if (ExtsPresentation.contains(ext)) {
            return DocumentType.Presentation;
        }
        return DocumentType.Spreadsheet;
    }

    private static List<String> ExtsDocument = Arrays.asList(
            ".doc", ".docx", ".docm",
            ".dot", ".dotx", ".dotm",
            ".odt", ".fodt", ".ott", ".rtf", ".txt",
            ".html", ".htm", ".mht",
            ".pdf", ".djvu", ".fb2", ".epub", ".xps");

    private static List<String> ExtsSpreadsheet = Arrays.asList(
            ".xls", ".xlsx", ".xlsm",
            ".xlt", ".xltx", ".xltm",
            ".ods", ".fods", ".ots", ".csv");

    private static List<String> ExtsPresentation = Arrays.asList(
            ".pps", ".ppsx", ".ppsm",
            ".ppt", ".pptx", ".pptm",
            ".pot", ".potx", ".potm",
            ".odp", ".fodp", ".otp");
}
