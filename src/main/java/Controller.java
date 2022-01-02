public class Controller {
    FXBuilder fxBuilder;

    public Controller(FXBuilder fxBuilder) {
        this.fxBuilder = fxBuilder;
    }

    public void callError(String errorMessage){
        fxBuilder.createErrorPopup(errorMessage);
    }

    public FXBuilder getFxBuilder() {
        return fxBuilder;
    }

    public void setFxBuilder(FXBuilder fxBuilder) {
        this.fxBuilder = fxBuilder;
    }
}
