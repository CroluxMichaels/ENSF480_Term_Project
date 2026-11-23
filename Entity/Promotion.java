package Entity;

public class Promotion {
    private int id;
    private String body;
    private String endDate;

    public int getId() {
        return id;
    }

    public String getBody() {
        return body;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Promotion(int id, String body, String endDate){
        this.id = id;
        this.body = body;
        this.endDate = endDate;
    }
}
