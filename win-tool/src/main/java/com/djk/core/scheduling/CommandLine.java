package com.djk.core.scheduling;

import org.apache.commons.io.FileUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author duanjunkai
 * @date 2024/05/25
 */
@Component
public class CommandLine {
    public static final String CHROME_PATH = "C:\\Program Files\\Google\\Chrome\\Application";
    public static final String CHROME_PLUGIN_PATH = "D:\\chrome_plugin";
    public static final String CHROME_STARTUP_TIME = "05:00:00";
    public static final String CHROME_SHUTDOWN_TIME = "17:34:00";

    public static boolean hasShutDown = false;
    public static boolean hasStartup = false;

    /**
     *
     */
    @Scheduled(cron = "0/5 * * * * ?")
    private void checkChrome() throws Exception {
        long currentTimeMillis = System.currentTimeMillis();
        SimpleDateFormat sdfDay = new SimpleDateFormat("yyyy-MM-dd");
        String format = sdfDay.format(new Date());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date shutDownDate = sdf.parse(format + " " + CHROME_SHUTDOWN_TIME);
        String target = "chrome.exe";
        if (currentTimeMillis > shutDownDate.getTime() && !hasShutDown) {
            killTask(target);
            hasShutDown = true;
            hasStartup = false;
        }
        Date startUpDate = sdf.parse(format + " " + CHROME_STARTUP_TIME);
        if (currentTimeMillis > startUpDate.getTime() && !hasStartup) {
            hasStartup = true;
            hasShutDown = false;
            //startUp
            try {
                List<String> urlList = new ArrayList<>();
                try {
                    FileUtils.forceMkdir(new File(CHROME_PLUGIN_PATH));
                    BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(CHROME_PLUGIN_PATH + "\\config.txt")));
                    String urlLine = null;
                    while ((urlLine = br.readLine()) != null) {
                        urlList.add(urlLine);
                    }
                    br.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (urlList.isEmpty()) {
                    return;
                }

                killTask(target);
                for (int i = 0; i < urlList.size(); i++) {
                    String url = urlList.get(i);
                    Process process = Runtime.getRuntime().exec(
                            CHROME_PATH + "\\chrome.exe --remote-allow-file-access-from-files --user-data-dir=\\\"D:\\\\chrome_plugin\\\\temp\\\" --start-maximized");
                    process.getOutputStream().write(("cmd /c start chrome " + url).getBytes());
                    process.getOutputStream().flush();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void killTask(String target) {
        String line;
        //shutDown
        try {
            boolean processRunning = isProcessRunning(target);
            if (processRunning) {
                Process taskkillProcess = Runtime.getRuntime().exec("taskkill /IM " + target + " /F");
                BufferedReader taskkillReader = new BufferedReader(new InputStreamReader(taskkillProcess.getInputStream()));
                while ((line = taskkillReader.readLine()) != null) {
                    System.out.println(line);
                }
                taskkillReader.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean isProcessRunning(String processName) {
        try {
            String taskLineCommand = "tasklist.exe";
            Process process = new ProcessBuilder(taskLineCommand).start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains(processName)) {
                    return true;
                }
            }

            process.waitFor();
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
