/* Model SGE */
/* Classe: VagaRotativa */
package model;

import controller.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class VagaRotativa {
    
    Connection con;
    PreparedStatement pstm;
    
    private Integer id_Vaga;
    private String placa;
    private boolean status;

    public VagaRotativa(Integer id_Vaga, String placa, boolean status) {
        this.id_Vaga = id_Vaga;
        this.placa = placa;
        this.status = status;
    }
    
    public VagaRotativa(Integer id_Vaga, boolean status){
        this.id_Vaga = id_Vaga;
        this.status = status;
    }
    
    public VagaRotativa(){
        
    }
    
    public Integer getId_Vaga() {
        return id_Vaga;
    }

    public void setId_Vaga(Integer id_Vaga) {
        this.id_Vaga = id_Vaga;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
    
    public void atualizarVaga(Integer id_vaga, String placa){
        con = new Conexao().combd();
        
        if(verificaVaga(id_vaga)== true){
            try{              
                String Query = "update vaga_rotativa set placa = ?, status = ? where id_vaga = ?";
                PreparedStatement st = con.prepareStatement(Query);
                st.setString(1,placa);
                st.setInt(2,1);
                st.setInt(3, id_vaga);
                st.executeUpdate();
                //return true;
            }
            catch(SQLException e){
                System.out.println("Model update error");
            }
        }
        //return false;
    }

    public void liberarVaga(Integer id_vaga){
        //if(verificaVaga(id_vaga)== true){
            con = new Conexao().combd();
        
            try{            
                String Query = "update vaga_rotativa set placa = ?, status = ? where id_vaga = ?";
                PreparedStatement st = con.prepareStatement(Query);
                st.setString(1,null);
                st.setInt(2,0);
                st.setInt(3, id_vaga);
                st.executeUpdate();
                //return true;
            }
            catch(SQLException e){
                System.out.println("Model update error");
            }
        //
        //return false;
    }
    
    public boolean verificaVaga(Integer id_vaga){
        con = new Conexao().combd();
        
        try{
            ResultSet res;
            String Query = "select status from vaga_rotativa where id_vaga = ?";
            PreparedStatement st = con.prepareStatement(Query);
            st.setInt(1, id_vaga);
            res = st.executeQuery();
            res.next();
            int status = res.getInt("status");
            if(status == 0){
                return true;
            }
            else{
                return false;
            }
        }
        catch(SQLException e){
            System.out.println("Vaga Verification Error");
        }
        return false;
    }
    
    public ArrayList<VagaRotativa> getVaga(){
        con = new Conexao().combd();
        ArrayList<VagaRotativa> list = new ArrayList<>();
        try{          
            ResultSet res;
            String Query = "select id_vaga, status from vaga_rotativa";
            PreparedStatement st = con.prepareStatement(Query);
            res = st.executeQuery();
            while(res.next()){
                id_Vaga = res.getInt("id_vaga");
                status = res.getBoolean("status");
                list.add( new VagaRotativa(id_Vaga, status));
            }
            return list;
        }
        catch(SQLException e){
            e.printStackTrace();
            System.out.println("Vaga get Error");
        }
        return new ArrayList<VagaRotativa>();
    }
    
    
}
