package org.xiangbalao.plug;

import android.graphics.drawable.Drawable;

/**
 * 插件的列表
 * @author longtaoger
 *
 */
public class PluginBean {
	/**
	 * 进程名、包名
	 */
	private String pakageName;
	/**
	 * 标签
	 */
	private String label;
	/**
	 * 图标
	 */
	
	private Drawable icon;
	
	public String getPakageName() {
		return pakageName;
	}
	public Drawable getIcon() {
		return icon;
	}
	public void setIcon(Drawable icon) {
		this.icon = icon;
	}
	public void setPakageName(String pakageName) {
		this.pakageName = pakageName;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	
}
