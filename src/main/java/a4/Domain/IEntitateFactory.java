package a4.Domain;
import a4.Repository.RepositoryException;

import java.text.ParseException;

public interface IEntitateFactory<T> {

    public abstract String toString(T entitate);

    public abstract T fromString(String line) throws ParseException, RepositoryException;

}
