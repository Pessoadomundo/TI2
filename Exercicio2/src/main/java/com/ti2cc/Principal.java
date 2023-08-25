package com.ti2cc;

import java.util.Scanner;

public class Principal {
	
	public static void main(String[] args) {
		DAO dao = new DAO();
		Scanner sc = new Scanner(System.in);

		dao.conectar();


		System.out.println("Escolha uma opção:");
		System.out.println("1- Inserir elemento");
		System.out.println("2- Mostrar elemento");
		System.out.println("3- Atualizar elemento");
		System.out.println("4- Excluir elemento");
		System.out.println("5- Sair");
		int opcao = sc.nextInt();

		while(opcao!=5) {
			System.out.println("Escolha uma opção:");
			System.out.println("1- Inserir elemento");
			System.out.println("2- Mostrar elemento");
			System.out.println("3- Atualizar elemento");
			System.out.println("4- Excluir elemento");
			System.out.println("5- Sair");
			opcao = sc.nextInt();
			if(opcao==1){
				System.out.println("Digite o ID:");
				int ID = sc.nextInt();
				System.out.println("Digite o nome:");
				String nome = sc.next();
				System.out.println("Digite a altura:");
				int altura = sc.nextInt();
				System.out.println("Digite o peso:");
				int peso = sc.nextInt();
				X elemento = new X(ID, nome, altura, peso);
				if(dao.inserirElemento(elemento) == true) {
					System.out.println("Inserção com sucesso -> " + elemento.toString());
				}
			}
			if(opcao==2){
				System.out.println("Digite o ID:");
				int ID = sc.nextInt();
				X elemento = dao.getElemento(ID);
				System.out.println(elemento.toString());
			}
			if(opcao==3){
				System.out.println("Digite o ID:");
				int ID = sc.nextInt();
				System.out.println("Digite o novo peso:");
				int peso = sc.nextInt();
				X elemento = dao.getElemento(ID);
				elemento.setPeso(peso);
				dao.atualizarElemento(elemento);
			}
			if(opcao==4){
				System.out.println("Digite o ID:");
				int ID = sc.nextInt();
				dao.excluirElemento(ID);
			}
		}
		dao.close();
	}
}
