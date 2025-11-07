package org.example.resources;

import org.example.entities.PedidoAutoPecas;
import org.example.services.PedidoAutoPecasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("pedidos")
public class PedidoAutoPecasController {

    @Autowired
    private PedidoAutoPecasService service;

    @GetMapping
    public ResponseEntity<List<PedidoAutoPecas>> listarTodos() {
        List<PedidoAutoPecas> list = service.listarTodos();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoAutoPecas> buscarPorId(@PathVariable Long id) {
        PedidoAutoPecas pedido = service.buscarPorId(id);
        return ResponseEntity.ok().body(pedido);
    }

    @PostMapping
    public ResponseEntity<PedidoAutoPecas> criar(@RequestBody PedidoAutoPecas pedido) {
        PedidoAutoPecas salvo = service.salvar(pedido);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(salvo.getPedId()).toUri();
        return ResponseEntity.created(uri).body(salvo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PedidoAutoPecas> atualizar(@PathVariable Long id, @RequestBody PedidoAutoPecas pedido) {
        PedidoAutoPecas atualizado = service.atualizar(id, pedido);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
