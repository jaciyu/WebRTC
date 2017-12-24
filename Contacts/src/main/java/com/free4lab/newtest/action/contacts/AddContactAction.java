package com.free4lab.newtest.action.contacts;

//import javax.persistence.Column;

import org.apache.log4j.Logger;

import com.free4lab.account.manager.ContactManager;
import com.free4lab.newtest.action.base.BaseAction;

public class AddContactAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 增加联系人信息
	 */
	private static final Logger logger = Logger.getLogger(ContactListAction.class);
	
	private String lname;
	private String fname;	
	private String sex;
	private String nc;
	private String birth;
	private String zw;
	private String bm;
	private String telp;
	private String mobp;
	private String emails;
	private String address;
	private String postcode;
	private Integer groupId;
	private String bz;

	
	public String execute() throws Exception{
		if(ContactManager.addContact(lname, fname, sex, nc, birth, zw, 
				bm, telp, mobp, emails, address, postcode, groupId, bz)){
			logger.info("testadd");
			System.out.println("abcdefg");
		}
		return SUCCESS;
	}


	public String getLname() {
		return lname;
	}


	public void setLname(String lname) {
		this.lname = lname;
	}


	public String getFname() {
		return fname;
	}


	public void setFname(String fname) {
		this.fname = fname;
	}


	public String getSex() {
		return sex;
	}


	public void setSex(String sex) {
		this.sex = sex;
	}


	public String getNc() {
		return nc;
	}


	public void setNc(String nc) {
		this.nc = nc;
	}


	public String getBirth() {
		return birth;
	}


	public void setBirth(String birth) {
		this.birth = birth;
	}


	public String getZw() {
		return zw;
	}


	public void setZw(String zw) {
		this.zw = zw;
	}


	public String getBm() {
		return bm;
	}


	public void setBm(String bm) {
		this.bm = bm;
	}


	public String getTelp() {
		return telp;
	}


	public void setTelp(String telp) {
		this.telp = telp;
	}


	public String getMobp() {
		return mobp;
	}


	public void setMobp(String mobp) {
		this.mobp = mobp;
	}


	public String getEmails() {
		return emails;
	}


	public void setEmails(String emails) {
		this.emails = emails;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public String getPostcode() {
		return postcode;
	}


	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public String getBz() {
		return bz;
	}


	public void setBz(String bz) {
		this.bz = bz;
	}


	public Integer getGroupId() {
		return groupId;
	}


	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}
	
}