注意:

1、base_port要配置港口信息才能爬取，港口的 port_code要与调用传过来的一致，
    比如 传过来的 departurePortEn = port_code 并且 status = 0
    msk_code = msk网站前端可查的英文名，比如 岳阳->YUE YANG   上海->SHANGHAI
    每个网站的code不太一样,岳阳在msk的查询输入必须有空格，但是在one查询输入不能用空格,需要录入时注意
2、船司表 要配置，比如 en_abbreviation = MSK
3、需要给爬虫服务的时间做一个同步，不能时区不同
4、fee_item需要增加  6:基础航运费