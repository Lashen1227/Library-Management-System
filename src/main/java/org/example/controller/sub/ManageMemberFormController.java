package org.example.controller.sub;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.example.dto.MemberDTO;
import org.example.service.MemberService;

public class ManageMemberFormController {
    public TextField txtMemberId;
    public TextField txtMemberName;
    public TextField txtMemberAddress;
    public TextField txtMemberEmail;
    public TextField txtMemberContact;
    public TableView tblMember;
    public TableColumn colMemberId;
    public TableColumn colMemberName;
    public TableColumn colMemberAddress;
    public TableColumn colMemberEmail;
    public TableColumn colMemberContact;

    private final MemberService service = new MemberService();

    public void txtMemberIdOnAction(ActionEvent actionEvent) {
    }

    public void txtMemberEmailOnAction(ActionEvent actionEvent) {
    }

    public void txtMemberContactOnAction(ActionEvent actionEvent) {
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
        MemberDTO memberDTO = collectData();
        boolean isMemberSaved = service.addMember(memberDTO);
        if(isMemberSaved){
            new Alert(Alert.AlertType.INFORMATION,"Member Saved Successfully").show();
        }else{
            new Alert(Alert.AlertType.ERROR,"Something went wrong - May Be Duplicate ID").show();
        }
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
    }

    public void btnClearOnAction(ActionEvent actionEvent) {

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

}