package org.example.controller.sub;

import javafx.event.ActionEvent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.example.service.custom.BookService;
import org.example.service.custom.impl.BookServiceIMPL;

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

    private final BookService service = new BookServiceIMPL();


    public void bookIdOnAcion(ActionEvent actionEvent) {
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
    }

    public void btnClearOnAction(ActionEvent actionEvent) {
    }
}