package br.unicamp.contatos.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import br.unicamp.contatos.client.Contato;

public class ContatoBusiness {
	private static Map<String, Contato> contatos = new HashMap<String, Contato>();
    
    static {
    	contatos.put("Contato 01", 
          new Contato("Contato 01", "contato01@unicamp.com", "categoria01", "123456789"));
    	contatos.put("Contato 02", 
    	          new Contato("Contato 02", "contato02@unicamp.com", "categoria02", "123456789"));
    	contatos.put("Contato 03", 
    	          new Contato("Contato 03", "contato03@unicamp.com", "categoria03", "123456789"));
    }
	
	public void inserir(Contato contato) {
		contatos.put(contato.getNome(), contato);
	}

	public void atualizar(Contato contato) {
		contatos.put(contato.getNome(), contato);
	}

	public void remover(Contato contato) {
		contatos.remove(contato.getNome());
	}

	public ArrayList<Contato> listar() {
		return new ArrayList<Contato>(contatos.values());
	}

	public Contato obter(String nome) {
		return contatos.get(nome);
	}
}
