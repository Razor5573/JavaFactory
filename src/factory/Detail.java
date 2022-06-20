package factory;

import java.util.UUID;

public class Detail {
    private Integer ID;
    Detail(){
        generateID();
    }
    private void generateID (){
        ID = UUID.randomUUID().hashCode();
    }

    public Integer getID() {
        return ID;
    }
}
