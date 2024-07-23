package org.example.controller.popup;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.dto.custom.CategoryDTO;
import org.example.service.custom.CategoryService;
import org.example.service.util.OtherDependancies;
import org.example.service.util.ServiceFactory;
import org.example.service.util.ServiceType;
import org.example.tableModels.CategoryTM;
import org.example.util.exceptions.ServiceException;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

public class ManageCategoryFormController {
    public TextField txtCategoryId;
    public TextField txtCategoryName;
    public TableView<CategoryTM> tblCategories;
    public TableColumn<CategoryTM,Integer> colCategoryId;
    public TableColumn<CategoryTM,String> colCategoryName;

    CategoryService service = (CategoryService) ServiceFactory.getInstance().getService(ServiceType.CATEGORY_SERVICE);
    ModelMapper mapper = OtherDependancies.getInstance().getMapper();
    public void initialize(){
        setCellValueFactories();
        loadTableData();
    }

    private void setCellValueFactories(){
        colCategoryId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colCategoryName.setCellValueFactory(new PropertyValueFactory<>("name"));
    }

    private void loadTableData(){
        try {
            List<CategoryTM> list = service.getAll().stream().map(e -> mapper.map(e, CategoryTM.class)).toList();
            tblCategories.setItems(FXCollections.observableArrayList(list));
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
        CategoryDTO categoryDTO = collectData();
        try {
            boolean isAdded = service.add(categoryDTO);
            if (isAdded){
                new Alert(Alert.AlertType.INFORMATION,"Saved Successfully").show();
                clearFields();
                loadTableData();
            }else {
                new Alert(Alert.AlertType.ERROR,"Not Saved").show();
            }
        } catch (ServiceException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }

    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        CategoryDTO categoryDTO = collectData();
        if (categoryDTO.getId()==0){
            new Alert(Alert.AlertType.ERROR,"Invalid Id").show();
            return;
        }
        try {
            boolean isUpdated = service.update(categoryDTO);
            if (isUpdated){
                new Alert(Alert.AlertType.INFORMATION,"Updated").show();
                clearFields();
                loadTableData();
            }else {
                new Alert(Alert.AlertType.ERROR,"Not Updated").show();
            }
        } catch (ServiceException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        CategoryDTO categoryDTO = collectData();
        if (categoryDTO.getId()==0){
            new Alert(Alert.AlertType.ERROR,"Invalid Id").show();
            return;
        }

        Optional<ButtonType> buttonType = new Alert(Alert.AlertType.CONFIRMATION, "Are You Sure :(", ButtonType.YES, ButtonType.NO).showAndWait();
        if (buttonType.isPresent()){
            if (buttonType.get().equals(ButtonType.YES)){
                try {
                    boolean isDeleted = service.delete(categoryDTO.getId());
                    if (isDeleted){
                        new Alert(Alert.AlertType.INFORMATION,"Deleted").show();
                        loadTableData();
                    }else {
                        new Alert(Alert.AlertType.ERROR,"Not Deleted").show();
                    }
                } catch (ServiceException e) {
                    new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
                }
            }
        }


    }

    public void btnClearOnAction(ActionEvent actionEvent) {
        clearFields();
    }

    public void clearFields(){
        txtCategoryId.clear();
        txtCategoryName.clear();
    }

    private CategoryDTO collectData(){
        String idText = txtCategoryId.getText();
        String categoryName = txtCategoryName.getText();
        int id = 0;
        try {
            id = Integer.parseInt(idText);
        } catch (NumberFormatException ex) {
        }
        return CategoryDTO.builder().id(id).name(categoryName).build();
    }

    public void txtSearchIdOnAction(ActionEvent actionEvent) {
        CategoryDTO categoryDTO = collectData();
        try {
            Optional<CategoryDTO> search = service.search(categoryDTO.getId());
            if (search.isPresent()) {
                txtCategoryName.setText(search.get().getName());
            } else {
                new Alert(Alert.AlertType.ERROR, "Invalid Id").show();
            }
        } catch (ServiceException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }
}