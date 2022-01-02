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
}
