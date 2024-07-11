package org.example.controller.sub;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.dto.custom.PublisherDTO;
import org.example.service.custom.PublisherService;
import org.example.service.custom.impl.PublisherServiceIMPL;
import org.example.tableModels.PublisherTM;
import org.example.util.exceptions.ServiceException;
import org.example.util.exceptions.custom.PublisherException;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ManageAuthorsAndPublishersController {
    public TextField txtPublisherId;
    public TextField txtPublisherName;
    public TextField txtPublisherLocation;
    public TextField txtPublisherContact;
    public TableView<PublisherTM> tblPublishers;
    public TableColumn<PublisherTM,Integer> colPublisherId;
    public TableColumn<PublisherTM,String> colPublisherName;
    public TableColumn<PublisherTM,String> colPublisherContact;
    public TextField txtAuthorId;
    public TextField txtAuthorName;
    public TextField txtAuthorContact;
    public TableView tblAuthors;
    public TableColumn colAuthorId;
    public TableColumn colAuthorName;
    public TableColumn colAuthorContact;

    private final PublisherService publisherService = new PublisherServiceIMPL();
    private final ModelMapper modelMapper = new ModelMapper();


    public void initialize() {
        txtPublisherId.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                txtPublisherId.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        visualizeData();
        loadTableData();
    }

    public void btnClearAuthorOnAction(ActionEvent actionEvent) {
    }

    public void btnDeleteAuthorOnAction(ActionEvent actionEvent) {
    }

    public void btnUpdateAuthorOnAction(ActionEvent actionEvent) {
    }

    public void btnSaveAuthorOnAction(ActionEvent actionEvent) {
    }

    public void btnClearPublisherOnAction(ActionEvent actionEvent) {
        clearPublisherFields();
    }

    private void clearPublisherFields(){
        txtPublisherId.clear();
        txtPublisherName.clear();
        txtPublisherContact.clear();
        txtPublisherLocation.clear();
    }

    public void btnDeletePublisherOnAction(ActionEvent actionEvent) {
        PublisherDTO publisherDTO = collectData();
        if (publisherDTO.getId() == 0){
            new Alert(Alert.AlertType.ERROR,"Invalid ID - Please Enter Valid Id").show();
            return;
        }
        System.out.println("Stopped");
        Optional<ButtonType> buttonType = new Alert(Alert.AlertType.CONFIRMATION, "Are You Sure", ButtonType.YES, ButtonType.NO).showAndWait();
        System.out.println("Continued");
        if (buttonType.isPresent()){
            if (buttonType.get().equals(ButtonType.YES)){
                try {
                    boolean delete = publisherService.delete(publisherDTO.getId());
                    if (delete){
                        new Alert(Alert.AlertType.INFORMATION,"Deleted Success").show();
                        loadTableData();
                    }else {
                        new Alert(Alert.AlertType.ERROR,"Not Deleted").show();
                    }
                } catch (ServiceException e) {
                    e.printStackTrace();
                    new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
                }
            }
        }
    }

    public void btnUpdatePublisherOnAction(ActionEvent actionEvent) {
        PublisherDTO publisherDTO = collectData();
        try {
            boolean update = publisherService.update(publisherDTO);
            if (update){
                new Alert(Alert.AlertType.INFORMATION,"Updated Success").show();
                clearPublisherFields();
                loadTableData();
            }else{
                new Alert(Alert.AlertType.ERROR,"Not Updated").show();
            }

        } catch (ServiceException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    public void btnSavePublisherOnAction(ActionEvent actionEvent) {
        PublisherDTO publisherDTO = collectData();
        try {
            boolean isSaved = publisherService.add(publisherDTO);
            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "Saved Success").show();
                clearPublisherFields();
                loadTableData();
            }else{
                new Alert(Alert.AlertType.ERROR,"Not Saved").show();
            }
        } catch (ServiceException e) {
            //if(e instanceof PublisherException){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
            //}
        }
    }

    private PublisherDTO collectData(){
        String id = txtPublisherId.getText();

        String name = txtPublisherName.getText();
        String text = txtPublisherContact.getText();
        String location = txtPublisherLocation.getText();

        int idNum = 0;
        try{
            idNum = Integer.parseInt(id);
        }catch (NumberFormatException e){
            //e.printStackTrace();
        }

        PublisherDTO publisherDTO = new PublisherDTO();
        publisherDTO.setId(idNum);
        publisherDTO.setName(name);
        publisherDTO.setLocation(location);
        publisherDTO.setContact(text);

        return publisherDTO;
    }

    public void txtPublisherIdOnAction(ActionEvent actionEvent) {
        PublisherDTO publisherDTO = collectData();
        try {
            Optional<PublisherDTO> search = publisherService.search(publisherDTO.getId());
            if (search.isPresent()){
                setDataToFields(search.get());
            }else{
                new Alert(Alert.AlertType.ERROR,"Publisher Not Found Or Invalid ID").show();
            }
        } catch (ServiceException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    private void setDataToFields(PublisherDTO publisherDTO) {
        txtPublisherId.setText(String.valueOf(publisherDTO.getId()));
        txtPublisherName.setText(publisherDTO.getName());
        txtPublisherContact.setText(publisherDTO.getContact());
        txtPublisherLocation.setText(publisherDTO.getLocation());
    }

    private void loadTableData(){
        try {
            List<PublisherDTO> all = publisherService.getAll();
            List<PublisherTM> list = new ArrayList<>();
            for (PublisherDTO publisherDTO : all) {
                list.add(convertDtoToTM(publisherDTO));
            }
            tblPublishers.setItems(FXCollections.observableArrayList(list));
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }

    private void visualizeData(){
        colPublisherId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colPublisherName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colPublisherContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
    }

    private PublisherTM convertDtoToTM(PublisherDTO obj){
        return modelMapper.map(obj, PublisherTM.class);
    }

}