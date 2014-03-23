import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;

public class AES {

	static Key pobierzKlucz(String sciezkaDoKeyStore, String aliasHasla,
			String hasloDoKeystora) {

		try {
			KeyStore ks = KeyStore.getInstance("UBER", "BC");
			InputStream inputStream = new FileInputStream(sciezkaDoKeyStore);
			ks.load(inputStream, hasloDoKeystora.toCharArray());

			// klucz zostal juz wygenerowany przy liscie 1 - java project:
			// KryptographyLista1
			// klucz jest 128-bitowy wygenerowany szyfrem strumieniowym ARC4
			return ks.getKey(aliasHasla, hasloDoKeystora.toCharArray());
		} catch (UnrecoverableKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (KeyStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchProviderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CertificateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static void zapiszZakodowanaWiadomoscDoPilku(byte[] kryptogram,
			String plikZWiadomoscia) {

		File kryptogramFile = new File(plikZWiadomoscia);
		FileOutputStream stream;
		try {
			stream = new FileOutputStream(kryptogramFile);

			stream.write(kryptogram);
			stream.close();
			// System.out.println("sceizka do kryptogramu"
			// + kryptogramFile.getAbsolutePath());

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void zaszyfrowanieWiadomosci(Key klucz,
			String sciezkaWejsciowa, String sciezkaWyjsciowa) {
		IvParameterSpec ivSpec = new IvParameterSpec(new byte[16]);
		// przygotowanie do zaszyfrowania
		Cipher aesCipher;
		try {
			aesCipher = Cipher.getInstance("AES/CTR/NoPadding", "BC");

			aesCipher.init(Cipher.ENCRYPT_MODE, klucz, ivSpec);

			File inFile = new File(sciezkaWejsciowa);
			File outFile = new File(sciezkaWyjsciowa);
			FileInputStream inputStream = new FileInputStream(inFile);
			ByteArrayOutputStream bOut = new ByteArrayOutputStream();
			// FileOutputStream out = new FileOutputStream(outFile);
			CipherOutputStream outCipherStream = new CipherOutputStream(bOut,
					aesCipher);
			int ch;
			while ((ch = inputStream.read()) >= 0) {
				bOut.write(ch);
			}

			byte[] cipherText = bOut.toByteArray();
			outCipherStream.write(cipherText);

			aesCipher.doFinal(cipherText);
			outCipherStream.close();
			inputStream.close();
		} catch (NoSuchAlgorithmException | NoSuchProviderException
				| NoSuchPaddingException | InvalidKeyException
				| InvalidAlgorithmParameterException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static byte[] deszyfrowanieWiadomosci(Key klucz,
			String sciezkaWejsciowa, String sciezkaWyjsciowa) {
		IvParameterSpec ivSpec = new IvParameterSpec(new byte[16]);
		Cipher aesCipher;
		try {
			aesCipher = Cipher.getInstance("AES/CTR/NoPadding", "BC");

			aesCipher.init(Cipher.DECRYPT_MODE, klucz, ivSpec);

			File outFile = new File(sciezkaWyjsciowa);
			File inFile = new File(sciezkaWejsciowa);
			FileOutputStream outputStream = new FileOutputStream(outFile);
			ByteArrayOutputStream bOut = new ByteArrayOutputStream();
			FileInputStream input = new FileInputStream(inFile);
			CipherInputStream inCipherStream = new CipherInputStream(input,
					aesCipher);

			int ch;
			while ((ch = inCipherStream.read()) >= 0) {
				bOut.write(ch);
			}

			byte[] cipherText = bOut.toByteArray();
			outputStream.write(cipherText);

			aesCipher.doFinal(cipherText);
			inCipherStream.close();
			bOut.close();
			return cipherText;
		} catch (NoSuchAlgorithmException | NoSuchProviderException
				| NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}
}
