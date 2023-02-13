package com.ragnar.MySchoolManagement.auth;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

@Service
public class LoginAttemptService {

	private static final int LOGIN_ATTEMPTS = 2;

	private LoadingCache<String, Integer> loginAttemptsCache;

	public LoginAttemptService() {
		super();
		this.loginAttemptsCache = CacheBuilder.newBuilder()
				                  .expireAfterAccess(2, TimeUnit.MINUTES)
				                  .maximumSize(100)
			               	.build(new CacheLoader<String, Integer>() 
			               	{
					public Integer load(String key) {
						return 0;
					} 

				});
	}

	public void removeUserFromCache(String username) {
		
		loginAttemptsCache.invalidate(username);
	}

	public void addUserToOurCache(String username) {

		int attempts = 0;
		
		try {
			
			attempts = loginAttemptsCache.get(username);

		} catch (ExecutionException e) {
			attempts = 0;
		}

		attempts++;
		loginAttemptsCache.put(username, attempts);
	}

	public boolean hasExceededMaxAttempt(String username) {
		try {
			return loginAttemptsCache.get(username) >= LOGIN_ATTEMPTS;

		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		return false;

	}
}
