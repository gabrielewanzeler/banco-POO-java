
import java.io.*;
import java.util.*;

public class PrincipalConta {
    private static final int OPCAO_SAIR = 0;
    private static final int OPCAO_CADASTRAR_CLIENTE = 1;
    private static final int OPCAO_VER_LISTA_CLIENTES = 2;
    private static final int OPCAO_DEPOSITAR = 3;
    private static final int OPCAO_TRANSFERENCIA = 4;
    private static final int OPCAO_ENTRAR_CONTA = 5;


    public static void main(String[] args) {

        try (BufferedReader in = new BufferedReader(new InputStreamReader(System.in))) {
            List<Cliente> listaClientes = new LinkedList<>();

            int opcao;
            do {
                exibirMenu();
                opcao = Integer.parseInt(in.readLine());

                switch (opcao) {
                    case OPCAO_CADASTRAR_CLIENTE:
                        criarCliente(in, listaClientes);
                        break;
                    case OPCAO_VER_LISTA_CLIENTES:
                        exibirListaClientes(listaClientes);
                        break;
                    case OPCAO_DEPOSITAR:
                        depositar(in, listaClientes);
                        break;
                    case OPCAO_TRANSFERENCIA:
                        transferirEntreContas(in, listaClientes);
                        break;                    
                    case OPCAO_ENTRAR_CONTA:
                        entrarNaConta(in, listaClientes);
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
        System.out.println("3 - Realizar depósito");
        System.out.println("4 - Realizar Transferência");
        System.out.println("5 - Entrar em uma conta");
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
            System.out.println("\nVamos criar uma conta?");
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

        if (valor !=0) {
            System.out.println("\nValor de R$ " + valor + " depositado!");
        }
        else {
            System.out.println("\nNenhum valor depositado!");
        }
        
        cliente.incluirConta(c);
    }    

    private static void entrarNaConta(BufferedReader in, List<Cliente> listaClientes) throws IOException {
        System.out.println("\nEntre em uma conta já criada!");
        System.out.print("\nInforme o nome do cliente: ");
        String nome = in.readLine();
    
        boolean clienteEncontrado = false;
        for (Cliente cliente : listaClientes) {
            if (nome.equalsIgnoreCase(cliente.getNome())) {
                int opcao;
                do {
                    System.out.println("\nBem-vindo(a), " + cliente.getNome() + "!");
                    System.out.println("1 - Ver dados da conta");
                    System.out.println("2 - Depositar");
                    System.out.println("3 - Transferência entre contas");
                    System.out.println("4 - Criar mais uma conta em seu nome");
                    System.out.println("0 - Sair da conta");
                    System.out.print("\nEscolha uma opção: ");
                    opcao = Integer.parseInt(in.readLine());

                    switch (opcao) {
                        case 1:
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
                            break;
                        case 2:
                            depositar(in, listaClientes);
                            break;
                        case 3:
                            transferirEntreContas(in, listaClientes);
                            break;
                        case 4:
                            String tipoConta;
                            do {
                                System.out.println("\nVamos criar uma conta?");
                                System.out.println("\nQual tipo de conta deseja criar?");
                                System.out.println("1 - Conta Corrente");
                                System.out.println("2 - Conta Poupança");
                                System.out.println("0 - Sair criar conta");
                
                                tipoConta = in.readLine();
                                if (!tipoConta.equals("0")) {
                                    criarConta(in, cliente, tipoConta);
                                }
                            } while (!tipoConta.equals("0"));
                            break;
                        case 0:
                            System.out.println("Saindo da conta...");
                            break;
                        default:
                            System.out.println("Opção inválida. Tente novamente.");
                            break;
                    }
                } while (opcao != 0);

                clienteEncontrado = true;
                break;
            }
        }
        if (!clienteEncontrado) {
            System.out.println("\nCliente não encontrado!");
        }
    }

    private static void exibirListaClientes(List<Cliente> listaClientes) {
        System.out.println("\nLista de todos os clientes cadastrados: \n");

        for (Cliente cliente : listaClientes) {
            System.out.println("Nome: " + cliente.getNome() + ", E-mail: " + cliente.getEmail() + ", Telefone: " + cliente.getTelefone());
        }
    }

    private static void depositar(BufferedReader in, List<Cliente> listaClientes) throws IOException {
        System.out.print("\nInforme o nome do cliente da conta para o depósito: ");
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
            System.out.println("\nValor não depositado!");
        }
    }
    
    private static void transferirEntreContas(BufferedReader in, List<Cliente> listaClientes) throws IOException {
        System.out.print("Informe o nome do cliente que está transferindo o dinheiro: ");
        String nomeOrigem = in.readLine();

        Cliente clienteOrigem = encontrarClientePorNome(listaClientes, nomeOrigem);
        if (clienteOrigem == null) {
            System.out.println("\nCliente não encontrado!");
            return;
        }
    
        System.out.print("Informe o número da conta de origem: ");
        int numeroContaOrigem = Integer.parseInt(in.readLine());

        Conta contaOrigem = encontrarContaPorNumero(clienteOrigem, numeroContaOrigem);
        if (contaOrigem == null) {
            System.out.println("\nConta de origem não encontrada!");
            return;
        }
    
        System.out.print("Informe o nome do cliente que está recebendo o dinheiro: ");
        String nomeDestino = in.readLine();

        Cliente clienteDestino = encontrarClientePorNome(listaClientes, nomeDestino);
        if (clienteDestino == null) {
            System.out.println("\nCliente de destino não encontrado!");
            return;
        }

        System.out.print("Informe o número da conta de destino: ");
        int numeroContaDestino = Integer.parseInt(in.readLine());

        Conta contaDestino = encontrarContaPorNumero(clienteDestino, numeroContaDestino);
        if (contaDestino == null) {
            System.out.println("\nConta de destino não encontrada!");
            return;
        }

        System.out.print("Informe o valor a ser transferido: ");
        double valorTransferencia = Double.parseDouble(in.readLine());

        if (contaOrigem.getSaldo() < valorTransferencia) {
            System.out.println("\nSaldo insuficiente na conta de origem!");
            return;
        }

        contaOrigem.sacar(valorTransferencia);
        contaDestino.Depositar(valorTransferencia);

        System.out.println("\nTransferência realizada com sucesso!");
    }
    
    private static Cliente encontrarClientePorNome(List<Cliente> listaClientes, String nome) {
        for (Cliente cliente : listaClientes) {
            if (cliente.getNome().equalsIgnoreCase(nome)) {
                return cliente;
            }
        }
        return null;
    }
    
    // Método auxiliar para encontrar uma conta em um cliente pelo número da conta
    private static Conta encontrarContaPorNumero(Cliente cliente, int numeroConta) {
        for (Conta conta : cliente.getListaContas()) {
            if (conta.getNumeroConta() == numeroConta) {
                return conta;
            }
        }
        return null;
    }
    
}