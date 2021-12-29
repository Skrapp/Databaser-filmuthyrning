public class Controller {
    FXBuilder fxBuilder;

    public Controller(FXBuilder fxBuilder) {
        this.fxBuilder = fxBuilder;
    }

    public void callError(String errorMessage){
        fxBuilder.createErrorPopup(errorMessage);
    }
}
