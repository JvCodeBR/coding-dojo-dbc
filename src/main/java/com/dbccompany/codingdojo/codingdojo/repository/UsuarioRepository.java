package com.dbccompany.codingdojo.codingdojo.repository;

import com.dbccompany.codingdojo.codingdojo.dto.UsuarioCreateDTO;
import com.dbccompany.codingdojo.codingdojo.dto.UsuarioDTO;
import com.dbccompany.codingdojo.codingdojo.exceptions.BancoDeDadosException;
import com.dbccompany.codingdojo.codingdojo.model.TipoUsuario;
import com.dbccompany.codingdojo.codingdojo.model.Usuario;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class UsuarioRepository {

    public Integer getProximoId(Connection connection) throws SQLException {
        try {
            String sql = "SELECT SEQ_USUARIO.NEXTVAL mysequence FROM DUAL";

            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            if (rs.next()) {
                return rs.getInt("mysequence");
            }
            return null;
        } catch (SQLException e) {
            throw new BancoDeDadosException("Erro ao buscar sequence de posto");
        }
    }

    public List<Usuario> listarUsuariosAntigos() {
        return null;
    }

    public Usuario listarPorID(Integer id) throws BancoDeDadosException {
        Usuario usuario = null;
        Connection con = null;
        try {
            con = ConexaoBancoDeDados.getConnection();

            String sql = "SELECT * " +
                    "FROM USUARIO WHERE ID = ?";

            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, id);

            // Executa-se a consulta
            ResultSet res = stmt.executeQuery();

            if (res.next()) {
                usuario = getUsuarioFromResultSet(res);
            }

            return usuario;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new BancoDeDadosException("Erro no Banco!");
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public List<UsuarioDTO> listaPorTipo(TipoUsuario tipo) {
        return null;
    }

    public boolean delete(Integer id) throws BancoDeDadosException {
        Connection con = null;
        try {
            con = ConexaoBancoDeDados.getConnection();

            String sql = "UPDATE USUARIO u SET u.ATIVO = 0 WHERE u.ID = ? ";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, id);

            // Executar consulta
            int res = stmt.executeUpdate();
            return res > 0;
        } catch (SQLException e) {
            throw new BancoDeDadosException("Erro no banco");
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public List<Usuario> listar() throws BancoDeDadosException {
        List<Usuario> usuarios = new ArrayList<>();
        Connection con = null;
        try {
            con = ConexaoBancoDeDados.getConnection();
            Statement stmt = con.createStatement();

            String sql = "SELECT * " +
                    "FROM USUARIO" ;

            // Executa-se a consulta
            ResultSet res = stmt.executeQuery(sql);

            while (res.next()) {
                Usuario usuario = getUsuarioFromResultSet(res);
                usuarios.add(usuario);
            }
            return usuarios;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new BancoDeDadosException(e.getMessage());
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public List<Usuario> listarMaiorDeIdade() throws BancoDeDadosException {
        List<Usuario> usuarios = new ArrayList<>();
        Connection con = null;
        try {
            con = ConexaoBancoDeDados.getConnection();
            Statement stmt = con.createStatement();

            String sql = """
                    SELECT u.ID, u.NOME, u.DATA_NASCIMENTO, u.SENHA, u.DATA_CRIACAO, u.ATIVO,
                    u.TIPO
                    FROM USUARIO u WHERE MONTHS_BETWEEN(SYSDATE, u.DATA_NASCIMENTO)/12 >= 18;""";

            // Executa-se a consulta
            ResultSet res = stmt.executeQuery(sql);

            while (res.next()) {
                Usuario usuario = getUsuarioFromResultSet(res);
                usuarios.add(usuario);
            }
            return usuarios;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new BancoDeDadosException(e.getMessage());
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public Usuario adicionar(Usuario usuario) throws BancoDeDadosException {
        Connection con = null;
        try {
            con = ConexaoBancoDeDados.getConnection();

//            Integer proximoId = ;
            usuario.setId(this.getProximoId(con));
            String sql = """
                    INSERT INTO USUARIO
                                (ID, NOME, DATA_NASCIMENTO, SENHA, DATA_CRIACAO, ATIVO, TIPO)
                        VALUES(?, ?, ?, ?, ?, ?, ?)
                    """;
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, usuario.getId());
            stmt.setString(2, usuario.getNome());
            stmt.setDate(3, Date.valueOf(usuario.getDataNascimento()));
            stmt.setString(4, usuario.getSenha());
            stmt.setDate(5, Date.valueOf(usuario.getDataCriacao()));

            if(usuario.getAtivo()) {
                stmt.setInt(6, 1);
            } else {
                stmt.setInt(6, 0);
            }

            stmt.setInt(7, usuario.getTipo().getTipo());


            // Executar consulta
            int res = stmt.executeUpdate();

            return usuario;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new BancoDeDadosException(e.getMessage());
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    /*INSERT INTO USUARIO
            (ID, NOME, DATANASCIMENTO, SENHA, DATACRIACAO, ATIVO, TIPO)
    VALUES(0, ?, ?, ?, ?, ?, ?);*/

    public Usuario atualizar(Integer id, Usuario usuario) throws BancoDeDadosException {
        Connection con = null;
        try {
            con = ConexaoBancoDeDados.getConnection();

            StringBuilder sql = new StringBuilder();
            sql.append("UPDATE USUARIO SET \n");


            if (usuario.getNome() != null) {
                sql.append(" NOME = ?,");
            }

            if (usuario.getEmail() != null) {
                sql.append(" EMAIL = ?,");
            }

            if (usuario.getDataNascimento() != null){
                sql.append(" DATA_NASCIMENTO = ?,");
            }

            if (usuario.getDataCriacao() != null){
                sql.append(" DATA_CRIACAO = ?,");
            }

            if (usuario.getSenha() != null){
                sql.append(" SENHA = ?,");
            }

            if (usuario.getTipo() != null){
                sql.append(" TIPO = ?,");
            }

            sql.deleteCharAt(sql.length() - 1);
            sql.append(" WHERE ID_CLIENTE = ? ");

            PreparedStatement stmt = con.prepareStatement(sql.toString());

            int index = 1;
            if(usuario.getNome() != null){
                stmt.setString(index++, usuario.getNome());
            }

            if(usuario.getEmail() != null){
                stmt.setString(index++, usuario.getEmail());
            }

            if(usuario.getDataNascimento() != null){
                stmt.setDate(index++, Date.valueOf(usuario.getDataNascimento()));
            }

            if(usuario.getDataCriacao() != null){
                stmt.setDate(index++, Date.valueOf(usuario.getDataCriacao()));
            }

            if(usuario.getSenha() != null){
                stmt.setString(index++, usuario.getSenha());
            }

            if(usuario.getTipo() != null){
                stmt.setInt(index++, usuario.getTipo().getTipo());
            }


            stmt.setInt(index, id);

            // Executar consulta
            stmt.executeUpdate();

            return usuario;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new BancoDeDadosException("Erro no banco!");
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private Usuario getUsuarioFromResultSet(ResultSet res) throws SQLException {

        Usuario usuario = new Usuario();
        usuario.setId(res.getInt("id"));
        usuario.setNome(res.getString("nome"));
        usuario.setDataNascimento(res.getDate("data_nascimento").toLocalDate());
        usuario.setSenha(res.getString("senha"));
        usuario.setDataCriacao(res.getDate("data_criacao").toLocalDate());

        if(res.getInt("ativo") == 1) {
            usuario.setAtivo(true);
        } else {
            usuario.setAtivo(false);
        }

        if(res.getInt("tipo") == 1) {
            usuario.setTipo(TipoUsuario.ADMIN);
        } else {
            usuario.setTipo(TipoUsuario.NORMAL);
        }
        return usuario;
    }
}
