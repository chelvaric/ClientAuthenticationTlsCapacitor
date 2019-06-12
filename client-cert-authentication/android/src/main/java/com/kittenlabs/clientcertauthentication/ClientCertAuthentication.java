package com.kittenlabs.clientcertauthentication;

import android.security.keystore.KeyProperties;
import android.util.Log;

import com.getcapacitor.JSObject;
import com.getcapacitor.NativePlugin;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import android.util.Base64;
import com.getcapacitor.PluginMethod;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Security;


import org.spongycastle.util.io.pem.PemGenerationException;
import org.spongycastle.util.io.pem.PemObject;
import org.spongycastle.util.io.pem.PemWriter;


import java.security.GeneralSecurityException;

@NativePlugin()
public class ClientCertAuthentication extends Plugin {


    //this needs to be implemented to ovveride the default security of android
    static {
        Security.insertProviderAt(new org.spongycastle.jce.provider.BouncyCastleProvider(), 1);
    }

    @PluginMethod()
    public void echo(PluginCall call) {
        String value = call.getString("value");

        JSObject ret = new JSObject();
        ret.put("value", value);
        call.success(ret);
    }

    @PluginMethod()
    public void generateCsr(PluginCall call)
    {
        JSObject ret = new JSObject();
        ret.put("value", "succeed");
        call.success(ret);
    }

    // this method makes a key with the bit that is given to it
    @PluginMethod()
    public void generateRsaKey(PluginCall call)  {

        int bits = call.getInt( "keySize");

        try {
            //we make the generator with the rsa algorithm
            KeyPairGenerator generator = KeyPairGenerator.getInstance(KeyProperties.KEY_ALGORITHM_RSA);

            //initialize the generator with the amount of key bits the user defined
            generator.initialize(bits);

            //we generate the key pair
            KeyPair keypair = generator.genKeyPair();

            //this should be removed in production
            Log.d("Keypair private:", keypair.getPrivate().toString());


            //write the private key to a pem file
            PemObject object = new PemObject("RSA PRIVATE KEY",keypair.getPrivate().getEncoded());

            //well write it to an bytearray first
            ByteArrayOutputStream bytestream = new ByteArrayOutputStream();

            //write the file to the stream
            PemWriter pemWriter = new PemWriter(new OutputStreamWriter(bytestream));
            pemWriter.writeObject(object);

            //make a string from the file
            String pemfile = new String(bytestream.toByteArray());

            //this should be removed in production
            Log.d("PemFile:", pemfile);

            JSObject ret = new JSObject();
            ret.put("value", pemfile);
            call.success(ret);
        }
        catch (IOException ioex)
        {
            call.error(ioex.getMessage());
        }

        catch (NoSuchAlgorithmException exception)
        {
            //return the exception message as a fail of the method, this is an exception only throw when the method doesn't know the algorithme used
            call.error(exception.getMessage());
        }


    }
}
