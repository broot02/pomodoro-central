package com.pomodoro.integration.manuscript.domain;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "data", "errors", "warnings", "meta", "errorCode", "maxCacheAge" })
public class ManuscriptApiResponse implements Serializable {

	@JsonProperty("data")
	private Data data;
	@JsonProperty("errors")
	private List<Error> errors = null;
	@JsonProperty("warnings")
	private List<Object> warnings = null;
	@JsonProperty("meta")
	private Meta meta;
	@JsonProperty("errorCode")
	private Integer errorCode;
	@JsonProperty("maxCacheAge")
	private Object maxCacheAge;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();
	private final static long serialVersionUID = -2499849796314485772L;

	@JsonProperty("data")
	public Data getData() {
		return data;
	}

	@JsonProperty("data")
	public void setData(Data data) {
		this.data = data;
	}

	@JsonProperty("errors")
	public List<Error> getErrors() {
		return errors;
	}

	@JsonProperty("errors")
	public void setErrors(List<Error> errors) {
		this.errors = errors;
	}

	@JsonProperty("warnings")
	public List<Object> getWarnings() {
		return warnings;
	}

	@JsonProperty("warnings")
	public void setWarnings(List<Object> warnings) {
		this.warnings = warnings;
	}

	@JsonProperty("meta")
	public Meta getMeta() {
		return meta;
	}

	@JsonProperty("meta")
	public void setMeta(Meta meta) {
		this.meta = meta;
	}

	@JsonProperty("errorCode")
	public Integer getErrorCode() {
		return errorCode;
	}

	@JsonProperty("errorCode")
	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}

	@JsonProperty("maxCacheAge")
	public Object getMaxCacheAge() {
		return maxCacheAge;
	}

	@JsonProperty("maxCacheAge")
	public void setMaxCacheAge(Object maxCacheAge) {
		this.maxCacheAge = maxCacheAge;
	}

	@JsonAnyGetter
	public Map<String, Object> getAdditionalProperties() {
		return this.additionalProperties;
	}

	@JsonAnySetter
	public void setAdditionalProperty(String name, Object value) {
		this.additionalProperties.put(name, value);
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("data", data).append("errors", errors).append("warnings", warnings)
				.append("meta", meta).append("errorCode", errorCode).append("maxCacheAge", maxCacheAge)
				.append("additionalProperties", additionalProperties).toString();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(maxCacheAge).append(errors).append(additionalProperties).append(errorCode)
				.append(data).append(warnings).append(meta).toHashCode();
	}

	@Override
	public boolean equals(Object other) {
		if (other == this) {
			return true;
		}
		if ((other instanceof ManuscriptApiResponse) == false) {
			return false;
		}
		ManuscriptApiResponse rhs = ((ManuscriptApiResponse) other);
		return new EqualsBuilder().append(maxCacheAge, rhs.maxCacheAge).append(errors, rhs.errors)
				.append(additionalProperties, rhs.additionalProperties).append(errorCode, rhs.errorCode)
				.append(data, rhs.data).append(warnings, rhs.warnings).append(meta, rhs.meta).isEquals();
	}

}