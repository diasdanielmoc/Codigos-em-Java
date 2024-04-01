/* Model SGE */
/* Classe: Usuario */
package model;

import controller.Conexao;
import java.sql.*;
import java.util.ArrayList;


public class Usuario {
    
    Connection con;
    
    private String usuario_nome;
    private String usuario_senha;
    private int tipo;  
    
    public Usuario(String usuario_nome, String usuario_senha, int tipo) {
        this.usuario_nome = usuario_nome;
        this.usuario_senha = usuario_senha;
        this.tipo = tipo;
    }  
    public Usuario(String usuario_nome, String usuario_senha) {
        this.usuario_nome = usuario_nome;
        this.usuario_senha = usuario_senha;
    }  
    public Usuario(String usuario_nome, int tipo){
        this.usuario_nome = usuario_nome;
        this.tipo = tipo;
    }
    public Usuario(){}

    public String getUsuario_nome() {
        return usuario_nome;
    }

    public void setUsuario_nome(String usuario_nome) {
        this.usuario_nome = usuario_nome;
    }

    public String getUsuario_senha() {
        return usuario_senha;
    }

    public void setUsuario_senha(String usuario_senha) {
        this.usuario_senha = usuario_senha;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }
    
    public void cadastrarUsuario(){
        
    }
    public int logarUsuario(String nome, String senha){
        con = new Conexao().combd();
        if (verificarUsuario(nome) == true){
            try{
                ResultSet res;
                String Query = "Select usuario_senha, tipo from usuario where usuario_nome = ?";
                PreparedStatement st = con.prepareStatement(Query);
                st.setString(1, nome);
                res = st.executeQuery();
                res.next();
                if(res.getString("usuario_senha").equals(senha)){
                    return res.getInt("tipo");
                }
                else return -1;
            }
            catch(SQLException e){
                   System.out.println("Model login error");
            }
        }
        return -1;
    }
    
    public boolean verificarUsuario(String usuario_nome){   
        con = new Conexao().combd();
        
        try{
            ResultSet res;
            String Query = "select usuario_nome from usuario";
            PreparedStatement st = con.prepareStatement(Query);
            res = st.executeQuery(Query);
            while(res.next()){
                if(res.getString("usuario_nome").equals(usuario_nome)){
                    return true; // usuario existe
                }
            }
        }
        catch(java.sql.SQLException e){
            System.out.println("Model error verification");
            return false;
        }
        return false;
    }
    
    public ArrayList<Usuario> getUsuario(){
        con = new Conexao().combd();
        ArrayList<Usuario> list = new ArrayList<>();
        try{
            ResultSet res;
            String Query = "select usuario_nome, tipo from usuario";
            PreparedStatement st = con.prepareStatement(Query);
            res = st.executeQuery();
            while(res.next()){
                usuario_nome = res.getString("usuario_nome");
                tipo = res.getInt("tipo");
                list.add( new Usuario(usuario_nome,tipo));
            }
            return list;
        }
        catch(SQLException e){
            //e.printStackTrace();
            System.out.println("User get Error");
        }
        return new ArrayList<Usuario>();
    }
    
    public boolean verificaTipoUsuario(String usuario_nome){
        con = new Conexao().combd();
        
        try{         
            ResultSet res;
            String Query = "select tipo from usuario where usuario_nome = ?";
            PreparedStatement st = con.prepareStatement(Query);
            st.setString(1,usuario_nome);
            res = st.executeQuery();
            res.next();
            tipo = res.getInt("tipo");
            if(tipo == 0){
                return true; // pode excluir o usuario
            }
            else{
                return false; // não pode excluir o usuario
            }
        }
        catch(SQLException e){
            //e.printStackTrace();
            System.out.println("User verification tipo Error");
        }       
        return false;
    }
    
    public void removerUsuario(String usuario_nome){
        con = new Conexao().combd();
        
        try{
            String Query = "delete from usuario where usuario_nome = ?";
            PreparedStatement st = con.prepareStatement(Query);
            st.setString(1,usuario_nome);
            st.executeUpdate();
        }
        catch(SQLException e){
            //e.printStackTrace();
            System.out.println("User delete Error");
        }        
    }
    
    public void insereUsuario(String usuario_nome, String usuario_senha, Integer tipo){
        con = new Conexao().combd();
        
        try{
            String Query = "insert into usuario values (?,?,?)";
            PreparedStatement st = con.prepareStatement(Query);
            st.setString(1,usuario_nome);
            st.setString(2,usuario_senha);
            st.setInt(3, tipo);
            st.executeUpdate();
        }
        catch(SQLException e){
            //e.printStackTrace();
            System.out.println("User insert Error");
        }   
    }
    
}
