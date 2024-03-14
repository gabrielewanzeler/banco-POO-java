class ContaCorrente extends Conta {
    private double limite;

    ContaCorrente() {
        limite = 0.0;
        super.Saldo = 0.0;
    }

    ContaCorrente(double limite, int numeroConta) {
        this.limite = limite;
        super.Saldo = 0.0;
        this.numeroConta = numeroConta;
    }

    @Override
    public double getSaldo() {
        double saldoCalculado = super.Saldo;
        saldoCalculado -= saldoCalculado * 0.0038;
        saldoCalculado += limite;
        return saldoCalculado;
    }
}