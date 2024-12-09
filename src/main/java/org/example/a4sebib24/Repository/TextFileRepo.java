package org.example.a4sebib24.Repository;

import org.example.a4sebib24.Domain.Entitate;
import org.example.a4sebib24.Domain.IEntitateFactory;
import java.io.*;
import java.text.ParseException;

public class TextFileRepo<T extends Entitate> extends AbstractFileRepo<T> {

    protected IEntitateFactory<T> entitateFactory;

    public TextFileRepo(String fileName, IEntitateFactory<T> converter) throws ParseException {
        super(fileName);
        this.entitateFactory = converter;
        try {
            loadFile();
        } catch (RepositoryException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void saveFile() throws RepositoryException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(this.filepath))) {
            for (var entitate : this.list) {
                String asString = entitateFactory.toString((T) entitate);
                bw.write(asString);
                bw.newLine();
            }
        } catch (IOException e) {
            throw new RepositoryException("Eroare la scrierea fisierului", e);
        }
    }

    @Override
    protected void loadFile() throws RepositoryException, ParseException {
        try (BufferedReader br = new BufferedReader(new FileReader(this.filepath))) {
            String line = br.readLine();
            while (line != null) {
                list.add(entitateFactory.fromString(line));
                line = br.readLine();
            }
        } catch (IOException e) {
            throw new RepositoryException("Eroare la citirea fisierului", e);
        }
    }
}