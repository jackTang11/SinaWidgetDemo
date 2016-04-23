package com.sina.sinawidgetdemo.returnmodel;

import com.sina.engine.base.db4o.Db4oInterface;
/**
 * 筛选分类子选项
 * @author kangshaozhe
 *
 */
public class ClassifyItemModel extends BaseModel implements Db4oInterface<ClassifyItemModel> {
private static final long serialVersionUID = 1L;
	
	private String absId;
	private String name;
	
	public String getAbsId() {
		return absId;
	}


	public void setAbsId(String absId) {
		this.absId = absId;
	}
	
	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}
	
	
	@Override
	public void objectUpdate(ClassifyItemModel object) {
		if(object==null){
			return;
		}
	    setAbsId(object.getAbsId());
		setName(object.getName());
	}

}
