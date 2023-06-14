package biro.compilador.principal;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Font;
import javax.swing.border.EtchedBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.awt.event.ActionEvent;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.ImageIcon;
import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JButton;
import javax.swing.JTextArea;

public class Compilador extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private String abiertos, arch, txt;
	private JTextField filenamer;
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Compilador frame = new Compilador();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public Compilador() {
		setResizable(false);
		setFont(new Font("Roboto", Font.PLAIN, 12));
		setIconImage(Toolkit.getDefaultToolkit().getImage(Compilador.class.getResource("/biro/compilador/img/logo.png")));
		
		setTitle("BiroCompilador");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1024, 720);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setFont(new Font("Roboto", Font.PLAIN, 12));
		menuBar.setBorderPainted(false);
		menuBar.setForeground(new Color(255, 255, 153));
		menuBar.setBackground(new Color(0, 0, 51));
		setJMenuBar(menuBar);
		
		JMenu mnNewMenu = new JMenu("Archivo");
		mnNewMenu.setFont(new Font("Roboto", Font.PLAIN, 12));
		mnNewMenu.setForeground(new Color(255, 255, 153));
		mnNewMenu.setToolTipText("Opciones de Archivo.");
		menuBar.add(mnNewMenu);
		
		JMenuItem nuevoDoc = new JMenuItem("Nuevo");
		
		mnNewMenu.add(nuevoDoc);
		
		JMenuItem abrirDoc = new JMenuItem("Abrir");
		
		mnNewMenu.add(abrirDoc);
		
		JMenuItem salirProg = new JMenuItem("Salir");
		salirProg.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		mnNewMenu.add(salirProg);
		
		JMenuItem guardarDoc = new JMenuItem("Guardar");
		
		mnNewMenu.add(guardarDoc);
		
		JMenuItem guardarcomoDoc = new JMenuItem("Guardar como...");
		
		mnNewMenu.add(guardarcomoDoc);
		
		JMenuItem resetForm = new JMenuItem("Reiniciar");
		
		mnNewMenu.add(resetForm);
		
		JMenu mnNewMenu_1 = new JMenu("Preferencias");
		mnNewMenu_1.setFont(new Font("Roboto", Font.PLAIN, 12));
		mnNewMenu_1.setForeground(new Color(255, 255, 153));
		mnNewMenu_1.setToolTipText("Preferencias visuales...");
		menuBar.add(mnNewMenu_1);
		
		JCheckBoxMenuItem chckbxmntmNewCheckItem = new JCheckBoxMenuItem("Modo Oscuro");
		
		chckbxmntmNewCheckItem.setSelected(true);
		mnNewMenu_1.add(chckbxmntmNewCheckItem);
		
		JMenu mnNewMenu_3 = new JMenu("Acerca de");
		mnNewMenu_3.setFont(new Font("Roboto", Font.PLAIN, 12));
		mnNewMenu_3.setForeground(new Color(255, 255, 153));
		mnNewMenu_3.setToolTipText("Acerca del proyecto.");
		menuBar.add(mnNewMenu_3);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("BiroCompilador 2023");
		mntmNewMenuItem.setToolTipText("Informacion.");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "BiroCompilador 2023 - Creado para\nLenguajes y Automatas II 7SA\n\nEquipo 1\nVer. 7.2.22 alpha - 2 Junio 2023 08.34am.");
			}
		});
		mnNewMenu_3.add(mntmNewMenuItem);
		
		
		JMenuItem changes = new JMenuItem("Lista de Cambios");
		changes.setToolTipText("Registro de Cambios.");
		changes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "ChangeLog:\n"
						+ "Se han eliminado errores de acentos\n"
						+ "Implementacion de Triplos\n"
						+ "Se ha implementado un nuevo algoritmo con mayor eficiencia\n"
						+ "Corregidos errores de mal color de palabras\n"
						+ "Corregidos errores comunes\n"
						+ "Corregido el error de resolución para laptops\n"
						+ "Implementado el uso de funciones\n"
						+ "Implementado ensamblador y optimizar\n\n"
						+ "BiroCompilador BiroSupremacia...");
			}
		});
		mnNewMenu_3.add(changes);
		
		
		
		JMenu mnNewMenu_4 = new JMenu("Ayuda");
		mnNewMenu_4.setFont(new Font("Roboto", Font.PLAIN, 12));
		mnNewMenu_4.setForeground(new Color(255, 255, 153));
		mnNewMenu_4.setToolTipText("Ayuda externa.");
		menuBar.add(mnNewMenu_4);
		
		JMenuItem mntmNewMenuItem_5 = new JMenuItem("Website");
		mntmNewMenuItem_5.setToolTipText("Sitio web del Desarrollador.");
		
		mnNewMenu_4.add(mntmNewMenuItem_5);
		
		JMenu mnNewMenu_5 = new JMenu("Empezar desde...");
		mnNewMenu_5.setFont(new Font("Roboto", Font.PLAIN, 12));
		mnNewMenu_5.setForeground(new Color(255, 255, 153));
		mnNewMenu_5.setToolTipText("Plantillas para comenzar...");
		menuBar.add(mnNewMenu_5);		
		JMenuItem mntmNewMenuItem_1 = new JMenuItem("Triplos.biro");		
		mnNewMenu_5.add(mntmNewMenuItem_1);		
		JMenuItem mntmNewMenuItem_2 = new JMenuItem("Funciones.biro");		
		mnNewMenu_5.add(mntmNewMenuItem_2);		
		JMenuItem mntmNewMenuItem_3 = new JMenuItem("Promedios.biro");		
		mnNewMenu_5.add(mntmNewMenuItem_3);		
		JMenuItem mntmNewMenuItem_4 = new JMenuItem("Aritmetica.biro");
		mnNewMenu_5.add(mntmNewMenuItem_4);
		contentPane = new JPanel();
		contentPane.setBackground(Color.DARK_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBackground(Color.DARK_GRAY);
		scrollPane.setViewportBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		scrollPane.setBounds(10, 60, 988, 438);
		contentPane.add(scrollPane);
		
		JTextPane entornoT = new JTextPane();		
		entornoT.setCaretColor(new Color(255, 255, 255));
		entornoT.setText("//Bienvenido");
		entornoT.setToolTipText("Escribe tu codigo aqui para que el BiroCompilador se encargue del resto.");
		entornoT.setFont(new Font("Consolas", Font.PLAIN, 13));
		entornoT.setForeground(Color.WHITE);
		entornoT.setBackground(Color.DARK_GRAY);
		scrollPane.setViewportView(entornoT);
		
		JLabel lblNewLabel = new JLabel("");
		scrollPane.setRowHeaderView(lblNewLabel);
		
		filenamer = new JTextField();
		filenamer.setToolTipText("Nombre del archivo.");
		filenamer.setEditable(false);
		filenamer.setHorizontalAlignment(SwingConstants.CENTER);
		filenamer.setForeground(new Color(255, 255, 153));
		filenamer.setText(getTitle());
		filenamer.setFont(new Font("Roboto", Font.PLAIN, 11));
		filenamer.setBorder(null);
		filenamer.setBackground(Color.DARK_GRAY);
		scrollPane.setColumnHeaderView(filenamer);
		filenamer.setColumns(10);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBackground(Color.BLACK);
		scrollPane_1.setBounds(10, 528, 988, 121);
		contentPane.add(scrollPane_1);
		
		JTextPane console = new JTextPane();
		scrollPane_1.setViewportView(console);
		console.setText(">_");
		console.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		console.setEditable(false);
		console.setForeground(Color.ORANGE);
		console.setBackground(Color.BLACK);
		
		JTextArea bar = new JTextArea();
		bar.setForeground(new Color(0, 128, 0));
		bar.setBackground(new Color(0, 0, 0));
		bar.setEditable(false);
		scrollPane_1.setColumnHeaderView(bar);
		
		JLabel consola = new JLabel("Consola:");
		consola.setHorizontalAlignment(SwingConstants.LEFT);
		consola.setFont(new Font("Roboto", Font.PLAIN, 17));
		consola.setForeground(Color.WHITE);
		consola.setBounds(10, 494, 112, 34);
		contentPane.add(consola);
		
		JPanel panel = new JPanel();
		panel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				Cargando barraDeCarga = new Cargando(bar);
				barraDeCarga.start();
				
				Exportar exp = new Exportar(entornoT);
				if(txt!=null) {
					try {
					exp.analyzeText();
					String exportado = txt.replace(".biro",".csv");
					exp.writeToCSV(exp.dataList, exportado);
					arch=arch.replace(".biro",".csv");
				console.setText(console.getText()+"\n"+"Se ha exportado correctamente la tabla "+arch+"\nUbicacion: "+exportado+"\n");
				BiroInterpretar inter = new BiroInterpretar();
				inter.interpret(entornoT.getText());
				ExportarOld eo = new ExportarOld();
				String exportados = txt.replace(".biro","TablaErrores_.csv");
				eo.Imprimir(exportados, inter.getErrorsAsString());
				Triplo trp = new Triplo();
				trp.ens.setRuta(exportados.replace("TablaErrores_.csv", ".ASM"));
				trp.ens.ruta=exportados.replace("TablaErrores_.csv", ".ASM");
				String triplotabla = trp.processCode(entornoT.getText());
				trp.reinicioCont();
				eo.Imprimir(exportados.replace("TablaErrores_.csv", "Triplo.csv"), triplotabla);
				console.setText(console.getText()+"\nSe ha generado exitosamente la tabla de errores "+ arch+"\nUbicacion: "+exportados+"\n");
				console.setText(console.getText()+"\nTabla de Triplos realizada correctamente...");
				//Ensamblador ens = new Ensamblador();
				//eo.Imprimir(exportados.replace("TablaErrores_.csv", ".ASM"), ens.Ensamblar(trp.malon));
				
				console.setText(console.getText()+"\n\nEnsamblado correcto... "+exportados.replace("TablaErrores_.csv", ".ASM"));
				
				
					} catch(Exception exc) {
						JOptionPane.showMessageDialog(null,"fallo en "+exc.toString()+"\n");
						exc.printStackTrace();
					}
				}
				else {
					JOptionPane.showMessageDialog(contentPane, "Por favor, primero abre un archivo");
				}
			}
		});
		panel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		panel.setBounds(10, 11, 106, 38);
		contentPane.add(panel);
		
		JLabel compi = new JLabel("Compilar");
		compi.setFont(new Font("Roboto", Font.PLAIN, 11));
		compi.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Cargando barraDeCarga = new Cargando(bar);
				barraDeCarga.start();
				
				Exportar exp = new Exportar(entornoT);
				if(txt!=null) {
					try {
						exp.analyzeText();
						String exportado = txt.replace(".biro",".csv");
						exp.writeToCSV(exp.dataList, exportado);
						arch=arch.replace(".biro",".csv");
					console.setText(console.getText()+"\n"+"Se ha exportado correctamente la tabla "+arch+"\nUbicacion: "+exportado+"\n");
					BiroInterpretar inter = new BiroInterpretar();
					inter.interpret(entornoT.getText());
					ExportarOld eo = new ExportarOld();
					String exportados = txt.replace(".biro","TablaErrores_.csv");
					eo.Imprimir(exportados, inter.getErrorsAsString());
					Triplo trp = new Triplo();
					trp.ens.ruta=exportados.replace("TablaErrores_.csv", ".ASM");
					trp.ens.setRuta(exportados.replace("TablaErrores_.csv", ".ASM"));
					String triplotabla = trp.processCode(entornoT.getText());
					trp.reinicioCont();
					eo.Imprimir(exportados.replace("TablaErrores_.csv", "Triplo.csv"), triplotabla);
					console.setText(console.getText()+"\nSe ha generado exitosamente la tabla de errores "+ arch+"\nUbicacion: "+exportados+"\n");
					console.setText(console.getText()+"\nTabla de Triplos realizada correctamente...");
					//Ensamblador ens = new Ensamblador();
					//eo.Imprimir(exportados.replace("TablaErrores_.csv", ".ASM"), ens.Ensamblar(trp.triplolimpio));
					
					console.setText(console.getText()+"\n\nEnsamblado correcto... "+exportados.replace("TablaErrores_.csv", ".ASM"));
					
					} catch(Exception exc) {
						JOptionPane.showMessageDialog(null,"fallo en "+exc.toString());
					}
				}
				else {
					JOptionPane.showMessageDialog(contentPane, "Por favor, primero abre un archivo");
				}
			}
		});
		panel.add(compi);
		compi.setBackground(Color.WHITE);
		compi.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		compi.setHorizontalTextPosition(SwingConstants.LEADING);
		compi.setHorizontalAlignment(SwingConstants.CENTER);
		compi.setToolTipText("Analizar...");
		compi.setForeground(Color.BLACK);
		compi.setIcon(new ImageIcon(Compilador.class.getResource("/biro/compilador/img/engranaje.png")));
		
		JPanel panel_1 = new JPanel();
		panel_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Optimizar opti = new Optimizar();
				String code = opti.optimizar(entornoT.getText());
				entornoT.setText(code);
				console.setText(console.getText()+"\nCodigo optimizado correctamente.");
			}
		});
		panel_1.setEnabled(true);
		panel_1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		panel_1.setBounds(126, 11, 112, 38);
		contentPane.add(panel_1);
		
		JLabel lblNewLabel_1 = new JLabel("Optimizar");
		lblNewLabel_1.setFont(new Font("Roboto", Font.PLAIN, 11));
		
		lblNewLabel_1.setEnabled(true);
		lblNewLabel_1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblNewLabel_1.setHorizontalTextPosition(SwingConstants.LEADING);
		lblNewLabel_1.setIcon(new ImageIcon(Compilador.class.getResource("/biro/compilador/img/run.png")));
		panel_1.add(lblNewLabel_1);
		
		JButton btnNewButton = new JButton("Limpiar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Limpia consola
				console.setText(">_");
			}
		});
		btnNewButton.setFont(new Font("Roboto", Font.PLAIN, 13));
		btnNewButton.setBounds(907, 503, 91, 19);
		contentPane.add(btnNewButton);
		
		NumeroLinea nl;
		nl=new NumeroLinea(entornoT);
		scrollPane.setRowHeaderView(nl);
		nl.setBackground(Color.BLACK);
		nl.setForeground(Color.white);
		
		
		//Aqui las acciones
		nuevoDoc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(JOptionPane.showConfirmDialog(entornoT,"Se van a perder todos sus datos no guardados, ¿Desea continuar?")==0) {
				setTitle("Nuevo.biro *");
				entornoT.setText("");}
				filenamer.setText(getTitle());
			}
		});
		
		
		
		resetForm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(JOptionPane.showConfirmDialog(entornoT,"Se van a perder todos sus datos no guardados, ¿Desea reiniciar?")==0) {
					Compilador reset= new Compilador();
					reset.setVisible(true);
					dispose();
					
					}
				}
		});
		
		mntmNewMenuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Hola mundo
				if(JOptionPane.showConfirmDialog(entornoT,"Se pueden perder todos sus datos no guardados, ¿Desea continuar?")==0) {
					setTitle("Triplos.biro");
					filenamer.setText(getTitle());
					entornoT.setText("*ent .entero1, .entero2, .entero3;\n" +
			                "*dec .real1, .real2, .real3;\n" +
			                "*cad .cadena1, .cadena2, .cadena3;\n" +
			                "\n" +
			                "function *dec .funcion ( *ent .arg1 , *ent .arg2 ) {\n" +
			                "    .arg2 = .arg1 + .arg2;\n" +
			                "    return ( .arg2 );\n" +
			                "}\n" +
			                "\n" +
			                "function *dec .usar ( *ent .ent, *dec .deci) {\n" +
			                "    .real2 = .funcion(.entero1, .real2);\n" +
			                "    return (.real2);\n" +
			                "}\n" +
			                "\n" +
			                ".real1 = .usar (.enterito, .enterado);");
					}
			}
		});
		
		
		mntmNewMenuItem_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(JOptionPane.showConfirmDialog(entornoT,"Se pueden perder todos sus datos no guardados, ¿Desea continuar?")==0) {
					setTitle("Funciones.biro");
					filenamer.setText(getTitle());
					entornoT.setText("//Funciones \r\n"
							+ "*dec .save;"
							+ "function *dec .miObjeto( *dec .a, *dec .b) {\r\n"
							+ "*ent .v = 121;\r\n"
							+ "return (151);\r\n"
							+ "}\r\n"
							+ ".save = .miObjeto(131, 161);\r\n"
							+ "*dec .var = 121;\r\n"
							+ "*ent .jj = 131;");
					}
				
			}
		});
		
		
		
		mntmNewMenuItem_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//promedios
				if(JOptionPane.showConfirmDialog(entornoT,"Se van a perder todos sus datos no guardados, ¿Desea continuar?")==0) {
					setTitle("Promedios.biro");
					filenamer.setText(getTitle());
					entornoT.setText("/* Bienvenido */\n"
							+ "\r\n"
							+ "*ent[] .x[4] = { 10, 9, 9, 10 } ;\r\n"
							+ "iterar i en 4 {\r\n"
							+ "	*ent .suma = .x[i];\r\n"
							+ "	*dec .promedio = .suma/4 ;\r\n"
							+ "	print .promedio ;\r\n"
							+ "	    }\r\n");
					}
			}
		});
		
		
		mntmNewMenuItem_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {

		            Desktop.getDesktop().browse(new URI("http://birosoft.000webhostapp.com/"));

		        } catch (URISyntaxException ex) {

		            System.out.println(ex);

		        }catch(IOException exxc){

		            System.out.println(exxc);

		        }
				    
			}
		});
		
		
		
		
		chckbxmntmNewCheckItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//dark mode
				if (chckbxmntmNewCheckItem.isSelected()==true) {
					entornoT.setForeground(Color.WHITE);
					entornoT.setBackground(Color.DARK_GRAY);
					contentPane.setBackground(Color.DARK_GRAY);
					console.setBackground(Color.BLACK);
					console.setForeground(Color.ORANGE);
					consola.setForeground(Color.WHITE);
					filenamer.setBackground(Color.DARK_GRAY);
					filenamer.setForeground(new Color(255, 255, 153));
					panel.setBackground(Color.WHITE);
					compi.setForeground(Color.BLACK);
					entornoT.setCaretColor(new Color(255, 255, 255));
					
					nl.setBackground(Color.BLACK);
					nl.setForeground(Color.white);
					nl.setCurrentLineForeground(Color.green);
					
					
				} else {
					contentPane.setBackground(Color.WHITE);
					entornoT.setForeground(Color.BLACK);
					entornoT.setBackground(Color.WHITE);
					console.setBackground(Color.WHITE);
					console.setForeground(Color.BLUE);
					consola.setForeground(Color.BLACK);
					filenamer.setBackground(Color.GRAY);
					filenamer.setForeground(Color.BLACK);
					panel.setBackground(Color.DARK_GRAY);
					compi.setForeground(Color.WHITE);
					entornoT.setCaretColor(Color.BLACK);
					
					
					nl.setBackground(Color.WHITE);
					nl.setForeground(Color.BLACK);
					nl.setCurrentLineForeground(Color.RED);
					
				}
				
			}
		});
		
		abiertos = null;
		
		guardarDoc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(abiertos==null) {
				ExportarOld exp = new ExportarOld();
				if(getTitle()==("BiroCompilador") || txt==null) {
				txt="C:/Users/Public/Desktop/Unnamed.biro";
				arch="Unnamed.biro";
					exp.Imprimir("C:/Users/Public/Desktop/Unnamed.biro", entornoT.getText());
				JOptionPane.showMessageDialog(null, "Se ha guardado correctamente Unnamed.biro en el Escritorio");
				
				console.setText(console.getText()+"\nArchivo guardado.");
				} else {
				exp.Imprimir(txt, entornoT.getText());
				JOptionPane.showMessageDialog(null, "Se ha guardado correctamente "+getTitle()+" en el Escritorio");
				console.setText(console.getText()+"\nArchivo guardado.");
				}
									} else {
										
										ExportarOld exp = new ExportarOld();
										exp.Imprimir(getTitle(), entornoT.getText());
										JOptionPane.showMessageDialog(null, "Se ha guardado correctamente "+arch);
										console.setText(console.getText()+"\nArchivo guardado.");
									}
			}
		});
		
		
		
		abrirDoc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
			    fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			    
			    FileNameExtensionFilter birobytes = new FileNameExtensionFilter("Birobytes Coding Extension (.biro)", "biro", "BIRO", "Biro"); 
			    fileChooser.setFileFilter(birobytes);

			    int result = fileChooser.showOpenDialog(contentPane);

			    if (result != JFileChooser.CANCEL_OPTION) {

			        File fileName = fileChooser.getSelectedFile();
						txt="";
					if ((fileName == null) || (fileName.getName().equals(""))) {
			            txt="...";
			        } else {
			            txt=fileName.getAbsolutePath();
			            arch = fileName.getName();
			            String texto = "", aux="";
			            FileReader archivos = null;
						try {
							archivos = new FileReader(txt);
						} catch (FileNotFoundException e1) {
							e1.printStackTrace();
						}
			            BufferedReader leer = new BufferedReader(archivos);
			            try {
							while ((aux = leer.readLine()) != null) {
							texto += aux + "\n";
							}
						} catch (IOException e1) {
							e1.printStackTrace();
						}
			            try {
							leer.close();
						} catch (IOException e1) {
							e1.printStackTrace();
						}
			            entornoT.setText(texto);
			            setTitle(arch);
			            
			            filenamer.setText(arch);
						abiertos=("Abierto");
			        }
			    }

			}
		});
		
		Timer hilo = new Timer(500, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				BiroTexto tipodatos = new BiroTexto(entornoT);
				tipodatos.highlightKeywords();
				BiroPuntos hl = new BiroPuntos(entornoT);
				hl.highlightDotWords();
				BiroCadena comillas = new BiroCadena(entornoT);
				comillas.highlightQuotedWords();
				BiroSignos sign = new BiroSignos(entornoT);
				sign.senalizar();
				BiroComments comenta = new BiroComments(entornoT);
				comenta.comentar();
			}
		});
		
		Timer hilo2 = new Timer(10000, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				BiroExtra extra = new BiroExtra(entornoT);
				extra.highlight();
			}
		});
		hilo2.start();
		hilo.start();
		entornoT.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				
			}
		});
		
		
		guardarcomoDoc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ExportarOld birotes = new ExportarOld();
				JFileChooser fileChooser = new JFileChooser();
			    fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			    
			    FileNameExtensionFilter birobytes = new FileNameExtensionFilter("Birobytes Coding Extension (*.biro)", "biro"); 
			    fileChooser.setFileFilter(birobytes);

			    int result = fileChooser.showOpenDialog(contentPane);

			    if (result != JFileChooser.CANCEL_OPTION) {

			        File fileName = fileChooser.getSelectedFile();
						String txt="";
					
					if ((fileName == null) || (fileName.getName().equals(""))) {
			            txt="...";
			        } else {
			            txt=fileName.getAbsolutePath();
			            birotes.Imprimir(txt+".biro", entornoT.getText());
			        }
			    }
			}
		});
		
		
		lblNewLabel_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				Optimizar opti = new Optimizar();
				String code = opti.optimizar(entornoT.getText());
				entornoT.setText(code);
				console.setText(console.getText()+"\nCodigo optimizado correctamente.");
				
			}
			
			
			
			
			
			
		});
	}
}
