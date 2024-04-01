/* Model SGE */
/* Classe: MVaga */
package model;

import controller.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class MVaga {

    Connection con;
    PreparedStatement pstm;

    private int idVaga;
    private int tipoVaga;
    private boolean status;
    private String placa;

    public MVaga() {
    }

    public MVaga(int idVaga, int tipoVaga, boolean status, String placa) {
        this.idVaga = idVaga;
        this.tipoVaga = tipoVaga;
        this.status = status;
        this.placa = placa;
    }

    public int getIdVaga() {
        return idVaga;
    }

    public void setIdVaga(int idVaga) {
        this.idVaga = idVaga;
    }

    public int getTipoVaga() {
        return tipoVaga;
    }

    public void setTipoVaga(int tipoVaga) {
        this.tipoVaga = tipoVaga;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public List<MVaga> preencherLista() {
        con = new Conexao().combd();
        List<MVaga> vagas = new ArrayList<>();

        try {
            vagas = obterVagas();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao obter a lista de vagas");
        }
        return vagas;
    }

    private List<MVaga> obterVagas() throws SQLException {
        con = new Conexao().combd();
        List<MVaga> vagas = new ArrayList<>();
        String sql = "SELECT id_vaga, tipo_reservado, status, placa FROM vaga_reservada";

        try (PreparedStatement pstm = con.prepareStatement(sql); ResultSet rs = pstm.executeQuery()) {

            while (rs.next()) {
                MVaga vaga = new MVaga(
                        rs.getInt("id_vaga"),
                        rs.getInt("tipo_reservado"),
                        rs.getBoolean("status"),
                        rs.getString("placa")
                );
                vagas.add(vaga);
            }
        }
        return vagas;
    }

    public void liberarVaga(int idVaga) throws SQLException {
        String sql = "UPDATE vaga_reservada SET status = NULL, placa = NULL WHERE id_vaga = ?";

        try (PreparedStatement pstm = con.prepareStatement(sql)) {

            pstm.setInt(1, idVaga);
            pstm.executeUpdate();
        }
    }

    public boolean vagaDisponivel(int idVaga, String placa) throws SQLException {
        con = new Conexao().combd();

        String sql = "SELECT * FROM vaga_reservada WHERE id_vaga = ? AND (status IS NULL OR status = 0) AND (placa IS NULL OR placa = ?)";

        try (PreparedStatement pstm = con.prepareStatement(sql)) {
            pstm.setInt(1, idVaga);
            pstm.setString(2, placa);

            ResultSet rs = pstm.executeQuery();
            return rs.next();

        }
    }

    public boolean placaExiste(String placa) throws SQLException {
        String sqlPlacaExiste = "SELECT COUNT(*) FROM veiculo_reservado WHERE placa = ?";

        try (PreparedStatement pstmPlacaExiste = con.prepareStatement(sqlPlacaExiste)) {
            pstmPlacaExiste.setString(1, placa);
            ResultSet rsPlacaExiste = pstmPlacaExiste.executeQuery();

            return rsPlacaExiste.next() && rsPlacaExiste.getInt(1) > 0;
        }
    }

    public void preencherVaga(int idVaga, String placa) throws SQLException {
        String sql = "UPDATE vaga_reservada SET status = 1, placa = ? WHERE id_vaga = ?";

        try (PreparedStatement pstm = con.prepareStatement(sql)) {

            pstm.setString(1, placa);
            pstm.setInt(2, idVaga);
            pstm.executeUpdate();
        }
    }

    public double calcularReserva(int dias, int idVaga) {
        con = new Conexao().combd();
        double valorReserva = 0;

        try {

            String sql = "SELECT valor_reservado FROM precos_reservado pr "
                    + "INNER JOIN vaga_reservada vr ON pr.tipo_reservado = vr.tipo_reservado "
                    + "WHERE vr.id_vaga = ?";

            try (PreparedStatement pstm = con.prepareStatement(sql)) {
                pstm.setInt(1, idVaga);

                try (ResultSet rs = pstm.executeQuery()) {
                    if (rs.next()) {
                        valorReserva = rs.getDouble("valor_reservado");
                    } else {
                        JOptionPane.showMessageDialog(null, "Tipo de vaga não encontrado na tabela de preços.");
                    }
                }
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao calcular o valor da reserva: " + e.getMessage());
        }

        return valorReserva * dias;
    }
}
