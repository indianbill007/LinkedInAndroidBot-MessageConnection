package com.example.startapp;

import io.selendroid.client.SelendroidDriver;
import io.selendroid.client.SelendroidKeys;
import io.selendroid.common.SelendroidCapabilities;
import io.selendroid.standalone.SelendroidConfiguration;
import io.selendroid.standalone.SelendroidLauncher;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.touch.TouchActions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.example.model.DataModel;
import com.example.utills.ConstantID;
import com.example.utills.Utilss;

public class StartApp {

	static boolean isSearchPresent;
	private static SelendroidLauncher selendroidServer = null;
	private static WebDriver driver = null;
	static WebElement add_button, profile_button;

	// static String csvfilepath = "assets/data.csv";
	static String notefilepath = "assets/data.txt";
	static String bodyfilepath = "assets/links.txt";
	static ArrayList<DataModel> datalist = new ArrayList<DataModel>();

	static ArrayList<DataModel> bodylist = new ArrayList<DataModel>();

	static int invite_present_count = 0;
	static int profile_visit_count = 0;
	static int invite_not_present_count = 0;
	static int csv_index = 0;
	static int bodydata_index = 0;
	static WebElement search_field_editbox;

	static String groupusercsv = "assets/groupname.txt";
	static ArrayList<String> name_list = new ArrayList<String>();

	public static void main(String[] args) throws Exception {

		// Get all the keyword from excel to runtime list

		// if data is in csv
		// Utilss.getExceldata(csvfilepath, datalist);

		// if data is in notepad
		Utilss.getDataFromNotepad(notefilepath, datalist);
		Utilss.getDataFromNotepad(bodyfilepath, bodylist);
		SelendroidConfiguration config = new SelendroidConfiguration();
		config.addSupportedApp("apk/linkedin.apk");
		config.setSessionTimeoutSeconds(60 * 60 * 24); // one day, change to
														// what you want it to
														// be.

		selendroidServer = new SelendroidLauncher(config);

		selendroidServer.launchSelendroid();

		URL url = new URL("http://localhost:4444/wd/hub");
		// -------------------------------------------------------
		SelendroidCapabilities linkedin = SelendroidCapabilities
				.device("com.linkedin.android:4.0.20");

		driver = new SelendroidDriver(url, linkedin);

		try {

			if (ConstantID.Code == 1) {
			} else if (ConstantID.Code == 4) {

				// click on back button
				Thread.sleep(5000);
				driver.findElement(By.name(ConstantID.Relationship_btn_name))
						.click();
				// clcik on connection
				Thread.sleep(10000);
				driver.findElement(By.id(ConstantID.Relationship_contact_id))
						.click();
				Thread.sleep(5000);
				driver.findElement(By.xpath(ConstantID.connection_xpath_name))
						.click();

				// wait for 20 sec
				Thread.sleep(10000);

				for (int i = 0; i < ConstantID.iterate_time; i++) {

					Message_fixed_row(ConstantID.subject);

					Thread.sleep(10000);

					Scroll();
					Thread.sleep(10000);
					Scroll();

					if (bodydata_index >= bodylist.size()) {
						System.out.println(">>>>>> END <<<<<");
						break;
					}
				}

			}

		} catch (Exception e) {
			isSearchPresent = false;

			e.printStackTrace();

		}

		if (isSearchPresent == false) {
			try {
				Thread.sleep(5000);
				driver.findElement(By.id(ConstantID.SignIn_ID)).click();
				Thread.sleep(5000);
				driver.findElement(By.id(ConstantID.Username_ID)).sendKeys(
						ConstantID.UserName);
				driver.findElement(By.id(ConstantID.Password_ID)).sendKeys(
						ConstantID.PassWord);

				driver.findElement(By.id(ConstantID.Button_SignIN_ID)).click();

				Thread.sleep(5000);
				driver.findElement(By.xpath(ConstantID.Action_bar_Xpath))
						.click();

				// new Actions(driver).sendKeys(SelendroidKeys.BACK).perform();
				// new Actions(driver).sendKeys(SelendroidKeys.BACK).perform();

				driver.findElement(By.id(ConstantID.Button_search)).click();
				new Actions(driver).sendKeys(SelendroidKeys.BACK).perform();
				new Actions(driver).sendKeys(SelendroidKeys.BACK).perform();
			} catch (Exception e) {
				// TODO: handle exception
			}

		}

	}

	public static void Scroll() {

		System.out.println("scroll>>");
		WebElement list = driver.findElement(By
				.id("relationships_connections_list"));
		TouchActions flick = new TouchActions(driver).flick(list, 0, -200, 0);
		flick.perform();

	}

	public static void Set_Search_keyword(String keyword, WebDriver driver)

	{
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		search_field_editbox = driver.findElement(By
				.id(ConstantID.Search_Editbox_ID));
		search_field_editbox.sendKeys(keyword);
	}

	public static boolean isElementPresent(By by) {
		try {
			driver.findElement(by);
			return true;
		} catch (Exception e) {

			return false;
		}
	}

	public static void Invite_User() {
		for (int i = 1; i <= ConstantID.list_size; i++) {

			String button_xpath = "//ListView/RelativeLayout[" + i
					+ "]/FrameLayout/AnimatedActionImageView";
			if (isElementPresent(By.xpath(button_xpath))) {

				add_button = driver.findElement(By.xpath(button_xpath));

				if (add_button.isDisplayed() == true) {
					add_button.click();
					invite_present_count++;

					System.out.println("Total Invite Count="
							+ invite_present_count);
					invite_not_present_count = 0;
				} else {
					invite_not_present_count++;
				}

				if (invite_not_present_count > 500) {
					invite_not_present_count = 0;
					System.out.println("inside false greater > 500");
					csv_index++;
					if (csv_index < datalist.size()) {
						search_field_editbox.clear();
						Set_Search_keyword(
								datalist.get(csv_index).getKeyword(), driver);
						try {
							Thread.sleep(5000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

				}

			} else {

				System.out.println("Inside Profile click");
				new Actions(driver).sendKeys(SelendroidKeys.BACK).perform();
			}

		}
	}

	public static void AcceptInviationRequest() throws InterruptedException {
		Thread.sleep(2000);
		driver.findElement(By.id(ConstantID.back_btn_ID)).click();
		driver.findElement(By.id(ConstantID.back_btn_ID)).click();
		driver.findElement(By.id(ConstantID.list_user_invite_btn_ID)).click();

		// Thread.sleep(60000);
		new WebDriverWait(driver, 100).until(ExpectedConditions
				.presenceOfElementLocated(By.id(ConstantID.picture_ID)));

		if (isElementPresent(By.id(ConstantID.picture_ID))) {
			System.out.println("button found");
			for (int i = 0; i < 50; i++) {

				Thread.sleep(3000);
				driver.findElement(By.id(ConstantID.InviteAcceptButton))
						.click();
			}

		}

		else {
			System.out.println("invitation not found");
		}
	}

	public static void VisitProfile() throws InterruptedException {
		Set_Search_keyword(datalist.get(0).getKeyword(), driver);

		Thread.sleep(5000);

		// one element width= 50dp

		System.out.println(">>>>>> Iterater is initialised <<<<<");

		for (int i = 0; i < ConstantID.profile_visit_count_for_each_keyword; i++) {

			ViewProfile();

			Thread.sleep(2000);

			Scroll();

			if (csv_index >= datalist.size()) {
				System.out.println(">>>>>> END <<<<<");
				break;
			}
		}
	}

	public static void ViewProfile() throws InterruptedException {

		for (int i = 1; i <= ConstantID.list_size; i++) {

			String button_xpath = "//ListView/RelativeLayout[" + i + "]";
			if (isElementPresent(By.xpath(button_xpath))) {

				profile_button = driver.findElement(By.xpath(button_xpath));

				profile_button.click();

				profile_visit_count++;
				System.out.println("Total Profile visit Count="
						+ profile_visit_count);
				Thread.sleep(5000);
				new Actions(driver).sendKeys(SelendroidKeys.BACK).perform();

				Thread.sleep(2000);

				if (profile_visit_count > 500) {

					csv_index++;
					if (csv_index < datalist.size()) {
						search_field_editbox.clear();
						Set_Search_keyword(
								datalist.get(csv_index).getKeyword(), driver);
						try {
							Thread.sleep(5000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

				}

			}

		}

	}

/*	public static void Message(String subject) throws InterruptedException {

		for (int i = 1; i <= ConstantID.msg_list_size; i++) {

			String body_part = bodylist.get(bodydata_index).getKeyword();

			String rowxpath = "//RelativeLayout[@id='connections_row'][" + i
					+ "]";

			Thread.sleep(2000);
			driver.findElement(By.xpath(rowxpath)).click();

			Thread.sleep(10000);
			driver.findElement(By.xpath(ConstantID.message_xpath)).click();

			// ------------------------------------
			Thread.sleep(2000);
			driver.findElement(By.id(ConstantID.subject_id)).sendKeys(
					ConstantID.subject);
			String body_field1 = Utilss.getbody1(body_part,
					ConstantID.name_signature);
			driver.findElement(By.id(ConstantID.body_id)).sendKeys(body_field1);

			driver.findElement(By.id(ConstantID.sendbutton_id)).click();

			// ----------------------------------------------

			Thread.sleep(5000);

			driver.findElement(By.xpath(ConstantID.message_xpath)).click();
			driver.findElement(By.id(ConstantID.subject_id)).sendKeys(
					ConstantID.subject);

			driver.findElement(By.id(ConstantID.body_id)).sendKeys(
					Utilss.getbody2());

			driver.findElement(By.id(ConstantID.sendbutton_id)).click();
			// ----------------------------------------------------

			Thread.sleep(5000);
			driver.findElement(By.xpath(ConstantID.message_xpath)).click();
			driver.findElement(By.id(ConstantID.subject_id)).sendKeys(
					ConstantID.subject);

			driver.findElement(By.id(ConstantID.body_id)).sendKeys(
					Utilss.getbody2(body_part));

			driver.findElement(By.id(ConstantID.sendbutton_id)).click();

			// --------------------------------------------------------

			Thread.sleep(5000);

			driver.findElement(By.xpath(ConstantID.message_xpath)).click();
			driver.findElement(By.id(ConstantID.subject_id)).sendKeys(
					ConstantID.subject);

			driver.findElement(By.id(ConstantID.body_id)).sendKeys(
					Utilss.getbody4(ConstantID.name_signature));

			driver.findElement(By.id(ConstantID.sendbutton_id)).click();
			// ------------------------------------------------------------------------------

			Thread.sleep(2000);

			new Actions(driver).sendKeys(SelendroidKeys.BACK).perform();

			bodydata_index++;

			if (bodydata_index >= bodylist.size()) {
				bodydata_index = 0;
			}
		}

	}*/

	//
	public static void Message_fixed_row(String subject)
			throws InterruptedException, IOException {

		for (int i = 2; i < ConstantID.msg_list_size; i++) {

			String body_part = bodylist.get(bodydata_index).getKeyword();
			name_list.clear();
			Utilss.getPeopleNameFromNotepad(groupusercsv, name_list);

			String rowxpath = "//RecyclerView[@id='relationships_connections_list']/LinearLayout["
					+ i + "]";
			String namepath = "//RecyclerView[@id='relationships_connections_list']/LinearLayout["
					+ i
					+ "]/LinearLayout/LinearLayout/LinearLayout/AppCompatTextView[1]";

			String name = driver.findElement(By.xpath(namepath)).getText()
					.toString().replace("?", "").trim();

			if (!name_list.contains(name)) {

				Utilss.writeDataInText(groupusercsv, name.replace("?", "")
						.trim());

				System.out.println("rowxpath" + rowxpath);
				Thread.sleep(4000);
				driver.findElement(By.xpath(rowxpath)).click();
				Thread.sleep(4000);
				driver.findElement(By.id(ConstantID.send_msg_popup_id)).click();

				Thread.sleep(2000);
				driver.findElement(By.id(ConstantID.mesg_input)).sendKeys(
						Utilss.getbody4(body_part, name));

				driver.findElement(By.id(ConstantID.mesg_send)).click();

				Thread.sleep(4000);
				new Actions(driver).sendKeys(SelendroidKeys.BACK).perform();
				Thread.sleep(2000);
				new Actions(driver).sendKeys(SelendroidKeys.BACK).perform();

				bodydata_index++;

				if (bodydata_index >= bodylist.size()) {
					bodydata_index = 0;
				}
			} else {
				System.out.println("messege  already sent Hell Yeah");
			}

		}
	}
}
