package com.sina.sinawidgetdemo.returnmodel;

import java.util.ArrayList;
import java.util.List;

import com.sina.engine.base.db4o.Db4oInterface;
/**
 * 筛选分类数据模型
 * @author kangshaozhe
 *
 */
public class ClassifyModel extends BaseModel implements Db4oInterface<ClassifyModel> {
private static final long serialVersionUID = 1L;
	
	private String abstitle;
	private String absId;
	private List<ClassifyItemModel> item = new ArrayList<ClassifyItemModel>();
	
	
	public String getAbstitle() {
		return abstitle;
	}


	public void setAbstitle(String abstitle) {
		this.abstitle = abstitle;
	}
	
	public String getAbsId() {
		return absId;
	}


	public void setAbsId(String absId) {
		this.absId = absId;
	}


	public List<ClassifyItemModel> getItem() {
		return item;
	}


	public void setItem(List<ClassifyItemModel> item) {
		if (this.item == null) {
			this.item = new ArrayList<ClassifyItemModel>();
		}
		this.item.clear();
		if (item != null) {
			for (ClassifyItemModel model : item) {
				if (model == null) {
					continue;
				}
				ClassifyItemModel copyModel = new ClassifyItemModel();
				copyModel.objectUpdate(model);
				this.item.add(copyModel);
			}
		}
	}


	@Override
	public void objectUpdate(ClassifyModel object) {
		if(object==null){
			return;
		}
	    setAbstitle(object.getAbstitle());
	    setAbsId(object.getAbsId());
		setItem(object.getItem());
	}

}
