package org.example.controller.sub;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import org.example.dto.custom.AuthorDTO;
import org.example.dto.custom.PublisherDTO;
import org.example.service.custom.AuthorService;
import org.example.service.custom.PublisherService;
import org.example.service.util.OtherDependancies;
import org.example.service.util.ServiceFactory;
import org.example.service.util.ServiceType;
import org.example.tableModels.AuthorTM;
import org.example.tableModels.PublisherTM;
import org.example.util.exceptions.ServiceException;
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
    public TableColumn<PublisherTM, Integer> colPublisherId;
    public TableColumn<PublisherTM, String> colPublisherName;
    public TableColumn<PublisherTM, String> colPublisherContact;
    public TextField txtAuthorId;
    public TextField txtAuthorName;
    public TextField txtAuthorContact;
    public TableView<AuthorTM> tblAuthors;
    public TableColumn<AuthorTM,Integer> colAuthorId;
    public TableColumn<AuthorTM,String> colAuthorName;
    public TableColumn<AuthorTM,String> colAuthorContact;


    private final PublisherService publisherService = (PublisherService) ServiceFactory.getInstance()
            .getService(ServiceType.PUBLISHER_SERVICE);

    private final ModelMapper modelMapper = OtherDependancies.getInstance().getMapper();

    private final AuthorService authorService = (AuthorService) ServiceFactory.getInstance()
            .getService(ServiceType.AUTHOR_SERVICE);

    public void initialize() {
        txtPublisherId.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                txtPublisherId.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        visualizeData();
        loadPublisherTableData();
        loadAuthorTableData();
    }

    public void btnClearAuthorOnAction(ActionEvent actionEvent) {
        clearAuthorFields();
    }

    public void clearAuthorFields() {
        txtAuthorId.clear();
        txtAuthorName.clear();
        txtAuthorContact.clear();
    }

    public void btnDeleteAuthorOnAction(ActionEvent actionEvent) {
        AuthorDTO authorDTO = collectAuthorData();
        if (authorDTO.getId() == 0) {
            new Alert(Alert.AlertType.ERROR, "Invalid ID - please Enter valid Id").show();
            return;
        }
        Optional<ButtonType> pressedButton = new Alert(Alert.AlertType.CONFIRMATION, "Are You Sure", ButtonType.YES, ButtonType.NO).showAndWait();
        if (pressedButton.isPresent()) {
            ButtonType buttonType = pressedButton.get();
            if (buttonType.equals(ButtonType.YES)) {
                try {
                    boolean isDeleted = authorService.delete(authorDTO.getId());
                    if (isDeleted) {
                        new Alert(Alert.AlertType.INFORMATION, "Deleted Success").show();
                        clearAuthorFields();
                        loadAuthorTableData();
                    } else {
                        new Alert(Alert.AlertType.ERROR, "Not Deleted").show();
                    }
                } catch (ServiceException e) {
                    new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
                }
            }
        }


    }

    public void btnUpdateAuthorOnAction(ActionEvent actionEvent) {
        AuthorDTO authorDTO = collectAuthorData();
        if (authorDTO.getId() == 0) {
            new Alert(Alert.AlertType.ERROR, "Invalid ID - please Enter valid Id").show();
            return;
        }
        try {
            boolean isUpdated = authorService.update(authorDTO);
            if (isUpdated) {
                new Alert(Alert.AlertType.INFORMATION, "Updated Success").show();
                clearAuthorFields();
                loadAuthorTableData();
            } else {
                new Alert(Alert.AlertType.ERROR, "Not Updated").show();
            }
        } catch (ServiceException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    public void btnSaveAuthorOnAction(ActionEvent actionEvent) {
        AuthorDTO authorDTO = collectAuthorData();
        try {
            boolean isAdded = authorService.add(authorDTO);
            if (isAdded) {
                new Alert(Alert.AlertType.INFORMATION, "Saved Success").show();
                clearAuthorFields();
                loadAuthorTableData();
            } else {
                new Alert(Alert.AlertType.ERROR, "Not Saved").show();
            }
        } catch (ServiceException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void btnClearPublisherOnAction(ActionEvent actionEvent) {
        clearPublisherFields();
    }

    private void clearPublisherFields() {
        txtPublisherId.clear();
        txtPublisherName.clear();
        txtPublisherContact.clear();
        txtPublisherLocation.clear();
    }

    public void btnDeletePublisherOnAction(ActionEvent actionEvent) {
        PublisherDTO publisherDTO = collectPublisherData();
        if (publisherDTO.getId() == 0) {
            new Alert(Alert.AlertType.ERROR, "Invalid ID - please Enter valid Id").show();
            return;
        }
        System.out.println("Stopped");
        Optional<ButtonType> buttonType = new Alert(Alert.AlertType.CONFIRMATION, "Are You Sure", ButtonType.YES, ButtonType.NO).showAndWait();
        System.out.println("Continued");
        if (buttonType.isPresent()) {
            if (buttonType.get().equals(ButtonType.YES)) {
                try {
                    boolean delete = publisherService.delete(publisherDTO.getId());
                    if (delete) {
                        new Alert(Alert.AlertType.INFORMATION, "Deleted Success").show();
                        loadPublisherTableData();
                    } else {
                        new Alert(Alert.AlertType.ERROR, "Not Deleted").show();
                    }
                } catch (ServiceException e) {
                    e.printStackTrace();
                    new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
                }
            }
        }


    }

    public void btnUpdatePublisherOnAction(ActionEvent actionEvent) {
        PublisherDTO publisherDTO = collectPublisherData();
        try {
            boolean update = publisherService.update(publisherDTO);
            if (update) {
                new Alert(Alert.AlertType.INFORMATION, "Updated Success").show();
                clearPublisherFields();
                loadPublisherTableData();
            } else {
                new Alert(Alert.AlertType.ERROR, "Not Updated").show();
            }

        } catch (ServiceException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void btnSavePublisherOnAction(ActionEvent actionEvent) {
        PublisherDTO publisherDTO = collectPublisherData();
        try {
            boolean isSaved = publisherService.add(publisherDTO);
            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "Saved Success").show();
                clearPublisherFields();
                loadPublisherTableData();
            } else {
                new Alert(Alert.AlertType.ERROR, "Not Saved").show();
            }
        } catch (ServiceException e) {
            //if(e instanceof PublisherException){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            //}
        }

    }

    private PublisherDTO collectPublisherData() {
        String id = txtPublisherId.getText();

        String name = txtPublisherName.getText();
        String text = txtPublisherContact.getText();
        String location = txtPublisherLocation.getText();

        int idNum = 0;
        try {
            idNum = Integer.parseInt(id);
        } catch (NumberFormatException e) {
            //e.printStackTrace();
        }


        PublisherDTO publisherDTO = new PublisherDTO();
        publisherDTO.setId(idNum);
        publisherDTO.setName(name);
        publisherDTO.setLocation(location);
        publisherDTO.setContact(text);

        return publisherDTO;
    }

    private AuthorDTO collectAuthorData() {
        String authorId = txtAuthorId.getText();
        String authorName = txtAuthorName.getText();
        String authorContact = txtAuthorContact.getText();
        int id = 0;
        try {
            id = Integer.parseInt(authorId);
        } catch (NumberFormatException ex) {
            //ex.printStackTrace();
        }
        return AuthorDTO.builder().id(id).name(authorName).contact(authorContact).build();
    }

    public void txtPublisherIdOnAction(ActionEvent actionEvent) {
        PublisherDTO publisherDTO = collectPublisherData();
        try {
            Optional<PublisherDTO> search = publisherService.search(publisherDTO.getId());
            if (search.isPresent()) {
                setPublisherDataToFields(search.get());
            } else {
                new Alert(Alert.AlertType.ERROR, "Publisher Not Found Or Invalid ID").show();
            }
        } catch (ServiceException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void setPublisherDataToFields(PublisherDTO publisherDTO) {
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

    private void loadPublisherTableData() {
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
            tblAuthors.setItems(FXCollections.observableArrayList(authorService.getAll().stream()
                    .map(this::convertAuthorToTM).toList()));
        } catch (ServiceException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void visualizeData() {
        colPublisherId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colPublisherName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colPublisherContact.setCellValueFactory(new PropertyValueFactory<>("contact"));

        colAuthorId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colAuthorName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAuthorContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
    }

    private PublisherTM convertDtoToTM(PublisherDTO obj) {
        return modelMapper.map(obj, PublisherTM.class);
    }

    public void txtAuthorIdOnAction(ActionEvent actionEvent) {
        AuthorDTO authorDTO = collectAuthorData();
        if (authorDTO.getId() == 0) {
            new Alert(Alert.AlertType.ERROR, "Invalid ID").show();
            return;
        }
        try {
            Optional<AuthorDTO> search = authorService.search(authorDTO.getId());
            if (search.isPresent()) {
                AuthorDTO authorDTO1 = search.get();
                setAuthorDataToFields(authorDTO1);
            } else {
                new Alert(Alert.AlertType.ERROR, "Author Not Found Or Invalid ID").show();
            }
        } catch (ServiceException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    private AuthorTM convertAuthorToTM(AuthorDTO obj) {
        return modelMapper.map(obj, AuthorTM.class);
    }

    public void tblAuthorsOnMouseClick(MouseEvent mouseEvent) {
        AuthorTM selectedAuthor= tblAuthors.getSelectionModel().getSelectedItem();
        if (selectedAuthor!=null){
            txtAuthorId.setText(String.valueOf(selectedAuthor.getId()));
            txtAuthorIdOnAction(null);
        }
    }

    public void tblPublishersOnMouseClick(MouseEvent mouseEvent) {
        PublisherTM selectedPublisher = tblPublishers.getSelectionModel().getSelectedItem();
        if (selectedPublisher!=null){
            txtPublisherId.setText(String.valueOf(selectedPublisher.getId()));
            txtPublisherIdOnAction(null);
        }
    }

}