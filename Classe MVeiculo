/* Model SGE */
/* Classe: MVeiculo */
package model;

import controller.Ccliente;
import controller.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class MVeiculo {

    Connection con;
    PreparedStatement pstm;

    private String placa;
    private Integer tipoVeiculo;
    private Integer idCliente;
    private Integer idVaga;
    private String descricao;

    public MVeiculo() {
    }

    public MVeiculo(String placa, Integer tipoVeiculo, Integer idCliente,String descricao) {
        this.placa = placa;
        this.tipoVeiculo = tipoVeiculo;
        this.idCliente = idCliente;     
        this.descricao = descricao;
    }

    public MVeiculo(String placa, Integer tipoVeiculo, Integer idCliente, Integer idVaga) {
        this.placa = placa;
        this.tipoVeiculo = tipoVeiculo;
        this.idCliente = idCliente;
        this.idVaga = idVaga;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public Integer getTipoVeiculo() {
        return tipoVeiculo;
    }

    public void setTipoVeiculo(Integer tipoVeiculo) {
        this.tipoVeiculo = tipoVeiculo;
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public Integer getIdVaga() {
        return idVaga;
    }

    public void setIdVaga(Integer idVaga) {
        this.idVaga = idVaga;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public ResultSet autenticacadveic(String placa) {
        con = new Conexao().combd();

        try {
            String sql = "select * from veiculo_reservado where placa = ?";

            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, placa);

            ResultSet rs = pstm.executeQuery();
            return rs;

        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro ao autenticar veículo");
            return null;
        }
    }

    public void cadastrarveic(String placa, Integer tipoVeiculo, Integer idCliente, String descricao) {

        String sql = "insert into veiculo_reservado(placa, tipo_reservado, id_cliente, descricao) values(?,?,?,?)";

        con = new Conexao().combd();

        try {
            pstm = con.prepareStatement(sql);
            pstm.setString(1, placa);
            pstm.setInt(2, tipoVeiculo);
            pstm.setInt(3, idCliente);         
            pstm.setString(4, descricao);

            pstm.execute();
            pstm.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar veículo");
        }
    }

    public List<MVeiculo> preencherLista() {
        con = new Conexao().combd();
        List<MVeiculo> veiculos = new ArrayList<>();

        try {
            veiculos = obterVeiculos();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao obter a lista de veículos");
        }
        return veiculos;
    }

    private List<MVeiculo> obterVeiculos() throws SQLException {
        con = new Conexao().combd();
        List<MVeiculo> veiculos = new ArrayList<>();
        String sql = "SELECT placa, tipo_reservado, id_cliente, id_vaga FROM veiculo_reservado order by id_vaga DESC";

        try (PreparedStatement pstm = con.prepareStatement(sql); ResultSet rs = pstm.executeQuery()) {

            while (rs.next()) {
                MVeiculo veiculo = new MVeiculo(
                        rs.getString("placa"),
                        rs.getInt("tipo_reservado"),
                        rs.getInt("id_cliente"),
                        rs.getInt("id_vaga"));
                veiculos.add(veiculo);
            }
        }
        return veiculos;
    }

    public void apagarVeiculo(String placa) {
        con = new Conexao().combd();

        try {
            if (veiculoExiste(placa)) {
                int confirmacao = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja apagar o veículo?", "Confirmação", JOptionPane.YES_NO_OPTION);

                if (confirmacao == JOptionPane.YES_OPTION) {
                    String sql = "DELETE FROM veiculo_reservado WHERE placa = ?";
                    pstm = con.prepareStatement(sql);
                    pstm.setString(1, placa);

                    pstm.executeUpdate();
                    pstm.close();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Veículo com placa " + placa + " não encontrado.");
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao liberar veículo: " + e.getMessage());
        }
    }

    private boolean veiculoExiste(String placa) throws SQLException {
        String sql = "SELECT COUNT(*) FROM veiculo_reservado WHERE placa = ?";
        pstm = con.prepareStatement(sql);
        pstm.setString(1, placa);
        ResultSet rs = pstm.executeQuery();

        return rs.next() && rs.getInt(1) > 0;
    }  
    
}
