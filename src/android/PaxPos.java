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

import android.os.SystemClock;
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

	private IPrinter printer;
	public PaxGLPage paxGLPage;
	public String callback;
	public static IDAL dal;
	public static NeptuneLiteUser neptuneLiteUser;

	public void initialize(CordovaInterface cordova, CordovaWebView webView) {
		super.initialize(cordova, webView);
		context = this.cordova.getActivity().getApplicationContext();
		mycordova = cordova;
		mywebView = webView;

		callback = "71";

	}

	@Override
	public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
		if (action.equals("print_test")) {

			// String parms = args.getString(0);
			// JSONObject json = new JSONObject(parms);

			// int appid = json.getInt("app_id");
			// String txt = json.getString("txt");

			// InputStream is =
			// context.getResources().openRawResource(getAppResource("logo", "raw"));

			// callbackContext.success(appid + "," + txt);
			// callbackContext.success(parms);

			/// printing

			try {

				neptuneLiteUser = NeptuneLiteUser.getInstance();

				dal = neptuneLiteUser.getDal(context);

				// paxGLPage = PaxGLPage.getInstance(context);
				printer = dal.getPrinter();

				print_img();
				// callback = "getStatus:" + printer.getStatus();

				// printer.init();

				// printer.setGray(255);
				// printer.printBitmap(generateGLPage("mahmod"));

				// int res = printer.start();
				// callbackContext.success(String.valueOf(res));
				// return true;
			} catch (Exception e) {
				callbackContext.success(" exception :" + e.getMessage());
				return true;
			}
			callbackContext.success(callback);
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

	private int start(IPrinter printer) {
		try {
			while (true) {
				int ret = printer.start();
				// printer is busy, please wait
				if (ret == 1) {
					// SystemClock.sleep(100);
					continue;
				} else if (ret == 2) {
					// onShowMessage("Printer is Out of Paper");
					return -1;
				} else if (ret == 8) {
					// onShowMessage("Printer is too hot");
					return -1;
				} else if (ret == 9) {
					// onShowMessage("Voltage is too low!");
					return -1;
				} else if (ret != 0) {
					return -1;
				}

				return 0;
			}
		} catch (PrinterDevException e) {
			e.printStackTrace();
			return 0;
		}
	}

	private Bitmap generate() {

		IPage page = paxGLPage.createPage();

		page.adjustLineSpace(-9);

		// To set the font file
		// page.setTypeFace("/data/resource/font/DroidSansFallback.ttf");

		page.addLine().addUnit("11111", 36, IPage.EAlign.CENTER);
		/*
		 * page.addLine().addUnit("Print test string", 36, IPage.EAlign.CENTER);
		 * page.addLine().addUnit("Print test string", 36, IPage.EAlign.CENTER);
		 */
		page.addLine().addUnit("\n\n\n", 36);
		// page.addLine().addUnit(page.createUnit().setBitmap(getResources().getAssets().));
		return paxGLPage.pageToBitmap(page, 384);
	}

	public void init() {
		try {
			printer.init();
			printer.setGray(255);
		} catch (PrinterDevException e) {
			callback = "init error";
		}
	}

	protected void printBitmap(Bitmap bitmap) {
		init();
		// try {
		// printer.printBitmap(bitmap);
		// } catch (PrinterDevException e) {
		// e.printStackTrace();
		// Log.d("printBitmap", e.toString());
		// }
		// start(printer);
	}

	/**
	 * Print image
	 */
	private void print_img() {

		new Thread(new Runnable() {
			@Override
			public void run() {
				printBitmap(generate());
			}
		}).start();
	}

}