package br.unicamp.contatos.client;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ContatoServiceAsync {
	void inserir(Contato contato, AsyncCallback<Void> callback);
	void atualizar(Contato contato, AsyncCallback<Void> callback);
	void remover(Contato contato, AsyncCallback<Void> callback);
	void listar(AsyncCallback<ArrayList<Contato>> callback);
	void obter(String nome, AsyncCallback<Contato> callback);
}
