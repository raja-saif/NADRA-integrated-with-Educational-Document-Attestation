package id_matrix.id_matrix;

// Document Class
class Document {
    protected String email;
    protected String Name;
    protected String image;
    protected String type;
    public Payment payment;
    public Document() {
    }

    ;

    public Document(String id, String Name, String image) {
        this.email = id;
        this.Name = Name;
        this.image = image;
    }

    public void fill() {
    }

    ;
}
