package com.djk.core.utils;

import cn.hutool.core.exceptions.ExceptionUtil;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.ExceptionUtils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.Map;

/**
 * @author duanjunkai
 * @date 2024/05/01
 */
@Slf4j
public class FreeMakerUtil
{
    /**
     * @param templateFileName
     * @param data
     * @return {@link String}
     */
    @SneakyThrows
    public static String createByTemplate(String templateFileName, Map<String, Object> data)
    {
        try {
            InputStream inputStream = FreeMakerUtil.class.getResourceAsStream("/template/" + templateFileName);
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            StringBuffer sb = new StringBuffer();
            String line = null;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }

            Configuration configuration = new Configuration(Configuration.VERSION_2_3_30);
            configuration.setTemplateLoader(new StringTemplateLoader());
            configuration.setDefaultEncoding("UTF-8");

            Template template = new Template(templateFileName, sb.toString(), configuration);
            StringWriter writer = new StringWriter();
            template.process(data, writer);

            return writer.toString();
        } catch (Exception e) {
            log.error(ExceptionUtil.getMessage(e));
            log.error(ExceptionUtil.stacktraceToString(e));
            throw new RuntimeException(e);
        }
    }
}
