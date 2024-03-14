import java.util.*;

class Cliente {
    private String Nome;
    private String Email;
    private long Telefone;
    private double saldoGeral;
    private List<Conta> listaContas;

    Cliente() {
        Nome = "";
        Email = "";
        Telefone = 0;
        saldoGeral = 0;
        listaContas = new LinkedList<>();
    }

    public void setNome(String Nome) {
        this.Nome = Nome;
    }

    public String getNome() {
        return Nome;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getEmail() {
        return Email;
    }

    public void setTelefone(long Telefone) {
        this.Telefone = Telefone;
    }

    public long getTelefone() {
        return Telefone;
    }

    public void incluirConta(Conta c) {
        listaContas.add(c);
    }

    public double getSaldoGeral() {
        saldoGeral = 0;

        for (Conta c : listaContas) {
            saldoGeral += c.getSaldo();
        }
        return saldoGeral;
    }

    public List<Conta> getListaContas() {
        return listaContas;
    }
}
