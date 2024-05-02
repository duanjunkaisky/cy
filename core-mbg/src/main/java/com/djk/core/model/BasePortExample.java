package com.djk.core.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BasePortExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public BasePortExample() {
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

        public Criteria andPortNameEnIsNull() {
            addCriterion("port_name_en is null");
            return (Criteria) this;
        }

        public Criteria andPortNameEnIsNotNull() {
            addCriterion("port_name_en is not null");
            return (Criteria) this;
        }

        public Criteria andPortNameEnEqualTo(String value) {
            addCriterion("port_name_en =", value, "portNameEn");
            return (Criteria) this;
        }

        public Criteria andPortNameEnNotEqualTo(String value) {
            addCriterion("port_name_en <>", value, "portNameEn");
            return (Criteria) this;
        }

        public Criteria andPortNameEnGreaterThan(String value) {
            addCriterion("port_name_en >", value, "portNameEn");
            return (Criteria) this;
        }

        public Criteria andPortNameEnGreaterThanOrEqualTo(String value) {
            addCriterion("port_name_en >=", value, "portNameEn");
            return (Criteria) this;
        }

        public Criteria andPortNameEnLessThan(String value) {
            addCriterion("port_name_en <", value, "portNameEn");
            return (Criteria) this;
        }

        public Criteria andPortNameEnLessThanOrEqualTo(String value) {
            addCriterion("port_name_en <=", value, "portNameEn");
            return (Criteria) this;
        }

        public Criteria andPortNameEnLike(String value) {
            addCriterion("port_name_en like", value, "portNameEn");
            return (Criteria) this;
        }

        public Criteria andPortNameEnNotLike(String value) {
            addCriterion("port_name_en not like", value, "portNameEn");
            return (Criteria) this;
        }

        public Criteria andPortNameEnIn(List<String> values) {
            addCriterion("port_name_en in", values, "portNameEn");
            return (Criteria) this;
        }

        public Criteria andPortNameEnNotIn(List<String> values) {
            addCriterion("port_name_en not in", values, "portNameEn");
            return (Criteria) this;
        }

        public Criteria andPortNameEnBetween(String value1, String value2) {
            addCriterion("port_name_en between", value1, value2, "portNameEn");
            return (Criteria) this;
        }

        public Criteria andPortNameEnNotBetween(String value1, String value2) {
            addCriterion("port_name_en not between", value1, value2, "portNameEn");
            return (Criteria) this;
        }

        public Criteria andPortNameZhIsNull() {
            addCriterion("port_name_zh is null");
            return (Criteria) this;
        }

        public Criteria andPortNameZhIsNotNull() {
            addCriterion("port_name_zh is not null");
            return (Criteria) this;
        }

        public Criteria andPortNameZhEqualTo(String value) {
            addCriterion("port_name_zh =", value, "portNameZh");
            return (Criteria) this;
        }

        public Criteria andPortNameZhNotEqualTo(String value) {
            addCriterion("port_name_zh <>", value, "portNameZh");
            return (Criteria) this;
        }

        public Criteria andPortNameZhGreaterThan(String value) {
            addCriterion("port_name_zh >", value, "portNameZh");
            return (Criteria) this;
        }

        public Criteria andPortNameZhGreaterThanOrEqualTo(String value) {
            addCriterion("port_name_zh >=", value, "portNameZh");
            return (Criteria) this;
        }

        public Criteria andPortNameZhLessThan(String value) {
            addCriterion("port_name_zh <", value, "portNameZh");
            return (Criteria) this;
        }

        public Criteria andPortNameZhLessThanOrEqualTo(String value) {
            addCriterion("port_name_zh <=", value, "portNameZh");
            return (Criteria) this;
        }

        public Criteria andPortNameZhLike(String value) {
            addCriterion("port_name_zh like", value, "portNameZh");
            return (Criteria) this;
        }

        public Criteria andPortNameZhNotLike(String value) {
            addCriterion("port_name_zh not like", value, "portNameZh");
            return (Criteria) this;
        }

        public Criteria andPortNameZhIn(List<String> values) {
            addCriterion("port_name_zh in", values, "portNameZh");
            return (Criteria) this;
        }

        public Criteria andPortNameZhNotIn(List<String> values) {
            addCriterion("port_name_zh not in", values, "portNameZh");
            return (Criteria) this;
        }

        public Criteria andPortNameZhBetween(String value1, String value2) {
            addCriterion("port_name_zh between", value1, value2, "portNameZh");
            return (Criteria) this;
        }

        public Criteria andPortNameZhNotBetween(String value1, String value2) {
            addCriterion("port_name_zh not between", value1, value2, "portNameZh");
            return (Criteria) this;
        }

        public Criteria andPortCodeIsNull() {
            addCriterion("port_code is null");
            return (Criteria) this;
        }

        public Criteria andPortCodeIsNotNull() {
            addCriterion("port_code is not null");
            return (Criteria) this;
        }

        public Criteria andPortCodeEqualTo(String value) {
            addCriterion("port_code =", value, "portCode");
            return (Criteria) this;
        }

        public Criteria andPortCodeNotEqualTo(String value) {
            addCriterion("port_code <>", value, "portCode");
            return (Criteria) this;
        }

        public Criteria andPortCodeGreaterThan(String value) {
            addCriterion("port_code >", value, "portCode");
            return (Criteria) this;
        }

        public Criteria andPortCodeGreaterThanOrEqualTo(String value) {
            addCriterion("port_code >=", value, "portCode");
            return (Criteria) this;
        }

        public Criteria andPortCodeLessThan(String value) {
            addCriterion("port_code <", value, "portCode");
            return (Criteria) this;
        }

        public Criteria andPortCodeLessThanOrEqualTo(String value) {
            addCriterion("port_code <=", value, "portCode");
            return (Criteria) this;
        }

        public Criteria andPortCodeLike(String value) {
            addCriterion("port_code like", value, "portCode");
            return (Criteria) this;
        }

        public Criteria andPortCodeNotLike(String value) {
            addCriterion("port_code not like", value, "portCode");
            return (Criteria) this;
        }

        public Criteria andPortCodeIn(List<String> values) {
            addCriterion("port_code in", values, "portCode");
            return (Criteria) this;
        }

        public Criteria andPortCodeNotIn(List<String> values) {
            addCriterion("port_code not in", values, "portCode");
            return (Criteria) this;
        }

        public Criteria andPortCodeBetween(String value1, String value2) {
            addCriterion("port_code between", value1, value2, "portCode");
            return (Criteria) this;
        }

        public Criteria andPortCodeNotBetween(String value1, String value2) {
            addCriterion("port_code not between", value1, value2, "portCode");
            return (Criteria) this;
        }

        public Criteria andCountryNameEnIsNull() {
            addCriterion("country_name_en is null");
            return (Criteria) this;
        }

        public Criteria andCountryNameEnIsNotNull() {
            addCriterion("country_name_en is not null");
            return (Criteria) this;
        }

        public Criteria andCountryNameEnEqualTo(String value) {
            addCriterion("country_name_en =", value, "countryNameEn");
            return (Criteria) this;
        }

        public Criteria andCountryNameEnNotEqualTo(String value) {
            addCriterion("country_name_en <>", value, "countryNameEn");
            return (Criteria) this;
        }

        public Criteria andCountryNameEnGreaterThan(String value) {
            addCriterion("country_name_en >", value, "countryNameEn");
            return (Criteria) this;
        }

        public Criteria andCountryNameEnGreaterThanOrEqualTo(String value) {
            addCriterion("country_name_en >=", value, "countryNameEn");
            return (Criteria) this;
        }

        public Criteria andCountryNameEnLessThan(String value) {
            addCriterion("country_name_en <", value, "countryNameEn");
            return (Criteria) this;
        }

        public Criteria andCountryNameEnLessThanOrEqualTo(String value) {
            addCriterion("country_name_en <=", value, "countryNameEn");
            return (Criteria) this;
        }

        public Criteria andCountryNameEnLike(String value) {
            addCriterion("country_name_en like", value, "countryNameEn");
            return (Criteria) this;
        }

        public Criteria andCountryNameEnNotLike(String value) {
            addCriterion("country_name_en not like", value, "countryNameEn");
            return (Criteria) this;
        }

        public Criteria andCountryNameEnIn(List<String> values) {
            addCriterion("country_name_en in", values, "countryNameEn");
            return (Criteria) this;
        }

        public Criteria andCountryNameEnNotIn(List<String> values) {
            addCriterion("country_name_en not in", values, "countryNameEn");
            return (Criteria) this;
        }

        public Criteria andCountryNameEnBetween(String value1, String value2) {
            addCriterion("country_name_en between", value1, value2, "countryNameEn");
            return (Criteria) this;
        }

        public Criteria andCountryNameEnNotBetween(String value1, String value2) {
            addCriterion("country_name_en not between", value1, value2, "countryNameEn");
            return (Criteria) this;
        }

        public Criteria andCountryNameZhIsNull() {
            addCriterion("country_name_zh is null");
            return (Criteria) this;
        }

        public Criteria andCountryNameZhIsNotNull() {
            addCriterion("country_name_zh is not null");
            return (Criteria) this;
        }

        public Criteria andCountryNameZhEqualTo(String value) {
            addCriterion("country_name_zh =", value, "countryNameZh");
            return (Criteria) this;
        }

        public Criteria andCountryNameZhNotEqualTo(String value) {
            addCriterion("country_name_zh <>", value, "countryNameZh");
            return (Criteria) this;
        }

        public Criteria andCountryNameZhGreaterThan(String value) {
            addCriterion("country_name_zh >", value, "countryNameZh");
            return (Criteria) this;
        }

        public Criteria andCountryNameZhGreaterThanOrEqualTo(String value) {
            addCriterion("country_name_zh >=", value, "countryNameZh");
            return (Criteria) this;
        }

        public Criteria andCountryNameZhLessThan(String value) {
            addCriterion("country_name_zh <", value, "countryNameZh");
            return (Criteria) this;
        }

        public Criteria andCountryNameZhLessThanOrEqualTo(String value) {
            addCriterion("country_name_zh <=", value, "countryNameZh");
            return (Criteria) this;
        }

        public Criteria andCountryNameZhLike(String value) {
            addCriterion("country_name_zh like", value, "countryNameZh");
            return (Criteria) this;
        }

        public Criteria andCountryNameZhNotLike(String value) {
            addCriterion("country_name_zh not like", value, "countryNameZh");
            return (Criteria) this;
        }

        public Criteria andCountryNameZhIn(List<String> values) {
            addCriterion("country_name_zh in", values, "countryNameZh");
            return (Criteria) this;
        }

        public Criteria andCountryNameZhNotIn(List<String> values) {
            addCriterion("country_name_zh not in", values, "countryNameZh");
            return (Criteria) this;
        }

        public Criteria andCountryNameZhBetween(String value1, String value2) {
            addCriterion("country_name_zh between", value1, value2, "countryNameZh");
            return (Criteria) this;
        }

        public Criteria andCountryNameZhNotBetween(String value1, String value2) {
            addCriterion("country_name_zh not between", value1, value2, "countryNameZh");
            return (Criteria) this;
        }

        public Criteria andCountryCodeIsNull() {
            addCriterion("country_code is null");
            return (Criteria) this;
        }

        public Criteria andCountryCodeIsNotNull() {
            addCriterion("country_code is not null");
            return (Criteria) this;
        }

        public Criteria andCountryCodeEqualTo(String value) {
            addCriterion("country_code =", value, "countryCode");
            return (Criteria) this;
        }

        public Criteria andCountryCodeNotEqualTo(String value) {
            addCriterion("country_code <>", value, "countryCode");
            return (Criteria) this;
        }

        public Criteria andCountryCodeGreaterThan(String value) {
            addCriterion("country_code >", value, "countryCode");
            return (Criteria) this;
        }

        public Criteria andCountryCodeGreaterThanOrEqualTo(String value) {
            addCriterion("country_code >=", value, "countryCode");
            return (Criteria) this;
        }

        public Criteria andCountryCodeLessThan(String value) {
            addCriterion("country_code <", value, "countryCode");
            return (Criteria) this;
        }

        public Criteria andCountryCodeLessThanOrEqualTo(String value) {
            addCriterion("country_code <=", value, "countryCode");
            return (Criteria) this;
        }

        public Criteria andCountryCodeLike(String value) {
            addCriterion("country_code like", value, "countryCode");
            return (Criteria) this;
        }

        public Criteria andCountryCodeNotLike(String value) {
            addCriterion("country_code not like", value, "countryCode");
            return (Criteria) this;
        }

        public Criteria andCountryCodeIn(List<String> values) {
            addCriterion("country_code in", values, "countryCode");
            return (Criteria) this;
        }

        public Criteria andCountryCodeNotIn(List<String> values) {
            addCriterion("country_code not in", values, "countryCode");
            return (Criteria) this;
        }

        public Criteria andCountryCodeBetween(String value1, String value2) {
            addCriterion("country_code between", value1, value2, "countryCode");
            return (Criteria) this;
        }

        public Criteria andCountryCodeNotBetween(String value1, String value2) {
            addCriterion("country_code not between", value1, value2, "countryCode");
            return (Criteria) this;
        }

        public Criteria andProvinceZhIsNull() {
            addCriterion("province_zh is null");
            return (Criteria) this;
        }

        public Criteria andProvinceZhIsNotNull() {
            addCriterion("province_zh is not null");
            return (Criteria) this;
        }

        public Criteria andProvinceZhEqualTo(String value) {
            addCriterion("province_zh =", value, "provinceZh");
            return (Criteria) this;
        }

        public Criteria andProvinceZhNotEqualTo(String value) {
            addCriterion("province_zh <>", value, "provinceZh");
            return (Criteria) this;
        }

        public Criteria andProvinceZhGreaterThan(String value) {
            addCriterion("province_zh >", value, "provinceZh");
            return (Criteria) this;
        }

        public Criteria andProvinceZhGreaterThanOrEqualTo(String value) {
            addCriterion("province_zh >=", value, "provinceZh");
            return (Criteria) this;
        }

        public Criteria andProvinceZhLessThan(String value) {
            addCriterion("province_zh <", value, "provinceZh");
            return (Criteria) this;
        }

        public Criteria andProvinceZhLessThanOrEqualTo(String value) {
            addCriterion("province_zh <=", value, "provinceZh");
            return (Criteria) this;
        }

        public Criteria andProvinceZhLike(String value) {
            addCriterion("province_zh like", value, "provinceZh");
            return (Criteria) this;
        }

        public Criteria andProvinceZhNotLike(String value) {
            addCriterion("province_zh not like", value, "provinceZh");
            return (Criteria) this;
        }

        public Criteria andProvinceZhIn(List<String> values) {
            addCriterion("province_zh in", values, "provinceZh");
            return (Criteria) this;
        }

        public Criteria andProvinceZhNotIn(List<String> values) {
            addCriterion("province_zh not in", values, "provinceZh");
            return (Criteria) this;
        }

        public Criteria andProvinceZhBetween(String value1, String value2) {
            addCriterion("province_zh between", value1, value2, "provinceZh");
            return (Criteria) this;
        }

        public Criteria andProvinceZhNotBetween(String value1, String value2) {
            addCriterion("province_zh not between", value1, value2, "provinceZh");
            return (Criteria) this;
        }

        public Criteria andProvinceEnIsNull() {
            addCriterion("province_en is null");
            return (Criteria) this;
        }

        public Criteria andProvinceEnIsNotNull() {
            addCriterion("province_en is not null");
            return (Criteria) this;
        }

        public Criteria andProvinceEnEqualTo(String value) {
            addCriterion("province_en =", value, "provinceEn");
            return (Criteria) this;
        }

        public Criteria andProvinceEnNotEqualTo(String value) {
            addCriterion("province_en <>", value, "provinceEn");
            return (Criteria) this;
        }

        public Criteria andProvinceEnGreaterThan(String value) {
            addCriterion("province_en >", value, "provinceEn");
            return (Criteria) this;
        }

        public Criteria andProvinceEnGreaterThanOrEqualTo(String value) {
            addCriterion("province_en >=", value, "provinceEn");
            return (Criteria) this;
        }

        public Criteria andProvinceEnLessThan(String value) {
            addCriterion("province_en <", value, "provinceEn");
            return (Criteria) this;
        }

        public Criteria andProvinceEnLessThanOrEqualTo(String value) {
            addCriterion("province_en <=", value, "provinceEn");
            return (Criteria) this;
        }

        public Criteria andProvinceEnLike(String value) {
            addCriterion("province_en like", value, "provinceEn");
            return (Criteria) this;
        }

        public Criteria andProvinceEnNotLike(String value) {
            addCriterion("province_en not like", value, "provinceEn");
            return (Criteria) this;
        }

        public Criteria andProvinceEnIn(List<String> values) {
            addCriterion("province_en in", values, "provinceEn");
            return (Criteria) this;
        }

        public Criteria andProvinceEnNotIn(List<String> values) {
            addCriterion("province_en not in", values, "provinceEn");
            return (Criteria) this;
        }

        public Criteria andProvinceEnBetween(String value1, String value2) {
            addCriterion("province_en between", value1, value2, "provinceEn");
            return (Criteria) this;
        }

        public Criteria andProvinceEnNotBetween(String value1, String value2) {
            addCriterion("province_en not between", value1, value2, "provinceEn");
            return (Criteria) this;
        }

        public Criteria andStateZhIsNull() {
            addCriterion("state_zh is null");
            return (Criteria) this;
        }

        public Criteria andStateZhIsNotNull() {
            addCriterion("state_zh is not null");
            return (Criteria) this;
        }

        public Criteria andStateZhEqualTo(String value) {
            addCriterion("state_zh =", value, "stateZh");
            return (Criteria) this;
        }

        public Criteria andStateZhNotEqualTo(String value) {
            addCriterion("state_zh <>", value, "stateZh");
            return (Criteria) this;
        }

        public Criteria andStateZhGreaterThan(String value) {
            addCriterion("state_zh >", value, "stateZh");
            return (Criteria) this;
        }

        public Criteria andStateZhGreaterThanOrEqualTo(String value) {
            addCriterion("state_zh >=", value, "stateZh");
            return (Criteria) this;
        }

        public Criteria andStateZhLessThan(String value) {
            addCriterion("state_zh <", value, "stateZh");
            return (Criteria) this;
        }

        public Criteria andStateZhLessThanOrEqualTo(String value) {
            addCriterion("state_zh <=", value, "stateZh");
            return (Criteria) this;
        }

        public Criteria andStateZhLike(String value) {
            addCriterion("state_zh like", value, "stateZh");
            return (Criteria) this;
        }

        public Criteria andStateZhNotLike(String value) {
            addCriterion("state_zh not like", value, "stateZh");
            return (Criteria) this;
        }

        public Criteria andStateZhIn(List<String> values) {
            addCriterion("state_zh in", values, "stateZh");
            return (Criteria) this;
        }

        public Criteria andStateZhNotIn(List<String> values) {
            addCriterion("state_zh not in", values, "stateZh");
            return (Criteria) this;
        }

        public Criteria andStateZhBetween(String value1, String value2) {
            addCriterion("state_zh between", value1, value2, "stateZh");
            return (Criteria) this;
        }

        public Criteria andStateZhNotBetween(String value1, String value2) {
            addCriterion("state_zh not between", value1, value2, "stateZh");
            return (Criteria) this;
        }

        public Criteria andStateEnIsNull() {
            addCriterion("state_en is null");
            return (Criteria) this;
        }

        public Criteria andStateEnIsNotNull() {
            addCriterion("state_en is not null");
            return (Criteria) this;
        }

        public Criteria andStateEnEqualTo(String value) {
            addCriterion("state_en =", value, "stateEn");
            return (Criteria) this;
        }

        public Criteria andStateEnNotEqualTo(String value) {
            addCriterion("state_en <>", value, "stateEn");
            return (Criteria) this;
        }

        public Criteria andStateEnGreaterThan(String value) {
            addCriterion("state_en >", value, "stateEn");
            return (Criteria) this;
        }

        public Criteria andStateEnGreaterThanOrEqualTo(String value) {
            addCriterion("state_en >=", value, "stateEn");
            return (Criteria) this;
        }

        public Criteria andStateEnLessThan(String value) {
            addCriterion("state_en <", value, "stateEn");
            return (Criteria) this;
        }

        public Criteria andStateEnLessThanOrEqualTo(String value) {
            addCriterion("state_en <=", value, "stateEn");
            return (Criteria) this;
        }

        public Criteria andStateEnLike(String value) {
            addCriterion("state_en like", value, "stateEn");
            return (Criteria) this;
        }

        public Criteria andStateEnNotLike(String value) {
            addCriterion("state_en not like", value, "stateEn");
            return (Criteria) this;
        }

        public Criteria andStateEnIn(List<String> values) {
            addCriterion("state_en in", values, "stateEn");
            return (Criteria) this;
        }

        public Criteria andStateEnNotIn(List<String> values) {
            addCriterion("state_en not in", values, "stateEn");
            return (Criteria) this;
        }

        public Criteria andStateEnBetween(String value1, String value2) {
            addCriterion("state_en between", value1, value2, "stateEn");
            return (Criteria) this;
        }

        public Criteria andStateEnNotBetween(String value1, String value2) {
            addCriterion("state_en not between", value1, value2, "stateEn");
            return (Criteria) this;
        }

        public Criteria andCityZhIsNull() {
            addCriterion("city_zh is null");
            return (Criteria) this;
        }

        public Criteria andCityZhIsNotNull() {
            addCriterion("city_zh is not null");
            return (Criteria) this;
        }

        public Criteria andCityZhEqualTo(String value) {
            addCriterion("city_zh =", value, "cityZh");
            return (Criteria) this;
        }

        public Criteria andCityZhNotEqualTo(String value) {
            addCriterion("city_zh <>", value, "cityZh");
            return (Criteria) this;
        }

        public Criteria andCityZhGreaterThan(String value) {
            addCriterion("city_zh >", value, "cityZh");
            return (Criteria) this;
        }

        public Criteria andCityZhGreaterThanOrEqualTo(String value) {
            addCriterion("city_zh >=", value, "cityZh");
            return (Criteria) this;
        }

        public Criteria andCityZhLessThan(String value) {
            addCriterion("city_zh <", value, "cityZh");
            return (Criteria) this;
        }

        public Criteria andCityZhLessThanOrEqualTo(String value) {
            addCriterion("city_zh <=", value, "cityZh");
            return (Criteria) this;
        }

        public Criteria andCityZhLike(String value) {
            addCriterion("city_zh like", value, "cityZh");
            return (Criteria) this;
        }

        public Criteria andCityZhNotLike(String value) {
            addCriterion("city_zh not like", value, "cityZh");
            return (Criteria) this;
        }

        public Criteria andCityZhIn(List<String> values) {
            addCriterion("city_zh in", values, "cityZh");
            return (Criteria) this;
        }

        public Criteria andCityZhNotIn(List<String> values) {
            addCriterion("city_zh not in", values, "cityZh");
            return (Criteria) this;
        }

        public Criteria andCityZhBetween(String value1, String value2) {
            addCriterion("city_zh between", value1, value2, "cityZh");
            return (Criteria) this;
        }

        public Criteria andCityZhNotBetween(String value1, String value2) {
            addCriterion("city_zh not between", value1, value2, "cityZh");
            return (Criteria) this;
        }

        public Criteria andCityEnIsNull() {
            addCriterion("city_en is null");
            return (Criteria) this;
        }

        public Criteria andCityEnIsNotNull() {
            addCriterion("city_en is not null");
            return (Criteria) this;
        }

        public Criteria andCityEnEqualTo(String value) {
            addCriterion("city_en =", value, "cityEn");
            return (Criteria) this;
        }

        public Criteria andCityEnNotEqualTo(String value) {
            addCriterion("city_en <>", value, "cityEn");
            return (Criteria) this;
        }

        public Criteria andCityEnGreaterThan(String value) {
            addCriterion("city_en >", value, "cityEn");
            return (Criteria) this;
        }

        public Criteria andCityEnGreaterThanOrEqualTo(String value) {
            addCriterion("city_en >=", value, "cityEn");
            return (Criteria) this;
        }

        public Criteria andCityEnLessThan(String value) {
            addCriterion("city_en <", value, "cityEn");
            return (Criteria) this;
        }

        public Criteria andCityEnLessThanOrEqualTo(String value) {
            addCriterion("city_en <=", value, "cityEn");
            return (Criteria) this;
        }

        public Criteria andCityEnLike(String value) {
            addCriterion("city_en like", value, "cityEn");
            return (Criteria) this;
        }

        public Criteria andCityEnNotLike(String value) {
            addCriterion("city_en not like", value, "cityEn");
            return (Criteria) this;
        }

        public Criteria andCityEnIn(List<String> values) {
            addCriterion("city_en in", values, "cityEn");
            return (Criteria) this;
        }

        public Criteria andCityEnNotIn(List<String> values) {
            addCriterion("city_en not in", values, "cityEn");
            return (Criteria) this;
        }

        public Criteria andCityEnBetween(String value1, String value2) {
            addCriterion("city_en between", value1, value2, "cityEn");
            return (Criteria) this;
        }

        public Criteria andCityEnNotBetween(String value1, String value2) {
            addCriterion("city_en not between", value1, value2, "cityEn");
            return (Criteria) this;
        }

        public Criteria andPortTypeIsNull() {
            addCriterion("port_type is null");
            return (Criteria) this;
        }

        public Criteria andPortTypeIsNotNull() {
            addCriterion("port_type is not null");
            return (Criteria) this;
        }

        public Criteria andPortTypeEqualTo(Integer value) {
            addCriterion("port_type =", value, "portType");
            return (Criteria) this;
        }

        public Criteria andPortTypeNotEqualTo(Integer value) {
            addCriterion("port_type <>", value, "portType");
            return (Criteria) this;
        }

        public Criteria andPortTypeGreaterThan(Integer value) {
            addCriterion("port_type >", value, "portType");
            return (Criteria) this;
        }

        public Criteria andPortTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("port_type >=", value, "portType");
            return (Criteria) this;
        }

        public Criteria andPortTypeLessThan(Integer value) {
            addCriterion("port_type <", value, "portType");
            return (Criteria) this;
        }

        public Criteria andPortTypeLessThanOrEqualTo(Integer value) {
            addCriterion("port_type <=", value, "portType");
            return (Criteria) this;
        }

        public Criteria andPortTypeIn(List<Integer> values) {
            addCriterion("port_type in", values, "portType");
            return (Criteria) this;
        }

        public Criteria andPortTypeNotIn(List<Integer> values) {
            addCriterion("port_type not in", values, "portType");
            return (Criteria) this;
        }

        public Criteria andPortTypeBetween(Integer value1, Integer value2) {
            addCriterion("port_type between", value1, value2, "portType");
            return (Criteria) this;
        }

        public Criteria andPortTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("port_type not between", value1, value2, "portType");
            return (Criteria) this;
        }

        public Criteria andRegionIsNull() {
            addCriterion("region is null");
            return (Criteria) this;
        }

        public Criteria andRegionIsNotNull() {
            addCriterion("region is not null");
            return (Criteria) this;
        }

        public Criteria andRegionEqualTo(Long value) {
            addCriterion("region =", value, "region");
            return (Criteria) this;
        }

        public Criteria andRegionNotEqualTo(Long value) {
            addCriterion("region <>", value, "region");
            return (Criteria) this;
        }

        public Criteria andRegionGreaterThan(Long value) {
            addCriterion("region >", value, "region");
            return (Criteria) this;
        }

        public Criteria andRegionGreaterThanOrEqualTo(Long value) {
            addCriterion("region >=", value, "region");
            return (Criteria) this;
        }

        public Criteria andRegionLessThan(Long value) {
            addCriterion("region <", value, "region");
            return (Criteria) this;
        }

        public Criteria andRegionLessThanOrEqualTo(Long value) {
            addCriterion("region <=", value, "region");
            return (Criteria) this;
        }

        public Criteria andRegionIn(List<Long> values) {
            addCriterion("region in", values, "region");
            return (Criteria) this;
        }

        public Criteria andRegionNotIn(List<Long> values) {
            addCriterion("region not in", values, "region");
            return (Criteria) this;
        }

        public Criteria andRegionBetween(Long value1, Long value2) {
            addCriterion("region between", value1, value2, "region");
            return (Criteria) this;
        }

        public Criteria andRegionNotBetween(Long value1, Long value2) {
            addCriterion("region not between", value1, value2, "region");
            return (Criteria) this;
        }

        public Criteria andAreaIsNull() {
            addCriterion("area is null");
            return (Criteria) this;
        }

        public Criteria andAreaIsNotNull() {
            addCriterion("area is not null");
            return (Criteria) this;
        }

        public Criteria andAreaEqualTo(Long value) {
            addCriterion("area =", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaNotEqualTo(Long value) {
            addCriterion("area <>", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaGreaterThan(Long value) {
            addCriterion("area >", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaGreaterThanOrEqualTo(Long value) {
            addCriterion("area >=", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaLessThan(Long value) {
            addCriterion("area <", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaLessThanOrEqualTo(Long value) {
            addCriterion("area <=", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaIn(List<Long> values) {
            addCriterion("area in", values, "area");
            return (Criteria) this;
        }

        public Criteria andAreaNotIn(List<Long> values) {
            addCriterion("area not in", values, "area");
            return (Criteria) this;
        }

        public Criteria andAreaBetween(Long value1, Long value2) {
            addCriterion("area between", value1, value2, "area");
            return (Criteria) this;
        }

        public Criteria andAreaNotBetween(Long value1, Long value2) {
            addCriterion("area not between", value1, value2, "area");
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

        public Criteria andNotesIsNull() {
            addCriterion("notes is null");
            return (Criteria) this;
        }

        public Criteria andNotesIsNotNull() {
            addCriterion("notes is not null");
            return (Criteria) this;
        }

        public Criteria andNotesEqualTo(String value) {
            addCriterion("notes =", value, "notes");
            return (Criteria) this;
        }

        public Criteria andNotesNotEqualTo(String value) {
            addCriterion("notes <>", value, "notes");
            return (Criteria) this;
        }

        public Criteria andNotesGreaterThan(String value) {
            addCriterion("notes >", value, "notes");
            return (Criteria) this;
        }

        public Criteria andNotesGreaterThanOrEqualTo(String value) {
            addCriterion("notes >=", value, "notes");
            return (Criteria) this;
        }

        public Criteria andNotesLessThan(String value) {
            addCriterion("notes <", value, "notes");
            return (Criteria) this;
        }

        public Criteria andNotesLessThanOrEqualTo(String value) {
            addCriterion("notes <=", value, "notes");
            return (Criteria) this;
        }

        public Criteria andNotesLike(String value) {
            addCriterion("notes like", value, "notes");
            return (Criteria) this;
        }

        public Criteria andNotesNotLike(String value) {
            addCriterion("notes not like", value, "notes");
            return (Criteria) this;
        }

        public Criteria andNotesIn(List<String> values) {
            addCriterion("notes in", values, "notes");
            return (Criteria) this;
        }

        public Criteria andNotesNotIn(List<String> values) {
            addCriterion("notes not in", values, "notes");
            return (Criteria) this;
        }

        public Criteria andNotesBetween(String value1, String value2) {
            addCriterion("notes between", value1, value2, "notes");
            return (Criteria) this;
        }

        public Criteria andNotesNotBetween(String value1, String value2) {
            addCriterion("notes not between", value1, value2, "notes");
            return (Criteria) this;
        }

        public Criteria andCoscoCodeIsNull() {
            addCriterion("cosco_code is null");
            return (Criteria) this;
        }

        public Criteria andCoscoCodeIsNotNull() {
            addCriterion("cosco_code is not null");
            return (Criteria) this;
        }

        public Criteria andCoscoCodeEqualTo(String value) {
            addCriterion("cosco_code =", value, "coscoCode");
            return (Criteria) this;
        }

        public Criteria andCoscoCodeNotEqualTo(String value) {
            addCriterion("cosco_code <>", value, "coscoCode");
            return (Criteria) this;
        }

        public Criteria andCoscoCodeGreaterThan(String value) {
            addCriterion("cosco_code >", value, "coscoCode");
            return (Criteria) this;
        }

        public Criteria andCoscoCodeGreaterThanOrEqualTo(String value) {
            addCriterion("cosco_code >=", value, "coscoCode");
            return (Criteria) this;
        }

        public Criteria andCoscoCodeLessThan(String value) {
            addCriterion("cosco_code <", value, "coscoCode");
            return (Criteria) this;
        }

        public Criteria andCoscoCodeLessThanOrEqualTo(String value) {
            addCriterion("cosco_code <=", value, "coscoCode");
            return (Criteria) this;
        }

        public Criteria andCoscoCodeLike(String value) {
            addCriterion("cosco_code like", value, "coscoCode");
            return (Criteria) this;
        }

        public Criteria andCoscoCodeNotLike(String value) {
            addCriterion("cosco_code not like", value, "coscoCode");
            return (Criteria) this;
        }

        public Criteria andCoscoCodeIn(List<String> values) {
            addCriterion("cosco_code in", values, "coscoCode");
            return (Criteria) this;
        }

        public Criteria andCoscoCodeNotIn(List<String> values) {
            addCriterion("cosco_code not in", values, "coscoCode");
            return (Criteria) this;
        }

        public Criteria andCoscoCodeBetween(String value1, String value2) {
            addCriterion("cosco_code between", value1, value2, "coscoCode");
            return (Criteria) this;
        }

        public Criteria andCoscoCodeNotBetween(String value1, String value2) {
            addCriterion("cosco_code not between", value1, value2, "coscoCode");
            return (Criteria) this;
        }

        public Criteria andOoclCodeIsNull() {
            addCriterion("oocl_code is null");
            return (Criteria) this;
        }

        public Criteria andOoclCodeIsNotNull() {
            addCriterion("oocl_code is not null");
            return (Criteria) this;
        }

        public Criteria andOoclCodeEqualTo(String value) {
            addCriterion("oocl_code =", value, "ooclCode");
            return (Criteria) this;
        }

        public Criteria andOoclCodeNotEqualTo(String value) {
            addCriterion("oocl_code <>", value, "ooclCode");
            return (Criteria) this;
        }

        public Criteria andOoclCodeGreaterThan(String value) {
            addCriterion("oocl_code >", value, "ooclCode");
            return (Criteria) this;
        }

        public Criteria andOoclCodeGreaterThanOrEqualTo(String value) {
            addCriterion("oocl_code >=", value, "ooclCode");
            return (Criteria) this;
        }

        public Criteria andOoclCodeLessThan(String value) {
            addCriterion("oocl_code <", value, "ooclCode");
            return (Criteria) this;
        }

        public Criteria andOoclCodeLessThanOrEqualTo(String value) {
            addCriterion("oocl_code <=", value, "ooclCode");
            return (Criteria) this;
        }

        public Criteria andOoclCodeLike(String value) {
            addCriterion("oocl_code like", value, "ooclCode");
            return (Criteria) this;
        }

        public Criteria andOoclCodeNotLike(String value) {
            addCriterion("oocl_code not like", value, "ooclCode");
            return (Criteria) this;
        }

        public Criteria andOoclCodeIn(List<String> values) {
            addCriterion("oocl_code in", values, "ooclCode");
            return (Criteria) this;
        }

        public Criteria andOoclCodeNotIn(List<String> values) {
            addCriterion("oocl_code not in", values, "ooclCode");
            return (Criteria) this;
        }

        public Criteria andOoclCodeBetween(String value1, String value2) {
            addCriterion("oocl_code between", value1, value2, "ooclCode");
            return (Criteria) this;
        }

        public Criteria andOoclCodeNotBetween(String value1, String value2) {
            addCriterion("oocl_code not between", value1, value2, "ooclCode");
            return (Criteria) this;
        }

        public Criteria andEmcCodeIsNull() {
            addCriterion("emc_code is null");
            return (Criteria) this;
        }

        public Criteria andEmcCodeIsNotNull() {
            addCriterion("emc_code is not null");
            return (Criteria) this;
        }

        public Criteria andEmcCodeEqualTo(String value) {
            addCriterion("emc_code =", value, "emcCode");
            return (Criteria) this;
        }

        public Criteria andEmcCodeNotEqualTo(String value) {
            addCriterion("emc_code <>", value, "emcCode");
            return (Criteria) this;
        }

        public Criteria andEmcCodeGreaterThan(String value) {
            addCriterion("emc_code >", value, "emcCode");
            return (Criteria) this;
        }

        public Criteria andEmcCodeGreaterThanOrEqualTo(String value) {
            addCriterion("emc_code >=", value, "emcCode");
            return (Criteria) this;
        }

        public Criteria andEmcCodeLessThan(String value) {
            addCriterion("emc_code <", value, "emcCode");
            return (Criteria) this;
        }

        public Criteria andEmcCodeLessThanOrEqualTo(String value) {
            addCriterion("emc_code <=", value, "emcCode");
            return (Criteria) this;
        }

        public Criteria andEmcCodeLike(String value) {
            addCriterion("emc_code like", value, "emcCode");
            return (Criteria) this;
        }

        public Criteria andEmcCodeNotLike(String value) {
            addCriterion("emc_code not like", value, "emcCode");
            return (Criteria) this;
        }

        public Criteria andEmcCodeIn(List<String> values) {
            addCriterion("emc_code in", values, "emcCode");
            return (Criteria) this;
        }

        public Criteria andEmcCodeNotIn(List<String> values) {
            addCriterion("emc_code not in", values, "emcCode");
            return (Criteria) this;
        }

        public Criteria andEmcCodeBetween(String value1, String value2) {
            addCriterion("emc_code between", value1, value2, "emcCode");
            return (Criteria) this;
        }

        public Criteria andEmcCodeNotBetween(String value1, String value2) {
            addCriterion("emc_code not between", value1, value2, "emcCode");
            return (Criteria) this;
        }

        public Criteria andMskCodeIsNull() {
            addCriterion("msk_code is null");
            return (Criteria) this;
        }

        public Criteria andMskCodeIsNotNull() {
            addCriterion("msk_code is not null");
            return (Criteria) this;
        }

        public Criteria andMskCodeEqualTo(String value) {
            addCriterion("msk_code =", value, "mskCode");
            return (Criteria) this;
        }

        public Criteria andMskCodeNotEqualTo(String value) {
            addCriterion("msk_code <>", value, "mskCode");
            return (Criteria) this;
        }

        public Criteria andMskCodeGreaterThan(String value) {
            addCriterion("msk_code >", value, "mskCode");
            return (Criteria) this;
        }

        public Criteria andMskCodeGreaterThanOrEqualTo(String value) {
            addCriterion("msk_code >=", value, "mskCode");
            return (Criteria) this;
        }

        public Criteria andMskCodeLessThan(String value) {
            addCriterion("msk_code <", value, "mskCode");
            return (Criteria) this;
        }

        public Criteria andMskCodeLessThanOrEqualTo(String value) {
            addCriterion("msk_code <=", value, "mskCode");
            return (Criteria) this;
        }

        public Criteria andMskCodeLike(String value) {
            addCriterion("msk_code like", value, "mskCode");
            return (Criteria) this;
        }

        public Criteria andMskCodeNotLike(String value) {
            addCriterion("msk_code not like", value, "mskCode");
            return (Criteria) this;
        }

        public Criteria andMskCodeIn(List<String> values) {
            addCriterion("msk_code in", values, "mskCode");
            return (Criteria) this;
        }

        public Criteria andMskCodeNotIn(List<String> values) {
            addCriterion("msk_code not in", values, "mskCode");
            return (Criteria) this;
        }

        public Criteria andMskCodeBetween(String value1, String value2) {
            addCriterion("msk_code between", value1, value2, "mskCode");
            return (Criteria) this;
        }

        public Criteria andMskCodeNotBetween(String value1, String value2) {
            addCriterion("msk_code not between", value1, value2, "mskCode");
            return (Criteria) this;
        }

        public Criteria andMccCodeIsNull() {
            addCriterion("mcc_code is null");
            return (Criteria) this;
        }

        public Criteria andMccCodeIsNotNull() {
            addCriterion("mcc_code is not null");
            return (Criteria) this;
        }

        public Criteria andMccCodeEqualTo(String value) {
            addCriterion("mcc_code =", value, "mccCode");
            return (Criteria) this;
        }

        public Criteria andMccCodeNotEqualTo(String value) {
            addCriterion("mcc_code <>", value, "mccCode");
            return (Criteria) this;
        }

        public Criteria andMccCodeGreaterThan(String value) {
            addCriterion("mcc_code >", value, "mccCode");
            return (Criteria) this;
        }

        public Criteria andMccCodeGreaterThanOrEqualTo(String value) {
            addCriterion("mcc_code >=", value, "mccCode");
            return (Criteria) this;
        }

        public Criteria andMccCodeLessThan(String value) {
            addCriterion("mcc_code <", value, "mccCode");
            return (Criteria) this;
        }

        public Criteria andMccCodeLessThanOrEqualTo(String value) {
            addCriterion("mcc_code <=", value, "mccCode");
            return (Criteria) this;
        }

        public Criteria andMccCodeLike(String value) {
            addCriterion("mcc_code like", value, "mccCode");
            return (Criteria) this;
        }

        public Criteria andMccCodeNotLike(String value) {
            addCriterion("mcc_code not like", value, "mccCode");
            return (Criteria) this;
        }

        public Criteria andMccCodeIn(List<String> values) {
            addCriterion("mcc_code in", values, "mccCode");
            return (Criteria) this;
        }

        public Criteria andMccCodeNotIn(List<String> values) {
            addCriterion("mcc_code not in", values, "mccCode");
            return (Criteria) this;
        }

        public Criteria andMccCodeBetween(String value1, String value2) {
            addCriterion("mcc_code between", value1, value2, "mccCode");
            return (Criteria) this;
        }

        public Criteria andMccCodeNotBetween(String value1, String value2) {
            addCriterion("mcc_code not between", value1, value2, "mccCode");
            return (Criteria) this;
        }

        public Criteria andOneCodeIsNull() {
            addCriterion("one_code is null");
            return (Criteria) this;
        }

        public Criteria andOneCodeIsNotNull() {
            addCriterion("one_code is not null");
            return (Criteria) this;
        }

        public Criteria andOneCodeEqualTo(String value) {
            addCriterion("one_code =", value, "oneCode");
            return (Criteria) this;
        }

        public Criteria andOneCodeNotEqualTo(String value) {
            addCriterion("one_code <>", value, "oneCode");
            return (Criteria) this;
        }

        public Criteria andOneCodeGreaterThan(String value) {
            addCriterion("one_code >", value, "oneCode");
            return (Criteria) this;
        }

        public Criteria andOneCodeGreaterThanOrEqualTo(String value) {
            addCriterion("one_code >=", value, "oneCode");
            return (Criteria) this;
        }

        public Criteria andOneCodeLessThan(String value) {
            addCriterion("one_code <", value, "oneCode");
            return (Criteria) this;
        }

        public Criteria andOneCodeLessThanOrEqualTo(String value) {
            addCriterion("one_code <=", value, "oneCode");
            return (Criteria) this;
        }

        public Criteria andOneCodeLike(String value) {
            addCriterion("one_code like", value, "oneCode");
            return (Criteria) this;
        }

        public Criteria andOneCodeNotLike(String value) {
            addCriterion("one_code not like", value, "oneCode");
            return (Criteria) this;
        }

        public Criteria andOneCodeIn(List<String> values) {
            addCriterion("one_code in", values, "oneCode");
            return (Criteria) this;
        }

        public Criteria andOneCodeNotIn(List<String> values) {
            addCriterion("one_code not in", values, "oneCode");
            return (Criteria) this;
        }

        public Criteria andOneCodeBetween(String value1, String value2) {
            addCriterion("one_code between", value1, value2, "oneCode");
            return (Criteria) this;
        }

        public Criteria andOneCodeNotBetween(String value1, String value2) {
            addCriterion("one_code not between", value1, value2, "oneCode");
            return (Criteria) this;
        }

        public Criteria andMscCodeIsNull() {
            addCriterion("msc_code is null");
            return (Criteria) this;
        }

        public Criteria andMscCodeIsNotNull() {
            addCriterion("msc_code is not null");
            return (Criteria) this;
        }

        public Criteria andMscCodeEqualTo(String value) {
            addCriterion("msc_code =", value, "mscCode");
            return (Criteria) this;
        }

        public Criteria andMscCodeNotEqualTo(String value) {
            addCriterion("msc_code <>", value, "mscCode");
            return (Criteria) this;
        }

        public Criteria andMscCodeGreaterThan(String value) {
            addCriterion("msc_code >", value, "mscCode");
            return (Criteria) this;
        }

        public Criteria andMscCodeGreaterThanOrEqualTo(String value) {
            addCriterion("msc_code >=", value, "mscCode");
            return (Criteria) this;
        }

        public Criteria andMscCodeLessThan(String value) {
            addCriterion("msc_code <", value, "mscCode");
            return (Criteria) this;
        }

        public Criteria andMscCodeLessThanOrEqualTo(String value) {
            addCriterion("msc_code <=", value, "mscCode");
            return (Criteria) this;
        }

        public Criteria andMscCodeLike(String value) {
            addCriterion("msc_code like", value, "mscCode");
            return (Criteria) this;
        }

        public Criteria andMscCodeNotLike(String value) {
            addCriterion("msc_code not like", value, "mscCode");
            return (Criteria) this;
        }

        public Criteria andMscCodeIn(List<String> values) {
            addCriterion("msc_code in", values, "mscCode");
            return (Criteria) this;
        }

        public Criteria andMscCodeNotIn(List<String> values) {
            addCriterion("msc_code not in", values, "mscCode");
            return (Criteria) this;
        }

        public Criteria andMscCodeBetween(String value1, String value2) {
            addCriterion("msc_code between", value1, value2, "mscCode");
            return (Criteria) this;
        }

        public Criteria andMscCodeNotBetween(String value1, String value2) {
            addCriterion("msc_code not between", value1, value2, "mscCode");
            return (Criteria) this;
        }

        public Criteria andZimCodeIsNull() {
            addCriterion("zim_code is null");
            return (Criteria) this;
        }

        public Criteria andZimCodeIsNotNull() {
            addCriterion("zim_code is not null");
            return (Criteria) this;
        }

        public Criteria andZimCodeEqualTo(String value) {
            addCriterion("zim_code =", value, "zimCode");
            return (Criteria) this;
        }

        public Criteria andZimCodeNotEqualTo(String value) {
            addCriterion("zim_code <>", value, "zimCode");
            return (Criteria) this;
        }

        public Criteria andZimCodeGreaterThan(String value) {
            addCriterion("zim_code >", value, "zimCode");
            return (Criteria) this;
        }

        public Criteria andZimCodeGreaterThanOrEqualTo(String value) {
            addCriterion("zim_code >=", value, "zimCode");
            return (Criteria) this;
        }

        public Criteria andZimCodeLessThan(String value) {
            addCriterion("zim_code <", value, "zimCode");
            return (Criteria) this;
        }

        public Criteria andZimCodeLessThanOrEqualTo(String value) {
            addCriterion("zim_code <=", value, "zimCode");
            return (Criteria) this;
        }

        public Criteria andZimCodeLike(String value) {
            addCriterion("zim_code like", value, "zimCode");
            return (Criteria) this;
        }

        public Criteria andZimCodeNotLike(String value) {
            addCriterion("zim_code not like", value, "zimCode");
            return (Criteria) this;
        }

        public Criteria andZimCodeIn(List<String> values) {
            addCriterion("zim_code in", values, "zimCode");
            return (Criteria) this;
        }

        public Criteria andZimCodeNotIn(List<String> values) {
            addCriterion("zim_code not in", values, "zimCode");
            return (Criteria) this;
        }

        public Criteria andZimCodeBetween(String value1, String value2) {
            addCriterion("zim_code between", value1, value2, "zimCode");
            return (Criteria) this;
        }

        public Criteria andZimCodeNotBetween(String value1, String value2) {
            addCriterion("zim_code not between", value1, value2, "zimCode");
            return (Criteria) this;
        }

        public Criteria andHmmCodeIsNull() {
            addCriterion("hmm_code is null");
            return (Criteria) this;
        }

        public Criteria andHmmCodeIsNotNull() {
            addCriterion("hmm_code is not null");
            return (Criteria) this;
        }

        public Criteria andHmmCodeEqualTo(String value) {
            addCriterion("hmm_code =", value, "hmmCode");
            return (Criteria) this;
        }

        public Criteria andHmmCodeNotEqualTo(String value) {
            addCriterion("hmm_code <>", value, "hmmCode");
            return (Criteria) this;
        }

        public Criteria andHmmCodeGreaterThan(String value) {
            addCriterion("hmm_code >", value, "hmmCode");
            return (Criteria) this;
        }

        public Criteria andHmmCodeGreaterThanOrEqualTo(String value) {
            addCriterion("hmm_code >=", value, "hmmCode");
            return (Criteria) this;
        }

        public Criteria andHmmCodeLessThan(String value) {
            addCriterion("hmm_code <", value, "hmmCode");
            return (Criteria) this;
        }

        public Criteria andHmmCodeLessThanOrEqualTo(String value) {
            addCriterion("hmm_code <=", value, "hmmCode");
            return (Criteria) this;
        }

        public Criteria andHmmCodeLike(String value) {
            addCriterion("hmm_code like", value, "hmmCode");
            return (Criteria) this;
        }

        public Criteria andHmmCodeNotLike(String value) {
            addCriterion("hmm_code not like", value, "hmmCode");
            return (Criteria) this;
        }

        public Criteria andHmmCodeIn(List<String> values) {
            addCriterion("hmm_code in", values, "hmmCode");
            return (Criteria) this;
        }

        public Criteria andHmmCodeNotIn(List<String> values) {
            addCriterion("hmm_code not in", values, "hmmCode");
            return (Criteria) this;
        }

        public Criteria andHmmCodeBetween(String value1, String value2) {
            addCriterion("hmm_code between", value1, value2, "hmmCode");
            return (Criteria) this;
        }

        public Criteria andHmmCodeNotBetween(String value1, String value2) {
            addCriterion("hmm_code not between", value1, value2, "hmmCode");
            return (Criteria) this;
        }

        public Criteria andCmaCodeIsNull() {
            addCriterion("cma_code is null");
            return (Criteria) this;
        }

        public Criteria andCmaCodeIsNotNull() {
            addCriterion("cma_code is not null");
            return (Criteria) this;
        }

        public Criteria andCmaCodeEqualTo(String value) {
            addCriterion("cma_code =", value, "cmaCode");
            return (Criteria) this;
        }

        public Criteria andCmaCodeNotEqualTo(String value) {
            addCriterion("cma_code <>", value, "cmaCode");
            return (Criteria) this;
        }

        public Criteria andCmaCodeGreaterThan(String value) {
            addCriterion("cma_code >", value, "cmaCode");
            return (Criteria) this;
        }

        public Criteria andCmaCodeGreaterThanOrEqualTo(String value) {
            addCriterion("cma_code >=", value, "cmaCode");
            return (Criteria) this;
        }

        public Criteria andCmaCodeLessThan(String value) {
            addCriterion("cma_code <", value, "cmaCode");
            return (Criteria) this;
        }

        public Criteria andCmaCodeLessThanOrEqualTo(String value) {
            addCriterion("cma_code <=", value, "cmaCode");
            return (Criteria) this;
        }

        public Criteria andCmaCodeLike(String value) {
            addCriterion("cma_code like", value, "cmaCode");
            return (Criteria) this;
        }

        public Criteria andCmaCodeNotLike(String value) {
            addCriterion("cma_code not like", value, "cmaCode");
            return (Criteria) this;
        }

        public Criteria andCmaCodeIn(List<String> values) {
            addCriterion("cma_code in", values, "cmaCode");
            return (Criteria) this;
        }

        public Criteria andCmaCodeNotIn(List<String> values) {
            addCriterion("cma_code not in", values, "cmaCode");
            return (Criteria) this;
        }

        public Criteria andCmaCodeBetween(String value1, String value2) {
            addCriterion("cma_code between", value1, value2, "cmaCode");
            return (Criteria) this;
        }

        public Criteria andCmaCodeNotBetween(String value1, String value2) {
            addCriterion("cma_code not between", value1, value2, "cmaCode");
            return (Criteria) this;
        }

        public Criteria andCncCodeIsNull() {
            addCriterion("cnc_code is null");
            return (Criteria) this;
        }

        public Criteria andCncCodeIsNotNull() {
            addCriterion("cnc_code is not null");
            return (Criteria) this;
        }

        public Criteria andCncCodeEqualTo(String value) {
            addCriterion("cnc_code =", value, "cncCode");
            return (Criteria) this;
        }

        public Criteria andCncCodeNotEqualTo(String value) {
            addCriterion("cnc_code <>", value, "cncCode");
            return (Criteria) this;
        }

        public Criteria andCncCodeGreaterThan(String value) {
            addCriterion("cnc_code >", value, "cncCode");
            return (Criteria) this;
        }

        public Criteria andCncCodeGreaterThanOrEqualTo(String value) {
            addCriterion("cnc_code >=", value, "cncCode");
            return (Criteria) this;
        }

        public Criteria andCncCodeLessThan(String value) {
            addCriterion("cnc_code <", value, "cncCode");
            return (Criteria) this;
        }

        public Criteria andCncCodeLessThanOrEqualTo(String value) {
            addCriterion("cnc_code <=", value, "cncCode");
            return (Criteria) this;
        }

        public Criteria andCncCodeLike(String value) {
            addCriterion("cnc_code like", value, "cncCode");
            return (Criteria) this;
        }

        public Criteria andCncCodeNotLike(String value) {
            addCriterion("cnc_code not like", value, "cncCode");
            return (Criteria) this;
        }

        public Criteria andCncCodeIn(List<String> values) {
            addCriterion("cnc_code in", values, "cncCode");
            return (Criteria) this;
        }

        public Criteria andCncCodeNotIn(List<String> values) {
            addCriterion("cnc_code not in", values, "cncCode");
            return (Criteria) this;
        }

        public Criteria andCncCodeBetween(String value1, String value2) {
            addCriterion("cnc_code between", value1, value2, "cncCode");
            return (Criteria) this;
        }

        public Criteria andCncCodeNotBetween(String value1, String value2) {
            addCriterion("cnc_code not between", value1, value2, "cncCode");
            return (Criteria) this;
        }

        public Criteria andHplCodeIsNull() {
            addCriterion("hpl_code is null");
            return (Criteria) this;
        }

        public Criteria andHplCodeIsNotNull() {
            addCriterion("hpl_code is not null");
            return (Criteria) this;
        }

        public Criteria andHplCodeEqualTo(String value) {
            addCriterion("hpl_code =", value, "hplCode");
            return (Criteria) this;
        }

        public Criteria andHplCodeNotEqualTo(String value) {
            addCriterion("hpl_code <>", value, "hplCode");
            return (Criteria) this;
        }

        public Criteria andHplCodeGreaterThan(String value) {
            addCriterion("hpl_code >", value, "hplCode");
            return (Criteria) this;
        }

        public Criteria andHplCodeGreaterThanOrEqualTo(String value) {
            addCriterion("hpl_code >=", value, "hplCode");
            return (Criteria) this;
        }

        public Criteria andHplCodeLessThan(String value) {
            addCriterion("hpl_code <", value, "hplCode");
            return (Criteria) this;
        }

        public Criteria andHplCodeLessThanOrEqualTo(String value) {
            addCriterion("hpl_code <=", value, "hplCode");
            return (Criteria) this;
        }

        public Criteria andHplCodeLike(String value) {
            addCriterion("hpl_code like", value, "hplCode");
            return (Criteria) this;
        }

        public Criteria andHplCodeNotLike(String value) {
            addCriterion("hpl_code not like", value, "hplCode");
            return (Criteria) this;
        }

        public Criteria andHplCodeIn(List<String> values) {
            addCriterion("hpl_code in", values, "hplCode");
            return (Criteria) this;
        }

        public Criteria andHplCodeNotIn(List<String> values) {
            addCriterion("hpl_code not in", values, "hplCode");
            return (Criteria) this;
        }

        public Criteria andHplCodeBetween(String value1, String value2) {
            addCriterion("hpl_code between", value1, value2, "hplCode");
            return (Criteria) this;
        }

        public Criteria andHplCodeNotBetween(String value1, String value2) {
            addCriterion("hpl_code not between", value1, value2, "hplCode");
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