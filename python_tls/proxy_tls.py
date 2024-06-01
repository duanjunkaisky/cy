from curl_cffi.requests import RequestsError
from flask import Flask, jsonify, request
from curl_cffi import requests

app = Flask(__name__)


# 添加新项目
@app.route('/py/proxy', methods=['POST'])
def proxy():
    api = request.json['url']
    method = request.json['method']
    headers = request.json['headers']

    timeout = 30
    if "timeOut" in request.json:
        timeout = request.json['timeOut']

    proxies = None
    if "proxyIp" in request.json:
        proxies = {
            'http': 'http://' + request.json['proxyIp'] + ":" + request.json['proxyPort'],
            'https': 'http://' + request.json['proxyIp'] + ":" + request.json['proxyPort']
        }

    ret = {
        "code": -1,
        "data": None
    }

    try:
        if "post" == method.lower():
            response = requests.post(api, headers=headers, impersonate="chrome110", proxies=proxies, timeout=timeout)
        else:
            response = requests.get(api, headers=headers, impersonate="chrome110", proxies=proxies, timeout=timeout)
        ret = {
            "code": response.status_code,
            "data": response.text
        }
    except RequestsError as e:
        print(e)
    return ret


if __name__ == '__main__':
    app.run(host='0.0.0.0', port=8899, debug=True)
