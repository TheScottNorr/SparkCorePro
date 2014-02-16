package ApiSparkCore;


//import io.spark.core.android.app.DeviceState;
//import io.spark.core.android.cloud.api.Device;
//import io.spark.core.android.cloud.api.SimpleResponse;
//import io.spark.core.android.cloud.api.TinkerResponse;
//import io.spark.core.android.cloud.requestservice.SimpleSparkApiService;
//import io.spark.core.android.smartconfig.SmartConfigState;
//import io.spark.core.android.ui.tinker.DigitalValue;

import java.lang.reflect.Type;
import java.util.List;

import org.apache.http.HttpStatus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import Util.TLog;
//import Util.Toaster;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.v4.content.LocalBroadcastManager;

//import com.google.gson.reflect.TypeToken;


/**
* A simple interface for making requests and handling responses from the Spark
* API.
* 
* If you want to work with the Spark API from Android, this is the place to
* start. See examples below like {@link #nameCore(String, String)},
* {@link #digitalWrite(String, String, DigitalValue, DigitalValue)}, etc, for
* templates to work from.
* 
*/
public class ApiFacade {

	private static final TLog log = new TLog(ApiFacade.class);


	public static final int REQUEST_FAILURE_CODE = -1;

	// broadcast receiver actions
	public static final String BROADCAST_SIGN_UP_FINISHED = "BROADCAST_SIGN_UP_FINISHED";
	public static final String BROADCAST_DEVICES_UPDATED = "BROADCAST_DEVICES_UPDATED";
	public static final String BROADCAST_LOG_IN_FINISHED = "BROADCAST_LOG_IN_FINISHED";
	public static final String BROADCAST_CORE_CLAIMED = "BROADCAST_CORE_CLAIMED";
	public static final String BROADCAST_CORE_NAMING_REQUEST_COMPLETE = "BROADCAST_CORE_NAMING_REQUEST_COMPLETE";
	public static final String BROADCAST_TINKER_RESPONSE_RECEIVED = "BROADCAST_TINKER_RESPONSE_RECEIVED";
	public static final String BROADCAST_SHOULD_LOG_OUT = "BROADCAST_SHOULD_LOG_OUT";

	public static final String BROADCAST_SERVICE_API_ERROR = "BROADCAST_SERVICE_API_ERROR";

	public static final String EXTRA_ERROR_CODE = "EXTRA_ERROR_CODE";
	public static final String EXTRA_TINKER_RESPONSE = "EXTRA_TINKER_RESPONSE";


	private static ApiFacade instance = null;


	public static ApiFacade getInstance(Context context) {
		if (instance == null) {
			instance = new ApiFacade(context.getApplicationContext());
		}
		return instance;
	}

/*
	public static int getResultCode(Intent broadcastIntent) {
		int resultcode = broadcastIntent.getIntExtra(SimpleSparkApiService.EXTRA_RESULT_CODE,
				SimpleSparkApiService.REQUEST_FAILURE_CODE);
		return resultcode;
	}
*/

	final Context ctx;
	final Handler handler;
	final LocalBroadcastManager broadcastMgr;


	private ApiFacade(Context context) {
		this.ctx = context.getApplicationContext();
		this.handler = new Handler();
		this.broadcastMgr = LocalBroadcastManager.getInstance(this.ctx);
	}

/*
	public void digitalRead(String coreId, String pinId, DigitalValue oldValue) {
		TinkerReadValueReceiver receiver = new TinkerReadValueReceiver(handler,
				TinkerResponse.REQUEST_TYPE_READ, coreId, pinId,
				TinkerResponse.RESPONSE_TYPE_DIGITAL, oldValue.asInt());

		Bundle args = new Bundle();
		args.putString("params", pinId);

		SimpleSparkApiService.post(ctx, new String[] { "devices", coreId, "digitalread" },
				args, receiver, null);
	}

	public void digitalWrite(String coreId, String pinId, DigitalValue oldValue,
			DigitalValue newValue) {
		TinkerWriteValueReceiver receiver = new TinkerWriteValueReceiver(handler,
				TinkerResponse.REQUEST_TYPE_WRITE, coreId, pinId,
				TinkerResponse.RESPONSE_TYPE_DIGITAL, oldValue.asInt(), newValue.asInt());

		Bundle args = new Bundle();
		args.putString("params", pinId + "," + newValue.name());

		SimpleSparkApiService.post(ctx, new String[] { "devices", coreId, "digitalwrite" },
				args, receiver, null);
	}

	public void analogRead(String coreId, String pinId, int oldValue) {
		TinkerReadValueReceiver receiver = new TinkerReadValueReceiver(handler,
				TinkerResponse.REQUEST_TYPE_READ, coreId, pinId,
				TinkerResponse.RESPONSE_TYPE_ANALOG, oldValue);

		Bundle args = new Bundle();
		args.putString("params", pinId);

		SimpleSparkApiService.post(ctx, new String[] { "devices", coreId, "analogread" },
				args, receiver, null);
	}
	
	public void analogWrite(String coreName, String pinId, int oldValue, int newValue) {
		TinkerWriteValueReceiver receiver = new TinkerWriteValueReceiver(handler,
				TinkerResponse.REQUEST_TYPE_WRITE, coreName, pinId,
				TinkerResponse.RESPONSE_TYPE_ANALOG, oldValue, newValue);
		Bundle args = new Bundle();
		args.putString("params", pinId + "," + newValue);

		SimpleSparkApiService.post(ctx, new String[] { "devices", coreName, "analogwrite" },
				args, receiver, null);
	}
	
*/

	// Senior Design Functions
	public void toggle_activation(String coreId, String pinId, int oldValue) {
	/*
		TinkerReadValueReceiver receiver = new TinkerReadValueReceiver(handler,
				TinkerResponse.REQUEST_TYPE_READ, coreId, pinId,
				TinkerResponse.RESPONSE_TYPE_ANALOG, oldValue);
	*/
		Bundle args = new Bundle();
		args.putString("params", "t_act");
		SimpleSparkApiService.post(ctx, new String[] { "devices",  coreId,  "fn_r" }, 
				args, null, null);
	}
	
	public void set_rgbl(String coreName, String pinId, int oldValue, int newValue) {
	/*
		TinkerWriteValueReceiver receiver = new TinkerWriteValueReceiver(handler,
				TinkerResponse.REQUEST_TYPE_WRITE, coreName, pinId,
				TinkerResponse.RESPONSE_TYPE_ANALOG, oldValue, newValue);
	*/
		Bundle args = new Bundle();
		args.putString("params",  "s_rgbl," + newValue);
		SimpleSparkApiService.post(ctx, new String[] { "devices",  coreName,  "fn_r" }, 
				args, null, null);
	}
	
	public void set_rgb(String coreName, String pinId, int oldValue, int newValue) {
	/*
		TinkerWriteValueReceiver receiver = new TinkerWriteValueReceiver(handler,
				TinkerResponse.REQUEST_TYPE_WRITE, coreName, pinId,
				TinkerResponse.RESPONSE_TYPE_ANALOG, oldValue, newValue);
	*/
		Bundle args = new Bundle();
		args.putString("params",  "s_rgb," + newValue);
		SimpleSparkApiService.post(ctx, new String[] { "devices",  coreName,  "fn_r" }, 
				args, null, null);
	}
	
	public void rainbow(String coreId, String pinId, int oldValue) {
	/*
		TinkerReadValueReceiver receiver = new TinkerReadValueReceiver(handler,
				TinkerResponse.REQUEST_TYPE_READ, coreId, pinId,
				TinkerResponse.RESPONSE_TYPE_ANALOG, oldValue);
	*/
		Bundle args = new Bundle();
		args.putString("params", "rainbow");
		SimpleSparkApiService.post(ctx, new String[] { "devices",  coreId,  "fn_r" }, 
				args, null, null);
	}

	public void enable_rgb(String coreId, String pinId, int oldValue) {
	/*
		TinkerReadValueReceiver receiver = new TinkerReadValueReceiver(handler,
				TinkerResponse.REQUEST_TYPE_READ, coreId, pinId,
				TinkerResponse.RESPONSE_TYPE_ANALOG, oldValue);
	*/
		Bundle args = new Bundle();
		args.putString("params", "e_rgb");
		SimpleSparkApiService.post(ctx, new String[] { "devices",  coreId,  "fn_r" }, 
				args, null, null);
	}
	
	public void disable_rgb(String coreId, String pinId, int oldValue) {
	/*
		TinkerReadValueReceiver receiver = new TinkerReadValueReceiver(handler,
				TinkerResponse.REQUEST_TYPE_READ, coreId, pinId,
				TinkerResponse.RESPONSE_TYPE_ANALOG, oldValue);
		*/
		Bundle args = new Bundle();
		args.putString("params", "d_rgb");
		SimpleSparkApiService.post(ctx, new String[] { "devices",  coreId,  "fn_r" }, 
				args, null, null);
	}
	
	// Senior Design Variables
	// Need to look into manipulating the get/post commands to either properly use the
	// Bundle, receiver, and broadcast, commented for now
/*	public void get_bat_level(String coreId) {
		SimpleSparkApiService.get(ctx, new String[] { "devices", coreId, "bat_level" }, 
				args, receiver, null)
	}*/

}

