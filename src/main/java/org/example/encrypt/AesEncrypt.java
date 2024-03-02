package org.example.encrypt;

import org.example.enums.ErrorCodeEnum;
import org.example.exception.ProcessException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.util.Base64;

public class AesEncrypt {

    /**
     *  encrypt type: Symmetric
     *  algorithm: AES
     *  Uses Initialization Vector: Yes 16Bytes
     *  Mode: CBC(Cipher block chaining)
     *  Padding: PKCs5Padding
     *  Encoding: Base64
     */

    private String iv;
    private String key;

    public AesEncrypt(String key, String iv){
        this.iv = iv;
        this.key = key;
        if (Base64.getDecoder().decode(this.iv).length != 16)
            throw new ProcessException(ErrorCodeEnum.AES_IV_ERROR);
        if (key== null || "".equals(key))
            throw new ProcessException(ErrorCodeEnum.AES_KEY_ERROR);
    }

    public String aesEncryptString(String originStr) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, UnsupportedEncodingException, BadPaddingException, IllegalBlockSizeException {
        IvParameterSpec iv = new IvParameterSpec(Base64.getDecoder().decode(this.iv));
        Key encryptionKey = new SecretKeySpec(Base64.getDecoder().decode(this.key), "AES");

        byte[] content = null;
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, encryptionKey, iv);
        content = cipher.doFinal(originStr.getBytes("UTF-8"));
        String encryptedString = Base64.getEncoder().encodeToString(content);
        return encryptedString;
    }

    public String aesDecryptString(String inputStr) throws Exception {
        System.out.println("begin decrypt ...");
        IvParameterSpec iv = new IvParameterSpec(Base64.getDecoder().decode(this.iv));//iv
        Key encryptionKey = new SecretKeySpec(Base64.getDecoder().decode(this.key), "AES");//key

        byte[] content = Base64.getDecoder().decode(inputStr);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, encryptionKey, iv);
        content = cipher.doFinal(content);
        String decryptedString = new String(content, "UTF-8");
        return decryptedString;
    }

    public void encryptFile(String inputPath, String outputPath) {
        try{

            File outputFile = new File(outputPath);
            if (!outputFile.getParentFile().exists()){
                outputFile.getParentFile().mkdir();
            }

            FileInputStream inputStream = new FileInputStream(new File(inputPath));
            FileOutputStream outputStream = new FileOutputStream(outputFile);
            IvParameterSpec ivParameterSpec = new IvParameterSpec(this.iv.getBytes());
//            byte[] keyBytes = Base64.getDecoder().decode(this.key);
            SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParameterSpec);

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                byte[] output = cipher.update(buffer, 0, bytesRead);
                if (output != null) {
                    outputStream.write(output);
                }
            }
            byte[] outputBytes = cipher.doFinal();
            if (outputBytes != null) {
                outputStream.write(outputBytes);
            }
            inputStream.close();
            outputStream.close();
        }catch (Exception e){
            System.out.println(  "error doEncrypt: " + inputPath);
            System.out.println(e.toString());
        }

    }

    /**
     * Method to generate IV:
     * (it is 16 Bytes / 16 characters)
     */
    public String generateIv() {
        byte[] iv = new byte[16];
        new SecureRandom().nextBytes(iv);
        return new String(Base64.getEncoder().encode(iv));
    }
}
