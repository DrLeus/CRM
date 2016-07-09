package ua.com.smart.andrey.leus.CRM.model;

import java.io.IOException;
import java.sql.SQLException;

public class CRMException extends Exception {

    public CRMException() {
        super();
    }

    public CRMException(String message, IOException cause) {
        super(message, cause);
    }

    public CRMException(String message, SQLException cause) {
        super(message, cause);
    }

    public CRMException(String message) {
        super(message);
    }
}