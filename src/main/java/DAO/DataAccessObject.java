package DAO;
import java.util.List;

public interface DataAccessObject<T> {
    public boolean create(T data);
    public T get(int id);
    public List<T> getAll(int id);
    public boolean update(T data);
    public boolean delete(int id);
}
