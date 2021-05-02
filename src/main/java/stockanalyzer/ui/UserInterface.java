package stockanalyzer.ui;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

import stockanalyzer.ctrl.Controller;

public class UserInterface 
{

	private Controller ctrl = new Controller();

	public void getDataFromCtrl1() {
		try {
			for (String s : ctrl.process("AAPL")) {
				System.out.println(s);
			}

		} catch (IOException e) {
			System.out.println("A problem occured. Please try again");
		}
	}

	public void getDataFromCtrl2(){
			try {
				for (String s : ctrl.process("AMZN")){
					System.out.println(s);
				}

			} catch (IOException e) {
				System.out.println("A problem occured. Please try again");
			}
		}


	public void getDataFromCtrl3(){
		try {
			for (String s : ctrl.process("MC.PA")){
				System.out.println(s);
			}

		} catch (IOException e) {
			System.out.println("A problem occured. Please try again");
		}

	}
	public void getDataFromCtrl4(){
		try {
			for (String s : ctrl.process("TSLA")){
				System.out.println(s);
			}

		} catch (IOException e) {
			System.out.println("A problem occured. Please try again");
		}

	}
	
	public void getDataForCustomInput() {

		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter Ticker");
		String a = scanner.nextLine();

		try {
			for (String s : ctrl.process(a)) {
				System.out.println(s);
			}
		} catch (IOException e) {
			System.out.println("A problem occured. Please try again");
		}
		
	}


	public void start() {
		Menu<Runnable> menu = new Menu<>("User Interfacx");
		menu.setTitel("Wählen Sie aus:");
		menu.insert("a", "APPLE 	(AAPL)  Data:", this::getDataFromCtrl1);
		menu.insert("b", "AMAZON 	(AMZN)  Data:", this::getDataFromCtrl2);
		menu.insert("c", "MOET 	(MC.PA) Data:", this::getDataFromCtrl3);
		menu.insert("d", "TESLA 	(TSLA)  Data:",this::getDataFromCtrl4);
		menu.insert("z", "Choice User Input:",this::getDataForCustomInput);
		menu.insert("q", "Quit:", null);
		Runnable choice;
		while ((choice = menu.exec()) != null) {
			 choice.run();
		}
		System.out.println("Program finished");
	}


	protected String readLine() 
	{
		String value = "\0";
		BufferedReader inReader = new BufferedReader(new InputStreamReader(System.in));
		try {
			value = inReader.readLine();
		} catch (IOException e) {
		}
		return value.trim();
	}

	protected Double readDouble(int lowerlimit, int upperlimit) 
	{
		Double number = null;
		while(number == null) {
			String str = this.readLine();
			try {
				number = Double.parseDouble(str);
			}catch(NumberFormatException e) {
				number=null;
				System.out.println("Please enter a valid number:");
				continue;
			}
			if(number<lowerlimit) {
				System.out.println("Please enter a higher number:");
				number=null;
			}else if(number>upperlimit) {
				System.out.println("Please enter a lower number:");
				number=null;
			}
		}
		return number;
	}
}
