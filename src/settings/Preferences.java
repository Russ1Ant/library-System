package settings;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import org.apache.commons.codec.digest.DigestUtils;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

public class Preferences {
	public static final String CONFIG_FILE = "config.txt";
	
	
	int daysCountWithoutFine;
	public int getDaysCountWithoutFine() {
		return daysCountWithoutFine;
	}

	public void setDaysCountWithoutFine(int daysCountWithoutFine) {
		this.daysCountWithoutFine = daysCountWithoutFine;
	}

	public float getFinePerDay() {
		return finePerDay;
	}

	public void setFinePerDay(float finePerDay) {
		this.finePerDay = finePerDay;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
 
		this.password = DigestUtils.sha1Hex(password);
	}

	float finePerDay;
	String username;
	String password;
	
	public Preferences() {
		daysCountWithoutFine = 7;
		finePerDay = 50;
		username = "admin";
		setPassword("admin");
		
	}
	
public static void initConfig() {
	Writer writer = null;
	Preferences preference = new Preferences();
	Gson gson = new Gson();
	try {
		writer = new FileWriter(CONFIG_FILE);
		gson.toJson(preference, writer);
		
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}finally {
		try {
			writer.close();
		}catch(IOException e1) {
			e1.printStackTrace();
			
		}
	}}
public static Preferences getPreferences() {
	Gson gson = new Gson();
	Preferences  preferences = new Preferences();
	try {
		preferences = gson.fromJson(new FileReader(CONFIG_FILE), Preferences.class);
	} catch (FileNotFoundException e) {
		initConfig();
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (JsonIOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return preferences;
	
}
public static void writePrefToFile(Preferences preference ) {
	Writer writer = null;
	
	Gson gson = new Gson();
	try {
		writer = new FileWriter(CONFIG_FILE);
		gson.toJson(preference, writer);
		
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}finally {
		try {
			writer.close();
		}catch(IOException e1) {
			e1.printStackTrace();
			
		}
	}}}