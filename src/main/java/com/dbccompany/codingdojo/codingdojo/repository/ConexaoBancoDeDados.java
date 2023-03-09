package com.dbccompany.codingdojo.codingdojo.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoBancoDeDados {

    private static final String SERVER = "vemser-dbc.dbccompany.com.br";
    private static final String PORT = "25000"; // Porta TCP padrão do Oracle
    private static final String DATABASE = "xe";

    // Configuração dos parâmetros de autenticação
    private static final String USER = "DOJO";
    private static final String PASS = "codingdojo";
    private static final String SCHEMA = "DOJO";

    public static Connection getConnection() throws SQLException {
        String url = "jdbc:oracle:thin:@" + SERVER + ":" + PORT + ":" + DATABASE;

        // Abre-se a conexão com o Banco de Dados
        Connection con = DriverManager.getConnection(url, USER, PASS);

        // sempre usar o schema vem_ser
        con.createStatement().execute("alter session set current_schema=" + SCHEMA);

        return con;
    }
}
