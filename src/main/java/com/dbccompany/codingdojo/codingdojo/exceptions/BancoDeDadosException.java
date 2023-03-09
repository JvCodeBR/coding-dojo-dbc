package com.dbccompany.codingdojo.codingdojo.exceptions;

import java.sql.SQLException;

public class BancoDeDadosException extends SQLException {
    public BancoDeDadosException(String message) {
        super(message);
    }
}
