package dao;
import java.sql.*;
import java.util.*;
import model.Instrutor;

public class InstrutorDAO {

    public void inserir(Instrutor instrutor) {
        String sql = "INSERT INTO instrutor (nome, especialidade) VALUES (?, ?)";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, instrutor.getNome());
            stmt.setString(2, instrutor.getEspecialidade());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Instrutor> listar() {
        List<Instrutor> lista = new ArrayList<>();
        String sql = "SELECT * FROM instrutor";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Instrutor i = new Instrutor();
                i.setId(rs.getInt("id_instrutor"));
                i.setNome(rs.getString("nome"));
                i.setEspecialidade(rs.getString("especialidade"));
                lista.add(i);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public void atualizar(Instrutor instrutor) {
        String sql = "UPDATE instrutor SET nome = ?, especialidade = ? WHERE id_instrutor = ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, instrutor.getNome());
            stmt.setString(2, instrutor.getEspecialidade());
            stmt.setInt(3, instrutor.getId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void excluir(int id) {
        String sql = "DELETE FROM instrutor WHERE id_instrutor = ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}