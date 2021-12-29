public class FilmSearchResults {
    Integer id;
    String title;
    String description;
    FilmSearchResults(Integer id, String title, String description) {
        this.id = new Integer(id);
        this.title = new String(title);
        this.description = new String(description);
    }

    public int getId() {
        return id;
    }
    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
