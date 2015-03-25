import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidParameterSpecException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Base64;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class Test {
    
    private static final byte[] IV = {-73, -42, -74, 97, 30, -1, 18, 16, 50, -108, 92, -80, 24, 79, -83, 26};
    private static final String CHARSET = "UTF-8";
    
    public static void main(String[] args) throws UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException, InvalidParameterSpecException, InvalidAlgorithmParameterException {
	for (int i = 0; i < 10; i++) {
	    if (i + i > 10)
		break;
	}

	Stack<String> stack = new Stack<String>();
	stack.push("Hello");
	stack.push("World");
	System.out.println(stack.toString());
	stack.pop();
	System.out.println(stack.toString());
	
	Queue<String> queue = new LinkedList<String>();
	queue.offer("Hello");
	queue.offer("World");
	System.out.println(queue.toString());
	queue.poll();
	System.out.println(queue.toString());
	
	System.out.println("4 & 3 = "+(3 & 3));
	
	byte b1 = (byte) 2;
	System.out.println(b1+" binär: "+toBinaryString(b1));
	
	System.out.println("Leeres Array-Test: "+toBinaryString(new byte[]{}));
	System.out.println(toBinaryString((byte) 129));
	System.out.println(Integer.toBinaryString(129));
	
	String plain = "test";
	String plain2 = "3";
	String plain3 = "4";
	String plain_key = "5";
	
	System.out.println("\tPlain: \""+plain+"\""); 
	System.out.println("\tPlain-Binär: "+toBinaryString(plain.getBytes(CHARSET)));
	System.out.println("\tPlain-Bytes: "+Arrays.toString(plain.getBytes(CHARSET)));
	System.out.println("\tPlain-Base64: "+Base64.getEncoder().encodeToString(plain.getBytes(CHARSET)));
	System.out.println("\tPlain-Hex: "+toHexString(plain.getBytes(CHARSET)));
	System.out.println("---------------------------------");
	
	System.out.println("\tPlain2: \""+plain2+"\""); 
	System.out.println("\tPlain2-Binär: "+toBinaryString(plain2.getBytes(CHARSET)));
	System.out.println("\tPlain2-Bytes: "+Arrays.toString(plain2.getBytes(CHARSET)));
	System.out.println("\tPlain2-Base64: "+Base64.getEncoder().encodeToString(plain2.getBytes(CHARSET)));
	System.out.println("\tPlain2-Hex: "+toHexString(plain2.getBytes(CHARSET)));
	System.out.println("---------------------------------");
	
	System.out.println("\tPlain3: \""+plain3+"\""); 
	System.out.println("\tPlain3-Binär: "+toBinaryString(plain3.getBytes(CHARSET)));
	System.out.println("\tPlain3-Bytes: "+Arrays.toString(plain3.getBytes(CHARSET)));
	System.out.println("\tPlain3-Base64: "+Base64.getEncoder().encodeToString(plain3.getBytes(CHARSET)));
	System.out.println("\tPlain3-Hex: "+toHexString(plain3.getBytes(CHARSET)));
	System.out.println("---------------------------------");
	
	byte[] key_bytes = (plain_key).getBytes(CHARSET);
	System.out.println("\tKey-Bytes: "+Arrays.toString(key_bytes));
	MessageDigest sha = MessageDigest.getInstance("SHA-1");
	key_bytes = sha.digest();
	System.out.println("\tKey-Bytes (after SHA-1): "+Arrays.toString(key_bytes));
	key_bytes = Arrays.copyOf(key_bytes, 24); // benutze nur die ersten 128 bit
	Key secretKey = new SecretKeySpec(key_bytes, "AES");
	System.out.println("\tSecretKey-Bytes: "+Arrays.toString(secretKey.getEncoded()));
	System.out.println("\tSecretKey-Base64: "+Base64.getEncoder().encodeToString(secretKey.getEncoded()));
	System.out.println("---------------------------------");
	
	Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
	cipher.init(Cipher.ENCRYPT_MODE, secretKey);
	AlgorithmParameters params = cipher.getParameters();
	byte[] iv = params.getParameterSpec(IvParameterSpec.class).getIV();
	byte[] cipher_bytes = cipher.doFinal(plain.getBytes(CHARSET));
	cipher.init(Cipher.ENCRYPT_MODE, secretKey,new IvParameterSpec(IV));
	AlgorithmParameters params2 = cipher.getParameters();
	byte[] iv2 = params2.getParameterSpec(IvParameterSpec.class).getIV();
	byte[] cipher_bytes2 = cipher.doFinal(plain.getBytes(CHARSET));
	
	System.out.println("\tIV-Bytes: "+Arrays.toString(iv));
	System.out.println("\tIV-Base64: "+Base64.getEncoder().encodeToString(iv));
	System.out.println("\tCipher-Binär: "+toBinaryString(cipher_bytes));
	System.out.println("\tCipher-Bytes: "+Arrays.toString(cipher_bytes));
	System.out.println("\tCipher-Base64: "+Base64.getEncoder().encodeToString(cipher_bytes));
	System.out.println("---------------------------------");
	
	System.out.println("\tIV2-Bytes: "+Arrays.toString(iv2));
	System.out.println("\tIV2-Base64: "+Base64.getEncoder().encodeToString(iv2));
	System.out.println("\tCipher2-Binär: "+toBinaryString(cipher_bytes2));
	System.out.println("\tCipher2-Bytes: "+Arrays.toString(cipher_bytes2));	
	System.out.println("\tCipher2-Base64: "+Base64.getEncoder().encodeToString(cipher_bytes2));
	System.out.println("---------------------------------");
	
	cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(iv));
	byte[] encrypted_bytes = cipher.doFinal(cipher_bytes);
	System.out.println("\tEncrypted-Binär: "+toBinaryString(encrypted_bytes));
	System.out.println("\tEncrypted-Bytes: "+Arrays.toString(encrypted_bytes));
	System.out.println("\tEncrypted-Base64: "+Base64.getEncoder().encodeToString(encrypted_bytes));
	cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(iv2));
	byte[] encrypted_bytes2 = cipher.doFinal(cipher_bytes2);
	System.out.println("\tEncrypted2-Binär: "+toBinaryString(encrypted_bytes2));
	System.out.println("\tEncrypted2-Bytes: "+Arrays.toString(encrypted_bytes2));
	System.out.println("\tEncrypted2-Base64: "+Base64.getEncoder().encodeToString(encrypted_bytes2));
	
    }
    
    private static String toHexString(byte b) {
	return String.format("%02x", b);
    }
    
    private static String toHexString(byte[] bytes) {
	
	if(bytes==null)
	    return null;
	if(bytes.length==0)
	    return toHexString(new byte[]{0});
	
	int iMax = bytes.length-1;
	StringBuilder sb = new StringBuilder();
	for(int i=0; ;i++)
	{
	    sb.append(toHexString(bytes[i]));
	    if(iMax==i)
		return sb.toString();
//	    sb.append(" ");
	}
    }
    
    private static String toBinaryString(byte b) {	
	// & 0xFF muss sein, da sonst bei zB 129 Überlauf entsteht
	// replace(" ","0") muss sein, da anführende 0en sonst ignoriert werden
	return String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(" ", "0");
    }
    
    private static String toBinaryString(byte[] bytes) {
	
	if(bytes == null)
	    return null;
	if(bytes.length==0)
	    return toBinaryString(new byte[]{0});
	
	int iMax = bytes.length-1;
	StringBuilder sb = new StringBuilder();
	for(int i=0; ;i++)
	{
	    sb.append(toBinaryString(bytes[i]));
	    if(i == iMax)
		return sb.toString();
	    sb.append(".");
	}
    }

    public void dosmth() throws SQLException {
	PreparedStatement preparedStatement = null;
	preparedStatement.setString(1, "");

	setString(preparedStatement, 2, null);

    }

    public void setString(PreparedStatement ps, int parameterIndex, String str)
	    throws SQLException {
	if (str.equalsIgnoreCase("NULL"))
	    ps.setNull(parameterIndex, java.sql.Types.INTEGER);
	else
	    ps.setString(parameterIndex, str);
    }

    public interface A {

	public void hello();

    }

    public class B implements A {

	/**
	 * Hello this is Dog
	 */
	@Override
	public void hello() {
	    // TODO Auto-generated method stub

	}

    }

}
