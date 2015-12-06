package com.uiautomatortest;

import java.io.IOException;

import android.R.integer;
import android.os.Bundle;

import com.android.uiautomator.core.UiDevice;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;

public class Test extends UiAutomatorTestCase {
	String Tag = "taobaotest";
	
	String resultKey = "resultKey";
	
	public void testParam()
	{
		Bundle params = this.getParams();
		String args  = params.getString(common.ARGS);
		
		common.Log("testParam : " + args);
		
		Bundle br = new Bundle();
		br.putString(resultKey, "good");
		this.getAutomationSupport().sendStatus(1, br);
	}
	
	public void startTaobao() {
	    try {
	        Runtime.getRuntime().exec(
	                "am start -n " + "com.taobao.taobao" + "/com.taobao.tao.welcome.Welcome");
	        sleep(1000);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    
	    boolean ret = false;
	    for (int i = 0; i < 20; i++) {
	        sleep(1000);
	        if (getUiDevice().getCurrentPackageName().contains("com.taobao.taobao")){
	           ret = true;
	           break;
	        }
	    }
	    
	    Bundle br = new Bundle();
	    
	    if(ret == true)
	    	br.putString(resultKey, "good");
	    else
	    	br.putString(resultKey, "bad");
	    
	    this.getAutomationSupport().sendStatus(ret == true?0:-1, br);
	}
	
	public void stopTaobao() {
	    try {
	        Runtime.getRuntime().exec(
	                "am force-stop com.taobao.taobao");
	        sleep(1000);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    
	    Bundle br = new Bundle();
	    br.putString(resultKey, "good");
	    
	    this.getAutomationSupport().sendStatus(1, br);
	}
	
	public void entryMainActivity()
	{
		closeUpdateActivity();
		
		String strResult = "";
		
		do {
			mainActivity ma = new mainActivity();
			if(!ma.isThisActivityRight())
			{
				strResult = "entry mainActivity fail";
				break;
			}

			if(!ma.entrySearchActivity())
			{
				strResult = "try to entry searchActivity fail";
				break;
			}
			
			strResult = "good";
		} while (false);

		Bundle br = new Bundle();
		br.putString(resultKey, strResult);
		this.getAutomationSupport().sendStatus(0, br);
	}
	
	public void entrySearchConditionActivity()
	{
		closeUpdateActivity();
		
		String strResult = "";
		
		Bundle params = this.getParams();
		String Condition = params.getString(common.ARGS);
		
		/*
		 *  处理空格
		 *  这里直接将空格取代符为‘ ’（空格）
		 */
		String[] for_kg = Condition.split("@");
		Condition = "";
		for(int i = 0; i < for_kg.length; i++)
		{
			Condition += for_kg[i];
			
			if(i + 1 < for_kg.length)
				Condition += " ";
		}
	
		/*
		 *  分割参数串
		 */
		String splite_sc[] = Condition.split("#");
		Condition = "";
		for(int i = 0; i < splite_sc.length; i++)
		{
			Condition += splite_sc[i];
			
			if(i + 1 < splite_sc.length)
				Condition += " ";
		}
		
		common.Log("Condition = " + Condition);
		
		searchConditionActivity sa = new searchConditionActivity();
		do {
			if(Condition == null)
			{
				strResult = "Condition is null";
				break;
			}
			
			if (!sa.isThisActivityRight())
			{
				strResult = "entry searchConditionActivity fail";
				break;
			}
			
			if(!sa.search(Condition))
			{
				strResult = "entry searchConditionActivity fail";
				break;
			}
			
			strResult = "good";
		}while(false);
		
		Bundle br = new Bundle();
		br.putString(resultKey, strResult);
		this.getAutomationSupport().sendStatus(0, br);
	}
	
	public void entrySearchResultActivity()
	{
		closeUpdateActivity();
		
		String strResult = "";

		Bundle params = this.getParams();
		String args  = params.getString(common.ARGS);
		
		/*
		 *  处理空格
		 *  这里直接将空格取代符为‘ ’（空格）
		 */
		String[] for_kg = args.split("@");
		args = "";
		for(int i = 0; i < for_kg.length; i++)
		{
			args += for_kg[i];
			
			if(i + 1 < for_kg.length)
				args += " ";
		}
		
		/*
		 *  分割参数串 : address、price、postfee、match和go_on
		 */
		String[] source_splite = args.split("#");
		int source_count = source_splite.length;
		
		String mark_address = "address=";
		String mark_price = "price=";
		String mark_postfee = "postfee=";
		String mark_go_on = "go_on=";
		
		String address = "";
		String go_on = "true";
		float price = -1, postfee = -1;
		
		for(String tmp:source_splite)
		{			
			if(tmp.startsWith(mark_address))
			{
				source_count -= 1;
				address = tmp.substring(mark_address.length() );
			}
			else if(tmp.startsWith(mark_price))
			{
				source_count -= 1;
				String str_price = tmp.substring(mark_price.length());
				price = Float.parseFloat(str_price);
			}
			else if(tmp.startsWith(mark_postfee))
			{
				source_count -= 1;
				String str_postfee = tmp.substring(mark_postfee.length());
				postfee = Float.parseFloat(str_postfee);
			}
			else if(tmp.startsWith(mark_go_on))
			{
				source_count -= 1;
				go_on = tmp.substring(mark_go_on.length());
			}
		}
		
		String[] matchs = new String[source_count];
		int index = 0;
		for(String tmp:source_splite)
		{
			if(
					!tmp.startsWith(mark_address) &&
					!tmp.startsWith(mark_price) &&
					!tmp.startsWith(mark_postfee) &&
					!tmp.startsWith(mark_go_on)
					)
					matchs[index++] = tmp;
		}
		
		for(String tmp:matchs)
		{
			common.Log( "match : " + tmp);
		}
		common.Log( "address : " + address);
		common.Log( "price : " + price);
		common.Log( "postfee : " + postfee);
		common.Log("go_on : " + go_on);
		
		searchResultsActivity sra = new searchResultsActivity();
		do {
			if(matchs.length < 1)
			{
				strResult = "split matchs  wrong !!";
				common.Log(strResult);
				break;
			}
			
			if (!sra.isThisActivityRight())
			{
				strResult = "entrySearchResultActivity fail";
				break;
			}

			if (!sra.findAndEntryCommodity(matchs, address, price, postfee, go_on.equals("TRUE")||go_on.equals("true"), 30))
			{
				strResult = "findAndEntryCommodity fail";
				break;
			}
			
			strResult = "good";
		} while (false);

		Bundle br = new Bundle();
		br.putString(resultKey, strResult);
		this.getAutomationSupport().sendStatus(0, br);
	}
	
	public void entryCommodityActivity()
	{
		closeUpdateActivity();
		
		String strResult = "";
		
		Bundle params = this.getParams();
		String args  = params.getString(common.ARGS);
		
		long time;
		try {
			time = Long.parseLong(args) * 1000;
		} catch (NumberFormatException e) {
			// TODO: handle exception
			common.Log("parse showCommodityDetialWithTimeout param fail, set it to 10 seconds !!");
			time = 10;
		}
		
		common.Log("entryCommodityActivity param is " + time);
		
		commodityActivity ca = new commodityActivity();
		do {
			if (!ca.isThisActivityRight())
			{
				strResult = "entryCommodityActivity fail ";
				break;
			}
			
			ca.showCommodityDetialWithTimeout(time);
			
			strResult = "good";
		}while(false);
		
		Bundle br = new Bundle();
		br.putString(resultKey, strResult);
		this.getAutomationSupport().sendStatus(0, br);
	}
	
	public void entryEvaluationActivity()
	{
		closeUpdateActivity();
		
		String strResult = "";

		Bundle params = this.getParams();
		String args = params.getString(common.ARGS);

		long time;
		try {
			time = Long.parseLong(args) * 1000;
		} catch (NumberFormatException e) {
			// TODO: handle exception
			common.Log("parse showEvaluationWithTimeout param fail, set it to 10 seconds !!");
			time = 10;
		}

		common.Log("entryEvaluationActivity param is " + time);
		
		commodityActivity ca = new commodityActivity();
		evaluationActivity ea = new evaluationActivity();
		do {
			if (!ca.isThisActivityRight())
			{
				strResult = "no longer at CommodityActivity ";
				break;
			}
			
			if(!ca.entryEvaluationActivity())
			{
				strResult = "entryEvaluationActivity fail ";
				//break;
			}
			
			ea.showEvaluationWithTimeout(time);

			if(!ea.exitActivity())
			{
				strResult = "entryEvaluationActivity fail ";
				break;
			}
			
			strResult = "good";
		} while (false);
		
		Bundle br = new Bundle();
		br.putString(resultKey, strResult);
		this.getAutomationSupport().sendStatus(0, br);
	}
	
	public void entryCommodityActivityRandomly()
	{
		closeUpdateActivity();
		
		String strResult = "";

		commodityActivity ca = new commodityActivity();
		do {
			if (!ca.isThisActivityRight()) {
				strResult = "no longer at CommodityActivity";
				break;
			}

			ca.entryShopActivity();
			
			shopActivity sha = new shopActivity();
			if (!sha.isThisActivityRight()) 
			{
				strResult = "no long at shopActivity !";
				break;
			} 
			
			if (!sha.entryCommodityActivityRandomly()) 
			{
				strResult = "entryCommodityActivityRandomly fail !";
				break;
			} 
			
			strResult = "good";
		} while (false);
		
		Bundle br = new Bundle();
		br.putString(resultKey, strResult);
		this.getAutomationSupport().sendStatus(0, br);
	}
	
	public void exitCommodityActivity()
	{
		closeUpdateActivity();
		
		String strResult = "";
		
		commodityActivity ca = new commodityActivity();
		do {
			if (!ca.isThisActivityRight())
			{
				strResult = "entryCommodityActivity fail ";
				break;
			}
			
			if(!ca.exitActivity())
			{
				strResult = "exitCommodityActivity fail ";
				break;
			}
			
			strResult = "good";
		}while(false);
		
		Bundle br = new Bundle();
		br.putString(resultKey, strResult);
		this.getAutomationSupport().sendStatus(0, br);
	}
	
	public void exitShopActivity()
	{
		closeUpdateActivity();
		
		String strResult = "";

		do {
			shopActivity sha = new shopActivity();
			if (!sha.isThisActivityRight()) 
			{
				strResult = "no long at shopActivity !";
				break;
			} 
			
			if(!sha.exitActivity())
			{
				strResult = "exit shopActivity fail !";
				break;
			}
			
			strResult = "good";
		} while (false);
		
		Bundle br = new Bundle();
		br.putString(resultKey, strResult);
		this.getAutomationSupport().sendStatus(0, br);
	}
	
	public void exitSearchResultActivity()
	{
		closeUpdateActivity();
		
		String strResult = "";
		
		searchResultsActivity sra = new searchResultsActivity();
		do {
			if (!sra.isThisActivityRight())
			{
				strResult = "no longer at SearchResultActivity ";
				break;
			}

			if (!sra.exitActivity())
			{
				strResult = "exit searchResultActivity fail";
				break;
			}
			
			strResult = "good";
		} while (false);

		Bundle br = new Bundle();
		br.putString(resultKey, strResult);
		this.getAutomationSupport().sendStatus(0, br);
	}
	
	public void exitSearchConditionActivity()
	{
		closeUpdateActivity();
		
		String strResult = "";
				
		searchConditionActivity sa = new searchConditionActivity();
		do {
			if (!sa.isThisActivityRight())
			{
				strResult = "no longer at  searchConditionActivity ";
				break;
			}
			
			if(!sa.exitActivity())
			{
				strResult = "exit searchConditionActivity fail";
				break;
			}
			
			strResult = "good";
		}while(false);
		
		Bundle br = new Bundle();
		br.putString(resultKey, strResult);
		this.getAutomationSupport().sendStatus(0, br);
	}
	
	public void exitMainActivity()
	{
		closeUpdateActivity();
		
		String strResult = "";
		
		do {
			mainActivity ma = new mainActivity();
			if(!ma.isThisActivityRight())
			{
				strResult = "no longer at mainActivity ";
				break;
			}

			if(!ma.exitActivity())
			{
				strResult = "exit searchActivity fail";
				break;
			}
			
			strResult = "good";
		} while (false);

		Bundle br = new Bundle();
		br.putString(resultKey, strResult);
		this.getAutomationSupport().sendStatus(0, br);
	}
	
	public void closeUpdateActivity()
	{
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				updateActivity ua = new updateActivity();

				while (getUiDevice().getCurrentPackageName().contains(
						"com.taobao.taobao")) {
					
					//common.Log("closeUpdateActivity running!");
					
					if (ua.isThisActivityRight()) {
						ua.exitActivity();
					}
				}
				
				common.Log("closeUpdateActivity out!");
			}
		}).start();		
	}
	
	public void test()
	{
		closeUpdateActivity();
		
		while (getUiDevice().getCurrentPackageName().contains(
				"com.taobao.taobao"))
		{
			common.sleep(1 * 1000);
		}
	}
}