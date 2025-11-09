package com.david.view;

import java.awt.Color;
import java.awt.Font;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;

import com.david.controller.Coordinador;
import com.david.model.Moneda;
import com.formdev.flatlaf.intellijthemes.FlatDarkPurpleIJTheme;

public class VentanaPrincipal extends JFrame {
    private Coordinador coordinador;

    private JPanel panelPrincipal;
    private JLabel lblDescripcion, lblFecha;
    private JComboBox<String> comboBoxMoneda1, comboBoxMoneda2;
    private JTextField txtCantidad, txtResultado;
    private JButton btnConvertir;

    public VentanaPrincipal() {
        // Configuración de la ventana
        setTitle("Conversor de Monedas");
        setSize(400, 250);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);
    }

    public void iniciarComponentes() {
        try {

            FlatDarkPurpleIJTheme.setup();

            // Panel principal
            panelPrincipal = new JPanel();
            panelPrincipal.setBounds(0, 0, 400, 250);
            panelPrincipal.setLayout(null);
            panelPrincipal.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
            getContentPane().add(panelPrincipal);

            // ComboBoxes
            comboBoxMoneda1 = new JComboBox<String>(new DefaultComboBoxModel<String>(coordinador.getTasas("MXN")));
            comboBoxMoneda1.setFont(new Font("Consolas", Font.PLAIN, 14));
            comboBoxMoneda1.setBounds(20, 80, 220, 25);
            comboBoxMoneda1.addActionListener(e -> {
                cargarDatos();
            });
            panelPrincipal.add(comboBoxMoneda1);

            comboBoxMoneda2 = new JComboBox<String>(new DefaultComboBoxModel<String>(coordinador.getTasas("USD")));
            comboBoxMoneda2.setFont(new Font("Consolas", Font.PLAIN, 14));
            comboBoxMoneda2.setBounds(20, 120, 220, 25);
            comboBoxMoneda2.addActionListener(e -> {
                cargarDatos();
            });
            panelPrincipal.add(comboBoxMoneda2);

            txtCantidad = new JTextField("1");
            txtCantidad.setFont(new Font("Consolas", Font.PLAIN, 14));
            txtCantidad.setBounds(250, 80, 120, 25);
            panelPrincipal.add(txtCantidad);

            txtResultado = new JTextField();
            txtResultado.setFont(new Font("Consolas", Font.PLAIN, 14));
            txtResultado.setBounds(250, 120, 120, 25);
            txtResultado.setEditable(false);
            panelPrincipal.add(txtResultado);

            // Etiquetas 
            lblDescripcion = new JLabel();
            lblDescripcion.setHorizontalAlignment(SwingConstants.CENTER);
            lblDescripcion.setFont(new Font("Consolas", Font.PLAIN, 15));
            lblDescripcion.setForeground(new Color(255, 255, 255));
            lblDescripcion.setBounds(70, 20, 250, 25);
            panelPrincipal.add(lblDescripcion);

            lblFecha = new JLabel();
            lblFecha.setHorizontalAlignment(SwingConstants.CENTER);
            lblFecha.setFont(new Font("Consolas", Font.PLAIN, 12));
            lblFecha.setForeground(new Color(255, 255, 255));
            lblFecha.setBounds(70, 45, 250, 25);
            panelPrincipal.add(lblFecha);

            btnConvertir = new JButton("Convertir");
            btnConvertir.setFont(new Font("Consolas", Font.PLAIN, 14));
            btnConvertir.setBounds(250, 160, 120, 27);
            btnConvertir.setMnemonic('a');
            btnConvertir.addActionListener(e -> {
                txtResultado.setText(convertirMoneda());
            });
            panelPrincipal.add(btnConvertir);
            
            cargarDatos();
            setVisible(true);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al iniciar la interfaz: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void setCoordinador(Coordinador coordinador) {
        this.coordinador = coordinador;
    }

    public void cargarDatos() {
        Moneda moneda = coordinador.getMoneda(comboBoxMoneda1.getSelectedItem().toString());
        comboBoxMoneda1.setModel(new DefaultComboBoxModel<String>(coordinador.getTasas(comboBoxMoneda1.getSelectedItem().toString())));
        comboBoxMoneda2.setModel(new DefaultComboBoxModel<String>(coordinador.getTasas(comboBoxMoneda2.getSelectedItem().toString())));
        lblDescripcion.setText(moneda.rates.get(comboBoxMoneda1.getSelectedItem().toString()) + " " + comboBoxMoneda1.getSelectedItem().toString() + " Es igual a " + moneda.rates.get(comboBoxMoneda2.getSelectedItem().toString()) + " " + comboBoxMoneda2.getSelectedItem().toString());
        lblFecha.setText(moneda.timeLastUpdateUtc);
        txtCantidad.setText(txtCantidad.getText());
        txtResultado.setText(convertirMoneda());
    }

    public String convertirMoneda() {
        return coordinador.convertirMoneda(
                    comboBoxMoneda1.getSelectedItem().toString(),
                    comboBoxMoneda2.getSelectedItem().toString(),
                    Double.parseDouble(txtCantidad.getText())
                ) + "";
    }
}