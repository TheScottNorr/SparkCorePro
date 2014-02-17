package ApiSparkCore;


import static Util.Py.list;
import static Util.Py.truthy;
//import io.spark.core.android.R;
//import io.spark.core.android.app.AppConfig;
//import io.spark.core.android.cloud.ApiFacade;
//import io.spark.core.android.cloud.ApiFacade.ApiResponseReceiver;
//import io.spark.core.android.cloud.ApiUrlHelper;
//import io.spark.core.android.cloud.WebHelpers;
//import io.spark.core.android.cloud.login.TokenRequest;
//import io.spark.core.android.cloud.login.TokenResponse;
//import io.spark.core.android.cloud.login.TokenTool;
//import io.spark.core.android.storage.Prefs;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import Util.TLog;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.support.v4.content.LocalBroadcastManager;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.OkHttpClient;
	
public class SimpleSparkApiService {
	
	/**
	 * Perform a POST request with the given args -- see {@link ApiFacade} for
	 * examples.
	 * 
	 * @param ctx
	 *            any Context
	 * @param resourcePathSegments
	 *            the URL path as a String array (not including version string).
	 *            e.g.: if your path was
	 *            "/v1/devices/0123456789abcdef01234567/myFunction", you'd use:
	 *            new String[] { "devices", "0123456789abcdef01234567",
	 *            "myFunction" }
	 * @param formEncodingBodyData
	 *            the data to post, as key-value pairs to be encoded as form
	 *            data.
	 * @param resultReceiver
	 *            Optional; specifies the ResultReceiver instance to use for
	 *            receiving the result. Using a subclass of
	 *            {@link ApiResponseReceiver} here is recommended for
	 *            simplicity.
	 * @param broadcastName
	 *            Optional; specifies the "action" string for a broadcast to be
	 *            sent via {@link LocalBroadcastManager}. See
	 *            {@link #processResponse(Response, Intent)} for more info.
	 */
	public static void post(Context ctx, String[] resourcePathSegments,
			Bundle formEncodingBodyData, ResultReceiver resultReceiver, String broadcastName) {
		ctx.startService(
				buildRestRequestIntent(ctx, resourcePathSegments, formEncodingBodyData,
						resultReceiver, broadcastName)
						.setAction(ACTION_POST));
	}
	
		/**
	 * Perform a PUT request with the given args -- see {@link ApiFacade} for
	 * examples
	 * 
	 * @param ctx
	 *            any Context
	 * @param resourcePathSegments
	 *            the URL path as a String array (not including version string).
	 *            e.g.: if your path was
	 *            "/v1/devices/0123456789abcdef01234567/myFunction", you'd use:
	 *            new String[] { "devices", "0123456789abcdef01234567",
	 *            "myFunction" }
	 * @param params
	 *            the data to PUT, as key-value pairs in a Bundle
	 * @param resultReceiver
	 *            Optional; specifies the ResultReceiver instance to use for
	 *            receiving the result. Using a subclass of
	 *            {@link ApiResponseReceiver} here is recommended for
	 *            simplicity.
	 * @param broadcastName
	 *            Optional; specifies the "action" string for a broadcast to be
	 *            sent via {@link LocalBroadcastManager}. See
	 *            {@link #processResponse(Response, Intent)} for more info.
	 */
	public static void put(Context ctx, String[] resourcePathSegments, Bundle params,
			ResultReceiver resultReceiver, String broadcastName) {
		ctx.startService(
				// null post data
				buildRestRequestIntent(ctx, resourcePathSegments, params, resultReceiver,
						broadcastName)
						.setAction(ACTION_PUT));
	}

	/**
	 * Perform a GET request -- see {@link ApiFacade} for examples
	 * 
	 * @param ctx
	 *            any Context
	 * @param resourcePathSegments
	 *            the URL path as a String array (not including version string).
	 *            e.g.: if your path was
	 *            "/v1/devices/0123456789abcdef01234567/myFunction", you'd use:
	 *            new String[] { "devices", "0123456789abcdef01234567",
	 *            "myFunction" }
	 * @param params
	 *            the URL params, as key-value pairs in a Bundle
	 * @param resultReceiver
	 *            Optional; specifies the ResultReceiver instance to use for
	 *            receiving the result. Using a subclass of
	 *            {@link ApiResponseReceiver} here is recommended for
	 *            simplicity.
	 * @param broadcastName
	 *            Optional; specifies the "action" string for a broadcast to be
	 *            sent via {@link LocalBroadcastManager}. See
	 *            {@link #processResponse(Response, Intent)} for more info.
	 */
	public static void get(Context ctx, String[] resourcePathSegments, Bundle params,
			ResultReceiver resultReceiver, String broadcastName) {
		ctx.startService(
				buildRestRequestIntent(ctx, resourcePathSegments, params, resultReceiver,
						broadcastName)
						.setAction(ACTION_GET));
	}
	
	private static Intent buildRestRequestIntent(Context ctx, String[] resourcePathSegments,
			Bundle params, ResultReceiver resultReceiver, String broadcastAction) {

		Intent intent = new Intent(ctx, SimpleSparkApiService.class)
				.putExtra(EXTRA_RESOURCE_PATH_SEGMENTS, resourcePathSegments);

		if (params != null) {
			intent.putExtra(EXTRA_REQUEST_QUERY_PARAMS, params);
		}

		if (resultReceiver != null) {
			intent.putExtra(EXTRA_RESULT_RECEIVER, resultReceiver);
		}

		if (broadcastAction != null) {
			intent.putExtra(EXTRA_BROADCAST_ACTION, broadcastAction);
		}

		return intent;
	}
	
	private static final String NS = SimpleSparkApiService.class.getCanonicalName() + ".";
	private static final String ACTION_GET = NS + "ACTION_GET";
	private static final String ACTION_POST = NS + "ACTION_POST";
	private static final String ACTION_PUT = NS + "ACTION_PUT";
	
	private static final String ACTION_LOG_IN = NS + "ACTION_LOG_IN";

	private static final String EXTRA_RESOURCE_PATH_SEGMENTS = NS + "EXTRA_RESOURCE_PATH_SEGMENTS";
	private static final String EXTRA_REQUEST_QUERY_PARAMS = NS + "EXTRA_REQUEST_QUERY_PARAMS";
	private static final String EXTRA_RESULT_RECEIVER = NS + "EXTRA_RESULT_RECEIVER";
	private static final String EXTRA_BROADCAST_ACTION = NS + "EXTRA_BROADCAST_ACTION";
	
	ByteArrayOutputStream reusableResponseStream = new ByteArrayOutputStream(8192);
	
	private static OkHttpClient okHttpClient;
	private static Gson gson;
	
	public SimpleSparkApiService() {
		//super(SimpleSparkApiService.class.getSimpleName());
		gson = new GsonBuilder()
					.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
					.create();
		okHttpClient = new OkHttpClient();

	}

	
	protected void onHandleIntent(Intent intent) {
	
		Bundle extras = intent.getExtras();
		String action = intent.getAction();
		//Response response = null;

		String token = "token";
		
		if (ACTION_GET.equals(action)) {
			URL url = buildGetUrl(extras, token);
			get(url);

		} else if (ACTION_POST.equals(action)) {
			URL url = buildPostUrl(extras, token);
			String postData = getPostData(extras);
			post(url, postData);

		} else {
			log.wtf("Received intent with unrecognized action: " + action);
		}

		//Not sure if processResponse is needed comment for now
		//processResponse(response, intent);
	}
	
	/*
	void processResponse(Response response, Intent intent) {

	}
	*/
	
	void post(URL url, String stringData) {
		performRequestWithInputData(url, "POST", stringData);
	}

	void put(URL url, String stringData) {
		performRequestWithInputData(url, "PUT", stringData);
	}

	void performRequestWithInputData(URL url, String httpMethod, String stringData) {
		HttpURLConnection connection = this.okHttpClient.open(url);
		OutputStream out = null;
		InputStream in = null;
		int responseCode = -1;
		String responseData = "";

		try {
			try {
				connection.setRequestMethod(httpMethod);
				connection.setDoOutput(true);

				out = connection.getOutputStream();
				out.write(stringData.getBytes(HTTP.UTF_8));
				out.close();

				responseCode = connection.getResponseCode();

				in = connection.getInputStream();
				responseData = readAsUtf8String(in);

			} finally {
				// Clean up.
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			}
		} catch (IOException e) {
			log.e("Error trying to make " + connection.getRequestMethod() + " request");
		}

		//return new Response(responseCode, responseData);
	}

	void get(URL url) {
		HttpURLConnection connection = this.okHttpClient.open(url);
		InputStream in = null;
		int responseCode = -1;
		String responseData = "";
		// Java I/O throws *exception*al parties!
		try {
			try {
				responseCode = connection.getResponseCode();
				in = connection.getInputStream();
				responseData = readAsUtf8String(in);
			} finally {
				if (in != null) {
					in.close();
				}
			}
		} catch (IOException e) {
			log.e("Error trying to make GET request");
		}

		//return new Response(responseCode, responseData);
	}
	
	URL buildGetUrl(Bundle intentExtras, String token) {
		Uri.Builder uriBuilder = ApiUrlHelper.buildUri(token,
				intentExtras.getStringArray(EXTRA_RESOURCE_PATH_SEGMENTS));
		Bundle queryParams = intentExtras.getBundle(EXTRA_REQUEST_QUERY_PARAMS);
		if (queryParams != null) {
			for (NameValuePair param : bundleParamsToNameValuePairs(queryParams)) {
				uriBuilder.appendQueryParameter(param.getName(), param.getValue());
			}
		}

		return ApiUrlHelper.convertToURL(uriBuilder);
	}

	URL buildPostUrl(Bundle intentExtras, String token) {
		Uri.Builder uriBuilder = ApiUrlHelper.buildUri(token,
				intentExtras.getStringArray(EXTRA_RESOURCE_PATH_SEGMENTS));
		return ApiUrlHelper.convertToURL(uriBuilder);
	}
	
	String getPostData(Bundle intentExtras) {
		String postString = "";

		Bundle queryParams = intentExtras.getBundle(EXTRA_REQUEST_QUERY_PARAMS);

		if (queryParams != null) {
			String putString = URLEncodedUtils.format(
					bundleParamsToNameValuePairs(queryParams),
					HTTP.UTF_8);
			postString = putString;
		}

		return postString;
	}

	List<NameValuePair> bundleParamsToNameValuePairs(Bundle params) {
		List<NameValuePair> paramList = list();
		for (String key : params.keySet()) {
			Object value = params.get(key);
			if (value != null) {
				paramList.add(new BasicNameValuePair(key, value.toString()));
			}
		}
		return paramList;
	}
	
	String readAsUtf8String(InputStream in) throws IOException {
		reusableResponseStream.reset();
		byte[] buffer = new byte[1024];
		
		for (int count; (count = in.read(buffer)) != -1;) {
			reusableResponseStream.write(buffer, 0, count);
		}
		return reusableResponseStream.toString(HTTP.UTF_8);
	}
	
	private static final TLog log = new TLog(SimpleSparkApiService.class);
}
