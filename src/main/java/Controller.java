public class Controller {
    FXBuilder fxBuilder = new FXBuilder();

    public void callError(String errorMessage){
        fxBuilder.createErrorPopup(errorMessage);
    }
}
