package com.guitarshack;

import com.guitarshack.domain.ProductId;
import com.guitarshack.domain.Quantity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class Program {

    private static String[] args;

    @Autowired
    private ReorderMonitorService reorderMonitorService;

    @PostConstruct
    private void postContruct() {

        // NOTE: this is a crappy workaround and usually input values would use a rest endpoint to provide values
        if (args != null && args.length == 2) {
            ProductId productId = new ProductId(Integer.parseInt(args[0]));
            Quantity quantity = new Quantity(Integer.parseInt(args[1]));
            reorderMonitorService.sold(productId, quantity);
        }
    }

    public static void main(String[] args) {

        Program.args = args;
        SpringApplication.run(Program.class, args);
    }
}