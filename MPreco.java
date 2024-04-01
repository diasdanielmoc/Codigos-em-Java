/* Model SGE */
/* Classe: MPreco */
package model;

import controller.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class MPreco {

    private Connection con;
    private PreparedStatement pstm;

    private int tipo;
    private double valor;

    public MPreco() {
    }

    public MPreco(int tipo, double valor) {
        this.tipo = tipo;
        this.valor = valor;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public ResultSet obterPrecos() {
        con = new Conexao().combd();

        try {
            String sql = "SELECT tipo_reservado, valor_reservado FROM precos_reservado";
            pstm = con.prepareStatement(sql);
            return pstm.executeQuery();

        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro ao obter os preços");
            return null;
        }
    }

    public void atualizarPreco(int tipo, double novoValor) {
        con = new Conexao().combd();

        try {
            String sql = "UPDATE precos_reservado SET valor_reservado = ? WHERE tipo_reservado = ?";
            pstm = con.prepareStatement(sql);
            pstm.setDouble(1, novoValor);
            pstm.setInt(2, tipo);

            pstm.executeUpdate();
            JOptionPane.showMessageDialog(null, "Preço atualizado com sucesso.");

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar o preço: " + e.getMessage());
        }
    }
}
