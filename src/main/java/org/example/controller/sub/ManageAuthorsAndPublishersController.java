package org.example.controller.sub;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.example.dto.custom.PublisherDTO;
import org.example.service.custom.PublisherService;
import org.example.service.custom.impl.PublisherServiceIMPL;
import org.example.util.exceptions.ServiceException;
import org.example.util.exceptions.custom.PublisherException;

import java.util.Optional;

public class ManageAuthorsAndPublishersController {
    public TextField txtPublisherId;
    public TextField txtPublisherName;
    public TextField txtPublisherLocation;
    public TextField txtPublisherContact;
    public TableView tblPublishers;
    public TableColumn colPublisherId;
    public TableColumn colPublisherName;
    public TableColumn colPublisherContact;
    public TextField txtAuthorId;
    public TextField txtAuthorName;
    public TextField txtAuthorContact;
    public TableView tblAuthors;
    public TableColumn colAuthorId;
    public TableColumn colAuthorName;
    public TableColumn colAuthorContact;

    private PublisherService publisherService = new PublisherServiceIMPL();

    public void btnClearAuthorOnAction(ActionEvent actionEvent) {
    }

    public void btnDeleteAuthorOnAction(ActionEvent actionEvent) {
    }

    public void btnUpdateAuthorOnAction(ActionEvent actionEvent) {
    }

    public void btnSaveAuthorOnAction(ActionEvent actionEvent) {
    }

    public void btnClearPublisherOnAction(ActionEvent actionEvent) {
    }

    public void btnDeletePublisherOnAction(ActionEvent actionEvent) {
    }

    public void btnUpdatePublisherOnAction(ActionEvent actionEvent) {
    }

    public void btnSavePublisherOnAction(ActionEvent actionEvent) {
        PublisherDTO publisherDTO = collectData();
        try {
            boolean isSaved = publisherService.add(publisherDTO);
            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "Publisher Saved Successfully").show();
            }else{
                new Alert(Alert.AlertType.ERROR,"Publisher Not Saved - Unexpected Error").show();
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
            e.printStackTrace();
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
}