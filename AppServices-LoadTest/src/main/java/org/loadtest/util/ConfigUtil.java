package org.loadtest.util;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Properties;

class ConfigUtil {

    private static final String ENV_P_NAME = "APP_SRVCS_REPL_TEST";
    private static Properties configProps = new Properties();

    static {
        String configFile = System.getenv(ENV_P_NAME) + "/dbinfo.properties";
        try {
            InputStream path = new FileInputStream(new File(configFile));
            configProps.load(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static String getDbUserName() {
        return configProps.getProperty("dbusername");
    }

    static String getDBPassword() {
        return configProps.getProperty("dbpassword");
    }

    static String getDBName() {
        return configProps.getProperty("dbname");
    }

    static String getDBLocation() {
        return configProps.getProperty("dblocation");
    }

    static String getDBLogFileLocation() {return configProps.getProperty("dblogfileloc");}

    static boolean getReplicatorAction() {
        return Boolean.valueOf(configProps.getProperty("replicationAction"));
    }

    static X509Certificate getAppServicesCert() {
        X509Certificate certificate = null;
        try {
            CertificateFactory certFact  = CertificateFactory.getInstance("X.509");
            FileInputStream fis = new FileInputStream(configProps.getProperty("appservicescert"));
            certificate = (X509Certificate) certFact.generateCertificate(fis);
        } catch (CertificateException e) {
            throw new RuntimeException(e);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return certificate;
    }

    static URI getSGEndPoint() {
        StringBuilder builder = new StringBuilder();
        builder.append("wss://");
        builder.append(configProps.getProperty("dbsghost"));
        builder.append(":");
        builder.append("4984");
        URI url = null;
        try {
            url = new URI(String.format("%s/%s", builder.toString(), getDBName()));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return url;
    }

    static boolean checkDirExists()
    {
        return false;
    }
}
