package org.example.a4sebib24.Domain;
import org.example.a4sebib24.Repository.RepositoryException;

import java.text.ParseException;

public interface IEntitateFactory<T> {

    public abstract String toString(T entitate);

    public abstract T fromString(String line) throws ParseException, RepositoryException;

}
