import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Collections;



public class MainFrame extends JFrame {
	private long _timeStart;
	private JPanel _contentPane;
	private ArrayList<Color> _colors;
	private ArrayList<Color> _colors2;
	private JOptionPane _optionPane;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


	public MainFrame() {
		this.setVisible(true);
		setTitle("Tiles");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 652, 597);
		_contentPane = new JPanel();
		_contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(_contentPane);
		_contentPane.setLayout(null);
		_colors= new ArrayList<Color>(24);
		_colors2= new ArrayList<Color>(24);
		for(int i=0;i<24;i++){
			if(i/4==0){ _colors.add(Color.BLUE); _colors2.add(Color.BLUE);}
			if(i/4==1){ _colors.add(Color.RED); _colors2.add(Color.RED);}
			if(i/4==2){ _colors.add(Color.GREEN); _colors2.add(Color.GREEN);}
			if(i/4==3){ _colors.add(Color.MAGENTA); _colors2.add(Color.MAGENTA);}
			if(i/4==4){ _colors.add(Color.WHITE);_colors2.add(Color.WHITE);}
			if(i/4==5){ _colors.add(Color.YELLOW); _colors2.add(Color.YELLOW);}

		}

		Collections.shuffle(_colors);
		Collections.shuffle(_colors2);


		final JButton[] displayButtons = new JButton[25];
		for(int i=0;i<displayButtons.length;i++){
			displayButtons[i]=new JButton("");
			displayButtons[i].setName(""+i);
			displayButtons[i].setBounds(5+58*(i%5),5+63*(i/5),58,63);

			_contentPane.add(displayButtons[i]);

		}
		final JButton[]	buttons = new JButton[25];
		for(int i=0; i<buttons.length;i++){
			buttons[i]=new JButton("");
			buttons[i].setName(""+i);
			buttons[i].setBounds(329+58*(i%5),5+63*(i/5),58,63);

			buttons[i].addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent arg0){
					/////////LOGIC
					JButton caller= (JButton)arg0.getSource();
					int touch = adjacentEmpty(caller.getName());
					if(touch!=-1){
						buttons[touch].setBackground(caller.getBackground());
						caller.setBackground(Color.DARK_GRAY);
						if(checkWin()){

							dispose();
							int input = JOptionPane.showOptionDialog(null, "You completed the puzzle in : "+(System.currentTimeMillis()-_timeStart)/1000 + " seconds.", "Winner!", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);
							if(input == JOptionPane.OK_OPTION){
								new MainFrame();
							}
							else{
								System.out.println("quit");
								System.exit(0);
							}
						}
					}

					//////////END LOGIC
				}

				private boolean checkWin() {
					for(int i=6; i<19; i++){
						if(i%5 !=0 && i%5!=4){
							if(!displayButtons[i].getBackground().equals(buttons[i].getBackground()))return false;
						}
					}
					return true;
				}

				private int adjacentEmpty(String location) {
					int loc = Integer.parseInt(location);
					if(loc+1<=24 && buttons[loc+1].getBackground()==Color.DARK_GRAY) return loc+1;
					if(loc+5<=24 && buttons[loc+5].getBackground()==Color.DARK_GRAY) return loc+5;
					if(loc-1>=0 && buttons[loc-1].getBackground()==Color.DARK_GRAY) return loc-1;
					if(loc-5>=0 && buttons[loc-5].getBackground()==Color.DARK_GRAY) return loc-5;
					return -1;
				}
			});
			_contentPane.add(buttons[i]);
		}

		final JButton BtnGo = new JButton("Go");
		BtnGo.setBounds(296, 442, 91, 23);
		_contentPane.add(BtnGo);
		BtnGo.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent arg0){
				_timeStart=System.currentTimeMillis();
				int count=0;  //count will not exceed 23
				BtnGo.setEnabled(false);
				for(int i=0;i<25;i++){
					displayButtons[i].setEnabled(false);
					if(i==6||i==7||i==8||i==11||i==12||i==13||i==16||i==17||i==18){ //non border buttons
						displayButtons[i].setBackground(_colors2.get(count));
					}
					if(i!=12){
						buttons[i].setBackground(_colors.get(count));
						count++;
					}
					else{
						buttons[i].setBackground(Color.DARK_GRAY);
					}
				}
			}
		});



	}
}