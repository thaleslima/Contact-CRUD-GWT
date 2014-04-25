package br.unicamp.contatos.server;

import java.util.ArrayList;

import br.unicamp.contatos.client.Contato;
import br.unicamp.contatos.client.ContatoService;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class ContatoServiceImpl extends RemoteServiceServlet implements ContatoService {
	private static final long serialVersionUID = 1L;
	ContatoBusiness cb = new ContatoBusiness();
	
	@Override
	public void inserir(Contato contato) {
		cb.inserir(contato);
	}

	@Override
	public void atualizar(Contato contato) {
		cb.atualizar(contato);
	}

	@Override
	public void remover(Contato contato) {
		cb.remover(contato);
	}

	@Override
	public ArrayList<Contato> listar() {
		return cb.listar();
	}

	@Override
	public Contato obter(String nome) {
		return cb.obter(nome);
	}
}
