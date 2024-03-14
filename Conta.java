import java.util.Random;

abstract class Conta {
    double Saldo;
    int numeroConta;

    Conta() {
        Saldo = 0.0;
        Random rand = new Random();
        numeroConta = rand.nextInt(10000) + 1; // Números de 1 a 10000
    }

    public void Depositar(double valor) {
        Saldo += valor;
    }

    public abstract double getSaldo();

    public int getNumeroConta() {
        return numeroConta;
    }
}
