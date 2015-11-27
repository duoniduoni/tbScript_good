package com.uiautomatortest;

import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiSelector;

public class updateActivity implements IActivity {
	UiObject cancelBtn = new UiObject(new UiSelector().resourceId("com.taobao.taobao:id/TBDialog_buttons_Cancel"));
	@Override
	public boolean isThisActivityRight() {
		return cancelBtn.waitForExists(3 * 1000);
	}

	@Override
	public boolean exitActivity(){
		if(cancelBtn.exists())
			try {
				cancelBtn.click();
			} catch (UiObjectNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		return true;
	}

}
