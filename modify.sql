alter table crawl_product_fee_item add column price_compute_type int(1) default 0 comment '单价计算方式：0:有多个箱子时需要乘以个数,1:固定单价,与箱子个数无关';
alter table crawl_request_status drop column data_count;