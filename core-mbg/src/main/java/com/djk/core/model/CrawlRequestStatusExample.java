package com.djk.core.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CrawlRequestStatusExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public CrawlRequestStatusExample() {
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

        public Criteria andRequestParamsIsNull() {
            addCriterion("request_params is null");
            return (Criteria) this;
        }

        public Criteria andRequestParamsIsNotNull() {
            addCriterion("request_params is not null");
            return (Criteria) this;
        }

        public Criteria andRequestParamsEqualTo(String value) {
            addCriterion("request_params =", value, "requestParams");
            return (Criteria) this;
        }

        public Criteria andRequestParamsNotEqualTo(String value) {
            addCriterion("request_params <>", value, "requestParams");
            return (Criteria) this;
        }

        public Criteria andRequestParamsGreaterThan(String value) {
            addCriterion("request_params >", value, "requestParams");
            return (Criteria) this;
        }

        public Criteria andRequestParamsGreaterThanOrEqualTo(String value) {
            addCriterion("request_params >=", value, "requestParams");
            return (Criteria) this;
        }

        public Criteria andRequestParamsLessThan(String value) {
            addCriterion("request_params <", value, "requestParams");
            return (Criteria) this;
        }

        public Criteria andRequestParamsLessThanOrEqualTo(String value) {
            addCriterion("request_params <=", value, "requestParams");
            return (Criteria) this;
        }

        public Criteria andRequestParamsLike(String value) {
            addCriterion("request_params like", value, "requestParams");
            return (Criteria) this;
        }

        public Criteria andRequestParamsNotLike(String value) {
            addCriterion("request_params not like", value, "requestParams");
            return (Criteria) this;
        }

        public Criteria andRequestParamsIn(List<String> values) {
            addCriterion("request_params in", values, "requestParams");
            return (Criteria) this;
        }

        public Criteria andRequestParamsNotIn(List<String> values) {
            addCriterion("request_params not in", values, "requestParams");
            return (Criteria) this;
        }

        public Criteria andRequestParamsBetween(String value1, String value2) {
            addCriterion("request_params between", value1, value2, "requestParams");
            return (Criteria) this;
        }

        public Criteria andRequestParamsNotBetween(String value1, String value2) {
            addCriterion("request_params not between", value1, value2, "requestParams");
            return (Criteria) this;
        }

        public Criteria andStartTimeIsNull() {
            addCriterion("start_time is null");
            return (Criteria) this;
        }

        public Criteria andStartTimeIsNotNull() {
            addCriterion("start_time is not null");
            return (Criteria) this;
        }

        public Criteria andStartTimeEqualTo(Long value) {
            addCriterion("start_time =", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeNotEqualTo(Long value) {
            addCriterion("start_time <>", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeGreaterThan(Long value) {
            addCriterion("start_time >", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeGreaterThanOrEqualTo(Long value) {
            addCriterion("start_time >=", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeLessThan(Long value) {
            addCriterion("start_time <", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeLessThanOrEqualTo(Long value) {
            addCriterion("start_time <=", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeIn(List<Long> values) {
            addCriterion("start_time in", values, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeNotIn(List<Long> values) {
            addCriterion("start_time not in", values, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeBetween(Long value1, Long value2) {
            addCriterion("start_time between", value1, value2, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeNotBetween(Long value1, Long value2) {
            addCriterion("start_time not between", value1, value2, "startTime");
            return (Criteria) this;
        }

        public Criteria andFromProtIsNull() {
            addCriterion("from_prot is null");
            return (Criteria) this;
        }

        public Criteria andFromProtIsNotNull() {
            addCriterion("from_prot is not null");
            return (Criteria) this;
        }

        public Criteria andFromProtEqualTo(String value) {
            addCriterion("from_prot =", value, "fromProt");
            return (Criteria) this;
        }

        public Criteria andFromProtNotEqualTo(String value) {
            addCriterion("from_prot <>", value, "fromProt");
            return (Criteria) this;
        }

        public Criteria andFromProtGreaterThan(String value) {
            addCriterion("from_prot >", value, "fromProt");
            return (Criteria) this;
        }

        public Criteria andFromProtGreaterThanOrEqualTo(String value) {
            addCriterion("from_prot >=", value, "fromProt");
            return (Criteria) this;
        }

        public Criteria andFromProtLessThan(String value) {
            addCriterion("from_prot <", value, "fromProt");
            return (Criteria) this;
        }

        public Criteria andFromProtLessThanOrEqualTo(String value) {
            addCriterion("from_prot <=", value, "fromProt");
            return (Criteria) this;
        }

        public Criteria andFromProtLike(String value) {
            addCriterion("from_prot like", value, "fromProt");
            return (Criteria) this;
        }

        public Criteria andFromProtNotLike(String value) {
            addCriterion("from_prot not like", value, "fromProt");
            return (Criteria) this;
        }

        public Criteria andFromProtIn(List<String> values) {
            addCriterion("from_prot in", values, "fromProt");
            return (Criteria) this;
        }

        public Criteria andFromProtNotIn(List<String> values) {
            addCriterion("from_prot not in", values, "fromProt");
            return (Criteria) this;
        }

        public Criteria andFromProtBetween(String value1, String value2) {
            addCriterion("from_prot between", value1, value2, "fromProt");
            return (Criteria) this;
        }

        public Criteria andFromProtNotBetween(String value1, String value2) {
            addCriterion("from_prot not between", value1, value2, "fromProt");
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

        public Criteria andStatusIsNull() {
            addCriterion("status is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("status is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(Integer value) {
            addCriterion("status =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(Integer value) {
            addCriterion("status <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(Integer value) {
            addCriterion("status >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("status >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(Integer value) {
            addCriterion("status <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(Integer value) {
            addCriterion("status <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<Integer> values) {
            addCriterion("status in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<Integer> values) {
            addCriterion("status not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(Integer value1, Integer value2) {
            addCriterion("status between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("status not between", value1, value2, "status");
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