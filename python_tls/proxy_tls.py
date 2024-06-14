from curl_cffi.requests import RequestsError
from flask import Flask, jsonify, request
from curl_cffi import requests

app = Flask(__name__)


# tls代理请求
@app.route('/py/proxy', methods=['POST'])
def proxy():
    api = request.json['url']
    method = request.json['method']
    headers = request.json['headers']
    data = request.json['body']

    timeout = 30
    if "timeOut" in request.json:
        timeout = request.json['timeOut']

    proxies = None
    if "proxyIp" in request.json:
        proxies = {
            'http': 'http://' + request.json['proxyUserPwd']
                    + "@" + request.json['proxyIp'] + ":" + request.json['proxyPort'],
            'https': 'http://' + request.json['proxyUserPwd']
                     + "@" + request.json['proxyIp'] + ":" + request.json['proxyPort']
        }

    ret = {
        "succ": False,
        "data": None
    }

    response = {
        "status_code": 404,
        "text": "未匹配methon: " + method
    }

    try:
        if "post" == method.lower():
            response = requests.post(api, headers=headers, json=data, impersonate="chrome110", proxies=proxies,
                                     timeout=timeout)
        elif "get" == method.lower():
            response = requests.get(api, headers=headers, impersonate="chrome110", proxies=proxies,
                                    timeout=timeout)
        elif "put" == method.lower():
            response = requests.put(api, headers=headers, json=data, impersonate="chrome110", proxies=proxies,
                                    timeout=timeout)
        elif "options" == method.lower():
            response = requests.options(api, headers=headers, json=data, impersonate="chrome110", proxies=proxies,
                                        timeout=timeout)
        ret = {
            "succ": True if (
                    response.status_code == 200 or response.status_code == 201 or response.status_code == 202 or response.status_code == 204) else False,
            "data": response.text
        }

    #         print(ret)

    except RequestsError as e:
        #         print("error")
        print(e)
        ret["data"] = e.args[0]
    return ret


if __name__ == '__main__':
    app.run(host='0.0.0.0', port=8899, debug=False)
