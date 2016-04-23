package com.sina.sinawidgetdemo.sharesdk;

import com.sina.engine.base.db4o.Db4oInterface;
import com.sina.sinawidgetdemo.returnmodel.BaseModel;

/**
 * 配置文件中的签到任务规则
 * 
 * @author kangshaozhe
 * 
 */
public class SignRule extends BaseModel implements Db4oInterface<SignRule> {
	private static final long serialVersionUID = 1L;

	private String signtime;
	private String signScore;
	private int weight;

	public void setSigntime(String signtime) {
		this.signtime = signtime;
	}

	public String getSigntime() {
		return signtime;
	}

	public void setSignScore(String signScore) {
		this.signScore = signScore;
	}

	public String getSignScore() {
		return signScore;
	}

	@Override
	public void objectUpdate(SignRule o) {
		if (o == null) {
			return;
		}
		setSignScore(o.getSignScore());
		setSigntime(o.getSigntime());
		setWeight(o.getWeight());
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

}
