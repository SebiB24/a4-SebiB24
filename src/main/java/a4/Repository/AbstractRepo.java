package a4.Repository;

import a4.Domain.Entitate;

import java.util.ArrayList;

public abstract class AbstractRepo<T extends Entitate> {

    protected ArrayList<T> list = new ArrayList<>();

    public abstract ArrayList<T> getList();

    public abstract boolean findById(int var1);

    public abstract T getElementById(int var1) throws RepositoryException;

    public abstract void add(T var1) throws RepositoryException;

    public abstract void removeById(int var1) throws RepositoryException;

    public abstract void remove(T var1) throws RepositoryException;

    public abstract void Act(T var1) throws RepositoryException;

    public int size() {
        return this.list.size();
    }

}