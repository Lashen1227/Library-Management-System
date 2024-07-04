package org.example.controller.sub;

import javafx.event.ActionEvent;
import javafx.scene.control.*;

public class ReturnBookFormController {
    public Label lblLateDateCount;
    public TableView tblNotReturnedBookRecords;
    public TableColumn colRecordId;
    public TableColumn colBookId;
    public TableColumn colBookName;
    public TableColumn colMemberId;
    public TableColumn colMemberName;
    public TableColumn colReturnDate;
    public TextField txtSearchKeyword;
    public RadioButton rbBookId;
    public RadioButton rbMemberId;
    public RadioButton rbMemberMobileNumber;
    public TextField txtFineForOneDay;
    public Label lblFine;
    public TextField txtPayment;
    public Label lblBalance;

    public void btnMarkAsReturnedOnAction(ActionEvent actionEvent) {
    }
}
