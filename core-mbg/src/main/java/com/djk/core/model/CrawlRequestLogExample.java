package com.djk.core.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CrawlRequestLogExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public CrawlRequestLogExample() {
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

        public Criteria andLogIdIsNull() {
            addCriterion("log_id is null");
            return (Criteria) this;
        }

        public Criteria andLogIdIsNotNull() {
            addCriterion("log_id is not null");
            return (Criteria) this;
        }

        public Criteria andLogIdEqualTo(String value) {
            addCriterion("log_id =", value, "logId");
            return (Criteria) this;
        }

        public Criteria andLogIdNotEqualTo(String value) {
            addCriterion("log_id <>", value, "logId");
            return (Criteria) this;
        }

        public Criteria andLogIdGreaterThan(String value) {
            addCriterion("log_id >", value, "logId");
            return (Criteria) this;
        }

        public Criteria andLogIdGreaterThanOrEqualTo(String value) {
            addCriterion("log_id >=", value, "logId");
            return (Criteria) this;
        }

        public Criteria andLogIdLessThan(String value) {
            addCriterion("log_id <", value, "logId");
            return (Criteria) this;
        }

        public Criteria andLogIdLessThanOrEqualTo(String value) {
            addCriterion("log_id <=", value, "logId");
            return (Criteria) this;
        }

        public Criteria andLogIdLike(String value) {
            addCriterion("log_id like", value, "logId");
            return (Criteria) this;
        }

        public Criteria andLogIdNotLike(String value) {
            addCriterion("log_id not like", value, "logId");
            return (Criteria) this;
        }

        public Criteria andLogIdIn(List<String> values) {
            addCriterion("log_id in", values, "logId");
            return (Criteria) this;
        }

        public Criteria andLogIdNotIn(List<String> values) {
            addCriterion("log_id not in", values, "logId");
            return (Criteria) this;
        }

        public Criteria andLogIdBetween(String value1, String value2) {
            addCriterion("log_id between", value1, value2, "logId");
            return (Criteria) this;
        }

        public Criteria andLogIdNotBetween(String value1, String value2) {
            addCriterion("log_id not between", value1, value2, "logId");
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

        public Criteria andDataIdIsNull() {
            addCriterion("data_id is null");
            return (Criteria) this;
        }

        public Criteria andDataIdIsNotNull() {
            addCriterion("data_id is not null");
            return (Criteria) this;
        }

        public Criteria andDataIdEqualTo(Long value) {
            addCriterion("data_id =", value, "dataId");
            return (Criteria) this;
        }

        public Criteria andDataIdNotEqualTo(Long value) {
            addCriterion("data_id <>", value, "dataId");
            return (Criteria) this;
        }

        public Criteria andDataIdGreaterThan(Long value) {
            addCriterion("data_id >", value, "dataId");
            return (Criteria) this;
        }

        public Criteria andDataIdGreaterThanOrEqualTo(Long value) {
            addCriterion("data_id >=", value, "dataId");
            return (Criteria) this;
        }

        public Criteria andDataIdLessThan(Long value) {
            addCriterion("data_id <", value, "dataId");
            return (Criteria) this;
        }

        public Criteria andDataIdLessThanOrEqualTo(Long value) {
            addCriterion("data_id <=", value, "dataId");
            return (Criteria) this;
        }

        public Criteria andDataIdIn(List<Long> values) {
            addCriterion("data_id in", values, "dataId");
            return (Criteria) this;
        }

        public Criteria andDataIdNotIn(List<Long> values) {
            addCriterion("data_id not in", values, "dataId");
            return (Criteria) this;
        }

        public Criteria andDataIdBetween(Long value1, Long value2) {
            addCriterion("data_id between", value1, value2, "dataId");
            return (Criteria) this;
        }

        public Criteria andDataIdNotBetween(Long value1, Long value2) {
            addCriterion("data_id not between", value1, value2, "dataId");
            return (Criteria) this;
        }

        public Criteria andBusinessNameIsNull() {
            addCriterion("business_name is null");
            return (Criteria) this;
        }

        public Criteria andBusinessNameIsNotNull() {
            addCriterion("business_name is not null");
            return (Criteria) this;
        }

        public Criteria andBusinessNameEqualTo(String value) {
            addCriterion("business_name =", value, "businessName");
            return (Criteria) this;
        }

        public Criteria andBusinessNameNotEqualTo(String value) {
            addCriterion("business_name <>", value, "businessName");
            return (Criteria) this;
        }

        public Criteria andBusinessNameGreaterThan(String value) {
            addCriterion("business_name >", value, "businessName");
            return (Criteria) this;
        }

        public Criteria andBusinessNameGreaterThanOrEqualTo(String value) {
            addCriterion("business_name >=", value, "businessName");
            return (Criteria) this;
        }

        public Criteria andBusinessNameLessThan(String value) {
            addCriterion("business_name <", value, "businessName");
            return (Criteria) this;
        }

        public Criteria andBusinessNameLessThanOrEqualTo(String value) {
            addCriterion("business_name <=", value, "businessName");
            return (Criteria) this;
        }

        public Criteria andBusinessNameLike(String value) {
            addCriterion("business_name like", value, "businessName");
            return (Criteria) this;
        }

        public Criteria andBusinessNameNotLike(String value) {
            addCriterion("business_name not like", value, "businessName");
            return (Criteria) this;
        }

        public Criteria andBusinessNameIn(List<String> values) {
            addCriterion("business_name in", values, "businessName");
            return (Criteria) this;
        }

        public Criteria andBusinessNameNotIn(List<String> values) {
            addCriterion("business_name not in", values, "businessName");
            return (Criteria) this;
        }

        public Criteria andBusinessNameBetween(String value1, String value2) {
            addCriterion("business_name between", value1, value2, "businessName");
            return (Criteria) this;
        }

        public Criteria andBusinessNameNotBetween(String value1, String value2) {
            addCriterion("business_name not between", value1, value2, "businessName");
            return (Criteria) this;
        }

        public Criteria andStepNumIsNull() {
            addCriterion("step_num is null");
            return (Criteria) this;
        }

        public Criteria andStepNumIsNotNull() {
            addCriterion("step_num is not null");
            return (Criteria) this;
        }

        public Criteria andStepNumEqualTo(Long value) {
            addCriterion("step_num =", value, "stepNum");
            return (Criteria) this;
        }

        public Criteria andStepNumNotEqualTo(Long value) {
            addCriterion("step_num <>", value, "stepNum");
            return (Criteria) this;
        }

        public Criteria andStepNumGreaterThan(Long value) {
            addCriterion("step_num >", value, "stepNum");
            return (Criteria) this;
        }

        public Criteria andStepNumGreaterThanOrEqualTo(Long value) {
            addCriterion("step_num >=", value, "stepNum");
            return (Criteria) this;
        }

        public Criteria andStepNumLessThan(Long value) {
            addCriterion("step_num <", value, "stepNum");
            return (Criteria) this;
        }

        public Criteria andStepNumLessThanOrEqualTo(Long value) {
            addCriterion("step_num <=", value, "stepNum");
            return (Criteria) this;
        }

        public Criteria andStepNumIn(List<Long> values) {
            addCriterion("step_num in", values, "stepNum");
            return (Criteria) this;
        }

        public Criteria andStepNumNotIn(List<Long> values) {
            addCriterion("step_num not in", values, "stepNum");
            return (Criteria) this;
        }

        public Criteria andStepNumBetween(Long value1, Long value2) {
            addCriterion("step_num between", value1, value2, "stepNum");
            return (Criteria) this;
        }

        public Criteria andStepNumNotBetween(Long value1, Long value2) {
            addCriterion("step_num not between", value1, value2, "stepNum");
            return (Criteria) this;
        }

        public Criteria andStepNameIsNull() {
            addCriterion("step_name is null");
            return (Criteria) this;
        }

        public Criteria andStepNameIsNotNull() {
            addCriterion("step_name is not null");
            return (Criteria) this;
        }

        public Criteria andStepNameEqualTo(String value) {
            addCriterion("step_name =", value, "stepName");
            return (Criteria) this;
        }

        public Criteria andStepNameNotEqualTo(String value) {
            addCriterion("step_name <>", value, "stepName");
            return (Criteria) this;
        }

        public Criteria andStepNameGreaterThan(String value) {
            addCriterion("step_name >", value, "stepName");
            return (Criteria) this;
        }

        public Criteria andStepNameGreaterThanOrEqualTo(String value) {
            addCriterion("step_name >=", value, "stepName");
            return (Criteria) this;
        }

        public Criteria andStepNameLessThan(String value) {
            addCriterion("step_name <", value, "stepName");
            return (Criteria) this;
        }

        public Criteria andStepNameLessThanOrEqualTo(String value) {
            addCriterion("step_name <=", value, "stepName");
            return (Criteria) this;
        }

        public Criteria andStepNameLike(String value) {
            addCriterion("step_name like", value, "stepName");
            return (Criteria) this;
        }

        public Criteria andStepNameNotLike(String value) {
            addCriterion("step_name not like", value, "stepName");
            return (Criteria) this;
        }

        public Criteria andStepNameIn(List<String> values) {
            addCriterion("step_name in", values, "stepName");
            return (Criteria) this;
        }

        public Criteria andStepNameNotIn(List<String> values) {
            addCriterion("step_name not in", values, "stepName");
            return (Criteria) this;
        }

        public Criteria andStepNameBetween(String value1, String value2) {
            addCriterion("step_name between", value1, value2, "stepName");
            return (Criteria) this;
        }

        public Criteria andStepNameNotBetween(String value1, String value2) {
            addCriterion("step_name not between", value1, value2, "stepName");
            return (Criteria) this;
        }

        public Criteria andTimePointIsNull() {
            addCriterion("time_point is null");
            return (Criteria) this;
        }

        public Criteria andTimePointIsNotNull() {
            addCriterion("time_point is not null");
            return (Criteria) this;
        }

        public Criteria andTimePointEqualTo(Long value) {
            addCriterion("time_point =", value, "timePoint");
            return (Criteria) this;
        }

        public Criteria andTimePointNotEqualTo(Long value) {
            addCriterion("time_point <>", value, "timePoint");
            return (Criteria) this;
        }

        public Criteria andTimePointGreaterThan(Long value) {
            addCriterion("time_point >", value, "timePoint");
            return (Criteria) this;
        }

        public Criteria andTimePointGreaterThanOrEqualTo(Long value) {
            addCriterion("time_point >=", value, "timePoint");
            return (Criteria) this;
        }

        public Criteria andTimePointLessThan(Long value) {
            addCriterion("time_point <", value, "timePoint");
            return (Criteria) this;
        }

        public Criteria andTimePointLessThanOrEqualTo(Long value) {
            addCriterion("time_point <=", value, "timePoint");
            return (Criteria) this;
        }

        public Criteria andTimePointIn(List<Long> values) {
            addCriterion("time_point in", values, "timePoint");
            return (Criteria) this;
        }

        public Criteria andTimePointNotIn(List<Long> values) {
            addCriterion("time_point not in", values, "timePoint");
            return (Criteria) this;
        }

        public Criteria andTimePointBetween(Long value1, Long value2) {
            addCriterion("time_point between", value1, value2, "timePoint");
            return (Criteria) this;
        }

        public Criteria andTimePointNotBetween(Long value1, Long value2) {
            addCriterion("time_point not between", value1, value2, "timePoint");
            return (Criteria) this;
        }

        public Criteria andFromPortIsNull() {
            addCriterion("from_port is null");
            return (Criteria) this;
        }

        public Criteria andFromPortIsNotNull() {
            addCriterion("from_port is not null");
            return (Criteria) this;
        }

        public Criteria andFromPortEqualTo(String value) {
            addCriterion("from_port =", value, "fromPort");
            return (Criteria) this;
        }

        public Criteria andFromPortNotEqualTo(String value) {
            addCriterion("from_port <>", value, "fromPort");
            return (Criteria) this;
        }

        public Criteria andFromPortGreaterThan(String value) {
            addCriterion("from_port >", value, "fromPort");
            return (Criteria) this;
        }

        public Criteria andFromPortGreaterThanOrEqualTo(String value) {
            addCriterion("from_port >=", value, "fromPort");
            return (Criteria) this;
        }

        public Criteria andFromPortLessThan(String value) {
            addCriterion("from_port <", value, "fromPort");
            return (Criteria) this;
        }

        public Criteria andFromPortLessThanOrEqualTo(String value) {
            addCriterion("from_port <=", value, "fromPort");
            return (Criteria) this;
        }

        public Criteria andFromPortLike(String value) {
            addCriterion("from_port like", value, "fromPort");
            return (Criteria) this;
        }

        public Criteria andFromPortNotLike(String value) {
            addCriterion("from_port not like", value, "fromPort");
            return (Criteria) this;
        }

        public Criteria andFromPortIn(List<String> values) {
            addCriterion("from_port in", values, "fromPort");
            return (Criteria) this;
        }

        public Criteria andFromPortNotIn(List<String> values) {
            addCriterion("from_port not in", values, "fromPort");
            return (Criteria) this;
        }

        public Criteria andFromPortBetween(String value1, String value2) {
            addCriterion("from_port between", value1, value2, "fromPort");
            return (Criteria) this;
        }

        public Criteria andFromPortNotBetween(String value1, String value2) {
            addCriterion("from_port not between", value1, value2, "fromPort");
            return (Criteria) this;
        }

        public Criteria andToPortIsNull() {
            addCriterion("to_port is null");
            return (Criteria) this;
        }

        public Criteria andToPortIsNotNull() {
            addCriterion("to_port is not null");
            return (Criteria) this;
        }

        public Criteria andToPortEqualTo(String value) {
            addCriterion("to_port =", value, "toPort");
            return (Criteria) this;
        }

        public Criteria andToPortNotEqualTo(String value) {
            addCriterion("to_port <>", value, "toPort");
            return (Criteria) this;
        }

        public Criteria andToPortGreaterThan(String value) {
            addCriterion("to_port >", value, "toPort");
            return (Criteria) this;
        }

        public Criteria andToPortGreaterThanOrEqualTo(String value) {
            addCriterion("to_port >=", value, "toPort");
            return (Criteria) this;
        }

        public Criteria andToPortLessThan(String value) {
            addCriterion("to_port <", value, "toPort");
            return (Criteria) this;
        }

        public Criteria andToPortLessThanOrEqualTo(String value) {
            addCriterion("to_port <=", value, "toPort");
            return (Criteria) this;
        }

        public Criteria andToPortLike(String value) {
            addCriterion("to_port like", value, "toPort");
            return (Criteria) this;
        }

        public Criteria andToPortNotLike(String value) {
            addCriterion("to_port not like", value, "toPort");
            return (Criteria) this;
        }

        public Criteria andToPortIn(List<String> values) {
            addCriterion("to_port in", values, "toPort");
            return (Criteria) this;
        }

        public Criteria andToPortNotIn(List<String> values) {
            addCriterion("to_port not in", values, "toPort");
            return (Criteria) this;
        }

        public Criteria andToPortBetween(String value1, String value2) {
            addCriterion("to_port between", value1, value2, "toPort");
            return (Criteria) this;
        }

        public Criteria andToPortNotBetween(String value1, String value2) {
            addCriterion("to_port not between", value1, value2, "toPort");
            return (Criteria) this;
        }

        public Criteria andDepartureDateIsNull() {
            addCriterion("departure_date is null");
            return (Criteria) this;
        }

        public Criteria andDepartureDateIsNotNull() {
            addCriterion("departure_date is not null");
            return (Criteria) this;
        }

        public Criteria andDepartureDateEqualTo(String value) {
            addCriterion("departure_date =", value, "departureDate");
            return (Criteria) this;
        }

        public Criteria andDepartureDateNotEqualTo(String value) {
            addCriterion("departure_date <>", value, "departureDate");
            return (Criteria) this;
        }

        public Criteria andDepartureDateGreaterThan(String value) {
            addCriterion("departure_date >", value, "departureDate");
            return (Criteria) this;
        }

        public Criteria andDepartureDateGreaterThanOrEqualTo(String value) {
            addCriterion("departure_date >=", value, "departureDate");
            return (Criteria) this;
        }

        public Criteria andDepartureDateLessThan(String value) {
            addCriterion("departure_date <", value, "departureDate");
            return (Criteria) this;
        }

        public Criteria andDepartureDateLessThanOrEqualTo(String value) {
            addCriterion("departure_date <=", value, "departureDate");
            return (Criteria) this;
        }

        public Criteria andDepartureDateLike(String value) {
            addCriterion("departure_date like", value, "departureDate");
            return (Criteria) this;
        }

        public Criteria andDepartureDateNotLike(String value) {
            addCriterion("departure_date not like", value, "departureDate");
            return (Criteria) this;
        }

        public Criteria andDepartureDateIn(List<String> values) {
            addCriterion("departure_date in", values, "departureDate");
            return (Criteria) this;
        }

        public Criteria andDepartureDateNotIn(List<String> values) {
            addCriterion("departure_date not in", values, "departureDate");
            return (Criteria) this;
        }

        public Criteria andDepartureDateBetween(String value1, String value2) {
            addCriterion("departure_date between", value1, value2, "departureDate");
            return (Criteria) this;
        }

        public Criteria andDepartureDateNotBetween(String value1, String value2) {
            addCriterion("departure_date not between", value1, value2, "departureDate");
            return (Criteria) this;
        }

        public Criteria andHostCodeIsNull() {
            addCriterion("host_code is null");
            return (Criteria) this;
        }

        public Criteria andHostCodeIsNotNull() {
            addCriterion("host_code is not null");
            return (Criteria) this;
        }

        public Criteria andHostCodeEqualTo(String value) {
            addCriterion("host_code =", value, "hostCode");
            return (Criteria) this;
        }

        public Criteria andHostCodeNotEqualTo(String value) {
            addCriterion("host_code <>", value, "hostCode");
            return (Criteria) this;
        }

        public Criteria andHostCodeGreaterThan(String value) {
            addCriterion("host_code >", value, "hostCode");
            return (Criteria) this;
        }

        public Criteria andHostCodeGreaterThanOrEqualTo(String value) {
            addCriterion("host_code >=", value, "hostCode");
            return (Criteria) this;
        }

        public Criteria andHostCodeLessThan(String value) {
            addCriterion("host_code <", value, "hostCode");
            return (Criteria) this;
        }

        public Criteria andHostCodeLessThanOrEqualTo(String value) {
            addCriterion("host_code <=", value, "hostCode");
            return (Criteria) this;
        }

        public Criteria andHostCodeLike(String value) {
            addCriterion("host_code like", value, "hostCode");
            return (Criteria) this;
        }

        public Criteria andHostCodeNotLike(String value) {
            addCriterion("host_code not like", value, "hostCode");
            return (Criteria) this;
        }

        public Criteria andHostCodeIn(List<String> values) {
            addCriterion("host_code in", values, "hostCode");
            return (Criteria) this;
        }

        public Criteria andHostCodeNotIn(List<String> values) {
            addCriterion("host_code not in", values, "hostCode");
            return (Criteria) this;
        }

        public Criteria andHostCodeBetween(String value1, String value2) {
            addCriterion("host_code between", value1, value2, "hostCode");
            return (Criteria) this;
        }

        public Criteria andHostCodeNotBetween(String value1, String value2) {
            addCriterion("host_code not between", value1, value2, "hostCode");
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