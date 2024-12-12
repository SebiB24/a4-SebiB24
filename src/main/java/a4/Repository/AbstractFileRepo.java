package a4.Repository;

import a4.Domain.Entitate;

import java.text.ParseException;

public abstract class AbstractFileRepo<T extends Entitate> extends Repository<T> {
    protected String filepath;

    public AbstractFileRepo(String filepath) {
        this.filepath = filepath;
    }

    @Override
    public void add(T elem) throws RepositoryException {
        super.add(elem);
        saveFile();
    }

    @Override
    public void remove(T elem) throws RepositoryException {
        super.remove(elem);
        saveFile();
    }

    @Override
    public void removeById(int id) throws RepositoryException {
        super.removeById(id);
        saveFile();
    }

    @Override
    public void Act(T elem) throws RepositoryException {
        super.Act(elem);
        saveFile();
    }

    protected abstract void saveFile() throws RepositoryException;

    protected abstract void loadFile() throws RepositoryException, ParseException;

}