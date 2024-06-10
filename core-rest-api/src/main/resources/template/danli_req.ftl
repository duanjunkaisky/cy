{
  "appid": "${appId}",
  "method": "${method}",
  "url": "${api}",
  "headers": {
  <#list header?keys as key>
    "${key}":"${header[key]!}"<#if key_has_next>,</#if>
  </#list>
  },
  "body": ${jsonParam},
  "timeOut":${timeOut},
  "proxyIp": "${ip}",
  "proxyPort": "${port}",
  "proxyUserPwd": "${password}"
}