package com.pomodoro.integration.manuscript.domain;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "min", "max" })
public class ClientVersionAllowed implements Serializable {

	@JsonProperty("min")
	private Integer min;
	@JsonProperty("max")
	private Integer max;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();
	private final static long serialVersionUID = -3621052683789555058L;

	@JsonProperty("min")
	public Integer getMin() {
		return min;
	}

	@JsonProperty("min")
	public void setMin(Integer min) {
		this.min = min;
	}

	@JsonProperty("max")
	public Integer getMax() {
		return max;
	}

	@JsonProperty("max")
	public void setMax(Integer max) {
		this.max = max;
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
		return new org.apache.commons.lang3.builder.ToStringBuilder(this).append("min", min).append("max", max)
				.append("additionalProperties", additionalProperties).toString();
	}

	@Override
	public int hashCode() {
		return new org.apache.commons.lang3.builder.HashCodeBuilder().append(min).append(max)
				.append(additionalProperties).toHashCode();
	}

	@Override
	public boolean equals(Object other) {
		if (other == this) {
			return true;
		}
		if ((other instanceof ClientVersionAllowed) == false) {
			return false;
		}
		ClientVersionAllowed rhs = ((ClientVersionAllowed) other);
		return new org.apache.commons.lang3.builder.EqualsBuilder().append(min, rhs.min).append(max, rhs.max)
				.append(additionalProperties, rhs.additionalProperties).isEquals();
	}

}