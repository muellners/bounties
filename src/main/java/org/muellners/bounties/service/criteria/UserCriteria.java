package org.muellners.bounties.service.criteria;

import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

import java.io.Serializable;

/**
 * Criteria class for the [cm.pwork.schoolman.domain.Option] entity. This class is used in
 * [cm.pwork.schoolman.web.rest.OptionResource] to receive all the possible filtering options from the
 * Http GET request parameters.
 * For example the following could be a valid request:
 * ```/options?id.greaterThan=5&attr1.contains=something&attr2.specified=false```
 * As Spring is unable to properly convert the types, unless specific [Filter] class are used, we need to use
 * fix type specific filters.
 */
public class UserCriteria implements Serializable, Criteria {

	private static final long serialVersionUID = 1L;

	private StringFilter id;
	private StringFilter login;
	private StringFilter firstName;
	private StringFilter lastName;
	private StringFilter email;
	private LongFilter profileId;

	public UserCriteria(final UserCriteria other) {
		other.id.copy();
		other.login.copy();
		other.firstName.copy();
		other.lastName.copy();
		other.email.copy();
		other.profileId.copy();
	}

	public StringFilter getId() {
		return id;
	}

	public void setId(StringFilter id) {
		this.id = id;
	}

	public StringFilter getLogin() {
		return login;
	}

	public void setLogin(StringFilter login) {
		this.login = login;
	}

	public StringFilter getFirstName() {
		return firstName;
	}

	public void setFirstName(StringFilter firstName) {
		this.firstName = firstName;
	}

	public StringFilter getLastName() {
		return lastName;
	}

	public void setLastName(StringFilter lastName) {
		this.lastName = lastName;
	}

	public StringFilter getEmail() {
		return email;
	}

	public void setEmail(StringFilter email) {
		this.email = email;
	}

	public LongFilter getProfileId() {
		return profileId;
	}

	public void setProfileId(LongFilter profileId) {
		this.profileId = profileId;
	}

	@Override
	public Criteria copy() {
		return new UserCriteria(this);
	}
}
