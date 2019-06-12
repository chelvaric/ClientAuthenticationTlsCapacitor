package com.kittenlabs.clientcertauthentication;

import org.spongycastle.crypto.generators.RSAKeyPairGenerator;
import org.spongycastle.crypto.params.RSAPrivateCrtKeyParameters;
import org.spongycastle.crypto.util.PrivateKeyFactory;
import org.spongycastle.crypto.util.PrivateKeyInfoFactory;
import org.spongycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.spongycastle.util.io.pem.PemObject;
import org.spongycastle.util.io.pem.PemReader;
import org.spongycastle.util.io.pem.PemWriter;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.StringWriter;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;


public class CsrHelperClass {

    private final static String DEFAULT_SIGNATURE_ALGORITHM = "SHA256withRSA";
    private final static String CN_PATTERN = "CN=%s, O=Aralink, OU=OrgUnit";



    private static PemObject readPem(String pem) throws IOException
    {
        PemObject keyObject;

        PemReader pemReader = new PemReader(new InputStreamReader(new ByteArrayInputStream(pem.getBytes())));

        keyObject = pemReader.readPemObject();

        return keyObject;
    }

    public static PrivateKey GetPrivateKeyFromPem(String privateKeyPem) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {

         PemObject privateKeyObject = readPem(privateKeyPem);

        RSAPrivateCrtKeyParameters  privateKeyParameter = (RSAPrivateCrtKeyParameters) PrivateKeyFactory.createKey(
                privateKeyObject.getContent()
        );


        return new JcaPEMKeyConverter()
                .getPrivateKey(
                        PrivateKeyInfoFactory.createPrivateKeyInfo(
                                privateKeyParameter
                        )
                );



    }

    public static PublicKey GetPublicKeyFromPem(String publickKeyPem) throws IOException, NoSuchAlgorithmException ,InvalidKeySpecException
    {
        PemObject publicKeyObject = readPem(publickKeyPem);

        X509EncodedKeySpec spec =
                new X509EncodedKeySpec(publicKeyObject.getContent());
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePublic(spec);
    }


    public static KeyPairString WritePrivatePemString(KeyPair key) throws IOException
    {
        KeyPairString keyPair = new KeyPairString();

        keyPair.PrivateKey = WriteKeyToString(key.getPrivate().getEncoded(),"PRIVATE KEY");

        keyPair.PublicKey = WriteKeyToString(key.getPublic().getEncoded(),"PUBLIC KEY");

        return keyPair;
    }

    public static String WriteKeyToString(byte[] encoding,String subject) throws IOException {

            PemObject object = new PemObject(subject, encoding);

            //we'll write to a string with a string writer
            StringWriter writer = new StringWriter();

            //write the file to the stream
            PemWriter pemWriter = new PemWriter(writer);
            pemWriter.writeObject(object);
            pemWriter.flush();
            pemWriter.close();

           return writer.toString();
    }



}
