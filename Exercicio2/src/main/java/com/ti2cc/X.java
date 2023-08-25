package com.ti2cc;

public class X {
	private int ID;
	private String nome;
	private int altura;
	private int peso;
	
	public X() {
		this.ID = -1;
		this.nome = "";
		this.altura = 0;
		this.peso = 0;
	}
	
	public X(int ID, String nome, int altura, int peso) {
		this.ID = ID;
		this.nome = nome;
		this.altura = altura;
		this.peso = peso;
	}

	public int getID() {
		return ID;
	}

	public void setID(int ID) {
		this.ID = ID;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getAltura() {
		return altura;
	}

	public void setAltura(int altura) {
		this.altura = altura;
	}

	public int getPeso() {
		return peso;
	}

	public void setPeso(int peso) {
		this.peso = peso;
	}

	@Override
	public String toString() {
		return "X [ID=" + ID + ", nome=" + nome + ", altura=" + altura + ", peso=" + peso + "]";
	}	
}