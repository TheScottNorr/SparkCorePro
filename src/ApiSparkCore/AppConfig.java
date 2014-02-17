package ApiSparkCore;



import android.content.Context;


public class AppConfig {

	private static Context ctx;
	private static int staging_hostname;
	private static int prod_hostname;
	private static int api_url_scheme;
	private static int api_host_port;
	private static int spark_token_creation_credentials;
	private static int use_staging;
	private static int api_version;
	private static int api_param_access_token;
	private static int smart_config_hello_listen_address;
	private static int smart_config_hello_port;
	private static int smart_config_hello_msg_length;
	private static int smart_config_default_aes_key;
	

	// Should be called when starting up the app, probably in
	// Application.onCreate()
	public static void initialize(Context context) {
		ctx = context.getApplicationContext();
	}

	public static String getApiHostname() {
		int resId = useStaging() ? staging_hostname : prod_hostname;
		return ctx.getString(resId);
	}

	public static String getApiUrlScheme() {
		return ctx.getString(api_url_scheme);
	}

	public static int getApiHostPort() {
		return ctx.getResources().getInteger(api_host_port);
	}

	public static String getSparkTokenCreationCredentials() {
		return ctx.getString(spark_token_creation_credentials);
	}

	public static boolean useStaging() {
		return ctx.getResources().getBoolean(use_staging);
	}

	public static String getApiVersion() {
		return ctx.getString(api_version);
	}

	public static String getApiParamAccessToken() {
		return ctx.getString(api_param_access_token);
	}

	public static String getSmartConfigHelloListenAddress() {
		return ctx.getString(smart_config_hello_listen_address);
	}

	public static int getSmartConfigHelloListenPort() {
		return ctx.getResources().getInteger(smart_config_hello_port);
	}

	public static int getSmartConfigHelloMessageLength() {
		return ctx.getResources().getInteger(smart_config_hello_msg_length);
	}

	public static String getSmartConfigDefaultAesKey() {
		return ctx.getString(smart_config_default_aes_key);
	}

}

