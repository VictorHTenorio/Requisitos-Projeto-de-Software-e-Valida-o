package comum.administracao.notificacao;

public interface Subject {
    void adicionarObservador(Observer observador, String identificador);
    void removerObservador(Observer observador, String identificador);
    void notificarObservadores(String identificador, String mensagem);
}
