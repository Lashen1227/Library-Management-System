package org.example.controller.sub;

import javafx.event.ActionEvent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.example.repo.custom.BookRepo;
import org.example.repo.util.RepoFactory;
import org.example.repo.util.RepoTypes;
import org.example.service.custom.BookService;
import org.example.service.custom.impl.BookServiceIMPL;
import org.example.service.util.OtherDependancies;
import org.example.service.util.ServiceFactory;
import org.example.service.util.ServiceType;
import org.modelmapper.ModelMapper;

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