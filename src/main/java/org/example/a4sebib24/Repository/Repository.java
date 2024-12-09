package org.example.a4sebib24.Repository;

import org.example.a4sebib24.Domain.Entitate;

import java.util.ArrayList;
import java.util.Iterator;

public class Repository<T extends Entitate> extends AbstractRepo<T> {

    @Override
    public ArrayList<T> getList() {
        return this.list;
    }

    @Override
    public boolean findById(int id) {
        if(!list.isEmpty()) {
            for (T element : list) {
                if(element.getId() == id) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public T getElementById(int id) throws RepositoryException {
        if(!findById(id)) {
            throw new RepositoryException("ID inexistent !!");
        }
        for (T element : list) {
            if(element.getId() == id) {
                return element;
            }
        }
        return null;
    }

    @Override
    public void add(T element) throws RepositoryException {
        if (this.findById(element.getId())) {
            throw new DuplciateIDException("ID deja existent !!");
        } else {
            this.list.add(element);
        }
    }

    @Override
    public void removeById(int id) throws RepositoryException {
        if (!this.findById(id)) {
            throw new RepositoryException("ID inexistent !!");
        } else {
            this.list.remove(this.getElementById(id));
        }
    }

    @Override
    public void remove(T element) throws RepositoryException {
        if (!this.findById(element.getId())) {
            throw new RepositoryException("Element inexistent !!");
        } else {
            this.list.remove(element);
        }
    }

    @Override
    public void Act(T element) throws RepositoryException {
        if (!this.findById(element.getId())) {
            throw new RepositoryException("Element inexistent !!");
        } else {
            this.list.set(this.list.indexOf(this.getElementById(element.getId())), element);
        }
    }
}
