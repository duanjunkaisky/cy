{
  "appid": "${appId}",
  "method": "${method}",
  "url": "${api}",
  "headers": {
    "akamai-bm-telemetry":"${telemetry}",
    "authorization":"${authorization}",
    "consumer-key":"${consumerKey}",
    "content-type":"application/json",
    "Accept-Encoding":"gzip, deflate, br, zstd",
    "Accept-Language":"zh-CN,zh;q=0.9",
    "Origin":"https://www.maersk.com.cn/book"
  },
  "userAgent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/110.0.0.0 Safari/537.36",
  "body": "${jsonParam}",
  "timeOut":${timeOut},
  "proxyIp": "${ip}",
  "proxyPort": "${port}",
  "proxyUserPwd": "${password}"
}