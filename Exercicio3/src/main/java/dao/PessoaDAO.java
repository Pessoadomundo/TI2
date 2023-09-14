package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import model.Pessoa;

public class PessoaDAO {
	private Connection conexao;

	public PessoaDAO() {
		conexao = null;
	}

	public boolean conectar() {
		String driverName = "org.postgresql.Driver";                    
		String serverName = "localhost";
		String mydatabase = "teste";
		int porta = 5432;
		String url = "jdbc:postgresql://" + serverName + ":" + porta +"/" + mydatabase;
		String username = "postgres";
		String password = "Gabri8448";
		boolean status = false;

		try {
			Class.forName(driverName);
			conexao = DriverManager.getConnection(url, username, password);
			status = (conexao == null);
			System.out.println("Conexão efetuada com o postgres!");
		} catch (ClassNotFoundException e) { 
			System.err.println("Conexão NÃO efetuada com o postgres -- Driver não encontrado -- " + e.getMessage());
		} catch (SQLException e) {
			System.err.println("Conexão NÃO efetuada com o postgres -- " + e.getMessage());
		}

		return status;
	}

	public boolean close() {
		boolean status = false;
		try {
			conexao.close();
			status = true;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return status;
	}

	public boolean inserirElemento(Pessoa elemento) {
		boolean status = false;
		try {
			Statement st = conexao.createStatement();
			st.executeUpdate("INSERT INTO X (ID, nome, altura, peso) " +
					"VALUES (" + elemento.getID() + ", '" + elemento.getNome() + "', " +
					elemento.getAltura() + ", " + elemento.getPeso() + ");");
			st.close();
			status = true;
		} catch (SQLException u) {
			throw new RuntimeException(u);
		}
		return status;
	}

	public boolean atualizarElemento(Pessoa elemento) {
		boolean status = false;
		try {
			Statement st = conexao.createStatement();
			String sql = "UPDATE X SET nome = '" + elemento.getNome() + "', altura = " +
					elemento.getAltura() + ", peso = " + elemento.getPeso() +
					" WHERE ID = " + elemento.getID();
			st.executeUpdate(sql);
			st.close();
			status = true;
		} catch (SQLException u) {
			throw new RuntimeException(u);
		}
		return status;
	}

	public boolean excluirElemento(int ID) {
		boolean status = false;
		try {
			Statement st = conexao.createStatement();
			st.executeUpdate("DELETE FROM X WHERE ID = " + ID);
			st.close();
			status = true;
		} catch (SQLException u) {
			throw new RuntimeException(u);
		}
		return status;
	}

	public Pessoa[] getElementos() {
		Pessoa[] elementos = null;

		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = st.executeQuery("SELECT * FROM X");
			if (rs.next()) {
				rs.last();
				elementos = new Pessoa[rs.getRow()];
				rs.beforeFirst();

				for (int i = 0; rs.next(); i++) {
					elementos[i] = new Pessoa(rs.getInt("ID"), rs.getString("nome"),
							rs.getInt("altura"), rs.getInt("peso"));
				}
			}
			st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return elementos;
	}
	public Pessoa getElemento(int ID) {
		Pessoa elemento = null;
		try{
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = st.executeQuery("SELECT * FROM X WHERE ID = " + ID);
			if(rs.next()){
				elemento = new Pessoa(rs.getInt("ID"), rs.getString("nome"), rs.getInt("altura"), rs.getInt("peso"));
			}
		}catch(Exception e){
			System.err.println(e.getMessage());
		}
		return elemento;
	}
	private List<Pessoa> get(String orderBy) {
		List<Pessoa> pessoas = new ArrayList<Pessoa>();
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM pessoa" + ((orderBy.trim().length() == 0) ? "" : (" ORDER BY " + orderBy));
			ResultSet rs = st.executeQuery(sql);	           
	        while(rs.next()) {	            	
	        	Pessoa p = new Pessoa(rs.getInt("id"), rs.getString("nome"), rs.getInt("altura"), rs.getInt("peso"));
	            pessoas.add(p);
	        }
	        st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return pessoas;
	}

	public List<Pessoa> get() {
		return get("");
	}

	
	public List<Pessoa> getOrderByID() {
		return get("id");		
	}
	
	
	public List<Pessoa> getOrderByNome() {
		return get("nome");		
	}
	
	
	public List<Pessoa> getOrderByAltura() {
		return get("altura");		
	}
}
