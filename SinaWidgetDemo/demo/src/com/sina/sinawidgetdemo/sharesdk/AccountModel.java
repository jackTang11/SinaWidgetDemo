package com.sina.sinawidgetdemo.sharesdk;

import java.util.Date;

import com.sina.engine.base.db4o.Db4oInterface;
import com.sina.sinagame.usercredit.AccountItem;

public class AccountModel extends AccountItem implements
		Db4oInterface<AccountModel> {

	private static final long serialVersionUID = 1L;

	public AccountModel(String userName, String nickName, String avatar,
			String gender, String protocol, String unionName, String resource, String password,
			int priority, Date lastSync, Date expiresin) {
		super(userName, nickName, avatar, gender, protocol, unionName, resource, password,
				priority, lastSync, expiresin);
	}

	@Override
	public void objectUpdate(AccountModel o) {
		if (o == null) {
			return;
		}
		setUserName(o.getUserName());
		setNickName(o.getNickName());
		setAvatar(o.getAvatar());
		setGender(o.getGender());
		setProtocol(o.getProtocol());
		setUnionName(o.getUnionName());
		setResource(o.getResource());
		setPassword(o.getPassword());
		setPriority(o.getPriority());
		setLastSync(o.getLastSync());
		setExpiresin(o.getExpiresin());
	}

}
