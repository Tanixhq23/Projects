package chattingapplication;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.*;
import javax.swing.border.*;

public class Server implements ActionListener {

    JTextField text;
    JPanel a1;
    static Box vertical = Box.createVerticalBox();
    static JFrame f = new JFrame();
    static DataOutputStream dout;

    Server() {
        // Info: Layout
        f.setLayout(null);

        // Info: For Header
        JPanel f1 = new JPanel();
        f1.setBackground(new Color(7, 94, 84));

        // Info: To Set panel location
        f1.setBounds(0, 0, 450, 70);
        f1.setLayout(null);

        // Info: To add panel in the current frame  
        f.add(f1);

        // Topic: To Set image (Back Image)
        ImageIcon i1 = new ImageIcon(getClass().getResource("/chattingapplication/icons/3.png"));
        Image i2 = i1.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel back = new JLabel(i3);
        back.setBounds(5, 20, 25, 25);

        // Info: To add image on the panel
        f1.add(back);

        // Info: To Close the Frame thourgh an Mouse Event
        back.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent ae) {
                System.exit(0);
            }
        });

        // Topic: To set Profile Image
        ImageIcon i4 = new ImageIcon(getClass().getResource("/chattingapplication/icons/1.png"));
        Image i5 = i4.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
        ImageIcon i6 = new ImageIcon(i5);
        JLabel profile = new JLabel(i6);
        profile.setBounds(40, 10, 50, 50);

        // Info: To add image on the panel
        f1.add(profile);

        // Topic: To set Video Call Icon
        ImageIcon i7 = new ImageIcon(getClass().getResource("/chattingapplication/icons/video.png"));
        Image i8 = i7.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
        ImageIcon i9 = new ImageIcon(i8);
        JLabel video = new JLabel(i9);
        video.setBounds(300, 20, 30, 30);

        // Info: To add image on the panel
        f1.add(video);

        // Topic: To set Phone Icon
        ImageIcon i10 = new ImageIcon(getClass().getResource("/chattingapplication/icons/phone.png"));
        Image i11 = i10.getImage().getScaledInstance(35, 50, Image.SCALE_DEFAULT);
        ImageIcon i12 = new ImageIcon(i11);
        JLabel call = new JLabel(i12);
        call.setBounds(360, 20, 35, 30);

        // Info: To add image on the panel
        f1.add(call);

        // Topic: To set More vert Icon
        ImageIcon i13 = new ImageIcon(getClass().getResource("/chattingapplication/icons/3icon.png"));
        Image i14 = i13.getImage().getScaledInstance(10, 25, Image.SCALE_DEFAULT);
        ImageIcon i15 = new ImageIcon(i14);
        JLabel morevert = new JLabel(i15);
        morevert.setBounds(420, 20, 10, 25);

        // Info: To add image on the panel
        f1.add(morevert);

        // Topic: To add text
        JLabel name = new JLabel("Tanishq");
        name.setBounds(110, 15, 100, 18);
        name.setForeground(Color.white);
        name.setFont(new Font("SAN_SERIF", Font.BOLD, 18));
        f1.add(name);
        // Info: For Status
        JLabel status = new JLabel("Active Now");
        status.setBounds(110, 35, 100, 18);
        status.setForeground(Color.white);
        status.setFont(new Font("SAN_SERIF", Font.PLAIN, 14));
        f1.add(status);

        // Topic: For Text Area
        a1 = new JPanel();
        a1.setBounds(5, 75, 440, 570);
        f.add(a1);

        // Topic: For text input area
        text = new JTextField();
        text.setBounds(5, 655, 310, 40);
        text.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
        f.add(text);

        // Info: For Sent Button
        JButton send = new JButton("Send");
        send.setBounds(320, 655, 123, 40);
        send.setBackground(new Color(7, 94, 84));
        send.addActionListener(this); // Info: To add send button action
        send.setForeground(Color.white);
        send.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));

        f.add(send);

        // Info: Basic Frame frontend 
        f.setSize(450, 700);
        f.setLocation(200, 50);
        f.setUndecorated(true);
        f.getContentPane().setBackground(Color.white);
        f.setVisible(true);
    }

    // Info: To get Actions from Frame and perform necessary funtions
    @Override
    public void actionPerformed(ActionEvent ae) {
        try {
            String out = text.getText();

            JPanel p2 = formatLabel(out);

            a1.setLayout(new BorderLayout());

            JPanel right = new JPanel(new BorderLayout());

            // Info: Align to the right side
            right.add(p2, BorderLayout.LINE_END);
            vertical.add(right);

            // Info: Spacing between two messages
            vertical.add(Box.createVerticalStrut(15));

            a1.add(vertical, BorderLayout.PAGE_START);

            // Info: To send the message
            dout.writeUTF(out);

            // Info: To empty the text
            text.setText("");

            // Info: To refresh the frame
            f.repaint();
            f.invalidate();
            f.validate();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static JPanel formatLabel(String out) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel output = new JLabel("<html><p style=\"width: 150px\">" + out + "</p></html>");
        output.setFont(new Font("Tahoma", Font.PLAIN, 16));
        output.setBackground(new Color(37, 211, 102));
        output.setOpaque(true);
        output.setBorder(new EmptyBorder(15, 15, 15, 50));

        panel.add(output);

        // Info: To show the time ofthe message
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

        JLabel time = new JLabel();
        time.setText(sdf.format(cal.getTime()));

        panel.add(time);

        return panel;
    }

    public static void main(String[] args) {
        new Server();

        try {
            ServerSocket skt = new ServerSocket(6001);
            while (true) {
                // Info: Storing the message
                Socket s = skt.accept();
                // Info: To receiver the message
                DataInputStream din = new DataInputStream(s.getInputStream());
                // Info: To send the message
                dout = new DataOutputStream(s.getOutputStream());

                while (true) {
                    String msg = din.readUTF();
                    JPanel panel = formatLabel(msg);

                    JPanel left = new JPanel(new BorderLayout());
                    left.add(panel, BorderLayout.LINE_START);
                    vertical.add(left);
                    f.validate();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println();
        }
    }
}
