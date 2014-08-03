package org.xiangbalao;


import java.util.ArrayList;
import java.util.List;

import org.xiangbalao.plug.PluginBean;
import org.xiangbalao.plugin.test.R;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;


public class MainActivity extends Activity implements OnClickListener {
	private Button button;
	private List<PluginBean> plugins;
	private LinearLayout llMainLayout;
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       
        
    findView();
    }


	/**
	 * ��ʼ��View
	 */
	private void findView() {
		// TODO
		button=(Button) findViewById(R.id.button1);
		button.setOnClickListener(this);
		llMainLayout=(LinearLayout) findViewById(R.id.main_llMainLayout);
		
	}


	@Override
	public void onClick(View v) {
		
		switch (v.getId()) {
		case R.id.button1:
			
			if (plugins==null) {
				plugins=new ArrayList<PluginBean>();
			}
			plugins.clear();
			attachPlugin(findPlugins());
			
			break;

		default:
			break;
		}
		
		
		
	}



	/**
	 * ���ز���б�
	 * 
	 * @param plugins
	 */
	private void attachPlugin(final List<PluginBean> plugins) {
	
		this.plugins = plugins;
		if (llMainLayout.getChildCount()>0) {
			llMainLayout.removeAllViews();
		}
		for (final PluginBean plugin : plugins) {
			// ����
			TextView mTextView = new TextView(this);
			// ������ɫΪ��
			mTextView.setTextColor(Color.RED);
			// ���ò����ǩ
			mTextView.setText(plugin.getLabel());
			// ���ò��ͼ��

			Drawable img_icon;

			img_icon = plugin.getIcon();
			// ����setCompoundDrawablesʱ���������Drawable.setBounds()����,����ͼƬ����ʾ
			img_icon.setBounds(0, 0, img_icon.getMinimumWidth(),
					img_icon.getMinimumHeight());
			mTextView.setCompoundDrawables(img_icon, null, null, null);
			//
			llMainLayout.addView(mTextView);
			// ����¼�

			mTextView.setOnClickListener(new OnClickListener() {
				// ������ü���
				@Override
				public void onClick(View v) {
					// ����Viewѡ���

					Intent it = new Intent();
					// ����ActionΪ�������
					
					
				
					
					if (!plugin.getPakageName().equals("org.xiangbalao.plugin")) {
						it.setAction(plugin.getPakageName());
					}else {
						it.setAction("android.intent.action.xiangbalao");
						it.addCategory("android.intent.category.LAUNCHER"); 
					}

					startActivity(it);

				}
			});

		}
	}

	/**
	 * ���Ҳ��
	 * 
	 * @return
	 */
	private List<PluginBean> findPlugins() {
		/**
		 * �������
		 */
		// ��������������ȡ���
		PackageManager pm = getPackageManager();

		// ����Ϣ����
		List<PackageInfo> pkgs = pm
				.getInstalledPackages(PackageManager.GET_UNINSTALLED_PACKAGES);
		for (PackageInfo pkg : pkgs) {
			// Ӧ�ó�����Ϣ
			ApplicationInfo mApplicationInfo = pkg.applicationInfo;
			// ����
			String packageName = pkg.packageName;
			String sharedUserId = pkg.sharedUserId;
			Drawable iconDrawable = mApplicationInfo.loadIcon(pm);

			// sharedUserId�ǿ���ʱԼ���õģ������ж��Ƿ�Ϊ�Լ���
			if (!"org.xiangbalao".equals(sharedUserId)
					|| "org.xiangbalao.plugin.test".equals(packageName))
				continue;

			// ������
			String prcessName = pkg.applicationInfo.processName;
			
			
			// label��Ҳ����appName��
			String label = pm.getApplicationLabel(pkg.applicationInfo)
					.toString();
			
			// �����Ϣ�ŵ���������
			PluginBean plug = new PluginBean();
			plug.setLabel(label);
			plug.setPakageName(packageName);
			
			plug.setIcon(iconDrawable);
			plugins.add(plug);
		}

		// ���ز����Ϣ
		return plugins;

	}
  
}
