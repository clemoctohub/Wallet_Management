package napier.example.napierproject3_api21;

public class Account {
    private String id,id_client,name;

    public Account(String id, String id_client, String name){
        this.id = id;
        this.id_client = id_client;
        this.name = name;
    }

    public String getId(){
        return id;
    }

    public String getName() {
        return name;
    }

    public String getId_client() {
        return id_client;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setId_client(String id_client) {
        this.id_client = id_client;
    }
}
