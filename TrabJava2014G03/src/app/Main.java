package app;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

import DAO.DAOCliente;
import DAO.DAOPedido;
import DAO.DAOPedidoItem;
import DAO.DAOProduto;
import connection.ApagarBd;
import connection.ConexaoBD;
import connection.CriarBd;
import connection.CriarIni;
import entities.Cliente;
import entities.Pedido;
import entities.PedidoItem;
import entities.Produto;
import util.Sc;

public class Main {
    private static Scanner sc = new Scanner(System.in);
    
    public static DAOPedido DAOPedido;
    public static DAOPedidoItem DAOPedidoItem;
    public static DAOCliente DAOCliente;
    public static DAOProduto DAOProduto;
    
	public static ConexaoBD conexao = new ConexaoBD();
    public static void main(String[] args) {   
    	criarConexao();
    	menuPrincipal();
    }
    public static void menuPrincipal() {
    	int escolha =-1;
        while (escolha != 0) {
        	System.out.print("""
                    ******************************
                    \tMENU PRINCIPAL
                    ******************************
                    1. Menu Pedidos
                    2. Listar Clientes Cadastrados
                    3. Listar Produtos Cadastrados
                    4. Creditos
                    0. Sair""");
        	System.out.print("\nEscolha uma opção: ");
        	escolha = Sc.lerInt();
        	
        	switch(escolha) {
        	case 0:
                mensagemFinal();
                break;
        	case 1:
                MenuPedidos();
                break;
            case 2:
            	listarClientes();
                break;
            case 3:
                DAOProduto.listar();
                break;
            case 4:
            	creditos();
            	break;
            default:
                System.out.println("Opção inválida.");
                break;
        	}
        }
    }
    public static void MenuPedidos() {
    	int escolha =-1;
		while (escolha != 0){
			System.out.println("""
			        
		            ****************************
		            \tMENU PEDIDOS
		            ****************************
		            1- Cadastrar Pedido
		            2- Alterar Pedido
		            3- Excluir Pedido
		            4- Localizar Pedido
		            5- Gerar Valor Total dos Pedidos
		            6- Imprimir Pedidos
		            0- Retornar""");
			System.out.print("\nEscolha uma opção: ");
			escolha = Sc.lerInt();
			
        switch (escolha) {
        	case 0:
            System.out.println("Retornando ao menu principal...");
            menuPrincipal();
            	break;
        	case 1:
                cadastrarPedido();
                break;
            case 2:
                alterarPedido();
                break;
            case 3:
                excluirPedido();
                break;
            case 4:
                localizarPedido();
                break;
            case 5:
                gerarValorTotal();
                break;
            case 6:
            	imprimirPedidos();
            default:
                System.out.println("Opção inválida.");
                break;
            }
        }
    }
    
    public static void cadastrarPedido() {
    	Pedido pedido = new Pedido();
    	
    	System.out.println("************************");
    	System.out.println("   CADASTRAR PEDIDO");
    	System.out.println("************************");
    	System.out.println("\nDeseja verificar a lista de Clientes antes do cadastro? \n1- sim \t2- não");
    	int escolha = Sc.lerInt();
		if (escolha == 1) {
			listarClientes();
		}
		System.out.print("Digite o id do cliente a ser adicionado ao pedido: ");
		int idCliente = sc.nextInt();
		Cliente cliente = DAOCliente.ler(idCliente);
		if (cliente == null) {
			System.out.println("Cliente não encontrado.");
			return;
		}
		pedido.setIdCliente(idCliente);
		System.out.println("O cliente selecionado foi: " + cliente.getNome());
		System.out.println("Cpf: "+ cliente.getCpf() + "\n");
		
		adicionarProdutos(pedido.getIdPedido());
		
		DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        
		try {
        	System.out.print("Digite a data de emissão do pedido(yyyy-MM-dd): ");
        	String dataEmissaoStr = sc.next();
        	LocalDate dataEmissao = LocalDate.parse(dataEmissaoStr, df);
        	pedido.setDataEmissao(dataEmissao);
        }
        catch(DateTimeParseException e) {
            System.out.println("Data inválida. Certifique-se de que a data está no formato yyyy-MM-dd.");
            e.getMessage();
            return;
        }
        
        try {
        	System.out.print("Digite a data de entrega do pedido(yyyy-MM-dd): ");
            String dataEntregaStr = sc.next();
        	LocalDate dataEntrega = LocalDate.parse(dataEntregaStr, df);
            pedido.setDataEntrega(dataEntrega);
        }
        catch(DateTimeParseException e) {
            System.out.println("Data inválida. Certifique-se de que a data está no formato yyyy-MM-dd.");
            e.getMessage(); 
            return;
        }
        if (pedido.getDataEmissao().isAfter(pedido.getDataEntrega())) {
			System.out.println("A data de entrega não pode ser anterior a data de emissão.");
			System.out.println("Refaça o pedido.");
			return;
		}
        
        sc.nextLine();
		System.out.print("Observação: ");
		String observacao = sc.nextLine();
		pedido.setObservacao(observacao);
		
		DAOPedido.criar(pedido);
		if (DAOPedido == null) {
			return;
		}
		else {
			System.out.println("Pedido cadastrado com sucesso!\n");			
		}
	}
    public static void adicionarProdutos(int idPedido) {
    	PedidoItem item = new PedidoItem();
    	
    	System.out.println("Deseja ver a lista de Produtos antes do cadastro? \n1- sim \t2- não");
    	int escolha = Sc.lerInt();
		if (escolha == 1) {
			listarProdutos();
		}
		System.out.print("Digite o id do produto a ser adicionado ao pedido: ");
		int idProduto = sc.nextInt();
		Produto produto = DAOProduto.ler(idProduto);
		
		if (produto == null) {
			System.out.println("Produto não encontrado.");
			return;
		}
		System.out.println("O produto selecionado foi: " + produto.getNome());
		System.out.println("Valor: "+ produto.getValorVenda() + "\n");
		
    	System.out.print("Digite a quantidade de" + produto.getNome()+" a ser adicionada: ");
    	int quantidade = sc.nextInt();
    	item.setQuantidade(quantidade);
    	
    	item.setValorUnitario(produto.getValorVenda());
    	
    	System.out.print("Digite a porcentagem do desconto: ");
    	int desconto = Sc.lerInt();
    	item.setValorDesconto(desconto);
    	
    	DAOPedidoItem.criar(item);
		
    	if (DAOPedidoItem == null) {
			return;
		} 
    	else {
			System.out.println("\nProduto adicionado ao pedido!\n");
		}
    }
    
    public static void alterarPedido() {
        System.out.println("Digite o ID do pedido que deseja alterar: ");
        int idPedido = Sc.lerInt();
        Pedido pedido = DAOPedido.ler(idPedido);
        
        if (pedido != null) {
            System.out.println("Pedido encontrado. Selecione o que deseja alterar:");
            System.out.println("1 - Alterar Cliente");
            System.out.println("2 - Alterar Produtos");
            System.out.println("0 - Cancelar Alteração");
            int escolha = Sc.lerInt();

            switch (escolha) {
                case 1:
                    alterarCliente(pedido);
                    break;
                case 2:
                    alterarProduto(pedido);
                    break;
                case 0:
                    System.out.println("Alteração cancelada.");
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        } 
        else {
            System.out.println("Pedido não encontrado.");
        }
    }
	public static void alterarCliente(Pedido pedido) {
		System.out.println("Digite o ID do novo cliente: ");
		int idCliente = Sc.lerInt();
		Cliente cliente = DAOCliente.ler(idCliente);

		if (cliente != null) {
			pedido.setIdCliente(idCliente);
			DAOPedido.atualizar(pedido);
			System.out.println("Cliente alterado com sucesso.");
		} else {
			System.out.println("Cliente não encontrado.");
		}
	}
	public static void alterarProduto(Pedido pedido) {
		if (DAOPedidoItem.buscarPorPedidoEProduto(pedido.getIdPedido(), 0) == null) {
			System.out.println("Pedido não possui produtos.");
			
			System.out.println("Deseja adicionar produtos? \n1- Sim \t2- Cancelar Alteração");
			int escolha = Sc.lerInt();
			if (escolha == 1) {
				adicionarProdutos(pedido.getIdPedido());
			} else {
				System.out.println("Alteração cancelada.");
			}
		}
		System.out.println("Produtos atuais do pedido:");
	    DAOPedidoItem.buscarPorPedidoEProduto(pedido.getIdPedido(), 0); 
	    
	    System.out.println("Deseja adicionar ou remover produtos?");
	    System.out.println("1 - Adicionar Produto");
	    System.out.println("2 - Remover Produto");
	    System.out.println("0 - Cancelar Alteração");
	    int escolha = Sc.lerInt();

	    switch (escolha) {
	    	case 0:
	    		System.out.println("Alteração cancelada.");
	    		break;
	    	case 1:
	            adicionarProdutos(pedido.getIdPedido()); 
	            break;
	        case 2:
	            removerProduto(pedido);
	            break;
	        default:
	            System.out.println("Opção inválida.");
	    }
	}
	public static void removerProduto(Pedido pedido) {
        System.out.println("Digite o ID do produto que deseja remover: ");
        int idProduto = Sc.lerInt();
        PedidoItem item = DAOPedidoItem.ler(idProduto);

        if (item != null) {
            DAOPedidoItem.excluir(item.getIdPedidoItem());
            System.out.println("Produto removido com sucesso.");
        } else {
            System.out.println("Produto não encontrado");
        }
	}
	
	public static void excluirPedido() {
		System.out.println("Digite o ID do pedido que deseja excluir: ");
		int idPedido = Sc.lerInt();
		try {
			DAOPedido.excluir(idPedido);
			System.out.println("Pedido excluído com sucesso.");
		} 
		catch (Exception e) {
			System.out.println("Erro ao excluir o pedido.");
		}
		
		System.out.println("Deseja excluir outro pedido? \n1- sim \t2- não");
		int escolha = Sc.lerInt();
		if (escolha == 1) {
			excluirPedido();
		} 
		else {
			return;
		}
	}
	
	public static void localizarPedido() {
		System.out.println("Digite o ID do pedido que deseja localizar: ");
		int idPedido = Sc.lerInt();
		Pedido pedido = DAOPedido.ler(idPedido);
		if (pedido != null) {
			System.out.println(pedido);
		} 
		else {
			System.out.println("Pedido não encontrado.");
		}
		
		System.out.println("Deseja localizar outro pedido? \n1- sim \t2- não");
		int escolha = Sc.lerInt();
		if (escolha == 1) {
			localizarPedido();
		} else {
			return;
		}
	}
    
	public static void gerarValorTotal() {
		//Adicionar algum comando para somar o valor total dos pedidos de determinado cliente.
		List<Pedido> pedidos = DAOPedido.listar();
		double valorTotal = 0;
		for (Pedido pedido : pedidos) {
			valorTotal += pedido.getValorTotal();
		}
		System.out.println("Valor total dos pedidos: " + valorTotal);
	}
    
    public static void listarClientes() {
    	System.out.println("*************************");
    	System.out.println("    LISTA DE CLIENTES");
    	System.out.println("*************************");
    	DAOCliente.listar();
    }
    public static void listarProdutos() {
    	System.out.println("*************************");
    	System.out.println("    LISTA DE PRODUTOS");
    	System.out.println("*************************");
    	DAOProduto.listar();
    }
    public static void imprimirPedidos(){
    	Pedido pedido = new Pedido();
    	PedidoItem item = new PedidoItem();
    	
    	int opcao = 0;
	    int idPedido = 0;
	    boolean pedidoValido = false;
	    
	    while (!pedidoValido) {
	        System.out.print("Digite o id do Pedido a ser impresso: ");
	        idPedido = Sc.lerInt();
	        if(pedido == null) {
	        	System.out.println("Pedido não encontrado.");
	        	return;
	        }
			if (item.getIdPedido() != 0) {
				System.out.println("Deseja imprimir o pedido com os produtos? \n1- sim \t2- não");
		        opcao = Sc.lerInt();
		        try {
		        	switch(opcao) {
		        	case 1:
		        		DAOPedido.imprimirPedido(idPedido, true);
						pedidoValido = true;
						System.out.println("\nDeseja imprimir o pedido com os produtos? \n1- sim \t2- não");
		                opcao = Sc.lerInt();
						if (opcao == 1) {
							imprimirPedidos();
						}
						else if (opcao == 2) {
							return;
						}
                		break;
		        	case 2:
		        		DAOPedido.imprimirPedido(idPedido, false);
		            	pedidoValido = true;
		            	System.out.println("\nDeseja imprimir o pedido com os produtos? \n1- sim \t2- não");
		                opcao = Sc.lerInt();
						if (opcao == 1) {
							imprimirPedidos();
						}
						else if (opcao == 2) {
							return;
						}
						break;
					default:
						System.out.println("Opção inválida.");
		        	}
		        }
		        catch(Exception e) {
		        	e.printStackTrace();
		        }
			}
			else {
            	try {
					DAOPedido.imprimirPedido(idPedido, false);
				}
            	catch (SQLException e) {
					e.printStackTrace();
				}
            	System.out.println("O pedido não possui itens para serem impressos.");
            	pedidoValido = true;
        	}
	    }
    }
    
	public static void creditos() {
    	System.out.println("Tamo junto Professor Ricardo! Sentiremos saudades!");
	}
    
    public static void criarConexao() {
    	CriarIni.criarIni();
    	CriarBd.criarBancoETabelas();
    	if(!conexao.isConnectionClosed()) {
    		System.out.println("Conexão com o banco de dados estabelecida com sucesso.\n");
    	}
       // ConexaoBD.getConnection();	
        if (conexao.getConnection() != null) {
        	DAOPedido = new DAOPedido(conexao.getConnection());
		    DAOPedidoItem = new DAOPedidoItem(conexao.getConnection());
		    DAOCliente = new DAOCliente(conexao.getConnection());
		    DAOProduto = new DAOProduto(conexao.getConnection());
		} 
		else {
			System.err.println("Houve um problema na criação do banco de dados.");
		}
    }
    public static void mensagemFinal() {
        System.out.println();
    	conexao.retirarAcessoBd();
        conexao.closeConnection();
        System.out.println("Deseja apagar o o Banco de Dados ao sair?\n1- sim \t2- não");
        int escolha = Sc.lerInt();
		if (escolha == 1) {
			ApagarBd.apagarBanco();
			System.out.println("\nObrigado por testar nosso projeto!");
			System.out.println("Desenvolvido por: Andressa, Isabella, Lucas, Renata & Samuel");
			System.out.println("Saindo...");
		}
		else {
			System.out.println("\nObrigado por testar nosso projeto!");
			System.out.println("Desenvolvido por: Andressa, Isabella, Lucas, Renata & Samuel");
			System.out.println("Saindo...");
		}
		System.exit(0);
    }
}
