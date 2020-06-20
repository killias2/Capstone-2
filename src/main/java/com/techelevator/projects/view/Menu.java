package com.techelevator.projects.view;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

public class Menu {

	//holds printwriter and scanner logic
	//does the try/catch loops to check user input
	
	private PrintWriter out;
	private Scanner in;
	
	
	public Menu(InputStream input, OutputStream output) {
		this.out = new PrintWriter(output);
		this.in = new Scanner(input);
	}
	
	public getUserInput() {
		
	}
}
