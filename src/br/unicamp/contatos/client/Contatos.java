package br.unicamp.contatos.client;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTMLTable.Cell;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;

public class Contatos implements EntryPoint {
	private final ContatoServiceAsync greetingService = GWT
			.create(ContatoService.class);

	protected Button addButton;
	protected Button updateButton;
	protected Button search;
	protected TextBox txtNome;
	protected TextBox txtEmail;
	protected TextBox txtCategoria;
	protected TextBox txtTelefone;
	protected TextBox txtSearch;
	protected Label status;
	protected Grid contatoGrid;
	protected Grid formGrid;

	private List<Contato> contatos;
	private Contato correnteContato;

	public void onModuleLoad() {
		addButton = new Button("Add novo contato");
		updateButton = new Button("Atualizar contato");
		search = new Button("Pesquisar contato");
		txtNome = new TextBox();
		txtEmail = new TextBox();
		txtCategoria = new TextBox();
		txtTelefone = new TextBox();
		txtSearch = new TextBox();
		status = new Label();
		contatoGrid = new Grid(2, 6);

		formGrid = new Grid(5, 4);

		formGrid.setWidget(0, 0, new Label("Nome"));
		formGrid.setWidget(0, 1, txtNome);

		formGrid.setWidget(1, 0, new Label("Email"));
		formGrid.setWidget(1, 1, txtEmail);

		formGrid.setWidget(2, 0, new Label("Categoria"));
		formGrid.setWidget(2, 1, txtCategoria);

		formGrid.setWidget(3, 0, new Label("Telefone"));
		formGrid.setWidget(3, 1, txtTelefone);

		formGrid.setWidget(4, 0, updateButton);
		formGrid.setWidget(4, 1, addButton);

		RootPanel.get("contatoStatus").add(status);
		RootPanel.get("contatoForm").add(formGrid);
		RootPanel.get("contactListing").add(contatoGrid);
		RootPanel.get("campoPesquisar").add(txtSearch);
		RootPanel.get("pesquisar").add(search);

		updateButton.setVisible(false);
		addButton.setVisible(true);

		contatoGrid.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				Cell cellForEvent = contatoGrid.getCellForEvent(event);

				int row = cellForEvent.getRowIndex();
				int col = cellForEvent.getCellIndex();

				Contato contato = contatos.get(row);

				if (col == 4) {
					updateButton.setVisible(true);
					addButton.setVisible(false);
					loadForm(contato);
				} else if (col == 5) {
					removeContato(contato);
				}
			}
		});

		search.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				searchContact(txtSearch.getText());
			}
		});

		addButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				copiaDadosContatos();
				addContact(correnteContato);
			}
		});

		updateButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				copiaDadosContatos();
				updateContact(correnteContato);
			}
		});

		ServiceDefTarget target = (ServiceDefTarget) greetingService;
		target.setServiceEntryPoint(GWT.getModuleBaseURL() + "contato");

		listarContatos();
	}

	void listarContatos() {
		greetingService.listar(new AsyncCallback<ArrayList<Contato>>() {

			@Override
			public void onFailure(Throwable caught) {
				status.setText("Erro ao consultar contatos"
						+ caught.getMessage());
			}

			@Override
			public void onSuccess(ArrayList<Contato> result) {
				contatos = result;
				contatoGrid.clear();
				contatoGrid.resizeRows(contatos.size());
				int row = 0;

				loadForm(new Contato());
				updateButton.setVisible(false);
				addButton.setVisible(true);

				for (Contato item : result) {
					contatoGrid.setWidget(row, 0, new Label(item.getNome()));
					contatoGrid.setWidget(row, 1, new Label(item.getEmail()));
					contatoGrid.setWidget(row, 2,
							new Label(item.getCategoria()));
					contatoGrid.setWidget(row, 3, new Label(item.getTelefone()));
					contatoGrid.setWidget(row, 4,
							new Hyperlink("Atualizar", ""));
					contatoGrid.setWidget(row, 5, new Hyperlink("Remover", ""));
					row++;
				}
			}
		});
	}

	void addContact(final Contato contato) {
		greetingService.inserir(contato, new AsyncCallback<Void>() {

			@Override
			public void onSuccess(Void result) {
				status.setText("Contato inserido com sucesso");
				txtSearch.setText("");
				listarContatos();
			}

			@Override
			public void onFailure(Throwable caught) {
				status.setText("Erro ao inserir contato" + caught.getMessage());
			}
		});
	}

	void searchContact(final String nameContato) {

		greetingService.obter(nameContato, new AsyncCallback<Contato>() {

			@Override
			public void onSuccess(Contato result) {

				if (result != null) {
					contatoGrid.clear();
					contatoGrid.resizeRows(1);
					contatoGrid.setWidget(0, 0, new Label(result.getNome()));
					contatoGrid.setWidget(0, 1, new Label(result.getEmail()));
					contatoGrid.setWidget(0, 2,
							new Label(result.getCategoria()));
					contatoGrid.setWidget(0, 3, new Label(result.getTelefone()));
					contatoGrid.setWidget(0, 4, new Hyperlink("Atualizar", ""));
					contatoGrid.setWidget(0, 5, new Hyperlink("Remover", ""));

					contatos = new ArrayList<Contato>();
					contatos.add(result);
				} else {
					listarContatos();
				}
			}

			@Override
			public void onFailure(Throwable caught) {
				status.setText("Erro ao consultar contato"
						+ caught.getMessage());
			}
		});

	}

	void updateContact(final Contato contato) {

		greetingService.atualizar(contato, new AsyncCallback<Void>() {

			@Override
			public void onSuccess(Void result) {
				txtSearch.setText("");
				status.setText("Contato atualizado com sucesso");
				listarContatos();
			}

			@Override
			public void onFailure(Throwable caught) {
				status.setText("Erro ao atualizar contato"
						+ caught.getMessage());
			}
		});
	}

	void removeContato(final Contato contato) {
		greetingService.remover(contato, new AsyncCallback<Void>() {
			@Override
			public void onSuccess(Void result) {
				txtSearch.setText("");
				status.setText("Contato removido com sucesso");
				listarContatos();
			}

			@Override
			public void onFailure(Throwable caught) {
				status.setText("Erro ao excluir contato" + caught.getMessage());
			}
		});
	}

	private void loadForm(Contato contato) {
		this.formGrid.setVisible(true);
		correnteContato = contato;
		this.txtNome.setText(contato.getNome());
		this.txtEmail.setText(contato.getEmail());
		this.txtCategoria.setText(contato.getCategoria());
		this.txtTelefone.setText(contato.getTelefone());
	}

	private void copiaDadosContatos() {
		correnteContato.setNome(txtNome.getText());
		correnteContato.setEmail(txtEmail.getText());
		correnteContato.setCategoria(txtCategoria.getText());
		correnteContato.setTelefone(txtTelefone.getText());
	}
}
