package zahlenumrechner;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

public class GUI implements ActionListener{
    private static GUI instance;
    private final JFrame zahlenumrechner;
    private JPanel jp;
    private JLabel lbl_dual, lbl_oktal, lbl_dezi, lbl_hexa, lbl_Error;
    private JTextField eingabe, ausgabe_dual, ausgabe_oktal, ausgabe_dezi, ausgabe_hexa;
    private JButton btn_berechnen, btn_neu, btn_reset;
    private JRadioButton radiobtn_dual, radiobtn_dezi, radiobtn_oktal, radiobtn_hexa;
    private ButtonGroup zahlenarten;
    private final String[] hexArray = {"0","1","2","3","4","5","6","7","8","9","A","B","C","D","E","F"};
    
    private GUI(){
    zahlenumrechner = new JFrame("Zahlenumrechner");
    zahlenumrechner.setSize(400,370);
    zahlenumrechner.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    zahlenumrechner.add(getContainer());
    // Fensterposition zentrieren
    zahlenumrechner.setLocationRelativeTo(null);
    zahlenumrechner.setVisible(true);
    }
    
    public static GUI getInstance(){
        // Abfrage, ob eine Instanz existiert
        if(GUI.instance == null){
           GUI.instance = new GUI();
        }
        return GUI.instance;
    }
    
    private JPanel getContainer(){
        jp = new JPanel();
        jp.setLayout(null);
        
        eingabe = new JTextField();
        eingabe.setBounds(20,20,200,30);
        jp.add(eingabe);
        
        //Radiobutton hinzufügen
        radiobtn_dual = new JRadioButton("Dual");
        radiobtn_oktal = new JRadioButton("Oktal");
        radiobtn_dezi = new JRadioButton("Dezimal");
        radiobtn_hexa = new JRadioButton("Hexadezimal");
        radiobtn_dual.setBounds(20, 60, 60, 20);
        radiobtn_oktal.setBounds(90, 60, 60, 20);
        radiobtn_dezi.setBounds(160, 60, 75, 20);
        radiobtn_hexa.setBounds(240, 60, 100, 20);
        // ButtonGruppe erstellen
        zahlenarten = new ButtonGroup();
        zahlenarten.add(radiobtn_dual);
        zahlenarten.add(radiobtn_oktal);
        zahlenarten.add(radiobtn_dezi);
        zahlenarten.add(radiobtn_hexa);
        jp.add(radiobtn_dual);
        jp.add(radiobtn_oktal);
        jp.add(radiobtn_dezi);
        jp.add(radiobtn_hexa);
        radiobtn_dual.addActionListener(this);
        radiobtn_oktal.addActionListener(this);
        radiobtn_dezi.addActionListener(this);
        radiobtn_hexa.addActionListener(this);
        
        lbl_Error = new JLabel();
        lbl_Error.setBounds(20 ,85 , 350, 20);
        lbl_Error.setForeground(Color.RED);
        jp.add(lbl_Error);
        
        btn_berechnen = new JButton("Berechnen");
        btn_neu = new JButton("Neu");
        btn_reset = new JButton("Reset"); 
        btn_berechnen.setBounds(20,120,100,30);
        btn_neu.setBounds(140,120,100,30);
        btn_reset.setBounds(260,120,100,30);
        jp.add(btn_berechnen);
        jp.add(btn_neu);
        jp.add(btn_reset);
        btn_berechnen.addActionListener(this);
        btn_neu.addActionListener(this);
        btn_reset.addActionListener(this);
        
        lbl_dual = new JLabel("Dual");
        lbl_oktal = new JLabel("Oktal");
        lbl_dezi = new JLabel("Dezimal");
        lbl_hexa = new JLabel("Hexadezimal");       
        lbl_dual.setBounds(20,160,100,30);
        lbl_oktal.setBounds(20,200,100,30);
        lbl_dezi.setBounds(20,240,100,30);
        lbl_hexa.setBounds(20,280,100,30);
        jp.add(lbl_dual);
        jp.add(lbl_oktal);
        jp.add(lbl_dezi);
        jp.add(lbl_hexa);
        
        ausgabe_dual = new JTextField();
        ausgabe_oktal = new JTextField();
        ausgabe_dezi = new JTextField();
        ausgabe_hexa = new JTextField();
        ausgabe_dual.setBounds(140,160,220,30);
        ausgabe_oktal.setBounds(140,200,220,30);
        ausgabe_dezi.setBounds(140,240,220,30);
        ausgabe_hexa.setBounds(140,280,220,30);
        jp.add(ausgabe_dual);
        jp.add(ausgabe_oktal);
        jp.add(ausgabe_dezi);
        jp.add(ausgabe_hexa);
        
        return jp;
    }
    
    private String deziToAll(int dezi, int ziel){
        String erg = "";
        int i = 0;
        while(dezi/ziel > 0){
            if(i>0){
                dezi/=ziel;                
            }
            if(ziel == 16){
                erg = hexArray[dezi%ziel] + erg;    
            }else{
                erg = dezi%ziel + erg;
            }
            i++;
        }
        return erg; 
    }
    
    private int getHexaValue(String hexa){
        int i = -1;
        for (i = 0; i < hexArray.length; i++) {
            if(hexa.equals(hexArray[i])){
                return i;
            }
        }
        return i;
    }
    
    private int allToDezi(String zahl, int basis){
        int erg = 0;
        if(basis == 16){
            for (int i = 0; i < zahl.length(); i++) {
                erg = (int)Math.pow(basis, i) * getHexaValue((String.valueOf(zahl.charAt(zahl.length()-1-i)))) + erg;
            }
        }else {
            for (int i = 0; i < zahl.length(); i++) {
                erg = (int)Math.pow(basis, i) * Integer.parseInt(String.valueOf(zahl.charAt(zahl.length()-1-i))) + erg;
            }
        }
        return erg;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        int eingabe_basis = 0;        
        if(radiobtn_dual.isSelected()){eingabe_basis = 2;}
        if(radiobtn_oktal.isSelected()){eingabe_basis = 8;}
        if(radiobtn_dezi.isSelected()){eingabe_basis = 10;}
        if(radiobtn_hexa.isSelected()){eingabe_basis = 16;}
          
        if(e.getSource() == this.btn_berechnen){          
            switch(eingabe_basis){
                case 2: 
                    ausgabe_dual.setText(eingabe.getText());
                    ausgabe_oktal.setText(deziToAll(allToDezi(eingabe.getText(), 2), 8));
                    ausgabe_dezi.setText(String.valueOf(allToDezi(eingabe.getText(), 2)));
                    ausgabe_hexa.setText(deziToAll(allToDezi(eingabe.getText(), 2), 16));
                break;
                case 8: 
                    ausgabe_dual.setText(deziToAll(allToDezi(eingabe.getText(), 8), 2));
                    ausgabe_oktal.setText(eingabe.getText());
                    ausgabe_dezi.setText(String.valueOf(allToDezi(eingabe.getText(), 8)));
                    ausgabe_hexa.setText(deziToAll(allToDezi(eingabe.getText(), 8), 16));
                break;
                case 10: 
                    ausgabe_dual.setText(String.valueOf(deziToAll(Integer.parseInt(eingabe.getText()), 2)));
                    ausgabe_oktal.setText(String.valueOf(deziToAll(Integer.parseInt(eingabe.getText()), 8)));
                    ausgabe_dezi.setText(eingabe.getText());
                    ausgabe_hexa.setText(String.valueOf(deziToAll(Integer.parseInt(eingabe.getText()), 16)));
                break;
                case 16: 
                    ausgabe_dual.setText(deziToAll(allToDezi(eingabe.getText(), 16), 2));
                    ausgabe_oktal.setText(deziToAll(allToDezi(eingabe.getText(), 16), 8));
                    ausgabe_dezi.setText(String.valueOf(allToDezi(eingabe.getText(), 16)));
                    ausgabe_hexa.setText(eingabe.getText());
                break;
                default:
                    lbl_Error.setText("Bitte wählen sie zuerst eine Zielbasis aus.");
                break;
            }
        }
        if(e.getSource() == this.btn_reset){
            ausgabe_dual.setText("");
            ausgabe_oktal.setText("");
            ausgabe_dezi.setText("");
            ausgabe_hexa.setText("");
            eingabe.setText("");
            lbl_Error.setText("");
            zahlenarten.clearSelection();           
        }
    }
}
