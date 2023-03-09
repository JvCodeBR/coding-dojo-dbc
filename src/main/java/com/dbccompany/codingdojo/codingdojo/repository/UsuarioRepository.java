package com.dbccompany.codingdojo.codingdojo.repository;

import com.dbccompany.codingdojo.codingdojo.dto.UsuarioCreateDTO;
import com.dbccompany.codingdojo.codingdojo.dto.UsuarioDTO;
import com.dbccompany.codingdojo.codingdojo.exceptions.BancoDeDadosException;
import com.dbccompany.codingdojo.codingdojo.model.TipoUsuario;
import com.dbccompany.codingdojo.codingdojo.model.Usuario;
import org.springframework.stereotype.Repository;

import java.sql.*;
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
            throw new BancoDeDadosException("Erro ao buscar sequence de posto" + e);
        }
    }

    public List<Usuario> listarUsuariosAntigos() {
        return ;
    }

    public List<UsuarioDTO> listaPorId(Integer id) {
        return ;
    }

    public List<UsuarioDTO> listaPorTipo(TipoUsuario tipo) {
        return ;
    }

    public void delete(Integer id) {
        ;
    }

    public List<UsuarioDTO> listar() {
        return ;
    }

    public UsuarioDTO listarMaiorDeIdade() {
        return ;
    }

    public UsuarioDTO adicionar(UsuarioCreateDTO usuarioCreateDTO) throws BancoDeDadosException {
        Connection con = null;
        try {
            con = ConexaoBancoDeDados.getConnection();

            Integer proximoId = this.getProximoId(con);

            String sql = """
                    INSERT INTO USUARIO
                                (ID, NOME, DATANASCIMENTO, SENHA, DATACRIACAO, ATIVO, TIPO)
                        VALUES(?, ?, ?, ?, ?, ?, ?)
                    """;
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, getProximoId());////
            stmt.setString(2, usuarioCreateDTO.getCpf());///
            stmt.setString(3, usuarioCreateDTO.getNome());

            // Executar consulta
            stmt.executeUpdate();
            return usuarioCreateDTO;
        } catch (SQLException e) {
            throw new BancoDeDadosException(e.getCause());
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

    public UsuarioDTO atualizar(Integer id, UsuarioCreateDTO usuarioCreateDTO) {
        return ;
    }
}
