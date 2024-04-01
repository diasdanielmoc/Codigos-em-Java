/* Model do SGE */
/* Classe: ClienteRotativo */
package model;

import controller.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.*;
import java.util.ArrayList;

public class ClienteRotativo {

    Connection con;
    PreparedStatement pstm;

    private Integer id_cliente;
    private Long celular;

    public ClienteRotativo(Integer id_cliente, Long celular) {
        this.id_cliente = id_cliente;
        this.celular = celular;
    }

    public ClienteRotativo() {
    }

    public Integer getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(Integer id_cliente) {
        this.id_cliente = id_cliente;
    }

    public Long getCelular() {
        return celular;
    }

    public void setCelular(Long celular) {
        this.celular = celular;
    }

    public void insereCliente(Integer id_cliente, Long celular) {
        con = new Conexao().combd();

        try {
            String Query = "insert into cliente_rotativo values (?,?)";
            PreparedStatement st = con.prepareStatement(Query);
            st.setInt(1, id_cliente);
            st.setLong(2, celular);
            st.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Model Insert error");
        }
    }

    public boolean verificaCliente(Integer id_cliente) {
        con = new Conexao().combd();
        
        try {
            ResultSet res;
            String Query = "select id_cliente from cliente_rotativo where id_cliente = ?";
            PreparedStatement st = con.prepareStatement(Query);
            st.setInt(1, id_cliente);
            res = st.executeQuery();
            if (res.next()) {
                return true; // existe um cliente com esse id no banco
            } else {
                return false; // não existe essse cliente
            }
        } catch (SQLException e) {
            System.out.println("Model verification error");
        }
        return false;
    }

    public void deleteCliente(Integer id_cliente) {
        con = new Conexao().combd();
        
        try {
            String Query = "delete from cliente_rotativo where id_cliente = ?";
            PreparedStatement st = con.prepareStatement(Query);
            st.setInt(1, id_cliente);
            st.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Model delete cliente error");
        }
    }

    public ArrayList<ClienteRotativo> getCliente() {
        con = new Conexao().combd();
        
        ArrayList<ClienteRotativo> list = new ArrayList<>();
        try {
            ResultSet res;
            String Query = "select * from cliente_rotativo";
            PreparedStatement st = con.prepareStatement(Query);
            res = st.executeQuery();
            while (res.next()) {
                id_cliente = res.getInt("id_cliente");
                celular = res.getLong("celular");
                list.add(new ClienteRotativo(id_cliente, celular));
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Cliente get Error");
        }
        return new ArrayList<ClienteRotativo>();
    }

}
