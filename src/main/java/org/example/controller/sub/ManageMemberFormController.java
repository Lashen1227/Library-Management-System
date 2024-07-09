package org.example.controller.sub;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.dto.MemberDTO;
import org.example.service.custom.impl.MemberServiceIMPL;
import org.example.tableModels.MemberTM;
import org.example.util.exceptions.MemberException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ManageMemberFormController {
    public TextField txtMemberId;
    public TextField txtMemberName;
    public TextField txtMemberAddress;
    public TextField txtMemberEmail;
    public TextField txtMemberContact;
    public TableView<MemberTM> tblMember;
    public TableColumn<MemberTM,String> colMemberId;
    public TableColumn<MemberTM,String> colMemberName;
    public TableColumn<MemberTM,String> colMemberAddress;
    public TableColumn<MemberTM,String> colMemberEmail;
    public TableColumn<MemberTM,String> colMemberContact;

    private final MemberServiceIMPL service = new MemberServiceIMPL();

    public void initialize(){
        loadTableData();
        visualizeTable();
    }

    private void visualizeTable() {
        colMemberId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colMemberName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colMemberAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colMemberEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colMemberContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
    }

    public void txtMemberIdOnAction(ActionEvent actionEvent) {
        Optional<MemberDTO> search = service.search(txtMemberId.getText());
        if (search.isPresent()){
            setDataToFields(search.get());
        }else{
            new Alert(Alert.AlertType.ERROR,"Member Not Found").show();
        }
    }

    public void txtMemberEmailOnAction(ActionEvent actionEvent) {
    }

    public void txtMemberContactOnAction(ActionEvent actionEvent) {
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
        MemberDTO memberDTO = collectData();
        boolean isMemberSaved = false;
        String errorMessage = "Unexpected Error - Contact Developer";
        try {
            isMemberSaved = service.add(memberDTO);

        } catch (MemberException e) {
            errorMessage = e.getMessage();
        }
        if(isMemberSaved){
            new Alert(Alert.AlertType.INFORMATION,"Member Saved Successfully").show();
            clearFields();
            loadTableData();
        }else{
            new Alert(Alert.AlertType.ERROR,errorMessage).show();
        }
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        MemberDTO memberDTO = collectData();
        boolean isUpdated = false;
        String errorMessage = "Data is Already Same - Not Updated";
        try {
            isUpdated = service.update(memberDTO);
        } catch (MemberException e) {
            errorMessage = e.getMessage();
        }
        if (isUpdated){
            new Alert(Alert.AlertType.INFORMATION,"Member Updated Success").show();
            clearFields();
            loadTableData();
        }else {
            new Alert(Alert.AlertType.ERROR,errorMessage).show();
        }
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        String memberId = txtMemberId.getText();
        boolean delete = false;
        String errorMessage = "User Cancelled - Not Deleted";
        Alert areYouSure = new Alert(Alert.AlertType.CONFIRMATION, "Are You Sure", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> buttonType = areYouSure.showAndWait();
        if (buttonType.isPresent()){
            ButtonType pressedButton = buttonType.get();
            if (pressedButton.equals(ButtonType.YES)){
                try {
                    delete = service.delete(memberId);
                    if (!delete){
                        errorMessage = "User Not Found - Check ID";
                    }
                } catch (MemberException e) {
                    errorMessage = e.getMessage();
                }

            }
        }
        if (delete){
            new Alert(Alert.AlertType.INFORMATION,"Member Deleted Successfully").show();
            clearFields();
            loadTableData();
        }else {
            new Alert(Alert.AlertType.ERROR,errorMessage).show();
        }

    }

    public void loadTableData(){
        try {
            List<MemberTM> list = new ArrayList<>();
            List<MemberDTO> all = service.getAll();
            for (MemberDTO memberDTO : all) {
                MemberTM memberTM = convertMemberDtoToTM(memberDTO);
                list.add(memberTM);
            }
            ObservableList<MemberTM> memberTMS = FXCollections.observableArrayList(list);
            tblMember.setItems(memberTMS);
        } catch (MemberException e) {
            e.printStackTrace();
        }
    }

    public void btnClearOnAction(ActionEvent actionEvent) {
        clearFields();
    }

    public MemberDTO collectData(){
        String id = txtMemberId.getText();
        String name = txtMemberName.getText();
        String email = txtMemberEmail.getText();
        String address = txtMemberAddress.getText();
        String contact = txtMemberContact.getText();
        MemberDTO memberDTO = new MemberDTO(id,name,address,email,contact);
        return memberDTO;
    }

    public void setDataToFields(MemberDTO member){
        txtMemberId.setText(member.getId());
        txtMemberName.setText(member.getName());
        txtMemberAddress.setText(member.getAddress());
        txtMemberEmail.setText(member.getEmail());
        txtMemberContact.setText(member.getContact());
    }

    public void clearFields(){
        txtMemberId.clear();
        txtMemberName.clear();
        txtMemberAddress.clear();
        txtMemberEmail.clear();
        txtMemberContact.clear();
    }

    private MemberTM convertMemberDtoToTM(MemberDTO memberDTO){
        MemberTM memberTM = new MemberTM();
        memberTM.setId(memberDTO.getId());
        memberTM.setName(memberDTO.getName());
        memberTM.setAddress(memberDTO.getAddress());
        memberTM.setEmail(memberDTO.getEmail());
        memberTM.setContact(memberDTO.getContact());
        return memberTM;
    }

}