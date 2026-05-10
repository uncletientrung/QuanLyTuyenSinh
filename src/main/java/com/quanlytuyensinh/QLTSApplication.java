package com.quanlytuyensinh;

import com.quanlytuyensinh.GUI.Main;
import com.quanlytuyensinh.GUI.Login;
import javax.swing.SwingUtilities;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class QLTSApplication {

    public static void main(String[] args) {
        SpringApplicationBuilder builder = new SpringApplicationBuilder(QLTSApplication.class);
        builder.headless(false);
        ConfigurableApplicationContext context = builder.run(args);
        
        // GUI Main
        SwingUtilities.invokeLater(() -> {
            Login gui = new Login();
            gui.setVisible(true);
        });
    }
}
