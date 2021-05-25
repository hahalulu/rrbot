package com.hant.trade;

/**
 * @author
 * @version 1.0
 * @brief Custom build vars FILL EVERYTHING CORRECTLY
 * @date
 */

public class BuildVars {
    public static final Boolean debug = true;
    public static final Boolean useWebHook = false;
    public static final int PORT = 8443;
    public static final String EXTERNALWEBHOOKURL = "https://example.changeme.com:" + PORT; // https://(xyz.)externaldomain.tld
    public static final String INTERNALWEBHOOKURL = "https://localhost.changeme.com:" + PORT; // https://(xyz.)localip/domain(.tld)
    public static final String pathToCertificatePublicKey = "./YOURPEM.pem"; //only for self-signed webhooks
    public static final String pathToCertificateStore = "./YOURSTORE.jks"; //self-signed and non-self-signed.
    public static final String certificateStorePassword = "yourpass"; //password for your certificate-store



}
