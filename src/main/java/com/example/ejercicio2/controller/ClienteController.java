package com.example.ejercicio2.controller;

import com.example.ejercicio2.entity.Cliente;
import com.example.ejercicio2.repository.RepositoryCliente;
import com.example.ejercicio2.security.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/api/cliente")
public class ClienteController {

    @Autowired
    private RepositoryCliente repositoryCliente;

    @Autowired
    private SecurityService securityService;

    //Crea un nuevo Cliente
    @PostMapping("/post")
    public ResponseEntity<Cliente> nuevo (@RequestBody Cliente cliente, @RequestParam String token){
        if(SecurityService.validateToken(token)){
            return new ResponseEntity<>(repositoryCliente.save(cliente), HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    //Devuelve los datos del cliente con el id
    @GetMapping("/id/{id}")
    public Cliente getClientePorId(@PathVariable Long id){
        return repositoryCliente.getClienteById(id);
    }

    //Devuelve una lista con clientes activos con un total mayor al introducido
    @GetMapping("/activoVentaMayor/{total}")
    public List<Cliente> getClientesActivosConMayorVentas(@PathVariable Double total){
        return repositoryCliente.getActivosPorVentaMayor(total);
    }

    //Obtener un resumen estadistico
    @GetMapping("/estadistica")
    public ResponseEntity<Map<String, Object>> getClienteEstadistica(){
        Map<String, Object> estadistica = new HashMap<>();
        estadistica.put("sumaTotales: ", repositoryCliente.getSumaTotales());
        estadistica.put("mediaTotalClientesActivos: ", repositoryCliente.getMediaTotalClientesActivos());
        estadistica.put("numeroDeClientesInactivosConTotalMayoraCero: ", repositoryCliente.getNumeroClientesInactivosTotalMayoCero());

        return new ResponseEntity<>(estadistica, HttpStatus.OK);
    }

}
