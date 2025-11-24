package dao;
import java.sql.*;
import java.util.*;
import model.PlanoTreino;

public class PlanoTreinoDAO {
    public void inserir(PlanoTreino plano) {
        String sql = "INSERT INTO plano_treino (id_aluno, id_instrutor, descricao, duracao_semanas) VALUES (?, ?, ?, ?)";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, plano.getIdAluno());
            stmt.setInt(2, plano.getIdInstrutor());
            stmt.setString(3, plano.getDescricao());
            stmt.setInt(4, plano.getDuracaoSemanas());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<PlanoTreino> listar() {
        List<PlanoTreino> lista = new ArrayList<>();
        String sql = "SELECT * FROM plano_treino";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                PlanoTreino p = new PlanoTreino();
                p.setId(rs.getInt("id_plano"));
                p.setIdAluno(rs.getInt("id_aluno"));
                p.setIdInstrutor(rs.getInt("id_instrutor"));
                p.setDescricao(rs.getString("descricao"));
                p.setDuracaoSemanas(rs.getInt("duracao_semanas"));
                lista.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
    public void atualizar(PlanoTreino plano) {
        String sql = "UPDATE plano_treino SET id_aluno = ?, id_instrutor = ?, descricao = ?, duracao_semanas = ? WHERE id_plano = ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, plano.getIdAluno());
            stmt.setInt(2, plano.getIdInstrutor());
            stmt.setString(3, plano.getDescricao());
            stmt.setInt(4, plano.getDuracaoSemanas());
            stmt.setInt(5, plano.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void excluir(int id) {
        String sql = "DELETE FROM plano_treino WHERE id_plano = ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
