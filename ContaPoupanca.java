class ContaPoupanca extends Conta {
    ContaPoupanca(int numeroConta) {
        super.numeroConta = numeroConta;
    }

    public double getSaldo() {
        return super.Saldo;
    }

    public void sacar(double valor) {
        if (valor <= getSaldo()) {
            Saldo -= valor;
        } else {
            System.out.println("Saldo insuficiente!");
        }
    }

}