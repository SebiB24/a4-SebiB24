package a4.Domain;

import java.io.Serializable;

public abstract class Entitate implements Serializable {

    protected  int id;

    Entitate(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String toString(){
        return Integer.toString(id);
    }

}
