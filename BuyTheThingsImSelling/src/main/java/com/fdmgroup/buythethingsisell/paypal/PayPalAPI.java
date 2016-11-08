package com.fdmgroup.buythethingsisell.paypal;

public class PayPalAPI {

  /*  public static void main(String[] args) throws PayPalRESTException {
       
    	String clientId = "ATZKP7fEbpA6uG9kzC4gi56_s_0KL2yi7W-R62QkmL7KZbLuKEemdkE9H39HLosqjDR2icOc5YETGe-6";
    	String clientSecret = "ELATYHOTSnN6vPtxIxfh5J-H2X_GWLtGZZQv383rS67f86bhQmUiHnriPpKlVF4xWRkafjiQDrcVKPBi";
    	
    	// Initialize apiContext with proper credentials and environment.
    	APIContext context = new APIContext(clientId, clientSecret, "sandbox");

    	List<String> scopes = new ArrayList<String>() {{
    	    *//**
    	    * 'openid'
    	    * 'profile'
    	    * 'address'
    	    * 'email'
    	    * 'phone'
    	    * 'https://uri.paypal.com/services/paypalattributes'
    	    * 'https://uri.paypal.com/services/expresscheckout'
    	    * 'https://uri.paypal.com/services/invoicing'
    	    *//*
    	    add("openid");
    	    add("profile");
    	    add("email");
    	}};
    	String redirectUrl = Session.getRedirectURL("UserConsent", scopes, context);
    	System.out.println(redirectUrl);
    	
    	// Replace the code with the code value returned from the redirect on previous step.
    	Tokeninfo info = Tokeninfo.createFromAuthorizationCode(context, redirectUrl);
    	String accessToken = info.getAccessToken();
    	String refreshToken = info.getRefreshToken();
    	
    	// Initialize apiContext with proper credentials and environment. Also, set the refreshToken retrieved from previous step.
    	APIContext userAPIContext = new APIContext(clientId, clientSecret, "sandbox").setRefreshToken(info.getRefreshToken());

    	Userinfo userinfo = Userinfo.getUserinfo(userAPIContext);
    	System.out.println(userinfo);
    }
    	*/
}