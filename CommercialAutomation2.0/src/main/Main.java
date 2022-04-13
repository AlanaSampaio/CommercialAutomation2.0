package main;

import java.util.Scanner;

public class Main {
	public static void main(String args[]) {
		Scanner input = new Scanner(System.in);
		
		int n = 0;
		while (n == 0) {
			System.out.println("1. Acessar login \n2. Cadastrar login \n3. Sair");
			int answer = input.nextInt();
			
			switch(answer) {
			case 3:
				System.out.println("Saindo...");
				break;
			case 2:
				System.out.println("Cadastrando...");
			case 1:
				System.out.println("Cadastrando..");
			}
		}
	}

}
