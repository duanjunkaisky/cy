package com.djk.core.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProductInfoExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ProductInfoExample() {
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

        public Criteria andProductNumberIsNull() {
            addCriterion("product_number is null");
            return (Criteria) this;
        }

        public Criteria andProductNumberIsNotNull() {
            addCriterion("product_number is not null");
            return (Criteria) this;
        }

        public Criteria andProductNumberEqualTo(String value) {
            addCriterion("product_number =", value, "productNumber");
            return (Criteria) this;
        }

        public Criteria andProductNumberNotEqualTo(String value) {
            addCriterion("product_number <>", value, "productNumber");
            return (Criteria) this;
        }

        public Criteria andProductNumberGreaterThan(String value) {
            addCriterion("product_number >", value, "productNumber");
            return (Criteria) this;
        }

        public Criteria andProductNumberGreaterThanOrEqualTo(String value) {
            addCriterion("product_number >=", value, "productNumber");
            return (Criteria) this;
        }

        public Criteria andProductNumberLessThan(String value) {
            addCriterion("product_number <", value, "productNumber");
            return (Criteria) this;
        }

        public Criteria andProductNumberLessThanOrEqualTo(String value) {
            addCriterion("product_number <=", value, "productNumber");
            return (Criteria) this;
        }

        public Criteria andProductNumberLike(String value) {
            addCriterion("product_number like", value, "productNumber");
            return (Criteria) this;
        }

        public Criteria andProductNumberNotLike(String value) {
            addCriterion("product_number not like", value, "productNumber");
            return (Criteria) this;
        }

        public Criteria andProductNumberIn(List<String> values) {
            addCriterion("product_number in", values, "productNumber");
            return (Criteria) this;
        }

        public Criteria andProductNumberNotIn(List<String> values) {
            addCriterion("product_number not in", values, "productNumber");
            return (Criteria) this;
        }

        public Criteria andProductNumberBetween(String value1, String value2) {
            addCriterion("product_number between", value1, value2, "productNumber");
            return (Criteria) this;
        }

        public Criteria andProductNumberNotBetween(String value1, String value2) {
            addCriterion("product_number not between", value1, value2, "productNumber");
            return (Criteria) this;
        }

        public Criteria andDeparturePortZhIsNull() {
            addCriterion("departure_port_zh is null");
            return (Criteria) this;
        }

        public Criteria andDeparturePortZhIsNotNull() {
            addCriterion("departure_port_zh is not null");
            return (Criteria) this;
        }

        public Criteria andDeparturePortZhEqualTo(String value) {
            addCriterion("departure_port_zh =", value, "departurePortZh");
            return (Criteria) this;
        }

        public Criteria andDeparturePortZhNotEqualTo(String value) {
            addCriterion("departure_port_zh <>", value, "departurePortZh");
            return (Criteria) this;
        }

        public Criteria andDeparturePortZhGreaterThan(String value) {
            addCriterion("departure_port_zh >", value, "departurePortZh");
            return (Criteria) this;
        }

        public Criteria andDeparturePortZhGreaterThanOrEqualTo(String value) {
            addCriterion("departure_port_zh >=", value, "departurePortZh");
            return (Criteria) this;
        }

        public Criteria andDeparturePortZhLessThan(String value) {
            addCriterion("departure_port_zh <", value, "departurePortZh");
            return (Criteria) this;
        }

        public Criteria andDeparturePortZhLessThanOrEqualTo(String value) {
            addCriterion("departure_port_zh <=", value, "departurePortZh");
            return (Criteria) this;
        }

        public Criteria andDeparturePortZhLike(String value) {
            addCriterion("departure_port_zh like", value, "departurePortZh");
            return (Criteria) this;
        }

        public Criteria andDeparturePortZhNotLike(String value) {
            addCriterion("departure_port_zh not like", value, "departurePortZh");
            return (Criteria) this;
        }

        public Criteria andDeparturePortZhIn(List<String> values) {
            addCriterion("departure_port_zh in", values, "departurePortZh");
            return (Criteria) this;
        }

        public Criteria andDeparturePortZhNotIn(List<String> values) {
            addCriterion("departure_port_zh not in", values, "departurePortZh");
            return (Criteria) this;
        }

        public Criteria andDeparturePortZhBetween(String value1, String value2) {
            addCriterion("departure_port_zh between", value1, value2, "departurePortZh");
            return (Criteria) this;
        }

        public Criteria andDeparturePortZhNotBetween(String value1, String value2) {
            addCriterion("departure_port_zh not between", value1, value2, "departurePortZh");
            return (Criteria) this;
        }

        public Criteria andDeparturePortEnIsNull() {
            addCriterion("departure_port_en is null");
            return (Criteria) this;
        }

        public Criteria andDeparturePortEnIsNotNull() {
            addCriterion("departure_port_en is not null");
            return (Criteria) this;
        }

        public Criteria andDeparturePortEnEqualTo(String value) {
            addCriterion("departure_port_en =", value, "departurePortEn");
            return (Criteria) this;
        }

        public Criteria andDeparturePortEnNotEqualTo(String value) {
            addCriterion("departure_port_en <>", value, "departurePortEn");
            return (Criteria) this;
        }

        public Criteria andDeparturePortEnGreaterThan(String value) {
            addCriterion("departure_port_en >", value, "departurePortEn");
            return (Criteria) this;
        }

        public Criteria andDeparturePortEnGreaterThanOrEqualTo(String value) {
            addCriterion("departure_port_en >=", value, "departurePortEn");
            return (Criteria) this;
        }

        public Criteria andDeparturePortEnLessThan(String value) {
            addCriterion("departure_port_en <", value, "departurePortEn");
            return (Criteria) this;
        }

        public Criteria andDeparturePortEnLessThanOrEqualTo(String value) {
            addCriterion("departure_port_en <=", value, "departurePortEn");
            return (Criteria) this;
        }

        public Criteria andDeparturePortEnLike(String value) {
            addCriterion("departure_port_en like", value, "departurePortEn");
            return (Criteria) this;
        }

        public Criteria andDeparturePortEnNotLike(String value) {
            addCriterion("departure_port_en not like", value, "departurePortEn");
            return (Criteria) this;
        }

        public Criteria andDeparturePortEnIn(List<String> values) {
            addCriterion("departure_port_en in", values, "departurePortEn");
            return (Criteria) this;
        }

        public Criteria andDeparturePortEnNotIn(List<String> values) {
            addCriterion("departure_port_en not in", values, "departurePortEn");
            return (Criteria) this;
        }

        public Criteria andDeparturePortEnBetween(String value1, String value2) {
            addCriterion("departure_port_en between", value1, value2, "departurePortEn");
            return (Criteria) this;
        }

        public Criteria andDeparturePortEnNotBetween(String value1, String value2) {
            addCriterion("departure_port_en not between", value1, value2, "departurePortEn");
            return (Criteria) this;
        }

        public Criteria andDestinationPortZhIsNull() {
            addCriterion("destination_port_zh is null");
            return (Criteria) this;
        }

        public Criteria andDestinationPortZhIsNotNull() {
            addCriterion("destination_port_zh is not null");
            return (Criteria) this;
        }

        public Criteria andDestinationPortZhEqualTo(String value) {
            addCriterion("destination_port_zh =", value, "destinationPortZh");
            return (Criteria) this;
        }

        public Criteria andDestinationPortZhNotEqualTo(String value) {
            addCriterion("destination_port_zh <>", value, "destinationPortZh");
            return (Criteria) this;
        }

        public Criteria andDestinationPortZhGreaterThan(String value) {
            addCriterion("destination_port_zh >", value, "destinationPortZh");
            return (Criteria) this;
        }

        public Criteria andDestinationPortZhGreaterThanOrEqualTo(String value) {
            addCriterion("destination_port_zh >=", value, "destinationPortZh");
            return (Criteria) this;
        }

        public Criteria andDestinationPortZhLessThan(String value) {
            addCriterion("destination_port_zh <", value, "destinationPortZh");
            return (Criteria) this;
        }

        public Criteria andDestinationPortZhLessThanOrEqualTo(String value) {
            addCriterion("destination_port_zh <=", value, "destinationPortZh");
            return (Criteria) this;
        }

        public Criteria andDestinationPortZhLike(String value) {
            addCriterion("destination_port_zh like", value, "destinationPortZh");
            return (Criteria) this;
        }

        public Criteria andDestinationPortZhNotLike(String value) {
            addCriterion("destination_port_zh not like", value, "destinationPortZh");
            return (Criteria) this;
        }

        public Criteria andDestinationPortZhIn(List<String> values) {
            addCriterion("destination_port_zh in", values, "destinationPortZh");
            return (Criteria) this;
        }

        public Criteria andDestinationPortZhNotIn(List<String> values) {
            addCriterion("destination_port_zh not in", values, "destinationPortZh");
            return (Criteria) this;
        }

        public Criteria andDestinationPortZhBetween(String value1, String value2) {
            addCriterion("destination_port_zh between", value1, value2, "destinationPortZh");
            return (Criteria) this;
        }

        public Criteria andDestinationPortZhNotBetween(String value1, String value2) {
            addCriterion("destination_port_zh not between", value1, value2, "destinationPortZh");
            return (Criteria) this;
        }

        public Criteria andDestinationPortEnIsNull() {
            addCriterion("destination_port_en is null");
            return (Criteria) this;
        }

        public Criteria andDestinationPortEnIsNotNull() {
            addCriterion("destination_port_en is not null");
            return (Criteria) this;
        }

        public Criteria andDestinationPortEnEqualTo(String value) {
            addCriterion("destination_port_en =", value, "destinationPortEn");
            return (Criteria) this;
        }

        public Criteria andDestinationPortEnNotEqualTo(String value) {
            addCriterion("destination_port_en <>", value, "destinationPortEn");
            return (Criteria) this;
        }

        public Criteria andDestinationPortEnGreaterThan(String value) {
            addCriterion("destination_port_en >", value, "destinationPortEn");
            return (Criteria) this;
        }

        public Criteria andDestinationPortEnGreaterThanOrEqualTo(String value) {
            addCriterion("destination_port_en >=", value, "destinationPortEn");
            return (Criteria) this;
        }

        public Criteria andDestinationPortEnLessThan(String value) {
            addCriterion("destination_port_en <", value, "destinationPortEn");
            return (Criteria) this;
        }

        public Criteria andDestinationPortEnLessThanOrEqualTo(String value) {
            addCriterion("destination_port_en <=", value, "destinationPortEn");
            return (Criteria) this;
        }

        public Criteria andDestinationPortEnLike(String value) {
            addCriterion("destination_port_en like", value, "destinationPortEn");
            return (Criteria) this;
        }

        public Criteria andDestinationPortEnNotLike(String value) {
            addCriterion("destination_port_en not like", value, "destinationPortEn");
            return (Criteria) this;
        }

        public Criteria andDestinationPortEnIn(List<String> values) {
            addCriterion("destination_port_en in", values, "destinationPortEn");
            return (Criteria) this;
        }

        public Criteria andDestinationPortEnNotIn(List<String> values) {
            addCriterion("destination_port_en not in", values, "destinationPortEn");
            return (Criteria) this;
        }

        public Criteria andDestinationPortEnBetween(String value1, String value2) {
            addCriterion("destination_port_en between", value1, value2, "destinationPortEn");
            return (Criteria) this;
        }

        public Criteria andDestinationPortEnNotBetween(String value1, String value2) {
            addCriterion("destination_port_en not between", value1, value2, "destinationPortEn");
            return (Criteria) this;
        }

        public Criteria andShippingCompanyIdIsNull() {
            addCriterion("shipping_company_id is null");
            return (Criteria) this;
        }

        public Criteria andShippingCompanyIdIsNotNull() {
            addCriterion("shipping_company_id is not null");
            return (Criteria) this;
        }

        public Criteria andShippingCompanyIdEqualTo(Long value) {
            addCriterion("shipping_company_id =", value, "shippingCompanyId");
            return (Criteria) this;
        }

        public Criteria andShippingCompanyIdNotEqualTo(Long value) {
            addCriterion("shipping_company_id <>", value, "shippingCompanyId");
            return (Criteria) this;
        }

        public Criteria andShippingCompanyIdGreaterThan(Long value) {
            addCriterion("shipping_company_id >", value, "shippingCompanyId");
            return (Criteria) this;
        }

        public Criteria andShippingCompanyIdGreaterThanOrEqualTo(Long value) {
            addCriterion("shipping_company_id >=", value, "shippingCompanyId");
            return (Criteria) this;
        }

        public Criteria andShippingCompanyIdLessThan(Long value) {
            addCriterion("shipping_company_id <", value, "shippingCompanyId");
            return (Criteria) this;
        }

        public Criteria andShippingCompanyIdLessThanOrEqualTo(Long value) {
            addCriterion("shipping_company_id <=", value, "shippingCompanyId");
            return (Criteria) this;
        }

        public Criteria andShippingCompanyIdIn(List<Long> values) {
            addCriterion("shipping_company_id in", values, "shippingCompanyId");
            return (Criteria) this;
        }

        public Criteria andShippingCompanyIdNotIn(List<Long> values) {
            addCriterion("shipping_company_id not in", values, "shippingCompanyId");
            return (Criteria) this;
        }

        public Criteria andShippingCompanyIdBetween(Long value1, Long value2) {
            addCriterion("shipping_company_id between", value1, value2, "shippingCompanyId");
            return (Criteria) this;
        }

        public Criteria andShippingCompanyIdNotBetween(Long value1, Long value2) {
            addCriterion("shipping_company_id not between", value1, value2, "shippingCompanyId");
            return (Criteria) this;
        }

        public Criteria andCnFullNameIsNull() {
            addCriterion("cn_full_name is null");
            return (Criteria) this;
        }

        public Criteria andCnFullNameIsNotNull() {
            addCriterion("cn_full_name is not null");
            return (Criteria) this;
        }

        public Criteria andCnFullNameEqualTo(String value) {
            addCriterion("cn_full_name =", value, "cnFullName");
            return (Criteria) this;
        }

        public Criteria andCnFullNameNotEqualTo(String value) {
            addCriterion("cn_full_name <>", value, "cnFullName");
            return (Criteria) this;
        }

        public Criteria andCnFullNameGreaterThan(String value) {
            addCriterion("cn_full_name >", value, "cnFullName");
            return (Criteria) this;
        }

        public Criteria andCnFullNameGreaterThanOrEqualTo(String value) {
            addCriterion("cn_full_name >=", value, "cnFullName");
            return (Criteria) this;
        }

        public Criteria andCnFullNameLessThan(String value) {
            addCriterion("cn_full_name <", value, "cnFullName");
            return (Criteria) this;
        }

        public Criteria andCnFullNameLessThanOrEqualTo(String value) {
            addCriterion("cn_full_name <=", value, "cnFullName");
            return (Criteria) this;
        }

        public Criteria andCnFullNameLike(String value) {
            addCriterion("cn_full_name like", value, "cnFullName");
            return (Criteria) this;
        }

        public Criteria andCnFullNameNotLike(String value) {
            addCriterion("cn_full_name not like", value, "cnFullName");
            return (Criteria) this;
        }

        public Criteria andCnFullNameIn(List<String> values) {
            addCriterion("cn_full_name in", values, "cnFullName");
            return (Criteria) this;
        }

        public Criteria andCnFullNameNotIn(List<String> values) {
            addCriterion("cn_full_name not in", values, "cnFullName");
            return (Criteria) this;
        }

        public Criteria andCnFullNameBetween(String value1, String value2) {
            addCriterion("cn_full_name between", value1, value2, "cnFullName");
            return (Criteria) this;
        }

        public Criteria andCnFullNameNotBetween(String value1, String value2) {
            addCriterion("cn_full_name not between", value1, value2, "cnFullName");
            return (Criteria) this;
        }

        public Criteria andCnAbbreviationIsNull() {
            addCriterion("cn_abbreviation is null");
            return (Criteria) this;
        }

        public Criteria andCnAbbreviationIsNotNull() {
            addCriterion("cn_abbreviation is not null");
            return (Criteria) this;
        }

        public Criteria andCnAbbreviationEqualTo(String value) {
            addCriterion("cn_abbreviation =", value, "cnAbbreviation");
            return (Criteria) this;
        }

        public Criteria andCnAbbreviationNotEqualTo(String value) {
            addCriterion("cn_abbreviation <>", value, "cnAbbreviation");
            return (Criteria) this;
        }

        public Criteria andCnAbbreviationGreaterThan(String value) {
            addCriterion("cn_abbreviation >", value, "cnAbbreviation");
            return (Criteria) this;
        }

        public Criteria andCnAbbreviationGreaterThanOrEqualTo(String value) {
            addCriterion("cn_abbreviation >=", value, "cnAbbreviation");
            return (Criteria) this;
        }

        public Criteria andCnAbbreviationLessThan(String value) {
            addCriterion("cn_abbreviation <", value, "cnAbbreviation");
            return (Criteria) this;
        }

        public Criteria andCnAbbreviationLessThanOrEqualTo(String value) {
            addCriterion("cn_abbreviation <=", value, "cnAbbreviation");
            return (Criteria) this;
        }

        public Criteria andCnAbbreviationLike(String value) {
            addCriterion("cn_abbreviation like", value, "cnAbbreviation");
            return (Criteria) this;
        }

        public Criteria andCnAbbreviationNotLike(String value) {
            addCriterion("cn_abbreviation not like", value, "cnAbbreviation");
            return (Criteria) this;
        }

        public Criteria andCnAbbreviationIn(List<String> values) {
            addCriterion("cn_abbreviation in", values, "cnAbbreviation");
            return (Criteria) this;
        }

        public Criteria andCnAbbreviationNotIn(List<String> values) {
            addCriterion("cn_abbreviation not in", values, "cnAbbreviation");
            return (Criteria) this;
        }

        public Criteria andCnAbbreviationBetween(String value1, String value2) {
            addCriterion("cn_abbreviation between", value1, value2, "cnAbbreviation");
            return (Criteria) this;
        }

        public Criteria andCnAbbreviationNotBetween(String value1, String value2) {
            addCriterion("cn_abbreviation not between", value1, value2, "cnAbbreviation");
            return (Criteria) this;
        }

        public Criteria andImageIsNull() {
            addCriterion("image is null");
            return (Criteria) this;
        }

        public Criteria andImageIsNotNull() {
            addCriterion("image is not null");
            return (Criteria) this;
        }

        public Criteria andImageEqualTo(String value) {
            addCriterion("image =", value, "image");
            return (Criteria) this;
        }

        public Criteria andImageNotEqualTo(String value) {
            addCriterion("image <>", value, "image");
            return (Criteria) this;
        }

        public Criteria andImageGreaterThan(String value) {
            addCriterion("image >", value, "image");
            return (Criteria) this;
        }

        public Criteria andImageGreaterThanOrEqualTo(String value) {
            addCriterion("image >=", value, "image");
            return (Criteria) this;
        }

        public Criteria andImageLessThan(String value) {
            addCriterion("image <", value, "image");
            return (Criteria) this;
        }

        public Criteria andImageLessThanOrEqualTo(String value) {
            addCriterion("image <=", value, "image");
            return (Criteria) this;
        }

        public Criteria andImageLike(String value) {
            addCriterion("image like", value, "image");
            return (Criteria) this;
        }

        public Criteria andImageNotLike(String value) {
            addCriterion("image not like", value, "image");
            return (Criteria) this;
        }

        public Criteria andImageIn(List<String> values) {
            addCriterion("image in", values, "image");
            return (Criteria) this;
        }

        public Criteria andImageNotIn(List<String> values) {
            addCriterion("image not in", values, "image");
            return (Criteria) this;
        }

        public Criteria andImageBetween(String value1, String value2) {
            addCriterion("image between", value1, value2, "image");
            return (Criteria) this;
        }

        public Criteria andImageNotBetween(String value1, String value2) {
            addCriterion("image not between", value1, value2, "image");
            return (Criteria) this;
        }

        public Criteria andEstimatedDepartureDateIsNull() {
            addCriterion("estimated_departure_date is null");
            return (Criteria) this;
        }

        public Criteria andEstimatedDepartureDateIsNotNull() {
            addCriterion("estimated_departure_date is not null");
            return (Criteria) this;
        }

        public Criteria andEstimatedDepartureDateEqualTo(Date value) {
            addCriterion("estimated_departure_date =", value, "estimatedDepartureDate");
            return (Criteria) this;
        }

        public Criteria andEstimatedDepartureDateNotEqualTo(Date value) {
            addCriterion("estimated_departure_date <>", value, "estimatedDepartureDate");
            return (Criteria) this;
        }

        public Criteria andEstimatedDepartureDateGreaterThan(Date value) {
            addCriterion("estimated_departure_date >", value, "estimatedDepartureDate");
            return (Criteria) this;
        }

        public Criteria andEstimatedDepartureDateGreaterThanOrEqualTo(Date value) {
            addCriterion("estimated_departure_date >=", value, "estimatedDepartureDate");
            return (Criteria) this;
        }

        public Criteria andEstimatedDepartureDateLessThan(Date value) {
            addCriterion("estimated_departure_date <", value, "estimatedDepartureDate");
            return (Criteria) this;
        }

        public Criteria andEstimatedDepartureDateLessThanOrEqualTo(Date value) {
            addCriterion("estimated_departure_date <=", value, "estimatedDepartureDate");
            return (Criteria) this;
        }

        public Criteria andEstimatedDepartureDateIn(List<Date> values) {
            addCriterion("estimated_departure_date in", values, "estimatedDepartureDate");
            return (Criteria) this;
        }

        public Criteria andEstimatedDepartureDateNotIn(List<Date> values) {
            addCriterion("estimated_departure_date not in", values, "estimatedDepartureDate");
            return (Criteria) this;
        }

        public Criteria andEstimatedDepartureDateBetween(Date value1, Date value2) {
            addCriterion("estimated_departure_date between", value1, value2, "estimatedDepartureDate");
            return (Criteria) this;
        }

        public Criteria andEstimatedDepartureDateNotBetween(Date value1, Date value2) {
            addCriterion("estimated_departure_date not between", value1, value2, "estimatedDepartureDate");
            return (Criteria) this;
        }

        public Criteria andRouteIsNull() {
            addCriterion("route is null");
            return (Criteria) this;
        }

        public Criteria andRouteIsNotNull() {
            addCriterion("route is not null");
            return (Criteria) this;
        }

        public Criteria andRouteEqualTo(Integer value) {
            addCriterion("route =", value, "route");
            return (Criteria) this;
        }

        public Criteria andRouteNotEqualTo(Integer value) {
            addCriterion("route <>", value, "route");
            return (Criteria) this;
        }

        public Criteria andRouteGreaterThan(Integer value) {
            addCriterion("route >", value, "route");
            return (Criteria) this;
        }

        public Criteria andRouteGreaterThanOrEqualTo(Integer value) {
            addCriterion("route >=", value, "route");
            return (Criteria) this;
        }

        public Criteria andRouteLessThan(Integer value) {
            addCriterion("route <", value, "route");
            return (Criteria) this;
        }

        public Criteria andRouteLessThanOrEqualTo(Integer value) {
            addCriterion("route <=", value, "route");
            return (Criteria) this;
        }

        public Criteria andRouteIn(List<Integer> values) {
            addCriterion("route in", values, "route");
            return (Criteria) this;
        }

        public Criteria andRouteNotIn(List<Integer> values) {
            addCriterion("route not in", values, "route");
            return (Criteria) this;
        }

        public Criteria andRouteBetween(Integer value1, Integer value2) {
            addCriterion("route between", value1, value2, "route");
            return (Criteria) this;
        }

        public Criteria andRouteNotBetween(Integer value1, Integer value2) {
            addCriterion("route not between", value1, value2, "route");
            return (Criteria) this;
        }

        public Criteria andCourseIsNull() {
            addCriterion("course is null");
            return (Criteria) this;
        }

        public Criteria andCourseIsNotNull() {
            addCriterion("course is not null");
            return (Criteria) this;
        }

        public Criteria andCourseEqualTo(String value) {
            addCriterion("course =", value, "course");
            return (Criteria) this;
        }

        public Criteria andCourseNotEqualTo(String value) {
            addCriterion("course <>", value, "course");
            return (Criteria) this;
        }

        public Criteria andCourseGreaterThan(String value) {
            addCriterion("course >", value, "course");
            return (Criteria) this;
        }

        public Criteria andCourseGreaterThanOrEqualTo(String value) {
            addCriterion("course >=", value, "course");
            return (Criteria) this;
        }

        public Criteria andCourseLessThan(String value) {
            addCriterion("course <", value, "course");
            return (Criteria) this;
        }

        public Criteria andCourseLessThanOrEqualTo(String value) {
            addCriterion("course <=", value, "course");
            return (Criteria) this;
        }

        public Criteria andCourseLike(String value) {
            addCriterion("course like", value, "course");
            return (Criteria) this;
        }

        public Criteria andCourseNotLike(String value) {
            addCriterion("course not like", value, "course");
            return (Criteria) this;
        }

        public Criteria andCourseIn(List<String> values) {
            addCriterion("course in", values, "course");
            return (Criteria) this;
        }

        public Criteria andCourseNotIn(List<String> values) {
            addCriterion("course not in", values, "course");
            return (Criteria) this;
        }

        public Criteria andCourseBetween(String value1, String value2) {
            addCriterion("course between", value1, value2, "course");
            return (Criteria) this;
        }

        public Criteria andCourseNotBetween(String value1, String value2) {
            addCriterion("course not between", value1, value2, "course");
            return (Criteria) this;
        }

        public Criteria andArrivalTimeIsNull() {
            addCriterion("arrival_time is null");
            return (Criteria) this;
        }

        public Criteria andArrivalTimeIsNotNull() {
            addCriterion("arrival_time is not null");
            return (Criteria) this;
        }

        public Criteria andArrivalTimeEqualTo(Date value) {
            addCriterion("arrival_time =", value, "arrivalTime");
            return (Criteria) this;
        }

        public Criteria andArrivalTimeNotEqualTo(Date value) {
            addCriterion("arrival_time <>", value, "arrivalTime");
            return (Criteria) this;
        }

        public Criteria andArrivalTimeGreaterThan(Date value) {
            addCriterion("arrival_time >", value, "arrivalTime");
            return (Criteria) this;
        }

        public Criteria andArrivalTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("arrival_time >=", value, "arrivalTime");
            return (Criteria) this;
        }

        public Criteria andArrivalTimeLessThan(Date value) {
            addCriterion("arrival_time <", value, "arrivalTime");
            return (Criteria) this;
        }

        public Criteria andArrivalTimeLessThanOrEqualTo(Date value) {
            addCriterion("arrival_time <=", value, "arrivalTime");
            return (Criteria) this;
        }

        public Criteria andArrivalTimeIn(List<Date> values) {
            addCriterion("arrival_time in", values, "arrivalTime");
            return (Criteria) this;
        }

        public Criteria andArrivalTimeNotIn(List<Date> values) {
            addCriterion("arrival_time not in", values, "arrivalTime");
            return (Criteria) this;
        }

        public Criteria andArrivalTimeBetween(Date value1, Date value2) {
            addCriterion("arrival_time between", value1, value2, "arrivalTime");
            return (Criteria) this;
        }

        public Criteria andArrivalTimeNotBetween(Date value1, Date value2) {
            addCriterion("arrival_time not between", value1, value2, "arrivalTime");
            return (Criteria) this;
        }

        public Criteria andProductExpiryDateIsNull() {
            addCriterion("product_expiry_date is null");
            return (Criteria) this;
        }

        public Criteria andProductExpiryDateIsNotNull() {
            addCriterion("product_expiry_date is not null");
            return (Criteria) this;
        }

        public Criteria andProductExpiryDateEqualTo(Date value) {
            addCriterion("product_expiry_date =", value, "productExpiryDate");
            return (Criteria) this;
        }

        public Criteria andProductExpiryDateNotEqualTo(Date value) {
            addCriterion("product_expiry_date <>", value, "productExpiryDate");
            return (Criteria) this;
        }

        public Criteria andProductExpiryDateGreaterThan(Date value) {
            addCriterion("product_expiry_date >", value, "productExpiryDate");
            return (Criteria) this;
        }

        public Criteria andProductExpiryDateGreaterThanOrEqualTo(Date value) {
            addCriterion("product_expiry_date >=", value, "productExpiryDate");
            return (Criteria) this;
        }

        public Criteria andProductExpiryDateLessThan(Date value) {
            addCriterion("product_expiry_date <", value, "productExpiryDate");
            return (Criteria) this;
        }

        public Criteria andProductExpiryDateLessThanOrEqualTo(Date value) {
            addCriterion("product_expiry_date <=", value, "productExpiryDate");
            return (Criteria) this;
        }

        public Criteria andProductExpiryDateIn(List<Date> values) {
            addCriterion("product_expiry_date in", values, "productExpiryDate");
            return (Criteria) this;
        }

        public Criteria andProductExpiryDateNotIn(List<Date> values) {
            addCriterion("product_expiry_date not in", values, "productExpiryDate");
            return (Criteria) this;
        }

        public Criteria andProductExpiryDateBetween(Date value1, Date value2) {
            addCriterion("product_expiry_date between", value1, value2, "productExpiryDate");
            return (Criteria) this;
        }

        public Criteria andProductExpiryDateNotBetween(Date value1, Date value2) {
            addCriterion("product_expiry_date not between", value1, value2, "productExpiryDate");
            return (Criteria) this;
        }

        public Criteria andShipNameIsNull() {
            addCriterion("ship_name is null");
            return (Criteria) this;
        }

        public Criteria andShipNameIsNotNull() {
            addCriterion("ship_name is not null");
            return (Criteria) this;
        }

        public Criteria andShipNameEqualTo(String value) {
            addCriterion("ship_name =", value, "shipName");
            return (Criteria) this;
        }

        public Criteria andShipNameNotEqualTo(String value) {
            addCriterion("ship_name <>", value, "shipName");
            return (Criteria) this;
        }

        public Criteria andShipNameGreaterThan(String value) {
            addCriterion("ship_name >", value, "shipName");
            return (Criteria) this;
        }

        public Criteria andShipNameGreaterThanOrEqualTo(String value) {
            addCriterion("ship_name >=", value, "shipName");
            return (Criteria) this;
        }

        public Criteria andShipNameLessThan(String value) {
            addCriterion("ship_name <", value, "shipName");
            return (Criteria) this;
        }

        public Criteria andShipNameLessThanOrEqualTo(String value) {
            addCriterion("ship_name <=", value, "shipName");
            return (Criteria) this;
        }

        public Criteria andShipNameLike(String value) {
            addCriterion("ship_name like", value, "shipName");
            return (Criteria) this;
        }

        public Criteria andShipNameNotLike(String value) {
            addCriterion("ship_name not like", value, "shipName");
            return (Criteria) this;
        }

        public Criteria andShipNameIn(List<String> values) {
            addCriterion("ship_name in", values, "shipName");
            return (Criteria) this;
        }

        public Criteria andShipNameNotIn(List<String> values) {
            addCriterion("ship_name not in", values, "shipName");
            return (Criteria) this;
        }

        public Criteria andShipNameBetween(String value1, String value2) {
            addCriterion("ship_name between", value1, value2, "shipName");
            return (Criteria) this;
        }

        public Criteria andShipNameNotBetween(String value1, String value2) {
            addCriterion("ship_name not between", value1, value2, "shipName");
            return (Criteria) this;
        }

        public Criteria andVoyageNumberIsNull() {
            addCriterion("voyage_number is null");
            return (Criteria) this;
        }

        public Criteria andVoyageNumberIsNotNull() {
            addCriterion("voyage_number is not null");
            return (Criteria) this;
        }

        public Criteria andVoyageNumberEqualTo(String value) {
            addCriterion("voyage_number =", value, "voyageNumber");
            return (Criteria) this;
        }

        public Criteria andVoyageNumberNotEqualTo(String value) {
            addCriterion("voyage_number <>", value, "voyageNumber");
            return (Criteria) this;
        }

        public Criteria andVoyageNumberGreaterThan(String value) {
            addCriterion("voyage_number >", value, "voyageNumber");
            return (Criteria) this;
        }

        public Criteria andVoyageNumberGreaterThanOrEqualTo(String value) {
            addCriterion("voyage_number >=", value, "voyageNumber");
            return (Criteria) this;
        }

        public Criteria andVoyageNumberLessThan(String value) {
            addCriterion("voyage_number <", value, "voyageNumber");
            return (Criteria) this;
        }

        public Criteria andVoyageNumberLessThanOrEqualTo(String value) {
            addCriterion("voyage_number <=", value, "voyageNumber");
            return (Criteria) this;
        }

        public Criteria andVoyageNumberLike(String value) {
            addCriterion("voyage_number like", value, "voyageNumber");
            return (Criteria) this;
        }

        public Criteria andVoyageNumberNotLike(String value) {
            addCriterion("voyage_number not like", value, "voyageNumber");
            return (Criteria) this;
        }

        public Criteria andVoyageNumberIn(List<String> values) {
            addCriterion("voyage_number in", values, "voyageNumber");
            return (Criteria) this;
        }

        public Criteria andVoyageNumberNotIn(List<String> values) {
            addCriterion("voyage_number not in", values, "voyageNumber");
            return (Criteria) this;
        }

        public Criteria andVoyageNumberBetween(String value1, String value2) {
            addCriterion("voyage_number between", value1, value2, "voyageNumber");
            return (Criteria) this;
        }

        public Criteria andVoyageNumberNotBetween(String value1, String value2) {
            addCriterion("voyage_number not between", value1, value2, "voyageNumber");
            return (Criteria) this;
        }

        public Criteria andDistanceIsNull() {
            addCriterion("distance is null");
            return (Criteria) this;
        }

        public Criteria andDistanceIsNotNull() {
            addCriterion("distance is not null");
            return (Criteria) this;
        }

        public Criteria andDistanceEqualTo(String value) {
            addCriterion("distance =", value, "distance");
            return (Criteria) this;
        }

        public Criteria andDistanceNotEqualTo(String value) {
            addCriterion("distance <>", value, "distance");
            return (Criteria) this;
        }

        public Criteria andDistanceGreaterThan(String value) {
            addCriterion("distance >", value, "distance");
            return (Criteria) this;
        }

        public Criteria andDistanceGreaterThanOrEqualTo(String value) {
            addCriterion("distance >=", value, "distance");
            return (Criteria) this;
        }

        public Criteria andDistanceLessThan(String value) {
            addCriterion("distance <", value, "distance");
            return (Criteria) this;
        }

        public Criteria andDistanceLessThanOrEqualTo(String value) {
            addCriterion("distance <=", value, "distance");
            return (Criteria) this;
        }

        public Criteria andDistanceLike(String value) {
            addCriterion("distance like", value, "distance");
            return (Criteria) this;
        }

        public Criteria andDistanceNotLike(String value) {
            addCriterion("distance not like", value, "distance");
            return (Criteria) this;
        }

        public Criteria andDistanceIn(List<String> values) {
            addCriterion("distance in", values, "distance");
            return (Criteria) this;
        }

        public Criteria andDistanceNotIn(List<String> values) {
            addCriterion("distance not in", values, "distance");
            return (Criteria) this;
        }

        public Criteria andDistanceBetween(String value1, String value2) {
            addCriterion("distance between", value1, value2, "distance");
            return (Criteria) this;
        }

        public Criteria andDistanceNotBetween(String value1, String value2) {
            addCriterion("distance not between", value1, value2, "distance");
            return (Criteria) this;
        }

        public Criteria andCargoTypeIsNull() {
            addCriterion("cargo_type is null");
            return (Criteria) this;
        }

        public Criteria andCargoTypeIsNotNull() {
            addCriterion("cargo_type is not null");
            return (Criteria) this;
        }

        public Criteria andCargoTypeEqualTo(String value) {
            addCriterion("cargo_type =", value, "cargoType");
            return (Criteria) this;
        }

        public Criteria andCargoTypeNotEqualTo(String value) {
            addCriterion("cargo_type <>", value, "cargoType");
            return (Criteria) this;
        }

        public Criteria andCargoTypeGreaterThan(String value) {
            addCriterion("cargo_type >", value, "cargoType");
            return (Criteria) this;
        }

        public Criteria andCargoTypeGreaterThanOrEqualTo(String value) {
            addCriterion("cargo_type >=", value, "cargoType");
            return (Criteria) this;
        }

        public Criteria andCargoTypeLessThan(String value) {
            addCriterion("cargo_type <", value, "cargoType");
            return (Criteria) this;
        }

        public Criteria andCargoTypeLessThanOrEqualTo(String value) {
            addCriterion("cargo_type <=", value, "cargoType");
            return (Criteria) this;
        }

        public Criteria andCargoTypeLike(String value) {
            addCriterion("cargo_type like", value, "cargoType");
            return (Criteria) this;
        }

        public Criteria andCargoTypeNotLike(String value) {
            addCriterion("cargo_type not like", value, "cargoType");
            return (Criteria) this;
        }

        public Criteria andCargoTypeIn(List<String> values) {
            addCriterion("cargo_type in", values, "cargoType");
            return (Criteria) this;
        }

        public Criteria andCargoTypeNotIn(List<String> values) {
            addCriterion("cargo_type not in", values, "cargoType");
            return (Criteria) this;
        }

        public Criteria andCargoTypeBetween(String value1, String value2) {
            addCriterion("cargo_type between", value1, value2, "cargoType");
            return (Criteria) this;
        }

        public Criteria andCargoTypeNotBetween(String value1, String value2) {
            addCriterion("cargo_type not between", value1, value2, "cargoType");
            return (Criteria) this;
        }

        public Criteria andProductTypeIsNull() {
            addCriterion("product_type is null");
            return (Criteria) this;
        }

        public Criteria andProductTypeIsNotNull() {
            addCriterion("product_type is not null");
            return (Criteria) this;
        }

        public Criteria andProductTypeEqualTo(String value) {
            addCriterion("product_type =", value, "productType");
            return (Criteria) this;
        }

        public Criteria andProductTypeNotEqualTo(String value) {
            addCriterion("product_type <>", value, "productType");
            return (Criteria) this;
        }

        public Criteria andProductTypeGreaterThan(String value) {
            addCriterion("product_type >", value, "productType");
            return (Criteria) this;
        }

        public Criteria andProductTypeGreaterThanOrEqualTo(String value) {
            addCriterion("product_type >=", value, "productType");
            return (Criteria) this;
        }

        public Criteria andProductTypeLessThan(String value) {
            addCriterion("product_type <", value, "productType");
            return (Criteria) this;
        }

        public Criteria andProductTypeLessThanOrEqualTo(String value) {
            addCriterion("product_type <=", value, "productType");
            return (Criteria) this;
        }

        public Criteria andProductTypeLike(String value) {
            addCriterion("product_type like", value, "productType");
            return (Criteria) this;
        }

        public Criteria andProductTypeNotLike(String value) {
            addCriterion("product_type not like", value, "productType");
            return (Criteria) this;
        }

        public Criteria andProductTypeIn(List<String> values) {
            addCriterion("product_type in", values, "productType");
            return (Criteria) this;
        }

        public Criteria andProductTypeNotIn(List<String> values) {
            addCriterion("product_type not in", values, "productType");
            return (Criteria) this;
        }

        public Criteria andProductTypeBetween(String value1, String value2) {
            addCriterion("product_type between", value1, value2, "productType");
            return (Criteria) this;
        }

        public Criteria andProductTypeNotBetween(String value1, String value2) {
            addCriterion("product_type not between", value1, value2, "productType");
            return (Criteria) this;
        }

        public Criteria andReeferTemperatureIsNull() {
            addCriterion("reefer_temperature is null");
            return (Criteria) this;
        }

        public Criteria andReeferTemperatureIsNotNull() {
            addCriterion("reefer_temperature is not null");
            return (Criteria) this;
        }

        public Criteria andReeferTemperatureEqualTo(String value) {
            addCriterion("reefer_temperature =", value, "reeferTemperature");
            return (Criteria) this;
        }

        public Criteria andReeferTemperatureNotEqualTo(String value) {
            addCriterion("reefer_temperature <>", value, "reeferTemperature");
            return (Criteria) this;
        }

        public Criteria andReeferTemperatureGreaterThan(String value) {
            addCriterion("reefer_temperature >", value, "reeferTemperature");
            return (Criteria) this;
        }

        public Criteria andReeferTemperatureGreaterThanOrEqualTo(String value) {
            addCriterion("reefer_temperature >=", value, "reeferTemperature");
            return (Criteria) this;
        }

        public Criteria andReeferTemperatureLessThan(String value) {
            addCriterion("reefer_temperature <", value, "reeferTemperature");
            return (Criteria) this;
        }

        public Criteria andReeferTemperatureLessThanOrEqualTo(String value) {
            addCriterion("reefer_temperature <=", value, "reeferTemperature");
            return (Criteria) this;
        }

        public Criteria andReeferTemperatureLike(String value) {
            addCriterion("reefer_temperature like", value, "reeferTemperature");
            return (Criteria) this;
        }

        public Criteria andReeferTemperatureNotLike(String value) {
            addCriterion("reefer_temperature not like", value, "reeferTemperature");
            return (Criteria) this;
        }

        public Criteria andReeferTemperatureIn(List<String> values) {
            addCriterion("reefer_temperature in", values, "reeferTemperature");
            return (Criteria) this;
        }

        public Criteria andReeferTemperatureNotIn(List<String> values) {
            addCriterion("reefer_temperature not in", values, "reeferTemperature");
            return (Criteria) this;
        }

        public Criteria andReeferTemperatureBetween(String value1, String value2) {
            addCriterion("reefer_temperature between", value1, value2, "reeferTemperature");
            return (Criteria) this;
        }

        public Criteria andReeferTemperatureNotBetween(String value1, String value2) {
            addCriterion("reefer_temperature not between", value1, value2, "reeferTemperature");
            return (Criteria) this;
        }

        public Criteria andReeferHumidityIsNull() {
            addCriterion("reefer_humidity is null");
            return (Criteria) this;
        }

        public Criteria andReeferHumidityIsNotNull() {
            addCriterion("reefer_humidity is not null");
            return (Criteria) this;
        }

        public Criteria andReeferHumidityEqualTo(String value) {
            addCriterion("reefer_humidity =", value, "reeferHumidity");
            return (Criteria) this;
        }

        public Criteria andReeferHumidityNotEqualTo(String value) {
            addCriterion("reefer_humidity <>", value, "reeferHumidity");
            return (Criteria) this;
        }

        public Criteria andReeferHumidityGreaterThan(String value) {
            addCriterion("reefer_humidity >", value, "reeferHumidity");
            return (Criteria) this;
        }

        public Criteria andReeferHumidityGreaterThanOrEqualTo(String value) {
            addCriterion("reefer_humidity >=", value, "reeferHumidity");
            return (Criteria) this;
        }

        public Criteria andReeferHumidityLessThan(String value) {
            addCriterion("reefer_humidity <", value, "reeferHumidity");
            return (Criteria) this;
        }

        public Criteria andReeferHumidityLessThanOrEqualTo(String value) {
            addCriterion("reefer_humidity <=", value, "reeferHumidity");
            return (Criteria) this;
        }

        public Criteria andReeferHumidityLike(String value) {
            addCriterion("reefer_humidity like", value, "reeferHumidity");
            return (Criteria) this;
        }

        public Criteria andReeferHumidityNotLike(String value) {
            addCriterion("reefer_humidity not like", value, "reeferHumidity");
            return (Criteria) this;
        }

        public Criteria andReeferHumidityIn(List<String> values) {
            addCriterion("reefer_humidity in", values, "reeferHumidity");
            return (Criteria) this;
        }

        public Criteria andReeferHumidityNotIn(List<String> values) {
            addCriterion("reefer_humidity not in", values, "reeferHumidity");
            return (Criteria) this;
        }

        public Criteria andReeferHumidityBetween(String value1, String value2) {
            addCriterion("reefer_humidity between", value1, value2, "reeferHumidity");
            return (Criteria) this;
        }

        public Criteria andReeferHumidityNotBetween(String value1, String value2) {
            addCriterion("reefer_humidity not between", value1, value2, "reeferHumidity");
            return (Criteria) this;
        }

        public Criteria andReeferVentilationRequirementsIsNull() {
            addCriterion("reefer_ventilation_requirements is null");
            return (Criteria) this;
        }

        public Criteria andReeferVentilationRequirementsIsNotNull() {
            addCriterion("reefer_ventilation_requirements is not null");
            return (Criteria) this;
        }

        public Criteria andReeferVentilationRequirementsEqualTo(String value) {
            addCriterion("reefer_ventilation_requirements =", value, "reeferVentilationRequirements");
            return (Criteria) this;
        }

        public Criteria andReeferVentilationRequirementsNotEqualTo(String value) {
            addCriterion("reefer_ventilation_requirements <>", value, "reeferVentilationRequirements");
            return (Criteria) this;
        }

        public Criteria andReeferVentilationRequirementsGreaterThan(String value) {
            addCriterion("reefer_ventilation_requirements >", value, "reeferVentilationRequirements");
            return (Criteria) this;
        }

        public Criteria andReeferVentilationRequirementsGreaterThanOrEqualTo(String value) {
            addCriterion("reefer_ventilation_requirements >=", value, "reeferVentilationRequirements");
            return (Criteria) this;
        }

        public Criteria andReeferVentilationRequirementsLessThan(String value) {
            addCriterion("reefer_ventilation_requirements <", value, "reeferVentilationRequirements");
            return (Criteria) this;
        }

        public Criteria andReeferVentilationRequirementsLessThanOrEqualTo(String value) {
            addCriterion("reefer_ventilation_requirements <=", value, "reeferVentilationRequirements");
            return (Criteria) this;
        }

        public Criteria andReeferVentilationRequirementsLike(String value) {
            addCriterion("reefer_ventilation_requirements like", value, "reeferVentilationRequirements");
            return (Criteria) this;
        }

        public Criteria andReeferVentilationRequirementsNotLike(String value) {
            addCriterion("reefer_ventilation_requirements not like", value, "reeferVentilationRequirements");
            return (Criteria) this;
        }

        public Criteria andReeferVentilationRequirementsIn(List<String> values) {
            addCriterion("reefer_ventilation_requirements in", values, "reeferVentilationRequirements");
            return (Criteria) this;
        }

        public Criteria andReeferVentilationRequirementsNotIn(List<String> values) {
            addCriterion("reefer_ventilation_requirements not in", values, "reeferVentilationRequirements");
            return (Criteria) this;
        }

        public Criteria andReeferVentilationRequirementsBetween(String value1, String value2) {
            addCriterion("reefer_ventilation_requirements between", value1, value2, "reeferVentilationRequirements");
            return (Criteria) this;
        }

        public Criteria andReeferVentilationRequirementsNotBetween(String value1, String value2) {
            addCriterion("reefer_ventilation_requirements not between", value1, value2, "reeferVentilationRequirements");
            return (Criteria) this;
        }

        public Criteria andSpecialContainerRemarksIsNull() {
            addCriterion("special_container_remarks is null");
            return (Criteria) this;
        }

        public Criteria andSpecialContainerRemarksIsNotNull() {
            addCriterion("special_container_remarks is not null");
            return (Criteria) this;
        }

        public Criteria andSpecialContainerRemarksEqualTo(String value) {
            addCriterion("special_container_remarks =", value, "specialContainerRemarks");
            return (Criteria) this;
        }

        public Criteria andSpecialContainerRemarksNotEqualTo(String value) {
            addCriterion("special_container_remarks <>", value, "specialContainerRemarks");
            return (Criteria) this;
        }

        public Criteria andSpecialContainerRemarksGreaterThan(String value) {
            addCriterion("special_container_remarks >", value, "specialContainerRemarks");
            return (Criteria) this;
        }

        public Criteria andSpecialContainerRemarksGreaterThanOrEqualTo(String value) {
            addCriterion("special_container_remarks >=", value, "specialContainerRemarks");
            return (Criteria) this;
        }

        public Criteria andSpecialContainerRemarksLessThan(String value) {
            addCriterion("special_container_remarks <", value, "specialContainerRemarks");
            return (Criteria) this;
        }

        public Criteria andSpecialContainerRemarksLessThanOrEqualTo(String value) {
            addCriterion("special_container_remarks <=", value, "specialContainerRemarks");
            return (Criteria) this;
        }

        public Criteria andSpecialContainerRemarksLike(String value) {
            addCriterion("special_container_remarks like", value, "specialContainerRemarks");
            return (Criteria) this;
        }

        public Criteria andSpecialContainerRemarksNotLike(String value) {
            addCriterion("special_container_remarks not like", value, "specialContainerRemarks");
            return (Criteria) this;
        }

        public Criteria andSpecialContainerRemarksIn(List<String> values) {
            addCriterion("special_container_remarks in", values, "specialContainerRemarks");
            return (Criteria) this;
        }

        public Criteria andSpecialContainerRemarksNotIn(List<String> values) {
            addCriterion("special_container_remarks not in", values, "specialContainerRemarks");
            return (Criteria) this;
        }

        public Criteria andSpecialContainerRemarksBetween(String value1, String value2) {
            addCriterion("special_container_remarks between", value1, value2, "specialContainerRemarks");
            return (Criteria) this;
        }

        public Criteria andSpecialContainerRemarksNotBetween(String value1, String value2) {
            addCriterion("special_container_remarks not between", value1, value2, "specialContainerRemarks");
            return (Criteria) this;
        }

        public Criteria andDangerousGoodsUnNumberIsNull() {
            addCriterion("dangerous_goods_un_number is null");
            return (Criteria) this;
        }

        public Criteria andDangerousGoodsUnNumberIsNotNull() {
            addCriterion("dangerous_goods_un_number is not null");
            return (Criteria) this;
        }

        public Criteria andDangerousGoodsUnNumberEqualTo(String value) {
            addCriterion("dangerous_goods_un_number =", value, "dangerousGoodsUnNumber");
            return (Criteria) this;
        }

        public Criteria andDangerousGoodsUnNumberNotEqualTo(String value) {
            addCriterion("dangerous_goods_un_number <>", value, "dangerousGoodsUnNumber");
            return (Criteria) this;
        }

        public Criteria andDangerousGoodsUnNumberGreaterThan(String value) {
            addCriterion("dangerous_goods_un_number >", value, "dangerousGoodsUnNumber");
            return (Criteria) this;
        }

        public Criteria andDangerousGoodsUnNumberGreaterThanOrEqualTo(String value) {
            addCriterion("dangerous_goods_un_number >=", value, "dangerousGoodsUnNumber");
            return (Criteria) this;
        }

        public Criteria andDangerousGoodsUnNumberLessThan(String value) {
            addCriterion("dangerous_goods_un_number <", value, "dangerousGoodsUnNumber");
            return (Criteria) this;
        }

        public Criteria andDangerousGoodsUnNumberLessThanOrEqualTo(String value) {
            addCriterion("dangerous_goods_un_number <=", value, "dangerousGoodsUnNumber");
            return (Criteria) this;
        }

        public Criteria andDangerousGoodsUnNumberLike(String value) {
            addCriterion("dangerous_goods_un_number like", value, "dangerousGoodsUnNumber");
            return (Criteria) this;
        }

        public Criteria andDangerousGoodsUnNumberNotLike(String value) {
            addCriterion("dangerous_goods_un_number not like", value, "dangerousGoodsUnNumber");
            return (Criteria) this;
        }

        public Criteria andDangerousGoodsUnNumberIn(List<String> values) {
            addCriterion("dangerous_goods_un_number in", values, "dangerousGoodsUnNumber");
            return (Criteria) this;
        }

        public Criteria andDangerousGoodsUnNumberNotIn(List<String> values) {
            addCriterion("dangerous_goods_un_number not in", values, "dangerousGoodsUnNumber");
            return (Criteria) this;
        }

        public Criteria andDangerousGoodsUnNumberBetween(String value1, String value2) {
            addCriterion("dangerous_goods_un_number between", value1, value2, "dangerousGoodsUnNumber");
            return (Criteria) this;
        }

        public Criteria andDangerousGoodsUnNumberNotBetween(String value1, String value2) {
            addCriterion("dangerous_goods_un_number not between", value1, value2, "dangerousGoodsUnNumber");
            return (Criteria) this;
        }

        public Criteria andDangerousGoodsClassIsNull() {
            addCriterion("dangerous_goods_class is null");
            return (Criteria) this;
        }

        public Criteria andDangerousGoodsClassIsNotNull() {
            addCriterion("dangerous_goods_class is not null");
            return (Criteria) this;
        }

        public Criteria andDangerousGoodsClassEqualTo(String value) {
            addCriterion("dangerous_goods_class =", value, "dangerousGoodsClass");
            return (Criteria) this;
        }

        public Criteria andDangerousGoodsClassNotEqualTo(String value) {
            addCriterion("dangerous_goods_class <>", value, "dangerousGoodsClass");
            return (Criteria) this;
        }

        public Criteria andDangerousGoodsClassGreaterThan(String value) {
            addCriterion("dangerous_goods_class >", value, "dangerousGoodsClass");
            return (Criteria) this;
        }

        public Criteria andDangerousGoodsClassGreaterThanOrEqualTo(String value) {
            addCriterion("dangerous_goods_class >=", value, "dangerousGoodsClass");
            return (Criteria) this;
        }

        public Criteria andDangerousGoodsClassLessThan(String value) {
            addCriterion("dangerous_goods_class <", value, "dangerousGoodsClass");
            return (Criteria) this;
        }

        public Criteria andDangerousGoodsClassLessThanOrEqualTo(String value) {
            addCriterion("dangerous_goods_class <=", value, "dangerousGoodsClass");
            return (Criteria) this;
        }

        public Criteria andDangerousGoodsClassLike(String value) {
            addCriterion("dangerous_goods_class like", value, "dangerousGoodsClass");
            return (Criteria) this;
        }

        public Criteria andDangerousGoodsClassNotLike(String value) {
            addCriterion("dangerous_goods_class not like", value, "dangerousGoodsClass");
            return (Criteria) this;
        }

        public Criteria andDangerousGoodsClassIn(List<String> values) {
            addCriterion("dangerous_goods_class in", values, "dangerousGoodsClass");
            return (Criteria) this;
        }

        public Criteria andDangerousGoodsClassNotIn(List<String> values) {
            addCriterion("dangerous_goods_class not in", values, "dangerousGoodsClass");
            return (Criteria) this;
        }

        public Criteria andDangerousGoodsClassBetween(String value1, String value2) {
            addCriterion("dangerous_goods_class between", value1, value2, "dangerousGoodsClass");
            return (Criteria) this;
        }

        public Criteria andDangerousGoodsClassNotBetween(String value1, String value2) {
            addCriterion("dangerous_goods_class not between", value1, value2, "dangerousGoodsClass");
            return (Criteria) this;
        }

        public Criteria andDangerousGoodsRemarksIsNull() {
            addCriterion("dangerous_goods_remarks is null");
            return (Criteria) this;
        }

        public Criteria andDangerousGoodsRemarksIsNotNull() {
            addCriterion("dangerous_goods_remarks is not null");
            return (Criteria) this;
        }

        public Criteria andDangerousGoodsRemarksEqualTo(String value) {
            addCriterion("dangerous_goods_remarks =", value, "dangerousGoodsRemarks");
            return (Criteria) this;
        }

        public Criteria andDangerousGoodsRemarksNotEqualTo(String value) {
            addCriterion("dangerous_goods_remarks <>", value, "dangerousGoodsRemarks");
            return (Criteria) this;
        }

        public Criteria andDangerousGoodsRemarksGreaterThan(String value) {
            addCriterion("dangerous_goods_remarks >", value, "dangerousGoodsRemarks");
            return (Criteria) this;
        }

        public Criteria andDangerousGoodsRemarksGreaterThanOrEqualTo(String value) {
            addCriterion("dangerous_goods_remarks >=", value, "dangerousGoodsRemarks");
            return (Criteria) this;
        }

        public Criteria andDangerousGoodsRemarksLessThan(String value) {
            addCriterion("dangerous_goods_remarks <", value, "dangerousGoodsRemarks");
            return (Criteria) this;
        }

        public Criteria andDangerousGoodsRemarksLessThanOrEqualTo(String value) {
            addCriterion("dangerous_goods_remarks <=", value, "dangerousGoodsRemarks");
            return (Criteria) this;
        }

        public Criteria andDangerousGoodsRemarksLike(String value) {
            addCriterion("dangerous_goods_remarks like", value, "dangerousGoodsRemarks");
            return (Criteria) this;
        }

        public Criteria andDangerousGoodsRemarksNotLike(String value) {
            addCriterion("dangerous_goods_remarks not like", value, "dangerousGoodsRemarks");
            return (Criteria) this;
        }

        public Criteria andDangerousGoodsRemarksIn(List<String> values) {
            addCriterion("dangerous_goods_remarks in", values, "dangerousGoodsRemarks");
            return (Criteria) this;
        }

        public Criteria andDangerousGoodsRemarksNotIn(List<String> values) {
            addCriterion("dangerous_goods_remarks not in", values, "dangerousGoodsRemarks");
            return (Criteria) this;
        }

        public Criteria andDangerousGoodsRemarksBetween(String value1, String value2) {
            addCriterion("dangerous_goods_remarks between", value1, value2, "dangerousGoodsRemarks");
            return (Criteria) this;
        }

        public Criteria andDangerousGoodsRemarksNotBetween(String value1, String value2) {
            addCriterion("dangerous_goods_remarks not between", value1, value2, "dangerousGoodsRemarks");
            return (Criteria) this;
        }

        public Criteria andWaiverOfContainerInstructionsIsNull() {
            addCriterion("waiver_of_container_instructions is null");
            return (Criteria) this;
        }

        public Criteria andWaiverOfContainerInstructionsIsNotNull() {
            addCriterion("waiver_of_container_instructions is not null");
            return (Criteria) this;
        }

        public Criteria andWaiverOfContainerInstructionsEqualTo(String value) {
            addCriterion("waiver_of_container_instructions =", value, "waiverOfContainerInstructions");
            return (Criteria) this;
        }

        public Criteria andWaiverOfContainerInstructionsNotEqualTo(String value) {
            addCriterion("waiver_of_container_instructions <>", value, "waiverOfContainerInstructions");
            return (Criteria) this;
        }

        public Criteria andWaiverOfContainerInstructionsGreaterThan(String value) {
            addCriterion("waiver_of_container_instructions >", value, "waiverOfContainerInstructions");
            return (Criteria) this;
        }

        public Criteria andWaiverOfContainerInstructionsGreaterThanOrEqualTo(String value) {
            addCriterion("waiver_of_container_instructions >=", value, "waiverOfContainerInstructions");
            return (Criteria) this;
        }

        public Criteria andWaiverOfContainerInstructionsLessThan(String value) {
            addCriterion("waiver_of_container_instructions <", value, "waiverOfContainerInstructions");
            return (Criteria) this;
        }

        public Criteria andWaiverOfContainerInstructionsLessThanOrEqualTo(String value) {
            addCriterion("waiver_of_container_instructions <=", value, "waiverOfContainerInstructions");
            return (Criteria) this;
        }

        public Criteria andWaiverOfContainerInstructionsLike(String value) {
            addCriterion("waiver_of_container_instructions like", value, "waiverOfContainerInstructions");
            return (Criteria) this;
        }

        public Criteria andWaiverOfContainerInstructionsNotLike(String value) {
            addCriterion("waiver_of_container_instructions not like", value, "waiverOfContainerInstructions");
            return (Criteria) this;
        }

        public Criteria andWaiverOfContainerInstructionsIn(List<String> values) {
            addCriterion("waiver_of_container_instructions in", values, "waiverOfContainerInstructions");
            return (Criteria) this;
        }

        public Criteria andWaiverOfContainerInstructionsNotIn(List<String> values) {
            addCriterion("waiver_of_container_instructions not in", values, "waiverOfContainerInstructions");
            return (Criteria) this;
        }

        public Criteria andWaiverOfContainerInstructionsBetween(String value1, String value2) {
            addCriterion("waiver_of_container_instructions between", value1, value2, "waiverOfContainerInstructions");
            return (Criteria) this;
        }

        public Criteria andWaiverOfContainerInstructionsNotBetween(String value1, String value2) {
            addCriterion("waiver_of_container_instructions not between", value1, value2, "waiverOfContainerInstructions");
            return (Criteria) this;
        }

        public Criteria andDeficitFreightInstructionsIsNull() {
            addCriterion("deficit_freight_instructions is null");
            return (Criteria) this;
        }

        public Criteria andDeficitFreightInstructionsIsNotNull() {
            addCriterion("deficit_freight_instructions is not null");
            return (Criteria) this;
        }

        public Criteria andDeficitFreightInstructionsEqualTo(String value) {
            addCriterion("deficit_freight_instructions =", value, "deficitFreightInstructions");
            return (Criteria) this;
        }

        public Criteria andDeficitFreightInstructionsNotEqualTo(String value) {
            addCriterion("deficit_freight_instructions <>", value, "deficitFreightInstructions");
            return (Criteria) this;
        }

        public Criteria andDeficitFreightInstructionsGreaterThan(String value) {
            addCriterion("deficit_freight_instructions >", value, "deficitFreightInstructions");
            return (Criteria) this;
        }

        public Criteria andDeficitFreightInstructionsGreaterThanOrEqualTo(String value) {
            addCriterion("deficit_freight_instructions >=", value, "deficitFreightInstructions");
            return (Criteria) this;
        }

        public Criteria andDeficitFreightInstructionsLessThan(String value) {
            addCriterion("deficit_freight_instructions <", value, "deficitFreightInstructions");
            return (Criteria) this;
        }

        public Criteria andDeficitFreightInstructionsLessThanOrEqualTo(String value) {
            addCriterion("deficit_freight_instructions <=", value, "deficitFreightInstructions");
            return (Criteria) this;
        }

        public Criteria andDeficitFreightInstructionsLike(String value) {
            addCriterion("deficit_freight_instructions like", value, "deficitFreightInstructions");
            return (Criteria) this;
        }

        public Criteria andDeficitFreightInstructionsNotLike(String value) {
            addCriterion("deficit_freight_instructions not like", value, "deficitFreightInstructions");
            return (Criteria) this;
        }

        public Criteria andDeficitFreightInstructionsIn(List<String> values) {
            addCriterion("deficit_freight_instructions in", values, "deficitFreightInstructions");
            return (Criteria) this;
        }

        public Criteria andDeficitFreightInstructionsNotIn(List<String> values) {
            addCriterion("deficit_freight_instructions not in", values, "deficitFreightInstructions");
            return (Criteria) this;
        }

        public Criteria andDeficitFreightInstructionsBetween(String value1, String value2) {
            addCriterion("deficit_freight_instructions between", value1, value2, "deficitFreightInstructions");
            return (Criteria) this;
        }

        public Criteria andDeficitFreightInstructionsNotBetween(String value1, String value2) {
            addCriterion("deficit_freight_instructions not between", value1, value2, "deficitFreightInstructions");
            return (Criteria) this;
        }

        public Criteria andCustomerRemarksIsNull() {
            addCriterion("customer_remarks is null");
            return (Criteria) this;
        }

        public Criteria andCustomerRemarksIsNotNull() {
            addCriterion("customer_remarks is not null");
            return (Criteria) this;
        }

        public Criteria andCustomerRemarksEqualTo(String value) {
            addCriterion("customer_remarks =", value, "customerRemarks");
            return (Criteria) this;
        }

        public Criteria andCustomerRemarksNotEqualTo(String value) {
            addCriterion("customer_remarks <>", value, "customerRemarks");
            return (Criteria) this;
        }

        public Criteria andCustomerRemarksGreaterThan(String value) {
            addCriterion("customer_remarks >", value, "customerRemarks");
            return (Criteria) this;
        }

        public Criteria andCustomerRemarksGreaterThanOrEqualTo(String value) {
            addCriterion("customer_remarks >=", value, "customerRemarks");
            return (Criteria) this;
        }

        public Criteria andCustomerRemarksLessThan(String value) {
            addCriterion("customer_remarks <", value, "customerRemarks");
            return (Criteria) this;
        }

        public Criteria andCustomerRemarksLessThanOrEqualTo(String value) {
            addCriterion("customer_remarks <=", value, "customerRemarks");
            return (Criteria) this;
        }

        public Criteria andCustomerRemarksLike(String value) {
            addCriterion("customer_remarks like", value, "customerRemarks");
            return (Criteria) this;
        }

        public Criteria andCustomerRemarksNotLike(String value) {
            addCriterion("customer_remarks not like", value, "customerRemarks");
            return (Criteria) this;
        }

        public Criteria andCustomerRemarksIn(List<String> values) {
            addCriterion("customer_remarks in", values, "customerRemarks");
            return (Criteria) this;
        }

        public Criteria andCustomerRemarksNotIn(List<String> values) {
            addCriterion("customer_remarks not in", values, "customerRemarks");
            return (Criteria) this;
        }

        public Criteria andCustomerRemarksBetween(String value1, String value2) {
            addCriterion("customer_remarks between", value1, value2, "customerRemarks");
            return (Criteria) this;
        }

        public Criteria andCustomerRemarksNotBetween(String value1, String value2) {
            addCriterion("customer_remarks not between", value1, value2, "customerRemarks");
            return (Criteria) this;
        }

        public Criteria andPublisherIsNull() {
            addCriterion("publisher is null");
            return (Criteria) this;
        }

        public Criteria andPublisherIsNotNull() {
            addCriterion("publisher is not null");
            return (Criteria) this;
        }

        public Criteria andPublisherEqualTo(Long value) {
            addCriterion("publisher =", value, "publisher");
            return (Criteria) this;
        }

        public Criteria andPublisherNotEqualTo(Long value) {
            addCriterion("publisher <>", value, "publisher");
            return (Criteria) this;
        }

        public Criteria andPublisherGreaterThan(Long value) {
            addCriterion("publisher >", value, "publisher");
            return (Criteria) this;
        }

        public Criteria andPublisherGreaterThanOrEqualTo(Long value) {
            addCriterion("publisher >=", value, "publisher");
            return (Criteria) this;
        }

        public Criteria andPublisherLessThan(Long value) {
            addCriterion("publisher <", value, "publisher");
            return (Criteria) this;
        }

        public Criteria andPublisherLessThanOrEqualTo(Long value) {
            addCriterion("publisher <=", value, "publisher");
            return (Criteria) this;
        }

        public Criteria andPublisherIn(List<Long> values) {
            addCriterion("publisher in", values, "publisher");
            return (Criteria) this;
        }

        public Criteria andPublisherNotIn(List<Long> values) {
            addCriterion("publisher not in", values, "publisher");
            return (Criteria) this;
        }

        public Criteria andPublisherBetween(Long value1, Long value2) {
            addCriterion("publisher between", value1, value2, "publisher");
            return (Criteria) this;
        }

        public Criteria andPublisherNotBetween(Long value1, Long value2) {
            addCriterion("publisher not between", value1, value2, "publisher");
            return (Criteria) this;
        }

        public Criteria andPublisherPhoneIsNull() {
            addCriterion("publisher_phone is null");
            return (Criteria) this;
        }

        public Criteria andPublisherPhoneIsNotNull() {
            addCriterion("publisher_phone is not null");
            return (Criteria) this;
        }

        public Criteria andPublisherPhoneEqualTo(String value) {
            addCriterion("publisher_phone =", value, "publisherPhone");
            return (Criteria) this;
        }

        public Criteria andPublisherPhoneNotEqualTo(String value) {
            addCriterion("publisher_phone <>", value, "publisherPhone");
            return (Criteria) this;
        }

        public Criteria andPublisherPhoneGreaterThan(String value) {
            addCriterion("publisher_phone >", value, "publisherPhone");
            return (Criteria) this;
        }

        public Criteria andPublisherPhoneGreaterThanOrEqualTo(String value) {
            addCriterion("publisher_phone >=", value, "publisherPhone");
            return (Criteria) this;
        }

        public Criteria andPublisherPhoneLessThan(String value) {
            addCriterion("publisher_phone <", value, "publisherPhone");
            return (Criteria) this;
        }

        public Criteria andPublisherPhoneLessThanOrEqualTo(String value) {
            addCriterion("publisher_phone <=", value, "publisherPhone");
            return (Criteria) this;
        }

        public Criteria andPublisherPhoneLike(String value) {
            addCriterion("publisher_phone like", value, "publisherPhone");
            return (Criteria) this;
        }

        public Criteria andPublisherPhoneNotLike(String value) {
            addCriterion("publisher_phone not like", value, "publisherPhone");
            return (Criteria) this;
        }

        public Criteria andPublisherPhoneIn(List<String> values) {
            addCriterion("publisher_phone in", values, "publisherPhone");
            return (Criteria) this;
        }

        public Criteria andPublisherPhoneNotIn(List<String> values) {
            addCriterion("publisher_phone not in", values, "publisherPhone");
            return (Criteria) this;
        }

        public Criteria andPublisherPhoneBetween(String value1, String value2) {
            addCriterion("publisher_phone between", value1, value2, "publisherPhone");
            return (Criteria) this;
        }

        public Criteria andPublisherPhoneNotBetween(String value1, String value2) {
            addCriterion("publisher_phone not between", value1, value2, "publisherPhone");
            return (Criteria) this;
        }

        public Criteria andProductOwnerIsNull() {
            addCriterion("product_owner is null");
            return (Criteria) this;
        }

        public Criteria andProductOwnerIsNotNull() {
            addCriterion("product_owner is not null");
            return (Criteria) this;
        }

        public Criteria andProductOwnerEqualTo(Long value) {
            addCriterion("product_owner =", value, "productOwner");
            return (Criteria) this;
        }

        public Criteria andProductOwnerNotEqualTo(Long value) {
            addCriterion("product_owner <>", value, "productOwner");
            return (Criteria) this;
        }

        public Criteria andProductOwnerGreaterThan(Long value) {
            addCriterion("product_owner >", value, "productOwner");
            return (Criteria) this;
        }

        public Criteria andProductOwnerGreaterThanOrEqualTo(Long value) {
            addCriterion("product_owner >=", value, "productOwner");
            return (Criteria) this;
        }

        public Criteria andProductOwnerLessThan(Long value) {
            addCriterion("product_owner <", value, "productOwner");
            return (Criteria) this;
        }

        public Criteria andProductOwnerLessThanOrEqualTo(Long value) {
            addCriterion("product_owner <=", value, "productOwner");
            return (Criteria) this;
        }

        public Criteria andProductOwnerIn(List<Long> values) {
            addCriterion("product_owner in", values, "productOwner");
            return (Criteria) this;
        }

        public Criteria andProductOwnerNotIn(List<Long> values) {
            addCriterion("product_owner not in", values, "productOwner");
            return (Criteria) this;
        }

        public Criteria andProductOwnerBetween(Long value1, Long value2) {
            addCriterion("product_owner between", value1, value2, "productOwner");
            return (Criteria) this;
        }

        public Criteria andProductOwnerNotBetween(Long value1, Long value2) {
            addCriterion("product_owner not between", value1, value2, "productOwner");
            return (Criteria) this;
        }

        public Criteria andAgentCompanyIsNull() {
            addCriterion("agent_company is null");
            return (Criteria) this;
        }

        public Criteria andAgentCompanyIsNotNull() {
            addCriterion("agent_company is not null");
            return (Criteria) this;
        }

        public Criteria andAgentCompanyEqualTo(String value) {
            addCriterion("agent_company =", value, "agentCompany");
            return (Criteria) this;
        }

        public Criteria andAgentCompanyNotEqualTo(String value) {
            addCriterion("agent_company <>", value, "agentCompany");
            return (Criteria) this;
        }

        public Criteria andAgentCompanyGreaterThan(String value) {
            addCriterion("agent_company >", value, "agentCompany");
            return (Criteria) this;
        }

        public Criteria andAgentCompanyGreaterThanOrEqualTo(String value) {
            addCriterion("agent_company >=", value, "agentCompany");
            return (Criteria) this;
        }

        public Criteria andAgentCompanyLessThan(String value) {
            addCriterion("agent_company <", value, "agentCompany");
            return (Criteria) this;
        }

        public Criteria andAgentCompanyLessThanOrEqualTo(String value) {
            addCriterion("agent_company <=", value, "agentCompany");
            return (Criteria) this;
        }

        public Criteria andAgentCompanyLike(String value) {
            addCriterion("agent_company like", value, "agentCompany");
            return (Criteria) this;
        }

        public Criteria andAgentCompanyNotLike(String value) {
            addCriterion("agent_company not like", value, "agentCompany");
            return (Criteria) this;
        }

        public Criteria andAgentCompanyIn(List<String> values) {
            addCriterion("agent_company in", values, "agentCompany");
            return (Criteria) this;
        }

        public Criteria andAgentCompanyNotIn(List<String> values) {
            addCriterion("agent_company not in", values, "agentCompany");
            return (Criteria) this;
        }

        public Criteria andAgentCompanyBetween(String value1, String value2) {
            addCriterion("agent_company between", value1, value2, "agentCompany");
            return (Criteria) this;
        }

        public Criteria andAgentCompanyNotBetween(String value1, String value2) {
            addCriterion("agent_company not between", value1, value2, "agentCompany");
            return (Criteria) this;
        }

        public Criteria andProductRemarksIsNull() {
            addCriterion("product_remarks is null");
            return (Criteria) this;
        }

        public Criteria andProductRemarksIsNotNull() {
            addCriterion("product_remarks is not null");
            return (Criteria) this;
        }

        public Criteria andProductRemarksEqualTo(String value) {
            addCriterion("product_remarks =", value, "productRemarks");
            return (Criteria) this;
        }

        public Criteria andProductRemarksNotEqualTo(String value) {
            addCriterion("product_remarks <>", value, "productRemarks");
            return (Criteria) this;
        }

        public Criteria andProductRemarksGreaterThan(String value) {
            addCriterion("product_remarks >", value, "productRemarks");
            return (Criteria) this;
        }

        public Criteria andProductRemarksGreaterThanOrEqualTo(String value) {
            addCriterion("product_remarks >=", value, "productRemarks");
            return (Criteria) this;
        }

        public Criteria andProductRemarksLessThan(String value) {
            addCriterion("product_remarks <", value, "productRemarks");
            return (Criteria) this;
        }

        public Criteria andProductRemarksLessThanOrEqualTo(String value) {
            addCriterion("product_remarks <=", value, "productRemarks");
            return (Criteria) this;
        }

        public Criteria andProductRemarksLike(String value) {
            addCriterion("product_remarks like", value, "productRemarks");
            return (Criteria) this;
        }

        public Criteria andProductRemarksNotLike(String value) {
            addCriterion("product_remarks not like", value, "productRemarks");
            return (Criteria) this;
        }

        public Criteria andProductRemarksIn(List<String> values) {
            addCriterion("product_remarks in", values, "productRemarks");
            return (Criteria) this;
        }

        public Criteria andProductRemarksNotIn(List<String> values) {
            addCriterion("product_remarks not in", values, "productRemarks");
            return (Criteria) this;
        }

        public Criteria andProductRemarksBetween(String value1, String value2) {
            addCriterion("product_remarks between", value1, value2, "productRemarks");
            return (Criteria) this;
        }

        public Criteria andProductRemarksNotBetween(String value1, String value2) {
            addCriterion("product_remarks not between", value1, value2, "productRemarks");
            return (Criteria) this;
        }

        public Criteria andSubmissionTimeIsNull() {
            addCriterion("submission_time is null");
            return (Criteria) this;
        }

        public Criteria andSubmissionTimeIsNotNull() {
            addCriterion("submission_time is not null");
            return (Criteria) this;
        }

        public Criteria andSubmissionTimeEqualTo(Date value) {
            addCriterion("submission_time =", value, "submissionTime");
            return (Criteria) this;
        }

        public Criteria andSubmissionTimeNotEqualTo(Date value) {
            addCriterion("submission_time <>", value, "submissionTime");
            return (Criteria) this;
        }

        public Criteria andSubmissionTimeGreaterThan(Date value) {
            addCriterion("submission_time >", value, "submissionTime");
            return (Criteria) this;
        }

        public Criteria andSubmissionTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("submission_time >=", value, "submissionTime");
            return (Criteria) this;
        }

        public Criteria andSubmissionTimeLessThan(Date value) {
            addCriterion("submission_time <", value, "submissionTime");
            return (Criteria) this;
        }

        public Criteria andSubmissionTimeLessThanOrEqualTo(Date value) {
            addCriterion("submission_time <=", value, "submissionTime");
            return (Criteria) this;
        }

        public Criteria andSubmissionTimeIn(List<Date> values) {
            addCriterion("submission_time in", values, "submissionTime");
            return (Criteria) this;
        }

        public Criteria andSubmissionTimeNotIn(List<Date> values) {
            addCriterion("submission_time not in", values, "submissionTime");
            return (Criteria) this;
        }

        public Criteria andSubmissionTimeBetween(Date value1, Date value2) {
            addCriterion("submission_time between", value1, value2, "submissionTime");
            return (Criteria) this;
        }

        public Criteria andSubmissionTimeNotBetween(Date value1, Date value2) {
            addCriterion("submission_time not between", value1, value2, "submissionTime");
            return (Criteria) this;
        }

        public Criteria andReviewStatusIsNull() {
            addCriterion("review_status is null");
            return (Criteria) this;
        }

        public Criteria andReviewStatusIsNotNull() {
            addCriterion("review_status is not null");
            return (Criteria) this;
        }

        public Criteria andReviewStatusEqualTo(Integer value) {
            addCriterion("review_status =", value, "reviewStatus");
            return (Criteria) this;
        }

        public Criteria andReviewStatusNotEqualTo(Integer value) {
            addCriterion("review_status <>", value, "reviewStatus");
            return (Criteria) this;
        }

        public Criteria andReviewStatusGreaterThan(Integer value) {
            addCriterion("review_status >", value, "reviewStatus");
            return (Criteria) this;
        }

        public Criteria andReviewStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("review_status >=", value, "reviewStatus");
            return (Criteria) this;
        }

        public Criteria andReviewStatusLessThan(Integer value) {
            addCriterion("review_status <", value, "reviewStatus");
            return (Criteria) this;
        }

        public Criteria andReviewStatusLessThanOrEqualTo(Integer value) {
            addCriterion("review_status <=", value, "reviewStatus");
            return (Criteria) this;
        }

        public Criteria andReviewStatusIn(List<Integer> values) {
            addCriterion("review_status in", values, "reviewStatus");
            return (Criteria) this;
        }

        public Criteria andReviewStatusNotIn(List<Integer> values) {
            addCriterion("review_status not in", values, "reviewStatus");
            return (Criteria) this;
        }

        public Criteria andReviewStatusBetween(Integer value1, Integer value2) {
            addCriterion("review_status between", value1, value2, "reviewStatus");
            return (Criteria) this;
        }

        public Criteria andReviewStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("review_status not between", value1, value2, "reviewStatus");
            return (Criteria) this;
        }

        public Criteria andShelvesTimeIsNull() {
            addCriterion("shelves_time is null");
            return (Criteria) this;
        }

        public Criteria andShelvesTimeIsNotNull() {
            addCriterion("shelves_time is not null");
            return (Criteria) this;
        }

        public Criteria andShelvesTimeEqualTo(Date value) {
            addCriterion("shelves_time =", value, "shelvesTime");
            return (Criteria) this;
        }

        public Criteria andShelvesTimeNotEqualTo(Date value) {
            addCriterion("shelves_time <>", value, "shelvesTime");
            return (Criteria) this;
        }

        public Criteria andShelvesTimeGreaterThan(Date value) {
            addCriterion("shelves_time >", value, "shelvesTime");
            return (Criteria) this;
        }

        public Criteria andShelvesTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("shelves_time >=", value, "shelvesTime");
            return (Criteria) this;
        }

        public Criteria andShelvesTimeLessThan(Date value) {
            addCriterion("shelves_time <", value, "shelvesTime");
            return (Criteria) this;
        }

        public Criteria andShelvesTimeLessThanOrEqualTo(Date value) {
            addCriterion("shelves_time <=", value, "shelvesTime");
            return (Criteria) this;
        }

        public Criteria andShelvesTimeIn(List<Date> values) {
            addCriterion("shelves_time in", values, "shelvesTime");
            return (Criteria) this;
        }

        public Criteria andShelvesTimeNotIn(List<Date> values) {
            addCriterion("shelves_time not in", values, "shelvesTime");
            return (Criteria) this;
        }

        public Criteria andShelvesTimeBetween(Date value1, Date value2) {
            addCriterion("shelves_time between", value1, value2, "shelvesTime");
            return (Criteria) this;
        }

        public Criteria andShelvesTimeNotBetween(Date value1, Date value2) {
            addCriterion("shelves_time not between", value1, value2, "shelvesTime");
            return (Criteria) this;
        }

        public Criteria andViewsIsNull() {
            addCriterion("views is null");
            return (Criteria) this;
        }

        public Criteria andViewsIsNotNull() {
            addCriterion("views is not null");
            return (Criteria) this;
        }

        public Criteria andViewsEqualTo(Integer value) {
            addCriterion("views =", value, "views");
            return (Criteria) this;
        }

        public Criteria andViewsNotEqualTo(Integer value) {
            addCriterion("views <>", value, "views");
            return (Criteria) this;
        }

        public Criteria andViewsGreaterThan(Integer value) {
            addCriterion("views >", value, "views");
            return (Criteria) this;
        }

        public Criteria andViewsGreaterThanOrEqualTo(Integer value) {
            addCriterion("views >=", value, "views");
            return (Criteria) this;
        }

        public Criteria andViewsLessThan(Integer value) {
            addCriterion("views <", value, "views");
            return (Criteria) this;
        }

        public Criteria andViewsLessThanOrEqualTo(Integer value) {
            addCriterion("views <=", value, "views");
            return (Criteria) this;
        }

        public Criteria andViewsIn(List<Integer> values) {
            addCriterion("views in", values, "views");
            return (Criteria) this;
        }

        public Criteria andViewsNotIn(List<Integer> values) {
            addCriterion("views not in", values, "views");
            return (Criteria) this;
        }

        public Criteria andViewsBetween(Integer value1, Integer value2) {
            addCriterion("views between", value1, value2, "views");
            return (Criteria) this;
        }

        public Criteria andViewsNotBetween(Integer value1, Integer value2) {
            addCriterion("views not between", value1, value2, "views");
            return (Criteria) this;
        }

        public Criteria andBusinessTypeIsNull() {
            addCriterion("business_type is null");
            return (Criteria) this;
        }

        public Criteria andBusinessTypeIsNotNull() {
            addCriterion("business_type is not null");
            return (Criteria) this;
        }

        public Criteria andBusinessTypeEqualTo(String value) {
            addCriterion("business_type =", value, "businessType");
            return (Criteria) this;
        }

        public Criteria andBusinessTypeNotEqualTo(String value) {
            addCriterion("business_type <>", value, "businessType");
            return (Criteria) this;
        }

        public Criteria andBusinessTypeGreaterThan(String value) {
            addCriterion("business_type >", value, "businessType");
            return (Criteria) this;
        }

        public Criteria andBusinessTypeGreaterThanOrEqualTo(String value) {
            addCriterion("business_type >=", value, "businessType");
            return (Criteria) this;
        }

        public Criteria andBusinessTypeLessThan(String value) {
            addCriterion("business_type <", value, "businessType");
            return (Criteria) this;
        }

        public Criteria andBusinessTypeLessThanOrEqualTo(String value) {
            addCriterion("business_type <=", value, "businessType");
            return (Criteria) this;
        }

        public Criteria andBusinessTypeLike(String value) {
            addCriterion("business_type like", value, "businessType");
            return (Criteria) this;
        }

        public Criteria andBusinessTypeNotLike(String value) {
            addCriterion("business_type not like", value, "businessType");
            return (Criteria) this;
        }

        public Criteria andBusinessTypeIn(List<String> values) {
            addCriterion("business_type in", values, "businessType");
            return (Criteria) this;
        }

        public Criteria andBusinessTypeNotIn(List<String> values) {
            addCriterion("business_type not in", values, "businessType");
            return (Criteria) this;
        }

        public Criteria andBusinessTypeBetween(String value1, String value2) {
            addCriterion("business_type between", value1, value2, "businessType");
            return (Criteria) this;
        }

        public Criteria andBusinessTypeNotBetween(String value1, String value2) {
            addCriterion("business_type not between", value1, value2, "businessType");
            return (Criteria) this;
        }

        public Criteria andStatusIsNull() {
            addCriterion("status is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("status is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(Byte value) {
            addCriterion("status =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(Byte value) {
            addCriterion("status <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(Byte value) {
            addCriterion("status >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(Byte value) {
            addCriterion("status >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(Byte value) {
            addCriterion("status <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(Byte value) {
            addCriterion("status <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<Byte> values) {
            addCriterion("status in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<Byte> values) {
            addCriterion("status not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(Byte value1, Byte value2) {
            addCriterion("status between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(Byte value1, Byte value2) {
            addCriterion("status not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andApproverIsNull() {
            addCriterion("approver is null");
            return (Criteria) this;
        }

        public Criteria andApproverIsNotNull() {
            addCriterion("approver is not null");
            return (Criteria) this;
        }

        public Criteria andApproverEqualTo(Long value) {
            addCriterion("approver =", value, "approver");
            return (Criteria) this;
        }

        public Criteria andApproverNotEqualTo(Long value) {
            addCriterion("approver <>", value, "approver");
            return (Criteria) this;
        }

        public Criteria andApproverGreaterThan(Long value) {
            addCriterion("approver >", value, "approver");
            return (Criteria) this;
        }

        public Criteria andApproverGreaterThanOrEqualTo(Long value) {
            addCriterion("approver >=", value, "approver");
            return (Criteria) this;
        }

        public Criteria andApproverLessThan(Long value) {
            addCriterion("approver <", value, "approver");
            return (Criteria) this;
        }

        public Criteria andApproverLessThanOrEqualTo(Long value) {
            addCriterion("approver <=", value, "approver");
            return (Criteria) this;
        }

        public Criteria andApproverIn(List<Long> values) {
            addCriterion("approver in", values, "approver");
            return (Criteria) this;
        }

        public Criteria andApproverNotIn(List<Long> values) {
            addCriterion("approver not in", values, "approver");
            return (Criteria) this;
        }

        public Criteria andApproverBetween(Long value1, Long value2) {
            addCriterion("approver between", value1, value2, "approver");
            return (Criteria) this;
        }

        public Criteria andApproverNotBetween(Long value1, Long value2) {
            addCriterion("approver not between", value1, value2, "approver");
            return (Criteria) this;
        }

        public Criteria andApprovalTimeIsNull() {
            addCriterion("approval_time is null");
            return (Criteria) this;
        }

        public Criteria andApprovalTimeIsNotNull() {
            addCriterion("approval_time is not null");
            return (Criteria) this;
        }

        public Criteria andApprovalTimeEqualTo(Date value) {
            addCriterion("approval_time =", value, "approvalTime");
            return (Criteria) this;
        }

        public Criteria andApprovalTimeNotEqualTo(Date value) {
            addCriterion("approval_time <>", value, "approvalTime");
            return (Criteria) this;
        }

        public Criteria andApprovalTimeGreaterThan(Date value) {
            addCriterion("approval_time >", value, "approvalTime");
            return (Criteria) this;
        }

        public Criteria andApprovalTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("approval_time >=", value, "approvalTime");
            return (Criteria) this;
        }

        public Criteria andApprovalTimeLessThan(Date value) {
            addCriterion("approval_time <", value, "approvalTime");
            return (Criteria) this;
        }

        public Criteria andApprovalTimeLessThanOrEqualTo(Date value) {
            addCriterion("approval_time <=", value, "approvalTime");
            return (Criteria) this;
        }

        public Criteria andApprovalTimeIn(List<Date> values) {
            addCriterion("approval_time in", values, "approvalTime");
            return (Criteria) this;
        }

        public Criteria andApprovalTimeNotIn(List<Date> values) {
            addCriterion("approval_time not in", values, "approvalTime");
            return (Criteria) this;
        }

        public Criteria andApprovalTimeBetween(Date value1, Date value2) {
            addCriterion("approval_time between", value1, value2, "approvalTime");
            return (Criteria) this;
        }

        public Criteria andApprovalTimeNotBetween(Date value1, Date value2) {
            addCriterion("approval_time not between", value1, value2, "approvalTime");
            return (Criteria) this;
        }

        public Criteria andApprovalCommentIsNull() {
            addCriterion("approval_comment is null");
            return (Criteria) this;
        }

        public Criteria andApprovalCommentIsNotNull() {
            addCriterion("approval_comment is not null");
            return (Criteria) this;
        }

        public Criteria andApprovalCommentEqualTo(String value) {
            addCriterion("approval_comment =", value, "approvalComment");
            return (Criteria) this;
        }

        public Criteria andApprovalCommentNotEqualTo(String value) {
            addCriterion("approval_comment <>", value, "approvalComment");
            return (Criteria) this;
        }

        public Criteria andApprovalCommentGreaterThan(String value) {
            addCriterion("approval_comment >", value, "approvalComment");
            return (Criteria) this;
        }

        public Criteria andApprovalCommentGreaterThanOrEqualTo(String value) {
            addCriterion("approval_comment >=", value, "approvalComment");
            return (Criteria) this;
        }

        public Criteria andApprovalCommentLessThan(String value) {
            addCriterion("approval_comment <", value, "approvalComment");
            return (Criteria) this;
        }

        public Criteria andApprovalCommentLessThanOrEqualTo(String value) {
            addCriterion("approval_comment <=", value, "approvalComment");
            return (Criteria) this;
        }

        public Criteria andApprovalCommentLike(String value) {
            addCriterion("approval_comment like", value, "approvalComment");
            return (Criteria) this;
        }

        public Criteria andApprovalCommentNotLike(String value) {
            addCriterion("approval_comment not like", value, "approvalComment");
            return (Criteria) this;
        }

        public Criteria andApprovalCommentIn(List<String> values) {
            addCriterion("approval_comment in", values, "approvalComment");
            return (Criteria) this;
        }

        public Criteria andApprovalCommentNotIn(List<String> values) {
            addCriterion("approval_comment not in", values, "approvalComment");
            return (Criteria) this;
        }

        public Criteria andApprovalCommentBetween(String value1, String value2) {
            addCriterion("approval_comment between", value1, value2, "approvalComment");
            return (Criteria) this;
        }

        public Criteria andApprovalCommentNotBetween(String value1, String value2) {
            addCriterion("approval_comment not between", value1, value2, "approvalComment");
            return (Criteria) this;
        }

        public Criteria andProductLabelsIsNull() {
            addCriterion("product_labels is null");
            return (Criteria) this;
        }

        public Criteria andProductLabelsIsNotNull() {
            addCriterion("product_labels is not null");
            return (Criteria) this;
        }

        public Criteria andProductLabelsEqualTo(String value) {
            addCriterion("product_labels =", value, "productLabels");
            return (Criteria) this;
        }

        public Criteria andProductLabelsNotEqualTo(String value) {
            addCriterion("product_labels <>", value, "productLabels");
            return (Criteria) this;
        }

        public Criteria andProductLabelsGreaterThan(String value) {
            addCriterion("product_labels >", value, "productLabels");
            return (Criteria) this;
        }

        public Criteria andProductLabelsGreaterThanOrEqualTo(String value) {
            addCriterion("product_labels >=", value, "productLabels");
            return (Criteria) this;
        }

        public Criteria andProductLabelsLessThan(String value) {
            addCriterion("product_labels <", value, "productLabels");
            return (Criteria) this;
        }

        public Criteria andProductLabelsLessThanOrEqualTo(String value) {
            addCriterion("product_labels <=", value, "productLabels");
            return (Criteria) this;
        }

        public Criteria andProductLabelsLike(String value) {
            addCriterion("product_labels like", value, "productLabels");
            return (Criteria) this;
        }

        public Criteria andProductLabelsNotLike(String value) {
            addCriterion("product_labels not like", value, "productLabels");
            return (Criteria) this;
        }

        public Criteria andProductLabelsIn(List<String> values) {
            addCriterion("product_labels in", values, "productLabels");
            return (Criteria) this;
        }

        public Criteria andProductLabelsNotIn(List<String> values) {
            addCriterion("product_labels not in", values, "productLabels");
            return (Criteria) this;
        }

        public Criteria andProductLabelsBetween(String value1, String value2) {
            addCriterion("product_labels between", value1, value2, "productLabels");
            return (Criteria) this;
        }

        public Criteria andProductLabelsNotBetween(String value1, String value2) {
            addCriterion("product_labels not between", value1, value2, "productLabels");
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

        public Criteria andDpDeptIdIsNull() {
            addCriterion("dp_dept_id is null");
            return (Criteria) this;
        }

        public Criteria andDpDeptIdIsNotNull() {
            addCriterion("dp_dept_id is not null");
            return (Criteria) this;
        }

        public Criteria andDpDeptIdEqualTo(Long value) {
            addCriterion("dp_dept_id =", value, "dpDeptId");
            return (Criteria) this;
        }

        public Criteria andDpDeptIdNotEqualTo(Long value) {
            addCriterion("dp_dept_id <>", value, "dpDeptId");
            return (Criteria) this;
        }

        public Criteria andDpDeptIdGreaterThan(Long value) {
            addCriterion("dp_dept_id >", value, "dpDeptId");
            return (Criteria) this;
        }

        public Criteria andDpDeptIdGreaterThanOrEqualTo(Long value) {
            addCriterion("dp_dept_id >=", value, "dpDeptId");
            return (Criteria) this;
        }

        public Criteria andDpDeptIdLessThan(Long value) {
            addCriterion("dp_dept_id <", value, "dpDeptId");
            return (Criteria) this;
        }

        public Criteria andDpDeptIdLessThanOrEqualTo(Long value) {
            addCriterion("dp_dept_id <=", value, "dpDeptId");
            return (Criteria) this;
        }

        public Criteria andDpDeptIdIn(List<Long> values) {
            addCriterion("dp_dept_id in", values, "dpDeptId");
            return (Criteria) this;
        }

        public Criteria andDpDeptIdNotIn(List<Long> values) {
            addCriterion("dp_dept_id not in", values, "dpDeptId");
            return (Criteria) this;
        }

        public Criteria andDpDeptIdBetween(Long value1, Long value2) {
            addCriterion("dp_dept_id between", value1, value2, "dpDeptId");
            return (Criteria) this;
        }

        public Criteria andDpDeptIdNotBetween(Long value1, Long value2) {
            addCriterion("dp_dept_id not between", value1, value2, "dpDeptId");
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