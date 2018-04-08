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

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "jsdbInvalidator", "clientVersionAllowed" })
public class Meta implements Serializable {

	@JsonProperty("jsdbInvalidator")
	private String jsdbInvalidator;
	@JsonProperty("clientVersionAllowed")
	private ClientVersionAllowed clientVersionAllowed;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();
	private final static long serialVersionUID = -813120166073252194L;

	@JsonProperty("jsdbInvalidator")
	public String getJsdbInvalidator() {
		return jsdbInvalidator;
	}

	@JsonProperty("jsdbInvalidator")
	public void setJsdbInvalidator(String jsdbInvalidator) {
		this.jsdbInvalidator = jsdbInvalidator;
	}

	@JsonProperty("clientVersionAllowed")
	public ClientVersionAllowed getClientVersionAllowed() {
		return clientVersionAllowed;
	}

	@JsonProperty("clientVersionAllowed")
	public void setClientVersionAllowed(ClientVersionAllowed clientVersionAllowed) {
		this.clientVersionAllowed = clientVersionAllowed;
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
		return new ToStringBuilder(this).append("jsdbInvalidator", jsdbInvalidator)
				.append("clientVersionAllowed", clientVersionAllowed)
				.append("additionalProperties", additionalProperties).toString();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(jsdbInvalidator).append(additionalProperties).append(clientVersionAllowed)
				.toHashCode();
	}

	@Override
	public boolean equals(Object other) {
		if (other == this) {
			return true;
		}
		if ((other instanceof Meta) == false) {
			return false;
		}
		Meta rhs = ((Meta) other);
		return new EqualsBuilder().append(jsdbInvalidator, rhs.jsdbInvalidator)
				.append(additionalProperties, rhs.additionalProperties)
				.append(clientVersionAllowed, rhs.clientVersionAllowed).isEquals();
	}

}