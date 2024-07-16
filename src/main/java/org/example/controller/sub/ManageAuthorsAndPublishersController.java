package org.example.controller.sub;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.dto.custom.AuthorDTO;
import org.example.dto.custom.PublisherDTO;
import org.example.entity.custom.Author;
import org.example.repo.custom.AuthorRepo;
import org.example.service.custom.AuthorService;
import org.example.service.custom.PublisherService;
import org.example.service.custom.impl.AuthorServiceIMPL;
import org.example.service.custom.impl.PublisherServiceIMPL;
import org.example.tableModels.AuthorTM;
import org.example.tableModels.PublisherTM;
import org.example.util.exceptions.ServiceException;
import org.example.repo.custom.PublisherRepo;
import org.example.repo.util.RepoFactory;
import org.example.repo.util.RepoTypes;
import org.example.util.exceptions.custom.PublisherException;
import org.example.util.exceptions.custom.AuthorException;
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
    public TableView<AuthorTM> tblAuthors;
    public TableColumn<AuthorTM,Integer> colAuthorId;
    public TableColumn<AuthorTM,String> colAuthorName;
    public TableColumn<AuthorTM,String> colAuthorContact;

    PublisherRepo publisherRepo = RepoFactory.getInstance().getRepo(RepoTypes.PUBLISHER_REPO);
    private final PublisherService publisherService = new PublisherServiceIMPL(publisherRepo);
    private final ModelMapper modelMapper = new ModelMapper();
    AuthorRepo authorRepo = RepoFactory.getInstance().getRepo(RepoTypes.AUTHOR_REPO);
    private final AuthorService authorService = new AuthorServiceIMPL(authorRepo);
    private final ModelMapper modelAuthorMapper = new ModelMapper();

    public void initialize() {
        // Publisher
        txtPublisherId.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                txtPublisherId.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        visualizeData();
        loadTableData();

        // Author
        txtAuthorId.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                txtAuthorId.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        visualizeAuthorData();
        loadAuthorTableData();
    }

    public void btnClearAuthorOnAction(ActionEvent actionEvent) {
        clearAuthorFields();
    }

    public void btnDeleteAuthorOnAction(ActionEvent actionEvent) {
        AuthorDTO authorDTO = collectAuthorData();
        if (authorDTO.getId() == 0){
            new Alert(Alert.AlertType.ERROR,"Invalid ID - Please Enter Valid Id").show();
            return;
        }
        System.out.println("Stopped");
        Optional<ButtonType> buttonType = new Alert(Alert.AlertType.CONFIRMATION, "Are You Sure", ButtonType.YES, ButtonType.NO).showAndWait();
        System.out.println("Continued");
        if (buttonType.isPresent()){
            if (buttonType.get().equals(ButtonType.YES)){
                try {
                    boolean delete = authorService.delete(authorDTO.getId());
                    if (delete){
                        new Alert(Alert.AlertType.INFORMATION,"Deleted Success").show();
                        loadAuthorTableData();
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

    public void btnUpdateAuthorOnAction(ActionEvent actionEvent) {
        AuthorDTO authorDTO = collectAuthorData();
        try {
            boolean update = authorService.update(authorDTO);
            if (update){
                new Alert(Alert.AlertType.INFORMATION,"Updated Success").show();
                clearAuthorFields();
                loadAuthorTableData();
            }else{
                new Alert(Alert.AlertType.ERROR,"Not Updated").show();
            }

        } catch (ServiceException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    public void btnSaveAuthorOnAction(ActionEvent actionEvent) {
        AuthorDTO authorDTO = collectAuthorData();
        try {
            boolean isSaved = authorService.add(authorDTO);
            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "Saved Success").show();
                clearAuthorFields();
                loadAuthorTableData();
            }else{
                new Alert(Alert.AlertType.ERROR,"Not Saved").show();
            }
        } catch (ServiceException e) {
            //if(e instanceof PublisherException){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
            //}
        }
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

    private void clearAuthorFields(){
        txtAuthorId.clear();
        txtAuthorName.clear();
        txtAuthorContact.clear();
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

    private AuthorDTO collectAuthorData(){
        String id = txtAuthorId.getText();
        String name = txtAuthorName.getText();
        String text = txtAuthorContact.getText();

        int idNum = 0;
        try{
            idNum = Integer.parseInt(id);
        }catch (NumberFormatException e){
            //e.printStackTrace();
        }

        AuthorDTO authorDTO = new AuthorDTO();
        authorDTO.setId(idNum);
        authorDTO.setName(name);
        authorDTO.setContact(text);

        return authorDTO;
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

    public void txtAuthorIdOnAction(ActionEvent actionEvent) {
        AuthorDTO authorDTO = collectAuthorData();
        try {
            Optional<AuthorDTO> search = authorService.search(authorDTO.getId());
            if (search.isPresent()){
                setAuthorDataToFields(search.get());
            }else{
                new Alert(Alert.AlertType.ERROR,"Author Not Found Or Invalid ID").show();
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

    private void setAuthorDataToFields(AuthorDTO authorDTO) {
        txtAuthorId.setText(String.valueOf(authorDTO.getId()));
        txtAuthorName.setText(authorDTO.getName());
        txtAuthorContact.setText(authorDTO.getContact());
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

    private void loadAuthorTableData(){
        try {
            List<AuthorDTO> all = authorService.getAll();
            List<AuthorTM> list = new ArrayList<>();
            for (AuthorDTO authorDTO : all) {
                list.add(convertAuthorDtoToTM(authorDTO));
            }
            tblAuthors.setItems(FXCollections.observableArrayList(list));
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }

    private void visualizeData(){
        colPublisherId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colPublisherName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colPublisherContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
    }

    private void visualizeAuthorData(){
        colAuthorId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colAuthorName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAuthorContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
    }

    private PublisherTM convertDtoToTM(PublisherDTO obj){
        return modelMapper.map(obj, PublisherTM.class);
    }

    private AuthorTM convertAuthorDtoToTM(AuthorDTO obj){
        return modelAuthorMapper.map(obj, AuthorTM.class);
    }

}