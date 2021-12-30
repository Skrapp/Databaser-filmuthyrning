package attributes;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public class MovieInfo {
    private String title,
            description,
            rating,
            originalLanguage,
            language,
            category,
            specialFeatures;

    private List<String> ActorList;

    private BigDecimal replacementCost,
            rentalRate;

    private Short length,
            filmId;

    private Byte rentalDuration;

    private Date releaseYear;
        private Timestamp lastUpdate;

    private List<Integer> inventoryList;

    private List<Short> StoreIdList;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSpecialFeatures() {
        return specialFeatures;
    }

    public void setSpecialFeatures(String specialFeatures) {
        this.specialFeatures = specialFeatures;
    }

    public List<String> getActorList() {
        return ActorList;
    }

    public void setActorList(List<String> actorList) {
        ActorList = actorList;
    }

    public Short getLength() {
        return length;
    }

    public void setLength(Short length) {
        this.length = length;
    }

    public BigDecimal getReplacementCost() {
        return replacementCost;
    }

    public void setReplacementCost(BigDecimal replacementCost) {
        this.replacementCost = replacementCost;
    }

    public Byte getRentalDuration() {
        return rentalDuration;
    }

    public void setRentalDuration(Byte rentalDuration) {
        this.rentalDuration = rentalDuration;
    }

    public BigDecimal getRentalRate() {
        return rentalRate;
    }

    public void setRentalRate(BigDecimal rentalRate) {
        this.rentalRate = rentalRate;
    }

    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public int getFilmId() {
        return filmId;
    }

    public void setFilmId(Short filmId) {
        this.filmId = filmId;
    }

    public Date getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(Date releaseYear) {
        this.releaseYear = releaseYear;
    }

    public List<Integer> getInventoryList() {
        return inventoryList;
    }

    public void setInventoryList(List<Integer> inventoryList) {
        this.inventoryList = inventoryList;
    }

    public List<Short> getStoreIdList() {
        return StoreIdList;
    }

    public void setStoreIdList(List<Short> storeIdList) {
        this.StoreIdList = storeIdList;
    }

    public MovieInfo() {
    }
}
