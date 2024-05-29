package com.djk.core.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.djk.core.mapper.CrawlRequestStatusMapper;
import com.djk.core.model.*;
import com.djk.core.vo.QueryRouteVo;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.djk.core.config.Constant.BUSINESS_NAME_CRAWL;

/**
 * @author duanjunkai
 * @date 2024/05/01
 */
@Service
@Slf4j
@Data
public class CrawlServiceFroCoscoImpl extends BaseSimpleCrawlService implements CrawlService
{
    @Autowired
    CrawlRequestStatusMapper requestStatusMapper;

    @Override
    public String queryData(BaseShippingCompany shippingCompany, BasePort fromPort, BasePort toPort, QueryRouteVo queryRouteVo) throws Exception
    {
        String hostCode = queryRouteVo.getHostCode();
        this.setHostCode(hostCode);
        if (null != queryRouteVo.getOtherData() && !queryRouteVo.getOtherData().isEmpty()) {
            BaseShippingCompany shipCompany = getShipCompany(hostCode);
            parseData(queryRouteVo, shipCompany);
        }
        return "0";
    }

    private void parseData(QueryRouteVo queryRouteVo, BaseShippingCompany baseShippingCompany) throws ParseException
    {
        JSONObject otherData = queryRouteVo.getOtherData();
        Long id = otherData.getLong("id");
        JSONObject order = otherData.getJSONObject("order");
        JSONArray routeProductPricingList = order.getJSONArray("routeProductPricingList");
        JSONArray fee = otherData.getJSONArray("fee");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductNumber(getProductNumber());
        productInfo.setDeparturePortZh(order.getJSONObject("porCity").getString("cityFullNameCn"));
        productInfo.setDeparturePortEn(order.getJSONObject("porCity").getString("cityFullNameEn"));
        productInfo.setDestinationPortZh(order.getJSONObject("fndCity").getString("cityFullNameCn"));
        productInfo.setDestinationPortEn(order.getJSONObject("fndCity").getString("cityFullNameEn"));
        productInfo.setShippingCompanyId(baseShippingCompany.getId());
        productInfo.setCnFullName(baseShippingCompany.getCnFullName());
        productInfo.setCnAbbreviation(baseShippingCompany.getCnAbbreviation());
        productInfo.setImage(baseShippingCompany.getImage());
        productInfo.setEstimatedDepartureDate(sdf.parse(order.getString("ltd")));
        JSONArray legs = order.getJSONObject("scheduleData").getJSONArray("legs");
        productInfo.setRoute(legs.size() == 1 ? 1 : 2);
        productInfo.setCourse(productInfo.getDeparturePortZh() + " - " + productInfo.getDestinationPortZh());
        productInfo.setArrivalTime(sdf.parse(order.getString("lta")));
        productInfo.setProductExpiryDate(sdf.parse(order.getString("effectiveEndDate").split("T")[0] + " 23:59"));
        try {
            productInfo.setShipName(legs.getJSONObject(0).getJSONObject("vessel").getString("vesselName"));
        } catch (Exception e) {
            productInfo.setShipName("");
        }
        try {
            productInfo.setVoyageNumber(legs.getJSONObject(0).getString("externalVoyageNumber"));
        } catch (Exception e) {
            productInfo.setVoyageNumber("");
        }
        productInfo.setDistance(order.getString("estimatedTransitTimeInDays"));
        productInfo.setCargoType("G");
        productInfo.setProductType("P");
        productInfo.setWaiverOfContainerInstructions("无说明");
        productInfo.setDeficitFreightInstructions("无说明");
        productInfo.setReviewStatus(3);
        productInfo.setStatus((byte) 0);
        productInfo.setCreateTime(new Date());
        productInfo.setUpdateTime(new Date());
        productInfo.setDeleted(false);
        productInfo.setTenantId(0L);
        productInfo.setSpotId(queryRouteVo.getSpotId());
        productInfo.setId(Generator.nextId());
        productInfoMapper.insertSelective(productInfo);
        addLog(true, BUSINESS_NAME_CRAWL, "product_info完成入库", null, queryRouteVo);

        delData(queryRouteVo);

        for (int i = 0; i < routeProductPricingList.size(); i++) {
            JSONObject containerObj = routeProductPricingList.getJSONObject(i);
            String cntrType = containerObj.getString("cntrType");
            ProductContainer productContainer = new ProductContainer();
            productContainer.setContainerType(getContainerType(cntrType));
            productContainer.setProductId(productInfo.getId());
            productContainer.setSpotId(productInfo.getSpotId());
            productContainer.setSellingPrice(containerObj.getBigDecimal("price"));
            productContainer.setCost(productContainer.getSellingPrice());
            productContainer.setFeeCurrency(parseCurrentCy(containerObj.getString("currency")));
            productContainer.setCreateTime(new Date());
            productContainer.setUpdateTime(new Date());
            productContainer.setDeleted(false);
            productContainer.setTenantId(0L);
            productContainer.setShippingCompanyId(productInfo.getShippingCompanyId());
            productContainerMapper.insertSelective(productContainer);
            addLog(true, BUSINESS_NAME_CRAWL, "product_container完成入库", null, queryRouteVo);
        }

        JSONArray chargeDetailBl = null;
        for (int i = 0; i < fee.size(); i++) {
            ProductFeeItem productFeeItem = new ProductFeeItem();
            productFeeItem.setShippingCompanyId(productInfo.getShippingCompanyId());
            productFeeItem.setSpotId(productInfo.getSpotId());
            productFeeItem.setProductId(productInfo.getId());
            productFeeItem.setFeeUnit(1);
            productFeeItem.setFeeSource(1);
            productFeeItem.setCreateTime(new Date());
            productFeeItem.setUpdateTime(new Date());
            productFeeItem.setDeleted(false);
            productFeeItem.setTenantId(0L);

            JSONObject feeObj = fee.getJSONObject(i);
            JSONArray chargeInfo = feeObj.getJSONArray("chargeInfo");
            for (int j = 0; j < chargeInfo.size(); j++) {
                JSONObject chargeInfoItem = chargeInfo.getJSONObject(j);
                String cntrSize = chargeInfoItem.getString("cntrSize");
                int containerType = getContainerType(cntrSize);
                if (containerType > 0) {
                    productFeeItem.setContainerType(containerType);
                    JSONArray chargeDetails = chargeInfoItem.getJSONArray("chargeDetail");
                    for (int k = 0; k < chargeDetails.size(); k++) {
                        JSONObject chargeDetail = chargeDetails.getJSONObject(k);
                        productFeeItem.setPrice(chargeDetail.getBigDecimal("price"));
                        productFeeItem.setFeeCnName(chargeDetail.getString("chargeName"));
                        productFeeItem.setFeeEnName(productFeeItem.getFeeCnName());
                        productFeeItem.setFeeCurrency(parseCurrentCy(chargeDetail.getString("currency")));
                        productFeeItem.setPriceComputeType(0);
                        //费用类型 1海运费，2附加费，3其他费用，4亏仓费，5目的港费用
                        if ("OCEAN".equalsIgnoreCase(chargeDetail.getString("chargeTag"))) {
                            productFeeItem.setFeeCostType(1);
                        } else if ("POR".equalsIgnoreCase(chargeDetail.getString("chargeTag"))) {
                            productFeeItem.setFeeCostType(2);
                        } else if ("FND".equalsIgnoreCase(chargeDetail.getString("chargeTag"))) {
                            productFeeItem.setFeeCostType(3);
                        }
                        productFeeItemMapper.insertSelective(JSONObject.parseObject(JSONObject.toJSONString(productFeeItem), ProductFeeItem.class));
                    }
                } else {
                    chargeDetailBl = chargeInfoItem.getJSONArray("chargeDetail");
                }
            }
        }

        for (int i = 0; i < routeProductPricingList.size(); i++) {
            JSONObject containerObj = routeProductPricingList.getJSONObject(i);
            String cntrType = containerObj.getString("cntrType");
            ProductFeeItem productFeeItem = new ProductFeeItem();
            productFeeItem.setShippingCompanyId(productInfo.getShippingCompanyId());
            productFeeItem.setSpotId(productInfo.getSpotId());
            productFeeItem.setProductId(productInfo.getId());
            productFeeItem.setFeeUnit(1);
            productFeeItem.setFeeSource(1);
            productFeeItem.setCreateTime(new Date());
            productFeeItem.setUpdateTime(new Date());
            productFeeItem.setDeleted(false);
            productFeeItem.setTenantId(0L);
            productFeeItem.setContainerType(getContainerType(cntrType));
            if (null != chargeDetailBl) {
                for (int j = 0; j < chargeDetailBl.size(); j++) {
                    JSONObject chargeDetail = chargeDetailBl.getJSONObject(j);
                    productFeeItem.setPrice(chargeDetail.getBigDecimal("price"));
                    productFeeItem.setFeeCnName(chargeDetail.getString("chargeName"));
                    productFeeItem.setFeeEnName(productFeeItem.getFeeCnName());
                    productFeeItem.setFeeCurrency(parseCurrentCy(chargeDetail.getString("currency")));
                    productFeeItem.setPriceComputeType(1);
                    //费用类型 1海运费，2附加费，3其他费用，4亏仓费，5目的港费用
                    if ("OCEAN".equalsIgnoreCase(chargeDetail.getString("chargeTag"))) {
                        productFeeItem.setFeeCostType(1);
                    } else if ("POR".equalsIgnoreCase(chargeDetail.getString("chargeTag"))) {
                        productFeeItem.setFeeCostType(2);
                    } else if ("FND".equalsIgnoreCase(chargeDetail.getString("chargeTag"))) {
                        productFeeItem.setFeeCostType(3);
                    }
                    productFeeItemMapper.insertSelective(JSONObject.parseObject(JSONObject.toJSONString(productFeeItem), ProductFeeItem.class));
                }
            }

            addLog(true, BUSINESS_NAME_CRAWL, "product_fee_item完成入库", null, queryRouteVo);
            customDao.executeSql("update crawl_request_status set use_time=" + System.currentTimeMillis() + "-start_time where spot_id='" + queryRouteVo.getSpotId() + "' and host_code='" + queryRouteVo.getHostCode() + "' and (use_time is null or use_time='')");
        }
    }

    private int getContainerType(String cntrType)
    {
        if ("20GP".equalsIgnoreCase(cntrType)) {
            return 1;
        } else if ("40GP".equalsIgnoreCase(cntrType)) {
            return 2;
        } else if ("40HQ".equalsIgnoreCase(cntrType)) {
            return 3;
        }
        return 0;
    }

}
