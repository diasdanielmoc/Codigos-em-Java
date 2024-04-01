/* Model SGE */
/* Classe: MCliente */
package model;

import controller.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class MCliente {

    Connection con;
    PreparedStatement pstm;

    private int idCliente;
    private String nomeCliente;
    private String cpf;
    private long celular;
    private String endereco;

    public MCliente() {
    }

    public MCliente(int idCliente, String nomeCliente, String cpf, long celular) {
        this.idCliente = idCliente;
        this.cpf = cpf;
        this.nomeCliente = nomeCliente;
        this.celular = celular;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public long getCelular() {
        return celular;
    }

    public void setCelular(long celular) {
        this.celular = celular;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public ResultSet autenticaclienteid(int IdCliente) {
        con = new Conexao().combd();

        try {
            String sql = "select * from cliente_reservado where id_cliente = ?";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setInt(1, IdCliente);
            ResultSet rs = pstm.executeQuery();
            return rs;

        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "CCliente");
            return null;
        }
    }

    public ResultSet autenticaclientecpf(String cpf) {
        con = new Conexao().combd();

        try {
            String sql = "select * from cliente_reservado where cpf = ?";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, cpf);
            ResultSet rs = pstm.executeQuery();
            return rs;

        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "CCliente");
            return null;
        }
    }

    public void cadastrarcliente(int IdCliente, String NomeCliente, String cpf, Long celular, String endereco) {

        String sql = "insert into cliente_reservado (id_cliente, nome, cpf, celular, endereco) values(?,?,?,?,?)";

        con = new Conexao().combd();

        try {
            pstm = con.prepareStatement(sql);
            pstm.setInt(1, IdCliente);
            pstm.setString(2, NomeCliente);
            pstm.setString(3, cpf);
            pstm.setLong(4, celular);
            pstm.setString(5, endereco);

            pstm.execute();
            pstm.close();

        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, "CCliente" + e);
        }
    }

    public List<MCliente> preencherLista() {
        con = new Conexao().combd();
        List<MCliente> clientes = new ArrayList<>();

        try {
            clientes = obterClientes();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao obter a lista de clientes");
        }
        return clientes;
    }

    private List<MCliente> obterClientes() throws SQLException {
        con = new Conexao().combd();
        List<MCliente> clientes = new ArrayList<>();
        String sql = "SELECT id_cliente, nome, cpf, celular FROM cliente_reservado";

        try (PreparedStatement pstm = con.prepareStatement(sql); ResultSet rs = pstm.executeQuery()) {
            while (rs.next()) {
                MCliente cliente = new MCliente(
                        rs.getInt("id_cliente"),
                        rs.getString("nome"),
                        rs.getString("cpf"),
                        rs.getLong("celular"));
                clientes.add(cliente);
            }
        }
        return clientes;
    }
    
    public void apagarCliente(int idCliente) {
        Connection con = new Conexao().combd();

        try {
            if (ClienteExiste(idCliente)) {
                int confirmacao = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja apagar o Cliente?", "Confirmação", JOptionPane.YES_NO_OPTION);

                if (confirmacao == JOptionPane.YES_OPTION) {
                    String sql = "DELETE FROM cliente_reservado WHERE id_cliente = ?";
                    pstm = con.prepareStatement(sql);
                    pstm.setInt(1, idCliente);

                    pstm.executeUpdate();
                    pstm.close();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Cliente com id " + idCliente + " não encontrado.");
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao apagar Cliente: " + e.getMessage());
        }
    }
    
    private boolean ClienteExiste(int idCliente) throws SQLException {
        String sql = "SELECT COUNT(*) FROM cliente_reservado WHERE id_cliente = ?";
        pstm = con.prepareStatement(sql);
        pstm.setInt(1, idCliente);
        ResultSet rs = pstm.executeQuery();

        return rs.next() && rs.getInt(1) > 0;
    }  
}
