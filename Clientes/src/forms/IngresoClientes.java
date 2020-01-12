package forms;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import conexion.Conectar;

import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.sql.*;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.JTable;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class IngresoClientes extends JFrame {
	
	private DefaultTableModel model;
	private static final long serialVersionUID = 1L;
	private JTextField t_nom;
	private JTextField t_ape;
	private JTextField t_dni;
	private JTextField t_tel;
	private JTable t_datos;
	private JTextField txt_auxiliar;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					IngresoClientes frame = new IngresoClientes();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	public IngresoClientes() {
		inicioComponentes();
		limpiar();
		bloquear();
		cargarDatos("");
	}
	private void inicioComponentes() {
		setBackground(Color.BLUE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 519, 720);
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnNuevo = new JButton("Nuevo");
		btnNuevo.setBounds(10, 209, 89, 23);
		contentPane.add(btnNuevo);
		
		JButton btnGuardar = new JButton("Guardar");
		btnGuardar.setBounds(120, 209, 89, 23);
		contentPane.add(btnGuardar);
		
		JButton btnCancelar = new JButton("Cancelar");
	
		btnCancelar.setBounds(233, 209, 89, 23);
		contentPane.add(btnCancelar);
		
		JButton btnSalir = new JButton("Salir");
		btnSalir.setBounds(335, 209, 89, 23);
		contentPane.add(btnSalir);
		
		t_nom = new JTextField();
		t_nom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				t_nom.transferFocus();
			}
		});
		t_nom.setBounds(159, 51, 86, 20);
		contentPane.add(t_nom);
		t_nom.setColumns(10);
		
		t_ape = new JTextField();
		t_ape.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				t_ape.transferFocus();
			}
		});
		t_ape.setBounds(159, 82, 86, 20);
		contentPane.add(t_ape);
		t_ape.setColumns(10);
		
		t_dni = new JTextField();
		t_dni.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				t_dni.transferFocus();
			}
		});
		t_dni.setBounds(159, 119, 86, 20);
		contentPane.add(t_dni);
		t_dni.setColumns(10);
		
		t_tel = new JTextField();
		t_tel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				t_tel.transferFocus();
			}
		});
		t_tel.setBounds(159, 150, 86, 20);
		contentPane.add(t_tel);
		t_tel.setColumns(10);
		
		JLabel lblNombre = new JLabel("Nombre");
		lblNombre.setBounds(76, 54, 46, 14);
		contentPane.add(lblNombre);
		
		JLabel lblApellido = new JLabel("Apellido");
		lblApellido.setBounds(76, 85, 46, 14);
		contentPane.add(lblApellido);
		
		
		JLabel lblDni = new JLabel("DNI");
		lblDni.setBounds(76, 122, 46, 14);
		contentPane.add(lblDni);
		
		JLabel lblTelefono = new JLabel("Telefono");
		lblTelefono.setBounds(71, 153, 62, 14);
		contentPane.add(lblTelefono);
		
		JLabel lblNewLabel = new JLabel("INGRESO DE PACIENTES (IDP)");
		lblNewLabel.setToolTipText("");
		lblNewLabel.setFont(new Font("Calibri", Font.BOLD | Font.ITALIC, 17));
		lblNewLabel.setForeground(Color.BLUE);
		lblNewLabel.setBackground(Color.BLUE);
		lblNewLabel.setBounds(124, 11, 228, 14);
		contentPane.add(lblNewLabel);
		
		t_datos = new JTable();
		t_datos.setEnabled(false);
		t_datos.setBounds(10, 290, 483, 380);
		contentPane.add(t_datos);
		
		JLabel lblBuscar = new JLabel("BUSCAR");
		lblBuscar.setBounds(20, 246, 71, 14);
		contentPane.add(lblBuscar);
		
		txt_auxiliar = new JTextField();
		txt_auxiliar.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				cargarDatos(txt_auxiliar.getText());
			}
		});
		txt_auxiliar.setBounds(101, 243, 163, 20);
		contentPane.add(txt_auxiliar);
		txt_auxiliar.setColumns(10);
		
		JButton btnMostrarTodo = new JButton("Mostrar Todo");
		btnMostrarTodo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {	
				MuestroTodo();
			}
		});
		btnMostrarTodo.setBounds(285, 243, 125, 23);
		contentPane.add(btnMostrarTodo);
		
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnNuevo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				desbloquear();
				btnNuevo.setEnabled(false);
				btnGuardar.setEnabled(true);
				btnCancelar.setEnabled(true);
				t_nom.requestFocus();
			}
		});
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				bloquear();
				btnNuevo.setEnabled(true);
				btnGuardar.setEnabled(false);
				btnCancelar.setEnabled(false);
				limpiar();
			}
		});
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			   Conectar c= new Conectar();
			   Connection cx = c.conexion();
			   String nom,ape,dni,tel;
			   String sql= "";
			   nom=t_nom.getText();
			   ape=t_ape.getText();
			   dni=t_dni.getText();
			   tel=t_tel.getText();
			   sql="INSERT INTO pacientes(nom_pac,ape_pac,dni_pac,tel_pac) VALUES (?,?,?,?)";
			   try {
				PreparedStatement psd=  cx.prepareStatement(sql);
				psd.setString(1, nom);
				psd.setString(2, ape);
				psd.setString(3, dni);
				psd.setString(4, tel);
				
				int p= psd.executeUpdate();
				
				if (p>0)
				{ JOptionPane.showMessageDialog(null, "Registro guardado con exito!");
			         bloquear();
			         limpiar();
			         cargarDatos("");
				} 
			  } catch (SQLException e1) {
			
				   System.out.print(e1);
			}
				
			}
		});
	}
	void limpiar()
	{
		t_nom.setText("");
		t_ape.setText("");
		t_dni.setText("");
		t_tel.setText("");
	}
	void bloquear()
	{
		t_nom.setEnabled(false);
		t_ape.setEnabled(false);
		t_dni.setEnabled(false);
		t_tel.setEnabled(false);
	}
	void desbloquear()
	{
		t_nom.setEnabled(true);
		t_ape.setEnabled(true);
		t_dni.setEnabled(true);
		t_tel.setEnabled(true);
	}
	void cargarDatos(String valor)
	{
		String[] titulos= {"Identificacion","Nombre","Apellido","DNI","Tel"};
	    String[] registros= new String[5];
	    
	    String sql= "SELECT * FROM pacientes where CONCAT(nom_pac,' ',ape_pac,' ',dni_pac,' ',tel_pac) LIKE '%" + valor+"%'";
	    
	    model= new DefaultTableModel(null,titulos);
	    
	    Conectar cc= new Conectar();
	    Connection cx= cc.conexion();
	    try {
			Statement st=  cx.createStatement();
	        ResultSet rs=  st.executeQuery(sql);
	        
	        while (rs.next())
	        {
	        	registros[0]= rs.getString("id_pac");
	        	registros[1]= rs.getString("nom_pac");
	        	registros[2]= rs.getString("ape_pac");
	        	registros[3]= rs.getString("dni_pac");
	        	registros[4]= rs.getString("tel_pac");
	        
	        	model.addRow(registros);	
	        }
	        //model= new DefaultTableModel(null,titulos);
	        t_datos.setModel(model);
	    }catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
	       }
	}
	    void MuestroTodo()
		{
			String[] titulos= {"Identificacion","Nombre","Apellido","DNI","Tel"};
		    String[] registros= new String[5];
		    
		    String sql= "SELECT * FROM pacientes";
		    
		    model= new DefaultTableModel(null,titulos);
		    
		    Conectar cc= new Conectar();
		    Connection cx= cc.conexion();
		    try {
				Statement st=  cx.createStatement();
		        ResultSet rs=  st.executeQuery(sql);
		      
		        while (rs.next())
		        {
		        	registros[0]= rs.getString("id_pac");
		        	registros[1]= rs.getString("nom_pac");
		        	registros[2]= rs.getString("ape_pac");
		        	registros[3]= rs.getString("dni_pac");
		        	registros[4]= rs.getString("tel_pac");
		        
		        	model.addRow(registros);	
		        }
		        //model= new DefaultTableModel(null,titulos);
		        t_datos.setModel(model);
		    } catch (Exception e) {
				JOptionPane.showMessageDialog(null, e);
			}
	       }
         }