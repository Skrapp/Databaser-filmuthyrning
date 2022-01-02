package attributes;

public class MovieOperatingLanguage implements OperatingLanguage {

    private String title,
            filmId,
            description,
            rating,
            category,
            originalLanguage,
            language,
            specialFeatures,
            ActorList,
            rentalRate,
            replacementCost,
            rentalDuration,
            length,
            releaseYear,
            inventoryList,
            StoreIdList,
            lastUpdate;

    public MovieOperatingLanguage() {
    }

    public MovieOperatingLanguage(String title, String description, String rating, String originalLanguage, String language, String category, String specialFeatures, String ActorList, String replacementCost, String rentalRate, String length, String filmId, String rentalDuration, String releaseYear, String lastUpdate, String inventoryList, String StoreIdList) {
        this.title = title;
        this.description = description;
        this.rating = rating;
        this.originalLanguage = originalLanguage;
        this.language = language;
        this.category = category;
        this.specialFeatures = specialFeatures;
        this.ActorList = ActorList;
        this.replacementCost = replacementCost;
        this.rentalRate = rentalRate;
        this.length = length;
        this.filmId = filmId;
        this.rentalDuration = rentalDuration;
        this.releaseYear = releaseYear;
        this.lastUpdate = lastUpdate;
        this.inventoryList = inventoryList;
        this.StoreIdList = StoreIdList;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFilmId() {
        return filmId;
    }

    public void setFilmId(String filmId) {
        this.filmId = filmId;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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

    public String getSpecialFeatures() {
        return specialFeatures;
    }

    public void setSpecialFeatures(String specialFeatures) {
        this.specialFeatures = specialFeatures;
    }

    public String getActorList() {
        return ActorList;
    }

    public void setActorList(String actorList) {
        ActorList = actorList;
    }

    public String getRentalRate() {
        return rentalRate;
    }

    public void setRentalRate(String rentalRate) {
        this.rentalRate = rentalRate;
    }

    public String getReplacementCost() {
        return replacementCost;
    }

    public void setReplacementCost(String replacementCost) {
        this.replacementCost = replacementCost;
    }

    public String getRentalDuration() {
        return rentalDuration;
    }

    public void setRentalDuration(String rentalDuration) {
        this.rentalDuration = rentalDuration;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(String releaseYear) {
        this.releaseYear = releaseYear;
    }

    public String getInventoryList() {
        return inventoryList;
    }

    public void setInventoryList(String inventoryList) {
        this.inventoryList = inventoryList;
    }

    public String getStoreIdList() {
        return StoreIdList;
    }

    public void setStoreIdList(String storeIdList) {
        StoreIdList = storeIdList;
    }

    public String getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}
