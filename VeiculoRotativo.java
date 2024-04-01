/* Model SGE */
/* Classe: VeiculoRotativo */
package model;

import controller.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class VeiculoRotativo {
    
    Connection con;
    PreparedStatement pstm;
    
    private String placa;
    private Integer tipo_veiculo;
    private Integer id_cliente;
    private Integer id_vaga;
    private LocalDateTime hora_entrada;
    private String descricao;

    public VeiculoRotativo(String placa, Integer tipo_veiculo, Integer id_cliente, Integer id_vaga, LocalDateTime hora_entrada, String descricao) {
        this.placa = placa;
        this.tipo_veiculo = tipo_veiculo;
        this.id_cliente = id_cliente;
        this.id_vaga = id_vaga;
        this.hora_entrada = hora_entrada;
        this.descricao = descricao;
    }

    
    public VeiculoRotativo(){}
    
    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public Integer getTipo_veiculo() {
        return tipo_veiculo;
    }
    
    public Integer getTipo_veiculo2(String placa){
        con = new Conexao().combd();
        
        try{                   
            ResultSet res;
            String Query = "select tipo_veiculo from veiculo_rotativo where placa = ?";
            PreparedStatement st = con.prepareStatement(Query);
            st.setString(1, placa);
            res = st.executeQuery();
            if(res.next()){
                return res.getInt("tipo_veiculo");
            }
            else return -1;
        }
        catch(SQLException e){
            System.out.println("Tipo_veiculo get Error");
        }      
        return -1;     
     
    }

    public void setTipo_veiculo(Integer tipo_veiculo) {
        this.tipo_veiculo = tipo_veiculo;
    }

    public Integer getId_cliente() {
        return id_cliente;
    }
    
    public Integer getId_cliente2(String placa){
        con = new Conexao().combd();
        
        try{
            ResultSet res;
            String Query = "select id_cliente from veiculo_rotativo where placa = ?";
            PreparedStatement st = con.prepareStatement(Query);
            st.setString(1, placa);
            res = st.executeQuery();
            if(res.next()){
                return res.getInt("id_cliente");
            }
            else{
                return -1;
            }
        }
        catch(SQLException e){
            System.out.println("Id_cliente research Error");
        }      
        return -1;
    }

    public void setId_cliente(Integer id_cliente) {
        this.id_cliente = id_cliente;
    }
    public Integer getId_vaga(){
        return id_vaga;
    }

    public Integer getId_vaga2(String placa) {
        con = new Conexao().combd();
        
        try{
            ResultSet res;
            String Query = "select id_vaga from veiculo_rotativo where placa = ?";
            PreparedStatement st = con.prepareStatement(Query);
            st.setString(1, placa);
            res = st.executeQuery();
            if(res.next()){
                return res.getInt("id_vaga");
            }
            else{
                return -1;
            }
        }
        catch(SQLException e){
            System.out.println("Placa research Error");
        }      
        return -1;
    }

    public void setId_vaga(Integer id_vaga) {
        this.id_vaga = id_vaga;
    }

    public LocalDateTime getHora_entrada() {
        return hora_entrada;
    }
    
    public LocalDateTime getHora_entrada2(String placa){
        con = new Conexao().combd();
        
        try{
            LocalDateTime hora_entrada;
            ResultSet res;
            String Query = "select hora_entrada from veiculo_rotativo where placa = ?";
            PreparedStatement st = con.prepareStatement(Query);
            st.setString(1, placa);
            res = st.executeQuery();
            if(res.next()){
                Timestamp timestamp = res.getTimestamp("hora_entrada");
                hora_entrada = timestamp.toLocalDateTime();
                return hora_entrada;
            }
            else{
                return hora_entrada = null;
            }

        }
        catch(SQLException e){
            System.out.println("Veiuculo validation Error");
        }
        return hora_entrada = null;
    }

    public void setHora_entrada(LocalDateTime hora_entrada) {
        this.hora_entrada = hora_entrada;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    
    public ArrayList<VeiculoRotativo> getVeiculos(){
        con = new Conexao().combd();
        
        ArrayList<VeiculoRotativo> list = new ArrayList<>();
        try{
            ResultSet res;
            String Query = "select * from veiculo_rotativo";
            PreparedStatement st = con.prepareStatement(Query);
            res = st.executeQuery();
            while(res.next()){
                placa = res.getString("placa");
                tipo_veiculo = res.getInt("tipo_veiculo");
                id_cliente = res.getInt("id_cliente");
                id_vaga = res.getInt("id_vaga");
                Timestamp timestamp = res.getTimestamp("hora_entrada");
                hora_entrada = timestamp.toLocalDateTime();
                descricao = res.getString("descricao");
                list.add(new VeiculoRotativo(placa, tipo_veiculo, id_cliente, id_vaga, hora_entrada, descricao));
            }
            return list;
        }
        catch(SQLException e){
            e.printStackTrace();
            System.out.println("Veiculo get Error");
        }
        return new ArrayList<VeiculoRotativo>();
    }
    
    public void insereVeiculo(String placa, Integer tipo_veiculo, Integer id_cliente, Integer vaga, String descricao){
        con = new Conexao().combd();
        LocalDateTime dt = LocalDateTime.now();
        Timestamp timestamp = Timestamp.valueOf(dt);
        try{
            String Query = "insert into veiculo_rotativo values (?,?,?,?,?,?)";
            PreparedStatement st = con.prepareStatement(Query);
            st.setString(1, placa);
            st.setInt(2, tipo_veiculo);
            st.setInt(3, id_cliente);
            st.setInt(4, vaga);
            st.setTimestamp(5, timestamp);
            st.setString(6, descricao);
            st.executeUpdate();
        }
        catch(SQLException e){
            System.out.println("Veiuculo insert Error");
        }
    }
    public boolean verificaVeiculo(String placa){
        con = new Conexao().combd();
        
        try{         
            ResultSet res;
            String Query = "select placa from veiculo_rotativo where placa = ?";
            PreparedStatement st = con.prepareStatement(Query);
            st.setString(1, placa);
            res = st.executeQuery();
            if(res.next()){
                return false; // Esse veiculo já existe
            }
            else{
                return true; // Esse veiculo não existe ainda
            }

        }
        catch(SQLException e){
            System.out.println("Veiuculo validation Error");
        }
        return false;
    }
    
    public boolean verificaPlaca(String placa){
        if( placa.length() == 7){ 
            return verificaVeiculo(placa); // se retornar false então pode excluir
        }
        else return true;
    }
    
    public void deleteVeiculo(String placa){
        con = new Conexao().combd();
        
        try{
            String Query = "delete from veiculo_rotativo where placa = ?";
            PreparedStatement st = con.prepareStatement(Query);
            st.setString(1, placa);
            st.executeUpdate();
        }
        catch(SQLException e){
            System.out.println("Veículo delete Error");
        }
    }
    
}
