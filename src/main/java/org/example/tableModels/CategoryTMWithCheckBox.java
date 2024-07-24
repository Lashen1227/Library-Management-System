package org.example.tableModels;

import javafx.scene.control.CheckBox;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class CategoryTMWithCheckBox {
    private int id;
    private String name;
    private CheckBox checkBox;
}
