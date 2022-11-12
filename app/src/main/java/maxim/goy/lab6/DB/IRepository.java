package maxim.goy.lab6.DB;

import java.util.List;

public interface IRepository<T> {
    void close();
    List<T> getAll();
    long getCount();
    T get();
    long insert();
    long update();
}
