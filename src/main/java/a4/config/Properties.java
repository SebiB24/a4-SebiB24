package a4.config;

import a4.Repository.RepositoryException;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Properties {
    private final Map<String, String> properties = new HashMap<>();

    public Properties(String filepath) throws RepositoryException {
        loadProperties(filepath);
    }

    private void loadProperties(String filepath) throws RepositoryException {
        try (FileInputStream fis = new FileInputStream(filepath)) {
            java.util.Properties prop = new java.util.Properties();
            prop.load(fis);

            for (String key : prop.stringPropertyNames()) {
                properties.put(key, prop.getProperty(key).replace("\"", "").trim());
            }
        } catch (IOException e) {
            throw new RepositoryException("Eroare la citirea fișierului de proprietăți: " + filepath, e);
        }
    }

    public String getRepositoryType() throws RepositoryException {
        String repoType = properties.get("Repository");
        if (repoType == null) {
            throw new RepositoryException("Lipsește tipul de repository în fișierul de proprietăți.");
        }
        return repoType;
    }

    public String getProdusFile() throws RepositoryException {
        String produsFile = properties.get("ProdusFile");
        if (produsFile == null) {
            throw new RepositoryException("Lipsește fișierul pentru produse în fișierul de proprietăți.");
        }
        return produsFile;
    }


    public String getComandaFile() throws RepositoryException {
        String comandaFile = properties.get("ComandaFile");
        if (comandaFile == null) {
            throw new RepositoryException("Lipsește fișierul pentru comenzi în fișierul de proprietăți.");
        }
        return comandaFile;
    }

    public String getURL() throws RepositoryException {
        String url = properties.get("URL");
        if (url == null) {
            throw new RepositoryException("Lipseste adresa url in fisierul de proprietati.");
        }
        return url;
    }

    public String getTipRulare() throws RepositoryException {
        String tiprulare = properties.get("TipRulare");
        if (tiprulare == null) {
            throw new RepositoryException("Lipseste adresa url in fisierul de proprietati.");
        }
        return tiprulare;
    }
}