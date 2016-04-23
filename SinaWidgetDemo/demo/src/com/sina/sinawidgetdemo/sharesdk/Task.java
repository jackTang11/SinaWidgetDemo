package com.sina.sinawidgetdemo.sharesdk;

import java.util.ArrayList;
import java.util.List;

import com.sina.engine.base.db4o.Db4oInterface;
import com.sina.sinawidgetdemo.returnmodel.BaseModel;

public class Task extends BaseModel implements Db4oInterface<Task> {

	private static final long serialVersionUID = 1L;

	public Task() {
		super();
	}
	
	private String account;
	// account values
	private String absId;
	private int excuseTimes;
	private String firstExcusetime;
	private int signCycle;
	// config values
	private String abstitle;
	private int taskCycle;
	private int score;
	private int total;
	private List<SignRule> signRule;

	@Override
	public void objectUpdate(Task o) {
		if (o == null) {
			return;
		}

		setAccount(o.getAccount());

		setAbsId(o.getAbsId());
		setExcuseTimes(o.getExcuseTimes());
		setFirstExcusetime(o.getFirstExcusetime());
		setSignCycle(o.getSignCycle());

		setAbstitle(o.getAbstitle());
		setScore(o.getScore());
		setTaskCycle(o.getTaskCycle());
		setTotal(o.getTotal());
		setSignRule(o.getSignRule());

		setTaskComplete(o.isTaskComplete());
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getAbsId() {
		return absId;
	}

	public void setAbsId(String absId) {
		this.absId = absId;
	}

	public int getExcuseTimes() {
		return excuseTimes;
	}

	public void setExcuseTimes(int excuseTimes) {
		this.excuseTimes = excuseTimes;
	}

	public String getFirstExcusetime() {
		return firstExcusetime;
	}

	public void setFirstExcusetime(String firstExcusetime) {
		this.firstExcusetime = firstExcusetime;
	}

	public int getSignCycle() {
		return signCycle;
	}

	public void setSignCycle(int signCycle) {
		this.signCycle = signCycle;
	}

	public String getAbstitle() {
		return abstitle;
	}

	public void setAbstitle(String abstitle) {
		this.abstitle = abstitle;
	}

	public int getTaskCycle() {
		return taskCycle;
	}

	public void setTaskCycle(int taskCycle) {
		this.taskCycle = taskCycle;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public List<SignRule> getSignRule() {
		return signRule;
	}

	public void setSignRule(List<SignRule> signRule) {
		if (this.signRule == null) {
			this.signRule = new ArrayList<SignRule>();
		}
		this.signRule.clear();
		if (signRule != null) {
			for (SignRule model : signRule) {
				if (model == null) {
					continue;
				}
				SignRule item = new SignRule();
				item.objectUpdate(model);
				this.signRule.add(item);
			}
		}
	}

	private boolean taskComplete = false;

	public boolean isTaskComplete() {
		return taskComplete;
	}

	public void setTaskComplete(boolean taskComplete) {
		this.taskComplete = taskComplete;
	}

}