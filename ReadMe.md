注意:

alter table crawl_product_fee_item add column price_compute_type int(1) default 0 comment '单价计算方式：0:有多个箱子时需要乘以个数,1:固定单价,与箱子个数无关';
alter table crawl_request_status drop column data_count;

1、base_port要配置港口信息才能爬取，港口的 port_code要与调用传过来的一致，
    比如 传过来的 departurePortEn = port_code 并且 status = 0
    msk_code = msk网站前端可查的英文名，比如 岳阳->YUE YANG   上海->SHANGHAI
    每个网站的code不太一样,岳阳在msk的查询输入必须有空格，但是在one查询输入不能用空格,需要录入时注意
2、船司表 要配置，比如 en_abbreviation = MSK