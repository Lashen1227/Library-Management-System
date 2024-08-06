package org.example.controller.sub;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import org.example.service.util.OtherDependancies;
import org.example.service.util.ServiceFactory;
import org.example.service.util.ServiceType;
import org.example.tableModels.AuthorTMWithCheckBox;
import org.example.tableModels.BookTM;
import org.example.tableModels.CategoryTMWithCheckBox;
import org.example.util.exceptions.ServiceException;
import org.modelmapper.ModelMapper;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

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
    public TableView<BookTM> tblBook;
    public TableColumn<BookTM,Integer> colBookId;
    public TableColumn<BookTM,String> colBookName;
    public TableColumn<BookTM,String> colBookIsbn;
    public TableColumn<BookTM,Double> colBookPrice;
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
    private final ModelMapper mapper = OtherDependancies.getInstance().getMapper();


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
        loadBookTableData();
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

        colBookId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colBookName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colBookIsbn.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        colBookPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
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
        try {
            int bookId = Integer.parseInt(txtBookId.getText());
            Optional<BookDTO> search = bookService.search(bookId);
            if (search.isPresent()){
                setBookData(search.get());
            }
            //to be implemented
        }catch (NumberFormatException ex){

        } catch (ServiceException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    private void setBookData(BookDTO bookDTO) {
        clearBookFields();
        //set book name,isbn,price
        txtBookId.setText(String.valueOf(bookDTO.getId()));
        txtBookName.setText(bookDTO.getName());
        txtBookIsbn.setText(bookDTO.getIsbn());
        txtPrice.setText(String.valueOf(bookDTO.getPrice()));
        //set publisher
        ObservableList<PublisherCM> items = cmbPublisher.getItems();
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getId()==bookDTO.getPublisherId()){
                cmbPublisher.getSelectionModel().select(i);
                break;
            }
        }
        //set main category
        ObservableList<CategoryCM> categories = cmbMainCategory.getItems();
        for (int i = 0; i < categories.size(); i++) {
            if (categories.get(i).getId()==bookDTO.getMainCategoryId()){
                cmbMainCategory.getSelectionModel().select(i);
                break;
            }
        }
        //set sub categories
        ObservableList<CategoryTMWithCheckBox> subCategories = tblSubCategories.getItems();
        for (int i = 0; i < subCategories.size(); i++) {
            int id = subCategories.get(i).getId();
            if (bookDTO.getSubCategoryIds().contains(id)){
                subCategories.get(i).getCheckBox().setSelected(true);
            }
        }
        tblSubCategories.refresh();
        //set authors
        ObservableList<AuthorTMWithCheckBox> authors = tblAuthors.getItems();
        for (int i = 0; i < authors.size(); i++) {
            int id = authors.get(i).getId();
            if (bookDTO.getAuthors().contains(id)){
                authors.get(i).getCheckBox().setSelected(true);
            }
        }
        tblAuthors.refresh();
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
        //Find Selected Data in Author Table and Category Table
        BookDTO bookDTO = collectData();
        try {
            boolean add = bookService.add(bookDTO);
            if (add){
                new Alert(Alert.AlertType.INFORMATION, "Saved Success").show();
                clearBookFields();
                loadBookTableData();
            }
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadBookTableData() {
        try {
            List<BookTM> all = bookService.getAll().stream().map(e-> mapper.map(e,BookTM.class)).toList();
            tblBook.setItems(FXCollections.observableArrayList(all));
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }

    private void clearBookFields() {
        txtBookId.clear();
        txtBookName.clear();
        txtBookIsbn.clear();
        txtPrice.clear();
        cmbPublisher.getSelectionModel().clearSelection();
        cmbMainCategory.getSelectionModel().clearSelection();
        tblSubCategories.getItems().forEach(e->e.getCheckBox().setSelected(false));
        tblAuthors.getItems().forEach(e->e.getCheckBox().setSelected(false));
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        BookDTO bookDTO = collectData();
        try {
            boolean update = bookService.update(bookDTO);
            if (update){
                new Alert(Alert.AlertType.INFORMATION, "Updated Success").show();
                clearBookFields();
                loadBookTableData();
            }
        } catch (ServiceException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
    }

    public void btnClearOnAction(ActionEvent actionEvent) {
        clearBookFields();
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
            new Alert(Alert.AlertType.ERROR,"Please Enter Valid Price").show();
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