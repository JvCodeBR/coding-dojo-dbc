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
        return null;
    }

    public List<UsuarioDTO> listaPorId(Integer id) {
        return null;
    }

    public List<UsuarioDTO> listaPorTipo(TipoUsuario tipo) {
        return null;
    }

    public void delete(Integer id) {
        ;
    }

    public List<UsuarioDTO> listar() {
        return null;
    }

    public UsuarioDTO listarMaiorDeIdade() {
        return null;
    }

    public Usuario adicionar(Usuario usuario) throws BancoDeDadosException {
        Connection con = null;
        try {
            con = ConexaoBancoDeDados.getConnection();

//            Integer proximoId = ;
            usuario.setId(this.getProximoId(con));
            String sql = """
                    INSERT INTO USUARIO
                                (ID, NOME, DATANASCIMENTO, SENHA, DATACRIACAO, ATIVO, TIPO)
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

    public UsuarioDTO atualizar(Integer id, UsuarioCreateDTO usuarioCreateDTO) {
        return null;
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
