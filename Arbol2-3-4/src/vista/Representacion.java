package vista;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import maincode.Arbol234;
import nodos.Nodo;

/**
 *
 * @author Victor Cavero
 */
public class Representacion extends javax.swing.JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Arbol234 _arbol;
    private java.awt.Button buscar;
    private java.awt.Button insertar;
    private java.awt.Button borrar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTree jTree1;
    private java.awt.TextField textField1;
	
    
    public Representacion(Arbol234 arbol) 
    {
        _arbol = arbol;
        initComponents(); 
        representa_arbol();
    }
 
     private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        textField1 = new java.awt.TextField();
        jPanel3 = new javax.swing.JPanel();
        insertar = new java.awt.Button();
        buscar = new java.awt.Button();
        borrar = new java.awt.Button();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
  
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Representación de un árbol 2-3-4");
        setResizable(false);


        insertar.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        insertar.setLabel("INSERTAR");
        insertar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                insertarActionPerformed(evt);
            }
        });

        buscar.setActionCommand("INSERTAR");
        buscar.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        buscar.setLabel("BUSCAR");
        buscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscarActionPerformed(evt);
            }
        });

        borrar.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        borrar.setLabel("BORRAR");
        borrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                borrarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(borrar, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)
                    .addComponent(insertar, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
                .addComponent(buscar, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(buscar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(insertar, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(borrar, javax.swing.GroupLayout.DEFAULT_SIZE, 45, Short.MAX_VALUE))
        );

        buscar.getAccessibleContext().setAccessibleName("INSERTAR");

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18));
        jLabel1.setText("Valor:");
        jLabel1.setToolTipText("");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(62, 62, 62)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(textField1, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 39, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(63, 63, 63))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(textField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(51, 51, 51))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Arbol234");

        DefaultTreeModel model = new DefaultTreeModel(root);
        jTree1=new JTree(model);
        
        jScrollPane1.setViewportView(jTree1);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 296, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }                                                              

    private void insertarActionPerformed(java.awt.event.ActionEvent evt) { 
    	try
    	{
    		_arbol.insertar(Integer.parseInt(textField1.getText()));
    	}
    	catch (NumberFormatException ex)
    	{
    		JFrame jf = new JFrame();
    		
    		JOptionPane.showMessageDialog(jf,
    			    "Inserte un numero.", "Error",
    			    JOptionPane.ERROR_MESSAGE);
    	}
        
        representa_arbol();
    }                                       

    private void buscarActionPerformed(java.awt.event.ActionEvent evt) {  
    	
    	JFrame jf = new JFrame();
    	
    	try
    	{
    		if(_arbol.buscar(Integer.parseInt(textField1.getText())) != null)
        	{
        		JOptionPane.showMessageDialog(jf,
        			    "¡ Hemos encontrado el valor ! ");
        	}
        	else 
        	{
        		JOptionPane.showMessageDialog(jf,
        			    "No he encontrado el valor.", "Error",
        			    JOptionPane.ERROR_MESSAGE);
        	}
    	}
    	catch (NumberFormatException ex)
    	{
    		
    		JOptionPane.showMessageDialog(jf,
    			    "Inserte un numero.", "Error",
    			    JOptionPane.ERROR_MESSAGE);
    	}
    	
    	
    }                                       

    private void borrarActionPerformed(java.awt.event.ActionEvent evt) {        
    	try
    	{
    		_arbol.borrar(Integer.parseInt(textField1.getText()));
        	representa_arbol();
    	}
    	catch(NumberFormatException ex)
    	{
    		JFrame jf = new JFrame();
    		
    		JOptionPane.showMessageDialog(jf,
    			    "Inserte un numero.", "Error",
    			    JOptionPane.ERROR_MESSAGE);
    	}
    } 
    
    
    private void arbol_recursivo(Nodo arbol, DefaultMutableTreeNode root)
    {
    	if(arbol != null)
    	{
        	Nodo hi = arbol.get_hi();
        	Nodo hd = arbol.get_hd();
        	Nodo ci = arbol.get_ci();
        	Nodo cd = arbol.get_cd();
        	
        	if(hi != null) 
        	{
        		DefaultMutableTreeNode dn = new DefaultMutableTreeNode("Hijo Iz: " + arbol.get_hi().toString());
    			root.add(dn);
    			arbol_recursivo(hi, dn);
        	}
        	if(hd != null) 
        	{    			
        		DefaultMutableTreeNode dn = new DefaultMutableTreeNode("Hijo Drch: " + arbol.get_hd().toString());
        		root.add(dn);
        		arbol_recursivo(hd, dn);
        	}
        	if(ci != null) 
        	{
        		DefaultMutableTreeNode dn = new DefaultMutableTreeNode("Centro Iz: " + arbol.get_ci().toString());
    			root.add(dn);
    			arbol_recursivo(ci, dn);
        	}
        	if(cd != null) 
        	{    			
        		DefaultMutableTreeNode dn = new DefaultMutableTreeNode("Centro Drch: " + arbol.get_cd().toString());
        		root.add(dn);
        		arbol_recursivo(cd, dn);
        	}
        }
    	
    }
    
    
    private void representa_arbol()
    {
    	Nodo raiz = _arbol.get_raiz();
    	
    	if (raiz == null) { return; }
    	
        DefaultTreeModel model = (DefaultTreeModel) jTree1.getModel();
        DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getRoot();
        root.removeAllChildren();
        DefaultMutableTreeNode first = new DefaultMutableTreeNode(raiz.toString());
        
	    root.add(first);
		
        arbol_recursivo(raiz, first);
      
        //root.removeAllChildren();
       	
        model.reload();
        
        for (int i = 0; i < jTree1.getRowCount(); i++) { //Abre todas las ramas para poder ver el arbol al completo
            jTree1.expandRow(i);
        }
        
    }

}
