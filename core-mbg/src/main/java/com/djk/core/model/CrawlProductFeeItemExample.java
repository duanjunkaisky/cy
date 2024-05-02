package com.djk.core.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CrawlProductFeeItemExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public CrawlProductFeeItemExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Long value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andProductIdIsNull() {
            addCriterion("product_id is null");
            return (Criteria) this;
        }

        public Criteria andProductIdIsNotNull() {
            addCriterion("product_id is not null");
            return (Criteria) this;
        }

        public Criteria andProductIdEqualTo(Long value) {
            addCriterion("product_id =", value, "productId");
            return (Criteria) this;
        }

        public Criteria andProductIdNotEqualTo(Long value) {
            addCriterion("product_id <>", value, "productId");
            return (Criteria) this;
        }

        public Criteria andProductIdGreaterThan(Long value) {
            addCriterion("product_id >", value, "productId");
            return (Criteria) this;
        }

        public Criteria andProductIdGreaterThanOrEqualTo(Long value) {
            addCriterion("product_id >=", value, "productId");
            return (Criteria) this;
        }

        public Criteria andProductIdLessThan(Long value) {
            addCriterion("product_id <", value, "productId");
            return (Criteria) this;
        }

        public Criteria andProductIdLessThanOrEqualTo(Long value) {
            addCriterion("product_id <=", value, "productId");
            return (Criteria) this;
        }

        public Criteria andProductIdIn(List<Long> values) {
            addCriterion("product_id in", values, "productId");
            return (Criteria) this;
        }

        public Criteria andProductIdNotIn(List<Long> values) {
            addCriterion("product_id not in", values, "productId");
            return (Criteria) this;
        }

        public Criteria andProductIdBetween(Long value1, Long value2) {
            addCriterion("product_id between", value1, value2, "productId");
            return (Criteria) this;
        }

        public Criteria andProductIdNotBetween(Long value1, Long value2) {
            addCriterion("product_id not between", value1, value2, "productId");
            return (Criteria) this;
        }

        public Criteria andFeeCnNameIsNull() {
            addCriterion("fee_cn_name is null");
            return (Criteria) this;
        }

        public Criteria andFeeCnNameIsNotNull() {
            addCriterion("fee_cn_name is not null");
            return (Criteria) this;
        }

        public Criteria andFeeCnNameEqualTo(String value) {
            addCriterion("fee_cn_name =", value, "feeCnName");
            return (Criteria) this;
        }

        public Criteria andFeeCnNameNotEqualTo(String value) {
            addCriterion("fee_cn_name <>", value, "feeCnName");
            return (Criteria) this;
        }

        public Criteria andFeeCnNameGreaterThan(String value) {
            addCriterion("fee_cn_name >", value, "feeCnName");
            return (Criteria) this;
        }

        public Criteria andFeeCnNameGreaterThanOrEqualTo(String value) {
            addCriterion("fee_cn_name >=", value, "feeCnName");
            return (Criteria) this;
        }

        public Criteria andFeeCnNameLessThan(String value) {
            addCriterion("fee_cn_name <", value, "feeCnName");
            return (Criteria) this;
        }

        public Criteria andFeeCnNameLessThanOrEqualTo(String value) {
            addCriterion("fee_cn_name <=", value, "feeCnName");
            return (Criteria) this;
        }

        public Criteria andFeeCnNameLike(String value) {
            addCriterion("fee_cn_name like", value, "feeCnName");
            return (Criteria) this;
        }

        public Criteria andFeeCnNameNotLike(String value) {
            addCriterion("fee_cn_name not like", value, "feeCnName");
            return (Criteria) this;
        }

        public Criteria andFeeCnNameIn(List<String> values) {
            addCriterion("fee_cn_name in", values, "feeCnName");
            return (Criteria) this;
        }

        public Criteria andFeeCnNameNotIn(List<String> values) {
            addCriterion("fee_cn_name not in", values, "feeCnName");
            return (Criteria) this;
        }

        public Criteria andFeeCnNameBetween(String value1, String value2) {
            addCriterion("fee_cn_name between", value1, value2, "feeCnName");
            return (Criteria) this;
        }

        public Criteria andFeeCnNameNotBetween(String value1, String value2) {
            addCriterion("fee_cn_name not between", value1, value2, "feeCnName");
            return (Criteria) this;
        }

        public Criteria andFeeEnNameIsNull() {
            addCriterion("fee_en_name is null");
            return (Criteria) this;
        }

        public Criteria andFeeEnNameIsNotNull() {
            addCriterion("fee_en_name is not null");
            return (Criteria) this;
        }

        public Criteria andFeeEnNameEqualTo(String value) {
            addCriterion("fee_en_name =", value, "feeEnName");
            return (Criteria) this;
        }

        public Criteria andFeeEnNameNotEqualTo(String value) {
            addCriterion("fee_en_name <>", value, "feeEnName");
            return (Criteria) this;
        }

        public Criteria andFeeEnNameGreaterThan(String value) {
            addCriterion("fee_en_name >", value, "feeEnName");
            return (Criteria) this;
        }

        public Criteria andFeeEnNameGreaterThanOrEqualTo(String value) {
            addCriterion("fee_en_name >=", value, "feeEnName");
            return (Criteria) this;
        }

        public Criteria andFeeEnNameLessThan(String value) {
            addCriterion("fee_en_name <", value, "feeEnName");
            return (Criteria) this;
        }

        public Criteria andFeeEnNameLessThanOrEqualTo(String value) {
            addCriterion("fee_en_name <=", value, "feeEnName");
            return (Criteria) this;
        }

        public Criteria andFeeEnNameLike(String value) {
            addCriterion("fee_en_name like", value, "feeEnName");
            return (Criteria) this;
        }

        public Criteria andFeeEnNameNotLike(String value) {
            addCriterion("fee_en_name not like", value, "feeEnName");
            return (Criteria) this;
        }

        public Criteria andFeeEnNameIn(List<String> values) {
            addCriterion("fee_en_name in", values, "feeEnName");
            return (Criteria) this;
        }

        public Criteria andFeeEnNameNotIn(List<String> values) {
            addCriterion("fee_en_name not in", values, "feeEnName");
            return (Criteria) this;
        }

        public Criteria andFeeEnNameBetween(String value1, String value2) {
            addCriterion("fee_en_name between", value1, value2, "feeEnName");
            return (Criteria) this;
        }

        public Criteria andFeeEnNameNotBetween(String value1, String value2) {
            addCriterion("fee_en_name not between", value1, value2, "feeEnName");
            return (Criteria) this;
        }

        public Criteria andFeeUnitIsNull() {
            addCriterion("fee_unit is null");
            return (Criteria) this;
        }

        public Criteria andFeeUnitIsNotNull() {
            addCriterion("fee_unit is not null");
            return (Criteria) this;
        }

        public Criteria andFeeUnitEqualTo(Integer value) {
            addCriterion("fee_unit =", value, "feeUnit");
            return (Criteria) this;
        }

        public Criteria andFeeUnitNotEqualTo(Integer value) {
            addCriterion("fee_unit <>", value, "feeUnit");
            return (Criteria) this;
        }

        public Criteria andFeeUnitGreaterThan(Integer value) {
            addCriterion("fee_unit >", value, "feeUnit");
            return (Criteria) this;
        }

        public Criteria andFeeUnitGreaterThanOrEqualTo(Integer value) {
            addCriterion("fee_unit >=", value, "feeUnit");
            return (Criteria) this;
        }

        public Criteria andFeeUnitLessThan(Integer value) {
            addCriterion("fee_unit <", value, "feeUnit");
            return (Criteria) this;
        }

        public Criteria andFeeUnitLessThanOrEqualTo(Integer value) {
            addCriterion("fee_unit <=", value, "feeUnit");
            return (Criteria) this;
        }

        public Criteria andFeeUnitIn(List<Integer> values) {
            addCriterion("fee_unit in", values, "feeUnit");
            return (Criteria) this;
        }

        public Criteria andFeeUnitNotIn(List<Integer> values) {
            addCriterion("fee_unit not in", values, "feeUnit");
            return (Criteria) this;
        }

        public Criteria andFeeUnitBetween(Integer value1, Integer value2) {
            addCriterion("fee_unit between", value1, value2, "feeUnit");
            return (Criteria) this;
        }

        public Criteria andFeeUnitNotBetween(Integer value1, Integer value2) {
            addCriterion("fee_unit not between", value1, value2, "feeUnit");
            return (Criteria) this;
        }

        public Criteria andContainerTypeIsNull() {
            addCriterion("container_type is null");
            return (Criteria) this;
        }

        public Criteria andContainerTypeIsNotNull() {
            addCriterion("container_type is not null");
            return (Criteria) this;
        }

        public Criteria andContainerTypeEqualTo(Integer value) {
            addCriterion("container_type =", value, "containerType");
            return (Criteria) this;
        }

        public Criteria andContainerTypeNotEqualTo(Integer value) {
            addCriterion("container_type <>", value, "containerType");
            return (Criteria) this;
        }

        public Criteria andContainerTypeGreaterThan(Integer value) {
            addCriterion("container_type >", value, "containerType");
            return (Criteria) this;
        }

        public Criteria andContainerTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("container_type >=", value, "containerType");
            return (Criteria) this;
        }

        public Criteria andContainerTypeLessThan(Integer value) {
            addCriterion("container_type <", value, "containerType");
            return (Criteria) this;
        }

        public Criteria andContainerTypeLessThanOrEqualTo(Integer value) {
            addCriterion("container_type <=", value, "containerType");
            return (Criteria) this;
        }

        public Criteria andContainerTypeIn(List<Integer> values) {
            addCriterion("container_type in", values, "containerType");
            return (Criteria) this;
        }

        public Criteria andContainerTypeNotIn(List<Integer> values) {
            addCriterion("container_type not in", values, "containerType");
            return (Criteria) this;
        }

        public Criteria andContainerTypeBetween(Integer value1, Integer value2) {
            addCriterion("container_type between", value1, value2, "containerType");
            return (Criteria) this;
        }

        public Criteria andContainerTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("container_type not between", value1, value2, "containerType");
            return (Criteria) this;
        }

        public Criteria andFeeSourceIsNull() {
            addCriterion("fee_source is null");
            return (Criteria) this;
        }

        public Criteria andFeeSourceIsNotNull() {
            addCriterion("fee_source is not null");
            return (Criteria) this;
        }

        public Criteria andFeeSourceEqualTo(Integer value) {
            addCriterion("fee_source =", value, "feeSource");
            return (Criteria) this;
        }

        public Criteria andFeeSourceNotEqualTo(Integer value) {
            addCriterion("fee_source <>", value, "feeSource");
            return (Criteria) this;
        }

        public Criteria andFeeSourceGreaterThan(Integer value) {
            addCriterion("fee_source >", value, "feeSource");
            return (Criteria) this;
        }

        public Criteria andFeeSourceGreaterThanOrEqualTo(Integer value) {
            addCriterion("fee_source >=", value, "feeSource");
            return (Criteria) this;
        }

        public Criteria andFeeSourceLessThan(Integer value) {
            addCriterion("fee_source <", value, "feeSource");
            return (Criteria) this;
        }

        public Criteria andFeeSourceLessThanOrEqualTo(Integer value) {
            addCriterion("fee_source <=", value, "feeSource");
            return (Criteria) this;
        }

        public Criteria andFeeSourceIn(List<Integer> values) {
            addCriterion("fee_source in", values, "feeSource");
            return (Criteria) this;
        }

        public Criteria andFeeSourceNotIn(List<Integer> values) {
            addCriterion("fee_source not in", values, "feeSource");
            return (Criteria) this;
        }

        public Criteria andFeeSourceBetween(Integer value1, Integer value2) {
            addCriterion("fee_source between", value1, value2, "feeSource");
            return (Criteria) this;
        }

        public Criteria andFeeSourceNotBetween(Integer value1, Integer value2) {
            addCriterion("fee_source not between", value1, value2, "feeSource");
            return (Criteria) this;
        }

        public Criteria andFeeCostTypeIsNull() {
            addCriterion("fee_cost_type is null");
            return (Criteria) this;
        }

        public Criteria andFeeCostTypeIsNotNull() {
            addCriterion("fee_cost_type is not null");
            return (Criteria) this;
        }

        public Criteria andFeeCostTypeEqualTo(Integer value) {
            addCriterion("fee_cost_type =", value, "feeCostType");
            return (Criteria) this;
        }

        public Criteria andFeeCostTypeNotEqualTo(Integer value) {
            addCriterion("fee_cost_type <>", value, "feeCostType");
            return (Criteria) this;
        }

        public Criteria andFeeCostTypeGreaterThan(Integer value) {
            addCriterion("fee_cost_type >", value, "feeCostType");
            return (Criteria) this;
        }

        public Criteria andFeeCostTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("fee_cost_type >=", value, "feeCostType");
            return (Criteria) this;
        }

        public Criteria andFeeCostTypeLessThan(Integer value) {
            addCriterion("fee_cost_type <", value, "feeCostType");
            return (Criteria) this;
        }

        public Criteria andFeeCostTypeLessThanOrEqualTo(Integer value) {
            addCriterion("fee_cost_type <=", value, "feeCostType");
            return (Criteria) this;
        }

        public Criteria andFeeCostTypeIn(List<Integer> values) {
            addCriterion("fee_cost_type in", values, "feeCostType");
            return (Criteria) this;
        }

        public Criteria andFeeCostTypeNotIn(List<Integer> values) {
            addCriterion("fee_cost_type not in", values, "feeCostType");
            return (Criteria) this;
        }

        public Criteria andFeeCostTypeBetween(Integer value1, Integer value2) {
            addCriterion("fee_cost_type between", value1, value2, "feeCostType");
            return (Criteria) this;
        }

        public Criteria andFeeCostTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("fee_cost_type not between", value1, value2, "feeCostType");
            return (Criteria) this;
        }

        public Criteria andFeeCurrencyIsNull() {
            addCriterion("fee_currency is null");
            return (Criteria) this;
        }

        public Criteria andFeeCurrencyIsNotNull() {
            addCriterion("fee_currency is not null");
            return (Criteria) this;
        }

        public Criteria andFeeCurrencyEqualTo(Integer value) {
            addCriterion("fee_currency =", value, "feeCurrency");
            return (Criteria) this;
        }

        public Criteria andFeeCurrencyNotEqualTo(Integer value) {
            addCriterion("fee_currency <>", value, "feeCurrency");
            return (Criteria) this;
        }

        public Criteria andFeeCurrencyGreaterThan(Integer value) {
            addCriterion("fee_currency >", value, "feeCurrency");
            return (Criteria) this;
        }

        public Criteria andFeeCurrencyGreaterThanOrEqualTo(Integer value) {
            addCriterion("fee_currency >=", value, "feeCurrency");
            return (Criteria) this;
        }

        public Criteria andFeeCurrencyLessThan(Integer value) {
            addCriterion("fee_currency <", value, "feeCurrency");
            return (Criteria) this;
        }

        public Criteria andFeeCurrencyLessThanOrEqualTo(Integer value) {
            addCriterion("fee_currency <=", value, "feeCurrency");
            return (Criteria) this;
        }

        public Criteria andFeeCurrencyIn(List<Integer> values) {
            addCriterion("fee_currency in", values, "feeCurrency");
            return (Criteria) this;
        }

        public Criteria andFeeCurrencyNotIn(List<Integer> values) {
            addCriterion("fee_currency not in", values, "feeCurrency");
            return (Criteria) this;
        }

        public Criteria andFeeCurrencyBetween(Integer value1, Integer value2) {
            addCriterion("fee_currency between", value1, value2, "feeCurrency");
            return (Criteria) this;
        }

        public Criteria andFeeCurrencyNotBetween(Integer value1, Integer value2) {
            addCriterion("fee_currency not between", value1, value2, "feeCurrency");
            return (Criteria) this;
        }

        public Criteria andPriceIsNull() {
            addCriterion("price is null");
            return (Criteria) this;
        }

        public Criteria andPriceIsNotNull() {
            addCriterion("price is not null");
            return (Criteria) this;
        }

        public Criteria andPriceEqualTo(BigDecimal value) {
            addCriterion("price =", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceNotEqualTo(BigDecimal value) {
            addCriterion("price <>", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceGreaterThan(BigDecimal value) {
            addCriterion("price >", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("price >=", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceLessThan(BigDecimal value) {
            addCriterion("price <", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("price <=", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceIn(List<BigDecimal> values) {
            addCriterion("price in", values, "price");
            return (Criteria) this;
        }

        public Criteria andPriceNotIn(List<BigDecimal> values) {
            addCriterion("price not in", values, "price");
            return (Criteria) this;
        }

        public Criteria andPriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("price between", value1, value2, "price");
            return (Criteria) this;
        }

        public Criteria andPriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("price not between", value1, value2, "price");
            return (Criteria) this;
        }

        public Criteria andSpotIdIsNull() {
            addCriterion("spot_id is null");
            return (Criteria) this;
        }

        public Criteria andSpotIdIsNotNull() {
            addCriterion("spot_id is not null");
            return (Criteria) this;
        }

        public Criteria andSpotIdEqualTo(String value) {
            addCriterion("spot_id =", value, "spotId");
            return (Criteria) this;
        }

        public Criteria andSpotIdNotEqualTo(String value) {
            addCriterion("spot_id <>", value, "spotId");
            return (Criteria) this;
        }

        public Criteria andSpotIdGreaterThan(String value) {
            addCriterion("spot_id >", value, "spotId");
            return (Criteria) this;
        }

        public Criteria andSpotIdGreaterThanOrEqualTo(String value) {
            addCriterion("spot_id >=", value, "spotId");
            return (Criteria) this;
        }

        public Criteria andSpotIdLessThan(String value) {
            addCriterion("spot_id <", value, "spotId");
            return (Criteria) this;
        }

        public Criteria andSpotIdLessThanOrEqualTo(String value) {
            addCriterion("spot_id <=", value, "spotId");
            return (Criteria) this;
        }

        public Criteria andSpotIdLike(String value) {
            addCriterion("spot_id like", value, "spotId");
            return (Criteria) this;
        }

        public Criteria andSpotIdNotLike(String value) {
            addCriterion("spot_id not like", value, "spotId");
            return (Criteria) this;
        }

        public Criteria andSpotIdIn(List<String> values) {
            addCriterion("spot_id in", values, "spotId");
            return (Criteria) this;
        }

        public Criteria andSpotIdNotIn(List<String> values) {
            addCriterion("spot_id not in", values, "spotId");
            return (Criteria) this;
        }

        public Criteria andSpotIdBetween(String value1, String value2) {
            addCriterion("spot_id between", value1, value2, "spotId");
            return (Criteria) this;
        }

        public Criteria andSpotIdNotBetween(String value1, String value2) {
            addCriterion("spot_id not between", value1, value2, "spotId");
            return (Criteria) this;
        }

        public Criteria andCreatorIsNull() {
            addCriterion("creator is null");
            return (Criteria) this;
        }

        public Criteria andCreatorIsNotNull() {
            addCriterion("creator is not null");
            return (Criteria) this;
        }

        public Criteria andCreatorEqualTo(String value) {
            addCriterion("creator =", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorNotEqualTo(String value) {
            addCriterion("creator <>", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorGreaterThan(String value) {
            addCriterion("creator >", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorGreaterThanOrEqualTo(String value) {
            addCriterion("creator >=", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorLessThan(String value) {
            addCriterion("creator <", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorLessThanOrEqualTo(String value) {
            addCriterion("creator <=", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorLike(String value) {
            addCriterion("creator like", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorNotLike(String value) {
            addCriterion("creator not like", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorIn(List<String> values) {
            addCriterion("creator in", values, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorNotIn(List<String> values) {
            addCriterion("creator not in", values, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorBetween(String value1, String value2) {
            addCriterion("creator between", value1, value2, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorNotBetween(String value1, String value2) {
            addCriterion("creator not between", value1, value2, "creator");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andUpdaterIsNull() {
            addCriterion("updater is null");
            return (Criteria) this;
        }

        public Criteria andUpdaterIsNotNull() {
            addCriterion("updater is not null");
            return (Criteria) this;
        }

        public Criteria andUpdaterEqualTo(String value) {
            addCriterion("updater =", value, "updater");
            return (Criteria) this;
        }

        public Criteria andUpdaterNotEqualTo(String value) {
            addCriterion("updater <>", value, "updater");
            return (Criteria) this;
        }

        public Criteria andUpdaterGreaterThan(String value) {
            addCriterion("updater >", value, "updater");
            return (Criteria) this;
        }

        public Criteria andUpdaterGreaterThanOrEqualTo(String value) {
            addCriterion("updater >=", value, "updater");
            return (Criteria) this;
        }

        public Criteria andUpdaterLessThan(String value) {
            addCriterion("updater <", value, "updater");
            return (Criteria) this;
        }

        public Criteria andUpdaterLessThanOrEqualTo(String value) {
            addCriterion("updater <=", value, "updater");
            return (Criteria) this;
        }

        public Criteria andUpdaterLike(String value) {
            addCriterion("updater like", value, "updater");
            return (Criteria) this;
        }

        public Criteria andUpdaterNotLike(String value) {
            addCriterion("updater not like", value, "updater");
            return (Criteria) this;
        }

        public Criteria andUpdaterIn(List<String> values) {
            addCriterion("updater in", values, "updater");
            return (Criteria) this;
        }

        public Criteria andUpdaterNotIn(List<String> values) {
            addCriterion("updater not in", values, "updater");
            return (Criteria) this;
        }

        public Criteria andUpdaterBetween(String value1, String value2) {
            addCriterion("updater between", value1, value2, "updater");
            return (Criteria) this;
        }

        public Criteria andUpdaterNotBetween(String value1, String value2) {
            addCriterion("updater not between", value1, value2, "updater");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNull() {
            addCriterion("update_time is null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNotNull() {
            addCriterion("update_time is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeEqualTo(Date value) {
            addCriterion("update_time =", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotEqualTo(Date value) {
            addCriterion("update_time <>", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThan(Date value) {
            addCriterion("update_time >", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("update_time >=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThan(Date value) {
            addCriterion("update_time <", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThanOrEqualTo(Date value) {
            addCriterion("update_time <=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIn(List<Date> values) {
            addCriterion("update_time in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotIn(List<Date> values) {
            addCriterion("update_time not in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeBetween(Date value1, Date value2) {
            addCriterion("update_time between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotBetween(Date value1, Date value2) {
            addCriterion("update_time not between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andDeletedIsNull() {
            addCriterion("deleted is null");
            return (Criteria) this;
        }

        public Criteria andDeletedIsNotNull() {
            addCriterion("deleted is not null");
            return (Criteria) this;
        }

        public Criteria andDeletedEqualTo(Boolean value) {
            addCriterion("deleted =", value, "deleted");
            return (Criteria) this;
        }

        public Criteria andDeletedNotEqualTo(Boolean value) {
            addCriterion("deleted <>", value, "deleted");
            return (Criteria) this;
        }

        public Criteria andDeletedGreaterThan(Boolean value) {
            addCriterion("deleted >", value, "deleted");
            return (Criteria) this;
        }

        public Criteria andDeletedGreaterThanOrEqualTo(Boolean value) {
            addCriterion("deleted >=", value, "deleted");
            return (Criteria) this;
        }

        public Criteria andDeletedLessThan(Boolean value) {
            addCriterion("deleted <", value, "deleted");
            return (Criteria) this;
        }

        public Criteria andDeletedLessThanOrEqualTo(Boolean value) {
            addCriterion("deleted <=", value, "deleted");
            return (Criteria) this;
        }

        public Criteria andDeletedIn(List<Boolean> values) {
            addCriterion("deleted in", values, "deleted");
            return (Criteria) this;
        }

        public Criteria andDeletedNotIn(List<Boolean> values) {
            addCriterion("deleted not in", values, "deleted");
            return (Criteria) this;
        }

        public Criteria andDeletedBetween(Boolean value1, Boolean value2) {
            addCriterion("deleted between", value1, value2, "deleted");
            return (Criteria) this;
        }

        public Criteria andDeletedNotBetween(Boolean value1, Boolean value2) {
            addCriterion("deleted not between", value1, value2, "deleted");
            return (Criteria) this;
        }

        public Criteria andTenantIdIsNull() {
            addCriterion("tenant_id is null");
            return (Criteria) this;
        }

        public Criteria andTenantIdIsNotNull() {
            addCriterion("tenant_id is not null");
            return (Criteria) this;
        }

        public Criteria andTenantIdEqualTo(Long value) {
            addCriterion("tenant_id =", value, "tenantId");
            return (Criteria) this;
        }

        public Criteria andTenantIdNotEqualTo(Long value) {
            addCriterion("tenant_id <>", value, "tenantId");
            return (Criteria) this;
        }

        public Criteria andTenantIdGreaterThan(Long value) {
            addCriterion("tenant_id >", value, "tenantId");
            return (Criteria) this;
        }

        public Criteria andTenantIdGreaterThanOrEqualTo(Long value) {
            addCriterion("tenant_id >=", value, "tenantId");
            return (Criteria) this;
        }

        public Criteria andTenantIdLessThan(Long value) {
            addCriterion("tenant_id <", value, "tenantId");
            return (Criteria) this;
        }

        public Criteria andTenantIdLessThanOrEqualTo(Long value) {
            addCriterion("tenant_id <=", value, "tenantId");
            return (Criteria) this;
        }

        public Criteria andTenantIdIn(List<Long> values) {
            addCriterion("tenant_id in", values, "tenantId");
            return (Criteria) this;
        }

        public Criteria andTenantIdNotIn(List<Long> values) {
            addCriterion("tenant_id not in", values, "tenantId");
            return (Criteria) this;
        }

        public Criteria andTenantIdBetween(Long value1, Long value2) {
            addCriterion("tenant_id between", value1, value2, "tenantId");
            return (Criteria) this;
        }

        public Criteria andTenantIdNotBetween(Long value1, Long value2) {
            addCriterion("tenant_id not between", value1, value2, "tenantId");
            return (Criteria) this;
        }

        public Criteria andDeletedByIsNull() {
            addCriterion("deleted_by is null");
            return (Criteria) this;
        }

        public Criteria andDeletedByIsNotNull() {
            addCriterion("deleted_by is not null");
            return (Criteria) this;
        }

        public Criteria andDeletedByEqualTo(String value) {
            addCriterion("deleted_by =", value, "deletedBy");
            return (Criteria) this;
        }

        public Criteria andDeletedByNotEqualTo(String value) {
            addCriterion("deleted_by <>", value, "deletedBy");
            return (Criteria) this;
        }

        public Criteria andDeletedByGreaterThan(String value) {
            addCriterion("deleted_by >", value, "deletedBy");
            return (Criteria) this;
        }

        public Criteria andDeletedByGreaterThanOrEqualTo(String value) {
            addCriterion("deleted_by >=", value, "deletedBy");
            return (Criteria) this;
        }

        public Criteria andDeletedByLessThan(String value) {
            addCriterion("deleted_by <", value, "deletedBy");
            return (Criteria) this;
        }

        public Criteria andDeletedByLessThanOrEqualTo(String value) {
            addCriterion("deleted_by <=", value, "deletedBy");
            return (Criteria) this;
        }

        public Criteria andDeletedByLike(String value) {
            addCriterion("deleted_by like", value, "deletedBy");
            return (Criteria) this;
        }

        public Criteria andDeletedByNotLike(String value) {
            addCriterion("deleted_by not like", value, "deletedBy");
            return (Criteria) this;
        }

        public Criteria andDeletedByIn(List<String> values) {
            addCriterion("deleted_by in", values, "deletedBy");
            return (Criteria) this;
        }

        public Criteria andDeletedByNotIn(List<String> values) {
            addCriterion("deleted_by not in", values, "deletedBy");
            return (Criteria) this;
        }

        public Criteria andDeletedByBetween(String value1, String value2) {
            addCriterion("deleted_by between", value1, value2, "deletedBy");
            return (Criteria) this;
        }

        public Criteria andDeletedByNotBetween(String value1, String value2) {
            addCriterion("deleted_by not between", value1, value2, "deletedBy");
            return (Criteria) this;
        }

        public Criteria andDeletedTimeIsNull() {
            addCriterion("deleted_time is null");
            return (Criteria) this;
        }

        public Criteria andDeletedTimeIsNotNull() {
            addCriterion("deleted_time is not null");
            return (Criteria) this;
        }

        public Criteria andDeletedTimeEqualTo(Date value) {
            addCriterion("deleted_time =", value, "deletedTime");
            return (Criteria) this;
        }

        public Criteria andDeletedTimeNotEqualTo(Date value) {
            addCriterion("deleted_time <>", value, "deletedTime");
            return (Criteria) this;
        }

        public Criteria andDeletedTimeGreaterThan(Date value) {
            addCriterion("deleted_time >", value, "deletedTime");
            return (Criteria) this;
        }

        public Criteria andDeletedTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("deleted_time >=", value, "deletedTime");
            return (Criteria) this;
        }

        public Criteria andDeletedTimeLessThan(Date value) {
            addCriterion("deleted_time <", value, "deletedTime");
            return (Criteria) this;
        }

        public Criteria andDeletedTimeLessThanOrEqualTo(Date value) {
            addCriterion("deleted_time <=", value, "deletedTime");
            return (Criteria) this;
        }

        public Criteria andDeletedTimeIn(List<Date> values) {
            addCriterion("deleted_time in", values, "deletedTime");
            return (Criteria) this;
        }

        public Criteria andDeletedTimeNotIn(List<Date> values) {
            addCriterion("deleted_time not in", values, "deletedTime");
            return (Criteria) this;
        }

        public Criteria andDeletedTimeBetween(Date value1, Date value2) {
            addCriterion("deleted_time between", value1, value2, "deletedTime");
            return (Criteria) this;
        }

        public Criteria andDeletedTimeNotBetween(Date value1, Date value2) {
            addCriterion("deleted_time not between", value1, value2, "deletedTime");
            return (Criteria) this;
        }

        public Criteria andPriceComputeTypeIsNull() {
            addCriterion("price_compute_type is null");
            return (Criteria) this;
        }

        public Criteria andPriceComputeTypeIsNotNull() {
            addCriterion("price_compute_type is not null");
            return (Criteria) this;
        }

        public Criteria andPriceComputeTypeEqualTo(Integer value) {
            addCriterion("price_compute_type =", value, "priceComputeType");
            return (Criteria) this;
        }

        public Criteria andPriceComputeTypeNotEqualTo(Integer value) {
            addCriterion("price_compute_type <>", value, "priceComputeType");
            return (Criteria) this;
        }

        public Criteria andPriceComputeTypeGreaterThan(Integer value) {
            addCriterion("price_compute_type >", value, "priceComputeType");
            return (Criteria) this;
        }

        public Criteria andPriceComputeTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("price_compute_type >=", value, "priceComputeType");
            return (Criteria) this;
        }

        public Criteria andPriceComputeTypeLessThan(Integer value) {
            addCriterion("price_compute_type <", value, "priceComputeType");
            return (Criteria) this;
        }

        public Criteria andPriceComputeTypeLessThanOrEqualTo(Integer value) {
            addCriterion("price_compute_type <=", value, "priceComputeType");
            return (Criteria) this;
        }

        public Criteria andPriceComputeTypeIn(List<Integer> values) {
            addCriterion("price_compute_type in", values, "priceComputeType");
            return (Criteria) this;
        }

        public Criteria andPriceComputeTypeNotIn(List<Integer> values) {
            addCriterion("price_compute_type not in", values, "priceComputeType");
            return (Criteria) this;
        }

        public Criteria andPriceComputeTypeBetween(Integer value1, Integer value2) {
            addCriterion("price_compute_type between", value1, value2, "priceComputeType");
            return (Criteria) this;
        }

        public Criteria andPriceComputeTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("price_compute_type not between", value1, value2, "priceComputeType");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}