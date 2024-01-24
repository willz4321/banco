package com.facundosz.pruebajava.banco;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.facundosz.pruebajava.banco.models.dao.ITipoTransaccionDao;
import com.facundosz.pruebajava.banco.models.entity.Tipo;
import com.facundosz.pruebajava.banco.models.entity.TipoTransaccion;

@SpringBootApplication
public class BancoApplication {

	public static void main(String[] args) {
		SpringApplication.run(BancoApplication.class, args);
	}

	 @Bean
       public CommandLineRunner init(ITipoTransaccionDao tipoTransaccionDao) {
           return args -> {
               for (Tipo tipo : Tipo.values()) {
                   // Busca el tipo en la base de datos
                   TipoTransaccion existingTipo = tipoTransaccionDao.findByNombre(tipo);
       
                   // Si el tipo no existe, crea uno nuevo
                   if (existingTipo == null) {
                       TipoTransaccion tip = new TipoTransaccion();
                       tip.setNombre_transaccion(tipo);
                       tipoTransaccionDao.save(tip);
                   }
               }
           };
       }
}
