package org.volifecycle.ui.vo;

import java.io.Serializable;
import java.util.Map;

/**
 * Simple action representation.
 * 
 * @author anthony attia <anthony.attia1@gmail.com>
 *
 */
public class SimpleAction implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * Id which is used for forced the result of this action
	 */
	private String id;

	/**
	 * description.
	 */
	private String description;

	private Map<String, String> additionnalInformations;

	/**
	 * @return the id.
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set.
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the additionnalInformations
	 */
	public Map<String, String> getAdditionnalInformations() {
		return additionnalInformations;
	}

	/**
	 * @param additionnalInformations
	 *            the additionnalInformations to set
	 */
	public void setAdditionnalInformations(Map<String, String> additionnalInformations) {
		this.additionnalInformations = additionnalInformations;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
