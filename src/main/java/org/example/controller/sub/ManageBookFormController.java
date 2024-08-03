package org.example.controller.sub;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import org.example.cm.CategoryCM;
import org.example.cm.PublisherCM;
import org.example.controller.popup.ManageCategoryFormController;
import org.example.dto.custom.BookDTO;
import org.example.service.custom.AuthorService;
import org.example.service.custom.BookService;
import org.example.service.custom.CategoryService;
import org.example.service.custom.PublisherService;
import org.example.service.util.ServiceFactory;
import org.example.service.util.ServiceType;
import org.example.tableModels.AuthorTMWithCheckBox;
import org.example.tableModels.CategoryTMWithCheckBox;
import org.example.util.exceptions.ServiceException;

import java.io.IOException;
import java.util.List;

public class ManageBookFormController {
    public TextField txtBookId;
    public TextField txtBookName;
    public TextField txtBookIsbn;
    public TextField txtPrice;
    public ComboBox<PublisherCM> cmbPublisher;
    public ComboBox<CategoryCM> cmbMainCategory;

    public TableView<CategoryTMWithCheckBox> tblSubCategories;
    public TableColumn<CategoryTMWithCheckBox,String> colCategoryName;
    public TableColumn<CategoryTMWithCheckBox, CheckBox> colOption;

    public TableView tblBook;
    public TableColumn colBookId;
    public TableColumn colBookName;
    public TableColumn colBookIsbn;
    public TableColumn colBookPrice;

    public TableView<AuthorTMWithCheckBox> tblAuthors;
    public TableColumn<AuthorTMWithCheckBox,String> colAuthorName;
    public TableColumn<AuthorTMWithCheckBox,CheckBox> colAuthorOption;

    private final PublisherService publisherService = (PublisherService) ServiceFactory.getInstance()
            .getService(ServiceType.PUBLISHER_SERVICE);
    private final CategoryService categoryService = (CategoryService) ServiceFactory.getInstance()
            .getService(ServiceType.CATEGORY_SERVICE);
    private final AuthorService authorService = (AuthorService) ServiceFactory.getInstance()
            .getService(ServiceType.AUTHOR_SERVICE);
    private final BookService bookService = (BookService) ServiceFactory.getInstance()
            .getService(ServiceType.BOOK_SERVICE);

    public void initialize(){
        addConverterToComboBox();
        setCellValueFactories();
        try {
            List<PublisherCM> publishers = publisherService.getAll().stream().map(e -> PublisherCM.builder().id(e.getId()).name(e.getName()).build()).toList();
            cmbPublisher.setItems(FXCollections.observableArrayList(publishers));

            loadCategoryData();
            loadAuthorData();
        } catch (ServiceException e) {
            System.out.println(e);
        }
    }

    public void loadCategoryData() throws ServiceException {
        List<CategoryCM> categories = categoryService.getAll().stream().map(e -> CategoryCM.builder().id(e.getId()).name(e.getName()).build()).toList();
        cmbMainCategory.setItems(FXCollections.observableArrayList(categories));


        List<CategoryTMWithCheckBox> list = categories.stream().map(e -> CategoryTMWithCheckBox.builder().id(e.getId()).name(e.getName()).checkBox(new CheckBox()).build()).toList();
        tblSubCategories.setItems(FXCollections.observableArrayList(list));
    }

    private void loadAuthorData() throws ServiceException {
        List<AuthorTMWithCheckBox> list = authorService.getAll().stream().map(e -> AuthorTMWithCheckBox.builder().id(e.getId()).name(e.getName()).checkBox(new CheckBox()).build()).toList();
        tblAuthors.setItems(FXCollections.observableArrayList(list));
    }

    public void setCellValueFactories(){
        colCategoryName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colOption.setCellValueFactory(new PropertyValueFactory<>("checkBox"));


        colAuthorName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAuthorOption.setCellValueFactory(new PropertyValueFactory<>("checkBox"));
    }


    public void addConverterToComboBox(){
        cmbPublisher.setConverter(new StringConverter<PublisherCM>() {
            @Override
            public String toString(PublisherCM publisherCM) {
                return publisherCM.getName();
            }

            @Override
            public PublisherCM fromString(String s) {
                return null;
            }
        });

        cmbMainCategory.setConverter(new StringConverter<CategoryCM>() {
            @Override
            public String toString(CategoryCM categoryCM) {
                return categoryCM.getName();
            }

            @Override
            public CategoryCM fromString(String s) {
                return null;
            }
        });
    }


    public void bookIdOnAction(ActionEvent actionEvent) {
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
        //Find Selected Data in Author Table and Category Table
        BookDTO bookDTO = collectData();
        try {
            bookService.add(bookDTO);
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }


    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
    }

    public void btnClearOnAction(ActionEvent actionEvent) {
    }

    public void btnManageCategoryOnAction(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/popup/manage_category_form.fxml"));
            Parent load = loader.load();
            ManageCategoryFormController controller = loader.getController();
            controller.setBaseController(this);
            Stage stage = new Stage();
            stage.setScene(new Scene(load));

            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(txtBookId.getScene().getWindow());
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public BookDTO collectData(){
        int bookId = 0;
        String bookName = txtBookName.getText();
        String isbnNumber = txtBookIsbn.getText();
        double price = 0;
        int publisherId = 0;
        int mainCategoryId = 0;
        int count=0;
        try {
            bookId = Integer.parseInt(txtBookId.getText());
        }catch (NumberFormatException e){}
        try {
            price = Double.parseDouble(txtPrice.getText());
        }catch (NumberFormatException e){
            new Alert(Alert.AlertType.ERROR,"Enter valid Price").show();
            return null;
        }
        try {
            publisherId= cmbPublisher.getSelectionModel().getSelectedItem().getId();
            count++;
            mainCategoryId= cmbMainCategory.getSelectionModel().getSelectedItem().getId();
        }catch (NullPointerException ex){
            String er = count== 0 ?  "Publisher" : "Category";
            new Alert(Alert.AlertType.ERROR , "Select "+er ).show();
        }
        /*ArrayList<Integer> authorIds = new ArrayList<>();
        ObservableList<AuthorTMWithCheckBox> items = tblAuthors.getItems();
        for (AuthorTMWithCheckBox item : items) {
            if (item.getCheckBox().isSelected()){
                authorIds.add(item.getId());
            }
        }*/

        List<Integer> authorIds = tblAuthors.getItems().stream().filter(e -> e.getCheckBox().isSelected()).map(e -> e.getId()).toList();
        List<Integer> subCategoryIds = tblSubCategories.getItems().stream().filter(e -> e.getCheckBox().isSelected()).map(e -> e.getId()).toList();
        return BookDTO.builder().id(bookId).name(bookName).isbn(isbnNumber).price(price)
                .publisherId(publisherId).mainCategoryId(mainCategoryId).authors(authorIds)
                .subCategoryIds(subCategoryIds).build();
    }

}