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
import java.io.StringWriter;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;


import org.spongycastle.operator.ContentSigner;
import org.spongycastle.operator.OperatorCreationException;
import org.spongycastle.operator.jcajce.JcaContentSignerBuilder;
import org.spongycastle.pkcs.PKCS10CertificationRequest;
import org.spongycastle.pkcs.PKCS10CertificationRequestBuilder;
import org.spongycastle.pkcs.jcajce.JcaPKCS10CertificationRequestBuilder;
import org.spongycastle.util.io.pem.PemGenerationException;
import org.spongycastle.util.io.pem.PemObject;
import org.spongycastle.util.io.pem.PemWriter;


import java.security.GeneralSecurityException;

import javax.security.auth.x500.X500Principal;

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

        String privateKey = call.getString("privateKey");
        String publicKey = call.getString("publicKey");
        String commonName = call.getString("commonName");


        try {
            PrivateKey key = CsrHelperClass.GetPrivateKeyFromPem(privateKey);
            PublicKey pubKey = CsrHelperClass.GetPublicKeyFromPem(publicKey);


            PKCS10CertificationRequestBuilder p10Builder = new JcaPKCS10CertificationRequestBuilder(
                    new X500Principal("C=Belgium, L=East-Flanders, O=Qbus, CN=" + commonName),
                    pubKey
            );
            JcaContentSignerBuilder csBuilder = new JcaContentSignerBuilder("SHA256withRSA");
            ContentSigner signer = null;
            try {
                signer = csBuilder.build(key);
            } catch (OperatorCreationException e) {
                call.error(e.getMessage());
            }
            PKCS10CertificationRequest csr = p10Builder.build(signer);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            try (PemWriter pemWriter = new PemWriter(new OutputStreamWriter(outputStream))) {

                pemWriter.writeObject(new PemObject("CERTIFICATE REQUEST", csr.getEncoded()));

            } catch (IOException e) {

                throw new RuntimeException(e);

            }
            String CsrPem = new String(outputStream.toByteArray());

            JSObject ret = new JSObject();
            ret.put("value", CsrPem);
            call.success(ret);


        }
        catch (Exception ex)
        {
            call.error(ex.getMessage());
        }




    }

    // this method makes a key with the bit that is given to it
    @PluginMethod()
    public void generateRsaKey(PluginCall call)  {

        //get the key size passed from typescript
        int bits = call.getInt( "keySize");

        try {
            //we make the generator with the rsa algorithm
            KeyPairGenerator generator = KeyPairGenerator.getInstance(KeyProperties.KEY_ALGORITHM_RSA);

            //initialize the generator with the amount of key bits the user defined
            generator.initialize(bits);

            //we generate the key pair
            KeyPair keypair = generator.genKeyPair();




            //write the private key to a pem file

            KeyPairString kps = CsrHelperClass.WritePrivatePemString(keypair);






            JSObject ret = new JSObject();
            ret.put("privateKey", kps.PrivateKey);
            ret.put("publicKey", kps.PublicKey);
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
