/* Model SGE */
/* Classe: PrecoRotativo */
package model;

import controller.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PrecoRotativo {
    
    Connection con;
    PreparedStatement pstm;
    
    private Integer tipo_veiculo;
    private Double valor_hora;

    public PrecoRotativo(Integer tipo_veiculo, Double valor_hora) {
        this.tipo_veiculo = tipo_veiculo;
        this.valor_hora = valor_hora;
    }
    
    public PrecoRotativo(){}

    public Integer getTipo_veiculo() {
        return tipo_veiculo;
    }

    public void setTipo_veiculo(Integer tipo_veiculo) {
        this.tipo_veiculo = tipo_veiculo;
    }

    public Double getValor_hora() {
        return valor_hora;
    }
    
    public Double getValor_hora2(Integer tipo_veiculo){
        con = new Conexao().combd();
        
        try{
            ResultSet res;
            String Query = "select valor_hora from preco_rotativo where tipo_veiculo = ?";
            PreparedStatement st = con.prepareStatement(Query);
            st.setInt(1, tipo_veiculo);
            res = st.executeQuery();
            if(res.next()){
                return res.getDouble("valor_hora");
            }
            else return 0.0;
        }
        catch(SQLException e){
            System.out.println("Valor get Error");
        }
        return 0.0;
    }

    public void setValor_hora(Double valor_hora) {
        this.valor_hora = valor_hora;
    }
    
    public ArrayList<PrecoRotativo> getPreco(){
        con = new Conexao().combd();
        
        ArrayList<PrecoRotativo> list = new ArrayList<>();
        try{                   
            ResultSet res;
            String Query = "select * from preco_rotativo";
            PreparedStatement st = con.prepareStatement(Query);
            res = st.executeQuery();
            while(res.next()){
                tipo_veiculo = res.getInt("tipo_veiculo");
                valor_hora = res.getDouble("valor_hora");
                list.add(new PrecoRotativo(tipo_veiculo,valor_hora));
            }
            return list;
        }
        catch(SQLException e){
            e.printStackTrace();
            System.out.println("Preço get Error");
        }
        return new ArrayList<PrecoRotativo>();
    }
    
    
    public void alteraPreco(Double p1, Integer tipo){
        con = new Conexao().combd();
        
        try{
            String Query = "update preco_rotativo set valor_hora = ? where tipo_veiculo = ?";
            PreparedStatement st = con.prepareStatement(Query);
            st.setDouble(1, p1);
            st.setInt(2,tipo);
            st.executeUpdate();
        }
        catch(SQLException e){
            System.out.println("Update preço error");
        } 
    }
}
