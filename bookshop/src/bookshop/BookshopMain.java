package bookshop;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.table.DefaultTableModel;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import net.proteanit.sql.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


@SuppressWarnings("serial")
public class BookshopMain extends JFrame {

	// GUI panel
	private JPanel contentPane;
	//Table to view all books in the library
	private JTable viewbooks_tbl;
	//Search field to live search for books in the library
	private JTextField searchbox;
	//Table to add books to the library
	private JTextField addisbn;
	private JTextField addtitle;
	private JTextField addauthname;
	private JTextField addprice;
	private JTextField addquantity;
	//Table to remove books from the library
	private JTable removebook_tbl;
	private JTextField updatesearchbox;
	//Table to update the book details
	private JTable updatebooktbl;
	private JTextField updateisbn;
	private JTextField updatetitle;
	private JTextField updateauthname;
	private JTextField updateprice;
	private JTextField updatequantity;
	//Table to search books in the library
	private JTable searchtbl;
	private JTextField search;
	//Labels and Buttons
	private JLabel contentPnlLbl, viewPnlLbl, addPnlLbl, addPnlLbl_1, addPnlLbl_2, 
	addPnlLbl_3, addPnlLbl_4, addPnlLbl_5, remPnlLbl, liveSearchbox, liveSearchbox_1, 
	liveSearchbox_2, remisbnlbl, remtitlelbl,remauthnamelbl, isbnlbl_rem, titlelbl_rem, 
	authnamelbl_rem, updtPnlLbl, isbnlbl_updt, titlelbl_updt, authnamelbl_updt, 
	pricelbl_updt, quantitylbl_updt, searchPnlLbl, searchisbnlbl, searchtitlelbl, 
	searchauthnamelbl, searchpricelbl, searchquantitylbl, isbnlbl_search, titlelbl_search, 
	authnamelbl_search, pricelbl_search, quantitylbl_search;
	
	private JButton addbook_btn, deletebookbtn, updatebookdata, viewBooks_btn, 
	addNewBook_btn, remBook_btn, updtBook_btn, searchBook_btn, changeLang;
	
	//Variables necessary for database connection
	Connection conn=null;
	public String url;
	public String user;
	public String password;
	PreparedStatement pst=null;
	ResultSet rs=null;
	//To change language
	private ResourceBundle bun;
	
	/*
	 * ======================================Queries======================================
	 */

	//Method to load properties file containing the database sign-in details
	private void loadProps() throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(".\\src\\bookDBconnection.properties"));
		Properties bookDBconnection = new Properties();
		bookDBconnection.load(reader);
		this.url = bookDBconnection.getProperty("url");
		this.user = bookDBconnection.getProperty("user");
		this.password = bookDBconnection.getProperty("password");
	}
		
	//Method to change language
	private void changeLocale() {
		Locale locale = Locale.getDefault();

	    if (changeLang.getText().contains("Change")) {
	        locale = new Locale ("HU");
	    } else{
	        locale = Locale.getDefault();
	    }
	        
	    bun = ResourceBundle.getBundle("langBundle", locale);
	    //Labels
	    contentPnlLbl.setText(bun.getString("contentPnlLabel"));
	    viewPnlLbl.setText(bun.getString("viewPnlLabel"));
	    addPnlLbl.setText(bun.getString("addPnlLabel"));
	    addPnlLbl_1.setText(bun.getString("isbn"));
	    addPnlLbl_2.setText(bun.getString("title"));
	    addPnlLbl_3.setText(bun.getString("author"));
	    addPnlLbl_4.setText(bun.getString("price"));
	    addPnlLbl_5.setText(bun.getString("quantity"));
	    remPnlLbl.setText(bun.getString("remPnlLbl"));
	    liveSearchbox.setText(bun.getString("liveSearchbox"));
	    liveSearchbox_1.setText(bun.getString("liveSearchbox"));
	    liveSearchbox_2.setText(bun.getString("liveSearchbox"));
	    remisbnlbl.setText(bun.getString("na"));
	    remtitlelbl.setText(bun.getString("na"));
	    remauthnamelbl.setText(bun.getString("na"));
	    isbnlbl_rem.setText(bun.getString("isbn"));
	    titlelbl_rem.setText(bun.getString("title"));
	    authnamelbl_rem.setText(bun.getString("author"));
	    updtPnlLbl.setText(bun.getString("updtPnlLbl"));
	    isbnlbl_updt.setText(bun.getString("isbn"));
	    titlelbl_updt.setText(bun.getString("title"));
	    authnamelbl_updt.setText(bun.getString("author"));
	    pricelbl_updt.setText(bun.getString("price"));
	    quantitylbl_updt.setText(bun.getString("quantity"));
	    searchPnlLbl.setText(bun.getString("searchPnlLbl"));
	    searchisbnlbl.setText(bun.getString("na"));
	    searchtitlelbl.setText(bun.getString("na"));
	    searchauthnamelbl.setText(bun.getString("na"));
	    searchpricelbl.setText(bun.getString("na"));
	    searchquantitylbl.setText(bun.getString("na"));
	    isbnlbl_search.setText(bun.getString("isbn"));
	    titlelbl_search.setText(bun.getString("title"));
	    authnamelbl_search.setText(bun.getString("author"));
	    pricelbl_search.setText(bun.getString("price"));
	    quantitylbl_search.setText(bun.getString("price"));
	    //Buttons
	    addbook_btn.setText(bun.getString("addBtn"));
	    deletebookbtn.setText(bun.getString("deleteBtn"));
	    updatebookdata.setText(bun.getString("updateBtn"));
	    viewBooks_btn.setText(bun.getString("viewBtn"));
	    addNewBook_btn.setText(bun.getString("addBtn"));
	    remBook_btn.setText(bun.getString("remBtn"));
	    updtBook_btn.setText(bun.getString("updateBtn"));
	    searchBook_btn.setText(bun.getString("searchBtn"));
	        
	    this.changeLang.setText(bun.getString("change"));
	        
	}
		
	//For updating a book 
	public void updateBook(Book b) { 

		try {
			loadProps();
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection	conn=(Connection)DriverManager.getConnection(url,user,password);
			String sql=" Update bookshop.book  set  title=?, firstAuthor=? , price=?, quantityInStock=? where  ISBN=?;";
			
			pst=conn.prepareStatement(sql);
				
			pst.setString(1, b.getTitle());
			pst.setString(2, b.getFirstAuthor());
			pst.setDouble(3, b.getPrice());
			pst.setInt(4, b.getQuantity());
			pst.setString(5, b.getIsbn());
				
		    pst.executeUpdate();

	    } catch (ClassNotFoundException | SQLException | IOException e) {e.printStackTrace();}

	}
		
	//For deleting a book
	public void deleteBook(String isbn) { 

		try {
			loadProps();
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection	conn=(Connection)DriverManager.getConnection(url,user,password);
			String sql=" Delete  from bookshop.book where ISBN= '"+isbn+"';"; 
			pst=conn.prepareStatement(sql);

		    pst.executeUpdate();
				
		    JOptionPane.showMessageDialog(null, "Book Deleted");
		    	
	    } catch (ClassNotFoundException | SQLException | IOException e) {e.printStackTrace();}

	}
		
	//For adding a new book
	public void addBook(Book b) {  

		try {
			loadProps();
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection	conn=(Connection)DriverManager.getConnection(url,user,password);
			String sql=" insert into bookshop.book  values (?,?,?,?,?);";
			pst=conn.prepareStatement(sql);
				
			pst.setString(1, b.getIsbn());
				
			pst.setString(2, b.getTitle());
			pst.setString(3, b.getFirstAuthor());
			pst.setDouble(4, b.getPrice());
			pst.setInt(5, b.getQuantity());

		    pst.executeUpdate();

	    } catch (ClassNotFoundException | SQLException | IOException e) {e.printStackTrace();}

	}
		
	//Adding books in list from database table
	public ArrayList<Book> booklist() { 
		
		ArrayList<Book> booklist=new ArrayList<>();
		//Return book list;
		try {
			loadProps();
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection	conn=(Connection)DriverManager.getConnection(url,user,password);
			String sql="Select * from bookshop.book;";
			pst=conn.prepareStatement(sql);
			rs=pst.executeQuery();
				
			Book b;
				
			while(rs.next()) {
				b=new Book(rs.getString(1),rs.getString(2),rs.getString(3),rs.getDouble(4),rs.getInt(5)); 
			    booklist.add(b);
			}
		
	    } catch (ClassNotFoundException | SQLException | IOException e) {e.printStackTrace();}
			
		return booklist;
	}
		
	//For showing data in table
	public void showBook(JTable j) { 
			
		ArrayList<Book> list=booklist();
		DefaultTableModel tm=(DefaultTableModel)j.getModel();
		tm.setRowCount(0);
			
		Object[] row=new Object[5];
			
		for(int i=0;i<list.size();i++) {
				
			row[0]=list.get(i).getIsbn();
			row[1]=list.get(i).getTitle();
			row[2]=list.get(i).getFirstAuthor();
			row[3]=list.get(i).getPrice();
			row[4]=list.get(i).getQuantity();
			tm.addRow(row);
				
		}
	}

	/*
	 * ======================================Constructor======================================
	 */
	
	//Creating the GUI
	public BookshopMain() {
		//Main frame
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100,1200, 600);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//Content pane label
		contentPnlLbl = new JLabel("Books Management");
		contentPnlLbl.setFont(new Font("Tahoma", Font.BOLD, 28));
		contentPnlLbl.setForeground(Color.DARK_GRAY);
		contentPnlLbl.setHorizontalAlignment(SwingConstants.CENTER);
		contentPnlLbl.setBounds(110, 34, 1064, 30);
		contentPane.add(contentPnlLbl);
		
		
		//---------------------------View books panel---------------------------
		JPanel viewbook_pnl = new JPanel();
		viewbook_pnl.setBorder(null);
		viewbook_pnl.setBackground(new Color(255, 255, 255));
		viewbook_pnl.setBounds(136, 75, 1038, 475);
		contentPane.add(viewbook_pnl);
		viewbook_pnl.setLayout(null);
		
		viewbook_pnl.setVisible(false);
		
		viewPnlLbl = new JLabel("All Available Books");
		viewPnlLbl.setHorizontalAlignment(SwingConstants.CENTER);
		viewPnlLbl.setFont(new Font("Tahoma", Font.PLAIN, 18));
		viewPnlLbl.setBounds(10, 11, 1003, 22);
		viewbook_pnl.add(viewPnlLbl);
		
		//Scroll pane
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 93, 1003, 239);
		viewbook_pnl.add(scrollPane);
		scrollPane.setViewportBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		
		viewbooks_tbl = new JTable();
		viewbooks_tbl.setBackground(new Color(255, 255, 255));
		scrollPane.setViewportView(viewbooks_tbl);
				
		viewbooks_tbl.setRowHeight(30);
		viewbooks_tbl.setAlignmentX(CENTER_ALIGNMENT);
		viewbooks_tbl.setAlignmentY(CENTER_ALIGNMENT);
					
		viewbooks_tbl.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"ISBN", "title", "firstAuthor", "price", "quantityInStock"
			}
		));	
				
		
		//---------------------------Add a new book panel---------------------------
		JPanel addnewbook_pnl = new JPanel();
		addnewbook_pnl.setBackground(Color.WHITE);
		addnewbook_pnl.setBounds(147, 86, 1003, 448);
		contentPane.add(addnewbook_pnl);
		addnewbook_pnl.setLayout(null);
				
		addnewbook_pnl.setVisible(false);

		addPnlLbl = new JLabel("Add New Book");
		addPnlLbl.setFont(new Font("Tahoma", Font.PLAIN, 18));
		addPnlLbl.setHorizontalAlignment(SwingConstants.CENTER);
		addPnlLbl.setBounds(0, 11, 1038, 22);
		addnewbook_pnl.add(addPnlLbl);
				
		addPnlLbl_1 = new JLabel("ISBN");
		addPnlLbl_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		addPnlLbl_1.setBounds(113, 69, 227, 14);
		addnewbook_pnl.add(addPnlLbl_1);
				
		addPnlLbl_2 = new JLabel("Title");
		addPnlLbl_2.setFont(new Font("Tahoma", Font.PLAIN, 16));
		addPnlLbl_2.setBounds(113, 94, 227, 14);
		addnewbook_pnl.add(addPnlLbl_2);
				
		addPnlLbl_3 = new JLabel("Author Name");
		addPnlLbl_3.setFont(new Font("Tahoma", Font.PLAIN, 16));
		addPnlLbl_3.setBounds(113, 119, 227, 14);
		addnewbook_pnl.add(addPnlLbl_3);
				
		addPnlLbl_4 = new JLabel("Price");
		addPnlLbl_4.setFont(new Font("Tahoma", Font.PLAIN, 16));
		addPnlLbl_4.setBounds(113, 144, 227, 14);
		addnewbook_pnl.add(addPnlLbl_4);
				
		addPnlLbl_5 = new JLabel("Quantity");
		addPnlLbl_5.setFont(new Font("Tahoma", Font.PLAIN, 16));
		addPnlLbl_5.setBounds(113, 169, 227, 14);
		addnewbook_pnl.add(addPnlLbl_5);
				
		addisbn = new JTextField();
		addisbn.setBounds(370, 69, 469, 20);
		addnewbook_pnl.add(addisbn);
		addisbn.setColumns(10);
				
		addtitle = new JTextField();
		addtitle.setColumns(10);
		addtitle.setBounds(370, 93, 469, 20);
		addnewbook_pnl.add(addtitle);
				
		addauthname = new JTextField();
		addauthname.setColumns(10);
		addauthname.setBounds(370, 118, 469, 20);
		addnewbook_pnl.add(addauthname);
				
		addprice = new JTextField();
		//Add key listener so it will not take any input but digits and period for decimals
		addprice.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
			
			}
			@Override
			public void keyPressed(KeyEvent ke) {
				char c=ke.getKeyChar();
					if ((Character.isDigit(c) || c == KeyEvent.VK_BACK_SPACE || c == KeyEvent.VK_DELETE || c==KeyEvent.VK_PERIOD) ) {
						addprice.setEditable(true);
					} else {
						JOptionPane.showMessageDialog(null, "Invalid Entry. Please enter a integer or a double type number.");
						addprice.setText("");    
					}
						
			}
		});
				
		addprice.setColumns(10);
		addprice.setBounds(370, 143, 469, 20);
		addnewbook_pnl.add(addprice);
				
		addquantity = new JTextField();
		//Add key listener so it will not take any input but digits
		addquantity.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent ke) {
				char c=ke.getKeyChar();
					if ((Character.isDigit(c) || c == KeyEvent.VK_BACK_SPACE || c == KeyEvent.VK_DELETE ) ) {
						addquantity.setEditable(true);

					} else {
						JOptionPane.showMessageDialog(null, "Invalid Entry. Please enter an integer number.");
						addquantity.setText("");   
					}
			}
		});
				
		addquantity.setColumns(10);
		addquantity.setBounds(370, 168, 469, 20);
		addnewbook_pnl.add(addquantity);
				
		addbook_btn = new JButton("Add Book");
		addbook_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String ISBN = addisbn.getText();
				String ISBNRegex="[0-9]{3}\\-[0-9]{1}\\-[0-9]{2}\\-[0-9]{6}\\-[0-9]{1}";
				if (! Pattern.matches(ISBNRegex, ISBN)) {
					JOptionPane.showMessageDialog(null, "Invalid ISBN number. Use this format XXX-X-XX-XXXXXX-X, where X is a digit");
					System.out.println("ISBN:"+ISBN+":End");
					return ;
				}

				Book b=new Book();
				b.setIsbn(addisbn.getText().toString());
				b.setTitle(addtitle.getText().toString());
		        b.setFirstAuthor(addauthname.getText().toString());
		        b.setPrice(Double.parseDouble(addprice.getText().toString()));
		        b.setQuantity(Integer.parseInt(addquantity.getText().toString()));

		        addBook(b);
		        JOptionPane.showMessageDialog(null, "New Book Added!");
		        addisbn.setText(" ");
		        addtitle.setText(" ");
		        addauthname.setText(" ");
		        addprice.setText(" ");
		        addquantity.setText(" ");
		             
			}
		});
				
		addbook_btn.setBackground(Color.WHITE);
		addbook_btn.setFont(new Font("Tahoma", Font.BOLD, 11));
		addbook_btn.setForeground(new Color(95, 158, 160));
		addbook_btn.setBounds(370, 199, 194, 32);
		addnewbook_pnl.add(addbook_btn);
		
		
		//---------------------------Remove a book panel---------------------------
		JPanel removebook_pnl = new JPanel();
		removebook_pnl.setBackground(new Color(255, 255, 255));
		removebook_pnl.setBounds(136, 86, 1014, 448);
		contentPane.add(removebook_pnl);
		removebook_pnl.setLayout(null);
		
		removebook_pnl.setVisible(false);
				
		remPnlLbl = new JLabel("Delete a Book");
		remPnlLbl.setBounds(10, 11, 1003, 22);
		remPnlLbl.setHorizontalAlignment(SwingConstants.CENTER);
		remPnlLbl.setFont(new Font("Tahoma", Font.PLAIN, 18));
		removebook_pnl.add(remPnlLbl);
		
		//Label and text field to live search books in the database	by ISBN	
		liveSearchbox = new JLabel ("Search books by ISBN");
		liveSearchbox.setForeground(new Color(95, 158, 160));
		liveSearchbox.setBackground(Color.WHITE);
		liveSearchbox.setBounds(398, 46, 138, 23);
		removebook_pnl.add(liveSearchbox);
		
		searchbox = new JTextField();
		searchbox.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				try {
					loadProps();
					Class.forName("com.mysql.cj.jdbc.Driver");		
					Connection	conn=(Connection)DriverManager.getConnection(url,user,password);
					String sql="select * from bookshop.book where ISBN LIKE ?";
					pst=conn.prepareStatement(sql);
					pst.setString(1, searchbox.getText()+"%");//Remainder operator to display the rest of the ISBN of all possibilities still remaining
					rs=pst.executeQuery();
					removebook_tbl.setModel(DbUtils.resultSetToTableModel(rs));

					pst.close();
							
				    } catch (ClassNotFoundException | SQLException | IOException e1) {e1.printStackTrace();}
						
			}
		});
				
		searchbox.setBounds(548, 44, 381, 27);
		removebook_pnl.add(searchbox);
		searchbox.setColumns(10);
		
		/*
		 * Labels situated at the bottom left of the panel 
		 * but set and declared before the scroll pane, 
		 * because of the mouse events from the scroll pane to be able to update these labels
		 */
		remisbnlbl = new JLabel("n/a");
		remisbnlbl.setBounds(109, 343, 407, 14);
		removebook_pnl.add(remisbnlbl);
				
		remtitlelbl = new JLabel("n/a");
		remtitlelbl.setBounds(109, 365, 407, 14);
		removebook_pnl.add(remtitlelbl);
				
		remauthnamelbl = new JLabel("n/a");
		remauthnamelbl.setBackground(new Color(255, 255, 255));
		remauthnamelbl.setBounds(109, 390, 407, 14);
		removebook_pnl.add(remauthnamelbl);
		
		//Scroll pane
		JScrollPane scrollPane_rem = new JScrollPane();
		scrollPane_rem.setBounds(10, 93, 1003, 239);
		removebook_pnl.add(scrollPane_rem);
		
		//Clicking a row in the database table, will select it and display it at the bottom left corner of the pane		
		removebook_tbl = new JTable();
		removebook_tbl.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				//To detect double click events
				JTable target = (JTable)e.getSource();
				//Select a row
				int row = target.getSelectedRow(); 
				          
				remisbnlbl.setText((String) removebook_tbl.getValueAt(row, 0));
				remtitlelbl.setText((String) removebook_tbl.getValueAt(row, 1));
				remauthnamelbl.setText((String) removebook_tbl.getValueAt(row, 2));

			}
		});
		
		//Database table inside the scroll pane		
		removebook_tbl.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"ISBN", "title", "firstAuthor", "price", "quantityInStock"
			}
		));
				
		scrollPane_rem.setViewportView(removebook_tbl);
		
		isbnlbl_rem = new JLabel("ISBN");
		isbnlbl_rem.setBounds(10, 343, 46, 14);
		removebook_pnl.add(isbnlbl_rem);
				
		titlelbl_rem = new JLabel("Title");
		titlelbl_rem.setBounds(10, 365, 46, 14);
		removebook_pnl.add(titlelbl_rem);
				
		authnamelbl_rem = new JLabel("Author Name");
		authnamelbl_rem.setBounds(10, 390, 81, 14);
		removebook_pnl.add(authnamelbl_rem);
				
		deletebookbtn = new JButton("DELETE");
		deletebookbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					
				deleteBook(remisbnlbl.getText().toString());
				showBook(removebook_tbl);
					
				remisbnlbl.setText("n/a");
				remauthnamelbl.setText("n/a");
				remtitlelbl.setText("n/a");
					
						
			}
		});
				
		deletebookbtn.setBackground(new Color(255, 255, 255));
		deletebookbtn.setFont(new Font("Tahoma", Font.BOLD, 16));
		deletebookbtn.setForeground(new Color(95, 158, 160));
		deletebookbtn.setBounds(783, 343, 220, 59);
		removebook_pnl.add(deletebookbtn);


		//---------------------------Update panel---------------------------
		JPanel updatebook_pnl = new JPanel();
		updatebook_pnl.setBackground(new Color(255, 255, 255));
		updatebook_pnl.setBounds(136, 86, 1014, 448);
		contentPane.add(updatebook_pnl);
		updatebook_pnl.setLayout(null);
		
		updatebook_pnl.setVisible(false);
		
		updtPnlLbl = new JLabel("Update a Book");
		updtPnlLbl.setHorizontalAlignment(SwingConstants.CENTER);
		updtPnlLbl.setFont(new Font("Tahoma", Font.PLAIN, 18));
		updtPnlLbl.setBounds(10, 11, 1003, 22);
		updatebook_pnl.add(updtPnlLbl);
		
		//Label and text field to live search books in the database	by ISBN
		liveSearchbox_1 = new JLabel ("Search books by ISBN");
		liveSearchbox_1.setForeground(new Color(95, 158, 160));
		liveSearchbox_1.setBackground(Color.WHITE);
		liveSearchbox_1.setBounds(398, 46, 138, 23);
		updatebook_pnl.add(liveSearchbox_1);
		
		updatesearchbox = new JTextField();
		updatesearchbox.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				try {
					loadProps();
					Class.forName("com.mysql.cj.jdbc.Driver");
				    Connection	conn=(Connection)DriverManager.getConnection(url,user,password);
				    String sql="select * from bookshop.book where ISBN LIKE ?";
					pst=conn.prepareStatement(sql);
					pst.setString(1,  updatesearchbox.getText()+"%");
					rs=pst.executeQuery();
					updatebooktbl.setModel(DbUtils.resultSetToTableModel(rs));
	
			    	pst.close();
					
		        } catch (ClassNotFoundException | SQLException | IOException e1) {e1.printStackTrace();}
			}
		});
		
		updatesearchbox.setColumns(10);
		updatesearchbox.setBounds(558, 44, 381, 27);
		updatebook_pnl.add(updatesearchbox);
		
		/*
		 * Text fields at the bottom left of the panel used to update book details, 
		 * but set and declared before the scroll pane, 
		 * because of the mouse event from the scroll pane to be able to update the database
		 */
		updateisbn = new JTextField();
		updateisbn.setEditable(false);
		updateisbn.setBounds(113, 289, 356, 20);
		updatebook_pnl.add(updateisbn);
		updateisbn.setColumns(10);
		
		updatetitle = new JTextField();
		updatetitle.setColumns(10);
		updatetitle.setBounds(113, 313, 356, 20);
		updatebook_pnl.add(updatetitle);
		
		updateauthname = new JTextField();
		updateauthname.setColumns(10);
		updateauthname.setBounds(111, 339, 356, 20);
		updatebook_pnl.add(updateauthname);
		
		updateprice = new JTextField();
		//Add key listener so it will not take any input but digits and period for decimals
		updateprice.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent ke) {
				char c=ke.getKeyChar();
				 if ((Character.isDigit(c) || c == KeyEvent.VK_BACK_SPACE || c == KeyEvent.VK_DELETE || c==KeyEvent.VK_PERIOD) ) {
					 updateprice.setEditable(true);
				 } else {
					 JOptionPane.showMessageDialog(null, "Invalid Entry. Please enter a integer or a double type number.");
					 updateprice.setText(""); 
				 }
			}
		});
		
		updateprice.setColumns(10);
		updateprice.setBounds(113, 364, 356, 20);
		updatebook_pnl.add(updateprice);
		
		updatequantity = new JTextField();
		//Add key listener so it will not take any input but digits
		updatequantity.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent ke) {
				char c=ke.getKeyChar();
				 if ((Character.isDigit(c) || c == KeyEvent.VK_BACK_SPACE || c == KeyEvent.VK_DELETE ) ) {
					 updatequantity.setEditable(true);
				 } else {
					 JOptionPane.showMessageDialog(null, "Invalid Entry. Please enter a integer number.");
					 updatequantity.setText("");     
				 }
			}
		});
		
		updatequantity.setColumns(10);
		updatequantity.setBounds(113, 389, 356, 22);
		updatebook_pnl.add(updatequantity);
		
		//Scroll pane
		JScrollPane scrollPane_updt = new JScrollPane();
		scrollPane_updt.setBounds(10, 93, 1003, 185);
		updatebook_pnl.add(scrollPane_updt);
		
		updatebooktbl = new JTable();
		updatebooktbl.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				//To detect double click events
	            JTable target = (JTable)e.getSource();
	            //Select a row
	            int row = target.getSelectedRow(); 
	          
	            updateisbn.setText((String) updatebooktbl.getValueAt(row, 0));
	            updatetitle.setText((String) updatebooktbl.getValueAt(row, 1));
	            updateauthname.setText((String) updatebooktbl.getValueAt(row, 2));
	           
	            updateprice.setText( (String) updatebooktbl.getValueAt(row, 3).toString());
	            updatequantity.setText( (String) updatebooktbl.getValueAt(row, 4).toString());
	             
			}
		});
		
		//Database table inside the scroll pane
		updatebooktbl.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"ISBN", "title", "firstAuthor", "price", "quantityInStock"
			}
		));
		
		scrollPane_updt.setViewportView(updatebooktbl);
		
		//Labels at the bottom of the scroll pane on the Update panel
		isbnlbl_updt = new JLabel("ISBN");
		isbnlbl_updt.setBounds(20, 292, 46, 14);
		updatebook_pnl.add(isbnlbl_updt);
		
		titlelbl_updt = new JLabel("Title");
		titlelbl_updt.setBounds(20, 314, 46, 14);
		updatebook_pnl.add(titlelbl_updt);
		
		authnamelbl_updt = new JLabel("Author Name");
		authnamelbl_updt.setBounds(20, 339, 81, 14);
		updatebook_pnl.add(authnamelbl_updt);
		
		pricelbl_updt = new JLabel("Price");
		pricelbl_updt.setBounds(20, 364, 81, 14);
		updatebook_pnl.add(pricelbl_updt);
		
		quantitylbl_updt = new JLabel("Quantity");
		quantitylbl_updt.setBounds(20, 389, 81, 14);
		updatebook_pnl.add(quantitylbl_updt);
		
		//Update button at the bottom of the scroll pane in the Update panel
		updatebookdata = new JButton("Update");
		updatebookdata.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Book b=new Book();
				b.setIsbn(updateisbn.getText().toString());
				b.setTitle(updatetitle.getText().toString());
				b.setFirstAuthor(updateauthname.getText().toString());
				b.setPrice(Double.parseDouble(updateprice.getText().toString()));
				b.setQuantity(Integer.parseInt(updatequantity.getText().toString()));
				
				updateBook(b);

				JOptionPane.showMessageDialog(null, "Book # "+b.getIsbn()+" Updated!");
				
				showBook(updatebooktbl);
			}
		});
		
		updatebookdata.setForeground(new Color(95, 158, 160));
		updatebookdata.setFont(new Font("Tahoma", Font.BOLD, 16));
		updatebookdata.setBackground(Color.WHITE);
		updatebookdata.setBounds(793, 343, 220, 59);
		updatebook_pnl.add(updatebookdata);
		
		
		//---------------------------Search panel---------------------------
		JPanel searchanddisplay_pnl = new JPanel();
		searchanddisplay_pnl.setBackground(new Color(255, 255, 255));
		searchanddisplay_pnl.setBounds(136, 86, 1014, 448);
		contentPane.add(searchanddisplay_pnl);
		searchanddisplay_pnl.setLayout(null);
		
		searchanddisplay_pnl.setVisible(false);
		
		searchPnlLbl = new JLabel("Search for a Book");
		searchPnlLbl.setHorizontalAlignment(SwingConstants.CENTER);
		searchPnlLbl.setFont(new Font("Tahoma", Font.PLAIN, 18));
		searchPnlLbl.setBounds(10, 11, 1003, 22);
		searchanddisplay_pnl.add(searchPnlLbl);
		
		
		//Label and text field to live search books in the database	by ISBN
		liveSearchbox_2 = new JLabel ("Search books by ISBN");
		liveSearchbox_2.setForeground(new Color(95, 158, 160));
		liveSearchbox_2.setBackground(Color.WHITE);
		liveSearchbox_2.setBounds(398, 46, 138, 23);
		searchanddisplay_pnl.add(liveSearchbox_2);
		
		search = new JTextField();
		search.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				
				try {
					loadProps();
					Class.forName("com.mysql.cj.jdbc.Driver");
				    Connection	conn=(Connection)DriverManager.getConnection(url,user,password);
				    String sql="select * from bookshop.book where ISBN LIKE ?";
					pst=conn.prepareStatement(sql);
					pst.setString(1, search.getText()+"%");
					rs=pst.executeQuery();
					searchtbl.setModel(DbUtils.resultSetToTableModel(rs));

			    	pst.close();
					
		        } catch (ClassNotFoundException | SQLException | IOException e1) {e1.printStackTrace();}
				
			}
		});
		
		search.setColumns(10);
		search.setBounds(558, 44, 381, 27);
		searchanddisplay_pnl.add(search);
		
		/*
		 * Labels situated at the bottom of the panel 
		 * but set and declared before the scroll pane, 
		 * because of the mouse event from the scroll pane to be able to update these labels
		 */
		searchisbnlbl = new JLabel("n/a");
		searchisbnlbl.setBounds(305, 326, 407, 14);
		searchanddisplay_pnl.add(searchisbnlbl);
		
		searchtitlelbl = new JLabel("n/a");
		searchtitlelbl.setBounds(305, 348, 407, 14);
		searchanddisplay_pnl.add(searchtitlelbl);
		
		searchauthnamelbl = new JLabel("n/a");
		searchauthnamelbl.setBackground(Color.WHITE);
		searchauthnamelbl.setBounds(305, 373, 407, 14);
		searchanddisplay_pnl.add(searchauthnamelbl);
		
		searchpricelbl = new JLabel("n/a");
		searchpricelbl.setBackground(Color.WHITE);
		searchpricelbl.setBounds(305, 398, 407, 14);
		searchanddisplay_pnl.add(searchpricelbl);
		
		searchquantitylbl = new JLabel("n/a");
		searchquantitylbl.setBackground(Color.WHITE);
		searchquantitylbl.setBounds(305, 423, 407, 14);
		searchanddisplay_pnl.add(searchquantitylbl);
		
		//Scroll pane
		JScrollPane scrollPane_1_2 = new JScrollPane();
		scrollPane_1_2.setBounds(10, 93, 1003, 217);
		searchanddisplay_pnl.add(scrollPane_1_2);
		
		searchtbl = new JTable();
		searchtbl.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				//To detect double click events
	            JTable target = (JTable)e.getSource();
	            //Select a row
	            int row = target.getSelectedRow(); 

		        searchisbnlbl.setText((String) searchtbl.getValueAt(row, 0));
		        searchtitlelbl.setText((String) searchtbl.getValueAt(row, 1));
		        searchauthnamelbl.setText((String) searchtbl.getValueAt(row, 2));
	           
		        searchpricelbl.setText( (String) searchtbl.getValueAt(row, 3).toString());
		        searchquantitylbl.setText( (String) searchtbl.getValueAt(row, 4).toString());
    
			}
		});
		
		searchtbl.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"ISBN", "title", "firstAuthor", "price", "quantityInStock"
			}
		));
		
		scrollPane_1_2.setViewportView(searchtbl);
		
		isbnlbl_search = new JLabel("ISBN");
		isbnlbl_search.setBounds(206, 326, 46, 14);
		searchanddisplay_pnl.add(isbnlbl_search);
		
		titlelbl_search = new JLabel("Title");
		titlelbl_search.setBounds(206, 348, 46, 14);
		searchanddisplay_pnl.add(titlelbl_search);
		
		authnamelbl_search = new JLabel("Author Name");
		authnamelbl_search.setBounds(206, 373, 81, 14);
		searchanddisplay_pnl.add(authnamelbl_search);

		pricelbl_search = new JLabel("Price");
		pricelbl_search.setBounds(206, 398, 81, 14);
		searchanddisplay_pnl.add(pricelbl_search);
		
		quantitylbl_search = new JLabel("Quantity");
		quantitylbl_search.setBounds(206, 423, 81, 14);
		searchanddisplay_pnl.add(quantitylbl_search);
		

		//---------------------------Side panel---------------------------
		JPanel sidepnl = new JPanel();
		sidepnl.setBackground(new Color(95, 158, 160));
		sidepnl.setBounds(0, 0, 126, 561);
		contentPane.add(sidepnl);
		sidepnl.setLayout(null);
		
		//View button in the side panel
		viewBooks_btn = new JButton("View");
		//Using Lambda expression
		viewBooks_btn.addActionListener(e -> {
			searchanddisplay_pnl.setVisible(false);
			addnewbook_pnl.setVisible(false);
			removebook_pnl.setVisible(false);
			viewbook_pnl.setVisible(true);
			updatebook_pnl.setVisible(false);
			showBook(viewbooks_tbl);
		});
		/*
		 * Using action listener
		viewBooks_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
				searchanddisplay_pnl.setVisible(false);
				addnewbook_pnl.setVisible(false);
				removebook_pnl.setVisible(false);
				viewbook_pnl.setVisible(true);
				updatebook_pnl.setVisible(false);
				Showbook(viewbooks_tbl);
              }
		});
		*/
		
		viewBooks_btn.setFont(new Font("Tahoma", Font.BOLD, 16));
		viewBooks_btn.setForeground(new Color(95, 158, 160));
		viewBooks_btn.setBackground(new Color(255, 255, 255));
		viewBooks_btn.setBounds(10, 87, 129, 52);
		sidepnl.add(viewBooks_btn);
		
		//Add button in the side panel
		addNewBook_btn = new JButton("Add Book");
		//Using Lambda expression
		addNewBook_btn.addActionListener(e -> {
			viewbook_pnl.setVisible(false);			
			addnewbook_pnl.setVisible(true);
			removebook_pnl.setVisible(false);
			updatebook_pnl.setVisible(false);
			searchanddisplay_pnl.setVisible(false);
		});
		/*
		 * Using action listener
		addNewBook_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				viewbook_pnl.setVisible(false);			
				addnewbook_pnl.setVisible(true);
				removebook_pnl.setVisible(false);
				updatebook_pnl.setVisible(false);
				searchanddisplay_pnl.setVisible(false);
			}

			@SuppressWarnings("unused")
			private void Addbook() {
			}
		});
		*/
		
		addNewBook_btn.setForeground(new Color(95, 158, 160));
		addNewBook_btn.setFont(new Font("Tahoma", Font.BOLD, 16));
		addNewBook_btn.setBackground(new Color(255, 255, 255));
		addNewBook_btn.setBounds(10, 150, 129, 52);
		sidepnl.add(addNewBook_btn);
		
		//Remove button in the side panel
		remBook_btn = new JButton("Remove");
		//Using Lambda expression
		remBook_btn.addActionListener(e -> {
			removebook_pnl.setVisible(true);
			searchanddisplay_pnl.setVisible(false);
			addnewbook_pnl.setVisible(false);
			viewbook_pnl.setVisible(false);
			updatebook_pnl.setVisible(false);
			showBook(removebook_tbl);
		});
		/*
		 * Using action listener
		remBook_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removebook_pnl.setVisible(true);
				searchanddisplay_pnl.setVisible(false);
				addnewbook_pnl.setVisible(false);
				viewbook_pnl.setVisible(false);
				updatebook_pnl.setVisible(false);
				Showbook(removebook_tbl);
			}
		});
		*/
		
		remBook_btn.setForeground(new Color(95, 158, 160));
		remBook_btn.setFont(new Font("Tahoma", Font.BOLD, 16));
		remBook_btn.setBackground(new Color(255, 255, 255));
		remBook_btn.setBounds(10, 213, 129, 52);
		sidepnl.add(remBook_btn);
		
		//Update button in the side panel
		updtBook_btn = new JButton("Update");
		//Using Lambda expression
		updtBook_btn.addActionListener(e -> {
			showBook(updatebooktbl);
			searchanddisplay_pnl.setVisible(false);
			updatebook_pnl.setVisible(true);
			addnewbook_pnl.setVisible(false);
			removebook_pnl.setVisible(false);
			viewbook_pnl.setVisible(false);
		});
		/*
		 * Using action listener
		updtBook_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Showbook(updatebooktbl);
				searchanddisplay_pnl.setVisible(false);
				updatebook_pnl.setVisible(true);
				addnewbook_pnl.setVisible(false);
				removebook_pnl.setVisible(false);
				viewbook_pnl.setVisible(false);
			}
		});
		*/
		
		updtBook_btn.setForeground(new Color(95, 158, 160));
		updtBook_btn.setFont(new Font("Tahoma", Font.BOLD, 16));
		updtBook_btn.setBackground(new Color(255, 255, 255));
		updtBook_btn.setBounds(10, 276, 129, 52);
		sidepnl.add(updtBook_btn);
		
		//Search button in the side panel
		searchBook_btn = new JButton("Search");
		//Using Lambda expression
		searchBook_btn.addActionListener(e -> {
			searchanddisplay_pnl.setVisible(true);
			addnewbook_pnl.setVisible(false);
			removebook_pnl.setVisible(false);
			viewbook_pnl.setVisible(false);
			updatebook_pnl.setVisible(false);
		
			showBook(searchtbl);
		});
		/*
		 * Using action listener
		searchBook_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				searchanddisplay_pnl.setVisible(true);
				addnewbook_pnl.setVisible(false);
				removebook_pnl.setVisible(false);
				viewbook_pnl.setVisible(false);
				updatebook_pnl.setVisible(false);
			
				Showbook(searchtbl);
			}
		});
		*/
		
		searchBook_btn.setForeground(new Color(95, 158, 160));
		searchBook_btn.setFont(new Font("Tahoma", Font.BOLD, 16));
		searchBook_btn.setBackground(new Color(255, 255, 255));
		searchBook_btn.setBounds(10, 339, 129, 52);
		sidepnl.add(searchBook_btn);
		
		//Change language button
		changeLang = new JButton("Change Language");

		changeLang.setForeground(new Color(95, 158, 160));
		changeLang.setFont(new Font("Tahoma", Font.BOLD, 10));
		changeLang.setBackground(new Color(255, 255, 255));
		changeLang.setBounds(10, 432, 129, 52);
		sidepnl.add(changeLang);
		
		//Using Lambda expression to call in method changeLocale()
		changeLang.addActionListener((ev) -> changeLocale());
		
	}//End constructor

}//End BookshopMain class
