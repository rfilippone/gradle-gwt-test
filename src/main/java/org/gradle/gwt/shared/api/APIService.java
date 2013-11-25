package org.gradle.gwt.shared.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.Options;
import org.fusesource.restygwt.client.RestService;

public interface APIService extends RestService {
	
	class LoginResult
	{
		public String text;
		public String ssk;
	}
	
	class GetDataResult
	{
		public String data;
	}
	
    @GET
    @Path("login/{userid}")
    public void login(@PathParam("userid") String userid, MethodCallback<LoginResult> callback);
    
    @GET
    @Options(dispatcher=HMACDispatcher.class)
    @Path("data")
    public void getData(MethodCallback<GetDataResult> callback);
    
}