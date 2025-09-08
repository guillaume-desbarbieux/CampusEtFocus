package fr.campusetfocus.db.dao;

import fr.campusetfocus.db.DAOTablePivot;
import java.sql.Connection;

public class DAOCell_Equipment extends DAOTablePivot {

    public DAOCell_Equipment(Connection CONNECTION) {
        super(CONNECTION, "Cell_Equipment", "CellId", "EquipmentId");
    }
}
