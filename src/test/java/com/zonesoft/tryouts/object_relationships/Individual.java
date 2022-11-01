package com.zonesoft.tryouts.object_relationships;

import static com.zonesoft.chatapp.utils.ToStringHelpers.c;
import static com.zonesoft.chatapp.utils.ToStringHelpers.lb;
import static com.zonesoft.chatapp.utils.ToStringHelpers.n;
import static com.zonesoft.chatapp.utils.ToStringHelpers.qv;
import static com.zonesoft.chatapp.utils.ToStringHelpers.rb;
import static com.zonesoft.chatapp.utils.ToStringHelpers.t;
import static com.zonesoft.chatapp.utils.ToStringHelpers.v;

public class Individual {

	private String firstname;
	private String lastname;
	
	public Individual(String firstname, String lastname) {
		super();
		this.firstname = firstname;
		this.lastname = lastname;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		n(sb);
		lb(sb);
			n(sb); t(sb); v(sb, "\"firstname\": "); qv(sb,this.firstname); c(sb);
			n(sb); t(sb); v(sb, "\"lastname\": "); qv(sb,this.lastname); 
			n(sb);
		rb(sb);
		return sb.toString();
	}
	
}
