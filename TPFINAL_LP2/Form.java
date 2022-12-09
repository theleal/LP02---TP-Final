/**
 * @author Rodrigo Rebelo e Luiz Gustavo
 */


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

import javax.swing.*;

import com.google.gson.Gson;

public class Form extends JFrame implements ActionListener
{  
    private JTextField textFieldNome,
                       textFieldIdade,
                       textFieldPeso,
                       textFieldAltura,
                       textFieldObjetivo;
    private JLabel labelNome,
                   labelIdade,
                   labelPeso,
                   labelAltura,
                   labelObjetivo;
    private JButton buttonIncluir,
                    buttonLimpar,
                    buttonApresentarDados,
                    buttonSair;
    private JPanel panelCamposInclusao,
                   panelBotoes;


    public Form(String title) {
        super(title);
    
        setSize(400, 280);
        setLayout(new BorderLayout(5, 5));
        setLocation(75, 75);

        criarCampos();
        criarPanels();        
    }

    private void criarCampos(){
        buttonIncluir = new JButton("Incluir");
        buttonLimpar = new JButton("Limpar");
        buttonApresentarDados = new JButton("Apresentar");
        buttonSair =  new JButton("Sair");

        buttonLimpar.addActionListener(this);
        buttonSair.addActionListener(this);
        buttonApresentarDados.addActionListener(this);
        buttonIncluir.addActionListener(this);

        addWindowListener(new Fechar());

        labelNome = new JLabel( "Nome: ");
        textFieldNome = new JTextField(8);

        labelIdade = new JLabel( "Idade: ");
        textFieldIdade = new JTextField(8);

        labelPeso = new JLabel( "Peso: ");
        textFieldPeso = new JTextField(8);

        labelAltura = new JLabel( "Altura: ");
        textFieldAltura = new JTextField(8);

        labelObjetivo = new JLabel( "Objetivo: ");
        textFieldObjetivo = new JTextField(8);

    }

    private void criarPanels(){

        panelCamposInclusao = new JPanel();
        panelCamposInclusao.setLayout(new GridLayout(5, 2));

        panelCamposInclusao.add(labelNome);
        panelCamposInclusao.add(textFieldNome);

        panelCamposInclusao.add(labelIdade);
        panelCamposInclusao.add(textFieldIdade);

        panelCamposInclusao.add(labelPeso);
        panelCamposInclusao.add(textFieldPeso);

        panelCamposInclusao.add(labelAltura);
        panelCamposInclusao.add(textFieldAltura);

        panelCamposInclusao.add(labelObjetivo);
        panelCamposInclusao.add(textFieldObjetivo);

        panelBotoes = new JPanel();
        panelBotoes.setLayout(new GridLayout(1, 4));

        panelBotoes.add(buttonIncluir);
        panelBotoes.add(buttonLimpar);
        panelBotoes.add(buttonApresentarDados);
        panelBotoes.add(buttonSair);
  
        add(panelCamposInclusao, BorderLayout.CENTER);
        add(panelBotoes, BorderLayout.SOUTH);
    }

    
    @Override
    public void actionPerformed(ActionEvent e) {
        Aluno aluno = new Aluno();

        aluno.setNome(textFieldNome.getText());        
        aluno.setObjetivo(textFieldObjetivo.getText());        
        aluno.setIdade(textFieldIdade.getText().trim().isEmpty()? 0 : Integer.parseInt(textFieldIdade.getText()));
        aluno.setPeso(textFieldPeso.getText().trim().isEmpty() ? 0 : Float.parseFloat(textFieldPeso.getText()));
        aluno.setAltura(textFieldAltura.getText().trim().isEmpty() ? 0 : Float.parseFloat(textFieldAltura.getText()));


        if(e.getSource() == buttonSair){
            System.exit(0);
        }
        
        if(e.getSource() == buttonLimpar){
            textFieldNome.setText("");
            textFieldIdade.setText("");
            textFieldPeso.setText("");
            textFieldAltura.setText("");
            textFieldObjetivo.setText("");
        }
        
        if(e.getSource() == buttonApresentarDados){
            Gson gson = new Gson();

            JOptionPane.showMessageDialog (this, gson.toJson(aluno));
        }


        if(e.getSource() == buttonIncluir){           
            try {
                String url = "jdbc:microsoft:sqlserver://localhost\\SQLEXPRESS;Database=AulaJava;Trusted_Connection=True";

                String usuario = "sa";
                String senha = "sa";
                
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

                Connection con = DriverManager.getConnection(url,usuario,senha);
            
                Statement st = con.createStatement();

                st.executeUpdate("INSERT INTO Aluno (Nome, Idade, Peso, Altura, Objetivo) Values ('" + aluno.getNome() + "', "+aluno.getIdade()+", "+aluno.getPeso()+", "+aluno.getAltura()+", '"+aluno.getObjetivo()+"')");
                
                con.close();

                JOptionPane.showMessageDialog (this, "Aluno adicionado com sucesso!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog (null, "Erro ao incluir aluno");
            }
        }
    }
    
    public static void main(String[] args) {
        Form f = new Form("TP - FINAL");
        f.setVisible(true);       
    }
}