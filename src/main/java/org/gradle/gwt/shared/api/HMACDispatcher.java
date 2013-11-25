/**
 * 
 */
package org.gradle.gwt.shared.api;

import java.util.Date;

import org.fusesource.restygwt.client.Dispatcher;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.Resource;
import org.gradle.gwt.client.model.Model;
import org.sgx.gwtsjcl.client.BitArrayType;
import org.sgx.gwtsjcl.client.CodecBase64;
import org.sgx.gwtsjcl.client.CodecHex;
import org.sgx.gwtsjcl.client.CodecUtf8String;
import org.sgx.gwtsjcl.client.HashSha256;
import org.sgx.gwtsjcl.client.SJCL;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat;
import com.google.gwt.user.client.Window;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * @author FirstName LastName
 *
 */
@Singleton
public class HMACDispatcher implements Dispatcher {
	public static HMACDispatcher INSTANCE;
	
	Model model;
	SJCL crypto;
	CodecBase64 base64;
	CodecUtf8String utf8;
	CodecHex hex;
	HashSha256 sha256;
	
	@Inject
	protected HMACDispatcher(Model model) {
		this.model = model;
		INSTANCE = this;
		crypto = SJCL.sjcl();
		base64 = crypto.codec().base64();
		utf8 = crypto.codec().utf8String();
		hex = crypto.codec().hex();
		sha256 = crypto.hash().sha256();
	}
		
	private String hash(String text) {
		return hex.fromBits(sha256.hash(text));
	}
	
	public native final BitArrayType _hmac(SJCL crypto, BitArrayType key, BitArrayType text) /*-{
		var mac = new crypto.misc.hmac(key);
		return mac.mac(text);
	}-*/;

	private BitArrayType hmac(BitArrayType key, BitArrayType text)
	{
		return _hmac(crypto, key, text);
	}
	
	/* (non-Javadoc)
	 * @see org.fusesource.restygwt.client.Dispatcher#send(org.fusesource.restygwt.client.Method, com.google.gwt.http.client.RequestBuilder)
	 */
	@Override
	public Request send(Method method, RequestBuilder builder) throws RequestException {

		try {					
			String content = builder.getRequestData() != null ?  builder.getRequestData() : "";

			DateTimeFormat fmt = DateTimeFormat.getFormat(PredefinedFormat.ISO_8601);
		    String date = fmt.format(new Date());
		    
		    builder.setHeader(Resource.HEADER_ACCEPT, Resource.CONTENT_TYPE_JSON);
		    
		    String signedHeaders = "accept;x-hmac-date";
			String canonicalRequest = builder.getHTTPMethod() + "\n" +			
					builder.getUrl() + "\n" +
					Resource.CONTENT_TYPE_JSON + "\n" +
					date + "\n" +
					signedHeaders + "\n" +
					hash(content) + "\n";
			
			Window.alert(canonicalRequest);
			
			String hashedCanonicalRequest = hash(canonicalRequest);

			String stringToSign = "HMAC-SHA256\n" +
					date + "\n" +
					model.user + "\n" +
					hashedCanonicalRequest;
			
			String signature = hex.fromBits(hmac(hmac(hmac(hex.toBits(model.ssk), utf8.toBits(date)), utf8.toBits(model.user)), utf8.toBits(stringToSign)));
						
			String authorization = "REST-HMAC-SHA256_1.0.0/" + model.user + "/" + signedHeaders + "/" + signature;			
			
			builder.setHeader("X-HMAC-Date", date);
			builder.setHeader("X-HMAC-Auth", authorization);
			
			return builder.send();
		} catch (Exception e) {
			throw new RequestException("Unable to sign request", e);
		}	
	}

}
