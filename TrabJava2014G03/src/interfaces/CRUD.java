package interfaces;

import java.util.List;

public interface CRUD<T> {
    void criar(T obj);
    T ler(int id);
    void atualizar(T obj);
    void excluir(int id);
    List<T> listar();
}
