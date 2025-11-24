package dao;
import java.sql.*;
import java.util.*;
import model.Frequencia;

public class FrequenciaDAO {
    public void inserir(Frequencia f) {
        String sql = "INSERT INTO frequencia (id_aluno, data, presenca) VALUES (?, ?, ?)";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, f.getIdAluno());
            stmt.setDate(2, f.getData());
            stmt.setBoolean(3, f.isPresenca());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Frequencia> listar() {
        List<Frequencia> lista = new ArrayList<>();
        String sql = "SELECT * FROM frequencia";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Frequencia f = new Frequencia();
                f.setId(rs.getInt("id_frequencia"));
                f.setIdAluno(rs.getInt("id_aluno"));
                f.setData(rs.getDate("data"));
                f.setPresenca(rs.getBoolean("presenca"));
                lista.add(f);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
    public void atualizar(Frequencia f) {
        String sql = "UPDATE frequencia SET id_aluno = ?, data = ?, presenca = ? WHERE id_frequencia = ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, f.getIdAluno());
            stmt.setDate(2, f.getData());
            stmt.setBoolean(3, f.isPresenca());
            stmt.setInt(4, f.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void excluir(int id) {
        String sql = "DELETE FROM frequencia WHERE id_frequencia = ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
