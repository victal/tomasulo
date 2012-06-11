package main;
import instructions.Instrucao;

import java.awt.EventQueue;
import java.awt.Font;
import java.io.File;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

import processor.Processor;
import reserve.ReserveStation;
import buffers.ReorderingBuffer;
import buffers.ReorderingLine;


public class Gui {

	private JFrame frame;
	private JTextField[] registradores = new JTextField[32];
	private JTextField R8;
	private JTextField R9;
	private JTextField R10;
	private JTextField R11;
	private JTextField R12;
	private JTextField R13;
	private JTextField R14;
	private JTextField R0;
	private JTextField R1;
	private JTextField R2;
	private JTextField R5;
	private JTextField R4;
	private JTextField R3;
	private JTextField R6;
	private JTextField R16;
	private JTextField R17;
	private JTextField R18;
	private JTextField R19;
	private JTextField R20;
	private JTextField R22;
	private JTextField R21;
	private JLabel lblR_8;
	private JTextField R7;
	private JLabel lblR_15;
	private JTextField R15;
	private JLabel lblR_23;
	private JTextField R23;
	private JTextField R24;
	private JLabel lblR_24;
	private JTextField R25;
	private JLabel lblR_25;
	private JTextField R26;
	private JLabel lblR_26;
	private JTextField R27;
	private JLabel lblR_27;
	private JTextField R28;
	private JLabel lblR_28;
	private JTextField R29;
	private JLabel lblR_29;
	private JTextField R30;
	private JLabel lblR_30;
	private JTextField R31;
	private JLabel lblR_31;
	private JTextField cpi;
	private JTextField pc;
	private JTextField numInstConcluidas;
	private JTextField clockCorrente;
	private JTextField endereco1;
	private JTextField valEnd1;
	private JTextField endereco2;
	private JTextField valEnd2;
	private JTextField endereco3;
	private JTextField valEnd3;
	private JTextField endereco4;
	private JTextField valEnd4;
	private JTable estacaoReserva;
	private JLabel lblId;
	private JLabel lblBusy;
	private JLabel lblInstruo;
	private JLabel lblDest;
	private JLabel lblVj;
	private JLabel lblVk;
	private JLabel lblQj;
	private JLabel lblQk;
	private JLabel lblA;
	private JTable tablbufferReordenacao;
	private JLabel lblBufferDeReordenao;
	private JLabel lblEntrada;
	private JLabel lblOcupado;
	private JLabel lblInstrucao;
	private JLabel lblEstado;
	private JLabel lblDestino;
	private JLabel lblValor_1;
	private Processor p;
	private Runner runner;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					File f = new File("teste.mips");
					Processor p = Utils.buildProcessor(f, 2);
					Gui window = new Gui(p);
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * Create the application.
	 */
	public Gui() {
		initialize();
	}
	
	public Gui(Processor p){
		this.p = p;
		runner = new Runner(this, p);
		runner.execute();
		initialize();
		runner.setRun(true);
	}
	
	public void setReg(int i, float value)
	{
		registradores[i].setText(String.valueOf(value));
	}
	
	public void useAddress(String address, String value)
	{
		endereco4.setText(endereco3.getText());
		valEnd4.setText(valEnd3.getText());
		
		endereco3.setText(endereco2.getText());
		valEnd3.setText(valEnd2.getText());
		
		endereco2.setText(endereco1.getText());
		valEnd2.setText(valEnd1.getText());
		
		endereco1.setText(address);
		valEnd1.setText(value);
	}
	
	public void setClockCorrente(int value)
	{
		clockCorrente.setText(Integer.toString(value));
	}
	
	public void setPc(int value)
	{
		pc.setText(Integer.toString(value));
	}
	
	public void setCpi(float value)
	{
		cpi.setText(String.valueOf(value));
	}
	
	public void populateReserveStation(int stationId, String tipo, String busy, String inst, Integer dest, Integer vj, Integer vk, Integer qj, Integer qk, Integer a)
	{
		setTipo(stationId, tipo);
		setBusy(stationId,busy);
		setInst(stationId, inst);
		setDest(stationId, dest);
		setVj(stationId, vj);
		setVk(stationId, vk);
		setQj(stationId, qj);
		setQk(stationId, qk);
		setA(stationId, a);
	}
	public void updateReorder(){
		List<ReorderingLine> lines = p.getReorder().getLines();
		for(int i = 0;i<10;i++){
			tablbufferReordenacao.getModel().setValueAt(i, i, 0);
			tablbufferReordenacao.getModel().setValueAt(lines.get(i).isBusy(), i, 1);
			if(lines.get(i).getInst()!=null)
				tablbufferReordenacao.getModel().setValueAt(lines.get(i).getInst().getNome(), i, 2);
			tablbufferReordenacao.getModel().setValueAt(lines.get(i).getState(), i, 3);
			tablbufferReordenacao.getModel().setValueAt(lines.get(i).getDest(), i, 4);
			tablbufferReordenacao.getModel().setValueAt(lines.get(i).getValue(), i, 5);
			tablbufferReordenacao.clearSelection();
			tablbufferReordenacao.changeSelection(p.getReorder().getListInit(), 0, true, false);
			tablbufferReordenacao.changeSelection(p.getReorder().getListInit(), 5, false,true);
			
		}
	}
	public void setBusy(int stationId, String value)
	{
		if(value!=null)
			estacaoReserva.getModel().setValueAt(value, stationId, 1);
		else
			estacaoReserva.getModel().setValueAt(" ", stationId, 1);
	}
	
	public void setTipo(int stationId, String value)
	{
		if(value!=null)
			estacaoReserva.getModel().setValueAt(value, stationId, 0);
		else
			estacaoReserva.getModel().setValueAt(" ", stationId, 0);
	}
	
	public void setInst(int stationId, String value)
	{
		if(value!=null)
			estacaoReserva.getModel().setValueAt(value, stationId, 2);
		else
			estacaoReserva.getModel().setValueAt(" ", stationId, 2);
	}
	
	public void setDest(int stationId, Integer value)
	{
		if(value!=null)
			estacaoReserva.getModel().setValueAt(value.toString(), stationId, 3);
		else
			estacaoReserva.getModel().setValueAt(" ", stationId, 3);
	}
	
	public void setVj(int stationId, Integer value)
	{
		if(value!=null)
			estacaoReserva.getModel().setValueAt(value, stationId, 4);
		else
			estacaoReserva.getModel().setValueAt(" ", stationId, 4);
	}
	
	public void setVk(int stationId, Integer value)
	{
		if(value!=null)
			estacaoReserva.getModel().setValueAt(value, stationId, 5);
		else
			estacaoReserva.getModel().setValueAt(" ", stationId, 5);
	}
	
	public void setQj(int stationId, Integer qj)
	{
		if(qj!=null)
			estacaoReserva.getModel().setValueAt(qj, stationId, 6);
		else
			estacaoReserva.getModel().setValueAt(" ", stationId, 6);
	}
	
	public void setQk(int stationId, Integer qk)
	{
		if(qk!=null)
			estacaoReserva.getModel().setValueAt(qk, stationId, 7);
		else
			estacaoReserva.getModel().setValueAt(" ", stationId, 7);
	}
	
	public void setA(int stationId, Integer a)
	{
		if(a!=null)
			estacaoReserva.getModel().setValueAt(a, stationId, 8);
		else
			estacaoReserva.getModel().setValueAt(" ", stationId, 8);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 960, 548);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		R8 = new JTextField();
		R8.setBounds(759, 262, 35, 20);
		frame.getContentPane().add(R8);
		R8.setColumns(10);
		registradores[8] = R8;
		
		JLabel lblR = new JLabel("R8");
		lblR.setBounds(738, 265, 46, 14);
		frame.getContentPane().add(lblR);
		
		R9 = new JTextField();
		R9.setColumns(10);
		R9.setBounds(759, 293, 35, 20);
		frame.getContentPane().add(R9);
		registradores[9] = R9;
		
		JLabel lblR_9 = new JLabel("R9");
		lblR_9.setBounds(738, 296, 46, 14);
		frame.getContentPane().add(lblR_9);
		
		R10 = new JTextField();
		R10.setColumns(10);
		R10.setBounds(759, 324, 35, 20);
		frame.getContentPane().add(R10);
		registradores[10] = R10;
		
		JLabel lblR_10 = new JLabel("R10");
		lblR_10.setBounds(738, 327, 46, 14);
		frame.getContentPane().add(lblR_10);
		
		R11 = new JTextField();
		R11.setColumns(10);
		R11.setBounds(759, 355, 35, 20);
		frame.getContentPane().add(R11);
		registradores[11] = R11;
		JLabel lblR_11 = new JLabel("R11");
		lblR_11.setBounds(738, 358, 46, 14);
		frame.getContentPane().add(lblR_11);
		
		R12 = new JTextField();
		R12.setColumns(10);
		R12.setBounds(759, 386, 35, 20);
		frame.getContentPane().add(R12);
		registradores[12] = R12;
		
		JLabel lblR_12 = new JLabel("R12");
		lblR_12.setBounds(738, 389, 46, 14);
		frame.getContentPane().add(lblR_12);
		
		R13 = new JTextField();
		R13.setColumns(10);
		R13.setBounds(759, 417, 35, 20);
		frame.getContentPane().add(R13);
		registradores[13] = R13;
		
		JLabel lblR_13 = new JLabel("R13");
		lblR_13.setBounds(738, 420, 46, 14);
		frame.getContentPane().add(lblR_13);
		
		R14 = new JTextField();
		R14.setColumns(10);
		R14.setBounds(759, 448, 35, 20);
		frame.getContentPane().add(R14);
		registradores[14] = R14;
		
		JLabel lblR_14 = new JLabel("R14");
		lblR_14.setBounds(738, 451, 46, 14);
		frame.getContentPane().add(lblR_14);
		
		R0 = new JTextField();
		R0.setColumns(10);
		R0.setBounds(676, 262, 35, 20);
		frame.getContentPane().add(R0);
		registradores[0] = R0;
		
		JLabel lblR_1 = new JLabel("R0");
		lblR_1.setBounds(653, 265, 46, 14);
		frame.getContentPane().add(lblR_1);
		
		R1 = new JTextField();
		R1.setColumns(10);
		R1.setBounds(676, 293, 35, 20);
		frame.getContentPane().add(R1);
		registradores[1] = R1;
		
		JLabel lblR_2 = new JLabel("R1");
		lblR_2.setBounds(653, 296, 46, 14);
		frame.getContentPane().add(lblR_2);
		registradores[1] = R1;
		
		R2 = new JTextField();
		R2.setColumns(10);
		R2.setBounds(676, 324, 35, 20);
		frame.getContentPane().add(R2);
		registradores[2] = R2;
		
		JLabel lblR_3 = new JLabel("R2");
		lblR_3.setBounds(653, 327, 46, 14);
		frame.getContentPane().add(lblR_3);
		
		R5 = new JTextField();
		R5.setColumns(10);
		R5.setBounds(676, 417, 35, 20);
		frame.getContentPane().add(R5);
		registradores[5] = R5;
		
		R4 = new JTextField();
		R4.setColumns(10);
		R4.setBounds(676, 386, 35, 20);
		frame.getContentPane().add(R4);
		registradores[4] = R4;
		
		JLabel lblR_5 = new JLabel("R4");
		lblR_5.setBounds(653, 392, 46, 14);
		frame.getContentPane().add(lblR_5);
		
		R3 = new JTextField();
		R3.setColumns(10);
		R3.setBounds(676, 355, 35, 20);
		frame.getContentPane().add(R3);
		registradores[3] = R3;
		
		JLabel lblR_4 = new JLabel("R3");
		lblR_4.setBounds(653, 361, 46, 14);
		frame.getContentPane().add(lblR_4);
		
		JLabel lblR_6 = new JLabel("R5");
		lblR_6.setBounds(653, 423, 46, 14);
		frame.getContentPane().add(lblR_6);
		
		R6 = new JTextField();
		R6.setColumns(10);
		R6.setBounds(676, 448, 35, 20);
		frame.getContentPane().add(R6);
		registradores[6] = R6;
		
		JLabel lblR_7 = new JLabel("R6");
		lblR_7.setBounds(653, 454, 46, 14);
		frame.getContentPane().add(lblR_7);
		
		R16 = new JTextField();
		R16.setColumns(10);
		R16.setBounds(837, 262, 35, 20);
		frame.getContentPane().add(R16);
		registradores[16] = R16;
		
		JLabel lblR_16 = new JLabel("R16");
		lblR_16.setBounds(816, 268, 46, 14);
		frame.getContentPane().add(lblR_16);
		
		R17 = new JTextField();
		R17.setColumns(10);
		R17.setBounds(837, 293, 35, 20);
		frame.getContentPane().add(R17);
		registradores[17] = R17;
		
		JLabel lblR_17 = new JLabel("R17");
		lblR_17.setBounds(816, 299, 46, 14);
		frame.getContentPane().add(lblR_17);
		
		R18 = new JTextField();
		R18.setColumns(10);
		R18.setBounds(837, 324, 35, 20);
		frame.getContentPane().add(R18);
		registradores[18] = R18;
		
		JLabel lblR_18 = new JLabel("R18");
		lblR_18.setBounds(816, 330, 46, 14);
		frame.getContentPane().add(lblR_18);
		
		R19 = new JTextField();
		R19.setColumns(10);
		R19.setBounds(837, 355, 35, 20);
		frame.getContentPane().add(R19);
		registradores[19] = R19;
		
		JLabel lblR_19 = new JLabel("R19");
		lblR_19.setBounds(816, 361, 46, 14);
		frame.getContentPane().add(lblR_19);
		
		R20 = new JTextField();
		R20.setColumns(10);
		R20.setBounds(837, 388, 35, 20);
		frame.getContentPane().add(R20);
		registradores[20] = R20;
		
		JLabel lblR_20 = new JLabel("R20");
		lblR_20.setBounds(816, 392, 46, 14);
		frame.getContentPane().add(lblR_20);
		
		R22 = new JTextField();
		R22.setColumns(10);
		R22.setBounds(837, 450, 35, 20);
		frame.getContentPane().add(R22);
		registradores[22] = R22;
		
		R21 = new JTextField();
		R21.setColumns(10);
		R21.setBounds(837, 419, 35, 20);
		frame.getContentPane().add(R21);
		registradores[21] = R21;
		
		JLabel lblR_21 = new JLabel("R21");
		lblR_21.setBounds(816, 423, 46, 14);
		frame.getContentPane().add(lblR_21);
		
		JLabel lblR_22 = new JLabel("R22");
		lblR_22.setBounds(816, 454, 46, 14);
		frame.getContentPane().add(lblR_22);
		
		lblR_8 = new JLabel("R7");
		lblR_8.setBounds(653, 485, 46, 14);
		frame.getContentPane().add(lblR_8);
		
		R7 = new JTextField();
		R7.setColumns(10);
		R7.setBounds(676, 479, 35, 20);
		frame.getContentPane().add(R7);
		registradores[7] = R7;
		
		lblR_15 = new JLabel("R15");
		lblR_15.setBounds(738, 485, 46, 14);
		frame.getContentPane().add(lblR_15);
		
		R15 = new JTextField();
		R15.setColumns(10);
		R15.setBounds(759, 479, 35, 20);
		frame.getContentPane().add(R15);
		registradores[15] = R15;
		
		lblR_23 = new JLabel("R23");
		lblR_23.setBounds(816, 485, 46, 14);
		frame.getContentPane().add(lblR_23);
		
		R23 = new JTextField();
		R23.setColumns(10);
		R23.setBounds(837, 481, 35, 20);
		frame.getContentPane().add(R23);
		registradores[23] = R23;
		
		R24 = new JTextField();
		R24.setColumns(10);
		R24.setBounds(909, 263, 35, 20);
		frame.getContentPane().add(R24);
		registradores[24] = R24;
		
		lblR_24 = new JLabel("R24");
		lblR_24.setBounds(882, 269, 46, 14);
		frame.getContentPane().add(lblR_24);
		
		R25 = new JTextField();
		R25.setColumns(10);
		R25.setBounds(909, 294, 35, 20);
		frame.getContentPane().add(R25);
		registradores[25] = R25;
		
		lblR_25 = new JLabel("R25");
		lblR_25.setBounds(882, 300, 46, 14);
		frame.getContentPane().add(lblR_25);
		
		R26 = new JTextField();
		R26.setColumns(10);
		R26.setBounds(909, 325, 35, 20);
		frame.getContentPane().add(R26);
		registradores[26] = R26;
		
		lblR_26 = new JLabel("R26");
		lblR_26.setBounds(882, 330, 46, 14);
		frame.getContentPane().add(lblR_26);
		
		R27 = new JTextField();
		R27.setColumns(10);
		R27.setBounds(909, 356, 35, 20);
		frame.getContentPane().add(R27);
		registradores[27] = R27;
		
		lblR_27 = new JLabel("R27");
		lblR_27.setBounds(882, 361, 46, 14);
		frame.getContentPane().add(lblR_27);
		
		R28 = new JTextField();
		R28.setColumns(10);
		R28.setBounds(909, 386, 35, 20);
		frame.getContentPane().add(R28);
		registradores[28] = R28;
		
		lblR_28 = new JLabel("R28");
		lblR_28.setBounds(882, 392, 46, 14);
		frame.getContentPane().add(lblR_28);
		
		R29 = new JTextField();
		R29.setColumns(10);
		R29.setBounds(909, 417, 35, 20);
		frame.getContentPane().add(R29);
		registradores[29] = R29;
		
		lblR_29 = new JLabel("R29");
		lblR_29.setBounds(882, 423, 46, 14);
		frame.getContentPane().add(lblR_29);
		
		R30 = new JTextField();
		R30.setColumns(10);
		R30.setBounds(909, 448, 35, 20);
		frame.getContentPane().add(R30);
		registradores[30] = R30;
		
		lblR_30 = new JLabel("R30");
		lblR_30.setBounds(882, 454, 46, 14);
		frame.getContentPane().add(lblR_30);
		
		R31 = new JTextField();
		R31.setColumns(10);
		R31.setBounds(909, 479, 35, 20);
		frame.getContentPane().add(R31);
		registradores[31] = R31;
		
		lblR_31 = new JLabel("R31");
		lblR_31.setBounds(882, 485, 46, 14);
		frame.getContentPane().add(lblR_31);
		
		JLabel lblClockCorrente = new JLabel("Clock corrente:");
		lblClockCorrente.setBounds(653, 186, 73, 14);
		frame.getContentPane().add(lblClockCorrente);
		
		JLabel lblPc = new JLabel("PC:");
		lblPc.setBounds(653, 211, 46, 14);
		frame.getContentPane().add(lblPc);
		
		JLabel lblNmeroDeInstrues = new JLabel("N\u00FAm. instr. conclu\u00EDdas:");
		lblNmeroDeInstrues.setBounds(773, 186, 109, 14);
		frame.getContentPane().add(lblNmeroDeInstrues);
		
		JLabel lblPc_1 = new JLabel("Clock por Instru\u00E7\u00E3o (CPI):");
		lblPc_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPc_1.setBounds(750, 211, 132, 14);
		frame.getContentPane().add(lblPc_1);
		
		cpi = new JTextField();
		cpi.setBounds(882, 208, 46, 20);
		frame.getContentPane().add(cpi);
		cpi.setColumns(10);
		
		pc = new JTextField();
		pc.setBounds(676, 208, 64, 20);
		frame.getContentPane().add(pc);
		pc.setColumns(10);
		
		numInstConcluidas = new JTextField();
		numInstConcluidas.setBounds(882, 180, 46, 20);
		frame.getContentPane().add(numInstConcluidas);
		numInstConcluidas.setColumns(10);
		
		clockCorrente = new JTextField();
		clockCorrente.setBounds(725, 183, 46, 20);
		frame.getContentPane().add(clockCorrente);
		clockCorrente.setColumns(10);
		
		JLabel lblMemriaRecentementeUsada = new JLabel("Mem\u00F3ria recentemente usada");
		lblMemriaRecentementeUsada.setBounds(738, 11, 144, 14);
		frame.getContentPane().add(lblMemriaRecentementeUsada);
		
		endereco1 = new JTextField();
		endereco1.setBounds(708, 61, 86, 20);
		frame.getContentPane().add(endereco1);
		endereco1.setColumns(10);
		
		valEnd1 = new JTextField();
		valEnd1.setBounds(816, 61, 86, 20);
		frame.getContentPane().add(valEnd1);
		valEnd1.setColumns(10);
		
		JLabel lblEndereo = new JLabel("Endere\u00E7o");
		lblEndereo.setBounds(725, 36, 46, 14);
		frame.getContentPane().add(lblEndereo);
		
		JLabel lblValor = new JLabel("Valor");
		lblValor.setBounds(848, 36, 24, 14);
		frame.getContentPane().add(lblValor);
		
		endereco2 = new JTextField();
		endereco2.setBounds(708, 92, 86, 20);
		frame.getContentPane().add(endereco2);
		endereco2.setColumns(10);
		
		valEnd2 = new JTextField();
		valEnd2.setBounds(816, 92, 86, 20);
		frame.getContentPane().add(valEnd2);
		valEnd2.setColumns(10);
		
		endereco3 = new JTextField();
		endereco3.setBounds(708, 123, 86, 20);
		frame.getContentPane().add(endereco3);
		endereco3.setColumns(10);
		
		valEnd3 = new JTextField();
		valEnd3.setBounds(816, 123, 86, 20);
		frame.getContentPane().add(valEnd3);
		valEnd3.setColumns(10);
		
		endereco4 = new JTextField();
		endereco4.setBounds(708, 154, 86, 20);
		frame.getContentPane().add(endereco4);
		endereco4.setColumns(10);
		
		valEnd4 = new JTextField();
		valEnd4.setBounds(816, 154, 86, 20);
		frame.getContentPane().add(valEnd4);
		valEnd4.setColumns(10);
		
		JLabel lblEstaesDeReserva = new JLabel("Esta\u00E7\u00F5es de Reserva");
		lblEstaesDeReserva.setBounds(210, 53, 101, 14);
		frame.getContentPane().add(lblEstaesDeReserva);
		
		estacaoReserva = new JTable();
		estacaoReserva.setBorder(UIManager.getBorder("ComboBox.border"));
		estacaoReserva.setFont(new Font("Tahoma", Font.PLAIN, 11));
		estacaoReserva.setToolTipText("true");
		estacaoReserva.setModel(new DefaultTableModel(
			new Object[][] {
				{"ER1", null, null, null, null, null, null, null, null},
				{"ER2", null, null, null, null, null, null, null, null},
				{"ER3", null, null, null, null, null, null, null, null},
				{"ER4", null, null, null, null, null, null, null, null},
				{"ER5", null, null, null, null, null, null, null, null},
				{"ER6", null, null, null, null, null, null, null, null},
				{"ER7", null, null, null, null, null, null, null, null},
				{"ER8", null, null, null, null, null, null, null, null},
				{"ER9", null, null, null, null, null, null, null, null},
				{"ER10", null, null, null, null, null, null, null, null},
				{"ER11", null, null, null, null, null, null, null, null},
			},
			new String[] {
				"ID", "Tipo", "New column", "New column", "New column", "New column", "New column", "New column", "New column"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				true, false, true, true, true, true, true, true, true
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		estacaoReserva.getColumnModel().getColumn(0).setPreferredWidth(34);
		estacaoReserva.getColumnModel().getColumn(1).setResizable(false);
		estacaoReserva.setBounds(30, 88, 514, 176);
		frame.getContentPane().add(estacaoReserva);
		
		lblId = new JLabel("ID");
		lblId.setBounds(37, 74, 11, 14);
		frame.getContentPane().add(lblId);
		
		lblBusy = new JLabel("Busy");
		lblBusy.setBounds(74, 74, 29, 14);
		frame.getContentPane().add(lblBusy);
		
		lblInstruo = new JLabel("Instru\u00E7\u00E3o");
		lblInstruo.setBounds(124, 74, 46, 14);
		frame.getContentPane().add(lblInstruo);
		
		lblDest = new JLabel("Dest.");
		lblDest.setBounds(193, 74, 35, 14);
		frame.getContentPane().add(lblDest);
		
		lblVj = new JLabel("Vj");
		lblVj.setBounds(264, 74, 11, 14);
		frame.getContentPane().add(lblVj);
		
		lblVk = new JLabel("Vk");
		lblVk.setBounds(323, 74, 11, 14);
		frame.getContentPane().add(lblVk);
		
		lblQj = new JLabel("Qj");
		lblQj.setBounds(383, 74, 11, 14);
		frame.getContentPane().add(lblQj);
		
		lblQk = new JLabel("Qk");
		lblQk.setBounds(447, 74, 18, 14);
		frame.getContentPane().add(lblQk);
		
		lblA = new JLabel("A");
		lblA.setBounds(507, 74, 11, 14);
		frame.getContentPane().add(lblA);
		
		tablbufferReordenacao = new JTable();
		tablbufferReordenacao.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
			},
			new String[] {
				"Entrada", "Ocupado", "Instru\u00E7\u00E3o", "Estado", "Destino", "Valor"
			}
		));
		tablbufferReordenacao.getColumnModel().getColumn(0).setPreferredWidth(53);
		tablbufferReordenacao.getColumnModel().getColumn(1).setPreferredWidth(58);
		tablbufferReordenacao.getColumnModel().getColumn(2).setPreferredWidth(113);
		tablbufferReordenacao.getColumnModel().getColumn(3).setPreferredWidth(68);
		tablbufferReordenacao.getColumnModel().getColumn(4).setPreferredWidth(52);
		tablbufferReordenacao.getColumnModel().getColumn(5).setPreferredWidth(54);
		tablbufferReordenacao.setBounds(30, 327, 514, 160);
		frame.getContentPane().add(tablbufferReordenacao);
		
		lblBufferDeReordenao = new JLabel("Buffer de Reordena\u00E7\u00E3o");
		lblBufferDeReordenao.setBounds(228, 293, 117, 14);
		frame.getContentPane().add(lblBufferDeReordenao);
		
		lblEntrada = new JLabel("Entrada");
		lblEntrada.setBounds(42, 313, 46, 14);
		frame.getContentPane().add(lblEntrada);
		
		lblOcupado = new JLabel("Ocupado");
		lblOcupado.setBounds(113, 313, 46, 14);
		frame.getContentPane().add(lblOcupado);
		
		lblInstrucao = new JLabel("Instrucao");
		lblInstrucao.setBounds(229, 313, 46, 14);
		frame.getContentPane().add(lblInstrucao);
		
		lblEstado = new JLabel("Estado");
		lblEstado.setBounds(337, 313, 46, 14);
		frame.getContentPane().add(lblEstado);
		
		lblDestino = new JLabel("Destino");
		lblDestino.setBounds(413, 313, 46, 14);
		frame.getContentPane().add(lblDestino);
		
		lblValor_1 = new JLabel("Valor");
		lblValor_1.setBounds(488, 313, 46, 14);
		frame.getContentPane().add(lblValor_1);
	}
	
	public void setProcessor(Processor p){
		this.p = p;
	}

	public void updateInfos() {
		for (int i = 0; i<p.getRegs().size();i++){
			setReg(i, p.getRegs().get(i).getValue());
			//Colocar Qi também.
		}
		ReorderingBuffer r = p.getReorder();
		for(int i = 0; i<r.getLines().size();i++){
			
		}
		List<ReserveStation> reserve =  p.getReserveStations();
		for(int i = 0;i<reserve.size();i++){
			String tipo = i<3 ? "add" : (i < 7 ? "mult" : "mem");
			tipo+=i<3 ? i+1 : (i < 7 ? i-3 : i%7+1);
			ReserveStation rs = reserve.get(i);
			Instrucao inst = rs.getInstrucao();
			String nome=null;
			if(inst!=null)
				nome=inst.getNome();
			populateReserveStation(i, tipo, ((Boolean) rs.isBusy()).toString(), nome,
					rs.getDest(), rs.getVj(), rs.getVk(), rs.getQj(), rs.getQk(), rs.getA());
		}
		Integer completed=r.getCompleted();
		Integer clocks = p.getClock();
		Integer pc = p.getIF().getPC();
		setPc(pc);
		setClockCorrente(clocks);
		setCpi(completed>0 ? completed/clocks : completed);
		updateReorder();
		if(endereco1.getText()!=null&&p.getDataMemory().getLastAddress()!=null){
			String lastAddress = endereco1.getText();
			if(lastAddress!=p.getDataMemory().getLastAddress().toString())
				useAddress(p.getDataMemory().getLastAddress().toString(),p.getDataMemory().getLastValue().toString());
		}
		else if(p.getDataMemory().getLastAddress()!=null)
			useAddress(p.getDataMemory().getLastAddress().toString(),p.getDataMemory().getLastValue().toString());
	}
}
