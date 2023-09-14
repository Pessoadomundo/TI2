package service;

import java.util.Scanner;
import java.io.File;
import java.util.List;
import dao.PessoaDAO;
import model.Pessoa;
import spark.Request;
import spark.Response;


public class PessoaService {

	private PessoaDAO pessoaDAO = new PessoaDAO();
	private String form;
	private final int FORM_INSERT = 1;
	private final int FORM_DETAIL = 2;
	private final int FORM_UPDATE = 3;
	private final int FORM_ORDERBY_ID = 1;
	private final int FORM_ORDERBY_NOME = 2;
	private final int FORM_ORDERBY_ALTURA = 3;
	
	
	public PessoaService() {
		makeForm();
	}

	
	public void makeForm() {
		makeForm(FORM_INSERT, new Pessoa(), FORM_ORDERBY_NOME);
	}

	
	public void makeForm(int orderBy) {
		makeForm(FORM_INSERT, new Pessoa(), orderBy);
	}

	
	public void makeForm(int tipo, Pessoa pessoa, int orderBy) {
		String nomeArquivo = "form.html";
		form = "";
		try{
			Scanner entrada = new Scanner(new File(nomeArquivo));
		    while(entrada.hasNext()){
		    	form += (entrada.nextLine() + "\n");
		    }
		    entrada.close();
		}  catch (Exception e) { System.out.println(e.getMessage()); }
		
		String umPessoa = "";
		if(tipo != FORM_INSERT) {
			umPessoa += "\t<table width=\"80%\" bgcolor=\"#f3f3f3\" align=\"center\">";
			umPessoa += "\t\t<tr>";
			umPessoa += "\t\t\t<td align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;<a href=\"/pessoa/list/1\">Novo Pessoa</a></b></font></td>";
			umPessoa += "\t\t</tr>";
			umPessoa += "\t</table>";
			umPessoa += "\t<br>";
		}
		
		if(tipo == FORM_INSERT || tipo == FORM_UPDATE) {
			String action = "/pessoa/";
			String name, nome, buttonLabel;
			if (tipo == FORM_INSERT){
				action += "insert";
				name = "Inserir Pessoa";
				nome = "digite seu nome";
				buttonLabel = "Inserir";
			} else {
				action += "update/" + pessoa.getID();
				name = "Atualizar Pessoa (ID " + pessoa.getID() + ")";
				nome = pessoa.getNome();
				buttonLabel = "Atualizar";
			}
			umPessoa += "\t<form class=\"form--register\" action=\"" + action + "\" method=\"post\" id=\"form-add\">";
			umPessoa += "\t<table width=\"80%\" bgcolor=\"#f3f3f3\" align=\"center\">";
			umPessoa += "\t\t<tr>";
			umPessoa += "\t\t\t<td colspan=\"3\" align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;" + name + "</b></font></td>";
			umPessoa += "\t\t</tr>";
			umPessoa += "\t\t<tr>";
			umPessoa += "\t\t\t<td colspan=\"3\" align=\"left\">&nbsp;</td>";
			umPessoa += "\t\t</tr>";
			umPessoa += "\t\t<tr>";
			umPessoa += "\t\t\t<td>&nbsp;Nome: <input class=\"input--register\" type=\"text\" name=\"nome\" value=\""+ nome +"\"></td>";
			umPessoa += "\t\t\t<td>Altura: <input class=\"input--register\" type=\"number\" name=\"altura\" value=\""+ pessoa.getAltura() +"\"></td>";
			umPessoa += "\t\t\t<td>Peso: <input class=\"input--register\" type=\"number\" name=\"peso\" value=\""+ pessoa.getPeso() +"\"></td>";
			umPessoa += "\t\t</tr>";
			umPessoa += "\t\t<tr>";
			umPessoa += "\t\t\t<td align=\"center\"><input type=\"submit\" value=\""+ buttonLabel +"\" class=\"input--main__style input--button\"></td>";
			umPessoa += "\t\t</tr>";
			umPessoa += "\t</table>";
			umPessoa += "\t</form>";		
		} else if (tipo == FORM_DETAIL){
			umPessoa += "\t<table width=\"80%\" bgcolor=\"#f3f3f3\" align=\"center\">";
			umPessoa += "\t\t<tr>";
			umPessoa += "\t\t\t<td colspan=\"3\" align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;Detalhar Pessoa (ID " + pessoa.getID() + ")</b></font></td>";
			umPessoa += "\t\t</tr>";
			umPessoa += "\t\t<tr>";
			umPessoa += "\t\t\t<td colspan=\"3\" align=\"left\">&nbsp;</td>";
			umPessoa += "\t\t</tr>";
			umPessoa += "\t\t<tr>";
			umPessoa += "\t\t\t<td>&nbsp;Nome: "+ pessoa.getNome() +"</td>";
			umPessoa += "\t\t\t<td>Altura: "+ pessoa.getAltura() +"</td>";
			umPessoa += "\t\t\t<td>Peso: "+ pessoa.getPeso() +"</td>";
			umPessoa += "\t\t</tr>";
			umPessoa += "\t</table>";		
		} else {
			System.out.println("ERRO! Tipo não identificado " + tipo);
		}
		form = form.replaceFirst("<UM-Pessoa>", umPessoa);
		
		String list = new String("<table width=\"80%\" align=\"center\" bgcolor=\"#f3f3f3\">");
		list += "\n<tr><td colspan=\"6\" align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;Relação de Pessoas</b></font></td></tr>\n" +
				"\n<tr><td colspan=\"6\">&nbsp;</td></tr>\n" +
    			"\n<tr>\n" + 
        		"\t<td><a href=\"/pessoa/list/" + FORM_ORDERBY_ID + "\"><b>ID</b></a></td>\n" +
        		"\t<td><a href=\"/pessoa/list/" + FORM_ORDERBY_NOME + "\"><b>Nome</b></a></td>\n" +
        		"\t<td><a href=\"/pessoa/list/" + FORM_ORDERBY_ALTURA + "\"><b>Altura</b></a></td>\n" +
        		"\t<td width=\"100\" align=\"center\"><b>Detalhar</b></td>\n" +
        		"\t<td width=\"100\" align=\"center\"><b>Atualizar</b></td>\n" +
        		"\t<td width=\"100\" align=\"center\"><b>Excluir</b></td>\n" +
        		"</tr>\n";
		
		List<Pessoa> pessoas;
		if (orderBy == FORM_ORDERBY_ID) {                 	pessoas = pessoaDAO.getOrderByID();
		} else if (orderBy == FORM_ORDERBY_NOME) {		pessoas = pessoaDAO.getOrderByNome();
		} else if (orderBy == FORM_ORDERBY_ALTURA) {			pessoas = pessoaDAO.getOrderByAltura();
		} else {											pessoas = pessoaDAO.get();
		}

		int i = 0;
		String bgcolor = "";
		for (Pessoa p : pessoas) {
			bgcolor = (i++ % 2 == 0) ? "#fff5dd" : "#dddddd";
			list += "\n<tr bgcolor=\""+ bgcolor +"\">\n" + 
            		  "\t<td>" + p.getID() + "</td>\n" +
            		  "\t<td>" + p.getNome() + "</td>\n" +
            		  "\t<td>" + p.getAltura() + "</td>\n" +
            		  "\t<td align=\"center\" valign=\"middle\"><a href=\"/pessoa/" + p.getID() + "\"><img src=\"/image/detail.png\" width=\"20\" height=\"20\"/></a></td>\n" +
            		  "\t<td align=\"center\" valign=\"middle\"><a href=\"/pessoa/update/" + p.getID() + "\"><img src=\"/image/update.png\" width=\"20\" height=\"20\"/></a></td>\n" +
            		  "\t<td align=\"center\" valign=\"middle\"><a href=\"javascript:confirmarDeletePessoa('" + p.getID() + "', '" + p.getNome() + "', '" + p.getAltura() + "');\"><img src=\"/image/delete.png\" width=\"20\" height=\"20\"/></a></td>\n" +
            		  "</tr>\n";
		}
		list += "</table>";		
		form = form.replaceFirst("<LISTAR-Pessoa>", list);				
	}
	
	
	public Object insert(Request request, Response response) {
		String nome = request.queryParams("nome");
		int altura = Integer.parseInt(request.queryParams("altura"));
		int peso = Integer.parseInt(request.queryParams("peso"));
		
		String resp = "";
		
		Pessoa pessoa = new Pessoa(-1, nome, altura, peso);
		
		if(pessoaDAO.inserirElemento(pessoa) == true) {
            resp = "Pessoa (" + nome + ") inserido!";
            response.status(201); // 201 Created
		} else {
			resp = "Pessoa (" + nome + ") não inserido!";
			response.status(404); // 404 Not found
		}
			
		makeForm();
		return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");
	}

	
	public Object get(Request request, Response response) {
		int id = Integer.parseInt(request.params(":id"));		
		Pessoa pessoa = (Pessoa) pessoaDAO.getElemento(id);
		
		if (pessoa != null) {
			response.status(200); // success
			makeForm(FORM_DETAIL, pessoa, FORM_ORDERBY_NOME);
        } else {
            response.status(404); // 404 Not found
            String resp = "Pessoa " + id + " não encontrado.";
    		makeForm();
    		form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");     
        }

		return form;
	}

	
	public Object getToUpdate(Request request, Response response) {
		int id = Integer.parseInt(request.params(":id"));		
		Pessoa pessoa = (Pessoa) pessoaDAO.getElemento(id);
		
		if (pessoa != null) {
			response.status(200); // success
			makeForm(FORM_UPDATE, pessoa, FORM_ORDERBY_NOME);
        } else {
            response.status(404); // 404 Not found
            String resp = "Pessoa " + id + " não encontrado.";
    		makeForm();
    		form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");     
        }

		return form;
	}
	
	
	public Object getAll(Request request, Response response) {
		int orderBy = Integer.parseInt(request.params(":orderby"));
		makeForm(orderBy);
	    response.header("Content-Type", "text/html");
	    response.header("Content-Encoding", "UTF-8");
		return form;
	}			
	
	public Object update(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id"));
		Pessoa pessoa = pessoaDAO.getElemento(id);
        String resp = "";       

        if (pessoa != null) {
        	pessoa.setNome(request.queryParams("nome"));
        	pessoa.setAltura(Integer.parseInt(request.queryParams("altura")));
        	pessoa.setPeso(Integer.parseInt(request.queryParams("peso")));
        	pessoaDAO.atualizarElemento(pessoa);
        	response.status(200); // success
            resp = "Pessoa (ID " + pessoa.getID() + ") atualizado!";
        } else {
            response.status(404); // 404 Not found
            resp = "Pessoa (ID \" + pessoa.getId() + \") não encontrado!";
        }
		makeForm();
		return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");
	}

	
	public Object delete(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id"));
        Pessoa pessoa = pessoaDAO.getElemento(id);
        String resp = "";       

        if (pessoa != null) {
            pessoaDAO.excluirElemento(id);
            response.status(200); // success
            resp = "Pessoa (" + id + ") excluído!";
        } else {
            response.status(404); // 404 Not found
            resp = "Pessoa (" + id + ") não encontrado!";
        }
		makeForm();
		return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");
	}
}