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
            throw new BancoDeDadosException("Erro ao buscar sequence de posto");
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

    public Usuario adicionar(UsuarioCreateDTO usuarioCreateDTO) throws BancoDeDadosException {
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
            stmt.setInt(1, proximoId);
            stmt.setString(2, usuarioCreateDTO.getNome());
            stmt.setDate(3, Date.valueOf(usuarioCreateDTO.getDataNascimento()));
            stmt.setString(4, usuarioCreateDTO.getSenhha());
            stmt.setDate(5, Date.valueOf(usuarioCreateDTO.getDataCriacao()));

            if(usuarioCreateDTO.getAtivo()) {
                stmt.setInt(6, 1);
            } else {
                stmt.setInt(6, 0);
            }

            stmt.setInt(7, usuarioCreateDTO.getTipo().getTipo());


            // Executar consulta
            ResultSet resultSet = stmt.executeQuery(sql);


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

    private Usuario getUsuarioFromResultSet(ResultSet res) throws SQLException {

        Usuario usuario = new Usuario;
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
