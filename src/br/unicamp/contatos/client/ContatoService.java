package br.unicamp.contatos.client;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.RemoteService;

public interface ContatoService extends RemoteService {
	void inserir(Contato contato);
	void atualizar(Contato contato);
	void remover(Contato contato);
	ArrayList<Contato> listar();
	Contato obter(String nome);
}
