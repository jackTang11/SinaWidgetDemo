package com.sina.sinawidgetdemo.returnmodel;

import com.sina.engine.base.db4o.Db4oInterface;
/**
 * 评测频道列表数据模型
 * @author kangshaozhe
 *
 */
public class EvidenceChannelModel extends BaseModel implements Db4oInterface<EvidenceChannelModel> {
private static final long serialVersionUID = 1L;
	
	private String absId;
	private String abstitle;
	
	public String getAbsId() {
		return absId;
	}


	public void setAbsId(String absId) {
		this.absId = absId;
	}
	
	public String getAbstitle() {
		return abstitle;
	}


	public void setAbstitle(String abstitle) {
		this.abstitle = abstitle;
	}
	
	
	@Override
	public void objectUpdate(EvidenceChannelModel object) {
		if(object==null){
			return;
		}
	    setAbsId(object.getAbsId());
	    setAbstitle(object.getAbstitle());
	}

}
