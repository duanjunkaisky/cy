package com.djk.core.service;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.djk.core.mapper.CrawlMetadataWebsiteConfigMapper;
import com.djk.core.model.CrawlMetadataWebsiteConfig;
import com.djk.core.model.CrawlMetadataWebsiteConfigExample;
import com.djk.core.utils.FreeMakerUtil;
import com.djk.core.utils.HttpResp;
import com.djk.core.utils.HttpUtil;
import com.djk.core.vo.QueryRouteVo;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author duanjunkai
 * @date 2024/05/01
 */
@Service
public class CrawlServiceFroMskImpl implements CrawlService
{
    @Autowired
    CrawlMetadataWebsiteConfigMapper crawlMetadataWebsiteConfigMapper;

    @Override
    public String queryData(QueryRouteVo queryRouteVo) throws InterruptedException
    {
        Map<String, Object> data = new HashMap<>(1);
        data.put("fromPortId", "2IW9P6J7XAW72");
        data.put("fromPortCode", "CNSGH");
        data.put("fromPortCountry", "CN");
        data.put("fromPortFullName", "Shanghai (Shanghai), China");

        data.put("toPortId", "1JUKNJGWHQBNJ");
        data.put("toPortCode", "NLROT");
        data.put("toPortCountry", "NL");
        data.put("toPortFullName", "Rotterdam (Zuid-Holland), Netherlands");

        data.put("containerCode", "22G1");
        data.put("containerSize", "20");
        data.put("containerType", "DRY");

        data.put("queryDate", "2024-05-07");
        data.put("weekOffset", 0);
        String jsonParam = FreeMakerUtil.createByTemplate("mskQuery.ftl", data);

        CrawlMetadataWebsiteConfigExample crawlMetadataWebsiteConfigExample = new CrawlMetadataWebsiteConfigExample();
        crawlMetadataWebsiteConfigExample.createCriteria().andHostCodeEqualTo("msk");
        List<CrawlMetadataWebsiteConfig> crawlMetadataWebsiteConfigs = crawlMetadataWebsiteConfigMapper.selectByExampleWithBLOBs(crawlMetadataWebsiteConfigExample);
        int tokenIndex = 0;
        CrawlMetadataWebsiteConfig crawlMetadataWebsiteConfig = crawlMetadataWebsiteConfigs.get(tokenIndex);
        String token = crawlMetadataWebsiteConfig.getToken();

        Map<String, String> header = new HashMap<>(4);

        getRemoteSensorData(token, header);

        HttpResp resp = HttpUtil.postBody("https://api.maersk.com/productoffer/v2/productoffers", header, jsonParam);
        System.out.println(resp.getBodyJson());
        TimeUnit.SECONDS.sleep(20L);
        return null;
    }

    public void getRemoteSensorData(String token, Map<String, String> header)
    {
        JSONObject tokenBean = JSONUtil.parseObj(token);
        String abck = tokenBean.getStr("_abck");
        String bmsz = tokenBean.getStr("bm_sz");
        Map<String, String> sensorDataParams = new HashMap<>();
        sensorDataParams.put("appid", "eyqq4t1ubp4fbjklkrguol6zcc8o5jp5");
        sensorDataParams.put("siteUrl", "https://www.maersk.com.cn/book");
        sensorDataParams.put("abck", abck);
        sensorDataParams.put("bmsz", bmsz);
        HttpResp resp = HttpUtil.postBody("http://api.zjdanli.com/akamai/v2/sensorData", null, JSONUtil.toJsonStr(sensorDataParams));
        JSONObject retObj = JSONUtil.parseObj(resp.getBodyJson());
        String sensorData = retObj.getStr("sensorData");
        String sd = Base64.getEncoder().encodeToString(sensorData.getBytes());
        String str = tokenBean.getStr("akamai-bm-telemetry");
        String start = str.split("sensor_data=")[0];
        String akaSign = start + "sensor_data=" + sd;

        header.put("Authorization", tokenBean.getStr("authorization"));
        header.put("Content-Type", "application/json");
        header.put("User-Agent", retObj.getStr("ua"));
        header.put("Consumer-Key", tokenBean.getStr("consumer-key"));
        header.put("Akamai-Bm-Telemetry", akaSign);

    }

    public static void main(String[] args)
    {
        String token = "{\"_abck\":\"1A9BBF852612B8CE2A49A24F1CAE7B67~0~YAAQJ4yUG0zusySPAQAAfkNNLgv+QOCmPCQVgIwqGe2k6g1U7QRHTNGd6GYLgZ0lG+9zPsX53reXXUZ7TYf/ZN5uggrwpdA/l9Oy9PvryyfrvhdnVH+gKy43mb+xjVAdPnVAYoxmJLFYUGaz1TzvPfBCCzTv7V/9kUkLcDi4ChwVONpEj9VOEkT3rwqcr+XmvmFjCxa6dCyX1rR1wdgBNdIOH1KFB/nvJe5LvMiOaUGgungx1+eMiFOI8mFXEeKk8B2WYxUq4liBNsYM1kozfm+zlKY6MN8gvX9KoGL4CnMtsaVLLhUs1aTAp4V4YUtQHOk1hW7E1OKmvl98Sh006uG65nDYUrB+e134NHfzmT6VxAMCJJbbaOSEZlx5wh6y/Q==~-1~-1~1714460467\",\"bm_sz\":\"68C5B0AA42DEA60D4807C70F1A5CEF39~YAAQJ4yUGwzusySPAQAATj5NLheVRbA6p7uVldUKgtmSwTOrVfK4QYSErKBMyTamRdNYzirmJWzWp6+HpZFMXcflwyU7pumijz+soNYS5DXrh6EjXrIXaeH69tcAkD8i1qqz8RSCscvQcv4cYaJWnimMPRpghbRwbG5MAfzWJLmNFj1zrgH1kDbfvqNAhtxmgy2kcYoPVypt0hNvTp/VxyDytnzwmX/FB/nuu+JF9AijLOCYgi1hW1ce3bN10mgGaKrqFbh4bu3EQaiUsj+QUG+l6Mtg1wfoF8y+3Vy4uoKMtD3mNBYNoqGFowZz9Br+QpkSNldULgY6J0WQUksqfNSG9GquDy68HHE+cOeoqsm5VDN+tsW2H7SsMJJ6HdKqoq6Kgt17G1oyLfmw27y5WZMinSqR0xhxzqZ4GZ3cKCUaKpUhQZRC8Oz/eutY1OimGbdI2MpOKxwCriET6WUCp2InA3SQpRwRkxBzVdl7m0dh2SPo37lOtVUzSL/1zNR+lQwvGbyEl0ih+shgJFfHcVCaJebVX3JIPYxlDnD5dxbXAWuCFQS9AZsxWEJcRj4s4qdC8u3UvuM=~3618356~3683393\",\"authorization\":\"Bearer eyJ0eXAiOiJKV1QiLCJraWQiOiJPK0FZTDdiY083WlNLL2NjdFRjOHFrSStIemc9IiwiYWxnIjoiUlMyNTYifQ.eyJhdF9oYXNoIjoiVmhHLV9oYjJOd0ZPbTRneEptZEhOQSIsInN1YiI6InNodXFpbmxpIiwiZmlyc3RuYW1lIjoic2h1cWluIiwiYXVkaXRUcmFja2luZ0lkIjoiNDFmNmViZGEtNDdiYi00M2QwLWE3NzQtMzQ2MTEzNWU2OGNiLTU1MTE2NjQxIiwicm9sZXMiOlsiQmFzaWNDdXN0b21lciIsIlBheW1lbnRzIiwiV0JPTEFwcHJvdmVyIiwiQ29udHJhY3RSYXRlIiwiSW52b2ljZXMiLCJCb29raW5nIiwiRG9jdW1lbnRhdGlvbiJdLCJpc3MiOiJodHRwczovL2lhbS5tYWVyc2suY29tL2FjbS9vYXV0aDIvbWF1IiwidG9rZW5OYW1lIjoiaWRfdG9rZW4iLCJvZmZpY2UiOiJOYW5qaW5nIC0gQ04iLCJsYXN0bmFtZSI6ImxpIiwiYXVkIjoiYmNhMDAxIiwiYWNyIjoiMCIsImNhcnJpZXIiOiJNQUVVIiwiYXpwIjoiYmNhMDAxIiwiYXV0aF90aW1lIjoxNzE0NDQ5NjU5LCJ1c2VyVXVpZCI6IjAwZjczMTQ5LTgwNDctNDcxYS04MTkzLTEwOGExNzMxMGFhNCIsInJlYWxtIjoiL21hdSIsInBlcnNvbmlkIjoiNDA2MDUxMzQzMjgiLCJleHAiOjE3MTQ1MzY0OTMsInRva2VuVHlwZSI6IkpXVFRva2VuIiwiaWF0IjoxNzE0NTI5MjkzLCJjdXN0b21lcl9jb2RlIjoiNDA2MDI5MDY2MzkiLCJlbWFpbCI6ImNzMDEubmpnQGhpZ2h3YXlsb2dpc3RpY3MuY29tLmNuIiwidXNlcm5hbWUiOiJzaHVxaW5saSJ9.zFzbcO55rpC7_vQykTSVILgjdELAe8P5ldAji_w736NigwasFOoO2W_0ueuKV2t39RZzXIxX2Rc2KV6ipsYhTRJumSKe_kYP4SHqr9RVUAPzfUjo5cil3lkbFYqNkTX2vpqZhUDr5bVtCcYXdeGzzpl4xYxTw-lUdBQ2j6iZI0fiABQEAjfexuSZcnbWw8oq9fQU7numef-BGLWMUcaQZXlCKURWnesH5x00TXFveUdrxIZ7LCuy7CCj1Ipz00_OkPkreIJ6fU1JTrMCInMqXhsF6raTsnE6NNaX7BsedIj4MSh2GcGtMIcbIaanvIeVfN-dxOPjx6qIq4FlZJzCJ43Q7tbxjRJdX5hrnjeLAHEw7RiJx81kzxIuQsesP7ZFh2Gx8dOZUCYpD8JBbgg02-ko5pRiCw7Y6eOlW8Y2JyprNuhy9GNUGnmfr5lhWPB7iYgjLuUIb7QlRuQIGZBQjR7dz1YglSC5IL0GcSDQcdl651PkPS_nrbNHpg7UsoWJ\",\"akamai-bm-telemetry\":\"a=1A9BBF852612B8CE2A49A24F1CAE7B67&&&e=RUU4M0VCQzk4NzlCNEZEREJBQTcwMjk2MkJCMDUyMUV+WUFBUXRXMmJHNkdmTmVlT0FRQUFzRzhVTWhmcUZRZkQ1WEkyRWtBbXlQTnJVN3JVNWxRMktKMG1WQlB5ZWVydXpiTXRsdmlCQlpOZTNjTmQ2cVZjanBabDQwZEE3TTRlWDBJWk0zUk5vcVJJcklKNzgyaWM4cWJNRXNKOEduNTZIbzdqKzlCc0JrTmhkdHV1YnNwazRJTFZBZHVUYWp0bVpIekI5WnlyLzFjTUxZeUF6eUVzL1hCWW04WVhsR3BPU1VVcHlpQ3dFTXJSMDM4WlFvWnlPRDRMOTRWOTIyY3JZcVl1d2dPbC9KQnovajlJbVNiTklwclVDVEpxaTI2eENXQVZaWDVvbU0wYThDZFl1dFpmd1JRLzJDcFdoblpTNmZaZUxKRFBzWjE1azV1V0R0SktoRFdJa2gxUk1zUURUQW4zclBSY0R2d2tVZTFKcE8yRTdRcUYwMXA3eHcvVlJmdzVpbVd1TWJYcERqR0diK3pVQkFFNXJxMUR3dmNpNkRxKzRyVm5oQlp3NEx3OG5zWTdCRHZONW03dHFvQmVEUUdHQkY2aTdTLzhaUUdGOWRGTFBFOG50RHpKakhoRWQ3cUlwWTBmMUZkMFh4YjFQY25QUXJzTnEwQmpPZDR4ZTRoS2FpOWpLM3VkdEtWM3dEMit2ME54dnc3NnREMWY3WVdGeWplZGlJRndncFN0QW5Qd3ZOSUhCazdmWUJGZ0xldmFHcmVmeUxPM3FyY3FOVWZMaVpMdFI1WkZJYTlHU3ExUlB2NGtaYmtWU1E9PX4zMzU2NzM5fjMyODkxNTg=&&&sensor_data=MjszMzU2NzM5OzMyODkxNTg7OSwxLDAsMCwyLDE4O2M8YzlKeCAwLG4saGM/Tz48UjdeLHYhLnVBYF1GWktCWDVZNjU2Qk1RSmZmJm1xeExMeC1GQiMrdittanZfIFVqRmV+Pz5DaGNTZWBMQU5hIzxoeVNPZF5RJlhXU3UsXj1sYGtKdjdRRHBMcHdYe1dFVi9QKS5efCUjZ3FwPVsjKitxfEYxQ2MlKVEjQHMxRG8kYiNDbT9VZTpyIH5QYFNPQHx4S3AycHJ3az0/QGB+YytSckBzT2ZESz9PaUEgbUkpMCA6T3tFWm9UNUlSIDZsQyhWKjZ+eTpyXn4qYi9qZ1ZKSCh3SSlFT2xZJEh7M2VYMEROd0Y4cF1JYHVXJmBsaGQ1VUREMHBmTjgkbWg9UVdJeWZjey1HLH5JYXBydGxCZGkvJTRHZWIjRjR9Wl92fk4jQ0UudTkjST4qVD83MmRdZ1RlQzpGd2pIY1YsZ2M/OnZ7TTFHc3poPWdWLzk8JHpBW2wlIENkQXg+YkFSeGZsRWo4Nl12KGg0aURvaGs1MEhMPCklO0d5bkkoZnRrUmFHVDQgRUx+KFdtZk5jdVdffnIzMzFmLUpQLUtBKC5eaikwbWU3ZHQqYjFxNE5YTi5MU1pETC4pMkgoWU9sTUpWSE1LSXpXYSBiNiVCXUM8T3N9QERNKlIxNCBXTD5XS2xqd2RvRCs5aEYhMzZacEJWKzljbSBnR2B+YEdBfTY5KnQrLDdxViAxbn18N1JtPGNzVH0pNX1vO2VOfGkrSEl0KzQ8QTl6RHRRTGFqR2U/Zi1CNjlMNjpiLHBlcHY5SmpgQT1YPmZjLXZ6NjVDPk4mMFMmZ1RXT3p4cWJFV0FSVUJiQVk7QWdHQjkobVNGUll3dnw8UzIpNi1fUThvel8wTUJSU29qSj1MR0pkajouSVR+Wn5GTVAyalpefi9lMV0hWGgpKSVnPUNJJThrZEMuK0s/WS88OCRbbnQqRHJJfkcoPEtrbFYyPFB6PT18bVA1Y2wySH4pfClCPnN6dXB8PktbLFMxfEdtL34mek12ZF83MWU9TWdJclBYI3kuY0NiY3c+Q0lsTWNaOWFYT3NBbEhEdlN3NHdlLD5qbitTQ3djO2sgOSwpM1k1eGdhb1VhUlYjbCNVRDVWa0omcE9EdGk5bGwtcUNddmJYTkMyUmFCLjV4MyBBOVNvbHg/eUYmdSEzdCsyJFV1dWwpeCwlNj8yKnVBayApS2Y8cS87S3VwZW1DXllFd0UkQ289Rz9ZTjE9ZjZldH5JYmphW0xMbHdwMld+KFAxaFh+aV4lcktRP1tRJltkc0dvZkdQRUhBTzMkYiotTiNrXVRwcS9NbW42NTBhS09nP3lxZVlOa2RtWDE0a3wxcUE5WGQgS2QuNlNSVEx2b0kvYTFYYmNseiR9eVY7RGdTLjlFYEtQaCpPaGQkckZefGVGTVFTLX4oNkNLJXEtJXN9XW59PTpWJklufDtwb2dTNTwwWzpLOVZzL3ktOzZdUDRSWkJ3QXY5IUZAcDVjLFtxVzp9PFsjdFkxTCRwOmttTXV9XmtoMGYraHw/NCRMY3xsKSlKajVHQnAveyp+TEJJZy88MV9iQHBhS2xialIya2psPXAzVm5KTWltajhBdW4zVThmMyZ6SDA/TSB3TSgkMERSO0klQ3U0SU9OJl9mckhbJn5yRTh2WSZKRz1FeSB4QmA4WDltO0t8dSprRVdoPE80WSlWUClvSSUoMH1jc3BxWzhZSXA4cTNAUjt+MDlbUzlxbl1iRTZjKFFmSUdMZz1bMltDIGN8a2MqbUlzIGBGKXImWXJUTHJoaTFnYGZVcjs4Lmp3Kz1oa2o6P2djRCFuXywuaWJiUUV7KFhuQTNrdnx5bXcqWzYhfk0/cSk1ZFRNLVF6NmZMSGhsT3hBOVRSPHdZeG05NVRuWSs8Pm84bWU3ZHc0MkZocEhzRitSME9GcFAjXi1TSWdCZ2xNXUJZLjElcl8reD9BZGBfL1BVdl8tU1Z+Pz4+QnNZIGB4Qmo/dEs+TEpAO21aWGhOWXEzLyEtbn1RYXBOcT55N1QuOVcpPkllZF49Q2FFQU9CIFtsUVJxbElTSC8raV1dJHBSQ28tZCYpfWVkPSEjYHp7PEpDLG9ULnc8bSkuSnloWjlseD9NbXs+LTEge3JIVD9xMTwtJnNCcWJyVGYyPk0vVXhHamFHJnN+QjJPWG5YTCtJbDgxKUNQeyR+US5YXkt1PyBLaVZyRV4qOUV8KEVJZS8qXSMufWgyYUN5UC9sPV56TWFAcSMxOjhoKjApenIyVF8gOz16aXQ0ISlIISR9aDtHcHhwWlpvOylAbFdGTHpKQ3s4WXY8fCNjLzwyYSFXLXxDdG5KWigtOnNOTUdjd1JoSFA0RWdpYkZ5eSBrRSV5NlgyL2EvXmNvQiwxNVdvO0VObTpfJCE0a1VhZDtCUCpUXVo0a15kc3hiXStkN3lUcT0xOEMkITFwZH5CfEshdHBJWEEpYThSbFAgNF9OJFUreG5lckRHUzxoWTtNb1E5LlAhMipidl15VCxnc0dvWzcpU0FxTFZ6ZC56LnRKST08LTN5I055YndxaHlxIToycFZzNF05WnFdLSxCJmhmW2xUa21YKzFOdXRtLSk4WjI/LnI+KFdGWV5UIEQwdzFzMDZOOjVackZoc3IlUjl1alNTNjJ0TnZKUlR0dzcrJSxIQjZhblEzI0Q1WS5DRX1uWm9PNEAmSTYrKmg3KSEzODxgdl57Zmg/Ny81Wi52cFJGSklPM0ZILUBifGB6QmpwL2B7NEMmTXs6cmgqLCZfbFtIcX5nQT9xYTxvN1tScCA+ZGBBKHwxYyFiciwlX1M1KWx2fXE+dF13WmgjLVpONDNQRzUlTG54Nn1qTlc3TzUxU1hMNXNZMlVoUUZ7ZCVtcSZkfFJJYEw2VHJgO01VX1Y9MzBhWDw5X3FdY3AlQ1hAW0NnLjlUKUZeI2phb2ouQ1ptKS5ALn1HbG9dJXtYJF4sTkBdeT40NU5ZQ2N5LnhZVHFob055QD8wYih4NjZrdE0vaTJ9T2Y3OjtyL3pTIXFAWlczJEhDeHp1dXMzZjkoZ353K3tBRHYjTTNbaytJb1NbbStpbnh9fCQ1YiVhZV4+WmN1M2pqPSQuLDlVRXA4eGV4ajEpaHRQLm1ENk4vRVgoYk9HIFNAd1RkazhWLSo+NkZfMT5IfWswL2lHJWpjRkQjZ1cjQ1A3ajQhSldpPiRdPFtyd3xHfUBZLmBNSj4hLXJqRCAzSUQpS0otUDxuWC95RyYpSEcmTmJ8fHpbfGlsT141fS1NYG01RSlETFtzOVJ3ZDN5e2Mva2ImcyssdEkreiE5ZXJxL3dqQUYudERHazEmNmFKYi9tYjYuLVEvX1NUQzpxNS5+PyQobFs5TlRnL3MpQkM2NyRPKCx9LGNFcSQ+YjpXY2k/bEMrLDtMPyB8fmRsajZoeUArfj9XQiwkMnpeVzMzOVZHKlQpYlJvcExHbn1Vbjxvd2lrNFBgLSBFNTBnSl1zP0lzZWNlbGBHWE8gZXMlbUpKRl5mOU9vKzc7TytvdzZrPUE7STtiUElaVSxyKEh3XXRzNDtIajVMe1J6YVFtQXhxRzxOUGwsR0hBe2ohPDRxc04tS3tCbUh3JkF2aDVBK3gkVUBIVG8oJjJyNHpEM105RHYrW2dtIDZqcG1YKUo7dGs0TWB+ME1WclImbCo0fD5Re0AkRDZIJnFbTDlnMy0kMzNacDJTdno1fH1MbHR0NkVhSTFfQmwwK1lbSUlRfXV9ZWd7S2k2Pj1TRCRIU1RwMXE2c25HWGQpeXhGJFd+T0ApVW9ROjt9SmJsSCosLykhLjRXYFN9cDNhXWl4KiteT2I1ODluOlN7KXszQiEwKlA9SHZ5e3c0cTdSM2E+TFclRXA+YFYrQWEuJmxNbmsyfmhTSChtSDE4am5vfk9mc0dnQHlab3U4amJEbGhvMFhFc1RyYW5LR0NZQF1IZn4rKzd0fWI6e3wrOSBtKiQ8UVdCOEsyPHx6Y3g0UT9iIzQ3bVE0LD4rTChEZ1pSRDFOXiN+UHVMIVE0\",\"consumer-key\":\"4d3yfgADGPhccLLfl1w1QhefwCdhzcFj\"}";
        Map<String, String> header = new HashMap<>();
        new CrawlServiceFroMskImpl().getRemoteSensorData(token, header);
        System.out.println(header);
    }
}
