package com.ragnar.MySchoolManagement.constanst;

public class SecurityConstants {

	public static final long EXPIRATION_TIME =   900_000;//  432_000_000;

	public static final String TOKEN_PREFIX = "Bearer";

	public static final String JWT_TOKEN_HEADER = "token";

	public static final String TOKEN_CANNOT_BE_VERIFIED = "Token cannot be verified";

	public static final String AUTHORITIES = "Authorities";

	public static final String ISSUER = "Egbedina_Inc";

	public static final String Egbedina_Inc_Administration = "Egbedina_Inc_Administration";

	public static final String FORBIDDEN_MESSAGE = "you need to login";

	public static final String ACCESS_DENIED_MESSAGE = "Access Denied";

	public static final String OPTIONS_HTTP_METHOD = "Options";

	public static final String[] PUBLIC_URLS = { "user/login", "user/register", "user/resetpassword/**" };

	//public static final String[] PUBLIC_URLS = { "**" };

}
