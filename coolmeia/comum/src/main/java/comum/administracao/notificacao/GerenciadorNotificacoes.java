package comum.administracao.notificacao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GerenciadorNotificacoes implements Subject {
    private static GerenciadorNotificacoes instance;
    private Map<String, List<Observer>> observadores;
    
    private GerenciadorNotificacoes() {
        this.observadores = new HashMap<>();
    }
    
    public static GerenciadorNotificacoes getInstance() {
        if (instance == null) {
            instance = new GerenciadorNotificacoes();
        }
        return instance;
    }
    
    @Override
    public void adicionarObservador(Observer observador, String identificador) {
        observadores.computeIfAbsent(identificador, k -> new ArrayList<>()).add(observador);
    }
    
    @Override
    public void removerObservador(Observer observador, String identificador) {
        if (observadores.containsKey(identificador)) {
            observadores.get(identificador).remove(observador);
        }
    }
    
    @Override
    public void notificarObservadores(String identificador, String mensagem) {
        if (observadores.containsKey(identificador)) {
            for (Observer observador : observadores.get(identificador)) {
                observador.receberNotificacao(mensagem);
            }
        }
    }
}
