package org.xiangbalao;

import java.util.ArrayList;
import java.util.List;

import org.xiangbalao.plug.PluginBean;
import org.xiangbalao.plugin.R;


import android.app.Activity;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener {

	private LinearLayout llMainLayout;
	private Button button;
	private List<org.xiangbalao.plug.PluginBean> plugins;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		findView();
	}

	private void findView() {

		button = (Button) findViewById(R.id.button1);
		button.setOnClickListener(this);
		llMainLayout = (LinearLayout) findViewById(R.id.main_llMainLayout);
	}

	@Override
	public void onClick(View v) {
		// TODO 处理点击事件
		switch (v.getId()) {
		case R.id.button1:
			if (plugins==null) {
				 plugins = new ArrayList<PluginBean>();
			}
			
			 plugins.clear();
			attachPlugin(findPlugins());	
			break;

		default:
			break;
		}
	}

	/**
	 * 加载插件列表
	 * 
	 * @param plugins
	 */
	private void attachPlugin(final List<PluginBean> plugins) {
	
		this.plugins = plugins;
		if (llMainLayout.getChildCount()>0) {
			llMainLayout.removeAllViews();
		}
		for (final PluginBean plugin : plugins) {
			// 遍历
			TextView mTextView = new TextView(this);
			// 字体颜色为红
			mTextView.setTextColor(Color.RED);
			// 设置插件标签
			mTextView.setText(plugin.getLabel());
			// 设置插件图标

			Drawable img_icon;

			img_icon = plugin.getIcon();
			// 调用setCompoundDrawables时，必须调用Drawable.setBounds()方法,否则图片不显示
			img_icon.setBounds(0, 0, img_icon.getMinimumWidth(),
					img_icon.getMinimumHeight());
			mTextView.setCompoundDrawables(img_icon, null, null, null);
			//
			llMainLayout.addView(mTextView);
			// 添加事件

			mTextView.setOnClickListener(new OnClickListener() {
				// 插件设置监听
				@Override
				public void onClick(View v) {
					// 附加View选择框

					Intent it = new Intent();
					// 设置Action为插件包名
					
					
					it.setAction(plugin.getPakageName());

					startActivity(it);

				}
			});

		}
	}

	/**
	 * 查找插件
	 * 
	 * @return
	 */
	private List<PluginBean> findPlugins() {
		/**
		 * 插件集合
		 */
	
		

		// 遍历包名，来获取插件
		PackageManager pm = getPackageManager();

		// 包信息集合
		List<PackageInfo> pkgs = pm
				.getInstalledPackages(PackageManager.GET_UNINSTALLED_PACKAGES);
		for (PackageInfo pkg : pkgs) {
			// 应用程序信息
			ApplicationInfo mApplicationInfo = pkg.applicationInfo;
			// 包名
			String packageName = pkg.packageName;
			String sharedUserId = pkg.sharedUserId;
			Drawable iconDrawable = mApplicationInfo.loadIcon(pm);

			// sharedUserId是开发时约定好的，这样判断是否为自己人
			if (!"org.xiangbalao".equals(sharedUserId)
					|| "org.xiangbalao.plugin".equals(packageName))
				continue;

			// 进程名
			String prcessName = pkg.applicationInfo.processName;
			
			
			// label，也就是appName了
			String label = pm.getApplicationLabel(pkg.applicationInfo)
					.toString();
			
			// 插件信息放到到集合中
			PluginBean plug = new PluginBean();
			plug.setLabel(label);
			plug.setPakageName(packageName);
			
			plug.setIcon(iconDrawable);
			plugins.add(plug);
		}

		// 返回插件信息
		return plugins;

	}

}
