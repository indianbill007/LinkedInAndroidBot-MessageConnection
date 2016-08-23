package com.example.utills;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import au.com.bytecode.opencsv.CSVReader;

import com.example.model.DataModel;

public class Utilss {

	// get data from excel sheet
	public static void getExceldata(String CSV_location_path,
			ArrayList<DataModel> arrayList) throws IOException {
		CSVReader csvReader = new CSVReader(new FileReader(CSV_location_path));
		String[] row = null;
		while ((row = csvReader.readNext()) != null) {

			String keywords = row[0].trim();
			System.out.println(keywords);

			DataModel model = new DataModel();

			model.setKeyword(keywords);
			arrayList.add(model);

		}
		// ...

		csvReader.close();

	}

	public static void getPeopleNameFromNotepad(String note_file_path,
			ArrayList<String> arrayList) throws IOException {
		String keywords;
		FileReader fr = new FileReader(note_file_path);
		BufferedReader br = new BufferedReader(fr);
		// /read line from the file upto null
		while ((keywords = br.readLine()) != null) {

			arrayList.add(keywords);
			System.out.println("From TextFile=" + keywords);
		}
		br.close();
	}

	public static void writeDataInText(String path, String data)
			throws FileNotFoundException {
		try {

			File file = new File(path);

			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}

			FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write("\r\n");
			bw.write(data);

			bw.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void getDataFromNotepad(String note_file_path,
			ArrayList<DataModel> arrayList) throws IOException {
		String keywords;
		FileReader fr = new FileReader(note_file_path);
		BufferedReader br = new BufferedReader(fr);
		// /read line from the file upto null
		while ((keywords = br.readLine()) != null) {
			DataModel model = new DataModel();
			model.setKeyword(keywords);
			arrayList.add(model);
			System.out.println("From TextFile=" + keywords);
		}
		br.close();
	}

	public static int randInt(int min, int max) {
		Random rand = new Random();
		int randomNum = rand.nextInt((max - min) + 1) + min;

		return randomNum;
	}

	public static String getbody1(String bodypart, String name) {

		// String body1="Hello";
		String body1 = "Hello, I am a recruiter at Amazon and I was checking your profile and it seems that its apt for the job role am hiring for. I am presently hiring for programmers, program managers, content creators, SEO professionals and multiple other roles at Amazon.";

		System.out.println("body  " + body1);

		return body1;

	}

	public static String getbody2() {

		// String body1="Hello";
		String body1 = "Would you be interested to work for Amazon. If yes please create your profile in our HRMS site - and apply with job code - 45965 ";

		return body1;

	}

	

	public static String getbody3(String link, String name) {

		String SingleMsg = "Hello, n I am Hiring for walmart,and your profile looks Perfact for some job roles.presently im hiring for Recruiters & programmers.\n are you interested to work for Walmart. \n If yes please create your profile in our site - "
				+ link + " and apply  \n Thank you  ";
		return SingleMsg;

	}

	public static String getbody4(String link, String name) {

		String SingleMsg = "Hi,\n"
				+ name
				+ "\n I am working at Walmart as a recruiter and we are hiring for various positions. Current openings include Programmers, Program Managers, Content Creators, SEO Professionals primarily with multiple other roles.\nWhile browsing relevant profiles, I came across yours and found it suitable for one of the job openings. If you are interested to work for Walmart, just sign up at our HRMS website -  "
				+ link + " \nand apply with updated resume.\nBest Regards\n Kathryn ";
		return SingleMsg;

	}

}
