package attributes;

public class MovieSearch {
    String sTitle,
            sFilmId,
            sDescription,
            sAFirstName,
            sALastName,
            sLengthMin,
            sLengthMax,
            sReplacementCostMin,
            sReplacementCostMax,
            sRentalDurationMin,
            sRentalDurationMax,
            sRentalRateMin,
            sRentalRateMax,
            sLastUpdate,
            sReleaseYear,

            sRating,
            sOriginalLanguage,
            sLanguage,
            sCategory;

    Boolean hasSFTrailer,
            hasSFCommentaries,
            hasSFBTS,
            hasSFDeletedScenes,
            inStore;

    public MovieSearch(String stitle, String sid, String sdescription, String saFirstName, String sALastName,
                       String slengthMin, String slengthMax, String sReplacementCostMin, String sReplacementCostMax,
                       String sRentalDurationMin, String sRentalDurationMax, String sRentalRateMin,
                       String sRentalRateMax, String sLastUpdate, String sReleaseYear, String sRating,
                       String sOriginalLanguage, String sLanguage, String sCategory, Boolean hasSFTrailer,
                       Boolean hasSFCommentaries, Boolean hasSFBTS, Boolean hasSFDeletedScenes, Boolean inStore) {
        this.sTitle = stitle;
        this.sFilmId = sid;
        this.sDescription = sdescription;
        this.sAFirstName = saFirstName;
        this.sALastName = sALastName;
        this.sLengthMin = slengthMin;
        this.sLengthMax = slengthMax;
        this.sReplacementCostMin = sReplacementCostMin;
        this.sReplacementCostMax = sReplacementCostMax;
        this.sRentalDurationMin = sRentalDurationMin;
        this.sRentalDurationMax = sRentalDurationMax;
        this.sRentalRateMin = sRentalRateMin;
        this.sRentalRateMax = sRentalRateMax;
        this.sLastUpdate = sLastUpdate;
        this.sReleaseYear = sReleaseYear;
        this.sRating = sRating;
        this.sOriginalLanguage = sOriginalLanguage;
        this.sLanguage = sLanguage;
        this.sCategory = sCategory;
        this.hasSFTrailer = hasSFTrailer;
        this.hasSFCommentaries = hasSFCommentaries;
        this.hasSFBTS = hasSFBTS;
        this.hasSFDeletedScenes = hasSFDeletedScenes;
        this.inStore = inStore;
    }

    public MovieSearch() {
    }

    public String getsTitle() {
        return sTitle;
    }

    public void setsTitle(String sTitle) {
        this.sTitle = sTitle;
    }

    public String getsFilmId() {
        return sFilmId;
    }

    public void setsFilmId(String sFilmId) {
        this.sFilmId = sFilmId;
    }

    public String getsDescription() {
        return sDescription;
    }

    public void setsDescription(String sDescription) {
        this.sDescription = sDescription;
    }

    public String getsAFirstName() {
        return sAFirstName;
    }

    public void setsAFirstName(String sAFirstName) {
        this.sAFirstName = sAFirstName;
    }

    public String getsALastName() {
        return sALastName;
    }

    public void setsALastName(String sALastName) {
        this.sALastName = sALastName;
    }

    public String getsLengthMin() {
        return sLengthMin;
    }

    public void setsLengthMin(String sLengthMin) {
        this.sLengthMin = sLengthMin;
    }

    public String getsLengthMax() {
        return sLengthMax;
    }

    public void setsLengthMax(String sLengthMax) {
        this.sLengthMax = sLengthMax;
    }

    public String getsReplacementCostMin() {
        return sReplacementCostMin;
    }

    public void setsReplacementCostMin(String sReplacementCostMin) {
        this.sReplacementCostMin = sReplacementCostMin;
    }

    public String getsReplacementCostMax() {
        return sReplacementCostMax;
    }

    public void setsReplacementCostMax(String sReplacementCostMax) {
        this.sReplacementCostMax = sReplacementCostMax;
    }

    public String getsRentalDurationMin() {
        return sRentalDurationMin;
    }

    public void setsRentalDurationMin(String sRentalDurationMin) {
        this.sRentalDurationMin = sRentalDurationMin;
    }

    public String getsRentalDurationMax() {
        return sRentalDurationMax;
    }

    public void setsRentalDurationMax(String sRentalDurationMax) {
        this.sRentalDurationMax = sRentalDurationMax;
    }

    public String getsRentalRateMin() {
        return sRentalRateMin;
    }

    public void setsRentalRateMin(String sRentalRateMin) {
        this.sRentalRateMin = sRentalRateMin;
    }

    public String getsRentalRateMax() {
        return sRentalRateMax;
    }

    public void setsRentalRateMax(String sRentalRateMax) {
        this.sRentalRateMax = sRentalRateMax;
    }

    public String getsLastUpdate() {
        return sLastUpdate;
    }

    public void setsLastUpdate(String sLastUpdate) {
        this.sLastUpdate = sLastUpdate;
    }

    public String getsReleaseYear() {
        return sReleaseYear;
    }

    public void setsReleaseYear(String sReleaseYear) {
        this.sReleaseYear = sReleaseYear;
    }

    public String getsRating() {
        return sRating;
    }

    public void setsRating(String sRating) {
        this.sRating = sRating;
    }

    public String getsOriginalLanguage() {
        return sOriginalLanguage;
    }

    public void setsOriginalLanguage(String sOriginalLanguage) {
        this.sOriginalLanguage = sOriginalLanguage;
    }

    public String getsLanguage() {
        return sLanguage;
    }

    public void setsLanguage(String sLanguage) {
        this.sLanguage = sLanguage;
    }

    public String getsCategory() {
        return sCategory;
    }

    public void setsCategory(String sCategory) {
        this.sCategory = sCategory;
    }

    public Boolean getHasSFTrailer() {
        return hasSFTrailer;
    }

    public void setHasSFTrailer(Boolean hasSFTrailer) {
        this.hasSFTrailer = hasSFTrailer;
    }

    public Boolean getHasSFCommentaries() {
        return hasSFCommentaries;
    }

    public void setHasSFCommentaries(Boolean hasSFCommentaries) {
        this.hasSFCommentaries = hasSFCommentaries;
    }

    public Boolean getHasSFBTS() {
        return hasSFBTS;
    }

    public void setHasSFBTS(Boolean hasSFBTS) {
        this.hasSFBTS = hasSFBTS;
    }

    public Boolean getHasSFDeletedScenes() {
        return hasSFDeletedScenes;
    }

    public void setHasSFDeletedScenes(Boolean hasSFDeletedScenes) {
        this.hasSFDeletedScenes = hasSFDeletedScenes;
    }

    public Boolean getInStore() {
        return inStore;
    }

    public void setInStore(Boolean inStore) {
        this.inStore = inStore;
    }
}
