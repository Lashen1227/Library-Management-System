package org.example.controller.sub;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.service.custom.BookService;
import org.example.service.util.ServiceFactory;
import org.example.service.util.ServiceType;

import java.io.IOException;

public class ManageBookFormController {
    public TextField txtBookId;
    public TextField txtBookName;
    public TextField txtBookIsbn;
    public TextField txtPrice;
    public ComboBox cmbPublisher;
    public ComboBox cmbMainCategory;
    public TableView tblSubCategories;
    public TableColumn colCategoryName;
    public TableColumn colOption;
    public TableView tblBook;
    public TableColumn colBookId;
    public TableColumn colBookName;
    public TableColumn colBookIsbn;
    public TableColumn colBookPrice;
    public TableView tblAuthors;
    public TableColumn colAuthorName;
    public TableColumn colAuthorOption;



    private final BookService service = (BookService) ServiceFactory.getInstance()
            .getService(ServiceType.BOOK_SERVICE);


    public void bookIdOnAction(ActionEvent actionEvent) {
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
    }

    public void btnClearOnAction(ActionEvent actionEvent) {
    }

    public void btnManageCategoryOnAction(ActionEvent actionEvent) {
        try {
            Parent load = FXMLLoader.load(getClass().getResource("/view/popup/manage_category_form.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(load));

            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(txtBookId.getScene().getWindow());
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}