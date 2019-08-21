package com.paxpos;

//import com.nbbse.printapi.*;

import com.pax.dal.IDAL;
import com.pax.dal.IPrinter;
import com.pax.dal.exceptions.PrinterDevException;
import com.pax.gl.page.IPage;
import com.pax.gl.page.PaxGLPage;

import com.pax.dal.IDAL;
import com.pax.neptunelite.api.NeptuneLiteUser;

import org.apache.cordova.*;

import java.io.*;
import java.util.*;
import java.text.*;
import java.util.Hashtable;
import java.util.Set;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Handler;
import android.util.Log;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Bitmap.Config;
import android.graphics.Bitmap.CompressFormat;
import android.util.Xml.Encoding;
import jdk.nashorn.internal.parser.*;
import android.util.Base64;
import java.util.ArrayList;
import java.util.List;
import android.content.*;
import android.R;

public class PaxPos extends CordovaPlugin {
	public Context context;
	CordovaInterface mycordova;
	CordovaWebView mywebView;

	private IPrinter iPrinter;
	public PaxGLPage paxGLPage;
	public static IDAL dal;
	public static NeptuneLiteUser neptuneLiteUser;

	public void initialize(CordovaInterface cordova, CordovaWebView webView) {
		super.initialize(cordova, webView);
		context = this.cordova.getActivity().getApplicationContext();
		mycordova = cordova;
		mywebView = webView;

		neptuneLiteUser = NeptuneLiteUser.getInstance();

		dal = neptuneLiteUser.getDal(this.getApplicationContext());

		paxGLPage = PaxGLPage.getInstance(context);
		// iPrinter = dal.getPrinter();

		// iPrinter.init();
		// int res = iPrinter.start();
	}

	@Override
	public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
		if (action.equals("print_test")) {

			String parms = args.getString(0);
			JSONObject json = new JSONObject(parms);

			int appid = json.getInt("app_id");
			String txt = json.getString("txt");

			// InputStream is =
			// context.getResources().openRawResource(getAppResource("logo", "raw"));

			callbackContext.success(appid + "," + txt);
			callbackContext.success(parms);

			// iPrinter.printBitmap(generateGLPage("mahmod"));

			return true;
		} else if (action.equals("print_json")) {
			callbackContext.success("1");
			return true;
		} else {
			return false;
		}
	}

	private int getAppResource(String name, String type) {
		return mycordova.getActivity().getResources().getIdentifier(name, type,
				mycordova.getActivity().getPackageName());
	}

	private Bitmap generateGLPage(String data) {

		IPage page = paxGLPage.createPage();

		page.adjustLineSpace(-9);

		page.addLine().addUnit(data, 36, IPage.EAlign.CENTER);

		page.addLine().addUnit("\n\n\n", 36);
		return paxGLPage.pageToBitmap(page, 384);
	}

}