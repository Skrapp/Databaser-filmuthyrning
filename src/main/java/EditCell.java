import javafx.scene.control.Hyperlink;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

public class EditCell implements Callback<TableColumn<CustomerSearchResults, Hyperlink>, TableCell<CustomerSearchResults, Hyperlink>> {

        @Override
                public TableCell<CustomerSearchResults, Hyperlink> call(TableColumn<CustomerSearchResults, Hyperlink> arg) {
                        TableCell<CustomerSearchResults, Hyperlink> cell = new TableCell<CustomerSearchResults, Hyperlink>() {
                                @Override
                                protected void updateItem(Hyperlink item, boolean empty) {
                                        setGraphic(item);
                                }
                        };
                        return cell;
        }
}
