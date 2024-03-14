import java.io.*;
import java.util.*;

public class PrincipalConta {
    private static final int OPCAO_SAIR = 0;
    private static final int OPCAO_CRIAR_CLIENTE = 1;
    private static final int OPCAO_VER_LISTA_CLIENTES = 2;
    private static final int OPCAO_VER_DADOS_CLIENTE = 3;
    private static final int OPCAO_DEPOSITAR = 4;
    private static final int OPCAO_CRIAR_CONTA = 5;

    public static void main(String[] args) {

        try (BufferedReader in = new BufferedReader(new InputStreamReader(System.in))) {
            List<Cliente> listaClientes = new LinkedList<>();

            int opcao;
            do {
                exibirMenu();
                opcao = Integer.parseInt(in.readLine());

                switch (opcao) {
                    case OPCAO_CRIAR_CLIENTE:
                        criarCliente(in, listaClientes);
                        break;
                    case OPCAO_VER_LISTA_CLIENTES:
                        exibirListaClientes(listaClientes);
                        break;
                    case OPCAO_VER_DADOS_CLIENTE:
                        verDadosCliente(in, listaClientes);
                        break;
                    case OPCAO_DEPOSITAR:
                        depositar(in, listaClientes);
                        break;
                    case OPCAO_CRIAR_CONTA:
                        criarContaParaClienteExistente(in, listaClientes);
                        break;
                    case OPCAO_SAIR:
                        System.out.println("\nSaindo...");
                        break;
                    default:
                        System.out.println("\nOpção inválida. Tente novamente.");
                        break;
                }
            } while (opcao != OPCAO_SAIR);
        } catch (IOException | NumberFormatException e) {
            System.out.println(e);
        }
    }

    private static void exibirMenu() {
        System.out.println("\n\nInforme a operação desejada:");
        System.out.println("\n1 - Cadastrar cliente");
        System.out.println("2 - Ver lista de clientes");
        System.out.println("3 - Ver dados da conta de um(a) cliente");
        System.out.println("4 - Realizar depósito");
        System.out.println("5 - Criar conta para um cliente já cadastrado");
        System.out.println("0 - Sair");
        System.out.print("\nDigite uma opção: ");
    }

    private static void criarCliente(BufferedReader in, List<Cliente> listaClientes) throws IOException {
        Cliente cliente = new Cliente();

        System.out.print("\nDigite o nome do cliente: ");
        String nome = in.readLine();
        cliente.setNome(nome);
        System.out.print("Digite o e-mail do cliente: ");
        String email = in.readLine();
        cliente.setEmail(email);
        System.out.print("Digite o número de telefone do cliente: ");
        long telefone = Long.parseLong(in.readLine());
        cliente.setTelefone(telefone);

        String tipoConta;
        do {
            System.out.println("\nQual tipo de conta deseja criar?");
            System.out.println("1 - Conta Corrente");
            System.out.println("2 - Conta Poupança");
            System.out.println("0 - Sair criar conta");
            
    
            tipoConta = in.readLine();
            if (!tipoConta.equals("0")) {
                criarConta(in, cliente, tipoConta);
            }
        } while (!tipoConta.equals("0"));
    
        listaClientes.add(cliente);
    }

    private static void criarConta(BufferedReader in, Cliente cliente, String tipoConta) throws IOException {
        Conta c;
        Random rand = new Random();
        int numeroConta = rand.nextInt(10000) + 1;
    
        switch (tipoConta) {
            case "1":
                System.out.print("\nInforme o limite da conta corrente: ");
                double limite = Double.parseDouble(in.readLine());
                c = new ContaCorrente(limite, numeroConta);
                break;
            case "2":
                c = new ContaPoupanca(numeroConta);
                break;
            default:
                System.out.println("\nOpção inválida.");
                return;
        }
    
        System.out.println("\nConta criada com sucesso!");
        System.out.println("Número da conta: " + numeroConta);
        System.out.print("\nAgora informe a quantidade que deseja depositar na conta: ");
        double valor = Double.parseDouble(in.readLine());
        c.Depositar(valor);
        System.out.println("\nValor depositado!");
        cliente.incluirConta(c);
    }    


    private static void exibirListaClientes(List<Cliente> listaClientes) {
        System.out.println("\nLista de todos os clientes cadastrados: \n");

        for (Cliente cliente : listaClientes) {
            System.out.println("Nome: " + cliente.getNome() + ", E-mail: " + cliente.getEmail() + ", Telefone: " + cliente.getTelefone());
        }
    }

    private static void verDadosCliente(BufferedReader in, List<Cliente> listaClientes) throws IOException {
        System.out.print("\nInforme o nome do cliente: ");
        String nome = in.readLine();
        System.out.print("Digite o e-mail do cliente: ");
        String email = in.readLine();

        boolean clienteEncontrado = false;
        for (Cliente cliente : listaClientes) {
            if (nome.equalsIgnoreCase(cliente.getNome()) && email.equalsIgnoreCase(cliente.getEmail())) {
                System.out.println("\nDados do cliente:");
                System.out.println("Nome: " + cliente.getNome());
                System.out.println("E-mail: " + cliente.getEmail());
                System.out.println("Número de telefone: " + cliente.getTelefone());
                System.out.println("Saldo Geral: R$ " + cliente.getSaldoGeral());
    
                System.out.println("\nContas do cliente:");
                for (Conta conta : cliente.getListaContas()) {
                    System.out.println("Número da conta: " + conta.getNumeroConta());
                    if (conta instanceof ContaCorrente) {
                        System.out.println("Tipo de conta: Conta Corrente");
                        System.out.println("Saldo: R$ " + conta.getSaldo());
                    } else if (conta instanceof ContaPoupanca) {
                        System.out.println("Tipo de conta: Conta Poupança");
                        System.out.println("Saldo: R$ " + conta.getSaldo());
                    }
                    System.out.println();
                }
    
                clienteEncontrado = true;
                break;
            }
        }
    
        if (!clienteEncontrado) {
            System.out.println("\nCliente não encontrado!");
        }
    }

    private static void depositar(BufferedReader in, List<Cliente> listaClientes) throws IOException {
        System.out.print("\nInforme o nome do cliente: ");
        String nomeCliente = in.readLine();
    
        System.out.print("Informe o número da conta: ");
        int numeroConta = Integer.parseInt(in.readLine());
    
        boolean clienteEncontrado = false;
        for (Cliente cliente : listaClientes) {
            if (nomeCliente.equalsIgnoreCase(cliente.getNome())) {
                for (Conta conta : cliente.getListaContas()) {
                    if (conta.getNumeroConta() == numeroConta) {
                        System.out.print("Informe o valor a ser depositado: ");
                        double valor = Double.parseDouble(in.readLine());
    
                        conta.Depositar(valor);
                        System.out.println("\nDepósito realizado com sucesso!");
                        clienteEncontrado = true;
                        break;
                    }
                }
                break;
            }
        }
    
        if (!clienteEncontrado) {
            System.out.println("\nCliente ou número da conta não encontrado!");
            System.out.println("Valor não depositado!");
        }
    }

    private static void criarContaParaClienteExistente(BufferedReader in, List<Cliente> listaClientes) throws IOException {
        System.out.print("\nInforme o nome do cliente: ");
        String nomeCliente = in.readLine();
    
        System.out.print("Informe o e-mail do cliente: ");
        String emailCliente = in.readLine();
    
        boolean clienteEncontrado = false;
        for (Cliente cliente : listaClientes) {
            if (nomeCliente.equalsIgnoreCase(cliente.getNome()) && emailCliente.equalsIgnoreCase(cliente.getEmail())) {
                System.out.println("Cliente encontrado!");
                String tipoConta;
                do {
                    System.out.println("\nQual tipo de conta deseja criar?");
                    System.out.println("1 - Conta Corrente");
                    System.out.println("2 - Conta Poupança");
                    System.out.println("0 - Sair criar conta");
    
                    tipoConta = in.readLine();
                    if (!tipoConta.equals("0")) {
                        criarConta(in, cliente, tipoConta);
                    }
                } while (!tipoConta.equals("0"));
    
                clienteEncontrado = true;
                break;
            }
        }
    
        if (!clienteEncontrado) {
            System.out.println("\nCliente não encontrado!");
        }
    }
    
}