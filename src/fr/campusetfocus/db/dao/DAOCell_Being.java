package fr.campusetfocus.db.dao;

import fr.campusetfocus.db.DAOTablePivot;

import java.sql.Connection;

public class DAOCell_Being extends DAOTablePivot {

    public DAOCell_Being(Connection CONNECTION) {
        super(CONNECTION, "Cell_Being", "CellId", "BeingId");
    }
}
