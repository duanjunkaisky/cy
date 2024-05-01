package com.djk.core.utils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author duanjunkai
 * @date 2024/05/01
 */
public class ZipUtil {

    public static void main(String[] args) throws Exception {
        Map<String,String> files = new HashMap<>();
        files.put("111.gif", "https://img-home.csdnimg.cn/images/20211108111828.gif");
        files.put("222.png", "https://csdnimg.cn/release/blogv2/dist/pc/img/original.png");


        ZipUtil.zipPersonPhotoFile(files,new ZipOutputStream(new FileOutputStream(new File("D:\\111.zip"))));
    }

    /**
     * 压缩文件
     * @param files 键值对-》（文件名：文件链接）
     * @param outputStream
     * @throws Exception
     * @throws IOException
     */
    public static void zipPersonPhotoFile(Map<String,String> files, ZipOutputStream outputStream) {
        try {
            Set<Entry<String, String>> entrySet = files.entrySet();
            for (Entry<String, String> file:entrySet) {
                try {
                    zipFile(getImgIs(file.getValue()),file.getKey(), outputStream);
                } catch (Exception e) {
                    continue;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 将文件写入到zip文件中
     * @param is
     * @param fileName
     * @param outputted
     * @throws Exception
     */
    public static void zipFile(InputStream is, String fileName, ZipOutputStream outputted) throws IOException, ServletException
    {
        try {
            if (is != null) {
                BufferedInputStream bInStream = new BufferedInputStream(is);
                ZipEntry entry = new ZipEntry(fileName);
                outputted.putNextEntry(entry);
                int len = 0 ;
                byte[] buffer = new byte[10 * 1024];
                while ((len = is.read(buffer)) > 0) {
                    outputted.write(buffer, 0, len);
                    outputted.flush();
                }
                outputted.closeEntry();//Closes the current ZIP entry and positions the stream for writing the next entry
                bInStream.close();//关闭
                is.close();
            } else {
                throw new ServletException("文件不存在！");
            }
        } catch (IOException e) {
            throw e;
        }
    }

    /**
     * 获取文件流
     */
    public static InputStream getImgIs(String imgURL) throws IOException{
        //new一个URL对象
        URL url = new URL(imgURL);
        //打开链接
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        //设置请求方式为"GET"
        conn.setRequestMethod("GET");
        //超时响应时间为5秒
        conn.setConnectTimeout(5 * 1000);
        //通过输入流获取图片数据
        return conn.getInputStream();
    }

    /**
     * 下载打包的文件
     *
     * @param file
     * @param response
     */
    public static void downloadZip(File file, HttpServletResponse response) {
        try {
            // 以流的形式下载文件。
            BufferedInputStream fis = new BufferedInputStream(new FileInputStream(file.getPath()));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            // 清空response
            response.reset();
            OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment;filename=" + file.getName());
            toClient.write(buffer);
            toClient.flush();
            toClient.close();
            file.delete();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}