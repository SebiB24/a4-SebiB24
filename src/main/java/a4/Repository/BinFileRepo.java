package a4.Repository;

import a4.Domain.Entitate;
import java.io.*;
import java.util.ArrayList;

public class BinFileRepo <T extends Entitate> extends AbstractFileRepo<T> {

    public BinFileRepo(String fileName) {
        super(fileName);
        try {
            loadFile();
        } catch (RepositoryException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void saveFile() throws RepositoryException {

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(this.filepath))) {
            oos.writeObject(list);
        } catch (IOException e) {
            throw new RepositoryException(e.getMessage(), e);
        }

    }

    @Override
    protected void loadFile() throws RepositoryException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(this.filepath))){
            this.list = (ArrayList<T>) ois.readObject();
        } catch (FileNotFoundException exc) {
            System.out.println("Fisierul a fost initializat!");
        } catch (IOException | ClassNotFoundException exc) {
            throw new RepositoryException("Eroare la incarcarea fisierului", exc);
        }
    }
}
