import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.Console;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;

public class AESMain {

	public static void main(String[] args) throws InvalidKeyException,
			NoSuchAlgorithmException, KeyStoreException, CertificateException,
			IOException, UnrecoverableKeyException, NoSuchPaddingException,
			IllegalBlockSizeException, BadPaddingException,
			InvalidAlgorithmParameterException {
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

		String trybSzyfrowania = "OFB";
		String hasloDoKeystora = "ala ma kota";
		String aliasHasla = "mojAlias";
		String sciezkaDoKeyStore = "D:\\eclipse\\Semestr4\\AES\\keyStore.ks";
		// Console console = System.console();
		// console.printf("Podaj tryb szyfrowania np. OFB lub CTR \n");
		// String trybSzyfrowania = console.readLine();
		// console.printf("Podaj sciezke do keystora przechowujcego klucz \n");
		// String sciezkaDoKeyStore = console.readLine();
		// char[] hasloDoKeystora =
		// console.readPassword("Haslo do KeyStore'a: ");
		// char aliasHasla[] = console
		// .readPassword("Identyikator  do klucza z keystore'a: \n");

		// String hasloDoKeystora = "ala ma kota";
		// String aliasHasla = "mojAlias";
		// String sciezkaDoKeyStore = "
		// D:\eclipse\Semestr4\KryptographyLista1\keyStore.ks";

		String sciezkaWejsciowa = "D:\\eclipse\\Semestr4\\AESplikMuzyczny\\input.wav";
		String sciezkaWyjsciowa = "D:\\eclipse\\Semestr4\\AESplikMuzyczny\\output.wav";
		AES.zaszyfrowanieWiadomosci(AES.pobierzKlucz(sciezkaDoKeyStore,
				new String(aliasHasla), new String(hasloDoKeystora)),
				sciezkaWejsciowa, sciezkaWyjsciowa);

		String sciezkaWejsciowa2 = "D:\\eclipse\\Semestr4\\AESplikMuzyczny\\output.wav";
		String sciezkaWyjsciowa2 = "D:\\eclipse\\Semestr4\\AESplikMuzyczny\\Decriptoutput.wav";

		byte[] zdekodowanyTekst = AES.deszyfrowanieWiadomosci(AES.pobierzKlucz(
				sciezkaDoKeyStore, new String(aliasHasla), new String(
						hasloDoKeystora)), sciezkaWejsciowa, sciezkaWyjsciowa);

		AudioInputStream outSteream;
		try {
			outSteream = AudioSystem
					.getAudioInputStream(new ByteArrayInputStream(
							zdekodowanyTekst));
			Clip clip = AudioSystem.getClip();
			clip.open(outSteream);
			clip.start();
		} catch (UnsupportedAudioFileException | LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		JFrame a = new JFrame();
		a.setVisible(true);

		// System.out.println("Wiadomosc: " + wiadomosc);
		// System.out.println("Kryptogram: " + new String(kryptogram));
		// System.out.println("Odszyfrowana wiadomosc: "
		// + new String(zdekodowanyTekst));

	}
}
