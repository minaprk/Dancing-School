import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;
import java.util.*;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class DancingSchool extends JFrame {
    private ArrayList<Choreographer> chs = null;
    private ArrayList<User> users = null;
    private ArrayList<Style> styles = null;
    private ArrayList<DanceClass> classes = null;
    private ArrayList<Appointment> apps = null;
    private ArrayList<Membership> mems = null;
    private Database db = new Database();
    private Reply rep;
    private JPanel si;
    private JPanel main;
    private JPanel am;
    private JPanel um;

    private Scanner in = null;
    private Connection conn;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;

    public DancingSchool(Socket socket) throws IOException {
         oos = new ObjectOutputStream(socket.getOutputStream());
         ois = new ObjectInputStream(socket.getInputStream());
        in =new Scanner(System.in);
        main = new JPanel(null);
//        try {
//            setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File("dancingschool.jpg")))));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        main.setSize(720, 479);
        add(main);


        JButton signIn = new JButton("Sign In");
        signIn.setBounds(480,10,100,30);
        signIn.setBackground(Color.lightGray);
        signIn.setForeground(Color.black);
        main.add(signIn);

        JButton signUp = new JButton("Sign Up");
        signUp.setBounds(600,10,100,30);
        signUp.setBackground(Color.lightGray);
        signUp.setForeground(Color.black);
        main.add(signUp);

        JButton exit = new JButton("Exit");
        exit.setBounds(50, 400, 100, 20);
        exit.setBackground(Color.lightGray);
        main.add(exit);

        try {
            JLabel logo = new JLabel(new ImageIcon(ImageIO.read(new File("logo.png"))));
            logo.setBounds(10,10,200,200);
            main.add(logo);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            JLabel txt = new JLabel(new ImageIcon(ImageIO.read(new File("wdnas.png"))));
            txt.setBounds(100,150,400,40);
            main.add(txt);
            JLabel txt1 = new JLabel(new ImageIcon(ImageIO.read(new File("js.png"))));
            txt1.setBounds(200,200,344,144);
            main.add(txt1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    oos.writeObject(new Request("EXIT"));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        signIn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sign_in();
            }
        });

        signUp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sign_up();
            }
        });
        setLayout(null);
        setSize(720, 479);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void sign_in(){
        si = new JPanel();
        JLabel llab = new JLabel("login: ");
        llab.setBounds(200, 200, 100, 20);
        si.add(llab);

        JLabel plab = new JLabel("password: ");
        plab.setBounds(200, 250, 100, 20);
        si.add(plab);

        JTextField lf = new JTextField();
        lf.setBounds(300, 200, 100, 20);
        si.add(lf);

        JPasswordField pf = new JPasswordField();
        pf.setBounds(300, 250, 100, 20);
        si.add(pf);

        JButton ok = new JButton("Login");
        ok.setBounds(400, 300, 100, 20);
        ok.setBackground(Color.lightGray);
        si.add(ok);

        JButton bb = new JButton("Back");
        bb.setBounds(50, 300, 100, 20);
        bb.setBackground(Color.lightGray);
        si.add(bb);

        bb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                si.setVisible(false);
                main.setVisible(true);
            }
        });

        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String login = lf.getText();
                String password = pf.getText();

                if(login.equals("admin") && password.equals("admin123")){
                    si.setVisible(false);
                    adminMenu();
                }
                else if(db.signIn(login, password) != null) {
                    User u = db.signIn(login, password);
                    si.setVisible(false);
                    userMenu(db.signIn(login, password));
                }
                else {
                    JOptionPane.showMessageDialog(null, "Invalid login or password");
                }
                lf.setText(null);
                pf.setText(null);
            }
        });

        si.setLayout(null);
        si.setSize(720, 479);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        main.setVisible(false);
        si.setVisible(true);
        add(si);
    }

    public void sign_up(){
        JPanel su = new JPanel(null);
        JLabel nlab = new JLabel("Name: ");
        nlab.setBounds(50,50,150,30);
        su.add(nlab);
        JLabel llab = new JLabel("Login: ");
        llab.setBounds(50,80,150,30);
        su.add(llab);
        JLabel plab = new JLabel("Password: ");
        plab.setBounds(50,110,150,30);
        su.add(plab);
        JLabel elab = new JLabel("Email: ");
        elab.setBounds(50,140,150,30);
        su.add(elab);
        JLabel phlab = new JLabel("Phone Number: ");
        phlab.setBounds(50,170,150,30);
        su.add(phlab);

        JTextField ntxt = new JTextField();
        ntxt.setBounds(200,50,150,30);
        su.add(ntxt);
        JTextField ltxt = new JTextField();
        ltxt.setBounds(200,80,150,30);
        su.add(ltxt);
        JTextField ptxt = new JTextField();
        ptxt.setBounds(200,110,150,30);
        su.add(ptxt);
        JTextField etxt = new JTextField();
        etxt.setBounds(200,140,150,30);
        su.add(etxt);
        JTextField phtxt = new JTextField();
        phtxt.setBounds(200,170,150,30);
        su.add(phtxt);

        JButton ok = new JButton("Sign up");
        ok.setBounds(400, 300, 100, 20);
        ok.setBackground(Color.lightGray);
        su.add(ok);

        JButton bb = new JButton("Back");
        bb.setBounds(50, 300, 100, 20);
        bb.setBackground(Color.lightGray);
        su.add(bb);

        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    oos.writeObject(new Request("ADD_USER",new User(null, ntxt.getText(), ltxt.getText(), ptxt.getText(),
                            etxt.getText(), phtxt.getText())));
                    Reply rep = (Reply) ois.readObject();
                    if(rep.getCode().equals("SAVED")){
                        JOptionPane.showMessageDialog(su,"SAVED");
                        ntxt.setText(null);
                        ltxt.setText(null);
                        ptxt.setText(null);
                        etxt.setText(null);
                        phtxt.setText(null);
                    }
                    else {
                        JOptionPane.showMessageDialog(su, "ERROR!");
                    }
                } catch (IOException | ClassNotFoundException exc) {
                    exc.printStackTrace();
                }
            }
        });
        bb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                su.setVisible(false);
                main.setVisible(true);
            }
        });
        su.setSize(720,479);
        main.setVisible(false);
        su.setVisible(true);
        add(su);
    }

    public void adminMenu(){

        am = new JPanel(null);
        am.setBackground(Color.PINK);

        try {
            JLabel admin = new JLabel(new ImageIcon(ImageIO.read(new File("admin.png"))));
            admin.setBounds(10,10,200,133);
            am.add(admin);
        } catch (IOException e) {
            e.printStackTrace();
        }

        JLabel adm = new JLabel("ADMIN MENU");
        adm.setForeground(Color.red);
        adm.setBounds(300,70,200,30);
        am.add(adm);

        JButton ac = new JButton("Add Class");
        ac.setBounds(238, 100, 200, 50);
        ac.setBackground(Color.lightGray);
        am.add(ac);
        JButton dc = new JButton("Delete Class");
        dc.setBounds(238, 150, 200, 50);
        dc.setBackground(Color.lightGray);
        am.add(dc);
        JButton as = new JButton("Add Dance Style");
        as.setBounds(238, 200, 200, 50);
        as.setBackground(Color.lightGray);
        am.add(as);
        JButton ach = new JButton("Add Choreographer");
        ach.setBounds(238, 250, 200, 50);
        ach.setBackground(Color.lightGray);
        am.add(ach);
        JButton ex = new JButton("Go Back");
        ex.setBounds(150, 400, 200, 30);
        ex.setBackground(Color.lightGray);
        am.add(ex);

         ac.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JPanel add_ch = new JPanel(null);
                styles = db.getAllStyles();
                chs = db.getAllChoreographers();
                JLabel sl = new JLabel("style");
                sl.setBounds(10,50,100,30);
                add_ch.add(sl);
                JLabel chl = new JLabel("choreographer");
                chl.setBounds(10,90,100,30);
                add_ch.add(chl);
                JLabel dl = new JLabel("date");
                dl.setBounds(10,130,100,30);
                add_ch.add(dl);
                JLabel tl = new JLabel("time");
                tl.setBounds(10,170,100,30);
                add_ch.add(tl);

                String[] stls1 = new String[styles.size()];
                for(int i = 0; i < styles.size(); i++){
                    assert stls1 != null;
                    stls1[i] = styles.get(i).getName();
                }
                JComboBox sbar = new JComboBox(stls1);
                sbar.setBounds(130,50,100,30);
                add_ch.add(sbar);

                String[] ch_names = new String[chs.size()];
                for(int i = 0; i < chs.size(); i++){
                    assert ch_names != null;
                    ch_names[i] = chs.get(i).getName();
                }
                JComboBox chbar = new JComboBox(ch_names);
                chbar.setBounds(130,90,100,30);
                add_ch.add(chbar);

                String[] month = {"December", "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November"};
                JComboBox mnth = new JComboBox(month);
                mnth.setBounds(130,130,80,30);
                add_ch.add(mnth);

                List<Integer> dates;

                JComboBox <Integer> dts = new JComboBox<>();
                if(Objects.equals(mnth.getSelectedItem(), "December") || Objects.equals(mnth.getSelectedItem(), "January") || mnth.getSelectedItem().equals("March") || mnth.getSelectedItem().equals("May") || mnth.getSelectedItem().equals("July") || mnth.getSelectedItem().equals("August") || mnth.getSelectedItem().equals("October")){
                    dates = IntStream.rangeClosed(1, 31).boxed().collect(Collectors.toList());
                }
                else if(mnth.getSelectedItem().equals("April") || mnth.getSelectedItem().equals("June") || mnth.getSelectedItem().equals("September") || mnth.getSelectedItem().equals("November")){
                    dates = IntStream.rangeClosed(1, 30).boxed().collect(Collectors.toList());
                }
                else{
                    dates = IntStream.rangeClosed(1, 29).boxed().collect(Collectors.toList());
                }

                for(Integer d: dates){
                    dts.addItem(d);
                    System.out.println();
                }
                dts.setBounds(220,130,80,30);
                add_ch.add(dts);

                String[] time = {"14:30 - 16:00","16:00 - 17:30", "17:30 - 19:00", "19:00 - 20:30"};
                JComboBox tbar = new JComboBox(time);
                tbar.setBounds(130,170,100,30);
                add_ch.add(tbar);

                JButton save = new JButton("Save");
                save.setBackground(Color.lightGray);
                save.setBounds(600,400,100,30);
                add_ch.add(save);
                JButton back = new JButton("Back");
                back.setBackground(Color.lightGray);
                back.setBounds(50,400,100,30);
                add_ch.add(back);

                back.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        add_ch.setVisible(false);
                        am.setVisible(true);
                    }
                });

                save.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        Long style_id = null;
                        Long ch_id = null;
                        String style = (String) sbar.getSelectedItem();
                        String choreo = (String) chbar.getSelectedItem();
                        for(Style s: styles){
                            if(s.getName().equals(style)){
                                style_id = s.getId();

                            }
                        }
                        System.out.println(style_id);
                        for(Choreographer ch: chs){
                            if(ch.getName().equals(choreo)){
                                ch_id = ch.getId();
                            }
                        }
                        try {
                            oos.writeObject(new Request("ADD_CLASS",new DanceClass(null, (String)mnth.getSelectedItem(),
                                    (int)dts.getSelectedItem(), (String)tbar.getSelectedItem(), style_id, ch_id)));
                            Reply rep = (Reply) ois.readObject();
                            if(rep.getCode().equals("SAVED")){
                                JOptionPane.showMessageDialog(add_ch,"SAVED");
                            }
                            else {
                                JOptionPane.showMessageDialog(add_ch, "ERROR!");
                            }
                        } catch (IOException | ClassNotFoundException exc) {
                            exc.printStackTrace();
                        }
                    }
                });

                am.setVisible(false);
                add_ch.setVisible(true);
                add_ch.setSize(720,479);
                add(add_ch);
            }
        });
         dc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                classes = db.getAllClasses();
                styles = db.getAllStyles();
                chs = db.getAllChoreographers();
                String[][] strB1;
                String[] strB;
                if (classes.size() != 0) {
                    strB1 = new String[classes.size()][5];
                    strB = new String[]{"style", "choreographer", "date", " time"};

                    for (int i = 0; i < classes.size(); i++) {
                        for (Style s : styles) {
                            for (Choreographer ch : chs) {
                                if (classes.get(i).getCh_id().equals(ch.getId()) && classes.get(i).getStyle_id().equals(s.getId())) {
                                    strB1[i][0] = String.valueOf(classes.get(i).getId());
                                    strB1[i][1] = s.getName();
                                    strB1[i][2] = ch.getName();
                                    strB1[i][3] = classes.get(i).getMonth() + classes.get(i).getDate();
                                    strB1[i][4] = classes.get(i).getTime();
                                }
                            }
                        }
                    }

                    JPanel shc = new JPanel(null);

                    JTable tableB = new JTable(strB1, strB);
                    JScrollPane pane = new JScrollPane(tableB);
                    getContentPane().add(pane,BorderLayout.CENTER);
                    tableB.setBounds(50,50,450,300);
                    tableB.setBackground(Color.LIGHT_GRAY);
                    shc.add(tableB);

                    JLabel ilab = new JLabel("Id:");
                    ilab.setBounds(500,400,100,20);
                    shc.add(ilab);

                    JTextField wtxt = new JTextField();
                    wtxt.setBounds(350,350,50,30);
                    shc.add(wtxt);

                    JButton save = new JButton("Delete");
                    save.setBounds(600,400,100,30);
                    save.setBackground(Color.lightGray);
                    shc.add(save);

                    save.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            for (DanceClass dc: classes) {
                                if (String.valueOf(dc.getId()).equals(wtxt.getText())) {
                                    try {
                                        oos.writeObject(new Request("DELETE_CLASS", dc));
                                    } catch (IOException exc) {
                                        exc.printStackTrace();
                                    }
                                }
                            }
                        }
                    });
                    JButton ex1 = new JButton("Go Back");
                    ex1.setBounds(150, 400, 100, 30);
                    ex1.setBackground(Color.lightGray);
                    shc.add(ex1);


                    am.setVisible(false);
                    shc.setVisible(true);
                    shc.setSize(720,479);
                    add(shc);



                    ex1.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            shc.setVisible(false);
                            am.setVisible(true);
                        }
                    });
                }
            }
        });
         as.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JPanel add_st = new JPanel(null);

                JLabel style_lab = new JLabel("Name: ");
                style_lab.setBounds(50,100,200,30);
                add_st.add(style_lab);

                JTextField style_text = new JTextField();
                style_text.setBounds(250,100,200,30);
                add_st.add(style_text);

                JButton save = new JButton("Save");
                save.setBackground(Color.LIGHT_GRAY);
                save.setBounds(400, 200, 100,30);
                add_st.add(save);

                JButton ex = new JButton("Go Back");
                ex.setBackground(Color.LIGHT_GRAY);
                ex.setBounds(50, 200, 100,30);
                add_st.add(ex);

                ex.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        add_st.setVisible(false);
                        am.setVisible(true);
                    }
                });

                save.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        try {
                            oos.writeObject(new Request("ADD_STYLE",new Style(null, style_text.getText())));
                            Reply rep = (Reply) ois.readObject();
                            if(rep.getCode().equals("SAVED")){
                                JOptionPane.showMessageDialog(add_st,"SAVED");
                                style_text.setText(null);
                            }
                            else {
                                JOptionPane.showMessageDialog(add_st, "ERROR!");
                            }
                        } catch (IOException | ClassNotFoundException exc) {
                            exc.printStackTrace();
                        }
                    }
                });

                am.setVisible(false);
                add_st.setVisible(true);
                add_st.setSize(720,479);
                add(add_st);
            }
        });
         ach.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JPanel add_ch = new JPanel(null);
                try {
                    oos.writeObject("GET_STYLES");
                    Reply rep = (Reply) ois.readObject();
                    styles = rep.getStyles();
                } catch (IOException | ClassNotFoundException exc) {
                    exc.printStackTrace();
                }

                JLabel nlab = new JLabel("Name: ");
                nlab.setBounds(50,50,150,30);
                add_ch.add(nlab);
                JLabel slab = new JLabel("Style: ");
                slab.setBounds(50,90,150,30);
                add_ch.add(slab);
                JLabel elab = new JLabel("Experience: ");
                elab.setBounds(50,130,150,30);
                add_ch.add(elab);

                JTextField ntxt = new JTextField();
                ntxt.setBounds(200,50,150,30);
                add_ch.add(ntxt);

                JTextField etxt = new JTextField();
                etxt.setBounds(200,130,150,30);
                add_ch.add(etxt);

                String[] stls1 = new String[styles.size()];
                for(int i = 0; i < styles.size(); i++){
                    assert stls1 != null;
                    stls1[i] = styles.get(i).getName();
                }
                JComboBox sbar = new JComboBox(stls1);
                sbar.setBounds(200,90,150,30);
                add_ch.add(sbar);

                JButton save = new JButton("Save");
                save.setBackground(Color.LIGHT_GRAY);
                save.setBounds(400, 200, 100,30);
                add_ch.add(save);

                JButton ex = new JButton("Go Back");
                ex.setBackground(Color.LIGHT_GRAY);
                ex.setBounds(50, 200, 100,30);
                add_ch.add(ex);

                ex.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        add_ch.setVisible(false);
                        am.setVisible(true);
                    }
                });
                Long style_id = null;
                for(Style s: styles) {
                    if (s.getName().equals(sbar.getSelectedItem())) {
                        style_id = s.getId();
                        break;
                    }
                }

                JComboBox <Integer> dts = new JComboBox<>();
                for(int i = 0; i < 15; i++){
                    dts.addItem(i);
                }
                dts.setBounds(200,130,150,30);
                add_ch.add(dts);

                Long finalStyle_id = style_id;
                save.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        try {
                            oos.writeObject(new Request("ADD_CHOREOGRAPHER",new Choreographer(null, ntxt.getText(), finalStyle_id, (Integer) dts.getSelectedItem())));
                            Reply rep = (Reply) ois.readObject();
                            if(rep.getCode().equals("SAVED")){
                                JOptionPane.showMessageDialog(add_ch,"SAVED");
                                ntxt.setText(null);
                                etxt.setText(null);
                            }
                            else {
                                JOptionPane.showMessageDialog(add_ch, "ERROR!");
                            }
                        } catch (IOException | ClassNotFoundException exc) {
                            exc.printStackTrace();
                        }
                    }
                });

                add_ch.setSize(720,479);
                am.setVisible(false);
                add_ch.setVisible(true);
                add(add_ch);
            }
        });
         ex.addActionListener(new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent e) {
                 am.setVisible(false);
                 main.setVisible(true);
             }
         });


        am.setSize(720,479);
        am.setVisible(true);
        add(am);
//           System.out.println("2. add choreographer");
//           System.out.println("3. delete choreographer");
    }

    public void userMenu(User u){
        JPanel um = new JPanel(null);
        um.setBackground(Color.pink);

        try {
            JLabel mina = new JLabel(new ImageIcon(ImageIO.read(new File("mina.png"))));
            mina.setBounds(10,100,200,200);
            um.add(mina);
            JLabel motiv = new JLabel(new ImageIcon(ImageIO.read(new File("lifeisdance.jpg"))));
            motiv.setBounds(500,200,100,133);
            um.add(motiv);
        } catch (IOException e) {
            e.printStackTrace();
        }

        JButton shch = new JButton("Show Choreographers");
        shch.setBounds(238, 100, 200, 50);
        shch.setBackground(Color.LIGHT_GRAY);
        um.add(shch);
        JButton shsch = new JButton("Show My Schedule");
        shsch.setBounds(238, 150, 200, 50);
        shsch.setBackground(Color.LIGHT_GRAY);
        um.add(shsch);
        JButton shc = new JButton("Show Classes");
        shc.setBounds(238, 200, 200, 50);
        shc.setBackground(Color.LIGHT_GRAY);
        um.add(shc);
        JButton pm = new JButton("Purchase a Membership");
        pm.setBounds(238, 250, 200, 50);
        pm.setBackground(Color.LIGHT_GRAY);
        um.add(pm);
        JButton gb = new JButton("Go Back");
        gb.setBounds(50, 400, 200, 50);
        gb.setBackground(Color.LIGHT_GRAY);
        um.add(gb);

        shch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                styles = db.getAllStyles();
                chs = db.getAllChoreographers();
                String[][] strB1;
                String[] strB;
                if (chs.size() != 0) {
                    strB1 = new String[chs.size()][3];
                    strB = new String[3];
                    strB[0] = "name";
                    strB[1] = "style";
                    strB[2] = "experience";

                    for (int i = 0; i < chs.size(); i++) {
                        for (Style s : styles) {
                            if (s.getId().equals(chs.get(i).getStyle_id())) {
                                strB1[i][0] = chs.get(i).getName();
                                strB1[i][1] = s.getName();
                                strB1[i][2] = String.valueOf(chs.get(i).getExperience());
                            }
                        }
                    }

                    JPanel shch = new JPanel(null);

                    JButton ex1 = new JButton("Go Back");
                    ex1.setBounds(150, 400, 100, 30);
                    ex1.setBackground(Color.lightGray);
                    shch.add(ex1);

                    ex1.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            shch.setVisible(false);
                            um.setVisible(true);
                        }
                    });

                    JTable table = new JTable(strB1, strB);
                    JScrollPane scrollPane = new JScrollPane(table);
                    getContentPane().add(scrollPane, BorderLayout.CENTER);
                    table.setBounds(10, 10, 450, 300);
                    table.setBackground(Color.LIGHT_GRAY);
                    shch.add(table);
                    shch.setVisible(true);
                    um.setVisible(false);
                    shch.setSize(720,479);
                    add(shch);
                }
            }
        });
        shsch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<Appointment> apps = db.getAllAppointments();
                classes =db.getAllClasses();
                styles = db.getAllStyles();
                chs=db.getAllChoreographers();

                String[][] strB1 = null;
                String[] strB = null;
                if (classes.size() != 0) {
                    strB1 = new String[apps.size()][3];
                    strB = new String[]{"style", "date", " time"};
                    for (int i = 0; i < apps.size(); i++) {
                        for (DanceClass d : classes) {
                            for (Style s : styles) {
                                if (apps.get(i).getUser_id().equals(u.getId()) && apps.get(i).getDance_class_id().equals(d.getId())) {
                                    if (d.getStyle_id().equals(s.getId())) {
                                        strB1[i][0] = s.getName();
                                        strB1[i][1] = d.getMonth() + d.getDate();
                                        strB1[i][2] = d.getTime();
                                    }
                                }

                            }

                        }
                    }

                }


            JPanel shc = new JPanel(null);

            JButton ex = new JButton("Go Back");
                ex.setBackground(Color.LIGHT_GRAY);
                ex.setBounds(50,200,100,30);
                shc.add(ex);

                ex.addActionListener(new

            ActionListener() {
                @Override
                public void actionPerformed (ActionEvent e){
                    shc.setVisible(false);
                    um.setVisible(true);
                }
            });

            JTable tableB = new JTable(strB1, strB);
            JScrollPane pane = new JScrollPane(tableB);

            getContentPane().

            add(pane, BorderLayout.CENTER);
                tableB.setBounds(50,50,450,300);
                tableB.setBackground(Color.LIGHT_GRAY);
                shc.add(tableB);
                shc.setSize(720,479);

            add(shc);
                um.setVisible(false);
                shc.setVisible(true);
            }
        });
        shc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                classes = db.getAllClasses();
                styles = db.getAllStyles();
                chs = db.getAllChoreographers();
                String[][] strB1;
                String[] strB;
                if (classes.size() != 0) {
                    strB1 = new String[classes.size()][5];
                    strB = new String[]{"style", "choreographer", "date", " time"};

                    for (int i = 0; i < classes.size(); i++) {
                        for (Style s : styles) {
                            for (Choreographer ch : chs) {
                                if (classes.get(i).getCh_id().equals(ch.getId()) && classes.get(i).getStyle_id().equals(s.getId())) {
                                    strB1[i][0] = String.valueOf(classes.get(i).getId());
                                    strB1[i][1] = s.getName();
                                    strB1[i][2] = ch.getName();
                                    strB1[i][3] = classes.get(i).getMonth() + classes.get(i).getDate();
                                    strB1[i][4] = classes.get(i).getTime();
                                }
                            }
                        }
                    }

                    JPanel shc = new JPanel(null);

                    JTable tableB = new JTable(strB1, strB);
                    JScrollPane pane = new JScrollPane(tableB);
                    getContentPane().add(pane,BorderLayout.CENTER);
                    tableB.setBounds(50,50,450,300);
                    tableB.setBackground(Color.LIGHT_GRAY);
                    shc.add(tableB);

                    JLabel wlab = new JLabel("Write id");
                    wlab.setBounds(250,350,100,30);
                    shc.add(wlab);
                    JTextField wtxt = new JTextField();
                    wtxt.setBounds(350,350,50,30);
                    shc.add(wtxt);
                    JButton book = new JButton("Book");
                    book.setBackground(Color.LIGHT_GRAY);
                    book.setBounds(350,400,100,30);
                    shc.add(book);

                    book.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if(u.getMem_count() != 0) {
                                for (DanceClass dc : classes) {
                                    if (String.valueOf(dc.getId()).equals(wtxt.getText())) {
                                        try {
                                            oos.writeObject(new Request("ADD_APPOINTMENT", new Appointment(null,
                                                    dc.getId(), u.getId())));
                                            Reply rep = (Reply) ois.readObject();
                                            if (rep.getCode().equals("SAVED")) {
                                                JOptionPane.showMessageDialog(pm, "SAVED");
                                                int n = u.getMem_count();
                                                u.setMem_count(n - 1);
                                                oos.writeObject(new Request("UPDATE_USER", u));
                                            } else {
                                                JOptionPane.showMessageDialog(pm, "ERROR!");
                                            }
                                        } catch (IOException | ClassNotFoundException ex) {
                                            ex.printStackTrace();
                                        }
                                        break;
                                    }
                                }
                            }
                            else{
                                JOptionPane.showMessageDialog(pm, "You don't have any membership!");
                                wtxt.setText(null);
                            }
                        }
                    });

                    JButton ex1 = new JButton("Go Back");
                    ex1.setBounds(150, 400, 100, 30);
                    ex1.setBackground(Color.lightGray);
                    shc.add(ex1);

                    um.setVisible(false);
                    shc.setVisible(true);
                    shc.setSize(720,479);
                    add(shc);


                    ex1.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            shc.setVisible(false);
                            um.setVisible(true);
                        }
                    });
                }
            }
        });
        pm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JPanel pm = new JPanel(null);

                //add image
                try {
                    JLabel price = new JLabel(new ImageIcon(ImageIO.read(new File("pricing.png"))));
                    price.setBounds(50,50,400,455);
                    pm.add(price);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

                JButton c1 = new JButton("Purchase");
                c1.setBackground(Color.LIGHT_GRAY);
                c1.setBounds(450,100, 100,50);
                pm.add(c1);
                JButton c2 = new JButton("Purchase");
                c2.setBackground(Color.LIGHT_GRAY);
                c2.setBounds(450,160, 100,50);
                pm.add(c2);
                JButton c3 = new JButton("Purchase");
                c3.setBackground(Color.LIGHT_GRAY);
                c3.setBounds(450,220, 100,50);
                pm.add(c3);
                JButton c4 = new JButton("Purchase");
                c4.setBackground(Color.LIGHT_GRAY);
                c4.setBounds(450,280, 100,50);
                pm.add(c4);
                JButton c5 = new JButton("Purchase");
                c5.setBackground(Color.LIGHT_GRAY);
                c5.setBounds(450,340, 100,50);
                pm.add(c5);
                JButton c6 = new JButton("Purchase");
                c6.setBackground(Color.LIGHT_GRAY);
                c6.setBounds(450,400, 100,50);
                pm.add(c6);

                c1.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        u.setMem_count(1);
                        try {
                            oos.writeObject(new Request("UPDATE_USER", u));
                            Reply rep = (Reply) ois.readObject();
                            if(rep.getCode().equals("SAVED")){
                                JOptionPane.showMessageDialog(pm,"SAVED");
                            }
                            else {
                                JOptionPane.showMessageDialog(pm, "ERROR!");
                            }
                        } catch (IOException | ClassNotFoundException ex) {
                            ex.printStackTrace();
                        }
                    }
                });
                c2.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        u.setMem_count(5);
                        try {
                            oos.writeObject(new Request("UPDATE_USER",u));
                            Reply rep = (Reply) ois.readObject();
                            if(rep.getCode().equals("SAVED")){
                                JOptionPane.showMessageDialog(pm,"SAVED");
                            }
                            else {
                                JOptionPane.showMessageDialog(pm, "ERROR!");
                            }
                        } catch (IOException | ClassNotFoundException ex) {
                            ex.printStackTrace();
                        }
                    }
                });
                c3.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        u.setMem_count(10);
                        try {
                            oos.writeObject(new Request("UPDATE_USER",u));
                            Reply rep = (Reply) ois.readObject();
                            if(rep.getCode().equals("SAVED")){
                                JOptionPane.showMessageDialog(pm,"SAVED");
                            }
                            else {
                                JOptionPane.showMessageDialog(pm, "ERROR!");
                            }
                        } catch (IOException | ClassNotFoundException ex) {
                            ex.printStackTrace();
                        }
                    }
                });
                c4.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        u.setMem_count(20);
                        try {
                            oos.writeObject(new Request("UPDATE_USER",u));
                            Reply rep = (Reply) ois.readObject();
                            if(rep.getCode().equals("SAVED")){
                                JOptionPane.showMessageDialog(pm,"SAVED");
                            }
                            else {
                                JOptionPane.showMessageDialog(pm, "ERROR!");
                            }
                        } catch (IOException | ClassNotFoundException ex) {
                            ex.printStackTrace();
                        }
                    }
                });
                c5.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        u.setMem_count(30);
                        try {
                            oos.writeObject(new Request("UPDATE_USER",u));
                            Reply rep = (Reply) ois.readObject();
                            if(rep.getCode().equals("SAVED")){
                                JOptionPane.showMessageDialog(pm,"SAVED");
                            }
                            else {
                                JOptionPane.showMessageDialog(pm, "ERROR!");
                            }
                        } catch (IOException | ClassNotFoundException ex) {
                            ex.printStackTrace();
                        }
                    }
                });
                c6.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        u.setMem_count(40);
                        try {
                            oos.writeObject(new Request("UPDATE_USER",u));
                            Reply rep = (Reply) ois.readObject();
                            if(rep.getCode().equals("SAVED")){
                                JOptionPane.showMessageDialog(pm,"SAVED");
                            }
                            else {
                                JOptionPane.showMessageDialog(pm, "ERROR!");
                            }
                        } catch (IOException | ClassNotFoundException ex) {
                            ex.printStackTrace();
                        }
                    }
                });

                JButton bb = new JButton("Go Back");
                bb.setBounds(10,450,100,20);
                bb.setBackground(Color.LIGHT_GRAY);
                pm.add(bb);
                bb.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        pm.setVisible(false);
                        um.setVisible(true);
                    }
                });

                pm.setSize(720,479);
                um.setVisible(false);
                pm.setVisible(true);
                add(pm);
            }
        });
        gb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                um.setVisible(false);
                main.setVisible(true);
            }
        });

        um.setSize(720,479);
        um.setVisible(true);
        add(um);
    }
}

//           if(n == 2){
//               styles = getAllStyles();
//               System.out.println("name: ");
//               String name = in.next();
//               System.out.println("style: ");
//               String style = in.next();
//               Long style_id = null;
//               for(Style s: styles){
//                   if(s.getName().equals(style)){
//                       style_id = s.getId();
//                        break;
//                   }
//                   else{
//                       continue;
//                   }
//               }
//               System.out.println("experience: ");
//               int experience = in.nextInt();
//               addChoreographer(new Choreographer(null, name, style_id, experience));
//           }
//           if(n == 3){
//               chs = getAllChoreographers();
//               for(Choreographer ch: chs){
//                   System.out.println(ch.getId() + ". " + ch.getName());
//               }
//               System.out.println("choose a choreographer to delete(id): ");
//               Long id = in.nextLong();
//               for(Choreographer ch: chs){
//                   if(id.equals(ch.getId())){
//                       removeChoreographer(ch.getId());
//                   }
//               }
//               chs = getAllChoreographers();
//               for(Choreographer ch: chs){
//                   System.out.println(ch.getId() + ch.getName());
//               }
//           }

//    }
//    public void userMenu(User u){
//        while(true){
//                System.out.println("1. show choreographers");
//                System.out.println("2. show styles");
//                System.out.println("3. show my schedule");
//                System.out.println("4. purchase a membership");
//                System.out.println("5. show classes");
//                System.out.println("0. exit");
//                int n = in.nextInt();
//                if(n == 1){
//                    chs = getAllChoreographers();
//                    for( Choreographer ch: chs){
//                        System.out.println(ch.getId() + ch.getName());
//                    }
//                    System.out.println("1. see more about ...");
//                    System.out.println("2. exit");
//                    int n1 = in.nextInt();
//                    if(n1 == 1){
//                        System.out.println("see more about (id): ");
//                        Long id = in.nextLong();
//                        styles = getAllStyles();
//                        for(Choreographer ch: chs){
//                            if(id.equals(ch.getId())){
//                                for(Style s: styles){
//                                    if(s.getId().equals(ch.getStyle_id())){
//                                        System.out.println(ch.getId() + ch.getName() + s.getName() + ch.getExperience());
//                                    }
//                                }
//                            }
//                        }
//                    }
//                    if(n1 == 2){
//                       styles = getAllStyles();
//                       for(Style s: styles){
//                           System.out.println(s);
//                       }
//                    }
//                }
//                if(n == 2){
//                    styles = getAllStyles();
//                    for(Style s: styles){
//                        System.out.println(s.getId() + " " +s.getName());
//                    }
//                }
//                if(n == 3){
//                    ArrayList<Appointment> apps = getAllAppointments();
//                    ArrayList<DanceClass> dcs = getAllClasses();
//                    styles = getAllStyles();
//                    String style_name = "";
//                    for(Appointment a: apps){
//                        for(DanceClass d: dcs) {
//                            for(Style s: styles) {
//                                if (a.getUser_id().equals(u.getId()) && a.getDance_class_id().equals(d.getId())) {
//                                    if(s.getId().equals(d.getStyle_id())){
//                                        style_name = s.getName();
//                                        System.out.println(style_name + " " + d.getMonth() + " " + d.getDate() + " " + d.getTime());
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
//                if(n == 4){
//                    System.out.println("1. 1 Class");
//                    System.out.println("2. 4 Class");
//                    System.out.println("3. 8 Class");
//                    System.out.println("4. 16 Class");
//                    System.out.println("5. 24 Class");
//                    System.out.println("6. Unlimited for a month");
//                    int m = in.nextInt();
//                }
//                if(n == 5){
//                    ArrayList<DanceClass> dcs = getAllClasses();
//                    chs = getAllChoreographers();
//                    styles = getAllStyles();
//                    String style_name = "";
//                    String ch_name = "";
//                    for(DanceClass dc: dcs){
//                        for(Style s: styles){
//                            for(Choreographer ch: chs) {
//                                if(ch.getId().equals(dc.getCh_id()) && s.getId().equals(dc.getStyle_id())){
//                                    style_name = s.getName();
//                                    ch_name = ch.getName();
//                                    System.out.println(dc.getId() + ". " + style_name + ", " + ch_name + ", " + dc.getDate());
//                                }
//                            }
//                        }
//                    }
//                    System.out.println("B. Book now");
//                    System.out.println("E. Exit");
//                    String l = in.next();
//                    if(l.equals("B")){
//                        System.out.println("Choose a class: ");
//                        Long ch1 = in.nextLong();
//                        Long dc_id = null;
//                        for (DanceClass dc: dcs){
//                            if(dc.getId().equals(ch1)){
//                                dc_id = dc.getId();
//                            }
//                        }
//                        addAppointment(new Appointment(null, dc_id, u.getId()));
//                    }
//                    else{
//                        continue;
//                    }
//                }
//                if(n == 0){
//                    break;
//                }
//        }
//    }




